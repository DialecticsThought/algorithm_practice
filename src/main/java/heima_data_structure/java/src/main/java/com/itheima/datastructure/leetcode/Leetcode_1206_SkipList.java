package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * <h3>跳表</h3>
 */
public class Leetcode_1206_SkipList {

    public static void main(String[] args) {
        Skiplist list = new Skiplist();
        Skiplist.Node head = list.head;
        Skiplist.Node n3 = new Skiplist.Node(3);
        Skiplist.Node n7 = new Skiplist.Node(7);
        Skiplist.Node n11 = new Skiplist.Node(11);
        Skiplist.Node n12 = new Skiplist.Node(12);
        Skiplist.Node n16 = new Skiplist.Node(16);
        Skiplist.Node n19 = new Skiplist.Node(19);
        Skiplist.Node n22 = new Skiplist.Node(22);
        Skiplist.Node n23 = new Skiplist.Node(23);
        Skiplist.Node n26 = new Skiplist.Node(26);
        Skiplist.Node n30 = new Skiplist.Node(30);
        Skiplist.Node n37 = new Skiplist.Node(37);
        head.next[0] = head.next[1] = head.next[2] = n3;
        head.next[3] = head.next[4] = head.next[5] = head.next[6] = head.next[7] = n19;
        n3.next[0] = n3.next[1] = n3.next[2] = n7;
        n7.next[0] = n11;
        n7.next[1] = n12;
        n7.next[2] = n16;
        n11.next[0] = n12;
        n12.next[0] = n12.next[1] = n16;
        n16.next[0] = n16.next[1] = n16.next[2] = n19;
        n19.next[0] = n19.next[1] = n19.next[2] = n22;
        n19.next[3] = n26;
        n22.next[0] = n23;
        n22.next[1] = n22.next[2] = n26;
        n23.next[0] = n26;
        n26.next[0] = n30;
        n26.next[1] = n37;
        n30.next[0] = n37;

        System.out.println(Arrays.toString(list.find(30)));
        list.add(15);
    }

    private static void testRandomLevel() {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            int level = randomLevel(5);
            map.compute(level, (k, v) -> v == null ? 1 : v + 1);
        }
        System.out.println(map.entrySet().stream().collect(
                Collectors.toMap(
                        Map.Entry::getKey,
                        e -> String.format("%d%%", e.getValue() * 100 / 1000)
                )
        ));
    }

    /**
     * 设计一个方法，方法会随机返回 1~max 的数字
     * 从 1 开始，数字的几率逐级减半，例如 max = 4，让大概
     * - 50% 的几率返回 1
     * - 25% 的几率返回 2
     * - 12.5% 的几率返回 3
     * - 12.5% 的几率返回 4
     */
    static Random r = new Random();

    static int randomLevel(int max) {
        /**
         * 50% 返回1 50% 返回2
         * 那么r.nextBoolean() ? 1: 2
         * 50% 返回1 25% 返回2  25%  返回3
         * 那么r.nextBoolean() ? 1: r.nextBoolean() ? 2 : 3
         * 50% 返回1 25% 返回2  12.5%  返回3  12.5%  返回4
         * 那么r.nextBoolean() ? 1: r.nextBoolean() ? 2 : r.nextBoolean() 3 : 4
         */
        int x = 1;
        while (x < max) {
            if (r.nextBoolean()) {
                return x;
            }
            x++;
        }
        return x;
    }

    static class Skiplist {
        private static final int MAX = 10; // redis 32 java 62
        //TODO 这是头结点
        private final Node head = new Node(-1);

        /**
         * 假设有一个跳表
         * -1
         * -1 -> 3
         * -1 -> 3
         * 第一个node头结点 有max层  第二个node是n3，也是max层 但是实际上只有2层
         * 那么
         * 头结点head.next[0] = n3
         * 头结点head.next[1] = n3
         */
        static class Node {
            // 值
            int val;
            /**
             * next 数组
             * 因为是跳表 每一个Node本质是一个数组 有多层
             * 可以把一个数组从横着的 -> 竖着的
             * <pre>
             *                 ⌜
             * [        ]  -->
             *                   ⌟
             * </pre>
             * 所以不能是 Node类型的next，而是Node[]类型的next
             * next[] 表示当前节点在跳表的下一个节点
             * 假设下一个节点层数是n,
             * 那么 node[n]~node[max]都是空指针
             * node[0]~node[n-1]的都是指向下一个节点
             */
            Node[] next = new Node[MAX];

            public Node(int val) {
                this.val = val;
            }

            @Override
            public String toString() {
                return "Node(" + val + ")";
            }
        }

        /**
         * TODO
         * 1.若next 指针为null，或者next 指向的节点值>=目标，向下找
         * 2.若next 指针不为null，next 指向的节点值<目标，向右找
         * <pre>
         * ↓
         * -1   -> null -> null ->  null -> null  ->  null ->  null ->  null ->  null ->  null ->  null
         * ↓
         * -1   -> null -> null ->  null -> null  ->  null ->  null ->  null ->  null ->  null ->  null
         * ↓
         * -1   ->  --> -> -->  ->  -->  ->  -->  ->  -->  ->   19  ->  null ->  null ->  null ->  null
         * ↓     	       	                                    ↓
         * -1   ->  --> -> -->  ->  -->  ->  -->  ->  -->  ->   19  ->  null ->  null ->  null ->  null
         * ↓     	       	                                    ↓
         * -1   ->  --> -> -->  ->  -->  ->  -->  ->  -->  ->   19  ->  null ->  null ->  null ->  null
         * ↓     	       	                                    ↓
         * -1   ->  --> -> -->  ->  -->  ->  -->  ->  -->  ->   19  ->  null ->  null ->  null ->  null
         * ↓     	       	                                    ↓
         * -1   ->  --> -> -->  ->  -->  ->  -->  ->  -->  ->   19  ->  --> ->   -->  ->   26  ->  null
         * ↓      	       	                                                                ↓
         * -1   ->  3   ->   7  ->  -->  ->  -->  ->  16   ->   19  ->   22  ->  -->  ->   26  ->  null
         *                   ↓      	       	                                                    ↓
         * -1   ->  3   ->   7  ->  -->  ->   12  ->   16  ->   19  ->   22  ->  -->  ->   26  ->  null
         *                   ↓     	       	                                                        ↓
         * -1   ->  3   ->   7  ->   11  ->   12  ->   16  ->   19  ->   22  ->   23  ->   26 ->   30
         * 上面的例子中，一共有11个节点Node对象 依次是 -1,3,7,11,12,16,19,22,23,26,30
         * eg1:
         * 从顶层的第一个链表开始找30
         * 来到头节点第8层 发现next指针不为空 并且 next指向的节点值19<30 向右来到节点19
         * 来到节点19第8层的19 发现发现next指针为空 向下找
         * 来到节点19第7层  发现next指针为空 向下找
         * 来到节点19第6层  发现next指针为空 向下找
         * 来到节点19第5层  发现next指针为空 向下找
         * 来到节点19第4层  发现next指针不为空 并且 next指向的节点值26<30 向右来到节点26
         * 来到节点26第3层  发现next指针为空 向下找
         * 来到节点26第2层  发现next指针为空 向下找
         * 来到节点26第1层  发现next指针不为空 并且 next指向的节点值==30
         * eg2:
         * 从顶层的第一个链表开始找11
         * 来到头节点第8层 发现next指针不为空 但是 next指向的节点值19 > 11 向下找
         * 来到头节点第7层  发现next指针为空 向下找
         * 来到头节点第6层  发现next指针为空 向下找
         * 来到头节点第5层  发现next指针为空 向下找
         * 来到头节点第4层  发现next指针为空 向下找
         * 来到头节点第3层  发现next指针不为空 并且 next指向的节点值3 < 11 向右到节点3
         * 来到节点3第3层  发现next指针不为空 并且 next指向的节点值7 < 11 向右到节点7
         * 来到节点7第3层  发现next指针不为空 但是 next指向的节点值16 > 11 向下找
         * </pre>
         *
         * @param val
         * @return 这个返回的是找val所产生的路径数组
         * 那么path[0]应该是离最接近且<=val的节点
         * 如果val存在于跳表中，那么 path[0].next[0]一定是val，反之，不是
         * 要找30的话 path[] =
         * [26,0] [26,1] [26,2] [26,3]  [19,4] [19,5] [19,6] [19,7]  [-1,8] [-1,9]
         */
        public Node[] find(int val) {
            //TODO 记录为了找val所经过的路径
            Node[] path = new Node[MAX];
            Node curr = head;
            for (int level = MAX - 1; level >= 0; level--) { // 从上向下找
                //TODO curr.next[level] != null表示当前这一层的next指正不是null
                while (curr.next[level] != null && curr.next[level].val < val) { // 向右找
                    curr = curr.next[level];
                }
                //TODO 当前被遍历到的第level层的节点 记录到path[level]
                path[level] = curr;
            }
            return path;
        }

        /**
         * 返回查找某个节点的路径
         * 但是这个路径是去重的 也就是每一个节点的第0层
         *
         * @param val
         * @return
         */
        public boolean search(int val) {
            Node[] path = find(val);
            //这个path[0] 表示最接近要找的val对应节点的节点，不是要找到的val对应节点本身 所以需要next[0]
            Node node = path[0].next[0];
            return node != null && node.val == val;
        }

        /**
         * TODO
         * <pre>
         * 跳表里面 要加入15这个节点的话 要找到最接近15的节点 这里是12
         *  ↓
         * -1   -> null -> null ->  null -> null  ->  null ->  null ->  null ->  null ->  null ->  null
         *  ↓
         * -1   -> null -> null ->  null -> null  ->  null ->  null ->  null ->  null ->  null ->  null
         *  ↓
         * -1   ->  --> -> -->  ->  -->  ->  -->  ->  -->  ->   19  ->  null ->  null ->  null ->  null
         *  ↓
         * -1   ->  --> -> -->  ->  -->  ->  -->  ->  -->  ->   19  ->  null ->  null ->  null ->  null
         *  ↓
         * -1   ->  --> -> -->  ->  -->  ->  -->  ->  -->  ->   19  ->  null ->  null ->  null ->  null
         *  ↓
         * -1   ->  --> -> -->  ->  -->  ->  -->  ->  -->  ->   19  ->  null ->  null ->  null ->  null
         *  ↓
         * -1   ->  --> -> -->  ->  -->  ->  -->  ->  -->  ->   19  ->  -->  ->  -->  ->   26  ->  null
         *  ↓
         * -1   ->  3   ->   7  ->  -->  ->  -->  ->   16  ->   19  ->   22  ->  -->  ->   26  ->  null
         *                   ↓
         * -1   ->  3   ->   7  ->  -->  ->   12  ->   16  ->   19  ->   22  ->  -->  ->   26  ->  -->  ->   37
         *
         * -1   ->  3   ->   7  ->   11  ->   12  ->   16  ->   19  ->   22  ->   23  ->   26  ->   30  ->   37
         * 假设15这个节点高度是5
         * ↓
         * -1  -> null -> null ->  null -> null  -> null  ->  null ->  null ->  null ->  null -> null -> null
         * ↓
         * -1  -> null -> null ->  null -> null  -> null  ->  null ->  null ->  null ->  null -> null -> null
         * ↓
         * -1  ->  --> -> -->  ->  -->  ->  -->  ->  -->  ->  -->  ->   19  ->  null ->  null -> null -> null
         * ↓
         * -1  ->  --> -> -->  ->  -->  ->  -->  ->  -->  ->  -->  ->   19  ->  null ->  null -> null -> null
         * ↓
         * -1  ->  --> -> -->  ->  -->  ->  -->  ->  -->  ->  -->  ->   19  ->  null ->  null -> null -> null
         * ↓
         * -1  ->  --> -> -->  ->  -->  ->  -->  ->   15  ->  -->  ->   19  ->  null ->  null -> null -> null
         * ↓
         * -1  ->  --> -> -->  ->  -->  ->  -->  ->   15  ->  -->  ->   19  ->  -->  ->  -->  ->  26  -> null
         * ↓
         * -1  ->  3   ->   7  ->  -->  ->  -->  ->   15  ->   16  ->   19  ->   22  ->  -->  ->  26  -> null
         * ↓
         * -1  ->  3   ->   7  ->  -->  ->   12  ->   15  ->   16  ->   19  ->   22  ->  -->  ->  26  ->  -->  -> 37
         *
         * -1  ->  3   ->   7  ->   11  ->   12  ->   15  ->   16  ->   19  ->   22  ->   23  ->  26  ->   30  -> 37
         * 修改被添加节点的next[]指针
         * 原先为了找被插入节点的位置的路径所对应的节点的next[]指针
         * path[] = [12,0] [12,1] [7,2] [3,2] [-1,3] .....[-1,9]
         * </pre>
         *
         * @param val
         */
        public void add(int val) {
            //TODO 1. 确定添加位置（把 val 当作目标查询，经历的路径就可以确定添加位置）
            Node[] path = find(val);
            //TODO  2. 创建新节点和随机高度
            Node node = new Node(val);
            int level = randomLevel(MAX);
            /**
             * 3. 修改路径节点 next 指针，以及新节点 next 指针
             * 类似于链表
             */
            for (int i = 0; i < level; i++) {//遍历0~level范围的层数 level~max层 没有变化
                /**
                 *TODO  level = 5
                 * path[i].next[i]表示path数组的第i个元素的第i层指向的节点
                 * path[] = [12,0] [12,1] [7,2] [3,2] [-1,2] [-1,3] .....[-1,9]
                 * 1.
                 * path[0] == [12,0]  path[0].next[0]== [16,0]
                 * node.next[0]=[16,0]  15的第0层指向16的第0层  path[0].next[0] = node  12的第0层指向15
                 * 2.
                 * path[1] == [12,1]  path[1].next[1]== [16,1]
                 * node.next[1]=[16,1]  15的第1层指向16的第1层  path[1].next[1] = node  12的第1层指向15
                 * 3.
                 * path[2] == [7,2]  path[2].next[2]== [16,2]
                 * node.next[2]=[16,1]  15的第2层指向16的第2层  path[2].next[2] = node  7的第2层指向15
                 * 4.
                 * path[3] == [-1,3]  path[2].next[2]== [19,3]
                 * node.next[2]=[19,3]  15的第3层指向-1的第3层  path[3].next[3] = node  -1的第3层指向15
                 * 5.
                 * path[4] == [-1,4]  path[3].next[3]== [19,4]
                 * node.next[2]=[19,4]  15的第3层指向-1的第3层  path[4].next[4] = node  -1的第4层指向15
                 */
                node.next[i] = path[i].next[i];
                path[i].next[i] = node;
            }
        }

        /**
         * <pre>
         *  -1  -> null -> null ->  null -> null  -> null  ->  null -> null
         *
         *  -1  -> null -> null ->  null -> null  -> null  ->  null -> null
         *
         *  -1  ->  --> -> -->  ->  -->  ->  -->  ->  -->  ->  -->  ->  19
         *
         *  -1  ->  --> -> -->  ->  -->  ->  -->  ->  -->  ->  -->  ->  19
         *
         *  -1  ->  --> -> -->  ->  -->  ->  -->  ->  -->  ->  -->  ->  19
         *
         *  -1  ->  --> -> -->  ->  -->  ->  -->  ->   15  ->  -->  ->  19
         *
         *  -1  ->  --> -> -->  ->  -->  ->  -->  ->   15  ->  -->  ->  19
         *
         *  -1  ->  3   ->   7  ->  -->  ->  -->  ->   15  ->   16  ->  19
         *
         *  -1  ->  3   ->   7  ->  -->  ->   12  ->   15  ->   16  ->  19
         *
         *  -1  ->  3   ->   7  ->   11  ->   12  ->   15  ->   16  ->  19
         *  </pre>
         * 上面被删除的节点是15
         * 那么path[] = [12,0],[12,1],[7,1],[7,2],[-1,2],[-1,3]......[-1,10]
         * 因为被删除的节点是5层 当遍历到第6层的时候 已经没有被删除节点了 直接结束
         *
         * @param val
         * @return
         */
        public boolean erase(int val) {
            //得到 为了找到val节点所遍历的节点路径
            Node[] path = find(val);
            // 得到离val最接近且<=val的节点的下一个节点
            Node node = path[0].next[0];
            //这个if判断val是否不存在
            if (node == null || node.val != val) {
                return false;
            }
            //把节点路径上的每一个节点的next指针，改成被删除节点的next指针，因为有很多层，所以需要遍历每一层
            for (int i = 0; i < MAX; i++) {// 0~MAX层 从最底层依次往上遍历
                // 被删除的节点的层数 如果是n的话 那么 n~max 不需要再修改了
                if (path[i].next[i] != node) {
                    break;
                }
                path[i].next[i] = node.next[i];
            }
            return true;
        }
    }
}
