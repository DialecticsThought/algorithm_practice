package heima_data_structure.java.src.main.java.com.itheima.algorithm.greedy;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h3>Huffman 树以及编解码</h3>
 */
public class HuffmanTreeColored {
    Node root;
    int length;
    String origin;
    Map<Character, Node> map = new HashMap<>();

    static class Node {
        Character ch;   // 对应字符
        int freq;       // 出现频次
        Node left;
        Node right;
        String code;
        int colorIndex;

        public Node(Character ch) {
            this.ch = ch;
        }

        public Node(int freq, Node left, Node right) {
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        public int freq() {
            return freq;
        }

        public boolean leaf() {
            return left == null;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "ch=" + ch +
                    ", code='" + code + '\'' +
                    '}';
        }

        public String colorCode() {
            return colors[colorIndex].get(code);
        }
    }

    private void build(String str) {
        char[] chars = str.toCharArray();
        for (char c : chars) {
            Node node = map.computeIfAbsent(c, Node::new);
            node.freq++;
        }
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::freq));
        queue.addAll(map.values());
        while (queue.size() >= 2) {
            Node left = queue.poll();
            Node right = queue.poll();
            int freq = left.freq + right.freq;
            queue.offer(new Node(freq, left, right));
        }
        root = queue.poll();
        length = dfs(root, new StringBuilder());
        System.out.println("字符串编码后占用 bits:" + length);
        map.forEach((k, v) -> {
            System.out.println(k + ":" + v.colorCode());
        });
        origin = str;
    }

    static Color[] colors = Color.values();
    int colorIndex = 0;

    private int dfs(Node node, StringBuilder code) {
        int sum = 0;
        if (!node.leaf()) {
            sum += dfs(node.left, code.append('0'));
            sum += dfs(node.right, code.append('1'));
        } else {
            node.colorIndex = colorIndex++;
            node.code = code.toString();
            sum = code.length() * node.freq;
        }
        if (code.length() > 0) {
            code.deleteCharAt(code.length() - 1);
        }
        return sum;
    }

    public HuffmanTreeColored(String str) {
        build(str);
    }

    public String encode() {
        StringBuilder sb = new StringBuilder(length);
        char[] chars = origin.toCharArray();
        int i = 0;
        for (char c : chars) {
            sb.append(map.get(c).code);
        }
        return sb.toString();
    }

    public String encodeColor() {
        StringBuilder sb = new StringBuilder(length);
        char[] chars = origin.toCharArray();
        for (char c : chars) {
            sb.append(map.get(c).colorCode());
        }
        return sb.toString();
    }

    public String decode(String encoded) {
        StringBuilder sb = new StringBuilder();
        char[] chars = encoded.toCharArray();
        Node node = root;
        int i = 0;
        while (i < chars.length) {
            if (!node.leaf()) {
                char c = chars[i++];
                if (c == '0') {
                    node = node.left;
                } else if (c == '1') {
                    node = node.right;
                }
            }
            if (node.leaf()) {
                sb.append(node.ch);
                node = root;
            }
        }
        return sb.toString();
    }

    @SuppressWarnings("all")
    public static void main(String[] args) {
        String origin = "abbccccccc";
        System.out.println("原始字符串:" + origin);
        HuffmanTreeColored tree =
                new HuffmanTreeColored(origin);
        System.out.println("编码:" + tree.encodeColor());
        System.out.println("解码:" + tree.decode(tree.encode()));
    }
}
