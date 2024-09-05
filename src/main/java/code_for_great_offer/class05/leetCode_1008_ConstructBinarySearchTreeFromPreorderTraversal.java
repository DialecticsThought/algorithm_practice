package code_for_great_offer.class05;

import java.util.Stack;

// 本题测试链接 : https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/
public class leetCode_1008_ConstructBinarySearchTreeFromPreorderTraversal {

	// 不用提交这个类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode() {
		}

		public TreeNode(int val) {
			this.val = val;
		}

		public TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}

	// 提交下面的方法
	public static TreeNode bstFromPreorder1(int[] pre) {
		if (pre == null || pre.length == 0) {
			return null;
		}
		return process1(pre, 0, pre.length - 1);
	}

	public static TreeNode process1(int[] pre, int L, int R) {
		//目的是如果是无效范围 也就是没有左子树或者右子树 也能正常返回
		if (L > R) {
			return null;
		}
		// 初始化firstBig为L+1，用于寻找第一个大于根节点的元素。
		// L是头结点 直接从L+1
		int firstBig = L + 1;
		// 循环遍历，找到第一个大于根节点的元素的索引。
		for (; firstBig <= R; firstBig++) {
			if (pre[firstBig] > pre[L]) {
				break;
			}
		}
		//建立根结点
		TreeNode head = new TreeNode(pre[L]);
		//左子树遍历
		head.left = process1(pre, L + 1, firstBig - 1);
		//右子树遍历
		head.right = process1(pre, firstBig, R);
		// 返回构建好的树的根节点。
		return head;
	}

	// 已经是时间复杂度最优的方法了，但是常数项还能优化
	public static TreeNode bstFromPreorder2(int[] pre) {
		if (pre == null || pre.length == 0) {
			return null;
		}

		//N用于存储数组pre的长度。
		int N = pre.length;
		// nearBig数组用于存储每个元素后面第一个比它大的元素的索引。
		int[] nearBig = new int[N];
		// 初始化nearBig数组并填充为-1。这个数组将存储每个元素后面第一个比它大的元素的索引。
		for (int i = 0; i < N; i++) {
			nearBig[i] = -1;
		}
		//创建一个栈stack来帮助找出每个元素的“近大值”。
		Stack<Integer> stack = new Stack<>();
		// 遍历pre数组。对于每个元素，当栈不为空且栈顶元素小于当前元素时，
		// 更新nearBig数组，并将栈顶元素弹出。然后将当前元素索引入栈。
		for (int i = 0; i < N; i++) {
			while (!stack.isEmpty() && pre[stack.peek()] < pre[i]) {
				nearBig[stack.pop()] = i;
			}
			stack.push(i);
		}
		return process2(pre, 0, N - 1, nearBig);
	}

	public static TreeNode process2(int[] pre, int L, int R, int[] nearBig) {
		// 检查给定的子数组范围，如果左边界大于右边界，表示没有元素，因此返回null。
		if (L > R) {
			return null;
		}
		/**
		 * (nearBig[L] == -1 || nearBig[L] > R) 表示无效
		 * 计算firstBig，即数组中第一个比pre[L]大的元素的索引。
		 * 如果nearBig[L]为-1或大于R，说明没有找到这样的元素，因此将firstBig设置为R + 1。
		 */
		int firstBig = (nearBig[L] == -1 || nearBig[L] > R) ? R + 1 : nearBig[L];
		// 创建新的树节点head，以pre[L]作为值。
		TreeNode head = new TreeNode(pre[L]);
		// 递归地构建左子树，处理数组的L + 1到firstBig - 1部分。
		head.left = process2(pre, L + 1, firstBig - 1, nearBig);
		// 递归地构建右子树，处理数组的firstBig到R部分。
		head.right = process2(pre, firstBig, R, nearBig);
		// 返回构建的树的根节点。
		return head;
	}

	// 最优解
	// 这个方法的逻辑与bstFromPreorder2类似，但使用数组而非栈来实现相同的功能。
	public static TreeNode bstFromPreorder3(int[] pre) {
		if (pre == null || pre.length == 0) {
			return null;
		}

		int N = pre.length;
		int[] nearBig = new int[N];
		for (int i = 0; i < N; i++) {
			nearBig[i] = -1;
		}
		// 使用一个整型数组stack来模拟栈的操作。
		int[] stack = new int[N];
		// size变量用来表示栈的当前大小。
		int size = 0;
		// 遍历数组，使用数组模拟的栈来找出每个元素的“近大值”
		for (int i = 0; i < N; i++) {
			// 当栈非空且栈顶元素小于当前元素时，更新nearBig数组，并减小栈的大小。然后将当前元素索引添加到栈中。
			while (size != 0 && pre[stack[size - 1]] < pre[i]) {
				nearBig[stack[--size]] = i;
			}
			stack[size++] = i;
		}
		return process3(pre, 0, N - 1, nearBig);
	}

	public static TreeNode process3(int[] pre, int L, int R, int[] nearBig) {
		if (L > R) {
			return null;
		}
		// 计算firstBig，即数组中第一个比pre[L]大的元素的索引。
		int firstBig = (nearBig[L] == -1 || nearBig[L] > R) ? R + 1 : nearBig[L];
		// 创建新的树节点head，以pre[L]作为值。
		TreeNode head = new TreeNode(pre[L]);
		// 递归地构建左右子树。
		head.left = process3(pre, L + 1, firstBig - 1, nearBig);
		head.right = process3(pre, firstBig, R, nearBig);
		// 返回构建的树的根节点。
		return head;
	}
}
