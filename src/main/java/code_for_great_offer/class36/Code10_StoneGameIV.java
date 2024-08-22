package code_for_great_offer.class36;

// 来自哈喽单车
// 本题是leetcode原题 : https://leetcode.com/problems/stone-game-iv/
public class Code10_StoneGameIV {
    /*
    * Alice 和 Bob 两个人轮流玩一个游戏，Alice 先手。
    一开始，有 n个石子堆在一起。每个人轮流操作，正在操作的玩家可以从石子堆里拿走 任意非零 平方数个石子。
    如果石子堆里没有石子了，则无法操作的玩家输掉游戏。
    给你正整数n，且已知两个人都采取最优策略。如果 Alice 会赢得比赛，那么返回True，否则返回False。
    链接：https://leetcode.cn/problems/stone-game-iv
    * */
    /*
     *TODO
     * 返回当前的！先手，会不会赢
     * 打表，不能发现规律
     * 拿走一个数之后 那么整体的数会减去拿走的数
     * 比如 一共1003 有平方数 1 4 9 16 .....
     * 先手拿了16 整体变成 1003-16
     * 谁先把数字 减成0 谁赢 或者说 谁最先面对0 谁输
     *TODO
     * 假设 一开始 先手 是A 后手是B 初始分数1003
     * 那么A调用f(1003) 假设 A拿走了6  对于 f(1003) 而言 A是先手
     * 那么B就会调用f(987) 对于f(987) 而言 B是先手
     * */
    public static boolean winnerSquareGame1(int n) {
        if (n == 0) {
            return false;
        }
        /*
        *TODO
        * 当前轮的先手，会尝试所有的情况，1，4，9，16，25，36.... 枚举行为
        * */
        for (int i = 1; i * i <= n; i++) {
            /*
            *TODO
            * 前的先手，决定拿走 i * i 这个平方数
            * 它的对手会不会赢？ winnerSquareGame1(n - i * i)
            * 当前的先手 调用winnerSquareGame1(int n)
            * 当前的后手 调用winnerSquareGame1(n - i * i)
            * 如果 当前的先手 做出的决定下（意思就是 调用winnerSquareGame1(int n)）
            * 对手做出的决定（调用winnerSquareGame1(n - i * i)）没有赢
            * 那么就是当前的先手赢了
             * */
            if (!winnerSquareGame1(n - i * i)) {
                return true;
            }
        }
        return false;
    }

    public static boolean winnerSquareGame2(int n) {
        int[] dp = new int[n + 1];
        dp[0] = -1;
        return process2(n, dp);
    }

    public static boolean process2(int n, int[] dp) {
        if (dp[n] != 0) {
            return dp[n] == 1 ? true : false;
        }
        boolean ans = false;
        for (int i = 1; i * i <= n; i++) {
            if (!process2(n - i * i, dp)) {
                ans = true;
                break;
            }
        }
        dp[n] = ans ? 1 : -1;
        return ans;
    }

    public static boolean winnerSquareGame3(int n) {
        boolean[] dp = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j * j <= i; j++) {
                if (!dp[i - j * j]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        int n = 10000000;
        System.out.println(winnerSquareGame3(n));
    }

}
