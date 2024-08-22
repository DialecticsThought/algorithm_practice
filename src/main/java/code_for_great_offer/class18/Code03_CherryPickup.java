package code_for_great_offer.class18;

/**
 *TODO
 * 牛客的测试链接：
 * https://www.nowcoder.com/questionTerminal/8ecfe02124674e908b2aae65aad4efdf\
 * 把如下的全部代码拷贝进java编辑器
 * 把文件大类名字改成Main，可以直接通过
 * 给定一个矩阵matrix，先从左上角开始，每一步只能往右或者往下走，走到右下角。
 * 然后从右下角出发，每一步只能往上或者往左走，再回到左上角。
 * 任何一个位置的数字，只能获得一遍。返回最大路径和。
 * 输入描述:
 * 第一行输入两个整数M和N，M,N<=200
 * 接下来M行，每行N个整数，表示矩阵中元素
 * 输出描述:
 * 输出一个整数，表示最大路径和
 * 示例1
 * 输入
 * 5 10
 * 1 1 1 1 1 0 0 0 0 0
 * 0 0 0 0 1 0 0 0 0 0
 * 0 0 0 0 1 0 0 0 0 1
 * 1 0 0 0 1 0 0 0 0 0
 * 0 0 0 0 1 1 1 1 1 1
 * 输出
 * 16
 * 示例2
 * 输入
 * 5 10
 * 1 1 1 1 1 0 0 0 0 0
 * 0 0 0 0 1 0 0 0 0 0
 * 0 0 0 0 1 0 0 0 0 1
 * 1 0 0 0 1 0 0 0 1 0
 * 0 0 0 0 1 1 1 1 0 1
 * 输出
 * 15
 * 可以理解为有2个人 A，B 都只能向下或者向右 如果A和B在同一个位置，"1"只有一个
 * A和B都是同步走，如果A和B到达相同的位置 一定是同时到达，不会有先后顺序
 * 每个格子 只有4种选择（分支）
 * A向右，B向右
 * A向右，B向下
 * A向下，B向下
 * A向下，B向右
 */
import java.util.Scanner;

public class Code03_CherryPickup {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[][] matrix = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }
        int ans = cherryPickup(matrix);
        int ans1 = cherryPickup1(matrix);
        System.out.println(ans);
        System.out.println(ans1);
        sc.close();
    }

    public static int cherryPickup1(int[][] grid) {
        int N = grid.length;
        int M = grid[0].length;
/*        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                for (int k = 0; k < N; k++) {
                    dp[i][j][k] = Integer.MIN_VALUE;
                }
            }
        }*/
        /**
         * A和B都从(0,0)除法走到右下角
         * 如果走到一块了那么只能获得一份桃子
         */
        int ans = process1(grid, 0, 0, 0, 0);
        return ans < 0 ? 0 : ans;
    }
    /**
     * A来到了(a,b)位置
     * B来到了(c,d)位置
     * 共同走到左下角的1 在整个旅途中，如果掉入同一个位置，桃子只获得一份/返回最大的桃子数量
     * d这个参数可以省略 原因 a+b=c+d => d = a+b-c
     * @param m
     * @param a
     * @param b
     * @param c
     * @param d
     * @return
     */
    public static int process1(int[][] m, int a, int b, int c, int d) {
        int N = m.length;
        int M = m[0].length;
        /**
         * 意思
         * a来到右下角的话 那么b也一定来到右下角
         */
        if (a == N - 1 && b == M - 1) {
            return m[a][b];
        }
        // A下 B下
        // A下 B右
        // A右 B右
        // A右 B下
        int best = 0;
        if (a < N - 1) {// A可以走下
            if (c < N - 1) {//B 也可以走下
                best = Math.max(best, process1(m, a + 1, b, c + 1, d));
            }
            if (d < M - 1) {//B 也可以走右
                best = Math.max(best, process1(m, a + 1, b, c, d + 1));
            }
        }
        if (b < M - 1) {// A可以走右
            if (c < N - 1) {//B 也可以走下
                best = Math.max(best, process1(m, a, b + 1, c + 1, d));
            }
            if (d < M - 1) {//B 也可以走右
                best = Math.max(best, process1(m, a, b + 1, c, d + 1));
            }
        }
        //A 和 B掉入同一个位置 那么只能得到一份桃子
        int cur = 0;
        if (a == c && b == d) {
            cur = m[a][b];
        } else {//A 和 B不是同一个位置 那么分别是2份桃子
            cur = m[a][b] + m[c][d];
        }
        return cur + best;
    }

    public static int cherryPickup(int[][] grid) {
        int N = grid.length;
        int M = grid[0].length;
        int[][][] dp = new int[N][M][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                for (int k = 0; k < N; k++) {
                    dp[i][j][k] = Integer.MIN_VALUE;
                }
            }
        }
        int ans = process(grid, 0, 0, 0, dp);
        return ans < 0 ? 0 : ans;
    }

    /**
     * 为什么只有3个参数的dp
     * 因为 a+b=c+d,
     * 只要知道等式的3个参数就能知道
     * @param grid
     * @param x1
     * @param y1
     * @param x2
     * @param dp
     * @return
     */
    public static int process(int[][] grid, int x1, int y1, int x2, int[][][] dp) {
        if (x1 == grid.length || y1 == grid[0].length || x2 == grid.length || x1 + y1 - x2 == grid[0].length) {
            return Integer.MIN_VALUE;
        }
        if (dp[x1][y1][x2] != Integer.MIN_VALUE) {
            return dp[x1][y1][x2];
        }
        if (x1 == grid.length - 1 && y1 == grid[0].length - 1) {
            dp[x1][y1][x2] = grid[x1][y1];
            return dp[x1][y1][x2];
        }
        int next = Integer.MIN_VALUE;
        next = Math.max(next, process(grid, x1 + 1, y1, x2 + 1, dp));
        next = Math.max(next, process(grid, x1 + 1, y1, x2, dp));
        next = Math.max(next, process(grid, x1, y1 + 1, x2, dp));
        next = Math.max(next, process(grid, x1, y1 + 1, x2 + 1, dp));
        if (grid[x1][y1] == -1 || grid[x2][x1 + y1 - x2] == -1 || next == -1) {
            dp[x1][y1][x2] = -1;
            return dp[x1][y1][x2];
        }
        if (x1 == x2) {
            dp[x1][y1][x2] = grid[x1][y1] + next;
            return dp[x1][y1][x2];
        }
        dp[x1][y1][x2] = grid[x1][y1] + grid[x2][x1 + y1 - x2] + next;
        return dp[x1][y1][x2];
    }

}
