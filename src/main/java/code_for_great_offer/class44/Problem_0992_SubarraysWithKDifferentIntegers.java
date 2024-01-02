package code_for_great_offer.class44;

import java.util.HashMap;

/*
*TODO
* 给定一个正整数数组 nums和一个整数 k，返回 num中 「好子数组」的数目。
* 如果 nums的某个子数组中不同整数的个数恰好为 k，则称 nums的这个连续、不一定不同的子数组为 「好子数组 」。
* 例如，[1,2,3,1,2] 中有3个不同的整数：1，2，以及3。
* 子数组 是数组的 连续 部分。
* 示例 1：
* 输入：nums = [1,2,1,2,3], k = 2
* 输出：7
* 解释：恰好由 2 个不同整数组成的子数组：[1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2].
* 示例 2：
* 输入：nums = [1,2,1,3,4], k = 3
* 输出：3
* 解释：恰好由 3 个不同整数组成的子数组：[1,2,1,3], [2,1,3], [1,3,4].
* 链接：https://leetcode.cn/problems/subarrays-with-k-different-integers
* */
public class Problem_0992_SubarraysWithKDifferentIntegers {
	/*
	*TODO
	* 以i位置结尾的情况下 子数组有几个？
	* eg:  Y  X  end  有3个位置下标
	* 实现目标
	* x~end凑齐k-1种  y~end凑齐k种  这有2个窗口
	* 如果x-1~end就会凑齐k种 说明x是临界点
	* 如归y~end-1就会凑齐k-1种 说明y是临界点
	* 以i位置结尾的情况下 子数组个数可以记下来
	* 现在开始 这2个窗口同时向右扩，向右扩的时候，y和x都会向右移动
	* 因为要维持 x~end凑齐k-1种  y~end凑齐k种
	* eg: k=3 [3 1 1 4 1 2]
	* 来到1位置 凑齐 2种数的窗口 有1个 左边界是0 右边界是1
	* 	凑齐3种的窗口不存在  以1位置结尾的答案是0
	* 来到2位置 凑齐 2种数的窗口 有1个 左边界是0 右边界是2
	* 	凑齐3种的窗口不存在  以2位置结尾的答案是0
	* 来到3位置 凑齐 2种数的窗口 有1个 左边界是1 右边界是3
	* 	左边界右扩的原因是 不扩的话会有3种数 凑齐3种的窗口有1个 左边界是0 右边界是3 以3位置结尾的答案是1
	* 来到4位置 凑齐 2种数的窗口 有1个 左边界是1 右边界是4
	* 	凑齐3种的窗口有1个 左边界是0 右边界是4 以4位置结尾的答案是1
	* 来到5位置 凑齐 2种数的窗口 有1个 左边界是4 右边界是5
	* 	左边界右扩直到 窗口内部 只有2种数 凑齐3种的窗口有1个 左边界是1 右边界是5  以5位置结尾的答案是3
	* 	分别是 [1 1 4 1 2] [1 4 1 2] [4 1 2] 也就是当前k-1种窗口的左边界和k中窗口的左边界的差
	* 可以外用hashmap做词频表 这里没有用
	* */
	// nums 数组，题目规定，nums中的数字，不会超过nums的长度
	// [ ]长度为5，0~5
	public static int subarraysWithKDistinct1(int[] nums, int k) {
		int n = nums.length;
		//TODO k-1种数的窗口词频统计
		int[] lessCounts = new int[n + 1];
		//TODO k种数的窗口词频统计
		int[] equalCounts = new int[n + 1];
		int lessLeft = 0;//TODO k-1种数窗口的左边界
		int equalLeft = 0;//TODO k种数窗口的左边界
		int lessKinds = 0;//TODO k-1种数窗口中所含元素的真实种类数量
		int equalKinds = 0;//TODO k种数窗口中所含元素的真实种类数量
		int ans = 0;
		for (int r = 0; r < n; r++) {
			//TODO 当前刚来到r位置！ k-1种数窗口的r位置是不是0词频 说明是新的数
			if (lessCounts[nums[r]] == 0) {
				lessKinds++;//TODO k-1种数窗口中所含元素的真实种类数量++
			}
			//TODO 当前刚来到r位置！ k种数窗口的r位置是不是0词频 说明是新的数
			if (equalCounts[nums[r]] == 0) {
				equalKinds++;//TODO k种数窗口中所含元素的真实种类数量++
			}
			lessCounts[nums[r]]++;//TODO k-1种数窗口的r位置对应的词频++
			equalCounts[nums[r]]++;//TODO k种数窗口的r位置对应的词频++
			while (lessKinds == k) {//TODO 超了一个数
				if (lessCounts[nums[lessLeft]] == 1) {
					lessKinds--;
				}
				//TODO k-1种数窗口的左边界右移 直到k-1种数窗口里面只有k-1种数 或者说 词频表里面只有k-1种
				lessCounts[nums[lessLeft++]]--;
			}
			while (equalKinds > k) {//TODO 超了一个数
				if (equalCounts[nums[equalLeft]] == 1) {
					equalKinds--;
				}
				//TODO k种数窗口的左边界右移 直到k种数窗口里面只有k种数 或者说 词频表里面只有k种
				equalCounts[nums[equalLeft++]]--;
			}
			ans += lessLeft - equalLeft;
		}
		return ans;
	}

	public static int subarraysWithKDistinct2(int[] arr, int k) {
		return numsMostK(arr, k) - numsMostK(arr, k - 1);
	}
	/*
	* 问1：多少子数组 可以满足 子数组的元素的种类<=k种  这些子数组全部算上
	* 问2：多少子数组 可以满足 子数组的元素的种类<=k-1种
	* 问1-问2=ans
	* 求法：
	* 0位置作为左边界 之后每一个位置都是作为一个右边界 直到 这个左右边界框住元素的个数 <=k种  假设这个右边界是R  算出一共几个窗口
	* 1位置作为左边界 也就是窗口左边界右移 右边界也跟着右移
	* ....
	* [3 1 1 2 2 3 3 4] k=3  求出<=3种
	* 0位置作为左边界 右边界不断右扩 直到扩到6位置的3  不能右移了因为k=4了 所以子数组的个数是7个 0~0 0~1 0~2 ... 0~6
	* 1位置作为左边界 右边界不断右扩 直到扩到6位置的3  不能右移了因为k=4了 所以子数组的个数是6个 1~1 1~2 1~3 ... 1~6
	* .....
	* 求出<=2种
	* */
	public static int numsMostK(int[] arr, int k) {
		int i = 0, res = 0;
		HashMap<Integer, Integer> count = new HashMap<>();
		for (int j = 0; j < arr.length; ++j) {
			if (count.getOrDefault(arr[j], 0) == 0) {
				k--;
			}
			count.put(arr[j], count.getOrDefault(arr[j], 0) + 1);
			while (k < 0) {
				count.put(arr[i], count.get(arr[i]) - 1);
				if (count.get(arr[i]) == 0) {
					k++;
				}
				i++;
			}
			res += j - i + 1;
		}
		return res;
	}

}
