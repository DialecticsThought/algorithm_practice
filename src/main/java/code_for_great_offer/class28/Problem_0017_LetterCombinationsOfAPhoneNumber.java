package code_for_great_offer.class28;

import java.util.ArrayList;
import java.util.List;

public class Problem_0017_LetterCombinationsOfAPhoneNumber {

	public static char[][] phone = {
			{ 'a', 'b', 'c' }, // 2按键    是phone的0位置
			{ 'd', 'e', 'f' }, // 3按键    是phone的1位置
			{ 'g', 'h', 'i' }, // 4按键    是phone的2位置
			{ 'j', 'k', 'l' }, // 5按键    是phone的3位置
			{ 'm', 'n', 'o' }, // 6按键	   是phone的4位置
			{ 'p', 'q', 'r', 's' }, // 7
			{ 't', 'u', 'v' },   // 8
			{ 'w', 'x', 'y', 'z' }, // 9
	};

	// "23"
	public static List<String> letterCombinations(String digits) {
		List<String> ans = new ArrayList<>();
		if (digits == null || digits.length() == 0) {
			return ans;
		}
		char[] str = digits.toCharArray();
		char[] path = new char[str.length];
		process(str, 0, path, ans);
		return ans;
	}
	/*
	*TODO
	* 当前来到 str[index]
	* 那么 str[0]~str[index]是拨过的部分 会存到 path里面 也可以理解为 之前做的决策
	* 来到str[str.len]的时候 就会把 path内容拷贝到res中
	* 假设 共拨了 “23”
	* index =0 str[index]='2' => 查出2节点的分支： 'a', 'b', 'c'
	* 走 'a' 分支 来到  index=1 str[index]='3' 节点
	 * */
	public static void process(char[] str, int index, char[] path, List<String> ans) {
		//TODO base case 来到最后一个char
		if (index == str.length) {
			//TODO 把path放入res
			ans.add(String.valueOf(path));
		} else {
			//TODO 得到 该节点的支路
			char[] cands = phone[str[index] - '2'];
			//TODO 深度优先遍历 当前节点的 分支 去下一个节点
			for (char cur : cands) {
				//TODO path的index位置 被赋值
				path[index] = cur;
				//TODO 来到 str[index+1]
				process(str, index + 1, path, ans);
			}
		}
	}

}
