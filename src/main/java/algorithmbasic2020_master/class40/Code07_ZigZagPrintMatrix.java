package algorithmbasic2020_master.class40;

public class Code07_ZigZagPrintMatrix {

	public static void printMatrixZigZag(int[][] matrix) {
		/*
		* 初始状态
		* a在0行0列 b在0行0列
		* 终止位置是 整个矩阵的行 -1 整个矩阵的列 -1
		* a点是先向右移动 直到 矩阵的行ed左后 再 向下移动
		* b点是先向下移动直到 矩阵的行ed左后 再 向右移动
		* a和b的行走步数是相同 并且都会走到终止位置
		 * */
		int aRow = 0;
		int aColumn = 0;
		int bRow = 0;
		int bColumn = 0;
		int endR = matrix.length - 1;
		int endC = matrix[0].length - 1;
		//是否从右上 往 左下 打印 一开始是false 说明 是 左下 向 右上 打印
		boolean fromUp = false;
		/*
		* a的行号 不等于 终止位置的行号 + 1 （+1 表示再下一行）是因为
		* a点是先向右移动 直到 矩阵的行ed左后 再 向下移动  一定是 最后走到 终止位置
		* 所以 不能走早终止位置的再下一行
		* */
		while (aRow != endR + 1) {
			printLevel(matrix, aRow, aColumn, bRow, bColumn, fromUp);
			/*if(aColumn == endC ){
				aRow = aRow + 1;
			}*/
			/*
			* 如果 a一直向右移动 判断是否移动到了最右端 也就是 是否移动到 终止位置的那一列
			* 如果没有 继续向右 如果有 那么 就不再向右移动了 向下移动
			* 如果 a一直向下移动 判断是否移动到了最下端 也就是 是否移动到 终止位置的那一行
			* 如果没有 a继续向下 如果有 那么 就不再向下移动了
			* */
			aRow = aColumn == endC ? aRow + 1 : aRow;//因为 a先向右移动 再向下移动  所以先规定行的变化 再规定列的变化
			/*if (aColumn != endC){
				aColumn = aColumn + 1;
			}*/
			aColumn = aColumn == endC ? aColumn : aColumn + 1;//因为 a先向右移动 再向下移动  所以先规定行的变化 再规定列的变化
			/*
			* 因为 b先向下移动 再 向右移动  所以先规定列的变化 在规定行的变化
			* 如果 b一直向下移动 判断是否移动到了最下端 也就是 是否移动到 终止位置的那一行
			* 如果没有 b继续向下 如果有 那么 就不再向下移动了
			* */
			bColumn = bRow == endR ? bColumn + 1 : bColumn;//因为 b先向下移动 再向右移动  所以先规定列的变化 再规定行的变化
			bRow = bRow == endR ? bRow : bRow + 1;//因为 b先向下移动 再向右移动  所以先规定列的变化 再规定行的变化
			fromUp = !fromUp;
		}
		System.out.println();
	}
	/*
	* 告诉 斜线的两端是点a 和 点b  方向是f 要么 右上 往 左下 打印， 要么 左下 向 右上 打印
	* */
	public static void printLevel(int[][] m, int tR, int tC, int dR, int dC, boolean f) {
		if (f) {
			while (tR != dR + 1) {
				System.out.print(m[tR++][tC--] + " ");
			}
		} else {
			while (dR != tR - 1) {
				System.out.print(m[dR--][dC++] + " ");
			}
		}
	}

	public static void main(String[] args) {
		int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
		printMatrixZigZag(matrix);

	}

}
