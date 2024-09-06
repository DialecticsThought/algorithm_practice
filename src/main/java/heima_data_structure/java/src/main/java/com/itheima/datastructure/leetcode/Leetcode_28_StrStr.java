package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

/**
 * <h3>字符串匹配</h3>
 */
public class Leetcode_28_StrStr {
    static int strStr(String str1, String str2) {
        char[] origin = str1.toCharArray(); // 原始
        char[] pattern = str2.toCharArray(); // 模式
        int i = 0;
        int j = 0;
        while (i <= origin.length - pattern.length) {
            for (j = 0; j < pattern.length; j++) {
                if (pattern[j] != origin[i+j]) {
                    break;
                }
            }
            if (j == pattern.length) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(strStr("aaacaaab", "aaab"));
    }
}
