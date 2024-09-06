package other.ad2.class08;

import java.util.ArrayList;

public class Code01_TSP {

	public static int tsp1(int[][] matrix, int origin) {
		if (matrix == null || matrix.length < 2 || origin < 0 || origin >= matrix.length) {
			return 0;
		}
		ArrayList<Integer> cities = new ArrayList<>();
		for (int i = 0; i < matrix.length; i++) {
			cities.add(i);
		}
		cities.set(origin, null);
		return process(matrix, origin, cities, origin);
	}

	public static int process(int[][] matrix, int origin, ArrayList<Integer> cities, int cur) {
		boolean hasCity = false;
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < cities.size(); i++) {
			if (cities.get(i) != null) {
				hasCity = true;
				cities.set(i, null);
				ans = Math.min(ans, matrix[cur][i] + process(matrix, origin, cities, i));
				cities.set(i, i);
			}
		}
		return hasCity ? ans : matrix[cur][origin];
	}

	public static int tsp2(int[][] matrix, int origin) {
		if (matrix == null || matrix.length < 2 || origin < 0 || origin >= matrix.length) {
			return 0;
		}
		int N = matrix.length - 1;
		int S = 1 << N;
		int[][] dp = new int[S][N];
		int icity = 0;
		int kcity = 0;
		for (int i = 0; i < N; i++) {
			icity = i < origin ? i : i + 1;
			dp[0][i] = matrix[icity][origin];
		}
		for (int status = 1; status < S; status++) {
			for (int i = 0; i < N; i++) {
				dp[status][i] = Integer.MAX_VALUE;
				if ((1 << i & status) != 0) {
					icity = i < origin ? i : i + 1;
					for (int k = 0; k < N; k++) {
						if ((1 << k & status) != 0) {
							kcity = k < origin ? k : k + 1;
							dp[status][i] = Math.min(dp[status][i], dp[status ^ (1 << i)][k] + matrix[icity][kcity]);
						}
					}
				}
			}
		}
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < N; i++) {
			icity = i < origin ? i : i + 1;
			ans = Math.min(ans, dp[S - 1][i] + matrix[origin][icity]);
		}
		return ans;
	}

	public static int[][] generateGraph(int maxSize, int maxValue) {
		int len = (int) (Math.random() * maxSize) + 1;
		int[][] matrix = new int[len][len];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				matrix[i][j] = (int) (Math.random() * maxValue) + 1;
			}
		}
		for (int i = 0; i < len; i++) {
			matrix[i][i] = 0;
		}
		return matrix;
	}

	public static void main(String[] args) {
		int len = 9;
		int value = 100;
		for (int i = 0; i < 20000; i++) {
			int[][] matrix = generateGraph(len, value);
			int origin = (int) (Math.random() * matrix.length);
			int ans1 = tsp1(matrix, origin);
			int ans2 = tsp2(matrix, origin);
			if (ans1 != ans2) {
				System.out.println("fuck");
			}
		}

	}

}
