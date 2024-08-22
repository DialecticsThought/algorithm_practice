package algorithmbasic2020_master.class43;

public class Code03_PavingTile {

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
		int[] pre = new int[M]; // pre代表-1行的状况
		for (int i = 0; i < pre.length; i++) {
			pre[i] = 1;
		}
		return process(pre, 0, N);
	}

	/*
	  TODO
	   pre 表示level-1行的状态
	   level表示，正在level行做决定
	   N 表示一共有多少行 固定的
	   默认 level-2行及其之上所有行，都摆满砖了
	   level做决定，让所有区域都满，方法数返回
	*/
	public static int process(int[] pre, int level, int N) {
		if (level == N) { // base case
			for (int i = 0; i < pre.length; i++) {
				if (pre[i] == 0) {
					return 0;
				}
			}
			return 1;
		}

		//TODO 没到终止行，可以选择在当前的level行摆瓷砖
		int[] op = getOp(pre);
		return dfs(op, 0, level, N);//TODO op是上一行的状态 开始从level行的第0列开始做决定
	}

	/*
	*TODO
	* op[i] == 0 可以考虑摆砖
	* op[i] == 1 只能竖着向上
	* col表示第几列
	*  假设 pre = 0 0 0 1 1 1 0 0
	*   这一层就是 1 1 1 0 0 0 1 1
	*  那么 3 4 5列可以自由做决定
	* */
	public static int dfs(int[] op, int col, int level, int N) {
		//TODO 在列上自由发挥，玩深度优先遍历，当col来到终止列，i行的决定做完了
		// 轮到i+1行，做决定
		if (col == op.length) {//TODO 当某一行的所有列都做完决定之后 开始下从一行的第0列开始做决定
			return process(op, level + 1, N);
		}
		//TODO 还没有到这一行的最后一列 那么在当前列做选择
		int ans = 0;
		//TODO 选择1 col位置不横摆
		ans += dfs(op, col + 1, level, N); // col位置上不摆横转
		//TODO 选择2 col位置横摆, 向右
		//TODO 这里判断是不是最后一列 最后一列 不能再向右放砖 否则溢出
		//TODO 如果某一列的后侧一列有砖 这列不能放砖 否则溢出
		if (col + 1 < op.length && op[col] == 0 && op[col + 1] == 0) {
			op[col] = 1;
			op[col + 1] = 1;
			//TODO 在某一列右侧一列的右侧一列深度优先遍历
			ans += dfs(op, col + 2, level, N);
			//TODO 做完决策之后 从某个节点返回到上一个节点 需要恢复原先的状态 才能进入其他分支节点
			op[col] = 0;
			op[col + 1] = 0;
		}
		return ans;
	}
	/*
	* TODO 根据上一层的状态 来决定这一层的操作
	*  假设 pre = 0 0 0 1 1 1 0 0
	*  这一层就是  1 1 1 0 0 0 1 1
	*  这一层的0自由发挥 选择是否向右铺砖  1指的是向上铺瓷砖
	* */
	public static int[] getOp(int[] pre) {
		int[] cur = new int[pre.length];
		for (int i = 0; i < pre.length; i++) {
			cur[i] = pre[i] ^ 1;
		}
		return cur;
	}

	// Min (N,M) 不超过 32
	public static int ways2(int N, int M) {
		if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
			return 0;
		}
		if (N == 1 || M == 1) {
			return 1;
		}
		/*
		* TODO
		*  假设 有 7位 那么 limit 就是 8位 最高位0 其余都是1
		*  limit的作用就是对齐
		* */
		int max = Math.max(N, M);
		int min = Math.min(N, M);
		//一开始 limit = pre
		int pre = (1 << min) - 1;
		return process2(pre, 0, max, min);
	}

	/*
	*TODO
	* 上一行的状态，是pre，limit是用来对齐的，固定参数不用管
	* 当前来到i行，一共N行，返回填满的方法数
	* M是列
	* */
	public static int process2(int pre, int i, int N, int M) {
		if (i == N) { //TODO base case 如果来到第n行 （逻辑上的最后一行）
			//TODO 判断 上一行n-1行 是否全满 全满的话 再加上 默认 n-2行及其以上都是满的 那么就是铺满了
			//TODO  (1 << M) - 1) 就是limit
			return pre == ((1 << M) - 1) ? 1 : 0;
		}
		//TODO (1 << M) - 1) 就是limit
		int op = ((~pre) & ((1 << M) - 1));
		/*
		*TODO
		* eg: M是8 那么 就对应0~7列 因为从0开始 所以对第M-1列做决定
		* 当前第i层
		* 总的行数N
		* 总的列数 M
		*/
		return dfs2(op, M - 1, i, N, M);
	}

	public static int dfs2(int op, int col, int level, int N, int M) {
		if (col == -1) {//TODO base case 直到-1列 说明这一行不能做决定了
			//TODO 开始做下一行的决定
			return process2(op, level + 1, N, M);
		}
		//TODO 开始做 这一行 第col列的决定了

		//TODO 这里第col列 不填砖
		int ans = 0;
		//TODO  因为 列 是 从高到低  eg： 4列  就是 4 3 2 1 0 ===> 做col-1 列 做决定
		ans += dfs2(op, col - 1, level, N, M);
		/*
		*TODO
		* op & (1 << col)) == 0 说明 当前列 是 0状态
		* col - 1 >= 0说明 右侧还有列
		* (op & (1 << (col - 1))) == 0) 说明 右侧的列 没有砖
		 * */
		if ((op & (1 << col)) == 0 && col - 1 >= 0 && (op & (1 << (col - 1))) == 0) {
			/*
			* TODO
			*  当前列填砖 也就是 col列 和 col-1列都从0变成1 ===> (op | (3 << (col - 1)))
			*  当前列填砖 完成后 对 col-2列做决定
			* */
			ans += dfs2((op | (3 << (col - 1))), col - 2, level, N, M);
			//TODO 不需要恢复现场 因为 传入的不是数组 而是 整数 每次传入参数都是新的
		}
		return ans;
	}

	// 记忆化搜索的解
	// Min(N,M) 不超过 32
	public static int ways3(int N, int M) {
		if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
			return 0;
		}
		if (N == 1 || M == 1) {
			return 1;
		}
		int max = Math.max(N, M);
		int min = Math.min(N, M);
		int pre = (1 << min) - 1;
		int[][] dp = new int[1 << min][max + 1];
		for (int i = 0; i < dp.length; i++) {
			for (int j = 0; j < dp[0].length; j++) {
				dp[i][j] = -1;
			}
		}
		return process3(pre, 0, max, min, dp);
	}

	public static int process3(int pre, int i, int N, int M, int[][] dp) {
		if (dp[pre][i] != -1) {
			return dp[pre][i];
		}
		int ans = 0;
		if (i == N) {
			ans = pre == ((1 << M) - 1) ? 1 : 0;
		} else {
			int op = ((~pre) & ((1 << M) - 1));
			ans = dfs3(op, M - 1, i, N, M, dp);
		}
		dp[pre][i] = ans;
		return ans;
	}

	public static int dfs3(int op, int col, int level, int N, int M, int[][] dp) {
		if (col == -1) {
			return process3(op, level + 1, N, M, dp);
		}
		int ans = 0;
		ans += dfs3(op, col - 1, level, N, M, dp);
		if (col > 0 && (op & (3 << (col - 1))) == 0) {
			ans += dfs3((op | (3 << (col - 1))), col - 2, level, N, M, dp);
		}
		return ans;
	}

	// 严格位置依赖的动态规划解
	public static int ways4(int N, int M) {
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
		int[] dp = new int[sn];
		dp[limit] = 1;
		int[] cur = new int[sn];
		for (int level = 0; level < big; level++) {
			for (int status = 0; status < sn; status++) {
				if (dp[status] != 0) {
					int op = (~status) & limit;
					dfs4(dp[status], op, 0, small - 1, cur);
				}
			}
			for (int i = 0; i < sn; i++) {
				dp[i] = 0;
			}
			int[] tmp = dp;
			dp = cur;
			cur = tmp;
		}
		return dp[limit];
	}

	public static void dfs4(int way, int op, int index, int end, int[] cur) {
		if (index == end) {
			cur[op] += way;
		} else {
			dfs4(way, op, index + 1, end, cur);
			if (((3 << index) & op) == 0) { // 11 << index 可以放砖
				dfs4(way, op | (3 << index), index + 1, end, cur);
			}
		}
	}

	public static void main(String[] args) {
		int N = 8;
		int M = 6;
		System.out.println(ways1(N, M));
		System.out.println(ways2(N, M));
		System.out.println(ways3(N, M));
		System.out.println(ways4(N, M));

		N = 10;
		M = 10;
		System.out.println("=========");
		System.out.println(ways3(N, M));
		System.out.println(ways4(N, M));
	}

}
