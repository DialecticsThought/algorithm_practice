package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

/**
 * <h3>某一天买入股票，未来任意一天卖出，只能先卖再买，最多买卖两次，求最大利润</h3>
 */
public class Leetcode_123_SharesIII {
    public static int maxProfit1(int[] prices) {
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

    public static Integer maxProfit(int k, int[] prices) {
        int n = prices.length;
        // dp数组保存已经计算的结果
        // dp[i][j][0] 表示第i天，剩余j次交易，当前不能买入的最大利润
        // dp[i][j][1] 表示第i天，剩余j次交易，当前可以买入的最大利润
        Integer[][][] dp = new Integer[n][k + 1][2];
        //Integer ans = recursion(prices, 0, true, k);
        Integer ans1 = recursionWithCache(prices, dp, 0, true, k);
        return ans1;
    }

    /**
     * <pre>
     *  TODO 解决思路
     *    从左到右的尝试模型
     *       1.从数组的最左侧（第0天）开始，每次都向右递归，依次尝试每个可能的操作，直到遍历完所有的天数
     *       2.在递归中，每个状态都依赖于之前天数的选择，因此递归结构是从左到右推进的
     *       3. 对于每一个状态（是否可以买入、剩余交易次数、当前天数），我们尝试不同的选择，类似于“尝试模型”
     *   1.关键问题分解：
     *     要解决这个问题，我们需要做决策：
     *      每天可以选择：
     *          1.买入股票
     *          2.卖出股票
     *          3.什么都不做（跳过这一天）
     *   2.递归的思路就是基于“分解问题”的原则，即：
     *     假设我们知道第 i 天以后最大化的利润是多少，如何基于第 i 天的决策来计算从第 i 天开始的最大利润
     *   3.状态设计：
     *     为了通过递归解决这个问题，我们需要跟踪一些关键状态：
     *       当前是哪一天（index）：用于标识我们当前递归到了第几天。
     *       剩余的交易次数（transactionsLeft）：表示我们还可以进行几次完整的买卖操作（最多是2次）。
     *       当前是否可以买入（canBuy）：用于表示我们当前是否可以进行“买入”操作
     *   4.递归的状态转移：
     *     每一天有两种状态：
     *       可以买入：如果可以进行买入操作，则有两个选择：
     *             买入股票：进入下一天，并设置状态为“不能买入”，因为买入后必须卖出才能再买。
     *             跳过不买：保持当前状态，继续到下一天。
     *       可以卖出：如果当前不能买入（即当前手上已经有股票），则有两个选择：
     *            卖出股票：进入下一天，交易次数减少一次，并设置状态为“可以买入”，因为卖出之后才能再次买入。
     *            跳过不卖：继续到下一天，不改变状态。
     * </pre>
     *
     * @param prices
     * @return
     */
    public static Integer recursion(int[] prices, int index, boolean canBuy, Integer transactionsLeft) {
        // base case
        // 如果没有剩余天数，或者没有剩余交易次数
        if (index == prices.length || transactionsLeft == 0) {
            return 0;
        }
        if (canBuy) {// 说明 要么 连一次交易都没有 要么上一个交易结束了
            // 选择1 选择当前的天 以当前的天的价位 买入
            // 递归到下一天（index + 1）
            // 将状态设置为不能买入(因为在当前天买了股票，接下来只能卖出）
            // 交易次数不变（transactionsLeft），因为买入本身不算一次完整的交易（完整交易是买入和卖出构成的）
            // 买入操作意味着我们从当前利润中减去当前天的股票价格 （prices[index]） 因为买股票是支出，需要扣掉这部分钱
            int buy = recursion(prices, index + 1, false, transactionsLeft) - prices[index];
            // 选择2 跳过当前天
            // 递归到下一天（index + 1）
            // 将状态设置为能买入(因为还没有买入）
            // 交易次数不变（transactionsLeft） 完整交易是买入和卖出构成的）
            int skip = recursion(prices, index + 1, true, transactionsLeft);

            return Math.max(buy, skip);
        } else {// 已经交易过了 只能选择是否卖出
            // 选择1 选择当前的天 以当前的天的价位 卖出
            int sell = recursion(prices, index + 1, true, transactionsLeft - 1) + prices[index];

            // 选择2 跳过当前天
            // 递归到下一天（index + 1）
            // 将状态设置为能买入(因为有买入过,接下来只能卖出））
            // 交易次数不变（transactionsLeft） 完整交易是买入和卖出构成的）
            int skip = recursion(prices, index + 1, false, transactionsLeft);
            return Math.max(sell, skip);
        }
    }

    /**
     * <pre>
     *   dp的第1个维度 index 的范围是 0~prices.length-1 所以 是 n
     *   dp的第2个维度 transactionsLeft 的范围是 0~transactionsLeft 所以 是 k+1  因为可以是0次交易
     *   dp的第3个维度 canBuy 的范围是 0和1 2中情况 所以 是 2
     * </pre>
     *
     * @param prices
     * @param dp
     * @param index
     * @param canBuy
     * @param transactionsLeft
     * @return
     */
    public static Integer recursionWithCache(int[] prices, Integer[][][] dp, Integer index, boolean canBuy, Integer transactionsLeft) {
        // base case
        // 如果没有剩余天数，或者没有剩余交易次数
        if (index == prices.length || transactionsLeft == 0) {
            return 0;
        }

        // 将 canBuy 转换为 0 和 1，方便用作 dp 数组的索引
        int canBuyFlag = canBuy ? 1 : 0;

        // 检查dp数组中是否已经有计算结果
        if (dp[index][transactionsLeft][canBuyFlag] != null) {
            return dp[index][transactionsLeft][canBuyFlag];
        }

        if (canBuy) {// 说明 要么 连一次交易都没有 要么上一个交易结束了
            // 选择1 选择当前的天 以当前的天的价位 买入
            // 递归到下一天（index + 1）
            // 将状态设置为不能买入(因为在当前天买了股票，接下来只能卖出）
            // 交易次数不变（transactionsLeft），因为买入本身不算一次完整的交易（完整交易是买入和卖出构成的）
            // 买入操作意味着我们从当前利润中减去当前天的股票价格 （prices[index]） 因为买股票是支出，需要扣掉这部分钱
            int buy = recursionWithCache(prices, dp, index + 1, false, transactionsLeft) - prices[index];
            // 选择2 跳过当前天
            // 递归到下一天（index + 1）
            // 将状态设置为能买入(因为还没有买入）
            // 交易次数不变（transactionsLeft） 完整交易是买入和卖出构成的）
            int skip = recursionWithCache(prices, dp, index + 1, true, transactionsLeft);
            // 存储结果
            dp[index][transactionsLeft][canBuyFlag] = Math.max(buy, skip);
        } else {// 已经交易过了 只能选择是否卖出
            // 选择1 选择当前的天 以当前的天的价位 卖出
            int sell = recursionWithCache(prices, dp, index + 1, true, transactionsLeft - 1) + prices[index];

            // 选择2 跳过当前天
            // 递归到下一天（index + 1）
            // 将状态设置为能买入(因为有买入过,接下来只能卖出））
            // 交易次数不变（transactionsLeft） 完整交易是买入和卖出构成的）
            int skip = recursionWithCache(prices, dp, index + 1, false, transactionsLeft);
            // 存储结果
            dp[index][transactionsLeft][canBuyFlag] = Math.max(sell, skip);
        }

        return dp[index][transactionsLeft][canBuyFlag];
    }

    public static void main(String[] args) {
        System.out.println(maxProfit(2, new int[]{3, 3, 5, 0, 0, 3, 1, 4}));
        System.out.println(maxProfit1(new int[]{3, 3, 5, 0, 0, 3, 1, 4}));// 6
    }
}
