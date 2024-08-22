package other.ad2.class01;

import java.util.*;

public class Code01_NiuNiuSplitField {

	public static int maxMinSumIn16(int[][] matrix) {
		if (matrix == null || matrix.length < 4 || matrix[0].length < 4) {
			return 0;
		}
		//左上角为（0,0）到右下角为（i，j）的子矩阵累加和是多少
		int[][] record = generateSumRecord(matrix);
		int col = matrix[0].length;
		int res = Integer.MIN_VALUE;
		//3个for循环就代表了竖着切的3刀
		for (int c1 = 0; c1 < col - 3; c1++) {
			for (int c2 = c1 + 1; c2 < col - 2; c2++) {
				for (int c3 = c2 + 1; c3 < col - 1; c3++) {
					//在确定竖着3刀的情况下 横着切可以优化
					//传入record的原因是因为需要累加和
					res = Math.max(res, getBestDicision(record, c1, c2, c3));
				}
			}
		}
		return res;
	}

	public static int[][] generateSumRecord(int[][] matrix) {
		int row = matrix.length;
		int col = matrix[0].length;
		int[][] record = new int[row][col];
		record[0][0] = matrix[0][0];
		for (int i = 1; i < row; i++) {
			record[i][0] = record[i - 1][0] + matrix[i][0];
		}
		for (int j = 1; j < col; j++) {
			record[0][j] = record[0][j - 1] + matrix[0][j];
		}
		for (int i = 1; i < row; i++) {
			for (int j = 1; j < col; j++) {
				record[i][j] = record[i][j - 1] + record[i - 1][j] - record[i - 1][j - 1] + matrix[i][j];
			}
		}
		return record;
	}

	public static int getBestDicision(int[][] record, int c1, int c2, int c3) {
		/*
		* 0~i行中切一刀得到8块相应的累加和 取最小
		* i~n-1行中切一刀得到8块相应的累加和 取最小
		* 最后求横着切的中间一刀切在哪里
		* */
		int[] up = getUpSplitArray(record, c1, c2, c3);
		int[] down = getDownSplitArray(record, c1, c2, c3);
		int res = Integer.MIN_VALUE;
		for (int i = 1; i < record.length - 2; i++) {
			res = Math.max(res, Math.min(up[i], down[i + 1]));
		}
		return res;
	}
	/*
	* 竖着切的三刀固定  开始行和结束行固定
	* */
	public static int value(int[][] record, int c1, int c2, int c3, int prow, int crow) {
		//得到了4块
		int value1 = area(record, prow, 0, crow, c1);
		int value2 = area(record, prow, c1 + 1, crow, c2);
		int value3 = area(record, prow, c2 + 1, crow, c3);
		int value4 = area(record, prow, c3 + 1, crow, record[0].length - 1);
		//返回4块的累加和最小值
		return Math.min(Math.min(value1, value2), Math.min(value3, value4));
	}

	public static int area(int[][] record, int i1, int j1, int i2, int j2) {
		int all = record[i2][j2];
		int left = j1 > 0 ? record[i2][j1 - 1] : 0;
		int up = i1 > 0 ? record[i1 - 1][j2] : 0;
		int makeUp = (i1 > 0 && j1 > 0) ? record[i1 - 1][j1 - 1] : 0;
		return all - left - up + makeUp;
	}

	public static int[] getUpSplitArray(int[][] record, int c1, int c2, int c3) {
		int size = record.length;
		int[] up = new int[size];
		int split = 0;
		up[1] = Math.min(value(record, c1, c2, c3, 0, 0), value(record, c1, c2, c3, 1, 1));
		for (int i = 2; i < size; i++) {
			/*
			* split代表当前循环 切第几行
			* */
			int minsMax = towSubMatrixMin(record, c1, c2, c3, 0, split, i);
			while (split < i) {
				if (split == i - 1) {
					break;
				}
				int moved = towSubMatrixMin(record, c1, c2, c3, 0, split + 1, i);
				if (moved < minsMax) {
					break;
				} else {
					minsMax = moved;
					split++;
				}
			}
			up[i] = minsMax;
		}
		return up;
	}

	public static int[] getDownSplitArray(int[][] record, int c1, int c2, int c3) {
		int size = record.length;
		int[] down = new int[size];
		int split = size - 1;
		down[size - 2] = Math.min(value(record, c1, c2, c3, size - 2, size - 2),
				value(record, c1, c2, c3, size - 1, size - 1));
		for (int i = size - 3; i >= 0; i--) {
			int minsMax = towSubMatrixMin(record, c1, c2, c3, i, split - 1, size - 1);
			while (split > i) {
				if (split == i + 1) {
					break;
				}
				int moved = towSubMatrixMin(record, c1, c2, c3, i, split - 2, size - 1);
				if (moved < minsMax) {
					break;
				} else {
					minsMax = moved;
					split--;
				}
			}
			down[i] = minsMax;
		}
		return down;
	}

	public static int towSubMatrixMin(int[][] record, int c1, int c2, int c3, int i, int split, int j) {
		return Math.min(value(record, c1, c2, c3, i, split), value(record, c1, c2, c3, split + 1, j));
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			int n = in.nextInt();
			int m = in.nextInt();
			int[][] matrix = new int[n][m];
			for (int i = 0; i < n; i++) {
				char[] chas = in.next().toCharArray();
				for (int j = 0; j < m; j++) {
					matrix[i][j] = chas[j] - '0';
				}
			}
			System.out.println(maxMinSumIn16(matrix));
		}
		in.close();
	}

}
