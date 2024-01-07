package code_for_great_offer.class36;

import java.util.HashMap;

/**
 * @Description
 * @Author veritas
 * @Data 2023/2/20 19:52
 * TODO [ 7 3 6 9 12 8] m=5
 * 7 % 5 = 2
 * 生成2对应的链表 此时只有一个节点 7
 * 3 % 5 = 3
 * 生成3对应的链表 此时只有一个节点 3
 * 6 % 5 = 1
 * 生成1对应的链表 此时只有一个节点 6
 * 9 % 5 = 4
 * 生成4对应的链表 此时只有一个节点 9
 * 12 % 5 = 2
 * 生成2对应的链表 此时只有一个节点 7->12
 * 8 % 5 = 3
 * 生成3对应的链表 此时只有一个节点 3->8
 */
// 来自腾讯
// 给定一个单链表的头节点head，每个节点都有value(>0)，给定一个正数m
// value%m的值一样的节点算一类
// 请把所有的类根据单链表的方式重新连接好，返回每一类的头节点
public class Code07_ModMHeads {

    public static class Node {
        public int value;
        public Node next;
    }

    public static class Ht {
        public Node h;
        public Node t;

        public Ht(Node a) {
            h = a;
            t = a;
        }
    }

    public static Node[] split(Node h, int m) {
        HashMap<Integer, Ht> map = new HashMap<>();
        while (h != null) {
            Node next = h.next;
            h.next = null;
            int mod = h.value % m;
            if (!map.containsKey(mod)) {
                map.put(mod, new Ht(h));
            } else {
                map.get(mod).t.next = h;
                map.get(mod).t = h;
            }
            h = next;
        }
        Node[] ans = new Node[m];
        for (int mod : map.keySet()) {
            ans[mod] = map.get(mod).h;
        }
        return ans;
    }

}
