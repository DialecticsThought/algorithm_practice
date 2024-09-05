package code_for_great_offer.class45;

import java.util.HashSet;

public class LeetCode_0291_WordPatternII {

	public static boolean wordPatternMatch(String pattern, String str) {
		return match(str, pattern, 0, 0, new String[26], new HashSet<>());
	}

	/**
	 *TODO
	 * 题目有限制，str和pattern其中的字符，一定是a~z小写
	 * p[a] -> "abc"
	 * p[b] -> "fbf"
	 * 需要指代的表最多26长度
	 * String[] map -> new String[26]
	 * eg:
	 * p[a] -> "abc"  说明 map[0] -> "abc"
	 * p[b] -> "fbf"  说明 map[1] -> "fbf";
	 * p[z] -> "kfk"  说明 map[25] -> "kfk"
	 * HashSet<String> set -> map中指代了哪些字符串 也就是abc fbf kfk 存进去
	 * eg:
	 * 来到str位置i,i+1,i+2,i+3 分别是 bzbf ，来到p的j位置 是k，并且k字符没有设定
	 * 准备设 k -> bzb, 但是如果之前已经bzb设置成其他字符的话 这里不能设bzb成其他字符了
	 * str[si.......]  是不是符合  p[pi......]？符合返回true，不符合返回false  ☆☆☆☆☆☆☆
	 * 之前的决定！由map和set，告诉我！不能冲突！ ☆☆☆☆☆☆☆
	 * eg:
	 * str = abc abc kft kft abc p = aabba
	 * a = abc b = kft
	 * 尝试：
	 * 假设str[0]~str[0]就是p的a,
	 * 假设str[0]~str[1]就是p的a,
	 * 假设str[0]~str[2]就是p的a,
	 * ...
	 * eg2：
	 * p来到的的位置的值是j，不关心所在索引，并且j已经之前匹配过了 之前匹配的假设是abc
	 * 当前来到str的i位置，0~i-1已经处理过了
	 * 1.假设 索引 i i+1 i+2 处是b k c
	 * 这个情况不能设置了 并且 这里是 b k c 不是之前的abc 说明之前的决定有问题
	 * 2.假设 索引 i i+1 i+2 处是a b c
	 * 这个情况不能设置了 但是 这里是 ab c 是之前的abc 那么直接来到str[i+3]
	 * eg3:str = a b c f t f a b c f t f , p = a a
	 * 假设来到 3下标 ，之前 假设已经定义了 abc = a 也就是做了个选择 str[0]~str[2]
	 * 此时来到的是p的第二个a，str的下标3 那么说明之前的决定是不对的
	 * 所以要退回到str[0] ，做出选择让a = str[0]~str[5]
	 * @param s
	 * @param p
	 * @param si
	 * @param pi
	 * @param map
	 * @param set
	 * @return
	 */

	public static boolean match(String s, String p, int si, int pi, String[] map, HashSet<String> set) {
		// base case
		if (pi == p.length() && si == s.length()) {// 2者都结束了
			return true;
		}
		// str和pattern，并没有都结束！因为如果都结束了上面的if就会return
		if (pi == p.length() || si == s.length()) {
			return false;
		}
		//  str和pattern，都没结束！
		char ch = p.charAt(pi);//得到p[pi]这个字符
		String cur = map[ch - 'a'];
		if (cur != null) { // 当前p[pi]已经指定过了！
			/**
			 * 假设来到str的si，si+1,si+2 是 a b c 假设 现在str[si~si+2]设置成一个字符
			 * pattern来到pi位置是k
			 * 先查询 abc是不是设过字母了
			 * 如果设置过字母了，
			 * 查看是不是之前就设置成k了 也即是查看map[k] == abc
			 * 如果是，那么直接从str[si+3]开始继续操作
			 * 如果不是，返回之前的
			 *
			 * 1.假设当前来到str倒数第3个下标
			 * 当前来到的p[pi]是a，并且之前设置过a了
			 * 假设a= abcef 5个字符 str只剩下三个字符 那么一定不成功
			 * 2.假设当前来到str倒数第5个下标
			 * 当前来到的p[pi]是a，并且之前设置过a了
			 * 假设a= abcef 5个字符 str剩下5个字符是bcbee 那么一定不成功
			 * 3.1和2的成功之外，还要往后都要匹配成功
			 */
			return si + cur.length() <= s.length() // 不能越界！
					&& cur.equals(s.substring(si, si + cur.length()))//map[k] == abc
					&& match(s, p, si + cur.length(), pi + 1, map, set);// 往后匹配也成立
		}
		// 代码执行到这里的时候，说明p[pi]没指定！
		int end = s.length();
		/**
		 * 剪枝！重要的剪枝！
		 * eg1:
		 * str = abc fbf kkk ...
		 * p: a b c a b
		 * 此时si = 6 pi = 2
		 * abc 已经被设置成a
		 * fbf 已经被设置成b
		 * 可以把 k 设置成c
		 * 可以把 kk 设置成c
		 * 可以把 kkk 设置成c
		 * ...
		 * 但是因为这个p的最后一个是b 说明如果 b=fbf 那么str一定最后3个字符是 fbf
		 * 同理因为这个p的最后2个是ab 说明如果 a=abc b=fbf 那么str一定最后6个字符是 abcfbf
		 * eg2:
		 * str = ab cd ab cz ........
		 * 此时来到 si  并且已经 a = ab, b= cd, a = ab ,c = cz
		 * p: a b a c d b c c t f a
		 * 此时来到pi = 4 p[pi]=d
		 * 我为了给  b c c t f a 留出空间
		 * 倒数第1个字符a 指定过 至少流出2个空间
		 * 倒数第2个字符f 没指定过 至少流出1个空间
		 * 倒数第3个字符t 没指定过 至少流出1个空间
		 * 倒数第4个字符c 指定过 至少流出2个空间
		 * 倒数第5个字符c 指定过 至少流出2个空间
		 * 倒数第6个字符v 指定过 至少流出2个空间
		 * 也就是说 str[si]~str[str.length-1]的长度必须 =2+1+1+2+2+2
		 */
		for (int i = p.length() - 1; i > pi; i--) {
			end -= map[p.charAt(i) - 'a'] == null ? 1 : map[p.charAt(i) - 'a'].length();
		}
		/**
		 * 因为p[pi]还没有被设置过
		 * 那么尝试
		 * str[si]~str[si+1]
		 * str[si]~str[si+2]
		 * ....
		 * str[si]~str[str.length-1]
		 */
		for (int i = si; i < end; i++) {
			//  从si出发的所有前缀串，全试
			cur = s.substring(si, i + 1);
			// 但是，只有这个前缀串，之前没占过别的坑！才能去尝试
			if (!set.contains(cur)) {//eg:之前已经设置 a= abc  那么就不能设置 b = abc
				// 设置 p[pi] =str[si]~str[n]
				set.add(cur);
				map[ch - 'a'] = cur;
				//从str[n+1]开始重复操作
				if (match(s, p, i + 1, pi + 1, map, set)) {
					return true;
				}
				// dfs清理现场 尝试下一个
				map[ch - 'a'] = null;
				set.remove(cur);
			}
		}
		return false;
	}

}
