package code_for_great_offer.class50;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Problem_0588_DesignInMemoryFileSystem {

	class FileSystem {
		//TODO 前缀树
		public class Node {
			//TODO 文件名、目录名
			public String name;
			//TODO content == null 意味着当前节点是目录
			// content != null 意味着当前节点是文件，并且可以写入数据
			public StringBuilder content;
			/*
			*TODO
			* 用treemap的原因
			* eg: 有个目录a，下面有3个子节点（不关心是目录还是文件）
			* 分别是 c，ek，b 但是返回的时候用需要字典序（排序）
			* 假设b节点下 还有子节点x  c节点下还有子节点y
			* 那么就是用有序表
			* <b,x>
			* <c,y>
			* */
			public TreeMap<String, Node> nexts;

			//TODO 构造目录
			public Node(String n) {
				name = n;
				content = null;
				nexts = new TreeMap<>();
			}

			//TODO 构造文件，c是文件内容
			public Node(String n, String c) {
				name = n;
				content = new StringBuilder(c);
				nexts = new TreeMap<>();
			}

		}

		public Node head;

		public FileSystem() {
			head = new Node("");
		}

		//TODO 调用该函数 返回该路径下有多少目录
		public List<String> ls(String path) {
			List<String> ans = new ArrayList<>();
			Node cur = head;
			String[] parts = path.split("/");
			int n = parts.length;
			for (int i = 1; i < n; i++) {
				if (!cur.nexts.containsKey(parts[i])) {
					return ans;
				}
				cur = cur.nexts.get(parts[i]);
			}
			/*
			* for循环之后 cur结束了！来到path最后的节点，该返回了
			* ls a/b/c  cur 来到c目录
			* 如果c是目录，那么就要返回c下面所有的东西！
			* 如果c是文件，那么就值返回c
			* */
			if (cur.content == null) {
				ans.addAll(cur.nexts.keySet());
			} else {
				ans.add(cur.name);
			}
			return ans;
		}

		public void mkdir(String path) {
			Node cur = head;
			String[] parts = path.split("/");
			int n = parts.length;
			for (int i = 1; i < n; i++) {
				//TODO 如果当前节点没有节点(目录)就新建 有目录的就复用
				if (!cur.nexts.containsKey(parts[i])) {
					cur.nexts.put(parts[i], new Node(parts[i]));
				}
				cur = cur.nexts.get(parts[i]);
			}
		}
		//TODO 把内容写入到文件里去
		public void addContentToFile(String path, String content) {
			Node cur = head;
			String[] parts = path.split("/");
			int n = parts.length;
			//TODO 来到倒数第二个节点 因为i < n - 1
			for (int i = 1; i < n - 1; i++) {
				if (!cur.nexts.containsKey(parts[i])) {
					cur.nexts.put(parts[i], new Node(parts[i]));
				}
				cur = cur.nexts.get(parts[i]);
			}
			//TODO 来到的是倒数第二的节点了！注意for！
			if (!cur.nexts.containsKey(parts[n - 1])) {
				cur.nexts.put(parts[n - 1], new Node(parts[n - 1], ""));
			}
			cur.nexts.get(parts[n - 1]).content.append(content);
		}
		//TODO 从文件读取出内容
		public String readContentFromFile(String path) {
			Node cur = head;
			String[] parts = path.split("/");
			int n = parts.length;
			for (int i = 1; i < n; i++) {
				if (!cur.nexts.containsKey(parts[i])) {
					cur.nexts.put(parts[i], new Node(parts[i]));
				}
				cur = cur.nexts.get(parts[i]);
			}
			return cur.content.toString();
		}
	}

}
