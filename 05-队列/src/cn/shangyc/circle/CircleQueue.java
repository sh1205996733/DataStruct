package cn.shangyc.circle;

//循环队列
@SuppressWarnings("unchecked")
public class CircleQueue<E> {
	private E[] elements;
	private int size;
	private int front;
	private int DEFAULT_CAPACITY = 10;

	public CircleQueue() {
		elements = (E[]) new Object[DEFAULT_CAPACITY];
	}

	public int size() { // 元素的数量
		return size;
	}

	public boolean isEmpty() { // 是否为空
		return size == 0;
	}

	public void clear() { // 清空
		// for (int i = 0; i < elements.length; i++) {
		// elements[i] = null;
		// }
		for (int i = 0; i < size; i++) {
			elements[index(i)] = null;
		}
		size = 0;
		front = 0;
	}

	// 计算真实索引
	private int index(int index) { // 元素的数量
		index += front;
		return index - (elements.length > index ? 0 : elements.length);// 仅适用于index<2*elements.length
	}

	public void enQueue(E element) { // 尾部入队
		ensureCapacity(size + 1);
		elements[index(size)] = element;
		size++;
	}

	public E deQueue() { // 头部出队
		E old = elements[front];
		elements[front] = null;
		front = index(1);
		size--;
		return old;
	}

	public E front() { // 获取队列的头元素
		return elements[front];
	}

	/**
	 * 保证要有capacity的容量
	 * 
	 * @param capacity
	 */
	private void ensureCapacity(int capacity) {
		int oldCapacity = elements.length;
		if (capacity <= oldCapacity)
			return;
		// 新容量为旧容量的1.5倍
		int newCapacity = (oldCapacity >> 1) + oldCapacity;
		E[] newElements = (E[]) new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[index(i)];
		}
		elements = newElements;
		// 重置front
		front = 0;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("capcacity=").append(elements.length).append(" size=").append(size).append(" front=")
				.append(front).append(", [");
		for (int i = 0; i < elements.length; i++) {
			if (i != 0) {
				string.append(", ");
			}

			string.append(elements[i]);
		}
		string.append("]");
		return string.toString();
	}
}
