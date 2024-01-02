package code_for_great_offer.class03;

import java.util.*;

public class Code08_DistanceKNodes {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static List<Node> distanceKNodes(Node root, Node target, int K) {
        HashMap<Node, Node> parents = new HashMap<>();
        parents.put(root, null);
        createParentMap(root, parents);
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> visited = new HashSet<>();
        queue.offer(target);
        visited.add(target);
        int curLevel = 0;
        List<Node> ans = new ArrayList<>();
        while (!queue.isEmpty()) {
            /**
             *TODO 计算出容量大小 这个容量大小决定了这一批有几个节点 也决定了做某件事的次数
             * 某件事指的是把当前队列的队首弹出 并把该节点的子节点（直接邻居）放入队列
             * 目的:能够定位当前是第几层
             * */
            int size = queue.size();
            while (size-- > 0) {//TODO 说明这一批没有完成 也就是同一层
                Node cur = queue.poll();
                if (curLevel == K) {//TODO 到达第K层节点 收集答案
                    ans.add(cur);
                }
                //TODO 当前节点的左子节点 放入队列
                if (cur.left != null && !visited.contains(cur.left)) {
                    visited.add(cur.left);
                    queue.offer(cur.left);
                }
                //TODO 当前节点的右子节点 放入队列
                if (cur.right != null && !visited.contains(cur.right)) {
                    visited.add(cur.right);
                    queue.offer(cur.right);
                }
                //TODO 当前节点的父节点 放入队列
                if (parents.get(cur) != null && !visited.contains(parents.get(cur))) {
                    visited.add(parents.get(cur));
                    queue.offer(parents.get(cur));
                }
            }
            curLevel++;//TODO 当前的遍历到的层数+1 用来搞下一批
            if (curLevel > K) {
                break;
            }
        }
        return ans;
    }

    //TODO 来到某个节点 设置 <某节点的子节点，该节点> ,来到某节点的子节点 继续干这件事
    public static void createParentMap(Node cur, HashMap<Node, Node> parents) {
        if (cur == null) {
            return;
        }
        if (cur.left != null) {
            parents.put(cur.left, cur);
            createParentMap(cur.left, parents);
        }
        if (cur.right != null) {
            parents.put(cur.right, cur);
            createParentMap(cur.right, parents);
        }
    }

    public static void main(String[] args) {
        Node n0 = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        Node n8 = new Node(8);

        n3.left = n5;
        n3.right = n1;
        n5.left = n6;
        n5.right = n2;
        n1.left = n0;
        n1.right = n8;
        n2.left = n7;
        n2.right = n4;

        Node root = n3;
        Node target = n5;
        int K = 2;

        List<Node> ans = distanceKNodes(root, target, K);
        for (Node o1 : ans) {
            System.out.println(o1.value);
        }

    }

}
