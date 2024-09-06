package code_for_great_offer.class32;

import java.util.HashSet;
import java.util.TreeSet;

/*
 * 编写一个算法来判断一个数 n 是不是快乐数。
* 「快乐数」定义为：
* 对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和。
* 然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。
* 如果这个过程 结果为1，那么这个数就是快乐数。
* 如果 n 是 快乐数 就返回 true ；不是，则返回 false 。
* 示例 1：
* 输入：n = 19
* 输出：true
* 解释：
* 12 + 92 = 82
* 82 + 22 = 68
* 62 + 82 = 100
* 12 + 02 + 02 = 1
* 链接：https://leetcode.cn/problems/happy-number\
* EG：num = 14
* 14 -> 1^2 + 4^2 = 17
* 17 -> 1^2 + 7^2 = 50
* 50 -> 5^2 + 0^2 =25
* .......
* EG：num = 13
* 13 -> 1^2 + 3^2 = 10
* 10 -> 1^0 + 0^2 = 0
* */
public class LeetCode_0202_HappyNumber {
	/*
	*TODO
	* 结论：不管什么数 再拆分的过程中 一定会遇到 1 或 4
	* 遇到4 就不是快乐数
	* 遇到1 就是快乐数
	* 不用数学证明
	* */
	public static boolean isHappy1(int n) {
		//TODO 把之前做的结果存进来 如果之后还出现了set中的结果就说明 n不是快乐数
		HashSet<Integer> set = new HashSet<>();
		while (n != 1) {
			int sum = 0;
			while (n != 0) {
				int r = n % 10;
				sum += r * r;
				n /= 10;
			}
			n = sum;
			if (set.contains(n)) {
				break;
			}
			set.add(n);
		}
		return n == 1;
	}

	// 实验代码
	public static TreeSet<Integer> sum(int n) {
		TreeSet<Integer> set = new TreeSet<>();
		while (!set.contains(n)) {
			set.add(n);
			int sum = 0;
			while (n != 0) {
				sum += (n % 10) * (n % 10);
				n /= 10;
			}
			n = sum;
		}
		return set;
	}

	public static boolean isHappy2(int n) {
		while (n != 1 && n != 4) {
			int sum = 0;
			while (n != 0) {
				sum += (n % 10) * (n % 10);
				n /= 10;
			}
			n = sum;
		}
		return n == 1;
	}

	public static void main(String[] args) {
		for (int i = 1; i < 1000; i++) {
			System.out.println(sum(i));
		}
	}

}
