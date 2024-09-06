package code_for_great_offer.class32;

/*
* 给你一个字符串columnTitle ，表示 Excel 表格中的列名称。返回 该列名称对应的列序号 。
例如：
A -> 1
B -> 2
C -> 3
...
Z -> 26
AA -> 27
AB -> 28
...

链接：https://leetcode.cn/problems/excel-sheet-column-number
* 示例 1:
* 输入: columnTitle = "A"
* 输出: 1
* 示例2:
* 输入: columnTitle = "AB"
* 输出: 28
* 示例3:
* 输入: columnTitle = "ZY"
* 输出: 701
* */
public class LeetCode_0171_ExcelSheetColumnNumber {
    /*
     *TODO 类似于26进制 但不是26进制 因为 每一位不能表示0  但是能表示26
     * 真正的26进制只能表示 0 ~ 25
     * 每一个位 可表示 1 ~ 26
     * B C A => 2 * 26 ^ 2 +3 * 26 ^ 1 + 1 * 26 ^ 0
     * */
    // 这道题反过来也要会写
    public static int titleToNumber(String s) {
        char[] str = s.toCharArray();
        int ans = 0;
        for (int i = 0; i < str.length; i++) {
            ans = ans * 26 + (str[i] - 'A') + 1;
        }
        return ans;
    }

}
