package heima_data_structure.java.src.main.java.com.itheima.algorithm.dynamicprogramming;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * <h3>零钱兑换 - 动态规划</h3>
 * <p>凑成总金额的凑法中，需要硬币最少个数是几？</p>
 */
public class LeetCode_322_ChangeMakingProblem {
    /**
     * <pre>
     * 面值    0        1        2        3        4        5
     *  1    0        1        11       111      1111     11111
     *  2    0        1        2        21       22       221
     *  5    0        1        2        21       22       5
     *
     * 面值    0        1        2        3        4        5
     *  10    0        max      max      max      max      max
     *
     * 总金额❤  - 类比为背包容量
     * 硬币面值  - 类比为物品重量
     * 硬币个数  - 类比为物品价值，固定为1 （求价值(个数)最小的）
     * if(装得下) {
     *      min(上次价值(个数), 剩余容量能装下的最小价值(个数)+1)
     *      dp[i][j] = min(dp[i-1][j], dp[i][j-item.weight] + 1)
     * } else {
     *      保留上次价值(个数)不变
     *      dp[i][j] = dp[i-1][j]
     * }
     * </pre>
     */
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        // 0 max max max max max
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        System.out.println(Arrays.toString(dp));
        for (int coin : coins) {
            for (int j = coin; j < amount + 1; j++) {
                dp[j] = Math.min(dp[j], 1 + dp[j - coin]);
            }
            System.out.println(Arrays.toString(dp));
        }
        int r = dp[amount];
        return r > amount ? -1 : r;
    }

    public int recursion(int[] coins, int currentIndex, int amount) {
        // base case 1 如果金额刚好为 0，则表示成功凑出，不需要更多硬币
        // 表示此路径不可能凑成目标金额，因此返回 0
        // TODO  返回 1，这会错误地表示已经用了一种方法凑出金额，但实际问题要求的是最少硬币数量
        if (amount == 0) {
            return 0;
        }
        // base case 2 如果金额为负，表示这种组合不可能，返回一个不可能的大值
        if (amount < 0) {
            return Integer.MAX_VALUE;
        }
        // base case3 如果没有更多的硬币可以选择，且金额大于 0，表示不可能凑出该金额
        if (currentIndex == coins.length) {
            return Integer.MAX_VALUE;
        }
        // 选择1，不选择当前的硬币,继续下一轮选择
        int case1 = recursion(coins, currentIndex + 1, amount);
        // 选择2，选择当前的硬币,并继续下一轮选择
        int case2 = recursion(coins, currentIndex , amount - coins[currentIndex]);

        if (case2 != Integer.MAX_VALUE) {
            case2 += 1; // 表示选择了当前硬币
        }

        return Math.min(case1, case2);
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

    public static void main(String[] args) {
        LeetCode_322_ChangeMakingProblem leetcode = new LeetCode_322_ChangeMakingProblem();
        int count = leetcode.coinChange(new int[]{1, 2, 5}, 5);
//        int count = leetcode.coinChange(new int[]{25, 10, 5, 1}, 41);
//        int count = leetcode.coinChange(new int[]{2}, 3);
//        int count = leetcode.coinChange(new int[]{15, 10, 1}, 21);
        System.out.println(count);
    }
}
