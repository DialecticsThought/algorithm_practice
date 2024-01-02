package heima_data_structure.java.src.main.java.com.itheima.algorithm.sort;

import java.util.Arrays;

/*
    根据另一个数组次序排序 前提
    1. 元素值均 >= 0  <=1000
    2. 两个数组长度 <= 1000
 */
public class E01Leetcode1122 {
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int[] count = new int[1001];
        for (int i : arr1) {
            count[i]++;
        }
        System.out.println(Arrays.toString(count));
        // 2, 4, 1
        // 1  2  3
        // 1 1 2 2 2 2 3 原始count排序
        // 2 2 2 2 3 1 1 要求的
        int[] result = new int[arr1.length];
        int k = 0;
        for (int i : arr2) {
            while (count[i] > 0) {
                result[k++] = i;
                count[i]--;
            }
        }
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                result[k++] = i;
                count[i]--;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr1 = {3, 2, 1, 2, 2, 1, 2, 5, 4};
        int[] arr2 = {2, 3, 1};

        E01Leetcode1122 leetcode = new E01Leetcode1122();
        int[] result = leetcode.relativeSortArray(arr1, arr2);
        System.out.println(Arrays.toString(result));
    }
}
