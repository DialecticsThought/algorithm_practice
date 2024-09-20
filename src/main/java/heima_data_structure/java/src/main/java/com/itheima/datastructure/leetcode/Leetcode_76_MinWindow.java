package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.util.HashMap;

public class Leetcode_76_MinWindow {
    public static void main(String[] args) {
//        System.out.println(minWindow("ADOBECODEBANC", "ABC")); // BANC
        System.out.println(minWindow("aaabbbbbcdd", "abcdd")); // abbbbbcdd
    }

    record Answer(int count, int i, int j) {

    }

    /**
     * <pre>
     *  t = "a b c"
     *  s = "a d o b e c o d e b a n c"
     *  初始情况：
     *  记录t的map:
     *   a b c
     *   1 1 1
     *  初始指针i=0,j=0
     *   a d o b e c o d e b a n c
     *   i
     *   j
     *   记录s的map：
     *   当前状态
     *   a
     *   1
     *  开始
     *  1.j++
     *   a d o b e c o d e b a n c
     *   i
     *     j
     *   记录s的map
     *   a d
     *   1 1
     *  2.j++
     *   a d o b e c o d e b a n c
     *   i
     *       j
     *   记录s的map
     *   a d o
     *   1 1 1
     *  3.j++
     *   a d o b e c o d e b a n c
     *   i
     *         j
     *   记录s的map
     *   a d o b
     *   1 1 1 1
     *  4.j++
     *   a d o b e c o d e b a n c
     *   i
     *           j
     *   记录s的map
     *   a d o b e
     *   1 1 1 1 1
     *  5.j++
     *   a d o b e c o d e b a n c
     *   i
     *             j
     *   记录s的map
     *   a d o b e c
     *   1 1 1 1 1 1
     *   找到目标字符串 覆盖了 a b c
     *   当前是扩大j 找到了目标字符串
     *   现在要找最小覆盖子串 ，所以需要缩小i
     *  6.i--
     *   a d o b e c o d e b a n c
     *     i
     *             j
     *   记录s的map
     *   a d o b e c
     *   0 1 1 1 1 1
     *   此时不能覆盖了 a b c，继续j++
     *  7.j++
     *    a d o b e c o d e b a n c
     *      i
     *                j
     *   记录s的map
     *   a d o b e c
     *   0 1 2 1 1 1
     *  8.j++
     *    a d o b e c o d e b a n c
     *      i
     *                  j
     *   记录s的map
     *   a d o b e c
     *   0 2 2 1 1 1
     *  9.j++
     *    a d o b e c o d e b a n c
     *      i
     *                    j
     *   记录s的map
     *   a d o b e c
     *   0 2 2 1 2 1
     *  10.j++
     *    a d o b e c o d e b a n c
     *      i
     *                      j
     *   记录s的map
     *   a d o b e c
     *   0 2 2 2 2 1
     *  10.j++
     *    a d o b e c o d e b a n c
     *      i
     *                        j
     *   记录s的map
     *   a d o b e c
     *   1 2 2 2 2 1
     *   找到目标字符串 覆盖了 a b c
     *   当前是扩大j 找到了目标字符串
     *   现在要找最小覆盖子串 ，所以需要缩小i
     *  11.i--
     *    a d o b e c o d e b a n c
     *        i
     *                        j
     *   记录s的map
     *   a d o b e c
     *   1 1 2 2 2 1
     *  12.i--
     *    a d o b e c o d e b a n c
     *          i
     *                        j
     *   记录s的map
     *   a d o b e c
     *   1 1 1 2 2 1
     *  13.i--
     *    a d o b e c o d e b a n c
     *            i
     *                        j
     *   记录s的map
     *   a d o b e c
     *   1 1 1 1 1 1
     *  14.i--
     *    a d o b e c o d e b a n c
     *              i
     *                        j
     *   记录s的map
     *   a d o b e c
     *   1 1 2 1 1 1
     *  15.i--
     *    a d o b e c o d e b a n c
     *                i
     *                        j
     *   记录s的map
     *   a d o b e c
     *   1 1 2 1 1 0
     *   此时不能覆盖了 a b c，继续j++
     *  16.j++
     *    a d o b e c o d e b a n c
     *                i
     *                          j
     *   记录s的map
     *   a d o b e c n
     *   1 1 2 1 1 0 1
     *   此时不能覆盖了 a b c，继续j++
     *  17.j++
     *    a d o b e c o d e b a n c
     *                i
     *                           j
     *   记录s的map
     *   a d o b e c n
     *   1 1 2 1 1 1 1
     *   找到目标字符串 覆盖了 a b c
     *   当前是扩大j 找到了目标字符串
     *   现在要找最小覆盖子串 ，所以需要缩小i
     *  18.i--
     *    a d o b e c o d e b a n c
     *                  i
     *                           j
     *   记录s的map
     *   a d o b e c n
     *   1 1 1 1 1 1 1
     *  19.i--
     *    a d o b e c o d e b a n c
     *                    i
     *                           j
     *   记录s的map
     *   a d o b e c n
     *   1 0 1 1 1 1 1
     *  19.i--
     *    a d o b e c o d e b a n c
     *                      i
     *                           j
     *   记录s的map
     *   a d o b e c n
     *   1 0 1 1 0 1 1
     *   到这里 找到了最小覆盖子传
     * </pre>
     *
     * @param s
     * @param t
     * @return
     */
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
        return answer.count != Integer.MAX_VALUE ? s.substring(answer.i, answer.j + 1) : "";
    }
}
