package heima_data_structure.java.src.main.java.com.itheima.datastructure.hashtable;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 无重复字符的最长子串
 * <p>1. 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。</p>
 * <p>2. s 由英文字母、数字、符号和空格组成</p>
 */
public class E02Leetcode3 {

    /*
    [(a,3) (b,1) (c,2)]

     b
       e
    abcabcbb
    要点：
        1.用 begin 和 end 表示子串开始和结束位置
        2.用 hash 表检查重复字符
        3.从左向右查看每个字符, 如果:
         - 没遇到重复字符，调整 end
         - 遇到重复的字符，调整 begin
         - 将当前字符放入 hash 表
        4.end - begin + 1 是当前子串长度
     */
    public int lengthOfLongestSubstring(String s) {
        int[] map = new int[128];
        Arrays.fill(map, -1);
        int begin = 0;
        int maxLength = 0;
        for (int end = 0; end < s.length(); end++) {
            char ch = s.charAt(end);
            if (map[ch] != -1) { // 重复时调整 begin
                begin = Math.max(begin, map[ch] + 1);
                map[ch] = end;
            } else { // 不重复
                map[ch] = end;
            }
            System.out.println(s.substring(begin, end + 1));
            maxLength = Math.max(maxLength, end - begin + 1);
        }
        return maxLength;
    }

    public static void main(String[] args) {
        E02Leetcode3 e02 = new E02Leetcode3();
//        System.out.println(e02.lengthOfLongestSubstring("abcabcbb"));
        System.out.println(e02.lengthOfLongestSubstring("abca"));
        /*
            [(a,0),(b,2)]
              b
               e
            abba
         */
    }
}
