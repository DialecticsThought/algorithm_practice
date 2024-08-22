package heima_data_structure.java.src.main.java.com.itheima.algorithm.dynamicprogramming;

import java.util.Arrays;

/**
 * <h3>组合总数IV - 类似于零钱兑换518题 区别在于零钱兑换求的是组合数，本题求的是排列数</h3>
 */
public class CombinationLeetcode377 {
    static int combinationSum4(int[] nums, int target) {
        return change(nums, target);
    }

    /*
            0       1       2       3       4 总金额
        1           1       11      111     1111
        2           1       11      111     1111
                            2       12      112
                                    21      121
                                            22
                                            211
        3           1       11      111     1111
                            2       12      112
                                    21      121
                                    3       13
                                            211
                                            22
                                            31
        面值
        dp[j] = dp[j-1] + dp[j-2] + dp[j-3]
     */

    static int change(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int j = 1; j < amount + 1; j++) {
            for (int coin : coins) {
                if (j >= coin) {
                    dp[j] += dp[j - coin];
                }
            }
            System.out.println(Arrays.toString(dp));
        }
        return dp[amount];
    }

    public static void main(String[] args) {
        System.out.println(combinationSum4(new int[]{1, 2, 3}, 4));
    }
}
