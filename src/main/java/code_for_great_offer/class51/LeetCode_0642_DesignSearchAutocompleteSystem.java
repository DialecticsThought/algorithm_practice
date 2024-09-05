package code_for_great_offer.class51;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class LeetCode_0642_DesignSearchAutocompleteSystem {
	/*
	*TODO
	*  <abc,8>  <abd,5,4> <abz,2>  <ade,2>
	* k是单词 v是词频
	* 比如在搜索栏输入 a
	* 依次输出 abc abd abz abe 根据词频从大到小 再根据字典序排列
	* 比如在搜索栏输入 ab
	* 依次输出 abc abd abz  根据词频从大到小 再根据字典序排列
	* 比如在搜索栏输入 abd
	* 依次输出 abd
	* 比如在搜索栏输入 abd#
	* 那么<abd,5,4> => <abd,5,5> 词频++
	 * */
	class AutocompleteSystem {
		/*
		*TODO
		* 前缀树 的节点有其属性
		* eg:
		* 那么 前缀树节点
		*    	 *
		* 	 	 | 路径a
		* 		 * 节点属性{<abc,5>,<abe,4>}
		* 	 	 | 路径b
		* 		 * 节点属性{<abc,5>,<abe,4>}
		* 路径c ↙   ↘ 路径e
		* 	  *     * 节点属性{<abe,4>}
		* 如果 多了 abk
		* 	那么 前缀树节点 从头开始遍历 遍历到b 发现没有f路径 新建节点
		*TODO
		*    		 *
		* 	 		 | 路径a
		* 			 *  节点属性{<abc,5>,<abe,4>}
		* 	 	 	 | 路径b
		* 			 * 节点属性{<abc,5>,<abe,4>}
		* 路径c↙      ↓ 路径e    ↘ 路径k
		* *{<abe,4>} *{<abe,4>}  *{<abk,1>}
		* 再向上回溯 把b上面的所有节点的属性都更新上
		*TODO
		*    		 * 节点属性{<abc,5>,<abe,4>,<abk,1>}
		* 	 		 | 路径a
		* 			 * 节点属性{<abc,5>,<abe,4>,<abk,1>}
		* 	 	 	 | 路径b
		* 			 *
		* 路径c↙      ↓ 路径e    ↘ 路径k
		* 	*		 *  		*
		* {<abe,4>}  {<abe,4>}	{<abk,1>}
		* */
		public class TrieNode {
			public TrieNode father;//TODO用来回溯
			public String path;
			public TrieNode[] nexts;

			public TrieNode(TrieNode f, String p) {
				father = f;
				path = p;
				//TODO 多了一个字符是空格
				nexts = new TrieNode[27];
			}
		}

		public class WordCount implements Comparable<WordCount> {
			public String word;//单词
			public int count;//词频

			public WordCount(String w, int c) {
				word = w;
				count = c;
			}

			public int compareTo(WordCount o) {
				return count != o.count ? (o.count - count) : word.compareTo(o.word);
			}
		}

		//TODO 题目的要求，只输出排名前3的列表
		public final int top = 3;
		//TODO 前缀树的头结点
		public final TrieNode root = new TrieNode(null, "");
		/*
		*TODO
		* 某个前缀树节点，上面的有序表，不在这个节点内部
		* 意思就是每一个前缀树的节点都有一个有序表
		* 用的是外挂
		* */
		public HashMap<TrieNode, TreeSet<WordCount>> nodeRankMap = new HashMap<>();

		//TODO 字符串 "abc"  7次   ->  ("abc", 7)
		public HashMap<String, WordCount> wordCountMap = new HashMap<>();
		/*
		*TODO
		* 		* 甲
		* 		↓ a
		* 		* 乙
		* 	b ↙   ↘ c
		* 	*丙		* 丁
		* 一开始input(a) 那么cur = 乙，path = a
		* 再input(c) 那么cur = 丁，path = ab
		* */
		//TODO 一共输入的字符平拼起来的样子
		public String path;
		//TODO 当前的前缀节点
		public TrieNode cur;

		/*
		*TODO
		* 输入c   对应路径
		* ' ' -> 0
		* 'a' -> 1
		* 'b' -> 2
		* ...
		* 'z' -> 26
		* c - '`' 的原因是 '`' 是 'a' 的ASCII序的前一个
		* a - '`' = 1   b - '`' = 2
		* */
		private int f(char c) {
			return c == ' ' ? 0 : (c - '`');
		}

		public AutocompleteSystem(String[] sentences, int[] times) {
			path = "";
			cur = root;
			for (int i = 0; i < sentences.length; i++) {
				String word = sentences[i];
				int count = times[i];
				WordCount wc = new WordCount(word, count - 1);
				wordCountMap.put(word, wc);
				for (char c : word.toCharArray()) {
					input(c);
				}
				input('#');
			}
		}

		/*
		*TODO
		* 之前已经有一些历史了！
		* 当前键入 c 字符
		* 请顺着之前的历史，根据c字符是什么，继续
		* path : 之前键入的字符串整体
		* cur : 当前滑到了前缀树的哪个节点
		* eg:
		* 一开始input(a) 那么搜以a为前缀的所有字符什么
		* 再input(b) 那么搜以ab为前缀的所有字符什么 a要在之前记录下来
		* */
		public List<String> input(char c) {
			List<String> ans = new ArrayList<>();
			if (c != '#') {
				path += c;//增加历史记录
				int i = f(c);
				if (cur.nexts[i] == null) {//TODO 路不存在
					cur.nexts[i] = new TrieNode(cur, path);
				}
				//TODO 路径存在 往下走
				cur = cur.nexts[i];
				if (!nodeRankMap.containsKey(cur)) {
					nodeRankMap.put(cur, new TreeSet<>());
				}
				int k = 0;
				//TODO for循环本身就是根据排序后的顺序来遍历！
				//TODO 前缀树的节点对应的有序表遍历 默认有序
				for (WordCount wc : nodeRankMap.get(cur)) {
					if (k == top) {
						break;
					}
					ans.add(wc.word);
					k++;
				}
			}
			/*
			*TODO
			* 假设path = "abcde"  输入c = #
			* 那么  abcde 就要增加词频
			* c == '#' && !path.equals("")
			* 表示当前输入的是 '#' 之前的输入历史不为空
			* */
			if (c == '#' && !path.equals("")) {
				// 真的有一个，有效字符串需要加入！path
				if (!wordCountMap.containsKey(path)) {
					//TODO 说明 前缀树没有这个节点 需要新建一个 ，0是因为下面的代码统一调整词频
					wordCountMap.put(path, new WordCount(path, 0));
				}
				/*
				*TODO
				* 有序表内部的小对象，该小对象参与排序的指标数据改变
				* 但是有序表并不会自动刷新
				* 所以，删掉老的，加新的！
				* eg:
				* 有个前缀树的节点对应的有序表 数据项有 <abc,7> <abd,1> <abe,3>
				* 现在abd词频+1
				* 那么 先删除 也就是只剩下<abc,7>  <abe,3>
				* 再加入 也就是 <abc,7> <abd,2> <abe,3>
				* */
				WordCount oldOne = wordCountMap.get(path);
				WordCount newOne = new WordCount(path, oldOne.count + 1);
				//TODO 通过father指针 向上回溯 沿途的前缀树节点的对应的有序表 更新
				while (cur != root) {
					nodeRankMap.get(cur).remove(oldOne);
					nodeRankMap.get(cur).add(newOne);
					cur = cur.father;
				}
				//TODO 词频表此时更新
				wordCountMap.put(path, newOne);
				//TODO 历史记录清空
				path = "";
				//TODO 代码执行到这里  cur 回到root了 cur也清空了
			}
			return ans;
		}

	}

}
