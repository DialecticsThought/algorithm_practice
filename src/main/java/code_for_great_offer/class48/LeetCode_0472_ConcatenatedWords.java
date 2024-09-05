package code_for_great_offer.class48;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给你一个 不含重复 单词的字符串数组 words ，请你找出并返回 words 中的所有 连接词 。
 * 连接词 定义为：一个完全由给定数组中的至少两个较短单词（不一定是不同的两个单词）组成的字符串。
 * 示例 1：
 * 输入：words = ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]
 * 输出：["catsdogcats","dogcatsdog","ratcatdogcat"]
 * 解释："catsdogcats" 由 "cats", "dog" 和 "cats" 组成;
 * "dogcatsdog" 由 "dog", "cats" 和 "dog" 组成;
 * "ratcatdogcat" 由 "rat", "cat", "dog" 和 "cat" 组成。
 * 示例 2：
 * 输入：words = ["cat","dog","catdog"]
 * 输出：["catdog"]
 */
public class LeetCode_0472_ConcatenatedWords {

    public static class TrieNode {
        public boolean end;
        public TrieNode[] nexts;

        public TrieNode() {
            end = false;
            nexts = new TrieNode[26];
        }
    }

    public static void insert(TrieNode root, char[] s) {
        int path = 0;
        for (char c : s) {
            path = c - 'a';
            if (root.nexts[path] == null) {
                root.nexts[path] = new TrieNode();
            }
            root = root.nexts[path];
        }
        root.end = true;
    }

    // 方法1：前缀树优化
    public static List<String> findAllConcatenatedWordsInADict1(String[] words) {
        List<String> ans = new ArrayList<>();
        if (words == null || words.length < 3) {
            return ans;
        }
        // 字符串数量 >= 3个
        //TODO 这行代码的意思就是根据字符串长度排序
        Arrays.sort(words, (str1, str2) -> str1.length() - str2.length());
        TrieNode root = new TrieNode();
        for (String str : words) {
            char[] s = str.toCharArray(); // "" 题目要求
            /**
             * 如果字符串能被分解就加入ans
             * 否则插入前缀树
             */
            if (s.length > 0 && split1(s, root, 0)) {
                ans.add(str);
            } else {
                insert(root, s);
            }
        }
        return ans;
    }

    /**
     * 字符串s[i....]能不能被分解？
     * 之前的元件，全在前缀树上，r就是前缀树头节点
     * 从左往右的暴力递归的模型
     *
     * @param s
     * @param r
     * @param i
     * @return
     */
    public static boolean split1(char[] s, TrieNode r, int i) {
        boolean ans = false;
        if (i == s.length) { // 没字符了！
            ans = true;
        } else { // 还有字符
            TrieNode c = r;
            /**
             * s[i.....] 此时来到s的i位置
             * s[i]~str[end]作前缀，看看是不是一个元件
             * 是 => f(s,r,end+1)...
             */

            for (int end = i; end < s.length; end++) {
                int path = s[end] - 'a';//这个表示从当前节点开始要走到下一个节点的路径
                if (c.nexts[path] == null) {//后续不用再尝试了
                    break;
                }
                c = c.nexts[path];
                //c.end 这个点真的是某一个字符串的结尾 并且s[end+1]开始往后的所有字符串都能被分解
                if (c.end && split1(s, r, end + 1)) {
                    ans = true;
                    break;
                }
            }
        }
        return ans;
    }

    // 提前准备好动态规划表
    public static int[] dp = new int[1000];

    // 方法二：前缀树优化 + 动态规划优化
    public static List<String> findAllConcatenatedWordsInADict2(String[] words) {
        List<String> ans = new ArrayList<>();
        if (words == null || words.length < 3) {
            return ans;
        }
        Arrays.sort(words, (str1, str2) -> str1.length() - str2.length());
        TrieNode root = new TrieNode();
        for (String str : words) {
            char[] s = str.toCharArray();
            Arrays.fill(dp, 0, s.length + 1, 0);
            if (s.length > 0 && split2(s, root, 0, dp)) {
                ans.add(str);
            } else {
                insert(root, s);
            }
        }
        return ans;
    }

    public static boolean split2(char[] s, TrieNode r, int i, int[] dp) {
        if (dp[i] != 0) {
            return dp[i] == 1;
        }
        boolean ans = false;
        if (i == s.length) {
            ans = true;
        } else {
            TrieNode c = r;
            for (int end = i; end < s.length; end++) {
                int path = s[end] - 'a';
                if (c.nexts[path] == null) {
                    break;
                }
                c = c.nexts[path];
                if (c.end && split2(s, r, end + 1, dp)) {
                    ans = true;
                    break;
                }
            }
        }
        dp[i] = ans ? 1 : -1;
        return ans;
    }

}
