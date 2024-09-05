package code_for_great_offer.class25;

// 本题测试链接 : https://leetcode.cn/problems/gas-station/
// 注意本题的实现比leetcode上的问法更加通用
// leetcode只让返回其中一个良好出发点的位置
// 本题是返回结果数组，每一个出发点是否是良好出发点都求出来了
// 得到结果数组的过程，时间复杂度O(N)，额外空间复杂度O(1)
public class LeetCode_134_GasStation {

	public static int canCompleteCircuit(int[] gas, int[] cost) {
		if (gas == null || gas.length == 0) {
			return -1;
		}
		if (gas.length == 1) {
			return gas[0] < cost[0] ? -1 : 0;
		}
		boolean[] good = stations(cost, gas);
		for (int i = 0; i < gas.length; i++) {
			if (good[i]) {
				return i;
			}
		}
		return -1;
	}
	//TODO 获得结果
	public static boolean[] stations(int[] cost, int[] gas) {
		if (cost == null || gas == null || cost.length < 2 || cost.length != gas.length) {
			return null;
		}
		int init = changeDisArrayGetInit(cost, gas);
		return init == -1 ? new boolean[cost.length] : enlargeArea(cost, init);
	}
	//TODO  变成纯能数组
	public static int changeDisArrayGetInit(int[] dis, int[] oil) {
		int init = -1;
		//TODO  变成纯能数组
		for (int i = 0; i < dis.length; i++) {
			dis[i] = oil[i] - dis[i];
			if (dis[i] >= 0) {
				init = i;
			}
		}
		//TODO  随便返回一个 dis[i]>0 的下标 作为初始点
		return init;
	}
	//TODO 扩充联通区 （逆时针） 扩不动就顺时针向前一个节点 继续逆时针扩
	public static boolean[] enlargeArea(int[] dis, int init) {
		boolean[] res = new boolean[dis.length];
		int start = init;
		int end = nextIndex(init, dis.length);
		int need = 0; //TODO 连接联通区开头节点的成本
		int rest = 0; //TODO 到达联通区结尾节点的时候剩余油量
		do {
			// 当前来到的start已经在连通区域中，可以确定后续的开始点一定无法转完一圈
			if (start != init && start == lastIndex(end, dis.length)) {
				break;
			}
			/**
			*TODO
			* 当前来到的start不在连通区域中，就扩充连通区域
			* eg:
			* start(5) ->  联通区的头部(7) -> need = 从0变成 2
			* start(-2) -> 联通区的头部(7) ->  need = 从0变成 9
			* */
			if (dis[start] < need) { //TODO  当前start无法接到连通区的头部
				need -= dis[start];
			} else { // 当前start可以接到连通区的头部，开始扩充连通区域的尾巴
				// start(7) -> 联通区的头部(5)
				//TODO  还清need之后剩下的值（油量）加到rest上
				rest += dis[start] - need;
				need = 0;
				while (rest >= 0 && end != start) {
					rest += dis[end];
					end = nextIndex(end, dis.length);
				}
				//TODO 如果连通区域已经覆盖整个环，当前的start是良好出发点，进入2阶段
				if (rest >= 0) {
					//TODO 真的找到了某个点 是良好出发点
					res[start] = true;
					//TODO 该点的逆时针之后的点 只需要关心是否接的上 该点即可
					connectGood(dis, lastIndex(start, dis.length), init, res);
					break;
				}
			}
			start = lastIndex(start, dis.length);
		} while (start != init);
		return res;
	}

	// 已知start的next方向上有一个良好出发点
	// start如果可以达到这个良好出发点，那么从start出发一定可以转一圈
	public static void connectGood(int[] dis, int start, int init, boolean[] res) {
		int need = 0;
		while (start != init) {
			if (dis[start] < need) {
				need -= dis[start];
			} else {
				res[start] = true;
				need = 0;
			}
			start = lastIndex(start, dis.length);
		}
	}
	//TODO 上一个节点下标
	public static int lastIndex(int index, int size) {
		return index == 0 ? (size - 1) : index - 1;
	}
	//TODO 下一个节点下标
	public static int nextIndex(int index, int size) {
		return index == size - 1 ? 0 : (index + 1);
	}

}
