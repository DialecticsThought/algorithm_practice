/**
 * @Description
 * @Author veritas
 * @Data 2025/3/4 19:50
 */
public class LeetCode_109_ConvertSortedListToBST {

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

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * @param head
     * @return
     */
    public TreeNode sortedListToBST(ListNode head) {
        // base case 1 当前的链表位空
        if (head == null) {
            return null;
        }
        // base case 2 当前的链表只有一个节点
        if (head.next == null) {
            return new TreeNode(head.val);
        }
        // 使用快慢指针找到中间节点，并断开链表
        //TODO prev是 指向 slow指针的前一个节点
        ListNode prev = null; // 用来 指向链表中间节点的前一个节点 目的是用来断开左半部分链表
        ListNode slow = head; // 用来 指向链表中间节点，即当前子树的根
        ListNode fast = head;
        /**
         * 使用快慢指针的主要目的是在单链表中高效地找到中间节点。
         * 由于链表是有序的，中间节点自然就是根节点，它能将链表分成左右两个部分，从而构造出一棵平衡的二叉搜索树
         */
        while (fast != null && fast.next != null) {
            prev = slow;      // 记录慢指针当前的位置
            fast = fast.next.next; // 快指针走两步
            slow = slow.next;// 慢指针前进一步
        }
        // 执行到这 说明 上面的while循环结束了

        // 断开左半部分
        if (prev != null) {
            prev.next = null;
        }

        // 此时 slow 也是 当前 该链表的的中间节点 ☆☆☆☆☆☆☆☆☆☆☆
        TreeNode treeNode = new TreeNode(slow.val);
        // 此时 head 是 左半部分的第一个节点
        // 此时 prev 是 左半部分的最后一个节点

        // 左半部分递归 执行 传入左半部分的开始节点 得到 左半部分所构造的子树的根节点
        TreeNode left = sortedListToBST(head);
        // 赋值
        treeNode.left = left;

        // 此时 slow.next 是 右半部分的开始节点
        // 右半部分递归 执行 传入右半部分的开始节点 得到 右半部分所构造的子树的根节点
        TreeNode right = sortedListToBST(slow.next);
        // 赋值
        treeNode.right = right;

        // 返回当前层所构造的子树的根
        return treeNode;
    }
}
