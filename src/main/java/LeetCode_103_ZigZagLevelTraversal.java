import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Description
 * @Author veritas
 * @Data 2025/3/4 15:38
 */
public class LeetCode_103_ZigZagLevelTraversal {
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

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        // 用于控制遍历方向，true 表示从左到右，false 表示从右到左
        boolean leftToRight = true;

        Queue<TreeNode> queue1 = new LinkedList<>();
        queue1.offer(root);

        while (!queue1.isEmpty()) {
            int size = queue1.size();

            //用来保存的当前层遍历的结果
            List<Integer> level = new LinkedList<>();
            // 遍历当前层
            for(int i = 0; i < size; i++) {
                TreeNode node = queue1.poll();
                if(leftToRight){// 从左往右依次遍历
                    level.addLast(node.val);
                }else {// 从右往左 依次遍历
                    level.addFirst(node.val);
                }
                if(node.left != null) {
                    queue1.offer(node.left);
                }
                if(node.right != null) {
                    queue1.offer(node.right);
                }
            }
            // 把当前层的结果 放入总结果
            result.add(level);
            // 改变状态 给下一次遍历做准备
            leftToRight = !leftToRight;
        }
        return result;
    }
}
