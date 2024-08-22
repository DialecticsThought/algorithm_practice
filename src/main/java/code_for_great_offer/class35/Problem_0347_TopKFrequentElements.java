package code_for_great_offer.class35;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/*
* 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
*
* 示例 1:
* 输入: nums = [1,1,1,2,2,3], k = 2
* 输出: [1,2]
* 示例 2:
* 输入: nums = [1], k = 1
* 输出: [1]
* 链接：https://leetcode.cn/problems/top-k-frequent-elements
*
* */
public class Problem_0347_TopKFrequentElements {
	/*
	*TODO
	* [3,1,2,2,2,3,4,4,4,4] k=2
	* 先遍历一边数组 把每个元素出现的次数找到
	* <3,2>
	* <1,1>
	* <2,3>
	* <4,4>
	* 然后偶 建立一个小根堆 堆的容量=k=2 比较的是出现次数
	* 1.<3,2>进入堆 堆没有满
	* 2.<1,1>进入堆 堆满了 并且堆 的次序是 <1,1> <3,2>
	* 3.<2,3> 与堆顶比较 哪个出现的次数大 大的进入小根堆
	* 此时 堆 的次序是  <3,2> <2,3>
	* 4.<4,4> 与堆顶比较 哪个出现的次数大 大的进入小根堆
	* 此时 堆 的次序是  <2,3> <4,4>
	* */
	public static class Node {
		public int num;
		public int count;

		public Node(int k) {
			num = k;
			count = 1;
		}
	}

	public static class CountComparator implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			return o1.count - o2.count;
		}

	}

	public static int[] topKFrequent(int[] nums, int k) {
		HashMap<Integer, Node> map = new HashMap<>();
		for (int num : nums) {
			if (!map.containsKey(num)) {
				map.put(num, new Node(num));
			} else {
				map.get(num).count++;
			}
		}
		PriorityQueue<Node> heap = new PriorityQueue<>(new CountComparator());
		for (Node node : map.values()) {
			if (heap.size() < k || (heap.size() == k && node.count > heap.peek().count)) {
				heap.add(node);
			}
			if (heap.size() > k) {
				heap.poll();
			}
		}
		int[] ans = new int[k];
		int index = 0;
		while (!heap.isEmpty()) {
			ans[index++] = heap.poll().num;
		}
		return ans;
	}

}
