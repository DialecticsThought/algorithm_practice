package other.mid.class04;
/*
* 添加最少字符使字符串整体都是回文字符串
* 给定一个字符串 str，如果可以在 str 的任意位置添加字符，请返回在添加字符最少的情况下，
* 让 str整体都是回文字符串的一种结果。
* 【举例】
str="ABA"。str本身就是回文串，不需要添加字符，所以返回"ABA"。
str="AB"。可以在'A'之前添加'B'，使 str整体都是回文串，故可以返回"BAB"。
* 也可以在'B’之后添加'A'，使 str 整体都是回文串，故也可以返回"ABA""。
* 总之，只要添加的字符数最少，返回其中一种结果即可。
* 进阶问题
* 给定一个字符串 str，再给定str 的最长回文子序列字符串 strlps，请返回在添加字符最少的情况下，
* 让 str 整体都是回文字符串的一种结果。进阶问题比原问题多了一个参数，请做到时间复杂度比原问题的实现低。
* */
public class Code05_PalindromeString {

	public static String getPalindrome1(String str) {
		if (str == null || str.length() < 2) {
			return str;
		}
		char[] chas = str.toCharArray();
		int[][] dp = getDP(chas);
		char[] res = new char[chas.length + dp[0][chas.length - 1]];
		int i = 0;
		int j = chas.length - 1;
		int resl = 0;
		int resr = res.length - 1;
		while (i <= j) {
			if (chas[i] == chas[j]) {
				res[resl++] = chas[i++];
				res[resr--] = chas[j--];
			} else if (dp[i][j - 1] < dp[i + 1][j]) {
				res[resl++] = chas[j];
				res[resr--] = chas[j--];
			} else {
				res[resl++] = chas[i];
				res[resr--] = chas[i++];
			}
		}
		return String.valueOf(res);
	}

	public static int[][] getDP(char[] str) {
		int[][] dp = new int[str.length][str.length];
		for (int j = 1; j < str.length; j++) {
			dp[j - 1][j] = str[j - 1] == str[j] ? 0 : 1;
			for (int i = j - 2; i > -1; i--) {
				if (str[i] == str[j]) {
					dp[i][j] = dp[i + 1][j - 1];
				} else {
					dp[i][j] = Math.min(dp[i + 1][j], dp[i][j - 1]) + 1;
				}
			}
		}
		return dp;
	}

	public static String getPalindrome2(String str, String strlps) {
		if (str == null || str.equals("")) {
			return "";
		}
		char[] chas = str.toCharArray();
		char[] lps = strlps.toCharArray();
		char[] res = new char[2 * chas.length - lps.length];
		int chasl = 0;
		int chasr = chas.length - 1;
		int lpsl = 0;
		int lpsr = lps.length - 1;
		int resl = 0;
		int resr = res.length - 1;
		int tmpl = 0;
		int tmpr = 0;
		while (lpsl <= lpsr) {
			tmpl = chasl;
			tmpr = chasr;
			while (chas[chasl] != lps[lpsl]) {
				chasl++;
			}
			while (chas[chasr] != lps[lpsr]) {
				chasr--;
			}
			set(res, resl, resr, chas, tmpl, chasl, chasr, tmpr);
			resl += chasl - tmpl + tmpr - chasr;
			resr -= chasl - tmpl + tmpr - chasr;
			res[resl++] = chas[chasl++];
			res[resr--] = chas[chasr--];
			lpsl++;
			lpsr--;
		}
		return String.valueOf(res);
	}

	public static void set(char[] res, int resl, int resr, char[] chas, int ls,
			int le, int rs, int re) {
		for (int i = ls; i < le; i++) {
			res[resl++] = chas[i];
			res[resr--] = chas[i];
		}
		for (int i = re; i > rs; i--) {
			res[resl++] = chas[i];
			res[resr--] = chas[i];
		}
	}

	public static void main(String[] args) {
		String str = "AB1CD2EFG3H43IJK2L1MN";
		System.out.println(getPalindrome1(str));

		String strlps = "1234321";
		System.out.println(getPalindrome2(str, strlps));

	}

}
