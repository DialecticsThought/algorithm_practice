package code_for_great_offer.class42;

public class LeetCode_0273_IntegerToEnglishWords {
	//TODO 1~19都是不同的单词
	public static String num1To19(int num) {
		if (num < 1 || num > 19) {
			return "";
		}
		String[] names = { "One ", "Two ", "Three ", "Four ", "Five ", "Six ", "Seven ", "Eight ", "Nine ", "Ten ",
				"Eleven ", "Twelve ", "Thirteen ", "Fourteen ", "Fifteen ", "Sixteen ", "Seventeen", "Eighteen ",
				"Nineteen " };
		return names[num - 1];
	}
	//TODO 解决 1~99的数字
	public static String num1To99(int num) {
		if (num < 1 || num > 99) {
			return "";
		}
		if (num < 20) {
			return num1To19(num);
		}
		//TODO 超过了20 那么先得到十位
		int high = num / 10;
		String[] tyNames = { "Twenty ", "Thirty ", "Forty ", "Fifty ", "Sixty ", "Seventy ", "Eighty ", "Ninety " };
		//TODO num % 10就是在1~9的范围 也属于1~19 所以执行num1To19
		return tyNames[high - 2] + num1To19(num % 10);
	}

	public static String  num1To999(int num) {
		if (num < 1 || num > 999) {
			return "";
		}
		if (num < 100) {
			return num1To99(num);
		}
		//TODO 超过了100
		int high = num / 100;//TODO 得到百位上的数
		//TODO 百位上的数在1~9的范围 也属于1~19 执行num1To19  十位和个位执行num1To99
		return num1To19(high) + "Hundred " + num1To99(num % 100);
	}
	/*
	*TODO 一个数字 从最右开始 每3个数分割一次 所有 /1000
	* */
	public static String numberToWords(int num) {
		if (num == 0) {
			return "Zero";
		}
		String res = "";
		if (num < 0) {
			res = "Negative ";
		}
		if (num == Integer.MIN_VALUE) {
			res += "Two Billion ";
			num %= -2000000000;
		}
		num = Math.abs(num);
		int high = 1000000000;//TODO 十亿位
		int highIndex = 0;
		String[] names = { "Billion ", "Million ", "Thousand ", "" };
		/*
		*TODO
		*  除以十亿查看还有多少 查看有没有billion 有的话 执行num1To999(cur) 再添加"Billion "
		*  取模 之后再除以1000000 查看有没有million 有的话 执行num1To999(cur)  再添加"Million "
		*  取模 之后再除以1000查看有没有Thousand	有的话 执行num1To999(cur)  再添加"Thousand "
		* */
		while (num != 0) {
			int cur = num / high;
			num %= high;
			if (cur != 0) {
				res += num1To999(cur);
				res += names[highIndex];
			}
			high /= 1000;
			highIndex++;
		}
		return res.trim();
	}

	public static void main(String[] args) {
		int test = Integer.MIN_VALUE;
		System.out.println(test);

		test = -test;
		System.out.println(test);

		int num = -10001;
		System.out.println(numberToWords(num));
	}

}
