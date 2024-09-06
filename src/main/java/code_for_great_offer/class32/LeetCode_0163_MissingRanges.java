package code_for_great_offer.class32;

import java.util.ArrayList;
import java.util.List;
/*
*TODO
* 给定一个排序的整数数组nums，其中元素的范围在闭区间[lower, upper]当中，返回不包含在数组中的缺失区间。
* 示例:
* 输入:nums = [0，1，3，50，75]，Lower = 0和upper = 99,输出:[ "2"，"4->49","51->74"，"76->99"]
* https://leetcode.cn/problems/missing-ranges/
* */
public class LeetCode_0163_MissingRanges {

	public static List<String> findMissingRanges(int[] nums, int lower, int upper) {
		/*
		* [0,1,3,50,75] [-5,99]
		* 来到0位置 [0] > lower  生成缺失的区间 [lower,[0]-1] lower = [0] +1 => [1,99]
		* 来到1位置 [1] = lower  lower = [1] +1 => [2,99]
		* 来到2位置 [2] > lower  生成缺失的区间 [lower,[2]-1]  lower = [2] +1 => [4,99]
		* 来到3位置 [3] > lower  生成缺失的区间 [lower,[3]-1]  lower = [3] +1 => [51,99]
		* 来到4位置 [3] > lower  生成缺失的区间 [lower,[4]-1]  lower = [4] +1 => [76,99]
		 * */
		//TODO 用来存放结果
		List<String> ans = new ArrayList<>();
		//TODO 循环条件结束 1.遍历完数组 或 2.num==upper
		for (int num : nums) {
			//TODO 如果当前遍历到的数 > lower 说明 缺失区间 [lower,num-1]
			if (num > lower) {
				ans.add(miss(lower, num - 1));
			}
			if (num == upper) {
				return ans;
			}
			//TODO 记录完缺失的空间之后 lower变成num+1 下一轮循环关心 [num+1,upper]缺失哪些空间
			lower = num + 1;
		}
		//TODO 循环结束 再判断
		if (lower <= upper) {
			ans.add(miss(lower, upper));
		}
		return ans;
	}

	// 生成"lower->upper"的字符串，如果lower==upper，只用生成"lower"
	public static String miss(int lower, int upper) {
		String left = String.valueOf(lower);
		String right = "";
		if (upper > lower) {
			right = "->" + String.valueOf(upper);
		}
		return left + right;
	}

}
