package leetcode;

import java.util.Arrays;

/**
 * code_for_great_offer.class12
 * 本题测试链接 : https://leetcode.com/problems/permutation-in-string/
 */
public class Leetcode_567_ContainAllCharExactly {

	public static int containExactly1(String s, String a) {
		if (s == null || a == null || s.length() < a.length()) {
			return -1;
		}
		char[] aim = a.toCharArray();
		Arrays.sort(aim);
		String aimSort = String.valueOf(aim);
		for (int L = 0; L < s.length(); L++) {
			for (int R = L; R < s.length(); R++) {
				char[] cur = s.substring(L, R + 1).toCharArray();
				Arrays.sort(cur);
				String curSort = String.valueOf(cur);
				if (curSort.equals(aimSort)) {
					return L;
				}
			}
		}
		return -1;
	}

	public static int containExactly2(String s, String a) {
		if (s == null || a == null || s.length() < a.length()) {
			return -1;
		}
		char[] str = s.toCharArray();
		char[] aim = a.toCharArray();
		for (int L = 0; L <= str.length - aim.length; L++) {
			if (isCountEqual(str, L, aim)) {
				return L;
			}
		}
		return -1;
	}

	public static boolean isCountEqual(char[] str, int L, char[] aim) {
		int[] count = new int[256];
		for (int i = 0; i < aim.length; i++) {
			count[aim[i]]++;
		}
		for (int i = 0; i < aim.length; i++) {
			if (count[str[L + i]]-- == 0) {
				return false;
			}
		}
		return true;
	}

	public static int containExactly3(String s1, String s2) {
		if (s1 == null || s2 == null || s1.length() < s2.length()) {
			return -1;
		}
		char[] str2 = s2.toCharArray();
		int M = str2.length;
		//记录的是字母和对应的词频 相当于欠账表
		int[] count = new int[256];//可以用该数组表示 也可以是hashmap
		for (int i = 0; i < M; i++) {
			count[str2[i]]++;
		}
		int all = M;//初始的all 就是str2的长度
		char[] str1 = s1.toCharArray();
		int R = 0;
		// 0~M-1
		for (; R < M; R++) { //这个循环 最早的M个字符，让其窗口初步形成
			if (count[str1[R]]-- > 0) {
				all--;
			}
		}
		/*
		* 窗口初步形成了，并没有判断有效无效，决定下一个位置一上来判断
		* 接下来的过程，窗口右进一个，左吐一个
		* 到达n位置 对于n位置 查看的是n-1为右边界的窗口是否满足要求
		* */
		for (; R < str1.length; R++) {
			if (all == 0) { //此时的窗口的右边界是 R-1
				return R - M;//表示R-M~R的范围可以找到str2的变形词
			}
			if (count[str1[R]]-- > 0) {// 词频--后还是>0 说明是一次有效的还款
				all--;
			}
			//如果++是>=0的 那就让all++
			if (count[str1[R - M]]++ >= 0) {//窗口右移 那么要突吐出R-M位置的字符
				all++;
			}
		}
		//最后的窗口的时候 查询all
		return all == 0 ? R - M : -1;
	}

	// for test
	public static String getRandomString(int possibilities, int maxSize) {
		char[] ans = new char[(int) (Math.random() * maxSize) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
		}
		return String.valueOf(ans);
	}

	public static void main(String[] args) {
		int possibilities = 5;
		int strMaxSize = 20;
		int aimMaxSize = 10;
		int testTimes = 500000;
		System.out.println("test begin, test time : " + testTimes);
		for (int i = 0; i < testTimes; i++) {
			String str = getRandomString(possibilities, strMaxSize);
			String aim = getRandomString(possibilities, aimMaxSize);
			int ans1 = containExactly1(str, aim);
			int ans2 = containExactly2(str, aim);
			int ans3 = containExactly3(str, aim);
			if (ans1 != ans2 || ans2 != ans3) {
				System.out.println("Oops!");
				System.out.println(str);
				System.out.println(aim);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
				break;
			}
		}
		System.out.println("test finish");

	}

}
