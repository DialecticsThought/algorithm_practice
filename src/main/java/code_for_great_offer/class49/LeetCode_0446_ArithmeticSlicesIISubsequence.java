package code_for_great_offer.class49;

import java.util.ArrayList;
import java.util.HashMap;

public class LeetCode_0446_ArithmeticSlicesIISubsequence {
	/*
	*TODO
	* 先不考虑子序列长度一定>2 an=a1+(n-1)d
	* [1 3 5 7 9]  每当来到i位置 查看以i位置位结尾 有几个子序列
	* 每个位置 都有一个map key是等差d value是对应的等差子序列的个数
	* 来到0位置 看看该位置左边有哪些数和当前构成等差数列
	* 对于该位置 map是空
	* 来到1位置 该位置元素 与它左侧所有的数都作差
	* 	3-1=2
	* 		问 以0位置结尾的差值=2的子序列有几个
	* 		返回0, 那么就生成1个等差子序列 [1,3]
	*   	最后该位置的map种添加 <2,1> => 以1位置结尾的d=2的等差子序列有1个
	* 来到2位置 该位置元素 与它左侧所有的数都作差
	* 	5-3=2
	* 		问 以1位置结尾的差值=2的子序列有几个
	* 		返回1, 那么就生成2个等差子序列 [1,3,5] [3,5]
	*   	最后该位置的map种添加 <2,2> => 以2位置结尾的d=2的等差子序列有2个
	* 	5-1=4
	* 		问 以0位置结尾的差值=4的子序列有几个
	* 		返回0, 那么就生成1个等差子序列 [1,5]
	*   	最后该位置的map种添加 <4,1> => 以2位置结尾的d=4的等差子序列有1个
	* 来到3位置 该位置元素 与它左侧所有的数都作差
	* 	7-5=2
	* 		问 以2位置结尾的差值=2的子序列有几个
	* 		返回2, 那么就生成3个等差子序列 [1,3,5,7] [3,5,7] [5,7]
	*   	最后该位置的map种添加 <2,3> => 以2位置结尾的d=2的等差子序列有3个
	*	7-3=4
	* 		问 以1位置结尾的差值=4的子序列有几个
	* 		返回0, 那么就生成1个等差子序列 [3,7]
	*   	最后该位置的map种添加 <4,1> => 以3位置结尾的d=4的等差子序列有1个
	*	7-1=6
	* 		问 以0位置结尾的差值=6的子序列有几个
	* 		返回0, 那么就生成1个等差子序列 [1,7]
	*   	最后该位置的map种添加 <6,1> => 以3位置结尾的d=6的等差子序列有1个
	* ......
	* 最终求出所有位置的map 把长度=2的子序列删除
	* */
	// 时间复杂度是O(N^2)，最优解的时间复杂度
	public static int numberOfArithmeticSlices(int[] arr) {
		int N = arr.length;
		int ans = 0;
		ArrayList<HashMap<Integer, Integer>> maps = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			maps.add(new HashMap<>());
			// TODO....j...i（结尾）j在i前面
			for (int j = i - 1; j >= 0; j--) {
				long diff = (long) arr[i] - (long) arr[j];
				if (diff <= Integer.MIN_VALUE || diff > Integer.MAX_VALUE) {
					continue;
				}
				int dif = (int) diff;
				//TODO j位置结尾的情况下 有几个等差d=dif的子序列
				int count = maps.get(j).getOrDefault(dif, 0);
				//TODO 更新ans
				ans += count;
				// TODO i位置结尾的情况下 有几个等差d=dif的子序列
				maps.get(i).put(dif, maps.get(i).getOrDefault(dif, 0) + count + 1);
			}
		}
		return ans;
	}

}
