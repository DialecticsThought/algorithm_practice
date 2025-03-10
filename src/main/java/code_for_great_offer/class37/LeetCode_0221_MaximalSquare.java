package code_for_great_offer.class37;

public class LeetCode_0221_MaximalSquare {
	/*
	*TODO
	* 用dp(i,j) 表示以(i,j) 为右下角，且只包含1 的正方形的边长最大值。如果我们能计算出所有
	* dp(i,j) 的值，那么其中的最大值即为矩阵中只包含1 的正方形的边长最大值，其平方即为最大正方形的面积。
	* 那么如何计算dp 中的每个元素值呢？对于每个位置(i,j)，检查在矩阵中该位置的值：
	* 如果该位置的值是0，则dp(i,j)=0，因为当前位置不可能在由1 组成的正方形中；
	* 如果该位置的值是1，则dp(i,j) 的值由其上方、左方和左上方的三个相邻位置的
	* dp 值决定。具体而言，当前位置的元素值等于三个相邻位置的元素中的最小值加1，状态转移方程如下：
	* dp(i,j)=min(dp(i−1,j),dp(i−1,j−1),dp(i,j−1))+1
	* 如果读者对这个状态转移方程感到不解，可以参考 1277.
	* 统计全为 1 的正方形子矩阵的官方题解，其中给出了详细的证明。
	* 此外，还需要考虑边界条件。如果i 和j 中至少有一个为0，则以位置
	* (i,j) 为右下角的最大正方形的边长只能是1，因此dp(i,j)=1。
	* 链接：https://leetcode.cn/problems/maximal-square/solution/zui-da-zheng-fang-xing-by-leetcode-solution/
	*TODO
	* 人话：
	* 以(i,j) 为正方形的右下角，如果能形成一个 x * x 的正方形区域
	* 1.那么 (i-1,j-1) 为正方形的右下角 就能能形成一个 (x-1) * (x-1) 的正方形区域
	* 	=> 那么 (i-2,j-2) 为正方形的右下角 就能能形成一个 (x-2) * (x-2) 的正方形区域
	* 		....
	* 2.那么 (i,j-1) 为正方形的右下角 就能形成一个 (x-1) * (x-1) 的正方形区域
	* 3.那么 (i-1,j) 为正方形的右下角 就能形成一个 (x-1) * (x-1) 的正方形区域
	 * */
	public static int maximalSquare(char[][] m) {
		if (m == null || m.length == 0 || m[0].length == 0) {
			return 0;
		}
		int N = m.length;
		int M = m[0].length;
		int[][] dp = new int[N + 1][M + 1];
		int max = 0;
		for (int i = 0; i < N; i++) {
			if (m[i][0] == '1') {
				dp[i][0] = 1;
				max = 1;
			}
		}
		for (int j = 1; j < M; j++) {
			if (m[0][j] == '1') {
				dp[0][j] = 1;
				max = 1;
			}
		}
		for (int i = 1; i < N; i++) {
			for (int j = 1; j < M; j++) {
				if (m[i][j] == '1') {
					//TODO ☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆
					dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
					max = Math.max(max, dp[i][j]);
				}
			}
		}
		return max * max;
	}
	/*
	*TODO
	* 暴力法是最简单直观的做法，具体做法如下：
	* 遍历矩阵中的每个元素，每次遇到 1，则将该元素作为正方形的左上角；
	* 确定正方形的左上角后，根据左上角所在的行和列计算可能的最大正方形的边长（正方形的范围不能超出矩阵的行数和列数），
	* 在该边长范围内寻找只包含 1 的最大正方形；
	* 每次在下方新增一行以及在右方新增一列，判断新增的行和列是否满足所有元素都是 1
	* */
	public int maximalSquare2(char[][] matrix) {
		int maxSide = 0;
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return maxSide;
		}
		int rows = matrix.length, columns = matrix[0].length;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (matrix[i][j] == '1') {
					// 遇到一个 1 作为正方形的左上角
					maxSide = Math.max(maxSide, 1);
					// 计算可能的最大正方形边长
					int currentMaxSide = Math.min(rows - i, columns - j);
					for (int k = 1; k < currentMaxSide; k++) {
						// 判断新增的一行一列是否均为 1
						boolean flag = true;
						if (matrix[i + k][j + k] == '0') {
							break;
						}
						for (int m = 0; m < k; m++) {
							if (matrix[i + k][j + m] == '0' || matrix[i + m][j + k] == '0') {
								flag = false;
								break;
							}
						}
						if (flag) {
							maxSide = Math.max(maxSide, k + 1);
						} else {
							break;
						}
					}
				}
			}
		}
		int maxSquare = maxSide * maxSide;
		return maxSquare;
	}
}
