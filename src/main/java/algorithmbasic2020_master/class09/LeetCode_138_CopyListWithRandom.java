package algorithmbasic2020_master.class09;

import java.util.HashMap;

/**
 * TODO
 * 测试链接 : https://leetcode.com/problems/copy-list-with-random-pointer/
 * 遍历链表中的所有节点 ，每次遍历到一个节点 ，创建出对应的复制节点 并记录到map<原始节点，复制节点></>
 * 遍历完之后，所有的复制节点都有了，接下来就是设置复制节点的next和rand指针
 * 复制节点的next指针 就是 原始节点的next指针对应的节点 然后找到这个节点的复制节点通过map
 * <pre>
 * eg:
 * ↑  ->   R    ->   ↓
 * ↑                 ↓
 * 1  ->   2    ->   3 -> null
 * ↑                 ↓
 * ↑  <-   R    <-   ↓
 * 上面是一个链表
 * 创建好map 建立映射
 * <1,1`> <2,2`> <3,3`>
 * 找到头节点的对应复制节点 1`
 * 这个节点1`的next指针 应该找到 原始节点1的next指针指向的节点2，然后找到对应的复制节点2`
 * 方法2
 * ↑  ->   R    ->   ↓
 * ↑                 ↓
 * 1  ->   2    ->   3 -> null
 * ↑                 ↓
 * ↑  <-   R    <-   ↓
 * 创建对应的复制节点 然后插入到原始链表里面
 * 插入方式 插在原始节点和原始节点的next节点之间
 * 也就是说原始节点的next指针就是复制节点
 * ↑  →  →  R  →  →  ↓
 * ↑                 ↓
 * 1  →  1`  →  2  →  2`  →  3  →  3`  →  null
 * ↑            ↓
 * ↑  ←  R ←  ← ↓
 * 现在复制节点有了next指针但是没有rand指针
 * 节点 成对遍历
 * 一开始遍历到了1和1`
 * 因为1的rand==3
 * 那么1`的rand==3`,3`是3的next节点
 *       ↑  →  →  →  R`   →  →  →  ↓
 *       ↑                         ↓
 * ↑  →  →  →  →  R  →  →  → ↓     ↓
 * ↑     ↑                   ↓     ↓
 * 1  →  1`  →  2  →  2`  →  3  →  3`  →  null
 * ↑            ↓
 * ↑  ←  ←  R ← ↓
 *
 * 	   ↑ → → → R`  → → → ↓
 * ↑ → ↑ → R → → → → ↓   ↓
 * 1 → 1` → 2 → 2` → 3 → 3` → null
 * ↑ ← R ← ↓
 * ......
 *       ↑  →  →  →  R`   →  →  →  ↓
 *       ↑                         ↓
 * ↑  →  →  →  →  R  →  →  → ↓     ↓
 * ↑     ↑                   ↓     ↓
 * 1  →  1`  →  2  →  2`  →  3  →  3`  →  null
 * ↑     ↑      ↓    ↓
 * ↑  ←  ←  ← ← ↓    ↓
 *       ↑           ↓
 *       ↑  ←  R  ←  ↓
 *
 * 	   ↑ → → → R`  → → → ↓
 * ↑ → ↑ → R → → → → ↓   ↓
 * 1 → 1` → 2 → 2` → 3 → 3` → null
 * ↑ ← ↑ ← ↓    ↓
 *     ↑ ← R ←  ↓
 * 最后就是分离 ，特别是next指针的next
 * </pre>
 */
public class LeetCode_138_CopyListWithRandom {
    /**
     * rand指针是单链表节点结构中新增的指针, rand可能指向链表中的任意一个节点，也可能指向null。
     * 给定一个由Node节点类型组成的无环单链表的头节点 head，请实现一个函数完成这个链表的复制，
     * 并返回复制的新链表的头节点。【要求】
     * 时间复杂度O(N)．额外空间复杂度O(1)
     */
    public static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public static Node copyRandomList1(Node head) {
        // key 老节点
        // value 新节点
        HashMap<Node, Node> map = new HashMap<Node, Node>();
        Node cur = head;//建了一个箭头引用 用来遍历
        while (cur != null) {
            //每遍历一个节点 创建一个对应的克隆节点 存到map
            map.put(cur, new Node(cur.val));
            cur = cur.next;//箭头指向下一个节点
        }
        cur = head;//重新回到头
        while (cur != null) {
            // cur 老 是key
            // map.get(cur) 新的节点
            // 新.next ->  cur.next克隆节点找到
            //克隆的节点.next域指向源节点的next域的克隆节点
            map.get(cur).next = map.get(cur.next);
            //克隆的节点.random域指向源节点的random域的克隆节点
            map.get(cur).random = map.get(cur.random);
            cur = cur.next;//箭头指向下一个节点
        }
        return map.get(head);
    }

    public static Node copyRandomList2(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        Node next = null;
        // 1 -> 2 -> 3 -> null
        // 1 -> 1' -> 2 -> 2' -> 3 -> 3'
        while (cur != null) {
            //cur指向的是原始的节点 next是原始节点的下一个
            next = cur.next;
            //原始节点的下一个变成 原始节点的克隆节点
            cur.next = new Node(cur.val);
            //原始节点的克隆节点的下一个节点是原始的节点原来的下一个节点
            cur.next.next = next;
            cur = next;
        }
        cur = head;
        Node copy = null;
        // 1 1' 2 2' 3 3'
        // 依次设置 1' 2' 3' random指针
        while (cur != null) {
            //cur.next.next;原始节点的下一个  cur.next克隆节点
            next = cur.next.next;
            // cur.next克隆节点
            copy = cur.next;
            //copy.random = cur.random != null ? cur.random.next : null;
            if (cur.random != null) {//原始节点的random域是否为空
                /*
                 * 原始节点的random域的下一个节点 是 拷贝克隆的节点的random域
                 * eg：1 -> 1' -> 2 -> 2' -> 3 -> 3'
                 * 并且2的random是1 按当前cur=2的情况下
                 * cur.random=1 cur.random.next=1‘
                 * 那么copy=2’的random指向1‘
                 * */
                copy.random = cur.random.next;
            } else {
                copy.random = null;
            }
            cur = next;
        }
        //head是头结点 head.next就是克隆的头结点
        Node res = head.next;
        cur = head;
        // 老 新 混在一起，next方向上，random正确
        // next方向上，把新老链表分离
        while (cur != null) {
            //cur.next当前节点的克隆节点 箭头指向 当前节点的克隆节点的next
            next = cur.next.next;
            copy = cur.next;//copy指向当前节点的克隆节点
            cur.next = next;//让当前节点的next指向当前节点的克隆节点的next
            /*
             * 当前节点的克隆节点的下一个节点是否为空
             * 不为空的话 克隆节点的下一个节点就是当前节点的克隆节点的下一个节点的 下一个节点
             * */
            copy.next = next != null ? next.next : null;
            cur = next;
        }
        return res;
    }

}
