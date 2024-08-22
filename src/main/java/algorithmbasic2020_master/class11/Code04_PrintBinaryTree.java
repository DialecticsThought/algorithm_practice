package algorithmbasic2020_master.class11;

public class Code04_PrintBinaryTree {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static void printTree(Node head) {
		System.out.println("Binary Tree:");
		printInOrder(head, 0, "H", 17);
		System.out.println();
	}
	/*
	 * 先调用右子树 再轮到左子树 因为 打印是把一棵树逆时针反转90°打印出来的
	 * head 一开始是根节点 后面是左右子节点
	 * height表示高度
	 * to 表示身份  右子树 是 "v"  左子树 是 "^"
	 * 一个节点如何找到自己的父节点 要利用身份
	 *
	 * len 处理当前节点怎么打印 如何确定一行输出多少个数
	 * 该节点打印的时候前面要预留多少空格 后面要打印多少空格
	 * 一个节点的层数越高 也就是height越大  该节点打印的时候前面要预留空格就越多
	 *
	 * 不管这个节点的value本身长度占多少 统一设为17
	 * eg： 3333 这个值前面留6个空格 后面留7个空格 确保17个长度
	 *
	 * 除了17个长度的值  要补上空格 这个空格的长度是根据height和len决定
	 * eg:^-2147483648^ 这个值前面的空格 就是 height和len界定的
	 *                                         v66v
	 *                    v3v
	 *                                 ^55555555^
	 *   H1H
	 *               ^-222222222^
	 *                                                     v777v
	 *                                ^-2147483648^
	 * */
	public static void printInOrder(Node head, int height, String to, int len) {
		if (head == null) {
			return;
		}
		//先调用右子树 因为 打印是把一棵树逆时针反转90°打印出来的
		printInOrder(head.right, height + 1, "v", len);

		/*
		 * 处理 节点的value  value前面加上身份表示自己是左/右子节点 后面也是
		 * */
		String val = to + head.value + to;
		/*
		 * 得到 处理过后value的长度 因为要在value前后加上空格
		 * */
		int lenM = val.length();
		int lenL = (len - lenM) / 2;//左侧空格数
		int lenR = len - lenM - lenL;//右侧空格数
		val = getSpace(lenL) + val + getSpace(lenR);//最后要显示的结果
		System.out.println(getSpace(height * len) + val);

		printInOrder(head.left, height + 1, "^", len);
	}
	/*
	 * 打印空格
	 * 根据传入的num 打印出num个空格
	 * */
	public static String getSpace(int num) {
		String space = " ";
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < num; i++) {
			buf.append(space);
		}
		return buf.toString();
	}

	public static void main(String[] args) {
		Node head = new Node(1);
		head.left = new Node(-222222222);
		head.right = new Node(3);
		head.left.left = new Node(Integer.MIN_VALUE);
		head.right.left = new Node(55555555);
		head.right.right = new Node(66);
		head.left.left.right = new Node(777);
		printTree(head);

		head = new Node(1);
		head.left = new Node(2);
		head.right = new Node(3);
		head.left.left = new Node(4);
		head.right.left = new Node(5);
		head.right.right = new Node(6);
		head.left.left.right = new Node(7);
		printTree(head);

		head = new Node(1);
		head.left = new Node(1);
		head.right = new Node(1);
		head.left.left = new Node(1);
		head.right.left = new Node(1);
		head.right.right = new Node(1);
		head.left.left.right = new Node(1);
		printTree(head);

	}

}
