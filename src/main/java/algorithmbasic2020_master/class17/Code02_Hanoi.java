package algorithmbasic2020_master.class17;

import java.util.Stack;

public class Code02_Hanoi {
	/**
	*TODO
	* n个盘子 从 左 中 右  的左 移动到右
	* 分成3步
	* 让n-1个盘子 从左边 移动到中间
	* 第n个盘子 从左边移动到右边
	* 让n-1个盘子 从中间移动到 右边
	*
	* 让n-1个盘子 从左边 移动到中间 可以再分
	* 让n-2个盘子 从左边 移动到中间
	* 第n-1个盘子 从左边移动到右边
	* 让n-2个盘子 从中间移动到 右边
	* ....
	* */
	public static void hanoi1(int n) {
		leftToRight(n);
	}

	// 请把1~N层圆盘 从左 -> 右 这个是主函数
	public static void leftToRight(int n) {
		if (n == 1) { // base case
			System.out.println("Move 1 from left to right");
			return;
		}
		leftToMid(n - 1);
		System.out.println("Move " + n + " from left to right");
		midToRight(n - 1);
	}

	// 请把1~N层圆盘 从左 -> 中
	public static void leftToMid(int n) {
		if (n == 1) {
			System.out.println("Move 1 from left to mid");
			return;
		}
		leftToRight(n - 1);
		System.out.println("Move " + n + " from left to mid");
		rightToMid(n - 1);
	}
	// 请把1~N层圆盘 从右 -> 中
	public static void rightToMid(int n) {
		if (n == 1) {
			System.out.println("Move 1 from right to mid");
			return;
		}
		rightToLeft(n - 1);
		System.out.println("Move " + n + " from right to mid");
		leftToMid(n - 1);
	}

	public static void midToRight(int n) {
		if (n == 1) {
			System.out.println("Move 1 from mid to right");
			return;
		}
		midToLeft(n - 1);
		System.out.println("Move " + n + " from mid to right");
		leftToRight(n - 1);
	}

	public static void midToLeft(int n) {
		if (n == 1) {
			System.out.println("Move 1 from mid to left");
			return;
		}
		midToRight(n - 1);
		System.out.println("Move " + n + " from mid to left");
		rightToLeft(n - 1);
	}

	public static void rightToLeft(int n) {
		if (n == 1) {
			System.out.println("Move 1 from right to left");
			return;
		}
		rightToMid(n - 1);
		System.out.println("Move " + n + " from right to left");
		midToLeft(n - 1);
	}

	public static void hanoi2(int n) {
		if (n > 0) {
			func(n, "left", "right", "mid");
		}
	}
	/*
	* 不管左 中 右
	* 假设是 from 起点 to 目的  other 其他
	* 先让 1-N-1个 从from 移动到other
	* 再让 第N个 从from 移动到 to
	* 最后 让1-N-1 从other 移动到 to
	* */
	public static void func(int N, String from, String to, String other) {
		if (N == 1) { // base
			System.out.println("Move 1 from " + from + " to " + to);
		} else {
			/*
			* 这里主过程的to就变成了子过程的other
			* 意思就是 1-N-1个 起点是from 但是终点是 other  to其他的
			* */
			func(N - 1, from, other, to);
			System.out.println("Move " + N + " from " + from + " to " + to);
			/*
			 * 这里主过程的to就变成了子过程的other
			 * 意思就是 1-N-1个 起点是other 但是终点是 to from是其他
			 * */
			func(N - 1, other, to, from);
		}
	}

	public static class Record {
		public boolean finish1;
		public int base;
		public String from;
		public String to;
		public String other;

		public Record(boolean f1, int b, String f, String t, String o) {
			finish1 = false;
			base = b;
			from = f;
			to = t;
			other = o;
		}
	}

	public static void hanoi3(int N) {
		if (N < 1) {
			return;
		}
		Stack<Record> stack = new Stack<>();
		stack.add(new Record(false, N, "left", "right", "mid"));
		while (!stack.isEmpty()) {
			Record cur = stack.pop();
			if (cur.base == 1) {
				System.out.println("Move 1 from " + cur.from + " to " + cur.to);
				if (!stack.isEmpty()) {
					stack.peek().finish1 = true;
				}
			} else {
				if (!cur.finish1) {
					stack.push(cur);
					stack.push(new Record(false, cur.base - 1, cur.from, cur.other, cur.to));
				} else {
					System.out.println("Move " + cur.base + " from " + cur.from + " to " + cur.to);
					stack.push(new Record(false, cur.base - 1, cur.other, cur.to, cur.from));
				}
			}
		}
	}

	public static void main(String[] args) {
		int n = 3;
		hanoi1(n);
		System.out.println("============");
		hanoi2(n);
//		System.out.println("============");
//		hanoi3(n);
	}

}
