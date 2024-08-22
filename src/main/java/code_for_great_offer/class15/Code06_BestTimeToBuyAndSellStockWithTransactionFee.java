package code_for_great_offer.class15;

//leetcode 714
/*
* 给定一个整数数组 prices，其中 prices[i]表示第 i 天的股票价格 ；整数 fee 代表了交易股票的手续费用。
 *你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。
*返回获得利润的最大值。
*注意：这里的一笔交易指买入持有并卖出股票的整个过程，每笔交易你只需要为支付一次手续费。
*示例 1：
*输入：prices = [1, 3, 2, 8, 4, 9], fee = 2
*输出：8
*解释：能够达到的最大利润:
*在此处买入 prices[0] = 1
*在此处卖出 prices[3] = 8
*在此处买入 prices[4] = 4
*在此处卖出 prices[5] = 9
*总利润: ((8 - 1) - 2) + ((9 - 4) - 2) = 8
*示例 2：
*输入：prices = [1,3,7,5,10,3], fee = 3
*输出：6
* */
public class Code06_BestTimeToBuyAndSellStockWithTransactionFee {
	/*
	* 到了i位置 0~i范围上 最后一个动作是买入的情况下 综合考虑-好的买入价格
	*  0~i范围上 最后一个动作是卖出的情况下
	 * */
	public static int maxProfit(int[] arr, int fee) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int N = arr.length;
		// 0..0   0 -[0] - fee
		int bestbuy = -arr[0] - fee;
		// 0..0  卖  0
		int bestsell = 0;
		for (int i = 1; i < N; i++) {
			// 来到i位置了！
			// 如果在i必须买  0~i范围的最好收入 - 批发价 - fee
			int curbuy = bestsell - arr[i] - fee;
			// 如果在i必须卖  整体最优（收入 - 良好批发价 - fee）
			int cursell = bestbuy + arr[i];
			//可以此时买 和 不买 这两个决策比较
			bestbuy = Math.max(bestbuy, curbuy);
			bestsell = Math.max(bestsell, cursell);
		}
		return bestsell;
	}

}
