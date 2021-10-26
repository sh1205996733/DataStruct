package cn.shangyc;

//汉诺塔
public class Hanoi {
	//实现把 A 的 n 个盘子移动到 C（盘子编号是 [1, n] ） 每次只能移动1个盘子且大盘子只能放在小盘子下面
	public static void main(String[] args) {
		new Hanoi().hanoi(3, "A", "B", "C");
	}
	
	/**
	 * 将 n 个碟子从 p1 挪动到 p3
	 * @param p2 中间的柱子
	 */
	private void hanoi(int n, String p1, String p2, String p3) {//T(n) = 2 ∗ T(n − 1) + O(1) 时间复杂度是：O(2^n)  空间复杂度：O(n)
		if (n==1) {
			move(n,p1,p3);
			return;
		}
		hanoi(n-1,p1,p3,p2);
		move(n,p1,p3);
		hanoi(n-1,p2,p1,p3);
	}
	/**
	 * 将 no 号盘子从 from 移动到 to
	 * @param no
	 * @param from
	 * @param to
	 */
	void move(int no, String from, String to) {
		System.out.println("将" + no + "号盘子从" + from + "移动到" + to);
	}
}
