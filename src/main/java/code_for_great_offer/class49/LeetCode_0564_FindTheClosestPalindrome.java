package code_for_great_offer.class49;

public class LeetCode_0564_FindTheClosestPalindrome {
	/*
	*TODO
	* eg:num=10002 找到<num 并且 是回文的数字 找到>num 并且 是回文的数字
	* 即10001 和10101
	* 返回最接近num的数字 10001
	* eg:num=36425 找到 36363 36463
	* 返回最接近num的数字 36463
	*TODO
	* 贪心
	* eg： 有一个数 36425
	* 先求出一个粗糙回文
	* 36425的中点是4 那么就把中点左侧的数逆序到中点右侧
	* 也就是 36463
	* 有可能粗糙回文就是答案
	* 如何找到比原始num大的 而且是回文的数字 且这个数字与num接近
	* eg: num=632765
	* 1.粗糙回文 => 632236
	* 2.找到比原始num大的 而且是回文的数字 且这个数字与num接近
	* 那么就先动最接近中点的数让这个数+1
	* 也就是 633336 而不是对更外围的数字+1 也就是642246
	* 3.再动最接近中点的数让这个数-1 也就是 631136
	* 比较这个3步形成的数
	* eg: 6325603
	* 1.粗糙回文 就是 6325236
	* 2.先动最接近中点的数让这个数+1
	* 找到 6326236 而不是 6335336
	* 3.再动最接近中点的数让这个数-1
	* 找到 6324236
	*  比较这个3步形成的数
	* eg:10099
	* 1.粗糙回文 10001
	* 2.先动最接近中点的数让这个数+1 找到10101  这就是答案
	* 3.再动最接近中点的数让这个数-1 找到1
	* eg: 10002
	* 1.粗糙回文 10001 这就是答案
	* 2.  。。。。
	* 3. 。。。。
	*TODO
	* eg: 10001
	* 粗糙回文就是num本身
	* 2.先动最接近中点的数让这个数+1 找到10101
	* 3.再动最接近中点的数让这个数-1 需要借位
	*	也就是 999 此时出现了错误 一个是3位 一个是5位 所以需要补一位 也就是 9999
	* eg: 100000
	* 粗糙回文:100001
	* 2.先动最接近中点的数让这个数+1 101101
	* 3.再动最接近中点的数让这个数-1 需要借位
	* 	也就是 9999 此时出现了错误 一个是4位 一个是6位 所以需要补一位 也就是 99999
	* eg: 23999921
	* 粗糙回文:23999932
	* 2.先动最接近中点的数让这个数+1 24000042
	* 3.再动最接近中点的数让这个数-1 23988932
	 * */
	public static String nearestPalindromic(String n) {
		Long num = Long.valueOf(n);
		//TODO 粗糙回文
		Long raw = getRawPalindrome(n);
		//TODO 粗糙回文的 最接近中点的数让这个数+1
		Long big = raw > num ? raw : getBigPalindrome(raw);
		//TODO 粗糙回文的 最接近中点的数让这个数-1
		Long small = raw < num ? raw : getSmallPalindrome(raw);
		return String.valueOf(big - num >= num - small ? small : big);
	}

	public static Long getRawPalindrome(String n) {
		char[] chs = n.toCharArray();
		int len = chs.length;
		for (int i = 0; i < len / 2; i++) {
			chs[len - 1 - i] = chs[i];
		}
		return Long.valueOf(String.valueOf(chs));
	}

	public static Long getBigPalindrome(Long raw) {
		char[] chs = String.valueOf(raw).toCharArray();
		char[] res = new char[chs.length + 1];
		res[0] = '0';
		for (int i = 0; i < chs.length; i++) {
			res[i + 1] = chs[i];
		}
		int size = chs.length;
		for (int j = (size - 1) / 2 + 1; j >= 0; j--) {
			if (++res[j] > '9') {
				res[j] = '0';
			} else {
				break;
			}
		}
		int offset = res[0] == '1' ? 1 : 0;
		size = res.length;
		for (int i = size - 1; i >= (size + offset) / 2; i--) {
			res[i] = res[size - i - offset];
		}
		return Long.valueOf(String.valueOf(res));
	}

	public static Long getSmallPalindrome(Long raw) {
		char[] chs = String.valueOf(raw).toCharArray();
		char[] res = new char[chs.length];
		int size = res.length;
		for (int i = 0; i < size; i++) {
			res[i] = chs[i];
		}
		for (int j = (size - 1) / 2; j >= 0; j--) {
			//TODO 此时需要借位
			if (--res[j] < '0') {
				//TODO 当前位置写成9
				res[j] = '9';
			} else {
				break;
			}
		}
		if (res[0] == '0') {
			res = new char[size - 1];
			for (int i = 0; i < res.length; i++) {
				res[i] = '9';
			}
			return size == 1 ? 0 : Long.parseLong(String.valueOf(res));
		}
		for (int k = 0; k < size / 2; k++) {
			res[size - 1 - k] = res[k];
		}
		return Long.valueOf(String.valueOf(res));
	}

}
