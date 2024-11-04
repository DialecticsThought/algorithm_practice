package heima_data_structure.java.src.main.java.com.itheima.datastructure.linkedlist;

/**
 * 判断回文链表
 */
public class Leetcode_234_E09 {
    /**
     * <pre>
     *  方法1
     *    这个步骤 可以解决 奇数个数 或者 偶数个数 的 指针
     *     步骤1. 找链表中间点(利用快慢指针)
     *     步骤2. 反转从中间结点之后的半个链表(包括中间节点)
     *     步骤3. 反转后的链表 与 原始链表 从头开始 逐一比较
     *  方法2 TODO 这是下面代码的方法
     *     步骤1. 找链表中间点(利用快慢指针)
     *     步骤2. 反转从中间结点之前的半个链表(不包括中间节点)
     *     步骤3. 反转后的链表 与 原始链表 从头开始 逐一比较
     *     eg:
     *     p2指向 最后一个节点或者最后一个节点的next节点，此时p1指向中间结点
     *              p1      p2
     *      1   2   2   1   null
     *      此时翻转 中间以及中间节点以前的 ，，，，
     *      n1
     *      2   1
     *   TODO 很重要的一点
     *      判断奇数节点是利用p1指向的是最后一个节点
     *      判断偶数节点是利用p2指向的是最后一个节点的next 也就是null
     *      如果是 利用方法2，并且是奇数个节点，那么让 p1 = p1.next 因为 翻转的链表和原始链表的个数差1
     * </pre>
     */
    public boolean isPalindrome(ListNode head) {
        // TODO 下面 一个循环中，找到链表的中间结点 并 中间之前的所有节点都翻转一遍（不包括）  ☆☆☆☆☆☆☆

        ListNode p1 = head; // 慢指针，用于遍历链表
        ListNode p2 = head; // 快指针，用于快速遍历链表
        ListNode n1 = null; // 新头，用于指向反转的链表的新的头
        ListNode o1 = head; // 旧头，用于指向链表的旧的头

        // 当快指针未到达链表末尾时，进行遍历
        while (p2 != null && p2.next != null) {
            p1 = p1.next; // 慢指针向前移动一个节点
            p2 = p2.next.next; // 快指针向前移动两个节点
            /**
             *
             * <pre>
             *  eg: 1 -> 2 -> 3 -> null，
             *  初始
             *      n1 -> null
             *
             *      o1 -> 1 -> 2 -> 3 -> null，
             *
             *  执行 ListNode o2 = o1.next;
             *      n1 -> null
             *                 o2
             *                 ↓
             *      o1 -> 1 -> 2 -> 3 -> null，
             *
             *  执行  o1.next = n1;
             *                 n1
             *                 ↓
             *      o1 -> 1 -> null
             *      o2
             *      ↓
             *      2 -> 3 -> null，
             *
             *  执行  n1 = o1;
             *             n1
             *             ↓
             *      o1 ->  1 -> null  此时n1又来到了新链表的表头
             *      o2
             *      ↓
             *      2 -> 3 -> null
             *
             *  执行  o1 = o2;
             *      n1
             *      ↓
             *      1 -> null
             *           o2
             *            ↓
             *     o1 ->  2 -> 3 -> null    此时o1又来到了旧链表的表头
             * TODO 上面的o2 和代码中的p1是相同的
             * </pre>
             */
            // 反转链表
            o1.next = n1; // 将旧头的 next 指向新头
            n1 = o1; // 更新新头为当前的旧头
            o1 = p1;  // 将旧头更新为慢指针的当前节点
        }

        // 打印新头和慢指针的状态，调试用
        System.out.println(n1);
        System.out.println(p1);

        // TODO 中间节点之后的子链表 和 前半个链表翻转之后的链表比较

        // 检查链表节点数量是否为奇数
        if (p2 != null) { // 如果快指针不为空，说明有奇数节点
            p1 = p1.next; // 跳过中间节点
        }
        // 比较反转后的链表和后半部分
        while (n1 != null) {
            if (n1.val != p1.val) { // 如果新头的值不等于慢指针的值
                return false; // 不是回文，返回 false
            }
            n1 = n1.next; // 新头向后移动一个节点
            p1 = p1.next; // 慢指针向后移动一个节点
        }
        return true;// 所有节点值均相等，返回 true
    }

    public static void main(String[] args) {
//        System.out.println(new E09Leetcode234()
//                .isPalindrome(ListNode.of(1, 2, 2, 1)));
        System.out.println(new Leetcode_234_E09()
                .isPalindrome(ListNode.of(1, 2, 3, 2, 1)));
    }
}
