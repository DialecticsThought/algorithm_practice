package heima_data_structure.java.src.main.java.com.itheima.datastructure.linkedlist;

/**
 * 有序链表去重
 */
public class Leetcode_83_E04 {

    /**
     * 方法1
     * <pre>
     *  eg: 1 -> 1 -> 2 -> 3 -> 3 -> null
     *   初始情况
     *      p1   p2
     *      1 -> 1 -> 2 -> 3 -> 3 -> null
     *   p1.val == p2.val 那么删除 p2，注意 p1 此时保持不变
     *      p1   p2
     *      1 -> 2 -> 3 -> 3 -> null
     *   p1.val != p2.val 那么 p1，p2 向后移动
     *           p1   p2
     *      1 -> 2 -> 3 -> 3 -> null
     *   p1.val != p2.val 那么 p1，p2 向后移动
     *                p1   p2
     *      1 -> 2 -> 3 -> 3 -> null
     *   p1.val == p2.val 那么删除 p2
     *                  p1   p2
     *      1 -> 2 -> 3 -> null
     *   当 p2 == null 退出循环
     * </pre>
     * @param head
     * @return
     */
    public ListNode deleteDuplicates1(ListNode head) {
        // base case 节点数 < 2
        if (head == null || head.next == null) {
            return head;
        }
        // 节点数 >= 2
        ListNode p1 = head;
        ListNode p2;
        while ((p2 = p1.next) != null) {
            if (p1.val == p2.val) {
                // 删除 p2
                p1.next = p2.next;
            } else {
                // 向后平移
                p1 = p1.next;
            }
        }
        return head;
    }

    /**
     * 方法2
     * <pre>
     *  TODO 递归函数负责返回：从当前节点（我）开始，完成去重的链表
     *      1. 若我与 next 重复，返回 next
     *      2. 若我与 next 不重复，返回我，但 next 应当更新
     *  eg: 1 -> 1 -> 2 -> 3 -> 3
     *  deleteDuplicates(1 -> 1 -> 2 -> 3 -> 3)
     *      │
     *      ├── deleteDuplicates(1 -> 2 -> 3 -> 3)  // p.val == p.next.val, 跳过第一个 1
     *      │   │
     *      │   └── deleteDuplicates(2 -> 3 -> 3)    // p.val != p.next.val, 保留 1, 更新 p.next
     *      │   │   │
     *      │   │   └── deleteDuplicates(3 -> 3)     // p.val != p.next.val, 保留 2, 更新 p.next
     *      │   │   │    │
     *      │   │   │    └── deleteDuplicates(3)      // p.val == p.next.val, 跳过第一个 3
     *      │   │   │    │    │
     *      │   │   │    │    └── deleteDuplicates(null)
     *      │   │   │    │    │    │
     *      │   │   │    │    │    └── 返回 null // 基准条件，到达链表末尾
     *      │   │   │    │    │
     *      │   │   │    │    └── 返回 3 -> null
     *      │   │   │    │
     *      │   │   │    └── 返回 3 -> null
     *      │   │   │
     *      │   │   └── 返回 2 -> 3 -> null
     *      │   │
     *      │   └── 返回 1 -> 2 -> 3 -> null
     *      │
     *      └── 返回 1 -> 2 -> 3 -> null
     * 第一层：调用 deleteDuplicates(1 -> 1 -> 2 -> 3 -> 3)，发现 1 == 1，因此跳过第一个节点 1，递归调用 deleteDuplicates(1 -> 2 -> 3 -> 3)。
     * 第二层：调用 deleteDuplicates(1 -> 2 -> 3 -> 3)，发现 1 != 2，保留当前节点 1，然后递归调用 deleteDuplicates(2 -> 3 -> 3)。
     * 第三层：调用 deleteDuplicates(2 -> 3 -> 3)，发现 2 != 3，保留当前节点 2，递归调用 deleteDuplicates(3 -> 3)。
     * 第四层：调用 deleteDuplicates(3 -> 3)，发现 3 == 3，跳过第一个节点 3，递归调用 deleteDuplicates(3)。
     * 第五层：调用 deleteDuplicates(3)，发现 p.next == null，返回当前节点 3（基准条件）
     * </pre>
     * @param p
     * @return
     */
    public ListNode deleteDuplicates(ListNode p) {
        // base case 当前被遍历到的节点 是 null 或者是链表的最后一个节点
        if (p == null || p.next == null) {
            return p;
        }
        // 这里比较当前节点 p 的值 p.val 与其下一个节点 p.next 的值 p.next.val

        // 如果相等，说明 p 和 p.next 是重复节点
        // 因此跳过当前节点 p，直接递归调用 deleteDuplicates(p.next)，继续检查后续节点
        if (p.val == p.next.val) {
            return deleteDuplicates(p.next);
        } else {// 如果 p.val 和 p.next.val 不相等，说明当前节点 p 是唯一的或非重复节点，需要保留
            // 将 p.next 更新为 deleteDuplicates(p.next) 的返回结果，以确保删除后的链表与当前节点正确连接
            p.next = deleteDuplicates(p.next);
            // 最后返回 p，以便上层递归调用可以保持链表结构
            return p;
        }
    }

    public static void main(String[] args) {
        ListNode head = ListNode.of(1, 1, 2, 3, 3);
        System.out.println(head);
        System.out.println(new Leetcode_83_E04().deleteDuplicates(head));
    }
}
