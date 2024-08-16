/**
 * @Description
 * @Author veritas
 * @Data 2024/8/16 11:13
 * <h1>
 * 范围尝试模型
 * </h1>
 */
public class Code_312_BurstBalloons {
    /**
     * 拆解问题：
     * 假设你有一个区间 [left, right]，表示从第 left 个气球到第 right 个气球。
     * 现在我们要计算在这个区间内，假设我们最终决定 k 是最后一个被戳破的气球。
     * 那么，假设我们已经先处理了 left 到 k-1 和 k+1 到 right 的区间（即这两个区间内的气球都已经被戳破），最后再戳破 k。
     * <p>
     * 戳破 k 时的得分：
     * 当 k 被戳破时，它的得分为 nums[left-1] * nums[k] * nums[right+1]。
     * 这个得分是基于 k 的左邻居 nums[left-1] 和右邻居 nums[right+1] 的值计算出来的。
     * <p>
     * 为什么说左边和右边的气球都已经被戳破了？
     * 当我们递归处理这个区间时，我们假设在 k 被戳破之前，[left, k-1] 和 [k+1, right] 两部分已经被处理过了（即在这个递归调用之前，这些区间中的气球已经被戳破）。
     * 因此，k 在被戳破时，它的左邻居是 nums[left-1]，右邻居是 nums[right+1]，而 left 到 k-1 和 k+1 到 right 之间的气球已经不存在了
     * <p>
     * 在递归解决这个问题时，不是使用 nums[k-1] * nums[k] * nums[k+1]，
     * 而是使用 nums[left-1] * nums[k] * nums[right+1]，原因如下：
     * 1. 递归的核心思路：
     * 当我们选择 k 作为最后一个被戳破的气球时，假设 k 左边的气球 [left, k-1] 和右边的气球 [k+1, right] 都已经被戳破了。
     * 因此在这个递归层次上，k 的左邻居就是 nums[left-1]，右邻居是 nums[right+1]。
     * 左边：nums[left-1] 是 [left, k-1] 区间外的一个气球（相当于剩下的唯一气球）；
     * 右边：nums[right+1] 是 [k+1, right] 区间外的一个气球（也是剩下的唯一气球）。
     * eg:
     * arr = [3, 1, 5, 8]  newNUms= [1, 3, 1, 5, 8, 1]
     * <pre>
     *                                           (1, 4)
     *                           /               /                     \               \
     *                   3+(2,4)               15+(1,1)+(3,4)      40+(1,2)+(4,4)         40+(1,3)
     *              /          \                /          \          /     \              /         \
     *      15+(3,4)          40+(2,2)+(4,4)  3 +(2,1) 40+(4,4)  3+(2,2)  40+(5,4)   3+(2,3)        15+(1,2)+(3,3)
     *       /   \              /   \                      /         /                 /     \          /     \
     *  40+(3,3) 40+(4,4)    3+(3,2) 40+(5,4)         40 +(5,4)  3+(3,2)         15+(3,3) 40+(2,2)   3+(2,1) 40+(4,3)
     *      /          \                                                             /        \
     *  40+(5,4)  40+(5,4)                                                     15+(4,3)   3+(3,2)
     *    /
     *  15+(4,4)
     *    /
     *  40+(5,4)
     * </pre>
     *
     * @param nums
     * @return
     */
    public int maxCoins(int[] nums) {
        // 在数组的两端各加一个1，便于处理边界情况
        int n = nums.length;
        int[] newNums = new int[n + 2];
        newNums[0] = 1;
        newNums[n + 1] = 1;
        System.arraycopy(nums, 0, newNums, 1, n);

        // 计算在新数组的区间[1, n]内戳破气球的最大得分
        return burst(newNums, 1, n);
    }

    private int burst(int[] nums, int left, int right) {
        if (left > right) {
            return 0;
        }
        int maxCoins = 0;
        // 尝试在[left, right]区间内最后一个戳破第k个气球
        for (int k = left; k <= right; k++) {
            // 分为左右两部分 + 当前选择的气球得分
            int mid = nums[left - 1] * nums[k] * nums[right + 1];
            // 递归找左部分
            int leftPart = burst(nums, left, k - 1);
            // 递归找右部分
            int rightPart = burst(nums, k + 1, right);

            int coins = mid + leftPart + rightPart;

            maxCoins = Math.max(maxCoins, coins);
        }
        return maxCoins;
    }
}
