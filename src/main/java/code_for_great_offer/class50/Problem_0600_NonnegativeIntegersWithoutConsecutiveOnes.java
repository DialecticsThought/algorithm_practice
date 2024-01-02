package code_for_great_offer.class50;

public class Problem_0600_NonnegativeIntegersWithoutConsecutiveOnes {
	// 6 5 ... 0 -1 n
	// 0 ? 停
	/*
	*TODO 数位dp
	* eg:
	* f(0, false, 5,n) 这里最高位是第5位
	*     5 4 3 2 1 0 -1
	* n : 1 0 1 1 1 0
	* 	  0 1 i
	* 表示当前来到 i 这个位置 之前做的决定是 0 1
	*TODO
	* 一个数 (X)D 转换成 (Y)B
	* 从二进制的 高位到低位 从左往右
	* pre : 第i+1位做的决定，0还是1
	* 在 第i+1位做的决定 是pre的情况下，从index位开始，往后都做决定！
	* 但是，不能有相邻的1，请问有多少决定！返回！
	* alreadyLess : 之前的决定，是不是已经导致你到第index位的时候，已经比n小了！
	* pre 就2种参数 0 1
	* index -> n的位数，（logN）
	* alreadyLess 就2种参数 true false
	* eg:
	*        n =  1 0 0 1 0 1
	* 当前做的选择: 1 0 i
	* i是要选择的并且只能是0 因为 如果是1 那么你做的决定已经比n小了
	*
	* 2 * 2 * logN
	* dp[2][]
	* */
	public int f(int pre, boolean alreadyLess, int index, int n) {
		if (index == -1) {
			return 1;
		}
		if (pre == 1) {
			/*
			*TODO 从左到右做决定 eg: .... i+1  i  i-1 .....
			* 只能做0的决定，然后去往i-1位置
			* eg: 来到 第i位  n这个数第i+1位是1 如果你做了选择是第i+1位是0的话
			* 那么后面无论做什么决定 都会被比n小  => alreadyLess = TRUE
			* eg:
			*   n: 1 0 1 0 1 0 1
			* 决定: 1 0 0 i......
			* 如果 alreadyLess = FALSE 而且n在第i位是1 ((n & 1 << index) != 0)
			* 此时做出了选择是第i位是0 那么从第i-1位之后的决定不管怎么做 都会比n小
			* */
			boolean curLessOrMore = alreadyLess | ((n & 1 << index) != 0);
			return f(0, curLessOrMore, index - 1, n);
		} else { //TODO pre == 0 也就是 i+1位 做了0决定
			/*
			*TODO 可能性1，继续做0的决定
			* 从左到右做决定 eg: .... i+1  i  i-1 .....
			* eg: 来到 第i位  n这个数第i+1位是1 如果你做了选择是第i+1位是0的话
			* 那么后面无论做什么决定 都会被比n小  => alreadyLess = TRUE
			* eg:
			*   n: 1 0 1 0 1 0 1
			* 决定: 1 0 0 i......
			* 如果 alreadyLess = FALSE 而且n在第i位是1 ((n & 1 << index) != 0)
			* * 此时做出了选择是第i位是0 那么从第i-1位之后的决定不管怎么做 都会比n小
			* */
			boolean curLessOrMore = alreadyLess | ((n & 1 << index) != 0);
			int p1 = f(0, curLessOrMore, index - 1, n);
			/*
			*TODO 可能性2，做1的决定
			* 条件
			* 1)pre == 0的决定
			* 2)之前做的决定已经比n小了 or n在第i位上是1
			* */
			int p2 = 0;
			if (alreadyLess || (n & 1 << index) != 0) {
				p2 = f(1, alreadyLess, index - 1, n);
			}
			return p1 + p2;
		}
	}
	public static int findIntegers(int n) {
		int i = 31;
		for (; i >= 0; i--) {
			if ((n & (1 << i)) != 0) {
				break;
			}
		}
		// for循环出来之后，i表示，n最高位的1，在哪？
		// 从这个位置，往右边低位上走！
		int[][][] dp = new int[2][2][i + 1];
		return f(0, 0, i, n, dp);
	}


	public static int f(int pre, int alreadyLess, int index, int num, int[][][] dp) {
		if (index == -1) {
			return 1;
		}
		if (dp[pre][alreadyLess][index] != 0) {
			return dp[pre][alreadyLess][index];
		}
		int ans = 0;
		if (pre == 1) {
			ans = f(0, Math.max(alreadyLess, (num & (1 << index)) != 0 ? 1 : 0), index - 1, num, dp);
		} else {
			if ((num & (1 << index)) == 0 && alreadyLess == 0) {
				ans = f(0, alreadyLess, index - 1, num, dp);
			} else {
				ans = f(1, alreadyLess, index - 1, num, dp)
						+ f(0, Math.max(alreadyLess, (num & (1 << index)) != 0 ? 1 : 0), index - 1, num, dp);
			}
		}
		dp[pre][alreadyLess][index] = ans;
		return ans;
	}

}
