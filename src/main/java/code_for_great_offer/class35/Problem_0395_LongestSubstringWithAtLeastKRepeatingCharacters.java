package code_for_great_offer.class35;
/*
* 给你一个字符串 s 和一个整数 k ，请你找出 s 中的最长子串
* 要求该子串中的每一字符出现次数都不少于 k 。返回这一子串的长度。
* 示例 1：
* 输入：s = "aaabb", k = 3
* 输出：3
* 解释：最长子串为 "aaa" ，其中 'a' 重复了 3 次。
* 示例 2：
* 输入：s = "ababbc", k = 2
* 输出：5
* 解释：最长子串为 "ababb" ，其中 'a' 重复了 2 次， 'b' 重复了 3 次。
* 链接：https://leetcode.cn/problems/longest-substring-with-at-least-k-repeating-characters
*
* */
public class Problem_0395_LongestSubstringWithAtLeastKRepeatingCharacters {
    /*
    *TODO
    * 利用只有a-z小写
    * 所以问题
    * 在整个范围中，子串只包含1种字符 每一字符出现次数>=k 有多长
    * 在整个范围中，子串只包含2种字符 每一字符出现次数>=k 有多长
    * 在整个范围中，子串只包含3种字符 每一字符出现次数>=k 有多长
    * ...
    *  在整个范围中，子串只包含26种字符 每一字符出现次数>=k 有多长
    * 相当于便利了26遍
    * */
    public static int longestSubstring1(String s, int k) {
        char[] str = s.toCharArray();
        int N = str.length;
/*        int max = 0;
        for (int i = 0; i < N; i++) {
            *//*
             * map[i]=v 表示i这个acssii码的字符 上次出现在v位置
             * *//*
            int[] count = new int[256];
            int collect = 0;
            int satisfy = 0;
            for (int j = i; j < N; j++) {
                if (count[str[j]] == 0) {
                    collect++;
                }
                if (count[str[j]] == k - 1) {
                    satisfy++;
                }
                count[str[j]]++;
                if (collect == satisfy) {
                    max = Math.max(max, j - i + 1);
                }
            }
        }*/
        int[] map = new int[256];
        for (int i = 0; i < N; i++) {
            /*
             * map[i]=v 表示i这个acssii码的字符 上次出现在v位置
             * */
            map[i] = -1;
        }
        int ans = 1;//答案最少是1 代表本身
        int pre = 1;//上一个位置向左推了多长
        map[str[0]]=0;
        for (int i = 1; i < N; i++) {
            /*
             * 2个因素
             * 当前位置的字符 之前出现过的话 ， 上一次出现的位置 就是向左推的最远距离
             * eg: 之前没有出现过a 那么对应的位置初始值是-1 现在求的是17位置的a的最长不重复字符的长度的话
             * 距离就是 17-（-1）=18
             * 当前位置的前一个位置能向左推的长度+1就是当前位置能推得的最远距离
             * */
            int p1 = i - map[str[i]];
            int p2 = pre + 1;
            int cur = Math.min(p1, p2);
            ans = Math.max(ans, cur);
            pre = cur;// 当前位置的最长距离就是 下一轮循环 前一个位置的最长距离
            map[str[i]] = i;
        }
        return ans;
    }

    public static int longestSubstring2(String s, int k) {
        char[] str = s.toCharArray();
        int N = str.length;
        int max = 0;
        for (int require = 1; require <= 26; require++) {
            // 3种
            // a~z 出现次数
            int[] count = new int[26];
            // 目前窗口内收集了几种字符了
            int collect = 0;
            // 目前窗口内出现次数>=k次的字符，满足了几种
            int satisfy = 0;
            // 窗口右边界
            int R = -1;
            for (int L = 0; L < N; L++) { // L要尝试每一个窗口的最左位置
                // [L..R] R+1
                while (R + 1 < N && !(collect == require && count[str[R + 1] - 'a'] == 0)) {
                    R++;
                    if (count[str[R] - 'a'] == 0) {
                        collect++;
                    }
                    if (count[str[R] - 'a'] == k - 1) {
                        satisfy++;
                    }
                    count[str[R] - 'a']++;
                }
                // [L...R]
                if (satisfy == require) {
                    max = Math.max(max, R - L + 1);
                }
                // L++
                if (count[str[L] - 'a'] == 1) {
                    collect--;
                }
                if (count[str[L] - 'a'] == k) {
                    satisfy--;
                }
                count[str[L] - 'a']--;
            }
        }
        return max;
    }

    // 会超时，但是思路的确是正确的
    public static int longestSubstring3(String s, int k) {
        return process(s.toCharArray(), 0, s.length() - 1, k);
    }

    public static int process(char[] str, int L, int R, int k) {
        if (L > R) {
            return 0;
        }
        int[] counts = new int[26];
        for (int i = L; i <= R; i++) {
            counts[str[i] - 'a']++;
        }
        char few = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < 26; i++) {
            if (counts[i] != 0 && min > counts[i]) {
                few = (char) (i + 'a');
                min = counts[i];
            }
        }
        if (min >= k) {
            return R - L + 1;
        }
        int pre = 0;
        int max = Integer.MIN_VALUE;
        for (int i = L; i <= R; i++) {
            if (str[i] == few) {
                max = Math.max(max, process(str, pre, i - 1, k));
                pre = i + 1;
            }
        }
        if (pre != R + 1) {
            max = Math.max(max, process(str, pre, R, k));
        }
        return max;
    }

}
