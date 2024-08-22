package code_for_great_offer.class32;

import java.util.Arrays;
/*
*TODO
* 快手面试题
* 给定一个数组arr，arr[i] = j，表示第i号试题的难度为j。给定一个非负数M
* 想出一张卷子，对于任何相邻的两道题目，前一题的难度不能超过后一题的难度+M
* 返回所有可能的卷子种数
*TODO
* 从左往右的尝试模型
* 先对 arr根据难度排序 从小到大
* 主函数 一定要把0~arr.len-1的所有题目使用完 返回有多少种卷子
* */
public class SequenceM {

	// 纯暴力方法，生成所有排列，一个一个验证
	public static int ways1(int[] arr, int m) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		return process(arr, 0, m);
	}
	/*
	*TODO
	* 0~i上形成了 a套合法的卷子
	* 此时来到了i+1位置 arr[i+1] = x
	* 把x加入到卷子的末尾之后 假设还是合法的 那么依然至少有a套合法的卷子
	* eg：M=1 [3,3,3] 假设来到2位置 之前已经有2套卷子了
	* 分别是 位置2 位置1  位置1 位置2
	* 那么把位置2的题目 放入最后
	* 如果 把x插到某一个题目前面的话
	* eg: x=7 M=2 那么x可以插在 >=5的前面
	* eg: 假设现在有一套 合法的卷子 当前来到i位置=7 M=2
	* 还假设 这一套卷子中 有6个题目 难度 >=5
	* 那么这个7能插在5个位子上， 那么能生成5个新的卷子
	* 那么来到下一个位置 合法的卷子中 一定 也是有6个题目 难度 >=5
	* eg：
	* 0~i方有a套合法的卷子 现在来到 i+1位置 arr[i+1]=x
	* 如果把x难度的题目加入卷子的情况下，起码有a套卷子（能插到卷子的末尾，x难度的题目作为最后一题）
	* eg:
	* x=7 M=2 当前有一套合法卷子 比如[6,4,2,7,5,3,7]
	* 因为 7的出现 还能产生多少套卷子
	* 如果7难度的题目放最后 那么下一到题目的难度 >=5
	* 除此之外 只要把x 插在一个之前>=5的难度的题目 前面 就是1套卷子
	* => [6,4,2,7,5,3,x,7] [6,4,2,7,x,5,3,7]  [x,6,4,2,7,5,3,7] [6,4,2,x,7,5,3,7]
	* 一共多了4套新的卷子
	* 这只是一套卷子的情况 如果有多套卷子的话 怎么办？
	* 起始都一样 因为 不管多少套卷子 出现的题目难度是一样的 也就是说数字的值一样 只是排列不同
	* 即使之前n套卷子 这一轮 也是多了4套新的卷子
	* 总结
	* 数组排列 相当于 有n个题目 并且 题目根据难度有小到大排序
	* 0~i有100套合法的卷子 现在来到 i+1位置 arr[i+1]=x x=7 M=2  签字说明 之前的题目的难度 必然<=7
	* 如果x难度的题目 插在卷子的最后 => 一定总共还是有100套
	* 如果0~i中有4个题目的难度>=5 ，那么x难度的题目插在折现题目的前面 就可以生成400套卷子 因为每一套能旧卷子变出4套新卷子
	* 相当于要知道 0~i范围 有多少个数 >= x-M (这里利用index树)
	* */
	public static int process(int[] arr, int index, int m) {
		if (index == arr.length) {
			for (int i = 1; i < index; i++) {
				if (arr[i - 1] > arr[i] + m) {
					return 0;
				}
			}
			return 1;
		}
		int ans = 0;
		for (int i = index; i < arr.length; i++) {
			swap(arr, index, i);
			ans += process(arr, index + 1, m);
			swap(arr, index, i);
		}
		return ans;
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// 时间复杂度O(N * logN)
	// 从左往右的动态规划 + 范围上二分
	public static int ways2(int[] arr, int m) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		Arrays.sort(arr);
		int all = 1;
		for (int i = 1; i < arr.length; i++) {
			all = all * (num(arr, i - 1, arr[i] - m) + 1);
		}
		return all;
	}

	// arr[0..r]上返回>=t的数有几个, 二分的方法
	// 找到 >=t 最左的位置a, 然后返回r - a + 1就是个数
	public static int num(int[] arr, int r, int t) {
		int i = 0;
		int j = r;
		int m = 0;
		int a = r + 1;
		while (i <= j) {
			m = (i + j) / 2;
			if (arr[m] >= t) {
				a = m;
				j = m - 1;
			} else {
				i = m + 1;
			}
		}
		return r - a + 1;
	}

	// 时间复杂度O(N * logV)
	// 从左往右的动态规划 + IndexTree
	public static int ways3(int[] arr, int m) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for (int num : arr) {
			max = Math.max(max, num);
			min = Math.min(min, num);
		}
		IndexTree itree = new IndexTree(max - min + 2);
		Arrays.sort(arr);
		int a = 0;
		int b = 0;
		int all = 1;
		itree.add(arr[0] - min + 1, 1);
		for (int i = 1; i < arr.length; i++) {
			a = arr[i] - min + 1;
			b = i - (a - m - 1 >= 1 ? itree.sum(a - m - 1) : 0);
			all = all * (b + 1);
			itree.add(a, 1);
		}
		return all;
	}
	/*public static int way4(int[]arr,int m){
		int pre = 1;
		for(int i = 1; i < arr.length;i++) {
			//情况1 当前难度的题目插在卷子的最后
			int p1 = pre;
			//int num = arr.[0...1]有多少数是arr[i] - m
			//查arr[o ..i-1]有多少数是>= arr[i] - m
			//情况2 当前难度的题目插在>=arr[i]-m难度的题目的前面
			int p2 = num * pre;
			int cur = p1 + p2;

			pre = cur;
		}
		return pre;

	}*/


	// 注意开始下标是1，不是0
	public static class IndexTree {

		private int[] tree;
		private int N;

		public IndexTree(int size) {
			N = size;
			tree = new int[N + 1];
		}

		public int sum(int index) {//TODO 求1~index的累加和
			int ret = 0;
			while (index > 0) {
				ret += tree[index];
				//TODO 剥掉最右侧的1得到新的index，下一轮累加
				index -= index & -index;
			}
			return ret;
		}
		/*
		* TODO
		*  index & -index 提取出index二进制的最右侧的1 剩下位置全变0
		*  index ：		   01100100
		*  index & -index  00000100
		*  -index 和 index的区别在于 -index是index的取反再+1 = ~index+1
		*  index & (~index+1) 就得到 index最右侧的1
		 * */
		public void add(int index, int d) {
			//TODO index位置的值+d
			while (index <= N) {
				tree[index] += d;
				index += index & -index;
			}
		}
	}

	// 为了测试
	public static int[] randomArray(int len, int value) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * (value + 1));
		}
		return arr;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 10;
		int value = 20;
		int testTimes = 1000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int len = (int) (Math.random() * (N + 1));
			int[] arr = randomArray(len, value);
			int m = (int) (Math.random() * (value + 1));
			int ans1 = ways1(arr, m);
			int ans2 = ways2(arr, m);
			int ans3 = ways3(arr, m);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("出错了!");
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
			}
		}
		System.out.println("测试结束");
	}

}
