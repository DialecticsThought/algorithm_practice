package other.ad2.class02;

public class Code02_CandyProblem {

	public static int candy1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int index = nextMinIndex1(arr, 0);
		int res = rightCands(arr, 0, index++);
		int lbase = 1;
		int next = 0;
		int rcands = 0;
		int rbase = 0;
		while (index != arr.length) {
			if (arr[index] > arr[index - 1]) {
				res += ++lbase;
				index++;
			} else if (arr[index] < arr[index - 1]) {
				next = nextMinIndex1(arr, index - 1);
				rcands = rightCands(arr, index - 1, next++);
				rbase = next - index + 1;
				res += rcands + (rbase > lbase ? -lbase : -rbase);
				lbase = 1;
				index = next;
			} else {
				res += 1;
				lbase = 1;
				index++;
			}
		}
		return res;
	}

	public static int nextMinIndex1(int[] arr, int start) {
		for (int i = start; i != arr.length - 1; i++) {
			if (arr[i] <= arr[i + 1]) {
				return i;
			}
		}
		return arr.length - 1;
	}

	public static int rightCands(int[] arr, int left, int right) {
		int n = right - left + 1;
		return n + n * (n - 1) / 2;
	}

	public static int candy2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int index = nextMinIndex2(arr, 0);
		int[] data = rightCandsAndBase(arr, 0, index++);
		int res = data[0];
		int lbase = 1;
		int same = 1;
		int next = 0;
		while (index != arr.length) {
			if (arr[index] > arr[index - 1]) {
				res += ++lbase;
				same = 1;
				index++;
			} else if (arr[index] < arr[index - 1]) {
				next = nextMinIndex2(arr, index - 1);
				data = rightCandsAndBase(arr, index - 1, next++);
				if (data[1] <= lbase) {
					res += data[0] - data[1];
				} else {
					res += -lbase * same + data[0] - data[1] + data[1] * same;
				}
				index = next;
				lbase = 1;
				same = 1;
			} else {
				res += lbase;
				same++;
				index++;
			}
		}
		return res;
	}

	public static int nextMinIndex2(int[] arr, int start) {
		for (int i = start; i != arr.length - 1; i++) {
			if (arr[i] < arr[i + 1]) {
				return i;
			}
		}
		return arr.length - 1;
	}

	public static int[] rightCandsAndBase(int[] arr, int left, int right) {
		int base = 1;
		int cands = 1;
		for (int i = right - 1; i >= left; i--) {
			if (arr[i] == arr[i + 1]) {
				cands += base;
			} else {
				cands += ++base;
			}
		}
		return new int[] { cands, base };
	}

	public static void main(String[] args) {
		int[] test1 = { 3, 0, 5, 5, 4, 4, 0 };
		System.out.println(candy1(test1));

		int[] test2 = { 3, 0, 5, 5, 4, 4, 0 };
		System.out.println(candy2(test2));
	}

}
