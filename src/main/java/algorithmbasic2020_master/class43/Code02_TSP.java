package algorithmbasic2020_master.class43;

import java.util.ArrayList;
import java.util.List;

public class Code02_TSP {

	public static int t1(int[][] matrix) {
		int N = matrix.length; // 0...N-1 个城市
		// set
		// set.get(i) != null i这座城市在集合里
		// set.get(i) == null i这座城市不在集合里
		List<Integer> set = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			set.add(1);
		}
		return func1(matrix, set, 0);
	}

	/*
	  TODO
	   1.任何两座城市之间的距离，可以在matrix里面拿到
	   2.set中表示着哪些城市的集合，
	   3.start这座城一定在set里，
	   4. 规则：  从start出发，要把set中所有的城市过一遍，最终回到0这座城市，最小距离是多少
	   func1(int[][] matrix, List<Integer> set, int start)
	    表示 以start作为出发点 把集合里城市过一遍最后回到归宿点的代价
	*/
	public static int func1(int[][] matrix, List<Integer> set, int start) {
		int cityNum = 0;
		//TODO 统计set集合中还有几座城市
		for (int i = 0; i < set.size(); i++) {
			if (set.get(i) != null) {//TODO 表示i这个城市还在
				cityNum++;
			}
		}
		//TODO base case 集合中的城市只有1个了 也就是当前的start就是那个唯一的城市 那么当前的func1函数 就是 f({start},start)
		//TODO 以start这个城市出发 把集合里的所有元素 过一遍 最终回到归宿点（0）
		if (cityNum == 1) {
			return matrix[start][0];
		}
		// cityNum > 1  不只start这一座城
		set.set(start, null);//TODO 先把当前的城市start 删除
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < set.size(); i++) {
			if (set.get(i) != null) {//TODO  set.get(i) != null 表示如果还能去i这个城市的话
				//TODO 因为 先start -> i 再 i... -> 0
				int cur = matrix[start][i] + func1(matrix, set, i);
				min = Math.min(min, cur);
			}
		}
		set.set(start, 1);
		return min;
	}

	public static int t2(int[][] matrix) {
		int N = matrix.length; // 0...N-1
		// 7座城 1111111
		int allCity = (1 << N) - 1;
		//TODO 用位来表示某个城市是否还在
		return f2(matrix, allCity, 0);
	}

	/*
 	 TODO
   		1.任何两座城市之间的距离，可以在matrix里面拿到
   		2.set中表示着哪些城市的集合，
   		3.start这座城一定在set里，
   		4. 规则：  从start出发，要把set中所有的城市过一遍，最终回到0这座城市，最小距离是多少
   		func1(int[][] matrix, List<Integer> set, int start)
   		 表示 以start作为出发点 把集合里城市过一遍最后回到归宿点的代价
	*/
	public static int f2(int[][] matrix, int cityStatus, int start) {
		// cityStatus == cityStatux & (~cityStaus + 1)
		//TODO base case 如果city中只有一个1了
		//TODO "~"意思就是取反
		if (cityStatus == (cityStatus & (~cityStatus + 1))) {//TODO 把二进制中的最右侧的1拿出来 和cityStatus 相同
			return matrix[start][0];
		}

		//TODO 把start位的1去掉，
		cityStatus &= (~(1 << start));
		int min = Integer.MAX_VALUE;
		//TODO 枚举所有的城市
		for (int move = 0; move < matrix.length; move++) {
			if ((cityStatus & (1 << move)) != 0) {//TODO 枚举到的城市必须在集合 或者说 对应的位是1
				int cur = matrix[start][move] + f2(matrix, cityStatus, move);
				min = Math.min(min, cur);
			}
		}
		cityStatus |= (1 << start);
		return min;
	}

	public static int t3(int[][] matrix) {
		int N = matrix.length; // 0...N-1
		// 7座城 1111111
		int allCity = (1 << N) - 1;
		//TODO 二维表 是因为 cityStatus 和 start一起决定返回值
		int[][] dp = new int[1 << N][N];
		/*
		* TODO 没算过的就是-1 算过的就是其他值
		* */
		for (int i = 0; i < (1 << N); i++) {
			for (int j = 0; j < N; j++) {
				dp[i][j] = -1;
			}
		}
		return f3(matrix, allCity, 0, dp);
	}

	/*
  	TODO
       1.任何两座城市之间的距离，可以在matrix里面拿到
       2.set中表示着哪些城市的集合，
       3.start这座城一定在set里，
       4. 规则：  从start出发，要把set中所有的城市过一遍，最终回到0这座城市，最小距离是多少
       func1(int[][] matrix, List<Integer> set, int start)
        表示 以start作为出发点 把集合里城市过一遍最后回到归宿点的代价
	*/
	public static int f3(int[][] matrix, int cityStatus, int start, int[][] dp) {
		if (dp[cityStatus][start] != -1) {//TODO 如果算过了
			return dp[cityStatus][start];//TODO 直接返回
		}
		//TODO base case 如果city中只有一个1了
		//TODO "~"意思就是取反
		if (cityStatus == (cityStatus & (~cityStatus + 1))) {//TODO 把二进制中的最右侧的1拿出来 和cityStatus 相同
			dp[cityStatus][start] = matrix[start][0];
		} else {
			// 把start位的1去掉，
			cityStatus &= (~(1 << start));
			int min = Integer.MAX_VALUE;
			// 枚举所有的城市
			for (int move = 0; move < matrix.length; move++) {
				if (move != start && (cityStatus & (1 << move)) != 0) {
					int cur = matrix[start][move] + f3(matrix, cityStatus, move, dp);
					min = Math.min(min, cur);
				}
			}
			cityStatus |= (1 << start);
			dp[cityStatus][start] = min;
		}
		return dp[cityStatus][start];
	}

	public static int t4(int[][] matrix) {
		int N = matrix.length; // 0...N-1
		int statusNums = 1 << N;
		int[][] dp = new int[statusNums][N];

		for (int status = 0; status < statusNums; status++) {
			for (int start = 0; start < N; start++) {
				if ((status & (1 << start)) != 0) {
					if (status == (status & (~status + 1))) {
						dp[status][start] = matrix[start][0];
					} else {
						int min = Integer.MAX_VALUE;
						// start 城市在status里去掉之后，的状态
						int preStatus = status & (~(1 << start));
						// start -> i
						for (int i = 0; i < N; i++) {
							if ((preStatus & (1 << i)) != 0) {
								int cur = matrix[start][i] + dp[preStatus][i];
								min = Math.min(min, cur);
							}
						}
						dp[status][start] = min;
					}
				}
			}
		}
		return dp[statusNums - 1][0];
	}

	// matrix[i][j] -> i城市到j城市的距离
	public static int tsp1(int[][] matrix, int origin) {
		if (matrix == null || matrix.length < 2 || origin < 0 || origin >= matrix.length) {
			return 0;
		}
		// 要考虑的集合
		ArrayList<Integer> cities = new ArrayList<>();
		// cities[0] != null 表示0城在集合里
		// cities[i] != null 表示i城在集合里
		for (int i = 0; i < matrix.length; i++) {
			cities.add(1);
		}
		// null,1,1,1,1,1,1
		// origin城不参与集合
		cities.set(origin, null);
		return process(matrix, origin, cities, origin);
	}

	// matrix 所有距离，存在其中
	// origin 固定参数，唯一的目标
	// cities 要考虑的集合，一定不含有origin
	// 当前来到的城市是谁，cur
	public static int process(int[][] matrix, int aim, ArrayList<Integer> cities, int cur) {
		boolean hasCity = false; // 集团中还是否有城市
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < cities.size(); i++) {
			if (cities.get(i) != null) {
				hasCity = true;
				cities.set(i, null);
				// matrix[cur][i] + f(i, 集团(去掉i) )
				ans = Math.min(ans, matrix[cur][i] + process(matrix, aim, cities, i));
				cities.set(i, 1);
			}
		}
		return hasCity ? ans : matrix[cur][aim];
	}

	// cities 里，一定含有cur这座城
	// 解决的是，集合从cur出发，通过集合里所有的城市，最终来到aim，最短距离
	public static int process2(int[][] matrix, int aim, ArrayList<Integer> cities, int cur) {
		if (cities.size() == 1) {
			return matrix[cur][aim];
		}
		cities.set(cur, null);
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < cities.size(); i++) {
			if (cities.get(i) != null) {
				int dis = matrix[cur][i] + process2(matrix, aim, cities, i);
				ans = Math.min(ans, dis);
			}
		}
		cities.set(cur, 1);
		return ans;
	}

	public static int tsp2(int[][] matrix, int origin) {
		if (matrix == null || matrix.length < 2 || origin < 0 || origin >= matrix.length) {
			return 0;
		}
		int N = matrix.length - 1; // 除去origin之后是n-1个点
		int S = 1 << N; // 状态数量
		int[][] dp = new int[S][N];
		int icity = 0;
		int kcity = 0;
		for (int i = 0; i < N; i++) {
			icity = i < origin ? i : i + 1;
			// 00000000 i
			dp[0][i] = matrix[icity][origin];
		}
		for (int status = 1; status < S; status++) {
			// 尝试每一种状态 status = 0 0 1 0 0 0 0 0 0
			// 下标 8 7 6 5 4 3 2 1 0
			for (int i = 0; i < N; i++) {
				// i 枚举的出发城市
				dp[status][i] = Integer.MAX_VALUE;
				if ((1 << i & status) != 0) {
					// 如果i这座城是可以枚举的，i = 6 ， i对应的原始城的编号，icity
					icity = i < origin ? i : i + 1;
					for (int k = 0; k < N; k++) { // i 这一步连到的点，k
						if ((1 << k & status) != 0) { // i 这一步可以连到k
							kcity = k < origin ? k : k + 1; // k对应的原始城的编号，kcity
							dp[status][i] = Math.min(dp[status][i], dp[status ^ (1 << i)][k] + matrix[icity][kcity]);
						}
					}
				}
			}
		}
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < N; i++) {
			icity = i < origin ? i : i + 1;
			ans = Math.min(ans, dp[S - 1][i] + matrix[origin][icity]);
		}
		return ans;
	}

	public static int[][] generateGraph(int maxSize, int maxValue) {
		int len = (int) (Math.random() * maxSize) + 1;
		int[][] matrix = new int[len][len];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				matrix[i][j] = (int) (Math.random() * maxValue) + 1;
			}
		}
		for (int i = 0; i < len; i++) {
			matrix[i][i] = 0;
		}
		return matrix;
	}

	public static void main(String[] args) {
		int len = 10;
		int value = 100;
		System.out.println("功能测试开始");
		for (int i = 0; i < 20000; i++) {
			int[][] matrix = generateGraph(len, value);
			int origin = (int) (Math.random() * matrix.length);
			int ans1 = t3(matrix);
			int ans2 = t4(matrix);
			int ans3 = tsp2(matrix, origin);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("fuck");
			}
		}
		System.out.println("功能测试结束");

		len = 22;
		System.out.println("性能测试开始，数据规模 : " + len);
		int[][] matrix = new int[len][len];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				matrix[i][j] = (int) (Math.random() * value) + 1;
			}
		}
		for (int i = 0; i < len; i++) {
			matrix[i][i] = 0;
		}
		long start;
		long end;
		start = System.currentTimeMillis();
		t4(matrix);
		end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + " 毫秒");
		System.out.println("性能测试结束");

	}

}
