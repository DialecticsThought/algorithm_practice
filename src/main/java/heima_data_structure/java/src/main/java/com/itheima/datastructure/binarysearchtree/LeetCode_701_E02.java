package heima_data_structure.java.src.main.java.com.itheima.datastructure.binarysearchtree;

/**
 * 新增节点 (题目前提：val 一定与树中节点不同)
 */
public class LeetCode_701_E02 {

    /*
            val=1

            5
           / \
          2   6
           \
            3
     */
    public TreeNode insertIntoBST(TreeNode node, int val) {
        if (node == null) {
            return new TreeNode(val);
        }
        if (val < node.val) {
            node.left = insertIntoBST(node.left, val);
        } else if (node.val < val) {
            node.right = insertIntoBST(node.right, val);
        }
        return node;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5, new TreeNode(2, null, new TreeNode(3)), new TreeNode(6));
        new LeetCode_701_E02().insertIntoBST(root, 1);
    }
}
