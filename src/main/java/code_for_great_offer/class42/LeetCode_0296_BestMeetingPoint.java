package code_for_great_offer.class42;

/*
*TODO
* 有一队人(两人或以上）想要在一个地方碰面，他们希望能够最小化他们的总行走距离。
* 给你一个2D网格，其中各个格子内的值要么是0，要么是1。
* 1表示某个人的家所处的位置。这里，我们将使用曼哈顿距离来计算，
* 其中 distance(p1,p2)= |p2.x - p1.x| + |p2.y - p1.y|。
* 输入:
* 1 - 0 - 0 - 0 - 1
* |   |   |   |   |
* 0 - 0 - 0 - 0 - 0
* |   |   |   |   |
* 0 - 0 - 1 - 0 - 0
* 输出:6
* 解析:给定的三个人分别住在(0,0)，(0,4)和(2,2):
* (0,2〉是一个最佳的碰面点，其总行走距离为2 + 2 + 2 = 6，最小，因此返回6。
*TODO
* 矩阵中的所有的1 汇聚到哪一个列j最大
* 矩阵中的所有的1 汇聚到哪一个行i最大
* 那么最右位置就是（i,j）
* 假设每一行有多少个1的统计已经做完了
* 假设第0行 有9个1 第5行有13个1
* 写成数组 9 22 15 3 10 12  有两个指针 一个指向第0行 一个指向第5行
* 指针1 和指针2比较 先9和13比较 9<13
* 指针1来到第1行 22+9 =31 > 13
* 指针2来到第4行 22+9 > 10 + 31
* 指针2来到第3行 22+9 > 10 + 31 + 3
* 指针2来到第2行 22+9 < 10 + 31 + 3 + 15
* 指针1来到第2行 此时指针1=指针2 确定了哪一行
* 同理列也是这样
* */
public class LeetCode_0296_BestMeetingPoint {

	public static int minTotalDistance(int[][] grid) {
		int N = grid.length;
		int M = grid[0].length;
		int[] iOnes = new int[N];//行有几个1
		int[] jOnes = new int[M];//列有几个1
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (grid[i][j] == 1) {
					iOnes[i]++;
					jOnes[j]++;
				}
			}
		}
		int total = 0;
		int i = 0;
		int j = N - 1;
		int iRest = 0;
		int jRest = 0;
		while (i < j) {
			if (iOnes[i] + iRest <= iOnes[j] + jRest) {
				total += iOnes[i] + iRest;
				iRest += iOnes[i++];
			} else {
				total += iOnes[j] + jRest;
				jRest += iOnes[j--];
			}
		}
		i = 0;
		j = M - 1;
		iRest = 0;
		jRest = 0;
		while (i < j) {
			if (jOnes[i] + iRest <= jOnes[j] + jRest) {
				total += jOnes[i] + iRest;
				iRest += jOnes[i++];
			} else {
				total += jOnes[j] + jRest;
				jRest += jOnes[j--];
			}
		}
		return total;
	}

}
