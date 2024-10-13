package heima_data_structure.java.src.main.java.com.itheima.algorithm.divideandconquer;

import java.util.Arrays;

/**
 * <h3>至少k个字符的最长子串</h3>
 * <pre>
 * str = ababbc  要求 k = 2 也就是切割出的子串 每一个字符最少出现2次
 * 1.遍历str的所有字符
 * 2.如果对str做切分
 *      做出 ababb | c , c这个子串是不可行的, ababb是可行的
 *
 * str = ababbc  要求 k = 3 也就是切割出的子串 每一个字符最少出现3次
 * 1.遍历str的所有字符
 * 2.如果对str做切分
 *      2.1做出 ababb | c , c这个子串是不可行的, ababb也是不可行的
 *          ababb中 a出现的次数是2 b出现的次数是3 因为a所以不满足
 *      2.2做出 a | babb | c , c这个子串是不可行的,  a这个子串是不可行的 , babb也是不可行的
 *          babb中 a出现的次数是1 b出现的次数是3 因为a所以不满足
 *
 * str = dddxaabaaabaacciiiiefbff  k = 3
 * 第1步 遍历str的所有字符
 * 第2步 把 出现次数<k的字符 或者 整个子串长度 < k 的子串 剔除掉
 *      变成 ddd aabaaabaa iiii fbff  这3个子串
 * 第3步 把  第1步生成的子串中 出现次数<k的字符 或者 整个子串长度 < k 的子串 剔除掉
 *      变成 ddd aa aaa aa iiii f ff  这7个子串
 * 第4步 把  第2步生成的子串中 出现次数<k的字符 或者 整个子串长度 < k 的子串 剔除掉
 *      变成 ddd  aaa  iiii  这3个子串
 * </pre>
 * <pre>
 * 统计字符串中每个字符的出现次数，移除哪些出现次数 < k 的字符
 * 剩余的子串，递归做此处理，直至
 * - 整个子串长度 < k (排除)
 * - 子串中没有出现次数 < k 的字符
 * </pre>
 */
public class LeetCode_395_LongestSubstring {
    /**
     * <pre>
     *  TODO
     *   lss("dddxaabaaabaacciiiiefbff", 3)
     *   |
     *   ├── 遍历str的所有字符, 检查字符出现次数并找到需要切割的字符：
     *   |     - 在索引 3 处，字符 'x' 出现次数小于 3，需要切割。
     *   |
     *   ├── 左子串：
     *   |     lss("ddd", 3)
     *   |     |
     *   |     ├── 所有字符均至少出现 3 次，返回长度 3。
     *   |
     *   ├── 右子串：
     *         lss("aabaaabaacciiiiefbff", 3)
     *         |
     *         ├── 遍历str的所有字符, 检查字符出现次数并找到需要切割的字符：
     *         |     - 在索引 9 处，字符 'c' 出现次数小于 3，需要切割。
     *         |
     *         ├── 左子串：
     *         |     lss("aabaaabaa", 3)
     *         |     |
     *         |     ├── 遍历str的所有字符, 检查字符出现次数并找到需要切割的字符：
     *         |     |     - 在索引 2 处，字符 'b' 出现次数小于 3，需要切割。
     *         |     |
     *         |     ├── 左子串：
     *         |     |     lss("aa", 3)
     *         |     |     |
     *         |     |     ├── 字符串长度小于 3，返回 0。
     *         |     |
     *         |     ├── 右子串：
     *         |     |     lss("aabaa", 3)
     *         |     |     |
     *         |     |     ├── 遍历str的所有字符, 检查字符出现次数并找到需要切割的字符：
     *         |     |     |     - 在索引 2 处，字符 'b' 出现次数小于 3，需要切割。
     *         |     |     |
     *         |     |     ├── 左子串：
     *         |     |     |     lss("aa", 3)
     *         |     |     |     |
     *         |     |     |     ├── 字符串长度小于 3，返回 0。
     *         |     |     |
     *         |     |     ├── 右子串：
     *         |     |     |     lss("aa", 3)
     *         |     |     |     |
     *         |     |     |     ├── 字符串长度小于 3，返回 0。
     *         |     |     |
     *         |     |     ├── 返回最大值 0。
     *         |     |
     *         |     ├── 返回最大值 0。
     *         |
     *         ├── 右子串：
     *         |     lss("iiiefbff", 3)
     *         |     |
     *         |     ├── 遍历str的所有字符, 检查字符出现次数并找到需要切割的字符：
     *         |     |     - 在索引 3 处，字符 'e' 出现次数小于 3，需要切割。
     *         |     |
     *         |     ├── 左子串：
     *         |     |     lss("iii", 3)
     *         |     |     |
     *         |     |     ├── 所有字符均至少出现 3 次，返回长度 3。
     *         |     |
     *         |     ├── 右子串：
     *         |     |     lss("fbff", 3)
     *         |     |     |
     *         |     |     ├── 遍历str的所有字符, 检查字符出现次数并找到需要切割的字符：
     *         |     |     |     - 在索引 1 处，字符 'b' 出现次数小于 3，需要切割。
     *         |     |     |
     *         |     |     ├── 左子串：
     *         |     |     |     lss("f", 3)
     *         |     |     |     |
     *         |     |     |     ├── 字符串长度小于 3，返回 0。
     *         |     |     |
     *         |     |     ├── 右子串：
     *         |     |     |     lss("ff", 3)
     *         |     |     |     |
     *         |     |     |     ├── 字符串长度小于 3，返回 0。
     *         |     |     |
     *         |     |     ├── 返回最大值 0。
     *         |     |
     *         |     ├── 返回最大值 3。
     *         |
     *         ├── 返回最大值 3。
     *   |
     *   ├── 最终结果：
     *         返回左子串和右子串中的最大值，即 3。
     * </pre>
     *
     * @param s
     * @param k
     * @return
     */
    static int longestSubstring(String s, int k) {
        // base case1
        // if 子串长度 < k,那么 子串落选
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
            // TODO 这一步 说明，如果要切割，那么切割的字符一定是第一次遍历到的
            char c = chars[i];
            // 字符[i]出现的次数
            int count = counts[c - 'a'];
            // 满足  count > 0 && count < k 条件 就要切割成2个子串
            if (count > 0 && count < k) {
                /**
                 * eg:
                 * str = aaaccbbb k=3
                 * 中间的c只出现2次
                 * 那么切割 可以切成 aaa 和 bbb
                 * 需要 i和j两个索引
                 */
                int j = i + 1;
                // chars[j] 得到 j索引对应的字符
                // chars[j] - 'a' 就是counts数组中 chars[j]字符对应的索引位置
                // 如果  chars[j] 出现的次数 < k 那么说明 需要删除 chars[j]
                // j++ 继续判断下一个字符
                while (j < s.length() && counts[chars[j] - 'a'] < k) {
                    j++;
                }
                // 切割成 0 ~ i 和  j ~ str.length
                System.out.println(s.substring(0, i) + "\t" + s.substring(j));
                // 继续对 切割 0 ~ i得到的子串 做递归
                int case1 = longestSubstring(s.substring(0, i), k);
                // 继续对 切割 j ~ str.length得到的子串 做递归
                int case2 = longestSubstring(s.substring(j), k);
                // 返回
                return Integer.max(case1, case2);
            }
        }
        // base case2
        // 执行到这里 就说明  子串的长度就是需要返回的长度
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
