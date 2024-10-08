package heima_data_structure.java.src.main.java.com.itheima.algorithm.dynamicprogramming;

/**
 * <h3>打家劫舍 - 动态规划</h3>
 */
public class Leetcode_198_HouseRobber {

    /**
     * 价值   0   1   2   3   4
     * 0   0   0   0   0
     * 0(7)  7   0   0   0   0
     * 1(2)  7   7   0   0   0
     * 2(9)  2   7   11  0   0
     * 3(3)  2   7   11  11  0
     * 4(1)  2   7   11  11  12
     * <p>
     * dp[4] = dp[2]+1 = 12
     * dp[3] = max(dp[1]+3, dp[2]) = max(10, 11) = 11
     * <p>
     * dp[i] = max(dp[i-2]+value, dp[i-1])
     *
     * <pre>
     *  arr =  [2, 7, 9, 3, 1]
     *
     *      rob(0) = max(2 + rob(2), rob(1))
     *         /                     \
     *      rob(2)                    rob(1)
     *      /      \                /         \
     *  rob(4)    rob(3)        rob(3)        rob(2)
     *   /  \      /  \         /   \          /    \
     *  0    0    0   rob(4) rob(5) rob(4)  rob(3)    rob(4)
     *               /  \     /  \          /  \
     *              0    0   0    0      rob(5) rob(4)
     *                                   /  \
     *                                  0    0
     * </pre>
     */
    public int rob(int[] houses) {
        int len = houses.length;
        if (len == 1) {
            return houses[0];
        }
        int[] dp = new int[len];
        dp[0] = houses[0];
        dp[1] = Integer.max(houses[1], houses[0]);
        for (int i = 2; i < len; i++) {
            dp[i] = Integer.max(dp[i - 2] + houses[i], dp[i - 1]);
        }
        return dp[len - 1];
    }

    public Integer robWithRecursion(int[] nums) {
        Integer[] cache = new Integer[nums.length];

        return robWithCache(nums, 0, cache);
    }


    public Integer robWithoutCache(Integer[] houses, Integer index) {
        // base case
        if (index >= houses.length) {
            return 0;
        }
        // 当前来到index的家中

        // 选择1 选择偷当前index的家，但是下一个家(index+1)不能偷
        Integer case1 = houses[index] + robWithoutCache(houses, index + 2);
        // 选择2 选择不偷当前index的家，去下一个家(index+1)继续做选择
        Integer case2 = robWithoutCache(houses, index + 1);
        // 选择取最大值 作为当前节点的答案
        Integer result = Math.max(case1, case2);
        // 返回当前节点的答案
        return result;
    }

    public Integer robWithCache(int[] houses, Integer index, Integer[] cache) {
        // base case
        if (index >= houses.length) {
            return 0;
        }
        // cache 不为空 直接返回
        if (cache[index] != null) {
            return cache[index];
        } else {// cache 为空
            // 当前来到index的家中

            // 选择1 选择偷当前index的家，但是下一个家(index+1)不能偷
            Integer case1 = houses[index] + robWithCache(houses, index + 2, cache);
            // 选择2 选择不偷当前index的家，去下一个家(index+1)继续做选择
            Integer case2 = robWithCache(houses, index + 1, cache);
            // 选择取最大值 作为当前节点的答案
            Integer result = Math.max(case1, case2);
            // 放入缓存
            cache[index] = result;
            // 返回当前节点的答案
            return result;
        }
    }


    public static void main(String[] args) {
        Leetcode_198_HouseRobber code = new Leetcode_198_HouseRobber();
        System.out.println(code.rob(new int[]{2, 7, 9, 3, 1}));
        System.out.println(code.rob(new int[]{2, 1, 1, 2}));
    }
}
