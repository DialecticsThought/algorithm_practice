package other.ad2.class02;

import java.util.ArrayList;

public class Code04_DeleteColumnsToMakeSorted {

	public static int minDeletionSize1(String[] strs) {
		if (strs == null || strs.length == 0 || strs[0] == null || strs[0].length() == 0) {
			return 0;
		}
		int num = strs.length;
		int len = strs[0].length();
		char[][] matrix = new char[len][num];
		for (int i = 0; i < num; i++) {
			char[] str = strs[i].toCharArray();
			for (int j = 0; j < len; j++) {
				matrix[j][i] = str[j];
			}
		}
		int res = Integer.MAX_VALUE;
		int[] dp = new int[len];
		for (int i = 0; i < dp.length; i++) {
			dp[i] = 1;
		}
		for (int j = 0; j < len; ++j) {
			for (int i = 0; i < j; ++i) {
				int k = 0;
				for (k = 0; k < num; ++k) {
					if (matrix[i][k] > matrix[j][k]) {
						break;
					}
				}
				if (k == num && dp[i] + 1 > dp[j]) {
					dp[j] = dp[i] + 1;
				}
			}
			res = Math.min(res, len - dp[j]);
		}
		return res;
	}

	public static int minDeletionSize2(String[] strs) {
		if (strs == null || strs.length == 0 || strs[0] == null || strs[0].length() == 0) {
			return 0;
		}
		int num = strs.length;
		int len = strs[0].length();
		char[][] matrix = new char[len][num];
		for (int i = 0; i < num; i++) {
			char[] str = strs[i].toCharArray();
			for (int j = 0; j < len; j++) {
				matrix[j][i] = str[j];
			}
		}
		int[] dp = new int[len];
		dp[0] = 1;
		ArrayList<ArrayList<char[]>> ends = new ArrayList<>();
		for (int i = 0; i < len; i++) {
			ends.add(new ArrayList<>());
		}
		ends.get(0).add(matrix[0]);
		int right = 0;
		int res = len - 1;
		for (int j = 1; j < len; ++j) {
			int l = 0;
			int r = right;
			while (l <= r) {
				int m = (l + r) / 2;
				if (noMore(ends.get(m), matrix[j])) {
					l = m + 1;
				} else {
					r = m - 1;
				}
			}
			right = Math.max(right, l);
			ends.get(l).add(matrix[j]);
			dp[j] = l + 1;
			res = Math.min(res, len - dp[j]);
		}
		return res;
	}

	public static boolean noMore(ArrayList<char[]> pres, char[] cur) {
		for (char[] pre : pres) {
			int i = 0;
			for (; i < pre.length; i++) {
				if (pre[i] > cur[i]) {
					break;
				}
			}
			if (i == pre.length) {
				return true;
			}
		}
		return false;
	}

	public static String random(int len, int varible) {
		char[] str = new char[len];
		for (int i = 0; i < len; i++) {
			str[i] = (char) ((int) (Math.random() * varible) + 'a');
		}
		return String.valueOf(str);
	}

	public static String[] randoms(int num, int maxLen, int varible) {
		int size = (int) (Math.random() * num) + 1;
		int len = (int) (Math.random() * maxLen) + 1;
		String[] strs = new String[size];
		for (int i = 0; i < strs.length; i++) {
			strs[i] = random(len, varible);
		}
		return strs;
	}

	public static void printStringArray(String[] strs) {
		for (int i = 0; i < strs.length; i++) {
			System.out.println(strs[i]);
		}
	}

	public static void main(String[] args) {
		String[] test = randoms(20000, 20000, 5);
		long start;
		long end;
		start = System.nanoTime();
		minDeletionSize1(test);
		end = System.nanoTime();
		System.out.println((end - start) + " nano time");
		start = System.nanoTime();
		minDeletionSize2(test);
		end = System.nanoTime();
		System.out.println((end - start) + " nano time");

	}

}
