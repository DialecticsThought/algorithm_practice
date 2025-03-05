package heima_data_structure.java.src.main.java.com.itheima.datastructure.linkedlist;

/**
 * 合并多个有序链表
 */
public class Leetcode_23_E07 {
    /**
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }
        return split(lists, 0, lists.length - 1);
    }

    /**
     * <pre>
     *   TODO
     *      1.利用归并排序的思想
     *   eg: [ [1 -> 3] , [4 -> 8] , [2 -> 5] , [2 -> 6] , [3 -> 7] ]
     *                       [ [1 -> 3] , [4 -> 8] , [2 -> 5] , [2 -> 6] , [3 -> 7] ]               递
     *                          ↙                                           ↘
     *          [ [1 -> 3] , [4 -> 8] , [2 -> 5] ]                  [ [2 -> 6] , [3 -> 7] ]         递
     *                  ↙               ↘                             ↙               ↘
     *    [ [1 -> 3] , [4 -> 8] ]        [2 -> 5]                 [2 -> 6]             [3 -> 7]     递
     *          ↙         ↘                 ↓                         ↓                  ↓
     *    [1 -> 3]      [4 -> 8]         [2 -> 5]                 [2 -> 6]             [3 -> 7]
     *          ↘        ↙                  ↓                         ↓                  ↓
     *       [1 -> 3 -> 4 -> 8]          [2 -> 5]                 [2 -> 6]             [3 -> 7]     归
     *                  ↘                ↙                              ↘                ↙
     *            [1 -> 2 -> 3 -> 4 -> 5 -> 8]                          [2 -> 3 -> 6 -> 7]          归
     *                        ↘                                                 ↙
     *                          [1 -> 2 -> 2 -> 3 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8]                    归
     * </pre>
     *
     * @param lists
     * @param i     左边界
     * @param j     右边界
     * @return 返回合并后的链表
     */
    private ListNode split(ListNode[] lists, int i, int j) {
        if (i == j) { // 数组内只有一个链表
            return lists[i];
        }
        // 计算中间索引
        int m = (i + j) >>> 1;
        // 递归拆分左半部分  得到左半部分的合并后的链表
        ListNode left = split(lists, i, m);// 递
        // 递归拆分右半部分 得到右半部分的合并后的链表
        ListNode right = split(lists, m + 1, j); // 递
        //  执行 合并 当前左右两个链表 操作 得到 新的链表并返回
        return mergeTwoListsWithRecursion(left, right); // 归
    }

    /**
     * 多个链表的合并，可以理解为先是前2个链表合并完之后，再和第3个链表合并，..... 不断地和第n个链表合并，直到所有链表都合并完成
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists2(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null; // 如果链表数组为空，返回 null
        }
        // 初始化 mergedList 为第一个链表
        ListNode mergedList = lists[0];
        // 从第二个链表开始，依次合并
        for (int i = 1; i < lists.length; i++) {
            // 获取当前要合并的链表
            ListNode currentList = lists[i];
            // 合并当前的 mergedList 和 currentList
            ListNode newMergedList = mergeTwoListsWithRecursion(mergedList, currentList);
            // 更新 mergedList 为新的合并结果
            mergedList = newMergedList;
        }
        return mergedList; // 返回最终合并后的链表
    }


    // 合并两个有序链表
    public ListNode mergeTwoListsWithRecursion(ListNode p1, ListNode p2) {
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
            p1.next = mergeTwoListsWithRecursion(p1.next, p2); // 递
            return p1; // 归
        } else {
            // 如果 p2 的值小于或等于 p1，
            // 将 p2 的 next 指向 下一轮合并后的链表头节点
            p2.next = mergeTwoListsWithRecursion(p1, p2.next); // 递
            return p2; // 归
        }
    }

    /**
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // 创建哑节点和一个指针 current 指向哑节点
        ListNode dummy = new ListNode(0, null);
        ListNode current = dummy;
        // 当两个链表都不为空时，逐个比较并连接较小的节点
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                current.next = l1;

                l1 = l1.next;
            } else {
                current.next = l2;

                l2 = l2.next;
            }
        }
        // 当其中一个链表遍历完，将另一个链表直接连接到 current 后面
        if (l1 != null) {
            current.next = l1;
        }
        if (l2 != null) {
            current.next = l2;
        }
        return dummy.next;
    }


    /**
     * <h3>Divide and Conquer 分而治之（分、治、合）</h3>
     * <h3>Decrease and Conquer 减而治之</h3>
     */

    public static void main(String[] args) {
        ListNode[] lists = {
                ListNode.of(1, 4, 5),
                ListNode.of(1, 3, 4),
                ListNode.of(2, 6),
        };
        ListNode m = new Leetcode_23_E07().mergeKLists(lists);
        System.out.println(m);
    }
}
