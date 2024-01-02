package code_for_great_offer.class33;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/*
*TODO
* 有一种新的使用拉丁字母的外来语言。但是，你不知道字母之间的顺序。
* 你会从词典中收到一个非空的单词列表，其中的单词在这种新语言的规则下按字典顺序排序。
* 请推导出这种语言的字母顺序。
* 说明：
* 你可以假设所有的字母都是小写。
* 如果a是b的前缀且b出现在a之前，那么这个顺序是无效的。
* 如果顺序是无效的，则返回空字符串。
* 这里可能有多个有效的字母顺序，返回以正常字典顺序看来最小的。
* 例1：
* 输入：["wrt","wrf","er","ett","rftt"]
* 输出："wertf"
* 解释：
* 从 "wrt"和"wrf" ,我们可以得到 't'<'f'
* 从 "wrt"和"er" ,我们可以得到'w'<'e'
* 从 "er"和"ett" ,我们可以得到 get 'r'<'t'
* 从 "ett"和"rftt" ,我们可以得到 'e'<'r'
* 所以返回 "wertf"
* 例2:
* 输入：["z","x"]
* 输出："zx"
* 解释：
* 从 "z" 和 "x"，我们可以得到 'z' < 'x'
* 所以返回"zx"。
* 考点：构造&存储图 + 拓扑排序
*
* */
public class Problem_0269_AlienDictionary {

	public static String alienOrder(String[] words) {
		if (words == null || words.length == 0) {
			return "";
		}
		int N = words.length;
		HashMap<Character, Integer> indegree = new HashMap<>();
		for (int i = 0; i < N; i++) {
			for (char c : words[i].toCharArray()) {
				indegree.put(c, 0);
			}
		}
		HashMap<Character, HashSet<Character>> graph = new HashMap<>();
		for (int i = 0; i < N - 1; i++) {
			char[] cur = words[i].toCharArray();
			char[] nex = words[i + 1].toCharArray();
			int len = Math.min(cur.length, nex.length);
			int j = 0;
			for (; j < len; j++) {
				if (cur[j] != nex[j]) {
					if (!graph.containsKey(cur[j])) {
						graph.put(cur[j], new HashSet<>());
					}
					if (!graph.get(cur[j]).contains(nex[j])) {
						graph.get(cur[j]).add(nex[j]);
						indegree.put(nex[j], indegree.get(nex[j]) + 1);
					}
					break;
				}
			}
			if (j < cur.length && j == nex.length) {
				return "";
			}
		}
		StringBuilder ans = new StringBuilder();
		Queue<Character> q = new LinkedList<>();
		for (Character key : indegree.keySet()) {
			if (indegree.get(key) == 0) {
				q.offer(key);
			}
		}
		while (!q.isEmpty()) {
			char cur = q.poll();
			ans.append(cur);
			if (graph.containsKey(cur)) {
				for (char next : graph.get(cur)) {
					indegree.put(next, indegree.get(next) - 1);
					if (indegree.get(next) == 0) {
						q.offer(next);
					}
				}
			}
		}
		return ans.length() == indegree.size() ? ans.toString() : "";
	}

}
