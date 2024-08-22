package code_for_great_offer.class32;

public class Problem_0152_MaximumProductSubarray {
/*
* 给你一个整数数组 nums，请你找出数组中乘积最大的非空连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
* 测试用例的答案是一个32-位 整数。
* 子数组 是数组的连续子序列。
* 示例 1:
* 输入: nums = [2,3,-2,4]
* 输出: 6
* 解释:子数组 [2,3] 有最大乘积 6。
* 示例 2:
* 输入: nums = [-2,0,-1]
* 输出: 0
* 解释:结果不能为 2, 因为 [-2,-1] 不是子数组。
* 链接：https://leetcode.cn/problems/maximum-product-subarray
*
*TODO
* 子数组问题 的思想传统 求 每个位置是结尾的情况下 。。。
* 类似于dp 以i（1<=i<=arr.len-1）位置结尾的情况下 向左推多远 能求出 乘积的max
* 每个i位置求出相应的答案 然后取出最大的一个就是ans
*  ====> 因为之前求出的数据 能加速后面求解的速度
* eg： 假设 已经求出i-1位置的最大累乘积 ，此时来到i位置
* 分析情况 =>  dp[17]=Math.max(dp[16]*arr[17],arr[17])
* 1.只有i位置这个数 说明 i-1位置的累乘积 * i位置的数 < i位置的数
* 2.用到了i-1位置的最大累乘积 说明 i-1位置的累乘积 *i位置的数 > i位置的数
* 3.用到了i-1位置的最小累乘积(因为 负数*负数=整数) eg:[100,6,-2,-3] 到2位置，最大累乘积 是 1位置的最小累乘积 *2位置的数
* 总结：
* 每个位置 求最小累成积+最大累成积
* */

	public static double max(double[] arr) {
		if(arr == null || arr.length == 0) {
			return 0; // 报错！
		}
		int n = arr.length;
		// 上一步的最大的初始是0位置的数
		double premax = arr[0];
		// 上一步的最小的初始是0位置的数
		double premin = arr[0];
		double ans = arr[0];
		//从1位置开始
		for(int i = 1; i < n; i++) {
			double p1 = arr[i];
			double p2 = arr[i] * premax;
			double p3 = arr[i] * premin;
			double curmax = Math.max(Math.max(p1, p2), p3);
			double curmin = Math.min(Math.min(p1, p2), p3);
			ans = Math.max(ans, curmax);
			premax = curmax;
			premin = curmin;
		}
		return ans;
	}



	public static int maxProduct(int[] nums) {
		int ans = nums[0];
		int min = nums[0];
		int max = nums[0];
		for (int i = 1; i < nums.length; i++) {
			int curmin = Math.min(nums[i], Math.min(min * nums[i], max * nums[i]));
			int curmax = Math.max(nums[i], Math.max(min * nums[i], max * nums[i]));
			min = curmin;
			max = curmax;
			ans  = Math.max(ans, max);
		}
		return ans;
	}

}
