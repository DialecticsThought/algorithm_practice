package code_for_great_offer.class26;

import java.util.*;

// 本题测试链接 : https://leetcode.cn/problems/word-ladder-ii/
public class Code_126_WordLadderII {

	public static List<List<String>> findLadders(String start, String end, List<String> list) {
		list.add(start);
		//第1步生成邻居表
		HashMap<String, List<String>> nexts = getNexts(list);
		//第2步生成距离表
		HashMap<String, Integer> distances = getDistances(start, nexts);
		LinkedList<String> pathList = new LinkedList<>();
		List<List<String>> res = new ArrayList<>();
		//第3步 收集答案
		getShortestPaths(start, end, nexts, distances, pathList, res);
		return res;
	}

	//TODO 针对表里的每个单词生成对应的邻接表
	public static HashMap<String, List<String>> getNexts(List<String> words) {
		HashSet<String> dict = new HashSet<>(words);
		//nexts 就是邻接表
		HashMap<String, List<String>> nexts = new HashMap<>();
		for (int i = 0; i < words.size(); i++) {
			nexts.put(words.get(i), getNext(words.get(i), dict));
		}
		return nexts;
	}

	// word, 在表中，有哪些邻居，把邻居们，生成list返回
	public static List<String> getNext(String word, HashSet<String> dict) {

		ArrayList<String> res = new ArrayList<String>();
		char[] chs = word.toCharArray();
		for (char cur = 'a'; cur <= 'z'; cur++) {
			for (int i = 0; i < chs.length; i++) {
				if (chs[i] != cur) {
					char tmp = chs[i];
					chs[i] = cur;
					if (dict.contains(String.valueOf(chs))) {
						res.add(String.valueOf(chs));
					}
					chs[i] = tmp;
				}
			}
		}
		return res;
	}

	// 生成距离表，从start开始，根据邻居表，宽度优先遍历，对于能够遇到的所有字符串，生成(字符串，距离)这条记录，放入距离表中
	public static HashMap<String, Integer> getDistances(String start, HashMap<String, List<String>> nexts) {
		HashMap<String, Integer> distances = new HashMap<>();
		distances.put(start, 0);
		Queue<String> queue = new LinkedList<>();
		queue.add(start);
		HashSet<String> set = new HashSet<>();
		set.add(start);
		while (!queue.isEmpty()) {
			String cur = queue.poll();
			for (String next : nexts.get(cur)) {
				if (!set.contains(next)) {
					distances.put(next, distances.get(cur) + 1);
					queue.add(next);
					set.add(next);
				}
			}
		}
		return distances;
	}

	/**
	* cur：当前来到的字符串 可变参数
	* to： 目标，固定参数
	* nexts： 每一个字符串的邻居表
	* 假设：cur 到开头距离5 -> 到开头距离是6的支路 distances距离表 不会走距离 <5 的路
	* path : 来到cur之前，深度优先遍历之前的历史是什么 或者说从开始走到现在的路径
	* res : 当遇到cur，把历史，放入res，作为一个结果
	* */
	public static void getShortestPaths(String cur, String to, HashMap<String, List<String>> nexts,
			HashMap<String, Integer> distances, LinkedList<String> path, List<List<String>> res) {
		//TODO 执行该方法的时候 说明 当前来到cur节点 需要把cur节点 加入到path中
		path.add(cur);
		//TODO 当前节点 就是 目标节点 生成答案
		if (to.equals(cur)) {
			res.add(new LinkedList<String>(path));
		} else {
			//TODO 遍历 目的： 每个节点 执行dfs
			for (String next : nexts.get(cur)) {
				//TODO 要求： 有dfs的过程中 必须 距离不断增加 才能继续走该路径
				if (distances.get(next) == distances.get(cur) + 1) {
					getShortestPaths(next, to, nexts, distances, path, res);
				}
			}
		}
		/**
		* TODO 因为dfs
		*  当前来到的节点x 需要返回当当前节点的上一层节点y
		*  让节点y 走其他子节点的分支 所以 需要还原现场 与path.add(cur);对应
		* */
		path.pollLast();
	}

}
