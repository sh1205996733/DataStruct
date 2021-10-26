package cn.shangyc;

@SuppressWarnings("unused")
public class Main {
	public static void main(String[] args) {
		int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4 };
		System.out.println(maxSubArray(nums));
	}
	private static int maxSubArray(int[] nums) {//分治解法：空间复杂度：O(nlogn)，时间复杂度：O(logn)
		if (nums == null || nums.length == 0) return 0;
		return maxSubArray(nums, 0, nums.length);
	}
	
	/**
	 * 求解[begin, end)中最大连续子序列的和
	 * T(n) = T(n/2) + T(n/2) + O(n)
	 * T(n) = 2T(n/2) + O(n)
	 * logba = 1  d = 1
	 */
	private static int maxSubArray(int[] nums, int begin, int end) {
		if (end - begin < 2) return nums[begin];//只有一个元素，直接返回
		
		int mid = (begin + end) >> 1;
		//1.maxSubArray(nums,begin,mid);//求解[begin, mid)中最大连续子序列的和
		//2.maxSubArray(nums,mid,end);//求解[mid, end)中最大连续子序列的和
		
		//3.求解[i , mid) + [mid , j)中最大连续子序列的和,一部分存在于 [begin , mid) 中，另一部分存在于 [mid , end) 中
		int leftMax = nums[mid - 1];
		int leftSum = leftMax;
		for (int i = mid - 2; i >= begin; i--) {//计算左边最大连续子序列,从mid-2开始--累加计算左边最大值
			leftSum += nums[i];
			leftMax = Math.max(leftMax, leftSum);
		}
		int rightMax = nums[mid];
		int rightSum = rightMax;
		for (int i = mid + 1; i < end; i++) {//计算左边最大连续子序列,从mid-2开始--累加计算左边最大值
			rightSum += nums[i];
			rightMax = Math.max(rightMax, rightSum);
		}
		//4.比较三个值的最大值
		return Math.max(leftMax+rightMax, 
				Math.max(maxSubArray(nums,begin,mid),
						maxSubArray(nums,mid,end)));
	}
	//最大连续子序列和
	private static int maxSubArray1(int[] nums) {//暴力解法优化：空间复杂度：O(1)，时间复杂度：O(n^2)
		if (nums == null || nums.length == 0) return 0;
		int max = Integer.MIN_VALUE;
		for (int begin = 0; begin < nums.length; begin++) {
			int sum = 0;// sum是[begin, end]的和
			for (int end = begin; end < nums.length; end++) {
				sum += nums[end];
				max = Math.max(sum,max);
			}
		}
		return max;
	}
		
	//最大连续子序列和
	private static int maxSubArray0(int[] nums) {//暴力解法：空间复杂度：O(1)，时间复杂度：O(n^3)
		if (nums == null || nums.length == 0) return 0;
		int max = Integer.MIN_VALUE;
		for (int begin = 0; begin < nums.length; begin++) {
			for (int end = begin; end < nums.length; end++) {
				int sum = 0;// sum是[begin, end]的和
				for (int i = begin; i <= end; i++) {
					sum += nums[i];
				}
				max = Math.max(sum,max);
			}
		}
		return max;
	}
}
