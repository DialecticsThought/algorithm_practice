package heima_data_structure.java.src.main.java.com.itheima.algorithm.sort;

import java.util.Arrays;

/**
 * 插入排序
 */
public class InsertionSort {

    public static void sort(int[] a) {
        for (int low = 1; low < a.length; low++) {
            int t = a[low];
            int i = low - 1;
            // 自右向左找插入位置，如果比待插入元素大，则不断右移，空出插入位置
            while (i >= 0 && t < a[i]) {
                a[i + 1] = a[i];
                i--;
            }
            // 找到插入位置
            if (i != low - 1) {
                a[i + 1] = t;
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {9, 3, 7, 2, 5, 8, 1, 4};
        System.out.println(Arrays.toString(a));
        sort(a);
        System.out.println(Arrays.toString(a));
    }
}
