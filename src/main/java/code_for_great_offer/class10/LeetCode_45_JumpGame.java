package code_for_great_offer.class10;

// 本题测试链接 : https://leetcode.cn/problems/jump-game-ii/
/**
 * 给K出MP一算组法正扩整展数题，目你二从第一个数向最后一个数方向跳跃， 每次至少跳跃1格， 每个数的值表示你
 * 从这个位置可以跳跃的最大长度。 计算如何以最少的跳跃次数跳到最后一个数。
 * 输入描述：
 * 第一行表示有多少个数n
 * 第二行开始依次是1到n个数， 一个数一行
 * 输出描述：
 * 输出一行， 表示最少跳跃的次数。
 * 输入
 *  7	2 3 2 1 2 1 5
 * 输出
 * 3
 */
public class LeetCode_45_JumpGame {

	public static int jump(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int step = 0; //跳的次数
		int cur = 0; //当前位置
		int next = 0; //跳的下一个最远位置
		for (int i = 0; i < arr.length; i++) {
			if (cur < i) {
				step++;
				cur = next;
			}
			next = Math.max(next, i + arr[i]);
		}
		return step;
	}

}
