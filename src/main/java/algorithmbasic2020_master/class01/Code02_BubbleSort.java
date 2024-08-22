package algorithmbasic2020_master.class01;

//import org.junit.jupiter.api.Test;


import java.util.Arrays;

public class Code02_BubbleSort {

	public static void bubbleSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		// 0 ~ N-1
		// 0 ~ N-2
		// 0 ~ N-3
		for (int e = arr.length - 1; e > 0; e--) { // 0 ~ e
			for (int i = 0; i < e; i++) {
				if (arr[i] > arr[i + 1]) {
					swap(arr, i, i + 1);
				}
			}
		}
	}
	public static int[] bubbleSort1(int[] arr) {
		for (int i = arr.length-1; i >= 0 ; i--) {
			for (int j = arr.length-1; j > arr.length-1-i ; j--) {
				if(arr[j] < arr[j-1]){
					swap(arr,j,j-1);
				}
			}
		}
		return arr;
	}

	public static int[]  bubbleSort2(int[] arr) {
		// 0 ~ N-1
		// 0 ~ N-2
		// 0 ~ N-3
		for (int e = arr.length - 1; e > 0; e--) { // 0 ~ e
			for (int i = 0; i < e; i++) {
				if (arr[i] > arr[i + 1]) {
					swap(arr, i, i + 1);
				}
			}
		}
		return arr;
	}
	// 交换arr的i和j位置上的值
	public static void swap(int[] arr, int i, int j) {
		/*
		* a和b内存地址一定是不同的 才能交换 要不然 得到的结果会有问题
		* int a = 甲 ,  int b = 乙
		* a = a ^ b  ==> a = 甲 ^ 乙 , b = 乙
		* b = a ^ b  ==> b = 甲 ^ 乙 ^ 乙 = 甲 ^ (乙 ^ 乙) = 甲 ^ 0 = 甲, a = 甲 ^ 乙
		* a = a ^ b  ==> a = 甲 ^ 乙 ^ 甲 = 甲 ^ 甲 ^ 乙 = 0 ^ 乙  = 乙 , b = 甲
		* */
		arr[i] = arr[i] ^ arr[j];
		arr[j] = arr[i] ^ arr[j];
		arr[i] = arr[i] ^ arr[j];
	}

	// for test
	public static void comparator(int[] arr) {
		Arrays.sort(arr);
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
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
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			bubbleSort(arr1);
			comparator(arr2);
			if (!isEqual(arr1, arr2)) {
				succeed = false;
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");

		int[] arr = generateRandomArray(maxSize, maxValue);
		printArray(arr);
		bubbleSort(arr);
		printArray(arr);
	}
/*	@Test
	public void test01(){
		int[] arr = {4,3,2,1,5,6};
		printArray(bubbleSort1(arr));
		printArray(bubbleSort2(arr));
	}*/
}
