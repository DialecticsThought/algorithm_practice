package code_for_great_offer.class33;

/*
* 给你一个整数数组nums，返回 数组answer，其中answer[i]等于nums中除nums[i]之外其余各元素的乘积。
题目数据 保证 数组nums之中任意元素的全部前缀元素和后缀的乘积都在 32 位 整数范围内。
请不要使用除法，且在O(n) 时间复杂度内完成此题。
示例 1:
输入: nums = [1,2,3,4]
输出: [24,12,8,6]
示例 2:
输入: nums = [-1,1,0,-3,3]
输出: [0,0,9,0,0]

来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/product-of-array-except-self
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
* */
public class LeetCode_0238_ProductOfArrayExceptSelf {

	public int[] productExceptSelf(int[] nums) {
		int n = nums.length;
		int[] ans = new int[n];
		ans[0] = nums[0];
		/*
		*TODO
		* ans[i]是指把num[i]~num[n]的所有数乘起来  可以从称为后缀积
		* 再从头开始遍历 另外准备一个数初始 right=1
		* 对于ans[0]是要把1~n的数乘起来 那么就是 right*ans[1] 然后right=right*nums[0]
		* 对于ans[2]是要把3~n的数乘起来再乘0位置的数 那么就是right*ans[2]
		* */
		for (int i = 1; i < n; i++) {
			ans[i] = ans[i - 1] * nums[i];
		}
		int right = 1;
		for (int i = n - 1; i > 0; i--) {
			ans[i] = ans[i - 1] * right;
			right *= nums[i];
		}
		ans[0] = right;
		return ans;
	}
	/*
	*TODO
	* 扩展 : 如果仅仅是不能用除号，把结果直接填在nums里呢？
	* 解法：数一共几个0；每一个位得到结果就是，a / b，位运算替代 /，之前的课讲过（新手班）
	*TODO 假设能用除法
	* 先对nums遍历遍历一边
	* 遍历的过程中
	* 1.把不是0的数乘在一起
	* 2.统计0的个数
	* 一共3种情况
	* 1.没有0 假设 把所有数乘在一起是a i位置的值是b 那么结果是 a/b
	* 2.有一个0 除了那个0位置结果是 把不是0的数乘在一起之外 其他位置都是0
	* 3.有>=2个0 那么所有数都是0
	* TODO 如果避免除号问题 用位运算
	* */
}
