package heima_data_structure.java.src.main.java.com.itheima.algorithm.dynamicprogramming;

/**
 * <h3>打家劫舍 - 动态规划</h3>
 */
public class HouseRobberLeetcode198 {

    /*

        价值   0   1   2   3   4
              0   0   0   0   0
        0(7)  7   0   0   0   0
        1(2)  7   7   0   0   0
        2(9)  2   7   11  0   0
        3(3)  2   7   11  11  0
        4(1)  2   7   11  11  12

        dp[4] = dp[2]+1 = 12
        dp[3] = max(dp[1]+3, dp[2]) = max(10, 11) = 11

        dp[i] = max(dp[i-2]+value, dp[i-1])
     */

    public int rob(int[] houses) {
        int len = houses.length;
        if (len == 1) {
            return houses[0];
        }
        int[] dp = new int[len];
        dp[0] = houses[0];
        dp[1] = Integer.max(houses[1], houses[0]);
        for (int i = 2; i < len; i++) {
            dp[i] = Integer.max(dp[i - 2] + houses[i], dp[i - 1]);
        }
        return dp[len - 1];
    }

    public static void main(String[] args) {
        HouseRobberLeetcode198 code = new HouseRobberLeetcode198();
        System.out.println(code.rob(new int[]{2, 7, 9, 3, 1}));
        System.out.println(code.rob(new int[]{2, 1, 1, 2}));
    }
}
