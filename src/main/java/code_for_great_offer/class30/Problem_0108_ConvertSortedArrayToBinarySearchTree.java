package code_for_great_offer.class30;

public class Problem_0108_ConvertSortedArrayToBinarySearchTree {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int val) {
			this.val = val;
		}
	}

	public TreeNode sortedArrayToBST(int[] nums) {
		return process(nums, 0, nums.length - 1);
	}
	/*
	*TODO
	* 把arr的L~R范围装成二叉搜索树 把头部返回
	* 1.找到L~R的中点
	* 2.调用 把arr的L~M-1范围装成二叉搜索树 即左子树
	* 3.调用 把arr的M+1~R范围装成二叉搜索树 即右子树
	* 4.返回头部
	* */
	public static TreeNode process(int[] nums, int L, int R) {
		if (L > R) {
			return null;
		}
		if (L == R) {
			return new TreeNode(nums[L]);
		}
		int M = (L + R) / 2;
		TreeNode head = new TreeNode(nums[M]);
		//TODO 在 二分的左侧范围 左递归 返回左子树的头
		head.left = process(nums, L, M - 1);
		//TODO 在 右分的左侧范围 右递归 返回右子树的头
		head.right = process(nums, M + 1, R);
		return head;
	}

}
