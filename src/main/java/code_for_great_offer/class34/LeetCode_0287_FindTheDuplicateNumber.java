package code_for_great_offer.class34;

/*
*TODO
* 给定一个包含n + 1 个整数的数组nums ，其数字都在[1, n]范围内（包括 1 和 n），
* 可知至少存在一个重复的整数。
* 假设 nums 只有 一个重复的整数 ，返回这个重复的数 。
* 你设计的解决方案必须 不修改 数组 nums 且只用常量级 O(1) 的额外空间。
* 示例 1：
* 输入：nums = [1,3,4,2,2]
* 输出：2
* 示例 2：
* 输入：nums = [3,1,3,4,2]
* 输出：3
来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/find-the-duplicate-number
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
*
* */
public class LeetCode_0287_FindTheDuplicateNumber {
	/*
	*TODO
	* eg；一个数组 一共7个数 下表是0~6 那么有数字1~6
	* 并且有一个数是重复的
	* 假设arr[0]=5 那么就检查arr[5]的数是不是5
	* 也就说 arr[i]=n 那么就找arr[n]是什么
	* 假设arr[5]=3 那么就检查arr[3]=是啥
	* 假设arr[3]=2 那么就检查arr[2]=是啥
	* 如果arr[2]=2那么 就找到了
	* 可以看成 链表 5->3->2
	* 用快慢指针
	* 一个快指针 一次走2步 一个慢指针 一次走1步
	* 2个指针会在环的某一个节点相遇
	* 此时快指针回到初始点 这一次快指针  一次走1步 慢指针 还是一次走1步
	* 2个指针第二次遇到就是要找的第一个入环节点
	* */
	public static int findDuplicate(int[] nums) {
		if (nums == null || nums.length < 2) {
			return -1;
		}
		int slow = nums[0];
		int fast = nums[nums[0]];
		while (slow != fast) {
			slow = nums[slow];
			fast = nums[nums[fast]];
		}
		fast = 0;
		while (slow != fast) {
			fast = nums[fast];
			slow = nums[slow];
		}
		return slow;
	}

}
