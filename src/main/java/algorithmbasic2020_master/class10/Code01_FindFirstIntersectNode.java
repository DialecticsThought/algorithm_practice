package algorithmbasic2020_master.class10;
/*
* 常见面试题
给定两个可能有环也可能无环的单链表，头节点head1和head2。
请实现一个函数，如果两个链表相交，请返回相交的第一个节点。如果不相交，返回null
【要求】
如果两个链表长度之和为N，时间复杂度请达到O(N)，额外空间复杂度请达到O(1)。
* */
public class Code01_FindFirstIntersectNode {

	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	public static Node getIntersectNode(Node head1, Node head2) {
		if (head1 == null || head2 == null) {
			return null;
		}
		Node loop1 = getLoopNode(head1);
		Node loop2 = getLoopNode(head2);
		if (loop1 == null && loop2 == null) {
			return noLoop(head1, head2);
		}
		if (loop1 != null && loop2 != null) {
			return bothLoop(head1, loop1, head2, loop2);
		}
		return null;
	}

	// 找到链表第一个入环节点，如果无环，返回null
	public static Node getLoopNode(Node head) {
		if (head == null || head.next == null || head.next.next == null) {
			return null;
		}
		// n1 慢  n2 快
		Node slow = head.next; // n1 -> slow
		Node fast = head.next.next; // n2 -> fast
		while (slow != fast) {
			//如果快指针走到底了 还是null的话 一定是无环
			if (fast.next == null || fast.next.next == null) {
				return null;
			}
			fast = fast.next.next;
			slow = slow.next;
		}
		//跳出循环的时候 说明快慢指针相遇了
		// slow fast  相遇
		//快指针重置回指向head
		fast = head; // n2 -> walk again from head
		while (slow != fast) {//通过循环重新让快慢指针相遇
			slow = slow.next;
			fast = fast.next;
		}
		return slow;//相遇点就是第一个入环的节点
	}

	// 如果两个链表都无环，返回第一个相交节点，如果不想交，返回null
	public static Node noLoop(Node head1, Node head2) {
		if (head1 == null || head2 == null) {
			return null;
		}
		Node cur1 = head1;
		Node cur2 = head2;
		int n = 0;//n是正数说明链表1更长 负数说明链表2更长
		while (cur1.next != null) {
			n++;
			cur1 = cur1.next;
		}
		while (cur2.next != null) {
			n--;
			cur2 = cur2.next;
		}
		//两个循环结束之后 一个指向end1 另一个end2
		if (cur1 != cur2) {//end1 != end2 一定不想交
			return null;
		}
		// n  :  链表1长度减去链表2长度的值
		cur1 = n > 0 ? head1 : head2; // 谁长，谁的头变成cur1
		cur2 = cur1 == head1 ? head2 : head1; // 谁短，谁的头变成cur2
		n = Math.abs(n);//n代表2个链表之间的长度差值
		while (n != 0) {//长链表先走 让n=0 差值为0
			n--;
			cur1 = cur1.next;
		}
		//让两个链表同时在相同的长度上遍历 直到两个链表遍历到相同的部分
		while (cur1 != cur2) {
			cur1 = cur1.next;
			cur2 = cur2.next;
		}
		return cur1;
	}

	// 两个有环链表，返回第一个相交节点，如果不想交返回null
	public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
		Node cur1 = null;
		Node cur2 = null;
		if (loop1 == loop2) {//表示和环没有关系了
			cur1 = head1;
			cur2 = head2;
			int n = 0;
			while (cur1 != loop1) {
				n++;
				cur1 = cur1.next;
			}
			while (cur2 != loop2) {
				n--;
				cur2 = cur2.next;
			}
			cur1 = n > 0 ? head1 : head2;
			cur2 = cur1 == head1 ? head2 : head1;
			n = Math.abs(n);
			while (n != 0) {
				n--;
				cur1 = cur1.next;
			}
			while (cur1 != cur2) {
				cur1 = cur1.next;
				cur2 = cur2.next;
			}
			return cur1;
		} else {
			cur1 = loop1.next;
			//也就是loop1沿着环遍历最终便利到自己了 说明两个链表不在一起
			while (cur1 != loop1) {
				if (cur1 == loop2) {//遍历到loop2节点了
					return loop1;
				}
				cur1 = cur1.next;
			}
			return null;
		}
	}

	public static void main(String[] args) {
		// 1->2->3->4->5->6->7->null
		Node head1 = new Node(1);
		head1.next = new Node(2);
		head1.next.next = new Node(3);
		head1.next.next.next = new Node(4);
		head1.next.next.next.next = new Node(5);
		head1.next.next.next.next.next = new Node(6);
		head1.next.next.next.next.next.next = new Node(7);

		// 0->9->8->6->7->null
		Node head2 = new Node(0);
		head2.next = new Node(9);
		head2.next.next = new Node(8);
		head2.next.next.next = head1.next.next.next.next.next; // 8->6
		System.out.println(getIntersectNode(head1, head2).value);

		// 1->2->3->4->5->6->7->4...
		head1 = new Node(1);
		head1.next = new Node(2);
		head1.next.next = new Node(3);
		head1.next.next.next = new Node(4);
		head1.next.next.next.next = new Node(5);
		head1.next.next.next.next.next = new Node(6);
		head1.next.next.next.next.next.next = new Node(7);
		head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

		// 0->9->8->2...
		head2 = new Node(0);
		head2.next = new Node(9);
		head2.next.next = new Node(8);
		head2.next.next.next = head1.next; // 8->2
		System.out.println(getIntersectNode(head1, head2).value);

		// 0->9->8->6->4->5->6..
		head2 = new Node(0);
		head2.next = new Node(9);
		head2.next.next = new Node(8);
		head2.next.next.next = head1.next.next.next.next.next; // 8->6
		System.out.println(getIntersectNode(head1, head2).value);

	}

}
