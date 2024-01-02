 package code_for_great_offer.class35;

/*
* 给定一个二叉树的root，返回最长的路径的长度 ，这个路径中的每个节点具有相同值。 这条路径可以经过也可以不经过根节点。
* 两个节点之间的路径长度由它们之间的边数表示。
* 来源：力扣（LeetCode）
* 链接：https://leetcode.cn/problems/longest-univalue-path
*TODO
* 与x无关
*  左子树上的 最大路径
*  右子树上的 最大路径
* 与x有关
* 	1.只有x自己
* 	2.左子节点也是 x，而且从左子节点开始 与x相同的节点形成的最大路径
* 	3.右子节点也是 x，而且从右子节点开始 与x相同的节点形成的最大路径
* 需要的信息：
*  左子节点为头的子树中 不以左子节点出发的最大路径
*  右子节点为头的子树中 不以右子节点出发的最大路径
*  左子节点为头的子树中 要求以左子节点出发的最大路径
*  右子节点为头的子树中 要求以右子节点出发的最大路径
* */
public class Problem_0687_LongestUnivaluePath {

	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int v) {
			val = v;
		}
	}

	public static int longestUnivaluePath(TreeNode root) {
		if (root == null) {
			return 0;
		}
		return process(root).max - 1;
	}

	// 建设以x节点为头的树，返回两个信息
	public static class Info {
		// 在一条路径上：要求每个节点通过且只通过一遍
		public int len; //TODO 路径必须从x出发且只能往下走的情况下，路径的最大距离
		public int max; //TODO 路径不要求必须从x出发的情况下，整棵树的合法路径最大距离

		public Info(int l, int m) {
			len = l;
			max = m;
		}
	}

	private static Info process(TreeNode x) {
		if (x == null) {
			return new Info(0, 0);
		}
		TreeNode l = x.left;
		TreeNode r = x.right;
		// 左树上，不要求从左孩子出发，最大路径
		// 左树上，必须从左孩子出发，往下的最大路径
		Info linfo = process(l);
		// 右树上，不要求从右孩子出发，最大路径
		// 右树上，必须从右孩子出发，往下的最大路径
		Info rinfo = process(r);
		//TODO case1 必须从x出发的情况下，往下的最大路径
		int len = 1;
		if (l != null && l.val == x.val) {
			len = linfo.len + 1;
		}
		if (r != null && r.val == x.val) {
			len = Math.max(len, rinfo.len + 1);
		}
		//TODO case2 不要求从x出发，最大路径
		int max = Math.max(Math.max(linfo.max, rinfo.max), len);
		if (l != null && r != null && l.val == x.val && r.val == x.val) {
			max = Math.max(max, linfo.len + rinfo.len + 1);
		}
		return new Info(len, max);
	}

}
