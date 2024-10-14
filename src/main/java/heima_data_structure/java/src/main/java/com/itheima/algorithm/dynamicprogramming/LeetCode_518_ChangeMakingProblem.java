package heima_data_structure.java.src.main.java.com.itheima.algorithm.dynamicprogramming;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * <h3>零钱兑换 II - 动态规划</h3>
 * <p>凑成总金额有几种凑法？</p>
 */
public class LeetCode_518_ChangeMakingProblem {
    /*
     面值    0      1      2      3      4      5         总金额-背包容量
     1      1      1      11     111    1111   11111

     2      1      1      11     111    1111   11111
                          2      21     211    2111
                                        22     221

     5      1      1      11     111    1111   11111
                          2      21     211    2111
                                        22     221
                                               5

     if(放得下)
        dp[i][j] = dp[i-1][j] + dp[i][j-coin]
     else(放不下)
        dp[i][j] = dp[i-1][j]

     */

    public int change(int[] coins, int amount) {
        int[][] dp = new int[coins.length][amount + 1];
        for (int i = 0; i < coins.length; i++) {
            dp[i][0] = 1;
        }
        for (int j = 1; j < amount + 1; j++) {
            if (j >= coins[0]) {
                dp[0][j] = dp[0][j - coins[0]];
            }
        }
        print(dp);
        for (int i = 1; i < coins.length; i++) {
            for (int j = 1; j < amount + 1; j++) {
                if (j >= coins[i]) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - coins[i]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
            print(dp);
        }
        return dp[coins.length - 1][amount];
    }

    public int recursion(int[] coins, int currentIndex, int amount) {
        // base case 1 如果金额刚好为 0，则表示找到了一种有效的组合
        if (amount == 0) {
            return 1;
        }
        // base case 2 如果金额为负，表示这种组合不可能，返回一个不可能的大值
        if (amount < 0) {
            return 0;
        }
        // base case3 如果没有更多的硬币可以选择，且金额大于 0，表示不可能凑出该金额
        if (currentIndex == coins.length) {
            return 0;
        }
        // 选择1，不选择当前的硬币,继续下一轮选择
        int case1 = recursion(coins, currentIndex + 1, amount);
        // 选择2，选择当前的硬币,并继续下一轮选择
        int case2 = recursion(coins, currentIndex, amount - coins[currentIndex]);

        return case1 + case2;
    }

    public static void main(String[] args) {
        LeetCode_518_ChangeMakingProblem leetcode = new LeetCode_518_ChangeMakingProblem();
//        int count = leetcode.change(new int[]{1, 2, 5}, 5);
//        int count = leetcode.change(new int[]{2}, 3);
//        int count = leetcode.change(new int[]{15, 10, 1}, 21);
        int count = leetcode.change(new int[]{25, 10, 5, 1}, 41);
        System.out.println(count);
    }

    static void print(int[][] dp) {
        System.out.println("-".repeat(18));
        Object[] array = IntStream.range(0, dp[0].length + 1).boxed().toArray();
        System.out.printf(("%2d ".repeat(dp[0].length)) + "%n", array);
        for (int[] d : dp) {
            array = Arrays.stream(d).boxed().toArray();
            System.out.printf(("%2d ".repeat(d.length)) + "%n", array);
        }
    }
}
