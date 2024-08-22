package other.mid.class07;

/*
* 新类型字符的定义如下:
* 1.新类型字符是长度为1或者2的字符串。
* 2.表现形式可以仅是小写字母，也可以是大写字母+小写字母，还可以是大写字母+大写字母。
* 现在给定一个字符串str， str一定是若干新类型字符正确组合的结果。比如"eaCCBi"，由新类型字符组成。
 * 再给定一个整数k，代表str中的位置。请返回被k位置指中的新类型字符。
*
* */
public class Code08_FindNewTypeChar {

	public static String pointNewchar(String s, int k) {
		if (s == null || s.equals("") || k < 0 || k >= s.length()) {
			return "";
		}
		char[] chas = s.toCharArray();
		int uNum = 0;
		for (int i = k - 1; i >= 0; i--) {
			if (!isUpper(chas[i])) {
				break;
			}
			uNum++;
		}
		if ((uNum & 1) == 1) {
			return s.substring(k - 1, k + 1);
		}
		if (isUpper(chas[k])) {
			return s.substring(k, k + 2);
		}
		return String.valueOf(chas[k]);
	}

	public static boolean isUpper(char ch) {
		return !(ch < 'A' || ch > 'Z');
	}

	public static void main(String[] args) {
		String str = "aaABCDEcBCg";
		System.out.println(pointNewchar(str, 7));
		System.out.println(pointNewchar(str, 4));
		System.out.println(pointNewchar(str, 10));

	}

}
