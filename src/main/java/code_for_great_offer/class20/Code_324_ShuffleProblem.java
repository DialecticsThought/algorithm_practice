package code_for_great_offer.class20;

import java.util.Arrays;

/**
 *TODO
 * https://leetcode.cn/problems/wiggle-sort-ii/description/
 * 前置问题
 * [a,b,c,d,e,f] 变成[e,f,a,b,c,d]
 * 三翻转
 * [a,b,c,d,e,f]中 [a,b,c,d]翻转,[e,f]翻转 得到[]d,c,b,a,f,e]
 * 最后再犯翻转 [e,f,a,b,c,d]
 * 现在的问题是
 * 原始arr[L1,L2,L3,L4,R1,R2,R3,R4]
 * 目标arr[R1,L1,R2,L2,R3,L3,R4,L4]
 * eg:
 * arr [1,2,3,4 | 5,6,7,8] 这个arr分成左半部分 和右半部分
 * 目标
 * 为了清晰展示 写成了2个arr，本质是一个arr
 * [ ,1, ,2, ,3, ,4] 左半部分 的元素在新的arr的位置
 * [5, ,6, ,7, ,8, ] 右半部分 的元素在新的arr的位置
 * 最终
 * [5,1,6,2,7,3,8,4]
 * 也就是说左半部分的arr[i]在新的arr中是2*i下标
 * 也就是说右半部分的arr[i]在新的arr中是(i-arr.length/2)*2-1下标
 * eg:
 * [a,b,c,d] 下标从1开始
 * 来到arr[1] 计算得到要去的arr[2]
 * 那么原先的arr[2]计算得到要去arr[4]
 * 那么原先的arr[4]计算得到要去arr[3]
 * 那么原先的arr[3]计算得到要去arr[1]
 *TODO 下标循环怼
 * eg:
 * [a,b,c,d,e,f] 这里arr的下标从1开始
 * arr[1]的a把arr[2]的b怼出来
 * arr[2]的b把arr[4]的d怼出来
 * arr[4]的d把arr[1]的位置占用
 * 也就是
 * 1 -> 2 -> 4
 * ↑<--<--<- ↓
 * 剩下的arr[3] arr[5] arr[6]就没转换的位置 需要另一个环
 * arr[3]的c把arr[6]的f怼出来
 * arr[6]的f把arr[5]的e怼出来
 * arr[5]的3把arr[3]的位置占用
 * 3 -> 6 -> 5
 * ↑<--<--<- ↓
 * 总结
 * arr.len = N =3^k-1 可以直接算出环的开始位置
 * 下标是 3^1-1,3^2-1,3^k-1.....
 * N=2  环的位置是1 开始 再形成环
 * N=8 环的开始位置是1和3，再形成环
 * N=26 环的开始位置是1和3和9，再形成环
 * eg:
 * [L1,L2,L3,L4,L5,L6,R1,R2,R3,R4,R5,R6] N = arr.len 这里arr的下标从1开始
 * [L1,L2,L3,L4,L5,L6 | R1,R2,R3,R4,R5,R6] 分成2部分
 * 1.找到离N=12最近的3^k-1 => 3^k-1=8
 * 2.左部分开始留出12-8=4个元素 [L1,L2,L3,L4 | L5,L6 | R1,R2,R3,R4,R5,R6]
 * 	右部分开始留出12-8=4个元素[L1,L2,L3,L4 | L5,L6 | R1,R2,R3,R4 | R5,R6]
 * 	用3翻转怼[L5,L6 | R1,R2,R3,R4 ]操作
 * 	得到新数组 [L1,L2,L3,L4,R1,R2,R3,R4 | L5,L6,R5,R6]
 * 	[L1,L2,L3,L4,R1,R2,R3,R4]是符合规律的
 * 	[L5,L6,R5,R6]再去分别考虑离3^k-1的是2，再去翻转[L5,L6,R5,R6]
 * 	得到[L5,R5,L6,R6]
 *
 */
public class Code_324_ShuffleProblem {

	// 数组的长度为len，调整前的位置是i，返回调整之后的位置
	// 下标不从0开始，从1开始
	public static int modifyIndex1(int i, int len) {
		if (i <= len / 2) {
			return 2 * i;
		} else {
			return 2 * (i - (len / 2)) - 1;
		}
	}

	// 数组的长度为len，调整前的位置是i，返回调整之后的位置
	// 下标不从0开始，从1开始
	public static int modifyIndex2(int i, int len) {
		return (2 * i) % (len + 1);
	}

	// 主函数
	// 数组必须不为空，且长度为偶数
	public static void shuffle(int[] arr) {
		if (arr != null && arr.length != 0 && (arr.length & 1) == 0) {
			shuffle(arr, 0, arr.length - 1);
		}
	}

	// 在arr[L..R]上做完美洗牌的调整（arr[L..R]范围上一定要是偶数个数字）
	public static void shuffle(int[] arr, int L, int R) {
		while (R - L + 1 > 0) { // 切成一块一块的解决，每一块的长度满足(3^k)-1
			int len = R - L + 1;
			int base = 3;
			int k = 1;
			// 计算小于等于len并且是离len最近的，满足(3^k)-1的数
			// 也就是找到最大的k，满足3^k <= len+1
			while (base <= (len + 1) / 3) { // base > (N+1)/3
				base *= 3;
				k++;
			}
			//TODO 下面就是算出需要转的部分

			// 3^k -1
			// 当前要解决长度为base-1的块，一半就是再除2
			int half = (base - 1) / 2;
			// [L..R]的中点位置
			int mid = (L + R) / 2;
			// 要旋转的左部分为[L+half...mid], 右部分为arr[mid+1..mid+half]
			// 注意在这里，arr下标是从0开始的
			rotate(arr, L + half, mid, mid + half);
			// 旋转完成后，从L开始算起，长度为base-1的部分进行下标连续推
			// 这个方法就是下标循环怼
			cycles(arr, L, base - 1, k);
			// 解决了前base-1的部分，剩下的部分下个循环继续处理
			L = L + base - 1; // L ->     [] [+1...R]
		}
	}

	// 从start位置开始，往右len的长度这一段，做下标连续推
	// 出发位置依次为1,3,9...
	public static void cycles(int[] arr, int start, int len, int k) {
		// 找到每一个出发位置trigger，一共k个
		// 每一个trigger都进行下标连续推
		// 出发位置是从1开始算的，而数组下标是从0开始算的。
		for (int i = 0, trigger = 1; i < k; i++, trigger *= 3) {
			int preValue = arr[trigger + start - 1];
			int cur = modifyIndex2(trigger, len);
			while (cur != trigger) {
				int tmp = arr[cur + start - 1];
				arr[cur + start - 1] = preValue;
				preValue = tmp;
				cur = modifyIndex2(cur, len);
			}
			arr[cur + start - 1] = preValue;
		}
	}

	// [L..M]为左部分，[M+1..R]为右部分，左右两部分互换
	public static void rotate(int[] arr, int L, int M, int R) {
		reverse(arr, L, M);
		reverse(arr, M + 1, R);
		reverse(arr, L, R);
	}

	// [L..R]做逆序调整
	public static void reverse(int[] arr, int L, int R) {
		while (L < R) {
			int tmp = arr[L];
			arr[L++] = arr[R];
			arr[R--] = tmp;
		}
	}

	public static void wiggleSort(int[] arr) {
		if (arr == null || arr.length == 0) {
			return;
		}
		// 假设这个排序是额外空间复杂度O(1)的，当然系统提供的排序并不是，你可以自己实现一个堆排序
		Arrays.sort(arr);
		if ((arr.length & 1) == 1) {
			shuffle(arr, 1, arr.length - 1);
		} else {
			shuffle(arr, 0, arr.length - 1);
			for (int i = 0; i < arr.length; i += 2) {
				int tmp = arr[i];
				arr[i] = arr[i + 1];
				arr[i + 1] = tmp;
			}
		}
	}

	// for test
	public static boolean isValidWiggle(int[] arr) {
		for (int i = 1; i < arr.length; i++) {
			if ((i & 1) == 1 && arr[i] < arr[i - 1]) {
				return false;
			}
			if ((i & 1) == 0 && arr[i] > arr[i - 1]) {
				return false;
			}
		}
		return true;
	}

	// for test
	public static void printArray(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static int[] generateArray() {
		int len = (int) (Math.random() * 10) * 2;
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * 100);
		}
		return arr;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 5000000; i++) {
			int[] arr = generateArray();
			wiggleSort(arr);
			if (!isValidWiggle(arr)) {
				System.out.println("ooops!");
				printArray(arr);
				break;
			}
		}
	}

}
