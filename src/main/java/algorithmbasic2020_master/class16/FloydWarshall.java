package algorithmbasic2020_master.class16;

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
 *      如果dist[i][j]>dist[i][k]+dist[k][j]，则更新：
 *          dist[i][j]=dist[i][k]+dist[k][j]
 *          pred[i][j]=pred[k][j]
 * 输出结果
 * 打印或返回距离矩阵dist，以及用于重建路径的前驱矩阵pred
 * </pre>
 */
public class FloydWarshall {
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
     * 初始化距离矩阵和前驱矩阵
     * Initial distance matrix:
     * 0   -1  3   INF INF
     * INF 0   INF 2   INF
     * INF INF 0   7   4
     * INF INF -6  0   5
     * INF INF INF INF 0
     *
     * Initial predecessor matrix:
     * 0   0   0   NIL NIL
     * NIL 1   NIL 1   NIL
     * NIL NIL 2   2   2
     * NIL NIL 3   3   3
     * NIL NIL NIL NIL 4
     *
     * 第1次迭代 (k = 0)
     * 考虑通过节点 0 (k = 0) 的路径，更新距离矩阵和前驱矩阵：
     * 更新说明：
     * 节点0到节点3的距离更新为 0 + 1 = 1
     * 前驱矩阵中，节点0到节点3的前驱节点更新为1
     * After considering vertex 0 (distance matrix):
     * 0   -1  3   1   INF
     * INF 0   INF 2   INF
     * INF INF 0   7   4
     * INF INF -6  0   5
     * INF INF INF INF 0
     *
     * After considering vertex 0 (predecessor matrix):
     * 0   0   0   1   NIL
     * NIL 1   NIL 1   NIL
     * NIL NIL 2   2   2
     * NIL NIL 3   3   3
     * NIL NIL NIL NIL 4
     *
     * 第2次迭代 (k = 1)
     * 考虑通过节点 1 (k = 1) 的路径，更新距离矩阵和前驱矩阵：
     * 更新说明：
     * 该步无更新
     * After considering vertex 1 (distance matrix):
     * 0   -1  3   1   INF
     * INF 0   INF 2   INF
     * INF INF 0   7   4
     * INF INF -6  0   5
     * INF INF INF INF 0
     *
     * After considering vertex 1 (predecessor matrix):
     * 0   0   0   1   NIL
     * NIL 1   NIL 1   NIL
     * NIL NIL 2   2   2
     * NIL NIL 3   3   3
     * NIL NIL NIL NIL 4
     *
     * 第3次迭代 (k = 2)
     * 考虑通过节点 2 (k = 2) 的路径，更新距离矩阵和前驱矩阵
     * 更新说明：
     * 节点0到节点4的距离更新为 3 + 4 = 7
     * 前驱矩阵中，节点0到节点4的前驱节点更新为2
     * After considering vertex 2 (distance matrix):
     * 0   -1  3   1   7
     * INF 0   INF 2   INF
     * INF INF 0   7   4
     * INF INF -6  0   5
     * INF INF INF INF 0
     *
     * After considering vertex 2 (predecessor matrix):
     * 0   0   0   1   2
     * NIL 1   NIL 1   NIL
     * NIL NIL 2   2   2
     * NIL NIL 3   3   3
     * NIL NIL NIL NIL 4
     *
     * 第4次迭代 (k = 3)
     * 考虑通过节点 3 (k = 3) 的路径，更新距离矩阵和前驱矩阵：
     * 更新说明：
     * 节点0到节点2的距离更新为 1 - 6 = -5
     * 节点0到节点4的距离更新为 1 + 5 = 6
     * 前驱矩阵中，节点0到节点2的前驱节点更新为3，节点0到节点4的前驱节点更新为2
     * After considering vertex 3 (distance matrix):
     * 0   -1  -5  1   6
     * INF 0   INF 2   INF
     * INF INF 0   7   4
     * INF INF -6  0   5
     * INF INF INF INF 0
     *
     * After considering vertex 3 (predecessor matrix):
     * 0   0   3   1   2
     * NIL 1   NIL 1   NIL
     * NIL NIL 2   2   2
     * NIL NIL 3   3   3
     * NIL NIL NIL NIL 4
     *
     * 第5次迭代 (k = 4)
     * 考虑通过节点 4 (k = 4) 的路径，更新距离矩阵和前驱矩阵：
     * 更新说明：
     * 该步无更新
     * Final distance matrix:
     * 0   -1  -5  1   6
     * INF 0   INF 2   INF
     * INF INF 0   7   4
     * INF INF -6  0   5
     * INF INF INF INF 0
     *
     * Final predecessor matrix:
     * 0   0   3   1   2
     * NIL 1   NIL 1   NIL
     * NIL NIL 2   2   2
     * NIL NIL 3   3   3
     * NIL NIL NIL NIL 4
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
