package heima_data_structure.java.src.main.java.com.itheima.algorithm.sort;

import heima_data_structure.java.src.main.java.com.itheima.datastructure.array.DynamicArray;

import java.util.Arrays;

/**
 * <h3>桶排序</h3>
 */
public class BucketSort {
    public static void main(String[] args) {
        int[] ages = {20, 18, 28, 66, 25, 31, 67, 30}; // 假设人类年龄 1~99
        System.out.println(Arrays.toString(ages));
        sort(ages);
        System.out.println(Arrays.toString(ages));
    }

    /**
     * 下面 每一行 就是一个桶
     * 遍历原始数组 ages 依次放入 桶中 根据元素的十位 决定
     * 0
     * 1   18
     * 2   20 25 28
     * 3   31
     * 4
     * 5
     * 6   66 67
     * 7
     * 8
     * 9
     */
    public static void sort(int[] ages) {
        // 1. 准备桶
        DynamicArray[] buckets = new DynamicArray[10];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new DynamicArray();
        }
        // 2. 放入年龄数据
        for (int age : ages) {
            buckets[age / 10].addLast(age);
        }
        int k = 0;
        for (DynamicArray bucket : buckets) {
            // 3. 排序桶内元素
            int[] array = bucket.array();
            InsertionSort.sort(array);
            System.out.println(Arrays.toString(array));
            // 4. 把每个桶排序好的内容，依次放入原始数组
            for (int v : array) {
                ages[k++] = v;
            }
        }
    }
}
