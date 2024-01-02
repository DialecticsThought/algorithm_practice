package code_for_great_offer.class44;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Problem_0317_ShortestDistanceFromAllBuildings {

	// 如果grid中0比较少，用这个方法比较好
	public static int shortestDistance1(int[][] grid) {
		int ans = Integer.MAX_VALUE;
		int N = grid.length;
		int M = grid[0].length;
		int buildings = 0;
		Position[][] positions = new Position[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (grid[i][j] == 1) {
					buildings++;
				}
				positions[i][j] = new Position(i, j, grid[i][j]);
			}
		}
		if (buildings == 0) {
			return 0;
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				ans = Math.min(ans, bfs(positions, buildings, i, j));
			}
		}
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}
	/*
	 *TODO 有多张距离表的方法
	 * 每个点做一个bfs 不能走回头路 也就是上下左右 某个方向走一步 碰到障碍(遇到2)停下来
	 * eg:
	 * 1 1 0 0 0
	 * 0 2 2 2 0
	 * 2 1 1 0 0
	 * 1 0 0 0 0
	 * 对于上面的二维数组的arr[0][0]位置元素做bfs
	 * 0  1  2 3 4
	 * 1  X  X X 5
	 * X  9  8 7 6
	 * 11 10 9 8 7
	 * 对于上面的二维数组的arr[3][0]位置元素做bfs
	 * 11 10 9 8 7
	 * 12 X  X X 6
	 * X  2  3 4 5
	 * 0  1  2 3 4
	 * ....
	 * 有多少个1就有多少个二维表
	 * ....
	 * 这些二维表求完之后 每一个点把所有二维表的这个位置的值求和得到结果 把这些结果那个最小
	 *TODO 多了一个规则 就是 1和2都不能走  也就是说每一个1去走到所有的0的位置并生成距离表  ☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆
	 * eg:
	 * 1 0 0 1
	 * 0 0 2 1
	 * 1 0 1 0
	 * 对于上面的二维数组的arr[0][0]位置元素做bfs
	 * 0 1 2 x
	 * 1 2 x x
	 * x 3 x x
	 * */
	public static int bfs(Position[][] positions, int buildings, int i, int j) {
		if (positions[i][j].v != 0) {
			return Integer.MAX_VALUE;
		}
		HashMap<Position, Integer> levels = new HashMap<>();
		Queue<Position> queue = new LinkedList<>();
		Position from = positions[i][j];
		levels.put(from, 0);
		queue.add(from);
		int ans = 0;
		int solved = 0;
		while (!queue.isEmpty() && solved != buildings) {
			Position cur = queue.poll();
			int level = levels.get(cur);
			if (cur.v == 1) {
				ans += level;
				solved++;
			} else {
				add(queue, levels, positions, cur.r - 1, cur.c, level + 1);
				add(queue, levels, positions, cur.r + 1, cur.c, level + 1);
				add(queue, levels, positions, cur.r, cur.c - 1, level + 1);
				add(queue, levels, positions, cur.r, cur.c + 1, level + 1);
			}
		}
		return solved == buildings ? ans : Integer.MAX_VALUE;
	}

	public static class Position {
		public int r;
		public int c;
		public int v;

		public Position(int row, int col, int value) {
			r = row;
			c = col;
			v = value;
		}
	}

	public static void add(Queue<Position> q, HashMap<Position, Integer> l, Position[][] p, int i, int j, int level) {
		if (i >= 0 && i < p.length && j >= 0 && j < p[0].length && p[i][j].v != 2 && !l.containsKey(p[i][j])) {
			l.put(p[i][j], level);
			q.add(p[i][j]);
		}
	}

	// 如果grid中1比较少，用这个方法比较好
	public static int shortestDistance2(int[][] grid) {
		int N = grid.length;
		int M = grid[0].length;
		int ones = 0;
		int zeros = 0;
		Info[][] infos = new Info[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (grid[i][j] == 1) {
					infos[i][j] = new Info(i, j, 1, ones++);
				} else if (grid[i][j] == 0) {
					infos[i][j] = new Info(i, j, 0, zeros++);
				} else {
					infos[i][j] = new Info(i, j, 2, Integer.MAX_VALUE);
				}
			}
		}
		if (ones == 0) {
			return 0;
		}
		int[][] distance = new int[ones][zeros];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (infos[i][j].v == 1) {
					bfs(infos, i, j, distance);
				}
			}
		}
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < zeros; i++) {
			int sum = 0;
			for (int j = 0; j < ones; j++) {
				if (distance[j][i] == 0) {
					sum = Integer.MAX_VALUE;
					break;
				} else {
					sum += distance[j][i];
				}
			}
			ans = Math.min(ans, sum);
		}
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}

	public static class Info {
		public int r;
		public int c;
		public int v;
		public int t;

		public Info(int row, int col, int value, int th) {
			r = row;
			c = col;
			v = value;
			t = th;
		}
	}

	public static void bfs(Info[][] infos, int i, int j, int[][] distance) {
		HashMap<Info, Integer> levels = new HashMap<>();
		Queue<Info> queue = new LinkedList<>();
		Info from = infos[i][j];
		add(queue, levels, infos, from.r - 1, from.c, 1);
		add(queue, levels, infos, from.r + 1, from.c, 1);
		add(queue, levels, infos, from.r, from.c - 1, 1);
		add(queue, levels, infos, from.r, from.c + 1, 1);
		while (!queue.isEmpty()) {
			Info cur = queue.poll();
			int level = levels.get(cur);
			distance[from.t][cur.t] = level;
			add(queue, levels, infos, cur.r - 1, cur.c, level + 1);
			add(queue, levels, infos, cur.r + 1, cur.c, level + 1);
			add(queue, levels, infos, cur.r, cur.c - 1, level + 1);
			add(queue, levels, infos, cur.r, cur.c + 1, level + 1);
		}
	}

	public static void add(Queue<Info> q, HashMap<Info, Integer> l, Info[][] infos, int i, int j, int level) {
		if (i >= 0 && i < infos.length && j >= 0 && j < infos[0].length && infos[i][j].v == 0
				&& !l.containsKey(infos[i][j])) {
			l.put(infos[i][j], level);
			q.add(infos[i][j]);
		}
	}


	/*
	 *TODO  只有一张距离表的方法
	 * 如果不走回头路和不越界
	 * 需要2种表 原始arr 距离表
	 * 初始arr 例子
	 * 1 0 0 1 0
	 * 0 0 0 0 0
	 * 0 0 0 0 0
	 * 距离表初始
	 * 0 0 0 0 0
	 * 0 0 0 0 0
	 * 0 0 0 0 0
	 * 对于arr[0][0]的1而言 开始bfs 对于arr[0][0] 只能arr[0][0]是0的位置认为是路 但是只要经过arr[i][j]位置那么就会是-1
	 * 初始arr变成    距离表变成
	 * 1 -1 0 1 0   0 1 0 0 0
	 * -1 0 0 0 0   1 0 0 0 0
	 * 0  0 0 0 0   0 0 0 0 0
	 * 接下来分别从-1的位置出发 上下左右只能走0的位置
	 * 初始arr变成    距离表变成
	 * 1 -1 -1 1 0   0 1 2 0 0
	 * -1 -1 0 0 0   1 2 0 0 0
	 * -1  0 0 0 0   2 0 0 0 0
	 * 接下来分别从-1的位置出发 上下左右只能走0的位置
	 * 初始arr变成    距离表变成
	 * 1 -1 -1 1 0   0 1 2 0 0
	 * -1 -1 -1 0 0  1 2 3 0 0
	 * -1 -1 0 0 0   2 3 0 0 0
	 * 接下来分别从-1的位置出发 上下左右只能走0的位置
	 * 初始arr变成      距离表变成
	 * 1 -1 -1   1 0   0 1 2 0 0
	 * -1 -1 -1 -1 0   1 2 3 4 0
	 * -1 -1 -1  0 0   2 3 4 0 0
	 * 接下来分别从-1的位置出发 上下左右只能走0的位置
	 * 初始arr变成      距离表变成
	 * 1 -1 -1   1 0   0 1 2 0 0
	 * -1 -1 -1 -1 -1  1 2 3 4 5
	 * -1 -1 -1 -1 0   2 3 4 5 0
	 * 接下来分别从-1的位置出发 上下左右只能走0的位置
	 * 初始arr变成       距离表变成
	 * 1 -1 -1   1 -1   0 1 2 0 6
	 * -1 -1 -1 -1 -1   1 2 3 4 5
	 * -1 -1 -1 -1 -1   2 3 4 5 6
	 * 接下来对于arr[0][3]的1而言 开始bfs
	 * 但是 对于arr[0][3] 只能arr[0][3]是-1的位置认为是路 但是只要经过arr[i][j]位置那么就会是-1
	 * .....
	 * eg2:
	 * 初始arr 例子 距离表初始
	 * 1 0 0 0    0 0 0 0
	 * 0 0 0 0    0 0 0 0
	 * 0 0 0 1    0 0 0 0
	 * 对于arr[0][0]的1而言 开始bfs 对于arr[0][0] 只能arr[0][0]是0的位置认为是路 但是只要经过arr[i][j]位置那么就会是-1
	 * 1 -1 -1 -1    0 1 2 3
	 * -1 -1 -1 -1   1 2 3 4
	 * -1 -1 -1 1    2 3 4 0
	 * 对于arr[2][3]的1而言 开始bfs 对于arr[2][3]只能arr[2][3]是-1的位置认为是路 但是只要经过arr[i][j]位置那么就会是-2
	 * 接下来分别从-2的位置出发 上下左右只能走-1的位置  此时是+1
	 * 1 -1 -1 -1    0 1 2 3
	 * -1 -1 -1 -2   1 2 3 5
	 * -1 -1 -2 1    2 3 5 0
	 * 接下来分别从-2的位置出发 上下左右只能走-1的位置  此时是+2
	 * 1 -1 -1 -2    0 1 2 5
	 * -1 -1 -2 -2   1 2 5 5
	 * -1 -2 -2 1    2 5 5 0
	 * 接下来分别从-2的位置出发 上下左右只能走-1的位置  此时是+3
	 * 1 -1 -2 -2    0 1 5 5
	 * -1 -2 -2 -2   1 5 5 5
	 * -2 -2 -2 1    5 5 5 0
	 * 接下来分别从-2的位置出发 上下左右只能走-1的位置  此时是+4
	 * 1 -2 -2 -2    0 5 5 5
	 * -2 -2 -2 -2   5 5 5 5
	 * -2 -2 -2 1    5 5 5 0
	 * 如果还有位置是1 就从该位置开始 上下左右走-2的位置 每次走过一个位置 该位置的元素--变成-3
	 *TODO
	 * eg：
	 * arr     dist
	 * 1 0 2   0 0 0
	 * 0 2 0   0 0 0
	 * 2 0 1   0 0 0
	 * 接下来对于arr[0][0]的1而言 开始bfs 相邻位置的元素是0的位置认为是路 但是只要经过arr[i][j]位置那么就会是-1
	 * 1 -1 2   0 1 0
	 * -1 2 0   1 0 0
	 * 2  0 1   0 0 0
	 * 本轮结束
	 * 接下来对于arr[2][2]的1而言 开始bfs 相邻位置的元素是-1的位置认为是路 但是只要经过arr[i][j]位置那么就会是-2
	 * 但是arr[2][2]的相邻位置没有-1 说明 无法得到最优地点 因为没有联通
	 *TODO
	 * int[] trans = { 0, 1, 0, -1, 0 };
	 * [0,1]表示行+0 列+1 也就是往右走
	 * [1,0]表示行+1 列+0 也就是往下走
	 * [0,-1]表示行+0 列-1 也就是往左走
	 * [-1,0]表示行-1 列+0 也就是往上走
	 * 数组遍历到i位置 那么就把[i,i+1]作为一个方向
	 *TODO
	 * 方法三的大流程和方法二完全一样，从每一个1出发，而不从0出发
	 * 运行时间快主要是因为常数优化，以下是优化点：
	 * 1) 宽度优先遍历时，一次解决一层，不是一个一个遍历：
	 * int size = que.size();
	 * level++;
	 * for (int k = 0; k < size; k++) { ... }
	 * 2) pass的值每次减1何用？只有之前所有的1都到达的0，才有必要继续尝试的意思
	 * 也就是说，如果某个1，自我封闭，之前的1根本到不了现在这个1附近的0，就没必要继续尝试了
	 * if (nextr >= 0 && nextr < grid.length  && nextc >= 0 && nextc < grid[0].length
	 * && grid[nextr][nextc] == pass)
	 * 3) int[] trans = { 0, 1, 0, -1, 0 }; 的作用是迅速算出上、下、左、右
	 * 4) 如果某个1在计算时，它周围已经没有pass值了，可以提前宣告1之间是不连通的
	 * step = bfs(grid, dist, i, j, pass--, trans);
	 * if (step == Integer.MAX_VALUE) {
	 * return -1;
	 * }
	 * 5) 最要的优化，每个1到某个0的距离是逐渐叠加的，每个1给所有的0叠一次（宽度优先遍历）
	 * dist[nextr][nextc] += level;
	* */
	public static int shortestDistance3(int[][] grid) {
		int[][] dist = new int[grid.length][grid[0].length];
		int pass = 0;
		int step = Integer.MAX_VALUE;
		int[] trans = { 0, 1, 0, -1, 0 };
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 1) {
					//TODO dist距离表只有一张 从arr[i][j]开始走  哪个相邻位置的元素==pass是被认作是路
					step = bfs(grid, dist, i, j, pass--, trans);
					if (step == Integer.MAX_VALUE) {
						return -1;
					}
				}
			}
		}
		return step == Integer.MAX_VALUE ? -1 : step;
	}


	/*
	*TODO
	* 原始矩阵是grid，但是所有的路(原来是0)，被改了
	* 改成了啥？改成认为，pass才是路！原始矩阵中的1和2呢？不变！
	* dist，距离压缩表，之前的bfs，也就是之前每个1，走到某个0，总距离和都在dist里
	* row,col 宽度优先遍历的，原始出发点！
	* trans -> 炫技的，上下左右
	* 返回值代表，进行完这一遍bfs，压缩距离表中(dist)，最小值是谁？
	* 如果突然发现，无法联通！返回系统最大！
	* eg:
	* 1 0 1 0 2 1
	* 0 0 0 0 2 0
	* 0 0 0 0 2 0
	* 2 2 2 2 2 0
	* 对于arr[0][0]而言 临近的位置的元素0是路 他把原始arr变成-1
	* 1 -1  1 -1  2 1
	* -1 -1 -1 -1 2 0
	* -1 -1 -1 -1 2 0
	* 2   2  2  2 2 0
	* 对于arr[0][2]而言 临近的位置的元素-1是路 他把原始arr变成-2
	* 1  -2  1 -2 2 1
	* -2 -2 -2 -2 2 0
	* -2 -2 -2 -2 2 0
	* 2   2  2  2 2 0
	* 对于arr[0][5]而言 临近的位置的元素-2是路 他把原始arr变成-3
	* 但是发现临近的位置的元素是0 不是-2 说明无法联通 向上返回了系统最大
	* */
	public static int bfs(int[][] grid, int[][] dist, int row, int col, int pass, int[] trans) {
		//TODO 存放的元素是当前点的行和列
		Queue<int[]> que = new LinkedList<int[]>();
		//TODO 这个queue没有site来表点进去过没有进去过 因为原始数组的值会改变 而且pass是路
		que.offer(new int[] { row, col });
		int level = 0;
		int ans = Integer.MAX_VALUE;
		/*
		*TODO
		* bfs 也就是按层遍历 优化：一次搞出一层
		* eg： 队列里面 有[a b c] size=3
		* c出来的时候 加队列 e f g h
		* b出来的时候 x y z
		* a出来的时候 j h
		* 此时队列 [j h x y z e f g h] 此时队列的新的一层就出来 因为直到了size=3 只要弹出队列3个元素
		* eg: 0是路 arr原始的样子：
		* 1 0 0 0
		* 0 0 0 0
		* 0 0 0 0
		* 0 0 0 0
		* [0,0]先进入队列
		* 也就是说第0批次搞几个数 1个数 取决于当前队列长度
		* 弹出队列的1个元素[0,0] 当前位置的元素-- 判断当前该元素的邻接位置的元素哪个是0 进队列
		* 当前队列 [1,0] [0,1]
		* 第1批次搞几个数 2个数 取决于当前队列长度
		* 弹出队列的1个元素[0,1] 当前位置的元素-- 判断当前该元素的邻接位置的元素哪个是0 进队列
		* 当前队列 [1,1] [0,2] [1,0]
		* 弹出队列的1个元素[1,0] 当前位置的元素-- 判断当前该元素的邻接位置的元素哪个是0 进队列
		* 当前队列 [2,0] [1,1] [0,2]
		* 第2批次搞几个数 3个数 取决于当前队列长度
		* */
		while (!que.isEmpty()) {
			int size = que.size();//方便搞出一批
			level++;
			for (int k = 0; k < size; k++) {
				int[] node = que.poll();//TODO 弹出队列的一个节点
				for (int i = 1; i < trans.length; i++) { //TODO 当前位置 上下左右尝试
					//TODO 下一级点的行和列
					int nextr = node[0] + trans[i - 1];
					int nextc = node[1] + trans[i];
					//TODO 如果不越界 当前下一级点的位置是路的话 加入到队列
					if (nextr >= 0 && nextr < grid.length && nextc >= 0 && nextc < grid[0].length
							&& grid[nextr][nextc] == pass) {
						que.offer(new int[] { nextr, nextc });
						//TODO 把下一级点在距离表里面的值加上层数
						dist[nextr][nextc] += level;
						//TODO 距离表里面取出最小值
						ans = Math.min(ans, dist[nextr][nextc]);
						//TODO 更新原始表
						grid[nextr][nextc]--;
					}
				}
			}
		}
		return ans;
	}

}
