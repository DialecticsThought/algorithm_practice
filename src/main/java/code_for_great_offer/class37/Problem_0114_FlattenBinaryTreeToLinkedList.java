package code_for_great_offer.class37;

// 注意，我们课上讲了一个别的题，并不是leetcode 114
// 我们课上讲的是，把一棵搜索二叉树变成有序链表，怎么做
// 而leetcode 114是，把一棵树先序遍历的结果串成链表
// 所以我更新了代码，这个代码是leetcode 114的实现
// 利用morris遍历
//https://leetcode.cn/problems/flatten-binary-tree-to-linked-list/
public class Problem_0114_FlattenBinaryTreeToLinkedList {

    // 这个类不用提交
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            val = value;
        }
    }

    // 普通解
    public static void flatten1(TreeNode root) {
        process(root);
    }

    public static class Info {
        public TreeNode head;
        public TreeNode tail;

        public Info(TreeNode h, TreeNode t) {
            head = h;
            tail = t;
        }
    }

    //普通解
    public static TreeNode convert(TreeNode head) {
        if (head == null) {
            return null;
        }
        return process(head).head;
    }

    /*
     *TODO
     * 把以head为头的子树 转成链表 然后把链表的头和尾分装成info
     * */
    public static Info process(TreeNode cur) {
        if (cur == null) {
            return null;
        }
        //TODO 得到左子树的链表头和尾
        Info leftInfo = process(cur.left);
        //TODO 得到右子树的链表头和尾
        Info rightInfo = process(cur.right);
        if (leftInfo != null) {
            //TODO 那么 左子节点构成的单链表的最后一个节点 的下一个节点就是当前节点
            leftInfo.tail.right = cur;
            //TODO 当前节点的head 就指向  左子节点构成的单链表的最后一个节点
            cur.left = leftInfo.tail;
        }
        if (rightInfo != null) {
            //TODO 那么 右子节点构成的单链表的第一个节点 的下一个节点就是当前节点
            rightInfo.head.left = cur;
            //TODO 当前节点的 下一个节点 右子节点构成的单链表的第一个节点
            cur.right = rightInfo.head;
        }
        TreeNode head = null;
        if (leftInfo != null) {
            //TODO 当前节点所形成的单链表的头是左子节点的单链表的头
            head = leftInfo.head;
        } else {
            //TODO 当前节点所形成的单链表的头 是当前节点
            head = cur;
        }
        TreeNode tail;
        if (rightInfo != null) {
            tail = rightInfo.tail;
        } else {
            //TODO 当前节点所形成的单链表的头 是当前节点
            tail = cur;
        }

        return new Info(head, tail);
    }

    /*
    *TODO
    * Morris遍历的解
    * Morris遍历
    * 来到当前节点
    * 当前节点没有左孩子 当前节点 来到右孩子
    * 当前节点有左孩子 当前节点 找到左孩子的最右节点
    *   如果左孩子的最右节点的右指针是null ，让右指针指向当前节点 + 当前节点向左孩子移动
    *   如果左孩子的最右节点的右指针指向当前节点  ，让右指针指向null+ 当前节点向右孩子移动
    * */
    public static void flatten2(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode pre = null;
        TreeNode cur = root;
        TreeNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    if (pre != null) {
                        pre.left = cur;
                    }
                    pre = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            } else {
                if (pre != null) {
                    pre.left = cur;
                }
                pre = cur;
            }
            cur = cur.right;
        }
        cur = root;
        TreeNode next = null;
        while (cur != null) {
            next = cur.left;
            cur.left = null;
            cur.right = next;
            cur = next;
        }
    }

}
