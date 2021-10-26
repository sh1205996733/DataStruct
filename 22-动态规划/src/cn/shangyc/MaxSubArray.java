package cn.shangyc;

//最大连续子序列
public class MaxSubArray {
	public static void main(String[] args) {
		System.out.println(maxSubArray2(new int[] {-2,1,-3,4,-1,2,1,-5,4}));
	}
	
	//最大连续子序列和 – 动态规划 – 优化实现
	private static int maxSubArray2(int[] nums) {
		if (nums == null || nums.length == 0) return 0;
		int dp = nums[0];
		int max = dp;
		for (int i = 1; i < nums.length; i++) {
			dp = (dp > 0 ? dp+nums[i]:nums[i]);//dp记录前一个值即dp[i-1]
			max = Math.max(dp, max);
		}
		return max;
	}
	
	@SuppressWarnings("unused")
	private static int maxSubArray1(int[] nums) {
		if (nums == null || nums.length == 0) return 0;
		int[] dp = new int[nums.length];
		dp[0] = nums[0];
		int max = dp[0];
		for (int i = 1; i < dp.length; i++) {
			int prev = dp[i-1];
			dp[i] = (prev > 0 ? prev+nums[i]:nums[i]);
			max = Math.max(dp[i], max);
		}
		return max;
	}
}
