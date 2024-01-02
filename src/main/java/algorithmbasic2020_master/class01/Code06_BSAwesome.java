package algorithmbasic2020_master.class01;

public class Code06_BSAwesome {
	//4)局部最小值问题
	public static int getLessIndex(int[] arr) {
		if (arr == null || arr.length == 0) {
			return -1; // no exist
		}
		if (arr.length == 1 || arr[0] < arr[1]) {
			return 0;
		}
		if (arr[arr.length - 1] < arr[arr.length - 2]) {
			return arr.length - 1;
		}
		int left = 1;
		int right = arr.length - 2;
		int mid = 0;
		while (left < right) {
			mid = (left + right) / 2;
			if (arr[mid] > arr[mid - 1]) {
				//上一轮mid的左侧 继续
				right = mid - 1;
			} else if (arr[mid] > arr[mid + 1]) {
				//上一轮mid的侧 继续
				left = mid + 1;
			} else {
				return mid;
			}
		}
		return left;
	}

}
