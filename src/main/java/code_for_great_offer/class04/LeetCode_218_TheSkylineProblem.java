package code_for_great_offer.class04;

import java.util.*;
import java.util.Map.Entry;

// 本题测试链接 : https://leetcode.com/problems/the-skyline-problem/
public class LeetCode_218_TheSkylineProblem {
	/**
	 * Node 类表示建筑物的一个边界（左或右）。
	 */
	public static class Node {
		//x 是边界的 x-坐标。
		public int x;
		//isAdd 表明这个节点是开始点（true）还是结束点（false）。
		public boolean isAdd;
		//h 是建筑物的高度。
		public int h;

		public Node(int x, boolean isAdd, int h) {
			this.x = x;
			this.isAdd = isAdd;
			this.h = h;
		}
	}

	public static class NodeComparator implements Comparator<Node> {
		@Override
		public int compare(Node o1, Node o2) {
			return o1.x - o2.x;
		}
	}

	/**
	 * 这个算法的核心在于如何有效地跟踪每个 x-坐标的最大高度。
	 * 当我们遇到一个建筑物的开始，我们增加那个高度的计数；
	 * 当我们遇到一个建筑物的结束，我们减少那个高度的计数。
	 * 使用两个 TreeMap 是因为它们能够保持键的顺序，并且能够快速地找到最大的键（即最高的建筑物）。这对于构建轮廓线是非常重要的。
	 * @param matrix
	 * @return
	 */
	public static List<List<Integer>> getSkyline(int[][] matrix) {
		Node[] nodes = new Node[matrix.length * 2];
		for (int i = 0; i < matrix.length; i++) {
			nodes[i * 2] = new Node(matrix[i][0], true, matrix[i][2]);
			nodes[i * 2 + 1] = new Node(matrix[i][1], false, matrix[i][2]);
		}
		Arrays.sort(nodes, new NodeComparator());
		// key  高度  value 次数 这个 TreeMap 用于存储每个高度出现的次数
		TreeMap<Integer, Integer> mapHeightTimes = new TreeMap<>();
		//TODO 存储每个 x-坐标和对应的最大高度 每个位置对应的在最大高度 用来做轮廓线 TreeMap目的是从小到大生成轮廓线
		TreeMap<Integer, Integer> mapXHeight = new TreeMap<>();

		//这个循环遍历排序后的所有节点。每个节点代表建筑物的一个边界（开始或结束）。
		for (int i = 0; i < nodes.length; i++) {
			/**
			 * 当遇到一个开始边界节点（isAdd 为 true），表示一个建筑物在这个位置开始。
			 * 如果这个高度在 mapHeightTimes 中还不存在，就添加这个高度并将次数设置为 1。
			 * 如果这个高度已经存在，就将这个高度的次数增加 1。
			 */
			if (nodes[i].isAdd) {//TODO 这个点是加一个高度
				if (!mapHeightTimes.containsKey(nodes[i].h)) {
					mapHeightTimes.put(nodes[i].h, 1);
				} else {
					mapHeightTimes.put(nodes[i].h, mapHeightTimes.get(nodes[i].h) + 1);//TODO 增加该高度 的 次数
				}
			} else {
				if (mapHeightTimes.get(nodes[i].h) == 1) {
					mapHeightTimes.remove(nodes[i].h);//TODO <h,0> 一定要删除 因为排序的时候会干扰判断
				} else {
					mapHeightTimes.put(nodes[i].h, mapHeightTimes.get(nodes[i].h) - 1);//TODO 减少该高度 的 次数
				}
			}
			if (mapHeightTimes.isEmpty()) {
				mapXHeight.put(nodes[i].x, 0);
			} else {
				mapXHeight.put(nodes[i].x, mapHeightTimes.lastKey());//TODO 某个高度中的 最后一个key
			}
		}
		List<List<Integer>> ans = new ArrayList<>();
		for (Entry<Integer, Integer> entry : mapXHeight.entrySet()) {
			int curX = entry.getKey();
			int curMaxHeight = entry.getValue();
			if (ans.isEmpty() || ans.get(ans.size() - 1).get(1) != curMaxHeight) {
				ans.add(new ArrayList<>(Arrays.asList(curX, curMaxHeight)));
			}
		}
		return ans;
	}

}
