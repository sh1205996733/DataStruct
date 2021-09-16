package cn.shangyc.single;

import cn.shangyc.AbstractList;

/**
 * 带有虚拟头结点的链表
 * 
 * @author Administrator
 *
 * @param <E>
 */
public class SingleLinkedList2<E> extends AbstractList<E> {
	private Node<E> first;

	private static class Node<E> {
		private E element;
		private Node<E> next;

		public Node(E element, Node<E> next) {
			this.element = element;
			this.next = next;
		}
	}

	public SingleLinkedList2() {
		first = new Node<E>(null, null);
	}

	@Override
	public void clear() {
		first = null;
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
		Node<E> pre = index == 0 ? first : node(index - 1);// 获取前一个节点
		pre.next = new Node<>(element, pre.next);
		size++;
	}

	@Override
	public E remove(int index) {// 1,2,3,4,5
		rangeCheck(index);

		Node<E> pre = index == 0 ? first : node(index - 1);// 获取前一个节点
		Node<E> node = pre.next;
		pre.next = node.next;
		size--;
		return node.element;
	}

	@Override
	public int indexOf(E element) {
		if (element == null) {
			Node<E> node = first.next;
			for (int i = 0; i < size; i++) {
				if (node.element == null)
					return i;
				node = node.next;
			}
		} else {
			Node<E> node = first.next;
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

		Node<E> node = first.next;
		for (int i = 0; i < index; i++) {
			node = node.next;
		}
		return node;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("size=").append(size).append(", [");
		Node<E> node = first.next;
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
