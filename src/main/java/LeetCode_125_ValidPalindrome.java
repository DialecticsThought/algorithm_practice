/**
 * @Description
 * @Author veritas
 * @Data 2025/3/7 11:35
 */
public class LeetCode_125_ValidPalindrome {

    public boolean isPalindrome(String s) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                sb.append(Character.toLowerCase(c));
            }
        }
        String string = sb.toString();
        int i = 0;
        while (i < string.length() / 2) {
            if (string.charAt(i) != string.charAt(string.length() - 1 - i)) {
                return false;
            }
            i++; // 别忘了增加i的值
        }
        return true;
    }

}
