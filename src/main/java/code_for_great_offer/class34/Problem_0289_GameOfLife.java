package code_for_great_offer.class34;

// 有关这个游戏更有意思、更完整的内容：
// https://www.bilibili.com/video/BV1rJ411n7ri
// 也推荐这个up主
/**
* 根据百度百科，生命游戏，简称为 生命 ，是英国数学家约翰·何顿·康威在 1970 年发明的细胞自动机。
* 给定一个包含 m × n个格子的面板，每一个格子都可以看成是一个细胞。
* 每个细胞都具有一个初始状态： 1 即为 活细胞 （live），或 0 即为 死细胞 （dead）。每个细胞与其八个相邻位置（水平，垂直，对角线）的细胞都遵循以下四条生存定律：
* 如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡；
* 如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活；
* 如果活细胞周围八个位置有超过三个活细胞，则该位置活细胞死亡；
* 如果死细胞周围正好有三个活细胞，则该位置死细胞复活；
* 下一个状态是通过将上述规则同时应用于当前状态下的每个细胞所形成的，其中细胞的出生和死亡是同时发生的。
* 给你 m x n 网格面板 board 的当前状态，返回下一个状态
来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/game-of-life
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
*
* */
public class Problem_0289_GameOfLife {
	/**
	* eg:
	* 0  0  1
	* 0  1  1
	* 0  0  0
	* 对于[0][0]，这个位置的下一轮是0
	* 对于[0][1]，这个位置的下一轮是1
	* 但是当前这一轮的值是0 把0看成32个0
	* 再把32个0变成 0000....10 也就是最低的第二位是1  其他位都是0
	* 所以 矩阵变成了
	* 0  2  1
	* 0  1  1
	* 0  0  0
	* 对于[0][2] 怎么判断它的邻居[0][1]这一轮代表的是0还是1
	* 就看他的最低位是0还是1， [0][1]的最低位是0  只不过最低的第二位是1
	* */
	public static void gameOfLife(int[][] board) {
		int N = board.length;
		int M = board[0].length;
		//TODO 遍历每一个位置
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				//得到 当前位置的周围有多少个1
				int neighbors = neighbors(board, i, j);
				if (neighbors == 3 || (board[i][j] == 1 && neighbors == 2)) {
					//TODO 第2位变成1 就是 |=2
					board[i][j] |= 2;
				}
			}
		}
		//TODO 下一轮
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				//TODO 第2位的1变成0
				board[i][j] >>= 1;
			}
		}
	}

	// b[i][j] 这个位置的数，周围有几个1
	public static int neighbors(int[][] b, int i, int j) {
		return f(b, i - 1, j - 1)
				+ f(b, i - 1, j)
				+ f(b, i - 1, j + 1)
				+ f(b, i, j - 1)
				+ f(b, i, j + 1)
				+ f(b, i + 1, j - 1)
				+ f(b, i + 1, j)
				+ f(b, i + 1, j + 1);
	}

	// b[i][j] 上面有1，就返回1，上面不是1，就返回0
	public static int f(int[][] b, int i, int j) {
		return (i >= 0 && i < b.length && j >= 0 && j < b[0].length && (b[i][j] & 1) == 1) ? 1 : 0;
	}

}
