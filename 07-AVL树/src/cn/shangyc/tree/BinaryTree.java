package cn.shangyc.tree;

import java.util.LinkedList;
import java.util.Queue;

import cn.shangyc.printer.BinaryTreeInfo;

//二叉树
@SuppressWarnings("unchecked")
public class BinaryTree<E> implements BinaryTreeInfo {
	protected int size;
	protected Node<E> root;
	
	protected static class Node<E> {
		protected E element;
		protected Node<E> parent;
		protected Node<E> left;
		protected Node<E> right;

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
		
		public boolean isLeftChild() {
			return parent != null && parent.left == this;
		}
		public boolean isRightChild() {
			return parent != null && parent.right == this;
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
	
	
	protected Node<E> createNode(E element, Node<E> parent) {
		return new Node<>(element, parent);
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
		if (root == null)
			return;
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
	protected Node<E> successor(Node<E> node) {
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
