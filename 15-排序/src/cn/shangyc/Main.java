package cn.shangyc;

import java.util.Arrays;

import cn.shangyc.sort.BubbleSort1;
import cn.shangyc.sort.BubbleSort2;
import cn.shangyc.sort.BubbleSort3;
import cn.shangyc.sort.BucketSort;
import cn.shangyc.sort.CountingSort;
import cn.shangyc.sort.HeapSort;
import cn.shangyc.sort.InsertionSort;
import cn.shangyc.sort.InsertionSort2;
import cn.shangyc.sort.InsertionSort3;
import cn.shangyc.sort.MergeSort;
import cn.shangyc.sort.QuickSort;
import cn.shangyc.sort.RadixSort;
import cn.shangyc.sort.SelectionSort;
import cn.shangyc.sort.ShellSort;
import cn.shangyc.tools.Asserts;
import cn.shangyc.tools.Integers;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Main {

	public static void main(String[] args) {
		Integer[] array = Integers.tailAscOrder(1, 20, 15);
		
		testSorts(array, 
				new InsertionSort(),
				new InsertionSort2(),
				new InsertionSort3(),
				new SelectionSort(),
				new HeapSort(), 
				new MergeSort(),
				new BubbleSort1(),
				new BubbleSort2(),
				new BubbleSort3(),
				new QuickSort(),
				new ShellSort(),
				new CountingSort(),
				new RadixSort(),
				new BucketSort()
				);
	}
	
	static void testSorts(Integer[] array, Sort... sorts) {
		for (Sort sort : sorts) {
			Integer[] newArray = Integers.copy(array);
			sort.sort(newArray);
			Asserts.test(Integers.isAscOrder(newArray));
		}
		Arrays.sort(sorts);
		
		for (Sort sort : sorts) {
			System.out.println(sort);
		}
	}
}
