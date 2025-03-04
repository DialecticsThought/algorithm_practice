/**
 * @Description
 * @Author veritas
 * @Data 2025/3/4 20:16
 */
public class LeetCode_108_ConvertSortedArrayToBST {

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

    public TreeNode sortedArrayToBST(int[] nums, int left, int right) {
        // base case
        if (left > right) {
            return null;
        }
        // 得到当前这一层的子树的根节点索引
        int mid = left + (right - left) / 2;
        // 构造出当前这一层的子树的根节点
        TreeNode treeNode = new TreeNode(nums[mid]);
        // 递归中的"递"
        TreeNode leftNode = sortedArrayToBST(nums, left, mid - 1);
        // 递归中的"递"
        TreeNode rightNode = sortedArrayToBST(nums, mid + 1, right);
        // 当前子树的根节点 赋值
        treeNode.left = leftNode;
        treeNode.right = rightNode;

        // 向上范围当前这一层的子树的根节点
        return treeNode;
    }
}
