package code_for_great_offer.class37;

public class LeetCode_0226_InvertBinaryTree {

	public class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	public static TreeNode invertTree(TreeNode root) {
		if (root == null) {
			return null;
		}
		TreeNode left = root.left;
		//TODO 旋转右子树 再让左指针指向这个被旋转过的子树的头
		root.left = invertTree(root.right);
		//TODO 旋转左子树 再右让指针指向这个被旋转过的子树的头
		root.right = invertTree(left);
		return root;
	}

}
