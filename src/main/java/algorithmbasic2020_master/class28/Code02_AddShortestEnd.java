package algorithmbasic2020_master.class28;

public class Code02_AddShortestEnd {
	/**
	* 1221 -> #1#2#2#1#1
	* 申请一个回文半径数组
	* 也就是必须包含最后一个字符的情况下 最长的回文子串是多长
	* 也就是说 不是回文的部分 逆序写在最后
	* eg: a b c 1 2 3 2 1  => a b c 1 2 3 2 1 c b a
	* eg： a b c 1 2 3 3 2 1 k f c f k => a b c 1 2 3 3 2 1 k f c f k 1 2 3 3 2 1 c b a
	* */
	public static String shortestEnd(String s) {
		if (s == null || s.length() == 0) {
			return null;
		}
		char[] str = manacherString(s);
		int[] pArr = new int[str.length];
		int C = -1;//中心
		int R = -1;//回文右边界 再往右一个位置 最右的有效区是R-1位置
		int maxContainsEnd = -1;
		for (int i = 0; i != str.length; i++) {//每个位置为中心的时候求回文半径
			/**
			*TODO 哪一个位置 作为中心点 的回文半径 先罩到了最后一个字符 那么就是找到了需要的回文子串 其余部分 开始逆序
			* eg: a b c b 2 2 =>
			* str =  # a # b # c # b # 2 # 2 #
			* 对应下标 0 1 2 3 4 5 6 7 8 9 10 11 12
			* 对于[1]=a 而言 回文半径右边界是 2位置
			* 对于[3]=b 而言 回文半径右边界是 4位置
			* 对于[5]=c 而言 回文半径右边界是 8位置
			* 对于[9]=2 而言 回文半径右边界是 10位置
			* 对于[11]=# 而言 回文半径右边界是 12位置 左边界是8  => 想要的  不用继续了
			* 对于前面遍历到的 非回文子串的部分 逆序写到后面
			*
			* 对于R而言 ，这个R位置对应 加了"#"好的处理过的str的最后一个char
			* 那么 （R-1）/2就是原始str的最后一个位置  end就是R
			* */
			/*
			* i在R的外部 从i开始往两边暴力扩展 --> R变大
			* i在R的内部
			* 	i'的回文区域彻底在L..R内部  [ ( i' )  c  ( i ) ]     ()表示i和i'的回文区域 []表示c中心的回文区域一个L一个R
			* 	i'的回文区域有一部分在L..R外部 ( [ i' )  c  (  i ] )   ()表示i和i'的回文区域 []表示c中心的回文区域一个L一个R
			*   i'的回文区域和L..R的最边界压线   [  i' )  c  ( i ]
			* 		-> 从R之外的字符开始 往外扩 然后确定pArr[i]的答案
			* 			第一步扩展失败了 R不变
			* 			否则R变大
			* */
			/*
			* [ ( i' )  c   ( i ) ]
			* R > i说明 i在R的内部
			* 	2 * C - i是i'的位置  pArr[2 * C - i]就是i'位置的元素  针对的是i'的回文区域彻底在L..R内部的情况 因为取决于i的回文半径和i'一样 \
			* 														i到R的距离一定小于i本身的回文半径
			* R-i的意思是i到R的距离 针对的是i'的回文区域有一部分在L..R外部的情况 那么i的回文半径就是i到R的距离
			*   				   针对的是i'的回文区域和L..R的最边界压线的情况  压线的话 i'的回文半径和i到R的距离有关联
			* R > i说明 i在R的外部  至少不用验证的区域就是1 也就是i位置的字符本身就是回文
			* */
			pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
			while (i + pArr[i] < str.length && i - pArr[i] > -1) {
				/**
				* 右边的字符是到达的字符加上至少不用验证的区域所到达的位置 左边的字符是到达的字符减去至少不用验证的区域所到达的位置
				* 表示至少不用验证的区域跳过之后  接下来左右边能不能扩的更大
				* 四至情况里面 只有两种需要扩展，但是为了省略代码量 而且另外两种即使扩展了也会失败 直接break就可以了
				* */
				if (str[i + pArr[i]] == str[i - pArr[i]])
					pArr[i]++;
				else {
					break;
				}
			}
			if (i + pArr[i] > R) {//查看是不是更往右了
				R = i + pArr[i];
				C = i;
			}
			if (R == str.length) {
				maxContainsEnd = pArr[i];
				break;
			}
		}
		char[] res = new char[s.length() - maxContainsEnd + 1];
		for (int i = 0; i < res.length; i++) {
			res[res.length - 1 - i] = str[i * 2 + 1];
		}
		return String.valueOf(res);
	}
	//1221 -> #1#2#2#1#1
	public static char[] manacherString(String str) {
		char[] charArr = str.toCharArray();
		char[] res = new char[str.length() * 2 + 1];
		int index = 0;
		for (int i = 0; i != res.length; i++) {
			res[i] = (i & 1) == 0 ? '#' : charArr[index++];
		}
		return res;
	}

	public static void main(String[] args) {
		String str1 = "abcd123321";
		System.out.println(shortestEnd(str1));
	}

}
