package code_for_great_offer.class28;

/*
* 14. 最长公共前缀
示例 1：
输入：strs = ["flower","flow","flight"]
输出："fl"
示例 2：
输入：strs = ["dog","racecar","car"]
输出：""
解释：输入不存在公共前缀。
提示：
1 <= strs.length <= 200
0 <= strs[i].length <= 200
strs[i] 仅由小写英文字母组成
* */
public class Problem_0014_LongestCommonPrefix {
	/*
	* eg：
	* 遍历第1个 str[0] = abcdef  共6个char
	* 遍历第2个 str[1]= abcdks 那么 str[0]和str[1]从0位置开始一个个比较char
	* 得到 当前 最长公共前缀是4 abcd
	* 遍历第3个 str[2]= abkc 那么 之前的公共前缀 abcd 与str[2]比较
	* 得到 当前 最长公共前缀是2  ab
	*
	 * */
	public static String longestCommonPrefix(String[] strs) {
		if (strs == null || strs.length == 0) {
			return "";
		}
		char[] chs = strs[0].toCharArray();
		int min = Integer.MAX_VALUE;
		for (String str : strs) {
			char[] tmp = str.toCharArray();
			int index = 0;
			while (index < tmp.length && index < chs.length) {
				if (chs[index] != tmp[index]) {
					break;
				}
				index++;
			}
			min = Math.min(index, min);
			if (min == 0) {
				return "";
			}
		}
		return strs[0].substring(0, min);
	}

}
