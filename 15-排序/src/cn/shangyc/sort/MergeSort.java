package cn.shangyc.sort;

import cn.shangyc.Sort;

//归并排序
@SuppressWarnings("unchecked")
public class MergeSort<T extends Comparable<T>> extends Sort<T> {
	private T[] leftArray;
	/**
	 * 执行流程 
	 * ① 不断地将当前序列平均分割成2个子序列 ✓ 直到不能再分割（序列中只剩1个元素） 
	 * ② 不断地将2个子序列合并成一个有序序列 ✓ 直到最终只剩下1个有序序列
	 */
	protected void sort() {
		leftArray = (T[]) new Comparable[array.length >> 1];
		sort(0,array.length);
	}
	
	/**
	 * 对 [begin, end) 范围的数据进行归并排序
	 */
	private void sort(int begin, int end) {
		//不断地将当前序列平均分割成2个子序列 ✓ 直到不能再分割（序列中只剩1个元素） 
		if (end - begin < 2) return;//只剩1个元素
		int mid = (end + begin) >> 1;
		sort(begin,mid);
		sort(mid,end);
		//不断地将2个子序列合并成一个有序序列 ✓ 直到最终只剩下1个有序序列
		merge(begin, mid, end);
	}
	
	/**
	 * 将 [begin, mid) 和 [mid, end) 范围的序列合并成一个有序序列
	 */
	private void merge(int begin, int mid, int end) {
		int li = 0,le = mid - begin;
		int ri = mid,re = end;
		int ai = begin;
		// 备份左边数组
		for (int i = li; i < le; i++) {
			leftArray[i] = array[begin+i];
		}
		while (li < le) {// 如果左边还没有结束,左边结束直接返回
			//cmp(array[ri], leftArray[li]) <= 0 改为<=就失去稳定性
			if (ri < re && cmp(array[ri], leftArray[li]) < 0) {// 如果右边没有结束并且array[ri]<leftArray[li],右边先结束执行else
				array[ai++] = array[ri++];
			}else {
				array[ai++] = leftArray[li++];
			}
		}
	}

}
