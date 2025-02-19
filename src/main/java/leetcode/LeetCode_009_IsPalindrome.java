package leetcode;

// 测试链接：
public class LeetCode_009_IsPalindrome {

	// n<0 不是回文数
	public static boolean isPalindrome(int n) {
		if (n < 0) {
			return false;
		}
		int help = 1;
		while (n / help >= 10) {
			help *= 10;
		}
		while (n != 0) {
			if (n / help != n % 10) {
				return false;
			}
			n = (n % help) / 10;
			help /= 100;
		}
		return true;
	}

	/**
	 * 初始值：
	 * n = 12321
	 * help = 1
	 * 第一次计算 help：
	 * n = 12321, 我们开始通过 n / help 来逐步增加 help，直到它等于 10000。
	 * 最终得到 help = 10000
	 * 第一次进入 while (n != 0)：
	 * n = 12321
	 * left = n / help = 12321 / 10000 = 1 // 最高位
	 * right = n % 10 = 12321 % 10 = 1 // 最低位
	 * left == right，继续去除最高位和最低位。
	 * n = n - left * help = 12321 - 1 * 10000 = 2321
	 * n = n / 10 = 2321 / 10 = 232
	 * 更新 help = help / 100 = 10000 / 100 = 100
	 * 第二次进入 while (n != 0)：
	 * n = 232
	 * left = n / help = 232 / 100 = 2 // 最高位
	 * right = n % 10 = 232 % 10 = 2 // 最低位
	 * left == right，继续去除最高位和最低位。
	 * n = n - left * help = 232 - 2 * 100 = 32
	 * n = n / 10 = 32 / 10 = 3
	 * 更新 help = help / 100 = 100 / 100 = 1
	 * 第三次进入 while (n != 0)：
	 * n = 3
	 * left = n / help = 3 / 1 = 3 // 最高位
	 * right = n % 10 = 3 % 10 = 3 // 最低位
	 * left == right，继续去除最高位和最低位。
	 * n = n - left * help = 3 - 3 * 1 = 0
	 * n = n / 10 = 0 / 10 = 0
	 * 更新 help = help / 100 = 1 / 100 = 0，但此时 n == 0，循环结束。
	 * @param n
	 * @return
	 */
	public static boolean isPalindrome2(int n) {
		if (n < 0) {
			return false;
		}
		int help = 1;
		// 计算 help 的值，使其为 10 的幂
		while (true) {
			int temp = n / help;
			if (temp < 10) {
				break;
			}
			help = help * 10;
		}
		// 开始检查数字的两端
		while (n != 0) {
			int left = n / help;   // 获取最高位数字
			int right = n % 10;    // 获取最低位数字
			// 比较最高位和最低位
			if (left != right) {
				return false;
			}
			// 移除最高位和最低位
			n = n - left * help;   // 移除最高位
			n = n / 10;            // 移除最低位
			// 更新 help 为减少两位
			help = help / 100;
		}

		return true;
	}

}
