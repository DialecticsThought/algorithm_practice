package heima_data_structure.java.src.main.java.com.itheima.datastructure.linkedlist;



/**
 * 反转链表
 */
public class Leetcode_206_E01 {
    /**
     * 方法1
     * 构造一个新链表，从**旧链表**依次拿到每个节点，创建新节点添加至**新链表**头部，完成后新链表即是倒序的
     *
     * @param o1
     * @return
     */
    public ListNode reverseList1(ListNode o1) {
        // 初始化一个新的链表，相当于一个指向链表的头的指针，
        // 只不过初始情况下，新的链表元素为空，所以尾巴为空
        ListNode n1 = null;
        // 先得到旧的链表的头
        ListNode p = o1;
        // 遍历旧的链表的所有元素
        while (p != null) {
            // 创建一个新的元素
            // 新的元素的val是老的元素的val 但是新的元素的next指针指向的新的链表的头
            // 最后再把指向链表的头的指针 指向刚刚创建的元素
            n1 = new ListNode(p.val, n1);
            // 遍历到旧链表下一个的元素
            p = p.next;
        }
        return n1;
    }


    /**
     * 方法2
     * 构造一个新链表，从旧链表头部移除节点，添加到新链表头部，完成后新链表即是倒序的，
     * 区别在于原题目未提供节点外层的容器类，这里提供一个，另外一个区别是并不去构造新节点
     *
     * @param head
     * @return
     */
    public ListNode reverseList2(ListNode head) {
        // 旧的链表
        List list1 = new List(head);
        // 新的链表
        List list2 = new List(null);
        // 遍历
        while (true) {
            // 得到旧的链表的第一个节点(头部节点)
            ListNode first = list1.removeFirst();
            // 旧的链表的第一个节点 为空 说明旧的链表没有元素了
            if (first == null) {
                break;
            }
            // 让旧的链表的第一个节点(头部节点) 变成新的链表的第一个节点
            list2.addFirst(first);
        }
        return list2.head;
    }

    static class List {
        ListNode head;

        public List(ListNode head) {
            this.head = head;
        }

        /**
         * 把节点变成链表的第一个节点
         *
         * @param first
         */
        public void addFirst(ListNode first) {
            // first的next指针 指向 原始链表的头结点
            first.next = head;
            // 更新指向头节点的指针 指向 first
            head = first;
        }

        /**
         * 得到链表的第一个节点 并删除
         *
         * @return
         */
        public ListNode removeFirst() {
            // 用一个新的指针first指向链表头节点
            ListNode first = head;

            if (first != null) {
                // 让指向链表的头节点的指针指向链表的头节点的next节点
                head = first.next;
            }
            return first;
        }
    }

    /**
     * 方法3 - 递归
     * 写一个递归方法，
     * 从链表中的节点 p 开始，反转p之后的所有节点，最终返回反转后的新头节点
     * TODO 递的时候不做操作，归的时候做操作
     * <pre>
     *  reverseList3(1)
     *  |
     *  |-- reverseList3(2)
     *      |
     *      |-- reverseList3(3)
     *          |
     *          |-- reverseList3(4)
     *              |
     *              |-- reverseList3(null) // 返回4 (递归终止)
     *              |
     *              | 反转链表: 4 -> null
     *              | 返回4
     *          |
     *          | 反转链表: 4 -> 3 -> null
     *          | 返回4
     *      |
     *      | 反转链表: 4 -> 3 -> 2 -> null
     *      | 返回4
     *  |
     *  | 反转链表: 4 -> 3 -> 2 -> 1 -> null
     *  | 返回4
     * </pre>
     *
     * @param p
     * @return
     */
    public ListNode reverseList3(ListNode p) {
        // base case
        if (p == null || p.next == null) {
            return p; // 最后节点
        }
        // 从链表中的节点 p.next 开始，反转p.next之后的所有节点，最终返回反转后的新头节点
        ListNode last = reverseList3(p.next);
        // 下面两行是让p和p.next局部的逆序

        // 让当前节点 p 的下一个节点（即 p.next）指向当前节点 p，实现局部的反转
        p.next.next = p;
        // 把当前节点 p 的下一个节点设为 null，防止链表形成循环
        p.next = null;

        // 是最深层递归中返回的链表新头节点，它保持不变，最终作为整个链表反转后的头节点返回
        return last;
    }

    /**
     * 方法4 - 不断把 o2 头插入 n1
     * <pre>
     *   o1 指向旧的链表头部的指针
     *   n1 指向新的链表头部的指针
     *   eg：
     *   o1
     *   ↓
     *   1 -> 2 -> 3 -> 4 -> null
     *   1.找到旧的链表的第二个节点，用o2指向它
     *   o1  o2
     *   ↓    ↓
     *   1 -> 2 -> 3 -> 4 -> null
     *   2.把o2指向的节点从所属链表中断开
     *   o1                         o2
     *   ↓                          ↓
     *   1 -> 3 -> 4 -> null        2
     *   3.把 o2 插入 o1所指向的链表的头部
     *   o2  o1
     *   ↓    ↓
     *   1 -> 1 -> 3 -> 4 -> null
     *   4.用n1指向o2现在指向的节点
     *        o2  o1
     *        ↓    ↓
     *  n1 -> 2 -> 1 -> 3 -> 4 -> null
     *   5.o2指向o1现在指向的节点的下一个节点
     *             o1   o2
     *             ↓    ↓
     *  n1 -> 2 -> 1 -> 3 -> 4 -> null
     * </pre>
     *
     * @param o1
     * @return
     */
    public ListNode reverseList4(ListNode o1) {
        // 1. 空链表  2. 一个元素
        if (o1 == null || o1.next == null) {
            return o1;
        }
        ListNode o2 = o1.next;
        ListNode n1 = o1;
        // 当 o2 不为空时，表示链表还有节点需要处理
        while (o2 != null) {
            // 这一步将 o1 的 next 更新为 o2 的下一个节点，即跳过 o2。通过这一步，可以理解为“暂时断开” o1 和 o2 的连接，并准备将 o2 插入到已经反转的部分。
            o1.next = o2.next;
            // 这里将 o2 的 next 指向 n1，即让 o2 反转指向 n1，形成新的反转链表部分。例如，假设原链表是 1 -> 2 -> 3 -> null，此时 n1 是 1，o2 是 2，这一步会让 2 指向 1，形成部分反转链表 2 -> 1 -> null
            o2.next = n1;
            // 更新 n1 为 o2，即当前反转链表的头部节点更新为 o2
            n1 = o2;
            // 更新 o2 为 o1 的下一个节点（之前在第 3.1 步已经修改过 o1.next），此时 o2 是下一个待处理的节点。这样，下一轮循环就能继续反转链表
            o2 = o1.next;
        }
        // 当 while 循环结束时，链表已经完全反转，n1 就是反转后链表的头节点，返回 n1
        return n1;
    }

    /**
     * 方法5 - 不断把 o1 头插到 n1
     * <pre>
     *  n1 指向 null，代表新链表一开始没有元素，o1 指向原链表的首节点
     *       n1      o1
     *       ↓       ↓
     *       null    1 -> 2 -> 3 -> 4 -> null
     *  开始循环，o2 指向原链表次节点
     *       n1      o1   o2
     *       ↓       ↓    ↓
     *       null    1 -> 2 -> 3 -> 4 -> null
     *  搬移
     *       o1   n1      o2
     *       ↓     ↓      ↓
     *       1 -> null    2 -> 3 -> 4 -> null
     *  指针复位
     *       n1            o1
     *       ↓             ↓
     *       1 -> null    2 -> 3 -> 4 -> null
     *  重复 2~4 步
     *      ......
     *  当 o1 = null 时退出循环
     * </pre>
     *
     * @param o1
     * @return
     */
    public ListNode reverseList(ListNode o1) {
        if (o1 == null || o1.next == null) {
            return o1;
        }
        //n1 初始化为 null，这个变量将用于保存反转后的链表头节点
        ListNode n1 = null;
        // 当 o1 不为空时，进入循环，表示仍然有节点需要处理
        while (o1 != null) {
            // o2 保存当前节点 o1 的下一个节点。这一步是为了在反转 o1 时，能够继续访问链表的其余部分
            ListNode o2 = o1.next;
            //将当前节点 o1 的 next 指向 n1，这实际上是反转指针的操作。比如，如果当前链表是 1 -> 2 -> 3，在这一步后，1 的 next 将指向 null（因为 n1 是 null），变为 1 -> null
            o1.next = n1;
            // 将 n1 更新为当前节点 o1，这一步是为了记录反转后的链表头。随着循环的进行，n1 会逐渐指向反转后的链表的最上层节点
            n1 = o1;
            // 将 o1 更新为 o2，也就是之前保存的下一个节点，以继续处理链表的下一个部分
            o1 = o2;
        }
        return n1;
    }

    public static void main(String[] args) {
        ListNode o5 = new ListNode(5, null);
        ListNode o4 = new ListNode(4, o5);
        ListNode o3 = new ListNode(3, o4);
        ListNode o2 = new ListNode(2, o3);
        ListNode o1 = new ListNode(1, o2);
        System.out.println(o1);
        ListNode n1 = new Leetcode_206_E01().reverseList1(o1);
        System.out.println(n1);
    }

}
