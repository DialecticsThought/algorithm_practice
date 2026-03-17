package algorithmbasic2020_master.class20;

/**
 * 测试链接：https://leetcode.cn/problems/longest-palindromic-subsequence/
 */
public class LeetCode_516_PalindromeSubsequence {

    public static int lpsl1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        return f(str, 0, str.length - 1);
    }

    /**
     * 最长回文子序列
     *
     * @param arr
     * @param left
     * @param right
     * @return
     */
    public static int lps(char[] arr, int left, int right) {
        // 回文判断的边界 已经两边的指针指向相同的部分
        if (left == right) {
            return 1;
        }
        if (left > right) {
            return 0;
        }
        // 把这两个字符都选进回文子序列（它们将成为回文的左右两端）
        if (arr[left] == arr[right]) {
            //于是问题缩小到中间：
            //贡献 2
            //递归到 f(l+1, r-1)
            return 2 + lps(arr, left + 1, right + 1);
        }
        // 这两个字符不可能同时作为同一条回文的两端（回文要求两端相等），所以至少要“放弃”一个端点
        // 放弃左边的字符，看[left + 1, right]
        int case1 = lps(arr, left + 1, right);
        // 放弃右边的字符，看[left , right-1]
        int case2 = lps(arr, left, right - 1);

        return Math.max(case1, case2);
    }


    //TODO str[L..R]最长回文子序列长度返回
    public static int f(char[] str, int L, int R) {
        //TODO base case
        if (L == R) {
            return 1;
        }
        if (L == R - 1) {//TODO 判断2字符是否相同 不相同 返回2 相同是1
            return str[L] == str[R] ? 2 : 1;
        }
        int p1 = f(str, L + 1, R - 1);//TODO 不以L开通 不以R结尾
        int p2 = f(str, L, R - 1);//TODO 以L开通 不以R结尾
        int p3 = f(str, L + 1, R);
        //TODO 第四种情况 必须确保str[L] = str[R] 还要求L~R之间的最长回文字序列 最后+2
        int p4 = str[L] != str[R] ? 0 : (2 + f(str, L + 1, R - 1));
        return Math.max(Math.max(p1, p2), Math.max(p3, p4));
    }

    public static int lpsl2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[][] dp = new int[N][N];
        dp[N - 1][N - 1] = 1;
        //TODO 这个for循环把base case写完
        for (int i = 0; i < N - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = str[i] == str[i + 1] ? 2 : 1;
        }
        for (int L = N - 3; L >= 0; L--) {
            for (int R = L + 2; R < N; R++) {
                //TODO 先比较 某个格子的左侧格子 和 下面格子  也就是比较 可能性3和2
                dp[L][R] = Math.max(dp[L][R - 1], dp[L + 1][R]);
                //TODO 如果有可能性4 就 比较左下的格子
                if (str[L] == str[R]) {
                    dp[L][R] = Math.max(dp[L][R], 2 + dp[L + 1][R - 1]);
                }
               /* int p1 = dp[L + 1][R - 1];
                int p2 = dp[L][R - 1];
                int p3 = dp[L + 1][R];
                int p4 = str[L] != str[R] ? 0 : (2 + dp[L + 1][R - 1]);
                dp[L][R] = Math.max(Math.max(p1, p2), Math.max(p3, p4));*/
            }
        }
        return dp[0][N - 1];
    }

    public static int longestPalindromeSubseq1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        if (s.length() == 1) {
            return 1;
        }
        char[] str = s.toCharArray();
        char[] reverse = reverse(str);
        return longestCommonSubsequence(str, reverse);
    }

    public static char[] reverse(char[] str) {
        int N = str.length;
        char[] reverse = new char[str.length];
        for (int i = 0; i < str.length; i++) {
            reverse[--N] = str[i];
        }
        return reverse;
    }

    public static int longestCommonSubsequence(char[] str1, char[] str2) {
        int N = str1.length;
        int M = str2.length;
        int[][] dp = new int[N][M];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        for (int i = 1; i < N; i++) {
            dp[i][0] = str1[i] == str2[0] ? 1 : dp[i - 1][0];
        }
        for (int j = 1; j < M; j++) {
            dp[0][j] = str1[0] == str2[j] ? 1 : dp[0][j - 1];
        }
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (str1[i] == str2[j]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                }
            }
        }
        return dp[N - 1][M - 1];
    }

    public static int longestPalindromeSubseq2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        if (s.length() == 1) {
            return 1;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[][] dp = new int[N][N];
        dp[N - 1][N - 1] = 1;
        for (int i = 0; i < N - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = str[i] == str[i + 1] ? 2 : 1;
        }
        for (int i = N - 3; i >= 0; i--) {
            for (int j = i + 2; j < N; j++) {
                dp[i][j] = Math.max(dp[i][j - 1], dp[i + 1][j]);
                if (str[i] == str[j]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i + 1][j - 1] + 2);
                }
            }
        }
        return dp[0][N - 1];
    }

}
