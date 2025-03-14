package code_for_great_offer.class34;
/*
* 给你一个整数数组nums，将它重新排列成nums[0] < nums[1] > nums[2] < nums[3]...的顺序。
你可以假设所有输入数组都可以得到满足题目要求的结果。
示例 1：
输入：nums = [1,5,1,1,6,4]
输出：[1,6,1,5,1,4]
解释：[1,4,1,5,1,6] 同样是符合题目要求的结果，可以被判题程序接受。
示例 2：
输入：nums = [1,3,2,2,3,1]
输出：[2,3,1,3,1,2]
来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/wiggle-sort-ii
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
* */
public class LeetCode_0324_WiggleSortII {
	/*
	*TODO
	* 1.无序数组中找到第k小的数
	* 	也就是说 随机选一个数作为pivot 然后荷兰国旗问题
	* 	分成3个区域 <pivot  =pivot  >pivot
	* 	如果第k小的数没有命中=pivot的区域 也就是说k的值不包含=pivot的区域的左区间（下标）和右区间（下标）
	*	那么就去2侧找继续重复上面的流程
	*TODO
	* 2.完美洗牌问题 （class020）
	* eg：
	* 假设 已经分完 变成数组  再把数组分成2分 如下：
	* [3  1  4  4  4  6  8  7]
	* L1 L2 L3 L4 R1 R2 R3 R4
	* 然后再分成
	* [3  4  1  6  4  8  4  7]
	* L1 R1 L2 R2  L3 R3 L4 R4
	*	eg: 对于奇数项的数组
	* 分成3部分 第一个部分 0位置 单独成L0  第二部分L1~L4 第三部分
	* [0  3  1  4  4  4  6  7  8]
	* L0 L1 R1 L2 R2 L3 R3 L4 R4
	* 然后再分成
	* [0  4  3  6  1  7  4  8  4]
	* L0 L1 R1 L2 R2  L3 R3 L4 R4
	* */
	// 时间复杂度O(N)，额外空间复杂度O(1)
	public static void wiggleSort(int[] nums) {
		if (nums == null || nums.length < 2) {
			return;
		}
		int N = nums.length;
		//分成 小 中 右  荷兰国旗问题
		findIndexNum(nums, 0, nums.length - 1, N / 2);
		if ((N & 1) == 0) {//判断是否是偶数项
			// R L -> L R
			shuffle(nums, 0, nums.length - 1);
			// R1 L1 R2 L2 R3 L3 R4 L4
			// L4 R4 L3 R3 L2 R2 L1 R1 -> 代码中的方式，可以的！
			// L1 R1 L2 R2 L3 R3 L4 R4 -> 课上的分析，是不行的！不能两两交换！
			reverse(nums, 0, nums.length - 1);
			// 做个实验，如果把上一行的code注释掉(reverse过程)，然后跑下面注释掉的for循环代码
			// for循环的代码就是两两交换，会发现对数器报错，说明两两交换是不行的, 必须整体逆序
//			for (int i = 0; i < nums.length; i += 2) {
//				swap(nums, i, i + 1);
//			}
		} else {
			shuffle(nums, 1, nums.length - 1);
		}
	}

	public static int findIndexNum(int[] arr, int L, int R, int index) {
		int pivot = 0;
		int[] range = null;
		while (L < R) {
			pivot = arr[L + (int) (Math.random() * (R - L + 1))];
			range = partition(arr, L, R, pivot);
			if (index >= range[0] && index <= range[1]) {
				return arr[index];
			} else if (index < range[0]) {
				R = range[0] - 1;
			} else {
				L = range[1] + 1;
			}
		}
		return arr[L];
	}

	public static int[] partition(int[] arr, int L, int R, int pivot) {
		int less = L - 1;
		int more = R + 1;
		int cur = L;
		while (cur < more) {
			if (arr[cur] < pivot) {
				swap(arr, ++less, cur++);
			} else if (arr[cur] > pivot) {
				swap(arr, cur, --more);
			} else {
				cur++;
			}
		}
		return new int[] { less + 1, more - 1 };
	}

	public static void shuffle(int[] nums, int l, int r) {
		while (r - l + 1 > 0) {
			int lenAndOne = r - l + 2;
			int bloom = 3;
			int k = 1;
			while (bloom <= lenAndOne / 3) {
				bloom *= 3;
				k++;
			}
			int m = (bloom - 1) / 2;
			int mid = (l + r) / 2;
			rotate(nums, l + m, mid, mid + m);
			cycles(nums, l - 1, bloom, k);
			l = l + bloom - 1;
		}
	}

	public static void cycles(int[] nums, int base, int bloom, int k) {
		for (int i = 0, trigger = 1; i < k; i++, trigger *= 3) {
			int next = (2 * trigger) % bloom;
			int cur = next;
			int record = nums[next + base];
			int tmp = 0;
			nums[next + base] = nums[trigger + base];
			while (cur != trigger) {
				next = (2 * cur) % bloom;
				tmp = nums[next + base];
				nums[next + base] = record;
				cur = next;
				record = tmp;
			}
		}
	}

	public static void rotate(int[] arr, int l, int m, int r) {
		reverse(arr, l, m);
		reverse(arr, m + 1, r);
		reverse(arr, l, r);
	}

	public static void reverse(int[] arr, int l, int r) {
		while (l < r) {
			swap(arr, l, r);
			l++;
			r--;
		}
	}

	public static void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

	// 为了测试，暴力方法
	// 把arr全排列尝试一遍，
	// 其中任何一个排列能做到 < > < > ... 返回true;
	// 如果没有任何一个排列能做到，返回false;
	public static boolean test(int[] arr) {
		return process(arr, 0);
	}

	// 为了测试
	public static boolean process(int[] arr, int index) {
		if (index == arr.length) {
			return valid(arr);
		}
		for (int i = index; i < arr.length; i++) {
			swap(arr, index, i);
			if (process(arr, index + 1)) {
				return true;
			}
			swap(arr, index, i);
		}
		return false;
	}

	// 为了测试
	public static boolean valid(int[] arr) {
		boolean more = true;
		for (int i = 1; i < arr.length; i++) {
			if ((more && arr[i - 1] >= arr[i]) || (!more && arr[i - 1] <= arr[i])) {
				return false;
			}
			more = !more;
		}
		return true;
	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * v);
		}
		return ans;
	}

	// 为了测试
	public static int[] copyArray(int[] arr) {
		int[] ans = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			ans[i] = arr[i];
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 10;
		int V = 10;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * N) + 1;
			int[] arr1 = randomArray(n, V);
			int[] arr2 = copyArray(arr1);
			wiggleSort(arr1);
			if (valid(arr1) != test(arr2)) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}
