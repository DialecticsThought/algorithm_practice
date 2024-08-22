package code_for_great_offer.class46;

import java.util.TreeSet;
//返回arr中所有子数组的累加和 <=K 并且离k最近的累加和是多少
public class Problem_0363_MaxSumOfRectangleNoLargerThanK {
	public static int lessOrEqualK(int[] arr, int K) {
		int answer = 0;
		/*
		 * 存放 0~0 0~1 0~2 ....0~i的前缀和 并且有序排放
		 * */
		TreeSet<Integer> prefixSum = new TreeSet<>();
		//代表一个数也没有的情况下 前缀和 为 0
		prefixSum.add(0);
		/*
		 *TODO
		 * 尝试必须以0为结尾的子数组 累加和<=K 并且离K最近的累加和是多少
		 * 尝试必须以1为结尾的子数组 累加和<=K 并且离K最近的累加和是多少
		 * ....
		 * 尝试必须以i为结尾的子数组 累加和<=K 并且离K最近的累加和是多少
		 * 假设 K=1000 i=17
		 * 尝试必须以i为结尾的子数组 累加和<=1000 并且离1000最近的累加和是多少
		 * 但是0~17的整个累加和 = 1000 如何找到x 0~x的累加和>=900 且离900最近
		 * 那么 x~i的累加和 <=100 且离100最近
		 * 假设
		 * 范围  累加和
		 * 0~0  600
		 * 0~1  400
		 * 0~2  700
		 * 0~3  800
		 * 0~4  750
		 * 0~5  900
		 * 0~6  1000
		 * 求[i]~[6]范围的子数组的累加和<=100 => 求[0]~[i-1]范围的子数组的累加和>=900
		 * 对于2维数组
		 * eg:汉一共4行数组 0~3
		 * 0 [3 2 4 -5 6]
		 * 1 [2 4 2 3 -4]
		 * ....
		 * 0~0行数组压缩后求一遍
		 * 0~1行数组压缩后[5 6 6 -2 2]求一遍
		 * 0~2行....  0~3行....
		 * 1~1行.... 1~2行.... 1~3行....
		 * 2~2行.... 2~3行....
		 * 3~3行....
		 * */
		int sum = 0; //[0~i]的累加和
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
			int aim = sum - K;// >=aim 离aim最近的前缀累加和是什么
			//也就是找prefixSum里的数
			int bestPrefix = prefixSum.ceiling(aim);
			answer = sum - bestPrefix;
		}
		return answer;
	}

	public static int nearK(int[] arr, int k) {
		if (arr == null || arr.length == 0) {
			return Integer.MIN_VALUE;
		}

		TreeSet<Integer> set = new TreeSet<>();
		set.add(0);
		int ans = Integer.MIN_VALUE;
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			// 讨论子数组必须以i位置结尾，最接近k的累加和是多少？
			sum += arr[i];
			// 找之前哪个前缀和 >= sum - k  且最接近
			// 有序表中，ceiling(x) 返回>=x且最接近的！
			// 有序表中，floor(x) 返回<=x且最接近的！
			Integer find = set.ceiling(sum - k);
			if(find != null) {
				int curAns = sum - find;
				ans = Math.max(ans, curAns);
			}
			set.add(sum);
		}
		return ans;
	}

	public static int maxSumSubmatrix(int[][] matrix, int k) {
		if (matrix == null || matrix[0] == null) {
			return 0;
		}
		if (matrix.length > matrix[0].length) {
			matrix = rotate(matrix);
		}
		int row = matrix.length;
		int col = matrix[0].length;
		int res = Integer.MIN_VALUE;
		TreeSet<Integer> sumSet = new TreeSet<>();
		for (int s = 0; s < row; s++) {
			int[] colSum = new int[col];
			for (int e = s; e < row; e++) {
				// s ~ e 这些行  选的子矩阵必须包含、且只包含s行~e行的数据
				// 0 ~ 0  0 ~ 1  0 ~ 2 。。。
				// 1 ~ 2  1 ~ 2  1 ~ 3 。。。
				sumSet.add(0);
				int rowSum = 0;
				for (int c = 0; c < col; c++) {
					colSum[c] += matrix[e][c];
					rowSum += colSum[c];
					Integer it = sumSet.ceiling(rowSum - k);
					if (it != null) {
						res = Math.max(res, rowSum - it);
					}
					sumSet.add(rowSum);
				}
				sumSet.clear();
			}
		}
		return res;
	}

	public static int[][] rotate(int[][] matrix) {
		int N = matrix.length;
		int M = matrix[0].length;
		int[][] r = new int[M][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				r[j][i] = matrix[i][j];
			}
		}
		return r;
	}

}
