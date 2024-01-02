package algorithmbasic2020_master.class06;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Code03_HeapSort {

	// 堆排序额外空间复杂度O(1)
	public static void heapSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		// O(N*logN)
//		for (int i = 0; i < arr.length; i++) { // O(N)
//			heapInsert(arr, i); // O(logN)
//		}
		/*
		* 不同与先前 这里的方法是从后往前
		* 也就是二叉树的最后一层最有一个子树 向前推
		* O(N)
		* */
		for (int i = arr.length - 1; i >= 0; i--) {
			heapify(arr, i, arr.length);
		}
		int heapSize = arr.length;
		//一个大根堆 最大值（下标为0的元素） 和最后一个元素 交换
		// 最后一个元素 不用管了
		swap(arr, 0, --heapSize);
		// O(N*logN)
		/*
		* [最大值,........,最后一个元素] 0~N一开始
		* [最后一个元素,........,最大值] 交换完 之后 只要对0~N-1个元素堆排序
		* 后面再交换一次 对0~N-2个元素堆排序
		* 后面再交换一次 对0~N-3个元素堆排序 .....
		* */
		while (heapSize > 0) { // O(N)
			heapify(arr, 0, heapSize); // O(logN)
			heapSize--;
			swap(arr, 0, heapSize); // O(1)
		}
	}
	/*
	 * 新加进来的数，现在停在了index位置，请依次往上移动，
	 * 移动到0位置，或者干不掉自己的父亲了，停！
	 * */
	// arr[index]刚来的数，往上
	public static void heapInsert(int[] arr, int index) {
		/*
		 * [index]    [index-1]/2
		 * index == 0
		 * 判断当前节点和父节点的大小
		 * 当前节点大于父节点 的话 当前节点与父节点的位置交换
		 * 再判断当前节点与新的父节点的大小 如果当前节点大于父节点 的话 执行上面相同的操作
		 * 直到当前节点达到了最顶端
		 * */
		while (arr[index] > arr[(index - 1) / 2]) {
			swap(arr, index, (index - 1) / 2);
			index = (index - 1) / 2;
		}
	}

	// arr[index]位置的数，能否往下移动
	/*
	 * 从index位置，往下看，不断的下沉
	 * 停：较大的孩子都不再比index位置的数大；已经没孩子了
	 * eg：[3,8,7,6] 头节点3的左右子节点分别是 8 7
	 * 头节点和 左右子节点中最大的节点比较 如果 头节点 < 左右子节点中最大的节点 则 交换
	 * 得到 [8,3,7,6] 3这个节点 和左右子节点的最大值比较 交换
	 * [8,6,7,3]  heapSize发现3这个节点没有右子节点 越界了
	 * */
	public static void heapify(int[] arr, int index, int heapSize) {
		int left = index * 2 + 1; // 左孩子的下标
		while (left < heapSize) { // 下方还有孩子的时候
			/*
			 * 把较大孩子的下标，给largest
			 * 有右子节点 且 右子节点 > 左子节点 当前节点和右子节点交换
			 * 有左子节点 且 左子节点 > 右子节点 当前节点和左子节点交换
			 * 有无 左右子节点 查看 heapSize和right/left关系
			 * */
			// 两个孩子中，谁的值大，把下标给largest
			// 1）只有左孩子，left -> largest
			// 2) 同时有左孩子和右孩子，右孩子的值<= 左孩子的值，left -> largest
			// 3) 同时有左孩子和右孩子并且右孩子的值> 左孩子的值， right -> largest
			int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
			// 父和较大的孩子之间，谁的值大，把下标给largest
			/*
			 * 选出了 左右子节点中的最大子节点的下标
			 * 接下来 该下标的元素 和 父节点(当前节点)元素比较
			 * 哪个大 把哪一个元素的下标赋值给largest
			 * */
			largest = arr[largest] > arr[index] ? largest : index;
			if (largest == index) {//该下标的元素 和 父节点(当前节点)元素比较 父节点大 就结束
				break;
			}
			swap(arr, largest, index);// index和较大孩子，要互换
			index = largest;
			left = index * 2 + 1;
		}
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
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

		// 默认小根堆
		PriorityQueue<Integer> heap = new PriorityQueue<>();
		heap.add(6);
		heap.add(8);
		heap.add(0);
		heap.add(2);
		heap.add(9);
		heap.add(1);

		while (!heap.isEmpty()) {
			System.out.println(heap.poll());
		}

		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			heapSort(arr1);
			comparator(arr2);
			if (!isEqual(arr1, arr2)) {
				succeed = false;
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");

		int[] arr = generateRandomArray(maxSize, maxValue);
		printArray(arr);
		heapSort(arr);
		printArray(arr);
	}

}
