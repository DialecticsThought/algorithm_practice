package other.ad2.class02;

public class Code01_PathsToNums {

	public static void pathsToNums(int[] paths) {
		if (paths == null || paths.length == 0) {
			return;
		}
		// citiesPath -> distancesArray
		pathsToDistans(paths);

		// distancesArray -> numArray
		distansToNums(paths);
	}
	/*
	* 这个函数的都的数组还要变成统计数组
	* */
	public static void pathsToDistans(int[] paths) {
		int cap = 0;
		for (int i = 0; i != paths.length; i++) {
			if (paths[i] == i) {
				cap = i;
			} else if (paths[i] > -1) {
				int curI = paths[i];
				paths[i] = -1;
				int preI = i;
				//paths[curI] = curI 表示为首都
				while (paths[curI] != curI) {
					//paths[curI] > -1 是负数就往回走
					if (paths[curI] > -1) {
						int nextI = paths[curI];
						paths[curI] = preI;
						preI = curI;
						curI = nextI;
					} else {
						break;
					}
				}
				int value = paths[curI] == curI ? 0 : paths[curI];
				//往前走的逻辑
				while (paths[preI] != -1) {
					int lastPreI = paths[preI];
					paths[preI] = --value;
					curI = preI;
					preI = lastPreI;
				}
				paths[preI] = --value;
			}
		}
		//把首都对应的位置上的数变成0
		paths[cap] = 0;
	}

	public static void distansToNums(int[] disArr) {
		for (int i = 0; i != disArr.length; i++) {
			int index = disArr[i];
			if (index < 0) {
				disArr[i] = 0; // important
				while (true) {
					index = -index;
					if (disArr[index] > -1) {
						disArr[index]++;
						break;
					} else {
						int nextIndex = disArr[index];
						disArr[index] = 1;
						index = nextIndex;
					}
				}
			}
		}
		disArr[0] = 1;
	}

	public static void printArray(int[] arr) {
		if (arr == null || arr.length == 0) {
			return;
		}
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int[] paths = { 9, 1, 4, 9, 0, 4, 8, 9, 0, 1 };
		printArray(paths);
		pathsToNums(paths);
		printArray(paths);

	}

}
