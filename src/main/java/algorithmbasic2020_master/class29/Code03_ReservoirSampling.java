package algorithmbasic2020_master.class29;

/**
 *TODO 蓄水池算法
 * 让每个元素都被等概率地采集
 * 等概率 = 采集的次数越多 每个数字 被选到的次数趋于相同
 * 有n个数 但是n很大 不能让n个数都加载进内存  只能加载m个数
 * 只能在数据六中 没看到一个样本 就决定是否将其纳入采样 在这之前的数据无法操作，这之后的数据无法被看到
 * 如何让每个数只被访问一次的情况下，得到一个合理的采样
 * eg:
 * 有一个采样池 能存10个元素
 * 现在有个数据流 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
 * 将1~10个元素放入采样池
 * 让后序的数能被采样到
 * 用r 表示后面的数 生成一个0~r范围的随机数
 * 这个随机数 < 10的话 让这个r替换掉采样池的数(随机数就是被替换的数)
 * 对于目前而言 前10个数 1~10进入采样池的概率为1
 * 以采样池的1为例 p(1)=1
 * 党鞭力道第11个数 11 的时候 因为要生成个0~10的随机数
 * 那么采样池的1被换掉的的P = 1 /11 保留1的概率 P = 1- 1/11 = 10/11
 * 假设1没有被换掉 到遍历道第12个数12的时候 因为要生成0~10的随机数
 * 那么采样池里的1被换掉的P = 1/12 保留1的概率 P = (1- 1/11)*(1-1/12)
 * .....
 * 到了最后  保留1的概率 P = (1- 1/11)*(1-1/12)*(1-1/13)*(1-1/14)*(1-1/15)=10/15
 * 在采样池的前10个数都是 10/15
 * 对于一开始不在采样池的数字的话
 * 以1为例子，遍历到11，采样池有个数会被替换成11
 * 11初始进入采样池的概率 = 10/11 与上面的10/11含义不同
 * 遍历11到15结束 对于1而言 最终保留在采样池的概念
 * P(11) = 10/11 * 11/12 * 12/13 * 13/14 * 14/15 = 10 / 15
 * 12初始进入采样池的概率 = 11/12
 * 13初始进入采样池的概率 = 12/13
 * eg:
 * 样本数量 = n 采样池大小 = m
 * 每个元素i的概率 就可以协程概率地连乘
 * 1.连乘的第i个概率因子 就是该元素进入采样池的可能性
 * 2.后续的概率因子 就是保留在采样池不被替换的可能性
 * P(i,i∈[1,m]) = 1 * m/(m+1) * (m+1/(m+2) * (m+2/(m+3)  * ... * (n-1)/n = m/n
 * P(i,i∈[m+1,n]) = m/(m+1) * (m+1/(m+2) * (m+2/(m+3)  * ... * (n-1)/n = m/n
 */
public class Code03_ReservoirSampling {

	public static class RandomBox {
		private int[] bag;
		private int N;
		private int count;

		public RandomBox(int capacity) {
			bag = new int[capacity];
			N = capacity;
			count = 0;
		}

		private int rand(int max) {
			return (int) (Math.random() * max) + 1;
		}

		public void add(int num) {
			count++;
			if (count <= N) {
				bag[count - 1] = num;
			} else {
				if (rand(count) <= N) {
					bag[rand(N) - 1] = num;
				}
			}
		}

		public int[] choices() {
			int[] ans = new int[N];
			for (int i = 0; i < N; i++) {
				ans[i] = bag[i];
			}
			return ans;
		}

	}

	// 请等概率返回1~i中的一个数字
	public static int random(int i) {
		return (int) (Math.random() * i) + 1;
	}

	public static void main(String[] args) {
		System.out.println("hello");
		int test = 10000;
		int ballNum = 17;
		int[] count = new int[ballNum + 1];
		for (int i = 0; i < test; i++) {
			int[] bag = new int[10];
			int bagi = 0;
			for (int num = 1; num <= ballNum; num++) {
				if (num <= 10) {
					bag[bagi++] = num;
				} else { // num > 10
					if (random(num) <= 10) { // 一定要把num球入袋子
						bagi = (int) (Math.random() * 10);
						bag[bagi] = num;
					}
				}

			}
			for (int num : bag) {
				count[num]++;
			}
		}
		for (int i = 0; i <= ballNum; i++) {
			System.out.println(count[i]);
		}

		System.out.println("hello");
		int all = 100;
		int choose = 10;
		int testTimes = 50000;
		int[] counts = new int[all + 1];
		for (int i = 0; i < testTimes; i++) {
			RandomBox box = new RandomBox(choose);
			for (int num = 1; num <= all; num++) {
				box.add(num);
			}
			int[] ans = box.choices();
			for (int j = 0; j < ans.length; j++) {
				counts[ans[j]]++;
			}
		}

		for (int i = 0; i < counts.length; i++) {
			System.out.println(i + " times : " + counts[i]);
		}

	}
}
