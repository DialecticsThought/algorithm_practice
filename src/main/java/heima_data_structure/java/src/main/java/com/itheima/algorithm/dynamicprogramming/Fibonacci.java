package heima_data_structure.java.src.main.java.com.itheima.algorithm.dynamicprogramming;

/**
 * 求斐波那契数列的第n项（动态规划）
 */
public class Fibonacci {
    public static void main(String[] args) {
        System.out.println(fibonacci2(13));
    }
    /*
        要点1：
            从已知子问题的解，推导出当前问题的解
            推导过程可以表达为一个数学公式
        要点2：
            用一维或二维数组来保存之前的计算结果（可以进一步优化）

        Dynamic-Programming - 由 Bellman 提出
        动态     编程
            Programming - 在这里指用数学方法来根据子问题求解当前问题（通俗理解就是找到递推公式）
            Dynamic     - 指缓存上一步结果，根据上一步结果计算当前结果（多阶段进行）

        合在一起：
            找出递归公式，将当前问题分解成子问题，分阶段进行求解。
            求解过程中缓存子问题的解，避免重复计算。
     */
    public static int fibonacci2(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int a = 0;
        int b = 1;
        for (int i = 2; i <= n ; i++) {
            int c = b + a;
            a = b;
            b = c;
        }
        return b;
    }

    public static int fibonacci(int n) {
        int[] dp = new int[n + 1]; // 用来缓存结果
        if (n == 0) {
            dp[0] = 0;
            return 0;
        }
        if (n == 1) {
            dp[1] = 1;
            return 1;
        }
        for (int i = 2; i <= n ; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }
}
