import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Description
 * @Author veritas
 * @Data 2025/3/4 15:13
 */
public class LeetCode_102_LevelTraversal {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        // base case
        if (root == null) {
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        //初始化 这个队列
        queue.offer(root);

        while (!queue.isEmpty()) {
            // 每一次 遍历 先记住 当前队列的大小 也就是要遍历的数的这一层的节点个数
            int size = queue.size();
            // 存放当前层的结果的数组
            List<Integer> level = new ArrayList<>();
            // 遍历当前层
            for (int i = 0; i < size; i++) {
                TreeNode treeNode = queue.poll();
                level.add(treeNode.val);
                if(treeNode.left != null) {
                    queue.offer(treeNode.left);
                }
                if (treeNode.right != null) {
                    queue.offer(treeNode.right);
                }
            }
            result.add(level);
        }
        return result;
    }
}
