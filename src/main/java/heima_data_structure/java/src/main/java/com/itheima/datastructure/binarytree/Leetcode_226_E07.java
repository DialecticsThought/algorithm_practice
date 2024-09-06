package heima_data_structure.java.src.main.java.com.itheima.datastructure.binarytree;

/**
 * 翻转二叉树
 */
public class Leetcode_226_E07 {
    public TreeNode invertTree(TreeNode root) {
        fn(root);
        return root;
    }

    private static void fn(TreeNode node) {
        if (node == null) {
            return;
        }
        TreeNode t = node.left;
        node.left = node.right;
        node.right = t;

        fn(node.left);
        fn(node.right);
    }
}
