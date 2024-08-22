package code_for_great_offer.class05;

/**
 * 编辑距离
 * 可以再leetcode中查看EditDistance
 */
public class Code03_EditCost {

	public static int minCost1(String s1, String s2, int ic, int dc, int rc) {
		if (s1 == null || s2 == null) {
			return 0;
		}
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		int N = str1.length + 1;
		int M = str2.length + 1;
		int[][] dp = new int[N][M];
		// dp[0][0] = 0
		for (int i = 1; i < N; i++) {
			dp[i][0] = dc * i;
		}
		for (int j = 1; j < M; j++) {
			dp[0][j] = ic * j;
		}
		for (int i = 1; i < N; i++) {
			for (int j = 1; j < M; j++) {
				dp[i][j] = dp[i - 1][j - 1] + (str1[i - 1] == str2[j - 1] ? 0 : rc);
				dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + ic);
				dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + dc);
			}
		}
		return dp[N - 1][M - 1];
	}

	/**
	 * 空间压缩版本
	 * @param str1
	 * @param str2
	 * @param ic
	 * @param dc
	 * @param rc
	 * @return
	 */
	public static int minCost2(String str1, String str2, int ic, int dc, int rc) {
		if (str1 == null || str2 == null) {
			return 0;
		}
		char[] chs1 = str1.toCharArray();
		char[] chs2 = str2.toCharArray();
		char[] longs = chs1.length >= chs2.length ? chs1 : chs2;
		char[] shorts = chs1.length < chs2.length ? chs1 : chs2;
		if (chs1.length < chs2.length) {
			int tmp = ic;
			ic = dc;
			dc = tmp;
		}
		int[] dp = new int[shorts.length + 1];
		for (int i = 1; i <= shorts.length; i++) {
			dp[i] = ic * i;
		}
		for (int i = 1; i <= longs.length; i++) {
			int pre = dp[0];
			dp[0] = dc * i;
			for (int j = 1; j <= shorts.length; j++) {
				int tmp = dp[j];
				if (longs[i - 1] == shorts[j - 1]) {
					dp[j] = pre;
				} else {
					dp[j] = pre + rc;
				}
				dp[j] = Math.min(dp[j], dp[j - 1] + ic);
				dp[j] = Math.min(dp[j], tmp + dc);
				pre = tmp;
			}
		}
		return dp[shorts.length];
	}


	/*
	*TODO
	* 首先需要说明一些操作的等价性：
	* 	在word1插入==在word2删除，比如word1=xxx，word2 =xxxy，在word1插入y跟在word2删除y是等价的；
	* 	在word1删除==在word2插入，同上；
	* 	在word1替换==在word2替换，这个很好理解，你把word1中的x替换为y跟将word2中的y替换为x是等价的。
	* 首先是暴力法，罗列三种可能的操作，取最小值返回。
	* 具体做法是定义一个函数distance(word1, word2, idx1, idx2)代表当前对比的字符为word1[idx1]和word2[idx2]，根据这俩字符是否一样有两种情况：
	* 	俩字符一样，说明不需要额外操作，直接对比下一个字符，也就是distance(word1, word2, idx1, idx2)=distance(word1, word2, idx1+1, idx2+1)
	* 	俩字符不一样，有三种操作可选，我们选择这三种操作中最终操作数较少的那个：
	* 	在word1插入一个字符word2[idx2]使得跟word2[0:idx2]匹配上（从0到idx2这一段已经转换成功），
	* 		所以下一个对比的字符就是word1[idx1]和word2[idx2+1]，也就是distance(word1, word2, idx1, idx2)=1+distance(word1, word2, idx1, idx2+1)，这里的1就是插入操作的次数；
	*	在word1删除一个字符word1[idx1]，
	* 		所以下一个对比的字符就是word1[idx1+1]和word2[idx2]，也就是distance(word1, word2, idx1, idx2)=1+distance(word1, word2, idx1+1, idx2)；
	*   在word1替换字符word1[idx1]为word2[idx2]使得跟word2[0:idx2]匹配上（从0到idx2这一段已经转换成功），
	* 	    所以下一个对比的字符就是word1[idx1+1]和word2[idx2+1]，也就是distance(word1, word2, idx1, idx2)=1+distance(word1, word2, idx1+1, idx2+1)；
	* */
	private int distance1(String word1, String word2, int idx1, int idx2) {
		if (idx1 == word1.length()) {
			return word2.length() - idx2;
		}
		if (idx2 == word2.length()) {
			return word1.length() - idx1;
		}
		int result = 0;
		if (word1.charAt(idx1) == word2.charAt(idx2)) {
			result = distance1(word1, word2, idx1 + 1, idx2 + 1);
		}
		else {
			int d1 = 1 + distance1(word1, word2, idx1 + 1, idx2);    //删除word1[idx1]
			int d2 = 1 + distance1(word1, word2, idx1, idx2 + 1);    //word1插入一个字符word2[idx2]
			int d3 = 1 + distance1(word1, word2, idx1 + 1, idx2 + 1);//word1[idx1]替换为word2[idx2]
			result = Math.min(Math.min(d1, d2), d3);
		}
		return result;
	}
	public int minDistance1(String word1, String word2) {
		return distance1(word1, word2, 0, 0);
	}

	private int distance2(String word1, String word2, int idx1, int idx2) {
		if (idx1 == word1.length()) {
			return word2.length() - idx2;
		}
		if (idx2 == word2.length()) {
			return word1.length() - idx1;
		}
		int result = 0;
		if (word1.charAt(idx1) == word2.charAt(idx2)) {
			result = distance2(word1, word2, idx1 + 1, idx2 + 1);
		}
		else {
			int d1 = 1 + distance2(word1, word2, idx1 + 1, idx2);    //删除word1[idx1]
			int d2 = 1 + distance2(word1, word2, idx1, idx2 + 1);    //word1插入一个字符word2[idx2]
			int d3 = 1 + distance2(word1, word2, idx1 + 1, idx2 + 1);//word1[idx1]替换为word2[idx2]
			result = Math.min(Math.min(d1, d2), d3);
		}
		return result;
	}
	public int minDistance2(String word1, String word2) {
		return distance2(word1, word2, 0, 0);
	}



	public static void main(String[] args) {
		String str1 = "ab12cd3";
		String str2 = "abcdf";
		System.out.println(minCost1(str1, str2, 5, 3, 2));
		System.out.println(minCost2(str1, str2, 5, 3, 2));

		str1 = "abcdf";
		str2 = "ab12cd3";
		System.out.println(minCost1(str1, str2, 3, 2, 4));
		System.out.println(minCost2(str1, str2, 3, 2, 4));

		str1 = "";
		str2 = "ab12cd3";
		System.out.println(minCost1(str1, str2, 1, 7, 5));
		System.out.println(minCost2(str1, str2, 1, 7, 5));

		str1 = "abcdf";
		str2 = "";
		System.out.println(minCost1(str1, str2, 2, 9, 8));
		System.out.println(minCost2(str1, str2, 2, 9, 8));

	}

}
