package heima_data_structure.java.src.main.java.com.itheima.algorithm.dynamicprogramming;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 贝尔曼-福特算法求最短路径
 */
public class BellmanFord {
    static class Edge {
        int from;
        int to;
        int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    /*
            f(v) 用来表示从起点出发，到达 v 这个顶点的最短距离
            初始时
            f(v) = 0   当 v==起点 时
            f(v) = ∞   当 v!=起点 时

            之后
            新           旧     所有from
            f(to) = min(f(to), f(from) + from.weight)

            from 从哪来
            to   到哪去

            f(v4) = min( ∞, f(v3) + 11 ) = 20
            f(v4) = min( 20, f(v2) + 15 ) = 20


            v1  v2  v3  v4  v5  v6
            0   ∞   ∞   ∞   ∞   ∞
            0   7   9   ∞   ∞   14  第一轮
            0   7   9   20  23  11  第二轮
            0   7   9   20  20  11  第三轮
            0   7   9   20  20  11  第四轮
            0   7   9   20  20  11  第五轮

     */

    public static void main(String[] args) {
        List<Edge> edges = List.of(
                new Edge(6, 5, 9),
                new Edge(4, 5, 6),
                new Edge(1, 6, 14),
                new Edge(3, 6, 2),
                new Edge(3, 4, 11),
                new Edge(2, 4, 15),
                new Edge(1, 3, 9),
                new Edge(1, 2, 7)
        );
        int[] dp = new int[7]; // 一维数组用来缓存结果
        dp[1] = 0;
        for (int i = 2; i < dp.length; i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        print(dp);
        for (int i = 0; i < 5; i++) {
            for (Edge e : edges) {
                if(dp[e.from] != Integer.MAX_VALUE) {
                    dp[e.to] = Integer.min(dp[e.to], dp[e.from] + e.weight);
                }
            }
        }
        print(dp);
    }

    static void print(int[] dp) {
        System.out.println(Arrays.stream(dp)
                .mapToObj(i -> i == Integer.MAX_VALUE ? "∞" : String.valueOf(i))
                .collect(Collectors.joining(",", "[", "]")));
    }
}
