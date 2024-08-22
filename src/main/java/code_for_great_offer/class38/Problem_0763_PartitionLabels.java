package code_for_great_offer.class38;

import java.util.ArrayList;
import java.util.List;

/*
*TODO
* 给你一个字符串 s 。我们要把这个字符串划分为尽可能多的片段，同一字母最多出现在一个片段中。
* 注意，划分结果需要满足：将所有划分结果按顺序连接，得到的字符串仍然是 s 。
* 返回一个表示每个字符串片段的长度的列表。
* 示例 1：
* 输入：s = "ababcbacadefegdehijhklij"
* 输出：[9,7,8]
* 解释：
* 划分结果为 "ababcbaca"、"defegde"、"hijhklij" 。
* 每个字母最多出现在一个片段中。
* 像 "ababcbacadefegde", "hijhklij" 这样的划分是错误的，因为划分的片段数较少。
* 示例 2：
* 输入：s = "eccbbbbdec"
* 输出：[10]
* 链接：https://leetcode.cn/problems/partition-labels
* 有张表 用来记录 某个字符 在str最右侧的位置
* 还有个变量r 记录 当前块的右边界在哪个位置  r只会上升不会下降
* eg: str ="a b c a b a c c e f g z g"
* 对于0位置的a ，a在str最右的位置是5
* 那么 划分的一刀 可能在5和6位置之间 但这一刀 不可能在 0~5范围 因为 0位置的a 和str最右位置的a 必须在一起
* 所以此时r=5
* 对于1位置b b在str最右的位置是4，4<r
* 对于2位置c c在str最右的位置是8 说明 第一刀不能在5和6位置之间
* .....
* 来到位置8 是c 当前的位置 = r 那么 0~8切成一块
* 来到位置9 是e e在str最右的位置是 9 当前的位置 > r 那么 0~8切成一块
* */
public class Problem_0763_PartitionLabels {

	public static List<Integer> partitionLabels(String S) {
		char[] str = S.toCharArray();
		int[] far = new int[26];
		for (int i = 0; i < str.length; i++) {
			far[str[i] - 'a'] = i;
		}
		List<Integer> ans = new ArrayList<>();
		int left = 0;
		int right = far[str[0] - 'a'];
		for (int i = 1; i < str.length; i++) {
			if (i > right) {
				ans.add(right - left + 1);
				left = i;
			}
			right = Math.max(right, far[str[i] - 'a']);
		}
		ans.add(right - left + 1);
		return ans;
	}

}
