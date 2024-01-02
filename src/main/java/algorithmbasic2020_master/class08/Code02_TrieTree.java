package algorithmbasic2020_master.class08;

import java.util.HashMap;

// 该程序完全正确
public class Code02_TrieTree {

	public static class Node1 {
		public int pass;//TODO 表示该节点有多少String经过
		public int end;//TODO 表示有多少String 以该节点结束
		public Node1[] nexts;

		//TODO char tmp = 'b' 找b的分支 就是  ('b' - 'a') =2 ASCII
		public Node1() {
			pass = 0;
			end = 0;
			/*
			  TODO
			   0    a
			   1    b
			   2    c
			   ..   ..
			   25   z
			   nexts[i] == null   i方向的路不存在
			   nexts[i] != null   i方向的路存在
			  如果只有小写字母26个的话 一个节点最多有26个分支
			*/
			nexts = new Node1[26];
		}
	}

	public static class Trie1 {
		//TODO 只要留一个头节点
		private Node1 root;
		//TODO 初始化
		public Trie1() {
			root = new Node1();
		}

		public void insert(String word) {
			if (word == null) {
				return;
			}
			char[] str = word.toCharArray();//TODO 转成字符类型的数组
			/*
			*TODO
			* 每一个新加的节点都是从root开始的
			* 相当于 现在有一个引用箭头 指向了根节点
			* */
			Node1 node = root;
			//TODO 因为 要从头节点出加一个新的节点 也就是通过头节点的次数++
			node.pass++;
			int path = 0;
			// "abc"  0,1,2 三个下标就是i
			for (int i = 0; i < str.length; i++) { //TODO 从左往右遍历字符
				//TODO ASCII码相减 得到是哪一个分支
				path = str[i] - 'a'; //TODO 由字符，对应成走向哪条路
				if (node.nexts[path] == null) {//TODO 判断是否已经存在了该分支
					//TODO 不存在分支的话新建一个节点 当前引用箭头指向的是当前节点
					node.nexts[path] = new Node1();
				}
				//TODO  现在当前引用箭头指向 刚刚创建的节点
				node = node.nexts[path];
				//TODO 来到新的节点后 pass++ 表示 通过该节点的次数+1
				node.pass++;
			}
			//TODO 此时 退出循环的时候 箭头指向的是最有一个节点 end++
			//TODO  表示当前插入的字符串的结尾字符就是该节点
			node.end++;
		}

		public void delete(String word) {
			//TODO 先查询是否之前插入过
			if (search(word) != 0) {
				char[] chs = word.toCharArray();
				Node1 node = root;//TODO 一开始引用箭头指向根节点
				node.pass--;//TODO 箭头值的节点 要pass-1 减少一个通过次数
				int path = 0;
				for (int i = 0; i < chs.length; i++) {
					path = chs[i] - 'a';
					/*
					*TODO
					* 如果箭头指向的节点的下一个节点的pass--之后 = 0
					* 那么作为父节点（箭头指向的当前节点）的next指向null 防止内存泄漏
					* 删除 子节点
					* */
					if (--node.nexts[path].pass == 0) {
						node.nexts[path] = null;
						return;
					}
					node = node.nexts[path];
				}
				//没有一个节点的end-- 因为删除了一个节点 节点的末端数量-1
				node.end--;
			}
		}

		//TODO word这个单词之前加入过几次
		public int search(String word) {
			if (word == null) {
				return 0;
			}
			char[] chs = word.toCharArray();//TODO 字符数组化
			Node1 node = root;//TODO 一开始引用箭头指向根节点
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = chs[i] - 'a';//TODO 得到 字符串的第i个字符的路径
				/*
				* TODO 如果走着走着发现 没有该路径 说明没有插入过
				* */
				if (node.nexts[index] == null) {
					return 0;
				}
				//TODO 引用箭头不断指向下一个节点
				node = node.nexts[index];
			}
			return node.end;
		}

		// 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
		public int prefixNumber(String pre) {
			if (pre == null) {
				return 0;
			}
			char[] chs = pre.toCharArray();//字符数组化
			Node1 node = root;//一开始引用箭头指向根节点
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = chs[i] - 'a';
				/*
				 * 如果走着走着发现 没有该路径 说明没有插入过
				 * */
				if (node.nexts[index] == null) {
					return 0;
				}
				//引用箭头不断指向下一个节点
				node = node.nexts[index];
			}
			//退出循环了 就查看node.pass
			//表示通过该节点的次数
			return node.pass;
		}
	}

	public static class Node2 {
		public int pass;
		public int end;
		public HashMap<Integer, Node2> nexts;

		public Node2() {
			pass = 0;
			end = 0;
			nexts = new HashMap<>();
		}
	}

	public static class Trie2 {
		private Node2 root;

		public Trie2() {
			root = new Node2();
		}

		public void insert(String word) {
			if (word == null) {
				return;
			}
			char[] chs = word.toCharArray();
			Node2 node = root;
			node.pass++;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = (int) chs[i];
				if (!node.nexts.containsKey(index)) {
					node.nexts.put(index, new Node2());
				}
				node = node.nexts.get(index);
				node.pass++;
			}
			node.end++;
		}

		public void delete(String word) {
			if (search(word) != 0) {
				char[] chs = word.toCharArray();
				Node2 node = root;
				node.pass--;
				int index = 0;
				for (int i = 0; i < chs.length; i++) {
					index = (int) chs[i];
					if (--node.nexts.get(index).pass == 0) {
						node.nexts.remove(index);
						return;
					}
					node = node.nexts.get(index);
				}
				node.end--;
			}
		}

		// word这个单词之前加入过几次
		public int search(String word) {
			if (word == null) {
				return 0;
			}
			char[] chs = word.toCharArray();
			Node2 node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = (int) chs[i];
				if (!node.nexts.containsKey(index)) {
					return 0;
				}
				node = node.nexts.get(index);
			}
			return node.end;
		}

		// 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
		public int prefixNumber(String pre) {
			if (pre == null) {
				return 0;
			}
			char[] chs = pre.toCharArray();
			Node2 node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = (int) chs[i];
				if (!node.nexts.containsKey(index)) {
					return 0;
				}
				node = node.nexts.get(index);
			}
			return node.pass;
		}
	}

	public static class Right {

		private HashMap<String, Integer> box;

		public Right() {
			box = new HashMap<>();
		}

		public void insert(String word) {
			if (!box.containsKey(word)) {
				box.put(word, 1);
			} else {
				box.put(word, box.get(word) + 1);
			}
		}

		public void delete(String word) {
			if (box.containsKey(word)) {
				if (box.get(word) == 1) {
					box.remove(word);
				} else {
					box.put(word, box.get(word) - 1);
				}
			}
		}

		public int search(String word) {
			if (!box.containsKey(word)) {
				return 0;
			} else {
				return box.get(word);
			}
		}

		public int prefixNumber(String pre) {
			int count = 0;
			for (String cur : box.keySet()) {
				if (cur.startsWith(pre)) {
					count += box.get(cur);
				}
			}
			return count;
		}
	}

	// for test
	public static String generateRandomString(int strLen) {
		char[] ans = new char[(int) (Math.random() * strLen) + 1];
		for (int i = 0; i < ans.length; i++) {
			int value = (int) (Math.random() * 6);
			ans[i] = (char) (97 + value);
		}
		return String.valueOf(ans);
	}

	// for test
	public static String[] generateRandomStringArray(int arrLen, int strLen) {
		String[] ans = new String[(int) (Math.random() * arrLen) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = generateRandomString(strLen);
		}
		return ans;
	}

	public static void main(String[] args) {
		int arrLen = 100;
		int strLen = 20;
		int testTimes = 100000;
		for (int i = 0; i < testTimes; i++) {
			String[] arr = generateRandomStringArray(arrLen, strLen);
			Trie1 trie1 = new Trie1();
			Trie2 trie2 = new Trie2();
			Right right = new Right();
			for (int j = 0; j < arr.length; j++) {
				double decide = Math.random();
				if (decide < 0.25) {
					trie1.insert(arr[j]);
					trie2.insert(arr[j]);
					right.insert(arr[j]);
				} else if (decide < 0.5) {
					trie1.delete(arr[j]);
					trie2.delete(arr[j]);
					right.delete(arr[j]);
				} else if (decide < 0.75) {
					int ans1 = trie1.search(arr[j]);
					int ans2 = trie2.search(arr[j]);
					int ans3 = right.search(arr[j]);
					if (ans1 != ans2 || ans2 != ans3) {
						System.out.println("Oops!");
					}
				} else {
					int ans1 = trie1.prefixNumber(arr[j]);
					int ans2 = trie2.prefixNumber(arr[j]);
					int ans3 = right.prefixNumber(arr[j]);
					if (ans1 != ans2 || ans2 != ans3) {
						System.out.println("Oops!");
					}
				}
			}
		}
		System.out.println("finish!");

	}

}
