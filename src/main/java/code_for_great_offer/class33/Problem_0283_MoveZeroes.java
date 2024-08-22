package code_for_great_offer.class33;

public class Problem_0283_MoveZeroes {
	/*
	*TODO
	* 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
	* 请注意，必须在不复制数组的情况下原地对数组进行操作。
	* 示例 1:
	* 输入: nums = [0,1,0,3,12]
	* 输出: [1,3,12,0,0]
	* 示例 2:
	* 输入: nums = [0]
	* 输出: [0]
	* 链接：https://leetcode.cn/problems/move-zeroes
	*eg:
	* [5 0 3 0 1 2 3 0 0 4]
	* 指针从0位置开始出发 不断右移
	* 每移动到一个位置 判断当前位置是否是0
	* 是0的话 跳过当前位置 来到不是0的位置
	* 不是0的话 把当前的数字 拷贝到不是0位置的最右侧 最后指针右移
	* 0位置 不是0 右移指针
	* 1位置 是0 右移指针
	* 2位置 不是0 把它换到0位置的下一个位置 [5 3 0 0 1 2 3 0 0 4]
	* 3位置 是0 右移指针
	* 4位置 不是0 把它换到1位置的下一个位置 [5 3 1 0 0 2 3 0 0 4]
	* 5位置 不是0 把它换到2位置的下一个位置 [5 3 1 2 0 0 3 0 0 4]
	* 6位置 不是0 把它换到3位置的下一个位置 [5 3 1 2 3 0 0 0 0 4]
	* 7位置 是0
	* 8位置 是0
	* 8位置 不是0 把它换到4位置的下一个位置 [5 3 1 2 3 4 0 0 0 0 ]
	 * */
	public static void moveZeroes(int[] nums) {
		int to = 0;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] != 0) {
				swap(nums, to++, i);
			}
		}
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

}
