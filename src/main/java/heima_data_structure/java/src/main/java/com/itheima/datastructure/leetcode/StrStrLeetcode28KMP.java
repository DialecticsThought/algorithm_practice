package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.util.Arrays;

/**
 * <h3>字符串匹配 - Knuth Morris Pratt 算法</h3>
 */
public class StrStrLeetcode28KMP {
    static int strStr(String str1, String str2) {
        char[] origin = str1.toCharArray();     // 原始
        char[] pattern = str2.toCharArray();    // 模式
        int[] lps = lps(pattern);       // 最长前后缀数组
        /*
            1. 匹配成功，i++ j++，直到 j==模式字符串长度
            2. 匹配失败
                j != 0 跳过最长前后缀字符，继续匹配
                j == 0 则 i++
         */
        int i = 0;
        int j = 0;
        while (pattern.length - j <= origin.length - i) {
            if (origin[i] == pattern[j]) {
                i++;
                j++;
            } else if (j == 0) {
                i++;
            } else {
                j = lps[j - 1];
            }
            if (j == pattern.length) {
                // 找到解
                return i - j;
            }
        }
        return -1;
    }

    /*
        最长前后缀数组：只跟模式字符串相关
        1. 索引：使用了模式字符串前 j 个字符串 - 1
        2. 值：最长前后缀的长度（恰好是j要跳转的位置）
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
