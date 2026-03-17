package heima_data_structure.java.src.main.java.com.itheima.algorithm.dynamicprogramming;

import java.util.Arrays;

/**
 * <h3>最长公共子串</h3>
 */
public class LCSubstring {


    public static int lcs2(char[] arr1, char[] arr2, int i, int j) {
        // base case
        if(i== arr1.length||j== arr2.length){
            return 0;
        }
        // 如果当前arr1的i位置和arr2的j位置字符相同，那么两个数组都看当前位置的下一个位置
        if (arr1[i] == arr2[2]) {
            return lcs2(arr1, arr2, i + 1, j + 1);
        }
        // 如果当前arr1的i位置和arr2的j位置字符步相同，
        //  固定arr1的数组位置，看arr2的当前位置的下一个位置和 arr1的当前固定住的位置的字符串
        //  固定arr2的数组位置，看arr1的当前位置的下一个位置和 arr2的当前固定住的位置的字符串
        //if (arr1[i] != arr2[2]) {
        int case1 = lcs2(arr1, arr2, i, j + 1);
        int case2 = lcs2(arr1, arr2, i + 1, j);

        return Math.max(case1, case2);
    }

    static int lcs(String a, String b) {
        int[][] dp = new int[b.length()][a.length()];
        int max = 0;
        for (int i = 0; i < b.length(); i++) {
            for (int j = 0; j < a.length(); j++) {
                if (a.charAt(j) == b.charAt(i)) {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    }
                    max = Integer.max(max, dp[i][j]);
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        print(dp, a, b);
        return max;
    }

    static void print(int[][] dp, String a, String b) {
        System.out.println("-".repeat(23));
        Object[] array = a.chars().mapToObj(i -> String.valueOf((char) i)).toArray();
        System.out.printf("  " + "%2s ".repeat(a.length()) + "%n", array);
        for (int i = 0; i < b.length(); i++) {
            int[] d = dp[i];
            array = Arrays.stream(d).boxed().toArray();
            System.out.printf(b.charAt(i) + " " + "%2d ".repeat(d.length) + "%n", array);
        }
    }

    /**
     * i   t   h   e   i   m   a
     * t   0   1   0   0   0   0   0
     * h   0   0   2   0   0   0   0
     * e   0   0   0   3   0   0   0
     * m   0   0   0   0   0   1   0
     * a   0   0   0   0   0   0   2
     * if(相同字符) {
     * dp[i][j] = dp[i-1][j-1] + 1
     * } else {
     * dp[i][j] = 0
     * }
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(lcs("itheima", "thema"));
    }
}
