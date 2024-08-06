package algorithmbasic2020_master.class16;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * TODO undirected graph only
 * 1）可以从任意节点出发来寻找最小生成树
 * 2）某个点加入到被选取的点中后，解锁这个点出发的所有新的边
 * 3）在所有解锁的边中选最小的边，然后看看这个边会不会形成环
 * 4）如果会，不要当前边，继续考察剩下解锁的边中最小的边，重复3)
 * 5）如果不会，要当前边，将该边的指向点加入到被选取的点中，重复2)
 * 6)当所有点都被选取，最小生成树就得到了
 * 3个操作
 * scan操作必须首先进行，以找到当前最优的节点。
 * add操作必须在scan之后进行，以将节点加入到生成树中。
 * update操作必须在add之后进行，以更新邻接节点的最小距离。
 * <pre>
 * 		    8		7
 *  	 ①-----② ----- ③
 *   4/ | 	   2| \ 	|  \ 9
 *   /	|       |  \    |   \
 *  0	|11    ⑧   \7  |    ④
 *   \  |	  /|    \  |  /
 *  8 \ | 7/   |6    \ |/ 10
 *     ⑦ -----⑥ ---- ⑤
 *         2       2
 * </pre>
 * prim 从定点出发 优先选择所有边中的权值最小的边
 *
 * <pre>
 * 初始
 * 初始状态
 * selected: [false, false, false, false, false, false, false, false, false]
 * minDist: [0, ∞, ∞, ∞, ∞, ∞, ∞, ∞, ∞]
 * parent: [-1, -1, -1, -1, -1, -1, -1, -1, -1]
 *
 * 1（从节点0开始）
 * scan操作：
 * 查找未被选定的节点中最小距离的节点：节点0。
 * u = 0
 * add操作：
 * 标记节点0被选定。
 * selected: [true, false, false, false, false, false, false, false, false]
 * 当前没有连接任何边，因为这是起点
 * update操作：
 * 更新节点0的邻接节点的最小距离和前驱节点。
 * 更新节点1的距离为4，前驱节点为0。
 * 更新节点7的距离为8，前驱节点为0。
 * minDist: [0, 4, ∞, ∞, ∞, ∞, ∞, 8, ∞]
 * parent: [-1, 0, -1, -1, -1, -1, -1, 0, -1]
 * | 	 ①      ②       ③
 * |    /
 * |  /
 * | 0	         ⑧            ④
 * |
 * |
 * |    ⑦       ⑥      ⑤
 *
 * 2.
 * scan操作：
 * 查找未被选定的节点中最小距离的节点：节点1。
 * u = 1
 * add操作：
 * 标记节点1被选定。
 * selected: [true, true, false, false, false, false, false, false, false]
 * 连接边：0 - 1 (weight: 4)
 * update操作：
 * 更新节点1的邻接节点的最小距离和前驱节点。
 * 更新节点2的距离为8，前驱节点为1。
 * 更新节点7的距离为10，前驱节点为1（不更新，因为已有更小距离8）。
 * minDist: [0, 4, 8, ∞, ∞, ∞, ∞, 8, ∞]
 * parent: [-1, 0, 1, -1, -1, -1, -1, 0, -1]
 * | 	 ①      ②       ③
 * |    /
 * |  /
 * | 0	         ⑧            ④
 * |  \
 * |   \
 * |    ⑦       ⑥      ⑤
 *
 * 3.
 * scan操作：
 * 查找未被选定的节点中最小距离的节点：节点2。
 * u = 2
 * add操作：
 * 标记节点2被选定。
 * selected: [true, true, true, false, false, false, false, false, false]
 * 连接边：1 - 2 (weight: 8)
 * update操作：
 * 更新节点2的邻接节点的最小距离和前驱节点。
 * 更新节点8的距离为2，前驱节点为2。
 * 更新节点5的距离为7，前驱节点为2。
 * 更新节点3的距离为7，前驱节点为2。
 * minDist: [0, 4, 8, 7, ∞, 7, ∞, 8, 2]
 * parent: [-1, 0, 1, 2, -1, 2, -1, 0, 2]
 * | 	 ①      ②       ③
 * |    /
 * |  /
 * | 0	         ⑧            ④
 * |  \
 * |   \
 * |    ⑦ ----- ⑥      ⑤
 *
 * 4.
 * scan操作：
 * 查找未被选定的节点中最小距离的节点：节点8。
 * u = 8
 * add操作：
 * 标记节点8被选定。
 * selected: [true, true, true, false, false, false, false, false, true]
 * 连接边：2 - 8 (weight: 2)
 * update操作：
 * 更新节点8的邻接节点的最小距离和前驱节点。
 * 更新节点6的距离为6，前驱节点为8。
 * minDist: [0, 4, 8, 7, ∞, 7, 6, 8, 2]
 * parent: [-1, 0, 1, 2, -1, 2, 8, 0, 2]
 * | 	 ①      ②       ③
 * |    /
 * |  /
 * | 0	         ⑧            ④
 * |  \
 * |   \
 * |    ⑦ ----- ⑥ ----- ⑤
 *
 * 5.
 * scan操作：
 * 查找未被选定的节点中最小距离的节点：节点6。
 * u = 6
 * add操作：
 * 标记节点6被选定。
 * selected: [true, true, true, false, false, false, true, false, true]
 * 连接边：8 - 6 (weight: 6)
 * update操作：
 * 更新节点6的邻接节点的最小距离和前驱节点。
 * 更新节点7的距离为2，前驱节点为6。
 * 更新节点5的距离为2，前驱节点为6。
 * minDist: [0, 4, 8, 7, ∞, 2, 6, 2, 2]
 * parent: [-1, 0, 1, 2, -1, 6, 8, 6, 2]
 * scan操作，找到minDist[2],然后执行add操作
 * | 	 ①      ②       ③
 * |    /		   \
 * |  /             \
 * | 0	         ⑧  \         ④
 * |  \               \
 * |   \			   \
 * |    ⑦ ----- ⑥ -----⑤
 *
 * 6.
 * scan操作：
 * 查找未被选定的节点中最小距离的节点：节点5。
 * u = 5
 * add操作：
 * 标记节点5被选定。
 * selected: [true, true, true, false, false, true, true, false, true]
 * 连接边：6 - 5 (weight: 2)
 * update操作：
 * 更新节点5的邻接节点的最小距离和前驱节点。
 * 不更新，因为所有邻接节点已选定或已有更小距离。
 * minDist: [0, 4, 8, 7, ∞, 2, 6, 2, 2]
 * parent: [-1, 0, 1, 2, -1, 6, 8, 6, 2]
 * | 	 ①      ②       ③
 * |    /		 | \
 * |  /          |  \
 * | 0	         ⑧  \         ④
 * |  \               \
 * |   \			   \
 * |    ⑦ ----- ⑥ -----⑤
 *
 * 7.
 * scan操作：
 * 查找未被选定的节点中最小距离的节点：节点7。
 * u = 7
 * add操作：
 * 标记节点7被选定。
 * selected: [true, true, true, false, false, true, true, true, true]
 * 连接边：6 - 7 (weight: 2)
 * update操作：
 * 更新节点7的邻接节点的最小距离和前驱节点。
 * 不更新，因为所有邻接节点已选定或已有更小距离。
 * minDist: [0, 4, 8, 7, ∞, 2, 6, 2, 2]
 * parent: [-1, 0, 1, 2, -1, 6, 8, 6, 2]
 * | 	 ①      ② ----- ③
 * |    /		 | \
 * |  /          |  \
 * | 0	         ⑧  \         ④
 * |  \               \
 * |   \			   \
 * |    ⑦ ----- ⑥ -----⑤
 *
 *
 * 8.
 * scan操作：
 * 查找未被选定的节点中最小距离的节点：节点3。
 * u = 3
 * add操作：
 * 标记节点3被选定。
 * selected: [true, true, true, true, false, true, true, true, true]
 * 连接边：2 - 3 (weight: 7)
 * update操作：
 * 更新节点3的邻接节点的最小距离和前驱节点。
 * 更新节点4的距离为9，前驱节点为3。
 * minDist: [0, 4, 8, 7, 9, 2, 6, 2, 2]
 * parent: [-1, 0, 1, 2, 3, 6, 8, 6, 2]
 * | 	 ①      ② ----- ③
 * |    /		   \		\
 * |  /             \		 \
 * | 0	         ⑧  \        ④
 * |  \          |    \
 * |   \		 |	   \
 * |    ⑦ ----- ⑥ -----⑤
 *
 * 9
 * scan操作：
 * 查找未被选定的节点中最小距离的节点：节点4。
 * u = 4
 * add操作：
 * 标记节点4被选定。
 * selected: [true, true, true, true, true, true, true, true, true]
 * 连接边：3 - 4 (weight: 9)
 * update操作：
 * 更新节点4的邻接节点的最小距离和前驱节点。
 * 不更新，因为所有邻接节点已选定或已有更小距离。
 * minDist: [0, 4, 8, 7, 9, 2, 6, 2, 2]
 * parent: [-1, 0, 1, 2, 3, 6, 8, 6, 2]
 * </pre>
 */
public class Code05_Prim {

    public static class EdgeComparator implements Comparator<Edge> {

        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }

    }

    /**
     * prim就是 从开始的点 从多个边中找到最小的边到达邻接点从多个边中找到最小的边到达另一个邻接点
     * 不断循环
     */
    public static Set<Edge> primMST(Graph graph) {
        // 解锁的边进入小根堆 越小的边越在前面
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
        // 哪些点被解锁出来了
        HashSet<Node> nodeSet = new HashSet<>();
        //已经考虑过的边不用重复考虑
        HashSet<Edge> edgeSet = new HashSet<>();
        // 依次挑选的的边在result里
        Set<Edge> result = new HashSet<>();
        for (Node node : graph.nodes.values()) { // 随便挑了一个点 当做开始点 最外层的for循环只需做一次 没有什么意义
            // node 是开始点
            if (!nodeSet.contains(node)) {
                nodeSet.add(node);//因为开始点不在set中 所以放入
                for (Edge edge : node.edges) { // 由一个点，解锁所有与节点相连的边
                    if (!edgeSet.contains(edge)) {//已经考虑过的边不用重复考虑
                        edgeSet.add(edge);
                        priorityQueue.add(edge);
                    }
                }
                while (!priorityQueue.isEmpty()) {
                    // 弹出解锁的边中，也就是最小的边
                    Edge edge = priorityQueue.poll();
                    Node toNode = edge.to; // 得到边的另一个点 可能的一个新的点
                    // 不含有的时候，就是新的点 a--->b  判断b是不是已经解锁过了
                    if (!nodeSet.contains(toNode)) {
                        nodeSet.add(toNode);//表示这个边被解锁了 之后不能再用了
                        result.add(edge);//表示这个边已经被处理过 挑选出来了
                        /*
                         * 某一个点 从所有边中跳出最小的边 查看to 也就是边的另一端是哪一个邻接点
                         * 在查看这个邻接点是否已经解锁过了
                         * 如果没有解锁 这个边被挑选出来 这个邻接点也会被登记为被解锁过
                         * 并且这个邻接点重复上面的操作
                         * 也就是下面的for循环 直到结束
                         * */
                        for (Edge nextEdge : toNode.edges) {
                            if (!edgeSet.contains(edge)) {//已经考虑过的边不用重复考虑
                                edgeSet.add(edge);
                                priorityQueue.add(edge);
                            }
                            priorityQueue.add(nextEdge);
                        }
                    }
                }
            }
            // break;
        }
        return result;
    }

    // 请保证graph是连通图
    // graph[i][j]表示点i到点j的距离，如果是系统最大值代表无路
    // 返回值是最小连通图的路径之和
    public static int prim(int[][] graph) {
        int size = graph.length;
        int[] distances = new int[size];
        boolean[] visit = new boolean[size];
        visit[0] = true;
        for (int i = 0; i < size; i++) {
            distances[i] = graph[0][i];
        }
        int sum = 0;
        for (int i = 1; i < size; i++) {
            int minPath = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < size; j++) {
                if (!visit[j] && distances[j] < minPath) {
                    minPath = distances[j];
                    minIndex = j;
                }
            }
            if (minIndex == -1) {
                return sum;
            }
            visit[minIndex] = true;
            sum += minPath;
            for (int j = 0; j < size; j++) {
                if (!visit[j] && distances[j] > graph[minIndex][j]) {
                    distances[j] = graph[minIndex][j];
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println("hello world!");
    }

}
