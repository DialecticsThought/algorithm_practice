package algorithmbasic2020_master.class17;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

// no negative weight
public class Code01_Dijkstra {
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
	 * @param from
	 * @return
	 */
	public static HashMap<Node, Integer> dijkstra1(Node from) {
		HashMap<Node, Integer> distanceMap = new HashMap<>();
		distanceMap.put(from, 0);
		// 打过对号的点
		HashSet<Node> selectedNodes = new HashSet<>();
		Node minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
		while (minNode != null) {
			// 原始点 -> minNode(跳转点) 最小距离distance
			int distance = distanceMap.get(minNode);
			for (Edge edge : minNode.edges) {
				Node toNode = edge.to;
				if (!distanceMap.containsKey(toNode)) {
					distanceMap.put(toNode, distance + edge.weight);
				} else { // toNode
					distanceMap.put(edge.to, Math.min(distanceMap.get(toNode), distance + edge.weight));
				}
			}
			selectedNodes.add(minNode);
			minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
		}
		return distanceMap;
	}

	public static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> touchedNodes) {
		Node minNode = null;
		int minDistance = Integer.MAX_VALUE;
		for (Entry<Node, Integer> entry : distanceMap.entrySet()) {
			Node node = entry.getKey();
			int distance = entry.getValue();
			if (!touchedNodes.contains(node) && distance < minDistance) {
				minNode = node;
				minDistance = distance;
			}
		}
		return minNode;
	}

	public static class NodeRecord {
		public Node node;
		public int distance;

		public NodeRecord(Node node, int distance) {
			this.node = node;
			this.distance = distance;
		}
	}

	public static class NodeHeap {
		// 堆！
		private Node[] nodes;
		// node -> 堆上的什么位置？

		private HashMap<Node, Integer> heapIndexMap;
		private HashMap<Node, Integer> distanceMap;
		private int size;

		public NodeHeap(int size) {
			nodes = new Node[size];
			heapIndexMap = new HashMap<>();
			distanceMap = new HashMap<>();
			size = 0;
		}

		public boolean isEmpty() {
			return size == 0;
		}

		// 有一个点叫node，现在发现了一个从源节点出发到达node的距离为distance
		// 判断要不要更新，如果需要的话，就更新
		public void addOrUpdateOrIgnore(Node node, int distance) {
			if (inHeap(node)) { // update
				distanceMap.put(node, Math.min(distanceMap.get(node), distance));
				insertHeapify(node, heapIndexMap.get(node));
			}
			if (!isEntered(node)) { // add
				nodes[size] = node;
				heapIndexMap.put(node, size);
				distanceMap.put(node, distance);
				insertHeapify(node, size++);
			}
			// ignore
		}

		public NodeRecord pop() {
			NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
			swap(0, size - 1); // 0 > size - 1    size - 1 > 0
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

		private boolean isEntered(Node node) {
			return heapIndexMap.containsKey(node);
		}

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
		nodeHeap.addOrUpdateOrIgnore(head, 0);
		HashMap<Node, Integer> result = new HashMap<>();
		while (!nodeHeap.isEmpty()) {
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
