package cn.shangyc;

//尾调用 一个函数的最后一个动作是调用函数,如果最后一个动作是调用自身，称为尾递归（Tail Recursion），是尾调用的特殊情况
public class TailCall {
	public static void main(String[] args) {
		System.out.println(facttorial(4));
		System.out.println(fib(10));
	}
	//尾递归示例1 – 阶乘
	static int facttorial(int n) {
		return facttorial(n, 1);
	}
	static int facttorial(int n, int result) {
		if (n <= 1) return result;
		return facttorial(n - 1, result * n);
	}
	//求n阶乘
//	static int facttorial(int n) {
//		if (n <= 1) return n;
//		return n * facttorial(n - 1);//不是尾调用,因为它最后1个动作是乘法
//	}
	//尾递归示例2 – 斐波那契数列
//	private static int fib(int n) {//时间复杂度O(2^n) 空间复杂度 O(n)
//		if (n <= 2) return 1;
//		return fib(n-1) + fib(n-2);
//	}
	private static int fib(int n) {//时间复杂度O(2^n) 空间复杂度 O(n)
		return fib(n,1,1);
	}
	private static int fib(int n, int first, int second) {
		if (n <= 2) return second;
		return fib(n-1,second,first+second);
	}
}
