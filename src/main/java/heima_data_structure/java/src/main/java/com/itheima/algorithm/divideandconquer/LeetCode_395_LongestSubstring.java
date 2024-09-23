package heima_data_structure.java.src.main.java.com.itheima.algorithm.divideandconquer;

import java.util.Arrays;

/**
 * <h3>至少k个字符的最长子串</h3>
 * <pre>
 * str = ababbc  要求 k = 2 也就是切割出的子串 每一个字符最少出现2次
 * 如果对str做切分
 *      做出 ababb | c , c这个子串是不可行的, ababb是可行的
 *
 * str = ababbc  要求 k = 3 也就是切割出的子串 每一个字符最少出现3次
 * 如果对str做切分
 *      做出 ababb | c , c这个子串是不可行的, ababb也是不可行的
 *          ababb中 a出现的次数是2 b出现的次数是3 因为a所以不满足
 *      做出 a | babb | c , c这个子串是不可行的,  a这个子串是不可行的 , babb也是不可行的
 *          babb中 a出现的次数是1 b出现的次数是3 因为a所以不满足
 *
 * str = dddxaabaaabaacciiiiefbff  k = 3
 * 第1步 把 出现次数<k的字符 或者 整个子串长度 < k 的子串 剔除掉
 *      变成 ddd aabaaabaa iiii fbff  这3个子串
 * 第2步 把  第1步生成的子串中 出现次数<k的字符 或者 整个子串长度 < k 的子串 剔除掉
 *      变成 ddd aa aaa aa iiii f ff  这7个子串
 * 第3步 把  第2步生成的子串中 出现次数<k的字符 或者 整个子串长度 < k 的子串 剔除掉
 *      变成 ddd  aaa  iiii  这3个子串
 * </pre>
 * 统计字符串中每个字符的出现次数，移除哪些出现次数 < k 的字符
 * 剩余的子串，递归做此处理，直至
 *    - 整个子串长度 < k (排除)
 *    - 子串中没有出现次数 < k 的字符
 */
public class LeetCode_395_LongestSubstring {

    static int longestSubstring(String s, int k) {
        // base case 子串长度 < k 子串落选
        // 方法一开始 或者 递归进入该方法一开始 会判断
        if (s.length() < k) {
            return 0;
        }
        // 索引对应字符 值用来存储该字符出现了几次
        int[] counts = new int[26];
        char[] chars = s.toCharArray();
        // 'a' -> 0 索引 'b' -> 1 索引 ....
        for (char c : chars) {
            counts[c - 'a']++;
        }
        // 输出统计
        System.out.println(Arrays.toString(counts));
        // 遍历 字符串的所有字符
        for (int i = 0; i < chars.length; i++) {
            // 得到 当前遍历到的字符
            char c = chars[i];
            // 字符[i]出现的次数
            int count = counts[c - 'a'];
            // 满足  count > 0 && count < k 条件 就要切割成2个子串
            if (count > 0 && count < k) {
                int j = i + 1;
                while(j < s.length() && counts[chars[j] - 'a'] < k) {
                    j++;
                }
                // 切割成 0 ~ i 和  i+1 ~ str.length
                System.out.println(s.substring(0, i) + "\t" + s.substring(j));
                return Integer.max(
                        longestSubstring(s.substring(0, i), k),
                        longestSubstring(s.substring(j), k)
                );
            }
        }
        // 子串入选情况
        return s.length();
    }

    public static void main(String[] args) {
        //                                         i j
        System.out.println(longestSubstring("aaaccbbb", 3)); // ababb
        System.out.println(longestSubstring("dddxaabaaabaacciiiiefbff", 3));
//        System.out.println(longestSubstring("ababbc", 3)); // ababb
//        System.out.println(longestSubstring("ababbc", 2)); // ababb
    }
}
