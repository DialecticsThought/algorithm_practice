package code_for_great_offer.class47;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 1
 * ↙    ↓    ↘
 * 2    3	  4
 * 5 6
 * 7
 * 调用方法
 * 1,[2,3,4]  []里面的是1的孩子
 * 2有子节点调用该方法
 * 也就是说
 * f(1) => 1,[f(2),3,4]
 * f(2) => 2,[f(5),6]
 * f(5) => 5,[7]
 * 递归调用
 */
public class LeetCode_0428_SerializeAndDeserializeNaryTree {

    // 不要提交这个类
    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {
            children = new ArrayList<>();
        }

        public Node(int _val) {
            val = _val;
            children = new ArrayList<>();
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    ;

    // 提交下面这个类
    public static class Codec {

        public static String serialize(Node root) {
            if (root == null) { // 空树！直接返回#
                return "#";
            }
            StringBuilder builder = new StringBuilder();
            serial(builder, root);
            return builder.toString();
        }

        // 当前来到head
        private static void serial(StringBuilder builder, Node head) {
            //当前节点的值
            builder.append(head.val + ",");
            if (!head.children.isEmpty()) {
                //开头
                builder.append("[,");
                //填充当前节点的子节点
                for (Node next : head.children) {
                    serial(builder, next);
                }
                //结尾
                builder.append("],");
            }
        }

        /**
         * |   1
         * |2  3  4
         * 序列化之后是这样1,[2,3,4]
         * 那么反序列化就是 找到1，再找到"["和"]"
         *
         * @param data
         * @return
         */
        public static Node deserialize(String data) {
            if (data.equals("#")) {
                return null;
            }
            String[] splits = data.split(",");
            Queue<String> queue = new LinkedList<>();
            for (String str : splits) {
                queue.offer(str);
            }
            return deserial(queue);
        }

        private static Node deserial(Queue<String> queue) {
            Node cur = new Node(Integer.valueOf(queue.poll()));
            cur.children = new ArrayList<>();
            //队列不为空，队列队首不是"[" 说明是子节点
            if (!queue.isEmpty() && queue.peek().equals("[")) {
                queue.poll();
                while (!queue.peek().equals("]")) {
                    Node child = deserial(queue);
                    cur.children.add(child);
                }
                queue.poll();
            }
            return cur;
        }

    }

    public static void main(String[] args) {
        // 如果想跑以下的code，请把Codec类描述和内部所有方法改成static的
        Node a = new Node(1);
        Node b = new Node(2);
        Node c = new Node(3);
        Node d = new Node(4);
        Node e = new Node(5);
        Node f = new Node(6);
        Node g = new Node(7);
        a.children.add(b);
        a.children.add(c);
        a.children.add(d);
        b.children.add(e);
        b.children.add(f);
        e.children.add(g);
        String content = Codec.serialize(a);
        System.out.println(content);
        Node head = Codec.deserialize(content);
        System.out.println(content.equals(Codec.serialize(head)));
    }

}
