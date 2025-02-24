package heima_data_structure.java.src.main.java.com.itheima.algorithm.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * <h3>N皇后 - 回溯</h3>
 */
public class LeetCode_051_NQueen {
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

    /**
     *
     * @param solutions 结果
     * @param queens  queen[0] = 2 表示第0行第二列
     * @param n 当前第几行
     * @param row 设定有几行
     * @param columns 用来记录哪些列表 已经有N皇后了
     * @param diagonals1 这个集合记录主对角线（从左上到右下）的占用情况
     *                   主对角线上的格子满足 row - col = constant。因此，如果两个皇后在同一条主对角线，它们的行列差是相等的
     * @param diagonals2 这个集合记录副对角线（从右上到左下）的占用情况
     *                   副对角线上的格子满足 row + col = constant。因此，如果两个皇后在同一条副对角线，它们的行列和是相等的
     */
    public void backtrack(List<List<String>> solutions, int[] queens, int n,
                          int row, Set<Integer> columns, Set<Integer> diagonals1, Set<Integer> diagonals2) {
        // base case
        if(row == n) {
            List<String> strings = generateBoard(queens, row);
            solutions.add(strings);
            return;
        }
        // 因为正方形 行和列的数量相同
        for(int col = 0; col < n; col++) {
            if(columns.contains(col)) {
                continue;
            }
            int diagonal1 = row - col;
            if(diagonals1.contains(diagonal1)) {
                continue;
            }
            int diagonal2 = row + col;
            if(diagonals2.contains(diagonal2)) {
                continue;
            }
            // 记录当前 第n行 第col列 占用的皇后
            queens[row] = col;
            columns.add(col);
            diagonals1.add(diagonal1);
            diagonals2.add(diagonal2);

            // 递归
            backtrack(solutions, queens, n, row + 1, columns, diagonals1, diagonals2);

            //恢复现场
            queens[row] = -1;
            columns.remove(-1);
            diagonals1.remove(diagonal1);
            diagonals2.remove(diagonal2);
            //执行到这里向上返回了
        }
    }


    public List<String> generateBoard(int[] queens, int n) {
        List<String> board = new ArrayList<String>();
        for (int i = 0; i < n; i++) {
            char[] row = new char[n];
            Arrays.fill(row, '.');
            row[queens[i]] = 'Q';
            board.add(new String(row));
        }
        return board;
    }
}
