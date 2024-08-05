package algorithmbasic2020_master.class16;

import java.util.*;

/**
 * <pre>
 * SPFA 算法
 *
 * SPFA 算法全称是最短路径快速算法 (Shortest Path Faster Algorithm)，它是对 Bellman-Ford 算法的改进
 *
 * 在每轮松弛的过程中，我们保留那些d值更新过的节点，而在下一次松弛时，仅仅需要对这些节点为起始节点的边进行松弛即可
 *
 * 算法开始时，先将节点 v_0 进入队列
 *
 * 每次从队列中取一个节点，并对这个节点为起始节点的所有边进行松弛
 *
 * 在松弛的过程中，如果对应的节点 d 值发生改变，且节点并不在队列中，则此节点进入队列（语句 8 到 11）
 *
 * 节点进入队列的次数达到 n 次，说明节点被松弛过 n 次，算法返回 False，说明图G存在权重为负的环。
 * </pre>
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
     * 从队列中取出节点0，开始松弛操作。
     * 松弛操作：
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
     * 从队列中取出节点1，开始松弛操作。
     * 松弛操作：
     * 对边 (1→3, 2)：
     * d[3] > d[1] + weight(1→3)  ==>  ∞ > -1 + 2  ==>  d[3] = 1
     * 将节点3入队：
     * queue = [2, 3]
     * in_queue = [False, False, True, True, False]
     * 更新距离数组：
     * d = [0, -1, 3, 1, ∞]
     *
     * 第三步：
     * 从队列中取出节点2，开始松弛操作。
     * 松弛操作：
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
     * 从队列中取出节点3，开始松弛操作。
     * 松弛操作：
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
     * 从队列中取出节点4，开始松弛操作。
     * 节点4没有出边，不需要松弛操作。
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
     * 从队列中取出节点2，开始松弛操作。
     * 松弛操作：
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
     * 从队列中取出节点4，开始松弛操作。
     * 节点4没有出边，不需要松弛操作。
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
    public static Map<Node, Integer> spfa(Node source, int numNodes, ArrayList<Edge> edges) {
        // 初始化距离数组
        Map<Node, Integer> distances = new HashMap<>();
        for (Edge edge : edges) {
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
