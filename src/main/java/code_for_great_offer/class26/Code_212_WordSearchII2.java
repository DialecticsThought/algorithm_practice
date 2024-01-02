package code_for_great_offer.class26;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

// 本题测试链接 : https://leetcode.cn/problems/word-search-ii/
public class Code_212_WordSearchII2 {

	public static class TrieNode {//前缀树
		public TrieNode[] nexts;
		public int pass;//通过前缀树多少个节点
		public int end;//多少单词以当前这个点结尾

		public TrieNode() {
			nexts = new TrieNode[26];
			pass = 0;
			end = 0;
		}

	}

	public static void fillWord(TrieNode head, String word) {
		head.pass++;//表示来过一次
		char[] chs = word.toCharArray();
		int index = 0;
		TrieNode node = head;
		for (int i = 0; i < chs.length; i++) {
			index = chs[i] - 'a';//一个前缀树中的节点最多有25个分支 25个节点
			if (node.nexts[index] == null) {//当前节点有没有子节点 也就是有没有分支
				node.nexts[index] = new TrieNode();//没有子节点的话 创建一个
			}
			node = node.nexts[index];//指针后移
			node.pass++;
		}
		node.end ++;//node来到最后一个节点的话 执行该操作
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
		HashSet<String> set = new HashSet<>();//去重
		for (String word : words) {
			if (!set.contains(word)) {
				fillWord(head, word);//从头结点出发把这个word加入到前缀树中
				set.add(word);//去重
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
				//在board矩阵里面 从row行 col列出发 根据创建好的前缀树从head节点开始走
				// 沿途走过的路径都存在path里,如果走出结果就放入ans
				process(board, row, col, path, head, ans);
			}
		}
		return ans;
	}

	// 想从board[row][col]位置的字符出发，
	// 之前的路径上，走过的字符，记录在path里
	// cur还没有登上，有待检查能不能登上去的前缀树的节点（通过查看前缀树上没有相应的路径）
	// 如果找到words中的某个str，就记录在 res里
	// 返回值，从row,col 出发，一共找到了多少个str
	public static int process(
			char[][] board, int row, int col,
			LinkedList<Character> path, TrieNode cur,
			List<String> res) {
		char cha = board[row][col];
		//因为row行 col列的字符走过的话 ASCII码是0 防止走回头路
		if (cha == 0) { // 这个row col位置是之前走过的位置
			return 0;
		}
		// (row,col) 不是回头路 cha 有效
		//前缀树某一个节点最多有25个分支（节点）
		int index = cha - 'a';//表明这个row行 col列的字符没有在当前节点中有相应的路径
		// 如果没路，或者这条路上最终的字符串之前加入过结果里
		//cur.nexts[index].pass == 0 表示下一个节点开始的之后（还没有走过）的路径已经没有了
		if (cur.nexts[index] == null || cur.nexts[index].pass == 0) {
			return 0;
		}
		// 没有走回头路且能登上去（根据某个分支 走向下一个节点）
		cur = cur.nexts[index];
		path.addLast(cha);// 当前位置的字符加到路径里去
		int fix = 0; // 从row和col位置出发，后续一共找到了多少答案
		/*
		* 有五种选择
		* 原地不动 查看当前节点的end属性是不是1
		* 向左
		* 向上
		* 向右
		* 向下
		* */
		// 当我来到row col位置，如果决定不往后走了。是不是已经搞定了某个字符串了
		if (cur.end > 0) {
			res.add(generatePath(path));
			cur.end--;//避免重复收集
			fix++;
		}
		// 往上、下、左、右，四个方向尝试
		board[row][col] = 0;//当前ASCII码变成0 意思就是走过这个位置的
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
		board[row][col] = cha;//因为中途变成过0 要变成原样
		path.pollLast();//因为之前path.addLast(cha);
		//看笔记
		/*
		* pass表示该节点开始的之后的路径的数量
		* 减掉fix之后的pass表示该节点开始的之后（还没有走过）的路径的数量还有多少
		* */
		cur.pass -= fix;
		return fix;
	}

}
