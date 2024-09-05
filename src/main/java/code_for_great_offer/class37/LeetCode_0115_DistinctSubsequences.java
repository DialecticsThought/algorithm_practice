package code_for_great_offer.class37;

/*
 * https://leetcode.cn/problems/distinct-subsequences/
 * 给定两个字符串S和T，返回S子序列等于T的不同子序列个数有多少个?
 * 如果得到子序列A删除的位置与得到子序列B删除的位置不同，那么认为A和B就是不同的。
 * 【例子】
 * s = "rabbbit",T = "rabbit"返回:3
 * 是以下三个S的不同子序列，没有^的位置表示删除的位置，因为删除的位置不同，所以这三个子序列是不一样的
 * rabbbit
 * ^^^^ ^^
 * rabbbit
 * ^^ ^^^^
 * rabbbit
 * ^^^ ^^^
 * */
public class LeetCode_0115_DistinctSubsequences {

    public static int numDistinct1(String S, String T) {
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        return process(s, t, s.length, t.length);
    }

    public static int process(char[] s, char[] t, int i, int j) {
        if (j == 0) {
            return 1;
        }
        if (i == 0) {
            return 0;
        }
        int res = process(s, t, i - 1, j);
        if (s[i - 1] == t[j - 1]) {
            res += process(s, t, i - 1, j - 1);
        }
        return res;
    }

    public static int numDistinct2(String S, String T) {
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        int[][] dp = new int[s.length + 1][t.length + 1];
        for (int j = 0; j <= t.length; j++) {
            dp[0][j] = 0;
        }
        for (int i = 0; i <= s.length; i++) {
            dp[i][0] = 1;
        }
        /*
         *TODO
         * dp[i][j]含义：
         * s长度为i的前缀字符串S[0~i-1]里 其中有多少子序列等于T取长度j[0~j-1]的字符串
         * s="1 2 3 4 3 ...."
         * t="1 2 3...."
         * dp[4][2]表示 s的0~4范围的子串的子序列 有多少是等于 123 的（就是t的0~2）
         *
         * */
        for (int i = 1; i <= s.length; i++) {
            for (int j = 1; j <= t.length; j++) {
                /*
                 *TODO
                 * 不以S[i]结尾的子序列 有dp[i][j]转化为dp[i-1][j]
                 * 以S[i]结尾的子序列 有dp[i][j]转化为dp[i-1][j-1] 这个情况必须s[i]=t[j] 2个char相同 转成dp[i-1][j-1]
                 * dp[i][j]=dp[i-1][j]+dp[i-1][j-1]
                 * */
                dp[i][j] = dp[i - 1][j] + (s[i - 1] == t[j - 1] ? dp[i - 1][j - 1] : 0);
            }
        }
        return dp[s.length][t.length];
    }

    public static int numDistinct3(String S, String T) {
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        int[] dp = new int[t.length + 1];
        dp[0] = 1;
        for (int j = 1; j <= t.length; j++) {
            dp[j] = 0;
        }
        for (int i = 1; i <= s.length; i++) {
            for (int j = t.length; j >= 1; j--) {
                dp[j] += s[i - 1] == t[j - 1] ? dp[j - 1] : 0;
            }
        }
        return dp[t.length];
    }

}
