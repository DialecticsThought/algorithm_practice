package leetcode;

// 测试链接：https://leetcode.cn/problems/ugly-number-ii
/**
 * 一个数的因子仅仅包括2， 3， 5的数称为丑数。
 * 数字1特别对待也看作是丑数， 所以从1開始的10个丑数分别为1、 2、 3、 4、 5、 6、 8、 9、10、 12
 * 返回第n个丑数
 */
public class LeetCode_264_UglyNumber {

	public static boolean isUgly(int num) {
		while (num % 2 == 0) {
			num /= 2;
		}
		while (num % 3 == 0) {
			num /= 3;
		}
		while (num % 5 == 0) {
			num /= 5;
		}
		return num == 1;
	}

	public static int nthUglyNumber1(int n) {
		int find = 0;
		int num = 1;
		for (; find < n; num++) {
			if (isUgly(num)) {
				find++;
			}
		}
		return num - 1;
	}

	public static int nthUglyNumber2(int n) {
		int[] ugly = new int[n + 1];
		ugly[1] = 1;
		int i2 = 1;
		int i3 = 1;
		int i5 = 1;
		for (int i = 2; i <= n; i++) {
			ugly[i] = Math.min(Math.min(ugly[i2] * 2, ugly[i3] * 3), ugly[i5] * 5);
			if (ugly[i] == ugly[i2] * 2) {
				i2++;
			}
			if (ugly[i] == ugly[i3] * 3) {
				i3++;
			}
			if (ugly[i] == ugly[i5] * 5) {
				i5++;
			}
		}
		return ugly[n];
	}

}
