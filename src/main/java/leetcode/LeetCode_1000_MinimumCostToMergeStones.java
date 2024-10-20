package leetcode;

/**
 *
 * code_for_great_offer.class23
 */
public class LeetCode_1000_MinimumCostToMergeStones {

//	// arr[L...R]一定要整出P份，合并的最小代价，返回！ arr和K是固定值
//	public static int f(int[] arr, int K, int L, int R, int P) {
//		if(从L到R根本不可能弄出P份) {
//			return Integer.MAX_VALUE;
//		}
//		// 真的有可能的！
//		if(P == 1) {
	//如果只有一个数 代价就是0
//			return L == R ? 0 : (f(arr, K, L, R, K) + 最后一次大合并的代价);
//		}
//		int ans = Integer.MAX_VALUE;
//		// 真的有可能，P > 1
//		for(int i = L; i < R;i++) { //枚举第一份的范围L..i 其他份就是i+1...R范围里
//			// L..i(1份)  i+1...R(P-1份)
//			int left = f(arr, K, L, i, 1);//得到左边的代价 表示 L..i范围作为第一份
//			if(left == Integer.MAX_VALUE) {//说明这个分发 或者说这条递归路径是分不出来的 失败
//				continue;
//			}
//			int right = f(arr, K, i+1,R,P-1);
//			if(right == Integer.MAX_VALUE) {
//				continue;
//			}
//			int all = left + right;
//			ans = Math.min(ans, all);//当前的答案和之前递归的答案 作比较 更新
//		}
//		return ans;
//	}

	public static int mergeStones1(int[] stones, int K) {
		int n = stones.length;
		/*
		 *TODO 判断是否有解。执行一次合并操作之后，会将k堆石头变为1堆石头，
		 * 那么总的石头堆数就是减少了k - 1堆。初始有n堆石头，
		 * 我们的目标是将其变为1堆，也就是减少n - 1堆，于是这里需要n - 1是k - 1的倍数才能保证最后能合成一堆石头，
		 * 即当(N - 1) % (K - 1) != 0 直接返回-1。
		 * */
		if ((n - 1) % (K - 1) > 0) {
			return -1;
		}
		int[] presum = new int[n + 1];
		for (int i = 0; i < n; i++) {
			presum[i + 1] = presum[i] + stones[i];
		}
		return process1(0, n - 1, 1, stones, K, presum);
	}

	// part >= 1
	// arr[L..R] 一定要弄出part份，返回最低代价
	// arr、K、presum（前缀累加和数组，求i..j的累加和，就是O(1)了） 最后一次大合并就是0~i的累加和
	public static int process1(int L, int R, int P, int[] arr, int K, int[] presum) {
		if (L == R) { // arr[L..R]
			return P == 1 ? 0 : -1;//只有一个数 合并代价是0
		}
		// L ... R 不只一个数
		if (P == 1) {//先分成K份
			//TODO P == 1 说明当前是最后一次合并了，如果要想是最后一次合并，此时合并完只能由k个数，所以要判断当前的状况能不能合并出k份
			int next = process1(L, R, K, arr, K, presum);
			// 如果不能合并成k份，那么一定就无法最后合并成一份，直接返回-1
			if (next == -1) {
				return -1;
			} else {
				// 当前的大合并+后续的合并
				// 代价就是合并出三分所需要的总代价，加上l~r范围的累加和，因为将3份数合并，合并代价就是这三部分的累加和
				return next + presum[R + 1] - presum[L];
			}
		} else { // P > 1 不止合并成1份的情况 说明合并还没有到最后
			int ans = Integer.MAX_VALUE;
			/*
			*TODO
			* 枚举最左边的一份是什么范围，这样就可以递归出左部分和右部分的最低代价。
			* 我们将每一层递归的所有可能的范围都枚举尝试一遍，最后就能求出来结果。
			* 这里我们就认为左边搞成1份，右边搞成P-1份，因为总归是存在一种划分能让左边搞出来1份，
			* 我们把所有可能的左部分范围都尝试一遍，
			* 就能得到全部左部分和右部分的情况。而且必须要来一个左右部分，
			* 因为只有划分为1份的时候才是递归出口，所以我们就要想到让左边固定搞1份，这样才能让整个递归过程结束返回。
			 * */
			/*
			*TODO
			* 开始遍历，尝试所有可能的左右部分划分情况，我们就规定左部分划分出1份，右部分划分出p-1份
			* 划分遍历步长就是k - 1，l最多到r - (p - 1)，因为要保证右部分至少有p-1个数，不然就不可能划分出p-1个目标了
			* L...mid是第1块，剩下的是part-1块
			* */
			for (int mid = L; mid < R; mid += K - 1) {
				// L..mid(一份) mid+1...R(part - 1份)  就枚举第一份在什么范围
				// 看左部分合并成1份最低代价
				int next1 = process1(L, mid, 1, arr, K, presum);
				// 右部分合并出p-1份的最低代价
				int next2 = process1(mid + 1, R, P - 1, arr, K, presum);
				// 如果左部分确实可以划分出1份，右部分也可以划分出来，则计算这两部分的合并代价，是否是l~r范围所有划分情况中最低的
				if (next1 != -1 && next2 != -1) {
					// 这里我们就是要将l~r范围合并出p份，并不会对这p份再做合并，所以这里的代价就是next1 + next2，不需要加上l~r的累加和
					ans = Math.min(ans, next1 + next2);
				}
			}
			return ans;
		}
	}

	public static int mergeStones2(int[] stones, int K) {
		int n = stones.length;
		/*
		*TODO 判断是否有解。执行一次合并操作之后，会将k堆石头变为1堆石头，
		* 那么总的石头堆数就是减少了k - 1堆。初始有n堆石头，
		* 我们的目标是将其变为1堆，也就是减少n - 1堆，于是这里需要n - 1是k - 1的倍数才能保证最后能合成一堆石头，
		* 即当(N - 1) % (K - 1) != 0 直接返回-1。
		* */
		if ((n - 1) % (K - 1) > 0) { // n个数，到底能不能K个相邻的数合并，最终变成1个数！
			return -1;
		}
		int[] presum = new int[n + 1];
		//TODO 构造前缀和数组，加速求任意区间累加和的速度
		for (int i = 0; i < n; i++) {
			presum[i + 1] = presum[i] + stones[i];
		}
		//TODO 构造三维dp缓存  第三维下标在递归中最多赋值就是k，所以长度开到k+1即可
		int[][][] dp = new int[n][n][K + 1];
		//TODO 将数组0~n-1范围合并成1份，每一次将相邻的k个合并
		return process2(0, n - 1, 1, stones, K, presum, dp);
	}

	public static int process2(int L, int R, int P, int[] arr, int K, int[] presum, int[][][] dp) {
		if (dp[L][R][P] != 0) {
			return dp[L][R][P];
		}
		if (L == R) {
			return P == 1 ? 0 : -1;
		}
		if (P == 1) {
			int next = process2(L, R, K, arr, K, presum, dp);
			// 如果不能合并成k份，那么一定就无法最后合并成一份，直接返回-1
			if (next == -1) {
				dp[L][R][P] = -1;
				return -1;
			} else {
				// 当前的大合并+后续的合并
				// 代价就是合并出三分所需要的总代价，加上l~r范围的累加和，因为将3份数合并，合并代价就是这三部分的累加和
				dp[L][R][P] = next + presum[R + 1] - presum[L];
				return next + presum[R + 1] - presum[L];
			}
		} else {
			/*
			 *TODO
			 * 枚举最左边的一份是什么范围，这样就可以递归出左部分和右部分的最低代价。
			 * 我们将每一层递归的所有可能的范围都枚举尝试一遍，最后就能求出来结果。
			 * 这里我们就认为左边搞成1份，右边搞成P-1份，因为总归是存在一种划分能让左边搞出来1份，
			 * 我们把所有可能的左部分范围都尝试一遍，
			 * 就能得到全部左部分和右部分的情况。而且必须要来一个左右部分，
			 * 因为只有划分为1份的时候才是递归出口，所以我们就要想到让左边固定搞1份，这样才能让整个递归过程结束返回。
			 * */
			/*
			 *TODO
			 * 开始遍历，尝试所有可能的左右部分划分情况，我们就规定左部分划分出1份，右部分划分出p-1份
			 * 划分遍历步长就是k - 1，l最多到r - (p - 1)，因为要保证右部分至少有p-1个数，不然就不可能划分出p-1个目标了
			 * L...mid是第1块，剩下的是part-1块
			 * */
			int ans = Integer.MAX_VALUE;
			// i...mid是第1块，剩下的是part-1块
			for (int mid = L; mid < R; mid += K - 1) {
				// L..mid(一份) mid+1...R(part - 1份)  就枚举第一份在什么范围
				// 看左部分合并成1份最低代价
				int next1 = process2(L, mid, 1, arr, K, presum, dp);
				// 右部分合并出p-1份的最低代价
				int next2 = process2(mid + 1, R, P - 1, arr, K, presum, dp);
				if (next1 == -1 || next2 == -1) {
					dp[L][R][P] = -1;
					return -1;
				} else {
					// 如果左部分确实可以划分出1份，右部分也可以划分出来，则计算这两部分的合并代价，是否是l~r范围所有划分情况中最低的
					// 这里我们就是要将l~r范围合并出p份，并不会对这p份再做合并，所以这里的代价就是next1 + next2，不需要加上l~r的累加和
					ans = Math.min(ans, next1 + next2);
				}
			}
			dp[L][R][P] = ans;
			return ans;
		}
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) (maxSize * Math.random()) + 1];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random());
		}
		return arr;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int maxSize = 12;
		int maxValue = 100;
		System.out.println("Test begin");
		for (int testTime = 0; testTime < 100000; testTime++) {
			int[] arr = generateRandomArray(maxSize, maxValue);
			int K = (int) (Math.random() * 7) + 2;
			int ans1 = mergeStones1(arr, K);
			int ans2 = mergeStones2(arr, K);
			if (ans1 != ans2) {
				System.out.println(ans1);
				System.out.println(ans2);
			}
		}

	}

}
