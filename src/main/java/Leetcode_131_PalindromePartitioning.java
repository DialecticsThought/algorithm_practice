import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author veritas
 * @Data 2025/3/12 20:15
 */
public class Leetcode_131_PalindromePartitioning {

    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<List<String>>();
        List<String> path = new ArrayList<>();
        process(s, result, path, 0);
        return result;
    }

    /**
     *
     * @param s 输入的字符串，即需要被拆分的目标字符串
     * @param result 存储所有符合条件的回文分割方案的集合，当递归结束时，每一个完整的分割方案都会被加入到这个集合中
     * @param path 记录当前递归路径上选中的回文子串序列，是一种临时保存分割结果的容器
     * @param start 当前递归过程中字符串的起始索引，表示从该位置开始尝试分割
     */
    public void process(String s, List<List<String>> result, List<String> path, int start) {
        // base case
        // 如果 start 达到字符串的末尾，说明已经将整个字符串成功分割成了回文子串，此时将当前路径 path 的副本添加到 result 中
        if (start == s.length()) {
            result.add(new ArrayList<>(path));
            return;
        }
        // 为什么会有end = s.length()长度 因为 看 base case
        // 对于每个子串 s[start:end+1]，判断是否为回文
        for (int end = start + 1; end <= s.length(); end++) {
            // 判断 start ~ end - 1 之间的子串是否是回文
            // end - 1 是因为 end最大值可以达到s.length()
            if (isPalindrome(s, start, end - 1)) {
                // 把当前选择到的回文子串 放入 path中
                path.add(s.substring(start, end));
                // 继续当前节点的下一层的遍历 这里 新的start就是end
                // 递归调用 process 从 end 位置开始继续处理剩下的部分
                // 这里不是end+1 是因为 和 for 循环的起始有关系 来到当前节点的时候 end = start+1
                process(s, result, path, end);
                // 遍历回来之后 恢复状态 遍历当前层的其他节点
                path.remove(path.size() - 1);
            }
        }
        // 此时for循环结束 也就是当前层的所有节点遍历结束
    }

    public boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
