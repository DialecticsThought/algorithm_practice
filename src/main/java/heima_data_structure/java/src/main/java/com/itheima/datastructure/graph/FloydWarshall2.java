package heima_data_structure.java.src.main.java.com.itheima.datastructure.graph;

import algorithmbasic2020_master.class16.Edge;
import algorithmbasic2020_master.class16.Node;

import java.util.ArrayList;

/**
 * @Description
 * @Author veritas
 * @Data 2024/8/6 17:09
 * <pre>
 * 初始化
 *  创建距离矩阵dist
 *      如果有直接路径从节点i 到节点j，则dist[i][j]=weight(i,j)。
 *      如果没有直接路径，则dist[i][j]=∞。
 *      对于所有节点dist[i][i]=0。
 *  创建前驱矩阵pred
 *      如果有直接路径从节点i 到节点j，则pred[i][j]=i。
 *      如果没有直接路径，则pred[i][j]=−1。
 *      对于所有节点i，pred[i][i]=i。
 * 迭代
 *  主循环：对于每个中间节点k（从 0 到n−1），执行以下操作：
 *      对于每一对节点(i,j)，检查是否通过节点k 可以得到更短的路径。
 *      如果dist[i][j]>dist[i][k]+dist[k][j]，
 *          则更新：
 *              dist[i][j]=dist[i][k]+dist[k][j]
 *              pred[i][j]=pred[k][j]
 * 输出结果
 * 打印或返回距离矩阵dist，以及用于重建路径的前驱矩阵pred
 * </pre>
 */
public class FloydWarshall2 {
    /**
     * <pre>
     *                2
     *          ① -------------> ③
     *   -1   ↗ ↓             ↙↗  ↓
     *     ↗    ↓        6 ↙↗     ↓
     *   0    4 ↓       ↙↗ 7      ↓ 5
     *     ↘    ↓    ↙↗           ↓
     *    3   ↘ ↓ ↙↗              ↓
     *          ② -------------> ④
     *                 4
     * </pre>
     * <pre>
     * 初始状态
     * 距离矩阵 (dist)
     * 0   -1   3   ∞   ∞
     * ∞    0   ∞   2   ∞
     * ∞    ∞   0   7   4
     * ∞    ∞  -6   0   5
     * ∞    ∞   ∞   ∞   0
     * 前驱矩阵 (pred)
     * -1   0   0  -1  -1
     * -1  -1  -1   1  -1
     * -1  -1  -1   2   2
     * -1  -1   3  -1   3
     * -1  -1  -1  -1  -1
     *
     * 第一轮 (k=0)
     * 对于所有节点i和j，检查是否可以通过节点0使路径更短。
     *
     * 第二轮 (k=1)
     * 更新步骤：dist[i][j] = dist[i][k] + dist[k][j] (其中k=1)
     * 示例更新：dist[0][3] = dist[0][1] + dist[1][3] = -1 + 2 = 1
     * 更新后的矩阵：
     * 距离矩阵 (dist):
     * 0   -1   3   1   ∞
     * ∞    0   ∞   2   ∞
     * ∞    ∞   0   7   4
     * ∞    ∞  -6   0   5
     * ∞    ∞   ∞   ∞   0
     * 前驱矩阵 (pred):
     * -1   0   0   1  -1
     * -1  -1  -1   1  -1
     * -1  -1  -1   2   2
     * -1  -1   3  -1   3
     * -1  -1  -1  -1  -1
     *
     * 第三轮 (k=2)
     * 更新步骤：dist[i][j] = dist[i][k] + dist[k][j] (其中k=2)
     * 示例更新：dist[3][3] = dist[3][2] + dist[2][3] = -6 + 7 = 1
     * 更新后的矩阵：
     * 距离矩阵 (dist):
     * 0   -1   3   1   ∞
     * ∞    0   ∞   2   ∞
     * ∞    ∞   0   7   4
     * ∞    ∞  -6   1   5
     * ∞    ∞   ∞   ∞   0
     * 前驱矩阵 (pred):
     * -1   0   0   1  -1
     * -1  -1  -1   1  -1
     * -1  -1  -1   2   2
     * -1  -1   3   2   3
     * -1  -1  -1  -1  -1
     *
     * 第四轮 (k=3)
     * 更新步骤：dist[i][j] = dist[i][k] + dist[k][j] (其中k=3)
     * 示例更新：dist[1][4] = dist[1][3] + dist[3][4] = 2 + 5 = 7
     * 更新后的矩阵：
     * 距离矩阵 (dist):
     * 0   -1   3   1   6
     * ∞    0   ∞   2   7
     * ∞    ∞   0   7   4
     * ∞    ∞  -6   1   5
     * ∞    ∞   ∞   ∞   0
     * 前驱矩阵 (pred):
     * -1   0   0   1   3
     * -1  -1  -1   1   3
     * -1  -1  -1   2   2
     * -1  -1   3   2   3
     * -1  -1  -1  -1  -1
     *
     * 第五轮 (k=4)
     * 更新步骤：dist[i][j] = dist[i][k] + dist[k][j] (其中k=4)
     * 此轮不会进行任何更新，因为k=4的行和列中所有值都为INF。
     * </pre>
     *
     * @param nodes
     */
    public static void floydWarshall(ArrayList<Node> nodes) {
        int n = nodes.size();
        int[][] dist = new int[n][n];
        int[][] pred = new int[n][n];  // 前驱矩阵

        // 初始化距离矩阵和前驱矩阵
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                    pred[i][j] = i;
                } else {
                    dist[i][j] = Integer.MAX_VALUE / 2; // 使用较大的值来表示无穷大，防止加法溢出
                    pred[i][j] = -1;
                }
            }
        }

        // 设置边的权重
        for (Node node : nodes) {
            for (Edge edge : node.edges) {
                dist[edge.from.value][edge.to.value] = edge.weight;
                pred[edge.from.value][edge.to.value] = edge.from.value;
            }
        }

        // 打印初始化后的距离矩阵和前驱矩阵
        printMatrix(dist, "Initial distance matrix:");
        printMatrix(pred, "Initial predecessor matrix:");

        // 进行Floyd-Warshall算法
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        pred[i][j] = pred[k][j];  // 更新前驱矩阵
                    }
                }
            }
            // 打印每次k迭代后的距离矩阵和前驱矩阵
            printMatrix(dist, "After considering vertex " + k + " (distance matrix):");
            printMatrix(pred, "After considering vertex " + k + " (predecessor matrix):");
        }

        // 打印最终的最短路径
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && dist[i][j] != Integer.MAX_VALUE / 2) {
                    System.out.print("Shortest path from " + i + " to " + j + ": ");
                    printPath(pred, i, j);
                    System.out.println(" with total cost " + dist[i][j]);
                }
            }
        }
    }

    public static void printPath(int[][] pred, int i, int j) {
        if (i == j) {
            System.out.print(i + " ");
        } else if (pred[i][j] == -1) {
            System.out.print("No path");
        } else {
            printPath(pred, i, pred[i][j]);
            System.out.print(j + " ");
        }
    }

    public static void printMatrix(int[][] matrix, String message) {
        System.out.println(message);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == Integer.MAX_VALUE / 2) {
                    System.out.print("INF ");
                } else if (matrix[i][j] == -1) {
                    System.out.print("NIL ");
                } else {
                    System.out.print(matrix[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

}
