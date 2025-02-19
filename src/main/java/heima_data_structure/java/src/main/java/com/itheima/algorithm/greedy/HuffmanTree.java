package heima_data_structure.java.src.main.java.com.itheima.algorithm.greedy;

import java.util.*;

/**
 * <h3>Huffman 树以及编解码</h3>
 */
public class  HuffmanTree {
    /**
        Huffman 树的构建过程：
        频次高的节点要求路径短 频次低的节点路径长
        1. 将统计了出现频率的字符，放入优先级队列
        2. 每次出队两个频次最低的元素，给它俩找个爹, TODO 找了个爹的话相当于让频次低的节点路径变长了
        3. 把爹重新放入队列，重复 2~3
        4. 当队列只剩一个元素时，Huffman 树构建完成
     */
    static class Node {
        Character ch; // 字符
        int freq;     // 频次
        Node left;
        Node right;
        String code;  // 编码

        public Node(Character ch) {
            this.ch = ch;
        }

        public Node(int freq, Node left, Node right) {
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        int freq() {
            return freq;
        }

        /**
         * huffman树 只要有左孩子 就必有右孩子
         * @return
         */
        boolean isLeaf() {
            return left == null;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "ch=" + ch +
                    ", freq=" + freq +
                    '}';
        }
    }

    String str;
    Map<Character, Node> map = new HashMap<>();
    Node root;

    public HuffmanTree(String str) {
        this.str = str;
        // 功能1：统计频率
        char[] charArray = str.toCharArray();

        for(char c : charArray) {
            if(!map.containsKey(c)) {
                map.put(c, new Node(c));
            }else {
                Node node = map.get(c);
                node.freq++;
            }
        }
        // 功能2: 构造树
        // 队列的元素先按照freq排序
        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::freq));
        // 得到所有的值
        Collection<Node> values = map.values();
        // 队列如果 只剩下一个元素的时候 就结束循环
        while(nodePriorityQueue.size()>1){
            // 弹出第1个节点 作为左节点
            Node first = nodePriorityQueue.poll();
            // 弹出第2个节点 作为左节点
            Node second = nodePriorityQueue.poll();
            //创建父节点
            int fatherFrequence = first.freq + second.freq;
            Node father = new Node(fatherFrequence, first, second);
            // 放入队列
            nodePriorityQueue.offer(father);
        }
        Node root = nodePriorityQueue.poll();
        // 功能3：计算每个字符的编码 本质就是从根节点遍历到指定的节点 + 功能4：字符串编码后占用 bits
        int sum = dfs(root,new StringBuilder());
        for(Node node : values) {
            System.out.println(node+" " + node.code);
        }
    }

    /**
     * <pre>
     *     遍历这棵树的时候
     *     每一个节点会被访问3次
     *     当前节点往左孩子走的话 sum + 0  往右孩子走的话 sum + 1
     *     当前节点往左孩子走的话 sum - 0  往右孩子走的话 sum - 1
     *                        A
     *              +0  ↙ ↗ -0    ↖ ↘  -1
     *               ↙ ↗        +1  ↖ ↘
     *              B                 C
     *   +0  ↙ ↗ -0    ↖ ↘  -1
     *      ↙ ↗      +1  ↖ ↘
     *    D                  E
     * </pre>
     * @param node
     * @param code
     * @return
     */
    private int dfs(Node node, StringBuilder code) {
        // 用来计算 该 字符 占用了多少位
        int sum = 0;
        // 判断当前遍历到的节点是否是叶子结点
        if (node.isLeaf()) {
            node.code = code.toString();
            // 如果该字符是树的叶子节点 该字符的编码 * 出现的频率
            sum = node.freq * code.length();
        } else {
            // 非叶子节点 就是 左右孩子的 位的和
            // TODO 原始节点是没有孩子的 只有认为构造的节点才有孩子
            // 遍历左孩子
            sum += dfs(node.left, code.append("0"));
            // 遍历右孩子
            sum += dfs(node.right, code.append("1"));
        }
        // 到这里 是 当前节点 的孩子遍历完毕
        // 现在是递归的 "归" 也就是从当前节点 回到 父节点
        // 删掉最后一个字符
        if (code.length() > 0) {
            code.deleteCharAt(code.length() - 1);
        }
        return sum;
    }

    /**
     * 编码
     * @return
     */
    public String encode() {
        char[] chars = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            sb.append(map.get(c).code);
        }
        return sb.toString();
    }

    /**
     * 解码
     * @param str
     * @return
     */
    public String decode(String str) {
        /**
            从根节点，寻找数字对应的字符
                数字是 0 向左走
                数字是 1 向右走
                如果没走到头，每走一步数字的索引 i++
            走到头就可以找到解码字符，再将 node 重置为根节点

            a 00
            b 10
            c 1

                            i
            0   0   0   1   0   1   1   1   1   1   1   1   1
         */
        char[] chars = str.toCharArray();
        int i = 0;
        StringBuilder sb = new StringBuilder();
        Node node = root;
        //             i = 13  node=root
        // 0001011111111
        while (i < chars.length) {
            if (!node.isLeaf()) { // 非叶子
                if(chars[i] == '0') { // 向左走
                    node = node.left;
                } else if(chars[i] == '1') { // 向右走
                    node = node.right;
                }
                i++;
            }
            // 单独写成if 不用else的原因
            // 上面的if判断中 可能导致从非叶子节点 遍历到叶子结点 也就是 0 和 1 的分支
            // i++ 之后 就退出if代码块 但是此时已经是叶子结点了
            if (node.isLeaf()) {
                sb.append(node.ch);
                node = root;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        HuffmanTree tree = new HuffmanTree("abbccccccc");
        String encoded = tree.encode();
        System.out.println(encoded);

        System.out.println(tree.decode(encoded));
    }
}
