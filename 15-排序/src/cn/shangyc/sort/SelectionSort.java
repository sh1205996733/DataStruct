package cn.shangyc.sort;

import cn.shangyc.Sort;

//选择排序
public class SelectionSort <T extends Comparable<T>> extends Sort<T> {

	@Override
	protected void sort() {//1 2 3 4 5 6
//		for (int end = 0; end < array.length-1; end++) {
//			int min = end;
//			for (int begin = end+1; begin < array.length; begin++) {
//				if (cmp(begin, min) < 0) {
//					min  = begin;
//				}
//			}
//			swap(min, end);
//		}
		
//		for (int end = 0; end < array.length-1; end++) {
//			int max = 0;
//			for (int begin = 1; begin < array.length-end; begin++) {
//				if (cmp(begin, max) > 0) {
//					max  = begin;
//				}
//			}
//			swap(max, array.length-1-end);
//		}

//		for (int end = array.length-1; end > 0; end--) {
//			for (int begin = 0; begin < end; begin++) {
//				if (cmp(begin, end) > 0) {
//					swap(begin, end);
//				}
//			}
//		}
		
		for (int end = array.length-1; end > 0; end--) {
			int max = 0;
			for (int begin = 1; begin <= end; begin++) {
				if (cmp(begin, max) > 0) {
					max = begin;
				}
			}
			swap(max, end);
		}
	}

}
