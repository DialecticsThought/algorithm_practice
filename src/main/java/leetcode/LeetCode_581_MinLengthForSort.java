package leetcode;

/**
 * 给定一个无序数组arr， 如果只能对一个子数组进行排序， 但是想让数组整体都
 * 有序， 求需要排序的最短子数组长度。
 * 例如:arr = [1,5,3,4,2,6,7]返回4， 因为只有[5,3,4,2]需要排序
 *
 * code_for_great_offer.class02
 *  本题测试链接 : https://leetcode.cn/problems/shortest-unsorted-continuous-subarray/
 */
public class LeetCode_581_MinLengthForSort {

	public static int findUnsortedSubarray(int[] nums) {
		if (nums == null || nums.length < 2) {
			return 0;
		}
		int N = nums.length;
		int right = -1;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < N; i++) {
			if (max > nums[i]) {
				right = i;
			}
			max = Math.max(max, nums[i]);
		}
		int min = Integer.MAX_VALUE;
		int left = N;
		for (int i = N - 1; i >= 0; i--) {
			if (min < nums[i]) {
				left = i;
			}
			min = Math.min(min, nums[i]);
		}
		return Math.max(0, right - left + 1);
	}

}
