package code_for_great_offer.class32;

/*
*TODO
* 给定一个整数数组 nums，将数组中的元素向右轮转 k个位置，其中k是非负数。
* 示例 1:
* 输入: nums = [1,2,3,4,5,6,7], k = 3
* 输出: [5,6,7,1,2,3,4]
* 解释:
* 向右轮转 1 步: [7,1,2,3,4,5,6]
* 向右轮转 2 步: [6,7,1,2,3,4,5]
* 向右轮转 3 步: [5,6,7,1,2,3,4]
* 示例2:
* 输入：nums = [-1,-100,3,99], k = 2
* 输出：[3,99,-1,-100]
* 解释:
* 向右轮转 1 步: [99,-1,-100,3]
链接：https://leetcode.cn/problems/rotate-array
* */
public class Problem_0189_RotateArray {

	public void rotate1(int[] nums, int k) {
		int N = nums.length;
		k = k % N;
		reverse(nums, 0, N - k - 1);
		reverse(nums, N - k, N - 1);
		reverse(nums, 0, N - 1);
	}

	public static void reverse(int[] nums, int L, int R) {
		while (L < R) {
			int tmp = nums[L];
			nums[L++] = nums[R];
			nums[R--] = tmp;
		}
	}
	/*
	*TODO 问题1
	* 在一定范围内逆序 只需要2个变量就可以完成
	* a b c d e
	* ↑       ↑
	* e b c d a
	*   ↑   ↑
	* e d c b a
	*    ↑↑
	*TODO 问题2
	* 一个数组 分成左右2个部分
	* 左侧 单独 逆序  右侧 也单独逆序
	* [a b c , d e f g h]
	* => [c b a , h g f e d]
	* => 最后 整体交换
	* */
	public static void rotate2(int[] nums, int k) {
		int N = nums.length;
		k = k % N;
		if (k == 0) {
			return;
		}
		int L = 0;
		int R = N - 1;
		int lpart = N - k;
		int rpart = k;
		int same = Math.min(lpart, rpart);
		int diff = lpart - rpart;
		exchange(nums, L, R, same);
		while (diff != 0) {
			if (diff > 0) {
				L += same;
				lpart = diff;
			} else {
				R -= same;
				rpart = -diff;
			}
			same = Math.min(lpart, rpart);
			diff = lpart - rpart;
			exchange(nums, L, R, same);
		}
	}

	public static void exchange(int[] nums, int start, int end, int size) {
		int i = end - size + 1;
		int tmp = 0;
		while (size-- != 0) {
			tmp = nums[start];
			nums[start] = nums[i];
			nums[i] = tmp;
			start++;
			i++;
		}
	}

}
