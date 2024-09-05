package code_for_great_offer.class28;

public class leetCode_0012_IntegerToRoman {

	public static String intToRoman(int num) {
		/*
		* TODO
		*  I单独表示1
		*  IV I=-1 V=5 IV=4
		*  VI I=1 V=5 VI=6
		*  X单独表示10
		*  XL X<L X=-10 L=60 XL=50
		*  C单独表示100
		*  XC X<C X=-10 C=100 XL=90
		* */
		String[][] c = {
				{ "", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX" },//0~9
				{ "", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC" },//10~90
				{ "", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM" },//100~900
				{ "", "M", "MM", "MMM" } //1000~3000
		};
		StringBuilder roman = new StringBuilder();
		/*
		* TODO
		* eg:一个数 num 3007
		* 先看千位 3 就是MMM
		* 中间的0忽略掉
		* 看个位7 就是VII
		* MMMVIII
		* */
		roman.append(c[3][num / 1000 % 10])
		.append(c[2][num / 100 % 10])
		.append(c[1][num / 10 % 10])
		.append(c[0][num % 10]);
		return roman.toString();
	}

}
