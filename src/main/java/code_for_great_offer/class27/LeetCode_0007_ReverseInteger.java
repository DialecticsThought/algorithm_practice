package code_for_great_offer.class27;

/*
* 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
* 如果反转后整数超过 32 位的有符号整数的范围 [−231,  231 − 1] ，就返回 0。
* 假设环境不允许存储 64 位整数（有符号或无符号）。
* 示例 1：
* 输入：x = 123
* 输出：321
* 示例 2：
* 输入：x = -123
* 输出：-321
* 示例 3：
* 输入：x = 120
* 输出：21
* 示例 4：
* 输入：x = 0
 * 输出：0
* */
public class LeetCode_0007_ReverseInteger {
	//TODO 争取 用int 类型 而不是long类型
	public static int reverse(int x) {
		//TODO 得到 数字的符号位
		boolean neg = ((x >>> 31) & 1) == 1;
		// TODO 正数 和 负数 都当做 负数处理
		//因为负数的范围比正数的范围大
		x = neg ? x : -x;
		/*
		 *TODO 这个if判断 解决 res * 10 + cur的溢出问题
		 * 假设 最终结果 res = -21724 x = -4657
		 * 当前操作 是 x % 10 = -7
		 * 然后 res * 10 + -7
		 * 在  * 10 之前 res 已经 小于 Integer.MIN_VALUE / 10的话
		 * res * 10 一定溢出
		 * 如果  * 10 之前 res 已经 == Integer.MIN_VALUE / 10的话
		 * res * 10 不溢出 判断 x % 10的结果
		 * 因为 res == Integer.MIN_VALUE / 10 那么 res * 10 和 Integer.MIN_VALUE的区别就是 个位数
		 * 判断
		 * x % 10（-7） < Integer.MIN_VALUE % 10的话
		 * res * 10 + x % 10 一定溢出
		 * */
		int m = Integer.MIN_VALUE / 10;
		int o = Integer.MIN_VALUE % 10;
		int res = 0;
		while (x != 0) {
			/*
			* eg
			* x=-132
			* x % 10 = -3
			* x % 10 = -2
			* -3 * 10 + (-2) = -32
			* x % 10 = -1
			* -32 * 10 + (-1) = -321
			*
			* res < m 一定 溢出
			* res == m 的情况下 判断 x % 10 < Integer.MIN_VALUE % 10
			*
			 * */
			if (res < m || (res == m && x % 10 < o)) {
				return 0;
			}
			res = res * 10 + x % 10;
			x /= 10;
		}
		return neg ? res : Math.abs(res);
	}

}
