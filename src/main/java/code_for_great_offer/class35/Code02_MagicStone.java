package code_for_great_offer.class35;

import java.util.Arrays;
/*
*TODO
* 来自小红书
* [0,4,7] ： 0表示这里石头没有颜色，如果变红代价是4，如果变蓝代价是7
* [1,X,X] ： 1表示这里石头已经是红，而且不能改颜色，所以后两个数X无意义
* [2,X,X] ： 2表示这里石头已经是蓝，而且不能改颜色，所以后两个数X无意义
* 颜色只可能是0、1、2，代价一定>=0
* 给你一批这样的小数组，要求最后必须所有石头都有颜色，且红色和蓝色一样多，返回最小代价
* 如果怎么都无法做到所有石头都有颜色、且红色和蓝色一样多，返回-1
*/
public class Code02_MagicStone {
	/*
	*TODO
	* 假设 总共20个石头 一共红色8个 蓝色4个
	* 就说明 要2个红色 6个蓝色
	*TODO 贪心算法
	* 假设 总共4个无色石头 需要分配一个红色 3个蓝色
	* [0,2,4] -2
	* [0,6,3] 3
	* [0,1,7] -6
	* [0,9,2] 7
	* 先把4个石头 全部变成红色
	* 那么代价就是2+6+9+1=18
	* 然后查看拿哪3个石头变成蓝色最值
	* 就是第2个参数-第3个参数差值最大的那3个
	* 因为变成红色取决于 第2个参数
	* 因为变成蓝色取决于 第3个参数
	* */
	public static int minCost(int[][] stones) {
		int n = stones.length;
		if ((n & 1) != 0) {
			return -1;
		}
		Arrays.sort(stones, (a, b) -> a[0] == 0 && b[0] == 0 ? (b[1] - b[2] - a[1] + a[2]) : (a[0] - b[0]));
		int zero = 0;
		int red = 0;
		int blue = 0;
		int cost = 0;
		for (int i = 0; i < n; i++) {
			if (stones[i][0] == 0) {
				zero++;
				cost += stones[i][1];
			} else if (stones[i][0] == 1) {
				red++;
			} else {
				blue++;
			}
		}
		if (red > (n >> 1) || blue > (n >> 1)) {
			return -1;
		}
		blue = zero - ((n >> 1) - red);
		for (int i = 0; i < blue; i++) {
			cost += stones[i][2] - stones[i][1];
		}
		return cost;
	}

	public static void main(String[] args) {
		int[][] stones = { { 1, 5, 3 }, { 2, 7, 9 }, { 0, 6, 4 }, { 0, 7, 9 }, { 0, 2, 1 }, { 0, 5, 9 } };
		System.out.println(minCost(stones));
	}

}
