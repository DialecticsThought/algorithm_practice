package code_for_great_offer.class38;
/*
*TODO
* 360笔试题
* 长城守卫军
* 题目描述：
* 长城上有连成一排的n个烽火台，每个烽火台都有士兵驻守。
* 第i个烽火台驻守着ai个士兵，相邻峰火台的距离为1。另外，有m位将军，
* 每位将军可以驻守一个峰火台，每个烽火台可以有多个将军驻守，
* 将军可以影响所有距离他驻守的峰火台小于等于x的烽火台。
* 每个烽火台的基础战斗力为士兵数，另外，每个能影响此烽火台的将军都能使这个烽火台的战斗力提升k。
* 长城的战斗力为所有烽火台的战斗力的最小值。
* 请问长城的最大战斗力可以是多少？
* 输入描述
* 第一行四个正整数n,m,x,k(1<=x<=n<=10^5,0<=m<=10^5,1<=k<=10^5)
* 第二行n个整数ai(0<=ai<=10^5)
* 输出描述 仅一行，一个整数，表示长城的最大战斗力
* 样例输入
* 5 2 1 2
* 4 4 2 4 4
* 样例输出
* 6
*TODO
* eg:
*  m = 将军数量 x = 将军影响直径 k = 将军的加成
*  [7,3,5,6,4]
*  假设 m = 2  k = 2  x = 3
* 如果 一个将军 设置在1位置
* 则[7,5,7,8,4] 此时薄弱点是位置4
* 如果 一个将军 设置在5位置
* 则[7,5,9,10,6] 此时薄弱点是位置1
* 目的是让 薄弱点 的数值 尽可能的高
*/
public class Code02_GreatWall {

	public static int maxForce(int[] wall, int m, int x, int k) {
		long L = 0;
		long R = 0;
		for (int num : wall) {
			R = Math.max(R, num);
		}
		R += m * k;
		long ans = 0;
		while (L <= R) {
			long M = (L + R) / 2;
			if (can(wall, m, x, k, M)) {
				ans = M;
				L = M + 1;
			} else {
				R = M - 1;
			}
		}
		return (int) ans;
	}
	/*
	*TODO
	* 返回 不超过m个将军的情况下 ，能否做到 wall的每个元素 >= limit
	* eg: [3,4,7,6,2] m=3 x=3 k=5
	* 假设将军 只在 最大的元素那里增强
	* 也就是说 最大的元素 从7变成22
	* 在这个题目中 limit 只能 是2~22范围
	* 在上面的范围做二分
	* 查看中点的值作为limit 调用can函数 是否返回true
	* 如果能 返回true 记录该值 再右侧继续二分
	* 如果不能 返回false 不记录该值 再左侧继续二分
	* 把所有值 中找出最佳的值
	* 技巧：从业务里面找出限制的技巧
	*TODO
	* [6 5 2 7 1 4 3 6]  m=2 x=3 k=2 limit=5
	* 来到0位置 查看该位置否需要加成 不需要
	* ..
	* 来到2位置 查看该位置否需要加成 需要 (第一个位置)
	* 对该位置的值 让其变成>=limit 需要2个将军
	* 让 增加的范围的左边界 正好在2位置 也就是增加2~4范围这3个位置 最经济
	* 通过线段树 变成[6 5 6 11 5 4 3 6]
	* 此时 将军还剩0个
	* 继续遍历 查看哪些位置还需要将军
	* 来到5位置发现 4 < limit 但是此时的将军数=0
	* 那么can方法返回false 表示做不到
	* 如果遍历到最后一个元素结束 此时的将拘束>=0 ，can方法返回true
	* */
	public static boolean can(int[] wall, int m, int x, int k, long limit) {
		int N = wall.length;
		//TODO 注意：下标从1开始
		SegmentTree st = new SegmentTree(wall);
		st.build(1, N, 1);
		int need = 0;
		for (int i = 0; i < N; i++) {
			//  注意：下标从1开始
			long cur = st.query(i + 1, i + 1, 1, N, 1);
			if (cur < limit) {
				int add = (int) ((limit - cur + k - 1) / k);
				need += add;
				if (need > m) {//将军数量不够
					return false;
				}
				st.add(i + 1, Math.min(i + x, N), add * k, 1, N, 1);
			}
		}
		//TODO 遍历到最后一个元素结束 此时的将拘束>=0 ，can方法返回true
		return true;
	}

	public static class SegmentTree {
		private int MAXN;
		private int[] arr;
		private int[] sum;
		private int[] lazy;
		private int[] change;
		private boolean[] update;

		public SegmentTree(int[] origin) {
			MAXN = origin.length + 1;
			arr = new int[MAXN];
			for (int i = 1; i < MAXN; i++) {
				arr[i] = origin[i - 1];
			}
			sum = new int[MAXN << 2];
			lazy = new int[MAXN << 2];
			change = new int[MAXN << 2];
			update = new boolean[MAXN << 2];
		}

		private void pushUp(int rt) {
			sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
		}

		private void pushDown(int rt, int ln, int rn) {
			if (update[rt]) {
				update[rt << 1] = true;
				update[rt << 1 | 1] = true;
				change[rt << 1] = change[rt];
				change[rt << 1 | 1] = change[rt];
				lazy[rt << 1] = 0;
				lazy[rt << 1 | 1] = 0;
				sum[rt << 1] = change[rt] * ln;
				sum[rt << 1 | 1] = change[rt] * rn;
				update[rt] = false;
			}
			if (lazy[rt] != 0) {
				lazy[rt << 1] += lazy[rt];
				sum[rt << 1] += lazy[rt] * ln;
				lazy[rt << 1 | 1] += lazy[rt];
				sum[rt << 1 | 1] += lazy[rt] * rn;
				lazy[rt] = 0;
			}
		}

		public void build(int l, int r, int rt) {
			if (l == r) {
				sum[rt] = arr[l];
				return;
			}
			int mid = (l + r) >> 1;
			build(l, mid, rt << 1);
			build(mid + 1, r, rt << 1 | 1);
			pushUp(rt);
		}

		public void update(int L, int R, int C, int l, int r, int rt) {
			if (L <= l && r <= R) {
				update[rt] = true;
				change[rt] = C;
				sum[rt] = C * (r - l + 1);
				lazy[rt] = 0;
				return;
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			if (L <= mid) {
				update(L, R, C, l, mid, rt << 1);
			}
			if (R > mid) {
				update(L, R, C, mid + 1, r, rt << 1 | 1);
			}
			pushUp(rt);
		}

		public void add(int L, int R, int C, int l, int r, int rt) {
			if (L <= l && r <= R) {
				sum[rt] += C * (r - l + 1);
				lazy[rt] += C;
				return;
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			if (L <= mid) {
				add(L, R, C, l, mid, rt << 1);
			}
			if (R > mid) {
				add(L, R, C, mid + 1, r, rt << 1 | 1);
			}
			pushUp(rt);
		}

		public long query(int L, int R, int l, int r, int rt) {
			if (L <= l && r <= R) {
				return sum[rt];
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			long ans = 0;
			if (L <= mid) {
				ans += query(L, R, l, mid, rt << 1);
			}
			if (R > mid) {
				ans += query(L, R, mid + 1, r, rt << 1 | 1);
			}
			return ans;
		}

	}

	public static void main(String[] args) {
		int[] wall = { 3, 1, 1, 1, 3 };
		int m = 2;
		int x = 3;
		int k = 1;
		System.out.println(maxForce(wall, m, x, k));
	}

}
