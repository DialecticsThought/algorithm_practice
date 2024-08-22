package code_for_great_offer.class38;

import java.util.ArrayList;
import java.util.List;
/*
*TODO
* 给你一个含 n 个整数的数组 nums ，其中 nums[i] 在区间 [1, n] 内。
* 请你找出所有在 [1, n] 范围内但没有出现在 nums 中的数字，并以数组的形式返回结果。
* 示例 1：
* 输入：nums = [4,3,2,7,8,2,3,1]
* 输出：[5,6]
* 示例 2：
* 输入：nums = [1,1]
* 输出：[2]
* 链接：https://leetcode.cn/problems/find-all-numbers-disappeared-in-an-array
*
*TODO
* [3 2 4 4 3 5 1] len=7 尽可能如果有数字i+1 那么就让这个数字 放在位置i上
* 那个位置i做不到 放的是i+1 那么就是缺失的数
* 从0位置开始
* 对于0位置的3 尽可能地放在位置2上
* 原本的2位置的4 就被怼出来了 这个4应该去3位置
* 但是此时3位置已经有了4
* 那么就结束了从0位置开始的下标循环怼任务
* 来到1位置 发现已经是数字2
* 来到2位置 发现已经是数字3
* 来到3位置 发现已经是数字4
* 来到4位置 开始 对于4位置的3 尽可能地放在位置2上
* 但是此时2位置已经有了3
* 那么就结束了从4位置开始的下标循环怼任务
* 来到5位置 开始 对于5位置的5 尽可能地放在位置4上
* 原本的4位置的3 就被怼出来了  这个3应该去2位置
* 但是此时2位置已经有了3 ==> 说明这个3不要了
* 来到6位置 开始 对于6位置的1  尽可能地放在位置0上
* 原本的0位置的3 就被怼出来了  这个3应该去2位置
* 但是此时2位置已经有了3 ==> 说明这个3不要了
* 最后数组[1 2 3 4 5 3 3]
* 最后2个位置 就说明缺失的数字
* */
public class Problem_0448_FindAllNumbersDisappearedInAnArray {

	public static List<Integer> findDisappearedNumbers(int[] nums) {
		List<Integer> ans = new ArrayList<>();
		if (nums == null || nums.length == 0) {
			return ans;
		}
		int N = nums.length;
		for (int i = 0; i < N; i++) {
			// 从i位置出发，去玩下标循环怼
			walk(nums, i);
		}
		for (int i = 0; i < N; i++) {
			if (nums[i] != i + 1) {
				ans.add(i + 1);
			}
		}
		return ans;
	}

	public static void walk(int[] nums, int i) {
		while (nums[i] != i + 1) { // 不断从i发货
			int nexti = nums[i] - 1;
			if (nums[nexti] == nexti + 1) {
				break;
			}
			swap(nums, i, nexti);
		}
	}

	public static void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

}
