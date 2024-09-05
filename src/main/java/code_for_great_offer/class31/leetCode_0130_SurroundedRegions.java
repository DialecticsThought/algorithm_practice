package code_for_great_offer.class31;

//https://leetcode.cn/problems/surrounded-regions/
public class leetCode_0130_SurroundedRegions {

//	// m -> 二维数组， 不是0就是1
//	//
//	public static void infect(int[][] m, int i, int j) {
//		if (i < 0 || i == m.length || j < 0 || j == m[0].length || m[i][j] != 1) {
//			return;
//		}
//		// m[i][j] == 1
//		m[i][j] = 2;
//		infect(m, i - 1, j);
//		infect(m, i + 1, j);
//		infect(m, i, j - 1);
//		infect(m, i, j + 1);
//	}

	public static void solve1(char[][] board) {
		boolean[] ans = new boolean[1];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == 'O') {
					ans[0] = true;
					can(board, i, j, ans);
					board[i][j] = ans[0] ? 'T' : 'F';
				}
			}
		}
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				char can = board[i][j];
				if (can == 'T' || can == 'F') {
					board[i][j] = '.';
					change(board, i, j, can);
				}
			}
		}

	}
	/*
	*TODO
	* 传入board
	* 当前横坐标i
	* 当前纵坐标j
	* 答案
	* 函数作用： 如果当前位置是'O'
	* 当前位置出发，找到所有'O'的位置，把这些位置的char变成'.'
	* 那些剩下的不能从当前位置出发找到的'O'的位置 就是要找的被'X'包裹的位置
	* 这个函数 二维数组的上下左右4个边界的每一个元素都要执行
	 * */
	public static void can(char[][] board, int i, int j, boolean[] ans) {
		/*
		*TODO base csae
		* 横坐标<0 或 > board.length
		* 纵坐标<0 或 > board.length
		* */
		if (i < 0 || i == board.length || j < 0 || j == board[0].length) {
			ans[0] = false;
			return;
		}
		//如果当前位置 是'O'字符 改成'.' 保证不会走回头路
		if (board[i][j] == 'O') {
			board[i][j] = '.';
			//走下
			can(board, i - 1, j, ans);
			//走上
			can(board, i + 1, j, ans);
			//走左
			can(board, i, j - 1, ans);
			//走右
			can(board, i, j + 1, ans);
		}
	}

	public static void change(char[][] board, int i, int j, char can) {
		if (i < 0 || i == board.length || j < 0 || j == board[0].length) {
			return;
		}
		if (board[i][j] == '.') {
			board[i][j] = can == 'T' ? 'X' : 'O';
			change(board, i - 1, j, can);
			change(board, i + 1, j, can);
			change(board, i, j - 1, can);
			change(board, i, j + 1, can);
		}
	}

	// 从边界开始感染的方法，比第一种方法更好
	public static void solve2(char[][] board) {
		if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
			return;
		}
		int N = board.length;
		int M = board[0].length;
		for (int j = 0; j < M; j++) {
			if (board[0][j] == 'O') {
				free(board, 0, j);
			}
			if (board[N - 1][j] == 'O') {
				free(board, N - 1, j);
			}
		}
		for (int i = 1; i < N - 1; i++) {
			if (board[i][0] == 'O') {
				free(board, i, 0);
			}
			if (board[i][M - 1] == 'O') {
				free(board, i, M - 1);
			}
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (board[i][j] == 'O') {
					board[i][j] = 'X';
				}
				if (board[i][j] == 'F') {
					board[i][j] = 'O';
				}
			}
		}
	}

	public static void free(char[][] board, int i, int j) {
		if (i < 0 || i == board.length || j < 0 || j == board[0].length || board[i][j] != 'O') {
			return;
		}
		board[i][j] = 'F';
		free(board, i + 1, j);
		free(board, i - 1, j);
		free(board, i, j + 1);
		free(board, i, j - 1);
	}

}
