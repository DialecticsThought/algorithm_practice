package code_for_great_offer.class36;

import java.util.ArrayList;
import java.util.HashMap;

// 来自三七互娱
// Leetcode原题 : https://leetcode.cn/problems/bus-routes/
public class Code11_BusRoutes {
/*
*TODO
* 给你一个数组 routes ，表示一系列公交线路，其中每个 routes[i] 表示一条公交线路，第 i 辆公交车将会在上面循环行驶。
* 例如，路线 routes[0] = [1, 5, 7] 表示第 0 辆公交车会一直按序列 1 -> 5 -> 7 -> 1 -> 5 -> 7 -> 1 -> ... 这样的车站路线行驶。
* 现在从 source 车站出发（初始时不在公交车上），要前往 target 车站。 期间仅可乘坐公交车。
* 求出 最少乘坐的公交车数量 。如果不可能到达终点车站，返回 -1 。
* 示例 1：
* 输入：routes = [[1,2,7],[3,6,7]], source = 1, target = 6
* 输出：2
* 解释：最优策略是先乘坐第一辆公交车到达车站 7 , 然后换乘第二辆公交车到车站 6 。
* 示例 2：
* 输入：routes = [[7,12],[4,5,15],[6],[15,19],[9,12,13]], source = 15, target = 12
* 输出：-1
* 来源：力扣（LeetCode）
* 链接：https://leetcode.cn/problems/bus-routes
*TODO
* 类似一个图
* routes的每一个元素 就是一个线路
*  一个线路就是一个环 一个个环交错在一起 形成一个特殊的图
* 每一个线路就是一个数组 数组的每个元素 就是一个车站的编号
* 根据线路做宽度优先
* 并且 每一个车站 拥有信息 就是 它属于哪些线路
* 题目会给一个source 来源 也就是针对某一个线路的某一个车站作为出发点
* 把这个车站对应的个线路都遍历一遍，查看目标车站在不在当前遍历的线路中，不在的话，找到哪个车站拥有除了当前线路之外的线路 ，那么这些车站选出来，作为下一轮遍历的车站
* 把上一轮选出来的车站 拿出来 把这些车站对应的线路都遍历一遍 查看目标车站在不在当前遍历的线路中，不在的话， 找到哪个车站拥有除了当前线路之外的线路
* ....
* 不断循环 直到 找到 目标车站被遍历到 那么遍历过的线路的数量就是答案
 * */
	// 0 : [1,3,7,0]
	// 1 : [7,9,6,2]
	// ....
	// 返回：返回换乘几次+1 -> 返回一共坐了多少条线的公交。
	public static int numBusesToDestination(int[][] routes, int source, int target) {
		if (source == target) {
			return 0;
		}
		int n = routes.length;
		/*
		* key : 车站 也就是route[i][j]
		* value : list -> 该车站拥有哪些线路！ 所处哪些route[x] route [y] ...
		* */
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < routes[i].length; j++) {
				if (!map.containsKey(routes[i][j])) {
					map.put(routes[i][j], new ArrayList<>());
				}
				map.get(routes[i][j]).add(i);
			}
		}
		/*
		*TODO
		* 记录 线路的 bfs
		* 一次 遍历 树/图的 一层
		* 搞定一层 ，生成 树/图下一层
		* */
		ArrayList<Integer> queue = new ArrayList<>();
		//TODO 记录哪些线路 被遍历过 防止重复遍历
		boolean[] set = new boolean[n];
		for (int route : map.get(source)) {
			queue.add(route);
			set[route] = true;
		}
		//TODO 第一层 初始
		int len = 1;
		while (!queue.isEmpty()) {
			//TODO 用来存放 当前这一轮 所生成 的树/图下一层
			ArrayList<Integer> nextLevel = new ArrayList<>();
			//TODO 遍历 队列里的元素（线路 即 一维数组）
			for (int route : queue) {
				int[] bus = routes[route];
				//TODO 遍历 某一个线路里面的所有车站
				for (int station : bus) {
					//TODO 遍历到的车站 是目标
					if (station == target) {
						return len;
					}
					//TODO 从缓存中 找 该车站的其他线路 并遍历
					for (int nextRoute : map.get(station)) {
						//TODO 如果当前遍历到的车站还有其他线路
						if (!set[nextRoute]) {
							nextLevel.add(nextRoute);
							set[nextRoute] = true;
						}
					}
				}
			}
			// 下一轮循环 就是 当前这一轮 所生成 的树/图下一层
			queue = nextLevel;
			len++;
		}
		return -1;
	}

}
