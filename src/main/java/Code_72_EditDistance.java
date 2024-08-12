/**
 * @Description <pre>
 * 在编辑距离问题中，插入和替换操作涉及到将一个字符匹配或替换为目标字符串中的字符。我们来看具体原因：
 * 1. 插入操作
 * 定义：插入操作意味着在 word1 中插入一个字符，使其与 word2 的当前字符匹配。
 * 原因：当我们进行插入操作时，目的是让 word1 的当前状态通过增加一个字符来匹配 word2 的当前字符。
 * 因为 word2[j-1] 是我们想要匹配的字符，所以插入的字符应该是 word2[j-1]，这样才能使两个字符串在当前位置匹配。
 * 递归关系：
 * 插入后，我们继续处理 word1 和 word2，但现在 word2 的指针 j 应该向前移动一位，
 * 而 word1 的指针 i 保持不变，因为我们在 word1 的当前位置插入了一个字符。
 * 递归调用为 minDistance(i, j-1)。
 * 举例：
 * 假设 word1 = "hor"，word2 = "horse"，我们在 word1 的末尾插入 word2[j-1] = 's'，得到 "hors"，然后继续匹配 word2 的剩余部分。
 * 2. 替换操作
 * 定义：替换操作意味着将 word1 中的一个字符替换为 word2 中的对应字符，使它们匹配。
 * 原因：当 word1[i-1] 与 word2[j-1] 不同时，我们需要进行替换操作，使得 word1[i-1] 变为 word2[j-1]，这样它们在当前字符上匹配。
 * 因此，替换操作的目标字符是 word2[j-1]。
 * 递归关系：
 * 替换后，我们将 word1 和 word2 的指针都向前移动一位，因为在这一位上已经完成了匹配，接下来需要处理剩下的部分。
 * 递归调用为 minDistance(i-1, j-1)。
 * 举例：
 * 假设 word1 = "hor"，word2 = "hos"，我们将 word1 中的 'r' 替换为 word2 中的 's'，得到 "hos"，然后继续匹配剩余的部分。
 * 3. 其他操作（删除）
 * 删除操作的目标是将 word1[i-1] 删除，从而尝试通过删除操作使得 word1 与 word2 的剩余部分匹配。
 * 递归调用为 minDistance(i-1, j)，表示删除当前 word1 的字符后，继续处理剩下的部分。
 * </pre>
 * @Author veritas
 * @Data 2024/8/12 16:34
 */
public class Code_72_EditDistance {
    /**
     * <pre>
     * d表示删除  i表示插入  r表示replace  2表示 "to"
     *                                                 (5,3)
     *                          /                     |                                   \
     *                   (4,3)                        (5,2)                               (4,2)
     *                   d'e'                          i's'                                 r'e'2's'
     *              "hors"和"ros"                     "horse"和"ro"                           "hors"和"ro"
     *               /    |      \                /        |           \                      /   |    \
     *         (3,3)     (4,2)    (3,2)        (4,2)       (5,1)        (4,1)              (3,2)  (4,1)  (3,1)
     *          d's'     i's'     r's'2'o'      d'e'       i'o'      r'e'2'o'            d's'      i'o'       r's'2'r'
     * "hor"和"ros" "hors"和"ro" "hor"和"ro"   "hors"和"ro" "horse"和"r" "hors"和"r"   "hor"和"ro"  "hors"和"r" "hor"和"r"
     *     /    |   \             /  |  \                /    | \                                /  |  \
     *   (2,3) (3,2) (2,2)       (2,2)(3,1)(2,1)        (4,1) (5,0) (4,0)                       (3,1)(4,0)(3,0)
     * d'r'     i's'  r'r'2'o'    d'r' i'o' r'r'2'r'     d'e'   i'r'  r'e'2'r'                  d's'   i'r'  r's'2'r'
     *"ho"和"ros"                "ho"和"ro"            "hors"和"r"                            "hor"和"r"
     *       "hor"和"ro"              "hor"和"r"              "horse"和""                             "hors"和""
     *               "ho"和"ro"               "ho"和"r"                "hors"和""                            "hor"和""
     * </pre>
     *
     * @param word1
     * @param word2
     * @return
     */
    public static int minDistance(String word1, String word2) {
        return minDistance(word1, word2, word1.length(), word2.length());
    }

    /**
     * <pre>
     * 赝本对应模型，需要对2个数组的最后一个位置做操作
     * 我们定义一个递归函数 minDistance(i, j)，表示将 word1[0:i-1] 转换成 word2[0:j-1] 所需要的最少操作数。我们从这两个字符串的末尾开始向前递归处理：
     * 基本情况：
     * 如果 i == 0，即 word1 是空字符串，则只能通过插入 j 个字符来匹配 word2，操作数为 j。
     * 如果 j == 0，即 word2 是空字符串，则只能通过删除 i 个字符来匹配 word1，操作数为 i。
     * 递归情况：
     *      如果 word1[i-1] == word2[j-1]：两个字符相同，不需要任何操作，我们只需处理剩下的部分，即 minDistance(i-1, j-1)。
     *      如果 word1[i-1] != word2[j-1]：有三种操作可以考虑：
     *          插入：在 word1 的当前字符前插入 word2[j-1]，然后处理 minDistance(i, j-1)。
     *          删除：删除 word1[i-1]，然后处理 minDistance(i-1, j)。
     *          替换：将 word1[i-1] 替换为 word2[j-1]，然后处理 minDistance(i-1, j-1)。
     * 我们取这三种操作中的最小值，并加上一步操作数。
     * </pre>
     *
     * @param word1
     * @param word2
     * @param i
     * @param j
     * @return
     */
    public static int minDistance(String word1, String word2, int i, int j) {
        // 基本情况
        if (i == 0) return j;
        if (j == 0) return i;

        // 如果最后一个字符相同，跳过它们
        if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
            return minDistance(word1, word2, i - 1, j - 1);
        } else {
            // 考虑三种操作：插入、删除、替换，取其中最小值加1
            // 插入操作
            int insert_case = minDistance(word1, word2, i, j - 1);
            // 删除操作
            int delete_case = minDistance(word1, word2, i - 1, j);
            // 替换操作
            int replace_case = minDistance(word1, word2, i - 1, j - 1);

            return 1 + Math.min(insert_case, Math.min(delete_case, replace_case));
        }
    }

    public static void main(String[] args) {
        String word1 = "horse";
        String word2 = "ros";
        System.out.println("Minimum Edit Distance: " + minDistance(word1, word2));  // 输出 3
    }
}
