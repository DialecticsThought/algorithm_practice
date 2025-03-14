package heima_data_structure.java.src.main.java.com.itheima.algorithm.dynamicprogramming;

/**
 * <pre>
 *   卡特兰数
 *   1.有1个节点： 1
 *     能组成二叉搜索树BST的个数 = 1  c(1) = 1
 *   2.有2个节点：1 2
 *     能组成二叉搜索树BST的个数 = 2  c(2) = 2
 *    2.1. 1作为根节点
 *     1
 *       ↘
 *         2
 *    2.2. 2作为根节点
 *          2
 *        ↙
 *      1
 *   3.有3个节点：1 2 3
 *     能组成二叉搜索树BST的个数 = 5  c(3) = 5
 *     3.1. 1作为根节点
 *      1                 1
 *        ↘                 ↘
 *          2                 3
 *             ↘            ↙
 *               3         2
 *     对于1作为根节点 其余所有节点都 > 1，都在 1的右侧，两个节点的排列 相当于 c(2) = 2
 *     3.2. 2作为根节点
 *          2
 *       ↙    ↘
 *      1      3
 *     对于2作为根节点 其余的节点在两侧  相当于 c(1) = 1
 *     3.3. 3作为根节点
 *             3          3
 *           ↙          ↙
 *         2          1
 *       ↙              ↘
 *     1                  2
 *     对于3作为根节点 其余的所有节点都在 3的左侧，  两个节点的排列 相当于 c(2) = 2
 *   4.有4个节点：1 2 3 4
 *     能组成二叉搜索树BST的个数 = 14    c(4) = 14
 *     4.1. 1作为根节点
 *      1                 1
 *        ↘                 ↘
 *          2                 2
 *             ↘                ↘
 *               4                3
 *             ↙                    ↘
 *          3                         4
 *
 *           1                  1
 *             ↘                  ↘
 *               4                  4
 *             ↙                  ↙
 *           2                  3
 *             ↘              ↙
 *               3          2
 *
 *        1
 *          ↘
 *            3
 *          ↙    ↘
 *        2        4
 *     对于1作为根节点 ，其余所有节点都 > 1 ,那么3个节点的排列组合 相当于 c(3) = 5
 *     4.2. 2作为根节点
 *          2                   2
 *       ↙     ↘             ↙     ↘
 *      1        4         1         3
 *             ↙                       ↘
 *           3                           4
 *     对于2作为根节点 ，其左侧节点一定是1，不会有变化
 *     其余所有节点都 > 2 ,那么2个节点的排列组合 相当于 c(2) = 2
 *     4.3. 3作为根节点
 *           3                    3
 *         ↙    ↘              ↙     ↘
 *       2        4          1        4
 *     ↙                       ↘
 *    1                           2
 *     对于3作为根节点 ，其右侧节点一定是4，不会有变化
 *     其余所有节点都 < 2 ,那么2个节点的排列组合 相当于 c(2) = 2
 *     4.4. 4作为根节点
 *               4                 4
 *             ↙                 ↙
 *          1                 1
 *             ↘                ↘
 *               3                2
 *             ↙                    ↘
 *          2                         3
 *
 *               4                   4
 *             ↙                   ↙
 *          3                    3
 *             ↘               ↙
 *               1           2
 *             ↙           ↙
 *          2             1
 *
 *                4
 *              ↙
 *            2
 *          ↙    ↘
 *        1        3
 *      对于4作为根节点 ，其余所有节点都 < 4 ,那么3个节点的排列组合 相当于 c(3) = 5
 *   5.有5个节点：1 2 3 4 5
 *     能组成二叉搜索树BST的个数 = 42    c(5) = 42
 *    设 C(0) = 1 很特殊
 *    5.1. 1作为根节点
 *          1
 *       ↙     ↘
 *     c(0)   c(4)
 *     意思表示：
 *       比1小的 有0个数 ，0个数的排列组合 = C(0) = 1
 *       比1大的 有4个数 ，4个数的排列组合 = C(4)
 *    5.2. 2作为根节点
 *          2
 *       ↙     ↘
 *     c(1)   c(3)
 *     意思表示：
 *       比2小的 有1个数 ，1个数的排列组合 = C(1)
 *       比2大的 有3个数 ，3个数的排列组合 = C(3)
 *    5.3. 3作为根节点
 *          2
 *       ↙     ↘
 *     c(2)   c(2)
 *     意思表示：
 *       比3小的 有2个数 ，2个数的排列组合 = C(2)
 *       比3大的 有2个数 ，2个数的排列组合 = C(2)
 *    5.4. 4作为根节点
 *          2
 *       ↙     ↘
 *     c(2)   c(2)
 *     意思表示：
 *       比4小的 有3个数 ，3个数的排列组合 = C(3)
 *       比4大的 有1个数 ，1个数的排列组合 = C(1)
 *    5.1. 1作为根节点
 *          1
 *       ↙     ↘
 *     c(0)   c(4)
 *     意思表示：
 *       比5小的 有4个数 ，4个数的排列组合 = C(4)
 *       比5大的 有0个数 ，0个数的排列组合 = C(0) = 1
 *    ===> C(0) * C(4) +   C(1) * C(3) +  C(2) * C(2) +  C(3) * C(1) + C(4) * C(0)
 *
 *  要求 C(n) , n 可以拆成 x 和 y,
 *  x 和 y 和 n 的关系: n = x + y + 1
 * </pre>
 * <pre>
 *   n 个 元素 进栈 序列 为 1 2 3 4 5 ..... n ，那么有多少种出栈序列
 *   进栈的顺序 一定是 1,2,3,4 ..... n  固定不变
 *   栈的顺序 从左到右 是 从栈底到栈顶
 *   1.对于 进栈元素中有1
 *          栈：1
 *          栈：null
 *     出栈是顺序是 1
 *   2.对于 进栈元素中按 1,2 顺序
 *          栈：1
 *          栈：1 2
 *          栈：1
 *          栈：null
 *     出栈是顺序是 2 1
 *          栈：1
 *          栈：null
 *          栈：2
 *          栈：null
 *   入栈1 之后 1再出来， 最后 2入栈，2再出栈
 *   3.对于 进栈元素中按 1,2,3 顺序
 *     对于1是第1个出栈的情况：
 *          栈：1
 *          栈：null
 *          栈：2
 *          栈：null
 *          栈：3
 *          栈：null
 *     出栈次序: 1 2 3
 *          栈：1
 *          栈：null
 *          栈：2
 *          栈：2 3
 *          栈：2
 *          栈：null
 *      出栈次序: 1 3 2
 *     对于1是第2个出栈的情况：
 *          栈：1
 *          栈：1 2
 *          栈：1
 *          栈：null
 *          栈：3
 *          栈：null
 *      出栈次序: 2 1 3
 *     对于1是第3个出栈的情况：
 *          栈：1
 *          栈：1 2
 *          栈：1
 *          栈：1 3
 *          栈：1
 *          栈：null
 *      出栈次序: 2 3 1
 *          栈：1
 *          栈：1 2
 *          栈：1 2 3
 *          栈：1 2
 *          栈：1
 *          栈：null
 *      出栈次序: 3 2 1
 *   4.对于 进栈元素中按 1,2,3,4 顺序
 *     按照卡特兰数的思想
 *          1最先出栈
 *          ↙     ↘
 *        c(3)    c(0)
 *     c(3)表示 之后的还没出栈的3个元素的排列组合
 *     c(0)表示 之前的已经出栈的0个元素的排列组合
 *          1第2出栈
 *          ↙     ↘
 *        c(2)    c(1)
 *     c(2)表示 之后的还没出栈的2个元素的排列组合
 *     c(1)表示 之前的已经出栈的1个元素的排列组合
 *          1第2出栈
 *          ↙     ↘
 *        c(1)    c(2)
 *     c(1)表示 之后的还没出栈的1个元素的排列组合
 *     c(2)表示 之前的已经出栈的2个元素的排列组合
 *          1最后出栈
 *          ↙     ↘
 *        c(0)    c(3)
 *     c(0)表示 之后的还没出栈的0个元素的排列组合
 *     c(3)表示 之前的已经出栈的3个元素的排列组合
 * </pre>

 */
public class LeetCode_096_Catalan {
    public static void main(String[] args) {
        System.out.println(catalan(6));
    }

    /**
     * 计算 C_3 时：
     *
     * j = 3 的时候，我们要计算 dp[3]。
     * i 依次取值 0, 1, 2，对应的计算是：
     * 当 i = 0，我们有 dp[0] * dp[2]
     * 当 i = 1，我们有 dp[1] * dp[1]
     * 当 i = 2，我们有 dp[2] * dp[0]
     * 最终 dp[3] = dp[0] * dp[2] + dp[1] * dp[1] + dp[2] * dp[0]。
     * @param n
     * @return
     */
    static int catalan(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        // 外层循环 j 负责逐步计算每个卡特兰数 dp[j]
        // 外层循环是因为要求 dp[j] j的范围: 2 -> n， 最终要得到dp[n]
        for (int j = 2; j < n + 1; j++) {
            // 内层循环 i 负责将当前 j 拆分成左右两部分 dp[i] * dp[j-1-i]，通过所有的组合来求得当前 j 的卡特兰数
            // 第j个卡特兰数的拆分 ，用dp[i] * dp[j-1-i] 来拆分 j
            // i的范围: 0 -> j-1
            for (int i = 0; i < j; i++) {
                dp[j] += dp[i] * dp[j - 1 - i];
            }
        }

        return dp[n];
    }
}
