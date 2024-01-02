package code_for_great_offer.class33;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
/*
*TODO
* 现在你总共有 numCourses 门课需要选，记为0到numCourses - 1。给你一个数组prerequisites ，
* 其中 prerequisites[i] = [ai, bi] ，表示在选修课程 ai 前 必须 先选修bi 。
* 例如，想要学习课程 0 ，你需要先完成课程1 ，我们用一个匹配来表示：[0,1] 。
* 返回你为了学完所有课程所安排的学习顺序。可能会有多个正确的顺序，你只要返回 任意一种 就可以了。如果不可能完成所有课程，返回 一个空数组 。
* 示例 1：
* 输入：numCourses = 2, prerequisites = [[1,0]]
* 输出：[0,1]
* 解释：总共有 2 门课程。要学习课程 1，你需要先完成课程 0。因此，正确的课程顺序为 [0,1] 。
* 示例 2：
* 输入：numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
* 输出：[0,2,1,3]
* 解释：总共有 4 门课程。要学习课程 3，你应该先完成课程 1 和课程 2。并且课程 1 和课程 2 都应该排在课程 0 之后。
* 因此，一个正确的课程顺序是[0,1,2,3] 。另一个正确的排序是[0,2,1,3] 。
* 示例 3：
* 输入：numCourses = 1, prerequisites = []
* 输出：[0]
链接：https://leetcode.cn/problems/course-schedule-ii
* */
public class Problem_0210_CourseScheduleII {

	public static class Node {
		public int name;
		public int in;
		public ArrayList<Node> nexts;

		public Node(int n) {
			name = n;
			in = 0;
			nexts = new ArrayList<>();
		}
	}
	//节点出队列的顺序 就是拓扑排序 记录下来
	public int[] findOrder(int numCourses, int[][] prerequisites) {
		int[] ans = new int[numCourses];
		for (int i = 0; i < numCourses; i++) {
			ans[i] = i;
		}
		if (prerequisites == null || prerequisites.length == 0) {
			return ans;
		}
		HashMap<Integer, Node> nodes = new HashMap<>();
		for (int[] arr : prerequisites) {
			int to = arr[0];
			int from = arr[1];
			if (!nodes.containsKey(to)) {
				nodes.put(to, new Node(to));
			}
			if (!nodes.containsKey(from)) {
				nodes.put(from, new Node(from));
			}
			Node t = nodes.get(to);
			Node f = nodes.get(from);
			f.nexts.add(t);
			t.in++;
		}
		int index = 0;
		Queue<Node> zeroInQueue = new LinkedList<>();
		for (int i = 0; i < numCourses; i++) {
			if (!nodes.containsKey(i)) {
				ans[index++] = i;
			} else {
				if (nodes.get(i).in == 0) {
					zeroInQueue.add(nodes.get(i));
				}
			}
		}
		int needPrerequisiteNums = nodes.size();
		int count = 0;
		while (!zeroInQueue.isEmpty()) {
			Node cur = zeroInQueue.poll();
			ans[index++] = cur.name;
			count++;
			for (Node next : cur.nexts) {
				if (--next.in == 0) {
					zeroInQueue.add(next);
				}
			}
		}
		return count == needPrerequisiteNums ? ans : new int[0];
	}

}
