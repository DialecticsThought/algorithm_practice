package algorithmbasic2020_master.class11;

import java.util.LinkedList;
import java.util.Queue;

public class Code01_LevelTraversalBT {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int v) {
			value = v;
		}
	}
	/*
	 * 按层遍历 树
	 * */
	public static void level(Node head) {
		if (head == null) {
			return;
		}
		//创建一个队列
		Queue<Node> queue = new LinkedList<>();
		//先把根节点放入队列中
		queue.add(head);
		while (!queue.isEmpty()) {//循环终止条件是 队里为空
			//弹出队列中第一个节点
			Node cur = queue.poll();
			//打印该节点
			System.out.println(cur.value);
			//判断该节点是否有左右子节点 有的话 放入队列中
			if (cur.left != null) {
				queue.add(cur.left);
			}
			//判断该节点是否有右子节点 有的话 放入队列中
			if (cur.right != null) {
				queue.add(cur.right);
			}
		}
	}

	public static void main(String[] args) {
		Node head = new Node(1);
		head.left = new Node(2);
		head.right = new Node(3);
		head.left.left = new Node(4);
		head.left.right = new Node(5);
		head.right.left = new Node(6);
		head.right.right = new Node(7);

		level(head);
		System.out.println("========");
	}

}
