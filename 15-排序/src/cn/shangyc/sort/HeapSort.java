package cn.shangyc.sort;

import cn.shangyc.Sort;

//堆排序
public class HeapSort <T extends Comparable<T>> extends Sort<T> {
	private int heapSize;
	@Override
	protected void sort() {
		// 原地建堆
		heapSize = array.length;
		// 自下而上的下滤 时间复杂度n (从第一个非叶子节点开始)
		for (int i = (heapSize >> 1)-1; i >= 0; i--) {
			siftDown(i);
		}
		
		while (heapSize > 1) {
			// 交换堆顶元素和尾部元素
			swap(0, --heapSize);

			// 对0位置进行siftDown（恢复堆的性质）
			siftDown(0);
		}
	}
	private void siftDown(int index) {
		T element = array[index];
		int half = heapSize >> 1;
		while (index < half) {
			// 默认为左子节点跟它进行比较
			int left = (index << 1) + 1;//计算左子节点的索引 2*i +1
			T child = array[left];
			// 右子节点
			int right = left + 1;//计算右子节点的索引 2*i +2 或者 leftIndex + 1
			// 右子节点比左子节点大
			if (right < heapSize && 
					cmp(array[right], child) > 0) { 
				child = array[left = right];
			}
			
			if (cmp(element, child) >= 0) break;// 大于等于最大子节点直接break
			array[index] = child;
			// 重新赋值index
			index = left;
		}
		array[index] = element;
	}

}
