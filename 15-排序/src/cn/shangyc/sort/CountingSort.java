package cn.shangyc.sort;

import cn.shangyc.Sort;
import cn.shangyc.Student;
/**
 * ◼ 之前学习的冒泡、选择、插入、归并、快速、希尔、堆排序，都是基于比较的排序,平均时间复杂度目前最低是 O(nlogn) 
 * ◼ 计数排序、桶排序、基数排序，都不是基于比较的排序,它们是典型的用空间换时间，在某些时候，平均时间复杂度可以比 O nlogn 更低
 */
//计数排序
public class CountingSort extends Sort<Integer> {
	
	/**
	 * 计数排序的核心:统计每个整数在序列中出现的次数，进而推导出每个整数在有序序列中的索引
	 * ◼ 这个版本的实现存在以下问题
	 * 	   无法对负整数进行排序
	 * 极其浪费内存空间
	 *   是个不稳定的排序
	 */
	protected void sort0() {
		// 找出最大值
		int max = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}// O(n)
		// 开辟内存空间，存储每个整数出现的次数
		int[] counts = new int[max + 1];
		// 统计每个整数出现的次数
		for (int i = 0; i < array.length; i++) {
			counts[array[i]]++;
		}// O(n)
		// 根据整数的出现次数，对整数进行排序
		int index = 0;
		for (int i = 0; i < counts.length; i++) {
			while (counts[i]-- >0) {
				array[index++] = i;
			}
		}
	}
	
	/**
	 * 计数排序 – 改进思路
	 */
	protected void sort() {
		// 找出最大值和最小值
		int max = array[0];
		int min = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
			if (array[i] < min) {
				min = array[i];
			}
		}// O(n)
		// 开辟内存空间，存储每个整数出现的次数
		int[] counts = new int[max - min + 1];
		// 统计每个整数出现的次数
		for (int i = 0; i < array.length; i++) {
			counts[array[i]-min]++;
		}// O(n)
		// 每个次数累加上其前面的所有次数
		for (int i = 1; i < counts.length; i++) {
			counts[i] += counts[i-1];
		}
		// 从后往前遍历元素，将它放到有序数组中的合适位置
		int[] output = new int[array.length];
		for (int i = array.length - 1; i >= 0; i--) {
			output[counts[array[i]-min]-1] = array[i];
			counts[array[i]-min]--;
		}
		// 将有序数组赋值到array
		for (int i = 0; i < array.length; i++) {
			array[i] = output[i];
		}
	}
	public static void main(String[] args) {
		Student[] students = new Student[] {
				new Student(20, "A"),
				new Student(-13, "B"),
				new Student(17, "C"),
				new Student(12, "D"),
				new Student(-13, "E"),
				new Student(20, "F")
		};
		int max = students[0].score;
		int min = students[0].score;
		for (int i = 1; i < students.length; i++) {
			if (students[i].score > max) {
				max = students[i].score;
			}
			if (students[i].score < min) {
				min = students[i].score;
			}
		}// O(n)
		// 开辟内存空间，存储每个整数出现的次数
		int[] counts = new int[max - min + 1];
		// 统计每个整数出现的次数
		for (int i = 0; i < students.length; i++) {
			counts[students[i].score-min]++;
		}// O(n)
		// 每个次数累加上其前面的所有次数
		for (int i = 1; i < counts.length; i++) {
			counts[i] += counts[i-1];
		}
		// 从后往前遍历元素，将它放到有序数组中的合适位置
		Student[] output = new Student[students.length];
		for (int i = students.length - 1; i >= 0; i--) {
			output[counts[students[i].score-min]-1] = students[i];
			counts[students[i].score-min]--;
		}
		// 将有序数组赋值到array
		for (int i = 0; i < students.length; i++) {
			students[i] = output[i];
		}
		
		for (int i = 0; i < students.length; i++) {
			System.out.println(students[i]);
		}
	}
}
