package algorithmbasic2020_master.class40;

/*
* 转圈打印矩阵
* */
public class Code05_PrintMatrixSpiralOrder {

	public static void spiralOrderPrint(int[][] matrix) {
		int tR = 0;
		int tC = 0;
		int dR = matrix.length - 1;
		int dC = matrix[0].length - 1;
		/*
		* 不允许
		* 左上角的行 越过 右下角的 行
		* 左上角的列 越过 右下角的 列
		 * */
		while (tR <= dR && tC <= dC) {
			/*
			* tR tC规定左上角  dR dC 规定右下角
			* 每次打印完最外层 就让左上角 向 右下移动  右下角 向 左上移动
			* */
			printEdge(matrix, tR++, tC++, dR--, dC--);
		}
	}

	public static void printEdge(int[][] m, int a, int b, int c, int d) {
		/*
		* 打印一条横线的状态
		* */
		if (a == c) {
			for (int i = b; i <= d; i++) {
				System.out.print(m[a][i] + " ");//a不变 i 在变
			}
		} else if (b == d) {
			for (int i = a; i <= c; i++) {
				System.out.print(m[i][b] + " ");//b不变 i 在变
			}
		} else {
			int curC = b;
			int curR = a;
			while (curC != d) {
				System.out.print(m[a][curC] + " ");
				curC++;
			}
			while (curR != c) {
				System.out.print(m[curR][d] + " ");
				curR++;
			}
			while (curC != b) {
				System.out.print(m[c][curC] + " ");
				curC--;
			}
			while (curR != a) {
				System.out.print(m[curR][b] + " ");
				curR--;
			}
		}
	}

	public static void main(String[] args) {
		int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 },
				{ 13, 14, 15, 16 } };
		spiralOrderPrint(matrix);

	}

}
