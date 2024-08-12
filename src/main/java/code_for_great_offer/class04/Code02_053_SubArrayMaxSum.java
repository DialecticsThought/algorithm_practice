package code_for_great_offer.class04;

// 本题测试链接 : https://leetcode.com/problems/maximum-subarray/

/**
 * 从左往右模型
 * 对于数组或序列中的每一个元素，你都有两个基本的选择
 * 选择该元素：
 *
 * 将当前元素包括在当前的子数组或子序列中。
 * 这个选择意味着你将这个元素加入到已经构建的部分解中，从而构成一个新的部分解。
 * 不选择该元素：
 *
 * 将当前元素排除在当前的子数组或子序列之外。
 * 这个选择意味着你忽略当前元素，继续处理下一个元素，可能从下一个元素开始一个新的部分解。
 * <pre>
 *     [2, 1, -3]
 *                                 []
 *                   (选2)   /              \ (不选2)
 *                        [2]                 []
 *                (选1)  /   \ (不选1)  (选1) /   \ (不选1)
 *             		[2,1]     [2]         [1]    []
 *          (选-3)/\(不选-3)     /\         /\     / \
 *   	[2,1,-3] [2,1]     [2,-3] [2]  [1,-3] [1] [-3] []
 * </pre>
 *
 *
 */
public class Code02_053_SubArrayMaxSum {

	public int maxSubArray(int[] nums) {
		// 调用递归函数，初始从第0个元素开始，前一个子数组和为0
		return findMaxSubArray(nums, 0, 0);
	}
	// 递归函数：findMaxSubArray
	private int findMaxSubArray(int[] nums, int index, int currentSum) {
		// 基础情况：如果已到达数组末尾，返回当前子数组和
		if (index == nums.length) {
			return currentSum;
		}
		// 情况1：选择当前元素，加入到当前子数组和中
		int includeCurrent = findMaxSubArray(nums, index + 1, currentSum + nums[index]);
		// 情况2：不选择当前元素，从当前元素重新开始一个新的子数组
		int startNewSubArray = findMaxSubArray(nums, index + 1, nums[index]);
		// 返回两种选择中的最大值
		return Math.max(includeCurrent, startNewSubArray);
	}

	public static int maxSubArray1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		int cur = 0;
		for (int i = 0; i < arr.length; i++) {
			cur += arr[i];
			max = Math.max(max, cur);
			cur = cur < 0 ? 0 : cur;
		}
		return max;
	}

	public static int maxSubArray2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		/*
		* 每一个位置都有两种选择
		* 1.向左边扩充 那么 i位置的最优解 依赖于i-1位置的最优解
		* 2.只包含自己
		* 对于 返回一个数组中，子数组最大累加和
		* 就求 以i位置结尾 能左移到哪里 子数组最大  这个i从0开始到arr.length-1
		* */
		// 上一步，dp的值
		// dp[0]
		int pre = arr[0];
		int max = arr[0];
		for (int i = 1; i < arr.length; i++) {
			pre = Math.max(arr[i], arr[i] + pre);
			max =  Math.max(max, pre);
		}
		return max;
	}

}
