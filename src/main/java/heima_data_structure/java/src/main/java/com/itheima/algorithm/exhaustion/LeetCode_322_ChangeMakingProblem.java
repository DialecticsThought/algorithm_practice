package heima_data_structure.java.src.main.java.com.itheima.algorithm.exhaustion;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h3>零钱兑换 - 穷举解法</h3>
 * <p>凑成总金额的凑法中，需要硬币最少个数是几？</p>
 */
public class LeetCode_322_ChangeMakingProblem {
    static int min = -1; // 需要的最少硬币数  2 3

    public int coinChange(int[] coins, int amount) {
        LinkedList<Integer> stack = new LinkedList<>();
        for (int i = 0; i < coins.length; i++) {
            rec(i, coins, amount - coins[i], new AtomicInteger(0), stack);
        }
        return min;
    }

    // count 代表某一组合 钱币的总数
    public void rec(int index, int[] coins, int remainder, AtomicInteger count, LinkedList<Integer> stack) {
        stack.push(coins[index]);
        count.incrementAndGet(); // count++
        if (remainder == 0) {
            System.out.println(stack);
            if (min == -1) {
                min = count.get();
            } else {
                min = Integer.min(min, count.get());
            }
        } else if (remainder > 0) {
            for (int i = index; i < coins.length; i++) {
                rec(i, coins, remainder - coins[i], count, stack);
            }
        }
        count.decrementAndGet(); // count--
        stack.pop();
    }

    public static void main(String[] args) {
        LeetCode_322_ChangeMakingProblem leetcode = new LeetCode_322_ChangeMakingProblem();
        int count = leetcode.coinChange(new int[]{5, 2, 1}, 5);
//        int count = leetcode.coinChange(new int[]{25, 10, 5, 1}, 41);
//        int count = leetcode.coinChange(new int[]{2}, 3);
//        int count = leetcode.coinChange(new int[]{15, 10, 1}, 21);
        System.out.println(count);
    }
}
