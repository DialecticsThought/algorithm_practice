package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * code_for_great_offer.class09
 * 测试链接 : https://leetcode.com/problems/remove-invalid-parentheses/
 */
public class LeetCode_301_RemoveInvalidParentheses {

	// 来自leetcode投票第一的答案，实现非常好，我们来赏析一下
	public static List<String> removeInvalidParentheses(String s) {
		List<String> ans = new ArrayList<>();
		remove(s, ans, 0, 0, new char[] { '(', ')' });
		return ans;
	}

	// modifyIndex <= checkIndex
	// 只查s[checkIndex....]的部分，因为之前的一定已经调整对了
	// 但是之前的部分是怎么调整对的，调整到了哪？就是modifyIndex
	// 比如：
	// ( ( ) ( ) ) ) ...
	// 0 1 2 3 4 5 6
	// 一开始当然checkIndex = 0，modifyIndex = 0
	// 当查到6的时候，发现不对了，
	// 然后可以去掉2位置、4位置的 )，都可以
	// 如果去掉2位置的 ), 那么下一步就是
	// ( ( ( ) ) ) ...
	// 0 1 2 3 4 5 6
	// checkIndex = 6 ，modifyIndex = 2
	// 如果去掉4位置的 ), 那么下一步就是
	// ( ( ) ( ) ) ...
	// 0 1 2 3 4 5 6
	// checkIndex = 6 ，modifyIndex = 4
	// 也就是说，
	// checkIndex和modifyIndex，分别表示查的开始 和 调的开始，之前的都不用管了 char[]类型 par 表示0位置是左括号"(" 1位置是右括号")"
	public static void remove(String s, List<String> ans, int checkIndex, int deleteIndex, char[] par) {
		for (int count = 0, i = checkIndex; i < s.length(); i++) {
			if (s.charAt(i) == par[0]) {
				count++;
			}
			if (s.charAt(i) == par[1]) {
				count--;
			}
			// i 位置就是 check计数<0的第一个位置
			// 只要有违规就把前缀调整对，而且没有其他分支
			if (count < 0) {
				for (int j = deleteIndex; j <= i; ++j) {
					// 比如
					if (s.charAt(j) == par[1] && (j == deleteIndex || s.charAt(j - 1) != par[1])) {
						remove(
								/*
								* 下一个递归函数
								* 参数1 剩余的字符串  除了j位置的字符 其他字符都保留
								* 参数3,4 当前来到的检查count的位置 和删去的位置 就是下一步的checkIndex和deleteIndex
								* */
								s.substring(0, j) + s.substring(j + 1, s.length()),
								ans, i, j, par);
					}
				}
				return;//TODO count < 0 就return
			}
		}
		//TODO 代码到这里 是之前删掉了多余的有括号 整个过程结束
		//TODO  把整个str逆序一下
		String reversed = new StringBuilder(s).reverse().toString();
		//TODO 0位置 还是 '(' 说明之前 没有 str逆序 删除多余的左括号过
		if (par[0] == '(') {//TODO 这个if条件 是避免 无限地 左括号 右括号 换来换去 后 无限地递归
			//TODO  这里开始删除多余的左括号
			remove(reversed, ans, 0, 0, new char[] { ')', '(' });
		} else {
			ans.add(reversed);
		}
	}

}
