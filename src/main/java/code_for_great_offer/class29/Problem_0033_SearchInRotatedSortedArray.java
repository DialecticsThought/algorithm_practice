package code_for_great_offer.class29;
/*
* 33. 搜索旋转排序数组
整数数组 nums 按升序排列，数组中的值 互不相同 。
在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。

示例 1：
输入：nums = [4,5,6,7,0,1,2], target = 0
输出：4
示例 2：
输入：nums = [4,5,6,7,0,1,2], target = 3
输出：-1
示例 3：
输入：nums = [1], target = 0
输出：-1
* */
public class Problem_0033_SearchInRotatedSortedArray {
	/*
	*TODO
	* arr，原本是有序数组，旋转过，而且左部分长度不知道
	* 找num
	* num所在的位置返回
	* */
	public static int search(int[] arr, int num) {
		int L = 0;
		int R = arr.length - 1;
		int M = 0;
		while (L <= R) {
			// M = L + ((R - L) >> 1) 怕溢出的话 这是安全的写法
			M = (L + R) / 2;
			if (arr[M] == num) {//如果中点 = num 返回
				return M;
			}
			// arr[M] != num
			// [L] == [M] == [R] != num 无法二分
			if (arr[L] == arr[M] && arr[M] == arr[R]) {
				// L不断的右移 直到 [L] == [M] == [R] 不成立
				while (L != M && arr[L] == arr[M]) {
					L++;
				}
				/*
				*TODO
				* 2种情况
				*  1) L == M L...M 一路都相等
				*  2) 从L到M终于找到了一个不等的位置
				* */
				if (L == M) { // L...M 一路都相等
					//那么 在 M+1 ~ R 范围二分
					L = M + 1;
					continue;
				}
			}
			/*
			*TODO
			* 下面的 逻辑 2种情况 会执行
			* 1.[L]  [M]  [R] 不都相同的时候 执行
			* 2.上面的情况2 从L到M终于找到了一个不等的位置
			* 总之就是 [L] [M] [R] 不都一样的情况, 如何二分的逻辑
			* */
			// arr[M] != num
			if (arr[L] != arr[M]) {
				/*
				*TODO
				* 断点的概念
				* 假设 原arr = [1,2,3,4,5]
				* 旋转后变成 [4,5,1,2,3] 那么[2]=1就是断点
				*TODO
				* [L] != [M]的情况 假设 [L]=1 [M]=3
				* 那么 L~M之间不可能有断点
				* eg:
				* 4 5 6 7 1 2 3 3 3 3 3 3 这是旋转过后的arr
				* L           M
				* 那么 L~M之间可能有断点 因为[L]>[M]
				* eg:
				* 1 2 2 2 3 3 3 3 3 1 1 1 1 1 这是旋转过后的arr
				* L           M
				* 那么 L~M之间不可能有断点 因为[L]<[M]
				* */
				if (arr[M] > arr[L]) { //TODO arr[M] > arr[L] =>说明 L~M 一定有序 + 断点在右侧

					if (num >= arr[L] && num < arr[M]) {
						/*
						 *TODO
						 * 假设： num= 3  [L] = 1 [M] = 5
						 * 那么 num 一定在 L~M - 1
						 * 因为 L~M之间 严格有序  且  M+1~R 是有断点的  断点之前的数 会 >=5 断点之后的数 会 <=1
						 * 那么 直接 在 L~M-1 做二分
						 * */
						R = M - 1;
					} else {
						/*
						 *TODO
						 * 假设： num= 9  [L] = 2 [M] = 7
						 * 那么 num 一定在 M+1 ~ R
						 * 因为 L~M之间 严格有序  且  M+1~R 是有断点的  断点之前的数 会 >=7 断点之后的数 会 <=2
						 * 那么 直接 在 M+1 ~ R 做二分
						 * */
						L = M + 1;
					}
				} else { //TODO arr[M] < arr[L] =>说明 M+1~R 一定有序 + 断点在左侧 即L~M
					if (num > arr[M] && num <= arr[R]) {
						L = M + 1;
					} else {
						R = M - 1;
					}
				}
			} else { //TODO [L] [M] [R] 不都一样， 但是 [L] == [M] 说明 [M]!=[R]
				if (arr[M] < arr[R]) {
					if (num > arr[M] && num <= arr[R]) {
						L = M + 1;
					} else {
						R = M - 1;
					}
				} else {
					if (num >= arr[L] && num < arr[M]) {
						R = M - 1;
					} else {
						L = M + 1;
					}
				}
			}
		}
		return -1;
	}

}
