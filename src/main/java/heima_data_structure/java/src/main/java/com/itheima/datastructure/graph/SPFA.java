package heima_data_structure.java.src.main.java.com.itheima.datastructure.graph;

import algorithmbasic2020_master.class16.Edge;
import algorithmbasic2020_master.class16.Node;

import java.util.*;

/**
 * <pre>
 * 假设已经知道一条从 0到 j的路径 0 → j，而通过某一节点 i（和j相连的某一节点）的路径 0→ i → j比原来的路径 0 → j的距离要小，
 * 即 d(i) + w(i,j) < d(j) ，其中 d(i) 表示从源节点 0到节点 i的距离，
 * 则0到节点j的距离更新为 d(j) <- d(i) + w(i,j):
 *  以上操作称之为通过w(i,j)对节点j的松弛操作，也称通过节点 i对节点 j 的松弛操作，定义为 relax(i,j)。
 *
 * SPFA 算法全称是最短路径快速算法 (Shortest Path Faster Algorithm)，它是对 Bellman-Ford 算法的改进
 *
 * 初始化：
 * 首先，将源节点（在这里我们选择节点0）的距离初始化为0，并将其他所有节点的距离初始化为无穷大（∞）。
 * 将源节点0入队列。
 *
 * 处理：
 * 当队列不为空时，进行以下操作：
 * 从队列中取出一个节点u。
 * 对于每个与u相连的邻接节点v，检查通过u到v的距离是否可以缩短。这一步称为“松弛”操作。
 * 如果通过u到v的距离得到了更新，并且v不在队列中，则将v入队列。
 *
 * 结束：
 * 当队列为空时，算法结束。此时，从源节点到所有节点的最短距离已经找出。
 * </pre>
 *
 * @Description
 * @Author veritas
 * @Data 2024/8/5 18:27
 */
public class SPFA {
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
     * 初始化
     * 距离数组初始化：源节点0到其他节点的距离初始化为无穷大（∞），但到自身的距离为0
     * d = [0, ∞, ∞, ∞, ∞]
     * 队列初始化：将源节点0入队，并标记其在队列中。
     * queue = [0]
     * in_queue = [True, False, False, False, False]
     *
     * 迭代过程
     * 第一步：
     * 从队列中取出0
     * 对0的所有邻接节点进行松弛操作：
     * 对边 (0→1, -1)：
     * d[1] > d[0] + weight(0→1)  ==>  ∞ > 0 - 1  ==>  d[1] = -1
     * 将节点1入队：
     * queue = [1]
     * in_queue = [False, True, False, False, False]
     * 更新距离数组：
     * d = [0, -1, ∞, ∞, ∞]
     * 对边 (0→2, 3)：
     * d[2] > d[0] + weight(0→2)  ==>  ∞ > 0 + 3  ==>  d[2] = 3
     * 将节点2入队：
     * queue = [1, 2]
     * in_queue = [False, True, True, False, False]
     * 更新距离数组：
     * d = [0, -1, 3, ∞, ∞]
     *
     *
     * 第二步：
     * 从队列中取出节点1，
     * 对1的所有邻接节点进行松弛操作：
     * 对边 (1→3, 2)：
     * d[3] > d[1] + weight(1→3)  ==>  ∞ > -1 + 2  ==>  d[3] = 1
     * 将节点3入队：
     * queue = [2, 3]
     * in_queue = [False, False, True, True, False]
     * 更新距离数组：
     * d = [0, -1, 3, 1, ∞]
     *
     * 第三步：
     * 从队列中取出节点2。
     * 对2的所有邻接节点进行松弛操作：
     * 对边 (2→3, 7)：
     * d[3] > d[2] + weight(2→3)  ==>  1 > 3 + 7  ==>  d[3] = 1 (不更新)
     * 对边 (2→4, 4)：
     * d[4] > d[2] + weight(2→4)  ==>  ∞ > 3 + 4  ==>  d[4] = 7
     * 将节点4入队：
     * queue = [3, 4]
     * in_queue = [False, False, False, True, True]
     * 更新距离数组：
     * d = [0, -1, 3, 1, 7]
     *
     * 第四步：
     * 从队列中取出3。
     * 对3的所有邻接节点进行松弛操作：
     * 对边 (3→4, 5)：
     * d[4] > d[3] + weight(3→4)  ==>  7 > 1 + 5  ==>  d[4] = 6
     * 将节点4再次入队：
     * queue = [4, 4]
     * in_queue = [False, False, False, True, True]
     * 更新距离数组：
     * d = [0, -1, 3, 1, 6]
     * 对边 (3→2, -6)：
     * d[2] > d[3] + weight(3→2)  ==>  3 > 1 - 6  ==>  d[2] = -5
     * 将节点2再次入队：
     * queue = [4, 4, 2]
     * in_queue = [False, False, True, True, True]
     * 更新距离数组：
     * d = [0, -1, -5, 1, 6]
     *
     *
     * 第五步：
     * 从队列中取出4。
     * 对4的所有邻接节点进行松弛操作：没有可松弛的边。
     * queue = [4, 2]
     * in_queue = [False, False, True, False, True]
     *
     *
     * 第六步：
     * 从队列中取出节点4，开始松弛操作。
     * 节点4没有出边，不需要松弛操作。
     * queue = [2]
     * in_queue = [False, False, True, False, False]
     *
     * 第七步：
     * 从队列中取出2。
     * 对2的所有邻接节点进行松弛操作：
     * 对边 (2→3, 7)：
     * d[3] > d[2] + weight(2→3)  ==>  1 > -5 + 7  ==>  d[3] = 1 (不更新)
     * 对边 (2→4, 4)：
     * d[4] > d[2] + weight(2→4)  ==>  6 > -5 + 4  ==>  d[4] = -1
     * 将节点4再次入队：
     * queue = [4]
     * in_queue = [False, False, False, False, True]
     * 更新距离数组：
     * d = [0, -1, -5, 1, -1]
     *
     * 第八步：
     * 队列中取出4。
     * 对4的所有邻接节点进行松弛操作：没有可松弛的边。
     * queue = []
     * in_queue = [False, False, False, False, False]
     *
     * 结果
     * 最终得到从源节点0到其他所有节点的最短路径长度：
     * d = [0, -1, -5, 1, -1]
     * </pre>
     *
     * @param source
     * @param numNodes
     * @param edges
     * @return
     */
    public static Map<Node, Integer> spfa(Node source, int numNodes, ArrayList<algorithmbasic2020_master.class16.Edge> edges) {
        // 初始化距离数组
        Map<Node, Integer> distances = new HashMap<>();
        for (algorithmbasic2020_master.class16.Edge edge : edges) {
            distances.put(edge.from, Integer.MAX_VALUE);
            distances.put(edge.to, Integer.MAX_VALUE);
        }
        distances.put(source, 0);

        // 初始化队列和在队列中的标记
        Queue<Node> queue = new ArrayDeque<>();
        Map<Node, Boolean> inQueue = new HashMap<>();
        for (Node node : distances.keySet()) {
            inQueue.put(node, false);
        }

        // 将源节点加入队列
        queue.add(source);
        inQueue.put(source, true);

        // 进行松弛操作
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            inQueue.put(current, false);
            for (Edge edge : current.edges) {
                if (distances.get(current) != Integer.MAX_VALUE && distances.get(current) + edge.weight < distances.get(edge.to)) {
                    distances.put(edge.to, distances.get(current) + edge.weight);
                    if (!inQueue.get(edge.to)) {
                        queue.add(edge.to);
                        inQueue.put(edge.to, true);
                    }
                }
            }
        }

        return distances;
    }
}
