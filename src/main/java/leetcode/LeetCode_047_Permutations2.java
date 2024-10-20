package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author veritas
 * @Data 2024/8/31 14:12
 */
public class LeetCode_047_Permutations2 {
    /**
     * arr=[2,4,4,5,7]
     * <pre>
     *                                                          []
     *                                      /            /             \         \         \
     *                                   [2]            [4]           [4]         [5]         [7]
     *                         /    |    \   \     /    |    \   \       /    |    \   \
     *                     [2,4] [2,4][2,5][2,7] [4,2] [4,4] [4,5][4,7]  [4,2] [4,4] [4,5][4,7]  [5,2] [5,4] [5,5][5,7]
     *
     * </pre>
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();  // 存储最终的所有唯一排列
        Arrays.sort(nums);  // 对数组进行排序，方便后续去重
        boolean[] used = new boolean[nums.length];  // 用于标记数组中的元素是否已被使用
        backtrack(nums, new ArrayList<>(), used, result);  // 调用递归函数
        return result;  // 返回结果列表
    }

    /**
     * arr=[2,4,4,5,7] 来详细分析为什么要跳过当前元素（当它与前一个元素相同且前一个元素未被使用）
     * <p>
     * 如果我们不跳过相同且未使用的元素，那么在生成排列时，可能会产生重复的排列
     * 第一步：
     * 选择 2，剩余元素是 [4,4,5,7]。
     * 生成的排列路径是 [2,...]。
     * 第二步：
     * 继续选择 4，剩余元素是 [4,5,7]。
     * 生成的排列路径是 [2,4,...]。
     * 第三步：
     * 在这里有两种选择，选择第一个4或者选择第二个4。
     * 如果我们选择第一个4，路径是 [2,4(1),4(2),...]。
     * 如果我们选择第二个4，路径是 [2,4(2),4(1),...]。
     * 结果：
     * 最终，这两种路径都会生成相同的排列，如 [2,4,4,5,7]，只是排列的顺序不同，但内容完全一致。
     * <p>
     * 跳过重复元素的情况
     * 通过在递归过程中跳过未使用的相同元素，我们可以避免这种重复。
     * 第一步：
     * 选择 2，剩余元素是 [4,4,5,7]。
     * 生成的排列路径是 [2,...]。
     * 第二步：
     * 遇到第1个 4，假设不选择这个4，剩余元素是 [4,5,7]。
     * 生成的排列路径是 [2,...]。
     * 第三步：
     * 遇到第2个 4，这里不能选择这个4，剩余元素是 [5,7]
     * 跳过：因为当前元素 4 与前一个 4 相同，且前一个 4 还没有被使用在本层递归的这个位置上（即在当前递归分支中还没有选择它），
     * 因此跳过当前的 4，避免了重复路径 [2,4(2),4(1),...] 的生成。
     * 直接选择下一个不同的元素，例如 5，生成 [2,5,...]。
     * 结果：
     * 最终，通过跳过重复元素，我们确保了路径 [2,4,4,5,7] 只通过 [2,4(1),4(2),...] 这条路径生成一次，不会再有 [2,4(2),4(1),...] 的重复。
     *
     * @param nums
     * @param path
     * @param used
     * @param result
     */
    private void backtrack(int[] nums, List<Integer> path, boolean[] used, List<List<Integer>> result) {
        if (path.size() == nums.length) {  // 如果当前排列的长度与原数组长度相同 如果是，则说明已经生成了一个完整的排列
            result.add(new ArrayList<>(path));  // 将当前排列加入到结果集
            return;  // 结束当前递归
        }
        for (int i = 0; i < nums.length; i++) {  // 遍历数组中的每个元素
            if (used[i]) {  // 如果该元素已被使用，跳过
                continue;
            }
            // 去重关键部分：如果当前元素与前一个元素相同且前一个元素未被使用，则跳过
            // 假设数组已经经过排序，那么相同的元素会紧挨在一起。
            // 如果当前元素与前一个元素相同且前一个元素未被使用，意味着在当前递归层次上，前一个元素实际上还没有参与排列的生成。
            // 因此如果直接使用当前元素，而不是先使用前一个元素，这样的选择路径将与使用前一个元素后再使用当前元素的路径重复
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue;
            }

            used[i] = true;  // 标记当前元素已被使用
            path.add(nums[i]);  // 将当前元素加入到排列路径中

            backtrack(nums, path, used, result);  // 递归处理下一个位置

            path.remove(path.size() - 1);  // 回溯，移除最后一个元素
            used[i] = false;  // 回溯，将当前元素标记为未使用
        }
    }

}
