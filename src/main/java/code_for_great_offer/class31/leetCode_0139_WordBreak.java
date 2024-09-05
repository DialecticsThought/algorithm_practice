package code_for_great_offer.class31;

import java.util.HashMap;
import java.util.List;

/*
 * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典。请你判断是否可以利用字典中出现的单词拼接出 s 。
 * 注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。
 * 示例 1：
 * 输入: s = "leetcode", wordDict = ["leet", "code"]
 * 输出: true
 * 解释: 返回 true 因为 "leetcode" 可以由 "leet" 和 "code" 拼接成。
 * 示例 2：
 * 输入: s = "applepenapple", wordDict = ["apple", "pen"]
 * 输出: true
 * 解释: 返回 true 因为 "applepenapple" 可以由 "apple" "pen" "apple" 拼接成。
 * 注意，你可以重复使用字典中的单词。
 * 示例 3：
 * 输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 * 输出: false
 * 链接：https://leetcode.cn/problems/word-break
 * lintcode也有测试，数据量比leetcode大很多 : https://www.lintcode.com/problem/107/
 *
 * */

public class leetCode_0139_WordBreak {
    //TODO 前缀树加速枚举
    public static class Node {
        public boolean end;
        public Node[] nexts;

        public Node() {
            end = false;
            nexts = new Node[26];
        }
    }

    /*
     *TODO
     * 题解： https://leetcode.cn/problems/word-break/solution/shou-hui-tu-jie-san-chong-fang-fa-dfs-bfs-dong-tai/
     * 从左往右的尝试模型
     * bool f(s,wordList,i) 一个可变参数 2个固定参数
     * 表示来到i位置，s[i]~s[s.len-1]的单词能否被wordList的单词分解
     * i位置单独成一单词 如果wordList有这个单词 那么执行 f(s,wordDict,i+1)
     * i~i+1位置成一单词 如果wordList有这个单词 那么执行 f(s,wordDict,i+2)
     * ...
     * 直到i~len-1位置成一单词 如果wordList有这个单词 那么执行 f(s,wordDict,len)
     * */
    public static boolean process(String s, List<String> wordDict, int i, HashMap<Integer, Boolean> map) {
        if (map.get(i) != null) {
            return map.get(i);
        }

        if (i == s.length()) {
            return true;
        }
        //TODO 枚举
        for (int count = i; count < s.length(); count++) {
            if (wordDict.contains(s.substring(i, count + 1))  && process(s, wordDict, count + 1, map)) {
                map.put(i, true);
                return true;
            }
        }
        map.put(i, false);
        return false;
    }

    public static boolean wordBreak1(String s, List<String> wordDict) {
        //TODO 1.先建立前缀树
        Node root = new Node();
        for (String str : wordDict) {
            char[] chs = str.toCharArray();
            Node node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                if (node.nexts[index] == null) {
                    node.nexts[index] = new Node();
                }
                node = node.nexts[index];
            }
            node.end = true;
        }

        //TODO 2.下面的动态规划
        char[] str = s.toCharArray();
        int N = str.length;
        boolean[] dp = new boolean[N + 1];
        //N位置是空字符 默认为true
        dp[N] = true;
        /*
        *TODO
        * dp[i]意义： word[i]~word[word.len-1] 能不能被分解
        * dp[N] word[N...]  -> ""  能不能够被分解
        * 对于dp[i] 依赖 dp[i+1], dp[i+2] ,.....dp[len-1]
        * 整个dp表 从右往左求 对应代码 for (int i = N - 1; i >= 0; i--)
        * */
        for (int i = N - 1; i >= 0; i--) {
            // i
            // word[i....] 能不能够被分解
            // i..i    i+1....
            // i..i+1  i+2...
            Node cur = root;
            for (int end = i; end < N; end++) {
                //TODO 得到当前路径下一个节点 判断有没有下一节点
                cur = cur.nexts[str[end] - 'a'];
                if (cur == null) {//TODO没有路 直接结束
                    break;
                }
                // 有路！
                if (cur.end) {//TODO 判读是否是某个单词的结尾 或者说来当前位置是否能形成一个串
                    /*
                    *TODO
                    * i ~ end 真的是一个有效的前缀串
                    * 那么就是判断word[end]~word[word.len-1]  能不能被分解
                    * */
                    dp[i] |= dp[end + 1];
                }
                if (dp[i]) {
                    break;
                }
            }
        }
        return dp[0];
    }

    public static int wordBreak2(String s, List<String> wordDict) {
        Node root = new Node();
        for (String str : wordDict) {
            char[] chs = str.toCharArray();
            Node node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                if (node.nexts[index] == null) {
                    node.nexts[index] = new Node();
                }
                node = node.nexts[index];
            }
            node.end = true;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[] dp = new int[N + 1];
        dp[N] = 1;
        for (int i = N - 1; i >= 0; i--) {
            Node cur = root;
            for (int end = i; end < N; end++) {
                cur = cur.nexts[str[end] - 'a'];
                if (cur == null) {
                    break;
                }
                if (cur.end) {
                    dp[i] += dp[end + 1];
                }
            }
        }
        return dp[0];
    }

}
