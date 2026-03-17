import java.util.HashSet;
import java.util.Set;

/**
 * @Description
 * @Author veritas
 * @Data 2025/3/7 22:29
 */
public class LeetCode_128_LongestConsecutiveSequence {

    public int longestConsecutive(int[] nums) {
        Set<Integer> numSet = new HashSet<>();

        for (int num : nums) {
            numSet.add(num);
        }

        int longestStreak = 0;
        // 遍历集合中的每个数字
        for (int num : numSet) {
            if (!numSet.contains(num - 1)) {
                // 以当前被遍历到的数字作为开头
                int start = num;
                // 以当前被遍历到的数字作为开头的最长子序列
                int currentStreak = 1;
                // start 不断 +1 然后判断是否在numSet存在这个数
                // 存在就说明这个子序列的长度+1
                // eg: start = 100
                // 判断101在不在numSet里面 存在的话判断102在不在numSet里面。。
                while (numSet.contains(start + 1)) {
                    start = start + 1;
                    currentStreak = currentStreak + 1;
                }
                // 循环出来 就说明 以当前num为开头的子序列求完了
                // 作比较
                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }
        return longestStreak;
    }
}
