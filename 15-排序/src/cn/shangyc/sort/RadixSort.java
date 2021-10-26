package cn.shangyc.sort;

import cn.shangyc.Sort;

//基数排序
public class RadixSort extends Sort<Integer> {
	/**
	 * 基数排序非常适合用于整数排序（尤其是非负整数），因此本课程只演示对非负整数进行基数排序
	 * 执行流程：依次对个位数、十位数、百位数、千位数、万位数...进行排序（从低位到高位）
	 * 	个位数、十位数、百位数的取值范围都是固定的0~9，可以使用计数排序对它们进行排序
	 */
	protected void sort1() {
		// 找出最大值
		int max = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}// O(n)
		
		// 个位数: array[i] / 1 % 10 = 3
		// 十位数：array[i] / 10 % 10 = 9
		// 百位数：array[i] / 100 % 10 = 5
		// 千位数：array[i] / 1000 % 10 = ...
		
		for (int divider = 1; divider <= max; divider*=10) {//123
			//1 10 100 1000
			countSort(divider);//对每一位进行计数排序
		}
	}

	private void countSort(int divider) {
		int[] counts = new int[10];
		// 从后往前遍历元素，将它放到有序数组中的合适位置
		int[] output = new int[array.length];
		// 统计每个整数出现的次数
		for (int i = 0; i < array.length; i++) {
			counts[array[i] / divider % 10]++;
		}// O(n)
		// 每个次数累加上其前面的所有次数
		for (int i = 1; i < counts.length; i++) {
			counts[i] += counts[i-1];
		}
		
		for (int i = array.length - 1; i >= 0; i--) {
			output[counts[array[i] / divider % 10]-1] = array[i];
			counts[array[i] / divider % 10]--;
		}
		// 将有序数组赋值到array
		for (int i = 0; i < array.length; i++) {
			array[i] = output[i];
		}
	}
	
	//基数排序 – 另一种思路的实现
	protected void sort() {
		// 找出最大值
		int max = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}// O(n)
		//桶数组
		int[][] buckets = new int[10][array.length];
		//每个桶的元素数量
		int[] bucketSize = new int[buckets.length];
		for (int divider = 1; divider <= max; divider*=10) {//123
			for (int i = 0; i < array.length; i++) {
				int no = array[i] / divider % 10;
				buckets[no][bucketSize[no]++] = array[i];
			}
			int index = 0;
			for (int i = 0; i < buckets.length; i++) {
				for (int j = 0; j < bucketSize[i]; j++) {
					array[index++] = buckets[i][j];
				}
				bucketSize[i] = 0;
			}
		}
	}

}
