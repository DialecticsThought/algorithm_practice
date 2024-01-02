package code_for_great_offer.class05;

// 如果一个节点X，它左树结构和右树结构完全一样，那么我们说以X为头的子树是相等子树
// 给定一棵二叉树的头节点head，返回head整棵树上有多少棵相等子树
public class Code02_LeftRightSameTreeNumber {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int v) {
			value = v;
		}
	}

	// 时间复杂度O(N * logN)
	public static int sameNumber1(Node head) {
		if (head == null) {
			return 0;
		}
		//TODO 当前节点head的左子树的答案+ 当前节点head的y右子树的答案+head为头的子树是否为相等子树 是就再+1 否则 +0
		return sameNumber1(head.left) + sameNumber2(head.right) + (same(head.left, head.right) ? 1 : 0);
	}

	public static boolean same(Node h1, Node h2) {
		if (h1 == null ^ h2 == null) {//意思是 一个为空 一个不为空 就是不相等
			return false;
		}
		if (h1 == null && h2 == null) {//如果2个都是空 相同
			return true;
		}
		// 两个都不为空
		//TODO  h1 = h2 + h1的左子树=h1的左子树 + h1的右子树 = h2的右子树
		return h1.value == h2.value && same(h1.left, h2.left) && same(h1.right, h2.right);
	}

	// 时间复杂度O(N)
	public static int sameNumber2(Node head) {
		String algorithm = "SHA-256";
		Hash hash = new Hash(algorithm);
		return process(head, hash).ans;
	}

	public static class Info {
		public int ans;
		public String str;

		public Info(int a, String s) {
			ans = a;
			str = s;
		}
	}

	public static Info process(Node head, Hash hash) {
		if (head == null) {//如果子树是空 就向上返回 规定的hashcode
			return new Info(0, hash.hashCode("#,"));
		}
		//向左子树要答案
		Info l = process(head.left, hash);
		//向右子树要答案
		Info r = process(head.right, hash);
		//如果当前节点的左子树和右子树的hashcode相同 说明当前节点是相等子树 再加上左子树中有多少相等子树 再加上右子树中有多少相等子树
		int ans = (l.str.equals(r.str) ? 1 : 0) + l.ans + r.ans;
		//生成当前节点的hash code
		String str = hash.hashCode(String.valueOf(head.value) + "," + l.str + r.str);
		//向上返回当前节点的信息
		return new Info(ans, str);
	}

	public static Node randomBinaryTree(int restLevel, int maxValue) {
		if (restLevel == 0) {
			return null;
		}
		Node head = Math.random() < 0.2 ? null : new Node((int) (Math.random() * maxValue));
		if (head != null) {
			head.left = randomBinaryTree(restLevel - 1, maxValue);
			head.right = randomBinaryTree(restLevel - 1, maxValue);
		}
		return head;
	}

	public static void main(String[] args) {
		int maxLevel = 8;
		int maxValue = 4;
		int testTime = 100000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			Node head = randomBinaryTree(maxLevel, maxValue);
			int ans1 = sameNumber1(head);
			int ans2 = sameNumber2(head);
			if (ans1 != ans2) {
				System.out.println("出错了！");
				System.out.println(ans1);
				System.out.println(ans2);
			}
		}
		System.out.println("测试结束");

	}

}
