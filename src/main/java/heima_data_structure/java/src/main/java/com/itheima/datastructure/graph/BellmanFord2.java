package heima_data_structure.java.src.main.java.com.itheima.datastructure.graph;

import algorithmbasic2020_master.class16.Edge;
import algorithmbasic2020_master.class16.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Bellman-Ford
 * TODO 设  d(i) 表示从源节点 0到节点 i的距离  这里 固定了一个点 i
 * 假设已经知道一条从 0到 j的路径 0 → j，而通过某一节点 i（和j相连的某一节点）的路径 0→ i → j比原来的路径 0 → j的距离要小
 * 即 d(i) + w(i,j) < d(j)
 * 则0到节点j的距离更新为 d(j) <- d(i) + w(i,j):
 *  以上操作称之为通过w(i,j)对节点j的松弛操作，也称通过节点 i对节点 j 的松弛操作，定义为 relax(i,j)。
 *
 * 1. 初始化，将源节点的 d 值设为 0，其他节点设为无穷 也就是从0到其他任何节点的距离=infinite
 * 2. 遍历图中所有的边，在遍历每条边时，做松弛操作。
 * 3. 对步骤 2 重复 n - 1 次
 * 4. 做第 n 次遍历，如果在此次遍历中，某节点的 d 值发生改变，则不存在最短路径，返回 false，
 *      否则返回每个节点的 d 值，即为源节点到此节点的最短路径。
 * 定理  如果图 G = (V, E)， |V| = n， |E| = m，不存在权重为负的环，
 * 则 Bellman-Ford 算法返回的各节点的 d 值即为从源节点到此节点的最短路径的长度。
 *
 * 证明：
 * 对于图 G 的任何一个节点 v，设其最短路径 p 的长度 δ(v_0, v) 为 v_0  -> v_1 -> v_2 -> v_3 -> ....... -> v_i -> v。
 * 因为 p 为最短路径，且由最短路径的最优子结构性质，可得 δ(v_0, v) = δ(v_0, v_i) + w(v_i, v)。
 *
 * 图 G（不包含权重为负的环）源节点 v_0 到任意一个节点的最短路径包含边的条数小于等于 n - 1，
 * 所以经过 n - 1 此松弛以后，可得源节点 v_0 到任意一个节点的最短路径。
 *
 * 推论 如果图G=（V，E) |v| =n, |E| = m, 在经过n-1此松弛收敛，则图G存在权重为负的环
 * </pre>
 *
 * @Description
 * @Author veritas
 * @Data 2024/8/5 18:26
 */
public class BellmanFord2 {
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
     * 规定边的松弛顺序是edge：(3,2),(1,3),(2,3),(3,4),(0,1),(0,2),(2,4),(3,2)
     *
     * 初始化
     * 距离数组初始化：源节点0到其他节点的距离初始化为无穷大（∞），但到自身的距离为0。
     * d = [0, ∞, ∞, ∞, ∞]
     *
     * 迭代过程
     * 对每一轮松弛，我们按照指定的顺序进行边的松弛操作，并进行 n−1 轮迭代（这里n=5）。
     *
     * 第1轮松弛操作：
     * 按照顺序松弛边：(3,2), (1,3), (2,3), (3,4), (0,1), (0,2), (2,4), (3,2)
     *
     * 边 (3,2)：
     * d[2] > d[3] + weight(3→2)  ==>  ∞ > ∞ - 6  ==>  不更新
     *
     * 边 (1,3)：
     * d[3] > d[1] + weight(1→3)  ==>  ∞ > ∞ + 2  ==>  不更新
     *
     * 边 (2,3)：
     * d[3] > d[2] + weight(2→3)  ==>  ∞ > ∞ + 7  ==>  不更新
     *
     * 边 (3,4)：
     * d[4] > d[3] + weight(3→4)  ==>  ∞ > ∞ + 5  ==>  不更新
     *
     * 边 (0,1)：
     * d[1] > d[0] + weight(0→1)  ==>  ∞ > 0 - 1  ==>  d[1] = -1
     *
     * 边 (0,2)：
     * d[2] > d[0] + weight(0→2)  ==>  ∞ > 0 + 3  ==>  d[2] = 3
     *
     * 边 (2,4)：
     * d[4] > d[2] + weight(2→4)  ==>  ∞ > 3 + 4  ==>  d[4] = 7
     *
     * 边 (3,2)：
     * d[2] > d[3] + weight(3→2)  ==>  3 > ∞ - 6  ==>  不更新
     *
     * 更新后的距离数组：
     * d = [0, -1, 3, ∞, 7]
     *
     * 第2轮松弛操作：
     * 按照顺序松弛边：(3,2), (1,3), (2,3), (3,4), (0,1), (0,2), (2,4), (3,2)
     *
     * 边 (3,2)：
     * d[2] > d[3] + weight(3→2)  ==>  3 > ∞ - 6  ==>  不更新
     * 边 (1,3)：
     * d[3] > d[1] + weight(1→3)  ==>  ∞ > -1 + 2  ==>  d[3] = 1
     * 边 (2,3)：
     * d[3] > d[2] + weight(2→3)  ==>  1 > 3 + 7  ==>  不更新
     * 边 (3,4)：
     * d[4] > d[3] + weight(3→4)  ==>  7 > 1 + 5  ==>  d[4] = 6
     * 边 (0,1)：
     * d[1] > d[0] + weight(0→1)  ==>  -1 > 0 - 1  ==>  不更新
     * 边 (0,2)：
     * d[2] > d[0] + weight(0→2)  ==>  3 > 0 + 3  ==>  不更新
     * 边 (2,4)：
     * d[4] > d[2] + weight(2→4)  ==>  6 > 3 + 4  ==>  不更新
     * 边 (3,2)：
     * d[2] > d[3] + weight(3→2)  ==>  3 > 1 - 6  ==>  d[2] = -5
     * 更新后的距离数组：
     * d = [0, -1, -5, 1, 6]
     *
     *
     * 第3轮松弛操作：
     * 按照顺序松弛边：(3,2), (1,3), (2,3), (3,4), (0,1), (0,2), (2,4), (3,2)
     * 边 (3,2)：
     * d[2] > d[3] + weight(3→2)  ==>  -5 > 1 - 6  ==>  不更新
     *
     * 边 (1,3)：
     * d[3] > d[1] + weight(1→3)  ==>  1 > -1 + 2  ==>  不更新
     *
     * 边 (2,3)：
     * d[3] > d[2] + weight(2→3)  ==>  1 > -5 + 7  ==>  d[3] = 2
     *
     * 边 (3,4)：
     * d[4] > d[3] + weight(3→4)  ==>  6 > 2 + 5  ==>  d[4] = 7
     *
     * 边 (0,1)：
     * d[1] > d[0] + weight(0→1)  ==>  -1 > 0 - 1  ==>  不更新
     *
     * 边 (0,2)：
     * d[2] > d[0] + weight(0→2)  ==>  -5 > 0 + 3  ==>  不更新
     *
     * 边 (2,4)：
     * d[4] > d[2] + weight(2→4)  ==>  7 > -5 + 4  ==>  d[4] = -1
     *
     * 边 (3,2)：
     * d[2] > d[3] + weight(3→2)  ==>  -5 > 2 - 6  ==>  不更新
     *
     * 更新后的距离数组：
     * d = [0, -1, -5, 2, -1]
     *
     *
     * 第4轮松弛操作：
     * 按照顺序松弛边：(3,2), (1,3), (2,3), (3,4), (0,1), (0,2), (2,4), (3,2)
     * 边 (3,2)：
     * d[2] > d[3] + weight(3→2)  ==>  -5 > 2 - 6  ==>  不更新
     *
     * 边 (1,3)：
     * d[3] > d[1] + weight(1→3)  ==>  2 > -1 + 2  ==>  不更新
     *
     * 边 (2,3)：
     * d[3] > d[2] + weight(2→3)  ==>  2 > -5 + 7  ==>  d[3] = 2
     *
     * 边 (3,4)：
     * d[4] > d[3] + weight(3→4)  ==>  -1 > 2 + 5  ==>  不更新
     *
     * 边 (0,1)：
     * d[1] > d[0] + weight(0→1)  ==>  -1 > 0 - 1  ==>  不更新
     *
     * 边 (0,2)：
     * d[2] > d[0] + weight(0→2)  ==>  -5 > 0 + 3  ==>  不更新
     *
     * 边 (2,4)：
     * d[4] > d[2] + weight(2→4)  ==>  -1 > -5 + 4  ==>  不更新
     *
     * 边 (3,2)：
     * d[2] > d[3] + weight(3→2)  ==>  -5 > 2 - 6  ==>  不更新
     *
     * 最终更新后的距离数组：
     * d = [0, -1, -5, 2, -1]
     * </pre>
     * <p>
     * 结果
     * 最终得到从源节点0到其他所有节点的最短路径长度：
     * 0到1的最短路径：0 → 1，长度为-1
     * 0到2的最短路径：0 → 1 → 3 → 2，长度为-5
     * 0到3的最短路径：0 → 1 → 3，长度为2
     * 0到4的最短路径：0 → 1 → 3 → 2 → 4，长度为-1
     *
     * @param source
     * @param numNodes
     * @param edges
     * @return
     */
    public static Map<Node, Integer> bellmanFord(Node source, int numNodes, ArrayList<algorithmbasic2020_master.class16.Edge> edges) {
        // 初始化距离数组
        Map<Node, Integer> distances = new HashMap<>();
        for (algorithmbasic2020_master.class16.Edge edge : edges) {
            distances.put(edge.from, Integer.MAX_VALUE);
            distances.put(edge.to, Integer.MAX_VALUE);
        }
        distances.put(source, 0);

        // 进行 numNodes - 1 轮松弛操作
        for (int i = 0; i < numNodes - 1; i++) {
            for (algorithmbasic2020_master.class16.Edge edge : edges) {
                if (distances.get(edge.from) != Integer.MAX_VALUE && distances.get(edge.from) + edge.weight < distances.get(edge.to)) {
                    distances.put(edge.to, distances.get(edge.from) + edge.weight);
                }
            }
        }

        // 检查是否存在负环
        for (Edge edge : edges) {
            if (distances.get(edge.from) != Integer.MAX_VALUE && distances.get(edge.from) + edge.weight < distances.get(edge.to)) {
                System.out.println("Graph contains a negative weight cycle");
                return null;
            }
        }

        return distances;
    }
}
