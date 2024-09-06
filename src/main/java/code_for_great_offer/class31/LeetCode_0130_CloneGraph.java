package code_for_great_offer.class31;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class LeetCode_0130_CloneGraph {

	public static class Node {
		int val;
		List<Node> neighbors;

		Node(int x) {
			val = x;
			neighbors = new ArrayList<Node>();
		}
	}

	// bfs
	public static Node cloneGraph(Node node) {
		if (node == null) {
			return null;
		}
		//克隆出来一份
		Node head = new Node(node.val);
		LinkedList<Node> queue = new LinkedList<>();
		// key：克隆节点对应的原节点  value：克隆节点
		//克隆过的节点 进入队列 没克隆过的 是没有进入过队列
		HashMap<Node, Node> map = new HashMap<>();
		queue.add(node);//原节点放入到队列
		map.put(node, head);//原节点 克隆节点放入到map中
		//这里类似宽度优先遍历
		while (!queue.isEmpty()) {
			//cur是原节点
			Node cur = queue.poll();
			//cur.neighbor 原节点的邻居 拿出来 拷贝  并且 按照 key：克隆节点对应的原节点  value：克隆节点 放入map
			for (Node next : cur.neighbors) {
				if (!map.containsKey(next)) {//防止宽度优先的过程重复放入队列
					Node copy = new Node(next.val);
					map.put(next, copy);
					//把原节点放入到队列 继续宽度优先
					queue.add(next);
				}
			}
		}
		for (Entry<Node, Node> entry : map.entrySet()) {
			Node cur = entry.getKey();//原节点
			Node copy = entry.getValue();//对应的克隆节点
			for (Node next : cur.neighbors) {
				copy.neighbors.add(map.get(next));//设置克隆节点的邻居
			}
		}
		return head;//返回新结构的头部
	}

	// dfs深度优先的方式赋值
	public Node cloneGraph2(Node node) {
		HashMap<Integer, Node> map = new HashMap<>();
		return clone(node, map);
	}

	private Node clone(Node node, HashMap<Integer, Node> map) {
		if (node == null)
			return null;

		if (map.containsKey(node.val)) {
			return map.get(node.val);
		}
		Node clone = new Node(node.val);
		map.put(clone.val, clone);
		for (Node neighbor : node.neighbors) {
			clone.neighbors.add(clone(neighbor, map));
		}
		return clone;
	}

}
