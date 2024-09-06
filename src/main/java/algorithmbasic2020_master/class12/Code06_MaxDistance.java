package algorithmbasic2020_master.class12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 树形dp套路第一步:
 * 以某个节点X为头节点的子树中，分析答案有哪些可能性，并且这种分析是以X的左子树、X的右子树和X整棵树的角度来考虑可能性的
 * 树形dp套路第二步:
 * 根据第一步的可能性分析，列出所有需要的信息树形dp套路第三步:
 * 合并第二步的信息，对左树和右树提出同样的要求，并写出信息结构
 * 树形dp套路第四步:
 * 设计递归函数，递归函数是处理以X为头节点的情况下的答案。
 * 包括设计递归的basecase，默认直接得到左树和右树的所有信息，以及把可能性做整合，并且要返回第三步的信息结构这四个小步骤
 * TODO
 * 叉树节点间的最大距离问题
 * 从二叉树的节点a出发，可以向上或者向下走，但沿途的节点只能经过一次，
 * 到达节点b时路径上的节点个数叫作a到b的距离，那么二叉树任何两个节点之间都有距离，求整棵树上的最大距离。
 */
public class Code06_MaxDistance {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 二叉树的递归套路深度实践
     * 给定一棵二叉树的头节点head，任何两个节点之间都存在距离，返回整棵二叉树的最大距离
     */
    public static int maxDistance1(Node head) {
        if (head == null) {
            return 0;
        }
        ArrayList<Node> arr = getPrelist(head);
        HashMap<Node, Node> parentMap = getParentMap(head);
        int max = 0;
        for (int i = 0; i < arr.size(); i++) {
            for (int j = i; j < arr.size(); j++) {
                max = Math.max(max, distance(parentMap, arr.get(i), arr.get(j)));
            }
        }
        return max;
    }

    public static ArrayList<Node> getPrelist(Node head) {
        ArrayList<Node> arr = new ArrayList<>();
        fillPrelist(head, arr);
        return arr;
    }

    public static void fillPrelist(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPrelist(head.left, arr);
        fillPrelist(head.right, arr);
    }

    public static HashMap<Node, Node> getParentMap(Node head) {
        HashMap<Node, Node> map = new HashMap<>();
        map.put(head, null);
        fillParentMap(head, map);
        return map;
    }

    public static void fillParentMap(Node head, HashMap<Node, Node> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
    }

    public static int distance(HashMap<Node, Node> parentMap, Node o1, Node o2) {
        HashSet<Node> o1Set = new HashSet<>();
        Node cur = o1;
        o1Set.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            o1Set.add(cur);
        }
        cur = o2;
        while (!o1Set.contains(cur)) {
            cur = parentMap.get(cur);
        }
        Node lowestAncestor = cur;
        cur = o1;
        int distance1 = 1;
        while (cur != lowestAncestor) {
            cur = parentMap.get(cur);
            distance1++;
        }
        cur = o2;
        int distance2 = 1;
        while (cur != lowestAncestor) {
            cur = parentMap.get(cur);
            distance2++;
        }
        return distance1 + distance2 - 1;
    }


    public static int maxDistance2(Node head) {
        return process(head).maxDistance;
    }

    /**
     * TODO
     * 返回的信息 包括
     * 一开始为头结点 后面是每个遍历到的节点的左子树的最大距离
     * 一开始为头结点 后面是每个遍历到的节点的右子树的最大距离
     * 左子树的高度
     * 右子树的高度
     * 因为一棵树的某两个节点的最大距离要考虑 是否与头结点有关
     * 与头结点没有关系：求出 头结点x的左/右子树的最大距离
     * 与头结点有关系： 求出左右子树的高度
     */
    public static class Info {
        public int maxDistance;// 最大距离
        public int height;// 子树的高度

        public Info(int m, int h) {
            maxDistance = m;
            height = h;
        }

    }

    /**
     * TODO
     * 二叉树的递归套路深度实践
     * 给定一棵二叉树的头节点head，任何两个节点之间都存在距离，返回整棵二叉树的最大距离
     */
    public static Info process(Node x) {
        if (x == null) {//头结点为空 最大距离为0  最大高度为0
            return new Info(0, 0);
        }
        /**
         * 求出 每个遍历到的节点的所有信息
         * 左子树的信息
         * 右子树的信息
         * 最后求出以自己为头结点的树的信息
         * */
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);
        /**
         * 根据 每个遍历到的节点的信息
         * 得到对于当前遍历到的节点而言 该节点为头结点的树的高度（因为每一次遍历 是遍历不同的子树 所以头结点会变化 一开始是x）
         * 得到右子树中最大距离的信息
         * 得到左子树中最大距离的信息
         * */
        //TODO +1的原因是头结点算一层
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int p1 = leftInfo.maxDistance;
        int p2 = rightInfo.maxDistance;
        /**
         * 这里是求出 对于如果通过递归遍历到的当前节点n的这颗树而言
         * 存在一个某两个节点之间的最大距离与该节点n有关 那么左子树的高度+右子树的高度+1（根节点）
         * */
        int p3 = leftInfo.height + rightInfo.height + 1;
        /**
         * 通过比较 得出最大距离
         * p1 p2 p3中的最大的一个
         * */
        int maxDistance = Math.max(Math.max(p1, p2), p3);
        return new Info(maxDistance, height);
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxDistance1(head) != maxDistance2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
