import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author veritas
 * @Data 2025/3/5 11:19
 */
public class LeetCode_117_PathSum2 {
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

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        pathSum(root, targetSum, result, path);
        return result;
    }

    public void pathSum(TreeNode treeNode, int targetSum,
                        List<List<Integer>> result, List<Integer> integers) {
        // basce case
        if (treeNode == null) {
            return;
        }
        // 对于当前遍历到的节点
        // 选择1: 选择把当前节点的值加入到集合
        // 只有一个选择 因为构造从根节点到叶子节点的路径 没有选择“跳过”某个节点的情况
        integers.add(treeNode.val);
        // 减去当前节点的值
        targetSum = targetSum - treeNode.val;

        //TODO 特殊 当这里遍历得到的是叶子节点
        if(treeNode.left == null && treeNode.right == null) {
            // 如果 叶子节点的值 与 剩下的被减下来的值相同 说明 该路径有效
            if(targetSum == 0) {
                // 复制一个新的数组
                result.add(new ArrayList<>(integers));
            }
        }
        // 来到 自己的左孩子
        pathSum(treeNode.left, targetSum, result, integers);
        // 来到 自己的右孩子
        pathSum(treeNode.right, targetSum, result, integers);

        // 回溯恢复现场 让上层节点去遍历其他路径
        integers.remove(integers.size() - 1);
    }
}
