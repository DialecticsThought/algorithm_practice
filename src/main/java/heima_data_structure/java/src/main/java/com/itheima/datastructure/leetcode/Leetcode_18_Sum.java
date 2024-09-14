package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * <h3>4数之和</h3>
 */
public class Leetcode_18_Sum {
    /**
     * <pre>
     * [-2 -1 0 0 1 2]
     * 初始target = 0
     * 1.一开始固定 -2 target= 0 -（-2）= 2
     * 接下来就是3数之和
     * 1.1再固定-1 target= 2 -（-1）= 3
     * 接下来就是2数之和
     * 变成
     * [-2 -1 0 0 1 2]
     *        i     j
     *  0 + 2 < target
     *  i++
     * [-2 -1 0 0 1 2]
     *          i   j
     *  0 + 2 < target
     *  i++
     * [-2 -1 0 0 1 2]
     *            i j
     *  1 + 2 = target
     *  找到一个解
     *  i++ j--
     *  i = j   结束 返回上一层
     *
     * 1.2再固定0  target= 2 -（0）= 2
     * 接下来就是2数之和
     * 变成
     * [-2 -1 0 0 1 2]
     *          i   j
     *  0 + 2 = target
     *  找到一个解
     *  i++ j--
     *  i = j   结束 返回上一层
     *  不会再固定0 因为会有重复解
     *  不会再固定1 因为 固定了字后 只剩下1个数了
     *
     * 2.固定 -1 target= 0 -（-1）= 1
     * 2.1再固定0  target= 1 -（0）= 1
     * 接下来就是2数之和
     * [-2 -1 0 0 1 2]
     *          i   j
     *  0 + 2 > target
     *  j--
     * [-2 -1 0 0 1 2]
     *          i j
     *  0 + 1 = target
     * 找到一个解
     * 此时 i= j - 1  结束
     * 2.2 不会固定0 因为重复了
     * 2.3 不会再固定1 因为 固定了字后 只剩下1个数了
     * 3.固定 0 target= 0 -（0）= 0
     * 3.1再固定0  target= 0 -（0）= 0
     * [-2 -1 0 0 1 2]
     *            i j
     * 1 + 2 > target
     * i= j - 1 结束
     * </pre>
     * <pre>
     *  1. dfs(n=4, i=0, j=5, target=0)
     *    |
     *    |-- 2. [-2] dfs(n=3, i=1, j=5, target=2)
     *    |     |
     *    |     |-- 3. [-2, -1] dfs(n=2, i=2, j=5, target=3)
     *    |     |     |
     *    |     |     |-- 调用 twoSum(i=2, j=5, target=3) => 找到组合 [-2, -1, 1, 2]
     *    |     |
     *    |     |-- 4. [-2, 0] dfs(n=2, i=3, j=5, target=2)
     *    |     |     |
     *    |     |     |-- 调用 twoSum(i=3, j=5, target=2) => 找到组合 [-2, 0, 0, 2]
     *    |     |
     *    |     |-- 5. [-2, 1] dfs(n=2, i=5, j=5, target=1)
     *    |           |
     *    |           |-- 调用 twoSum(i=5, j=5, target=1) => 无结果
     *    |
     *    |-- 6. [-1] dfs(n=3, i=2, j=5, target=1)
     *          |
     *          |-- 7. [-1, 0] dfs(n=2, i=3, j=5, target=1)
     *          |     |
     *          |     |-- 调用 twoSum(i=3, j=5, target=1) => 找到组合 [-1, 0, 0, 1]
     *          |
     *          |-- 8. [-1, 1] dfs(n=2, i=5, j=5, target=0)
     *                |
     *                |-- 调用 twoSum(i=5, j=5, target=0) => 无结果
     *    |
     *    |-- 9. [0] dfs(n=3, i=3, j=5, target=0)
     *          |
     *          |-- 10. [0, 0] dfs(n=2, i=4, j=5, target=0)
     *          |      |
     *          |      |-- 调用 twoSum(i=4, j=5, target=0) => 无结果
     *          |
     *          |-- 11. [0, 1] dfs(n=2, i=5, j=5, target=-1)
     *                  |
     *                  |-- 调用 twoSum(i=5, j=5, target=-1) => 无结果
     *    |
     *    |-- 12. [1] dfs(n=3, i=5, j=5, target=-1)
     *          |
     *          |-- 13. [1, 2] dfs(n=2, i=6, j=5, target=-2)
     *                |
     *                |-- 调用 twoSum(i=6, j=5, target=-2) => 无结果
     *
     *  初始调用
     * 1. dfs(n=4, i=0, j=5, target=0)
     *
     * 我们要从数组中找 4 个数，初始目标和为 0。
     * 从索引 i=0 开始遍历数组。
     * 第一层递归
     * 2. 选择 -2，调用 dfs(n=3, i=1, j=5, target=2)
     *
     * 将 -2 压入栈：stack = [-2]。
     * 更新目标和：0 - (-2) = 2。
     * 第二层递归
     * 3. 选择 -1，调用 dfs(n=2, i=2, j=5, target=3)
     *
     * 将 -1 压入栈：stack = [-2, -1]。
     * 更新目标和：2 - (-1) = 3。
     * n == 2，调用 twoSum(i=2, j=5, target=3)。
     * 找到组合 [-2, -1, 1, 2]，加入结果集。
     * 4. 回溯，选择 0，调用 dfs(n=2, i=3, j=5, target=2)
     *
     * 弹出 -1，将 0 压入栈：stack = [-2, 0]。
     * 更新目标和：2 - 0 = 2。
     * n == 2，调用 twoSum(i=3, j=5, target=2)。
     * 找到组合 [-2, 0, 0, 2]，加入结果集。
     * 5. 回溯，选择 1，调用 dfs(n=2, i=5, j=5, target=1)
     *
     * 弹出 0，将 1 压入栈：stack = [-2, 1]。
     * 更新目标和：2 - 1 = 1。
     * n == 2，调用 twoSum(i=5, j=5, target=1)。
     * 只有一个元素，无结果。
     * 回溯到第一层
     * 6. 回溯，选择 -1，调用 dfs(n=3, i=2, j=5, target=1)
     *
     * 弹出 -2，将 -1 压入栈：stack = [-1]。
     * 更新目标和：0 - (-1) = 1。
     * 第二层递归
     * 7. 选择 0，调用 dfs(n=2, i=3, j=5, target=1)
     *
     * 将 0 压入栈：stack = [-1, 0]。
     * 更新目标和：1 - 0 = 1。
     * n == 2，调用 twoSum(i=3, j=5, target=1)。
     * 找到组合 [-1, 0, 0, 1]，加入结果集。
     * 8. 回溯，选择 1，调用 dfs(n=2, i=5, j=5, target=0)
     *
     * 弹出 0，将 1 压入栈：stack = [-1, 1]。
     * 更新目标和：1 - 1 = 0。
     * n == 2，调用 twoSum(i=5, j=5, target=0)。
     * 只有一个元素，无结果。
     * 回溯到第一层
     * 9. 回溯，选择 0，调用 dfs(n=3, i=3, j=5, target=0)
     *
     * 弹出 -1，将 0 压入栈：stack = [0]。
     * 更新目标和：0 - 0 = 0。
     * 第二层递归
     * 10. 选择 0，调用 dfs(n=2, i=4, j=5, target=0)
     *
     * 将 0 压入栈：stack = [0, 0]。
     * 更新目标和：0 - 0 = 0。
     * n == 2，调用 twoSum(i=4, j=5, target=0)。
     * 无法找到和为 0 的两数组合。
     * 11. 回溯，选择 1，调用 dfs(n=2, i=5, j=5, target=-1)
     *
     * 弹出 0，将 1 压入栈：stack = [0, 1]。
     * 更新目标和：0 - 1 = -1。
     * n == 2，调用 twoSum(i=5, j=5, target=-1)。
     * 只有一个元素，无结果。
     * 回溯到第一层
     * 12. 回溯，选择 1，调用 dfs(n=3, i=5, j=5, target=-1)
     *
     * 弹出 0，将 1 压入栈：stack = [1]。
     * 更新目标和：0 - 1 = -1。
     * 第二层递归
     * 13. 选择 2，调用 dfs(n=2, i=6, j=5, target=-2)
     *
     * 将 2 压入栈：stack = [1, 2]。
     * 更新目标和：-1 - 2 = -3。
     * n == 2，调用 twoSum(i=6, j=5, target=-3)。
     * 左指针超过右指针，无结果。
     * </pre>
     *
     * @param nums
     * @param target
     * @return
     */
    static List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> result = new LinkedList<>();
        dfs(4, 0, nums.length - 1, target, nums,
                new LinkedList<>(), result);
        return result;
    }

    static void dfs(int n, int i, int j, int target, int[] nums,
                    LinkedList<Integer> stack,
                    List<List<Integer>> result) {
        if (n == 2) {
            // 套用两数之和求解
            twoSum(i, j, nums, target, stack, result);
            return;
        }
        for (int k = i; k < j - (n - 2); k++) { // 四数之和 i <j-2  三数之和 i <j-1
            // 检查重复
            if (k > i && nums[k] == nums[k - 1]) {
                continue;
            }
            // 固定一个数字，再尝试 n-1 数字之和
            stack.push(nums[k]);
            dfs(n - 1, k + 1, j, target - nums[k], nums, stack, result);
            stack.pop();
        }
    }

    static int count;

    static public void twoSum(int i, int j, int[] numbers, int target,
                              LinkedList<Integer> stack,
                              List<List<Integer>> result) {
        count++;
        while (i < j) {
            int sum = numbers[i] + numbers[j];
            if (sum < target) {
                i++;
            } else if (sum > target) {
                j--;
            } else { // 找到解
                ArrayList<Integer> list = new ArrayList<>(stack);
                list.add(numbers[i]);
                list.add(numbers[j]);
                result.add(list);
                // 继续查找其它的解
                i++;
                j--;
                while (i < j && numbers[i] == numbers[i - 1]) {
                    i++;
                }
                while (i < j && numbers[j] == numbers[j + 1]) {
                    j--;
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0));
//        System.out.println(fourSum(new int[]{2, 2, 2, 2, 2}, 8));
//        System.out.println(fourSum(new int[]{1000000000,1000000000,1000000000,1000000000}, -294967296));
    }
}
