package code_for_great_offer.class01;

/**
 * @author zbl
 * @version 1.0
 * @content:给定一个整数矩阵matrix，每个位置你可以向左、右、下、上移动，找到其中最长的递增路径。 例如：
 * matrix = [
 * [9,9,4],
 * [6,6,8],
 * [2,1,1]
 * ]
 * 返回4
 * 最长路径是[1, 2, 6, 9].
 * matrix = [
 * [3,4,5],
 * [3,2,6],
 * [2,2,1]
 * ]
 * 返回4
 * 最长路径是[1, 2, 6, 9].
 * @date 2020/2/15 18:37
 */
public class LeetCode_329_LongestIncreasingPath {
    /*
     * TODO
     *  给定一个二维数组matrix,
     * 你可以从任何位置出发，走向上下左右四个方向返回能走出来的最长的递增链长度  leetcode有
     * */
    public static int longestIncreasingPath1(int[][] matrix) {
        int ans = 0;
        int N = matrix.length;
        int M = matrix[0].length;
        //TODO 从二维数组中的每一个位置 开始做尝试 所以 2层for循环
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                ans = Math.max(ans, process1(matrix, i, j));
            }
        }
        return ans;
    }

    //TODO 从m[i][j]开始走，走出来的最长递增链，返回！
    public static int process(int[][] m, int i, int j) {
        if (i < 0 || i == m.length || j < 0 || j == m[0].length) {//TODO 越界 返回0
            return 0;
        }
        //TODO 不越界 有4种尝试 上下左右
        int up = 0;
        if (i > 0 && m[i][j] < m[i - 1][j]) {//TODO 当前位置有上面的位置 且 上面位置的数 比当前的数 大
            up = process(m, i - 1, j);//TODO 向上尝试
        }
        int down = 0;
        if (i < m.length - 1 && m[i][j] < m[i + 1][j]) {//TODO 当前位置有下面的位置 且 下面位置的数 比当前的数 大
            down = process(m, i + 1, j);//TODO 向下尝试
        }
        int left = 0;
        if (i > 0 && m[i][j] < m[i][j - 1]) {//TODO 当前位置有左面的位置 且 左面位置的数 比当前的数 大
            left = process(m, i, j - 1);//TODO 向左尝试
        }

        int right = 0;
        if (i < m.length - 1 && m[i][j] < m[i][j + 1]) {//TODO 当前位置有左面的位置 且 左面位置的数 比当前的数 大
            right = process(m, i, j + 1);//TODO 向右尝试
        }
        //TODO  把 当前节点 answer 返回给上一层
        return Math.max(Math.max(up, down), Math.max(left, right)) + 1;//TODO +1 表示当前节点（决策）
    }

    //TODO 从m[i][j]开始走，走出来的最长递增链，返回！
    public static int process1(int[][] m, int i, int j) {
        int up = i > 0 && m[i][j] < m[i - 1][j] ? process1(m, i - 1, j) : 0;
        int down = i < (m.length - 1) && m[i][j] < m[i + 1][j] ? process1(m, i + 1, j) : 0;
        int left = j > 0 && m[i][j] < m[i][j - 1] ? process1(m, i, j - 1) : 0;
        int right = j < (m[0].length - 1) && m[i][j] < m[i][j + 1] ? process1(m, i, j + 1) : 0;
        return Math.max(Math.max(up, down), Math.max(left, right)) + 1;
    }

    public static int longestIncreasingPath2(int[][] matrix) {
        int ans = 0;
        int N = matrix.length;
        int M = matrix[0].length;
        //TODO 创建一个缓存表
        int[][] dp = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                ans = Math.max(ans, process2(matrix, i, j, dp));
            }
        }
        return ans;
    }

    /*
     *TODO
     * 从m[i][j]开始走，走出来的最长递增链，返回！
     * 加缓存的原因 因为 i和j确定了 答案就确定了
     * */
    public static int process2(int[][] m, int i, int j, int[][] dp) {
        if (dp[i][j] != 0) {
            return dp[i][j];
        }
        // (i,j)不越界
        int up = i > 0 && m[i][j] < m[i - 1][j] ? process2(m, i - 1, j, dp) : 0;
        int down = i < (m.length - 1) && m[i][j] < m[i + 1][j] ? process2(m, i + 1, j, dp) : 0;
        int left = j > 0 && m[i][j] < m[i][j - 1] ? process2(m, i, j - 1, dp) : 0;
        int right = j < (m[0].length - 1) && m[i][j] < m[i][j + 1] ? process2(m, i, j + 1, dp) : 0;
        //TODO 取出当前节点的 answer
        int ans = Math.max(Math.max(up, down), Math.max(left, right)) + 1;
        //TODO 缓存 当前节点 answer
        dp[i][j] = ans;
        //TODO  把 当前节点 answer 返回给上一层
        return ans;
    }

}
