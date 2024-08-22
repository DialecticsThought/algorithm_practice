/**
 * @Description
 * @Author veritas
 * @Data 2024/8/12 17:35
 * <pre>
 * 这个问题转换为计算两个字符串的最长公共子序列（LCS）的长度。然后，将两个字符串的长度减去这个 LCS 的长度，得到需要删除的字符数
 *  考虑所有选择：
 * 如果 word1[i-1] == word2[j-1]：
 *      这两个字符相同，则它们是 LCS 的一部分，因此我们可以继续计算剩余部分的 LCS，递归调用为 lcs(i-1, j-1) + 1。
 * 如果 word1[i-1] != word2[j-1]：则我们有两种选择：
 *      忽略 word1[i-1]，继续比较 word1[0:i-2] 和 word2[0:j-1]，递归调用 lcs(i-1, j)。
 *      忽略 word2[j-1]，继续比较 word1[0:i-1] 和 word2[0:j-2]，递归调用 lcs(i, j-1)
 * </pre>
 * <pre>
 *
 * </pre>
 */
public class Code_583_DeleteOperationForTwoStrings {

    private int lcs(String word1, String word2, int i, int j) {
        // 基本情况：如果任何一个字符串为空，LCS 长度为 0
        if (i == 0 || j == 0) {
            return 0;
        }

        // 如果最后一个字符相同，将其加入 LCS
        if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
            return 1 + lcs(word1, word2, i - 1, j - 1);
        } else {
            // 否则，分别尝试跳过 word1 或 word2 的当前字符，取两者中的较大值
            return Math.max(lcs(word1, word2, i - 1, j), lcs(word1, word2, i, j - 1));
        }
    }
}
