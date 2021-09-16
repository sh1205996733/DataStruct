package cn.shangyc.heap;

import java.util.Comparator;

import cn.shangyc.printer.BinaryTreeInfo;

/**
 * 二叉堆（最大堆）
 * @param <E>
 */
@SuppressWarnings("unchecked")
public class BinaryHeap<E> extends AbstractHeap<E> implements BinaryTreeInfo {
	private E[] elements;
	private static final int DEFAULT_CAPACITY = 10;
	
	//批量建堆
	public BinaryHeap(E[] elements, Comparator<E> comparator)  {
		super(comparator);
		if (elements == null || elements.length == 0) {
			this.elements = (E[]) new Object[DEFAULT_CAPACITY];
		}else {//
			//不能直接 this.elements = elements,防止其他变量修改，只能复制过去
			
			size = elements.length;
			int capacity = Math.max(DEFAULT_CAPACITY, size);
			this.elements = (E[]) new Object[capacity];
			for (int i = 0; i < elements.length; i++) {
				this.elements[i]  = elements[i];
			}
			heapify();
		}
	}
	public BinaryHeap(E[] elements) {
		this(elements,null);
	}
	
	public BinaryHeap(Comparator<E> comparator) {
		this(null, comparator);
	}
	
	public BinaryHeap() {
		this(null,null);
	}
	
	/**
	 * 批量建堆
	 */
	private void heapify() {
		// 自上而下的上滤  时间复杂度nlog(n)
//		for (int i = 1; i < size; i++) {
//			siftUp(i);
//		}
		// 自下而上的下滤 时间复杂度n (从第一个非叶子节点开始)
		for (int i = (size >> 1)-1; i >= 0; i--) {
			siftDown(i);
		}
	}
	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	@Override
	public void add(E element) {
		elementNotNullCheck(element);
		ensureCapacity(size + 1);
		elements[size++] = element;
		siftUp(size - 1);
	}
	
	//让index位置的元素上滤(节点和父节点比较，大于父节点则交换位置，节点上去，父节点下来，一直往上比较，直到父节点为空(index==0)或者小于等于父节点)
	private void siftUp(int index) {
		E element = elements[index];
		while (index > 0) {
			int pid = (index-1) >> 1;//父节点的索引
			E parent = elements[pid];
			if (compare(element, parent) <= 0) break;// 小于等于父节点直接break
			
			//大于父节点则交换位置
			// 将父元素存储在index位置
			elements[index] = parent;
			// 重新赋值index
			index = pid;
		}
		elements[index] = element;
	}
	
	//让index位置的元素下滤(节点和最大的子节点比较，小于最大子节点则交换位置，最大子节点上去，节点下来，一直往下比较，直到叶子节点为空或者大于最大子父节点)
	private void siftDown(int index) {
		E element = elements[index];
		int half = size >> 1;
		// 第一个叶子节点的索引 == 非叶子节点的数量
		// index < 第一个叶子节点的索引
		// 必须保证index位置是非叶子节点
		while (index < half) {
			// index的节点有2种情况
			// 1.只有左子节点
			// 2.同时有左右子节点
			
			// 默认为左子节点跟它进行比较
			int left = (index << 1) + 1;//计算左子节点的索引 2*i +1
			// 右子节点
			int right = left + 1;//计算右子节点的索引 2*i +2 或者 leftIndex + 1
			if (compare(elements[right], elements[left]) > 0) {//找出左右子树最大的
				left = right;
			}
			
			E child = elements[left];
			
			if (compare(element, child) >= 0) break;// 大于等于最大子节点直接break
			
			//小于最大子节点则交换位置
			// 将最大子节点存储在index位置
			elements[index] = child;
			// 重新赋值index
			index = left;
		}
		elements[index] = element;
	}

	@Override
	public E get() {
		emptyCheck();
		return elements[0];
	}

	@Override
	public E remove() {
		emptyCheck();
		
		int lastIndex = --size;
		E root = elements[0];
		elements[0] = elements[lastIndex];
		elements[lastIndex] = null;
		
		siftDown(0);
		return root;
	}

	@Override
	public E replace(E element) {
//		remove();
//		add(element);
		
		elementNotNullCheck(element);
		E root = null;
		if (size == 0){//数组为空，直接新增
			elements[0] = element;
			size++;
		}else {
			root = elements[0];
			elements[0] = element;
			siftDown(0);
			return root;
		}
		return root;
	}

	@Override
	public Object root() {
		return 0;
	}

	@Override
	public Object left(Object node) {
		int index = ((int)node << 1) + 1;
		return index >= size ? null : index;
	}

	@Override
	public Object right(Object node) {
		int index = ((int)node << 1) + 2;
		return index >= size ? null : index;
	}

	@Override
	public Object string(Object node) {
		return elements[(int)node];
	}
	
	private void ensureCapacity(int capacity) {
		int oldCapacity = elements.length;
		if (oldCapacity >= capacity) return;
		
		// 新容量为旧容量的1.5倍
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		E[] newElements = (E[]) new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[i];
		}
		elements = newElements;
	}
	
	private void emptyCheck() {
		if (size == 0) {
			throw new IndexOutOfBoundsException("Heap is empty");
		}
	}
	
	private void elementNotNullCheck(E element) {
		if (element == null) {
			throw new IllegalArgumentException("element must not be null");
		}
	}
}
