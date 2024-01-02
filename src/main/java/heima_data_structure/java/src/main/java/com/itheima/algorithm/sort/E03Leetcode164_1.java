package heima_data_structure.java.src.main.java.com.itheima.algorithm.sort;


import heima_data_structure.java.src.main.java.com.itheima.datastructure.array.DynamicArray;

public class E03Leetcode164_1 {
    public int maximumGap(int[] nums) {
        // 1. 处理特殊情况
        if (nums.length < 2) {
            return 0;
        }
        // 2. 桶排序
        int max = nums[0];
        int min = nums[0];
        for (int i1 = 1; i1 < nums.length; i1++) {
            if (nums[i1] > max) {
                max = nums[i1];
            }
            if (nums[i1] < min) {
                min = nums[i1];
            }
        }
        // 1. 准备桶
        DynamicArray[] buckets = new DynamicArray[(max - min) / 1 + 1];
        for (int i1 = 0; i1 < buckets.length; i1++) {
            buckets[i1] = new DynamicArray();
        }
        // 2. 放入数据
        for (int age : nums) {
            buckets[(age - min) / 1].addLast(age);
        }
        int k = 0;
        for (DynamicArray bucket : buckets) {
            // 3. 排序桶内元素
            int[] array = bucket.array();
            InsertionSort.sort(array);
//            System.out.println(Arrays.toString(array));
            // 4. 把每个桶排序好的内容，依次放入原始数组
            for (int v : array) {
                nums[k++] = v;
            }
        }
        //        System.out.println(Arrays.toString(nums));
        // 3. 寻找最大差值
        int r = 0;
        for (int i = 1; i < nums.length; i++) {
            r = Math.max(r, nums[i] - nums[i - 1]);
        }
        return r;
    }

    public static void main(String[] args) {
//        int[] nums = {9, 1, 3, 5};
        int[] nums = {1, 10000000};
        int r = new E03Leetcode164_1().maximumGap(nums);
        System.out.println(r);
    }
}
