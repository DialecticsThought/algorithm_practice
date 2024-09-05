package heima_data_structure.java.src.main.java.com.itheima.algorithm.dynamicprogramming;

import static heima_data_structure.java.src.main.java.com.itheima.algorithm.dynamicprogramming.Code_518_ChangeMakingProblem.print;

/**
 * <h3>钢条切割问题 - 动态规划</h3>
 */
public class CutRodProblem {
    /*
        0   1   2   3   4   5   6   7   8   9   10
        0   1   5   8   9   10  17  17  20  24  30

        if(放得下)
            dp[i][j]=max(dp[i-1][j],当前物品价值+dp[i][j-物品重量]
        else(放不下)
            dp[i][j]=dp[i-1][j]

            0   1   2   3   4       钢条总长度=背包容量
        1       1   11  111 1111
                (1) (2) (3) (4)
        2       1   11  111 1111
                    2   21  211
                            22
                (1) (5) (6) (10)
        3       1   11  111 1111
                    2   21  211
                        3   22
                            31
                (1) (5) (8) (10)
        4       1   11  111 1111
                    2   21  211
                        3   22
                            31
                            4
                (1) (5) (8) (10)

        物品重量
     */
    static int cut(int[] values, int n) {
        int[][] dp = new int[values.length][n + 1];
        for (int i = 1; i < values.length; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (j >= i) { // 放得下
                    dp[i][j] = Integer.max(dp[i - 1][j], values[i] + dp[i][j - i]);
                } else { // 放不下
                    dp[i][j] = dp[i - 1][j];
                }
            }
            print(dp);
        }
        return dp[values.length - 1][n];
    }

    public static void main(String[] args) {
        // 不同长度钢条的价值数组，数组的索引对应钢条的长度（物品重量）
        System.out.println(cut(new int[]{0, 1, 5, 8, 9,}, 4)); // 10, 17, 17, 20, 24, 30
    }
}
