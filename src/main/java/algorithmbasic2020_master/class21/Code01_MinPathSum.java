package algorithmbasic2020_master.class21;

public class Code01_MinPathSum {
    /**
     * TODO
     * 给定一个二维数组matrix，一个人必须从左上角出发，
     * 最后到达右下角沿途只可以向下或者向右走，沿途的数字都累加就是距离累加和
     * 返回最小距离累加和
     * eg:
     * 3  7  8  7
     * ↓
     * 1→ 2  6  4
     * ↓
     * 10 3  8  9
     * ↓
     * 8  1→ 2→ 0
     * 每个节点有2个选择 向下/向右  2个选择取最小的
     * 决策树（不完整）
     * |         3
     * |   下 ↙     ↘ 右
     * |    1        7
     * |    ↙ ↘      ↙ ↘
     * |  10 2      2  8
     * |  ↙  ↘
     * | 3     6
     * |  ↙ ↘    ↙ ↘
     * |  1  8    8  4
     * |  ↙  ↙ ↘  ↙ ↘  ↘
     * |  2   2 9  2 9   9
     * dp[i][j]表示从(0,0)点出发(i,j)的最优解（最短路径）
     * dp表
     * 3   4   8   14  21
     * 12      a
     * |       ↑
     * 14  b ← ?
     * 21
     * 24
     * 第0行只能从出发点一直向右走
     * 第0列也是只能从向下走
     * 对于? 它的值依赖于上面的格子和左侧格子的min+原数组的位置对应的值
     * 对于每个字依赖于左 和 上
     * 那么dp表从上到下 从左网友填写
     * 优化点：
     * 对于第1行数据依赖于第0行的已知数据
     * 对于第2行数据依赖于第1行的已知数据 二部依赖于第0行数据
     * 这种类型 可以用空间压缩
     * 那么只要2个数组A和B
     * 第1轮:
     * 第0行数据存在A，第1行数据存在B
     * 第2轮:
     * 第1行数据存在B，第2行数据存在A,第0行数据释放掉
     * .......
     *
     * @param m
     * @return
     */
    public static int minPathSum1(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }
        int row = m.length;
        int col = m[0].length;
        int[][] dp = new int[row][col];
        dp[0][0] = m[0][0];//TODO base case
        for (int i = 1; i < row; i++) {//TODO 填好第0行
            dp[i][0] = dp[i - 1][0] + m[i][0];
        }
        for (int j = 1; j < col; j++) {//TODO 填好第0列
            dp[0][j] = dp[0][j - 1] + m[0][j];
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + m[i][j];
            }
        }
        return dp[row - 1][col - 1];
    }

    public static int minPathSum2(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }
        int row = m.length;
        int col = m[0].length;
        int[] dp = new int[col];
        dp[0] = m[0][0];
        for (int j = 1; j < col; j++) {
            dp[j] = dp[j - 1] + m[0][j];
        }
        for (int i = 1; i < row; i++) {
            dp[0] += m[i][0];
            for (int j = 1; j < col; j++) {
                dp[j] = Math.min(dp[j - 1], dp[j]) + m[i][j];
            }
        }
        return dp[col - 1];
    }

    public void test() {

    }

    //TODO 从(x,y) 到达(i,j)
    public static int process(int[][] m, int x, int y, int i, int j) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }
        //TODO 向下
        int way1 = process(m, x + 1, y, i, j);
        //TODO 向右
        int way2 = process(m, x, y + 1, i, j);
        //TODO 取最小
        int result = Math.min(way1, way2) + m[i][j];
        return result;
    }

    // for test
    public static int[][] generateRandomMatrix(int rowSize, int colSize) {
        if (rowSize < 0 || colSize < 0) {
            return null;
        }
        int[][] result = new int[rowSize][colSize];
        for (int i = 0; i != result.length; i++) {
            for (int j = 0; j != result[0].length; j++) {
                result[i][j] = (int) (Math.random() * 100);
            }
        }
        return result;
    }

    // for test
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i != matrix.length; i++) {
            for (int j = 0; j != matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int rowSize = 10;
        int colSize = 10;
        int[][] m = generateRandomMatrix(rowSize, colSize);
        int process = process(m, 0, 0, m[0].length, m.length);
        System.out.println(process);
        System.out.println(minPathSum1(m));
        System.out.println(minPathSum2(m));

    }
}
