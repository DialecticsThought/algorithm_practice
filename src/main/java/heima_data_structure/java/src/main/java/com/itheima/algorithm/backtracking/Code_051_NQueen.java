package heima_data_structure.java.src.main.java.com.itheima.algorithm.backtracking;

import java.util.Arrays;

/**
 * <h3>N皇后 - 回溯</h3>
 */
public class Code_051_NQueen {
    public static void main(String[] args) {
        int n = 4;
        boolean[] ca = new boolean[n]; // 记录列冲突
        boolean[] cb = new boolean[2 * n - 1]; // 左斜线冲突
        boolean[] cc = new boolean[2 * n - 1]; // 右斜线冲突
        char[][] table = new char[n][n]; // '.' 'Q'
        for (char[] t : table) {
            Arrays.fill(t, '.');
        }
        dfs(0, n, table, ca, cb, cc);
    }

    static void dfs(int i, int n, char[][] table, boolean[] ca, boolean[] cb, boolean[] cc) {
        if (i == n) { // 找到解
            System.out.println("-------------------");
            for (char[] t : table) {
                System.out.println(new String(t));
            }
            return;
        }
        for (int j = 0; j < n; j++) {
            if (ca[j] || cb[i + j] || cc[n - 1 - (i - j)]) {
                continue;
            }
            table[i][j] = 'Q';
            ca[j] = cb[i + j] = cc[n - 1 - (i - j)] = true;
            dfs(i + 1, n, table, ca, cb, cc);
            table[i][j] = '.';
            ca[j] = cb[i + j] = cc[n - 1 - (i - j)] = false;
        }
    }
}
