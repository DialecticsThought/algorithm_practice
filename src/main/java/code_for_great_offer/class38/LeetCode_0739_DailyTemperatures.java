package code_for_great_offer.class38;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
/*
*todo
* 给定一个整数数组temperatures，表示每天的温度，返回一个数组answer，其中answer[i]是指对于第 i 天，
* 下一个更高温度出现在几天后。如果气温在这之后都不会升高，请在该位置用 0 来代替。
* 示例 1:
* 输入: temperatures = [73,74,75,71,69,72,76,73]
* 输出:[1,1,4,2,1,1,0,0]
* 示例 2:
* 输入: temperatures = [30,40,50,60]
* 输出:[1,1,1,0]
* 示例 3:
* 输入: temperatures = [30,60,90]
* 输出: [1,1,0]
* 链接：https://leetcode.cn/problems/daily-temperatures
*TODO
* 单调栈
*
* */
public class LeetCode_0739_DailyTemperatures {

	public static int[] dailyTemperatures(int[] arr) {
		if (arr == null || arr.length == 0) {
			return new int[0];
		}
		int N = arr.length;
		int[] ans = new int[N];
		Stack<List<Integer>> stack = new Stack<>();
		for (int i = 0; i < N; i++) {
			while (!stack.isEmpty() && arr[stack.peek().get(0)] < arr[i]) {
				List<Integer> popIs = stack.pop();
				for (Integer popi : popIs) {
					ans[popi] = i - popi;
				}
			}
			if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
				stack.peek().add(Integer.valueOf(i));
			} else {
				ArrayList<Integer> list = new ArrayList<>();
				list.add(i);
				stack.push(list);
			}
		}
		return ans;
	}

}
