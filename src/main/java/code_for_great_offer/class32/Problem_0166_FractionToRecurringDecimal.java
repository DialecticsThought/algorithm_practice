package code_for_great_offer.class32;

import java.util.HashMap;

/*
*TODO
* 给定两个整数，分别表示分数的分子numerator 和分母 denominator，以 字符串形式返回小数 。
* 如果小数部分为循环小数，则将循环的部分括在括号内。
* 如果存在多个答案，只需返回 任意一个 。
* 对于所有给定的输入，保证 答案字符串的长度小于 104 。
* 示例 1：
* 输入：numerator = 1, denominator = 2
* 输出："0.5"
* 示例 2：
* 输入：numerator = 2, denominator = 1
* 输出："2"
* 示例 3：
* 输入：numerator = 4, denominator = 333
* 输出："0.(012)"
链接：https://leetcode.cn/problems/fraction-to-recurring-decimal
* */
public class Problem_0166_FractionToRecurringDecimal {
	/*
	*TODO
	* numerator：除数
	* denominator：被除数
	* */
	public static String fractionToDecimal(int numerator, int denominator) {
		if (numerator == 0) {
			return "0";
		}
		StringBuilder res = new StringBuilder();
		/*
		*TODO
		* 判断 除数和被除数的正负关系
		* “^”是异或符号  "+" or "-"
		* */
		res.append(((numerator > 0) ^ (denominator > 0)) ? "-" : "");
		//TODO long类型防止溢出
		long num = Math.abs((long) numerator);
		long den = Math.abs((long) denominator);
		// integral part
		//TODO res先添加 小数点之前的数
		res.append(num / den);
		//TODO num % den 的结果 = 0 的话 说明没有小数点止呕的部分
		num %= den;
		if (num == 0) {
			return res.toString();
		}
		// fractional part
		res.append(".");
		HashMap<Long, Integer> map = new HashMap<Long, Integer>();
		map.put(num, res.length());
		/*
		*TODO
		* eg: 1 / 3 =
		* 求小数点后的第一位 就是 1 * 10 / 3 = 3 这就是小数点后的第一位
		* 求小数点后的第2位 就是 先 10 % 3 = 1 , 1 * 10 / 3 = 3 这就是 小数点后的第2位
		* ....
		* => a / b
		* => a * 10 / b = c 小数点后的第1位
		* => 1. (a * 10) % b = d, 2. d * 10 / b = e 小数点后的第2位
		* => 1. (d * 10) % b = f, 2. f * 10 / b = g 小数点后的第3位
		* .......
		* 如果 第一步模出来的数 和 a相同 说明有循环
		* */
		while (num != 0) {
			num *= 10;
			res.append(num / den);
			num %= den;
			if (map.containsKey(num)) {
				int index = map.get(num);
				res.insert(index, "(");
				res.append(")");
				break;
			} else {
				map.put(num, res.length());
			}
		}
		return res.toString();
	}

	public static void main(String[] args) {
		System.out.println(fractionToDecimal(127, 999));
	}

}
