/**
 * @Description
 * @Author veritas
 * @Data 2024/8/16 12:04
 * <h1>
 * 范围尝试模型
 * </h1>
 */
public class Code_132_PalindromePartitioningII {
    /**
     * <pre>
     * Str = "aabcbdd"
     *                                      [0]
     *                      是回文  /    是回文 |         \ 不是回文
     *                  [a]+f(1)  [aa]+f(2)  [aab]
     *                  是回文 /      \ 不是回文
     *              [a]+f(2)    [ab]
     *              是回文 /    \ 是回文
     *          [b]+f(3)  [bcb]+f(4)
     *          是回文 /       \ 不是回文
     *      [c]+f(4)     [cb]
     *     是回文 /        \ 不是回文
     *   [b]+f(5)  [bd]
     *  是回文/        \是回文
     *  [d]+f(6) [dd]+f(7)
     *    / 是回文
     *  [d]+f(7)
     * </pre>
     * @param s
     * @return
     */
    public int minCut(String s) {
        return f(s, 0);
    }

    private int f(String s, int start) {
        if (start == s.length()) {
            return 0; // 如果已经到达字符串末尾，不需要再切割
        }

        int f = Integer.MAX_VALUE;

        for (int end = start; end < s.length(); end++) {
            /**
             * 如果 s[start~end] 是回文
             * 尝试s[start~end+1]是否是回文
             * 否则直接结束
             */
            if (isPalindrome(s, start, end)) {
                // 计算剩余部分的最小切割次数
                int cuts = 1 + f(s, end + 1);
                // 取最小的切割次数
                f = Math.min(f, cuts);
            }
        }
        return f;
    }

    // 辅助函数：判断子字符串 s[start:end] 是否是回文
    private boolean isPalindrome(String s, int start, int end) {
        while (start < end) {
            if (s.charAt(start) != s.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }
}
