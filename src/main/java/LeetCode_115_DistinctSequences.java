/**
 * @Description
 * @Author veritas
 * @Data 2025/3/5 16:33
 */
public class LeetCode_115_DistinctSequences {
    public static int MOD = 1000_000_007;

    public int numDistinct(String s, String t) {
        /**
         * 在递归中，我们允许 i 和 j 达到 s.length() 或 t.length() 的值来处理边界条件。具体来说：
         * 当 i == s.length() 时，表示 s 已经遍历完毕。
         * 当 j == t.length() 时，表示 t 已经匹配完毕（base case，返回 1）。
         *
         * 为了能在 memo 数组中存储这些状态（包括 i 或 j 等于字符串长度时的状态），我们需要数组的大小为 s.length() + 1 和 t.length() + 1。
         * 这样索引范围就是 0 到 s.length() 和 0 到 t.length()，能涵盖所有可能的状态
         */
        int[][] cache = new int[s.length() + 1][t.length() + 1];
        for (int i = 0; i <= s.length(); i++) {
            for (int j = 0; j <= t.length(); j++) {
                cache[i][j] = -1;
            }
        }
        return distinctSequences(s, t, 0, 0, cache);
    }

    /**
     * @param s
     * @param t
     * @param i 遍历到s哪一个位置
     * @param j 遍历到t哪一个位置
     * @return
     */
    public int distinctSequences(String s, String t, int i, int j) {
        // base case
        // 被比对的字符串是t
        // 如果t已经被遍历完了
        // 说明在t中存在一个s的子序列
        if (j == t.length()) {
            return 1;
        }
        // 如果s被遍历完了 但是 t没有被遍历完，说明：在t中不存在s的子序列
        if (i == s.length()) {
            return 0;
        }
        // 当前层(位置)开始 有多少方法 能让t剩下的字符串(t[j]~t[t.length-1]) 被s的剩下的字符串构成
        int counter = 0;
        // 每一次比对 都有两种选择
        // 选择1：比对 s[i] 和 t[j]  前提：s[i] == t[j]
        if (s.charAt(i) == t.charAt(j)) {
            // 递归返回的方案数表示使用 s[i] 的情况下后续匹配的方式
            counter = counter + distinctSequences(s, t, i + 1, j + 1);
            //取模
            counter = counter % MOD;
        }
        // 选择2: 不必对 s[i] 和 t[j] ，让s[i+1]去做相同的选择
        // 递归返回的方案数表示不使用 s[i] 的情况下的匹配方式
        counter = counter + distinctSequences(s, t, i + 1, j);
        //取模
        counter = counter % MOD;
        // 返回当前层的值给上一层
        return (int) counter;
    }

    /**
     * @param s
     * @param t
     * @param i     遍历到s哪一个位置
     * @param j     遍历到t哪一个位置
     * @param cache 缓存数组，memo[i][j] 表示 s[i...] 与 t[j...] 的匹配方案数
     * @return
     */
    public int distinctSequences(String s, String t, int i, int j, int[][] cache) {
        // base case
        // 被比对的字符串是t
        // 如果t已经被遍历完了
        // 说明在t中存在一个s的子序列
        if (j == t.length()) {
            return 1;
        }
        // 如果s被遍历完了 但是 t没有被遍历完，说明：在t中不存在s的子序列
        if (i == s.length()) {
            return 0;
        }

        // 找缓存
        if (cache[i][j] != 0) {
            return cache[i][j];
        }

        // 当前层(位置)开始 有多少方法 能让t剩下的字符串(t[j]~t[t.length-1]) 被s的剩下的字符串构成
        int counter = 0;
        // 每一次比对 都有两种选择
        // 选择1：比对 s[i] 和 t[j]  前提：s[i] == t[j]
        if (s.charAt(i) == t.charAt(j)) {
            // 递归返回的方案数表示使用 s[i] 的情况下后续匹配的方式
            counter = counter + distinctSequences(s, t, i + 1, j + 1, cache);
            // 取模
            counter = counter % MOD;
        }
        // 选择2: 不必对 s[i] 和 t[j] ，让s[i+1]去做相同的选择
        // 递归返回的方案数表示不使用 s[i] 的情况下的匹配方式
        counter = counter + distinctSequences(s, t, i + 1, j, cache);
        // 取模
        counter = counter % MOD;

        // 缓存
        cache[i][j] = counter;

        // 返回当前层的值给上一层
        return (int) counter;
    }
}
