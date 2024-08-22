package algorithmbasic2020_master.class12;

import java.util.ArrayList;

public class Code02_IsBST {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static boolean isBST1(Node head) {
		if (head == null) {
			return true;
		}
		ArrayList<Node> arr = new ArrayList<>();
		in(head, arr);
		for (int i = 1; i < arr.size(); i++) {
			if (arr.get(i).value <= arr.get(i - 1).value) {
				return false;
			}
		}
		return true;
	}

	public static void in(Node head, ArrayList<Node> arr) {
		if (head == null) {
			return;
		}
		in(head.left, arr);
		arr.add(head);
		in(head.right, arr);
	}

	public static boolean isBST2(Node head) {
		if (head == null) {
			return true;
		}
		return process(head).isBST;
	}

	public static class Info {
		public boolean isBST;
		public int max;
		public int min;

		public Info(boolean i, int ma, int mi) {
			isBST = i;
			max = ma;
			min = mi;
		}

	}

	public static Info process(Node x) {
		if (x == null) {//TODO base case
			return null;//TODO 既然没有值 就设置为null 让上一层处理null
		}
		//TODO 向左子树 要信息
		Info leftInfo = process(x.left);
		//TODO 向右子树 要信息
		Info rightInfo = process(x.right);
		int max = x.value;//TODO 假设当前节点的树的当前最大值 是当前节点
		if (leftInfo != null) {//TODO 如果左子树有返回信息  就更新最大值
			max = Math.max(max, leftInfo.max);
		}
		if (rightInfo != null) {//TODO 如果右子树有返回信息  就更新最大值
			max = Math.max(max, rightInfo.max);
		}
		int min = x.value;
		if (leftInfo != null) {//TODO 如果左子树有返回信息  就更新最小值
			min = Math.min(min, leftInfo.min);
		}
		if (rightInfo != null) {//TODO 如果右子树有返回信息  就更新最小值
			min = Math.min(min, rightInfo.min);
		}
		boolean isBST = true;//TODO 假设当前节点的树是搜索二叉树
		if (leftInfo != null && !leftInfo.isBST) {
			isBST = false;
		}
		if (rightInfo != null && !rightInfo.isBST) {
			isBST = false;
		}
		if (leftInfo != null && leftInfo.max >= x.value) {
			isBST = false;
		}
		if (rightInfo != null && rightInfo.min <= x.value) {
			isBST = false;
		}
		return new Info(isBST, max, min);
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

	public static void main(String[] args) {
		int maxLevel = 4;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
			if (isBST1(head) != isBST2(head)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
