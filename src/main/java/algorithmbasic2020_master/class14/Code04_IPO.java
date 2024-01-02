package algorithmbasic2020_master.class14;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Code04_IPO {
	/*
	* 输入:正数数组costs、正数数组profits、正数K、正数M
	* costs[i]表示i号项目的花费
	* profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润)K表示你只能串行的最多做k个项目
	* M表示你初始的资金
	* 说明:每做完一个项目，马上获得的收益，可以支持你去做下一个项目。不能并行的做项目。输出:你最后获得的最大钱数。
	* */
	/*
	 * TODO  最多K个项目
	 *   W是初始资金
	 *   Profits[] Capital[] 一定等长  项目i的 的花费（成本）和利润在Profits[i] Capital[i]
	 *   返回最终最大的资金
	*/
	public static int findMaximizedCapital(int K, int W, int[] Profits, int[] Capital) {

		//TODO  大根堆和小根堆的创建
		PriorityQueue<Program> minCostQ = new PriorityQueue<>(new MinCostComparator());
		PriorityQueue<Program> maxProfitQ = new PriorityQueue<>(new MaxProfitComparator());

		//利用循环 一开始把所有的项目 都放入小根堆里面  这个堆 针对花费的 不针对利润
		for (int i = 0; i < Profits.length; i++) {
			minCostQ.add(new Program(Profits[i], Capital[i]));
		}
		for (int i = 0; i < K; i++) {//最多做K-1轮
			/*
			*TODO 只要小根堆里面不为空 并且小根堆里面的项目花费是小于手里的初始资金的
			* 就把满足条件的项目放入大根堆里面（针对利润，针对花费）
			* */
			while (!minCostQ.isEmpty() && minCostQ.peek().c <= W) {
				maxProfitQ.add(minCostQ.poll());
			}
			//TODO 表明 现有的初始资金 没有大于小根堆里面的项目 导致没有项目可做
			if (maxProfitQ.isEmpty()) {
				return W;
			}
			//TODO 做大根堆堆顶的项目 因为针对利润的大根堆 堆顶利润最大
			W += maxProfitQ.poll().p;//执行完 最下一轮的项目
		}
		return W;
	}

	public static class Program {
		public int p;
		public int c;

		public Program(int p, int c) {
			this.p = p;
			this.c = c;
		}
	}
	/*用来模拟一个小根堆 针对花费的  是一个比较器*/
	public static class MinCostComparator implements Comparator<Program> {

		@Override
		public int compare(Program o1, Program o2) {
			return o1.c - o2.c;
		}

	}
	/*用来模拟一个大根堆 针对利润的 是一个比较器*/
	public static class MaxProfitComparator implements Comparator<Program> {

		@Override
		public int compare(Program o1, Program o2) {
			return o2.p - o1.p;
		}

	}

}
