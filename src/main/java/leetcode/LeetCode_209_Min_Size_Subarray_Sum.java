package leetcode;

/**
 * @Description
 * @Author veritas
 * @Data 2024/8/25 11:34
 * 假设我们有一个目标值 target = 15，数组为 nums = [2, 3, 1, 2, 4, 3, 6, 7]。
 * 目标：
 * 找到一个连续的子数组，使其和大于或等于 15，并返回这个子数组的最小长度。
 * 初始状态：
 * left = 0
 * currentSum = 0
 * minLength = Integer.MAX_VALUE
 * 右指针开始扩展窗口：
 * right = 0: currentSum = 2（2加入窗口），currentSum < 15，继续扩展。
 * right = 1: currentSum = 5（3加入窗口），currentSum < 15，继续扩展。
 * right = 2: currentSum = 6（1加入窗口），currentSum < 15，继续扩展。
 * right = 3: currentSum = 8（2加入窗口），currentSum < 15，继续扩展。
 * right = 4: currentSum = 12（4加入窗口），currentSum < 15，继续扩展。
 * right = 5: currentSum = 15（3加入窗口），currentSum >= 15，开始收缩窗口。
 * <p>
 * 左指针开始收缩窗口：
 * 收缩前的窗口：[2, 3, 1, 2, 4, 3]，currentSum = 15，长度为 6。
 * 收缩操作：
 * minLength 更新为 6，然后左指针右移：
 * left = 1: currentSum = 13（去掉2），currentSum < 15，停止收缩，继续扩展。
 * <p>
 * 继续扩展窗口：
 * right = 6: currentSum = 19（6加入窗口），currentSum >= 15，继续收缩。
 * <p>
 * 再次收缩窗口：
 * 收缩前的窗口：[3, 1, 2, 4, 3, 6]，currentSum = 19，长度为 6。
 * <p>
 * 收缩操作：
 * minLength 保持为 6（没有更短），然后左指针右移：
 * left = 2: currentSum = 16（去掉3），currentSum >= 15，继续收缩：
 * left = 3: currentSum = 15（去掉1），currentSum >= 15，继续收缩：
 * left = 4: currentSum = 13（去掉2），currentSum < 15，停止收缩，继续扩展。
 * <p>
 * 最后一次扩展窗口：
 * right = 7: currentSum = 20（7加入窗口），currentSum >= 15，继续收缩。
 * <p>
 * 最后一次收缩窗口：
 * 收缩前的窗口：[4, 3, 6, 7]，currentSum = 20，长度为 4。
 * <p>
 * 收缩操作：
 * minLength 更新为 4，然后左指针右移：
 * left = 5: currentSum = 16（去掉4），currentSum >= 15，继续收缩：
 * left = 6: currentSum = 13（去掉3），currentSum < 15，停止收缩。
 * <p>
 * 最终结果：
 * 此时，最小的子数组长度 minLength = 3，对应的子数组是 [6, 7]，它们的和是 13 + 7 = 15。
 */
public class LeetCode_209_Min_Size_Subarray_Sum {

    /**
     * 初始化变量：
     * left 指针指向数组的起始位置 (left = 0)。
     * current_sum 用于跟踪当前窗口内的元素和 (current_sum = 0)。
     * min_length 用于存储找到的最小长度 (min_length = Integer.MAX_VALUE)。
     * <p>
     * 右指针扩展窗口：
     * 使用一个 right 指针遍历数组，对于数组中的每个元素，累加其值到 current_sum。
     * <p>
     * 左指针收缩窗口：
     * 当 current_sum 大于或等于 target 时，尝试收缩窗口：
     * 更新 min_length 为当前最小的窗口长度 (right - left + 1)。
     * 从 current_sum 中减去 left 指针指向的值，然后将 left 指针右移一位。
     * <p>
     * 继续直到遍历完整个数组：
     * 继续扩展和收缩窗口，直到 right 指针遍历完整个数组。
     * 返回结果：
     * 如果 min_length 仍然是初始值（表示没有找到符合条件的子数组），返回 0。
     * 否则，返回 min_length。
     *
     * @param target
     * @param nums
     * @return
     */
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0; // 左指针初始化为数组的起始位置
        int currentSum = 0; // 当前窗口内的元素和初始化为0
        int minLength = Integer.MAX_VALUE; // 初始化最小长度为正无穷大，表示还未找到符合条件的子数组
        // 右指针从数组的第一个元素开始遍历，逐步扩展窗口
        for (int right = 0; right < nums.length; right++) {
            currentSum += nums[right]; // 将右指针指向的元素加入当前窗口的和
            // 当当前窗口的和大于或等于目标值时，尝试收缩窗口以找到最小长度
            while (currentSum >= target) {
                minLength = Math.min(minLength, right - left + 1); // 更新最小长度为当前窗口的长度，如果更小
                currentSum -= nums[left]; // 将左指针指向的元素从当前窗口的和中减去
                left++; // 将左指针右移，尝试收缩窗口
            }
        }
        // 如果minLength仍然是初始值，表示没有找到符合条件的子数组，返回0
        // 否则返回minLength，即找到的最小子数组长度
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }


}
