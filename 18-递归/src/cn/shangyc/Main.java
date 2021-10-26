package cn.shangyc;

import java.util.Stack;

//递归转非递归
public class Main {
	public static void main(String[] args) {
		log(6);
		log2(6);
	}
	static class Frame {
		int n;
		int v;
		Frame(int n, int v) {
			this.n = n;
			this.v = v;
		}
	}
	
	private static void log2(int n) {
		if (n < 1) return;
		Stack<Frame> frames = new Stack<>();
		while (n > 0) {
			while (n>0) {
				frames.push(new Frame(n, n + 10));
				n--;
			}
			while (!frames.isEmpty()) {
				Frame frame = frames.pop();
				System.out.println(frame.v);
			}
		}
	}
	private static void log(int n) {
		if (n < 1) return;
		log(n - 1);
		int v = n + 10;
		System.out.println(v);
	}
	
	/**
	 * 求 1 + 2 + 3 + ... + (n - 1) + n 的值
	 * @param n
	 * @return
	 */
	int sum(int n) {
		if (n <= 1) return n; 
		return n + sum(n - 1);
	}
}
