package heima_data_structure.java.src.main.java.com.itheima.datastructure.linkedlist;

/**
 * 查找链表中间节点
 */
public class Leetcode_876_E08 {

    /**
     * <pre>
     *   利用快慢指针
     *      慢指针一次走一步
     *      快指针一次走两步
     *      初始情况：快慢指针都在初始节点
     *
     *      1.针对奇数节点
     *         p1
     *         p2
     *         1   2   3   4   5   null
     *
     *             p1
     *                 p2
     *         1   2   3   4   5   null
     *
     *                   p1
     *                         p2
     *         1   2   3   4   5   null
     *         此时 快指针p2所指向的节点的next指针已经是null，说明p1已经指向了 需要指向的中间节点
     *
     *         2.针对奇数节点
     *         p1
     *         p2
     *         1   2   3   4   5   6   null
     *
     *             p1
     *                 p2
     *         1   2   3   4   5   6   null
     *
     *                 p1
     *                         p2
     *         1   2   3   4   5   6   null
     *
     *                     p1
     *                                 p2
     *         1   2   3   4   5   6   null
     *
     *         此时 快指针p2所指向的节点已经是null，说明p1已经指向了 需要指向的中间节点
     * </pre>
     *
     * @param head 头节点
     * @return 中间节点
     */
    public ListNode middleNode(ListNode head) {
        ListNode p1 = head;
        ListNode p2 = head;
        while (p2 != null && p2.next != null) {
            p1 = p1.next;
            p2 = p2.next;
            p2 = p2.next;
        }
        return p1;
    }

    public static void main(String[] args) {
        ListNode head1 = ListNode.of(1, 2, 3, 4, 5);
        System.out.println(new Leetcode_876_E08().middleNode(head1));

        ListNode head2 = ListNode.of(1, 2, 3, 4, 5, 6);
        System.out.println(new Leetcode_876_E08().middleNode(head2));
    }
}
