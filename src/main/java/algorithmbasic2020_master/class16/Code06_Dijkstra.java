package algorithmbasic2020_master.class16;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

//

/**
 * TODO
 * no negative weight
 * 初始化：
 *  dist[] 用于存储从起始节点到其他所有节点的最短距离，初始值为 Integer.MAX_VALUE（表示不可达），但起点 dist[0] = 0。
 *  prev[] 用于存储前驱节点。
 *  visited 用于记录已经确定最短路径的节点。
 * 主循环：
 *  选择当前未访问的距离最小的节点 u。
 *  更新与节点 u 相邻的节点 v 的距离，如果找到更短的路径，则更新 dist[v] 和 prev[v]。
 * 输出结果：
 *  输出从起始节点到每个节点的最短距离。
 *  输出每个节点的最短路径，通过前驱节点数组 prev[] 重建路径。
 */
public class Code06_Dijkstra {
    /**
     * <pre>
     *                8
     *          ② -------------> ⑤
     *     2  ↗   ↘ 6         ↗     ↘  3
     *     ↗         ↘     ↗ 2         ↘
     *   0              ③                ⑥
     *     ↘       1 ↗    ↘ 1          ↗
     *    5   ↘   ↗          ↘      ↗  7
     *          ① -------------> ④
     *                 6
     * </pre>
     * <pre>
     * 初始化：
     * 距离数组 dist[] 用于存储从起始节点到其他所有节点的最短距离，初始值设置为无穷大（表示不可达），但起点 dist[0] = 0。
     * 前驱节点数组 prev[] 初始化为 null。
     * 已访问节点集合 visited 初始化为空集。
     * 初始化状态：
     * dist = [0, ∞, ∞, ∞, ∞, ∞, ∞]
     * prev = [null, null, null, null, null, null, null]
     * visited = {}
     *
     * 步骤1：选择未访问的最小距离节点
     * 选择节点0，标记为已访问。
     * 更新与节点0相邻的节点：
     * dist[1] 更新为 0 + 5 = 5，prev[1] 设置为0。
     * dist[2] 更新为 0 + 2 = 2，prev[2] 设置为0。
     * 更新后的状态：
     * dist = [0, 5, 2, ∞, ∞, ∞, ∞]
     * prev = [null, 0, 0, null, null, null, null]
     * visited = {0}
     * 当前路径链：
     * 到节点1的路径：0 -> 1
     * 到节点2的路径：0 -> 2
     *
     * 步骤2：选择未访问的最小距离节点
     * 选择节点2（dist[2] = 2），标记为已访问。
     * 更新与节点2相邻的节点：
     * dist[5] 更新为 2 + 8 = 10，prev[5] 设置为2。
     * dist[3] 更新为 2 + 6 = 8，prev[3] 设置为2。
     * 更新后的状态：
     * dist = [0, 5, 2, 8, ∞, 10, ∞]
     * prev = [null, 0, 0, 2, null, 2, null]
     * visited = {0, 2}
     * 当前路径链：
     * 到节点5的路径：0 -> 2 -> 5
     * 到节点3的路径：0 -> 2 -> 3
     *
     * 步骤3：选择未访问的最小距离节点
     * 选择节点1（dist[1] = 5），标记为已访问。
     * 更新与节点1相邻的节点：
     * dist[3] 更新为 5 + 1 = 6（因为6小于8，需要更新），prev[3] 设置为1。
     * dist[4] 更新为 5 + 6 = 11，prev[4] 设置为1。
     * 更新后的状态：
     * dist = [0, 5, 2, 6, 11, 10, ∞]
     * prev = [null, 0, 0, 1, 1, 2, null]
     * visited = {0, 1, 2}
     * 当前路径链：
     * 到节点3的路径：0 -> 1 -> 3
     * 到节点4的路径：0 -> 1 -> 4
     *
     * 步骤4：选择未访问的最小距离节点
     * 选择节点3（dist[3] = 6），标记为已访问。
     * 更新与节点3相邻的节点：
     * dist[4] 更新为 6 + 1 = 7（因为7小于11，需要更新），prev[4] 设置为3。
     * dist[5] 更新为 6 + 2 = 8（因为8小于10，需要更新），prev[5] 设置为3。
     * 更新后的状态：
     * dist = [0, 5, 2, 6, 7, 8, ∞]
     * prev = [null, 0, 0, 1, 3, 3, null]
     * visited = {0, 1, 2, 3}
     * 当前路径链：
     * 到节点4的路径：0 -> 1 -> 3 -> 4
     * 到节点5的路径：0 -> 1 -> 3 -> 5
     *
     * 步骤5：选择未访问的最小距离节点
     * 选择节点4（dist[4] = 7），标记为已访问。
     * 更新与节点4相邻的节点：
     * dist[6] 更新为 7 + 7 = 14，prev[6] 设置为4。
     * 更新后的状态：
     * dist = [0, 5, 2, 6, 7, 8, 14]
     * prev = [null, 0, 0, 1, 3, 3, 4]
     * visited = {0, 1, 2, 3, 4}
     * 当前路径链：
     * 到节点6的路径：0 -> 1 -> 3 -> 4 -> 6
     *
     * 步骤6：选择未访问的最小距离节点
     * 选择节点5（dist[5] = 8），标记为已访问。
     * 更新与节点5相邻的节点：
     * dist[6] 更新为 8 + 3 = 11（因为11小于14，需要更新），prev[6] 设置为5。
     * 更新后的状态：
     * dist = [0, 5, 2, 6, 7, 8, 11]
     * prev = [null, 0, 0, 1, 3, 3, 5]
     * visited = {0, 1, 2, 3, 4, 5}
     * 当前路径链：
     * 到节点6的路径：0 -> 1 -> 3 -> 5 -> 6
     *
     * 步骤7：选择未访问的最小距离节点
     * 选择节点6（dist[6] = 11），标记为已访问。
     * 最终状态：
     * dist = [0, 5, 2, 6, 7, 8, 11]
     * prev = [null, 0, 0, 1, 3, 3, 5]
     * visited = {0, 1, 2, 3, 4, 5, 6}
     * 最短路径：
     * 到节点0的路径：0
     * 到节点1的路径：0 -> 1
     * 到节点2的路径：0 -> 2
     * 到节点3的路径：0 -> 1 -> 3
     * 到节点4的路径：0 -> 1 -> 3 -> 4
     * 到节点5的路径：0 -> 1 -> 3 -> 5
     * 到节点6的路径：0 -> 1 -> 3 -> 5 -> 6
     * </pre>
     *
     * @param from
     * @return
     */
    public static HashMap<Node, Integer> dijkstra1(Node from) {
        //记录A到所有的距离 eg A到D的距离 <D,distance> 这是针对A的map
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        distanceMap.put(from, 0);
        // 打过对号的点 表示这些点已经使用过了 不能再用了 被锁住了
        HashSet<Node> selectedNodes = new HashSet<>();
        Node minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        while (minNode != null) {
            /**
             * 原始点  ->  minNode(跳转点)   最小距离distance
             * 根据 得到 与 没有被选择过的点的距离 中 的 最小距离 的点 minNode
             * 可以找到 from 与 minNode 之间 的边
             */
            int distance = distanceMap.get(minNode);
            //遍历 从当前minNode有关联的其他边
            for (Edge edge : minNode.edges) {
                //eg ： D是minNode 有一条边是 D--->E  那么 E就是toNode
                Node toNode = edge.to;
                /**
                 * distanceMap中没有找到与toNode有关的记录
                 * 说明出发点from 到 toNode 的距离还是正无穷 还没有有被找到
                 * 那么记录到Map中 也就是 from 到 minNode距离 + minNode到toNode的距离
                 * eg: from 是 A minNode 是 D  minNode可以到达 E
                 * 那么 A到E的距离 就是 A--->D--->E D是中间点
                 * */
                if (!distanceMap.containsKey(toNode)) {
                    distanceMap.put(toNode, distance + edge.weight);
                } else { // 说明 之前 已经记录过 from 到 toNode 距离了
                    /**
                     * 老的距离 distanceMap.get(toNode) 和现在的距离（from 到 minNode距离 + minNode到toNode的距离）相比较
                     *  新的小 就更新
                     * */
                    distanceMap.put(edge.to, Math.min(distanceMap.get(toNode), distance + edge.weight));
                }
            }
            selectedNodes.add(minNode);//让当前的minNode标记为使用过 不能之后在被使用
            /**
             * 再从 没有被选择过的点的距离 中 得到 最小距离 的 点
             * */
            minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        }
        return distanceMap;
    }

    /**
     * 得到 与 没有被选择过的点的距离 中 的 最小距离 的点
     */
    public static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> touchedNodes) {
        Node minNode = null;//创建一个指针
        int minDistance = Integer.MAX_VALUE;//设为最大值表示不可达 后续调用的时候会更新
        for (Entry<Node, Integer> entry : distanceMap.entrySet()) {//遍历distanceMap
            Node node = entry.getKey();
            int distance = entry.getValue();
            /*
             * 确保node没有被使用过 并且 当前的距离 小于 之前所记录的最小距离
             * */
            if (!touchedNodes.contains(node) && distance < minDistance) {
                minNode = node;//让指针指向 现在遍历到的符合条件的节点
                minDistance = distance;//更新最小距离
            }
        }
        return minNode;
    }

    /*
     * 一条记录 源节点到某一个节点node的最小距离distance
     * */
    public static class NodeRecord {
        public Node node;
        public int distance;

        public NodeRecord(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    //小根堆

    /**
     * 如果from 到 某一个点 距离发生改变 并且变成了小根堆的最小
     * 那么小根堆就要把这条记录移动到栈顶
     */
    public static class NodeHeap {
        private Node[] nodes; // 实际的堆结构
        // key 某一个node， value 上面堆中的位置
        //eg: B 2  表示B在堆的2位置
        // B，-1 表示B曾经在堆里面 但是现在不在堆里面（这里只有一种可能性 就是进堆就弹出）
        private HashMap<Node, Integer> heapIndexMap;
        // key 某一个节点， value 从源节点出发到该节点的目前最小距离
        private HashMap<Node, Integer> distanceMap;
        private int size; // 堆上有多少个点

        public NodeHeap(int size) {
            nodes = new Node[size];
            heapIndexMap = new HashMap<>();
            distanceMap = new HashMap<>();
            size = 0;
        }

        /*
         * 接受的节点数量是0 就是空
         * */
        public boolean isEmpty() {
            return size == 0;
        }

        // 有一个点叫node，现在发现了一个从源节点出发到达node的距离为distance
        // 判断要不要更新，如果需要的话，就更新
        public void addOrUpdateOrIgnore(Node node, int distance) {
            /**
             * 如果这个节点 在heapIndexMap中有相应的记录 说明之前更新过最小距离
             * 拿过去更新过的最小距离和 现在的距离 作比较  两者中取最小的 作为最新的最小的距离
             * */
            if (inHeap(node)) {
                distanceMap.put(node, Math.min(distanceMap.get(node), distance));
                insertHeapify(node, heapIndexMap.get(node));
            }
            if (!isEntered(node)) {
                nodes[size] = node;
                heapIndexMap.put(node, size);
                distanceMap.put(node, distance);
                insertHeapify(node, size++);
            }
        }

        /**
         * 从小根堆弹出栈顶 也就是
         */
        public NodeRecord pop() {
            NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
            swap(0, size - 1);
            heapIndexMap.put(nodes[size - 1], -1);
            distanceMap.remove(nodes[size - 1]);
            // free C++同学还要把原本堆顶节点析构，对java同学不必
            nodes[size - 1] = null;
            heapify(0, --size);
            return nodeRecord;
        }

        private void insertHeapify(Node node, int index) {
            while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        private void heapify(int index, int size) {
            int left = index * 2 + 1;
            while (left < size) {
                int smallest = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left])
                        ? left + 1
                        : left;
                smallest = distanceMap.get(nodes[smallest]) < distanceMap.get(nodes[index]) ? smallest : index;
                if (smallest == index) {
                    break;
                }
                swap(smallest, index);
                index = smallest;
                left = index * 2 + 1;
            }
        }

        /**
         * 怎么判断这个节点之前进来过
         * 只要heapIndexMap（key 某一个node， value 上面堆中的位置）有一个node为key的记录
         */
        private boolean isEntered(Node node) {
            return heapIndexMap.containsKey(node);
        }

        /**
         * 只要这个节点进来 并且没有出去（就是等于-1） 算在堆里面
         */
        private boolean inHeap(Node node) {
            return isEntered(node) && heapIndexMap.get(node) != -1;
        }

        private void swap(int index1, int index2) {
            heapIndexMap.put(nodes[index1], index2);
            heapIndexMap.put(nodes[index2], index1);
            Node tmp = nodes[index1];
            nodes[index1] = nodes[index2];
            nodes[index2] = tmp;
        }
    }

    // 改进后的dijkstra算法
    // 从head出发，所有head能到达的节点，生成到达每个节点的最小路径记录并返回
    public static HashMap<Node, Integer> dijkstra2(Node head, int size) {
        NodeHeap nodeHeap = new NodeHeap(size);
        /*
         * z这个方法 能自动判断 是否需要加入/更新/忽略这条记录
         * 一开始这条记录 源节点到源节点的距离是0
         * */
        nodeHeap.addOrUpdateOrIgnore(head, 0);
        HashMap<Node, Integer> result = new HashMap<>();
        while (!nodeHeap.isEmpty()) {
            //从小根堆里面弹出一条记录 这条记录 一定距离最小的
            NodeRecord record = nodeHeap.pop();
            Node cur = record.node;
            int distance = record.distance;
            for (Edge edge : cur.edges) {
                nodeHeap.addOrUpdateOrIgnore(edge.to, edge.weight + distance);
            }
            result.put(cur, distance);
        }
        return result;
    }

}
