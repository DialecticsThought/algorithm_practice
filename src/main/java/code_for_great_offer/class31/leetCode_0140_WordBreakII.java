package code_for_great_offer.class31;

import java.util.ArrayList;
import java.util.List;
//https://leetcode.cn/problems/word-break-ii/
public class leetCode_0140_WordBreakII {

	public static class Node {
		//是否是单词的结尾 是 path记录
		public String path;
		public boolean end;
		public Node[] nexts;

		public Node() {
			path = null;
			end = false;
			nexts = new Node[26];
		}
	}

	public static List<String> wordBreak(String s, List<String> wordDict) {
		char[] str = s.toCharArray();
		//TODO 根据 wordDict得到前缀树
		Node root = gettrie(wordDict);
		//TODO生成dp表
		boolean[] dp = getdp(s, root);
		ArrayList<String> path = new ArrayList<>();
		List<String> ans = new ArrayList<>();
		process(str, 0, root, dp, path, ans);
		return ans;
	}
	/*
	*TODO
	* str[index]~str[str.len-1] 是要搞定的字符串
	* dp[0...N-1] 在dp里 存放 从i出发 i~str.len-1的字符串能否被分解的结果
	* root 单词表所有单词生成的前缀树头节点
	* path str[0..index-1]做过决定了，做的决定放在path里
	*TODO
	* 来到i位置 不断地枚举 ↓
	* 前缀树能告诉 i~end位置 构成的子串是否在wordList中
	* dp能告诉end+1~str.len-1能否被分解 也就是锁是否有这条路径
	* 所以同时要查前缀树和dp表
	* */
	public static void process(char[] str, int index, Node root, boolean[] dp, ArrayList<String> path,
			List<String> ans) {
		if (index == str.length) {//说明已经完成了
			StringBuilder builder = new StringBuilder();
			//TODO 把之前所有的决定 列出来
			for (int i = 0; i < path.size() - 1; i++) {
				builder.append(path.get(i) + " ");
			}
			builder.append(path.get(path.size() - 1));
			ans.add(builder.toString());
		} else {
			Node cur = root;
			for (int end = index; end < str.length; end++) {
				//TODO 判断 str[i]~str[end] （能不能拆出来）
				int road = str[end] - 'a';
				//TODO 如果没有下一个节点 说明不能拆出来
				if (cur.nexts[road] == null) {
					break;
				}
				cur = cur.nexts[road];
				/*
				*TODO
				* 1.当前节点必须是结尾节点
				* 2.剩下end+1~str.len-1范围的字符串能被分解
				* */
				if (cur.end && dp[end + 1]) {
					// [i...end] 前缀串
					// str.subString(i,end+1)  [i..end]
					//TODO 把当前节点（当前记录的结尾）记录到答案里
					path.add(cur.path);
					//TODO dfs
					process(str, end + 1, root, dp, path, ans);
					//TODO 当前递归的所在节点返回其父节点 需要还原现场
					path.remove(path.size() - 1);
				}
			}
		}
	}

	public static Node gettrie(List<String> wordDict) {
		Node root = new Node();
		for (String str : wordDict) {
			char[] chs = str.toCharArray();
			Node node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = chs[i] - 'a';
				if (node.nexts[index] == null) {
					node.nexts[index] = new Node();
				}
				node = node.nexts[index];
			}
			node.path = str;
			node.end = true;
		}
		return root;
	}

	public static boolean[] getdp(String s, Node root) {
		char[] str = s.toCharArray();
		int N = str.length;
		boolean[] dp = new boolean[N + 1];
		dp[N] = true;
		for (int i = N - 1; i >= 0; i--) {
			Node cur = root;
			for (int end = i; end < N; end++) {
				int path = str[end] - 'a';
				if (cur.nexts[path] == null) {
					break;
				}
				cur = cur.nexts[path];
				if (cur.end && dp[end + 1]) {
					dp[i] = true;
					break;
				}
			}
		}
		return dp;
	}

}
