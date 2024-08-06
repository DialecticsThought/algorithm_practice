package algorithmbasic2020_master.class16;

import java.util.*;

/**
 * TODO undirected graph only
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
 * <pre>
 * 有张表 记录所有的边和对应的权重 并按照权重从小到大排序
 * <6 -> 7,2> <2 -> 8,2>  <5 -> 6,2>
 * <0 -> 1,4> <2 -> 5,7> <6 -> 8,6>
 * <7 -> 8,7>  <2 -> 3,7> <0 -> 7,8>
 * <1 -> 2,8>  <3 -> 4,9>  <4 -> 5,10>
 * <1 -> 7,10> <3 -> 5,14>
 * 每一次取出一条边 填回图中 填入之后，判断有无环路(用并查集)
 * 没有形成环 说明这是最小生成树的一条边
 * 有形成环，不能把这条边加入进去
 * </pre>
 * <pre>
 * 初始
 * | 	 ①      ②       ③
 * |
 * |
 * | 0	         ⑧            ④
 * |
 * |
 * |    ⑦       ⑥      ⑤
 * edge=1
 * | 	 ①      ②       ③
 * |
 * |
 * | 0	         ⑧            ④
 * |
 * |
 * |    ⑦ ----- ⑥      ⑤
 * edge=2
 * | 	 ①      ②       ③
 * |             |
 * |             |
 * | 0	         ⑧            ④
 * |
 * |
 * |    ⑦ ----- ⑥      ⑤
 * edge=3
 * | 	 ①      ②       ③
 * |             |
 * |             |
 * | 0	         ⑧            ④
 * |
 * |
 * |    ⑦ ----- ⑥----- ⑤
 * edge=3
 * | 	 ①      ②       ③
 * |    /        |
 * |  /          |
 * | 0	         ⑧            ④
 * |
 * |
 * |    ⑦ ----- ⑥----- ⑤
 * edge=4
 * | 	 ①      ②       ③
 * |    /        | \
 * |  /          |  \
 * | 0	         ⑧  \          ④
 * |                  \
 * |                   \
 * |    ⑦ ----- ⑥----- ⑤
 * edge=5
 * | 	 ①      ②       ③
 * |    /        | \
 * |  /          |  \
 * | 0	         ⑧  \       ④
 * |             |    \
 * |             |     \
 * |    ⑦ ----- ⑥----- ⑤
 * 形成环路，回退
 * edge=5
 * | 	 ①      ②       ③
 * |    /        | \
 * |  /          |  \
 * | 0	         ⑧  \       ④
 * |          /       \
 * |        /          \
 * |     ⑦----- ⑥----- ⑤
 * 形成环路，回退
 * edge=5
 * | 	 ①      ② -----③
 * |    /        | \
 * |  /          |  \
 * | 0	         ⑧  \       ④
 * |                  \
 * |                   \
 * |     ⑦----- ⑥----- ⑤
 * edge=6
 * | 	 ①      ② -----③
 * |    /        | \
 * |  /          |  \
 * | 0	         ⑧  \       ④
 * |  \               \
 * |   \               \
 * |    ⑦----- ⑥----- ⑤
 * edge=5
 * | 	 ① ----- ② -----③
 * |    /        | \
 * |  /          |  \
 * | 0	         ⑧  \       ④
 * |  \               \
 * |   \               \
 * |    ⑦----- ⑥----- ⑤
 * 形成环路，回退
 * edge=6
 * | 	 ①      ② -----③
 * |    /        | \      \
 * |  /          |  \      \
 * | 0	         ⑧  \      ④
 * |  \               \
 * |   \               \
 * |    ⑦----- ⑥----- ⑤
 * 此时 节点数 = 边数+1 ,end
 * </pre>
 */
public class Code04_Kruskal {

    // Union-Find Set
    public static class UnionFind {
        // key: 某一个节点， value: key节点往上的节点
        private HashMap<Node, Node> fatherMap;
        // key: 某一个集合的代表节点, value: key所在集合的节点个数
        private HashMap<Node, Integer> sizeMap;

        public UnionFind() {
            fatherMap = new HashMap<Node, Node>();
            sizeMap = new HashMap<Node, Integer>();
        }

        public void makeSets(Collection<Node> nodes) {
            fatherMap.clear();
            sizeMap.clear();
            for (Node node : nodes) {
                //因为一开始 每个样本所在的集合里面只有样本自己一个 所以 node的parent是node本身
                fatherMap.put(node, node);
                //每个点一开始都是代表点
                sizeMap.put(node, 1);
            }
        }

        // 给你一个节点value，请你从value对应的node往上到不能再往上，把代表点返回
        private Node findFather(Node n) {
            Stack<Node> path = new Stack<>();
            /*
             * 如果cur节点不等于自己的父节点 说明还能向上找
             * */
            while (n != fatherMap.get(n)) {
                path.add(n);//在找到最顶端的父节点的过程中，把每一个遍历到的cur都存到栈里面
                n = fatherMap.get(n);//让cur的父节点代替掉cur 属于一种递归
            }
            /*
             * 上衣循环结束的时候 cur一定指向最顶端的父节点
             * */
            while (!path.isEmpty()) {
                /*
                 * eg x指向a b指向b b指向c  c指向d d指向d自己 d是代表点
                 * parent这个map中存放 <x,a> <a,b> <b,c> <c,d> <d,d>
                 * 最终在这个while循环中变成 <x,d> <a,d> <b,d> <c,d>  实现扁平化
                 * */
                fatherMap.put(path.pop(), n);
            }
            return n;
        }

        /*
         * 只要a和b的代表点是相同 就是同一个集合
         * */
        public boolean isSameSet(Node a, Node b) {
            return findFather(a) == findFather(b);
        }

        public void union(Node a, Node b) {
            if (a == null || b == null) {
                return;
            }
            Node aNode = findFather(a);
            Node bNode = findFather(b);
            /*
             * 表示如果a的代表点和b的代表点都是同一个的话 就是一个集合
             * 只有不同才要union
             * */
            if (aNode != bNode) {
                /*
                 * 得到 a和b 代表点所对应的集合的大小
                 * */
                int aSetSize = sizeMap.get(aNode);
                int bSetSize = sizeMap.get(bNode);
                if (aSetSize <= bSetSize) {
                    fatherMap.put(aNode, bNode);//表示aNode的父节点（代表点）是b节点
                    sizeMap.put(bNode, aSetSize + bSetSize);//修改所对应的集合大小
                    sizeMap.remove(aNode);//删除掉a节点对应的大小 因为a不再是代表点了
                } else {
                    fatherMap.put(bNode, aNode);//表示bNode的父节点（代表点）是a节点
                    sizeMap.put(aNode, aSetSize + bSetSize);//修改所对应的集合大小
                    sizeMap.remove(bNode);//删除掉b节点对应的大小 因为a不再是代表点了
                }
            }
        }
    }

    /*
     * 比较器
     * */
    public static class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }

    }

    public static Set<Edge> kruskalMST(Graph graph) {
        UnionFind unionFind = new UnionFind();
        unionFind.makeSets(graph.nodes.values());
        // 因为 kruskal 算法是针对 权值最小的边
        // 从小的边到大的边，依次弹出，小根堆！ 小根堆的实现就是队列+比较器 权重晓得边在堆顶
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
        for (Edge edge : graph.edges) { // M 条边
            priorityQueue.add(edge);  // O(logM)
        }
        Set<Edge> result = new HashSet<>();//创建一个结果集 表示已经处理多少个边
        while (!priorityQueue.isEmpty()) { // M 条边
            Edge edge = priorityQueue.poll(); // O(logM)  每次从队列弹出队首 也就是最小的边
            //查看边的两个节点是否属于同一个集合
            if (!unionFind.isSameSet(edge.from, edge.to)) { // O(1)
                //边的两个节点不属于同一个集合
                result.add(edge);//边放进结果集
                //把两个节点对应的集合合并
                unionFind.union(edge.from, edge.to);
            }
        }
        return result;
    }
}
