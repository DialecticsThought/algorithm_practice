package code_for_great_offer.class18;

import java.util.List;

public class Code01_HanoiProblem {
	public void hanota(List<Integer> A, List<Integer> B, List<Integer> C) {
		process1(A.size(),A,B,C);
	}
	/**
	 *TODO
	 * n个盘子 从 左 中 右  的左 移动到右
	 * 分成3步
	 * 让n-1个盘子 从左边 移动到中间
	 * 第n个盘子 从左边移动到右边
	 * 让n-1个盘子 从中间移动到 右边
	 *TODO
	 * 让n-1个盘子 从左边 移动到中间 可以再分
	 * 让n-2个盘子 从左边 移动到中间
	 * 第n-1个盘子 从左边移动到右边
	 * 让n-2个盘子 从中间移动到 右边
	 * ....
	 * */
	private void process1(int size, List<Integer> start, List<Integer> other, List<Integer> end) {
		if(size == 1){
			end.add(start.remove(start.size()-1));
		}else {
			// 将start上面n-1个通过end移到other
			process1(size - 1, start, end, other);
			// 将start最后一个移到end
			end.add(start.remove(start.size()-1));
			// 将other上面n-1个通过start移到end
			process1(size - 1, other, start, end);
		}
	}

	public static int step1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return -1;
		}
		return process(arr, arr.length - 1, 1, 2, 3);
	}

	// 目标是: 把0~i的圆盘，（对应的是arr[0~i]的值，一共i+1层塔）从from全部挪到to上
	// 返回，根据arr中的状态arr[0..i]，它是最优解的第几步？
	// f(i, 3 , 2, 1) f(i, 1, 3, 2) f(i, 3, 1, 2)
	public static int process(int[] arr, int i, int from, int other, int to) {
		if (i == -1) {
			return 0;
		}
		if (arr[i] != from && arr[i] != to) {
			return -1;
		}
		if (arr[i] == from) { // 第一大步没走完
			return process(arr, i - 1, from, to, other);
		} else { // arr[i] == to
			// 已经走完1，2两步了，
			int rest = process(arr, i - 1, other, from, to); // 第三大步完成的程度
			if (rest == -1) {
				return -1;
			}
			return (1 << i) + rest;
		}
	}

	public static int step2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return -1;
		}
		int from = 1;
		int mid = 2;
		int to = 3;
		int i = arr.length - 1;
		int res = 0;
		int tmp = 0;
		while (i >= 0) {
			if (arr[i] != from && arr[i] != to) {
				return -1;
			}
			if (arr[i] == to) {
				res += 1 << i;
				tmp = from;
				from = mid;
			} else {
				tmp = to;
				to = mid;
			}
			mid = tmp;
			i--;
		}
		return res;
	}

	public static int kth(int[] arr) {
		int N = arr.length;
		return step(arr, N - 1, 1, 3, 2);
	}

	// 0...index这些圆盘，arr[0..index] index+1层塔
	// 在哪？from 去哪？to 另一个是啥？other
	// arr[0..index]这些状态，是index+1层汉诺塔问题的，最优解第几步
	public static int step(int[] arr, int index, int from, int to, int other) {
		if (index == -1) {//没有圆盘的 base case
			return 0;
		}
		if (arr[index] == other) {//只要index的圆盘 在other位置上 就是不对
			return -1;
		}
		// arr[index] == from arr[index] == to;
		if (arr[index] == from) {
			//让 1~index1-1的圆盘 从当前位置到other位置
			return step(arr, index - 1, from, other, to);
		} else {
			int p1 = (1 << index) - 1;//第一步 0~i-1号圆盘从f走到other 需要2^index - 1步操作
			int p2 = 1;//i号圆盘从f走到t 算一步
			//0~i-1圆盘 从other 移动到t上
			int p3 = step(arr, index - 1, other, to, from);
			if (p3 == -1) {//说明p3不是整体最优解的某一步
				return -1;
			}
			return p1 + p2 + p3;
		}
	}

	public static void main(String[] args) {
		int[] arr = { 3, 3, 2, 1 };
		System.out.println(step1(arr));
		System.out.println(step2(arr));
		System.out.println(kth(arr));
		System.out.println(1<<2);
	}
}
