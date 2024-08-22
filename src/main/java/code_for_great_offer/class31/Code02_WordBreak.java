package code_for_great_offer.class31;

import java.util.*;

public class Code02_WordBreak {

	public static List<String> wordBreak1(String s, List<String> wordDict) {
		HashSet<String> set = new HashSet<>(wordDict);
		LinkedList<String> path = new LinkedList<String>();
		ArrayList<String> res = new ArrayList<String>();
		process(s, 0, set, path, res);
		return res;
	}
	/*
	* 从s的i位置出发，s[i...]这一段字符串，被字典所有组成的方案返回
	  沿途做的决定放在path里
	  如果找到某个组成方式，把它加入到结果序列中
	* */
	public static void process(String s, int i, HashSet<String> set, LinkedList<String> path, ArrayList<String> res) {
		if (i == s.length()) {//没有单词
			res.add(getString(path));
		} else {
			for (int k = i; k < s.length(); k++) {
				/*
				* substring左闭右开 所以k+1
				* */
				if (set.contains(s.substring(i, k + 1))) {
					path.add(s.substring(i, k + 1));
					process(s, k + 1, set, path, res);
					path.pollLast();
				}
			}
		}
	}
	public static int ways1(String str,String[] arr){
		HashSet<String> set = new HashSet<>();
		for (String word:arr){
			set.add(word);
		}
		return f1(str,0,set);
	}

	/*
	* 方法1
	* set是单词表
	* str[index...]被set单词分解的方法数返回
	* */
	public static int f1(String str , int index, HashSet<String> set){
		if(index == str.length()){
			return 1;
		}
		int ways = 0;
		//第一部分是str[index...end]
		for(int end = index;end<str.length();end++){
			/*
			* 任何位置开头 到 任何位置结尾 去查 set
			* */
			String first = str.substring(index, end + 1);
			if(set.contains(first)){
				ways+=f1(str,end+1,set);
			}
		}
		return ways;
	}
	/*
	* 前缀树
	* */
	public static class Node{
		boolean end;
		public Node[] next;

		public Node() {
			end = false;
			next = new Node[26];
		}
	}

	public static int ways2(String str,String[] arr){
		HashSet<String> set = new HashSet<>();
		/*
		* 比那里arr所有的字符 根据前缀树的方式 把字符挂载总的头部head以下
		* */
		for (String word:arr){
			set.add(word);
		}
		Node head = new Node();
		return f2(str.toCharArray(),0,head);
	}

	/*
	 * 方法1
	 * set是单词表
	 * str[index...]被set单词分解的方法数返回
	 * */
	public static int f2(char[] str , int index, Node head){
		if(index == str.length){
			return 1;
		}
		int ways = 0;
		//前缀树当前来到的节点 一开始是head
		//每一次字符串（查分出来的子字符串）都会从头遍历一次有arr数组组成的前缀树
		Node cur = head;
		//第一部分是str[index...end]
		for(int end = index;end<str.length;end++){
			char cha=str[end];
			if(cur.next[cha-'0']==null){//查看有没有路径
				break;//没有
			}
			cur = cur.next[cha-'0'];
			if(cur.end){//查看是否是某一个字符串结尾
				ways+=f2(str,end+1,head);//累加
			}
		}
		return ways;
	}



	public static String getString(LinkedList<String> path) {
		StringBuilder res = new StringBuilder();
		for (String str : path) {
			res.append(str + " ");
		}
		return res.substring(0, res.length() - 1);
	}

	public static List<String> wordBreak2(String s, List<String> words) {
		HashSet<String> set = new HashSet<>(words);
		return process(s, set, new HashMap<String, LinkedList<String>>());
	}

	public static List<String> process(String s, Set<String> set, HashMap<String, LinkedList<String>> map) {
		if (map.containsKey(s)) {
			return map.get(s);
		}
		LinkedList<String> res = new LinkedList<String>();
		if (s.length() == 0) {
			res.add("");
		} else {
			for (String word : set) {
				if (s.startsWith(word)) {
					List<String> sublist = process(s.substring(word.length()), set, map);
					for (String sub : sublist) {
						res.add(word + (sub.isEmpty() ? "" : " ") + sub);
					}
				}
			}
			map.put(s, res);
		}
		return res;
	}

}
