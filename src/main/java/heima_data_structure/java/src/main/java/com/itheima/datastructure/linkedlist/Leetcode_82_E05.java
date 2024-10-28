package heima_data_structure.java.src.main.java.com.itheima.datastructure.linkedlist;

/**
 * 有序链表去重
 */
public class Leetcode_82_E05 {

    /**
     * 方法1
     *
     * <pre>
     *  TODO 递归函数负责返回：从当前节点（我）开始，完成去重的链表
     *      1. 若我与 next 重复，一直找到下一个不重复的节点，以它的返回结果为准
     *      2. 若我与 next 不重复，返回我，同时更新 next
     *  eg: 1 -> 1 -> 2 -> 3 -> 3 -> null
     *   deleteDuplicates1(1 -> 1 -> 2 -> 3 -> 3)
     *      │
     *      ├── 重复，跳过所有值为 1 的节点，继续调用 deleteDuplicates1(2 -> 3 -> 3)
     *      │   │
     *      │   └── deleteDuplicates1(2 -> 3 -> 3)  // 不重复，保留 2，递归调用 deleteDuplicates1(3 -> 3)
     *      │       │
     *      │       └── 重复，跳过所有值为 3 的节点，继续调用 deleteDuplicates1(null)
     *      │           │
     *      │           └── deleteDuplicates1(null) -> null
     *      │               ↑
     *      │           返回 null
     *      │               ↑
     *      │       返回 2 -> null
     *      │           ↑
     *      └── 返回 2 -> null
     *  递的过程（向下递归）
     *      第1层调用：deleteDuplicates1(1 -> 1 -> 2 -> 3 -> 3)
     *      当前节点值为 1，且 p.val == p.next.val，进入 while 循环，跳过所有值为 1 的节点，将指针 x 移动到第一个与 1 不同的节点 2。
     *      递归调用 deleteDuplicates1(2 -> 3 -> 3)。
     *      第2层调用：deleteDuplicates1(2 -> 3 -> 3)
     *      当前节点值为 2，且 p.val != p.next.val（值 2 和 3 不相等），因此保留节点 2。
     *      设置 p.next = deleteDuplicates1(3 -> 3) 以继续递归调用。
     *      第3层调用：deleteDuplicates1(3 -> 3)
     *      当前节点值为 3，且 p.val == p.next.val，进入 while 循环，跳过所有值为 3 的节点，将指针 x 移动到 null。
     *      递归调用 deleteDuplicates1(null)。
     *      第4层调用（基准条件）：deleteDuplicates1(null)
     *      当前节点为 null，达到链表末尾，返回 null。
     *      此时，递的过程完成，进入逐层的“归”过程。
     *  归的过程（逐层返回并构造链表）
     *      第4层返回：deleteDuplicates1(null) 返回 null，开始逐层回溯。
     *      第3层返回：deleteDuplicates1(3 -> 3) 返回 null，因为我们跳过了所有值为 3 的节点。
     *      第2层返回：deleteDuplicates1(2 -> 3 -> 3) 将节点 2 的 next 指针指向 null（第3层返回的结果），并返回 2 -> null。
     *      第1层返回：deleteDuplicates1(1 -> 1 -> 2 -> 3 -> 3) 返回 2 -> null，因为我们跳过了所有值为 1 的节点。
     * </pre>
     *
     * @param p
     * @return
     */
    public ListNode deleteDuplicates1(ListNode p) {
        // base case：如果当前节点为空，或当前节点是最后一个节点
        // 返回当前节点（链表末尾或链表为空时直接返回）。
        if (p == null || p.next == null) {
            // 归：到达链表末尾，开始返回
            return p;
        }
        // 检查当前节点和下一个节点的值是否相等
        if (p.val == p.next.val) {
            // 从当前被遍历的节点 的下一个再下一个节点开始。因为 当前被遍历的节点和其下一个节点的值相同
            ListNode x = p.next.next;
            // 一直找,直到找到下一个不重复的节点
            while (x != null && x.val == p.val) {
                x = x.next;
            }
            // 递：继续深入，处理剩余的链表

            // 递归调用 deleteDuplicates1(x)，x 是第一个与 p.val 不同的节点
            // 开始处理 x 之后的链表
            return deleteDuplicates1(x); // x 就是与 p 取值不同的节点
        } else {
            // 递：递归处理子链表，更新 p.next

            // 当前节点与下一个节点值不同，保留当前节点
            // 递归调用 deleteDuplicates1(p.next)，处理下一个节点
            p.next = deleteDuplicates1(p.next);
            // 归：当前节点 p 是唯一的，返回它以构建最终的链表
            return p;
        }
    }

    /*
        p1 p2 p3
        s, 1, 1, 1, 2, 3, null

        p1 p2    p3
        s, 1, 1, 1, 2, 3, null

        p1 p2       p3
        s, 1, 1, 1, 2, 3, null

        p1 p3
        s, 2, 3, null

        p1 p2 p3
        s, 2, 3, null

           p1 p2 p3
        s, 2, 3, null
     */
    // 方法2
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode s = new ListNode(-1, head);
        ListNode p1 = s;
        ListNode p2, p3;
        while ((p2 = p1.next) != null
                && (p3 = p2.next) != null) {
            if (p2.val == p3.val) {
                while ((p3 = p3.next) != null
                        && p3.val == p2.val) {
                }
                // p3 找到了不重复的值
                p1.next = p3;
            } else {
                p1 = p1.next;
            }
        }
        return s.next;
    }

    public static void main(String[] args) {
        ListNode head = ListNode.of(1, 2, 3, 3, 4, 4, 5);
//        ListNode head = ListNode.of(1, 1, 1, 2, 3);
        System.out.println(head);
        System.out.println(new Leetcode_82_E05().deleteDuplicates(head));
    }
}
