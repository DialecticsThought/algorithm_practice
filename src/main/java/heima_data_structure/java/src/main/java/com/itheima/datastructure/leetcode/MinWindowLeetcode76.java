package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.util.HashMap;

public class MinWindowLeetcode76 {
    public static void main(String[] args) {
//        System.out.println(minWindow("ADOBECODEBANC", "ABC")); // BANC
        System.out.println(minWindow("aaabbbbbcdd", "abcdd")); // abbbbbcdd
    }

    record Answer(int count, int i, int j) {

    }

    static String minWindow(String s, String t) {
        char[] source = s.toCharArray();
        char[] target = t.toCharArray();
        HashMap<Character, Integer> targetCountMap = new HashMap<>();
        HashMap<Character, Integer> windowCountMap = new HashMap<>();
        for (char ch : target) {
            targetCountMap.compute(ch, (k, v) -> v == null ? 1 : v + 1);
        }
        int i = 0;
        int j = 0;
        Answer answer = new Answer(Integer.MAX_VALUE, i, j);
        int passCount = targetCountMap.size();
        int pass = 0;
        while (j < source.length) {
            char right = source[j];
            Integer c = windowCountMap.compute(right, (k, v) -> v == null ? 1 : v + 1);
            if (c.equals(targetCountMap.get(right))) {
                pass++;
            }
            System.out.println(windowCountMap);
            while (pass == passCount && i <= j) {
                System.out.println("都够了");
                if (j - i < answer.count) {
                    answer = new Answer(j - i, i, j);
                }
                char left = source[i];
                windowCountMap.compute(left, (k, v) -> v == null || v == 1 ? null : v - 1);
                System.out.println(windowCountMap);
                Integer x = targetCountMap.get(left);
                Integer y = windowCountMap.get(left);
                if (x != null && (y == null || y < x)) {
                    pass--;
                }
                i++;
            }
            j++;
        }
        return answer.count != Integer.MAX_VALUE ? s.substring(answer.i, answer.j+1) : "";
    }
}
