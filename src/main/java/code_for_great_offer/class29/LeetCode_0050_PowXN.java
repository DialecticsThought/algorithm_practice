package code_for_great_offer.class29;

public class LeetCode_0050_PowXN {
	/*
	* 50. Pow(x, n)
	* 实现 pow(x, n) ，即计算 x 的整数 n 次幂函数（即，xn ）。
	* 示例 1：
	* 输入：x = 2.00000, n = 10
	* 输出：1024.00000
	* 示例 2：
	* 输入：x = 2.10000, n = 3
	* 输出：9.26100
	* 示例 3：
	* 输入：x = 2.00000, n = -2
	* 输出：0.25000
	* 解释：2-2 = 1/22 = 1/4 = 0.25
	*
	* 提示：
	* -100.0 < x < 100.0
	* -231 <= n <= 231-1
	* n 是一个整数
	* -104 <= xn <= 104
	* */
	public static int pow(int a, int n) {
		int ans = 1;
		int t = a;
		// 什么时候 n 的二进制 位 被 拿光 循环就结束
		while (n != 0) {
			if ((n & 1) != 0) {
				ans *= t;
			}
			t *= t;
			/*
			* n向右 移动一位  也就是拿 n的最低位
			* 然后判断最低位是否为1
			* */
			n >>= 1;
		}
		return ans;
	}

	// x的n次方，n可能是负数
	public static double myPow(double x, int n) {
		if (n == 0) {
			return 1D;
		}
		int pow = Math.abs(n == Integer.MIN_VALUE ? n + 1 : n);
		double t = x;
		double ans = 1D;
		while (pow != 0) {
			/*
			* pow = 01100111
			* n   = 00000001
			* &结果= 00000001
			* */
			if ((pow & 1) != 0) {
				ans *= t;
			}
			pow >>= 1;
			t = t * t;
		}
		/*
		* 如果 n是负数 那么 先变成 整数 求出 res 最后 1/res
		* 但是系统最小值 是转换不了的
		* x^Math.abs(Integer.MIN_VALUE) = x^Integer.MAX_VALUE * x
		* x^Integer.MIN_VALUE = 1 / x^Math.abs(Integer.MIN_VALUE)
		* */
		if (n == Integer.MIN_VALUE) {
			ans *= x;
		}
		return n < 0 ? (1D / ans) : ans;
	}

}
