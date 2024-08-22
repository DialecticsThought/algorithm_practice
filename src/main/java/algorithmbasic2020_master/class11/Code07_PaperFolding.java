package algorithmbasic2020_master.class11;

public class Code07_PaperFolding {

	public static void printAllFolds(int N) {
		process(1, N, true);
		System.out.println();
	}
	/*
	 *TODO
	 * 用一棵树来表示 折痕的话 会发现
	 * 一个节点的左子节点 一定是凹折痕  右子节点一定是凸折痕 ☆☆☆☆☆☆☆☆
	 * 虽然可以用树来表示 但是为了简单
	 * 折了1次 N=1 树有1层 2折了2次 N=2 也就是树有2层 。。。。
	 * i表示当前节点在第几层 这个节点在第i层，一共有N层，N固定不变的
	 * 这个节点如果是凹的话，down = T
	 * 这个节点如果是凸的话，down = F
	 * 函数的功能：中序打印以你想象的节点为头的整棵树！
	 * */
	public static void process(int i, int N, boolean down) {
		if (i > N) {
			return;
		}
		process(i + 1, N, true);
		System.out.print(down ? "凹 " : "凸 ");
		process(i + 1, N, false);
	}

	public static void main(String[] args) {
		int N = 4;
		printAllFolds(N);
	}
}
