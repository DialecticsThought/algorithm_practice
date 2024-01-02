package code_for_great_offer.class14;

// 本题测试链接 : https://www.nowcoder.com/practice/e13bceaca5b14860b83cbcc4912c5d4a
// 提交以下的代码,并把主类名改成Main
// 可以直接通过

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Code03_BiggestBSTTopologyInTree {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int h = sc.nextInt();
        int[][] tree = new int[n + 1][3];
        for (int i = 1; i <= n; i++) {
            int c = sc.nextInt();
            int l = sc.nextInt();
            int r = sc.nextInt();
            tree[l][0] = c;
            tree[r][0] = c;
            tree[c][1] = l;
            tree[c][2] = r;
        }
        System.out.println(maxBSTTopology(h, tree, new int[n + 1]));
        sc.close();
    }

    // h: 代表当前的头节点
    // t: 代表树 t[i][0]是i节点的父节点、t[i][1]是i节点的左孩子、t[i][2]是i节点的右孩子
    // m: i节点为头的最大bst拓扑结构大小 -> m[i]
    // 返回: 以h为头的整棵树上,最大bst拓扑结构的大小
    public static int maxBSTTopology(int h, int[][] t, int[] m) {
        if (h == 0) {
            return 0;
        }
        int l = t[h][1];
        int r = t[h][2];
        int c = 0;
        int p1 = maxBSTTopology(l, t, m);
        int p2 = maxBSTTopology(r, t, m);
        while (l < h && m[l] != 0) {
            l = t[l][2];
        }
        if (m[l] != 0) {
            c = m[l];
            while (l != h) {
                m[l] -= c;
                l = t[l][0];
            }
        }
        while (r > h && m[r] != 0) {
            r = t[r][1];
        }
        if (m[r] != 0) {
            c = m[r];
            while (r != h) {
                m[r] -= c;
                r = t[r][0];
            }
        }
        m[h] = m[t[h][1]] + m[t[h][2]] + 1;
        return Math.max(Math.max(p1, p2), m[h]);
    }

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static int bstTopoSize1(Node head) {

        if (head == null) {
            return 0;
        }
        int max = maxTopo(head, head);
        max = Math.max(bstTopoSize1(head.left), max);
        max = Math.max(bstTopoSize1(head.right), max);
        return max;
    }

    public static int maxTopo(Node h, Node n) {
        if (h != null && n != null && isBSTNode(h, n, n.value)) {
            return maxTopo(h, n.left) + maxTopo(h, n.right) + 1;
        }
        return 0;
    }

    public static boolean isBSTNode(Node h, Node n, int value) {
        if (h == null) {
            return false;
        }
        if (h == n) {
            return true;
        }
        return isBSTNode(h.value > value ? h.left : h.right, n, value);
    }

    public static class Record {
        public int l;
        public int r;

        public Record(int left, int right) {
            this.l = left;
            this.r = right;
        }

    }

    public static int bstToposize2(Node head) {
        Map<Node, Record> map = new HashMap<Node, Record>();
        return posOrder(head, map);
    }

    public static int posOrder(Node h, Map<Node, Record> map) {
        if (h == null) {
            return 0;
        }
        int ls = posOrder(h.left, map);
        int rs = posOrder(h.right, map);
        modifyMap(h.left, h.value, map, true);
        modifyMap(h.right, h.value, map, false);
        Record lr = map.get(h.left);
        Record rr = map.get(h.right);
        int lbst = lr == null ? 0 : lr.l + lr.r + 1;
        int rbst = rr == null ? 0 : rr.l + rr.r + 1;
        map.put(h, new Record(lbst, rbst));
        return Math.max(lbst + rbst + 1, Math.max(ls, rs));
    }

    public static int modifyMap(Node n, int v, Map<Node, Record> m, boolean s) {
        if (n == null || (!m.containsKey(n))) {
            return 0;
        }
        Record r = m.get(n);
        if ((s && n.value > v) || ((!s) && n.value < v)) {
            m.remove(n);
            return r.l + r.r + 1;
        } else {
            int minus = modifyMap(s ? n.right : n.left, v, m, s);
            if (s) {
                r.r = r.r - minus;
            } else {
                r.l = r.l - minus;
            }
            m.put(n, r);
            return minus;
        }
    }

}
