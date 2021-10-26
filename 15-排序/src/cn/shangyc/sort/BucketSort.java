package cn.shangyc.sort;

import java.util.LinkedList;
import java.util.List;

import cn.shangyc.Sort;

//桶排序
public class BucketSort extends Sort<Integer> {
	
	/**
	 * ◼ 执行流程(类型HashMap的结构)
		① 创建一定数量的桶（比如用数组、链表作为桶）
		② 按照一定的规则（不同类型的数据，规则不同），将序列中的元素均匀分配到对应的桶
		③ 分别对每个桶进行单独排序
		④ 将所有非空桶的元素合并成有序序列
		 ◼ 元素在桶中的索引
		元素值 * 元素数量
	 */
	protected void sort() {
		//桶数组
		@SuppressWarnings("unchecked")
		List<Double>[] buckets = new List[array.length];
		for (int i = 0; i < array.length; i++) {
			int bucketIndex = (array[i] * array.length);//将序列中的元素均匀分配到对应的桶,不同数据类型的这里都要取整获取索引
			List<Double> bucket = buckets[bucketIndex];
			if (bucket == null) {
				bucket = new LinkedList<>();
				buckets[bucketIndex] = bucket;
			}
			bucket.add(array[i].doubleValue());
		}
		int index = 0;
		for (int i = 0; i < buckets.length; i++) {
			if (buckets[i] == null) continue;
			buckets[i].sort(null);//分别对每个桶进行单独排序
			for (Double d : buckets[i]) {//将所有非空桶的元素合并成有序序列
				array[index++] = d.intValue();
			}
		}
	}

}
