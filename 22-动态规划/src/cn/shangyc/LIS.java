package cn.shangyc;

//最长上升子序列
@SuppressWarnings("unused")
public class LIS {
	public static void main(String[] args) {
		System.out.println(lengthOfLIS2(new int[] {10, 2, 2, 5, 1, 7, 101, 18}));
	}
	//牌顶-二分搜索
	private static int lengthOfLIS2(int[] nums) {//空间复杂度：O(n)  时间复杂度：O(nlogn)
		if (nums == null || nums.length == 0) return 0;
		// 牌堆的数量
		int len = 0;
		// 牌顶数组
		int[] top = new int[nums.length];
		// 遍历所有的牌
		for (int num : nums) {
			int begin = 0,end = len; 
			while (begin < end) {//遍历所有牌堆的牌顶
				int mid = (begin + end) >> 1;
				if (top[mid] >= num) {//mid左边
					end = mid;
				}else {
					begin = mid + 1;
				}
			}
			// 覆盖牌顶
			top[begin] = num;
			// 检查是否要新建一个牌堆
			if (begin == len) len++;
			
		}
		return len;
	}
	//牌顶
	private static int lengthOfLIS1(int[] nums) {
		if (nums == null || nums.length == 0) return 0;
		// 牌堆的数量
		int len = 0;
		// 牌顶数组
		int[] top = new int[nums.length];
		// 遍历所有的牌
		for (int num : nums) {
			int j = 0;
			while (j < len) {//遍历所有牌堆的牌顶
				if (top[j] >= num) {//找到一个>=num的牌顶
					top[j] = num;//num成为top[j]的新牌顶，退出循环
					break;
				}
				j++;
			}
			if (j == len) { // 新建一个牌堆
				len++;
				top[j] = num;
			}
			
		}
		return len;
	}

	//动态规划
	private static int lengthOfLIS(int[] nums) {
		if (nums == null || nums.length == 0) return 0;
		int[] dp = new int[nums.length];
		int max = dp[0] = 1;
		for (int i = 1; i < dp.length; i++) {
			dp[i] = 1;
			for (int j = 0; j < i; j++) {
				if (nums[j] >= nums[i]) continue;
				dp[i] = Math.max(dp[j]+1, dp[i]);
			}
			max = Math.max(max, dp[i]);
		}
		return max;
	}
}
