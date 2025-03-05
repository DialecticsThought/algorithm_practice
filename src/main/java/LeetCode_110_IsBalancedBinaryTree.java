/**
 * @Description
 * @Author veritas
 * @Data 2025/3/4 22:02
 */
public class LeetCode_110_IsBalancedBinaryTree {
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

    /**
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {
        return getBalanced(root) != -1;
    }

    /**
     * 利用求树的高度的方法 改良得到 求平衡树
     * @param root
     * @return
     */
    public int getBalanced(TreeNode root) {
        // base case
        if (root == null) {
            return 0;
        }
        // 得到左子树高度
        int leftHeight = getBalanced(root.left);
        // 如果左子树已经不平衡，则直接返回 -1
        if(leftHeight == -1) {
            return -1;
        }
        // 得到右子树高度
        int rightHeight = getBalanced(root.right);
        // 如果右子树已经不平衡，则直接返回 -1
        if(rightHeight == -1) {
            return -1;
        }
        // 判断当前节点的左右子树 是否平衡
        if(Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }
        // 计算出 当前节点的树的高度
        int currentHeight = Math.max(leftHeight, rightHeight) + 1;
        // 返回
        return currentHeight;
    }
}
