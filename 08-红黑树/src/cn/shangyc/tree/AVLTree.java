package cn.shangyc.tree;

import java.util.Comparator;

//AVL树
public class AVLTree<E> extends BBST<E> {
	public AVLTree() {
		this(null);
	}

	public AVLTree(Comparator<E> comparator) {
		super(comparator);
	}
	
	@Override
	protected void afterAdd(Node<E> node) {// 添加元素之后，父节点不可能失衡 但是可能会导致所有祖先节点都失衡，但是只需要恢复一次就可以了
		while ((node = node.parent) != null) {
			if (isBalanced(node)) {//父节点是否平衡
				// 更新高度
				updateHeight(node);
			} else {//父节点、祖父节点必然不为空
				// 恢复平衡
				rebalance(node);//只需要恢复一次就可以了
				// 整棵树恢复平衡
				break;
			}
		}
	}
	
	@Override
	protected void afterRemove(Node<E> node) {// 删除元素之后，父节点和祖先节点都可能失衡
		while ((node = node.parent) != null) {
			if (isBalanced(node)) {
				// 更新高度
				updateHeight(node);
			} else {
				// 恢复平衡
				rebalance(node);
			}
		}
	}
	
	@Override
	protected Node<E> createNode(E element, Node<E> parent) {
		return new AVLNode<>(element, parent);
	}
	
	/**
	 * 恢复平衡
	 * @param grand 高度最低的那个不平衡节点
	 */
	@SuppressWarnings("unused")
	private void rebalance0(Node<E> grand) {
		Node<E> parent = ((AVLNode<E>)grand).tallerChild();//最高的child 必然不为空
		Node<E> node = ((AVLNode<E>)parent).tallerChild();//最高的child，可能为空
		if (parent.isLeftChild()) {//L
			if (node.isLeftChild()) {//LL
				rotateRight(grand);
			}else{//LR
				rotateLeft(parent);
				rotateRight(grand);
			}
		}else {//R
			if (node.isLeftChild()) {//RL
				rotateRight(parent);
				rotateLeft(grand);
			}else{//RR
				rotateLeft(grand);
			}
		}
	}
	
	@Override
	protected void afterRotate(Node<E> grand, Node<E> parent,Node<E> child) {
		super.afterRotate(grand, parent, child);
		updateHeight(parent);
		updateHeight(grand);
    }
	/**
	 * 恢复平衡(统一操作)
	 * @param grand 高度最低的那个不平衡节点
	 */
	private void rebalance(Node<E> grand) {
		Node<E> parent = ((AVLNode<E>)grand).tallerChild();
		Node<E> node = ((AVLNode<E>)parent).tallerChild();
		if (parent.isLeftChild()) { // L
			if (node.isLeftChild()) { // LL
				rotate(grand, node, node.right, parent, parent.right, grand);
			} else { // LR
				rotate(grand, parent, node.left, node, node.right, grand);
			}
		} else { // R
			if (node.isLeftChild()) { // RL
				rotate(grand, grand, node.left, node, node.right, parent);
			} else { // RR
				rotate(grand, grand, parent.left, parent, node.left, node);
			}
		}
	}
	
	@Override
	protected void rotate(
			Node<E> r, // 子树的根节点
			Node<E> b, Node<E> c,
			Node<E> d,
			Node<E> e, Node<E> f) {
		super.rotate(r,b,c,d,e,f);
		updateHeight(b);
		updateHeight(f);
		updateHeight(d);
	}
	
	/**
	 * 更新高度
	 * @param node
	 */
	private void updateHeight(Node<E> node) {
		((AVLNode<E>)node).updateHeight();
	}

	/**
	 * 是否平衡：左右高度差(平衡因子)绝对值小于等于1
	 * @param node
	 * @return
	 */
	private boolean isBalanced(Node<E> node) {
		return ((AVLNode<E>)node).balanceFactor() <=1;
	}
	
	private static class AVLNode<E> extends Node<E> {
		int height = 1;
		
		public AVLNode(E element, Node<E> parent) {
			super(element, parent);
		}
		public int leftHeight() {
			return left == null ? 0: ((AVLNode<E>)left).height;
		}
		public int rightHeight() {
			return right == null ? 0: ((AVLNode<E>)right).height;
		}
		//更新高度height (最高的子树高度加1)
		public void updateHeight() {
			height = Math.max(leftHeight(), rightHeight()) + 1;
		}
		
		//计算平衡因子(左右高度差的绝对值)
		public int balanceFactor() {
			return Math.abs(leftHeight()- rightHeight());
		}
		
		//返回node 最高的子节点
		public Node<E> tallerChild() {
			int leftHeight = leftHeight();
			int rightHeight = rightHeight();
			if (leftHeight > rightHeight) return left;
			if (leftHeight < rightHeight) return right;
			return isLeftChild() ? left : right;//相等看一下父节点是左子树还是右子树
		}
		@Override
		public String toString() {
			String parentString = "null";
			if (parent != null) {
				parentString = parent.element.toString();
			}
			return element + "_p(" + parentString + ")_h(" + height + ")";
		}
	}
}
