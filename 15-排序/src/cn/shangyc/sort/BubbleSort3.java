package cn.shangyc.sort;

import cn.shangyc.Sort;

//冒泡排序
public class BubbleSort3<T extends Comparable<T>> extends Sort<T> {
	//优化三， 如果序列尾部已经局部有序，可以记录最后1次交换的位置，减少比较次数 增加sortedIndex
	@Override
	protected void sort() {
		for (int end = array.length - 1; end > 0; end--) {//end从最后一个元素length - 1开始遍历到1
			int sortedIndex = 1;
			for (int begin = 1; begin <= end; begin++) {//begin从第二个元素arr[1]开始遍历到end
				if (cmp(begin, begin - 1) < 0) {//比较arr[i]和arr[i-1]的值，若arr[i-1]大，就交换
					swap(begin, begin - 1);
					sortedIndex = begin;
				}
			}
			//一轮结束之后让end等于sortedIndex
			end = sortedIndex;
		}
	}
}
