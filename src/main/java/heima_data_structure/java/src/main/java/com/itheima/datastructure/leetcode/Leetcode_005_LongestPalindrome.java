package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

/**
 * <h3>最长回文子串</h3>
 * <pre>
 * 中心扩散方法
 * str =  b c c b c b a b c b a f a
 * eg:
 *  b c c b c b a b c b a f a
 *              ↑
 *  两边扩散
 *  b c c b c b a b c b a f a
 *            _ ↑ _
 *  两边相同继续扩
 *  b c c b c b a b c b a f a
 *          _ _ ↑ _ _
 *  两边相同继续扩
 *  b c c b c b a b c b a f a
 *        _ _ _ ↑ _ _ _
 *  两边相同继续扩
 *  b c c b c b a b c b a f a
 *      × _ _ _ ↑ _ _ _ ×
 *  结束
 *
 * 奇数个字符构成回文子串的case:
 * 初始状态:
 *  当前字符的回文长度 = 0
 *  当前最大的回文长度 = 0
 *  b c c b c b a b c b a f a
 *  i
 *  j
 *  对于 b [0] 而言 回文就是自己
 *  得到：
 *  当前字符的回文长度 = 1
 *  当前最大的回文长度 = 1
 *  i++ j++
 *  b c c b c b a b c b a f a
 *    i
 *    j
 *  对于 c [1] 而言 左侧 != 右侧
 *  得到：
 *  当前字符的回文长度 = 1
 *  当前最大的回文长度 = 1
 *  i++ j++
 *  b c c b c b a b c b a f a
 *      i
 *      j
 *  对于 c [2] 而言 左侧 != 右侧
 *  得到：
 *  当前字符的回文长度 = 1
 *  当前最大的回文长度 = 1
 *  i++ j++
 *  b c c b c b a b c b a f a
 *        i
 *        j
 *  对于 b [3] 而言 左侧 == 右侧 扩大
 *  b c c b c b a b c b a f a
 *      i
 *          j
 *  不能再扩大了  结束
 *  得到：
 *  当前字符的回文长度 = 3
 *  当前最大的回文长度 = 3
 *  i++ j++
 *  b c c b c b a b c b a f a
 *          i
 *          j
 * 对于 c [4] 而言 左侧 == 右侧 扩大
 *  b c c b c b a b c b a f a
 *        i
 *            j
 *  左侧 != 右侧 不能再扩大了  结束
 *  得到：
 *  当前字符的回文长度 = 3
 *  当前最大的回文长度 = 3
 *
 *  b c c b c b a b c b a f a
 *            i
 *            j
 *  对于 b [5] 而言 左侧 != 右侧
 *  得到：
 *  当前字符的回文长度 = 1
 *  当前最大的回文长度 = 3
 *  b c c b c b a b c b a f a
 *              i
 *              j
 *  对于 a [6] 而言 左侧 == 右侧 扩大
 *  b c c b c b a b c b a f a
 *            i
 *                j
 * 左侧 == 右侧 再扩大
 *  b c c b c b a b c b a f a
 *          i
 *                  j
 * 左侧 == 右侧 再扩大
 *  b c c b c b a b c b a f a
 *        i
 *                    j
 * 左侧 != 右侧 不能再扩大了  结束
 * 得到：
 * 当前字符的回文长度 = 7
 * 当前最大的回文长度 = 7
 *  b c c b c b a b c b a f a
 *                i
 *                j
 * 对于 b [7] 而言 左侧 != 右侧
 * 得到：
 * 当前字符的回文长度 = 1
 * 当前最大的回文长度 = 7
 *  b c c b c b a b c b a f a
 *                  i
 *                  j
 *  对于 c [8] 而言 左侧 == 右侧 扩大
 *  b c c b c b a b c b a f a
 *                i
 *                    j
 * 左侧 == 右侧 再扩大
 *  b c c b c b a b c b a f a
 *              i
 *                      j
 * 左侧 != 右侧 不能再扩大了  结束
 * 得到：
 * 当前字符的回文长度 = 5
 * 当前最大的回文长度 = 7
 *  b c c b c b a b c b a f a
 *                    i
 *                    j
 * 对于 b [9] 而言 左侧 != 右侧
 * 得到：
 * 当前字符的回文长度 = 1
 * 当前最大的回文长度 = 7
 *  b c c b c b a b c b a f a
 *                      i
 *                      j
 * 对于 a [10] 而言 左侧 != 右侧
 * 得到：
 * 当前字符的回文长度 = 1
 * 当前最大的回文长度 = 7
 *  b c c b c b a b c b a f a
 *                        i
 *                        j
 * 对于 f [11] 而言 左侧 == 右侧 扩大
 *  b c c b c b a b c b a f a
 *                      i
 *                          j
 * 左侧 != 右侧 不能再扩大了  结束
 * 得到：
 * 当前字符的回文长度 = 3
 * 当前最大的回文长度 = 7
 *
 * 偶数个字符构成回文子串的case:
 * 也就是说中心字符有2个字符
 * 初始状态
 * i = 0 j = 1
 * 当前字符的回文长度 = 0
 * 当前最大的回文长度 = 0
 *  b c c b c b a b c b a f a
 *  i
 *    j
 * [i] != [j] 不可能作为中心字符
 * i++ j++
 * 得到：
 * 当前字符的回文长度 = 0
 * 当前最大的回文长度 = 0
 *  b c c b c b a b c b a f a
 *    i
 *      j
 * [i] == [j] 能作为中心字符
 * 并且 左侧 == 右侧 扩大
 *  b c c b c b a b c b a f a
 *  i
 *        j
 * 左侧 != 右侧 不能再扩大了  结束
 * 得到：
 * 当前字符的回文长度 = 4
 * 当前最大的回文长度 = 4
 *  b c c b c b a b c b a f a
 *      i
 *        j
 * [i] != [j] 不可能作为中心字符
 * i++ j++
 * 得到：
 * 当前字符的回文长度 = 0
 * 当前最大的回文长度 = 4
 * .....
 * 直接到最后
 * </pre>
 */
public class Leetcode_005_LongestPalindrome {
    public static void main(String[] args) {
        System.out.println(longestPalindrome("babad"));
        System.out.println(longestPalindrome("cbbd"));
        System.out.println(longestPalindrome("a"));
        System.out.println(longestPalindrome("bccbcbabcbafa"));
    }

    static String longestPalindrome(String s) {
        // 防止 如果之前有执行过该方法 left right 不为空 的情况
        left = 0;
        right = 0;
        char[] chars = s.toCharArray();
        // chars.length - 1 是因为针对 两个字符作为中心点的情况, j不超过字符数组范围
        for (int i = 0; i < chars.length - 1; i++) {
            // 一个字符作为中心点
            extend(chars, i, i);
            // 两个字符作为中心点
            extend(chars, i, i + 1);
        }
        return new String(chars, left, right - left + 1);
    }

    // 用来保存当前最大回文字符的左边界 右边界
    static int left; // i
    static int right; // j

    static void extend(char[] chars, int i, int j) {
        // 确保 i作为左边界 j作为右边界 是在数组范围
        while (i >= 0 && j < chars.length
                // 如果是回文，扩大回文范围
                && chars[i] == chars[j]) {
            i--; // -1
            j++; // 4
        }
        /**
         * 退出循环时，i和j指向的不是回文，需要还原
         * <pre>
         * eg:
         * 假设当前是
         *  b c c b c b a b c b a f a
         *  i
         *        j
         * 因为 [i] == [j] 那么还是会进入循环
         * 但是 i-- j++ 之后 i超出了数组范围
         * 所以需要还原
         * </pre>
         */
        i++;
        j--;
        if (j - i > right - left) {
            left = i;
            right = j;
        }
    }
}
