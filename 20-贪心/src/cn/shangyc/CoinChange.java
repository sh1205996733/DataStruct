package cn.shangyc;

import java.util.Arrays;

//零钱兑换
public class CoinChange {
	/**
	 *◼ 假设有 25 分、10 分、5 分、1 分的硬币，现要找给客户 41 分的零钱，如何办到硬币个数最少？
		◼ 贪心策略：每一次都优先选择面值最大的硬币
		① 选择 25 分的硬币，剩 16 分 ② 选择 10 分的硬币，剩 6 分 ③ 选择 5 分的硬币，剩 1 分 ④ 选择 1 分的硬币
		最终的解是共 4 枚硬币
		✓ 25 分、10 分、5 分、1 分硬币各一枚 
	 */
	public static void main(String[] args) {
		coinChange(new Integer[] {25, 10, 5, 1}, 41);
		//零钱兑换的另一个例子,最终的解是 1 枚 25 分、3 枚 5 分、1 枚 1 分的硬币，共 5 枚硬币,实际上本题的最优解是：2 枚 20 分、1 枚 1 分的硬币，共 3 枚硬币
//		coinChange(new Integer[] {25, 20, 5, 1}, 41);
	}

	private static void coinChange(Integer[] faces, int money) {
		Arrays.sort(faces);
		int coins = 0, idx = faces.length - 1;
		
		while (idx >= 0){
			while (money >= faces[idx]) {
				System.out.println(faces[idx]);
				money -= faces[idx];
				coins++;
			}
			idx--;
		}
		System.out.println(coins);
	}
	
	static void coinChange2(Integer[] faces, int money) {
		Arrays.sort(faces, (Integer f1, Integer f2) -> f2 - f1); 
		int coins = 0, i = 0;
		while (i < faces.length) {
			if (money < faces[i]) {
				i++;
				continue;
			}

			System.out.println(faces[i]);
			money -= faces[i];
			coins++;
		}
		System.out.println(coins);
	}
	
	static void coinChange1() {
		int[] faces = {25, 5, 10, 1};
		Arrays.sort(faces); // 1, 5, 10, 25
		
		int money = 41, coins = 0;
		for (int i = faces.length - 1; i >= 0; i--) {
			if (money < faces[i]) {
				continue;
			}

			System.out.println(faces[i]);
			money -= faces[i];
			coins++;
			i = faces.length;
		}
		
		System.out.println(coins);
	}
}
