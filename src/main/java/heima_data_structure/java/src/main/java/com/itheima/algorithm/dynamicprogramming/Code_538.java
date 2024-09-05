package heima_data_structure.java.src.main.java.com.itheima.algorithm.dynamicprogramming;

/**
 * <h3>两个字符串的删除操作</h3>
 */
public class Code_538 {
    public static void main(String[] args) {
        Code_538 code = new Code_538();
        System.out.println(code.minDistance("leetcode", "etco")); // 结果4  8-4 + 4-4 = 4
        System.out.println(code.minDistance("eat", "sea"));       // 结果2  3-2 + 3-2 = 2
        System.out.println(code.minDistance("park", "spake"));    // 结果3  4-3 + 5-3 = 3
    }

    public int minDistance(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        char[] chars1 = text1.toCharArray();
        char[] chars2 = text2.toCharArray();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i < m + 1; i++) {
            char x = chars1[i - 1];
            for (int j = 1; j < n + 1; j++) {
                char y = chars2[j - 1];
                if (x == y) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Integer.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return m + n - 2 * dp[m][n];
    }
}
