package code_for_great_offer.class11;

import java.util.ArrayList;
import java.util.List;

// 本题测试链接 : https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/
public class Code01_MinimumInsertionStepsToMakeAStringPalindrome {

	// 测试链接只测了本题的第一问，直接提交可以通过
	public static int minInsertions(String s) {
		if (s == null || s.length() < 2) {
			return 0;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		int[][] dp = new int[N][N];
		for (int i = 0; i < N - 1; i++) {
			dp[i][i + 1] = str[i] == str[i + 1] ? 0 : 1;
		}
		for (int i = N - 3; i >= 0; i--) {
			for (int j = i + 2; j < N; j++) {
				dp[i][j] = Math.min(dp[i][j - 1], dp[i + 1][j]) + 1;
				if (str[i] == str[j]) {
					dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
				}
			}
		}
		return dp[0][N - 1];
	}

	// 本题第二问，返回其中一种结果
	public static String minInsertionsOneWay(String s) {
		if (s == null || s.length() < 2) {
			return s;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		int[][] dp = new int[N][N];
		for (int i = 0; i < N - 1; i++) {
			dp[i][i + 1] = str[i] == str[i + 1] ? 0 : 1;
		}
		for (int i = N - 3; i >= 0; i--) {
			for (int j = i + 2; j < N; j++) {
				dp[i][j] = Math.min(dp[i][j - 1], dp[i + 1][j]) + 1;
				if (str[i] == str[j]) {
					dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
				}
			}
		}
		//TODO 从dp表的最左上角 进行回溯
		int L = 0;
		int R = N - 1;
		//TODO 原始字符串的长度+至少添加几个char
		char[] ans = new char[N + dp[L][R]];
		//TODO 2个指针  因为答案是从0,n-1 再 1,n-2 再 2,n-3。。。。。
		int ansl = 0;
		int ansr = ans.length - 1;
		while (L < R) {//如果L>R说明 前一个循环 str[L]=str[R] 并且L=R-1 ans填写完毕 L++ R——
			if (dp[L][R - 1] == dp[L][R] - 1) {
				ans[ansl++] = str[R];
				ans[ansr--] = str[R--];
			} else if (dp[L + 1][R] == dp[L][R] - 1) {
				ans[ansl++] = str[L];
				ans[ansr--] = str[L++];
			} else {
				ans[ansl++] = str[L++];
				ans[ansr--] = str[R--];
			}
		}
		if (L == R) {//TODO 说明还有个char要塞入ans
			ans[ansl] = str[L];
		}
		return String.valueOf(ans);
	}

	// 本题第三问，返回所有可能的结果
	public static List<String> minInsertionsAllWays(String s) {
		List<String> ans = new ArrayList<>();
		if (s == null || s.length() < 2) {
			ans.add(s);
		} else {
			char[] str = s.toCharArray();
			int N = str.length;
			int[][] dp = new int[N][N];
			for (int i = 0; i < N - 1; i++) {
				dp[i][i + 1] = str[i] == str[i + 1] ? 0 : 1;
			}
			for (int i = N - 3; i >= 0; i--) {
				for (int j = i + 2; j < N; j++) {
					dp[i][j] = Math.min(dp[i][j - 1], dp[i + 1][j]) + 1;
					if (str[i] == str[j]) {
						dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
					}
				}
			}
			int M = N + dp[0][N - 1];
			char[] path = new char[M];
			process(str, dp, 0, N - 1, path, 0, M - 1, ans);
		}
		return ans;
	}

	// TODO 当前来到的动态规划中的格子，(L,R) 不用关心 0~L-1 R+1~N-1位置已经解决掉了
	//  要填写一个path，pl~pr中间的字符 其他位置不用关心 也就是类似于 ....  [pl....pr] ....
	public static void process(char[] str, int[][] dp, int L, int R, char[] path, int pl, int pr, List<String> ans) {
		//base case
		if (L >= R) { // L > R的话 说明 ans生成了
			if (L == R) {// L==R的话  说明还有个char要塞入ans
				path[pl] = str[L];
			}
			ans.add(String.valueOf(path));
		} else {
			if (dp[L][R - 1] == dp[L][R] - 1) {//如果答案来自于左侧 可能性1
				//填写好两边
				path[pl] = str[R];
				path[pr] = str[R];
				//中间的char 利用递归
				process(str, dp, L, R - 1, path, pl + 1, pr - 1, ans);
			}
			if (dp[L + 1][R] == dp[L][R] - 1) {//如果答案来自于下方 可能性2
				//填写好两边
				path[pl] = str[L];
				path[pr] = str[L];
				//中间的char 利用递归
				process(str, dp, L + 1, R, path, pl + 1, pr - 1, ans);
			}
			/*
			* 可能性3
			* L == R - 1 的情况比较特殊 因为L和R位置之间没有其他字符了 不能用dp了
			 * */
			if (str[L] == str[R] && (L == R - 1 || dp[L + 1][R - 1] == dp[L][R])) {
				path[pl] = str[L];
				path[pr] = str[R];
				process(str, dp, L + 1, R - 1, path, pl + 1, pr - 1, ans);
			}
		}
	}

	public static void main(String[] args) {
		String s = null;
		String ans2 = null;
		List<String> ans3 = null;

		System.out.println("本题第二问，返回其中一种结果测试开始");
		s = "mbadm";
		ans2 = minInsertionsOneWay(s);
		System.out.println(ans2);

		s = "leetcode";
		ans2 = minInsertionsOneWay(s);
		System.out.println(ans2);

		s = "aabaa";
		ans2 = minInsertionsOneWay(s);
		System.out.println(ans2);
		System.out.println("本题第二问，返回其中一种结果测试结束");

		System.out.println();

		System.out.println("本题第三问，返回所有可能的结果测试开始");
		s = "mbadm";
		ans3 = minInsertionsAllWays(s);
		for (String way : ans3) {
			System.out.println(way);
		}
		System.out.println();

		s = "leetcode";
		ans3 = minInsertionsAllWays(s);
		for (String way : ans3) {
			System.out.println(way);
		}
		System.out.println();

		s = "aabaa";
		ans3 = minInsertionsAllWays(s);
		for (String way : ans3) {
			System.out.println(way);
		}
		System.out.println();
		System.out.println("本题第三问，返回所有可能的结果测试结束");
	}

}
