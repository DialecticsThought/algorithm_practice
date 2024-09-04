package heima_data_structure.java.src.main.java.com.itheima.algorithm.backtracking;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <h3>组合 回溯</h3>
 */
public class Code_077_Combination {
    // 此 n 代表数字范围, 1~n
    static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(1, n, k, new LinkedList<>(), result);
        return result;
    }

    // start 起始处理数字
    static void dfs(int start, int n, int k,
                    LinkedList<Integer> stack,
                    List<List<Integer>> result) {
        if (stack.size() == k) {
            result.add(new ArrayList<>(stack));
            return;
        }
        for (int i = start; i <= n ; i++) {
            //  还差几个数字          剩余可用数字
            if (k - stack.size() > n - i + 1) {
                continue;
            }
            stack.push(i);
            dfs(i + 1, n, k, stack, result);
            stack.pop();
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> lists = combine(4, 3);
        for (List<Integer> list : lists) {
            System.out.println(list);
        }
        /*
            n   数字范围 1 ~ 4
            k   数字个数

            12
            13
            14
            23
            24
            34
         */
    }
}
