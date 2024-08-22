package code_for_great_offer.class51;

import java.util.Arrays;
import java.util.HashSet;

// leetcode题目 : https://leetcode-cn.com/problems/programmable-robot/
public class LCP_0003_Robot {
	/*
	*TODO
	* 遇到障碍
	* 没有到达目标地点
	* 都是FALSE
	* 最终到达地点就是TRUE
	* */
	public static boolean robot1(String command, int[][] obstacles, int x, int y) {
		int X = 0;
		int Y = 0;
		HashSet<Integer> set = new HashSet<>();
		//TODO 因为 0_0 天然就存在 表示 x 和 y 方向移动0单位
		set.add(0);
		for (char c : command.toCharArray()) {
			if( c == 'R'){
				X += 1 ;
			}else {
				X += 0;
			}
			if( c == 'U'){
				Y += 1 ;
			}else {
				Y += 0;
			}
			//Y += c == 'U' ? 1 : 0;
			//TODO 用 位运算 来替代 x_y
			set.add((X << 10) | Y);
		}
		//TODO 不考虑任何额外的点，机器人能不能到达，(x，y)
		if (!meet1(x, y, X, Y, set)) {
			return false;
		}
		//TODO 考虑在撞上目标的情况下，是否会撞上障碍物
		for (int[] ob : obstacles) { // ob[0] ob[1]
			//TODO 障碍物必须 纵坐标 横坐标  <= 目标 纵坐标 横坐标
			if (ob[0] <= x && ob[1] <= y && meet1(ob[0], ob[1], X, Y, set)) {
				return false;
			}
		}
		return true;
	}


	/*
	*TODO
	* 一轮以内，
	* X，往右一共有几个单位
	* Y, 往上一共有几个单位
	* set, 一轮以内的所有可能性
	* (x,y)要去的点
	* 机器人从(0,0)位置，能不能走到(x,y)
	*TODO
	* 第一步先把目标传入函数 看看是否能撞上目标
	* 如果连目标都装不上 那么结束 返回F
	* 如果撞的上，那么分别把障碍物的坐标传入 看看是否能不能撞上某个障碍物
	* 那么也直接返回F
	* 如果障碍物有1000个 有一个目标
	* 那么就是调用1001次meet1
	* */
	public static boolean meet1(int x, int y, int X, int Y, HashSet<Integer> set) {
		/*
		*TODO
		* 执行指令之前 x和y都移动0单位
		* eg: 有个指令 = "URUURRR"
		* 对于str[0]=U 说明 x移动1单位 y移动0单位
		* 对于str[0~1]=UR 说明 x移动1单位 y移动1单位
		* 对于str[0~2]=URU 说明 x移动2单位 y移动1单位
		* 对于str[0~3]=URUU 说明 x移动3单位 y移动1单位
		* ....
		* 把str的前缀都搞出来，掌握 一轮以内 x和y方向分别移动的所有可能性
		* 记录在hashset里面
		* 即 <1,0> <1,1> <2,1> <3,1> .....
		*TODO
		* str = URRUR
		* 这个str说明 整个一轮 x方向一共移动3单位 y方向一共移动2单位
		* eg 假设有个位置(13,29)
		* 执行 13 / 3 = 4  29 / 2 = 14
		* min(4,14)  = 4 说明 到达这个位置 robot至少经历4轮
		* 4轮之后 剩余 多少距离
		* 13 -3*4=1  29-2*14=1
		* 再查看这一轮以内 有没有这个可能性 查set
		* 没有这个可能性 说明机器人到不了这个位置
		* */
		if (X == 0) { //TODO 这个分支说明 Y != 0 肯定往上走了！
			/*
			*TODO
			* X == 0 说明如果一轮以内 x方向不动 ，那么x必须是0
			* 本质 是查set中 有没有(0<<10 | (y%Y)) 这个值
			* 有个目标 （7,70）
			* 但是如果当前一轮 x 方向 移动 0 单位， y 方向 移动10单位
			* 这是不可能达到的 因为 x方向完全有移动
			* 但是 目标 (0,70)
			* 也就是说 x方向 没有必要移动
			* 那么当前一轮 x 方向 移动 0 单位 也无所谓
			* 在y 方向上 70 / 10 = 7 70 % 10 = 0
			* 所以在set中查找 一轮内 y方向上有没有 移动0单位的可能性(因为70 % 10 = 0)即可
			* */
			return x == 0 /*&& set.contains(y%Y)*/;
		}
		if (Y == 0) { //TODO 这个分支说明 X != 0 肯定往右走了！
			/*
			*TODO  Y == 0 说明如果一轮以内 y方向不动 ，那么y必须是0
			* 本质 是查set中 有没有((x%X)<<10 | 0) 这个值
			* */
			return y == 0 /*&& set.contains((x%X)<<10)*/;
		}
		//TODO 至少几轮？
		int atLeast = Math.min(x / X, y / Y);
		//TODO 经历过最少轮数后，x剩多少？
		int rx = x - atLeast * X;
		//TODO 经历过最少轮数后，y剩多少？
		int ry = y - atLeast * Y;
		//TODO 查看从当前位置走 走了atleast轮之后 ，再能不能x方向走rx单位  y方向走ry单位 到达目的地
		return set.contains((rx << 10) | ry);
	}
	/*
	 *TODO
	 * 高端写法
	 * 因为指令长度 <=1000 说明x 和 y 方向的移动单位都 <=1000
	 * 2^10 = 1024 >= 1000
	 * 那么 用20位 前10位表示 x方向移动的单位 后10位表示 y方向移动的单位
	 *TODO
	 * 此处为一轮以内，x和y最大能移动的步数，对应的2的几次方
	 * 比如本题，x和y最大能移动1000步，就对应2的10次方
	 * 如果换一个数据量，x和y最大能移动5000步，就对应2的13次方
	 * 只需要根据数据量修改这一个变量，剩下的代码不需要调整
	 * */
	public static final int bit = 10;
	/*
	*TODO
	* 如果，x和y最大能移动的步数，对应2的bit次方
	* 那么一个坐标(x,y)，所有的可能性就是：(2 ^ bit) ^ 2 = 2 ^ (bit * 2)
	* 也就是，(1 << (bit << 1))个状态，记为bits
	* */
	public static int bits = (1 << (bit << 1));
	// 为了表示下bits个状态，需要几个整数？
	// 32位只需要一个整数，所以bits个状态，需要bits / 32 个整数
	// 即整型长度需要 : bits >> 5
	public static int[] set = new int[bits >> 5];

	public static boolean robot2(String command, int[][] obstacles, int x, int y) {
		Arrays.fill(set, 0);
		set[0] = 1;
		int X = 0;
		int Y = 0;
		for (char c : command.toCharArray()) {
			X += c == 'R' ? 1 : 0;
			Y += c == 'U' ? 1 : 0;
			add((X << 10) | Y);
		}
		if (!meet2(x, y, X, Y)) {
			return false;
		}
		for (int[] ob : obstacles) {
			if (ob[0] <= x && ob[1] <= y && meet2(ob[0], ob[1], X, Y)) {
				return false;
			}
		}
		return true;
	}

	public static boolean meet2(int x, int y, int X, int Y) {
		if (X == 0) {
			return x == 0;
		}
		if (Y == 0) {
			return y == 0;
		}
		int atLeast = Math.min(x / X, y / Y);
		int rx = x - atLeast * X;
		int ry = y - atLeast * Y;
		return contains((rx << 10) | ry);
	}

	public static void add(int status) {
		set[status >> 5] |= 1 << (status & 31);
	}

	public static boolean contains(int status) {
		return (status < bits) && (set[status >> 5] & (1 << (status & 31))) != 0;
	}


	/*
	*TODO
	* int num -> 32位的状态
	* 请打印这32位状态
	* 1向左移动31位 和num与运算 不等于0 说明 num的第31位是1
	* 1向左移动30位 和num与运算 不等于0 说明 num的第30位是1
	* 1向左移动29位 和num与运算 不等于0 说明 num的第29位是1
	* .........
	* */
	public static void printBinary(int num) {
		for (int i = 31; i >= 0; i--) {
			System.out.print((num & (1 << i)) != 0 ? "1" : "0");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int x = 7;
		printBinary(x);

		int y = 4;

		printBinary(y);

		//TODO x_y 组合！
		int c = (x << 10) | y;
		printBinary(c);

		System.out.println(c);
		//TODO 位图表示法 不用看

		// 0 ~ 1048575 任何一个数，bit来表示的！
//		int[] set = new int[32768];
//		set[0] = int  32 位   0~31这些数出现过没出现过
//		set[1] = int  32 位   32~63这些数出现过没出现过

		// 0 ~ 1048575
//		int[] set = new int[32768];
//		int num = 738473; // 32 bit int
////		set[  734873 / 32   ] // 734873 % 32
//		boolean exist = (set[num / 32] & (1 << (num % 32))) != 0;

	}

}
