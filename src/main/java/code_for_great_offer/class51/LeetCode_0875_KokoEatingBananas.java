package code_for_great_offer.class51;

/*
*TODO
* 珂珂喜欢吃香蕉。这里有 n 堆香蕉，第 i 堆中有piles[i]根香蕉。
* 警卫已经离开了，将在 h 小时后回来。
* 珂珂可以决定她吃香蕉的速度 k （单位：根/小时）。
* 每个小时，她将会选择一堆香蕉，从中吃掉 k 根。
* 如果这堆香蕉少于 k 根，她将吃掉这堆的所有香蕉，然后这一小时内不会再吃更多的香蕉。
* 珂珂喜欢慢慢吃，但仍然想在警卫回来前吃掉所有的香蕉。
* 返回她可以在 h 小时内吃掉所有香蕉的最小速度 k（k 为整数）。
* 示例 1：
* 输入：piles = [3,6,7,11], h = 8
* 输出：4
* 示例 2：
* 输入：piles = [30,11,23,4,20], h = 5
* 输出：30
* 示例 3：
* 输入：piles = [30,11,23,4,20], h = 6
* 输出：23
* 链接：https://leetcode.cn/problems/koko-eating-bananas
*TODO
* [6 7 3 5 ....] k=5 每小时吃5根
* 那么
* 对于[0] 需要2小时吃完
* 对于[1] 需要2小时吃完
* 对于[2] 需要1小时吃完
* ...
* 最后求出整个时间T
* 但是  T 要 < h 并且 尽可能接近 T
* 本质 ===> 二分
* 比如
* 有个速度v1 求出 对应的时间a
* 有个速度v2 求出 对应的时间b
* 因为 v1 <= v2  ==> a >= b
* 这说明了单调性
* 先把 数组的max找出来 并且 吃香蕉速度 一定 < max
* 一旦 速度 = max 每小时的话 那是无效
* 假设 警察是T小时
* 那么 在 1 ~ max 取二分 得到mid = （1+max）/2
* 利用 v= mid 求出吃香蕉的时间 h
* 如果 h >= T, 那么右边mid+1~max去二分
* 如果 h <= T, 那么左边1~mid-1去二分
* */
public class LeetCode_0875_KokoEatingBananas {

	public static int minEatingSpeed(int[] piles, int h) {
		int L = 1;
		int R = 0;
		for (int pile : piles) {
			R = Math.max(R, pile);
		}
		int ans = 0;
		int M = 0;
		while (L <= R) {
			M = L + ((R - L) >> 1);
			if (hours(piles, M) <= h) {
				ans = M;
				R = M - 1;
			} else {
				L = M + 1;
			}
		}
		return ans;
	}

	public static long hours(int[] piles, int speed) {
		long ans = 0;
		int offset = speed - 1;
		for (int pile : piles) {
			ans += (pile + offset) / speed;
		}
		return ans;
	}

}
