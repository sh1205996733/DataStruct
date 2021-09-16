package cn.shangyc.circle;

import cn.shangyc.AbstractList;

//单向循环链表:和单链表的区别在 添加/删除头结点时需要维护最后一个的next
public class SingleCircleLinkedList<E> extends AbstractList<E>{
	private Node<E> first;
	
	private static class Node<E> {
		private E element;
		private Node<E> next;
		
		public Node(E element, Node<E> next) {
			this.element = element;
			this.next = next;
		}
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(element).append("_").append(next.element);
			return sb.toString();
		}
	}
	
	@Override
	public void clear() {
		first = null;
		size = 0;
	}

	@Override
	public E get(int index) {
		/*
		 * 最好：O(1)
		 * 最坏：O(n)
		 * 平均：O(n)
		 */
		return node(index).element;
	}

	@Override
	public E set(int index, E element) {
		/*
		 * 最好：O(1)
		 * 最坏：O(n)
		 * 平均：O(n)
		 */
		Node<E> node = node(index);
		E old = node.element;
		node.element = element;
		return old;
	}

	@Override
	public void add(int index, E element) {
		/*
		 * 最好：O(1)
		 * 最坏：O(n)
		 * 平均：O(n)
		 */
		rangeCheckForAdd(index);
		
		if (index == 0) {
			Node<E> newFirst = new Node<>(element,first);
			// 拿到最后一个节点
			Node<E> last = size == 0 ? newFirst:node(size-1);
			last.next = newFirst;//维护最后一个的next为新的first
			
			first = newFirst;//设置新的first
		}else {
			Node<E> prev = node(index-1);//获取前一个节点
			prev.next = new Node<>(element,prev.next);
		}
		size++;
	}

	@Override
	public E remove(int index) {//1,2,3,4,5
		/*
		 * 最好：O(1)
		 * 最坏：O(n)
		 * 平均：O(n)
		 */
		rangeCheck(index);
		
		Node<E> node = first;
		if (index == 0) {
			// 拿到最后一个节点
			if (size == 1) {//只有一个元素时
				first = null;
			}else {
				Node<E> last = node(size-1);
				first = first.next;//设置新的first
				last.next = first;//维护最后一个的next为新的first
			}
		} else {
			Node<E> pre = node(index-1);//获取前一个节点
			node = pre.next;
			pre.next = node.next;
		}
		size--;
		return node.element;
	}

	@Override
	public int indexOf(E element) {
		if (element == null) {
			Node<E> node = first;
			for (int i = 0; i < size; i++) {
				if (node.element == null) return i;
				node = node.next;
			}
		} else {
			Node<E> node = first;
			for (int i = 0; i < size; i++) {
				if (element.equals(node.element)) return i;
				node = node.next;
			}
		}
		return ELEMENT_NOT_FOUND;
	}
	
	//获取指定位置上的元素
	public Node<E> node(int index) {
		rangeCheck(index);
		
		Node<E> node = first;
		for (int i = 0; i < index; i++) {
			node = node.next;
		}
		return node;
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
