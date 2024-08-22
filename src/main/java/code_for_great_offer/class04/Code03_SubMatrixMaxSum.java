package code_for_great_offer.class04;

public class Code03_SubMatrixMaxSum {

	public static int maxSum(int[][] m) {
		if (m == null || m.length == 0 || m[0].length == 0) {
			return 0;
		}
		// O(N^2 * M)
		int N = m.length;
		// 这行代码定义了一个整数 M，它代表矩阵 m 的列数
		int M = m[0].length;
		// 初始化 max 为最小可能的整数值。这是为了在后面找到最大子矩阵和时有一个比较的基准。
		int max = Integer.MIN_VALUE;
		//TODO 2个for循环的目的就是 要从矩阵的i行到j行的所有数据
		for (int i = 0; i < N; i++) {
			// i~j
			// 在每一次外循环中，初始化一个新的数组 s，长度为矩阵的列数。这个数组用于存储当前考虑的行范围内的列元素的和
			int[] s = new int[M];
			// 第二个 for 循环，用于确定当前子矩阵的结束行。
			for (int j = i; j < N; j++) {
				// 第三个 for 循环，用于累加从第 i 行到第 j 行的每一列的元素。
				for (int k = 0; k < M; k++) {
					s[k] += m[j][k];
				}
				// 调用 maxSubArray 方法（需要额外定义）来找出当前累加行中最大子数组的和，并更新 max。
				max = Math.max(max, maxSubArray(s));
			}
		}
		// 方法结束，返回找到的最大子矩阵和
		return max;
	}

	public static int maxSubArray(int[] arr) {
		// 这个条件检查是为了确保输入数组不是空的。如果数组是空的或其长度为0，则方法返回0。
		if (arr == null || arr.length == 0) {
			return 0;
		}
		// 初始化 max 为最小可能的整数值。这是为了在寻找最大子数组和时有一个比较的基准。
		int max = Integer.MIN_VALUE;
		// 初始化当前子数组和 cur 为0。这个变量用于计算和存储目前为止遍历的子数组的和。
		int cur = 0;
		// 这是一个 for 循环，用于遍历数组的每个元素。
		for (int i = 0; i < arr.length; i++) {
			//  将当前元素 arr[i] 添加到当前子数组和 cur 中。
			cur += arr[i];
			// 更新 max 为 max 和 cur 中的较大值。这样可以确保 max 始终保持为目前为止遇到的最大子数组和。
			max = Math.max(max, cur);
			//  如果当前子数组和 cur 变成负数，那么将其重置为0。这是因为任何包含负总和的子数组都不可能是最大子数组和的一部分
			cur = cur < 0 ? 0 : cur;
		}
		// 方法结束，返回找到的最大子数组和
		return max;
	}

	// 本题测试链接 : https://leetcode-cn.com/problems/max-submatrix-lcci/
	public static int[] getMaxMatrix(int[][] m) {
		int N = m.length;
		int M = m[0].length;
		int max = Integer.MIN_VALUE;
		int cur = 0;
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		for (int i = 0; i < N; i++) {
			int[] s = new int[M];
			for (int j = i; j < N; j++) {
				cur = 0;
				int begin = 0;
				for (int k = 0; k < M; k++) {
					s[k] += m[j][k];
					cur += s[k];
					if (max < cur) {
						max = cur;
						a = i;
						b = begin;
						c = j;
						d = k;
					}
					if (cur < 0) {
						cur = 0;
						begin = k + 1;
					}
				}
			}
		}
		return new int[] { a, b, c, d };
	}

}
