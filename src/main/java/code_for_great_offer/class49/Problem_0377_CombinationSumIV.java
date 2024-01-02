package code_for_great_offer.class49;

import java.util.Arrays;

/*
*TODO
* 给你一个由 不同 整数组成的数组 nums ，和一个目标整数 target 。
* 请你从 nums 中找出并返回总和为 target 的元素组合的个数。
* 题目数据保证答案符合 32 位整数范围。
* 示例 1：
* 输入：nums = [1,2,3], target = 4
* 输出：7
* 解释：
* 所有可能的组合为：
* (1, 1, 1, 1)
* (1, 1, 2)
* (1, 2, 1)
* (1, 3)
* (2, 1, 1)
* (2, 2)
* (3, 1)
* 请注意，顺序不同的序列被视作不同的组合。
* 示例 2：
* 输入：nums = [9], target = 3
* 输出：0
* 链接：https://leetcode.cn/problems/combination-sum-iv
*
* */
public class Problem_0377_CombinationSumIV {
	/*
	*TODO
	* 当前剩下的值是rest，
	* nums中所有的值，都可能作为分解rest的，第一块！全试一试
	* nums, 无重复，都是正
	* */
	public static int ways(int rest, int[] nums) {
		//TODO base case 说明这条尝试路径是无效的
		if (rest < 0) {
			return 0;
		}
		//TODO base case 说明这条尝试路径是有效的
		if (rest == 0) {
			return 1;
		}
		int ways = 0;
		//TODO 遍历数组的每一个元素 去做尝试
		for (int num : nums) {
			ways += ways(rest - num, nums);
		}
		return ways;
	}

	public static int[] dp = new int[1001];

	public static int combinationSum41(int[] nums, int target) {
		Arrays.fill(dp, 0, target + 1, -1);
		return process1(nums, target);
	}

	public static int process1(int[] nums, int rest) {
		if (rest < 0) {
			return 0;
		}
		if (dp[rest] != -1) {
			return dp[rest];
		}
		int ans = 0;
		if (rest == 0) {
			ans = 1;
		} else {
			for (int num : nums) {
				ans += process1(nums, rest - num);
			}
		}
		dp[rest] = ans;
		return ans;
	}

	/*
	*TODO
	* 剪枝 + 严格位置依赖的动态规划
	* 所谓的剪枝 就是 判断 当前节点的rest和当前选择的nums[i]比较
	* rest>nums[i] 就是剪枝
	* */
	public static int combinationSum42(int[] nums, int target) {
		Arrays.sort(nums);
		int[] dp = new int[target + 1];
		dp[0] = 1;
		for (int rest = 1; rest <= target; rest++) {
			for (int i = 0; i < nums.length && nums[i] <= rest; i++) {
				dp[rest] += dp[rest - nums[i]];
			}
		}
		return dp[target];
	}

}
