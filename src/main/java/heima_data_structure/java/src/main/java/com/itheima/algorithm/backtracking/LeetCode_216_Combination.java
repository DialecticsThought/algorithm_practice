package heima_data_structure.java.src.main.java.com.itheima.algorithm.backtracking;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <h3>组合总和 III 回溯</h3>
 */
public class LeetCode_216_Combination {
    // 此 target 代表数字组合后的和
    static List<List<Integer>> combinationSum3(int k, int target) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(1, target, k, new LinkedList<>(), result);
        return result;
    }

    static int count = 0;

    static void dfs(int start, int target, int k,
                    LinkedList<Integer> stack,
                    List<List<Integer>> result) {
//        System.out.println(stack);
        count++;
        if (target == 0 && stack.size() == k) {
            result.add(new ArrayList<>(stack));
            return;
        }
        for (int i = start; i <= 9; i++) {
            //  还差几个数字          剩余可用数字
            /*if (k - stack.size() > 9 - i + 1) {
                continue;
            }*/
            if(target < i){
                continue;
            }
            if(stack.size() == k) {
                continue;
            }
            stack.push(i);
            dfs(i + 1, target - i, k, stack, result);
            stack.pop();
        }
    }

    public static void main(String[] args) {
//        List<List<Integer>> lists = combinationSum3(3, 7);
        List<List<Integer>> lists = combinationSum3(2, 18); // 9 8
        for (List<Integer> list : lists) {
            System.out.println(list);
        }
        System.out.println(count);
    }
}
