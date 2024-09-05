package code_for_great_offer.class28;

public class LeetCode_0037_SudokuSolver {

	public static void solveSudoku(char[][] board) {
		boolean[][] row = new boolean[9][10];
		boolean[][] col = new boolean[9][10];
		boolean[][] bucket = new boolean[9][10];
		initMaps(board, row, col, bucket);
		process(board, 0, 0, row, col, bucket);
	}
	/*
	 * 每一行 1-9 有没有出现过 怎么描述
	 * 定义一个二维arr  第i行某一个数字 是否出现 也就是 arr[i][num]的意义
	 * 每一列  1-9 有没有出现过 怎么描述
	 * 定义一个二维arr  第j列某一个数字 是否出现 也就是 arr[j][num]的意义
	 * 每一个桶  1-9 有没有出现过 怎么描述
	 * 定义一个二维arr  第bid桶某一个数字 是否出现 也就是 arr[bid][num]的意义
	 *TODO
	 * 生成好 3个arr的初始信息
	 * */
	public static void initMaps(char[][] board, boolean[][] row, boolean[][] col, boolean[][] bucket) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int bid = 3 * (i / 3) + (j / 3);
				if (board[i][j] != '.') {
					int num = board[i][j] - '0';
					row[i][num] = true;
					col[j][num] = true;
					bucket[bid][num] = true;
				}
			}
		}
	}
	/*
	* 当前来到(i,j)这个位置，如果已经有数字，跳到下一个位置上
	* 如果没有数字，尝试1~9，不能和row、col、bucket冲突
	* (i,j)上哪些数字能用 哪些不能用
	* 找到 这个位置是哪一个行 列 桶 就知道 哪些数字能用
	* 每个位置 玩一次dfs遍历
	* */
	public static boolean process(char[][] board, int i, int j, boolean[][] row, boolean[][] col, boolean[][] bucket) {
		//base case 有效行号0-8 当前来到9 就说明结束了
		if (i == 9) {
			return true;
		}
		/*
		*TODO
		* 当离开(i，j)，应该去哪？(nexti, nextj)
		* 先从左往右 到了最后一列 来到 新的一行 从左往右
		* */
		int nexti = j != 8 ? i : i + 1;
		int nextj = j != 8 ? j + 1 : 0;
		if (board[i][j] != '.') {//说明当前位置填过了 来到 下一个位置
			return process(board, nexti, nextj, row, col, bucket);
		} else {
			// 可以尝试1~9
			int bid = 3 * (i / 3) + (j / 3);
			for (int num = 1; num <= 9; num++) { // 尝试每一个数字1~9
				if ((!row[i][num]) && (!col[j][num]) && (!bucket[bid][num])) {
					// 可以尝试num
					//下面的操作就是尝试num
					row[i][num] = true;
					col[j][num] = true;
					bucket[bid][num] = true;
					board[i][j] = (char) (num + '0');
					//继续来到下一个节点做递归 比企鹅 下一个节点要返回当前节点一个true 才可以
					if (process(board, nexti, nextj, row, col, bucket)) {
						return true;
					}
					//dfs的还原现场 因为 当前节点 范围到上一个节点 要还原成上一个节点的状态
					row[i][num] = false;
					col[j][num] = false;
					bucket[bid][num] = false;
					board[i][j] = '.';
				}
			}
			return false;
		}
	}

}
