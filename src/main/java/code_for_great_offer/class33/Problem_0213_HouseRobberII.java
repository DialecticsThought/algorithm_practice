package code_for_great_offer.class33;
/*
* 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，
* 这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的房屋装有相互连通的防盗系统，
* 如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。
* 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，今晚能够偷窃到的最高金额。
* 示例1：
* 输入：nums = [2,3,2]
* 输出：3
* 解释：你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
* 示例 2：
* 输入：nums = [1,2,3,1]
* 输出：4
* 解释：你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
* 偷窃到的最高金额 = 1 + 3 = 4 。
* 示例 3：
* 输入：nums = [1,2,3]
* 输出：3
* 来源：力扣（LeetCode）
* 链接：https://leetcode.cn/problems/house-robber-ii
*TODO 原始问题不能取相邻数的情况下 子序列的累加和最大问题
* 假设arr.len=9  类似于dp表
* 从0位置考虑 0~0范围上 子序列累加和最大 就是arr[0]本身
* 从1位置考虑 0~1范围上 子序列累加和最大 arr[0]和arr[1]比较 谁大就是取谁 因为不能相邻
* 从1位置考虑 0~2范围上 子序列累加和最大 ....
* ......
* 从i位置考虑 0~i范围上 子序列累加和最大
* 讨论可能性
* 1.只考虑arr[i]  eg：[-3,-5,-7,6]
* 2.完全不考虑arr[i] 那么0~i范围上 子序列累加和最大 就是 0~i-1范围上 子序列累加和最大
* 3.不仅要i 还要其他位置 那么就是 arr[i] = 0~i-2范围上 子序列累加和最大
*TODO
* 进阶问题 0和N-1相连接 就是一个环
* 选择1 只要按照原先的方式 把 0~N-2范围上的dp表求出来 不要把N-1位置的答案求出来 因为N-1位置不能取
* 选择2 不选择0 直接从1位置考虑  1~1范围上 子序列累加和最大 ....  把 1~N-1范围上的dp表求出来
* 然后Math.max(选择1 选择2)
 * */
public class Problem_0213_HouseRobberII {

	// arr 长度大于等于1
	public static int pickMaxSum(int[] arr) {
		int n = arr.length;
		// dp[i] : arr[0..i]范围上，随意选择，但是，任何两数不能相邻。得到的最大累加和是多少？
		int[] dp = new int[n];
		dp[0] = arr[0];
		dp[1] = Math.max(arr[0], arr[1]);
		for (int i = 2; i < n; i++) {
			int p1 = arr[i];
			int p2 = dp[i - 1];
			int p3 = arr[i] + dp[i - 2];
			dp[i] = Math.max(p1, Math.max(p2, p3));
		}
		return dp[n - 1];
	}

	public static int rob(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		if (nums.length == 1) {
			return nums[0];
		}
		if (nums.length == 2) {
			return Math.max(nums[0], nums[1]);
		}
		int pre2 = nums[0];
		int pre1 = Math.max(nums[0], nums[1]);
		for (int i = 2; i < nums.length - 1; i++) {
			int tmp = Math.max(pre1, nums[i] + pre2);
			pre2 = pre1;
			pre1 = tmp;
		}
		int ans1 = pre1;
		pre2 = nums[1];
		pre1 = Math.max(nums[1], nums[2]);
		for (int i = 3; i < nums.length; i++) {
			int tmp = Math.max(pre1, nums[i] + pre2);
			pre2 = pre1;
			pre1 = tmp;
		}
		int ans2 = pre1;
		return Math.max(ans1, ans2);
	}

}
