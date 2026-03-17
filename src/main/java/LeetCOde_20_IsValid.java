/**
 * @description
 * @author jiahao.liu
 * @date 2026/03/16 22:24
 */

import java.util.Map;
import java.util.Stack;

/**
 * https://leetcode.cn/problems/valid-parentheses/
 * https://www.bilibili.com/video/BV1yYwJzJE69
 * @Description
 * @Author jiahao.liu
 * @Data 2026/3/16 22:24
 */
public class LeetCOde_20_IsValid {

    public boolean isValid(String s) {
        Stack<String> stack = new Stack<>();

        Map<String, String> map = Map.of(")", "(", "]", "[", "}", "{");

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(String.valueOf(c))) {
                if (!stack.getLast().equals(map.get(String.valueOf(c))) || stack.isEmpty()) {
                    return false;
                } else {
                    stack.pop();
                }
            } else {
                stack.addLast(String.valueOf(c));
            }
        }
        return stack.size() == 0;
    }
}
