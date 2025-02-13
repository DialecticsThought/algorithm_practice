package code_for_great_offer.class36;

/* 来自网易
*  规定：L[1]对应a，L[2]对应b，L[3]对应c，...，L[25]对应y
*  S1 = a
*  S(i) = S(i-1) + L[i] + reverse(invert(S(i-1)));
* 解释invert操作：
*  S1 = a
*  S2 = aby =S1 + L[2]+reverse(invert(S(1))) invert(S(1)) = Y reverse(invert(S(1))) =Y
*  假设invert(S(2)) = 甲乙丙
*  a + 甲 = 26, 那么 甲 = 26 - 1 = 25 -> y
*  b + 乙 = 26, 那么 乙 = 26 - 2 = 24 -> x
*  y + 丙 = 26, 那么 丙 = 26 - 25 = 1 -> a
* 如上就是每一位的计算方式，所以invert(S2) = yxa
* 所以S3 = S2 + L[3] + reverse(invert(S2)) = aby + c + axy = abycaxy
* invert(abycaxy) = yxawyba, 再reverse = abywaxy
* 所以S4 = abycaxy + d + abywaxy = abycaxydabywaxy
* 直到S25结束 因为没有L[26]
* 给定两个参数n和k，返回Sn的第k位是什么字符，n从1开始，k从1开始
* 比如n=4，k=2，表示S4的第2个字符是什么，返回b字符
* S1长度是1
* S2长度是3 因为S1 +L[1]+ reverse(invert(S(1)))
* S3长度是7
* S4长度是15
*/
public class Code01_ReverseInvertString {

	public static int[] lens = null;
	//生成一个长度表 下标是Sn  内容是Sn对应的长度
	public static void fillLens() {
		lens = new int[26];
		lens[1] = 1;
		for (int i = 2; i <= 25; i++) {
			lens[i] = (lens[i - 1] << 1) + 1;
		}
	}

	// 求sn中的第k个字符
	// O(n), s <= 25 O(1)
	public static char kth(int n, int k) {
		/*
		*TODO
		* 假设求 S17的第60个字符
		* 那么先求出S16的长度 假设长度=a
		* 判断 第60个字符 在 a长度里面 还是a+1个 还是a+2长度之外
		* 利用分治
		* 如果60 <a  说明 是在求 S16的第60个字符
		* 如果60 = a+1 说明 就是 L[17]
		* 如果60 > a+1  说明 是在求reverse(invert(S(16)))的某个字符
		*TODO
		* 已知 S5=31 S6=63 现在求 S6的第17个字符
		* 因为 17 < 31 说明 求 S6的第17个字符 就是 求 S5的第17个字符
		* 现在求 S6的第35个字符
		* 因为 35 > 31 说明 求 S6的第17个字符 就是 求 reverse(invert(S(5)))
		* 也就是求reverse(invert(S(5)))的第3个字符
		* 也就是求S(5)的倒数第3个字符 再invert
		* */
		if (lens == null) {
			//生成一个长度表
			fillLens();
		}
		if (n == 1) { // 无视k
			return 'a';
		}
		// Sn的一半 长度 就是 Sn-1
		int half = lens[n - 1];
		if (k <= half) {
			return kth(n - 1, k);
		} else if (k == half + 1) {
			return (char) ('a' + n - 1);
		} else {
			// sn
			// 我需要右半区，从左往右的第a个
			// 需要找到，s(n-1)从右往左的第a个
			// 当拿到字符之后，invert一下，就可以返回了！
			return invert(kth(n - 1, ((half + 1) << 1) - k));
		}
	}

	public static char invert(char c) {
		return (char) (('a' << 1) + 24 - c);
	}

	// 为了测试
	public static String generateString(int n) {
		String s = "a";
		for (int i = 2; i <= n; i++) {
			s = s + (char) ('a' + i - 1) + reverseInvert(s);
		}
		return s;
	}

	// 为了测试
	public static String reverseInvert(String s) {
		char[] invert = invert(s).toCharArray();
		for (int l = 0, r = invert.length - 1; l < r; l++, r--) {
			char tmp = invert[l];
			invert[l] = invert[r];
			invert[r] = tmp;
		}
		return String.valueOf(invert);
	}

	// 为了测试
	public static String invert(String s) {
		char[] str = s.toCharArray();
		for (int i = 0; i < str.length; i++) {
			str[i] = invert(str[i]);
		}
		return String.valueOf(str);
	}

	// 为了测试
	public static void main(String[] args) {
		int n = 20;
		String str = generateString(n);
		int len = str.length();
		System.out.println("测试开始");
		for (int i = 1; i <= len; i++) {
			if (str.charAt(i - 1) != kth(n, i)) {
				System.out.println(i);
				System.out.println(str.charAt(i - 1));
				System.out.println(kth(n, i));
				System.out.println("出错了！");
				break;
			}
		}
		System.out.println("测试结束");
	}

}
