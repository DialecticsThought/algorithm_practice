package code_for_great_offer.class16;

import java.util.Arrays;
/**
 * 给定一个有序的正数数组arr和一个正数range， 如果可以自由选择arr中的数字， 想累加得
 * 到 1~range 范围上所有的数， 返回arr最少还缺几个数。
 * 【 举例】
 * arr = {1,2,3,7}， range = 15
 * 想累加得到 1~15 范围上所有的数， arr 还缺 14 这个数， 所以返回1
 * arr = {1,5,7}， range = 15
 * 想累加得到 1~15 范围上所有的数， arr 还缺 2 和 4， 所以返回2
 *
 * 给定一个已排序的正整数数组 nums ，和一个正整数 n 。从 [1, n] 区间内选取任意个数字补充到 nums 中，
 * 使得 [1, n] 区间内的任何数字都可以用 nums 中某几个数字的和来表示。
 * 请返回 满足上述要求的最少需要补充的数字个数 。
 * 示例 1:
 * 输入: nums = [1,3], n = 6
 * 输出: 1
 * 解释:
 * 根据 nums 里现有的组合 [1], [3], [1,3]，可以得出 1, 3, 4。
 * 现在如果我们将 2 添加到 nums 中， 组合变为: [1], [2], [3], [1,3], [2,3], [1,2,3]。
 * 其和可以表示数字 1, 2, 3, 4, 5, 6，能够覆盖 [1, 6] 区间里所有的数。
 * 所以我们最少需要添加一个数字。
 * 示例 2:
 * 输入: nums  = [1,5,10], n = 20
 * 输出: 2
 * 解释: 我们需要添加 [2,4]。
 * 示例 3:
 * 输入: nums = [1,2,2], n = 5
 * 输出: 0
 *TODO
 * eg:
 * 问：[1,5] aim =10 要搞定1~100所有数 需要多少个数
 * eg:
 * 问： 现在有一个空arr，aim=1000，要搞定1~1000，缺哪些数
 * 问能否搞定了1~1范围上的所有的数 本来只搞定了0~0，现在缺1，就加上1
 * 问能否搞定2，现在缺2，就加上2，累加上2之后，嫩搞定1~3范围上所有数
 * 问能否搞定4，现在缺4，就加上4，累加上4之后，嫩搞定1~7范围上所有数
 * 能否搞定8，现在缺8，就加上8，累加上8之后，嫩搞定1~15范围上所有数
 * .....
 * eg:
 * [4,5,17,39] aim=83
 * 如何经济的使用4，前提是已经搞定了1~3范围上所有的数，再使用4，就能搞定1~7范围上所有的数
 *   是否搞定1~3范围上所有的数
 * 		搞定了
 * 		没搞定，问现在缺几个数
 * 			缺1，加上1，	就能搞定1~1范围上所有的数，没搞定1~3范围上所有的数
 * 			缺2，加上2，	就能搞定1~1+2范围上所有的数	，搞定1~3范围上所有的数
 * 	 使用4，搞定1~7范围上所有的数
 * 如何经济的使用5，前提是已经搞定了1~4范围上所有的数，在使用5，就能搞定1~7+5范围上所有的数
 *   是否搞定1~4范围上所有的数
 * 		搞定了
 * 如何经济的使用17，前提是已经搞定了1~16范围上所有的数，再使用17，就能搞定1~16范围上所有的数
 *   是否搞定1~16范围上所有的数
 * 		搞定了
 * 		没搞定，问现在缺几个数
 * 			缺13，加上13，就能搞定1~12+13范围上所有的数，搞定1~25范围上所有的数
 * 	 使用17，搞定1~25+17范围上所有的数
 * 如何经济的使用39，前提是已经搞定了1~38范围上所有的数，再使用39，就能搞定1~42+39范围上所有的数
 *   是否搞定1~38范围上所有的数
 * 		搞定了
 * 	因为aim=83	前提是已经搞定了1~82范围上所有的数，现在之搞定了1~81范围上所有的数
 * 		所以加上82，那么能搞定1~81+82
 * eg:
 * [5,1006,3009,5400] aim =76
 * 如何经济的使用5，前提是已经搞定了1~4范围上所有的数，再使用4，就能搞定1~7范围上所有的数
 *   是否搞定1~4范围上所有的数
 * 		搞定了
 * 		没搞定，问现在缺几个数
 * 			......
 * 如何经济的使用1006，前提是已经搞定了1~1005范围上所有的数，再使用1006，就能搞定1~????范围上所有的数
 * 问题：搞定了[1~1006]，一定搞定了[1~76]，所以每一次扩充范围，都要检查是否达到了[1~76]
 */
public class Code03_MinPatches {

	// arr请保证有序，且正数  1~aim
	public static int minPatches(int[] arr, int aim) {
		int patches = 0; // 缺多少个数字
		long range = 0; // 已经完成了1 ~ range的目标
		Arrays.sort(arr);
		for (int i = 0; i != arr.length; i++) {
			// arr[i]
			// 要求：1 ~ arr[i]-1 范围被搞定！
			while (arr[i] - 1 > range) { // arr[i] 1 ~ arr[i]-1
				range += range + 1; // range + 1 是缺的数字
				patches++;
				if (range >= aim) {
					return patches;
				}
			}
			// 要求被满足了！
			range += arr[i];
			if (range >= aim) {
				return patches;
			}
		}
		while (aim >= range + 1) {
			range += range + 1;
			patches++;
		}
		return patches;
	}

	// 嘚瑟
	public static int minPatches2(int[] arr, int K) {
		int patches = 0; // 缺多少个数字
		int range = 0; // 已经完成了1 ~ range的目标
		for (int i = 0; i != arr.length; i++) {
			// 1~range
			// 1 ~ arr[i]-1
			while (arr[i] > range + 1) { // arr[i] 1 ~ arr[i]-1

				if (range > Integer.MAX_VALUE - range - 1) {
					return patches + 1;
				}

				range += range + 1; // range + 1 是缺的数字
				patches++;
				if (range >= K) {
					return patches;
				}
			}
			if (range > Integer.MAX_VALUE - arr[i]) {
				return patches;
			}
			range += arr[i];
			if (range >= K) {
				return patches;
			}
		}
		while (K >= range + 1) {
			if (K == range && K == Integer.MAX_VALUE) {
				return patches;
			}
			if (range > Integer.MAX_VALUE - range - 1) {
				return patches + 1;
			}
			range += range + 1;
			patches++;
		}
		return patches;
	}

	public static void main(String[] args) {
		int[] test = { 1, 2, 31, 33 };
		int n = 2147483647;
		System.out.println(minPatches(test, n));

	}

}
