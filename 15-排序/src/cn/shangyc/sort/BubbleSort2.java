package cn.shangyc.sort;

import cn.shangyc.Sort;

//冒泡排序
public class BubbleSort2<T extends Comparable<T>> extends Sort<T> {
	//优化二，如果序列已经完全有序，可以提前终止冒泡排序 增加sorted(缺点 对于降序的没有作用)
	@Override
	protected void sort() {
		for (int end = array.length - 1; end > 0; end--) {//end从最后一个元素length - 1开始遍历到1
			boolean sorted = true;
			for (int begin = 1; begin <= end; begin++) {//begin从第二个元素arr[1]开始遍历到end
				if (cmp(begin, begin - 1) < 0) {//比较arr[i]和arr[i-1]的值，若arr[i-1]大，就交换
					swap(begin, begin - 1);
					sorted = false;
				}
			}
			//一轮结束之后arr[begin]也即arr[end]即为最大值（begin == end）
			if (sorted) {
				break;
			}
		}
	}
}
