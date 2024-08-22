package code_for_great_offer.class28;
/*
* 给定一个正整数 n ，输出外观数列的第 n 项。
* 「外观数列」是一个整数序列，从数字 1 开始，序列中的每一项都是对前一项的描述。
* 你可以将其视作是由递归公式定义的数字字符串序列：
* countAndSay(1) = "1"
* countAndSay(n) 是对 countAndSay(n-1) 的描述，然后转换成另一个数字字符串。
* 前五项如下：
* 1.     1
* 2.     11
* 3.     21
* 4.     1211
* 5.     111221
* 第一项是数字 1
* 描述前一项，这个数是 1 即 “ 一 个 1 ”，记作 "11"
* 描述前一项，这个数是 11 即 “ 二 个 1 ” ，记作 "21"
* 描述前一项，这个数是 21 即 “ 一 个 2 + 一 个 1 ” ，记作 "1211"
* 描述前一项，这个数是 1211 即 “ 一 个 1 + 一 个 2 + 二 个 1 ” ，记作 "111221"
* 要 描述 一个数字字符串，首先要将字符串分割为 最小 数量的组，每个组都由连续的最多 相同字符 组成。
* 然后对于每个组，先描述字符的数量，然后描述字符，形成一个描述组。要将描述转换为数字字符串，先将每组中的字符数量用数字替换，再将所有描述组连接起来。
链接：https://leetcode.cn/problems/count-and-say
* */
public class Problem_0038_CountAndSay {

	public static String countAndSay(int n) {
		if (n < 1) {
			return "";
		}
		if (n == 1) {
			return "1";
		}
		/*
		*TODO
		* 想知道第n项是啥 就需要知道第n-1项是啥
		* */
		char[] last = countAndSay(n - 1).toCharArray();
		StringBuilder ans = new StringBuilder();
		int times = 1;
		for (int i = 1; i < last.length; i++) {
			if (last[i - 1] == last[i]) {
				times++;
			} else {
				ans.append(String.valueOf(times));
				ans.append(String.valueOf(last[i - 1]));
				times = 1;
			}
		}
		ans.append(String.valueOf(times));
		ans.append(String.valueOf(last[last.length - 1]));
		return ans.toString();
	}

	public static void main(String[] args) {
		System.out.println(countAndSay(20));
	}

}
