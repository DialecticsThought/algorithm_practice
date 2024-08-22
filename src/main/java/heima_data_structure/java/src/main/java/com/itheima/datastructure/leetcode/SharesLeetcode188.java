package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.util.Arrays;

/**
 * <h3>某一天买入股票，未来任意一天卖出，只能先卖再买，最多买卖K次，求最大利润</h3>
 */
public class SharesLeetcode188 {

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

    static int maxProfit(int k, int[] prices) {
        /*
            第一次买 不依赖之前状态，以当日价格买入
            第一次卖，依赖于昨天第一次买 + 当日价格

            第二次买，依赖于昨天第一次卖 - 当日价格
            第二次卖，依赖于昨天第二次买 + 当日价格

            第三次买，依赖于昨天第二次卖 - 当日价格
            第三次卖，依赖于昨天第三次买 + 当日价格

            ...

            第 k 次买，依赖于昨天第 k-1 次卖 - 当日价格
            第 k 次卖，依赖于昨天第 k 次买 + 当日价格
         */
        if (k > prices.length / 2) {
            return maxProfit(prices);
        }
        int[] buy = new int[k];
        int[] sell = new int[k];
        Arrays.fill(buy, Integer.MIN_VALUE);

        for (int price : prices) {
            buy[0] = Math.max(buy[0], -price);
            sell[0] = Math.max(sell[0], buy[0] + price);
            for (int i = 1; i < k; i++) {
                buy[i] = Math.max(buy[i], sell[i - 1] - price);
                sell[i] = Math.max(sell[i], buy[i] + price);
            }
        }
        return sell[k - 1];
    }

    public static void main(String[] args) {
        System.out.println(maxProfit(2, new int[]{3, 2, 6, 5, 0, 3})); // 7
        System.out.println(maxProfit(2, new int[]{3, 3, 5, 0, 0, 3, 1, 4})); // 6
        System.out.println(maxProfit(4, new int[]{1, 2, 0, 1, 0, 3, 1, 4, 5})); // 9
    }
}
