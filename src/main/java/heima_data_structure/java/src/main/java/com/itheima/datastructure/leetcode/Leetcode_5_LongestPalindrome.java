package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

/**
 * <h3>最长回文子串</h3>
 */
public class Leetcode_5_LongestPalindrome {
    public static void main(String[] args) {
        System.out.println(longestPalindrome("babad"));
        System.out.println(longestPalindrome("cbbd"));
        System.out.println(longestPalindrome("a"));
        System.out.println(longestPalindrome("bccbcbabcbafa"));
    }

    static String longestPalindrome(String s) {
        left = 0;
        right = 0;
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length - 1; i++) {
            extend(chars, i, i); // 一个字符作为中心点
            extend(chars, i, i + 1); // 两个字符作为中心点
        }
        return new String(chars, left, right - left + 1);
    }

    static int left; // i
    static int right; // j

    static void extend(char[] chars, int i, int j) {
        while (i >= 0 && j < chars.length
                && chars[i] == chars[j]) { // 如果是回文，扩大回文范围
            i--; // -1
            j++; // 4
        }
        // 退出循环时，i和j指向的不是回文，需要还原
        i++;
        j--;
        if (j - i > right - left) {
            left = i;
            right = j;
        }
    }
}
