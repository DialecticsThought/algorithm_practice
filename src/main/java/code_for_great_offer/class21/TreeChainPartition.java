package code_for_great_offer.class21;

import java.util.HashMap;

public class TreeChainPartition {

	public static class TreeChain {
		// 时间戳 0 1 2 3 4 中儿子遍历的时候 想要编号
		private int tim;
		// 节点个数是n，节点编号是1~n 原始的是0~n-1
		private int n;
		// 谁是头
		private int h;
		/*
		* 朴素树结构
		* eg: [4,1,1,0,1]  ==> [5,2,2,1,2]
		* 二维数组
		* 0 [无]
		* 1 [4]
		* 2 [3,5]
		* 3 [无]
		* 4 [无]
		* 5 [1]
		* */
		private int[][] tree;
		// 权重数组 原始的0节点权重是6 -> val[1] = 6 相当于平移一个单位
		private int[] val;
		// father数组一个平移，因为标号要+1
		private int[] fa;
		// 深度数组！ 任何一个节点他在树的第几层
		private int[] dep;
		// son[i] = 0 i这个节点，没有儿子
		// son[i] != 0 == j i这个节点，重儿子是j
		private int[] son;
		// siz[i] i这个节点为头的子树，有多少个节点
		private int[] siz;
		// top[i] = j i这个节点，所在的重链，头是j
		private int[] top;
		// dfn[i] = j i这个节点，在dfs序中是第j个
		private int[] dfn;
		// 如果原来的节点a，权重是10 也就是节点的val=10
		// 如果a节点在dfs序中是第5个节点, tnw[5] = 10
		private int[] tnw;
		// 线段树，在tnw上，玩连续的区间查询或者更新
		private SegmentTree seg;

		public TreeChain(int[] father, int[] values) {
			// 原始的树 tree，弄好了，可以从i这个点，找到下级的直接孩子
			// 上面的一大堆结构，准备好了空间，values -> val
			// 找到头部点
			initTree(father, values);
			// fa;每个节点的父节点
			// dep;每个节点的深度的数组
			// son;每个节点的重儿子数组
			// siz;每个节点为头的子树的大小
			dfs1(h, 0);
			// top;
			// dfn;
			// tnw;
			dfs2(h, h);
			//以dfs序的权值数组上建立线段树
			seg = new SegmentTree(tnw);
			seg.build(1, n, 1);
		}

		private void initTree(int[] father, int[] values) {
			tim = 0;
			//eg：有7个节点 原始father是0~6 现在平移一个单位 1~7
			n = father.length + 1;
			tree = new int[n][];
			val = new int[n];
			fa = new int[n];
			dep = new int[n];
			son = new int[n];
			siz = new int[n];
			top = new int[n];
			dfn = new int[n];
			tnw = new int[n--];
			int[] cnum = new int[n];//孩子的数量
			for (int i = 0; i < n; i++) {

				val[i + 1] = values[i];
			}
			for (int i = 0; i < n; i++) {
				if (father[i] == i) {
					h = i + 1;//记录头部 因为平移 所以i+1
				} else {
					cnum[father[i]]++;
				}
			}
			tree[0] = new int[0];
			for (int i = 0; i < n; i++) {
				tree[i + 1] = new int[cnum[i]];
			}
			for (int i = 0; i < n; i++) {
				if (i + 1 != h) {
					tree[father[i] + 1][--cnum[father[i]]] = i + 1;
				}
			}
		}
		//第一个dfs是标记重孩子 和maxSize
		// u 是当前节点
		// f 是u的父节点
		private void dfs1(int u, int f) {
			fa[u] = f;
			dep[u] = dep[f] + 1;//u的深度是父节点的深度+1
			siz[u] = 1;//u节点未投的子树 数量初始是1
			int maxSize = -1;
			for (int v : tree[u]) { // 遍历u节点，所有的直接孩子v
				dfs1(v, u);//v是u的孩子 u是父
				/*
				* 因为u节点是v节点的父 所以计算u节点为投的子树大小
				* 要包括u节点的直接孩子v们为头的子树大小
				* */
				siz[u] += siz[v];
				if (siz[v] > maxSize) {//当前遍历到的孩子的子树节点 > maxSize
					//说明当前遍历到的孩子就是重孩子 更新 maxSize
					maxSize = siz[v];
					//当前节点是重孩子
					son[u] = v;
				}
			}
		}
		//只有第一遍sfs之后 才能知道每个节点与哪个子节点标记为重链 哪个子节点是轻链
		// 标记dfs序的编号
		// u是当前节点
		// t是u所在重链的头部
		private void dfs2(int u, int t) {
			dfn[u] = ++tim;//当前节点的dfs序是时间戳+1
			top[u] = t;//表示 t是u所在重链的头部
			// 当前节点的权值 转变 在当前节点转成dfs序后
			// u节点的dfs序作为下标 val[u]作为值
			tnw[tim] = val[u];
			//如果u有儿子  那么 u一定有 重儿子
			if (son[u] != 0) { // 如果u有儿子 也就是 siz[u] > 1
				//有重儿子的话 先走重儿子的路径 重儿子所处重链的链头也是t节点
				dfs2(son[u], t);
				//接下来遍历所有的儿子
				for (int v : tree[u]) {
					if (v != son[u]) {//排除重儿子 只看轻儿子
						// 轻儿子的重链以轻儿子自己开头 重链里只有一个节点
						dfs2(v, v);
					}
				}
			}
		}

		// 方法： head为头的子树上，所有节点值+value
		// 因为节点经过平移，所以head(原始节点) -> head(平移节点)
		public void addSubtree(int head, int value) {
			// 原始点编号 -> 平移编号
			head++;
			// 平移编号 -> dfs编号   dfn[head]是初始编号 真正的编号 dfn[head] + siz[head] - 1
			seg.add(dfn[head], dfn[head] + siz[head] - 1, value, 1, n, 1);
		}

		public int querySubtree(int head) {
			head++;
			return seg.query(dfn[head], dfn[head] + siz[head] - 1, 1, n, 1);
		}
		//从a~b的重链 的所有节点的权值都 加上v这个数
		public void addChain(int a, int b, int v) {
			a++;
			b++;
			while (top[a] != top[b]) {//a节点和b节点不在一条链上
				/*
				* a所在的重链的头 叫头a b所在的重链的头 叫头b
				* 如果头a的深度=7 头b的深度=9  那么头b先跳
				* 如果头a的深度=9 头b的深度=7  那么头a先跳
				* 防止跳过
				* */
				if (dep[top[a]] > dep[top[b]]) {
					seg.add(dfn[top[a]], dfn[a], v, 1, n, 1);
					a = fa[top[a]];
				} else {
					seg.add(dfn[top[b]], dfn[b], v, 1, n,  1);
					b = fa[top[b]];
				}
			}
			/*
			* a节点和b节点在一条链上 top[a] == top[b]
			* 说明a~b之间的节点dfs序是连续的
			* b如果在a的上方 dep[a] > dep[b] 那么 b的dfs序<a的dfs序 那么就是b~a范围上+v
			* 反之 那么就是a~b范围上+v
			* */
			if (dep[a] > dep[b]) {
				seg.add(dfn[b], dfn[a], v, 1, n, 1);
			} else {
				seg.add(dfn[a], dfn[b], v, 1, n, 1);
			}
		}
		//查询重链
		public int queryChain(int a, int b) {
			a++;
			b++;
			int ans = 0;
			//哪个节点深度更深 谁先结算
			while (top[a] != top[b]) {//当前没有汇聚 也就是a和b所处的重链的重链头不是一个节点
				if (dep[top[a]] > dep[top[b]]) {//a先跳
					//结算  重链的头部 到 a位置
					ans += seg.query(dfn[top[a]], dfn[a], 1, n, 1);
					//top[a]是a所处的重链头a fa[头a] 就是下一条重链的某个节点
					a = fa[top[a]];
				} else {
					//结算  重链的头部 到 b位置
					ans += seg.query(dfn[top[b]], dfn[b], 1, n, 1);
					//top[b]是b所处的重链头b fa[头b] 就是下一条重链的某个节点
					b = fa[top[b]];
				}
			}
			//汇聚了 也就是a和b所处的重链的重链头是一个节点  a和b在同一条重链
			if (dep[a] > dep[b]) {
				ans += seg.query(dfn[b], dfn[a], 1, n, 1);
			} else {
				ans += seg.query(dfn[a], dfn[b], 1, n, 1);
			}
			return ans;
		}
	}

	public static class SegmentTree {
		private int MAXN;
		private int[] arr;
		private int[] sum;
		private int[] lazy;

		public SegmentTree(int[] origin) {
			MAXN = origin.length;
			arr = origin;
			sum = new int[MAXN << 2];
			lazy = new int[MAXN << 2];
		}

		private void pushUp(int rt) {
			sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
		}

		private void pushDown(int rt, int ln, int rn) {
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

		public int query(int L, int R, int l, int r, int rt) {
			if (L <= l && r <= R) {
				return sum[rt];
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			int ans = 0;
			if (L <= mid) {
				ans += query(L, R, l, mid, rt << 1);
			}
			if (R > mid) {
				ans += query(L, R, mid + 1, r, rt << 1 | 1);
			}
			return ans;
		}

	}

	// 为了测试，这个结构是暴力但正确的方法
	public static class Right {
		private int n;
		private int[][] tree;
		private int[] fa;
		private int[] val;
		private HashMap<Integer, Integer> path;

		public Right(int[] father, int[] value) {
			n = father.length;
			tree = new int[n][];
			fa = new int[n];
			val = new int[n];
			for (int i = 0; i < n; i++) {
				fa[i] = father[i];
				val[i] = value[i];
			}
			int[] help = new int[n];
			int h = 0;
			for (int i = 0; i < n; i++) {
				if (father[i] == i) {
					h = i;
				} else {
					help[father[i]]++;
				}
			}
			for (int i = 0; i < n; i++) {
				tree[i] = new int[help[i]];
			}
			for (int i = 0; i < n; i++) {
				if (i != h) {
					tree[father[i]][--help[father[i]]] = i;
				}
			}
			path = new HashMap<>();
		}

		public void addSubtree(int head, int value) {
			val[head] += value;
			for (int next : tree[head]) {
				addSubtree(next, value);
			}
		}

		public int querySubtree(int head) {
			int ans = val[head];
			for (int next : tree[head]) {
				ans += querySubtree(next);
			}
			return ans;
		}

		public void addChain(int a, int b, int v) {
			path.clear();
			path.put(a, null);
			while (a != fa[a]) {
				path.put(fa[a], a);
				a = fa[a];
			}
			while (!path.containsKey(b)) {
				val[b] += v;
				b = fa[b];
			}
			val[b] += v;
			while (path.get(b) != null) {
				b = path.get(b);
				val[b] += v;
			}
		}

		public int queryChain(int a, int b) {
			path.clear();
			path.put(a, null);
			while (a != fa[a]) {
				path.put(fa[a], a);
				a = fa[a];
			}
			int ans = 0;
			while (!path.containsKey(b)) {
				ans += val[b];
				b = fa[b];
			}
			ans += val[b];
			while (path.get(b) != null) {
				b = path.get(b);
				ans += val[b];
			}
			return ans;
		}

	}

	// 为了测试
	// 随机生成N个节点树，可能是多叉树，并且一定不是森林
	// 输入参数N要大于0
	public static int[] generateFatherArray(int N) {
		int[] order = new int[N];
		for (int i = 0; i < N; i++) {
			order[i] = i;
		}
		for (int i = N - 1; i >= 0; i--) {
			swap(order, i, (int) (Math.random() * (i + 1)));
		}
		int[] ans = new int[N];
		ans[order[0]] = order[0];
		for (int i = 1; i < N; i++) {
			ans[order[i]] = order[(int) (Math.random() * i)];
		}
		return ans;
	}

	// 为了测试
	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// 为了测试
	public static int[] generateValueArray(int N, int V) {
		int[] ans = new int[N];
		for (int i = 0; i < N; i++) {
			ans[i] = (int) (Math.random() * V) + 1;
		}
		return ans;
	}

	// 对数器
	public static void main(String[] args) {
		int N = 50000;
		int V = 100000;
		int[] father = generateFatherArray(N);
		int[] values = generateValueArray(N, V);
		TreeChain tc = new TreeChain(father, values);
		Right right = new Right(father, values);
		int testTime = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			double decision = Math.random();
			if (decision < 0.25) {
				int head = (int) (Math.random() * N);
				int value = (int) (Math.random() * V);
				tc.addSubtree(head, value);
				right.addSubtree(head, value);
			} else if (decision < 0.5) {
				int head = (int) (Math.random() * N);
				if (tc.querySubtree(head) != right.querySubtree(head)) {
					System.out.println("出错了!");
				}
			} else if (decision < 0.75) {
				int a = (int) (Math.random() * N);
				int b = (int) (Math.random() * N);
				int value = (int) (Math.random() * V);
				tc.addChain(a, b, value);
				right.addChain(a, b, value);
			} else {
				int a = (int) (Math.random() * N);
				int b = (int) (Math.random() * N);
				if (tc.queryChain(a, b) != right.queryChain(a, b)) {
					System.out.println("出错了!");
				}
			}
		}
		System.out.println("测试结束");
	}

}
