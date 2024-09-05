package code_for_great_offer.class38;

/*
*给你一个字符串 s ，请你统计并返回这个字符串中 回文子串 的数目。
* 回文字符串 是正着读和倒过来读一样的字符串。
* 子字符串 是字符串中的由连续字符组成的一个序列。
* 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被视作不同的子串。
* 示例 1：
* 输入：s = "abc"
* 输出：3
* 解释：三个回文子串: "a", "b", "c"
* 示例 2：
* 输入：s = "aaa"
* 输出：6
* 解释：6个回文子串: "a", "a", "a", "aa", "aa", "aaa"
 * 链接：https://leetcode.cn/problems/palindromic-substrings
* */
public class LeetCode_0647_PalindromicSubstrings {

	public static int countSubstrings(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		int[] dp = getManacherDP(s);
		int ans = 0;
		for (int i = 0; i < dp.length; i++) {
			ans += dp[i] >> 1;
		}
		return ans;
	}

	public static int[] getManacherDP(String s) {
		char[] str = manacherString(s);
		int[] pArr = new int[str.length];
		int C = -1;
		int R = -1;
		for (int i = 0; i < str.length; i++) {
			pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
			while (i + pArr[i] < str.length && i - pArr[i] > -1) {
				if (str[i + pArr[i]] == str[i - pArr[i]])
					pArr[i]++;
				else {
					break;
				}
			}
			if (i + pArr[i] > R) {
				R = i + pArr[i];
				C = i;
			}
		}
		return pArr;
	}

	public static char[] manacherString(String str) {
		char[] charArr = str.toCharArray();
		char[] res = new char[str.length() * 2 + 1];
		int index = 0;
		for (int i = 0; i != res.length; i++) {
			res[i] = (i & 1) == 0 ? '#' : charArr[index++];
		}
		return res;
	}

}
