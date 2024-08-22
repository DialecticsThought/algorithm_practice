package algorithmbasic2020_master.class08;

public class Code01_JumpGameII {

	/*
	 * 评测代码可以直接去leetcode搜索：Jump Game II
	 *
	 */

	public static int jump(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int jump = 0;//跳了几步
		int cur = 0;//jump步内 右边界
		int next = 0;//jump+1步内 右边界
		for (int i = 0; i < arr.length; i++) {
			if (cur < i) {
				jump++;
				cur = next;
			}
			next = Math.max(next, i + arr[i]);
		}
		return jump;
	}

}
