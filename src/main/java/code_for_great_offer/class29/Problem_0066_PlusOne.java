package code_for_great_offer.class29;

public class Problem_0066_PlusOne {

	/*
	* 66. 加一
	给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
	最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
	你可以假设除了整数 0 之外，这个整数不会以零开头。
	示例 1：
	输入：digits = [1,2,3]
	输出：[1,2,4]
	解释：输入数组表示数字 123。
	示例 2：
	输入：digits = [4,3,2,1]
	输出：[4,3,2,2]
	解释：输入数组表示数字 4321。
	示例 3：
	输入：digits = [0]
	输出：[1]
	* */
	public static int[] plusOne(int[] digits) {
		int n = digits.length;
		// 因为arr[n-1]存放的是最低位 所以从 数组末尾开始遍历
		//只要还在for循环 就说明还在产生进位
		for (int i = n - 1; i >= 0; i--) {
			if (digits[i] < 9) {
				digits[i]++;
				//不进位 直接返回
				return digits;
			}
			//只考虑arr[i]=9 的时候+1 才会进位
			digits[i] = 0;
		}
		int[] ans = new int[n + 1];
		ans[0] = 1;
		return ans;
	}

}
