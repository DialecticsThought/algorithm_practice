package code_for_great_offer.class46;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/*
*TODO
* 注意！课上介绍题目设定的时候，有一点点小错
* 题目描述如下：
* 给定n个字符串，并且每个字符串长度一定是n，请组成单词方阵，比如：
* 给定4个字符串，长度都是4，["ball","area","lead","lady"]
* 可以组成如下的方阵：
* b a l l
* a r e a
* l e a d
* l a d y
* 什么叫单词方阵？如上的方阵可以看到，
* 第1行和第1列都是"ball"，第2行和第2列都是"area"，第3行和第3列都是"lead"，第4行和第4列都是"lady"
* 所以如果有N个单词，单词方阵是指:
* 一个N*N的二维矩阵，并且i行和i列都是某个单词，不要求全部N个单词都在这个方阵里。
* 请返回所有可能的单词方阵。
* 示例：
* 输入: words = ["abat","baba","atan","atal"]
* 输出: [["baba","abat","baba","atal"],["baba","abat","baba","atan"]]
* 解释：
* 可以看到输出里，有两个链表，代表两个单词方阵
* 第一个如下：
* b a b a
* a b a t
* b a b a
* a t a l
* 这个方阵里没有atan，因为不要求全部单词都在方阵里
* 第二个如下：
* b a b a
* a b a t
* b a b a
* a t a n
* 这个方阵里没有atal，因为不要求全部单词都在方阵里
* 课上说的是：一个N*N的二维矩阵，并且i行和i列都是某个单词，要求全部N个单词都在这个方阵里
* 原题说的是：一个N*N的二维矩阵，并且i行和i列都是某个单词，不要求全部N个单词都在这个方阵里
* 讲的过程没错，但是介绍题意的时候，这里失误了
*
*TODO
* ball area lead lady
* 针对这些单词求前缀
* 前缀为空的单词有  ball area lead lady  ☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆
* 前缀为b的单词有 ball
* 前缀为ba的单词有 ball
* 前缀为bal的单词有 ball
* 前缀为ball的单词有 ball
* 前缀为a的单词有 area
* 前缀为ar的单词有 area
* 前缀为are的单词有 area
* 前缀为area的单词有 area
* 前缀为l的单词有 lead lady
* 前缀为le的单词有 lead
* ....
* 会生成一张表
*TODO
* 对于上面的4个单词中选出第1个单词 填进表格 是不需要任何限制
* eg:
* b a l l
* a
* l
* l
* a r e a
* r
* e
* a
* ...
* 但是第2个单词开始 有限制了
* eg:
* b a l l
* a
* l
* l
* 上面的表格中 第2个单词 填在第2列 第2行 就需要前缀是a
* b a l l
* a r e a
* l e
* l a
* 上面的表格中 第3个单词 填在第3列 第3行 就需要前缀是le
* ......
* 当前来到第i行第1列的时候 的限制是什么
* 第1行单词的第i个字符
* 第2行单词的第i个字符
* ....
* 第i-1行单词的第i个字符
* */
public class LeetCode_0425_WordSquares {

	public static List<List<String>> wordSquares(String[] words) {
		int n = words[0].length();
		//TODO 所有单词，所有前缀字符串，都会成为key！
		HashMap<String, List<String>> map = new HashMap<>();
		for (String word : words) {
			for (int end = 0; end <= n; end++) {
				String prefix = word.substring(0, end);
				if (!map.containsKey(prefix)) {
					map.put(prefix, new ArrayList<>());
				}
				map.get(prefix).add(word);
			}
		}
		List<List<String>> ans = new ArrayList<>();
		process(0, n, map, new LinkedList<>(), ans);
		return ans;
	}
	/*
	*TODO
	* i, 当前填到第i号单词，从0开始，填到n-1
	* map, 前缀所拥有的单词 也就是前缀表
	* path, 之前填过的单词, 0...i-1填过的
	* ans, 收集答案
	*/
	public static void process(int i, int n, HashMap<String, List<String>> map, LinkedList<String> path,
			List<List<String>> ans) {
		if (i == n) {//TODO base case 来到终止位置 说明之前的尝试是正确的
			ans.add(new ArrayList<>(path));
		} else {
			//TODO 把前缀的限制求出来
			//TODO 0位置 也就是初始进来的时候 builder是空字符串 ☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆
			StringBuilder builder = new StringBuilder();
			//TODO 之前递归尝试出来的所有单词的第i个字符
			for (String pre : path) {
				builder.append(pre.charAt(i));
			}
			//TODO 拼出限制 也就是前缀
			String prefix = builder.toString();
			//TODO 如果cache也有这个前缀
			if (map.containsKey(prefix)) {
				for (String next : map.get(prefix)) {
					path.addLast(next);
					//TODO dfs的递归
					process(i + 1, n, map, path, ans);
					//TODO dfs的还原现场
					path.pollLast();
				}
			}
		}
	}

}
