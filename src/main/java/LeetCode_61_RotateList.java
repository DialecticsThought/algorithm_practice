import java.util.LinkedList;

/**
 * @Description
 * @Author veritas
 * @Data 2025/3/3 18:00
 */
public class LeetCode_61_RotateList {

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * <pre>
     * 1 -> 2 -> 3 -> 4 -> 5
     * 链表长度 size = 5
     * 如果 k = 12，我们实际希望旋转链表 12 次。
     * 为什么要取余？
     * 旋转操作的本质是：将最后 k 个节点移到链表前面。
     * 但是，如果 k 大于链表长度，多旋转一整圈后链表会回到原始状态。
     * 对于长度为 5 的链表：
     * 旋转 5 次后，链表回到原来的顺序；
     * 旋转 10 次（5×2）后，链表依然回到原来的顺序
     * 因此，对 k 进行取余操作：
     * effective k=k mod size=12 mod 5 = 2
     * 这意味着实际只需要旋转 2 次就能得到和旋转 12 次相同的效果
     * 效果演示
     *      初始链表：1 → 2 → 3 → 4 → 5
     * 旋转 1 次：
     *   将链表的最后一个节点（5）移动到链表前面：
     *      5 → 1 → 2 → 3 → 4
     * 旋转 2 次：
     *  再次把当前链表的最后一个节点（4）移到前面
     *  4 → 5 → 1 → 2 → 3
     * </pre>
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode rotateRight(ListNode head, int k) {
        LinkedList<ListNode> list = new LinkedList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        // 计算有效旋转次数
        int size = list.size();
        k = k % size;
        if (k == 0) return head;
        // 利用 LinkedList 旋转：将最后 k 个节点依次移动到链表前端
        for (int i = 0; i < k; i++) {
            // removeLast() 移除最后一个节点，再用 addFirst() 插入到开头
            list.addFirst(list.removeLast());
        }
        // 重新组装链表：遍历 nodeList 并更新每个节点的 next 指针
        for (int i = 0; i < list.size(); i++) {
            list.get(i).next = list.get(i + 1);
        }
        // 最后一个节点的 next 置为 null
        list.getLast().next = null;
        // 返回旋转后的新头节点（即 LinkedList 的第一个节点）
        return list.getFirst();
    }
}
