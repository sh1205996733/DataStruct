package cn.shangyc;

//斐波那契数列
public class Fib {
	public static void main(String[] args) {
		Fib fib = new Fib();
		int n = 10;
		Times.test("fib0", () -> {
			System.out.println(fib.fib0(n));
		});
		Times.test("fib1", () -> {
			System.out.println(fib.fib1(n));
		});
		Times.test("fib2", () -> {
			System.out.println(fib.fib2(n));
		});
		Times.test("fib3", () -> {
			System.out.println(fib.fib3(n));
		});
		Times.test("fib4", () -> {
			System.out.println(fib.fib4(n));
		});
		Times.test("fib5", () -> {
			System.out.println(fib.fib5(n));
		});
		Times.test("fib6", () -> {
			System.out.println(fib.fib6(n));
		});
	}
	
	private int fib6(int n) {//特征方程  时间复杂度、空间复杂度取决于 pow 函数（至少可以低至O(logn) ）
		double c = Math.sqrt(5);
		return (int)((Math.pow((1+c)/2, n)-Math.pow((1-c)/2, n))/c);
	}
	private int fib5(int n) {//去除数组  时间复杂度O(n) 空间复杂度 O(1)
		if (n <= 2) return 1;
		int first = 1;
		int second = 1;
		for (int i = 3; i <= n; i++) {
			second += first;
			first = second - first;
		}
		return second;
	}
	
	/*
	 * 4 % 2 = 0  0b100 & 0b001 = 0
	 * 3 % 2 = 1  0b011 & 0b001 = 1
	 * 5 % 2 = 1  0b101 & 0b001 = 1
	 * 6 % 2 = 0  0b110 & 0b001 = 0
	 */
	private int fib4(int n) {//位运算取代模运算 时间复杂度O(n) 空间复杂度 O(1)
		if (n <= 2) return 1;
		int[] array = new int[2];
		array[0] = array[1] = 1;
		for (int i = 3; i <= n; i++) {
			array[i & 1] = array[(i - 1) & 1] + array[(i - 2) & 1];
		}
		return array[n & 1];
	}
	
	private int fib3(int n) {//由于每次运算只需要用到数组中的 2 个元素，所以可以使用滚动数组来优化 时间复杂度O(n) 空间复杂度 O(1)
		if (n <= 2) return 1;
		int[] array = new int[2];
		array[0] = array[1] = 1;
		for (int i = 3; i <= n; i++) {
			array[i % 2] = array[(i - 1) % 2] + array[(i - 2) % 2];
		}
		return array[n % 2];
	}
	
	private int fib2(int n) {//去除递归调用 时间复杂度O(n) 空间复杂度 O(n)
		if (n <= 2) return 1;
		int[] array = new int[n+1];
		array[1] = array[2] = 1;
		for (int i = 3; i <= n; i++) {//i<= n和i<array.length都可以
			array[i] = array[i-1] + array[i-2];
		}
		return array[n];
	}

	private int fib1(int n) {//用数组存放计算过的结果，避免重复计算 时间复杂度O(n) 空间复杂度 O(n)
		if (n <= 2) return 1;
		int[] array = new int[n+1];
		array[1] = array[2] = 1;
		return fib1(n,array);
	}
	int fib1(int n, int[] array) {
		if (array[n] == 0) {
			array[n] = fib1(n-1,array) + fib1(n-2,array);
		}
		return array[n];
	}
	private int fib0(int n) {//时间复杂度O(2^n) 空间复杂度 O(n)
		if (n <= 2) return 1;
		return fib0(n-1) + fib0(n-2);
	}
}
