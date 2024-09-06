package heima_data_structure.java.src.main.java.com.itheima.algorithm.dynamicprogramming;

import java.util.Arrays;

/**
 * <h3>爬楼梯 - 动态规划</h3>
 */
public class LeetCode_70_ClimbingStairs {

    /*
        每次都要尝试走1步或走两步
            0       1      2      3      4      5
        1   1       1
        2           1      11
                           2
        3                         111
                                  12
                                  21
        4
                                         1111
                                         112
                                         121
                                         211
                                         22
        5                                       1111
                                                1112
                                                1121
                                                1211
                                                122
                                                2111
                                                212
                                                221
     */

    public int climbStairs(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= 2; j++) {
                if (i >= j) {
                    dp[i] = dp[i] + dp[i - j];
                }
            }
            System.out.println(Arrays.toString(dp));
        }
        return dp[n];
    }

    public static void main(String[] args) {
        System.out.println(new LeetCode_70_ClimbingStairs().climbStairs(5));
    }
}
