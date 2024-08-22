package code_for_great_offer.class25;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// 本题测试链接 : https://leetcode.cn/problems/ip-to-cidr/
public class Code01_IPToCIDR {

	public static List<String> ipToCIDR(String ip, int n) {
		// ip -> 32位整数
		int cur = status(ip);
		// cur这个状态，最右侧的1，能表示下2的几次方
		int maxPower = 0;
		// 已经解决了多少ip了
		int solved = 0;
		// 临时变量
		int power = 0;
		List<String> ans = new ArrayList<>();
		while (n > 0) {//说明还有ip需要去解决

			/*
			* 查看cur最右侧的1，能搞定2的几次方个ip
			* eg: cur : 000...000 01001000
			* 最后一位是第4位 =能搞定2^3个IP => power = 3
			* */
			maxPower = mostRightPower(cur);
			/*
			* 假设 solved不断的左移 直到与cur相同 cur就是n
			*  cur : 0000....0000 00001000 -> 2的3次方的问题
			*  sol初始状态 : 0000....0000 00000001 -> 1 2的0次方
			*  sol : 0000....0000 00000010 -> 2 2的1次方
			*  sol : 0000....0000 00000100 -> 4 2的2次方
			*  sol : 0000....0000 00001000 -> 8 2的3次方
			* */
			solved = 1;
			power = 0;
			/*
			* solved 解决的数量 就不能超过n
			* 其次 solved向左移动的过程不能超过cur的范围，
			* 因为在试cur本身够不够用解决n个IP
			* 提前去 solved左移 提前power + 1（while循环的条件）
			* 就是防止溢出
			* */
			while ((solved << 1) <= n && (power + 1) <= maxPower) {
				solved <<= 1;
				power++;
			}
			ans.add(content(cur, power));
			/*
			* n-当前能解决的ip数量
			* 如果 n-solved > 0 会进行下一轮循环 说明 cur不够用
			* 否者就是够用
			* */
			n -= solved;
			//相当于cur的最后一个1 所在位 上升了一位 下一轮循环去查看 上升一位之后 够不够用
			cur += solved;
		}
		return ans;
	}

	// ip -> int(32位状态)
	public static int status(String ip) {
		int ans = 0;
		int move = 24;
		// "\\"防止转义
		for (String str : ip.split("\\.")) {
			// 17.23.16.5 "17" "23" "16" "5"
			// "17.0.0.0" -> 17 << 24 ->17
			// "23" -> 23 << 16
			// "16" -> 16 << 8
			// "5" -> 5 << 0
			ans |= Integer.valueOf(str) << move;
			move -= 8;
		}
		return ans;
	}
	/*
	* 如果32位全是0 那么 能搞定 2的32次方个IP 就认为你当前的1是最左侧溢出位
	* */
	public static HashMap<Integer, Integer> map = new HashMap<>();

	public static int mostRightPower(int num) {
		// map只会生成1次，以后直接用
		//TODO key是 1个有1个1其他全是0的32位数 value 是 给这个32位数的1所在的位置 假设是i 那么 31-i能表示多少IP
		if (map.isEmpty()) {
			map.put(0, 32);
			/*
			* i向左移动0位  00...0000 00000001 2的0次方
			* i向左移动1位  00...0000 00000010 2的0次方
			* i向左移动2位 00...0000 00000100 2的2次方
			* i向左移动3位 00...0000 00001000 2的3次方
			* ....
			* */
			for (int i = 0; i < 32; i++) {

				map.put(1 << i, i);
			}
		}
		// num & (-num) 就是 num & (~num+1) -> 提取出最右侧的1
		return map.get(num & (-num));
	}

	public static String content(int status, int power) {
		StringBuilder builder = new StringBuilder();
		for (int move = 24; move >= 0; move -= 8) {
			builder.append(((status & (255 << move)) >>> move) + ".");
		}
		builder.setCharAt(builder.length() - 1, '/');
		builder.append(32 - power);
		return builder.toString();
	}

}
