package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

/**
 * <h3>某一天买入股票，未来任意一天卖出，可以买卖多次，卖出后只能隔天再买入，求最大利润</h3>
 */
public class SharesLeetcode309 {
    /*
        0       1           2           3           4
        1       2           3           0           2
 买     -1      -2          -3          1√          0
 等             -1√         -1√         -1          1√
 卖     0       1√          2√          -1          3√
 等             0           1           2√          2

     */
    static int maxProfit(int[] prices) {
        if (prices.length == 1) {
            return 0;
        }
        int[] buy = new int[prices.length];
        int[] sell = new int[prices.length];
        buy[0] = -prices[0];
        sell[0] = 0;
        buy[1] = Math.max(-prices[0], -prices[1]);
        sell[1] = Math.max(sell[0], buy[0] + prices[1]);
        for (int i = 2; i < prices.length; i++) {
            buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i]);
            sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i]);
        }
        return sell[prices.length - 1];
    }

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{1, 2, 3, 0, 2})); // 3
    }
}
