package cn.shangyc;

//蛮力算法
@SuppressWarnings("unused")
public class BruteForce {
	public static void main(String[] args) {
		String text = "Hello World";
		String pattern = "or";
		System.out.println(indexOf3(text,pattern));
	}
	private static int indexOf3(String text, String pattern) {//O(mn)
		if (text== null || pattern == null) return -1;
		int tlen = text.length();
		int plen = pattern.length();
		if (tlen == 0 || plen == 0 || tlen < plen) return -1;
		int tmax = tlen - plen;
		for (int ti = 0; ti <= tmax; ti++) {
			int pi = 0;
			for (;pi<plen;pi++) {
				if (text.charAt(ti+pi) != pattern.charAt(pi)) break; 
			}
			if (pi == plen) return ti;
		}
		return -1;
	}
	private static int indexOf2(String text, String pattern) {
		if (text== null || pattern == null) return -1;
		int tlen = text.length();
		int plen = pattern.length();
		if (tlen == 0 || plen == 0 || tlen < plen) return -1;
		int ti = 0,pi = 0;
		int tmax = tlen - plen;
		while (ti <= tmax && pi < plen) {
			if (text.charAt(ti+pi) == pattern.charAt(pi)) {
				pi++;
			} else {
				ti++;
				pi = 0;
			}
		}
		return pi == plen ? ti:-1;
	}
	//蛮力算法优化（此前实现的蛮力算法，在恰当的时候可以提前退出，减少比较次数）
	private static int indexOf(String text, String pattern) {
		if (text== null || pattern == null) return -1;
		int tlen = text.length();
		int plen = pattern.length();
		if (tlen == 0 || plen == 0 || tlen < plen) return -1;
		int ti = 0,pi = 0;
		/**
		 * 因此，ti 的退出条件可以从 ti < tlen 改为
			ti – pi <= tlen – plen
			ti – pi 是指每一轮比较中 text 首个比较字符的位置
		 */
		int tmax = tlen - plen;
		while (ti - pi <= tmax && pi<plen) {
			if (text.charAt(ti) == pattern.charAt(pi)) {
				ti++;
				pi++;
			} else {
				ti-= pi -1;
				pi = 0;
			}
		}
		return pi == plen ? ti-pi:-1;
	}
	//以字符为单位，从左到右移动模式串，直到匹配成功
	private static int indexOf1(String text, String pattern) {
		if (text== null || pattern == null) return -1;
		int tlen = text.length();
		int plen = pattern.length();
		if (tlen == 0 || plen == 0 || tlen < plen) return -1;
		int ti = 0,pi = 0;
		while (ti < tlen && pi<plen) {
			if (text.charAt(ti) == pattern.charAt(pi)) {
				ti++;
				pi++;
			} else {
				ti-= pi -1;
				pi = 0;
			}
		}
		return pi == plen ? ti-pi:-1;
	}
}
