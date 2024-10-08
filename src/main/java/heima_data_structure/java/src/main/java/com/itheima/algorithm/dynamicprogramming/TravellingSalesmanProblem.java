package heima_data_structure.java.src.main.java.com.itheima.algorithm.dynamicprogramming;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * <h3>旅行商问题</h3>
 */
public class TravellingSalesmanProblem {

    /**
     * 这个问题不是最短路径问题
     * 北京->
     * 上海->
     * 武汉->
     * 西安->
     * <p>
     * 4x3x2 = 24
     * 5x4x3x2 = 120
     * ...
     * (n-1)!
     * 北京->上海->
     * 武汉->西安->北京
     * 西安->武汉->北京
     * 西安->
     * 上海->武汉->北京
     * 武汉->上海->北京
     * 武汉->
     * 上海->西安->北京
     * 西安->上海->北京
     * <p>
     * g
     * 0  1  2  3
     * 0  {0, 1, 2, 3}
     * 1  {1, 0, 6, 4}
     * 2  {2, 6, 0, 5}
     * 3  {3, 4, 5, 0}
     * <p>
     * d(出发城市, 剩余城市集合) ==> 从出发城市开始，走完剩余城市，花费的最少代价
     * d(0,1|2|3) => g[0][1] + d(1,2|3) => g[1][3] + d(3,2) => g[3][2] + d(2,空)
     * g[2][0]
     * => g[1][2] + d(2,3) => g[2][3] + d(3,空)
     * g[3][0]
     * g[0][2] + d(2,1|3) => g[2][1] + d(1,3) => g[1][3] + d(3,空)
     * g[3][0]
     * => g[2][3] + d(3,1) => g[3][1] + d(1,空)
     * g[1][0]
     * g[0][3] + d(3,1|2) => g[3][1] + d(1,2) => g[1][2] + d(2,空)
     * g[2][0]
     * => g[3][2] + d(2,1) => g[2][1] + d(1,空)
     * g[1][0]
     * 0   1   2   3    4   5    6    7     j 剩余城市集合
     * 0   1   2   1|2  3   1|3  2|3  1|2|3
     * 0
     * 1
     * 2
     * 3
     * i 出发城市
     * <p>
     * 000 没城市  0
     * 001 1号    1
     * 010 2号    2
     * 100 3号    4
     * 011 1和2   3
     * 101 1和3   5
     * 110 2和3   6
     * 111 1和2和3 7
     * <p>
     * 出发城市 i
     * 剩余城市集合 j
     * 遍历 j 时的变量 k （剩余的某一个城市)
     * d(i, j) => min(
     * g[i][k] + d(k, j去掉k)
     * g[i][k] + d(k, j去掉k)
     * g[i][k] + d(k, j去掉k)
     * )
     * d(k,空) => 从k回到起点 => g[k][i]
     * <p>
     * <p>
     * d(0,1|2) => g[0][1] + d(1,2)
     * => g[0][2] + d(2,1)
     * d(1,1|2)
     * d(2,1|2)
     * d(3,1|2) => g[3][1] + d(1,2)
     * => g[3][2] + d(2,1)
     */


    public static void main(String[] args) {
        int[][] graph = {
                {0, 1, 2, 3},
                {1, 0, 6, 4},
                {2, 6, 0, 5},
                {3, 4, 5, 0},
        };
        System.out.println(tsp(graph));
    }

    static int tsp(int[][] g) {
        int m = g.length; // 城市数目
        int n = 1 << (m - 1); // 剩余城市的组合数  2^(m-1)
        int[][] dp = new int[m][n];

        // 填充第0列
        for (int k = 0; k < m; k++) {
            dp[k][0] = g[k][0];
        }
        print(dp);

        // 填充后续列
        for (int j = 1; j < n; j++) {
            for (int i = 0; i < m; i++) {
                dp[i][j] = Integer.MAX_VALUE / 2;
                if (contains(j, i)) continue; // 剩余城市集合已包含出发城市，不合理
                // 填充单元格
                for (int k = 0; k < m; k++) {
                    if (contains(j, k)) { // 只对剩余城市集合中的城市进行处理
                        dp[i][j] = Integer.min(dp[i][j], g[i][k] + dp[k][exclude(j, k)]);
                    }
                }
            }
        }
        print(dp);
        return dp[0][n - 1];
    }

    /*
        2|3
        110  城市1是否存在    110
                           001 &
                           ----
                           000
                           false
        110  城市2是否存在    011
                           001 &
                           ----
                           001
                           true
        110  城市3是否存在    001
                           001 &
                           ----
                           001
                           true
        110  城市4是否存在    000
                           001 &
                           ----
                           000
                           false

     */
    static boolean contains(int set, int city) {
        return (set >> (city - 1) & 1) == 1;
    }

    /*
        1|2|3  1 => 2|3

        111
        001 ^
        ----
        110     2|3

        1|2|3  2 => 1|3
        111
        010 ^
        ----
        101   1|3
     */
    static int exclude(int set, int city) {
        return set ^ (1 << (city - 1));
    }

    static void print(int[][] dist) {
        System.out.println("-------------------------");
        for (int[] row : dist) {
            System.out.println(Arrays.stream(row).boxed()
                    .map(x -> x >= Integer.MAX_VALUE / 2 ? "∞" : String.valueOf(x))
                    .map(s -> String.format("%2s", s))
                    .collect(Collectors.joining(",", "[", "]")));
        }
    }
}
