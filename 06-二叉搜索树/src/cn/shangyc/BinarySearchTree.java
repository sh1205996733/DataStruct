package cn.shangyc;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import cn.shangyc.printer.BinaryTreeInfo;

//二叉搜索树
@SuppressWarnings("unchecked")
public class BinarySearchTree<E> implements BinaryTreeInfo {
	private int size;
	private Node<E> root;
	private Comparator<E> comparator;

	public BinarySearchTree() {
		this(null);
	}

	public BinarySearchTree(Comparator<E> comparator) {
		this.comparator = comparator;
	}

	private static class Node<E> {
		private E element;
		private Node<E> parent;
		private Node<E> left;
		private Node<E> right;

		public Node(E element, Node<E> parent) {
			this.element = element;
			this.parent = parent;
		}

		public boolean isLeaf() {
			return left == null && right == null;
		}

		public boolean hasTwoChildren() {
			return left != null && right != null;
		}
		@Override
		public String toString() {
			String parentString = "null";
			if (parent != null) {
				parentString = parent.element.toString();
			}
			return element + "_p(" + parentString + ")";
		}
	}

	public static abstract class Visitor<E> {
		boolean stop;

		/**
		 * @return 如果返回true，就代表停止遍历
		 */
		public abstract boolean visit(E element);
	}

	public int size() { // 元素的数量
		return size;
	}

	public boolean isEmpty() { // 是否为空
		return size == 0;
	}

	public void clear() { // 清空所有元素
		size = 0;
		root = null;
	}

	public void add(E element) { // 添加元素
		elementNotNullCheck(element);
		if (root == null) {
			root = new Node<E>(element, null);
			size++;
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
		Node<E> newNode = new Node<>(element, parent);
		if (cmp > 0) {
			parent.right = newNode;
		} else {
			parent.left = newNode;
		}
		size++;
	}

	/**
	 * 比较器不为空：则使用比较器的compare，否则强转为Comparable接口、e1、e2必须实现Comparable接口，否则会报错
	 * 
	 * @return 返回值等于0，代表e1和e2相等；返回值大于0，代表e1大于e2；返回值小于于0，代表e1小于e2
	 */
	private int compare(E e1, E e2) {
		if (comparator != null) {
			return comparator.compare(e1, e2);
		}
		return ((Comparable<E>) e1).compareTo(e2);
		// return (Integer)e1-(Integer)e2;
	}

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
		Node<E> child = node.left != null ? node.left:node.right;
		if (child != null) {// node是度为1的节点并且是根节点
			// 更改parent
			child.parent = node.parent;
			if (node.parent == null){//删除度为0,直接删除
				root = child;
			}else if (node.parent.left == node) {
				node.parent.left = child;
			}else {
				node.parent.right = child;
			}
		}else if (node.parent == null){// node是叶子节点并且是根节点
			root = null;
		}else {//删除度为0
			if (node == node.parent.left) {
				node.parent.left = null;
			}else {
				node.parent.right = null;
			}
		}
	}

	public boolean contains(E element) { // 是否包含某元素
		return node(element) != null;
	}

	/**
	 * 前序遍历基础版
	 */
	public void preorderTraversal() {
		preorderTraversal(root);
	}

	private void preorderTraversal(Node<E> node) {
		if (node == null)
			return;
		System.out.print(node.element + " ");
		preorderTraversal(node.left);
		preorderTraversal(node.right);
	}

	/**
	 * 中序遍历基础版
	 */
	public void inorderTraversal() {
		inorderTraversal(root);
	}

	private void inorderTraversal(Node<E> node) {
		if (node == null)
			return;
		inorderTraversal(node.left);
		System.out.print(node.element + " ");
		inorderTraversal(node.right);
	}

	/**
	 * 后序遍历基础版
	 */
	public void postorderTraversal() {
		postorderTraversal(root);
	}

	private void postorderTraversal(Node<E> node) {
		if (node == null)
			return;
		postorderTraversal(node.left);
		postorderTraversal(node.right);
		System.out.print(node.element + " ");
	}
	/**
	 * 前序遍历非递归版
	 */
	public void preorderIterate(Visitor<E> visitor) {
		if (visitor == null || root == null) return;
		preorderIterate(root,visitor);
	}
	
	@SuppressWarnings("unused")
	private void preorderIterate0(Node<E> root,Visitor<E> visitor) {
		if (root == null)
			return;
		Stack<Node<E>> stack = new Stack<>();
		Node<E> top = root;
		while (top!=null) {
			// 访问node节点
			if (visitor.visit(top.element)) return;
			if (top.right != null){//右边不为空 就入栈
				stack.push(top.right);
			}
			if (top.left != null){//左边不为空 就一直向左遍历
				top = top.left;
			} else {//左边为空 就从栈queue中取出栈顶对象，赋值给node
				if (stack.isEmpty()) {
					break;
				}
				top = stack.pop();
			}
		}
	}
	private void preorderIterate(Node<E> root,Visitor<E> visitor) {
		Stack<Node<E>> stack = new Stack<>();
		stack.push(root);
		while (!stack.isEmpty()) {
			Node<E> top = stack.pop();
			// 访问node节点
			if (visitor.visit(top.element)) return;
			if (top.right != null){//右边不为空 就入栈
				stack.push(top.right);
			}
			if (top.left != null){//左边不为空 就一直向左遍历
				stack.push(top.left);
			} 
		}
	}
	
	/**
	 * 中序遍历非递归版
	 */
	public void inorderIterate(Visitor<E> visitor) {
		if (visitor == null || root == null) return;
		inorderIterate(root,visitor);
	}
	
	private void inorderIterate(Node<E> root,Visitor<E> visitor) {
		Stack<Node<E>> stack = new Stack<>();
		Node<E> node = root;
		while (!stack.isEmpty()) {
			if (node != null) {
				stack.push(node);
				node = node.left;
			} else {//左边为空
				node = stack.pop();
				// 访问节点
				if (visitor.visit(node.element)) return;
				node = node.right;
			}
		}
	}
	
	/**
	 * 后序遍历非递归版
	 */
	public void postorderIterate(Visitor<E> visitor) {
		if (visitor == null || root == null) return;
		postorderIterate(root,visitor);
	}
	
	private void postorderIterate(Node<E> root,Visitor<E> visitor) {
		Stack<Node<E>> stack = new Stack<>();
		// 记录上一次弹出访问的节点
		Node<E> prev = null;
		stack.push(root);
		while (!stack.isEmpty()) {
			Node<E> top = stack.peek();
			if (top.isLeaf() || (prev != null && prev.parent == top)) {//如果是叶子节点 或者上一个弹出节点等于top
				prev = stack.pop();
				// 访问节点
				if (visitor.visit(prev.element)) return;
			} else {
				if (top.right != null){//右边不为空 就入栈
					stack.push(top.right);
				}
				if (top.left != null){//左边不为空 就一直向左遍历
					stack.push(top.left);
				}
			}
		}
	}

	/**
	 * 层序遍历基础版
	 */
	public void levelOrderTraversal() {
		if (root == null)
			return;
		Queue<Node<E>> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			Node<E> node = queue.poll();
			System.out.print(node.element + " ");
			if (node.left != null) {
				queue.offer(node.left);
			}
			if (node.right != null) {
				queue.offer(node.right);
			}
		}
	}

	/**
	 * 前序遍历增强版
	 */
	public void preorder(Visitor<E> visitor) {
		if (visitor == null)
			return;
		preorder(root, visitor);
	}

	private void preorder(Node<E> root, Visitor<E> visitor) {
		if (root == null || visitor.stop)
			return;

		visitor.stop = visitor.visit(root.element);
		preorder(root.left, visitor);
		preorder(root.right, visitor);
	}

	/**
	 * 中序遍历增强版
	 */
	public void inorder(Visitor<E> visitor) {
		if (visitor == null)
			return;
		inorder(root, visitor);
	}

	private void inorder(Node<E> root, Visitor<E> visitor) {
		if (root == null || visitor.stop)
			return;// 结束递归
		inorder(root.left, visitor);
		if (visitor.stop)
			return;// 结束打印
		visitor.stop = visitor.visit(root.element);
		inorder(root.right, visitor);
	}

	/**
	 * 后序遍历增强版
	 */
	public void postorder(Visitor<E> visitor) {
		if (visitor == null)
			return;
		postorder(root, visitor);
	}

	private void postorder(Node<E> root, Visitor<E> visitor) {
		if (root == null || visitor.stop)
			return;// 结束递归
		postorder(root.left, visitor);
		postorder(root.right, visitor);
		if (visitor.stop)
			return;// 结束打印
		visitor.stop = visitor.visit(root.element);
	}

	/**
	 * 层次遍历增强版
	 */
	public void levelOrder(Visitor<E> visitor) {
		if (root == null || visitor == null) return;
		Queue<Node<E>> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			Node<E> node = queue.poll();
			if (visitor.visit(node.element))
				return;
			if (node.left != null)
				queue.offer(node.left);
			if (node.right != null)
				queue.offer(node.right);
		}
	}
	
	//前驱节点(中序遍历前一个)
	@SuppressWarnings("unused")
	private Node<E> predecessor(Node<E> node) {
		if (node == null)
			return null;
		//前驱节点在左子树当中（left.right.right.right....）
		Node<E> p = node.left;
		if (p != null) {//node.left != null
			while (p.right != null) {
				p = p.right;
			}
			return p;
		}
		// 从父节点、祖父节点中寻找前驱节点（node.parent.parent.parent....）
		while (node.parent != null && node == node.parent.left) {//node.left == null && node.parent != null
			node = node.parent;
		}
		// node.left == null && node.parent == null
		// node == node.parent.right
		return node.parent;
	}
	
	//后继节点(中序遍历后一个)
	private Node<E> successor(Node<E> node) {
		if (node == null) return null;
		//后继节点在右子树当中（right.left.left.left....）
		Node<E> p = node.right;
		if (p != null) {//node.left != null
			while (p.left != null) {
				p = p.left;
			}
			return p;
		}
		// 从父节点、祖父节点中寻找前驱节点（node.parent.parent.parent....）
		while (node.parent != null && node == node.parent.right) {//node.left == null && node.parent != null
			node = node.parent;
		}
		return node.parent;
	}
	
	/**
	 * 判断是否是完全二叉树
	 * 
	 * @return
	 */
	public boolean isComplete0() {
		if (root == null)
			return false;
		Queue<Node<E>> queue = new LinkedList<>();
		queue.offer(root);
		boolean isLeaf = false;
		while (!queue.isEmpty()) {
			Node<E> node = queue.poll();
			if (isLeaf && !node.isLeaf()) {// 往后再遍历的节点都必须是叶子节点，但当前节点不是叶子节点 直接返回false
				return false;
			}
			if (node.hasTwoChildren()) {// node.left != null && node.right !=null
				queue.offer(node.left);
				queue.offer(node.right);
			} else if (node.isLeaf()) {// node.left == null && node.right ==null
				isLeaf = true;// 遇到第一个叶子节点设置为true，往后再遍历的节点都必须是叶子节点
			} else if (node.left != null) {// node.left != null && node.right ==null
				queue.offer(node.left);
			} else {// node.left == null && node.right != null
				return false;
			}
		}
		return true;
	}

	public boolean isComplete() {
		if (root == null)
			return false;
		Queue<Node<E>> queue = new LinkedList<>();
		queue.offer(root);
		boolean isLeaf = false;
		while (!queue.isEmpty()) {
			Node<E> node = queue.poll();
			if (isLeaf && !node.isLeaf()) return false;
			if (node.left != null) {
				queue.offer(node.left);
			}else if (node.right != null){//node.left == null && node.right != null
				return false;
			}
			//node.left != null
			if (node.right != null) {//node.left != null && node.right != null
				queue.offer(node.right);
			}else {//node.left != null && node.right == null
				isLeaf = true;
			}
		}
		return true;
	}

	// 树的高度
	public int height() {
		return height(root);
	}

	// 递归
	// private int height(Node<E> root){
	// if (root == null) return 0;
	// return 1+ Math.max(height(root.left), height(root.right));
	// }
	// 非递归
	private int height(Node<E> root) {
		if (root == null)
			return 0;
		Queue<Node<E>> queue = new LinkedList<>();
		queue.offer(root);
		// 树的高度
		int height = 0;
		// 存储着每一层的元素数量
		int levelSize = 1;
		while (!queue.isEmpty()) {
			Node<E> node = queue.poll();
			levelSize--;
			if (node.left != null) {
				queue.offer(node.left);
			}
			if (node.right != null) {
				queue.offer(node.right);
			}
			if (levelSize == 0) {
				height++;
				levelSize = queue.size();
			}
		}
		return height;
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

	// 空元素校验
	private void elementNotNullCheck(E element) {
		if (element == null) {
			throw new IllegalArgumentException("element must not be null");
		}
	}

	@Override
	public Object root() {
		return root;
	}

	@Override
	public Object left(Object node) {
		return ((Node<E>) node).left;
	}

	@Override
	public Object right(Object node) {
		return ((Node<E>) node).right;
	}

	@Override
	public Object string(Object node) {
		Node<E> myNode = (Node<E>) node;
		String parentString = "null";
		if (myNode.parent != null) {
			parentString = myNode.parent.element.toString();
		}
		return myNode.element + "_p(" + parentString + ")";
	}
}
