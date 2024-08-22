package code_for_great_offer.class24;

import java.util.ArrayList;
import java.util.List;

public class Code04_Painting {

    /*
     * N * M的棋盘
     * 每种颜色的格子数必须相同的
     * 相邻格子染的颜色必须不同
     * 所有格子必须染色
     * 返回至少多少种颜色可以完成任务
     *
     * 答案就是 N * M 的最小质数因子
     * */
    public static int minColors(int N, int M) {
        // 颜色数量是i
        for (int i = 2; i < N * M; i++) {
            int[][] matrix = new int[N][M];
            // 下面这一句可知，需要的最少颜色数i，一定是N*M的某个因子
            // 必须(N * M) % i == 0 因为保证 每种颜色的格子数必须相同的
            if ((N * M) % i == 0 && can(matrix, N, M, i)) {
                return i;
            }
        }
        return N * M;
    }

    /*
     * 函数的意义：
     * 在matrix上染色，返回只用pNum种颜色是否可以做到要求
     *
     * */
    public static boolean can(int[][] matrix, int N, int M, int pNum) {
        int all = N * M;//总共多少格子
        int every = all / pNum;//每种颜色的数量是多少
        ArrayList<Integer> rest = new ArrayList<>();//记录每种颜色的剩余数量
        rest.add(0);
        //当前的颜色数量pNum的情况下，每种颜色的数量是多少 因为 每种颜色的格子数必须相同的
        for (int i = 1; i <= pNum; i++) {
            rest.add(every);
        }
        return process(matrix, N, M, pNum, 0, 0, rest);
    }

    /*
     * 先每一列递归 再每一行递归
     * 先向右 再向下
     * */
    public static boolean process(int[][] matrix, int N, int M, int pNum, int row, int col, List<Integer> rest) {
        if (row == N) {
            return true;
        }
        if (col == M) {
            return process(matrix, N, M, pNum, row + 1, 0, rest);
        }
        /*
        * 每个格子都考虑 左和上 即可 因为 递归是 先向右 再向下
        * */
        int left;
        //得到当前格子的左边格子的颜色
        if (col == 0) {
            left = 0;
        } else {
            left = matrix[row][col - 1];
        }
        int up ;
        //得到当前格子的上边格子的颜色
        if(row == 0){
            up=0;
        }else {
            up = matrix[row - 1][col];
        }
        //int left = col == 0 ? 0 : matrix[row][col - 1];
        //int up = row == 0 ? 0 : matrix[row - 1][col];
        //枚举 颜色  一共1~pNum种颜色
        for (int color = 1; color <= pNum; color++) {
            /*
            * 当前被遍历到的颜色的条件
            * 既不是和左边格子颜色相同 也不是和上边格子颜色相同 还要该颜色的数量有剩余
             * */
            if (color != left && color != up && rest.get(color) > 0) {
                int count = rest.get(color);
                //选定该颜色后 该颜色的剩余数量-1
                rest.set(color, count - 1);
                //选定该颜色
                matrix[row][col] = color;
                // 当前格子 [row][col]选择完之后 让 [row][col+1]选择（递归）
                if (process(matrix, N, M, pNum, row, col + 1, rest)) {
                    return true;
                }
                //如果当前节点（格子）的选择路径是正确的 那么还原现场 继续下一个枚举+递归 （dfs）
                rest.set(color, count);
                matrix[row][col] = 0;
            }
        }
        //如果用pNum数量的颜色 还是不能解决问题的话 就返回false 尝试pNum+1种
        return false;
    }

    public static void main(String[] args) {
        // 根据代码16行的提示，打印出答案，看看是答案是哪个因子
        for (int N = 2; N < 10; N++) {
            for (int M = 2; M < 10; M++) {
                System.out.println("N   = " + N);
                System.out.println("M   = " + M);
                System.out.println("ans = " + minColors(N, M));
                System.out.println("===========");
            }
        }
        // 打印答案，分析可知，是N*M最小的质数因子，原因不明，也不重要
        // 反正打表法猜出来了
    }

}
