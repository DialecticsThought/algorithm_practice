package algorithmbasic2020_master.class09;

import java.util.ArrayList;
/**
 * 快慢指针
 * 1）输入链表头节点，奇数长度返回中点，偶数长度返回上中点
 * 2) 输入链表头节点，奇数长度返回中点，偶数长度返回下中点
 * 3）输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个
 * 4）输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个
 *
 * *面试时链表解题的方法论
 * 1)对于笔试,不用太在乎空间复杂度，一切为了时间复杂度
 * 2)对于面试，时间复杂度依然放在第一位，但是一定要找到空间最省的方法
 * */
public class Code01_LinkedListMid {

	public static class Node {
		public int value;
		public Node next;

		public Node(int v) {
			value = v;
		}
	}
	//1）输入链表头节点，奇数长度返回中点，偶数长度返回上中点
	// head 头
	public static Node midOrUpMidNode(Node head) {
		/**
		* head节点为空
		* 只有head这个唯一节点
		* 只有两个点的时候
		* */
		if (head == null || head.next == null || head.next.next == null) {
			return head;
		}
		// 链表有3个点或以上 上面的if语句排除了3个点以内的情况
		Node slow = head.next;//慢指针是走一步 一开始来到头节点的后一个节点
		Node fast = head.next.next;//快指针是走两步 一开始来到头节点的后2个节点
		while (fast.next != null && fast.next.next != null) {
			/**
			* 如果是只有三个节点的情况 根本不会执行while循环
			* 如果是只有四个点的情况 也不会执行while循环
			* */
			slow = slow.next;
			fast = fast.next.next;
		}
		return slow;
	}
	//2) 输入链表头节点，奇数长度返回中点，偶数长度返回下中点
	public static Node midOrDownMidNode(Node head) {
		if (head == null || head.next == null) {
			return head;
		}
		Node slow = head.next;
		Node fast = head.next;
		while (fast.next != null && fast.next.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}
		return slow;
	}
	//3）输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个
	public static Node midOrUpMidPreNode(Node head) {
		if (head == null || head.next == null || head.next.next == null) {
			return null;
		}
		Node slow = head;//慢指针是走一步 一开始来到头节点
		Node fast = head.next.next;//快指针是走两步 一开始来到头节点的后2个节点
		while (fast.next != null && fast.next.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}
		return slow;
	}
	//4）输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个
	public static Node midOrDownMidPreNode(Node head) {
		if (head == null || head.next == null) {
			return null;
		}
		if (head.next.next == null) {
			return head;
		}
		Node slow = head;
		Node fast = head.next;
		while (fast.next != null && fast.next.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}
		return slow;
	}

	public static Node right1(Node head) {
		if (head == null) {
			return null;
		}
		Node cur = head;
		ArrayList<Node> arr = new ArrayList<>();
		while (cur != null) {
			arr.add(cur);
			cur = cur.next;
		}
		return arr.get((arr.size() - 1) / 2);
	}

	public static Node right2(Node head) {
		if (head == null) {
			return null;
		}
		Node cur = head;
		ArrayList<Node> arr = new ArrayList<>();
		while (cur != null) {
			arr.add(cur);
			cur = cur.next;
		}
		return arr.get(arr.size() / 2);
	}

	public static Node right3(Node head) {
		if (head == null || head.next == null || head.next.next == null) {
			return null;
		}
		Node cur = head;
		ArrayList<Node> arr = new ArrayList<>();
		while (cur != null) {
			arr.add(cur);
			cur = cur.next;
		}
		return arr.get((arr.size() - 3) / 2);
	}

	public static Node right4(Node head) {
		if (head == null || head.next == null) {
			return null;
		}
		Node cur = head;
		ArrayList<Node> arr = new ArrayList<>();
		while (cur != null) {
			arr.add(cur);
			cur = cur.next;
		}
		return arr.get((arr.size() - 2) / 2);
	}

	public static void main(String[] args) {
		Node test = null;
		test = new Node(0);
		test.next = new Node(1);
		test.next.next = new Node(2);
		test.next.next.next = new Node(3);
		test.next.next.next.next = new Node(4);
		test.next.next.next.next.next = new Node(5);
		test.next.next.next.next.next.next = new Node(6);
		test.next.next.next.next.next.next.next = new Node(7);
		test.next.next.next.next.next.next.next.next = new Node(8);

		Node ans1 = null;
		Node ans2 = null;

		ans1 = midOrUpMidNode(test);
		ans2 = right1(test);
		System.out.println(ans1 != null ? ans1.value : "无");
		System.out.println(ans2 != null ? ans2.value : "无");

		ans1 = midOrDownMidNode(test);
		ans2 = right2(test);
		System.out.println(ans1 != null ? ans1.value : "无");
		System.out.println(ans2 != null ? ans2.value : "无");

		ans1 = midOrUpMidPreNode(test);
		ans2 = right3(test);
		System.out.println(ans1 != null ? ans1.value : "无");
		System.out.println(ans2 != null ? ans2.value : "无");

		ans1 = midOrDownMidPreNode(test);
		ans2 = right4(test);
		System.out.println(ans1 != null ? ans1.value : "无");
		System.out.println(ans2 != null ? ans2.value : "无");

	}

}
