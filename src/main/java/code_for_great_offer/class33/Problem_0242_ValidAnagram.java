package code_for_great_offer.class33;

/*
* 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
注意：若s 和 t中每个字符出现的次数都相同，则称s 和 t互为字母异位词。
示例1:
输入: s = "anagram", t = "nagaram"
输出: true
示例 2:
输入: s = "rat", t = "car"
输出: false
来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/valid-anagram
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
*
* */
public class Problem_0242_ValidAnagram {

	public static boolean isAnagram(String s, String t) {
		if (s.length() != t.length()) {
			return false;
		}
		char[] str1 = s.toCharArray();
		char[] str2 = t.toCharArray();
		//ASCII码表的长度的数组
		int[] count = new int[256];
		/*
		*TODO
		* 因为2个str长度相同
		* 那么++ -- 是差不多的
		* */
		for (char cha : str1) {
			count[cha]++;
		}
		for (char cha : str2) {
			if (--count[cha] < 0) {
				return false;
			}
		}
		return true;
	}

}
