package code_for_great_offer.class31;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description
 * @Author veritas
 * @Data 2023/3/1 13:34
 *TODO
 * "给定一个非空字符串 s 和一个包含非空单词列表的字典 wordDict，
 * 在字符串中增加空格来构建一个句子，使得句子中所有的单词都在词典中。返回所有这些可能的句子。
 * 说明：
 * 分隔时可以重复使用字典中的单词。
 * 你可以假设字典中没有重复的单词。
 * 示例 1：
 * 输入:
 * s = "catsanddog"
 * wordDict = ["cat", "cats", "and", "sand", "dog"]
 * 输出:
 * [
 *   "cats and dog",
 *   "cat sand dog"
 * ]
 * 示例 2：
 * 输入
 * s = "pineapplepenapple"
 * wordDict = ["apple", "pen", "applepen", "pine", "pineapple"]
 * 输出:
 * [
 *   "pine apple pen apple",
 *   "pineapple pen apple",
 *   "pine applepen apple"
 * ]
 * 解释: 注意你可以重复使用字典中的单词。
 */
public class InterView2 {
    public static void main(String[] args) {
        String path = "";
        ArrayList<String> result = new ArrayList<>();
        List<String> wordDict = new ArrayList<>();
        Collections.addAll(wordDict, "apple", "pen", "applepen", "pine", "pineapple", "cat", "cats", "and", "sand", "dog");
        String s = "pineapplepenapple";
        String s2 = "catsanddog";
        process(s, wordDict, 0, result, path);
        System.out.println(result);
        process(s2, wordDict, 0, result, path);
        System.out.println(result);
    }

    /*
     *TODO
     * 从左往右的尝试模型 暴力递归
     * bool f(s,wordDict,i) 一个可变参数 2个固定参数
     * 表示来到i位置，s[i]~s[s.len-1]的单词能否被wordList的单词分解
     * i位置单独成一单词 如果wordList有这个单词 那么执行 f(s,wordDict,i+1)
     * i~i+1位置成一单词 如果wordList有这个单词 那么执行 f(s,wordDict,i+2)
     * ...
     * 直到i~len-1位置成一单词 如果wordList有这个单词 那么执行 f(s,wordDict,len)
     * */
    public static void process(String s, List<String> wordDict, int i, List<String> result, String path) {
        if (i == s.length()) {
            result.add(path);
            return;
        }
        //TODO 枚举
        for (int count = i; count < s.length(); count++) {
            if (wordDict.contains(s.substring(i, count + 1))) {
                //TODO 备份
                String pathBak = path;
                path = path + " " + s.substring(i, count + 1);
                //TODO 进入下一个节点
                process(s, wordDict, count + 1, result, path);
                //TODO dfs的还原现场
                path = pathBak;
            }
        }
        //TODO 向上返回
        return;
    }
}
