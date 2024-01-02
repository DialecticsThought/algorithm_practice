package code_for_great_offer.class35;

import java.util.PriorityQueue;

/* 来自网易
* map[i][j] == 0，代表(i,j)是海洋，渡过的话代价是2
* map[i][j] == 1，代表(i,j)是陆地，渡过的话代价是1
* map[i][j] == 2，代表(i,j)是障碍，无法渡过
* 每一步上、下、左、右都能走，返回从左上角走到右下角最小代价是多少，如果无法到达返回-1
 */
public class Code04_WalkToEnd {

	public static int minCost(int[][] map) {
		if (map[0][0] == 2) {
			return -1;
		}
		int n = map.length;
		int m = map[0].length;
		/*
		*TODO
		* 小根堆是根据代价排序的
		* [0][0]假设是0 代价=2
		* 此时该位置有2个选择分支
		* [0][1] 如果到来该位置 的代价 就是 <0,1,4> 放入小根堆
		* [1][0] 如果到来该位置 的代价 就是 <0,1,3> 放入小根堆
		* 小根堆此时就是 <0,1,3> <0,1,4>
		* 弹出堆首
		* 来到堆首的<row,col>位置 并把上下左右四个方向的 Node放入堆排序
		* 不断地弹出堆首 .....
		* 永远是在走最优代价的下一步，以最经济的方式走到右下角
		* 如果堆里的元素都空了 还没有走到右下角 就返回-1
		* */
		PriorityQueue<Node> heap = new PriorityQueue<>((a, b) -> a.cost - b.cost);
		//TODO 判断这个位置是否走过
		boolean[][] visited = new boolean[n][m];
		add(map, 0, 0, 0, heap, visited);
		while (!heap.isEmpty()) {
			Node cur = heap.poll();
			if (cur.row == n - 1 && cur.col == m - 1) {
				return cur.cost;
			}
			add(map, cur.row - 1, cur.col, cur.cost, heap, visited);
			add(map, cur.row + 1, cur.col, cur.cost, heap, visited);
			add(map, cur.row, cur.col - 1, cur.cost, heap, visited);
			add(map, cur.row, cur.col + 1, cur.cost, heap, visited);
		}
		return -1;
	}

	public static void add(int[][] m, int i, int j, int pre, PriorityQueue<Node> heap, boolean[][] visited) {
		if (i >= 0 && i < m.length && j >= 0 && j < m[0].length && m[i][j] != 2 && !visited[i][j]) {
			heap.add(new Node(i, j, pre + (m[i][j] == 0 ? 2 : 1)));
			visited[i][j] = true;
		}
	}

	public static class Node {
		public int row;
		public int col;
		public int cost;

		public Node(int a, int b, int c) {
			row = a;
			col = b;
			cost = c;
		}
	}

}
