package code_for_great_offer.class35;

import java.util.ArrayList;
import java.util.TreeMap;
/*
*TODO
* 给定一个未排序的整数数组nums，返回最长递增子序列的个数。
* 注意这个数列必须是 严格 递增的。
* 示例 1:
* 输入: [1,3,5,4,7]
* 输出: 2
* 解释: 有两个最长递增子序列，分别是 [1, 3, 4, 7] 和[1, 3, 5, 7]。
* 示例 2:
* 输入: [2,2,2,2,2]
* 输出: 5
* 解释: 最长递增子序列的长度是1，并且存在5个子序列的长度为1，因此输
 * 链接：https://leetcode.cn/problems/number-of-longest-increasing-subsequence
* */
public class LeetCode_0673_NumberOfLongestIncreasingSubsequence {
	/*
	*TODO
	* [3,1,4,3]
	* 对于0位置的3 是否能以0位置结尾生成多少长度的递增子序列？
	* 有一张表 记录 长度=1的子序列的结尾的元素
	* <3,1>  => key是 0位置的元素 value是生成子序列的个数
	* 对于1位置的1 能以1位置结尾生成多少长度的递增子序列？
	* 是否能以1位置结尾生成长度=2的递增子序列？
	* 不能 因为 查记录 长度=1的子序列的结尾的 表 发现 3 > 1(当前元素) ☆☆☆☆☆☆☆☆☆☆
	* 只能以1位置结尾生成长度为1的递增子序列
	* 也就是  <3,1>  <1,1>
	* 对于2位置的4 能以2位置结尾生成多少长度的递增子序列？
	* 是否以1位置结尾生成长度=2的递增子序列？
	* 能 因为 查记录 长度=1的子序列的结尾的 表 发现 3 < 4(当前元素) ☆☆☆☆☆☆☆☆☆☆
	*  因为[1,4] [3,4] 2个子序列
	* 再生成有一张表 记录 长度=2的子序列的结尾
	* <4,2>
	* 这个是依赖于 1位置的递增子序列长度
	* 对于3位置的3 能以3位置结尾生成多少长度的递增子序列？
	* 是否以1位置结尾生成长度=3的递增子序列？
	* 不能 因为 查记录 长度=1的子序列的结尾的 表 发现 4 > 3(当前元素) ☆☆☆☆☆☆☆☆☆☆
	* 只能以3位置结尾生成长度为2的递增子序列
	* 也就是 记录 长度=2的子序列的结尾的表  <3,1>  <4,2>
	* eg2：
	* 假设 记录 长度=5的子序列的结尾的表1
	* <3,3> <5,2> <7,3> <13,2>
	* 表示 元素3 结尾的递增子序列 的长度 =5 的个数 有3个
	* 表示 元素5 结尾的递增子序列 的长度 =5 的个数 有2个
	* 表示 元素7 结尾的递增子序列 的长度 =5 的个数 有3个
	* 设 记录 长度=6的子序列的结尾的表2
	* <10,2>
	* 此时 来到一个位置 该位置的元素 = 8 求以该元素结尾 长度=7的递增子序列
	* 是不可行的 因为 长度=6的子序列的结尾的元素 10 > 8
	* 所以只能求长度=6的递增子序列
	* 那么具体有多少个子序列呢
	* 就是3+2+3 因为 3<8 5<8 7<8 13>8
	* */
	// 好理解的方法，时间复杂度O(N^2)
	public static int findNumberOfLIS1(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		int n = nums.length;
		int[] lens = new int[n];
		int[] cnts = new int[n];
		lens[0] = 1;
		cnts[0] = 1;
		int maxLen = 1;
		int allCnt = 1;
		for (int i = 1; i < n; i++) {
			int preLen = 0;
			int preCnt = 1;
			for (int j = 0; j < i; j++) {
				if (nums[j] >= nums[i] || preLen > lens[j]) {
					continue;
				}
				if (preLen < lens[j]) {
					preLen = lens[j];
					preCnt = cnts[j];
				} else {
					preCnt += cnts[j];
				}
			}
			lens[i] = preLen + 1;
			cnts[i] = preCnt;
			if (maxLen < lens[i]) {
				maxLen = lens[i];
				allCnt = cnts[i];
			} else if (maxLen == lens[i]) {
				allCnt += cnts[i];
			}
		}
		return allCnt;
	}

	/*
	*TODO 不好理解
	* 优化后的最优解，时间复杂度O(N*logN)
	* 有一个元素 这个元素结尾的最长子序列 长度=6
	* 那么 这个元素 一定生成元素结尾并且长度=5的最长子序列
	* 那么 这个元素 一定生成元素结尾并且长度=4的最长子序列
	* ...
	* 那么就没有必要在长度=5的最长子序列的表中记录该元素的内容
	* 那么就没有必要在长度=4的最长子序列的表中记录该元素的内容
	* ....
	*TODO
	* 已知元素8 一定能生成长度为6的递增子序列
	* 请问 能生成几个？
	* 那么就找长度为5的递增子序列的表
	* 哪些key<8的 把那些key对应的value加起来
	* 优化点:把那些key对应的value加起来
	* eg：长度为5的递增子序列的表
	* <3,5>
	* <5,2>
	* <7,3>
	* <13,4>
	* 并且表的计算顺序 是先 13这个元素 再7这个元素 再5 最后3
	* 重新写一个表
	* <13,4> 只要结尾元素 >=13 那么就是有4个长度为5递增子序列
	* <7,7>
	* <5,9>
	* <3,14> 只要结尾元素 >=3 那么就是有14个长度为5递增子序列（总共有几个）
	* 这里求的是 >=8 的记录
	* 就是 14-4 再加上其他的长度=6的递增子序列的表的所有元素  因为 13 > 8 所以 -4
	* 假设现在有张长度为6的递增子序列的表 记录 <10 ,4> <13,8>
	* 就是 14-4+4 +4 是因为 当前长度为6的递增子序列的表 要找一个离8最近的元素（二分）对应value值
	*TODO
	* 如果之前没有长度
	* 假设 现在求 元素8 长度为1的递增子序列
	* 并且有
	* <10,3> 表示结尾元素>=10的 就有5个长度为1的递增子序列
	* <9,5> 表示结尾元素>=9的 就有3个长度为1的递增子序列
	* 那么 加上一条记录 <8,1+5>
	* */
	public static int findNumberOfLIS2(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		/*
		*TODO
		* 长度为1的表
		* 长度为2的表
		* ....
		* 长度为len-1的表
		* 用ArrayList连起来 有len个表
		* 假设
		* 长度=1的最小结尾是1 该表的其他元素不用看了
		* 长度=2的最小结尾是3 该表的其他元素不用看了
		* 长度=3的最小结尾是5 该表的其他元素不用看了
		* 长度=4的最小结尾是10 该表的其他元素不用看了
		* x=7 求长度的话
		* 直接在 1 3 5 10里面做二分 返回>=num最左的位置
		*
		*TODO
		* [9,9,8]
		* 对于 [0] 来说 建立一个长度=1的递增子序列
		* 里面添加一条记录 <9,1>表示 某个元素>=9的话 那么长度=1的递增子序列的个数 >=1
		* 对于 [1] 来说 建立一个长度=2的递增子序列 (不能)
		* 长度=1的递增子序列的表里面添加一条记录 <9,2>表示 某个元素>=9的话 那么长度=1的递增子序列的个数 >=2
		* 对于 [2] 来说 建立一个长度=2的递增子序列(不能)
		* 建立 长度=1的递增子序列的表里面添加一条记录  <8,3> 因为 9>=8
		*TODO
		* 现在有个元素8  有个长度=5的表 <3,10> <7,5>  <13,4>
		* 有个长度=6的表 <14,9>
		* 现在元素8 求长度=6的递增子序列的个数
		* 就是 10(长度=5的表的最小的key对应的value) - 4(>=8的元素对应的value) + 9（长度=6本表的第一个元素）
		* */
		ArrayList<TreeMap<Integer, Integer>> dp = new ArrayList<>();
		int len = 0;
		int cnt = 0;
		for (int num : nums) {
			// num：之前的长度，num到了长度 = len+1
			len = search(dp, num);
			// cnt : 最终要去加底下的记录，才是应该填入的value
			//如果上一个长度 是 0
			if (len == 0) {
				cnt = 1;
			} else {
				TreeMap<Integer, Integer> p = dp.get(len - 1);
				/*
				* 上一个张表的总共的值 - ceilingKey(num)对应的值
				* */
				cnt = p.firstEntry().getValue() - (p.ceilingKey(num) != null ? p.get(p.ceilingKey(num)) : 0);
			}
			if (len == dp.size()) {
				//如果当前是这张表的新的记录
				dp.add(new TreeMap<Integer, Integer>());
				dp.get(len).put(num, cnt);
			} else {
				//如果这张表有数据了，还要加上这表的第一条记录
				dp.get(len).put(num, dp.get(len).firstEntry().getValue() + cnt);
			}
		}
		return dp.get(dp.size() - 1).firstEntry().getValue();
	}

	//TODO 二分查找，返回>=num最左的位置
	public static int search(ArrayList<TreeMap<Integer, Integer>> dp, int num) {
		int l = 0, r = dp.size() - 1, m = 0;
		int ans = dp.size();
		while (l <= r) {
			m = (l + r) / 2;
			if (dp.get(m).firstKey() >= num) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

}
