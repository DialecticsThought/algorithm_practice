package code_for_great_offer.class33;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
/*
*TODO
* 你这个学期必须选修 numCourses 门课程，记为0到numCourses - 1 。
* 在选修某些课程之前需要一些先修课程。 先修课程按数组prerequisites 给出，其中prerequisites[i] = [ai, bi] ，表示如果要学习课程 ai 则 必须 先学习课程  bi 。
* 例如，先修课程对[0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
* 请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
* 示例 1：
* 输入：numCourses = 2, prerequisites = [[1,0]]
* 输出：true
* 解释：总共有 2 门课程。学习课程 1 之前，你需要完成课程 0 。这是可能的。
* 示例 2：
* 输入：numCourses = 2, prerequisites = [[1,0],[0,1]]
* 输出：false
* 解释：总共有 2 门课程。学习课程 1 之前，你需要先完成课程 0 ；并且学习课程 0 之前，你还应先完成课程 1 。这是不可能的。
链接：https://leetcode.cn/problems/course-schedule
*TODO
* 类似于给一张图 能不能把这个图的每个课程学习完 没有环
* 类似于拓扑排序
* 先 把图中入度为0的点 找到
* 把该节点删除 后 并输出该节点
* 接下来再找 把图中入度为0的点
* ....
* 如果同时有多个入度为0的节点 删除的顺序无所谓
* TODO
*  如果图的点不能被删除完 说明 有环路
* */
public class LeetCode_0207_CourseSchedule {

	// 一个node，就是一个课程
	// name是课程的编号
	// in是课程的入度
	public static class Course {
		public int name;
		public int in;//入度
		public ArrayList<Course> nexts;//当前节点的子节点

		public Course(int n) {
			name = n;
			in = 0;
			nexts = new ArrayList<>();
		}
	}
	/*
	*TODO
	* numCourses 表示课程的总数量 那么课程的编号 是 0~numCourses-1
	* */
	public static boolean canFinish1(int numCourses, int[][] prerequisites) {
		if (prerequisites == null || prerequisites.length == 0) {
			return true;
		}
		// 一个编号 对应 一个课的实例
		HashMap<Integer, Course> nodes = new HashMap<>();
		/*
		*TODO
		* prerequisites有一个 值是[3,1]
		* 那么 图的样子就是 1 -> 3
		* */
		for (int[] arr : prerequisites) {
			int to = arr[0];
			int from = arr[1];
			if (!nodes.containsKey(to)) {
				nodes.put(to, new Course(to));
			}
			if (!nodes.containsKey(from)) {
				nodes.put(from, new Course(from));
			}
			Course t = nodes.get(to);
			Course f = nodes.get(from);
			//把 to节点 放入from的子节点集合中
			f.nexts.add(t);
			//to的入度++
			t.in++;
		}
		int needPrerequisiteNums = nodes.size();
		Queue<Course> zeroInQueue = new LinkedList<>();
		//TODO 遍历到所有的节点 找到入度为0的节点
		for (Course node : nodes.values()) {
			if (node.in == 0) {
				//TODO 可能有多个入度为0 都放入到一个队列中
				zeroInQueue.add(node);
			}
		}
		int count = 0;
		while (!zeroInQueue.isEmpty()) {
			//TODO 弹出一个节点
			Course cur = zeroInQueue.poll();
			count++;
			//TODO 弹出这个节点之后 它的子节点（邻居）的入度全部-1
			for (Course next : cur.nexts) {
				next.in--;
				if (next.in == 0) {
					zeroInQueue.add(next);
				}
			}
		}
		return count == needPrerequisiteNums;
	}

	// 和方法1算法过程一样
	// 但是写法优化了
	public static boolean canFinish2(int courses, int[][] relation) {
		if (relation == null || relation.length == 0) {
			return true;
		}
		/*
		*TODO
		* 假设 课程3完成之后 才能选 课程0 1 2
		* 那么就是
		* 3 : {0,1,2}
		* 左侧是下标 表示课程 右侧是 该课程完成后 才能选哪些课程
		* */
		ArrayList<ArrayList<Integer>> nexts = new ArrayList<>();
		for (int i = 0; i < courses; i++) {
			nexts.add(new ArrayList<>());
		}
		//TODO 课程3的入度 1 => in[3] == 1
		int[] in = new int[courses];
		for (int[] arr : relation) {
			//TODO arr[1] 就是 from   arr[0] 就是 to
			nexts.get(arr[1]).add(arr[0]);
			//TODO 课程对应的位置++ 表示该课程入度+1
			in[arr[0]]++;
		}

		// 队列
		int[] zero = new int[courses];
		/*
		*TODO
		* 该队列有效范围是[l,r)
		* 新来的数，放哪？r位置，r++
		* 出队列的数，从哪拿？l位置，l++
		* l == r 队列无元素  l < r 队列有元素
		* */
		int l = 0;
		int r = 0;
		for (int i = 0; i < courses; i++) {
			if (in[i] == 0) {
				zero[r++] = i;
			}
		}
		int count = 0;
		while (l != r) {
			count++; //TODO 当前 zero[l] 出队列 并且  l++
			for (int next : nexts.get(zero[l])) {
				l++;
				//TODO next 是弹出的课程的邻居
				//next的入度--
				in[next]--;
				if (in[next] == 0) {//那么就要把该节点放入数组
					zero[r++] = next;
				}
			}
		}
		return count == nexts.size();
	}

}
