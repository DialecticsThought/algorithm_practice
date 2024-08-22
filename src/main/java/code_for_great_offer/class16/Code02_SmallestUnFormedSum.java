package code_for_great_offer.class16;

import java.util.Arrays;
import java.util.HashSet;

/**
 *TODO
 * 给K定MP一个算法正扩数展数题组目二arr， 其中所有的值都为整数， 以下是最小不可组成和的概念:
 * 把 arr 每个子集内的所有元素加起来会出现很多值， 其中最小的记为 min， 最大的记为max
 * 在区间[min,max]上， 如果有数不可以被arr某一个子集相加得到， 那么其中最小的那个数是arr
 * 的最小不可组成和
 * 在区间[min,max]上， 如果所有的数都可以被arr的某一个子集相加得到， 那么max+1是arr的最
 * 小不可组成和
 * 请写函数返回正数数组 arr 的最小不可组成和。
 * 【 举例】
 * arr=[3,2,5]。 子集{2}相加产生 2 为 min， 子集{3,2,5}相加产生 10 为 max。 在区间[2,10]
 * 上， 4、 6 和 9 不能被任何子集相加得到， 其中 4 是 arr 的最小不可组成和。
 * arr=[1,2,4]。 子集{1}相加产生 1 为 min， 子集{1,2,4}相加产生 7 为 max。 在区间[1,7]上，
 * 任何 数都可以被子集相加得到， 所以 8 是 arr 的最小不可组成和。
 * 【 进阶】
 * 如果已知正数数组 arr 中肯定有 1 这个数， 是否能更快地得到最小不可组成和?
 *
 *TODO
 * 给定一个正整数组arr返回arr的子集不能累加出的最小正数
 * 1.正常怎么做？ 2.如果arr中肯定有1这个值，怎么做
 * 解决方法：
 * 1.第一步：一定要给arr排序
 * 因为arr中一定有1+正整数arr，那么arr[0]=1
 * 2.定义一个变量range：表示，1~range范围上的所有数组，都能被arr中的元素累加出来
 * 并且初始值range=1，因为arr[0]=1
 * eg:arr[1,1,2.....,arr[i],.....]
 * 遍历到0下标，range变成1
 * 遍历到1下标，range变成2，
 * 		因为，arr[1]=2，说明arr[0]~arr[1]一定能搞出2来	即：arr[0]+arr[1] =2
 * 遍历到2下标，range变成4，
 * 		因为，arr[1]=2，说明arr[0]~arr[2]一定能搞出3来  即：arr[0]+arr[2] =3
 * 		因为，arr[1]=2，说明arr[0]~arr[2]一定能搞出4来	即：arr[0]+arr[1]+arr[2]=4
 * .....
 * eg：
 * 假设并遍历到i下标，arr[i]=17,且arr[0]~arr[i-1]能搞定出[1~100]所有数
 * 那么arr[0]~arr[i]一定能搞定出[1~117]范围上的所有数字
 * 因为arr[0]~arr[i-1]能搞定出[1~100]所有数，那么arr[0]~arr[i-1]能搞定出84这个数,84+arr[i]=101，
 * 			所以101这个数能被arr[0]~arr[i-1]累加出来
 * 因为arr[0]~arr[i-1]能搞定出[1~100]所有数，那么arr[0]~arr[i-1]能搞定出85这个数,85+arr[i]=102，
 * 			所以102这个数能被arr[0]~arr[i-1]累加出来
 * ......
 * 因为arr[0]~arr[i-1]能搞定出[1~100]所有数，那么arr[0]~arr[i-1]能搞定出100这个数,100+arr[i]=117，
 * 			所以117这个数能被arr[0]~arr[i-1]累加出来
 * eg：
 * 假设并遍历到i下标，arr[i]=101,且arr[0]~arr[i-1]能搞定出[1~100]所有数
 * 那么arr[0]~arr[i]一定能搞定出[1~201]范围上的所有数字
 * arr[0]~arr[i]一定能搞定出101，那是因为arr[i]=101 不用累加，本来就有
 * 因为arr[0]~arr[i-1]能搞定出[1~100]所有数，那么arr[0]~arr[i-1]能搞定出1这个数,1+arr[i]=102，
 * 			所以102这个数能被arr[0]~arr[i-1]累加出来
 * .....
 * 因为arr[0]~arr[i-1]能搞定出[1~100]所有数，那么arr[0]~arr[i-1]能搞定出100这个数,100+arr[i]=201，
 * 			所以201这个数能被arr[0]~arr[i-1]累加出来
 * eg:
 *  * 假设并遍历到i下标，arr[i]=102,且arr[0]~arr[i-1]能搞定出[1~100]所有数
 *  那么arr[0]~arr[i]一定不能搞定出[1~201]范围上的所有数字
 *  arr[0]~arr[i]不能累加出101这个数，因为arr是从小到大排序的
 *  	1.arr[0]~arr[i-1]能搞定出[1~100]
 *  	2.arr[i]==102
 *  那么102就不能被累加出来 或者 arr中本来就有
 * 总结：
 * arr[0]~[i-1]搞定了1~a,arr[i]=b
 * 如果 b<=a+1,那么新的range[1~a+b]
 * 如果 b>a+1,range搞不出来
 */
public class Code02_SmallestUnFormedSum {

	public static int unformedSum1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 1;
		}
		HashSet<Integer> set = new HashSet<Integer>();
		process(arr, 0, 0, set);
		int min = Integer.MAX_VALUE;
		for (int i = 0; i != arr.length; i++) {
			min = Math.min(min, arr[i]);
		}
		for (int i = min + 1; i != Integer.MIN_VALUE; i++) {
			if (!set.contains(i)) {
				return i;
			}
		}
		return 0;
	}

	public static void process(int[] arr, int i, int sum, HashSet<Integer> set) {
		if (i == arr.length) {
			set.add(sum);
			return;
		}
		process(arr, i + 1, sum, set);
		process(arr, i + 1, sum + arr[i], set);
	}

	public static int unformedSum2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 1;
		}
		int sum = 0;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i != arr.length; i++) {
			sum += arr[i];
			min = Math.min(min, arr[i]);
		}
		// boolean[][] dp ...
		int N = arr.length;
		boolean[][] dp = new boolean[N][sum + 1];
		for (int i = 0; i < N; i++) {// arr[0..i] 0
			dp[i][0] = true;
		}
		dp[0][arr[0]] = true;
		for (int i = 1; i < N; i++) {
			for (int j = 1; j <= sum; j++) {
				dp[i][j] = dp[i - 1][j] || ((j - arr[i] >= 0) ? dp[i - 1][j - arr[i]] : false);
			}
		}
		for (int j = min; j <= sum; j++) {
			if (!dp[N - 1][j]) {
				return j;
			}
		}
		return sum + 1;
	}

	// 已知arr中肯定有1这个数
	public static int unformedSum3(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		Arrays.sort(arr); // O (N * logN)
		int range = 1;
		// arr[0] == 1
		for (int i = 1; i != arr.length; i++) {
			if (arr[i] > range + 1) {
				return range + 1;
			} else {
				range += arr[i];
			}
		}
		return range + 1;
	}


	//保证arr中都是正数，找到最小不可组成和
	public static int unformedSum4(int[ ] arr) {
		int min = Integer.MAX_VALUE;
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			min = Math.min(min,arr[i]);
			sum += arr[i];
		}
		int N = arr.length;
		boolean[][] dp = new boolean[N][sum + 1];
		//第0行第0列一定是false
		dp[0][arr[0]] = true;
		for(int row = 1; row<N;row++){
			/*
			 * 第0列一定是false
			 * 从左往右
			 * 再上往下
			 * i行的 arr[i]列数一定是是true
			 * */
			for(int col = 1;col<=sum;col++){
				if(arr[row]==col){
					dp[row][col]=true;
				}else if(dp[row-1][col]){
					dp[row][col]=true;
					//col-arr[i]>=0是保证不越界
				}else if(col-arr[row]>=0&&dp[row-1][col-arr[row]]){
					dp[row][col]=true;
				}
			}
		}
		//只是用最后一行的值
		int ans =min;
		for(;ans<=sum;ans++){
			/*
			 * 从最后一行开始试
			 * 从最小值开始试 哪个累加和是无法组成的就返回sum
			 * 如果都可以返回sum+1
			 * */
			if(!dp[N-1][ans]){
				return ans;
			}
		}
		return sum+1;
	}

	public static int[] generateArray(int len, int maxValue) {
		int[] res = new int[len];
		for (int i = 0; i != res.length; i++) {
			res[i] = (int) (Math.random() * maxValue) + 1;
		}
		return res;
	}

	public static void printArray(int[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int len = 27;
		int max = 30;
		int[] arr = generateArray(len, max);
		printArray(arr);
		long start = System.currentTimeMillis();
		System.out.println(unformedSum1(arr));
		long end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + " ms");
		System.out.println("======================================");

		start = System.currentTimeMillis();
		System.out.println(unformedSum2(arr));
		end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + " ms");
		System.out.println("======================================");

		System.out.println("set arr[0] to 1");
		arr[0] = 1;
		start = System.currentTimeMillis();
		System.out.println(unformedSum3(arr));
		end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + " ms");

	}
}
