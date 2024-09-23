package heima_data_structure.java.src.main.java.com.itheima.algorithm.backtracking;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <h3>组合 回溯</h3>
 */
public class LeetCode_077_Combination {
    // 此 n 代表数字范围, 1~n
    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(1, n, k, new LinkedList<>(), result);
        return result;
    }


    /**
     * @param start  起始处理数字  因为下一次选择 是从 start + 1开始  TODO 这里我们在求组合 不是求排列
     *               TODO 对于组合 [1,2] == [2,1]    对于排列 [1,2] != [2,1]
     *               eg:
     *               n = 4 => 说明数字范围是1 ~ 4 ,k = 2
     *               一开始是选择1 ，接下来就是选择 2 ~ 4 的某一个数
     *               一开始是选择2 ，接下来就是选择 3 ~ 4 的某一个数  ==> 因为 1,2 作为一个组合之前选择过了 不用重复选择
     * @param n      数字范围 1 ~ 4
     * @param k      数字个数
     * @param stack  中间结果的容器
     * @param result 最终结果的容器
     */
    public static void dfs(int start, int n, int k,
                           LinkedList<Integer> stack,
                           List<List<Integer>> result) {
        // base case 找到了k个数字
        if (stack.size() == k) {
            result.add(new ArrayList<>(stack));
            return;
        }
        // 从start开始选择 ,选择范围: start ~ n
        for (int i = start; i <= n; i++) {
            //
            /**
             * TODO pruning 剪枝操作
             * 求出 还差几个数字:k - stack.size()
             * 求出 剩余可用数字:n - i + 1
             */
            //
            if (k - stack.size() > n - i + 1) {
                continue;
            }
            // 当前数字i 作为选择
            stack.push(i);
            // 递归 此时 i+1 可以看start注释的解释
            dfs(i + 1, n, k, stack, result);
            // 回溯 数字i的选择
            stack.pop();
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> lists = combine(4, 3);
        for (List<Integer> list : lists) {
            System.out.println(list);
        }
    }


}
