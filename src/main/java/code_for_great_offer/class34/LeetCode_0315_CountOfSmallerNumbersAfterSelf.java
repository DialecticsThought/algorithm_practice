package code_for_great_offer.class34;

import java.util.ArrayList;
import java.util.List;
/*
*给你一个整数数组 nums ，按要求返回一个新数组counts 。
* 数组 counts 有该性质： counts[i] 的值是 nums[i] 右侧小于nums[i] 的元素的数量。
示例 1：
输入：nums = [5,2,6,1]
输出：[2,1,1,0]
解释：
5 的右侧有 2 个更小的元素 (2 和 1)
2 的右侧仅有 1 个更小的元素 (1)
6 的右侧有 1 个更小的元素 (1)
1 的右侧有 0 个更小的元素
示例 2：
输入：nums = [-1]
输出：[0]
示例 3：
输入：nums = [-1,-1]
输出：[0,0]
来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/count-of-smaller-numbers-after-self
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
* */
public class LeetCode_0315_CountOfSmallerNumbersAfterSelf {

	public static class Node {
		public int value;
		public int index;

		public Node(int v, int i) {
			value = v;
			index = i;
		}
	}

	public static List<Integer> countSmaller(int[] nums) {
		List<Integer> ans = new ArrayList<>();
		if (nums == null) {
			return ans;
		}
		for (int i = 0; i < nums.length; i++) {
			ans.add(0);
		}
		if (nums.length < 2) {
			return ans;
		}
		Node[] arr = new Node[nums.length];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new Node(nums[i], i);
		}
		process(arr, 0, arr.length - 1, ans);
		return ans;
	}

	public static void process(Node[] arr, int l, int r, List<Integer> ans) {
		if (l == r) {
			return;
		}
		int mid = l + ((r - l) >> 1);
		process(arr, l, mid, ans);
		process(arr, mid + 1, r, ans);
		merge(arr, l, mid, r, ans);
	}

	public static void merge(Node[] arr, int l, int m, int r, List<Integer> ans) {
		Node[] help = new Node[r - l + 1];
		int i = help.length - 1;
		int p1 = m;
		int p2 = r;
		while (p1 >= l && p2 >= m + 1) {
			if (arr[p1].value > arr[p2].value) {
				ans.set(arr[p1].index, ans.get(arr[p1].index) + p2 - m);
			}
			if( arr[p1].value > arr[p2].value){
				help[i] = arr[p1];
				i--;
				p1--;
			}else {
				help[i] = arr[p2];
				i--;
				p2--;
			}
		}
		while (p1 >= l) {
			help[i] = arr[p1];
			i--;
			p1--;
		}
		while (p2 >= m + 1) {
			help[i] = arr[p2];
			i--;
			p2--;
		}
		for (i = 0; i < help.length; i++) {
			arr[l + i] = help[i];
		}
	}

}
