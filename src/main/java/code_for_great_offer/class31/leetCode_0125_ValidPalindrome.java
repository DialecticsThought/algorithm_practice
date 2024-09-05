package code_for_great_offer.class31;

/*
 * 如果在将所有大写字符转换为小写字符、并移除所有非字母数字字符之后，
 * 短语正着读和反着读都一样。则可以认为该短语是一个 回文串 。
 * 字母和数字都属于字母数字字符。
 * 给你一个字符串 s，如果它是 回文串 ，返回 true ；否则，返回 false 。
 * 示例 1：
 * 输入: s = "A man, a plan, a canal: Panama"
 * 输出：true
 * 解释："amanaplanacanalpanama" 是回文串。
 * 示例 2：
 * 输入：s = "race a car"
 * 输出：false
 * 解释："raceacar" 不是回文串。
 * 示例 3：
 * 输入：s = " "
 * 输出：true
 * 解释：在移除非字母数字字符之后，s 是一个空字符串 "" 。
 * 由于空字符串正着反着读都一样，所以是回文串。
 * */
public class leetCode_0125_ValidPalindrome {

    // 忽略空格、忽略大小写 -> 是不是回文
    // 数字不在忽略大小写的范围内
    public static boolean isPalindrome(String s) {
        //base case
        if (s == null || s.length() == 0) {
            return true;
        }
        char[] str = s.toCharArray();
        /*
         *TODO
         * 分成左右2个指针
         * 2个指针同时相向而行，
         * 如果指针落在空格处，则前进一格 直到不是空格
         * */
        int L = 0;
        int R = str.length - 1;
        while (L < R) {
            //TODO 判断 L和R分别指先多个char是否 英文（大小写） + 数字
            if (validChar(str[L]) && validChar(str[R])) {
                if (!equal(str[L], str[R])) {
                    return false;
                }
                //TODO L == R 那么 指针向前一步
                L++;
                R--;
            } else {//TODO 说明有一个指针指向的char不是 英文字符或数字
                if (validChar(str[L])) {
                    //TODO L当前指向的是英文字符或数字 L不动
                    L += 0;
                } else {
                    L += 1;
                }
                if (validChar(str[R])) {
                    //TODO R当前指向的是英文字符或数字 R不动
                    R += 0;
                } else {
                    R += 1;
                }
            }
        }
        return true;
    }

    public static boolean validChar(char c) {
        return isLetter(c) || isNumber(c);
    }

    public static boolean equal(char c1, char c2) {
        if (isNumber(c1) || isNumber(c2)) {
            return c1 == c2;
        }
        // a  A   32
        // b  B   32
        // c  C   32
        return (c1 == c2) || (Math.max(c1, c2) - Math.min(c1, c2) == 32);
    }

    //TODO 判断当前字符是否是a-z A-Z
    public static boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    //TODO 判断当前字符是否是1-9
    public static boolean isNumber(char c) {
        return (c >= '0' && c <= '9');
    }

}
