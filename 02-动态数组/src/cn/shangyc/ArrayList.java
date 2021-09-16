package cn.shangyc;

@SuppressWarnings("unchecked")
public class ArrayList<E> {
	/**
	 * 元素的数量
	 */
	private int size;
	/**
	 * 所有的元素
	 */
	private E[] elements;
	private static final int DEFAULT_CAPACITY = 10;
	private static final int ELEMENT_NOT_FOUND = -1;

	public ArrayList(int capition) {
		capition = (capition < DEFAULT_CAPACITY ? DEFAULT_CAPACITY : capition);
		elements = (E[]) new Object[capition];
	}

	public ArrayList() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * 元素的数量
	 * 
	 * @return
	 */
	public int size() {
		return size;
	}

	/**
	 * 元素的数量
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * 是否包含某个元素
	 * 
	 * @return
	 */
	public boolean contains(E element) {
		return indexOf(element) != ELEMENT_NOT_FOUND;
	}

	/**
	 * 添加元素到最后面
	 * 
	 * @return
	 */
	public void add(E element) {
		add(size, element);
	}

	/**
	 * 返回index位置对应的元素
	 * 
	 * @return
	 */
	public E get(int index) {
		rangeCheck(index);
		return elements[index];
	}

	private void rangeCheck(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
		}
	}

	private void rangeCheckForAdd(int index) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("添加角标越界");
		}
	}

	/**
	 * 设置index位置的元素
	 * 
	 * @param index
	 * @param element
	 * @return 原来的元素ֵ
	 */
	public E set(int index, E element) {
		rangeCheck(index);
		E old = elements[index];
		elements[index] = element;
		return old;
	}

	/**
	 * 往index位置添加元素
	 * 
	 * @param index
	 * @param element
	 * @return 原来的元素ֵ
	 */
	public void add(int index, E element) {
		rangeCheckForAdd(index);
		ensureCapacity(size + 1);// 扩容
		//1,2,3,4,5
		for (int i = size; i > index; i--) {//从size开始，从右到左，前一个往后挪
			elements[i] = elements[i - 1];
		}
		elements[index] = element;
		size++;
	}

	/**
	 * 保证要有capacity的容量
	 * 
	 * @param capacity
	 */
	private void ensureCapacity(int capacity) {
		int oldCapacity = elements.length;//原数组长度
		if (capacity <= oldCapacity)return;
		// 新容量为旧容量的1.5倍
		int newCapacity = (oldCapacity >> 2 + oldCapacity);
		E[] newElements = (E[]) new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[i];
		}
		elements = newElements;
		System.out.println(oldCapacity + "扩容为" + newCapacity);
	}

	public E remove(int index) { // 删除index位置对应的元素
		rangeCheck(index);
		E old = elements[index];// 1,2,3,4,5
		for (int i = index; i < size - 1; i++) {//从index开始，从左到右，后一个往前挪，最后一个不用挪，因为size--时候访问不到
			elements[i] = elements[i + 1];
		}
		elements[--size] = null;
		return old;
	}

	/**
	 * 查看元素的索引(返回第一次出现的索引位置)
	 * lastIndexOf ：返回最后一次出现的索引位置，只需倒序循环即可
	 */
	public int indexOf(E element) {
		if (element == null) { // 1
			for (int i = 0; i < size; i++) {
				if (elements[i] == null)
					return i;
			}
		} else {
			for (int i = 0; i < size; i++) {
				if (element.equals(elements[i]))
					return i; // n
			}
		}
		return ELEMENT_NOT_FOUND;
	}

	/**
	 * 清除所有元素
	 */
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	public String toString() {
		// size=3, [99, 88, 77]
		StringBuilder string = new StringBuilder();
		string.append("size=").append(size).append(", [");
		for (int i = 0; i < size; i++) {
			if (i != 0) {
				string.append(", ");
			}

			string.append(elements[i]);

			// if (i != size - 1) {
			// string.append(", ");
			// }
		}
		string.append("]");
		return string.toString();
	}
}
