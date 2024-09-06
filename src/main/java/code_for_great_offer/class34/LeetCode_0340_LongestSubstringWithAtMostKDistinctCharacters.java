package code_for_great_offer.class34;

/*
*TODO
* 题目描述：给定一个字符串 s ，找出 至多 包含 k 个不同字符的最长子串 T。
* 示例 1：
* 输入: s = “eceba”, k = 2
* 输出: 3
* 解释: 则 T 为 “ece”，所以长度为 3。
* 示例 2:
* 输入: s = “aa”, k = 1
* 输出: 2
* 解释: 则 T 为 “aa”，所以长度为 2。
*TODO
* 因为本题具有单调性（指针向前，种类不可能减少），所以可以用滑动窗口来做
* 哈希表，key是字符，value是窗口内字符出现的次数。
* 有一个hashmap
* eg:[e c e b a]
* 从下标0开始 hashmap  <e,1>  hashmap.size==1
* 来到1位置  hashmap  <e,1>  <c,1> hashmap.size==2
* 来到2位置  hashmap  <e,2>  <c,1> hashmap.size==2
* 来到3位置  hashmap  <e,2>  <c,1> <b,1> hashmap.size==3 > 2
* 说明 从0位置开始 有一个窗口范围是0~2,3个长度
* 窗口的左边界右移一个位置 来到1位置  左边界右移一个位置 来到4位置
* 此时hashmap <e,1> <c,1> <b,1> <a,1>  hashmap.size==4 > 2
* 那么 说明 从1位置开始 有一个窗口范围是1~3,4个长度
* 窗口的左边界右移一个位置 来到2位置  左边界右移一个位置 来到4位置
* 此时hashmap  <c,1> <b,1> <a,1>  hashmap.size==3 > 2
* 那么 说明 从2位置开始 有一个窗口范围是2~4,3个长度
* ......
* 从0位置不断执行上面的操作直到最后一个位置
 * */
public class LeetCode_0340_LongestSubstringWithAtMostKDistinctCharacters {

	public static int lengthOfLongestSubstringKDistinct(String s, int k) {
		if (s == null || s.length() == 0 || k < 1) {
			return 0;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		int[] count = new int[256];
		int diff = 0;
		int R = 0;
		int ans = 0;
		for (int i = 0; i < N; i++) {
			// R 窗口的右边界
			while (R < N && (diff < k || (diff == k && count[str[R]] > 0))) {
				diff += count[str[R]] == 0 ? 1 : 0;
				//count[str[R++]]++;
				count[str[R]]++;
				R++;

			}
			// R 来到违规的第一个位置
			ans = Math.max(ans, R - i);
			diff -= count[str[i]] == 1 ? 1 : 0;
			count[str[i]]--;
		}
		return ans;
	}

}
