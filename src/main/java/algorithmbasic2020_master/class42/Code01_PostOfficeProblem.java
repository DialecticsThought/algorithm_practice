package algorithmbasic2020_master.class42;

import java.util.Arrays;

public class Code01_PostOfficeProblem {
	//不优化的版本
	public static int min1(int[] arr, int num) {
		if (arr == null || num < 1 || arr.length < num) {
			return 0;
		}
		int N = arr.length;
		int[][] w = new int[N + 1][N + 1];
		for (int L = 0; L < N; L++) {
			for (int R = L + 1; R < N; R++) {
				//TODO 每个格子 =  之前的距离（左侧一个格子） + 新来的点  - 之前的邮局最优位置
				w[L][R] = w[L][R - 1] + arr[R] - arr[(L + R) >> 1];
			}
		}
		int[][] dp = new int[N][num + 1];
		//TODO 第0列 没有意义 第0行也没有意义
		//TODO 第一列的每一行表示 0~i个居名点 建立一个邮局 => 直接从w中拿
		for (int i = 0; i < N; i++) {
			dp[i][1] = w[0][i];
		}
		for (int i = 1; i < N; i++) {
			//TODO 假设0~i个居名点 一共i+1个 如果 j是超过i+1邮局 那么 dp[i][j] = 0 邮局过剩
			// j也不能超过num
			for (int j = 2; j <= Math.min(i, num); j++) {
				int ans = Integer.MAX_VALUE;
				//TODO 不做优化 就是枚举行为
				for (int k = 0; k <= i; k++) {
					/*
					* TODO 最有一个邮局负责w[k + 1][i]  其他所有邮局（j-1个邮局）负责k个居民点
					*  假设 i=7 j=4
					*  0~0 3个邮局 最后邮局负责 1~7  dp[0][3]+w[1][7]
					*  0~1 3个邮局 最后邮局负责 2~7  dp[1][3]+w[2][7]
					*  ...
					*  0~k 3个邮局 最后邮局负责 k~7  dp[1][k]+w[k+1][7]
					*  ===>最后取得所有答案的最小值
					* */
					ans = Math.min(ans, dp[k][j - 1] + w[k + 1][i]);
				}
				dp[i][j] = ans;
			}
		}
		return dp[N - 1][num];
	}

	public static int min2(int[] arr, int num) {
		if (arr == null || num < 1 || arr.length < num) {
			return 0;
		}
		int N = arr.length;
		int[][] w = new int[N + 1][N + 1];
		for (int L = 0; L < N; L++) {
			for (int R = L + 1; R < N; R++) {
				w[L][R] = w[L][R - 1] + arr[R] - arr[(L + R) >> 1];
			}
		}
		int[][] dp = new int[N][num + 1];
		int[][] best = new int[N][num + 1];
		for (int i = 0; i < N; i++) {
			dp[i][1] = w[0][i];
			best[i][1] = -1;
		}
		for (int j = 2; j <= num; j++) {
			for (int i = N - 1; i >= j; i--) {
				int down = best[i][j - 1];
				int up = i == N - 1 ? N - 1 : best[i + 1][j];
				int ans = Integer.MAX_VALUE;
				int bestChoose = -1;
				for (int leftEnd = down; leftEnd <= up; leftEnd++) {
					int leftCost = leftEnd == -1 ? 0 : dp[leftEnd][j - 1];
					int rightCost = leftEnd == i ? 0 : w[leftEnd + 1][i];
					int cur = leftCost + rightCost;
					if (cur <= ans) {
						ans = cur;
						bestChoose = leftEnd;
					}
				}
				dp[i][j] = ans;
				best[i][j] = bestChoose;
			}
		}
		return dp[N - 1][num];
	}

	// for test
	public static int[] randomSortedArray(int len, int range) {
		int[] arr = new int[len];
		for (int i = 0; i != len; i++) {
			arr[i] = (int) (Math.random() * range);
		}
		Arrays.sort(arr);
		return arr;
	}

	// for test
	public static void printArray(int[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {
		int N = 30;
		int maxValue = 100;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * N) + 1;
			int[] arr = randomSortedArray(len, maxValue);
			int num = (int) (Math.random() * N) + 1;
			int ans1 = min1(arr, num);
			int ans2 = min2(arr, num);
			if (ans1 != ans2) {
				printArray(arr);
				System.out.println(num);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println("Oops!");
			}
		}
		System.out.println("测试结束");

	}

}
