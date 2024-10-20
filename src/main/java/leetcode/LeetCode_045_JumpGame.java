package leetcode;

/**
 * @Description
 * @Author veritas
 * @Data 2024/8/28 23:25
 *
 * <pre>
 *   nums = [2, 3, 1, 1, 4]
 *   Start: Index 0 (2)
 *         |
 *         |-- Jump 1 --> Index 1 (3)
 *         |                  |
 *         |                  |-- Jump 1 --> Index 2 (1)
 *         |                  |                  |
 *         |                  |                  |-- Jump 1 --> Index 3 (1)
 *         |                  |                  |                  |
 *         |                  |                  |                  |-- Jump 1 --> Index 4 (4) [End, total jumps: 4]
 *         |                  |
 *         |                  |-- Jump 2 --> Index 3 (1)
 *         |                  |                  |
 *         |                  |                  |-- Jump 1 --> Index 4 (4) [End, total jumps: 3]
 *         |                  |
 *         |                  |-- Jump 3 --> Index 4 (4) [End, total jumps: 2]
 *         |
 *         |-- Jump 2 --> Index 2 (1)
 *                            |
 *                            |-- Jump 1 --> Index 3 (1)
 *                            |                  |
 *                            |                  |-- Jump 1 --> Index 4 (4) [End, total jumps: 3]
 *
 * 从索引0到索引4（路径[0 -> 1 -> 2 -> 3 -> 4]）: 需要4次跳跃。
 * 从索引0到索引4（路径[0 -> 1 -> 3 -> 4]）: 需要3次跳跃。
 * 从索引0到索引4（路径[0 -> 1 -> 4]）: 需要2次跳跃。 （最优解）
 * 从索引0到索引4（路径[0 -> 2 -> 3 -> 4]）: 需要3次跳跃。
 * </pre>
 *
 */
public class LeetCode_045_JumpGame {
    public int jump(int[] nums) {
        return jumpFromPosition(0, nums); // 从第一个位置开始跳跃
    }

    private int jumpFromPosition(int position, int[] nums) {
        // 如果当前位置是或超过了最后一个位置，说明已经到达终点
        if (position >= nums.length - 1) {
            return 0;
        }

        int maxJump = nums[position]; // 当前能够跳跃的最大步数
        int minJumps = Integer.MAX_VALUE; // 初始化为最大值，寻找最小的跳跃次数

        // 尝试从1步跳到maxJump步，递归计算每个可能跳跃后的最小跳跃次数
        for (int i = 1; i <= maxJump; i++) {
            int jumps = jumpFromPosition(position + i, nums);
            if (jumps != Integer.MAX_VALUE) { // 如果能到达终点
                minJumps = Math.min(minJumps, 1 + jumps); // 计算最小的跳跃次数
            }
        }

        return minJumps; // 返回从当前位置到达终点所需的最小跳跃次数
    }
}
