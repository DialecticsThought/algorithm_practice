package code_for_great_offer.class30;

public class Problem_0101_SymmetricTree {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
	}

	public boolean isSymmetric(TreeNode root) {
		return isMirror(root, root);
	}

	/*
	* 判断2个树是否是镜像树
	* 一棵树是原始树  head1
	* 另一棵是翻面树  head2
	* */
	public static boolean isMirror(TreeNode head1, TreeNode head2) {
		//来到当前2个节点都是空
		if (head1 == null && head2 == null) {
			return true;
		}
		if (head1 != null && head2 != null) {
			/*
			*TODO 3个条件
			* 当前来到的两个节点相同
			* 一个节点的左子树 == 另一个节点的右子树
			* 一个节点的右子树 == 另一个节点的左子树
			* */
			return head1.val == head2.val
					&& isMirror(head1.left, head2.right)
					&& isMirror(head1.right, head2.left);
		}
		// 一个为空，一个不为空  false
		return false;
	}

}
