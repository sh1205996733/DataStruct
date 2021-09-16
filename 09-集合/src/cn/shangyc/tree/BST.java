package cn.shangyc.tree;

import java.util.Comparator;

//二叉搜索树
public class BST<E> extends BinaryTree<E>{
	private Comparator<E> comparator;
	public BST() {
		this(null);
	}

	public BST(Comparator<E> comparator) {
		this.comparator = comparator;
	}
	
	public void add(E element) { // 添加元素
		elementNotNullCheck(element);
		if (root == null) {
			root = createNode(element, null);
			size++;
			// 新添加节点之后的处理
			afterAdd(root);
			return;
		}
		// 添加只能是叶子节点，所以必须找到其父节点
		Node<E> node = root;
		Node<E> parent = root;
		int cmp = 0;
		while (node != null) {
			cmp = compare(element, node.element);
			parent = node;
			if (cmp > 0) {
				node = node.right;
			} else if (cmp < 0) {
				node = node.left;
			} else {// 相等 直接覆盖
				node.element = element;
				return;
			}
		}
		// 看看插入到父节点的哪个位置
		Node<E> newNode = createNode(element, parent);
		if (cmp > 0) {
			parent.right = newNode;
		} else {
			parent.left = newNode;
		}
		size++;
		// 新添加节点之后的处理
		afterAdd(newNode);
	}
	
	/**
	 * 添加node之后的调整
	 * @param node 新添加的节点
	 */
	protected void afterAdd(Node<E> node) { }
	
	/**
	 * 删除node之后的调整
	 * @param node 被删除的节点
	 */
	protected void afterRemove(Node<E> node) { }
	
	public void remove(E element) { // 删除元素
		remove(node(element));
	}
	
	//删除指定节点
	private void remove(Node<E> node) {
		if (node == null) return;
		size--;
		if (node.hasTwoChildren()) {//删除度为2的节点时，找到他的前驱或者后继节点 先交换两值 删除前驱或者后继节点
			// 找到前驱/后继节点
			Node<E> predecessor = successor(node);
//			predecessor = successor(node);
			node.element = predecessor.element;//用前驱或者后继节点的值覆盖度为2的节点的值
			node = predecessor;//使用前驱或者后继节点替代node
		}
		// 删除node节点（node的度必然是1或者0）
		Node<E> replacement = node.left != null ? node.left:node.right;
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
			afterRemove(replacement);
		}else if (node.parent == null){// node是叶子节点并且是根节点
			root = null;
			// 删除节点之后的处理
			afterRemove(node);
		}else {//删除度为0
			if (node == node.parent.left) {
				node.parent.left = null;
			}else {
				node.parent.right = null;
			}
			// 删除节点之后的处理
			afterRemove(node);
		}
	}

	public boolean contains(E element) { // 是否包含某元素
		return node(element) != null;
	}
	
	// 根据元素内容找节点
	private Node<E> node(E element) {
		Node<E> node = root;
		while (node != null) {
			int cmp = compare(element, node.element);
			if (cmp == 0)
				return node;
			if (cmp > 0) {
				node = node.right;
			} else {// cmp < 0
				node = node.left;
			}
		}
		return null;
	}
	/**
	 * @return 返回值等于0，代表e1和e2相等；返回值大于0，代表e1大于e2；返回值小于于0，代表e1小于e2
	 */
	@SuppressWarnings("unchecked")
	private int compare(E e1, E e2) {
		if (comparator != null) {
			return comparator.compare(e1, e2);
		}
		return ((Comparable<E>)e1).compareTo(e2);
	}
	
	private void elementNotNullCheck(E element) {
		if (element == null) {
			throw new IllegalArgumentException("element must not be null");
		}
	}
}
