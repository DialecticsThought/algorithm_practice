package algorithmbasic2020_master.class05;

/**
* https://leetcode.cn/problems/count-of-range-sum/
* 给你一个整数数组nums 以及两个整数lower 和 upper 。求数组中，值位于范围 [lower, upper] （包含lower和upper）之内的 区间和的个数 。
* 区间和S(i, j)表示在nums中，位置从i到j的元素之和，包含i和j(i ≤ j)。
* 示例 1：
* 输入：nums = [-2,5,-1], lower = -2, upper = 2
* 输出：3
* 解释：存在三个区间：[0,0]、[2,2] 和 [0,2] ，对应的区间和分别是：-2 、-1 、2 。
* 示例 2：
* 输入：nums = [0], lower = 0, upper = 0
 * 输出：1
*
* */
public class leetCode_327_CountOfRangeSum {

	public static int countRangeSum(int[] nums, int lower, int upper) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		long[] sum = new long[nums.length];
		sum[0] = nums[0];
		for (int i = 1; i < nums.length; i++) {
			sum[i] = sum[i - 1] + nums[i];
		}
		return process(sum, 0, sum.length - 1, lower, upper);
	}

	public static int process(long[] sum, int L, int R, int lower, int upper) {
		if (L == R) {//TODO 表示arr[0]+arr[1]...+arr[L]
			return sum[L] >= lower && sum[L] <= upper ? 1 : 0;
		}
		int M = L + ((R - L) /2 );
		//TODO 左侧 + 右侧 + 左右合并产生的结果
		return process(sum, L, M, lower, upper) + process(sum, M + 1, R, lower, upper)
				+ merge(sum, L, M, R, lower, upper);
	}

	public static int merge(long[] arr, int L, int M, int R, int lower, int upper) {
		//TODO 1.merge但是 对于右组的每个数x，求左组中有多少个数位于[x-upper,x-lower]
		int ans = 0;
		int windowL = L;
		int windowR = L;
		// [windowL, windowR)
		for (int i = M + 1; i <= R; i++) {
			long min = arr[i] - upper;
			long max = arr[i] - lower;
			while (windowR <= M && arr[windowR] <= max) {
				windowR++;
			}
			while (windowL <= M && arr[windowL] < min) {
				windowL++;
			}
			//防止越界 当然可以省略 因为窗口是左闭2
			ans += Math.max(0,windowR - windowL);
		}
		//TODO 2.开始归并排序
		long[] help = new long[R - L + 1];
		int i = 0;
		int p1 = L;//左组的指针
		int p2 = M + 1;//右组的指针
		while (p1 <= M && p2 <= R) {
			help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
		}
		while (p1 <= M) {
			help[i++] = arr[p1++];
		}
		while (p2 <= R) {
			help[i++] = arr[p2++];
		}
		for (i = 0; i < help.length; i++) {
			arr[L + i] = help[i];
		}
		return ans;
	}

	public static int countRangeSum2(int[] nums, int lower, int upper) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		int[] sum = new int[nums.length];
		sum[0] = nums[0];
		for (int i = 1; i < nums.length; i++) {
			sum[i] = sum[i - 1] + nums[i];
		}
		return count(sum, 0, sum.length - 1, lower, upper);
	}

	// arr[L..R]已经不传进来了，只传进来sum(前缀和数组)
	//在原始的arr[L ...R]中，有多少个子数组累加和在[lower , upper]上
	public static int count(int[] sum,int L,int  R,int lower,int upperp) {
		return 1;
	}

}
