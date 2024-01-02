package code_for_great_offer.class13;

// 本题测试链接 : https://leetcode.com/problems/super-washing-machines/
public class Code02_SuperWashingMachines {

	public static int findMinMoves(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int size = arr.length;
		int sum = 0;
		for (int i = 0; i < size; i++) {
			sum += arr[i];
		}
		if (sum % size != 0) {
			return -1;
		}
		int avg = sum / size;
		int leftSum = 0;
		int ans = 0;
		for (int i = 0; i < arr.length; i++) {
			/*
			* 每个位置arr[i] 该位置的左侧整体当前拥有的所有的衣服数量 与 avg*i（意思就是 左侧整体理论上 需要多少件衣服）的差值
			* 每个位置arr[i] 该位置的右侧整体当前拥有的所有的衣服数量 与 avg*i（意思就是 右侧整体理论上 需要多少件衣服）的差值
			* */
			int leftRest = leftSum - i * avg;
			int rightRest = (sum - leftSum - arr[i]) - (size - i - 1) * avg;
			if (leftRest < 0 && rightRest < 0) {
				//情况1
				ans = Math.max(ans, Math.abs(leftRest) + Math.abs(rightRest));
			} else {
				//情况2+3
				ans = Math.max(ans, Math.max(Math.abs(leftRest), Math.abs(rightRest)));
			}
			leftSum += arr[i];
		}

		return ans;
	}

}
