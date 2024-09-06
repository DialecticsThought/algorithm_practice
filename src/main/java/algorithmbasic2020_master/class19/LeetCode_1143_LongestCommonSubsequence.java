package algorithmbasic2020_master.class19;


/**
 * 链接：https://leetcode.cn/problems/longest-common-subsequence/
 * 样本对应模型 就以最后一个元素为基础，讨论可能性  ☆☆☆☆☆☆☆☆☆
 * <pre>
 * TODO
 * 1.定义子问题
 *      对于字符串 text1 和 text2，我们定义一个递归函数 lcs(i, j) 来表示 text1[0:i-1] 和 text2[0:j-1] 的最长公共子序列的长度。
 * 2.考虑所有选择
 * 如果 text1[i-1] == text2[j-1]，
 *      那么这个字符属于最长公共子序列的一部分，我们可以将问题缩小为 lcs(i-1, j-1) + 1。
 * 如果 text1[i-1] != text2[j-1]，那么我们有两种可能的情况：
 *      忽略 字符 text1[i-1]，递归求解 lcs(i-1, j)。
 *      忽略 字符  text2[j-1]，递归求解 lcs(i, j-1)。
 *
 * eg: text1 = "abcde" 和 text2 = "ace"
 *                       lcs(5, 3) "abcde" "ace"
 *                       /         \
 *  "abcd"   "ace" lcs(4, 3)            lcs(5,2) "abcde" "ac"
 *                 /      \             /       \
 * "abc" "ace" lcs(3, 3)  lcs(4, 2)   lcs(4,2)  lcs(5,1)
 *                      "abcd" "ac"              "abcde" "a"
 *             / \          / \                    / \
 *        (3,2)  (2,3)   (3,2) (4,1)           (4,1) (5,0)
 * "abc" "ac"   "ab" "ace"   "abcd" "a"              "abcde" ""
 *        / \       / \           / \
 *  (2,2)  (3,1)  (1,3) (2,2)   (3,1)(4,0)
 * "ab" "ac"     "a" "ace"
 *       "abc" "a"     "ab" "ac"
 * </pre>
 * eg1:
 * str1 = a12bc345def str2=MNP123QRS45Z    res = 12345
 * <pre>
 * eg2:
 * str1 = a123bc str2= 12dea3f
 * 那么dp表
 * dp[i][j] 表示str1从0~i和str2从0~j的最长公共子串
 *   0  1  2  3  4  5  6
 * 0 0  0  0  0  1  1  1   第一行是因为 if(i==0&&j==0) return str1[i] == str2[j] ? 1:0
 * 1 1
 * 2 1
 * 3 1
 * 4 1              □
 *                   ↘    <====意思是dp[i][j] 依赖于dp[i-1][j-1]
 * 5 1                □
 *
 * 对于dp[0][4] 也就是str1 = a str2 = 12dea
 * </pre>
 * TODO
 *     str1[0...i]和str2[0...j]，这个范围上最长公共子序列长度是多少？
 *     可能性分类:
 *     a) 最长公共子序列，一定不以str1[i]字符结尾、也一定不以str2[j]字符结尾
 *     b) 最长公共子序列，可能以str1[i]字符结尾、但是一定不以str2[j]字符结尾
 *     c) 最长公共子序列，一定不以str1[i]字符结尾、但是可能以str2[j]字符结尾
 *     d) 最长公共子序列，必须以str1[i]字符结尾、也必须以str2[j]字符结尾
 *     注意：a)、b)、c)、d)并不是完全互斥的，他们可能会有重叠的情况
 *     但是可以肯定，答案不会超过这四种可能性的范围
 *     那么我们分别来看一下，这几种可能性怎么调用后续的递归。
 * TODO 一定以 str2[j] 结尾 还要验证 str1[0~i]里面有没有j字符  ====>所以考虑  可能 和 一定不
 * <pre>
 *     a) 最长公共子序列，一定不以str1[i]字符结尾、也一定不以str2[j]字符结尾
 *         eg: str1=123d  str2=a123e   公共子序列是123 但是一个是d结尾 另一个是e结尾
 *        如果是这种情况，那么有没有str1[i]和str2[j]就根本不重要了，因为这两个字符一定没用啊
 *        所以砍掉这两个字符，最长公共子序列 = str1[0...i-1]与str2[0...j-1]的最长公共子序列长度(后续递归)
 *        也就是 dp [1][j] 依赖于 dp[i-1][j-1]
 *     b) 最长公共子序列，可能以str1[i]字符结尾、但是一定不以str2[j]字符结尾 [言下之意 可以不考虑str1[i]字符结尾]
 *         eg: str1=123d  str2=a123e  或者 str1=d123  str2=a123e
 *        如果是这种情况，那么我们可以确定str2[j]一定没有用，要砍掉；但是str1[i]可能有用，所以要保留
 *        所以，最长公共子序列 = str1[0...i]与str2[0...j-1]的最长公共子序列长度(后续递归)
 *        也就是 dp [1][j] 依赖于 dp[i][j-1]
 *     c) 最长公共子序列，一定不以str1[i]字符结尾、但是可能以str2[j]字符结尾 [言下之意 可以不考虑str1[j]字符结尾]
 *        eg:  str1=123d  str2=a12e3  一个是d结尾 另一个是3结尾 或者 str1=d123f  str2=a123e
 *        跟上面分析过程类似，最长公共子序列 = str1[0...i-1]与str2[0...j]的最长公共子序列长度(后续递归)
 *        也就是 dp [1][j] 依赖于 dp[i-1][j]正下方向
 *     d) 最长公共子序列，必须以str1[i]字符结尾、也必须以str2[j]字符结尾
 *        同时可以看到，可能性d)存在的条件，一定是在str1[i] == str2[j]的情况下，才成立的
 *        eg: str1 = x57123 str2 = g123
 *        所以，最长公共子序列总长度 = str1[0...i-1]与str2[0...j-1]的最长公共子序列长度(后续递归) + 1(共同的结尾)
 *        也就是 dp [1][j] = dp[i-1][j-1]+1
 *     综上，四种情况已经穷尽了所有可能性。四种情况中取最大即可
 *     其中b)、c)一定参与最大值的比较，
 *     当str1[i] == str2[j]时，a)一定比d)小，所以d)参与
 *     当str1[i] != str2[j]时，d)压根不存在，所以a)参与
 *     但是再次注意了！
 *     a)是：str1[0...i-1]与str2[0...j-1]的最长公共子序列长度
 *     b)是：str1[0...i]与str2[0...j-1]的最长公共子序列长度
 *     c)是：str1[0...i-1]与str2[0...j]的最长公共子序列长度
 *     a)中str1的范围 < b)中str1的范围，a)中str2的范围 == b)中str2的范围
 *     所以a)不用求也知道，它比不过b)啊，因为有一个样本的范围比b)小啊！
 *     a)中str1的范围 == c)中str1的范围，a)中str2的范围 < c)中str2的范围
 *     所以a)不用求也知道，它比不过c)啊，因为有一个样本的范围比c)小啊！
 *     至此，可以知道，a)就是个垃圾，有它没它，都不影响最大值的决策
 *     所以，当str1[i] == str2[j]时，b)、c)、d)中选出最大值
 *     当str1[i] != str2[j]时，b)、c)中选出最大值
 * </pre>
 */
public class LeetCode_1143_LongestCommonSubsequence {

    public static int longestCommonSubsequence1(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        // 尝试
        return process1(str1, str2, str1.length - 1, str2.length - 1);
    }

    /**
     *
     * @param str1
     * @param str2
     * @param i
     * @param j
     * @return
     */
    public static int process1(char[] str1, char[] str2, int i, int j) {
        if (i == 0 && j == 0) {
            /**
             *str1[0..0]和str2[0..0]，都只剩一个字符了
             * 那如果字符相等，公共子序列长度就是1，不相等就是0
             * 这显而易见
             */
            return str1[i] == str2[j] ? 1 : 0;
        } else if (i == 0) {
            /**
             * 这里的情况为：
             * str1[0...0]和str2[0...j]，str1只剩1个字符了，但是str2不只一个字符
             * 因为str1只剩一个字符了，所以str1[0...0]和str2[0...j]公共子序列最多长度为1
             * 如果str1[0] == str2[j]，那么此时相等已经找到了！公共子序列长度就是1，也不可能更大了
             * 如果str1[0] != str2[j]，只是此时不相等而已，
             * 那么str2[0...j-1]上有没有字符等于str1[0]呢？不知道，所以递归继续找
             */
            if (str1[i] == str2[j]) {
                return 1;
            } else {
                return process1(str1, str2, i, j - 1);
            }
        } else if (j == 0) {
            /**
             * 和上面的else if同理
             * str1[0...i]和str2[0...0]，str2只剩1个字符了，但是str1不只一个字符
             * 因为str2只剩一个字符了，所以str1[0...i]和str2[0...0]公共子序列最多长度为1
             * 如果str1[i] == str2[0]，那么此时相等已经找到了！公共子序列长度就是1，也不可能更大了
             * 如果str1[i] != str2[0]，只是此时不相等而已，
             * 那么str1[0...i-1]上有没有字符等于str2[0]呢？不知道，所以递归继续找
             */
            if (str1[i] == str2[j]) {
                return 1;
            } else {
                return process1(str1, str2, i - 1, j);
            }
        } else { // i != 0 && j != 0
            /**
             * 这里的情况为：
             * str1[0...i]和str2[0...i]，str1和str2都不只一个字符
             * 看函数开始之前的注释部分
             * p1就是可能性c)
             * 因为完全不考虑str1[i] 所以是i-1 ，可能考虑str2[j] 所以是j
             */
            int p1 = process1(str1, str2, i - 1, j);
            /**
             * p2就是可能性b) str1[0...i]和str2[0...j-1]做递归
             * 因为可能考虑str1[i] 所以是i ，完全不考虑str2[j-1] 所以是j-1
             */
            int p2 = process1(str1, str2, i, j - 1);
            /**
             * p3就是可能性d)，如果可能性d)存在，即str1[i] == str2[j]，那么p3就求出来，参与pk
             * 如果可能性d)不存在，即str1[i] != str2[j]，那么让p3等于0，然后去参与pk，反正不影响
             *TODO
             * 如果 一定考虑i，也一定考虑j  如果不加上 str1[i] != str2[j]条件
             * 那么 就会变成process1(str1, str2, i, j) 死循环
             */
            int p3 = str1[i] == str2[j] ? (1 + process1(str1, str2, i - 1, j - 1)) : 0;
            return Math.max(p1, Math.max(p2, p3));
        }
    }

    public static int longestCommonSubsequence2(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int N = str1.length;
        int M = str2.length;
        int[][] dp = new int[N][M];
        //dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        if (str1[0] == str2[0]) {
            /*
             * 如果str1和str2第一个字符串相同
             * 那么截止到第一个字符串 最长公共子序列长度为1
             * */
            dp[0][0] = 1;
        } else {
            dp[0][0] = 0;
        }
        for (int j = 1; j < M; j++) {
            dp[0][j] = str1[0] == str2[j] ? 1 : dp[0][j - 1];
        }
        for (int i = 1; i < N; i++) {
            dp[i][0] = str1[i] == str2[0] ? 1 : dp[i - 1][0];
        }
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                int p1 = dp[i - 1][j];
                int p2 = dp[i][j - 1];
                int p3 = str1[i] == str2[j] ? (1 + dp[i - 1][j - 1]) : 0;
                dp[i][j] = Math.max(p1, Math.max(p2, p3));
            }
        }
        return dp[N - 1][M - 1];
    }

    public static int longestCommonSubsequence3(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int N = str1.length;
        int M = str2.length;
        int[][] dp = new int[N][M];
        //dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        if (str1[0] == str2[0]) {
            /*
             * 如果str1和str2第一个字符串相同
             * 那么截止到第一个字符串 最长公共子序列长度为1
             * */
            dp[0][0] = 1;
        } else {
            dp[0][0] = 0;
        }
        for (int i = 1; i < str1.length; i++) {
            /*
             * 一旦 str第位置的字符 等于 str2第0位置的字符
             * dp[i][0] 也就是截止到 str1从0~i的位置 str2一直是第0位置的情况下
             *  dp[i][0] =1 并且 从i开始到str1的最后 dp[i][0] 都是1
             * 也就是说 这张表的第i行开始到最后一行 第一列所代表的最长公共自序列都是1
             * */
            if (str1[i] == str2[0]) {
                dp[i][0] = Math.max(dp[i - 1][0], 1);
            } else {
                dp[i][0] = Math.max(dp[i - 1][0], 0);
            }
            //dp[i][0] = Math.max(dp[i - 1][0], str1[i] == str2[0] ? 1 : 0);
        }
        for (int j = 1; j < str2.length; j++) {
            dp[0][j] = Math.max(dp[0][j - 1], str1[0] == str2[j] ? 1 : 0);
        }
        for (int i = 1; i < str1.length; i++) {
            for (int j = 1; j < str2.length; j++) {
                dp[i][j] = Math.max(dp[i - 1][i], dp[i][j - 1]);
                if (str1[i] == str2[j]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                }
            }
        }
        return dp[str1.length - 1][str2.length - 1];
    }
}
