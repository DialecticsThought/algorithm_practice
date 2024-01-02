package algorithmbasic2020_master.class16;

import java.util.*;

public class Code03_TopologySort {

	// directed graph and no loop
	public static List<Node> sortedTopology(Graph graph) {
		// key 某个节点node   value 这个node还剩余的入度
		HashMap<Node, Integer> inMap = new HashMap<>();
		// 只有剩余入度为0的点，才进入这个队列
		Queue<Node> zeroInQueue = new LinkedList<>();
		//graph.nodes是一个map 存的是 点的编号 和点本身
		for (Node node : graph.nodes.values()) {
			//初始化inMap
			inMap.put(node, node.in);
			//如果在初始化amp的过程中 有读入等于0的点 直接进入队列
			if (node.in == 0) {
				zeroInQueue.add(node);
			}
		}
		List<Node> result = new ArrayList<>();
		while (!zeroInQueue.isEmpty()) {
			//弹出队首
			Node cur = zeroInQueue.poll();
			result.add(cur);
			for (Node next : cur.nexts) {
				/*
				* 遍历到的当前节点 在被处理之前
				* 让当前节点的直接邻居的入度-1
				* eg： a--->b a要被处理了 b有一个入度是a的
				* 那么b的入度-1
				* */
				inMap.put(next, inMap.get(next) - 1);
				/*
				* 处理完之后查看 inMap是否有入度为0的点
				* 有的话加入到队列
				* */
				if (inMap.get(next) == 0) {
					zeroInQueue.add(next);
				}
			}
		}
		return result;
	}
}
