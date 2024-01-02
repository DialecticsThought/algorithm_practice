package algorithmbasic2020_master.class16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

// OJ链接：https://www.lintcode.com/problem/topological-sorting
public class Code03_TopologicalOrderDFS2 {

	// 不要提交这个类
	public static class DirectedGraphNode {
		public int label;//节点名
		public ArrayList<DirectedGraphNode> neighbors;//节点的邻居

		public DirectedGraphNode(int x) {
			label = x;
			neighbors = new ArrayList<DirectedGraphNode>();
		}
	}

	// 提交下面的
	public static class Record {
		public DirectedGraphNode node;
		public long nodes;

		public Record(DirectedGraphNode n, long o) {
			node = n;
			nodes = o;
		}
	}

	public static class MyComparator implements Comparator<Record> {

		@Override
		public int compare(Record o1, Record o2) {
			return o1.nodes == o2.nodes ? 0 : (o1.nodes > o2.nodes ? -1 : 1);
		}
	}

	public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
		HashMap<DirectedGraphNode, Record> order = new HashMap<>();
		for (DirectedGraphNode cur : graph) {//TODO 每一个点都计算点次
			f(cur, order);
		}
		ArrayList<Record> recordArr = new ArrayList<>();
		for (Record r : order.values()) {
			recordArr.add(r);
		}
		//TODO 谁的点次高 谁在前面
		recordArr.sort(new MyComparator());
		ArrayList<DirectedGraphNode> ans = new ArrayList<DirectedGraphNode>();
		for (Record r : recordArr) {
			ans.add(r.node);
		}
		return ans;
	}

	/*
	* 当前来到cur点，请返回cur点所到之处，所有的点次！
	* 返回（cur，点次）
	* 缓存！！！！！order
	* key : 某一个点的点次，之前算过了！
	* value : 点次是多少
	*/
	public static Record f(DirectedGraphNode cur, HashMap<DirectedGraphNode, Record> order) {
		if (order.containsKey(cur)) {//TODO 查询缓存
			return order.get(cur);
		}
		//TODO cur的点次之前没算过！
		long nodes = 0;
		// TODO 算出当前节点的点次 就是把从当前节点开始 的所有路径上的节点的点次通通累加
		for (DirectedGraphNode next : cur.neighbors) {
			nodes += f(next, order).nodes;
		}
		//TODO 生成结果 +1 是因为算上自己
		Record ans = new Record(cur, nodes + 1);
		//TODO 放入缓存
		order.put(cur, ans);
		//TODO 返回结果
		return ans;
	}

}
