package heima_data_structure.java.src.main.java.com.itheima.datastructure.linkedlist;

/**
 * 检测链表是否有环
 *
 * <pre>
 *   如果链表上存在环，那么在环上以不同速度前进的两个指针必定会在某个时刻相遇。算法分为两个阶段
 *      阶段1
 *      龟一次走一步，兔子一次走两步
 *      当兔子能走到终点时，不存在环
 *      当兔子能追上龟时，可以判断存在环
 *      阶段2
 *      从它们第一次相遇开始，龟回到起点，兔子保持原位不变
 *      龟和兔子一次都走一步
 *      当再次相遇时，地点就是环的入口
 *    TODO 为什么呢？
 *      设起点到入口走 a 步（本例是 7），绕环一圈长度为 b（本例是 5），
 *      那么从起点开始，走 a + 绕环 n 圈（也就是 再走 b 的 n 倍） ，都能找到环入口
 *      第一次相遇时
 *        兔走了 a + 绕环 n 圈（本例 2 圈） + k，k 是它们相遇距环入口位置（本例 3，不重要）
 *        龟走了 a + 绕环 n 圈（本例 0 圈） + k，当然它绕的圈数比兔少
 *        兔走的距离是龟的两倍，所以龟走的 = 兔走的 - 龟走的 = 绕环 n 圈
 *      而前面分析过，如果走 a + 绕环 n 圈，都能找到环入口，因此从相遇点开始，再走 a 步，就是环入口
 *   eg:
 *                                              i -> j
 *                                           ↗          ↘
 *      a -> b -> c -> d -> e -> f -> g -> h              k
 *                                           ↖          ↙
 *                                                 l
 *      从 a 走到 h 需要 走 7 步
 *      从环的入口 h 走 再走到 h也就是环的出口 需要 走 5 步
 *
 *      龟(x) 走一步 兔子(y) 走两步
 *
 *      x y                                      i -> j
 *      ↓                                     ↗          ↘
 *      a -> b -> c -> d -> e -> f -> g -> h              k
 *                                           ↖          ↙
 *                                                 l
 *
 *           x    y                              i -> j
 *           ↓    ↓                           ↗          ↘
 *      a -> b -> c -> d -> e -> f -> g -> h              k
 *                                            ↖          ↙
 *                                                 l
 *
 *                x         y                    i -> j
 *                ↓         ↓                 ↗          ↘
 *      a -> b -> c -> d -> e -> f -> g -> h              k
 *                                            ↖          ↙
 *                                                 l
 *
 *                     x              y          i -> j
 *                     ↓              ↓       ↗          ↘
 *      a -> b -> c -> d -> e -> f -> g -> h              k
 *                                            ↖          ↙
 *                                                 l
 *
 *                          x              y ->  i -> j
 *                          ↓                 ↗          ↘
 *      a -> b -> c -> d -> e -> f -> g -> h              k
 *                                            ↖          ↙
 *                                                 l
 *
 *                               x               i -> j
 *                               ↓            ↗          ↘
 *      a -> b -> c -> d -> e -> f -> g -> h               k  <- y
 *                                            ↖          ↙
 *                                                 l
 *
 *
 *                                    x    y     i -> j
 *                                    ↓    ↓  ↗          ↘
 *      a -> b -> c -> d -> e -> f -> g -> h               k
 *                                            ↖          ↙
 *                                                 l
 *
 *                                                    y
 *                                                    ↓
 *                                         x     i -> j
 *                                         ↓  ↗          ↘
 *      a -> b -> c -> d -> e -> f -> g -> h               k
 *                                            ↖          ↙
 *                                                 l
 *
 *                                          x -> i -> j
 *                                            ↗          ↘
 *      a -> b -> c -> d -> e -> f -> g -> h               k
 *                                            ↖          ↙
 *                                                 l  <- y
 *
 *
 *                                          y -> i -> j  <- x
 *                                            ↗          ↘
 *      a -> b -> c -> d -> e -> f -> g -> h               k
 *                                            ↖          ↙
 *                                                  l
 *
 *                                               i -> j
 *                                            ↗          ↘
 *      a -> b -> c -> d -> e -> f -> g -> h               k <- x y
 *                                            ↖          ↙
 *                                                  l
 * </pre>
 */
public class Leetcode_141_E10 {
    public boolean hasCycle(ListNode head) {
        ListNode h = head; // 兔
        ListNode t = head; // 龟
        while (h != null && h.next != null) {
            t = t.next;
            h = h.next.next;
            if(h == t){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        // 构造一个带环链表
        ListNode n12 = new ListNode(12, null);
        ListNode n11 = new ListNode(11, n12);
        ListNode n10 = new ListNode(10, n11);
        ListNode n9 = new ListNode(9, n10);
        ListNode n8 = new ListNode(8, n9);
        ListNode n7 = new ListNode(7, n8);
        ListNode n6 = new ListNode(6, n7);
        ListNode n5 = new ListNode(5, n6);
        ListNode n4 = new ListNode(4, n5);
        ListNode n3 = new ListNode(3, n4);
        ListNode n2 = new ListNode(2, n3);
        ListNode n1 = new ListNode(1, n2);

//        n12.next = n8;

        System.out.println(new Leetcode_141_E10().hasCycle(n1));
    }
}
