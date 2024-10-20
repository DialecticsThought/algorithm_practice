package leetcode;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

/**
 *
 * code_for_great_offer.class19
 * 本题测试链接 : https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/
 * 如果只给定一个二叉树前序遍历数组pre和中序遍历数组in，能否不重建树，而直接生成这个二叉树的后序数组并返回已知二叉树中没有重复值
 * 你有 k 个 非递减排列 的整数列表。找到一个 最小 区间，使得 k 个列表中的每个列表至少有一个数包含在其中。
 *
 * 我们定义如果 b-a < d-c 或者在 b-a == d-c 时 a < c，则区间 [a,b] 比 [c,d] 小。
 * 示例 1：
 *
 * 输入：nums = [[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
 * 输出：[20,24]
 * 解释：
 * 列表 1：[4, 10, 15, 24, 26]，24 在区间 [20,24] 中。
 * 列表 2：[0, 9, 12, 20]，20 在区间 [20,24] 中。
 * 列表 3：[5, 18, 22, 30]，22 在区间 [20,24] 中。
 * 示例 2：
 *
 * 输入：nums = [[1,2,3],[1,2,3],[1,2,3]]
 * 输出：[1,1]
 *
 * 用有序表（堆，比较器+treeset）
 *TODO
 * eg:
 * [1,7,30]  [2,54]  [3,64]
 * 有序表 把所有的arr的第1个数放入有序表 从小到大
 * 有序表 从小到大 ： 1,3,21  找到一个区间[1,21]
 * 弹出有序表的第1个数 找到这个数的所属arr，把该arr[1]放入有序表
 * 有序表 从小到大 ： 3,7,21  找到一个区间[3,21]  比原先的[1,21]小，替换
 * 弹出有序表的第1个数 找到这个数的所属arr，把该arr[1]放入有序表
 * 有序表 从小到大 ： 7,21,67  找到一个区间[7,67]  没有比原先的[3,21] 小
 * 弹出有序表的第1个数 找到这个数的所属arr，把该arr[1]放入有序表
 * 有序表 从小到大 ： 21,30,67 找到一个区间[21,67]  没有比原先的[3,21] 小
 */
public class LeetCode_632_SmallestRangeCoveringElementsfromKLists {

	public static class Node {
		public int value;
		public int arrid;
		public int index;

		public Node(int v, int ai, int i) {
			value = v;
			arrid = ai;
			index = i;
		}
	}

	public static class NodeComparator implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			return o1.value != o2.value ? o1.value - o2.value : o1.arrid - o2.arrid;
		}

	}

	public static int[] smallestRange(List<List<Integer>> nums) {
		int N = nums.size();
		TreeSet<Node> orderSet = new TreeSet<>(new NodeComparator());
		for (int i = 0; i < N; i++) {
			orderSet.add(new Node(nums.get(i).get(0), i, 0));
		}
		boolean set = false;
		int a = 0;
		int b = 0;
		while (orderSet.size() == N) {
			Node min = orderSet.first();
			Node max = orderSet.last();
			if (!set || (max.value - min.value < b - a)) {
				set = true;
				a = min.value;
				b = max.value;
			}
			min = orderSet.pollFirst();
			int arrid = min.arrid;
			int index = min.index + 1;
			if (index != nums.get(arrid).size()) {
				orderSet.add(new Node(nums.get(arrid).get(index), arrid, index));
			}
		}
		return new int[] { a, b };
	}

}
