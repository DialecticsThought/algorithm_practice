package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * code_for_great_offer.class17
 * TODO
 * 测试链接 : https://leetcode.com/problems/palindrome-pairs/
 * 假设 有一个word[i] = aaab,设word[i]=str
 * 按照位置截取
 * 前缀s
 * 只截取前缀str[0]~str[0] 也就是"a" 是回文 ， 剩下部分是aab，找其逆序baa是否存在于数组，有的话，可以拼接在前成baaaaab
 * 只截取前缀str[0]~str[1] 也就是"aa" 是回文， 剩下部分是ab，找其逆序ba是否存在于数组，有的话，可以拼接在前成baaaab
 * 只截取前缀str[0]~str[2] 也就是"aaa" 是回文， 剩下部分是b，找其逆序b是否存在于数组，有的话，可以拼接在前成baaab
 * 后缀
 * 只截取后缀str[i]~str[i] 也就是"b" 是回文， 剩下部分是aaa，找其逆序aaa是否存在于数组，有的话，可以拼接在后成aaabaaa
 * 只截取后缀str[i-1]~str[i] 也就是"ab" 不是回文
 * 只截取后缀str[i-2]~str[i] 也就是"aab" 不是回文
 * 如果str本身是回文，那么就拼空字符
 * 不要忘记str本身的逆序，str="abcd" 那么在数组中找到dcba，
 *  可以往前拼接dcbaabcd，往后拼接abcddcba
 * word数组中可以有相同的字符串
 * 假设word[17]=aaaa,word[23]=aa,word[6]=a,word[46]=aaa,str=word[17]
 * 前缀s
 * 只截取前缀str[0]~str[0] 也就是"a" 是回文 ， 剩下部分是aaa，找其逆序aaa是否存在于数组，有的话，可以拼接在前成aaaaaaa
 *  生成记录[46,17] 46在前，17在后面 拼接成aaaaaaa
 * 只截取前缀str[0]~str[1] 也就是"aa" 是回文， 剩下部分是aa，找其逆序aa是否存在于数组，有的话，可以拼接在前成aaaaaa
 *  生成记录[23,17]
 * 只截取前缀str[0]~str[2] 也就是"aaa" 是回文， 剩下部分是a，找其逆序a是否存在于数组，有的话，可以拼接在前成aaaaa
 *  生成记录[6,17]
 * 后缀
 * 只截取后缀str[i]~str[i] 也就是"b" 是回文， 剩下部分是aaa，找其逆序aaa是否存在于数组，有的话，可以拼接在后成aaaaaaa
 *  生成记录[17,46]
 * .....
 * 针对word[]的每一个str都生成一个对应的数组
 * str = aaabaaa 那么生成一个length=7的arr
 * arr[0]表示str[0]~str[0]是不是回文
 * arr[1]表示str[0]~str[1]是不是回文
 * ...
 * arr[6]表示str[0]~str[6]是不是回文
 * 上面的操作可以用马拉车算法优化
 */
public class LeetCode_336_PalindromePairs {

    public static List<List<Integer>> palindromePairs(String[] words) {
        /**
         * 一个字符串，对应的位置
         */
        HashMap<String, Integer> wordset = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            wordset.put(words[i], i);
        }
        List<List<Integer>> res = new ArrayList<>();
        //{ [6,23] 、 [7,13] } 表示 6位置的str和23位置的str拼接能生成回文 7位置的str和13位置的str拼接能生成回文
        for (int i = 0; i < words.length; i++) {
            // i words[i]
            // findAll(字符串，在i位置，wordset) 返回所有生成的结果返回
            res.addAll(findAll(words[i], i, wordset));
        }
        return res;
    }

    public static List<List<Integer>> findAll(String word, int index, HashMap<String, Integer> words) {
        List<List<Integer>> res = new ArrayList<>();
        String reverse = reverse(word);
        // 有空字符串的话得到位置
        Integer rest = words.get("");
        /**
         * 表示如果word[]存在空字符串，并且当前word逆序和原始word相同
         * 那么再word前面加上"" 在word后面加上""
         */
        if (rest != null && rest != index && word.equals(reverse)) {
            addRecord(res, rest, index);
            addRecord(res, index, rest);
        }
        int[] rs = manacherss(word);
        int mid = rs.length >> 1;
        //检查前缀
        for (int i = 1; i < mid; i++) {
            if (i - rs[i] == -1) {
                rest = words.get(reverse.substring(0, mid - i));
                if (rest != null && rest != index) {
                    addRecord(res, rest, index);
                }
            }
        }
        //检查后缀
        for (int i = mid + 1; i < rs.length; i++) {
            if (i + rs[i] == rs.length) {
                rest = words.get(reverse.substring((mid << 1) - i));
                if (rest != null && rest != index) {
                    addRecord(res, index, rest);
                }
            }
        }
        return res;
    }

    public static void addRecord(List<List<Integer>> res, int left, int right) {
        List<Integer> newr = new ArrayList<>();
        newr.add(left);
        newr.add(right);
        res.add(newr);
    }

    public static int[] manacherss(String word) {
        char[] mchs = manachercs(word);
        int[] rs = new int[mchs.length];
        int center = -1;
        int pr = -1;
        for (int i = 0; i != mchs.length; i++) {
            rs[i] = pr > i ? Math.min(rs[(center << 1) - i], pr - i) : 1;
            while (i + rs[i] < mchs.length && i - rs[i] > -1) {
                if (mchs[i + rs[i]] != mchs[i - rs[i]]) {
                    break;
                }
                rs[i]++;
            }
            if (i + rs[i] > pr) {
                pr = i + rs[i];
                center = i;
            }
        }
        return rs;
    }

    public static char[] manachercs(String word) {
        char[] chs = word.toCharArray();
        char[] mchs = new char[chs.length * 2 + 1];
        int index = 0;
        for (int i = 0; i != mchs.length; i++) {
            mchs[i] = (i & 1) == 0 ? '#' : chs[index++];
        }
        return mchs;
    }

    public static String reverse(String str) {
        char[] chs = str.toCharArray();
        int l = 0;
        int r = chs.length - 1;
        while (l < r) {
            char tmp = chs[l];
            chs[l++] = chs[r];
            chs[r--] = tmp;
        }
        return String.valueOf(chs);
    }

}
