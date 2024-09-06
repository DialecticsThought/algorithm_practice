package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

/**
 * <h3>某一天买入股票，未来任意一天卖出，只能卖了再买，但可以买卖多次，并允许同一天卖出后再买入，求最大利润</h3>
 *
 * 有利润就买卖，只看眼前
 *
 */
public class Leetcode_122_SharesII {

    static int maxProfit(int[] prices) {
        int i = 0;
        int j = 1;
        int sum = 0;
        while (j < prices.length) {
            if (prices[j] - prices[i] > 0) { // 有利润
                sum += prices[j] - prices[i];
            }
            i++;
            j++;
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{9, 3, 12, 1, 2, 3})); // 11
        System.out.println(maxProfit(new int[]{7, 1, 5, 3, 6, 4})); // 7
    }
}
