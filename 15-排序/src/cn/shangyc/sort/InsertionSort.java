package cn.shangyc.sort;

import cn.shangyc.Sort;

//插入排序
public class InsertionSort <T extends Comparable<T>> extends Sort<T> {
	
	/**
	 *  执行流程:
	 *  ① 在执行过程中，插入排序会将序列分为2部分
	 *		✓ 头部是已经排好序的，尾部是待排序的
	 *	② 从头开始扫描每一个元素
	 *		✓ 每当扫描到一个元素，就将它插入到头部合适的位置，使得头部数据依然保持有序
	 */
	protected void sort() {
		for (int begin = 1; begin < array.length; begin++) {//从第一个元素开始，假如第一个是有序的
			int cur = begin;
			while (cur > 0 && cmp(cur, cur-1) < 0) {
				swap(cur, cur-1);
				cur--;
			}
		}
	}

}
