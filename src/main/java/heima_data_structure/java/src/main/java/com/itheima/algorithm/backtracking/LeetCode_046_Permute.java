package heima_data_structure.java.src.main.java.com.itheima.algorithm.backtracking;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <h3>全排列 - 回溯</h3>
 */
public class LeetCode_046_Permute {
    static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(nums, new boolean[nums.length], new LinkedList<>(), result);
        return result;
    }

    /**
     * [1,2,3] 求全排列
     * 递归调用顺序:
     *
     * 1. dfs([], [F, F, F])
     *    |
     *    |-- 2. dfs([1], [T, F, F])
     *        |
     *        |-- 3. dfs([2, 1], [T, T, F])
     *            |
     *            |-- 4. dfs([3, 2, 1], [T, T, T]) => 添加排列 [1, 2, 3]
     *        |
     *        |-- 5. dfs([3, 1], [T, F, T])
     *            |
     *            |-- 6. dfs([2, 3, 1], [T, T, T]) => 添加排列 [1, 3, 2]
     *    |
     *    |-- 7. dfs([2], [F, T, F])
     *        |
     *        |-- 8. dfs([1, 2], [T, T, F])
     *            |
     *            |-- 9. dfs([3, 1, 2], [T, T, T]) => 添加排列 [2, 1, 3]
     *        |
     *        |--10. dfs([3, 2], [F, T, T])
     *            |
     *            |--11. dfs([1, 3, 2], [T, T, T]) => 添加排列 [2, 3, 1]
     *    |
     *    |--12. dfs([3], [F, F, T])
     *        |
     *        |--13. dfs([1, 3], [T, F, T])
     *            |
     *            |--14. dfs([2, 1, 3], [T, T, T]) => 添加排列 [3, 1, 2]
     *        |
     *        |--15. dfs([2, 3], [F, T, T])
     *            |
     *            |--16. dfs([1, 2, 3], [T, T, T]) => 添加排列 [3, 2, 1]
     * @param nums
     * @param visited
     * @param stack
     * @param result
     */
    static void dfs(int[] nums, boolean[] visited, LinkedList<Integer> stack, List<List<Integer>> result) {
        //base case： 如果集合的长度 和原数组一致
        if (stack.size() == nums.length) {
            result.add(new ArrayList<>(stack));
            return;
        }
        // 遍历 nums 数组，发现没有被使用的数字，则将其标记为使用，并加入 stack
        for (int i = 0; i < nums.length; i++) {
            if (!visited[i]) {
                // 选择 遍历到的值 nums[i]
                stack.push(nums[i]);
                visited[i] = true;
                // 在选择 nums[i]的基础上 继续选择 下一个元素
                dfs(nums, visited, stack, result);
                // 回溯
                visited[i] = false;
                stack.pop();
            }
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> permute = permute(new int[]{1, 2, 3});
        for (List<Integer> list : permute) {
            System.out.println(list);
        }
    }
}
