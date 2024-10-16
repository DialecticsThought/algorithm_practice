package code_for_great_offer.class30;

public class LeetCode_0098_ValidateBinarySearchTree {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
	}
	//morris遍历
	public boolean isValidBST(TreeNode root) {
		if (root == null) {
			return true;
		}
		TreeNode cur = root;
		TreeNode mostRight = null;
		Integer pre = null;
		boolean ans = true;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					mostRight.right = null;
				}
			}
			if (pre != null && pre >= cur.val) {
				ans = false;
			}
			pre = cur.val;
			cur = cur.right;
		}
		return ans;
	}

}
