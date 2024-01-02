package code_for_great_offer.class49;

public class Problem_0548_SplitArrayEithEqualSum {
	/*
	*TODO
	* 数组切成4段 使得累加和相同
	* 有正有负有0 没有单调性 所以O(n^3) 就可以过了
	* 看Split4Part 这个题目有单调性
	* 假设 当前来到i位置 0~i的累加和=16  这是一个for循环
	* 那么从后往前找j位置 实现j~N-1的累加和=16 这是一个for循环
	* 然后在从i+1开始往后找 找到a位置  这是一个for循环
	* 使得i+1~a的累加和=16 a+1~j-1的累加和=16
	* */
	public static boolean splitArray(int[] nums) {
		if (nums.length < 7) {
			return false;
		}
		int[] sumLeftToRight = new int[nums.length];
		int[] sumRightToLeft = new int[nums.length];
		int s = 0;
		for (int i = 0; i < nums.length; i++) {
			sumLeftToRight[i] = s;
			s += nums[i];
		}
		s = 0;
		for (int i = nums.length - 1; i >= 0; i--) {
			sumRightToLeft[i] = s;
			s += nums[i];
		}
		for (int i = 1; i < nums.length - 5; i++) {
			for (int j = nums.length - 2; j > i + 3; j--) {
				//TODO find 也是for循环
				if (sumLeftToRight[i] == sumRightToLeft[j] && find(sumLeftToRight, sumRightToLeft, i, j)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean find(int[] sumLeftToRight, int[] sumRightToLeft, int l, int r) {
		int s = sumLeftToRight[l];
		int prefix = sumLeftToRight[l + 1];
		int suffix = sumRightToLeft[r - 1];
		for (int i = l + 2; i < r - 1; i++) {
			int s1 = sumLeftToRight[i] - prefix;
			int s2 = sumRightToLeft[i] - suffix;
			if (s1 == s2 && s1 == s) {
				return true;
			}
		}
		return false;
	}

}
