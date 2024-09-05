package code_for_great_offer.class38;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
*TODO
* 给定两个字符串s和 p，找到s中所有p的异位词的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
* 异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。
* 示例1:
* 输入: s = "cbaebabacd", p = "abc"
* 输出: [0,6]
* 解释:
* 起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
* 起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
* 示例 2:
* 输入: s = "abab", p = "ab"
* 输出: [0,1,2]
* 解释:
* 起始索引等于 0 的子串是 "ab", 它是 "ab" 的异位词。
* 起始索引等于 1 的子串是 "ba", 它是 "ab" 的异位词。
* 起始索引等于 2 的子串是 "ab", 它是 "ab" 的异位词。
* 链接：https://leetcode.cn/problems/find-all-anagrams-in-a-string
*TODO
* 利用滑动窗口
* p = abca
* b c c a b c b b a c c b
* 有张表 意思是 a欠2个 b欠1个 c欠1个 总共欠4个
* <a 2> <b 1> <c 1> all=4
* 初始的时候 窗口的左右边界都是在-1位置
* 右边界右移 框住了b 表记录
* <a 2> <b 0>  <c 1> all=3
* 右边界右移 框住了c 表记录
* <a 2> <b 0>  <c 0> all=2
* 右边界右移 框住了c 表记录  all不变因为 c已经变成了0
* <a 2> <b 0>  <c -1> all=2
* 右边界右移 框住了a   表记录
* <a 1> <b 0>  <c -1> all=1
* 此时生成了 从0位置开始的长度为4的窗口 但是all!=0 说明 窗口框住的str不是目标
* 左窗口右移 吐出b
* <a 1> <b 1>  <c -1> all=2
* 右边界右移 框住了b 表记录
* <a 1> <b 0>  <c -1> all=1
* 左窗口右移 吐出c
* <a 1> <b 0>  <c 0> all=1
* 右边界右移 框住了c 表记录 all不变因为 c已经变成了0
* <a 1> <b 0>  <c -1> all=1
* ......
* */
public class LeetCode_0438_FindAllAnagramsInAString {

	public static List<Integer> findAnagrams(String s, String p) {
		List<Integer> ans = new ArrayList<>();
		if (s == null || p == null || s.length() < p.length()) {
			return ans;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		char[] pst = p.toCharArray();
		int M = pst.length;
		HashMap<Character, Integer> map = new HashMap<>();
		for (char cha : pst) {
			if (!map.containsKey(cha)) {
				map.put(cha, 1);
			} else {
				map.put(cha, map.get(cha) + 1);
			}
		}
		int all = M;
		for (int end = 0; end < M - 1; end++) {
			if (map.containsKey(str[end])) {
				int count = map.get(str[end]);
				if (count > 0) {
					all--;
				}
				map.put(str[end], count - 1);
			}
		}
		for (int end = M - 1, start = 0; end < N; end++, start++) {
			if (map.containsKey(str[end])) {
				int count = map.get(str[end]);
				if (count > 0) {
					all--;
				}
				map.put(str[end], count - 1);
			}
			if (all == 0) {
				ans.add(start);
			}
			if (map.containsKey(str[start])) {
				int count = map.get(str[start]);
				if (count >= 0) {
					all++;
				}
				map.put(str[start], count + 1);
			}
		}
		return ans;
	}

}
