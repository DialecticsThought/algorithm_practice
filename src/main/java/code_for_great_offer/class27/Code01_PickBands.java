package code_for_great_offer.class27;

import java.util.Arrays;

public class Code01_PickBands {
	/*
	 *TODO
	 * 每一个项目都有三个数，[a,b,c]表示这个项目a和b乐队参演，花费为c
	 * 每一个乐队可能在多个项目里都出现了，但是只能挑一次
	 * nums是可以挑选的项目数量，所以一定会有nums*2只乐队被挑选出来
	 * 乐队的全部数量一定是nums*2，且标号一定是0 ~ nums*2-1
	 * 返回一共挑nums轮(也就意味着一定请到所有的乐队)，最少花费是多少？
	 * */
	public static int minCost(int[][] programs, int nums) {
		if (nums == 0 || programs == null || programs.length == 0) {
			return 0;
		}
		int size = clean(programs);
		//TODO 初始化 数组
		int[] map1 = init(1 << (nums << 1));
		int[] map2 = null;
		if ((nums & 1) == 0) {//说明 nums是偶数
			// nums = 8 ,变成 4
			f(programs, size, 0, 0, 0, nums >> 1, map1);
			map2 = map1;//偶数的话 只需要一张表
		} else {
			// nums == 7 拆成2个表  4 -> map1 和  3 -> map2
			f(programs, size, 0, 0, 0, nums >> 1, map1);
			map2 = init(1 << (nums << 1));
			f(programs, size, 0, 0, 0, nums - (nums >> 1), map2);
		}
		// 16 mask : 0..00 1111.1111(16个)
		// 12 mask : 0..00 1111.1111(12个)
		/*
		* 如果 16个乐队的情况 mask : 0..00 1111.1111(16个)
		* 如果 12个乐队的情况 mask : 0..00 1111.1111(12个)
		* */
		int mask = (1 << (nums << 1)) - 1;
		int ans = Integer.MAX_VALUE;
		// 遍历 数组 相当于遍历所有答案 然后取ans 最小
		for (int i = 0; i < map1.length; i++) {
			//map1[i] != Integer.MAX_VALUE 说明 被设置过
			if (map1[i] != Integer.MAX_VALUE && map2[mask & (~i)] != Integer.MAX_VALUE) {
				ans = Math.min(ans, map1[i] + map2[mask & (~i)]);
			}
		}
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}

	/*
	 * 洗数据
	 * // 小的数 是第0个 一次变大
	 * [9, 1, 100] => [1,9,100]
	 * [2, 9 , 50] => [2,9,50]
	 * */
	public static int clean(int[][] programs) {
		int x = 0;
		int y = 0;
		for (int[] p : programs) {
			x = Math.min(p[0], p[1]);
			y = Math.max(p[0], p[1]);
			p[0] = x;
			p[1] = y;
		}
		Arrays.sort(programs, (a, b) -> a[0] != b[0] ? (a[0] - b[0]) : (a[1] != b[1] ? (a[1] - b[1]) : (a[2] - b[2])));
		x = programs[0][0];
		y = programs[0][1];
		int n = programs.length;
		// (0, 1, ? )
		for (int i = 1; i < n; i++) {
			/*
			 *TODO
			 * program的第一个属性和第二个属性相同
			 * 说明当前program 不是这一组的第一个
			 * eg: [1,9,1] [1,9,5]那么 [1,9,5]就不要
			 * */
			if (programs[i][0] == x && programs[i][1] == y) {
				programs[i] = null;
			} else {//TODO 说明 是新的一组 拿出组的第一个
				x = programs[i][0];
				y = programs[i][1];
			}
		}
		int size = 1;//TODO 因为0号program一定会留下来
		for (int i = 1; i < n; i++) {
			if (programs[i] != null) {
				//TODO  当前不null的program 写在 size的位置
				//TODO  因为之前有些program 是null 所以是空位子
				programs[size++] = programs[i];
			}
		}
		// programs[0...size-1]
		return size;
	}

	public static int[] init(int size) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = Integer.MAX_VALUE;
		}
		return arr;
	}

	public static void f(int[][] programs, int size, int index, int status, int cost, int rest, int[] map) {
		if (rest == 0) {
			map[status] = Math.min(map[status], cost);
		} else {
			if (index != size) {
				f(programs, size, index + 1, status, cost, rest, map);
				int pick = 0 | (1 << programs[index][0]) | (1 << programs[index][1]);
				if ((pick & status) == 0) {
					f(programs, size, index + 1, status | pick, cost + programs[index][2], rest - 1, map);
				}
			}
		}
	}
	/*
	 *TODO
	 * 从左往右的尝试某型   来到当前i位置 判断 这个项目要不要
	 * 如果nums，2 * nums 只乐队 最多 16支乐队 因为 nums < 9  nums 最多是8
	 * 所以可以用二进制位 来表示某个乐队
	 *  done = 1 << (nums << 1)) - 1 => nums可以是0~8  done是固定参数
	 * 一个int 是32位 最多是前16位都是1 表示所有乐队都选到了 也就是（1<<16）-1 表示低位的16个位是0
	 * （1<<16）-1 => 可以写成 （1<< 8*2 ）-1 =>  （1<< (8<<1) )-1
	 * rest 表示 还剩下的乐队数量
	 * pick 表示 当前已经选的乐队状态 最终的结果一定是pick = done
	 * cost 表示 之前的选择 导致的花费
	 * eg:
	 * 初始函数 process(programs, size, (1 << (nums << 1)) - 1, 0, nums, 0, 0)
	 * done = (1 << (nums << 1)) - 1  rest = nums pick = 0  cost = 0
	 * done 这个参数可以省略
	 *
	 *
	 * */

	public static int minCost = Integer.MAX_VALUE;
	/*
	*TODO
	* 1<<16 正容量可以包含低位16位的所有情况
	* 0000...0000 32位全是0
	* 0000...0000 31位全是0 最后一位1
	* ....
	* 0000 0000。。。1111 1111  前16位是0 后十六位是1
	* 这个map 就是记录 当前pick状态导致的cost的最小值
	* */
	public static int[] map = new int[1 << 16]; // map初始化全变成系统最大

	public static void process(int[][] programs, int size, int done, int index, int rest, int pick, int cost) {
		if (rest == 0) {
			if(pick == done){
				minCost = Math.min(minCost,cost);
			}
			//map[pick] = Math.min(map[pick], cost);
		} else { // 还有项目可挑
			if (index != size) {
				//TODO case1 不考虑当前的项目！programs[index];  也就是什么也不做
				process(programs, size, done,index + 1, rest, pick, cost);
				//TODO case2 考虑当前的项目！programs[index];
				//TODO eg: [1,5,??] 那么 1位置 和5位置 置为1
				int x = programs[index][0];
				int y = programs[index][1];
				int cur = (1 << x) | (1 << y);
				//TODO 之前挑选乐队导致的状态 和 此时 的状态  说明 当前挑选的2值乐队 此前没有被挑选过
				if ((pick & cur) == 0) { // 终于可以考虑了！
					process(programs, size, done,index + 1, rest - 1, pick | cur, cost + programs[index][2]);
				}
			}
		}
	}

//	public static void zuo(int[] arr, int index, int rest) {
//		if(rest == 0) {
//			停止
//		}
//		if(index != arr.length) {
//			zuo(arr, index + 1, rest);
//			zuo(arr, index + 1, rest - 1);
//		}
//	}

	// 为了测试
	public static int right(int[][] programs, int nums) {
		min = Integer.MAX_VALUE;
		r(programs, 0, nums, 0, 0);
		return min == Integer.MAX_VALUE ? -1 : min;
	}

	public static int min = Integer.MAX_VALUE;

	public static void r(int[][] programs, int index, int rest, int pick, int cost) {
		if (rest == 0) {
			min = Math.min(min, cost);
		} else {
			if (index < programs.length) {
				r(programs, index + 1, rest, pick, cost);
				int cur = (1 << programs[index][0]) | (1 << programs[index][1]);
				if ((pick & cur) == 0) {
					r(programs, index + 1, rest - 1, pick | cur, cost + programs[index][2]);
				}
			}
		}
	}

	// 为了测试
	public static int[][] randomPrograms(int N, int V) {
		int nums = N << 1;
		int n = nums * (nums - 1);
		int[][] programs = new int[n][3];
		for (int i = 0; i < n; i++) {
			int a = (int) (Math.random() * nums);
			int b = 0;
			do {
				b = (int) (Math.random() * nums);
			} while (b == a);
			programs[i][0] = a;
			programs[i][1] = b;
			programs[i][2] = (int) (Math.random() * V) + 1;
		}
		return programs;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 4;
		int V = 100;
		int T = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < T; i++) {
			int nums = (int) (Math.random() * N) + 1;
			int[][] programs = randomPrograms(nums, V);
			int ans1 = right(programs, nums);
			int ans2 = minCost(programs, nums);
			if (ans1 != ans2) {
				System.out.println("Oops!");
				break;
			}
		}
		System.out.println("测试结束");

		long start;
		long end;
		int[][] programs;

		programs = randomPrograms(7, V);
		start = System.currentTimeMillis();
		right(programs, 7);
		end = System.currentTimeMillis();
		System.out.println("right方法，在nums=7时候的运行时间(毫秒) : " + (end - start));

		programs = randomPrograms(10, V);
		start = System.currentTimeMillis();
		minCost(programs, 10);
		end = System.currentTimeMillis();
		System.out.println("minCost方法，在nums=10时候的运行时间(毫秒) : " + (end - start));

	}

}
