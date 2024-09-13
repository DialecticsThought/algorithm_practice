package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.util.Arrays;

/**
 * <h3>字符串匹配 - Knuth Morris Pratt 算法</h3>
 * <pre>
 * pattern = a b a b a c a
 * origin = a b a b a b a b c a b a c a c a b a b a c a
 * 初始:
 * a b a b a c a
 * i
 * a b a b a b a b c a b a c a c a b a b a c a
 * j
 * pattern的第一个字符a 和 origin 的第一个字符a 开始比较
 * ....
 * 不断地找 找到了这里
 * a b a b a c a
 *           j
 * a b a b a b a b c a b a c a c a b a b a c a
 *           i
 * 需要pattern字符串的前缀 和origin字符串的后缀
 * 前面有5个共同的字符的时候
 * 公共 字符串 a b a b a 的 最长公共前后缀  a b a
 * 也就是说
 * a b a b a c a
 * _ _ _     j
 * a b a b a b a b c a b a c a c a b a b a c a
 *     _ _ _ i
 * 公共 字符串 a b a b a 的 最长公共前后缀 a b a
 * 之后找的话,可以加速
 *     a b a b a c a
 *     _ _ _     j
 * a b a b a b a b c a b a c a c a b a b a c a
 *         _ _ _ i
 * 公共 字符串 a b a b a 的 最长公共前后缀 a b a
 * 之后找的话,可以加速
 *         a b a b a c a
 *         _ _     j
 * a b a b a b a b c a b a c a c a b a b a c a
 *             _ _ i
 * 公共 字符串 a b a b 的 最长公共前后缀 a b
 * 之后找的话,可以加速
 *             a b a b a c a
 *             _ _ j
 * a b a b a b a b c a b a c a c a b a b a c a
 *             _ _ i
 * 公共 字符串 a b 的 最长公共前后缀 没有
 * 这种情况下 i和j 从头开始,不能加速
 * 之后找的话
 *                 a b a b a c a
 *                 j
 * a b a b a b a b c a b a c a c a b a b a c a
 *                 i
 * origin[i] != pattern[j]
 * 没有公共字符串
 * 直接i++
 *                   a b a b a c a
 *                   j
 * a b a b a b a b c a b a c a c a b a b a c a
 *                   i
 * origin[i] = pattern[j]
 * j++ and i+j
 *                   a b a b a c a
 *                     j
 * a b a b a b a b c a b a c a c a b a b a c a
 *                     i
 * origin[i] = pattern[j]
 * j++ and i+j
 *                   a b a b a c a
 *                       j
 * a b a b a b a b c a b a c a c a b a b a c a
 *                       i
 * origin[i] = pattern[j]
 * j++ and i+j
 *                   a b a b a c a
 *                         j
 * a b a b a b a b c a b a c a c a b a b a c a
 *                         i
 * origin[i] != pattern[j]
 * 公共 字符串 a b a 的 最长公共前后缀 a
 * 之后找的话
 *                       a b a b a c a
 *                         j
 * a b a b a b a b c a b a c a c a b a b a c a
 *                         i
 * origin[i] != pattern[j]
 * 公共 字符串 a 的 最长公共前后缀 无
 * i++
 *                         a b a b a c a
 *                         j
 * a b a b a b a b c a b a c a c a b a b a c a
 *                         i
 * 没有公共字符串
 *                           a b a b a c a
 *                           j
 * a b a b a b a b c a b a c a c a b a b a c a
 *                           i
 * origin[i] = pattern[j]
 * j++ and i+j
 *                             a b a b a c a
 *                             j
 * a b a b a b a b c a b a c a c a b a b a c a
 *                             i
 * 公共 字符串 a 的 最长公共前后缀 无
 * 直接i++
 *                               a b a b a c a
 *                               j
 * a b a b a b a b c a b a c a c a b a b a c a
 *                               i
 * ....
 * </pre>
 * 本质:
 * 求公共字符串的前后缀只和pattern有关
 * pattern
 */
public class Leetcode_28_KMP_StrStr {
    static int strStr(String str1, String str2) {
        char[] origin = str1.toCharArray();     // 原始
        char[] pattern = str2.toCharArray();    // 模式
        int[] lps = lps(pattern);       // 最长前后缀数组
        int i = 0;
        int j = 0;
        /**
         * while 的 退出循环条件 不是 i < origin.length
         * <pre>
         *                                    a b a b a c a
         *                                          j
         *  a b a b a b a b c a b a c a c a b a b a b a
         *                                          i
         * 这种情况不需要比对了
         * 因为模式字符串剩下3个字符   3 = pattern.length - j
         * 但是原始字符串只剩下1个字符  1 = origin.length - i
         * </pre>
         */
        while (pattern.length - j <= origin.length - i) {
            if (origin[i] == pattern[j]) {
                /**
                 * <pre>
                 *  1. 匹配成功，i++ j++，直到 j==模式字符串长度
                 *  eg:
                 *  a b a b a c a
                 *  j
                 *  a b a b a b a b c a b a c a c a b a b a b a
                 *  i
                 *  匹配成功
                 * </pre>
                 * 2. 匹配失败
                 * j != 0 跳过最长前后缀字符，继续匹配
                 * j == 0 则 i++
                 */
                i++;
                j++;
                // 没有这个分支就是匹配失败
            } else if (j == 0) {
                /**
                 * <pre>
                 * eg:
                 * origin[i] != pattern[j]
                 *                 a b a b a c a
                 *                 j
                 * a b a b a b a b c a b a c a c a b a b a b a
                 *                 i
                 * 这种情况之前没有匹配好的字符
                 * 只需要 i++ 即：
                 *                   a b a b a c a
                 *                   j
                 * a b a b a b a b c a b a c a c a b a b a b a
                 *                   i
                 * </pre>
                 */
                i++;
            } else {
                /**
                 * <pre>
                 * a b a b a c a
                 *           j
                 * a b a b a b a b c a b a c a c a b a b a b a
                 *           i
                 * a b a b a 的公共前后缀 = a b a 长度是3
                 * 直接查lsp[j-1]=3
                 * 这是下一轮 j跳到的位置
                 *       a b a b a c a
                 *       j
                 * a b a b a b a b c a b a c a c a b a b a b a
                 *           i
                 * </pre>
                 */
                j = lps[j - 1];
            }
            if (j == pattern.length) {
                /**
                 * 当模式字符串到最后一个位置的时候找到解了
                 * <pre>
                 *                               a b a b a c a
                 *                                           j
                 * a b a b a b a b c a b a c a c a b a b a b a
                 *                                           i
                 * </pre>
                 * 其索引位置 就是 i-j
                 */
                return i - j;
            }
        }
        return -1;
    }

    /**
     * 最长前后缀数组：只跟模式字符串相关
     * 1. 索引：使用了模式字符串前 j 个字符串 - 1
     * 2. 值：最长前后缀的长度（恰好是j要跳转的位置）
     */
    static int[] lps(char[] pattern) {
//        return new int[]{0, 0, 1, 2, 3, 0, 1};
        int[] lps = new int[pattern.length];
        int i = 1;
        int j = 0;
        while (i < pattern.length) {
            if (pattern[i] == pattern[j]) {
                lps[i] = j + 1;
                i++;
                j++;
            } else if (j == 0) {
                i++;
            } else {
                j = lps[j - 1];
            }
        }
        return lps;
    }

    public static void main(String[] args) {
//        System.out.println(strStr("ababababcabacacababaca", "ababaca"));
//        System.out.println("ababababcabacacababaca".indexOf("ababaca"));
        System.out.println(Arrays.toString(lps("ababaca".toCharArray())));
    }
}
