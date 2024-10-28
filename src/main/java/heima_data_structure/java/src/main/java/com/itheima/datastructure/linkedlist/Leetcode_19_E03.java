package heima_data_structure.java.src.main.java.com.itheima.datastructure.linkedlist;

/**
 * 删除倒数节点
 */
public class Leetcode_19_E03 {
    /*
    recursion(ListNode p=1, int n=2) {
        recursion(ListNode p=2, int n=2) {
            recursion(ListNode p=3, int n=2) {
                recursion(ListNode p=4, int n=2) {
                    recursion(ListNode p=5, int n=2) {
                        recursion(ListNode p=null, int n=2) {
                            return 0;
                        }
                        return 1;
                    }
                    return 2;
                }
                if(返回值 == n == 2) {
                    删除
                }
                return 3;
            }
            return 4;
        }
        return 5;
    }
    */
    // 方法1
    public ListNode removeNthFromEnd1(ListNode head, int n) {
        // 哨兵节点 因为 递归 的时候 不能解决头节点的问题
        ListNode s = new ListNode(-1, head);
        recursion(s, n);
        return s.next;
    }

    /**
     * 通过递归调用来计算每个节点的倒数位置，当找到倒数第 n 个节点时，将其从链表中移除
     * <pre>
     *     eg: 1 -> 2 -> 3 -> 4 -> 5 , 删除倒数第2个 即： n=2
     *     recursion(1 -> 2 -> 3 -> 4 -> 5, 2)
     *          │
     *          └── recursion(2 -> 3 -> 4 -> 5, 2)
     *          │    │
     *          │    └── recursion(3 -> 4 -> 5, 2)
     *          │    │    │
     *          │    │    └── recursion(4 -> 5, 2)
     *          │    │    │    │
     *          │    │    │    └── recursion(5, 2)
     *          │    │    │    │    │
     *          │    │    │    │    └── recursion(null, 2)
     *          │    │    │    │    │     └── nth = 0
     *          │    │    │    │    │           ↑
     *          │    │    │    │    └── nth = 1
     *          │    │    │    │         ↑
     *          │    │    │    └── nth = 2 (删除节点 4)
     *          │    │    │         ↑
     *          │    │    └── nth = 3
     *          │    │         ↑
     *          │    └── nth = 4
     *          │         ↑
     *          └── nth = 5
     * </pre>
     *
     * @param p 参数会变
     * @param n 参数不会变
     * @return
     */
    private int recursion(ListNode p, int n) {
        // base case 如果当前节点 p 是 null，意味着已经到达链表的末尾
        // 这是 递归的"归" 的一部分
        if (p == null) {
            return 0;
        }
        // 这行代码是 递归的"递"
        // 向下递归调用，逐步深入链表的尾部。每次递归调用都会把当前节点 p 传入下一个节点 p.next，直到链表末尾（p == null）
        // 下一个节点的倒数位置
        int nth = recursion(p.next, n);
        // 这是 递归的"归" 的一部分
        if (nth == n) {// 这里判断是否删除
            p.next = p.next.next;
        }
        // 逐层返回倒数位置的计数值 nth + 1，一层层向上返回
        return nth + 1;
    }

    /**
     * <pre>
     *   eg：1 -> 2 -> 3 -> 4 -> 5 -> null 删除倒数第2个 即： n=2
     *   一共有5个元素
     *   先加上哨兵节点，此时有6个元素
     *   初始情况 p1和p2都是位于头节点，即哨兵节点
     *   此时先让p2移动n+1个位置，+1是因为有哨兵节点的加入
     *   p2移动完n+1个位置之后，那么p1和p2同时移动
     *   直到p1移动到被删除节点的前一个节点的时候，此时p2正好指向最后一个节点的next，即null
     *       p1
     *       p2
     *       s -> 1 -> 2 -> 3 -> 4 -> 5 -> null
     *            p2
     *       s -> 1 -> 2 -> 3 -> 4 -> 5 -> null
     *                 p2
     *       s -> 1 -> 2 -> 3 -> 4 -> 5 -> null
     *       p1             p2
     *       s -> 1 -> 2 -> 3 -> 4 -> 5 -> null
     *                      p1             p2
     *       s -> 1 -> 2 -> 3 -> 4 -> 5 -> null
     * </pre>
     */
    // 方法2
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode s = new ListNode(-1, head);
        ListNode p1 = s;
        ListNode p2 = s;
        for (int i = 0; i < n + 1; i++) {
            p2 = p2.next;
        }
        while (p2 != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        p1.next = p1.next.next;
        return s.next;
    }

    public static void main(String[] args) {
        ListNode head = ListNode.of(1, 2, 3, 4, 5);
//        ListNode head = ListNode.of(1,2);
        System.out.println(head);
        System.out.println(new Leetcode_19_E03()
                .removeNthFromEnd(head, 5));
    }
}
