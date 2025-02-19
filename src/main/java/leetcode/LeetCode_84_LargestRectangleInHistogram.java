package leetcode;

import java.util.Stack;

// 测试链接：https://leetcode.com/problems/largest-rectangle-in-histogram

/**
 * <pre>
 * eg:[3,2,4,2,5]
 * 用单调栈
 *         □
 * 	   □   □
 * □   □   □
 * □ □ □ □ □
 * □ □ □ □ □
 * 0 1 2 3 4  下标/位置
 * </pre>
 * 求以0位置的长方形的高（3）向2边拓宽，能拖到多远
 *      对于0位置不能向右拓宽 也不能向左拓宽
 * 此时单调栈 栈底 -> 栈顶， 小 -> 大： 0->3
 * 求以1位置的长方形的高（2）向2边拓宽，能拖到多远
 * 		对于1位置向右拓宽4位置 因为高度 >= 2 向左拓宽到-1位置
 * 弹出 	0->3  对于arr[0] =3 而言 最左最近是 -1 最右最近是  0 -> 2
 * 求以2位置的长方形的高（4）向2边拓宽，能拖到多远
 * 		对于2位置不能向右拓宽 也不能向左拓宽
 * 	此时单调栈 栈底 -> 栈顶， 小 -> 大： 1->2	 2->4
 * 求以3位置的长方形的高（2）向2边拓宽，能拖到多远
 * 弹出 	1->2 2->4
 * 	对于arr[1] =2 而言 最左最近是 0->3 最右最近是  3 -> 2
 * 	对于arr[2] =4 而言 最左最近是 1->2 最右最近是  3 -> 4
 * 此时单调栈 栈底 -> 栈顶， 小 -> 大： 3 -> 2
 * 求以4位置的长方形的高（5）向2边拓宽，能拖到多远
 * 此时单调栈 栈底 -> 栈顶， 小 -> 大： 3 -> 2  4 -> 5
 * 最后
 * 单独弹出：
 * 弹出  4 -> 5  最左最近是 3->2 最右最近是 -1
 * 弹出  3->2  最左最近是 -1 最右最近是 4 -> 5
 */
public class LeetCode_84_LargestRectangleInHistogram {

	public static int largestRectangleArea1(int[] height) {
		if (height == null || height.length == 0) {
			return 0;
		}
		int maxArea = 0;
		Stack<Integer> stack = new Stack<Integer>();
		for (int i = 0; i < height.length; i++) {
			while (!stack.isEmpty() && height[i] <= height[stack.peek()]) {
				int j = stack.pop();
				int k = stack.isEmpty() ? -1 : stack.peek();
				int curArea = (i - k - 1) * height[j];
				maxArea = Math.max(maxArea, curArea);
			}
			stack.push(i);
		}
		while (!stack.isEmpty()) {
			int j = stack.pop();
			int k = stack.isEmpty() ? -1 : stack.peek();
			int curArea = (height.length - k - 1) * height[j];
			maxArea = Math.max(maxArea, curArea);
		}
		return maxArea;
	}

	public static int largestRectangleArea2(int[] height) {
		if (height == null || height.length == 0) {
			return 0;
		}
		int N = height.length;
		int[] stack = new int[N];
		int si = -1;
		int maxArea = 0;
		for (int i = 0; i < height.length; i++) {
			while (si != -1 && height[i] <= height[stack[si]]) {
				int j = stack[si--];
				int k = si == -1 ? -1 : stack[si];
				int curArea = (i - k - 1) * height[j];
				maxArea = Math.max(maxArea, curArea);
			}
			stack[++si] = i;
		}
		while (si != -1) {
			int j = stack[si--];
			int k = si == -1 ? -1 : stack[si];
			int curArea = (height.length - k - 1) * height[j];
			maxArea = Math.max(maxArea, curArea);
		}
		return maxArea;
	}

}
