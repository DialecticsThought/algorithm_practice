package heima_data_structure.java.src.main.java.com.itheima.algorithm.dynamicprogramming;

import java.util.Arrays;

public class Catalan {
    public static void main(String[] args) {
        System.out.println(catalan(6));
    }

    static int catalan(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int j = 2; j < n + 1; j++) {
            for (int i = 0; i < j; i++) { // 第j个卡特兰数的拆分
//                System.out.printf("(%d,%d)\t", i, j - 1 - i);
                dp[j] += dp[i] * dp[j - 1 - i];
            }
//            System.out.println();
//            System.out.println(Arrays.toString(dp));
        }

        return dp[n];
    }
}
