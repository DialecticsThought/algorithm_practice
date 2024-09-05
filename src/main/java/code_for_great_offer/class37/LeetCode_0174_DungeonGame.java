package code_for_great_offer.class37;
/*
*TODO
* 一些恶魔抓住了公主（P）并将她关在了地下城的右下角。地下城是由M x N 个房间组成的二维网格。
* 我们英勇的骑士（K）最初被安置在左上角的房间里，他必须穿过地下城并通过对抗恶魔来拯救公主。
* 骑士的初始健康点数为一个正整数。如果他的健康点数在某一时刻降至 0 或以下，他会立即死亡。
* 有些房间由恶魔守卫，因此骑士在进入这些房间时会失去健康点数（若房间里的值为负整数，则表示骑士将损失健康点数）；其
* 他房间要么是空的（房间里的值为 0），要么包含增加骑士健康点数的魔法球（若房间里的值为正整数，则表示骑士将增加健康点数）。
* 为了尽快到达公主，骑士决定每次只向右或向下移动一步。
* 编写一个函数来计算确保骑士能够拯救到公主所需的最低初始健康点数。
* 例如，考虑到如下布局的地下城，如果骑士遵循最佳路径 右 -> 右 -> 下 -> 下，则骑士的初始健康点数至少为 7。
* -2 (K)  -3	3
* -5	  -10	1
* 10	  30	-5 (P)
* 链接：https://leetcode.cn/problems/dungeon-game
*
* */
public class LeetCode_0174_DungeonGame {
	/*
	*TODO
	* dp[i][j]表示 （i,j）位置到右下角需要的初始血量最低是多少
	* dp表从下往上 从右往左
	* eg: m矩阵
	* [-3 5  2]
	* [6 -7 -9]
	* [3  2 -2]
	* 如果骑士只差一步 就可以来到右下角了
	* 也就是说当前位置在[2][1]或[1][2]
	* 那么所学的最低血量是3 而不是10 因为骑士时刻的血量>0 3-2=1>0
	* dp[最右下角]= m[最右下角]>0 最低血量=1
	* dp[最右下角]= m[最右下角]<0 最低血量=m[最右下角]的绝对值+1
	* =>dp[2][2]=3
	* 现在求dp[2][1] 也就是从[2][1]位置到右下角的最低血量是多少
	* 也就是说 dp[2][1]+m[2][1] >= 3 = dp[2][2]
	* =>dp[2][1]=1
	* 现在求dp[1][2] 也就是从[1][2]位置到右下角的最低血量是多少
	* 也就是说 dp[1][2]+m[1][2] >= 3 = dp[2][2]
	* =>dp[1][2]=12
	* 现在求dp[0][2] 也就是从[0][2]位置到右下角的最低血量是多少
	* 也就是说 dp[0][2]+m[0][2] >= 12 = dp[1][2]
	* =>dp[1][2]=10
	* 现在求dp[1][1] 也就是从[1][1]位置到右下角的最低血量是多少
	* 需要查看dp[1][2]和dp[2][1]的关系 ☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆
	* dp[1][1] 通过dp[2][1]这条方向走 因为dp[1][2]的路径要求血量搞
	* dp[1][1]-m[1][1]-dp[2][1]=0 => dp[1][1]=8
	 * */
	public static int minHP1(int[][] m) {
		if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
			return 1;
		}
		int row = m.length;
		int col = m[0].length;
		int[][] dp = new int[row--][col--];
		dp[row][col] = m[row][col] > 0 ? 1 : -m[row][col] + 1;
		for (int j = col - 1; j >= 0; j--) {
			dp[row][j] = Math.max(dp[row][j + 1] - m[row][j], 1);
		}
		int right = 0;
		int down = 0;
		for (int i = row - 1; i >= 0; i--) {
			dp[i][col] = Math.max(dp[i + 1][col] - m[i][col], 1);
			for (int j = col - 1; j >= 0; j--) {
				/*
				*TODO
				* 走向右边 m[i][j]
				* eg：右边的一个位置要求是7 当前位置遭遇了一个4的血瓶
				* 想要到达右边的位置 自己起码3个血  3和1比大小 取大的
				* eg：右边的一个位置要求是7 当前位置遭遇了一个10的血瓶
				* 那么自己 起码1个血
				* eg：右边的一个位置要求是7 当前位置遭遇了一个10的血瓶
				* right表示我迈入到当前这个位置也通过了右边的位置
				* down表示我迈入到当前这个位置也通过了下面的位置
				* */
				right = Math.max(dp[i][j + 1] - m[i][j], 1);
				down = Math.max(dp[i + 1][j] - m[i][j], 1);
				dp[i][j] = Math.min(right, down);
			}
		}
		return dp[0][0];
	}

	public static int minHP2(int[][] m) {
		if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
			return 1;
		}
		int more = Math.max(m.length, m[0].length);
		int less = Math.min(m.length, m[0].length);
		boolean rowmore = more == m.length;
		int[] dp = new int[less];
		int tmp = m[m.length - 1][m[0].length - 1];
		dp[less - 1] = tmp > 0 ? 1 : -tmp + 1;
		int row = 0;
		int col = 0;
		for (int j = less - 2; j >= 0; j--) {
			row = rowmore ? more - 1 : j;
			col = rowmore ? j : more - 1;
			dp[j] = Math.max(dp[j + 1] - m[row][col], 1);
		}
		int choosen1 = 0;
		int choosen2 = 0;
		for (int i = more - 2; i >= 0; i--) {
			row = rowmore ? i : less - 1;
			col = rowmore ? less - 1 : i;
			dp[less - 1] = Math.max(dp[less - 1] - m[row][col], 1);
			for (int j = less - 2; j >= 0; j--) {
				row = rowmore ? i : j;
				col = rowmore ? j : i;
				choosen1 = Math.max(dp[j] - m[row][col], 1);
				choosen2 = Math.max(dp[j + 1] - m[row][col], 1);
				dp[j] = Math.min(choosen1, choosen2);
			}
		}
		return dp[0];
	}

	public static void main(String[] args) {
		int[][] map = { { -2, -3, 3 }, { -5, -10, 1 }, { 10, 30, -5 }, };
		System.out.println(minHP1(map));
		System.out.println(minHP2(map));

	}

}
