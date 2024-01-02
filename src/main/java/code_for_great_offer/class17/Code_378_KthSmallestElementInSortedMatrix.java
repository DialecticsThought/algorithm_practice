package code_for_great_offer.class17;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *TODO
 * 本题测试链接 : https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/
 * 给定一个每一行有序、每一列也有序，整体可能无序的二维数组在给定一个正数k,
 * 返回二维数组中，最小的第k个数
 * 这个题目 转化成 < xxx的个数为k
 * 1.先问如果找到 <= k的数 的个数 有几个 假设 k = 100 求 <=100
 * 如果有一个矩阵arr[m][n]
 * 也是从右上角开始 如果是arr[0][n]=120 120>= 100 又因为每一列有序，
 * 说明 该列 <=100的个数 为 0，来到120的左侧一列 也就是arr[0][n-1] ,假设 arr[0][n-1] = 90
 * 因为 90<= 100 ，并且 每一行有序 也就是说 arr[0][0]~arr[0][n-1] 都是<=100 记录下来个数m
 * 来到90的下一行格子，假设是arr[1][n-1] = 100,那么说明<=100的个数右从0开始到该行的100，即arr[1][0]~arr[1][n-1]
 * 来到100的下一行格子，假设是arr[2][n-1] = 110,那么说明arr[2][n-1]~ arr[m][n-1] 都是>=100,来到110的左侧一列 也就是arr[2][n-2]
 * 总结 矩阵arr[m][n]
 * 当前节点[x][y]
 * 	1.判断[x][y+1]是否<=100
 * 		1.1是，往下走，来到[x][y+1]，得到[0][y+1]~[x][y+1]范围都是<=100 得到个数x+1，然后继续执行1
 * 		1.2不是，说明[x][y+1]~[x][n]都是>=100,
 * 			1.2.1向左走,判断[x-1][y]是否 < 100
 * 				1.2.1.1是，得到[0][y+1]~[x-1][y]范围都是<=100 得到个数x，然后继续执行1
 * 				1.2.1.2不是,继续执行1.2.1也就是向左走
 * 2.进一步的问题 求<=100最近的数
 * 如果有一个矩阵arr[m][n]
 * 也是从右上角开始 如果是arr[0][n]=120 120>= 100 又因为每一列有序，
 * 说明 该列 <=100的个数 为 0，来到120的左侧一列 也就是arr[0][n-1] ,假设 arr[0][n-1] = 90
 * 因为 90<= 100 ，有一个变量记录 <=100最近的数 = 90
 * 来到90的下一行格子，假设是arr[1][n-1] = 99，该变量记录 <=100最近的数 = 99
 * 来到90的下一行格子，假设是arr[2][n-1] = 113，那么向左走，来到arr[2][n-2]....
 * 2维数组中，arr[0][0]最小，arr[m][n]最大。设arr[0][0] = 0 arr[m][n] = 1000
 * 那么求第100小的数 一定在1~1000范围
 * 利用二分
 * (1+1000)/2 = 500 二分 去arr中查找arr有无500，没有500就找最近的数，因为arr中可能没有500,最接近500的是490
 * 问 <=500的个数有几个（转化成上面的问题），是有200个数，但是要求第100小
 * 所以在1~500做二分
 * 二分本质找到的是刚刚>=100个数的那个数，
 * 二分的过程会记录一个中间结果，如果<=100的话那么中间结果一定不是答案
 * 如果 > 100 那么就记录下来，下次判断有没有更好的
 * 最后，假设二分划到<=785的数是第100小的数 但可能arr中没有785，没有的话就找最接近785的数
 * eg:
 * 1 30 50
 * 7 33 52
 * 9 51 74
 * 问 <=55的个数有几个 并且arr中最接近55的是哪个
 * 从做右上角看 50<=55 说明 第0行的所有数<=55,已经有3个
 * 往下走 来到53 ，53 <= 55， 说明第1行的所有数都<=55
 * 往下走 来到74，74 > 55,那么向左走
 * 来到51，51 <= 55，那么 9,51都是<=55
 * 往下走，越界 结束
 * 也就是说  50 -> 52 -> 51，这个链中 最接近55的就是52
 * 假设 <= 55的个数有8个，但是<=55但有时最接近的是52，那么说明<= 52的个数有8个☆☆☆☆☆☆☆☆☆☆☆
 */
public class Code_378_KthSmallestElementInSortedMatrix {

	// 堆的方法
	public static int kthSmallest1(int[][] matrix, int k) {
		int N = matrix.length;
		int M = matrix[0].length;
		PriorityQueue<Node> heap = new PriorityQueue<>(new NodeComparator());
		boolean[][] set = new boolean[N][M];
		heap.add(new Node(matrix[0][0], 0, 0));
		set[0][0] = true;
		int count = 0;
		Node ans = null;
		while (!heap.isEmpty()) {
			ans = heap.poll();
			if (++count == k) {
				break;
			}
			int row = ans.row;
			int col = ans.col;
			if (row + 1 < N && !set[row + 1][col]) {
				heap.add(new Node(matrix[row + 1][col], row + 1, col));
				set[row + 1][col] = true;
			}
			if (col + 1 < M && !set[row][col + 1]) {
				heap.add(new Node(matrix[row][col + 1], row, col + 1));
				set[row][col + 1] = true;
			}
		}
		return ans.value;
	}

	public static class Node {
		public int value;
		public int row;
		public int col;

		public Node(int v, int r, int c) {
			value = v;
			row = r;
			col = c;
		}

	}

	public static class NodeComparator implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			return o1.value - o2.value;
		}

	}

	// 二分的方法
	public static int kthSmallest2(int[][] matrix, int k) {
		int N = matrix.length;
		int M = matrix[0].length;
		int left = matrix[0][0];
		int right = matrix[N - 1][M - 1];
		int ans = 0;
		while (left <= right) {
			int mid = left + ((right - left) >> 1);
			// <=mid 有几个 和 <= mid 在矩阵中真实出现的数，谁最接近mid
			Info info = noMoreNum(matrix, mid);
			if (info.num < k) {//该情况就是右侧去二分
				left = mid + 1;
			} else {//该情况是去左侧二分
				ans = info.near;//记录最靠近mid的数 因为mid可能不在数组中
				right = mid - 1;
			}
		}
		return ans;
	}

	public static class Info {
		public int near;
		public int num;

		public Info(int n1, int n2) {
			near = n1;
			num = n2;
		}
	}

	public static Info noMoreNum(int[][] matrix, int value) {
		int near = Integer.MIN_VALUE;
		int num = 0;
		int N = matrix.length;
		int M = matrix[0].length;
		int row = 0;
		int col = M - 1;
		while (row < N && col >= 0) {
			if (matrix[row][col] <= value) {
				near = Math.max(near, matrix[row][col]);
				num += col + 1;
				row++;
			} else {
				col--;
			}
		}
		return new Info(near, num);
	}

}
