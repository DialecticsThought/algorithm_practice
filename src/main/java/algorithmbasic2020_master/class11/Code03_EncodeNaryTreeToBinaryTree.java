package algorithmbasic2020_master.class11;

import java.util.ArrayList;
import java.util.List;

// 本题测试链接：https://leetcode.com/problems/encode-n-ary-tree-to-binary-tree
public class Code03_EncodeNaryTreeToBinaryTree {

	// 提交时不要提交这个类
	public static class Node {
		public int val;
		public List<Node> children;

		public Node() {
		}

		public Node(int _val) {
			val = _val;
		}

		public Node(int _val, List<Node> _children) {
			val = _val;
			children = _children;
		}
	};

	// 提交时不要提交这个类
	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	// 只提交这个类即可
	class Codec {
		// Encodes an n-ary tree to a binary tree.
		public TreeNode encode(Node root) {
			if (root == null) {
				return null;
			}
			TreeNode head = new TreeNode(root.val);
			//TODO 把所有孩子 往自己左子树上挂
			head.left = en(root.children);
			return head;
		}

		private TreeNode en(List<Node> children) {
			TreeNode head = null;
			TreeNode cur = null;
			for (Node child : children) {
				TreeNode tNode = new TreeNode(child.val);
				/*
				* TODO
				*  head表示 多叉树中 某一个节点的第一个子节点
				*  某一个节点的 的剩下的子节点
				*  会成为第一个子节点的右边界
				*  eg:
				*  第2个孩子节点是第1个孩子节点的右子节点
				*  第3个孩子节点是第2个孩子节点的右子节点
				*  ......
				* */
				if (head == null) {
					head = tNode;
				} else {
					cur.right = tNode;
				}
				cur = tNode;
				/*
				* TODO
				*  深度优先遍历
				*  也就是先到达 多叉树的最后一层的最左侧的节点x
				*  从该节点x开始 把该节点x的兄弟节点变成自己的右边界
				*  再回到该节点的父节点y 把该节点y的兄弟节点变成自己的右边界
				*  再回到该节点的父节点z 把该节点z的兄弟节点变成自己的右边界
				*  ......
				*  直到根节点
				* */
				cur.left = en(child.children);
			}
			return head;
		}

		// Decodes your binary tree to an n-ary tree.
		public Node decode(TreeNode root) {
			if (root == null) {
				return null;
			}
			return new Node(root.val, de(root.left));
		}
		/*
		* TODO 传入 某节点的第一个子节点
		*  不断地把自己的右边界 变成 兄弟节点 最后都放在一个集合里 返回给父节点
		* */
		public List<Node> de(TreeNode root) {
			List<Node> children = new ArrayList<>();
			while (root != null) {
				//也是深度优先
				Node cur = new Node(root.val, de(root.left));
				children.add(cur);
				root = root.right;
			}
			return children;
		}

	}

}
