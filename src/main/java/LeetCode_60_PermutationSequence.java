import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author veritas
 * @Data 2025/3/3 17:36
 */
public class LeetCode_60_PermutationSequence {

    public String getPermutation(int n, int k) {
        boolean[] booleans = new boolean[n];
        List<String> result = new ArrayList<>();
        generatePermutations(n, 0, new StringBuilder(), result, booleans);

        return result.get(k-1);
    }

    /**
     * @param n       一共几个数
     * @param counter 当前遍历了几个数了
     * @param result 存放结果
     * @param visited 哪些数被使用过了
     */
    public void generatePermutations(int n, int counter, StringBuilder path, List<String> result, boolean[] visited) {
        // base case
        if (n == counter) {
            result.add(path.toString());
            return;
        }
        // 生成的排列是按照字典序（或一种固定的顺序）排列的
        // for循环 每次都是从1开始到n依次尝试，因此候选数字始终以升序被选取
        for (int i = 1; i <= n; i++) {
            // 当前数字 i 没有被使用过
            if (!visited[i - 1]) {
                // 设置标记位
                visited[i - 1] = true;
                // 中间结果 更新
                path.append(i);
                // 继续下一层递归
                generatePermutations(n, counter + 1, path, result, visited);
                // 恢复现场
                visited[i - 1] = false;
                path.deleteCharAt(path.length() - 1);
            }
        }
    }

    public class Solution {
        // 全局计数器，用于记录当前生成排列的数量
        private int count = 0;
        // 用于保存第 k 个排列结果，一旦找到，就不再继续递归
        private String kthPermutation = null;

        public String getPermutation(int n, int k) {
            boolean[] visited = new boolean[n];  // 标记数字 1~n 是否已经使用
            StringBuilder path = new StringBuilder();
            backtrack(n, k, path, visited);
            return kthPermutation;
        }

        // 递归回溯生成排列
        private void backtrack(int n, int k, StringBuilder path, boolean[] visited) {
            // 当排列长度达到 n，说明生成了一个完整排列
            if (path.length() == n) {
                count++;
                if (count == k) {
                    kthPermutation = path.toString();
                }
                return;
            }

            // 按从 1 到 n 的顺序尝试（保证了字典序的顺序）
            for (int i = 1; i <= n; i++) {
                if (visited[i - 1]) {
                    continue;
                }
                visited[i - 1] = true;
                path.append(i);

                backtrack(n, k, path, visited);

                // 如果已经找到了第 k 个排列，则可以提前返回，避免不必要的计算
                if (kthPermutation != null) {
                    return;
                }

                // 回溯：撤销选择
                path.deleteCharAt(path.length() - 1);
                visited[i - 1] = false;
            }
        }
    }
}
