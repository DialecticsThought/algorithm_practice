package code_for_great_offer.class34;
/*
* 请在 n × n 的棋盘上，实现一个判定井字棋（Tic-Tac-Toe）胜负的神器，判断每一次玩家落子后，是否有胜出的玩家。
* 在这个井字棋游戏中，会有 2 名玩家，他们将轮流在棋盘上放置自己的棋子。
* 在实现这个判定器的过程中，你可以假设以下这些规则一定成立
* 1. 每一步棋都是在棋盘内的，并且只能被放置在一个空的格子里；
* 2. 一旦游戏中有一名玩家胜出的话，游戏将不能再继续；
* 3. 一个玩家如果在同一行、同一列或者同一斜对角线上都放置了自己的棋子，那么他便获得胜利。
给定棋盘边长 n = 3, 玩家 1 的棋子符号是 “X”，玩家 2 的棋子符号是 “O”。
TicTacToe toe = new TicTacToe(3);
toe.move(0, 0, 1); -> 函数返回 0 (此时，暂时没有玩家赢得这场对决)
|X| | |
| | | | // 玩家 1 在 (0, 0) 落子。
| | | |
toe.move(0, 2, 2); -> 函数返回 0 (暂时没有玩家赢得本场比赛)
|X| |O|
| | | | // 玩家 2 在 (0, 2) 落子。
| | | |
toe.move(2, 2, 1); -> 函数返回 0 (暂时没有玩家赢得比赛)
|X| |O|
| | | | // 玩家 1 在 (2, 2) 落子。
| | |X|
toe.move(1, 1, 2); -> 函数返回 0 (暂没有玩家赢得比赛)
|X| |O|
| |O| | // 玩家 2 在 (1, 1) 落子。
| | |X|
toe.move(2, 0, 1); -> 函数返回 0 (暂无玩家赢得比赛)
|X| |O|
| |O| | // 玩家 1 在 (2, 0) 落子。
|X| |X|
toe.move(1, 0, 2); -> 函数返回 0 (没有玩家赢得比赛)
|X| |O|
|O|O| | // 玩家 2 在 (1, 0) 落子.
|X| |X|
toe.move(2, 1, 1); -> 函数返回 1 (此时，玩家 1 赢得了该场比赛)
|X| |O|
|O|O| | // 玩家 1 在 (2, 1) 落子。
|X|X|X|
* */
public class LeetCode_0348_DesignTicTacToe {

	class TicTacToe {
		private int[][] rows;
		private int[][] cols;
		private int[] leftUp;
		private int[] rightUp;
		private boolean[][] matrix;
		private int N;

		public TicTacToe(int n) {
			// rows[a][1] : 1这个人，在a行上，下了几个
			// rows[b][2] : 2这个人，在b行上，下了几个
			rows = new int[n][3]; //0 1 2
			cols = new int[n][3];
			// leftUp[2] = 7 : 2这个人，在左对角线上，下了7个
			leftUp = new int[3];
			// rightUp[1] = 9 : 1这个人，在右对角线上，下了9个
			rightUp = new int[3];
			matrix = new boolean[n][n];
			N = n;
		}
		/*
		*TODO
		* player 只可能是1 或者 2
		* 该方法表示 哪一个player 在 [row,col]位置花了一个X 或者 O
		* 返回1的话 说明 该player 下了这一步棋 player1赢了
		* 返回2的话 说明 该player 下了这一步棋 player2赢了
		* 返回0的话 没有人赢
		* 收集 信息
		* 1.第0行 1号人 下了几个
		* 第1行 1号人 下了几个
		* 第2行 1号人 下了几个
		* ...
		* 第n-1行 1号人 下了几个
		* 2.第0列 1号人 下了几个
		* 第1列 1号人 下了几个
		* 第2列 1号人 下了几个
		* ...
		* 第n-1列 1号人 下了几个
		* 3.左斜对角线 1号人 下了几个
		* 4.右斜对角线 1号人 下了几个
		* TODO
		*  同理 2号人 也收集信息
		* */
		public int move(int row, int col, int player) {
			if (matrix[row][col]) {//说明这个位置下过了
				return 0;
			}
			matrix[row][col] = true;
			rows[row][player]++;
			cols[col][player]++;
			if (row == col) {//说明这个棋子在对角线上
				leftUp[player]++;
			}
			if (row + col == N - 1) {
				rightUp[player]++;
			}
			if (rows[row][player] == N || cols[col][player] == N || leftUp[player] == N || rightUp[player] == N) {
				return player;
			}
			return 0;
		}

	}

}
