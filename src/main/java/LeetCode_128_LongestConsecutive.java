import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * code_for_great_offer.class12
 * 本题测试链接 : https://leetcode.com/problems/longest-consecutive-sequence/
 */
public class LeetCode_128_LongestConsecutive {

    /**
     * 求数组中最长连续序列的长度。
     *
     * <pre>
     * 题目例子：
     * nums = [100, 4, 200, 1, 3, 2]
     *
     * 第 1 步：先把所有数字放入 HashSet
     * set = {100, 4, 200, 1, 3, 2}
     *
     * 这样做的目的：
     * 以后想判断某个数字在不在数组里，
     * 就可以直接用 set.contains(x)，时间复杂度接近 O(1)。
     *
     * --------------------------------------------------
     * 核心原则：
     * 只从“连续序列的开头”开始往后找。
     *
     * 怎么判断一个数是不是开头？
     * 看 num - 1 是否存在。
     *
     * - 如果 num - 1 不存在，说明 num 前面没有更小的连续数字，
     *   那么 num 就是一个连续段的开头，可以开始统计长度。
     *
     * - 如果 num - 1 存在，说明 num 不是开头，
     *   因为这个连续段应该从更前面的数字开始统计，
     *   当前 num 直接跳过。
     *
     * --------------------------------------------------
     * 下面开始一步一步遍历：
     *
     * 1）遍历到 100
     *    看 99 在不在 set 中？
     *    99 不在
     *    说明 100 是一个连续段的开头
     *
     *    然后向后找：
     *    101 在不在？不在
     *    所以这个连续段只有 [100]
     *    长度 = 1
     *
     *    当前最大长度 maxLen = 1
     *
     * --------------------------------------------------
     * 2）遍历到 4
     *    看 3 在不在 set 中？
     *    3 在
     *    说明 4 不是开头
     *
     *    为什么不是开头？
     *    因为既然 3 存在，那么这个连续段应该从 3、2、1 这种更前面的数开始算，
     *    不能从 4 重新起一段，否则会重复统计。
     *
     *    所以直接跳过 4
     *
     * --------------------------------------------------
     * 3）遍历到 200
     *    看 199 在不在 set 中？
     *    199 不在
     *    说明 200 是开头
     *
     *    向后找：
     *    201 在不在？不在
     *    所以这个连续段只有 [200]
     *    长度 = 1
     *
     *    当前最大长度 maxLen = 1
     *
     * --------------------------------------------------
     * 4）遍历到 1
     *    看 0 在不在 set 中？
     *    0 不在
     *    说明 1 是开头
     *
     *    开始向后找：
     *    2 在 -> 长度变成 2
     *    3 在 -> 长度变成 3
     *    4 在 -> 长度变成 4
     *    5 不在 -> 停止
     *
     *    所以这一段连续序列是：
     *    [1, 2, 3, 4]
     *
     *    长度 = 4
     *    当前最大长度 maxLen = 4
     *
     * --------------------------------------------------
     * 5）遍历到 3
     *    看 2 在不在 set 中？
     *    2 在
     *    说明 3 不是开头，跳过
     *
     * --------------------------------------------------
     * 6）遍历到 2
     *    看 1 在不在 set 中？
     *    1 在
     *    说明 2 不是开头，跳过
     *
     * --------------------------------------------------
     * 最终答案：
     * 最长连续序列是 [1, 2, 3, 4]
     * 返回 4
     *
     * --------------------------------------------------
     * 为什么这种做法高效？
     *
     * 因为每个连续段只会从“开头”位置统计一次。
     *
     * 例如：
     * [1, 2, 3, 4]
     *
     * - 1 会被当作开头，真正去向后统计
     * - 2 因为前面有 1，所以跳过
     * - 3 因为前面有 2，所以跳过
     * - 4 因为前面有 3，所以跳过
     *
     * 这样就避免了重复计算。
     *
     * --------------------------------------------------
     * 错误思路提醒：
     *
     * 不要对每个 num 都同时“向左找 + 向右找”，
     * 因为这样会重复统计很多次。
     *
     * 例如 [1, 2, 3, 4, 5]
     * - 从 1 找一遍
     * - 从 2 又找一遍
     * - 从 3 还找一遍
     *
     * 这样会做很多重复工作。
     *
     * 正确做法是：
     * 只让真正的开头去扩展整段连续区间。
     * </pre>
     */
    public static int func(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int maxLen = 0;
        // 所有元素放入集合
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        // 每一次遍历数字
        for (int num : set) {
            // 只有当 num 是连续段开头时，才开始统计
            if (!set.contains(num - 1)) {
                int currentMaxLen = 1;
                int currentNum = num;

                while (!set.contains(currentNum)) {
                    currentNum++;
                    currentMaxLen++;
                }
                maxLen = Math.max(maxLen, currentMaxLen);
            }
        }

        return maxLen;
    }


    public static int longestConsecutive(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int len = 0;
        for (int num : nums) {
            if (!map.containsKey(num)) {
                map.put(num, 1);
                int preLen = map.containsKey(num - 1) ? map.get(num - 1) : 0;
                int posLen = map.containsKey(num + 1) ? map.get(num + 1) : 0;
                int all = preLen + posLen + 1;
                map.put(num - preLen, all);
                map.put(num + posLen, all);
                len = Math.max(len, all);
            }
        }
        return len;
    }

}
