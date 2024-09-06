package heima_data_structure.java.src.main.java.com.itheima.algorithm.dynamicprogramming;

import java.util.Arrays;

import static heima_data_structure.java.src.main.java.com.itheima.algorithm.dynamicprogramming.LeetCode_518_ChangeMakingProblem.print;

/**
 * <h3>整数拆分 - 动态规划</h3>
 */
public class Code_343 {

    /*
         0   1   2   3   4
     1   1   1   11  111 1111
     2   1   1   11  111 1111
                 2   21  211
                         22
             (1) (2) (2) (4)
     3   1   1   11  111 1111
                 2   21  211
                     3   22
                         31
             (1) (2) (3) (4)
     4   1   1   11  111 1111
                 2   21  211
                     3   22
                         31
                         4
             (1) (2) (3) (4)
     */

    public int integerBreak(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, 1);
        dp[0] = 1;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (j >= i) {
                    dp[j] = Integer.max(dp[j], i * dp[j - i]);
                }
            }
            System.out.println(Arrays.toString(dp));
        }
        return dp[n];
    }

    public int integerBreak2(int n) {
        int[][] dp = new int[n][n + 1];
        Arrays.fill(dp[0], 1);
        for (int i = 1; i < n; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n + 1; j++) {
                if (j >= i) {
                    dp[i][j] = Integer.max(dp[i - 1][j], i * dp[i][j - i]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
            print(dp);
        }
        return dp[n - 1][n];
    }

    public static void main(String[] args) {
        Code_343 code = new Code_343();
        System.out.println(code.integerBreak(4));
        System.out.println("=================================");
        System.out.println(code.integerBreak(10));
    }
}
