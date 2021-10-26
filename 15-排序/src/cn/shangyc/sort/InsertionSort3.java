package cn.shangyc.sort;

import cn.shangyc.Sort;

//插入排序
public class InsertionSort3 <T extends Comparable<T>> extends Sort<T> {
	
	/**
	 *  执行流程:
	 *  ① 在执行过程中，插入排序会将序列分为2部分
	 *		✓ 头部是已经排好序的，尾部是待排序的
	 *	② 从头开始扫描每一个元素
	 *		✓ 每当扫描到一个元素，就将它插入到头部合适的位置，使得头部数据依然保持有序
	 */
	protected void sort() {
		for (int begin = 1; begin < array.length; begin++) {//从第一个元素开始，假如第一个是有序的
			insert(begin, search(begin));
		}
	}
	
	/**
	 * 将source位置的元素插入到dest位置
	 * @param source
	 * @param dest
	 */
	private void insert(int source, int dest) {
		T v = array[source];
		for (int i = source; i > dest; i--) {
			array[i] = array[i-1];
		}
		array[dest] = v;
	}
	
	/**
	 * 利用二分搜索找到 index 位置元素的待插入位置
	 * 已经排好序数组的区间范围是 [0, index)
	 * @param index
	 * @return
	 */
	private int search(int index) {
		int begin = 0,end = index;
		while (begin < end) {
			int mid = (begin + end) >> 1;
			if (cmp(index, mid) > 0) {//结果在mid右边
				begin = mid +1;
//			} else if (cmp(index, mid) < 0){//结果在mid左边
//				end = mid;
			}else {
				end = mid;
//				return mid;
			}
		}
		return begin;
	}

}
