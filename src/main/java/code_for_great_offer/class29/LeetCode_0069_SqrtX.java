package code_for_great_offer.class29;

/*
* 69. x 的平方根
给你一个非负整数 x ，计算并返回 x 的 算术平方根 。
由于返回类型是整数，结果只保留 整数部分 ，小数部分将被 舍去 。
注意：不允许使用任何内置指数函数和算符，例如 pow(x, 0.5) 或者 x ** 0.5 。
示例 1：
输入：x = 4
输出：2
示例 2：
输入：x = 8
输出：2
解释：8 的算术平方根是 2.82842..., 由于返回类型是整数，小数部分将被舍去。
*
* */
public class LeetCode_0069_SqrtX {

	// x一定非负，输入可以保证
	public static int mySqrt(int x) {
		if (x == 0) {
			return 0;
		}
		if (x < 3) {
			return 1;
		}
		// x >= 3
		long ans = 1;
		long L = 1;
		long R = x;
		long M = 0;
		while (L <= R) {
			M = (L + R) / 2;
			if (M * M <= x) {
				ans = M;
				L = M + 1;
			} else {
				R = M - 1;
			}
		}
		return (int) ans;
	}

}
