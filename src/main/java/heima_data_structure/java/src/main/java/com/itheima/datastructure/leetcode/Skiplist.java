package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.util.Random;

class Skiplist {
    int level = 10;
    class Node {
        int val;
        Node[] forward = new Node[level];
        Node (int _val) {
            val = _val;
        }
    }
    Random random = new Random();
    Node he = new Node(-1);
    void find(int t, Node[] ns) {
        Node cur = he;
        for (int i = level - 1; i >= 0; i--) {
            while (cur.forward[i] != null && cur.forward[i].val < t) cur = cur.forward[i];
            ns[i] = cur;
        }
    }
    public boolean search(int t) {
        Node[] ns = new Node[level];
        find(t, ns);
        return ns[0].forward[0] != null && ns[0].forward[0].val == t;
    }
    public void add(int t) {
        Node[] ns = new Node[level];
        find(t, ns);
        Node node = new Node(t);
        for (int i = 0; i < level; i++) {
            node.forward[i] = ns[i].forward[i];
            ns[i].forward[i] = node;
            if (random.nextInt(2) == 0) break;
        }
    }
    public boolean erase(int t) {
        Node[] ns = new Node[level];
        find(t, ns);
        Node node = ns[0].forward[0];
        if (node == null || node.val != t) return false;
        for (int i = 0; i < level && ns[i].forward[i] == node; i++)
            ns[i].forward[i] = ns[i].forward[i].forward[i];
        return true;
    }
}