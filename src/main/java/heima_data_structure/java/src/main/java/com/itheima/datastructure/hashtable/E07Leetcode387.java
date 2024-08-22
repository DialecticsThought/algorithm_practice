package heima_data_structure.java.src.main.java.com.itheima.datastructure.hashtable;

import java.util.Arrays;

/**
 * 字符串中的第一个不重复字符
 *
 * <p>s 中仅包含小写字符</p>
 */
public class E07Leetcode387 {
    /*
        输入: s = "leetcode"
        l t c o d e
        输出: 0

        输入: s = "loveleetcode"
        输出: 2

        输入: s = "aabb"
        输出: -1
     */
    public int firstUniqChar(String s) {
        int[] array = new int[26];
        char[] chars = s.toCharArray();
        for (char ch : chars) {
            array[ch - 97]++;
        }
//        System.out.println(Arrays.toString(array));
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if(array[ch - 97] == 1) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(new E07Leetcode387().firstUniqChar("leetcode"));
    }
}
