package heima_data_structure.java.src.main.java.com.itheima.algorithm.dynamicprogramming;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * <h3>0-1 背包问题 - 动态规划</h3>
 */
public class KnapsackProblem {
    /*
        1. n个物品都是固体，有重量和价值
        2. 现在你要取走不超过 10克 的物品
        3. 每次可以不拿或全拿，问最高价值是多少

            编号 重量(g)  价值(元)                        简称
            1   4       1600           黄金一块   400     A
            2   8       2400           红宝石一粒 300     R
            3   5       30             白银一块          S
            4   1       1_000_000      钻石一粒          D
        1_001_630 贪心解
        1_002_400 正确解
     */

    /*
            0   1   2   3   4   5   6   7   8   9   10
        0   0   0   0   0   A   A   A   A   A   A   A       黄金

        1   0   0   0   0   A   A   A   A   R   R   R       红宝石

        2   0   0   0   0   A   A   A   A   R   R   R       白银

        3   0   D   D   D   D   DA  DA  DA  DA  DR  DR      钻石



        if(装不下) {
            dp[i][j] = dp[i-1][j]
        } else { 装得下
            dp[i][j] = max(dp[i-1][j], item.value + dp[i-1][j-item.weight])
        }
     */

    static class Item {
        int index;
        String name;
        int weight;
        int value;

        public Item(int index, String name, int weight, int value) {
            this.index = index;
            this.name = name;
            this.weight = weight;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Item(" + name + ")";
        }
    }

    public static void main(String[] args) {
        Item[] items = new Item[]{
                new Item(1, "黄金", 4, 1600),
                new Item(2, "宝石", 8, 2400),
                new Item(3, "白银", 5, 30),
                new Item(4, "钻石", 1, 10_000),
        };
        System.out.println(select(items, 10));
    }

    static int select(Item[] items, int total) {
        int[] dp = new int[total + 1];
        for (Item item : items) {
            for (int j = total; j > 0; j--) {
                if (j >= item.weight) { // 装得下
                    dp[j] = Integer.max(dp[j], item.value + dp[j - item.weight]);
                }
            }
            System.out.println(Arrays.toString(dp));
        }
        return dp[total];
    }

    static int select2(Item[] items, int total) {
        int[][] dp = new int[items.length][total + 1];
        Item item0 = items[0];
        for (int j = 0; j < total + 1; j++) {
            if (j >= item0.weight) { // 装得下
                dp[0][j] = item0.value;
            } else {                 // 装不下
                dp[0][j] = 0;
            }
        }
        print(dp);
        for (int i = 1; i < dp.length; i++) {
            Item item = items[i];
            for (int j = 0; j < total + 1; j++) {
                int x = dp[i - 1][j];
                if (j >= item.weight) {
                    dp[i][j] = Integer.max(x,
//                            上次剩余空间能装的最大价值 + 当前物品价值
                            dp[i - 1][j - item.weight] + item.value);
                } else {
                    dp[i][j] = x;
                }
            }
            print(dp);
        }
        return dp[dp.length - 1][total];
    }

    static void print(int[][] dp) {
        System.out.println("   " + "-".repeat(63));
        Object[] array = IntStream.range(0, dp[0].length + 1).boxed().toArray();
        System.out.printf(("%5d ".repeat(dp[0].length)) + "%n", array);
        for (int[] d : dp) {
            array = Arrays.stream(d).boxed().toArray();
            System.out.printf(("%5d ".repeat(d.length)) + "%n", array);
        }
    }
}
