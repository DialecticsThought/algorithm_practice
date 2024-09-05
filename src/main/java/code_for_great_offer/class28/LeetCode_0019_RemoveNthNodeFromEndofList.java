package code_for_great_offer.class28;

/*
* 19. 删除链表的倒数第 N 个结点
给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
输入：head = [1,2,3,4,5], n = 2
输出：[1,2,3,5]
示例 2：
输入：head = [1], n = 1
输出：[]
示例 3：
输入：head = [1,2], n = 1
输出：[1]
* */
public class LeetCode_0019_RemoveNthNodeFromEndofList {

    public static class ListNode {
        public int val;
        public ListNode next;

        public ListNode(int i, ListNode head) {
        }
    }

    /*
     * eg: 17个节点 删除倒数第8个
     * 找到 倒数第9个元素
     * 有两个指针
     * 初始：第一个指针 指向 第一个元素 第二个指针 指向 第9个元素
     * 每次遍历 2个指正 同时后移
     * 直到 第二个指针 指向最后一个元素 那么第一个指针就是要删除的节点的前一个元素
     * */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode cur = head;
        ListNode pre = null;
        while (cur != null) {
            n--;
            if (n == -1) {
                pre = head;
            }
            if (n < -1) {
                pre = pre.next;
            }
            cur = cur.next;
        }
        if (n > 0) {
            return head;
        }
        if (pre == null) {
            return head.next;
        }
        pre.next = pre.next.next;
        return head;
    }


    public static ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode fast = head;
        //TODO dummy的作用是删除头结点的时候 为了方便
        ListNode dummy =  new ListNode(0, head);
        ListNode slow = dummy;

        for (int i = 0; i < n; ++i) {
            fast = fast.next;
        }

        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        ListNode ans = dummy.next;
        return ans;
    }

}
