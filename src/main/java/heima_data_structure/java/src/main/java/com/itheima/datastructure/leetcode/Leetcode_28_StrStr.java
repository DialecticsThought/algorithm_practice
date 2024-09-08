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
        /**
         * origin = a a a c a a a b
         * pattern = a a a b
         * 外层循环是对origin字符串进行遍历
         * 外层循环不需要从头遍历到尾，
         * 遍历到 位置 到 origin字符串 长度 <= pattern 长度 的时候 不用匹配了 => 一定匹配不上 pattern
         * 内存循环是对pattern字符串进行遍历
         * <pre>
         * 初始
         * 外层第1轮循环
         * a a a b
         * j
         * a a a c a a a b
         * i
         * origin[i] = pattern[j]
         * a a a b
         *   j
         * a a a c a a a b
         *   i
         * origin[i] = pattern[j]
         * a a a b
         *     j
         * a a a c a a a b
         *     i
         * origin[i] = pattern[j]
         * a a a b
         *       j
         * a a a c a a a b
         *       i
         * origin[i] != pattern[j]
         * 退出循环
         * 外层第2轮循环
         * a a a b
         * j
         * a a a c a a a b
         *   i
         * origin[i] = pattern[j]
         * a a a b
         *   j
         * a a a c a a a b
         *     i
         * origin[i] = pattern[j]
         * a a a b
         *     j
         * a a a c a a a b
         *       i
         * origin[i] != pattern[j]
         * 退出循环
         * 外层第3轮循环
         * a a a b
         * j
         * a a a c a a a b
         *     i
         * origin[i] = pattern[j]
         * a a a b
         *   j
         * a a a c a a a b
         *       i
         * origin[i] != pattern[j]
         * 退出循环
         * 外层第4轮循环
         * a a a b
         * j
         * a a a c a a a b
         *         i
         * origin[i] = pattern[j]
         * a a a b
         *   j
         * a a a c a a a b
         *           i
         * origin[i] = pattern[j]
         * a a a b
         *     j
         * a a a c a a a b
         *             i
         * origin[i] = pattern[j]
         * a a a b
         *       j
         * a a a c a a a b
         *               i
         * origin[i] = pattern[j]
         * 并且 j = pattern。length-1
         * 结束
         * </pre>
         */
        while (i <= origin.length - pattern.length) {
            for (j = 0; j < pattern.length; j++) {
                if (pattern[j] != origin[i + j]) {
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
