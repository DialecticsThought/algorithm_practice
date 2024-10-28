package heima_data_structure.java.src.main.java.com.itheima.datastructure.linkedlist;

/**
 * 根据值删除节点
 */
public class Leetcode_203_E02 {
    /**
     * 方法1
     * <pre>
     *   图中 s 代表 sentinel 哨兵（如果不加哨兵，则删除第一个节点要特殊处理），例如要删除 6
     *      p1   p2
     *      s -> 1 -> 2 -> 6 -> 3 -> 6 -> null
     *   如果 p2 不等于目标，则 p1，p2 不断后移
     *        	 p1   p2
     *      s -> 1 -> 2 -> 6 -> 3 -> 6 -> null
     *
     *      	 	  p1   p2
     *      s -> 1 -> 2 -> 6 -> 3 -> 6 -> null
     *    p2 == 6，删除它，注意 p1 此时保持不变，p2 后移
     *         	 	  p1   p2
     *      s -> 1 -> 2 -> 3 -> 6 -> null
     *    p2 不等于目标，则 p1，p2 不断后移
     *         	 	  	   p1   p2
     *      s -> 1 -> 2 -> 3 -> null
     *    p2 == null 退出循环
     * </pre>
     *
     * @param head 链表头
     * @param val  目标值
     * @return 删除后的链表头
     */
    public ListNode removeElements1(ListNode head, int val) {
        // 哨兵 相当于一个新的头结点
        ListNode s = new ListNode(-1, head);
        ListNode p1 = s;
        ListNode p2 = s.next;
        while (p2 != null) {
            if (p2.val == val) {
                // 删除, p2 向后平移
                p1.next = p2.next;// 因为被删节点是p2，删除之后 p1的下一个节点应该是原始p2节点的下一个节点
                p2 = p2.next;
            } else {
                // p1 p2 向后平移
                p1 = p1.next;
                p2 = p2.next;
            }
        }
        return s.next;
    }

    /**
     * 方法2
     *
     * @param p   链表头
     * @param val 目标值
     * @return 删除后的链表头
     */
    public ListNode removeElements(ListNode p, int val) {
        if (p == null) {
            return null;
        }
        if (p.val == val) {
            return removeElements(p.next, val);
        } else {
            p.next = removeElements(p.next, val);
            return p;
        }
    }

    public static void main(String[] args) {
        ListNode head = ListNode.of(1, 2, 6, 3, 6);
//        ListNode head = ListNode.of(7, 7, 7, 7);
        System.out.println(head);
        System.out.println(new Leetcode_203_E02()
                .removeElements(head, 6));
    }
}
