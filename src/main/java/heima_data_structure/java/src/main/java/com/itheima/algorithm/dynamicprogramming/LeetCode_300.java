package heima_data_structure.java.src.main.java.com.itheima.algorithm.dynamicprogramming;

import java.util.Arrays;

/**
 * <h3>最长递增子序列</h3>
 */
public class LeetCode_300 {
    /*
                    1       2       3       4
            1       3       6       4       9
            1       13      16      14      19
                            136     134     139
                                            169
                                            1369
                                            149
                                            1349
           (1)    (2)      (3)     (3)      (4)
                                            4
     */
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) { // 满足了升序条件
                    // 用之前递增子序列的最大长度 + 1 更新当前长度
                    dp[i] = Integer.max(dp[i], dp[j] + 1);
                }
            }
            System.out.println(Arrays.toString(dp));
        }
        return Arrays.stream(dp).max().getAsInt();
    }

    public static void main(String[] args) {
        LeetCode_300 code = new LeetCode_300();
        System.out.println(code.lengthOfLIS(new int[]{1, 3, 6, 4, 9}));
//        System.out.println(code.lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18}));
//        System.out.println(code.lengthOfLIS(new int[]{1, 3, 6, 7, 9, 4, 10, 5, 6}));
        //                                            1 3 6 7 9 10  = 6
        //                                            1 3 4 5 6     = 5
//        System.out.println(code.lengthOfLIS(new int[]{0, 1, 0, 3, 2, 3}));
//        System.out.println(code.lengthOfLIS(new int[]{7, 7, 7, 7, 7, 7, 7}));
    }
}
