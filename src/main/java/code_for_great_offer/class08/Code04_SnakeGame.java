package code_for_great_offer.class08;

import java.util.Arrays;

public class Code04_SnakeGame {
	/*
	* 因为当前能不能使用能力要被之前的状态影响，所以我们从最后面往前面看
	* 从当前位置，我们要从前面状态中得到什么东西呢？
	* 之前有没有使用过能力
	* 之前能获得的最大增长值是多少
	* 也就是说，需要前面提供：
	* 之前一次能力也没有使用过的情况下能够获得的最大增长值是多少
	* 之前使用过能力的情况下能够获得的最大增长值是多少
	* 然后对于当前位置，我们根据以前有没有使用过能力和当前位于什么位置来决定当前的状态
	*https://blog.csdn.net/zhizhengguan/article/details/125717247?spm=1001.2101.3001.6650.5&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7ERate-5-125717247-blog-117266669.pc_relevant_recovery_v2&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7ERate-5-125717247-blog-117266669.pc_relevant_recovery_v2&utm_relevant_index=5
	*
	*  */
	public static int walk1(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return 0;
		}
		int res = Integer.MIN_VALUE;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				int[] ans = process(matrix, i, j);
				res = Math.max(res, Math.max(ans[0], ans[1]));
			}
		}
		return res;
	}

	public static int zuo(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return 0;
		}
		int ans = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				Info cur = f(matrix, i, j);
				ans = Math.max(ans, Math.max(cur.no, cur.yes));
			}
		}
		return ans;
	}

	public static class Info {
		public int no;
		public int yes;

		public Info(int n, int y) {
			no = n;
			yes = y;
		}
	}
	/*
	* 定义f(i，j)：
		蛇从一个最优的最左列的格子开始走到i ， j i，ji，j位置停。返回两个值：
		中途一次能力也不用能够获得的最大增长值是多少？
		使用一次能够获得的最大增长值是多少？
		每一个格子都求一个f ( i ， j ) f(i，j)f(i，j)，蛇有可能在任何格子停
		所有的( i ， j ) (i，j)(i，j)中求max就是答案
	*
	*
	* */
	// 蛇从某一个最左列，且最优的空降点降落
	// 沿途走到(i,j)必须停！
	// 返回，一次能力也不用，获得的最大成长值
	// 返回，用了一次能力，获得的最大成长值
	// 如果蛇从某一个最左列，且最优的空降点降落，不用能力，怎么都到不了(i,j)，那么no = -1
	// 如果蛇从某一个最左列，且最优的空降点降落，用了一次能力，怎么都到不了(i,j)，那么yes = -1
	//TODO 蛇的初始值是0  空降到最左列的哪一个点 后 加上该点的值
	public static Info f(int[][] matrix, int i, int j) {
		if (j == 0) { //base case 来到最左列
			//没有之前，所以之前一定不会使用能力
			int no = Math.max(matrix[i][0], -1);//表示 来到最左列 不用能力 获得的增长
			int yes = Math.max(-matrix[i][0], -1);//表示 来到最左列 用能力（负号） 获得的增长
			return new Info(no, yes);//返回的是本次的状态
		}
		//TODO  j > 0 不在最左列
		//TODO  蛇每一步可以选择右上、右、右下三个方向的任何一个前进


		int preNo = -1;//来到 (i,j) 位置 之前所有的路中， 一次能力 也不用 获得的最好成长值  默认-1
		int preYes = -1;//来到 (i,j) 位置 之前所有的路中， 用了一次能力  获得的最好成长值  默认-1
		//TODO 表示 当前位置从上一个位置向右走的一个位置
		Info pre = f(matrix, i, j - 1);
		//TODO 决策
		preNo = Math.max(pre.no, preNo);//TODO 得到一个信息 其中 第1个是一次能力也不用
		preYes = Math.max(pre.yes, preYes);//TODO 得到一个信息 其中 第2个是用过一次能力
		//TODO 意味着 当前节点 一定有左上  i因为是行号
		if (i > 0) {
			pre = f(matrix, i - 1, j - 1);
			//TODO 决策
			preNo = Math.max(pre.no, preNo);
			preYes = Math.max(pre.yes, preYes);
		}
		//TODO 意味着 当前节点 一定有左下  i因为是行号
		if (i < matrix.length - 1) {
			pre = f(matrix, i + 1, j - 1);
			//TODO 决策
			preNo = Math.max(pre.no, preNo);
			preYes = Math.max(pre.yes, preYes);
		}
		//TODO 上面的事情做完 就知道 之前 用了一次能力 和不用能力

		//TODO 之前没有用过能力 当前也不用能力
		//TODO preNo == -1 表示之前的是无效解 所以 也是-1  如果不是 当前节点计算 preNo + matrix[i][j]  然后和-1比大小 看是否有效
		int no = preNo == -1 ? -1 : (Math.max(-1, preNo + matrix[i][j]));
		//TODO 能力只有一次，是之前用的！
		//TODO  [3,-100,4,-6] 来到-6的时候 之前肯定是用了能力了
		//TODO 如果 之前用了一次能力 还是-1  那当前p1=-1 eg: [3,-100,-200,60] 到了-100 用了一次能力 到了-200 就不能在用了
		int p1 = preYes == -1 ? -1 : (Math.max(-1, preYes + matrix[i][j]));
		//TODO 能力只有一次，就当前用！  也就是取反 变成 -matrix[i][j]
		int p2 = preNo == -1 ? -1 : (Math.max(-1, preNo - matrix[i][j]));
		int yes = Math.max(Math.max(p1, p2), -1);
		return new Info(no, yes);
	}

	// 从假想的最优左侧到达(i,j)的旅程中
	// 0) 在没有使用过能力的情况下，返回路径最大和，没有可能到达的话，返回负
	// 1) 在使用过能力的情况下，返回路径最大和，没有可能到达的话，返回负
	public static int[] process(int[][] m, int i, int j) {
		if (j == 0) { // (i,j)就是最左侧的位置
			return new int[] { m[i][j], -m[i][j] };
		}
		int[] preAns = process(m, i, j - 1);
		// 所有的路中，完全不使用能力的情况下，能够到达的最好长度是多大
		int preUnuse = preAns[0];
		// 所有的路中，使用过一次能力的情况下，能够到达的最好长度是多大
		int preUse = preAns[1];
		if (i - 1 >= 0) {
			preAns = process(m, i - 1, j - 1);
			preUnuse = Math.max(preUnuse, preAns[0]);
			preUse = Math.max(preUse, preAns[1]);
		}
		if (i + 1 < m.length) {
			preAns = process(m, i + 1, j - 1);
			preUnuse = Math.max(preUnuse, preAns[0]);
			preUse = Math.max(preUse, preAns[1]);
		}
		// preUnuse 之前旅程，没用过能力
		// preUse 之前旅程，已经使用过能力了
		int no = -1; // 之前没使用过能力，当前位置也不使用能力，的最优解
		int yes = -1; // 不管是之前使用能力，还是当前使用了能力，请保证能力只使用一次，最优解
		if (preUnuse >= 0) {
			no = m[i][j] + preUnuse;//TODO 之前不用 现在也不用
			yes = -m[i][j] + preUnuse;//TODO 之前不用 现在用
		}
		if (preUse >= 0) {//TODO 之前用过 现在不用
			yes = Math.max(yes, m[i][j] + preUse);
		}
		return new int[] { no, yes };
	}

	public static int walk2(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		int[][][] dp = new int[matrix.length][matrix[0].length][2];
		for (int i = 0; i < dp.length; i++) {
			dp[i][0][0] = matrix[i][0];
			dp[i][0][1] = -matrix[i][0];
			max = Math.max(max, Math.max(dp[i][0][0], dp[i][0][1]));
		}
		for (int j = 1; j < matrix[0].length; j++) {
			for (int i = 0; i < matrix.length; i++) {
				int preUnuse = dp[i][j - 1][0];
				int preUse = dp[i][j - 1][1];
				if (i - 1 >= 0) {
					preUnuse = Math.max(preUnuse, dp[i - 1][j - 1][0]);
					preUse = Math.max(preUse, dp[i - 1][j - 1][1]);
				}
				if (i + 1 < matrix.length) {
					preUnuse = Math.max(preUnuse, dp[i + 1][j - 1][0]);
					preUse = Math.max(preUse, dp[i + 1][j - 1][1]);
				}
				dp[i][j][0] = -1;
				dp[i][j][1] = -1;
				if (preUnuse >= 0) {
					dp[i][j][0] = matrix[i][j] + preUnuse;
					dp[i][j][1] = -matrix[i][j] + preUnuse;
				}
				if (preUse >= 0) {
					dp[i][j][1] = Math.max(dp[i][j][1], matrix[i][j] + preUse);
				}
				max = Math.max(max, Math.max(dp[i][j][0], dp[i][j][1]));
			}
		}
		return max;
	}

	public static int[][] generateRandomArray(int row, int col, int value) {
		int[][] arr = new int[row][col];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				arr[i][j] = (int) (Math.random() * value) * (Math.random() > 0.5 ? -1 : 1);
			}
		}
		return arr;
	}

	public static void main(String[] args) {
		int N = 7;
		int M = 7;
		int V = 10;
		int times = 1000000;
		for (int i = 0; i < times; i++) {
			int r = (int) (Math.random() * (N + 1));
			int c = (int) (Math.random() * (M + 1));
			int[][] matrix = generateRandomArray(r, c, V);
			int ans1 = zuo(matrix);
			int ans2 = walk2(matrix);
			if (ans1 != ans2) {
				for (int j = 0; j < matrix.length; j++) {
					System.out.println(Arrays.toString(matrix[j]));
				}
				System.out.println("Oops   ans1: " + ans1 + "   ans2:" + ans2);
				break;
			}
		}
		System.out.println("finish");
	}

}
