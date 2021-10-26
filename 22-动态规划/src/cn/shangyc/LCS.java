package cn.shangyc;

//最长公共子序列
@SuppressWarnings("unused")
public class LCS {
	public static void main(String[] args) {
		int len = lcs(new int[] {1, 3, 5, 9, 10}, new int[] {1, 4, 9, 10});
		System.out.println(len);
	}
	
	//最长公共子序列 – 非递归实现-一维数组
	private static int lcs(int[] nums1, int[] nums2) {//空间复杂度：O n ∗ m ◼ 时间复杂度：O(m)
		if (nums1 == null || nums1.length == 0) return 0;
		if (nums2 == null || nums2.length == 0) return 0;
		int[] rowsNums = nums1, colsNums = nums2;
		if (nums1.length < nums2.length) {
			colsNums = nums1;
			rowsNums = nums2;
		}
		int[] dp = new int[colsNums.length + 1];
		for (int i = 1; i <= rowsNums.length; i++) {
			int cur = 0;
			for (int j = 1; j <= colsNums.length; j++) {
				int leftTop = cur;
				cur = dp[j];
				if (rowsNums[i-1] == colsNums[j-1]) {
					dp[j] = leftTop + 1;
				}else {
					dp[j] = Math.max(dp[j-1],dp[j]);
				}
			}
		}
		return dp[nums2.length];
	}
	//最长公共子序列 – 非递归实现-一维数组
	private static int lcs4(int[] nums1, int[] nums2) {//空间复杂度：O n ∗ m ◼ 时间复杂度：O(m)
		if (nums1 == null || nums1.length == 0) return 0;
		if (nums2 == null || nums2.length == 0) return 0;
		int[] dp = new int[nums2.length+1];
		for (int i = 1; i <= nums1.length; i++) {
			int cur = 0;
			for (int j = 1; j <= nums2.length; j++) {
				int leftTop = cur;
				cur = dp[j];
				if (nums1[i-1] == nums2[j-1]) {
					dp[j] = leftTop + 1;
				}else {
					dp[j] = Math.max(dp[j-1],dp[j]);
				}
			}
		}
		return dp[nums2.length];
	}
	//最长公共子序列 – 非递归实现-滚动数组
	private static int lcs3(int[] nums1, int[] nums2) {//空间复杂度：O n ∗ m ◼ 时间复杂度：O(m)
		if (nums1 == null || nums1.length == 0) return 0;
		if (nums2 == null || nums2.length == 0) return 0;
		int[][] dp = new int[2][nums2.length+1];
		for (int i = 1; i <= nums1.length; i++) {
			int row = i & 1;
			int prevRow = (i - 1) & 1;
			for (int j = 1; j <= nums2.length; j++) {
				if (nums1[i-1] == nums2[j-1]) {
					dp[row][j] = dp[prevRow][j-1] + 1;
				}else {
					dp[row][j] = Math.max(dp[row][j-1],dp[prevRow][j]);
				}
			}
		}
		return dp[nums1.length & 1][nums2.length];
	}
		
	//最长公共子序列 – 非递归实现
	private static int lcs2(int[] nums1, int[] nums2) {//空间复杂度：O n ∗ m ◼ 时间复杂度：O(n ∗ m)
		if (nums1 == null || nums1.length == 0) return 0;
		if (nums2 == null || nums2.length == 0) return 0;
		int[][] dp = new int[nums1.length+1][nums2.length+1];
		for (int i = 1; i <= nums1.length; i++) {
			for (int j = 1; j <= nums2.length; j++) {
				if (nums1[i-1] == nums2[j-1]) {
					dp[i][j] = dp[i-1][j-1] + 1;
				}else {
					dp[i][j] = Math.max(dp[i][j-1],dp[i-1][j]);
				}
			}
		}
		return dp[nums1.length][nums2.length];
	}

	//递归◼ 空间复杂度：O k , k = min{n, m}，n、m 是 2 个序列的长度
	//◼ 时间复杂度：O 2n ，当 n = m 时
	private static int lcs1(int[] nums1, int[] nums2) {
		if (nums1 == null || nums1.length == 0) return 0;
		if (nums2 == null || nums2.length == 0) return 0;
		return lcs1(nums1, nums1.length, nums2, nums2.length);
	}

	private static int lcs1(int[] nums1, int i, int[] nums2, int j) {
		if (i == 0 || j == 0) return 0;
		if (nums1[i-1] == nums2[j-1]) {
			return lcs1(nums1,i-1,nums2,j-1) + 1;
		}
		return Math.max(lcs1(nums1,i-1,nums2,j), lcs1(nums1,i,nums2,j-1));
	}
}
