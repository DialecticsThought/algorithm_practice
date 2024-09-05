package code_for_great_offer.class26;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

// 本题测试链接 : https://leetcode.cn/problems/word-search-ii/
public class leetCode_212_WordSearchII {

	public static class TrieNode {
		public TrieNode[] nexts;
		public int pass;
		public boolean end;

		public TrieNode() {
			nexts = new TrieNode[26];
			pass = 0;
			end = false;
		}

	}

	public static void fillWord(TrieNode head, String word) {
		head.pass++;
		char[] chs = word.toCharArray();
		int index = 0;
		TrieNode node = head;
		for (int i = 0; i < chs.length; i++) {
			index = chs[i] - 'a';
			if (node.nexts[index] == null) {
				node.nexts[index] = new TrieNode();
			}
			node = node.nexts[index];
			node.pass++;
		}
		node.end = true;
	}

	public static String generatePath(LinkedList<Character> path) {
		char[] str = new char[path.size()];
		int index = 0;
		for (Character cha : path) {
			str[index++] = cha;
		}
		return String.valueOf(str);
	}

	public static List<String> findWords(char[][] board, String[] words) {
		TrieNode head = new TrieNode(); // 前缀树最顶端的头
		HashSet<String> set = new HashSet<>();
		for (String word : words) {
			if (!set.contains(word)) {
				fillWord(head, word);
				set.add(word);
			}
		}
		// 答案
		List<String> ans = new ArrayList<>();
		// 沿途走过的字符，收集起来，存在path里
		LinkedList<Character> path = new LinkedList<>();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				// 枚举在board中的所有位置
				// 每一个位置出发的情况下，答案都收集
				process(board, row, col, path, head, ans);
			}
		}
		return ans;
	}

	/*
	*TODO 函数的意义：
	* 从board[row][col]位置的字符出发，
	* 之前的路径上，走过的字符，记录在path里 这个path 肯定和前缀树的某个部分路径相同
	* cur还没有登上，有待检查能不能登上去的前缀树的节点
	* 如果找到words中的某个str，就记录在 res里
	* 返回值，从row,col 出发，一共找到了多少个str
	* 当前来到 节点board[row][col]= '?' 但是当前还不知道当前前缀树来到的节点cur 有没有'?'路径
	* */
	public static int process(
			char[][] board, int row, int col,
			LinkedList<Character> path, TrieNode cur,
			List<String> res) {
		/*
		*TODO
		* 先把 原始的字符记录 下来 因为 遍历过的节点 需要标记成0 防止 环路
		* dfs遍历退出之前 需要还原数据
		* */
		char cha = board[row][col];
		//TODO 这个row col位置是之前走过的位置 形成了环路
		if (cha == 0) {
			return 0;
		}
		/*
		* TODO 过了上面的if判断 说明 (row,col) 不是回头路 cha 有效
		* */
		int index = cha - 'a';
		/*
		*TODO
		* 当前前缀树的节点判断有没有 该char的路径
		* 如果没路，或者这条路上最终的字符串之前加入过结果里
		* 每次收集到一个答案 答案的路径上的节点的pass--
		* 如果cur.nexts[index].pass == 0 说明该节点出发的所有路径都找到了
		* */
		if (cur.nexts[index] == null || cur.nexts[index].pass == 0) {
			return 0;
		}
		/*
		*TODO
		* 没有走回头路且能登上去的话
		* 前缀树的节点 向前一个
		* */
		cur = cur.nexts[index];
		//TODO 当前位置的字符加到历史路径里去
		path.addLast(cha);
		int fix = 0; // 从row和col位置出发，后续一共搞定了多少答案
		// 当我来到row col位置，如果决定不往后走了。是不是已经搞定了某个字符串了
		if (cur.end) {//TODO 判断当前节点是否是某个字符串的结尾
			res.add(generatePath(path));//出结果了 加入到 答案集合
			cur.end = false;
			fix++; // 从row和col位置出发，后续一共搞定了多少答案 + 1
		}
		//TODO 往上、下、左、右，四个方向尝试
		board[row][col] = 0;
		if (row > 0) {
			fix += process(board, row - 1, col, path, cur, res);
		}
		if (row < board.length - 1) {
			fix += process(board, row + 1, col, path, cur, res);
		}
		if (col > 0) {
			fix += process(board, row, col - 1, path, cur, res);
		}
		if (col < board[0].length - 1) {
			fix += process(board, row, col + 1, path, cur, res);
		}
		//TODO 深度优先的还原 必须在 当前节点的递归结束 前执行
		board[row][col] = cha;
		path.pollLast();
		cur.pass -= fix;//TODO 从当前节点出发的有效路径数量 更新
		//TODO 当前节点的递归结束 向上返回
		return fix;
	}

}
