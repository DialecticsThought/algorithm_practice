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
     *
     * </pre>
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
