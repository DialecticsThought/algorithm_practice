package other.ad2.class07;

import java.util.LinkedList;
import java.util.Queue;

public class AC1 {

    public static class Node {


        public int end;
        public Node fail;
        public Node[] nexts;

        public Node() {
            end = 0;
            fail = null;
            nexts = new Node[26];
        }
    }

    public static class Node2 {
        //如果一个node，end为空，不是结尾
        //如果end不为空，表示这个点是某个字符串的结尾，end的值就是这个字符串
        public String end;
        //只有在上面的end变量不为空的时候,endUse才有意义
        //表示，这个字符串之前有没有加入过答案
        public boolean endUse;
        public Node2 fail;
        public Node2[] nexts;

        public Node2() {
            endUse = false;
            end = null;
            fail = null;
            nexts = new Node2[26];
        }
    }

    public static class ACAutomation {
        private Node root;
        //TODO 字符是放在路径上的 不是放在节点上的
        public ACAutomation() {
            root = new Node();
        }
        //TODO 建立前缀树（AC自动机）
        public void insert(String s) {
            char[] str = s.toCharArray();
            Node cur = root;
            int index = 0;
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                if (cur.nexts[index] == null) {
                    Node next = new Node();
                    cur.nexts[index] = next;
                }
                cur = cur.nexts[index];
            }
            cur.end++;
        }

        public void build() {
            /*
            * TODO 宽度优先的顺序遍历 来设置fail节点 需要一个队列
            *  父节点来帮助子节点 设置子节点的fail指针
            *  因为设置fail指针需要某个节点的父节点
            * */
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            Node cur = null;
            Node cfail = null;
            while (!queue.isEmpty()) {
                cur = queue.poll();
                for (int i = 0; i < 26; i++) {//TODO 所有的路
                    //TODO cur是父亲 要把 i号儿子的fail指针设置好
                    if (cur.nexts[i] != null) {//TODO 找到所有有效的路
                        //TODO 先设置成root 如果找到了再重新设置
                        cur.nexts[i].fail = root;
                        cfail = cur.fail;//TODO 记录父节点的fail指针
                        while (cfail != null) {
                            //TODO 判断fail指针指向的节点 是否有 i号儿子 和父亲 相同的路径
                            if (cfail.nexts[i] != null) {
                                cur.nexts[i].fail = cfail.nexts[i];
                                break;
                            }
                            //TODO fail指针指向的节点 的fail指针 变成新一轮的fail指针
                            cfail = cfail.fail;
                        }
                        queue.add(cur.nexts[i]);
                    }
                }
            }
        }

        public int containNum(String content) {
            char[] str = content.toCharArray();
            Node cur = root;
            Node follow = null;
            int index = 0;
            int ans = 0;
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                while (cur.nexts[index] == null && cur != root) {
                    cur = cur.fail;
                }
                cur = cur.nexts[index] != null ? cur.nexts[index] : root;
                follow = cur;
                while (follow != root) {
                    if (follow.end == -1) {
                        break;
                    }
                    { // 不同的需求，在这一段{ }之间修改
                        ans += follow.end;
                        follow.end = -1;
                    } // 不同的需求，在这一段{ }之间修改
                    follow = follow.fail;
                }
            }
            return ans;
        }

    }

    public static void main(String[] args) {
        ACAutomation ac = new ACAutomation();
        ac.insert("dhe");
        ac.insert("he");
        ac.insert("c");
        ac.build();
        System.out.println(ac.containNum("cdhe"));
    }

}
