package cn.shangyc;

public class KMP {
	/**
	 * ◼ KMP 主逻辑
			最好时间复杂度：O(m) 最坏时间复杂度：O(n)，不超过O(2n) 
	        ◼ next 表的构造过程跟 KMP 主体逻辑类似
			时间复杂度：O(m) 
	        ◼ KMP 整体
			最好时间复杂度：O(m) 最坏时间复杂度：O(n + m) 空间复杂度： O(m)
	 * @param args
	 */
	public static void main(String[] args) {
		String text = "Hello World";
		String pattern = "or";
		System.out.println(indexOf(text,pattern));
	}
	//对比蛮力算法，KMP的精妙之处：充分利用了此前比较过的内容，可以很聪明地跳过一些不必要的比较位置
	private static int indexOf(String text, String pattern) {//在暴力算法indexOf之间增加一个next表
		if (text== null || pattern == null) return -1;
		int tlen = text.length();
		int plen = pattern.length();
		if (tlen == 0 || plen == 0 || tlen < plen) return -1;
		int[] next = next(pattern);
		int tmax = tlen - plen;
		int ti = 0,pi = 0;
		while (ti - pi <= tmax && pi<plen) {
			if (pi < 0 ||text.charAt(ti) == pattern.charAt(pi)) {
				ti++;
				pi++;
			} else {
				pi = next[pi];
			}
		}
		return pi == plen ? ti - pi:-1;
	}
	//KMP – next表的优化
	private static int[] next(String pattern) {
		int len = pattern.length();
		int[] next = new int[len];
		int i = 0;
		int n = next[i] = -1;
		int imax = len-1;
		while (i < imax) {
			if (n < 0 || pattern.charAt(i) == pattern.charAt(n)) {
				i++;
				n++;
				if (pattern.charAt(i) == pattern.charAt(n)) {
					next[i] = next[n];
				} else {
					next[i] = n;
				}
			} else {
				n = next[n];
			}
		}
		return next;
	}
	private static int[] next1(String pattern) {
		int len = pattern.length();
		int[] next = new int[len];
		int i = 0;
		int n = next[i] = -1;
		int imax = len-1;
		while (i < imax) {
			if (n < 0 ||pattern.charAt(i) == pattern.charAt(n)) {
				next[++i] = ++n;
			} else {
				n = next[n];
			}
		}
		return next;
	}
}
