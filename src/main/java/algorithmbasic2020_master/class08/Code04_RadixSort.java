package algorithmbasic2020_master.class08;

import java.util.Arrays;

public class Code04_RadixSort {

	// only for no-negative value
	public static void radixSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		radixSort(arr, 0, arr.length - 1, maxbits(arr));
	}

	public static int maxbits(int[] arr) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {
			max = Math.max(max, arr[i]);
		}
		int res = 0;
		while (max != 0) {
			res++;
			max /= 10;
		}
		return res;
	}

	/*
	*TODO
	* arr[L..R]排序  ,  最大值的十进制位数digit
	* 0~r arr[]={3 56 17 100 3} 那么数组中最大的数是3位
	* */
	public static void radixSort(int[] arr, int L, int R, int digit) {
		final int radix = 10;//进制数
		int i = 0, j = 0;
		//TODO 有多少个数准备多少个辅助空间
		int[] help = new int[R - L + 1];
		//TODO d=1 表示一开始在个位上比较 d++ 之后是 在十位上比较
		for (int d = 1; d <= digit; d++) { //TODO 有多少位就进出桶几次 eg:第一次根据个位进入桶 第二次根据百位进入桶
			/*
			* TODO   10个空间
		    *  count[0] 当前位(d位)是0的数字有多少个
			*  count[1] 当前位(d位)是(0和1)的数字有多少个
			*  count[2] 当前位(d位)是(0、1和2)的数字有多少个
			*  count[i] 当前位(d位)是(0~i)的数字有多少个
			*/
			int[] count = new int[radix]; // count[0..9]
			for (i = L; i <= R; i++) {
				//TODO 103  1表示个位   取出3
				//TODO 209  1表示个位  取出 9
				j = getDigit(arr[i], d);
				count[j]++;
			}
			for (i = 1; i < radix; i++) {
				//TODO 做累加和变成count'
				count[i] = count[i] + count[i - 1];
			}
			//TODO 这for循环相当于出桶
			for (i = R; i >= L; i--) {//TODO 从后往前看
				j = getDigit(arr[i], d);
				help[count[j] - 1] = arr[i];
				count[j]--;
			}
			for (i = L, j = 0; i <= R; i++, j++) {
				arr[i] = help[j];
			}
		}
	}

	public static int getDigit(int x, int d) {
		return ((x / ((int) Math.pow(10, d - 1))) % 10);
	}

	// for test
	public static void comparator(int[] arr) {
		Arrays.sort(arr);
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random());
		}
		return arr;
	}

	// for test
	public static int[] copyArray(int[] arr) {
		if (arr == null) {
			return null;
		}
		int[] res = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i];
		}
		return res;
	}

	// for test
	public static boolean isEqual(int[] arr1, int[] arr2) {
		if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
			return false;
		}
		if (arr1 == null && arr2 == null) {
			return true;
		}
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100000;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			radixSort(arr1);
			comparator(arr2);
			if (!isEqual(arr1, arr2)) {
				succeed = false;
				printArray(arr1);
				printArray(arr2);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");

		int[] arr = generateRandomArray(maxSize, maxValue);
		printArray(arr);
		radixSort(arr);
		printArray(arr);

	}

}
