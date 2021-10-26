package cn.shangyc.sort;

import cn.shangyc.Sort;

//快速排序
public class QuickSort<T extends Comparable<T>> extends Sort<T> {

	@Override
	protected void sort() {
		sort(0, array.length);
	}

	/**
	 * 对 [begin, end) 范围的元素进行快速排序
	 * 
	 * @param begin
	 * @param end
	 */
	private void sort(int begin, int end) {
		if (end - begin < 2)
			return;// 只有一个元素直接返回

		// 确定轴点位置 O(n)
		int pivot = pivotIndex(begin, end);
		sort(begin, pivot);
		sort(pivot + 1, end);
	}

	/**
	 * 构造出 [begin, end) 范围的轴点元素
	 *  ① 从序列中选择一个轴点元素（pivot） 
	 *  	✓ 假设每次选择 0 位置的元素为轴点元素 
	 *  ② 利用pivot 将序列分割成 2 个子序列 
	 *  	✓ 将小于 pivot 的元素放在pivot前面（左侧） 
	 *  	✓ 将大于 pivot的元素放在pivot后面（右侧）
	 *  	 ✓ 等于pivot的元素放哪边都可以 
	 *  ③ 对子序列进行 ① ② 操作 
	 *  	✓直到不能再分割（子序列中只剩下1个元素）
	 * 快速排序的本质逐渐将每一个元素都转换成轴点元素
	 * 如果序列中的所有元素都与轴点元素相等，利用目前的算法实现，轴点元素可以将序列分割成 2 个均匀的子序列
	 * 如果cmp 位置的判断分别改为 ≤、≥ 轴点元素分割出来的子序列极度不均匀，导致出现最坏时间复杂度 O(n2)
	 * @return 轴点元素的最终位置
	 */
	private int pivotIndex(int begin, int end) {
		// 随机选择一个元素跟begin位置进行交换
		swap(begin, begin + (int) (Math.random() * (end - begin)));
		// 备份begin位置的元素
		T pivot = array[begin];
		// end指向最后一个元素
		end--;
		// 将大于 pivot 的元素放在pivot后面（右侧）
		// 将小于 pivot 的元素放在pivot前面（左侧）
		// 等于pivot的元素放哪边都可以
		while (begin < end) {
			while (begin < end) {// 先从end处遍历
				if (cmp(pivot, array[end]) < 0) {// 右边元素 > 轴点元素,end--,继续从end处遍历
					end--;
				} else {// 右边元素 <= 轴点元素,将array[end]覆盖到array[begin],begin++,退出循环，开始从begin处遍历
					array[begin++] = array[end];
					break;
				}
			}
			while (begin < end) {// 从begin处遍历
				if (cmp(array[begin], pivot) < 0) {// 轴点元素 > 左边元素,begin++,继续从begin处遍历
					begin++;
				} else {// 轴点元素 <= 左边元素,将array[begin]覆盖到array[end],end--,退出循环，开始从end处遍历
					array[end--] = array[begin];
					break;
				}
			}
		}
		// begin == end
		array[begin] = pivot;
		return begin;
	}

}
