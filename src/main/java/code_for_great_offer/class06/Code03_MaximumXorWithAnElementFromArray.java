package code_for_great_offer.class06;

// 测试链接 : https://leetcode.com/problems/maximum-xor-with-an-element-from-array/
public class Code03_MaximumXorWithAnElementFromArray {

	public static int[] maximizeXor(int[] nums, int[][] queries) {
		int N = nums.length;
		NumTrie trie = new NumTrie();
		for (int i = 0; i < N; i++) {
			trie.add(nums[i]);
		}
		int M = queries.length;
		int[] ans = new int[M];
		for (int i = 0; i < M; i++) {
			ans[i] = trie.maxXorWithXBehindM(queries[i][0], queries[i][1]);
		}
		return ans;
	}

	public static class Node {
		public int min;
		public Node[] nexts;

		public Node() {
			min = Integer.MAX_VALUE;
			nexts = new Node[2];
		}
	}

	public static class NumTrie {
		public Node head = new Node();

		public void add(int num) {
			Node cur = head;
			head.min = Math.min(head.min, num);
			//符号位规定是0
			for (int move = 30; move >= 0; move--) {
				int path = ((num >> move) & 1);
				//无路新建 有路复用
				cur.nexts[path] = cur.nexts[path] == null ? new Node() : cur.nexts[path];
				//节点下移
				cur = cur.nexts[path];
				//每次下移更新当前节点所对应的树中的最小值
				cur.min = Math.min(cur.min, num);
			}
		}

		// 这个结构中，已经收集了一票数字
		// 请返回哪个数字与X异或的结果最大，返回最大结果
		// 但是，只有<=m的数字，可以被考虑
		public int maxXorWithXBehindM(int x, int m) {
			if (head.min > m) {
				return -1;
			}
			// 一定存在某个数可以和x结合
			Node cur = head;
			int ans = 0;
			for (int move = 30; move >= 0; move--) {
				int path = (x >> move) & 1;
				// 期待遇到的东西
				int best = (path ^ 1);
				//一个条件是不存在想走的那条路 或者 存在但是想走的哪条路径的最小值 大于 要求的m的话 只能走反向
				best ^= (cur.nexts[best] == null || cur.nexts[best].min > m) ? 1 : 0;
				// best变成了实际遇到的
				ans |= (path ^ best) << move;
				//设置答案并且往下移动
				cur = cur.nexts[best];
			}
			return ans;
		}
	}

}
