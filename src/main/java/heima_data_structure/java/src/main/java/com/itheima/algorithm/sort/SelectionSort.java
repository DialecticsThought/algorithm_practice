package heima_data_structure.java.src.main.java.com.itheima.algorithm.sort;

import java.util.Arrays;

/**
 * <h3>选择排序</h3>
 */
public class SelectionSort {
    public static void sort(int[] a) {
        // 1. 选择轮数 a.length - 1
        // 2. 交换的索引位置(right) 初始 a.length - 1, 每次递减
        for (int right = a.length - 1; right > 0 ; right--) {
            int max = right;
            for (int i = 0; i < right; i++) {
                if (a[i] > a[max]) {
                    max = i;
                }
            }
            if(max != right) {
                int t = a[max];
                a[max] = a[right];
                a[right] = t;
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {6, 5, 4, 3, 2, 1};
        System.out.println(Arrays.toString(a));
        sort(a);
        System.out.println(Arrays.toString(a));
    }
}
