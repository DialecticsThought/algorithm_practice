import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 *TODO
 * 给定两个字符串s和 p，找到s中所有p的异位词的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
 * 异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。
 * 示例1:
 * 输入: s = "cbaebabacd", p = "abc"
 * 输出: [0,6]
 * 解释:
 * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
 * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
 * 示例 2:
 * 输入: s = "abab", p = "ab"
 * 输出: [0,1,2]
 * 解释:
 * 起始索引等于 0 的子串是 "ab", 它是 "ab" 的异位词。
 * 起始索引等于 1 的子串是 "ba", 它是 "ab" 的异位词。
 * 起始索引等于 2 的子串是 "ab", 它是 "ab" 的异位词。
 * 链接：https://leetcode.cn/problems/find-all-anagrams-in-a-string
 *TODO
 * 利用滑动窗口
 * p = abca
 * b c c a b c b b a c c b
 * 有张表 意思是 a欠2个 b欠1个 c欠1个 总共欠4个
 * <a 2> <b 1> <c 1> all=4
 * 初始的时候 窗口的左右边界都是在-1位置
 * 右边界右移 框住了b 表记录
 * <a 2> <b 0>  <c 1> all=3
 * 右边界右移 框住了c 表记录
 * <a 2> <b 0>  <c 0> all=2
 * 右边界右移 框住了c 表记录  all不变因为 c已经变成了0
 * <a 2> <b 0>  <c -1> all=2
 * 右边界右移 框住了a   表记录
 * <a 1> <b 0>  <c -1> all=1
 * 此时生成了 从0位置开始的长度为4的窗口 但是all!=0 说明 窗口框住的str不是目标
 * 左窗口右移 吐出b
 * <a 1> <b 1>  <c -1> all=2
 * 右边界右移 框住了b 表记录
 * <a 1> <b 0>  <c -1> all=1
 * 左窗口右移 吐出c
 * <a 1> <b 0>  <c 0> all=1
 * 右边界右移 框住了c 表记录 all不变因为 c已经变成了0
 * <a 1> <b 0>  <c -1> all=1
 * ......
 * */
public class LeetCode_0438_FindAllAnagramsInAString {

    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> ans = new ArrayList<>();
        if (s == null || p == null || s.length() < p.length()) {
            return ans;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        char[] pst = p.toCharArray();
        int M = pst.length;
        HashMap<Character, Integer> map = new HashMap<>();
        for (char cha : pst) {
            if (!map.containsKey(cha)) {
                map.put(cha, 1);
            } else {
                map.put(cha, map.get(cha) + 1);
            }
        }
        int all = M;
        for (int end = 0; end < M - 1; end++) {
            if (map.containsKey(str[end])) {
                int count = map.get(str[end]);
                if (count > 0) {
                    all--;
                }
                map.put(str[end], count - 1);
            }
        }
        for (int end = M - 1, start = 0; end < N; end++, start++) {
            if (map.containsKey(str[end])) {
                int count = map.get(str[end]);
                if (count > 0) {
                    all--;
                }
                map.put(str[end], count - 1);
            }
            if (all == 0) {
                ans.add(start);
            }
            if (map.containsKey(str[start])) {
                int count = map.get(str[start]);
                if (count >= 0) {
                    all++;
                }
                map.put(str[start], count + 1);
            }
        }
        return ans;
    }

    // https://www.bilibili.com/video/BV1F6rdBZE73
    public static List<Integer> findAnagrams2(String s, String p) {

        // 存放结果的数组
        List<Integer> result = new ArrayList<>();

        int sLen = s.length();
        int pLen = p.length();
        //base case
        if (sLen < pLen) {
            return result;
        }
        // 存放p字符的计数器，每一个下标就是代表了一个字母，对应的元素就是该字母出现的次数
        int[] need = new int[26];
        // 存放当前窗口的计数器 每一个下标就是代表了一个字母，对应的元素就是该字母出现的次数
        int[] window = new int[26];
        // 1) 统计 need，并计算 needCount（p里有多少种不同字符） 要和命中次数作比较
        int needCount = 0;
        for (int i = 0; i < pLen; i++) {
            //标就是代表了一个字母，对应的元素就是该字母出现的次数
            int index = p.charAt(i) - 'a';
            if (need[index] == 0) {
                needCount++;
            }
            need[index]++;
        }
        // 2) valid 表示：窗口中有多少个字符的频次 “刚好等于 need” 也就是命中次数
        int valid = 0;
        // 3) 固定窗口大小为 m：右进、左出
        for (int i = 0; i < sLen; i++) {
            int in = s.charAt(i) - 'a';
            // 读入的字符对应的window下标 的元素++
            window[in]++;
            // 判断 窗口中的该字符和 作比较的该字符的出现数量
            if (need[in] > 0) {
                // 如果 当前字符读入 两个数量相同 就达标
                if (need[in] == window[in]) {
                    valid++;
                }
                // 如果 当前字符读入 比 作比较的字符串中的该字符多一个
                if (need[in] + 1 == window[in]) {
                    valid--;
                }
            }
            // ---- 左边弹出（当窗口长度 > 作比较的字符串的长度）----
            if (i > pLen) {
                // 需要被弹出的字符在window中的下标
                int out = s.charAt(i - pLen) - 'a';
                //先判断当前字符是否在作比较的字符串里面
                if (need[out] > 0) {
                    // 弹出前刚好达标，弹出后会变不达标
                    if (window[out] == need[out]) {
                        valid--;
                    }
                    // 弹出前超标1个，弹出后会刚好达标
                    if (window[out] == need[out]) {
                        valid++;
                    }
                    // 弹出
                    window[out]--;
                }
                //  ---- 检查命中：窗口长度已到 m（r >= 作比较的字符串长度-1）----
                if (i >= pLen - 1 && valid == needCount) {
                    result.add(i - pLen + 1);
                }
            }
        }
        return result;
    }
}
