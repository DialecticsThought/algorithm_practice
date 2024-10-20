package leetcode;

/**
 * code_for_great_offer.class10
 * 测试链接 : https://leetcode.com/problems/k-inverse-pairs-array/
 */
public class LeetCode_629_KInversePairs {

	public static int kInversePairs(int n, int k) {
		if (n < 1 || k < 0) {
			return 0;
		}
		int[][] dp = new int[n + 1][k + 1];
		dp[0][0] = 1;
		int mod = 1000000007;//用来放置溢出
		for (int i = 1; i <= n; i++) {
			dp[i][0] = 1;
			for (int j = 1; j <= k; j++) {
				dp[i][j] = (dp[i][j - 1] + dp[i - 1][j]) % mod;
				if (j >= i) {
					//下面的操作 是
					dp[i][j] = (dp[i][j] - dp[i - 1][j - i] + mod) % mod;
				}
			}
		}
		return dp[n][k];
	}

	public static int kInversePairs2(int n, int k) {
		if (n < 1 || k < 0) {
			return 0;
		}
		int[][] dp = new int[n + 1][k + 1];
		dp[0][0] = 1;
		for (int i = 1; i <= n; i++) {
			dp[i][0] = 1;
			for (int j = 1; j <= k; j++) {
				dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
				if (j >= i) {
					dp[i][j] -= dp[i - 1][j - i];
				}
			}
		}
		return dp[n][k];
	}
	/*
	* [start, x, x, x, x, ..., x]，start可以为 1 到 n 中的任意
	* 一个数，故以start为起始的第一个数字，一共有n种可能：
	*
	* 设 f(n, k) 表示 1 到 n 组成的数组产生k个逆序对的数组个数。
	*
	* start = 1 时， [1, x, x, ..., x]， 第一个数字 1 和
	* 后面的 n - 1 个数字都不会产生逆序对，因此后
	* 面 n - 1 个数必须要产生 k 个逆序对，
	* 故f(n, k) = f(n - 1, k);
	*
	* start = 2 时， [2, x, x, ..., x]， 第一个数字 2 和
	* 后面的 n - 1 个数字产生 1 个逆序对 ([2,1]) ，
	* 因此后面 n - 1 个数必须要产生 k - 1 个逆序对，
	* 故f(n, k) = f(n - 1, k - 1);
	*
	* start = 3 时， [3, x, x, ..., x]， 第一个数字 3 和
	* 后面的 n - 1 个数字产生 2 个逆序对 ([3,1], [3, 2]) ，
	* 因此后面 n - 1 个数必须要产生 k - 2 个逆序对，
	* 故f(n, k) = f(n - 1, k - 2);
	*
	* start = 4 时， [4, x, x, ..., x]， 第一个数字 4 和
	* 后面的 n - 1 个数字产生 3 个逆序对
	* ([4,1], [4, 2], [4, 3]) ，因此后面 n - 1 个数必须
	* 要产生 k - 3 个逆序对，故f(n, k) = f(n - 1, k - 3);
	*
	* 以此类推...
	*
	* start = n 时， [n, x, x, ..., x]， 第一个数字 n 和
	* 后面的 n - 1 个数字产生 n - 1 个逆序对
	*  ([n,1], [n, 2], ..., [n, n - 1]) ，因此后面 n - 1 个
	* 数必须要产生 k - (n - 1) 个逆序对，
	* 故f(n, k) = f(n - 1, k - (n - 1));
	*
	* 综上所述：f(n, k) = f(n - 1, k) + f(n - 1, k - 1) + ...
	* 					  + f(n - 1, k - (n - 1)); 其中 k >= (n - 1);
	*
	* 		f(n, k + 1) = f(n - 1, k + 1) + f(n - 1, k) + ...
	* 					  + f(n - 1, k + 1 - (n - 1)); 其中 (k + 1) >= (n - 1);
	*
	* 		f(n, k + 1) = f(n - 1, k + 1) + f(n, k) - f(n - 1, k - (n - 1))
	* 						= f(n, k) + f(n - 1, k + 1) - f(n - 1, k - (n - 1)); 其中 (k + 1) >= (n - 1);
	*
	当 k >= (n - 1) 时， f(n, k) = f(n, k - 1) + f(n - 1, k) - f(n - 1, k - 1 - (n - 1));
	当 k <  (n - 1) 时， f(n, k) = f(n, k - 1) + f(n - 1, k);
	*
	* */
}
