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
     *                  [a]+minCuts(1)  [aa]+minCuts(2)  [aab]
     *                  是回文 /      \ 不是回文
     *              [a]+minCuts(2)    [ab]
     *              是回文 /    \ 是回文
     *          [b]+minCuts(3)  [bcb]+minCuts(4)
     *          是回文 /       \ 不是回文
     *      [c]+minCuts(4)     [cb]
     *     是回文 /        \ 不是回文
     *   [b]+minCuts(5)  [bd]
     *  是回文/        \是回文
     *  [d]+minCuts(6) [dd]+minCuts(7)
     *    / 是回文
     *  [d]+minCuts(7)
     * </pre>

     * @param s
     * @return
     */
    public int minCut(String s) {
        return minCuts(s, 0);
    }

    private int minCuts(String s, int start) {
        if (start == s.length()) {
            return 0; // 如果已经到达字符串末尾，不需要再切割
        }

        int minCuts = Integer.MAX_VALUE;

        for (int end = start; end < s.length(); end++) {
            /**
             * 如果 s[start~end] 是回文
             * 尝试s[start~end+1]是否是回文
             * 否则直接结束
             */
            if (isPalindrome(s, start, end)) {
                // 计算剩余部分的最小切割次数
                int cuts = 1 + minCuts(s, end + 1);
                // 取最小的切割次数
                minCuts = Math.min(minCuts, cuts);
            }
        }
        return minCuts;
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
