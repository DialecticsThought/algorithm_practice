package other.ad2.class08;

public class Code02_PavingTile {

	/*
	 * 2*M铺地的问题非常简单，这个是解决N*M铺地的问题
	 */

	public static int ways1(int N, int M) {
		if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
			return 0;
		}
		if (N == 1 || M == 1) {
			return 1;
		}
		int[] pre = new int[M];
		for (int i = 0; i < pre.length; i++) {
			pre[i] = 1;
		}
		return process(pre, 0, N);
	}

	public static int process(int[] pre, int level, int N) {
		if (level == N) {
			for (int i = 0; i < pre.length; i++) {
				if (pre[i] == 0) {
					return 0;
				}
			}
			return 1;
		}
		int[] op = getOp(pre);
		return dfs(op, 0, level, N);
	}

	public static int dfs(int[] op, int col, int level, int N) {
		if (col == op.length) {
			return process(op, level + 1, N);
		}
		int ans = 0;
		ans += dfs(op, col + 1, level, N);
		if (col + 1 < op.length && op[col] == 0 && op[col + 1] == 0) {
			op[col] = 1;
			op[col + 1] = 1;
			ans += dfs(op, col + 2, level, N);
			op[col] = 0;
			op[col + 1] = 0;
		}
		return ans;
	}

	public static int[] getOp(int[] pre) {
		int[] cur = new int[pre.length];
		for (int i = 0; i < pre.length; i++) {
			cur[i] = pre[i] ^ 1;
		}
		return cur;
	}

	public static int ways2(int N, int M) {
		if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
			return 0;
		}
		if (N == 1 || M == 1) {
			return 1;
		}
		int big = N > M ? N : M;
		int small = big == N ? M : N;
		int sn = 1 << small;
		int limit = sn - 1;
		int[] pre = new int[sn];
		pre[limit] = 1;
		int[] cur = new int[sn];
		for (int level = 0; level < big; level++) {
			for (int status = 0; status < sn; status++) {
				if (pre[status] != 0) {
					int op = (~status) & limit;
					dfs(pre[status], op, 0, small - 1, cur);
				}
			}
			for (int i = 0; i < sn; i++) {
				pre[i] = 0;
			}
			int[] tmp = pre;
			pre = cur;
			cur = tmp;
		}
		return pre[limit];
	}

	public static void dfs(int way, int op, int index, int end, int[] cur) {
		if (index == end) {
			cur[op] += way;
		} else {
			dfs(way, op, index + 1, end, cur);
			if (((3 << index) & op) == 0) {
				dfs(way, op | (3 << index), index + 1, end, cur);
			}
		}
	}

	public static void main(String[] args) {
		int N = 8;
		int M = 8;
		System.out.println(ways1(N, M));
		System.out.println(ways2(N, M));
	}

}
