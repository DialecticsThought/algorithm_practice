package heima_data_structure.java.src.main.java.com.itheima.datastructure.linkedlist;

/**
 * 合并两个有序链表
 */
public class Leetcode_21_E06 {
    /**
     * 方法1
     * <pre>
     *      p1
     *      ↓
     *      1	3	8	9	null
     *      p2
     *      ↓
     *      2	4	null
     *      p
     *      ↓
     *      s	null
     *
     *      p1 和 p2 比大小 ，哪一个指向的值 小 p1.val < p2.val
     *      复制 p1指向的节点，然后让 s的next指针指向 被赋值的节点
     *      p1 向后移动一位
     *      p 向后移动一位
     *          p1
     *          ↓
     *      1	3	8	9	null
     *      p2
     *      ↓
     *      2	4	null
     *          p
     *          ↓
     *      s	1   null
     *
     *      p1 和 p2 比大小 ，哪一个指向的值 小 p2.val < p1.val
     *      复制 p2 指向的节点，然后让 s的next指针指向 被赋值的节点
     *      p2 向后移动一位
     *      p 向后移动一位
     *          p1
     *          ↓
     *      1	3	8	9	null
     *          p2
     *          ↓
     *      2	4	null
     *              p
     *              ↓
     *      s	1   2   null
     *
     *      p1 和 p2 比大小 ，哪一个指向的值 小 p1.val < p2.val
     *      复制 p1指向的节点，然后让 s的next指针指向 被赋值的节点
     *      p1 向后移动一位
     *      p 向后移动一位
     *              p1
     *              ↓
     *      1	3	8	9	null
     *          p2
     *          ↓
     *      2	4	null
     *                  p
     *                  ↓
     *      s	1   2   3  null
     *
     *      p1 和 p2 比大小 ，哪一个指向的值 小 p2.val < p1.val
     *      复制 p2 指向的节点，然后让 s的next指针指向 被赋值的节点
     *      p2 向后移动一位
     *      p 向后移动一位
     *              p1
     *              ↓
     *      1	3	8	9	null
     *              p2
     *              ↓
     *      2	4	null
     *                      p
     *                      ↓
     *      s	1   2   3   4    null
     *
     *      此时 第二个链表结束了，因为 第二个链表比较短 ，那么第一个链表p1所指向的节点 及其 所有元素 全部 合并
     * </pre>
     *
     * @param p1
     * @param p2
     * @return
     */
    public ListNode mergeTwoLists(ListNode p1, ListNode p2) {
        // 创建一个哑节点 s，初始化为一个值为 -1 的新节点，后面将用于连接结果链表
        ListNode s = new ListNode(-1, null);
        // 创建一个指针 p，指向哑节点 s，用于在结果链表中移动
        ListNode p = s;
        // 当 p1 和 p2 都不为空时，继续比较并合并
        while (p1 != null && p2 != null) {
            if (p1.val < p2.val) {// 比较 p1 和 p2 的当前值
                // 如果 p1 的值小于 p2，连接 p1 到结果链表
                p.next = p1;
                // 移动 p1 到下一个节点
                p1 = p1.next;
            } else {
                // 否则连接 p2 到结果链表
                p.next = p2;
                // 移动 p2 到下一个节点
                p2 = p2.next;
            }
            // 移动指针 p 到结果链表的下一个节点
            p = p.next;
        }
        // TODO 下面两个分支 只能走一个 ，因为 两个链表 一个相对较短 另一个相对较长

        // 如果 p1 还有剩余节点，连接到结果链表
        if (p1 != null) {
            p.next = p1;
        }
        // 如果 p2 还有剩余节点，连接到结果链表
        if (p2 != null) {
            p.next = p2;
        }
        // 返回合并后的链表，去掉哑节点 s 的头
        return s.next;
    }

    //

    /**
     * 方法2
     * <pre>
     *   递归函数应该返回
     *     更小的那个链表节点，并把 更小的那个链表节点 所对应的链表 的 剩余节点与另一个链表再次递归
     *     返回之前，更新此节点的 next
     *   eg:
     *     1 -> 3 -> 8 -> 9, 2 -> 4
     *      mergeTwoLists(1 -> 3 -> 8 -> 9, 2 -> 4)
     *      │
     *      ├── p1.val < p2.val (1 < 2)
     *      │   │
     *      │   ├── mergeTwoLists(3 -> 8 -> 9, 2 -> 4)
     *      │   │   │
     *      │   │   ├── p1.val < p2.val (3 < 2)
     *      │   │   │   │
     *      │   │   │   ├── mergeTwoLists(3 -> 8 -> 9, 4)
     *      │   │   │   │   │
     *      │   │   │   │   ├── p1.val < p2.val (3 < 4)
     *      │   │   │   │   │   │
     *      │   │   │   │   │   ├── mergeTwoLists(8 -> 9, 4)
     *      │   │   │   │   │   │   │
     *      │   │   │   │   │   │   ├── p1.val < p2.val (8 < 4)
     *      │   │   │   │   │   │   │   │
     *      │   │   │   │   │   │   │   ├── mergeTwoLists(8 -> 9, null)
     *      │   │   │   │   │   │   │   │   │
     *      │   │   │   │   │   │   │   │   └── return 8 -> 9
     *      │   │   │   │   │   │   │   │
     *      │   │   │   │   │   │   ├── return 4 -> 8 -> 9
     *      │   │   │   │   │   │   │
     *      │   │   │   │   │   ├── return 3 -> 4 -> 8 -> 9
     *      │   │   │   │   │   │
     *      │   │   │   │   ├── return 2 -> 3 -> 4 -> 8 -> 9
     *      │   │   │   │
     *      │   │   ├── return 1 -> 2 -> 3 -> 4 -> 8 -> 9
     *      │   │
     *      │   ├── return 1 -> 2 -> 3 -> 4 -> 8 -> 9
     *      │
     *      ├── return 1 -> 2 -> 3 -> 4 -> 8 -> 9
     * </pre>
     * @param p1
     * @param p2
     * @return
     */
    public ListNode mergeTwoLists2(ListNode p1, ListNode p2) {
        // 基准条件：如果 p2 为空，返回 p1（因为 p1 可能还有节点）
        if (p2 == null) {
            return p1; // 归
        }
        // 基准条件：如果 p1 为空，返回 p2（因为 p2 可能还有节点）
        if (p1 == null) {
            return p2; // 归
        }
        // 比较 p1 和 p2 的当前节点值
        if (p1.val < p2.val) {
            // 如果 p1 的值小于 p2，
            // 将 p1 的 next 指向 下一轮后的得到的链表头节点
            p1.next = mergeTwoLists2(p1.next, p2); // 递
            return p1; // 归
        } else {
            // 如果 p2 的值小于或等于 p1，
            // 将 p2 的 next 指向 下一轮合并后的链表头节点
            p2.next = mergeTwoLists2(p1, p2.next); // 递
            return p2; // 归
        }
    }

    public static void main(String[] args) {
//        ListNode p1 = ListNode.of(1, 3, 8, 9, 10);
//        ListNode p2 = ListNode.of(2, 4);

        ListNode p1 = new ListNode(1,
                new ListNode(3,
                        new ListNode(8,
                                new ListNode(9,
                                        new ListNode(10, null)
                                )
                        )
                )
        );
        ListNode p2 = new ListNode(2, new ListNode(4, null));
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(new Leetcode_21_E06()
                .mergeTwoLists(p1, p2));
    }
}
