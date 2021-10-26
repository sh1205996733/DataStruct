package cn.shangyc.sort;

import cn.shangyc.Sort;

//冒泡排序
@SuppressWarnings("unused")
public class BubbleSort1<T extends Comparable<T>> extends Sort<T> {
	private void bubbleSort0(T[] array) {
		for (int i = 0; i < array.length-1; i++) {//array.length-1重复计算
			for (int j = 0; j < array.length-1-i; j++) {//array.length-1重复计算
				if (cmp(array[j], array[j+1]) > 0) {//array[j] > array[j + 1]
					swap(j, j+1);
				}
			}
		}
	}
	//优化一，从后面往前面遍历
	private void bubbleSort(T[] array) {
		for (int end = array.length - 1; end > 0; end--) {//end从最后一个元素length - 1开始遍历到1
			for (int begin = 1; begin <= end; begin++) {//begin从第二个元素arr[1]开始遍历到end
				if (cmp(begin, begin - 1) < 0) {//比较arr[i]和arr[i-1]的值，若arr[i-1]大，就交换
					swap(begin, begin - 1);
				}
			}
			//一轮结束之后arr[begin]也即arr[end]即为最大值（begin == end）
		}
	}
	@Override
	protected void sort() {
		bubbleSort(array);
	}
}
