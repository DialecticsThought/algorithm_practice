package leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description
 * @Author veritas
 * @Data 2024/8/16 12:29
 * <h1>
 * 子序列问题  从左往右模型
 * </h1>
 */
public class LeetCode_491_IncreasingSubsequences {
    /**
     * <pre>
     * [4, 6, 3, 8]
     *                                          [],0
     *                     /                  /             \           \
     *                [4]                    [6]           [3]           [8]
     *       /      /    \    \           /   |  \         /   \
     *     [4]  [4,6]  [4,3]  [4,8]     [6] [6,3] [6,8]  [3] [3,8]
     *          /   \    ×                    ×
     *      [4,6,3]  [4,6,8]
     *        ×
     * </pre>
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> findSubsequences(int[] nums) {
        Set<List<Integer>> res = new HashSet<>();
        List<Integer> path = new ArrayList<>();
        backtrack(nums, 0, path, res);
        return new ArrayList<>(res);
    }

    /**
     * 1.回溯的选择与不选择：
     * 每当我们递归到数组中的某个元素时，有两个选择：
     * 选择它：将这个元素加入当前路径（子序列），并递归处理剩余元素。
     * 不选择它：直接递归处理剩余元素而不加入当前路径。
     * <p>
     * 2. 递增子序列的判断：
     * 为了保持递增性，我们只有在当前元素 nums[i] 大于或等于 path 的最后一个元素时，才会选择它加入路径。
     * 1.如果当前路径为空，表示我们刚开始构建子序列，此时任何元素都可以被加入。
     * 2.如果路径中已经有元素，我们确保当前元素大于等于路径中最后一个元素，以保持递增性。
     *
     * @param nums
     * @param start
     * @param path
     * @param res
     */
    private void backtrack(int[] nums, int start, List<Integer> path, Set<List<Integer>> res) {
        // 在递归的过程中，每当我们构建出一个长度至少为 2 的递增子序列时，就可以将其加入到结果集中
        if (path.size() >= 2) {
            res.add(new ArrayList<>(path)); // 添加当前有效的递增子序列
        }
        // 在递归的过程中，每当我们构建出一个长度至少为 2 的递增子序列时，就可以将其加入到结果集中

        // 数组遍历 完毕 就是递归终止条件
        for (int i = start; i < nums.length; i++) {
            // 如果 路径不是空，并且路劲数组的左右一个元素 <= 当前被便利道德元素
            if (path.isEmpty() || nums[i] >= path.get(path.size() - 1)) {
                // 放入
                path.add(nums[i]);
                // 递归处理下一个元素
                backtrack(nums, i + 1, path, res);
                // 回溯 也就是拿出 刚才放入的num[i]
                path.remove(path.size() - 1);
            }
        }
    }
    public static void main(String[] args) {
        LeetCode_491_IncreasingSubsequences solution = new LeetCode_491_IncreasingSubsequences();

        int[] nums1 = {4, 6, 7, 7};
        System.out.println(solution.findSubsequences(nums1)); // 输出: [[4, 6], [4, 6, 7], [4, 6, 7, 7], [4, 7], [4, 7, 7], [6, 7], [6, 7, 7], [7, 7]]

        int[] nums2 = {4, 4, 3, 2, 1};
        System.out.println(solution.findSubsequences(nums2)); // 输出: [[4, 4]]
    }
}
