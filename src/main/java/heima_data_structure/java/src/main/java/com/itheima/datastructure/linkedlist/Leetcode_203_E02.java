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
     * <pre>
     *  TODO 递归含义： 从当前节点（我）开始，完成删除的子链表 ，返回删除之后的子链表头结点
     *  eg:
     *   1 -> 2 -> 3 -> 4
     *   删除2
     *    removeElements(1 -> 2 -> 3 -> 4, 2)
     *      │
     *      ├── 不匹配，继续递归调用
     *      │   │
     *      │   └─ removeElements(2 -> 3 -> 4, 2)
     *      │       │
     *      │       ├── 匹配，跳过当前节点，继续递归调用
     *      │       │   │
     *      │       │   └─ removeElements(3 -> 4, 2)
     *      │       │       │
     *      │       │       ├── 不匹配，继续递归调用
     *      │       │       │   │
     *      │       │       │   └─ removeElements(4, 2)
     *      │       │       │       │
     *      │       │       │       ├── 不匹配，继续递归调用
     *      │       │       │       │   │
     *      │       │       │       │   └─ removeElements(null, 2)
     *      │       │       │       │       │
     *      │       │       │       │       └─ 返回 null
     *      │       │       │       │
     *      │       │       │       └─ 返回 4 -> null
     *      │       │       │
     *      │       │       └─ 返回 3 -> 4 -> null
     *      │       │
     *      │       └─ 返回 3 -> 4 -> null
     *      │
     *      └─ 返回 1 -> 3 -> 4 -> null
     *   调用过程
     *      初始调用：removeElements(head, 2)
     *      当前节点值为 1，不等于 2。
     *      递归调用 removeElements(head.next, 2)，并将 head.next 更新为这个结果。
     *
     *      第二次调用：removeElements(2 -> 3 -> 4, 2)
     *      当前节点值为 2，等于 2。
     *      跳过当前节点，直接递归调用 removeElements(3 -> 4, 2)。
     *
     *      第三次调用：removeElements(3 -> 4, 2)
     *      当前节点值为 3，不等于 2。
     *      递归调用 removeElements(4, 2)，并将 head.next 更新为这个结果。
     *
     *      第四次调用：removeElements(4, 2)
     *      当前节点值为 4，不等于 2。
     *      递归调用 removeElements(null, 2)，并将 head.next 更新为这个结果。
     *
     *      第五次调用（基准情况）：removeElements(null, 2)
     *      当前节点是 null，表示已到达链表末尾。
     *      返回 null。
     * </pre>
     * @param p   链表头
     * @param val 目标值
     * @return 删除后的链表头
     */
    public ListNode removeElements(ListNode p, int val) {
        // base case 如果当前节点是null 说明来到链表的结尾了
        if (p == null) {
            return null;
        }
        // 若被遍历的节点与 v 相等，应该返回下一个节点递归结果
        if (p.val == val) {
            // 直接返回 removeElements(p.next, val)，即跳过当前节点，将后续节点中的值为 val 的节点继续删除
            return removeElements(p.next, val);
        } else {// 若我与 v 不等，则将当前被遍历的节点保留在链表中 应该返回当前被遍历的节点
            // 并将当前被遍历的节点的 next 应该更新 为 后续删过的子链表的链头
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
