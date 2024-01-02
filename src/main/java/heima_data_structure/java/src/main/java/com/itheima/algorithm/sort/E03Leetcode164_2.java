package heima_data_structure.java.src.main.java.com.itheima.algorithm.sort;

import java.util.ArrayList;
import java.util.Arrays;

public class E03Leetcode164_2 {
    public int maximumGap(int[] nums) {
        // 1. 处理特殊情况
        if (nums.length < 2) {
            return 0;
        }
        // 2. 基数排序
        // 2.1 准备工作
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            max = Math.max(max, nums[i]);
        }
        ArrayList<Integer>[] buckets = new ArrayList[10];
        for (int i = 0; i < 10; i++) {
            buckets[i] = new ArrayList<>();
        }
        // 2.2 一轮基数排序 O(6n) O(n)
        int m = 1;
        while(m <= max) {
            for (int i : nums) {
                buckets[i / m % 10].add(i);
            }
            int k = 0;
            for (ArrayList<Integer> bucket : buckets) {
//            System.out.println(bucket);
                for (Integer i : bucket) {
                    nums[k++] = i;
                }
                bucket.clear();
            }
            System.out.println(Arrays.toString(nums));
            m = m * 10;
        }

        // 3. 寻找最大差值
        int r = 0;
        for (int i = 1; i < nums.length; i++) {
            r = Math.max(r, nums[i] - nums[i - 1]);
        }
        return r;
    }

    // 4233 / 10 = 423 % 10 = 3
    // 4233 / 100 = 42 % 10 = 2
    // 4233 / 1000 = 4 % 10 = 4
    // 4233 / 10000 = 0 % 10 = 0

    /*
        0   11 13 16 26
        1   100000
        2
        3
        4
        5
        6
        7
        8
        9

        100000 11 13 26 16  个位排序结果
        100000 11 13 16 26  十位排序结果
        11 13 16 26 100000  最终排序结果
     */
    public static void main(String[] args) {
        int[] nums = {26, 16, 13, 11, 100000};
        int r = new E03Leetcode164_2().maximumGap(nums);
        System.out.println(r);
    }
}
