package code_for_great_offer.class28;

import java.util.ArrayList;
import java.util.List;

public class LeetCode_0017_LetterCombinationsOfAPhoneNumber {

    private static final String[] KEYPAD = {
            "",    // 0
            "",    // 1
            "abc", // 2
            "def", // 3
            "ghi", // 4
            "jkl", // 5
            "mno", // 6
            "pqrs",// 7
            "tuv", // 8
            "wxyz" // 9
    };

    /**
     * eg: digits = "23"
     * <pre>
     *                          []
     *  2 =>            /        |       \
     *              a           b            c
     *  3 =>    /   |   \    /   |   \     /   |   \
     *         ad  ae  af   bd  be  bf   cd   ce   cf
     *
     * </pre>
     * @param result  用于存储所有生成的字母组合的列表
     * @param current 用于构建当前正在生成的字母组合
     * @param digits  输入的数字字符串（例如 "23"）
     * @param index   当前处理的 `digits` 字符串中的位置，用于跟踪递归深度
     */
    private static void backtrack(String digits, int index, List<String> result, StringBuilder current) {
        // base case 如果当前的索引 `index` 等于输入数字字符串的长度
        if (index == digits.length()) {
            // 说明已经生成了一个完整的字母组合
            result.add(current.toString());
            // 递归返回上一层节点
            return;
        }
        // 获取当前数字所对应的字母映射
        String letters = KEYPAD[digits.charAt(index) - '0'];
        // 转成对应字符串数组
        char[] charArray = letters.toCharArray();

        for (char curChar : charArray) {
            // 将当前字母添加到 `current` 中，形成新的组合路径
            current.append(curChar);
            // 递归调用，处理下一个数字
            backtrack(digits, index + 1, result, current);
            // 回溯，删除最后一个字母，尝试其他可能的字母
            current.deleteCharAt(current.length() - 1);
        }
    }

    public static char[][] phone = {
            {'a', 'b', 'c'}, // 2按键    是phone的0位置
            {'d', 'e', 'f'}, // 3按键    是phone的1位置
            {'g', 'h', 'i'}, // 4按键    是phone的2位置
            {'j', 'k', 'l'}, // 5按键    是phone的3位置
            {'m', 'n', 'o'}, // 6按键	   是phone的4位置
            {'p', 'q', 'r', 's'}, // 7
            {'t', 'u', 'v'},   // 8
            {'w', 'x', 'y', 'z'}, // 9
    };

    // "23"
    public static List<String> letterCombinations(String digits) {
        List<String> ans = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return ans;
        }
        char[] str = digits.toCharArray();
        char[] path = new char[str.length];
        process(str, 0, path, ans);
        return ans;
    }

    /**
     * 当前来到 str[index]
     * 那么 str[0]~str[index]是拨过的部分 会存到 path里面 也可以理解为 之前做的决策
     * 来到str[str.len]的时候 就会把 path内容拷贝到res中
     * 假设 共拨了 “23”
     * index =0 str[index]='2' => 查出2节点的分支： 'a', 'b', 'c'
     * 走 'a' 分支 来到  index=1 str[index]='3' 节点
     */
    public static void process(char[] str, int index, char[] path, List<String> ans) {
        //TODO base case 来到最后一个char
        if (index == str.length) {
            //TODO 把path放入res
            ans.add(String.valueOf(path));
        } else {
            //TODO 得到 该节点的支路
            char[] cands = phone[str[index] - '2'];
            //TODO 深度优先遍历 当前节点的 分支 去下一个节点
            for (char cur : cands) {
                //TODO path的index位置 被赋值
                path[index] = cur;
                //TODO 来到 str[index+1]
                process(str, index + 1, path, ans);
            }
        }
    }
}
