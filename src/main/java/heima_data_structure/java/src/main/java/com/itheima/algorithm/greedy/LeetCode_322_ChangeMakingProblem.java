package heima_data_structure.java.src.main.java.com.itheima.algorithm.greedy;

/**
 * <h3>零钱兑换 - 贪心解法</h3>
 * <p>贪心解法：可能得到错误的解</p>
 */
public class LeetCode_322_ChangeMakingProblem {
    /*
            总金额 18
            5   2   1

            1*5 13
            1*5 8
            1*5 3
            1*2 1
            1*1 0

            贪心的原则：
                1. 每一次都选取当前最优解
                2.《向前看，别回头》

            几个有问题的情况
            总金额 21
            15  10  1
            7 个硬币
            1*15 6
            6*1  0

            3 个硬币
            2*10
            1*1

            总金额 20
            15  10
            1*15 5  无解

            2*10
         */
    public int coinChange(int[] coins, int amount) {
        // 每次循环找到当前最优解：面值最大的硬币，它凑出的硬币数最小
        int remainder = amount; // 18
        int count = 0;
        for (int coin : coins) { // 1
            while (remainder > coin) {
                remainder -= coin; // 1
                count++; // 4
            }
            if (remainder == coin) {
                remainder = 0;
                count++; // 5
                break;
            }
        }
        if (remainder > 0) {
            return -1;
        } else {
            return count;
        }
    }

    public static void main(String[] args) {
        LeetCode_322_ChangeMakingProblem leetcode = new LeetCode_322_ChangeMakingProblem();
//        int count = leetcode.coinChange(new int[]{5, 2, 1}, 18);
//        int count = leetcode.coinChange(new int[]{25, 10, 5, 1}, 41);
//        int count = leetcode.coinChange(new int[]{2}, 3);
        // 问题1 没有回头，导致找到更差的解
//        int count = leetcode.coinChange(new int[]{15, 10, 1}, 21);
        // 问题2 没有回头，导致无解
        int count = leetcode.coinChange(new int[]{15, 10}, 20);
        // int[] array = Arrays.stream(coins).boxed().sorted(Comparator.reverseOrder()).mapToInt(Integer::intValue).toArray();
        System.out.println(count);
    }
}
