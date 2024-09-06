package code_for_great_offer.class33;

/*
* 有一个单链表的head，我们想删除它其中的一个节点node。
给你一个需要删除的节点node。你将无法访问第一个节点head。
链表的所有值都是 唯一的，并且保证给定的节点node不是链表中的最后一个节点。
删除给定的节点。注意，删除节点并不是指从内存中删除它。这里的意思是：
给定节点的值不应该存在于链表中。
链表中的节点数应该减少 1。
node前面的所有值顺序相同。
node后面的所有值顺序相同。
自定义测试：
对于输入，你应该提供整个链表head和要给出的节点node。node不应该是链表的最后一个节点，而应该是链表中的一个实际节点。
我们将构建链表，并将节点传递给你的函数。
输出将是调用你函数后的整个链表。
来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/delete-node-in-a-linked-list
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
* */
public class LeetCode_0237_DeleteNodeInLinkedList {

	public static class ListNode {
		int val;
		ListNode next;
	}
	/*
	*TODO
	* 这个做法 不能作用在最后一个节点
	* 就是把被删除的节点的下一个节点的值 赋给被删除节点上 然后把被删除的节点的下一个节点删除掉
	* */
	public void deleteNode(ListNode node) {
		node.val = node.next.val;
		node.next = node.next.next;
	}

}
