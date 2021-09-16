package cn.shangyc.tree;

import java.util.Comparator;

//红黑树
public class RBTree<E> extends BBST<E> {
	private static final boolean RED = false;
	private static final boolean BLACK = true;

	public RBTree() {
		this(null);
	}

	public RBTree(Comparator<E> comparator) {
		super(comparator);
	}

	@Override
	protected void afterAdd(Node<E> node) {// 修复性质 4
		Node<E> parent = node.parent;
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
		Node<E> uncle = parent.subling();
		Node<E> grand = red(parent.parent);
		if (isRed(uncle)) {// 如果uncle节点是红色 【B树节点上溢】
			// 四种uncle点是红色的情况(null<--红-->null<--黑-->null<--红-->null)
			// red(grand);
			black(parent);
			black(uncle);
			// 把祖父节点当做是新添加的节点
			afterAdd(grand);
			return;
		}
		// 如果uncle节点不是红色
		// 四种uncle点是不红色的情况(null<--红-->null<--黑-->(uncle)null、null(uncle)<--黑-->null<--红-->null)
		// 处理方式先染色、然后再进行LL、LR、RR、RL四种旋转

		// red(grand);
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
	
	protected void afterRemove(Node<E> node) {
		// 1.如果删除的时候红色 或者 用以取代删除节点的子节点是红色 直接返回
		if (isRed(node)){
			black(node);
			return;// 当删除的节点度为2时，如果用来取代的红色节点直接返回
		}

		// 2.如果删除的黑色叶子节点 会导致B树节点下溢
		// 如果删除的黑色叶子节点是根节点 直接return
		Node<E> parent = node.parent;
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
		Node<E> sibling = left ? parent.right : parent.left;//不能使用node.subling() 因为parent的left和right在删除的时候被清空了
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
	
//	@Override
//	protected void afterRemove0(Node<E> node, Node<E> replacement) {
//		// 1.如果删除的时候红色 直接返回
//		if (isRed(node))
//			return;// 当删除的节点度为2时，如果用来取代的红色节点直接返回
//
//		// 删除的node的是黑色非叶子节点
//		if (isRed(replacement)) {// 当删除的节点度为1时，只需将用来取代的红色节点染成黑色，否则不满足红黑树的性质5
//			black(replacement);
//			return;
//		}
//
//		// 2.如果删除的黑色叶子节点 会导致B树节点下溢
//		// 如果删除的黑色叶子节点是根节点 直接return
//		Node<E> parent = node.parent;
//		if (parent == null)return;
//
//		// 3.如果删除的黑色叶子节点,兄弟节点是黑色并且兄弟节点时有红色节点
//		// 进行旋转操作
//		// 旋转之后的中心节点继承 parent 的颜色
//		// 旋转之后的左右节点染为 BLAC
//		// 4.如果删除的黑色叶子节点,兄弟节点是黑色并且兄弟节点没有红色节点(兄弟节点也是叶子节点)
//		// 将 sibling 染成 RED、parent 染成 BLACK 即可修复红黑树性质
//		// 如果 parent 是 BLACK
//		// 会导致 parent 也下溢
//		// 这时只需要把 parent 当做被删除的节点处理即可
//
//		// 5.如果删除的黑色叶子节点,兄弟节点是红色
//		// sibling 染成 BLACK，parent 染成 RED，进行旋转
//		// 于是又回到 sibling 是 BLACK 的情况
//		// // 判断被删除的node是左还是右
//		boolean left = parent.left == null || node.isLeftChild();//parent.left == null说明当初删除的叶子节点是在左边
//		Node<E> sibling = left ? parent.right : parent.left;//不能使用node.subling() 因为parent的left和right在删除的时候被清空了
//		if (left) { // 被删除的节点在左边，兄弟节点在右边 (左右是对称的)
//			// 兄弟节点是红色
//			if (isRed(sibling)){
//				black(sibling);
//				red(parent);
//				rotateLeft(parent);
//				// 更换兄弟
//				sibling = parent.right;
//			}
//			// 兄弟节点必然是黑色
//			if (isBlack(sibling.left) && isBlack(sibling.right)) {// 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
//				boolean parentBlack = isBlack(parent);
//				black(parent);
//				red(sibling);
//				if (parentBlack) {
//					afterRemove(parent, null);
//				}
//			}else {// 兄弟节点至少有1个红色子节点，向兄弟节点借元素
//				// 兄弟节点的左边是黑色，兄弟要先旋转
//				if (isBlack(sibling.right)) {
//					rotateRight(sibling);
//					sibling = parent.right;
//				} 
//				color(sibling, colorOf(parent));
//				black(sibling.right);
//				black(parent);
//				rotateLeft(parent);
//			}
//		}else {// 被删除的节点在右边，兄弟节点在左边
//			// 兄弟节点是红色
//			if (isRed(sibling)){
//				black(sibling);
//				red(parent);
//				rotateRight(parent);
//				// 更换兄弟
//				sibling = parent.left;
//			}
//			// 兄弟节点必然是黑色
//			if (isBlack(sibling.left) && isBlack(sibling.right)) {// 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
//				boolean parentBlack = isBlack(parent);
//				black(parent);
//				red(sibling);
//				if (parentBlack) {
//					afterRemove(parent, null);
//				}
//			}else {// 兄弟节点至少有1个红色子节点，向兄弟节点借元素
//				// 兄弟节点的左边是黑色，兄弟要先旋转
//				if (isBlack(sibling.left)) {
//					rotateLeft(sibling);
//					sibling = parent.left;
//				} 
//				color(sibling, colorOf(parent));
//				black(sibling.left);
//				black(parent);
//				rotateRight(parent);
//			}
//		}
//	}

	@Override
	protected Node<E> createNode(E element, Node<E> parent) {
		return new RBNode<>(element, parent);
	}

	// 染色
	private Node<E> color(Node<E> node, boolean color) {
		if (node == null)
			return node;
		((RBNode<E>) node).color = color;
		return node;
	}

	// 染成红色
	private Node<E> red(Node<E> node) {
		return color(node, RED);
	}

	// 染成黑色
	private Node<E> black(Node<E> node) {
		return color(node, BLACK);
	}

	// 判断节点颜色
	private boolean colorOf(Node<E> node) {
		// 空值节点 ：叶子节点默认是黑色(红黑树性质3)
		return node == null ? BLACK : ((RBNode<E>) node).color;
	}

	// 判断节点是否是黑色
	private boolean isBlack(Node<E> node) {
		return colorOf(node) == BLACK;
	}

	// 判断节点是否是红色
	private boolean isRed(Node<E> node) {
		return colorOf(node) == RED;
	}

	private static class RBNode<E> extends Node<E> {
		boolean color = RED;// 默认是红色(建议新添加的节点默认为 RED，这样能够让红黑树的性质尽快满足（性质 1、2、3、5
							// 都满足，性质 4 不一定）)

		public RBNode(E element, Node<E> parent) {
			super(element, parent);
		}

		@Override
		public String toString() {
			String str = "";
			if (color == RED) {
				str = "R_";
			}
			return str + element.toString();
		}
	}
}
