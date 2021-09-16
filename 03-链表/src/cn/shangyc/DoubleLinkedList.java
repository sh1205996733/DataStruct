package cn.shangyc;

import cn.shangyc.AbstractList;

//双链表
public class DoubleLinkedList<E> extends AbstractList<E> {
	private Node<E> first;
	private Node<E> last;

	private static class Node<E> {
		private E element;
		private Node<E> next;
		private Node<E> prev;

		public Node(Node<E> prev, E element, Node<E> next) {
			this.prev = prev;
			this.element = element;
			this.next = next;
		}

		@Override

		public String toString() {
			StringBuilder sb = new StringBuilder();

			if (prev != null) {
				sb.append(prev.element);
			} else {
				sb.append("null");
			}

			sb.append("_").append(element).append("_");

			if (next != null) {
				sb.append(next.element);
			} else {
				sb.append("null");
			}

			return sb.toString();
		}
	}

	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}

	@Override
	public E get(int index) {
		return node(index).element;
	}

	@Override
	public E set(int index, E element) {
		Node<E> node = node(index);
		E old = node.element;
		node.element = element;
		return old;
	}

	@Override
	public void add(int index, E element) {
		rangeCheckForAdd(index);

		if (index == size) {// 往最后面添加元素
			Node<E> oldLast = last;
			last = new Node<>(oldLast, element, null);
			if (oldLast == null) {// 这是链表添加的第一个元素
				first = last;
			} else {
				oldLast.next = last;
			}
		} else {
			Node<E> next = node(index);// 获取指定位置的节点
			Node<E> prev = next.prev;// 获取指定位置的上一个节点
			Node<E> node = new Node<>(prev, element, next);
			if (prev == null) {// index = 0
				first = node;
			} else {
				prev.next = node;
			}
			next.prev = node;
		}
		size++;
	}

	@Override
	public E remove(int index) {// 1,2,3,4,5
		rangeCheck(index);

		Node<E> node = node(index);// 获取指定位置的节点
		Node<E> next = node.next;// 获取指定位置的下一个节点
		Node<E> prev = node.prev;// 获取指定位置的上一个节点
		if (prev == null) {// index = 0
			first = next;
		} else {
			prev.next = next;
		}

		if (next == null) {// index = size-1
			last = prev;
		} else {
			next.prev = prev;
		}
		// node.prev = node.next = null
		size--;
		return node.element;
	}

	@Override
	public int indexOf(E element) {
		if (element == null) {
			Node<E> node = first;
			for (int i = 0; i < size; i++) {
				if (node.element == null)
					return i;
				node = node.next;
			}
		} else {
			Node<E> node = first;
			for (int i = 0; i < size; i++) {
				if (element.equals(node.element))
					return i;
				node = node.next;
			}
		}
		return ELEMENT_NOT_FOUND;
	}

	// 获取指定位置上的元素
	public Node<E> node(int index) {
		rangeCheck(index);
		if (index < (size >> 1)) {
			Node<E> node = first;
			for (int i = 0; i < index; i++) {
				node = node.next;
			}
			return node;
		} else {
			Node<E> node = last;
			for (int i = size - 1; i > index; i--) {
				node = node.prev;
			}
			return node;
		}
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("size=").append(size).append(", [");
		Node<E> node = first;
		for (int i = 0; i < size; i++) {
			if (i != 0) {
				string.append(", ");
			}

			string.append(node);

			node = node.next;
		}
		string.append("]");
		return string.toString();
	}
}
