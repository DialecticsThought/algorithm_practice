package code_for_great_offer.class04;

import java.util.*;
import java.util.Map.Entry;

// 本题测试链接 : https://leetcode.com/problems/the-skyline-problem/
public class Code08_TheSkylineProblem {

	public static class Node {
		public int x;
		public boolean isAdd;
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

	public static List<List<Integer>> getSkyline(int[][] matrix) {
		Node[] nodes = new Node[matrix.length * 2];
		for (int i = 0; i < matrix.length; i++) {
			nodes[i * 2] = new Node(matrix[i][0], true, matrix[i][2]);
			nodes[i * 2 + 1] = new Node(matrix[i][1], false, matrix[i][2]);
		}
		Arrays.sort(nodes, new NodeComparator());
		// key  高度  value 次数
		TreeMap<Integer, Integer> mapHeightTimes = new TreeMap<>();
		//TODO 每个位置对应的在最大高度 用来做轮廓线 TreeMap目的是从小到大生成轮廓线
		TreeMap<Integer, Integer> mapXHeight = new TreeMap<>();
		for (int i = 0; i < nodes.length; i++) {
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
