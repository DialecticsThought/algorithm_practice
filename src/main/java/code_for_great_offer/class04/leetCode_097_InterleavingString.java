package code_for_great_offer.class04;

// 本题测试链接 : https://leetcode.com/problems/interleaving-string/
public class leetCode_097_InterleavingString {

	public boolean isInterleave(String s1, String s2, String s3) {
		// 检查长度，如果不匹配直接返回false
		if (s1.length() + s2.length() != s3.length()) {
			return false;
		}

		return isInterleaveRecursive(s1, s2, s3, 0, 0, 0);
	}

	/**
	 * 基本案例: 如果 s1 和 s2 都是空字符串，那么只有当 s3 也是空的时候才返回 true。
	 *
	 * 递归步骤:
	 * 如果 s1 的第一个字符与 s3 的第一个字符相同，我们递归地检查 s1 的剩余部分和 s3 的剩余部分。
	 * 同样地，如果 s2 的第一个字符与 s3 的第一个字符相同，我们再递归地检查 s2 的剩余部分和 s3 的剩余部分。
	 * 任一路径返回 true，则整体结果为 true。
	 * 结束条件: 当 s3 被完全匹配完时，如果 s1 和 s2 也都匹配完，则说明可以通过交错组合形成 s3。
	 * @param s1
	 * @param s2
	 * @param s3
	 * @param i
	 * @param j
	 * @param k
	 * @return
	 */
	private boolean isInterleaveRecursive(String s1, String s2, String s3, int i, int j, int k) {
		// 如果s3已经匹配完，检查s1和s2是否也匹配完
		if (k == s3.length()) {
			return i == s1.length() && j == s2.length();
		}

		// 检查s1的当前字符是否匹配s3的当前字符
		boolean match1 = i < s1.length() && s1.charAt(i) == s3.charAt(k) &&
				isInterleaveRecursive(s1, s2, s3, i + 1, j, k + 1);

		// 检查s2的当前字符是否匹配s3的当前字符
		boolean match2 = j < s2.length() && s2.charAt(j) == s3.charAt(k) &&
				isInterleaveRecursive(s1, s2, s3, i, j + 1, k + 1);

		return match1 || match2;
	}

	public static boolean isInterleave2(String s1, String s2, String s3) {
		if (s1 == null || s2 == null || s3 == null) {
			return false;
		}
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		char[] str3 = s3.toCharArray();
		/**
		 * 如果 s3 的长度不等于 s1 和 s2 长度之和，则 s3 不可能是 s1 和 s2 交错组成的，直接返回 false。
		 */
		if (str3.length != str1.length + str2.length) {
			return false;
		}
		/**
		 * 初始化一个 (str1.length + 1) x (str2.length + 1) 的布尔数组 dp，
		 * 所有值默认为 false。dp[0][0] 被设置为 true，
		 * 表示当两个字符串都为空时，它们能够组成空字符串。
		 */
		boolean[][] dp = new boolean[str1.length + 1][str2.length + 1];
		dp[0][0] = true;
		//这个循环检查 s1 是否可以单独形成 s3 的开始部分。如果字符匹配，则设置对应的 dp[i][0] 为 true。
		for (int i = 1; i <= str1.length; i++) {
			if (str1[i - 1] != str3[i - 1]) {
				break;
			}
			dp[i][0] = true;
		}
		//类似地，这个循环检查 s2 是否可以单独形成 s3 的开始部分。
		for (int j = 1; j <= str2.length; j++) {
			if (str2[j - 1] != str3[j - 1]) {
				break;
			}
			dp[0][j] = true;
		}
		/**
		 * 动态规划逻辑
		 * 对于 dp[i][j] 的每个元素，代码检查是否有以下两种可能的情况之一成立：
		 *
		 * str1 的当前字符与 str3 的相应字符匹配，并且之前的状态也有效:
		 *
		 * str1[i - 1] == str3[i + j - 1] && dp[i - 1][j]
		 * 这里，str1[i - 1] == str3[i + j - 1] 检查 str1 的第 i 个字符是否与 str3 的第 i + j 个字符相匹配。
		 * dp[i - 1][j] 检查在不考虑 str1 的当前字符（即只考虑 str1 的前 i - 1 个字符和 str2 的前 j 个字符）时，
		 * 是否能够组成 str3 的前 i + j - 1 个字符。
		 * str2 的当前字符与 str3 的相应字符匹配，并且之前的状态也有效:
		 *
		 * str2[j - 1] == str3[i + j - 1] && dp[i][j - 1]
		 * 类似地，str2[j - 1] == str3[i + j - 1] 检查 str2 的第 j 个字符是否与 str3 的第 i + j 个字符相匹配。
		 * dp[i][j - 1] 检查在不考虑 str2 的当前字符（即只考虑 str1 的前 i 个字符和 str2 的前 j - 1 个字符）时，
		 * 是否能够组成 str3 的前 i + j - 1 个字符。
		 * 更新 dp 数组
		 * 如果上述任何一种情况为真，则将 dp[i][j] 设置为 true，
		 * 表示 str1 的前 i 个字符和 str2 的前 j 个字符可以交错组成 str3 的前 i + j 个字符。
		 *
		 * 示例
		 * 假设 str1 = "abc", str2 = "def", str3 = "adbcef"，在检查 dp[1][1] 时：
		 *
		 * str1[0] 是 'a'，str3[1] 是 'd'，不匹配，所以第一个条件不成立。
		 * str2[0] 是 'd'，str3[1] 也是 'd'，匹配。现在我们需要检查 dp[1][0]，
		 * 即只考虑 str1 的 'a' 和 str2 为空时，是否可以形成 str3 的 'a'。
		 * 在这个例子中，dp[1][0] 是 true，所以 dp[1][1] 也被设置为 true。
		 */
		for (int i = 1; i <= str1.length; i++) {
			for (int j = 1; j <= str2.length; j++) {
				if (
						(str1[i - 1] == str3[i + j - 1] && dp[i - 1][j])
						||
						(str2[j - 1] == str3[i + j - 1] && dp[i][j - 1])
						) {
					dp[i][j] = true;
				}
			}
		}
		return dp[str1.length][str2.length];
	}

}
