package code_for_great_offer.class37;
/*
*TODO
* 给定一个经过编码的字符串，返回它解码后的字符串。
* 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
* 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
* 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像3a或2[4]的输入。
* 示例 1：
* 输入：s = "3[a]2[bc]"
* 输出："aaabcbc"
* 示例 2：
* 输入：s = "3[a2[c]]"
* 输出："accaccacc"
* 示例 3：
* 输入：s = "2[abc]3[cd]ef"
* 输出："abcabccdcdcdef"
* 示例 4：
* 输入：s = "abc3[cd]xyz"
* 输出："abccdcdcdxyz"
* 链接：https://leetcode.cn/problems/decode-string
* */
public class Problem_0394_DecodeString {

	public static String decodeString(String s) {
		char[] str = s.toCharArray();
		return process(str, 0).ans;
	}

	public static class Info {
		public String ans;
		public int stop;

		public Info(String a, int e) {
			ans = a;
			stop = e;
		}
	}
	/*
	*TODO
	* s[i]出发 向后遍历
	* 何时停？遇到 ']'  或者遇到 s的终止位置，停止
	* 请把i位置到停下的位置之间的 还原出来 再加上停下的位置 生成Info一起向上返回
	* 0) 串
	* 1) 算到了哪
	* str= 2 1 [ b 5 [ c 2 [ f ]  ]  3  [  k  s  ]  ]
	* 下标: 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18
	* 1.调用f(0) f(0)的  ans =null cur=0
	* 	 从0位置开始遍历 来到0位置，是数字,此时cur=2
	*    来到1位置，是数字 cur=21
	* 	 来到2位置 是'[' 调用f(3)
	* 2.调用f(3)  f(3)的  ans =null cur=0
	* 	 来到3位置，是字母 ans = b
	*    来到4位置，是数字 cur = 5
	*    来到5位置 是'[' 调用f(6)
	* 3.调用f(6)  f(6)的  ans =null cur=0
	*    来到6位置，是字母 ans = c
	*    来到7位置，是数字 cur = 2
	* 	 来到8位置 是'[' 调用f(9)
	* 4.调用f(9)  f(9)的  ans =null cur=0
	* 	 来到9位置，是字母 ans = f
	* 	 来到10位置 是']' 向上返回[ans,10]
	* 5.来到f(6)
	* 	此时 ans= cff = ans +cur*f(9)的ans
	* 	此时cur归零 cur=0
	* 	来到11位置 是']' 向上返回[cff,11]
	* 6.来到f(3) ans = b cur = 5
	* 	此时 ans= bcffcffcffcffcff = ans +cur*f(6)的ans
	*   此时cur归零 cur=0
	*   来到12位置 是数字 cur = 3
	* 	来到13位置 是'[' 调用f(13)
	* 7.调用f(13)  f(13)的  ans =null cur=0
	* 	来到14位置，是字母 ans = k
	*   来到15位置，是字母 ans = ks
	* 	来到16位置 是']' 向上返回[ks,16]
	* 8.来到f(3) ans = bcffcffcffcffcff cur = 3
	* 	此时 ans= bcffcffcffcffcffksksks = ans +cur*f(13)的ans
	* 	此时cur归零 cur=0
	* 	来到17位置 是']' 向上返回[bcffcffcffcffcffksksks,17]
	* 9.来到f(0)
	* */
	public static Info process(char[] s, int i) {
		StringBuilder ans = new StringBuilder();
		int count = 0;
		while (i < s.length && s[i] != ']') {
			if ((s[i] >= 'a' && s[i] <= 'z') || (s[i] >= 'A' && s[i] <= 'Z')) {
				ans.append(s[i++]);
			} else if (s[i] >= '0' && s[i] <= '9') {
				count = count * 10 + s[i++] - '0';
			} else { //这个分支 是指 str[index] = '['
				Info next = process(s, i + 1);
				ans.append(timesString(count, next.ans));
				count = 0;
				i = next.stop + 1;
			}
		}
		return new Info(ans.toString(), i);
	}

	public static String timesString(int times, String str) {
		StringBuilder ans = new StringBuilder();
		for (int i = 0; i < times; i++) {
			ans.append(str);
		}
		return ans.toString();
	}

}
