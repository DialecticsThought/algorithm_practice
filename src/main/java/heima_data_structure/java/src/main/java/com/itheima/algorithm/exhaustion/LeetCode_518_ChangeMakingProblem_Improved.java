package heima_data_structure.java.src.main.java.com.itheima.algorithm.exhaustion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * <h3>零钱兑换 II</h3>
 * <p>可以凑成总金额所需的所有组合可能数</p>
 */
public class LeetCode_518_ChangeMakingProblem_Improved {
    static int level = -2;
    public int coinChange(int[] coins, int amount) {
        return rec(0, coins, amount, new LinkedList<>(), true);
    }

    public int rec(int index, int[] coins, int remainder, LinkedList<Integer> stack, boolean first) {
        level++;
//        if (level > 0) {
//            System.out.print("|");
//        }
        for (int i = 0; i < level; i++) {
            System.out.print("| ");
        }
        System.out.println("rec(" + coins[index] + "," + remainder+")");
        if (!first) {
            stack.push(coins[index]);
        }
        int count = 0;
        if (remainder < 0) {
//            print("无解:", stack);
        } else if (remainder == 0) {
//            print("有解:", stack);
            count = 1;
        } else {
            for (int i = index; i < coins.length; i++) {
                count += rec(i, coins, remainder - coins[i], stack, false);
            }
        }
        if (!stack.isEmpty()) {
            stack.pop();
        }
        level--;
        return count;
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
        LeetCode_518_ChangeMakingProblem_Improved leetcode = new LeetCode_518_ChangeMakingProblem_Improved();
//        int count = leetcode.coinChange(new int[]{1, 5, 10, 25}, 41);
        int count = leetcode.coinChange(new int[]{5, 2, 1}, 5);
//        int count = leetcode.coinChange(new int[]{1, 2, 5}, 5);
        System.out.println(count);
    }
}
