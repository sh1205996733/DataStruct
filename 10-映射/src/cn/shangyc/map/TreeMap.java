package cn.shangyc.map;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

@SuppressWarnings({"unchecked", "unused"})
public class TreeMap<K, V> implements Map<K, V> {
	private static final boolean RED = false;
	private static final boolean BLACK = true;
	private int size;
	private Node<K, V> root;
	
	private Comparator<K> comparator;
	public TreeMap() {
		this(null);
	}

	public TreeMap(Comparator<K> comparator) {
		this.comparator = comparator;
	}
	
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void clear() {
		root = null;
		size = 0;
	}

	@Override
	public V put(K key, V value) {
		keyNotNullCheck(key);
		if (root == null) {
			root = new Node<K,V>(key,value, null);
			size++;
			// 新添加节点之后的处理
			afterPut(root);
			return null;
		}
		// 添加只能是叶子节点，所以必须找到其父节点
		Node<K,V> node = root;
		Node<K,V> parent = root;
		int cmp = 0;
		do {
			cmp = compare(key, node.key);
			parent = node;
			if (cmp > 0) {
				node = node.right;
			} else if (cmp < 0) {
				node = node.left;
			} else {// 相等 直接覆盖
				node.key = key;
				V oldValue = node.value;
				node.value = value;
				return oldValue;
			}
		}while (node != null);
		// 看看插入到父节点的哪个位置
		Node<K,V> newNode = new Node<K,V>(key,value, parent);
		if (cmp > 0) {
			parent.right = newNode;
		} else {
			parent.left = newNode;
		}
		size++;
		// 新添加节点之后的处理
		afterPut(newNode);
		return null;
	}
	
	private void afterPut(Node<K,V> node) {// 修复性质 4
		Node<K,V> parent = node.parent;
		// 添加的是根节点 或者 上溢到达了根节点,染成黑色,直接返回
		if (parent == null) {
			black(node);
			return;
		}
		// 一共12中情况
		// 先判断父节点是否是黑色：四种父节点是黑色(红<--黑-->null、null<--黑-->红、null<--黑-->null)的不用处理，直接返回
		if (isBlack(parent))
			return;

		// 再判断uncle节点是否是红色
		Node<K,V> uncle = parent.subling();
		Node<K,V> grand = red(parent.parent);
		if (isRed(uncle)) {// 如果uncle节点是红色 【B树节点上溢】
			// 四种uncle点是红色的情况(null<--红-->null<--黑-->null<--红-->null)
			// red(grand);
			black(parent);
			black(uncle);
			// 把祖父节点当做是新添加的节点
			afterPut(grand);
			return;
		}
		// 如果uncle节点不是红色
		// 四种uncle点是不红色的情况(null<--红-->null<--黑-->(uncle)null、null(uncle)<--黑-->null<--红-->null)
		// 处理方式先染色、然后再进行LL、LR、RR、RL四种旋转
		if (parent.isLeftChild()) {// L
			if (node.isLeftChild()) {// LL
				black(parent);
			} else {// LR
				black(node);
				rotateLeft(parent);
			}
			rotateRight(grand);
		} else {// R
			if (node.isLeftChild()) {// RL
				black(node);
				rotateRight(parent);
			} else {// RR
				black(parent);
			}
			rotateLeft(grand);
		}
	}
	
	@Override
	public V get(K key) {
		Node<K, V> node = node(key);
		return node == null ? null:node(key).value;
	}

	@Override
	public V remove(K key) {
		return remove(node(key));
	}

	private V remove(Node<K, V> node) {
		if (node == null) return null;
		size--;
		V oldValue = node.value;
		if (node.hasTwoChildren()) {//删除度为2的节点时，找到他的前驱或者后继节点 先交换两值 删除前驱或者后继节点
			// 找到前驱/后继节点
			Node<K,V> predecessor = successor(node);
			// 用后继节点的值覆盖度为2的节点的值
//			predecessor = successor(node);
			node.key = predecessor.key;
			node.value = predecessor.value;//用前驱或者后继节点的值覆盖度为2的节点的值
			node = predecessor;//使用前驱或者后继节点替代node
		}
		// 删除node节点（node的度必然是1或者0）
		Node<K,V> replacement = node.left != null ? node.left:node.right;
		if (replacement != null) {// node是度为1的节点并且是根节点
			// 更改parent
			replacement.parent = node.parent;
			if (node.parent == null){//删除度为0,直接删除
				root = replacement;
			}else if (node.parent.left == node) {
				node.parent.left = replacement;
			}else {
				node.parent.right = replacement;
			}
			// 删除节点之后的处理
			afterRemove(replacement);//注意区分avl树，此处传用以取代被删除节点的子节点，而不是node，因为此时replacement必然为红色，只需将其染黑即可直接返回
		}else if (node.parent == null){// node是叶子节点并且是根节点
			root = null;
		}else {//删除度为0
			if (node == node.parent.left) {
				node.parent.left = null;
			}else {
				node.parent.right = null;
			}
			// 删除节点之后的处理
			afterRemove(node);
		}
		return oldValue;
	}

	private void afterRemove(Node<K, V> node) {
		// 1.如果删除的时候红色 或者 用以取代删除节点的子节点是红色 直接返回
		if (isRed(node)){
			black(node);
			return;// 当删除的节点度为2时，如果用来取代的红色节点直接返回
		}

		// 2.如果删除的黑色叶子节点 会导致B树节点下溢
		// 如果删除的黑色叶子节点是根节点 直接return
		Node<K,V> parent = node.parent;
		if (parent == null)return;

		// 3.如果删除的黑色叶子节点,兄弟节点是黑色并且兄弟节点时有红色节点
		// 进行旋转操作
		// 旋转之后的中心节点继承 parent 的颜色
		// 旋转之后的左右节点染为 BLAC
		// 4.如果删除的黑色叶子节点,兄弟节点是黑色并且兄弟节点没有红色节点(兄弟节点也是叶子节点)
		// 将 sibling 染成 RED、parent 染成 BLACK 即可修复红黑树性质
		// 如果 parent 是 BLACK
		// 会导致 parent 也下溢
		// 这时只需要把 parent 当做被删除的节点处理即可

		// 5.如果删除的黑色叶子节点,兄弟节点是红色
		// sibling 染成 BLACK，parent 染成 RED，进行旋转
		// 于是又回到 sibling 是 BLACK 的情况
		// // 判断被删除的node是左还是右
		boolean left = parent.left == null || node.isLeftChild();//parent.left == null说明当初删除的叶子节点是在左边
		Node<K,V> sibling = left ? parent.right : parent.left;//不能使用node.subling() 因为parent的left和right在删除的时候被清空了
		if (left) { // 被删除的节点在左边，兄弟节点在右边 (左右是对称的)
			// 兄弟节点是红色
			if (isRed(sibling)){
				black(sibling);
				red(parent);
				rotateLeft(parent);
				// 更换兄弟
				sibling = parent.right;
			}
			// 兄弟节点必然是黑色
			if (isBlack(sibling.left) && isBlack(sibling.right)) {// 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
				boolean parentBlack = isBlack(parent);
				black(parent);
				red(sibling);
				if (parentBlack) {
					afterRemove(parent);
				}
			}else {// 兄弟节点至少有1个红色子节点，向兄弟节点借元素
				// 兄弟节点的左边是黑色，兄弟要先旋转
				if (isBlack(sibling.right)) {
					rotateRight(sibling);
					sibling = parent.right;
				} 
				color(sibling, colorOf(parent));
				black(sibling.right);
				black(parent);
				rotateLeft(parent);
			}
		}else {// 被删除的节点在右边，兄弟节点在左边
			// 兄弟节点是红色
			if (isRed(sibling)){
				black(sibling);
				red(parent);
				rotateRight(parent);
				// 更换兄弟
				sibling = parent.left;
			}
			// 兄弟节点必然是黑色
			if (isBlack(sibling.left) && isBlack(sibling.right)) {// 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
				boolean parentBlack = isBlack(parent);
				black(parent);
				red(sibling);
				if (parentBlack) {
					afterRemove(parent);
				}
			}else {// 兄弟节点至少有1个红色子节点，向兄弟节点借元素
				// 兄弟节点的左边是黑色，兄弟要先旋转
				if (isBlack(sibling.left)) {
					rotateLeft(sibling);
					sibling = parent.left;
				} 
				color(sibling, colorOf(parent));
				black(sibling.left);
				black(parent);
				rotateRight(parent);
			}
		}
	}

	@Override
	public boolean containsKey(K key) {
		return node(key) != null;
	}

	@Override
	public boolean containsValue(V value) {
		if (root == null) return false;
		Queue<Node<K, V>> queue = new LinkedList<>();
		queue.offer(root);
		
		while (!queue.isEmpty()) {
			Node<K, V> node = queue.poll();
			if (valEquals(value, node.value)) return true;
			
			if (node.left != null) {
				queue.offer(node.left);
			}
			
			if (node.right != null) {
				queue.offer(node.right);
			}
		}
		
		return false;
	}
	
	private boolean valEquals(V v1, V v2) {
		return v1 == null ? v2 == null : v1.equals(v2);
	}
	
	@Override
	public void traversal(Visitor<K, V> visitor) {
		if (visitor == null) return;
		traversal(root, visitor);
	}
	
	private void traversal(Node<K, V> node, Visitor<K, V> visitor) {
		if (node == null || visitor.stop) return;
		
		traversal(node.left, visitor);
		if (visitor.stop) return;
		visitor.visit(node.key, node.value);
		traversal(node.right, visitor);
	}
	
	// 前驱节点
	private Node<K, V> predecessor(Node<K, V> node) {
		if (node == null) return null;
		
		// 前驱节点在左子树当中（left.right.right.right....）
		Node<K, V> p = node.left;
		if (p != null) {
			while (p.right != null) {
				p = p.right;
			}
			return p;
		}
		
		// 从父节点、祖父节点中寻找前驱节点
		while (node.parent != null && node == node.parent.left) {
			node = node.parent;
		}

		// node.parent == null
		// node == node.parent.right
		return node.parent;
	}
	
	// 后继节点
	private Node<K, V> successor(Node<K, V> node) {
		if (node == null) return null;
		
		// 后继节点在左子树当中（right.left.left.left....）
		Node<K, V> p = node.right;
		if (p != null) {
			while (p.left != null) {
				p = p.left;
			}
			return p;
		}
		
		// 从父节点、祖父节点中寻找前驱节点
		while (node.parent != null && node == node.parent.right) {
			node = node.parent;
		}

		return node.parent;
	}
	
	//根据key值找Node
	private Node<K, V> node(K key) {
		Node<K, V> node= root;
		while (node!= null) {
			int cmp = compare(key, node.key);
			if (cmp == 0) return node;
			if (cmp > 0) {
				node = node.right;
			}else {
				node = node.left;
			}
		}
		return null;
	}
	//左旋转
	private void rotateLeft(Node<K,V> grand) {
		Node<K,V> parent = grand.right;
		Node<K,V> child = parent.left;
		grand.right = child;
		parent.left = grand;
		afterRotate(grand,parent,child);
	}
		
	//右旋转
	private void rotateRight(Node<K,V> grand) {
		Node<K,V> parent = grand.left;
		Node<K,V> child = parent.right;
		grand.left = child;
		parent.right = grand;
		afterRotate(grand,parent,child);
	}
	
	//旋转之后更新节点的parent和高度
	private void afterRotate(Node<K,V> grand, Node<K,V> parent,Node<K,V> child) {
		// 让parent称为子树的根节点
		parent.parent = grand.parent;
		//更新grand.parent的左右子树
		if (grand.isLeftChild()) {
			grand.parent.left = parent;
		} else if(grand.isRightChild()){
			grand.parent.right = parent;
		}else {// grand是root节点
			root = parent;
		}
		
		// 更新child的parent
		if (child != null) {
			child.parent = grand;
		}
		// 更新grand的parent
		grand.parent = parent;
	}
	
	// 染色
	private Node<K,V> color(Node<K,V> node, boolean color) {
		if (node == null)
			return node;
		node.color = color;
		return node;
	}

	// 染成红色
	private Node<K,V> red(Node<K,V> node) {
		return color(node, RED);
	}

	// 染成黑色
	private Node<K,V> black(Node<K,V> node) {
		return color(node, BLACK);
	}

	// 判断节点颜色
	private boolean colorOf(Node<K,V> node) {
		// 空值节点 ：叶子节点默认是黑色(红黑树性质3)
		return node == null ? BLACK : node.color;
	}

	// 判断节点是否是黑色
	private boolean isBlack(Node<K,V> node) {
		return colorOf(node) == BLACK;
	}

	// 判断节点是否是红色
	private boolean isRed(Node<K,V> node) {
		return colorOf(node) == RED;
	}
	
	/**
	 * @return 返回值等于0，代表e1和e2相等；返回值大于0，代表e1大于e2；返回值小于于0，代表e1小于e2
	 */
	private int compare(K k1, K k2) {
		if (comparator != null) {
			return comparator.compare(k1, k2);
		}
		return ((Comparable<K>)k1).compareTo(k2);
	}
	
	//key 不能为空
	private void keyNotNullCheck(K key) {
		if (key == null) {
			throw new IllegalArgumentException("key must not be null");
		}
	}
	private static class Node<K,V> {
		private boolean color = RED;// 默认是红色(建议新添加的节点默认为 RED，这样能够让红黑树的性质尽快满足（性质 1、2、3、5
							// 都满足，性质 4 不一定）)
		private K key;
		private V value;
		private Node<K,V> parent;
		private Node<K,V> left;
		private Node<K,V> right;
		
		public Node(K key,V value, Node<K,V> parent) {
			this.key = key;
			this.value = value;
			this.parent = parent;
		}
		
		public boolean isLeaf() {
			return left == null && right == null;
		}

		public boolean hasTwoChildren() {
			return left != null && right != null;
		}
		
		public boolean isLeftChild() {
			return parent != null && parent.left == this;
		}
		public boolean isRightChild() {
			return parent != null && parent.right == this;
		}
		//返回兄弟节点
		public Node<K,V> subling() {
			if (isLeftChild()) {
				return parent.right;
			}
			if (isRightChild()){
				return parent.left;
			}
			return null;
		}
	}
}
