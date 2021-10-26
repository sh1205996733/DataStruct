package cn.shangyc.sort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.shangyc.Sort;

//希尔排序 希尔排序也被称为递减增量排序
public class ShellSort <T extends Comparable<T>> extends Sort<T> {
	
	/**
	 * 希尔排序把序列看作是一个矩阵，分成 𝑚 列，逐列进行排序
	 * 	✓ 𝑚 从某个整数逐渐减为1
	 *	✓ 当 𝑚 为1时，整个序列将完全有序
	 * 矩阵的列数取决于步长序列（step sequence） 
	 * 	✓ 比如，如果步长序列为{1,5,19,41,109,...}，就代表依次分成109列、41列、19列、5列、1列进行排序
	 * 	✓ 不同的步长序列，执行效率也不同
	 */
	protected void sort() {
		List<Integer> stepSequence = sedgewickStepSequence();
		for (Integer step : stepSequence) {
			sort(step);
		}
	}
	//因此希尔排序底层一般使用插入排序对每一列进行排序，也很多资料认为希尔排序是插入排序的改进版
	private void sort(int step) {
		// col : 第几列，column的简称
		for (int col = 0; col < step; col++) {
			// col、col+step、col+2*step、col+3*step
			for (int begin = col+step; begin < array.length; begin+=step) {
				//每一列进行排序(插入排序)
				int cur = begin;
				T v = array[begin];
//				while (cur > col && cmp(cur, cur - step) < 0 ) {
//					swap(cur,cur - step);
//					cur -= step;
//				}
				while (cur > col &&  cmp(v, array[cur - 1]) < 0 ) {
					array[cur] = array[cur - step];
					cur -= step;
				}
				array[cur] = v;
			}
		}
	}

	@SuppressWarnings("unused")
	//获取步长序列 16 8 4 2 1
	private List<Integer> shellStepSequence() {
		List<Integer> stepSequence = new ArrayList<>();
		int step = array.length;
		while ((step >>= 1) > 0) {
			stepSequence.add(step);
		}
		return stepSequence;
	}
	//获取步长序列
	private List<Integer> sedgewickStepSequence() {
		List<Integer> stepSequence = new LinkedList<>();
		int k = 0, step = 0;
		while (true) {
			if (k % 2 == 0) {
				int pow = (int) Math.pow(2, k >> 1);
				step = 1 + 9 * (pow * pow - pow);
			} else {
				int pow1 = (int) Math.pow(2, (k - 1) >> 1);
				int pow2 = (int) Math.pow(2, (k + 1) >> 1);
				step = 1 + 8 * pow1 * pow2 - 6 * pow2;
			}
			if (step >= array.length) break;
			stepSequence.add(0, step);
			k++;
		}
		return stepSequence;
	}
}
