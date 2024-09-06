package heima_data_structure.java.src.main.java.com.itheima.algorithm.exhaustion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * <h3>零钱兑换 II</h3>
 * <p>可以凑成总金额所需的所有组合可能数</p>
 */
public class LeetCode_518_ChangeMakingProblem_Improved2 {
    static int level = -2;

    public int coinChange(int[] coins, int amount) {
        int count = 0;
        LinkedList<Integer> stack = new LinkedList<>();
        for (int i = 0; i < coins.length; i++) {
            count += rec(i, coins, amount - coins[i], stack);
        }
        return count;
    }

    public int rec(int index, int[] coins, int remainder, LinkedList<Integer> stack) {
        stack.push(coins[index]);
        try {
            if (remainder < 0) {
                print("无解:", stack);
                return 0;
            } else if (remainder == 0) {
                print("有解:", stack);
                return 1;
            } else {
                int count = 0;
                for (int i = index; i < coins.length; i++) {
                    count += rec(i, coins, remainder - coins[i], stack);
                }
                return count;
            }
        } finally {
            stack.pop();
        }
    }

    private static void print(String prompt, LinkedList<Integer> stack) {
        ArrayList<Integer> print = new ArrayList<>();
        ListIterator<Integer> iterator = stack.listIterator(stack.size());
        while (iterator.hasPrevious()) {
            print.add(iterator.previous());
        }
        System.out.println(prompt + print);
    }


    public static void main(String[] args) {
        LeetCode_518_ChangeMakingProblem_Improved2 leetcode = new LeetCode_518_ChangeMakingProblem_Improved2();
//        int count = leetcode.coinChange(new int[]{1, 5, 10, 25}, 41);
//        int count = leetcode.coinChange(new int[]{5, 2, 1}, 5);
        int count = leetcode.coinChange(new int[]{1, 2, 5}, 5);
        System.out.println(count);
    }
}
