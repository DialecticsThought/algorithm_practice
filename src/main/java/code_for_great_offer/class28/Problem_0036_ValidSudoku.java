package code_for_great_offer.class28;

/*
* 36. 有效的数独
* 请你判断一个 9 x 9 的数独是否有效。只需要 根据以下规则 ，验证已经填入的数字是否有效即可。
* 数字 1-9 在每一行只能出现一次。
* 数字 1-9 在每一列只能出现一次。
* 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）
* 注意：
* 一个有效的数独（部分已被填充）不一定是可解的。
* 只需要根据以上规则，验证已经填入的数字是否有效即可。
* 空白格用 '.' 表示。
* */
public class Problem_0036_ValidSudoku {

	public static boolean isValidSudoku(char[][] board) {
		/*
		* 每一行 1-9 有没有出现过 怎么描述
		* 定义一个二维arr  第i行某一个数字 是否出现 也就是 arr[i][num]的意义
		* 每一列  1-9 有没有出现过 怎么描述
		* 定义一个二维arr  第j列某一个数字 是否出现 也就是 arr[j][num]的意义
		* 每一个桶  1-9 有没有出现过 怎么描述
		* 定义一个二维arr  第bid桶某一个数字 是否出现 也就是 arr[bid][num]的意义
		* */
		boolean[][] row = new boolean[9][10];
		boolean[][] col = new boolean[9][10];
		boolean[][] bucket = new boolean[9][10];
		//从第0列->第8列遍历
		for (int i = 0; i < 9; i++) {
			//从0列->第8列遍历
			for (int j = 0; j < 9; j++) {
				/*
				* bid 是桶的编号
				* 举例
				* 0号桶  包括 0~2行 0~2列 这9个格子
				* 1号桶  包括 0~2行 3~5列 这9个格子
				* 2号桶  包括 0~2行 6~8列 这9个格子
				* .....
				* 规律 => bid = 3 * (i / 3) + (j / 3)
				* */
				int bid = 3 * (i / 3) + (j / 3);
				if (board[i][j] != '.') {//说明这个位置有数
					//得到 当前的数 board[i][j]的ASCII码 - '0'的ASCII码
					int num = board[i][j] - '0';
					// 只要有一个出现过
					/*
					* eg：来到 [6][7] =4
					* 检查 [6][4]的数字 是否出现过  第6行中是否出现过4
					* 检查 [7][4]的数字 是否出现过  第7列中是否出现过4
					* */
					if (row[i][num] || col[j][num] || bucket[bid][num]) {
						return false;
					}
					//标记为出现过
					row[i][num] = true;
					col[j][num] = true;
					bucket[bid][num] = true;
				}
			}
		}
		return true;
	}

}
