package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

/**
 * <h3>某一天买入股票，未来任意一天卖出，只能买卖一次，求最大利润</h3>
 * <p>
 * i 尝试买入，j 尝试卖出
 *
 * <ul>
 *     <li><b>遇到涨 i 不变, j++</b></li>
 *     <li><b>遇到跌 i 变 j，j++</b></li>
 * </ul>
 */
public class SharesILeetcode121 {
    static int maxProfit(int[] prices) {
        int i = 0;
        int j = 1;
        int max = 0;
        while (j < prices.length) {
            if (prices[j] - prices[i] > 0) { // 涨
                max = Math.max(max, prices[j] - prices[i]);
                j++;
            } else { // 跌
                i = j;
                j++;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{7, 1, 5, 3, 6, 4})); // 5
        System.out.println(maxProfit(new int[]{9, 3, 12, 1, 2, 3})); // 9
    }
}
