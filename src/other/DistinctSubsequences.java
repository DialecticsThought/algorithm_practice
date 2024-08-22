package other;

public class DistinctSubsequences {
    public static int numDistinct1(String S,String T) {
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        return process(s, t, s.length,t.length);
    }
    public static int process(char[] s, char[] t, int i, int j) {
        if (j == 0) {
            return 1;
        }
        if (i == 0) {
            return 0;
        }
        int res = process(s, t, i - 1,j);
        if (s[i - 1] == t[j - 1]) {
            res += process(s, t, i - 1,j - 1);}
        return res;
    }

    public static int numDistinct2(String S, String T) {
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        int[][]dp = new int[s.length + 1][t.length + 1];
        for (int j = 0; j <= t.length; j++) {
            dp[0][j] = 0;
        }
        for (int i = 0; i <= s.length; i++) {
            dp[i][0] = 1;
        }
        /*
        * dp[i][j]含义：
        * s长度为i的前缀字符串S[0~i-1]里 其中有多少子序列等于T取长度j[0~j-1]的字符串
        * */
        for (int i = 1; i <= s.length; i++) {
            for (int j = 1; j <= t.length; j++) {
                /*
                * 不以S[i]结尾的子序列 有dp[i][j]转化为dp[i-1][j]
                * 以S[i]结尾的子序列 有dp[i][j]转化为dp[i-1][j-1]
                * */
                dp[i][j]= dp[i - 1][j] + (s[i - 1] == t[j- 1] ? dp[i - 1][j- 1] : 0);
            }
        }
        return dp[s.length][t.length];
    }
}
