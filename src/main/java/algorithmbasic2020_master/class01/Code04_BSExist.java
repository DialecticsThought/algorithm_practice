package algorithmbasic2020_master.class01;

import java.util.Arrays;

public class Code04_BSExist {
	//1)在一个有序数组中，找某个数是否存在
	public static boolean exist(int[] sortedArr, int num) {
		if (sortedArr == null || sortedArr.length == 0) {
			return false;
		}
		int L = 0;
		int R = sortedArr.length - 1;
		int mid = 0;
		// L..R
		while (L < R) { // L..R 至少两个数的时候
			/*
			* 等于 mid = (L+R)/2 如果L的下标到达10亿的话 可能溢出
			* 安全的写法 mid = L + (R-L)/2
			* L+R可能溢出 L+(R-L)/2不会溢出
			* 而且 /2 可以是右移一位 代替 速度会快
			* eg： N * 2 + 1 => ((N << 1) | 1)
			* 因为 右移一位后最低位是补0 想+1的话 就 或 一个1
			* */
			mid = L + ((R - L) >> 1);
			if (sortedArr[mid] == num) {
				return true;
			} else if (sortedArr[mid] > num) {
				R = mid - 1;
			} else {
				L = mid + 1;
			}
		}
		return sortedArr[L] == num;
	}

	// for test
	public static boolean test(int[] sortedArr, int num) {
		for(int cur : sortedArr) {
			if(cur == num) {
				return true;
			}
		}
		return false;
	}


	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	public static void main(String[] args) {
		/*
		* 对数器的测试用例
		* */
		int testTime = 500000;
		int maxSize = 10;
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(maxSize, maxValue);
			Arrays.sort(arr);
			int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
			if (test(arr, value) != exist(arr, value)) {
				succeed = false;
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");
	}

}
