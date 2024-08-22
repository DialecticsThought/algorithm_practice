package code_for_great_offer.class28;

public class Problem_0008_StringToInteger {

	public static int myAtoi(String s) {
		if (s == null || s.equals("")) {
			return 0;
		}
		s = removeHeadZero(s.trim());
		if (s == null || s.equals("")) {
			return 0;
		}
		char[] str = s.toCharArray();
		if (!isValid(str)) {
			return 0;
		}
		// str 是符合日常书写的，正经整数形式
		//TODO 得到 整数的符号位
		boolean posi = str[0] == '-' ? false : true;
		int minq = Integer.MIN_VALUE / 10;
		int minr = Integer.MIN_VALUE % 10;
		//TODO 每一位数字都会放入res中
		int res = 0;
		int cur = 0;
		//TODO 如果 开头是 ± 号 那么 从 str[1]开始转换数字 没有符号 就是 从 str[0]开始转换数字
		for (int i = (str[0] == '-' || str[0] == '+') ? 1 : 0; i < str.length; i++) {
			/*
			* 用0的ASCII码 -str[i]的ASCII码
			* eg: str [i] = '3' cur = -3 str [i] = '5'  cur = -5
			* 因为正数 和 负数 都当做 负数处理 因为负数的范围比正数的范围大
			 * */
			cur = '0' - str[i];
			/*
			 *TODO 这个if判断 解决 res * 10 + cur的溢出问题
			 * 假设 最终结果 res = -21724 x = -4657
			 * 当前操作 是 x % 10 = -7
			 * 然后 res * 10 + -7
			 * 在  * 10 之前 res 已经 小于 Integer.MIN_VALUE / 10的话
			 * res * 10 一定溢出
			 * 如果  * 10 之前 res 已经 == Integer.MIN_VALUE / 10的话
			 * res * 10 不溢出 判断 x % 10的结果
			 * 因为 res == Integer.MIN_VALUE / 10 那么 res * 10 和 Integer.MIN_VALUE的区别就是 个位数
			 * 判断
			 * x % 10（-7） < Integer.MIN_VALUE % 10的话
			 * res * 10 + x % 10 一定溢出
			 * */
			if ((res < minq) || (res == minq && cur < minr)) {
				//LeetCode要有 整数溢出 返回 系统最大
				return posi ? Integer.MAX_VALUE : Integer.MIN_VALUE;
			}
			res = res * 10 + cur;
		}
		//TODO 从 for循环出来 说明res 小于 Integer.MIN_VALUE的绝对值
		// res 负
		if (posi && res == Integer.MIN_VALUE) {//TODO 如果要转成整数 并且 res == Integer.MIN_VALUE
			return Integer.MAX_VALUE;
		}
		return posi ? -res : res;
	}
	//类似于 “0000123” = “123”
	public static String removeHeadZero(String str) {
		boolean r = (str.startsWith("+") || str.startsWith("-"));
		int s = r ? 1 : 0;
		for (; s < str.length(); s++) {
			if (str.charAt(s) != '0') {
				break;
			}
		}
		// s 到了第一个不是'0'字符的位置
		int e = -1;
		// 左<-右
		for (int i = str.length() - 1; i >= (r ? 1 : 0); i--) {
			if (str.charAt(i) < '0' || str.charAt(i) > '9') {
				e = i;
			}
		}
		// e 到了最左的 不是数字字符的位置
		return (r ? String.valueOf(str.charAt(0)) : "") + str.substring(s, e == -1 ? str.length() : e);
	}

	public static boolean isValid(char[] chas) {
		if (chas[0] != '-' && chas[0] != '+' && (chas[0] < '0' || chas[0] > '9')) {
			return false;
		}
		if ((chas[0] == '-' || chas[0] == '+') && chas.length == 1) {
			return false;
		}
		// 0 +... -... num
		for (int i = 1; i < chas.length; i++) {
			if (chas[i] < '0' || chas[i] > '9') {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		System.out.println(Integer.MAX_VALUE);
	}



}
