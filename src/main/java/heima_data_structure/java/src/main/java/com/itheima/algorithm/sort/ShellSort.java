package heima_data_structure.java.src.main.java.com.itheima.algorithm.sort;

import java.util.Arrays;

/**
 * 希尔排序
 */
public class ShellSort {
    public static void sort(int[] a) {
        for (int gap = a.length >> 1; gap >= 1; gap = gap >> 1) {
            // gap=4
            for (int low = gap; low < a.length; low++) {
                int t = a[low]; // t=5
                int i = low - gap;
                // 自右向左找插入位置，如果比待插入元素大，则不断右移，空出插入位置
                while (i >= 0 && t < a[i]) {
                    a[i + gap] = a[i];
                    i -= gap;
                }
                // 找到插入位置
                if (i != low - gap) {
                    a[i + gap] = t;
                }
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
