import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Description
 * @Author veritas
 * @Data 2025/3/5 17:10
 */
public class LeetCode_116_PopulatingNextRightPointers {
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }
    public Node connect(Node root) {
        if (root == null) {
            return null;
        }
        // 准备一个队列
        // 先放入一个根节点
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        //循环退出条件: 队列没有任何元素
        while(!queue.isEmpty()) {
            // 得到当前层的元素数量
            int size = queue.size();
            // 需要记录遍历到的节点的前一个节点
            Node prev = null;
            // 再开始一个循环 遍历当前层
            for(int i = 0; i < size; i++) {
                //得到第一个节点
                Node node = queue.poll();
                // prev == null 只有遍历到这一层的第一个节点的时候
                if(prev != null) {
                    prev.next = node;
                }
                prev = node;
                // 拓展下一层节点
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        // 返回根节点
        return root;
    }
}
