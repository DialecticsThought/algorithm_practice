package code_for_great_offer.class06;

import java.util.ArrayList;
import java.util.HashMap;

public class Code04_MostXorZero {

	// 暴力方法
	public static int comparator(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		int[] eor = new int[N];
		eor[0] = arr[0];
		for (int i = 1; i < N; i++) {
			eor[i] = eor[i - 1] ^ arr[i];
		}
		return process(eor, 1, new ArrayList<>());
	}

	// index去决定：前一坨部分，结不结束！
	// 如果结束！就把index放入到parts里去
	// 如果不结束，就不放
	public static int process(int[] eor, int index, ArrayList<Integer> parts) {
		int ans = 0;
		if (index == eor.length) {
			parts.add(eor.length);
			ans = eorZeroParts(eor, parts);
			//
			/*
			*TODO 深度优先还原现场
			* 假设 已经在 2,3之间 5,6之间  7，8之间 做了划分 =》 {3,6,8}
			* 现在 arr还差9和10位置没做选择
			* 情况1 9,10位置之间做划分 =》 {3,6,8,10}
			* 情况2之前 还原现场 {3,6,8,10} =》 {3,6,8}
			* 情况2
			* */
			parts.remove(parts.size() - 1);
		} else {
			//TODO 决定1 当前遍历到的位置index 不与 index-1之间做划分 让下一轮 决定 index + 1和index之间做划分
			int p1 = process(eor, index + 1, parts);
			//TODO 决定2 当前遍历到的位置index 与 index-1之间做划分  让下一轮 决定 index + 1和index之间做划分
			parts.add(index);
			int p2 = process(eor, index + 1, parts);
			//TODO 决定2的结果删去 深度优先还原
			parts.remove(parts.size() - 1);
			ans = Math.max(p1, p2);
		}
		return ans;
	}
	//TODO  parts表示哪些位置要做划分 eor异或前缀数组
	public static int eorZeroParts(int[] eor, ArrayList<Integer> parts) {
		int L = 0;
		int ans = 0;
		for (Integer end : parts) {
			if ((eor[end - 1] ^ (L == 0 ? 0 : eor[L - 1])) == 0) {
				ans++;
			}
			L = end;
		}
		return ans;
	}

	// 时间复杂度O(N)的方法
	public static int mostXor(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		int[] dp = new int[N];//TODO 该数组的最后一个元素就是答案

		// key 某一个前缀异或和
		// value 这个前缀异或和上次出现的位置(最晚！)
		HashMap<Integer, Integer> map = new HashMap<>();
		map.put(0, -1);//TODO 一个数也没有 就有异或和0了 出现的位置为-1
		//代表 0~i整体的异或和
		int xor = 0;
		for (int i = 0; i < N; i++) {
			xor ^= arr[i];
			if (map.containsKey(xor)) { // 可能性2  之前出现过这个异或和
				/*
				*TODO 最近出现的位置为pre  说明 pre+1~i 范围是最后一个arr 实现异或和为0的部分
				* 那么 dp[pre]+1
				* 如果pre是-1 那说明 只有一个arr是异或和为0的部分 那就是 0~i整体
				* */
				int pre = map.get(xor);
				dp[i] = pre == -1 ? 1 : (dp[pre] + 1);
			}
			if (i > 0) {
				dp[i] = Math.max(dp[i - 1], dp[i]);
			}
			map.put(xor, i);
		}
		return dp[N - 1];
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
		int testTime = 150000;
		int maxSize = 12;
		int maxValue = 5;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(maxSize, maxValue);
			int res = mostXor(arr);
			int comp = comparator(arr);
			if (res != comp) {
				succeed = false;
				printArray(arr);
				System.out.println(res);
				System.out.println(comp);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");
	}

}
