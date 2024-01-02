package code_for_great_offer.class28;

import java.util.ArrayList;
import java.util.List;

public class Problem_0022_GenerateParentheses {

	public static List<String> generateParenthesis(int n) {
		char[] path = new char[n << 1];
		List<String> ans = new ArrayList<>();
		process(path, 0, 0, n, ans);
		return ans;
	}


	/*
	 * path 从头开始到现在为止做的决定  path[0....index-1]做完决定的！
	 * path[index.....] 还没做决定，当前轮到index位置做决定！
	 * leftMinusRight和leftRest的存在因为要剪枝 也就是每个节点 有些分支 不合法 那么就不会走这些分支
	 * 之前做的决定中 左括号的数量-右括号的数量 leftMinusRight
	 * 左括号还剩几个leftRest
	 * */
	public static void process(char[] path, int index, int leftMinusRight, int leftRest, List<String> ans) {
		//base case 来到 str最后一个位置
		if (index == path.length) {
			// 直接把path 加入到 ans 中 不用额外的判断 因为 之前 剪枝 剪得好
			ans.add(String.valueOf(path));
		} else {
			/*
			* 来到index 位置 一共就2个决定
			* 决定1 在index位置 放 “(”  这个决定 必须是 还有剩下的 “(” 才能放
			* 做了决定1之后 左括号的数量-右括号的数量 会+1   左括号还剩几个 会-1
			* 决定2 在index位置 放 “)”  这个决定 必须是 之前做的决定中 左括号的数量-右括号的数量 >0 才能放
			* eg: 当前状况 ( ( ( ) 那么当前可以决定 放一个 “)”
			* eg: 当前状况 ( ( ) ) ( 那么当前可以决定 放一个 “)”
			 * */
			if (leftRest > 0) {
				path[index] = '(';
				process(path, index + 1, leftMinusRight + 1, leftRest - 1, ans);
			}
			if (leftMinusRight > 0) {
				path[index] = ')';
				process(path, index + 1, leftMinusRight - 1, leftRest, ans);
			}
		}
	}

	// 不剪枝的做法
	public static List<String> generateParenthesis2(int n) {
		char[] path = new char[n << 1];
		List<String> ans = new ArrayList<>();
		process2(path, 0, ans);
		return ans;
	}
	/*
	* path 从头开始到现在为止做的决定  path[0....index-1]做完决定的！
	*  path[index.....] 还没做决定，当前轮到index位置做决定！
	* */
	public static void process2(char[] path, int index, List<String> ans) {
		if (index == path.length) {
			if (isValid(path)) {
				ans.add(String.valueOf(path));
			}
		} else {
			path[index] = '(';
			process2(path, index + 1, ans);
			path[index] = ')';
			process2(path, index + 1, ans);
		}
	}

	public static boolean isValid(char[] path) {
		int count = 0;
		for (char cha : path) {
			if (cha == '(') {
				count++;
			} else {
				count--;
			}
			if (count < 0) {
				return false;
			}
		}
		return count == 0;
	}

}
