package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

/**
 * <h3>最长公共前缀</h3>
 */
public class LCPLeetcode14 {
    static String longestCommonPrefix(String[] strings) {
        /*
            情况1：比较某一列时，遇到不同字符，i 之前的字符就是解
            情况2：比较某一列时，遇到字符串长度不够，i 之前的字符就是解
            情况3：i 循环自然结束，此时第一个字符串就是解
         */
        char[] first = strings[0].toCharArray(); // 第一个字符串
        for (int i = 0; i < first.length; i++) {
            char ch = first[i];
            for (int j = 1; j < strings.length; j++) {
                /*if (strings[j].length() == i) { // 情况 2
                    return new String(first, 0, i);
                }
                if(ch != strings[j].charAt(i)) { // 情况 1
                    return new String(first, 0, i);
                } */
                if (strings[j].length() == i || ch != strings[j].charAt(i)) {
                    return new String(first, 0, i);
                }
            }
        }
        return strings[0];
    }

    public static void main(String[] args) {
        System.out.println(longestCommonPrefix(new String[]{"flower", "flow", "flight"})); // fl
        System.out.println(longestCommonPrefix(new String[]{"dog", "racecar", "car"})); // 空串
        System.out.println(longestCommonPrefix(new String[]{"ab", "a"})); // a
        System.out.println(longestCommonPrefix(new String[]{"dog", "dogaa", "dogbb"})); // dog
    }
}
