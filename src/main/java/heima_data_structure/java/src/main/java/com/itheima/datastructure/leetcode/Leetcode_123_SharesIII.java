package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

/**
 * <h3>某一天买入股票，未来任意一天卖出，只能先卖再买，最多买卖两次，求最大利润</h3>
 */
public class Leetcode_123_SharesIII {
    static int maxProfit(int[] prices) {
        /*
            第一次买 不依赖之前状态，以当日价格买入
            第一次卖，依赖于昨天第一次买 + 当日价格

            第二次买，依赖于昨天第一次卖 - 当日价格
            第二次卖，依赖于昨天第二次买 + 当日价格
         */
        int buy1 = Integer.MIN_VALUE;
        int sell1 = 0;
        int buy2 = Integer.MIN_VALUE;
        int sell2 = 0;
        for (int price : prices) {
            buy1 = Math.max(buy1, -price);
            sell1 = Math.max(sell1, buy1 + price);

            buy2 = Math.max(buy2, sell1 - price);
            sell2 = Math.max(sell2, buy2 + price);
        }
        return sell2;
    }

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{3, 3, 5, 0, 0, 3, 1, 4})); // 6
    }
}
