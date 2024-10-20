package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * code_for_great_offer.class03
 * 本题测试链接 : https://leetcode.com/problems/longest-substring-without-repeating-characters/
 */
public class LeetCode_003_LongestSubstringWithoutRepeatingCharacters {

    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        char[] str = s.toCharArray();
        // map[i] =表示ascii表第i个字符 上次出现的位置
        int[] map = new int[256];
        //TODO 说有字符都在-1位置出现过
        for (int i = 0; i < 256; i++) {
            map[i] = -1;
        }
        map[str[0]] = 0;
        int N = str.length;
        int ans = 1;//TODO 初始答案
        int pre = 1;//TODO 代表上一位置向左推了多少长度的答案 对于0位置 pre=1
        for (int i = 1; i < N; i++) {
            /**
             *TODO
             * i位置出现字符是a 那么 向左看j位置的字符也是a 那么说明 最大长度就是j-i ==> 可能性1 => i - map[str[i]]
             * i-1位置的长度+1（i位置的字符）  ==> 可能性2
             * 可能性1 可能性2 取最小
             * 取最小因为 i-1位置的长度对应的字符串 可能有和i位置的重复 那么答案就不是i-1位置的长度+1
             * 更新答案，给下一轮循环使用
             * */
            pre = Math.min(i - map[str[i]], pre + 1);
            ans = Math.max(ans, pre);
            map[str[i]] = i;
        }
        return ans;
    }

    /**
     * 该解法使用两个嵌套循环来以不同的起始点和结束点检查每个子字符串是否有重复字符。
     * allUnique 方法用于检查子字符串中是否有重复字符，通过遍历子字符串中的每个字符，
     * 将其添加到一个 HashSet 集合中，如果遇到重复字符，则返回 false。
     *
     * 请注意，此解法的时间复杂度为 O(n^3)，其中 n 是字符串的长度。
     * 它通过检查每个可能的子字符串来找到最长的无重复字符子串。
     * 这种解法在输入较大的情况下可能会超时，因此不是一个高效的解法。可以通过使用滑动窗口算法来优化该问题的解法
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring2(String s) {
        int n = s.length();
        int maxLength = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j <= n; j++) {
                if (allUnique(s, i, j)) {
                    maxLength = Math.max(maxLength, j - i);
                }
            }
        }

        return maxLength;
    }

    private boolean allUnique(String s, int start, int end) {
        Set<Character> set = new HashSet<>();
        for (int i = start; i < end; i++) {
            char c = s.charAt(i);
            if (set.contains(c)) {
                return false;
            }
            set.add(c);
        }
        return true;
    }
}
