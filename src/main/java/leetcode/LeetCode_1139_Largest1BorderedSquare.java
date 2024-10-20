package leetcode;

/**
 * code_for_great_offer.class03
 * 本题测试链接 : https://leetcode.cn/problems/largest-1-bordered-square/
 */
public class LeetCode_1139_Largest1BorderedSquare {

/*    public static int test(int[][] m) {
        int N = m.length;
        int M = m[0].length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                for (int border = 1; border <= Math.min(N - i, M - j); border++) {
                    *//*
                    *TODO 改正发行最上顶点是(i,j) 边长为border
                    *  验证这个正方形 是不是边框都为1 并且O(1)
                    * *//*
                }
            }
        }
    }*/


    public static int largest1BorderedSquare(int[][] m) {
        int[][] right = new int[m.length][m[0].length];
        int[][] down = new int[m.length][m[0].length];
        setBorderMap(m, right, down);
        for (int size = Math.min(m.length, m[0].length); size != 0; size--) {
            if (hasSizeOfBorder(size, right, down)) {
                return size * size;
            }
        }
        return 0;
    }
    /*
    *TODO
    *  对于矩阵中的任何一个点（i，j）
    *  需要知道以该点作为正方形的左上角 从该点开始向右到数组最右侧一共有多少连续的1
    *  需要知道以该点作为正方形的左上角 从该点开始向下到数组最底边一共有多少连续的1
    *  这会形成2个数组 这2个数组会记录 上面的答案
    * */
    public static void setBorderMap(int[][] m, int[][] right, int[][] down) {
        int r = m.length;
        int c = m[0].length;
        if (m[r - 1][c - 1] == 1) {
            right[r - 1][c - 1] = 1;
            down[r - 1][c - 1] = 1;
        }
        for (int i = r - 2; i != -1; i--) {
            if (m[i][c - 1] == 1) {
                right[i][c - 1] = 1;
                down[i][c - 1] = down[i + 1][c - 1] + 1;
            }
        }
        for (int i = c - 2; i != -1; i--) {
            if (m[r - 1][i] == 1) {
                right[r - 1][i] = right[r - 1][i + 1] + 1;
                down[r - 1][i] = 1;
            }
        }
        for (int i = r - 2; i != -1; i--) {
            for (int j = c - 2; j != -1; j--) {
                if (m[i][j] == 1) {
                    right[i][j] = right[i][j + 1] + 1;
                    down[i][j] = down[i + 1][j] + 1;
                }
            }
        }
    }

    public static boolean hasSizeOfBorder(int size, int[][] right, int[][] down) {
        for (int i = 0; i != right.length - size + 1; i++) {
            for (int j = 0; j != right[0].length - size + 1; j++) {
                if (right[i][j] >= size && down[i][j] >= size && right[i + size - 1][j] >= size
                        && down[i][j + size - 1] >= size) {
                    return true;
                }
            }
        }
        return false;
    }

}
