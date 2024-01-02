package algorithmbasic2020_master.class13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Code03_lowestAncestor {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static Node lowestAncestor1(Node head, Node o1, Node o2) {
		if (head == null) {
			return null;
		}
		// key的父节点是value
		HashMap<Node, Node> parentMap = new HashMap<>();
		parentMap.put(head, null);
		fillParentMap(head, parentMap);
		HashSet<Node> o1Set = new HashSet<>();
		Node cur = o1;
		o1Set.add(cur);
		while (parentMap.get(cur) != null) {
			cur = parentMap.get(cur);
			o1Set.add(cur);
		}
		cur = o2;
		while (!o1Set.contains(cur)) {
			cur = parentMap.get(cur);
		}
		return cur;
	}

	public static void fillParentMap(Node head, HashMap<Node, Node> parentMap) {
		if (head.left != null) {
			parentMap.put(head.left, head);
			fillParentMap(head.left, parentMap);
		}
		if (head.right != null) {
			parentMap.put(head.right, head);
			fillParentMap(head.right, parentMap);
		}
	}

	public static Node lowestAncestor2(Node head, Node a, Node b) {
		return process(head, a, b).ans;
	}
	/*
	 * TODO
	 *  这棵树上没有没有a
	 *  这棵树上没有没有b
	 *  这棵树上有没有a和b的汇聚点
	 * */
	public static class Info {
		public boolean findA;
		public boolean findB;
		public Node ans;

		public Info(boolean fA, boolean fB, Node an) {
			findA = fA;
			findB = fB;
			ans = an;
		}
	}

	public static Info process(Node x, Node a, Node b) {
		if (x == null) {
			return new Info(false, false, null);
		}
		//TODO 向x的左子树和右子树要信息
		Info leftInfo = process(x.left, a, b);
		Info rightInfo = process(x.right, a, b);
		/*
		* TODO
		*  如果根节点 等于a 或 左子树发现a 或 右子树发现a => 发现a
		*  如果根节点 等于b 或 左子树发现b 或 右子树发现b => 发现b
		* */
		boolean findA = (x == a) || leftInfo.findA || rightInfo.findA;
		boolean findB = (x == b) || leftInfo.findB || rightInfo.findB;
		Node ans = null;//TODO 答案先是空
		/*
		* TODO
		*  如果左子树的答案不为空  说明找到了最初汇聚点 那么整棵树的最低汇聚点 也是那个点
		*  如果右子树的答案不为空  说明找到了最初汇聚点 那么整棵树的最低汇聚点 也是那个点
		*  如果左子树的答案和右子树的答案 都是空 就查看当前节点 以当前节点最根的数 是否找到a和b 找到了 x就是答案本身
		* */
		if (leftInfo.ans != null) {
			ans = leftInfo.ans;
		} else if (rightInfo.ans != null) {
			ans = rightInfo.ans;
		} else {
			if (findA && findB) {
				ans = x;
			}
		}
		return new Info(findA, findB, ans);
	}

	// for test
	public static Node generateRandomBST(int maxLevel, int maxValue) {
		return generate(1, maxLevel, maxValue);
	}

	// for test
	public static Node generate(int level, int maxLevel, int maxValue) {
		if (level > maxLevel || Math.random() < 0.5) {
			return null;
		}
		Node head = new Node((int) (Math.random() * maxValue));
		head.left = generate(level + 1, maxLevel, maxValue);
		head.right = generate(level + 1, maxLevel, maxValue);
		return head;
	}

	// for test
	public static Node pickRandomOne(Node head) {
		if (head == null) {
			return null;
		}
		ArrayList<Node> arr = new ArrayList<>();
		fillPrelist(head, arr);
		int randomIndex = (int) (Math.random() * arr.size());
		return arr.get(randomIndex);
	}

	// for test
	public static void fillPrelist(Node head, ArrayList<Node> arr) {
		if (head == null) {
			return;
		}
		arr.add(head);
		fillPrelist(head.left, arr);
		fillPrelist(head.right, arr);
	}

	public static void main(String[] args) {
		int maxLevel = 4;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
			Node o1 = pickRandomOne(head);
			Node o2 = pickRandomOne(head);
			if (lowestAncestor1(head, o1, o2) != lowestAncestor2(head, o1, o2)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
