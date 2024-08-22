package heima_data_structure.java.src.main.java.com.itheima.algorithm.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * <h3>全排列 II - 回溯</h3>
 */
public class PermuteLeetcode47 {

    static List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(nums, new boolean[nums.length], new LinkedList<>(), result);
        return result;
    }

    static void dfs(int[] nums, boolean[] visited, LinkedList<Integer> stack, List<List<Integer>> result) {
        if (stack.size() == nums.length) {
            result.add(new ArrayList<>(stack));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1] && !visited[i-1]) { // 找出重复数字
                continue;
            }
            if (!visited[i]) {
                stack.push(nums[i]);
                visited[i] = true;
                dfs(nums, visited, stack, result);
                visited[i] = false;
                stack.pop();
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 1, 3};
        Arrays.sort(nums);
        List<List<Integer>> permute = permuteUnique(nums);
        for (List<Integer> list : permute) {
            System.out.println(list);
        }
    }
}
