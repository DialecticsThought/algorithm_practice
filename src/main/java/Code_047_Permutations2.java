import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author veritas
 * @Data 2024/8/31 14:12
 */
public class Code_047_Permutations2 {
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
