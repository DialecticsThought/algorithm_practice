package algorithmbasic2020_master.class32;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://www.bilibili.com/video/BV1Ag41117YU/?spm_id_from=333.788.top_right_bar_window_custom_collection.content.click
 * 一、构建fail指针的遍历为层次遍历。
 * 二、root节点的fail指针指向自己本身。
 * 三、如果当前节点父节点的fail指针指向的节点下存在与当前节点一样的子节点，
 * 则当前节点的fail指针指向该子节点。否则指向root节点。
 * fail针对某个分支匹配失败之后，重新指向关联的其他分支上
 * <p>
 * 在扫描字符串的时候，是顺着trie数
 * 但不是每次匹配都能找到我们的敏感词，为了不中断这个查找的链路
 * 代表着在这个节点上无法再继续深入的时候，跳转到哪一个位置能继续查找，不中断
 * <p>
 * 每个节点都有可能无法继续向下进行匹配，每个节点都要去构建
 * 敏感词：[say,she,shr,he,her]
 *TODO
 * |		  R
 * | 		 ↙ ↘
 * |	   s	 h
 * |	  ↙ ↘	   ↘
 * |	 a   h 	 	e[2]
 * |    ↙   ↙  ↘	   ↘
 * |  y   e     r		r
 * | [3]  [3]   [3]      [3]
 *TODO
 * |         → → → →  ↓ ← ← ← ← ← ← ←
 * |		 ↑		  R → ↑          ↑      1
 * |		 ↑		 ↙ ↘             ↑
 * |		 ↑ ← ← s	 h → → → → → ↑      2
 * |		 ↑	  ↙ ↘ ↗    ↘         ↑
 * |		 ← ← a   h 	 	e[2] → → ↑      3
 * |		 ↑  ↙   ↙  ↘	↑  ↘     ↑
 * |		 ← y    e   r	↑	 r → ↑      4
 * |		 [3,2]  ↓       ↑
 * |               → → → → →
 * 对于第1层的root指向自己
 * 对于第2层的所有节点都是指向root
 * 对于第4层的y节点而言，其父是a，a的fail == root， root无y的分支
 * 对于第3层的h节点而言，其父是s，s的fail == root， root有h的分支
 * 对于第4层的e节点而言，其父是h，h的fail == h， h有e的分支
 */
public class Code03_AC1 {

    public static class Node {
        public int end; // 有多少个字符串以该节点结尾
        public Node fail;
        public Node[] nexts;

        public Node() {
            end = 0;
            fail = null;
            nexts = new Node[26];
        }
    }

    public static class ACAutomation {
        private Node root;

        public ACAutomation() {
            root = new Node();
        }

        // 你有多少个匹配串，就调用多少次insert
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
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            Node cur = null;
            Node cfail = null;
            while (!queue.isEmpty()) {
                cur = queue.poll(); // 父
                for (int i = 0; i < 26; i++) { // 下级所有的路
                    if (cur.nexts[i] != null) { // 该路下有子节点
                        cur.nexts[i].fail = root; // 初始时先设置一个值
                        cfail = cur.fail;
                        while (cfail != null) { // cur不是头节点
                            if (cfail.nexts[i] != null) {
                                cur.nexts[i].fail = cfail.nexts[i];
                                break;
                            }
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
