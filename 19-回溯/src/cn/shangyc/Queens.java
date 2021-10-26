package cn.shangyc;

public class Queens {
	public static void main(String[] args) {
		new Queens().placeQueens(4);
	}

	/**
	 * 数组索引是行号，数组元素是列号(存放的是每一个皇后的列号，在第几列) queens[0] = 1表示第一个皇后在第1行第2列
	 */
	int[] queens;
	/**
	 * 一共有多少种摆法
	 */
	int ways;

	private void placeQueens(int n) {
		if (n < 1) return;
		queens = new int[n];
		place(0);
		System.out.println(n + "皇后一共有" + ways + "种摆法");
	}
	
	/**
	 * 从第row行开始摆放皇后
	 * @param row
	 */
	private void place(int row) {
		if (row == queens.length) {
			ways++;
			show();
			return;
		}
		for (int col = 0; col < queens.length; col++) {
			if (isValid(row,col)) {//有效，摆放下一个皇后，回溯操作
				queens[row] = col;
				place(row+1);
			}
		}
	}
	
	/**
	 * 判断第row行第col列是否可以摆放皇后,剪枝
	 */
	private boolean isValid(int row, int col) {
		for (int i = 0; i < row; i++) {
			// 第col列已经有皇后
			if (queens[i] == col) {
				System.out.println("[" + row + "][" + col + "]=false,同一列");
				return false;
			}
			// 第i行的皇后跟第row行第col列格子处在同一斜线上
			if (row - i == Math.abs(col - queens[i])) {
				System.out.println("[" + row + "][" + col + "]=false,同一斜线");
				return false;
			}
		}
		System.out.println("[" + row + "][" + col + "]=true");
		return true;
	}

	/**
	 * 显示皇后摆放情况
	 */
	private void show() {
		for (int row = 0; row < queens.length; row++) {//行
			for (int col = 0; col < queens.length; col++) {//列
				if (queens[row] == col) {
					System.out.print("1 ");
				}else {
					System.out.print("0 ");
				}
			}
			System.out.println();
		}
		System.out.println("------------------------------");
	}
}
