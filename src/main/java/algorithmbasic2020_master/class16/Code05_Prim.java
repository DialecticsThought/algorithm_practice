package algorithmbasic2020_master.class16;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *TODO undirected graph only
 * 1）可以从任意节点出发来寻找最小生成树
 * 2）某个点加入到被选取的点中后，解锁这个点出发的所有新的边
 * 3）在所有解锁的边中选最小的边，然后看看这个边会不会形成环
 * 4）如果会，不要当前边，继续考察剩下解锁的边中最小的边，重复3)
 * 5）如果不会，要当前边，将该边的指向点加入到被选取的点中，重复2)
 * 6)当所有点都被选取，最小生成树就得到了
 * 3个操作
 * update：到一个新的节点 更新与该点相连的边和点的信息
 * scan：扫描minDict表，找到最小值对应的节点
 * add：添加scan找到的顶点，到一个已选的集合里面，并且根据最小生成的信息创建一条边
 * <pre>
 * 		    8		7
 *  	 ①-----② ----- ③
 *   4/ | 	   2| \ 	|  \ 9
 *   /	 |      |  \    |   \
 *  0	 |11   ⑧   \4  |    ④
 *   \  |	  / |   \   |  /
 *  8 \ | 7/	|    \  |/ 10
 *     ⑦ -----⑥ ---- ⑤
 *         1       2
 * </pre>
 * <pre>
 * prim 从定点出发 优先选择所有边中的权值最小的边
 * 有2个集合：
 * 一个是已选定点的集合，就是确定路径最小生成树的方式的那些定点集合
 * 一个是未确定最小生成树方式的顶点的集合
 * 有1张表selected：记录顶点是否被选
 * F F F F F F F F F
 * 0 1 2 3 4 5 6 7 8
 * </pre>
 * <pre>
 * 初始
 * 出发点是0 ★★★★★★★★★★★★
 * 			0 	1	2 	3 	4 	5 	6 	7 	8
 * selected T 	F 	F 	F 	F 	F 	F 	F 	F
 * minDist	-  max max max max max max max max
 * parent	-1	-1 -1  -1  -1  -1  -1  -1  -1
 * 1.
 * 0->4,4<max,执行update 0->7,8<max 执行update
 * 			0 	1	2 	3 	4 	5 	6 	7 	8
 * selected T 	F 	F 	F 	F 	F 	F 	F 	F
 * minDist	-   4  max max max max max  8 max
 * parent	-1	0  -1  -1  -1  -1  -1   0  -1
 * scan操作，找到minDist[1],然后执行add操作
 * | 	 ①      ②       ③
 * |    /
 * |  /
 * | 0	         ⑧            ④
 * |
 * |
 * |    ⑦       ⑥      ⑤
 * 			0 	1	2 	3 	4 	5 	6 	7 	8
 * selected T 	T 	F 	F 	F 	F 	F 	F 	F
 * minDist	-   -  max max max max max  8 max
 * parent	-1	0  -1  -1  -1  -1  -1   0  -1
 *
 * 2.
 * 1->2,8<max,执行update 1->7,11>8 不执行update
 * 			0 	1	2 	3 	4 	5 	6 	7 	8
 * selected T 	T 	F 	F 	F 	F 	F 	F 	F
 * minDist	-   -   8 max max max max   8 max
 * parent	-1	0   1  -1  -1  -1  -1   0  -1
 * scan操作，找到minDist[7],然后执行add操作
 * | 	 ①      ②       ③
 * |    /
 * |  /
 * | 0	         ⑧            ④
 * |  \
 * |   \
 * |    ⑦       ⑥      ⑤
 * 			0 	1	2 	3 	4 	5 	6 	7 	8
 * selected T 	T 	T 	F 	F 	F 	F 	T 	F
 * minDist	-   -   8 max max max max   - max
 * parent	-1	0  1  -1  -1  -1  -1    0  -1
 *
 * 3.
 * 7->6,1<max,执行update 7->8,7<max 不执行update
 * 			0 	1	2 	3 	4 	5 	6 	7 	8
 * selected T 	T 	F 	F 	F 	F 	F 	T 	F
 * minDist	-   -   8 max max max   1   -   7
 * parent	-1	0   1  -1  -1  -1   7   0   7
 * scan操作，找到minDist[6](随便选),然后执行add操作
 * | 	 ①      ②       ③
 * |    /
 * |  /
 * | 0	         ⑧            ④
 * |  \
 * |   \
 * |    ⑦ ----- ⑥      ⑤
 * 			0 	1	2 	3 	4 	5 	6 	7 	8
 * selected T 	T 	F 	F 	F 	F 	T 	T 	F
 * minDist	-   -   8 max max max   -   -   7
 * parent	-1	0   1  -1  -1  -1   7   0   7
 *
 * 4.
 * 6->5,2<max,执行update 6->8,6<7 执行update
 * 			0 	1	2 	3 	4 	5 	6 	7 	8
 * selected T 	T 	F 	F 	F 	F 	T 	T 	F
 * minDist	-   -   8 max max   2   -   -   6
 * parent	-1	0   1  -1  -1  -1   7   0   6
 * scan操作，找到minDist[5],然后执行add操作
 * | 	 ①      ②       ③
 * |    /
 * |  /
 * | 0	         ⑧            ④
 * |  \
 * |   \
 * |    ⑦ ----- ⑥ ----- ⑤
 * 			0 	1	2 	3 	4 	5 	6 	7 	8
 * selected T 	T 	F 	F 	F 	T 	T 	T 	F
 * minDist	-   -   8 max max   -   -   -   6
 * parent	-1	0   1  -1  -1  -1   7   0   6
 * 5.
 * 5->2,4<8,执行update 5->3,14<max 执行update  5->4,10<max 执行update
 * 			0 	1	2 	3 	4 	5 	6 	7 	8
 * selected T 	T 	F 	F 	F 	T 	T 	T 	F
 * minDist	-   -   4  14  10   -   -   -   6
 * parent	-1	0   5   5   5  -1   7   0   6
 * scan操作，找到minDist[2],然后执行add操作
 * | 	 ①      ②       ③
 * |    /		   \
 * |  /             \
 * | 0	         ⑧  \         ④
 * |  \               \
 * |   \			   \
 * |    ⑦ ----- ⑥ -----⑤
 * 			0 	1	2 	3 	4 	5 	6 	7 	8
 * selected T 	T 	T 	F 	F 	T 	T 	T 	F
 * minDist	-   -   -  14  10   -   -   -   6
 * parent	-1	0   5   5   5  -1   7   0   6
 * 6.
 * 2->8,2<6,执行update
 * 			0 	1	2 	3 	4 	5 	6 	7 	8
 * selected T 	T 	T 	F 	F 	T 	T 	T 	F
 * minDist	-   -   -   7  10   -   -   -   2
 * parent	-1	0   5   2   5  -1   7   0   2
 * scan操作，找到minDist[8],然后执行add操作
 * | 	 ①      ②       ③
 * |    /		 | \
 * |  /          |  \
 * | 0	         ⑧  \         ④
 * |  \               \
 * |   \			   \
 * |    ⑦ ----- ⑥ -----⑤
 * 			0 	1	2 	3 	4 	5 	6 	7 	8
 * selected T 	T 	T 	F 	F 	T 	T 	T 	T
 * minDist	-   -   -   7  10   -   -   -   -
 * parent	-1	0   5   2   5  -1   7   0   2
 * 7.
 * 			0 	1	2 	3 	4 	5 	6 	7 	8
 * selected T 	T 	T 	F 	F 	T 	T 	T 	T
 * minDist	-   -   -   7  10   -   -   -   -
 * parent	-1	0   5   2   5  -1   7   0   2
 * scan操作，找到minDist[3],然后执行add操作
 * | 	 ①      ② ----- ③
 * |    /		 | \
 * |  /          |  \
 * | 0	         ⑧  \         ④
 * |  \               \
 * |   \			   \
 * |    ⑦ ----- ⑥ -----⑤
 * 			0 	1	2 	3 	4 	5 	6 	7 	8
 * selected T 	T 	T 	T 	F 	T 	T 	T 	T
 * minDist	-   -   -   -  10   -   -   -   -
 * parent	-1	0   5   2   5  -1   7   0   6
 * 8.
 * 没有更新的了
 * 			0 	1	2 	3 	4 	5 	6 	7 	8
 * selected T 	T 	T 	T 	F 	T 	T 	T 	T
 * minDist	-   -   -   -  10   -   -   -   -
 * parent	-1	0   5   2   5  -1   7   0   6
 * scan操作，找到minDist[4],然后执行add操作
 * | 	 ①      ② ----- ③
 * |    /		   \		\
 * |  /             \		 \
 * | 0	         ⑧  \        ④
 * |  \          |    \
 * |   \		 |	   \
 * |    ⑦ ----- ⑥ -----⑤
 * 			0 	1	2 	3 	4 	5 	6 	7 	8
 * selected T 	T 	T 	T 	T 	T 	T 	T 	T
 * minDist	-   -   -   -   -   -   -   -   -
 * parent	-1	0   5   2   5  -1   7   0   6
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
