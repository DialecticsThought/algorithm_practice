package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author veritas
 * @Data 2024/8/25 10:33
 * 解决思路：
 * 初始化：
 * <p>
 * 频率表：首先创建一个哈希表 tMap，记录字符串 t 中每个字符的频率。我们还需要一个 windowMap，用于记录当前窗口内的字符频率。
 * 滑动窗口：定义两个指针 left 和 right，用于表示当前窗口的左右边界。初始时，left 和 right 都指向字符串 s 的起始位置。
 * 计数器：定义一个变量 formed，表示当前窗口内满足 t 要求的字符数量。我们还需要一个 required 变量，用于表示 t 中不同字符的数量。
 * 结果：初始化一个 ans 数组，用于存储当前最小窗口的长度及其边界，格式为 [window_length, left, right]。初始时设为 [-1, 0, 0]，表示没有找到符合条件的子串。
 * <p>
 * 移动窗口右边界：
 * 扩展窗口：右指针 right 负责扩展窗口，即通过不断向右移动 right 来增加窗口的长度。
 * 添加字符：每当 right 移动时，将 s[right] 对应的字符添加到 windowMap 中，并更新该字符的频率。
 * 检查条件：如果 windowMap 中的字符 s[right] 的频率达到了 tMap 中该字符的频率，表示该字符的要求已经满足，这时我们增加 formed 的值。
 * 右指针继续移动：无论当前窗口是否满足条件，右指针都会继续向右移动，直到遍历完整个字符串 s。
 * <p>
 * 收缩窗口左边界：
 * 收缩窗口：当 formed == required 时，说明当前窗口已经包含了 t 中的所有字符，这时我们尝试通过移动左指针 left 来收缩窗口，以寻找更小的符合条件的子串。
 * 移除字符：每当 left 移动时，将 s[left] 对应的字符从 windowMap 中移除，并更新该字符的频率。
 * 检查条件：如果 windowMap 中的字符 s[left] 的频率低于 tMap 中该字符的频率，说明移除该字符后窗口不再满足条件，这时我们减少 formed 的值。
 * 继续移动左指针：左指针继续向右移动，直到 formed 小于 required，表示窗口不再满足条件，此时停止收缩，重新开始扩展窗口。
 * <p>
 * 记录结果：
 * 更新最小窗口：在收缩窗口过程中，如果当前窗口的长度小于之前记录的最小窗口长度，则更新 ans 数组，记录当前最小窗口的长度及其边界。
 * 继续扩展：一旦窗口不再满足条件（即 formed < required），停止收缩，右指针继续移动，重新扩展窗口
 * <p>
 * 结束：
 * 当 right 指针移动到字符串 s 的末尾时，结束循环。
 * 最终检查 ans 数组。如果 ans[0] == -1，说明没有找到符合条件的子串，返回空字符串。否则，返回 s.substring(ans[1], ans[2] + 1)，即最小窗口对应的子串。
 */
public class Code_76_Minimum_Window_Substring {
    /**
     * <pre>
     * String s = "BAAACCBAB";
     * String t = "AABC";
     * 初始化：
     * tMap = {"A": 2, "B": 1, "C": 1} 记录 t 中每个字符的频率。
     * windowMap = {} 初始化空的窗口字符频率表。
     * left = 0 和 right = 0 初始化窗口的左右边界。
     * formed = 0 表示当前窗口满足 t 要求的字符数。
     * required = 3 因为 t 中有三个不同的字符：A, B, C。
     * ans = {-1, 0, 0} 用于存储最小窗口的长度和边界。
     * 右移窗口右边界：
     *
     * 第一次迭代 (right = 0)：
     * c = 'B'
     * 将 c 加入 windowMap，得到 windowMap = {"B": 1}。
     * windowMap["B"] 达到 tMap["B"] 的要求，formed 增加 1，formed = 1。
     * 右移 right 到下一个位置。
     *
     * 第二次迭代 (right = 1)：
     * c = 'A'
     * 更新 windowMap = {"B": 1, "A": 1}。
     * windowMap["A"] 未达到 tMap["A"] 的要求，formed 仍为 1。
     * 右移 right 到下一个位置。
     *
     * 第三次迭代 (right = 2)：
     * c = 'A'
     * 更新 windowMap = {"B": 1, "A": 2}。
     * windowMap["A"] 达到 tMap["A"] 的要求，formed 增加 1，formed = 2。
     * 右移 right 到下一个位置。
     *
     * 第四次迭代 (right = 3)：
     * c = 'A'
     * 更新 windowMap = {"B": 1, "A": 3}。
     * windowMap["A"] 超过 tMap["A"] 的要求，formed 仍为 2。
     * 右移 right 到下一个位置。
     *
     * 第五次迭代 (right = 4)：
     * c = 'C'
     * 更新 windowMap = {"B": 1, "A": 3, "C": 1}。
     * windowMap["C"] 达到 tMap["C"] 的要求，formed 增加 1，formed = 3。
     * formed == required，进入收缩窗口过程。
     *
     * 收缩窗口左边界：
     * 第一轮收缩 (left = 0)：
     * 当前窗口 "BAAAC" 满足条件。
     * 更新 ans = {5, 0, 4}，表示当前最小窗口长度为 5。
     * 缩小窗口：
     * 移除 left = 0 的字符 'B'，更新 windowMap = {"B": 0, "A": 3, "C": 1}。
     * windowMap["B"] < tMap["B"]，所以 formed = 2。
     * 右移 left 到 1，结束收缩。
     *
     * 第二轮窗口扩大：
     * 右移窗口右边界 (right = 5)：
     * c = 'C'
     * 更新 windowMap = {"B": 0, "A": 3, "C": 2}。
     * windowMap["C"] 超过 tMap["C"] 的要求，formed 仍为 2。
     * 右移 right 到下一个位置。
     * 右移窗口右边界 (right = 6)：
     * c = 'B'
     * 更新 windowMap = {"B": 1, "A": 3, "C": 2}。
     * windowMap["B"] 达到 tMap["B"] 的要求，formed = 3。
     * formed == required，进入收缩窗口过程。
     *
     * 再次收缩窗口：
     * 第二轮收缩 (left = 1)：
     * 当前窗口 "AAACCB" 满足条件。
     * 更新 ans = {6, 1, 6}，表示当前最小窗口长度为 6。
     * 缩小窗口：
     * 移除 left = 1 的字符 'A'，更新 windowMap = {"B": 1, "A": 2, "C": 2}。
     * windowMap["A"] 仍满足 tMap["A"] 的要求，formed 仍为 3。
     * 右移 left 到 2，继续收缩。
     *
     * 第三轮收缩 (left = 2)：
     * 当前窗口 "AACCB" 满足条件。
     * 更新 ans = {5, 2, 6}，表示当前最小窗口长度为 5。
     * 缩小窗口：
     * 移除 left = 2 的字符 'A'，更新 windowMap = {"B": 1, "A": 1, "C": 2}。
     * windowMap["A"] 仍满足 tMap["A"] 的要求，formed 仍为 3。
     * 右移 left 到 3，继续收缩。
     *
     * 第四轮收缩 (left = 3)：
     * 当前窗口 "ACCB" 满足条件。
     * 更新 ans = {4, 3, 6}，表示当前最小窗口长度为 4。
     * 缩小窗口：
     * 移除 left = 3 的字符 'A'，更新 windowMap = {"B": 1, "A": 0, "C": 2}。
     * windowMap["A"] < tMap["A"]，所以 formed = 2。
     * 右移 left 到 4，结束收缩。
     *
     * 结束：
     * 继续右移 right，直到 right 超过 s 的长度，结束循环。
     * 最终最小窗口为 "ACCB"，起始位置在 left = 3，结束位置在 right = 6。
     * </pre>
     *
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {
        if (s == null || s.length() == 0 || t == null || t.length() == 0) {
            return "";
        }
        // 记录t中字符的频率
        Map<Character, Integer> tMap = new HashMap<>();
        for (char c : t.toCharArray()) {
            tMap.put(c, tMap.getOrDefault(c, 0) + 1);
        }
        // 滑动窗口中的字符频率
        Map<Character, Integer> windowMap = new HashMap<>();
        int left = 0, right = 0;
        int formed = 0; // 记录当前窗口中满足tMap要求的字符数量
        int required = tMap.size(); // 需要满足的不同字符的数量
        int[] ans = {-1, 0, 0}; // 最优窗口 (长度, 左边界, 右边界)
        // 开始滑动窗口
        while (right < s.length()) {
            // 将s[right]加入窗口
            char c = s.charAt(right);
            windowMap.put(c, windowMap.getOrDefault(c, 0) + 1);
            // 如果窗口内字符c的频率达到了tMap中的要求，formed加1
            if (tMap.containsKey(c) && windowMap.get(c).intValue() == tMap.get(c).intValue()) {
                formed++;
            }
            // 当formed等于required时，尝试收缩窗口
            while (left <= right && formed == required) {
                c = s.charAt(left);
                // 更新最优窗口
                if (ans[0] == -1 || right - left + 1 < ans[0]) {
                    ans[0] = right - left + 1;
                    ans[1] = left;
                    ans[2] = right;
                }
                // 减少窗口内c的频率
                windowMap.put(c, windowMap.get(c) - 1);
                if (tMap.containsKey(c) && windowMap.get(c).intValue() < tMap.get(c).intValue()) {
                    formed--;
                }
                // 左边界右移，缩小窗口
                left++;
            }
            // 右边界右移，扩大窗口
            right++;
        }
        // 如果ans[0] == -1，说明没有找到合适的窗口
        return ans[0] == -1 ? "" : s.substring(ans[1], ans[2] + 1);
    }
}
