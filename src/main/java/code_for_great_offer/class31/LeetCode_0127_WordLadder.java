package code_for_great_offer.class31;

import java.util.*;
/*
* 字典wordList 中从单词 beginWord和 endWord 的 转换序列
* 是一个按下述规格形成的序列beginWord -> s1-> s2-> ... -> sk：
*
* 每一对相邻的单词只差一个字母。
* 对于1 <= i <= k时，每个si都在wordList中。注意， beginWord不需要在wordList中。
* sk== endWord
* 给你两个单词 beginWord和 endWord 和一个字典 wordList ，返回 从beginWord 到endWord 的 最短转换序列 中的 单词数目 。
* 如果不存在这样的转换序列，返回 0 。
*
* 示例 1：
* 输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
* 输出：5
* 解释：一个最短转换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog", 返回它的长度 5。
* 示例 2：
* 输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
* 输出：0
* 解释：endWord "cog" 不在字典中，所以无法进行转换。
*
* */
public class LeetCode_0127_WordLadder {

	/*
	* start，出发的单词
	* to, 目标单位
	* list, 列表
	* to 一定属于list , start未必属于list
	* 返回变幻的最短路径长度
	* */
	public static int ladderLength1(String start, String to, List<String> list) {
		/*
		 * 概念：邻居
		 * str1和str2只有一个char不同
		 * abcd 和 bbcd
		 *TODO
		 * 1.生成一个hashmap
		 * key就是wordList的所有str, value是 key的邻居 用set结构表示
		 * 2.宽度优先遍历
		 * 对于String start这个形参 算出它的邻居 ，然后它的每个邻居也求出对应的个邻居 类似一个树形结构
		 * 每个树的节点就是key,它的孩子就是value
		 * 直到找到end 就结束bfs
		 *TODO 优化点
		 * start开始bfs 同时，end 也bfs  什么时候装上
		 * 但是，还要优化的是 start为头的树 和end为头的树
		 * 每一次展开一层 比较是start为头的树这一层节点数量多 还是 是end为头的树这一层节点数量多
		 * 哪一个这一层节点数量少 哪一个树再基于当前的层的节点展开新的一层
		 * 再比较  比较是start为头的树这一层节点数量多 还是 是end为头的树这一层节点数量多
		 * 不断循环
		 * */
		list.add(start);

		/*
		* key : 列表中的单词，每一个单词都会有记录！
		* value : key这个单词，有哪些邻居！
		* */
		HashMap<String, ArrayList<String>> nexts = getNeighbors(list);

		/*
		*TODO eg:
		* abc  出发 abc  -> 1 这个<k,v>表示bbc到达原始出发点start的距离是1
		* bbc -> 2 这个<k,v>表示bbc到达原始出发点start的距离是2
		* */
		HashMap<String, Integer> distanceMap = new HashMap<>();
		distanceMap.put(start, 1);
		HashSet<String> set = new HashSet<>();
		//TODO set是为了防止bfs重复
		set.add(start);
		Queue<String> queue = new LinkedList<>();
		queue.add(start);
		while (!queue.isEmpty()) {
			String cur = queue.poll();
			Integer distance = distanceMap.get(cur);
			//遍历自己的邻居
			for (String neighbor : nexts.get(cur)) {
				if (neighbor.equals(to)) {
					return distance + 1;
				}
				//TODO 判断当前的节点的邻居是否被遍历过
				if (!set.contains(neighbor)) {
					set.add(neighbor);
					queue.add(neighbor);
					distanceMap.put(neighbor, distance + 1);
				}
			}

		}
		return 0;
	}

	public static HashMap<String, ArrayList<String>> getNeighbors(List<String> words) {
		//把list直接变成set
		HashSet<String> dict = new HashSet<>(words);
		HashMap<String, ArrayList<String>> nexts = new HashMap<>();
		//TODO 遍历
		for (int i = 0; i < words.size(); i++) {
			nexts.put(words.get(i), getNeighbor(words.get(i), dict));
		}
		return nexts;
	}
	/*
	* 应该根据具体数据状况决定用什么来找邻居
	* 1)如果字符串长度比较短，字符串数量比较多，以下方法适合
	* 2)如果字符串长度比较长，字符串数量比较少，以下方法不适合
	*TODO
	* 针对这道题
	* eg：有个str = abc 找这个str的邻居
	* 把str[0]变一下 可以是 bbc cbc dbc .... 然后每一次变，就查询这个新str有没有在dict中
	* 同理 str[1]变一下 可以是 aec acc adc .... 然后每一次变，就查询这个新str有没有在dict中
	* 同理 str[2]变一下 可以是 aba abb abd .... 然后每一次变，就查询这个新str有没有在dict中
	* */
	public static ArrayList<String> getNeighbor(String word, HashSet<String> dict) {
		ArrayList<String> res = new ArrayList<String>();
		char[] chs = word.toCharArray();
		for (int i = 0; i < chs.length; i++) {
			for (char cur = 'a'; cur <= 'z'; cur++) {
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

	public static int ladderLength2(String beginWord, String endWord, List<String> wordList) {
		HashSet<String> dict = new HashSet<>(wordList);
		if (!dict.contains(endWord)) {
			return 0;
		}
		/*
		*TODO
		* 默认 startSet.size < endSet.size
		* 说明 默认从start开头的树开始宽度优先遍历
		* 如果 遍历之后 startSet.size < endSet.size
		* startSet还是startSet
		* 如果 遍历之后 startSet.size > endSet.size
		* startSet就是endSet
		* 总之：下一轮startSet就是上一轮 startSet 和 endSet小的
		* */
		//start开始bfs 被遍历到的树的所有节点 都要记录到这个set中
		HashSet<String> startSet = new HashSet<>();
		//end开始bfs 被遍历到的树的所有节点 都要记录到这个set中
		HashSet<String> endSet = new HashSet<>();
		//只要被 start开始bfs 或end开始bfs 访问到的节点 就要记录到这个set中
		HashSet<String> visit = new HashSet<>();
		startSet.add(beginWord);
		endSet.add(endWord);
		for (int len = 2; !startSet.isEmpty(); len++) {
			// startSet是较小的，endSet是较大的
			HashSet<String> nextSet = new HashSet<>();
			/*
			* startSet的每一个单词 去找她的下一级（也就是邻居）
			* eg: 当前 w = a b c
			* 把str[0]变一下 可以是 bbc cbc dbc .... 然后每一次变，就查询这个新str有没有在dict中
			* 同理 str[1]变一下 可以是 aec acc adc .... 然后每一次变，就查询这个新str有没有在dict中
			* 同理 str[2]变一下 可以是 aba abb abd .... 然后每一次变，就查询这个新str有没有在dict中
			* ....
			* 上面的操作就是
			* for (int j = 0; j < w.length(); j++) {
			* 	for (char c = 'a'; c <= 'z'; c++) {
			* 	}
			* }
			* */
			for (String w : startSet) {
				// w -> a(nextSet)
				for (int j = 0; j < w.length(); j++) {
					char[] ch = w.toCharArray();
					for (char c = 'a'; c <= 'z'; c++) {
						if (c != w.charAt(j)) {
							ch[j] = c;
							/*
							* 只要 当前 startSet按层遍历到某一个元素 == endSet按侧边遍历到的某一个元素
							* 就说明 找到了路径
							* */
							String next = String.valueOf(ch);
							if (endSet.contains(next)) {//撞上了
								return len;
							}
							if (dict.contains(next) && !visit.contains(next)) {
								nextSet.add(next);
								visit.add(next);
							}
						}
					}
				}
			}
			// startSet(小) -> nextSet(某个大小)   和 endSet大小来比
			startSet = (nextSet.size() < endSet.size()) ? nextSet : endSet;
			endSet = (startSet == nextSet) ? endSet : nextSet;
		}
		return 0;
	}

}
