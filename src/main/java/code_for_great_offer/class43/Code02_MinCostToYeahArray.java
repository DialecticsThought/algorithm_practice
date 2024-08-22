package code_for_great_offer.class43;

import java.util.Arrays;

/*
*TODO
* 来自360笔试
* 给定一个正数数组arr，长度为n，下标0~n-1
* arr中的0、n-1位置不需要达标，它们分别是最左、最右的位置
* 中间位置i需要达标，达标的条件是 : arr[i-1] > arr[i] 或者 arr[i+1] > arr[i]哪个都可以
* 你每一步可以进行如下操作：对任何位置的数让其-1
* 你的目的是让arr[1~n-2]都达标，这时arr称之为yeah！数组
* 返回至少要多少步可以让arr变成yeah！数组
* 数据规模 : 数组长度 <= 10000，数组中的值<=500
*/
public class Code02_MinCostToYeahArray {

	public static final int INVALID = Integer.MAX_VALUE;

	// 纯暴力方法，只是为了结果对
	// 时间复杂度极差
	public static int minCost0(int[] arr) {
		if (arr == null || arr.length < 3) {
			return 0;
		}
		int n = arr.length;
		int min = INVALID;
		for (int num : arr) {
			min = Math.min(min, num);
		}
		int base = min - n;
		return process0(arr, base, 0);
	}

	public static int process0(int[] arr, int base, int index) {
		if (index == arr.length) {
			for (int i = 1; i < arr.length - 1; i++) {
				if (arr[i - 1] <= arr[i] && arr[i] >= arr[i + 1]) {
					return INVALID;
				}
			}
			return 0;
		} else {
			int ans = INVALID;
			int tmp = arr[index];
			for (int cost = 0; arr[index] >= base; cost++, arr[index]--) {
				int next = process0(arr, base, index + 1);
				if (next != INVALID) {
					ans = Math.min(ans, cost + next);
				}
			}
			arr[index] = tmp;
			return ans;
		}
	}

	// 递归方法，已经把尝试写出
	public static int minCost1(int[] arr) {
		if (arr == null || arr.length < 3) {
			return 0;
		}
		int min = INVALID;
		for (int num : arr) {
			min = Math.min(min, num);
		}
		for (int i = 0; i < arr.length; i++) {
			arr[i] += arr.length - min;
		}
		return process1(arr, 1, arr[0], true);
	}

	/*
	*TODO
	* 当前来到index位置，值 = arr[index]
	* pre : 前一个位置的值，可能减掉了一些，所以不能用原始的arr[index-1]
	* preOk : 前一个位置的值，是否被它左边的数变有效了
	* 返回 : 让arr都变有效，最小代价是什么？
	* eg: arr [7 5 6 ...]
	* 那么主函数f(1,7,true)
	* 这个主函数 会调用
	* f(2,5,true) + 0(这个0是因为5没有变)
	* f(2,4,true) + 1(这个1是因为5-1)
	* f(2,3,true) + 2(这个2是因为5-2)
	* f(2,2,true) + 3(这个3是因为5-3)
	* ....
	* f(2,x,true) + 5-x(这个3是因为5-5+x)
	* 上面的所有 分支求一个min 就是最优解
	* 这个底 也就是 x
	* 这个底 到底是什么 ==> 利用平凡解找性质 ☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆
	* eg: [7 1 6 1 4 8 3] 想知道这个arr 减到什么程度 不用试了
	* 先让 arr 的所有元素 变成这个arr的最小值 也就是1
	* [7 1 6 1 4 8 3] => [1 1 1 1 1 1 1]
	* 再让 [1 1 1 1 1 1 1] 的每一个位置i依次减去i
	* [1 1 1 1 1 1 1] => [1 0 -1 -2 -3 -4 -5]
	* 说明原始数组的所有元素不用再减到-5以下 ☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆
	* eg: arr [7 2 6 2 4]
	*  => [2 2 2 2 2] => [2 1 0 -1 -2] 说明底就是-2 -2就是基准
	* 优化：利用-2 把原始arr变化一下
	* [7 2 6 2 4] => [9 4 8 4 6] => [4 4 4 4 4] => [4 3 2 1 0]
	* ====> 抽象话
	* 有一个原始arr 这个arr有一个min 长度是N
	* 让arr变成全是min的新arr
	* 再让新的每一个位置i依次减去i => 这个新arr的最后元素就是min-(N-1)
	* 如何让最后元素：min-N+1 变成 0
	* 那么 就是 min+ （N-1-min）
	* 也就是新arr的每一个元素都 + （N-1-min）
	* 也就是原始数组统一每一个元素加上N-min
	 * */
	public static int process1(int[] arr, int index, int pre, boolean preOk) {
		//TODO base case 已经来到最后一个数了
		if (index == arr.length - 1) {
			/*
			*TODO
			* 如果N-2位置的数被N-3位置的数搞定了 也就是arr[N-2] < arr[N-3]
			* 如果N-2位置的数没被N-3位置的数搞定了 也就是arr[N-2] > arr[N-3] 那么pre < arr[index]
			* */
			return preOk || pre < arr[index] ? 0 : INVALID;
		}
		//TODO 下面的代码 当前index，不是最后一个数！
		int ans = INVALID;//TODO
		if (preOk) {
			/*
			*TODO
			* index-1位置的数 被index-2的数搞定了
			* 这个情况 index位置的数 有 arr[index]个分支 因为都要尝试
			* eg:
			* arr[index]=9  arr[index-1]=10 index-1被搞定了
			* 注意：这里的arr是被处理过的☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆ 也就是 arr的每一个元素都 + （N-1-min）
			* 那么分支：
			* f(i+1,9,true)+0
			* f(i+1,8,true)+1
			* f(i+1,7,true)+2
			* ....
			* f(i+1,0,true)+9
			* arr[index]=9  arr[index-1]=5 index-1被搞定了
			* 那么分支：
			* f(i+1,9,f)+0
			* f(i+1,8,f)+1
			* f(i+1,7,f)+2
			* f(i+1,6,f)+3
			* f(i+1,5,t)+4
			* f(i+1,4,t)+5
			* ....
			* f(i+1,0,true)+9
			 * */
			for (int cur = arr[index]; cur >= 0; cur--) {
				int next = process1(arr, index + 1, cur, cur < pre);
				if (next != INVALID) {
					ans = Math.min(ans, arr[index] - cur + next);
				}
			}
		} else {
			/*
			*TODO
			* index-1位置的数 没有index-2的数搞定了
			* eg:
			* arr[index]=9  arr[index-1]=5 index-1没被搞定
			* 那么分支： 只有4个分支☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆
			* f(i+1,9,f)+0
			* f(i+1,8,f)+1
			* f(i+1,7,f)+2
			* f(i+1,6,f)+3
			* eg:
			* arr[index]=9  arr[index-1]=13 index-1没被搞定
			* 直接返回无效
			* */
			for (int cur = arr[index]; cur > pre; cur--) {
				int next = process1(arr, index + 1, cur, false);
				if (next != INVALID) {
					ans = Math.min(ans, arr[index] - cur + next);
				}
			}
		}
		return ans;
	}

	// 初改动态规划方法，就是参考minCost1，改出来的版本
	public static int minCost2(int[] arr) {
		if (arr == null || arr.length < 3) {
			return 0;
		}
		int min = INVALID;
		for (int num : arr) {
			min = Math.min(min, num);
		}
		int n = arr.length;
		for (int i = 0; i < n; i++) {
			arr[i] += n - min;
		}
		int[][][] dp = new int[n][2][];
		for (int i = 1; i < n; i++) {
			dp[i][0] = new int[arr[i - 1] + 1];
			dp[i][1] = new int[arr[i - 1] + 1];
			Arrays.fill(dp[i][0], INVALID);
			Arrays.fill(dp[i][1], INVALID);
		}
		for (int pre = 0; pre <= arr[n - 2]; pre++) {
			dp[n - 1][0][pre] = pre < arr[n - 1] ? 0 : INVALID;
			dp[n - 1][1][pre] = 0;
		}
		for (int index = n - 2; index >= 1; index--) {
			for (int pre = 0; pre <= arr[index - 1]; pre++) {
				for (int cur = arr[index]; cur > pre; cur--) {
					int next = dp[index + 1][0][cur];
					if (next != INVALID) {
						dp[index][0][pre] = Math.min(dp[index][0][pre], arr[index] - cur + next);
					}
				}
				for (int cur = arr[index]; cur >= 0; cur--) {
					int next = dp[index + 1][cur < pre ? 1 : 0][cur];
					if (next != INVALID) {
						dp[index][1][pre] = Math.min(dp[index][1][pre], arr[index] - cur + next);
					}
				}
			}
		}
		return dp[1][1][arr[0]];
	}

	/*
	* minCost2动态规划 + 枚举优化
	* 改出的这个版本，需要一些技巧，但很可惜不是最优解
	* 虽然不是最优解，也足以通过100%的case了，
	* 这种技法的练习，非常有意义
	* */
	public static int minCost3(int[] arr) {
		if (arr == null || arr.length < 3) {
			return 0;
		}
		int min = INVALID;
		for (int num : arr) {
			min = Math.min(min, num);
		}
		int n = arr.length;
		for (int i = 0; i < n; i++) {
			arr[i] += n - min;
		}
		int[][][] dp = new int[n][2][];
		for (int i = 1; i < n; i++) {
			dp[i][0] = new int[arr[i - 1] + 1];
			dp[i][1] = new int[arr[i - 1] + 1];
		}
		for (int p = 0; p <= arr[n - 2]; p++) {
			dp[n - 1][0][p] = p < arr[n - 1] ? 0 : INVALID;
		}
		int[][] best = best(dp, n - 1, arr[n - 2]);
		for (int i = n - 2; i >= 1; i--) {
			for (int p = 0; p <= arr[i - 1]; p++) {
				if (arr[i] < p) {
					dp[i][1][p] = best[1][arr[i]];
				} else {
					dp[i][1][p] = Math.min(best[0][p], p > 0 ? best[1][p - 1] : INVALID);
				}
				dp[i][0][p] = arr[i] <= p ? INVALID : best[0][p + 1];
			}
			best = best(dp, i, arr[i - 1]);
		}
		return dp[1][1][arr[0]];
	}
	/*
	*TODO
	* eg1：f(i,9,t) 并且 pre位置是9 i位置是7 pre位置是被满足的
	* 这个函数有以下分支
	* 0+f(i+1,7,t)
	* 1+f(i+1,6,t)
	* 2+f(i+1,5,t)
	* ....
	* 并且求出上面的最小
	* 但是对于dp而言 是先求出f(i+1,...,...)再求出f(i,...,...)
	* 与求递归的操作是相反的
	* dp表示三维的 index pre（pre是前一个位置的值） True/false（pre是否被他之前满足）    ==》和递归的函数相同
	* 即 dp[index][pre][T/F]
	* eg2:当前的index=6 前一个位置是5 元素是8 ar[5]=8=pre
	* 那么dp表就要求出
	* dp[6][8][t] dp[6][8][f] dp[6][7][t] dp[6][7][f] dp[6][5][t]....都要求出来
	* eg:当前的index=6 前一个位置是5 元素是3 ar[5]=3=pre
	* dp[6][3][t]+0 dp[6][3][f]+0
	* dp[6][2][t]+1 dp[6][2][f]+1
	* dp[6][1][t]+2 dp[6][1][f]+2
	* dp[6][0][t]+3 dp[6][0][f]+3
	* 再求出上面的min
	* eg3: arr【3,1,3,4】 假设现在求最有一个位置的递归函数
	* 那么f(3,3,T) f(3,3,F) f(3,2,T) f(3,2,F) f(3,1,T) f(3,1,F) f(3,1,T) f(3,1,F)
	* eg4:arr【?,3,2,?】 假设现在求最有一个位置3的递归函数
	* 那么f(3,2,T) f(3,2,F) f(3,1,T) f(3,1,F) f(3,1,T) f(3,1,F) 共6个递归函数
	* 现在求2位置的时候的递归函数 而且是从后往前求 先求3位置 知道了上面的递归函数的结果再求2位置
	* f(2,3,T)就会调用
	* f(3,2,T)+0
	* f(3,1,T)+1
	* f(3,0,T)+2
	* 再求出 上面的min 也就是说dp表只记录了6个递归函数结果值
	* 还要记录 6个递归函数 + 对应的数 之后求min的结果 这个结果可以给f(2,3,T)使用
	* eg5：假设有一个递归函数f(6,4,F) arr[6]=8  arr[5]=4
	* 这个递归函数需要下面的结果
	* f(7,8,F)+0
	* f(7,7,F)+1
	* f(7,6,F)+2
	* f(7,5,F)+3
	* f(7,4,F)+4
	* ...
	* 现在有一个结构 存放上面的所有结果的min 这个结构 会用到位置6的对应的函数f(6,4,F)
	* 所以这个结构是一个arr
	* 这个 arr[8]=f(7,8,F)+0
	* 这个 arr[7]=min{f(7,7,F)+1,f(7,8,F)+0}
	* 这个 arr[6]=min{f(7,6,F)+2,f(7,7,F)+1,f(7,8,F)+0}
	* 这个 arr[5]=min{f(7,5,F)+3,f(7,6,F)+2,f(7,7,F)+1,f(7,8,F)+0}
	* ....
	* 这个arr[5]就是f(6,4,F)想要的
	*TODO
	* 上面的arr叫做bestF 因为都是False
	*TODO
	*  9 6 ？ pre=2  当前index=3
	*  2 3 4
	* 当前是求f(3,9,T) 那么f(3,9,T)依赖什么？
	* f(4,6,T) f(4,5,T) f(4,4,T) ....
	* 但是如果 arr[2]=8=pre  只要pre大于等于当前位置的数6
	*  8 6 ？
	*  2 3 4
	*  还是依赖相同的东西 f(4,6,T) f(4,5,T) f(4,4,T) ....
	* 但是如果
	*  3 6 ?    pre=arr[7]=3 < 6 求当前8位置
	*  7 8 9
	* f(8,3,T)依赖于
	* f(9,6,F)+0 f(9,5,F)+1 f(9,4,F)+2 f(9,3,F)+3 f(9,2,T)+4 f(9,1,T)+5 f(9,0,T)+6
	* 这上面的数都是存在bestF 和 bestT里面
	* bestF[6]记录的是min{f(9,6,F)+0}
	* bestF[5]记录的是min{f(9,6,F)+0,f(9,5,F)+1}
	* bestF[4]记录的是min{f(9,6,F)+0,f(9,5,F)+1, f(9,4,F)+2}
	* bestF[3]记录的是min{f(9,6,F)+0,f(9,5,F)+1, f(9,4,F)+2,f(9,3,F)+3}
	* bestT[0]记录的是min{f(9,0,T)+6}
	* bestT[1]记录的是min{f(9,0,T)+6,f(9,1,T)+5}
	* bestT[2]记录的是min{f(9,0,T)+6,f(9,1,T)+5,f(9,2,T)+4}
	*TODO
	* 也就是说bestT和bestF是相反的 bestT是从小到大 bestF是从大到小
	* 也就是说dp表没有用了
	 * */
	public static int[][] best(int[][][] dp, int i, int v) {
		int[][] best = new int[2][v + 1];
		best[0][v] = dp[i][0][v];
		for (int p = v - 1; p >= 0; p--) {
			best[0][p] = dp[i][0][p] == INVALID ? INVALID : v - p + dp[i][0][p];
			best[0][p] = Math.min(best[0][p], best[0][p + 1]);
		}
		best[1][0] = dp[i][1][0] == INVALID ? INVALID : v + dp[i][1][0];
		for (int p = 1; p <= v; p++) {
			best[1][p] = dp[i][1][p] == INVALID ? INVALID : v - p + dp[i][1][p];
			best[1][p] = Math.min(best[1][p], best[1][p - 1]);
		}
		return best;
	}
	/*
	* 最终的最优解，贪心
	* 时间复杂度O(N)
	* 请注意，重点看上面的方法
	* 这个最优解容易理解，但让你学到的东西不是很多
	*TODO
	* 这个题目的最终解
	* 一定是数组 先下降 后上升 下坡再上坡
	* 数组中不会连续出现3个相同的数 因为 中间的数不达标
	* 数组可以只有下坡，上坡长度是0
	* 数组可以只有上坡，下坡长度是0
	* 现在做一件事 0~i的位置都下降 需要多少代价 + i~n-1的位置都上升 需要多少代价
	* i就是1~n-2范围 求出最小代价
	* 但是还有个问题
	* eg:[9 9 9 9 9]
	* 让 中间的9变成波谷 => [9 8 7 8 9]
	* 对于中间的9而言 因为汇聚到了一个点 那么这个9 就要承担左和右2个代价
	* 解决方法： 分开来 不汇聚在一个点
	* 假设 有 0 1 2 3 4 5 6 这7个位置
	* 那么 求1位置的时候 1作为做为下坡的波谷 求一个 + 2位置做为上坡的波谷 求一下
	* 那么 求2位置的时候 2作为做为下坡的波谷 求一个 + 3位置做为上坡的波谷 求一下
	* 那么 求3位置的时候 3作为做为下坡的波谷 求一个 + 4位置做为上坡的波谷 求一下
	* ....
	* eg:[3 4 6 2 5 7 3]
	* 求1位置 1作为做为下坡的波谷 求出来是2
	* 求2位置 2作为做为下坡的波谷 求出来是1
	* ....
	* 解决方法：预处理数组
	* eg:[5 6 2 7 3 4 5]
	* dp:[ 0 2 2 8]
	* dpLeft[i]：代表 原始arr的0~i范围想要都下坡 总代价是多少 当前i位置是坡谷
	* 1位置 6->4 代价2 dpLeft[1]=dpLeft[0]+2
	* 2位置 2->2 代价0 dpLeft[2]=dpLeft[1]+0
	* 3位置 7->1 代价0 dpLeft[3]=dpLeft[2]+6
	* 4位置 3->0 代价0 dpLeft[4]=dpLeft[3]+3
	* ....
	* 再同样的方式求dpRight[i]：代表 原始arr的i~n-1范围想要都上坡 总代价是多少 当前i位置是坡谷
	* 最后要求i位置 那么 dpLeft[i]+ dpRight[i+1]
	 * */
	public static int yeah(int[] arr) {
		if (arr == null || arr.length < 3) {
			return 0;
		}
		int n = arr.length;
		int[] nums = new int[n + 2];
		nums[0] = Integer.MAX_VALUE;
		nums[n + 1] = Integer.MAX_VALUE;
		for (int i = 0; i < arr.length; i++) {
			nums[i + 1] = arr[i];
		}
		int[] leftCost = new int[n + 2];
		int pre = nums[0];
		int change = 0;
		for (int i = 1; i <= n; i++) {
			change = Math.min(pre - 1, nums[i]);
			leftCost[i] = nums[i] - change + leftCost[i - 1];
			pre = change;
		}
		int[] rightCost = new int[n + 2];
		pre = nums[n + 1];
		for (int i = n; i >= 1; i--) {
			change = Math.min(pre - 1, nums[i]);
			rightCost[i] = nums[i] - change + rightCost[i + 1];
			pre = change;
		}
		int ans = Integer.MAX_VALUE;
		for (int i = 1; i <= n; i++) {
			ans = Math.min(ans, leftCost[i] + rightCost[i + 1]);
		}
		return ans;
	}

	// 为了测试
	public static int[] randomArray(int len, int v) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * v) + 1;
		}
		return arr;
	}

	// 为了测试
	public static int[] copyArray(int[] arr) {
		int[] ans = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			ans[i] = arr[i];
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int len = 7;
		int v = 10;
		int testTime = 100;
		System.out.println("==========");
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * len) + 1;
			int[] arr = randomArray(n, v);
			int[] arr0 = copyArray(arr);
			int[] arr1 = copyArray(arr);
			int[] arr2 = copyArray(arr);
			int[] arr3 = copyArray(arr);
			int[] arr4 = copyArray(arr);
			int ans0 = minCost0(arr0);
			int ans1 = minCost1(arr1);
			int ans2 = minCost2(arr2);
			int ans3 = minCost3(arr3);
			int ans4 = yeah(arr4);
			if (ans0 != ans1 || ans0 != ans2 || ans0 != ans3 || ans0 != ans4) {
				System.out.println("出错了！");
			}
		}
		System.out.println("功能测试结束");
		System.out.println("==========");

		System.out.println("性能测试开始");

		len = 10000;
		v = 500;
		System.out.println("生成随机数组长度：" + len);
		System.out.println("生成随机数组值的范围：[1, " + v + "]");
		int[] arr = randomArray(len, v);
		int[] arr3 = copyArray(arr);
		int[] arrYeah = copyArray(arr);
		long start;
		long end;
		start = System.currentTimeMillis();
		int ans3 = minCost3(arr3);
		end = System.currentTimeMillis();
		System.out.println("minCost3方法:");
		System.out.println("运行结果: " + ans3 + ", 时间(毫秒) : " + (end - start));

		start = System.currentTimeMillis();
		int ansYeah = yeah(arrYeah);
		end = System.currentTimeMillis();
		System.out.println("yeah方法:");
		System.out.println("运行结果: " + ansYeah + ", 时间(毫秒) : " + (end - start));

		System.out.println("性能测试结束");
		System.out.println("==========");

	}

}
