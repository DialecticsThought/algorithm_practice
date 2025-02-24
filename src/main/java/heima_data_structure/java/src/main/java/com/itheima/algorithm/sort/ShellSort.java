package heima_data_structure.java.src.main.java.com.itheima.algorithm.sort;

import java.util.Arrays;

/**
 * 希尔排序 插入排序就是gap = 1 的shell排序
 *
 * [ 5, 3, 1, 2, 9, 8, 7, 4]
 *   0  1  2  3  4  5  6  7
 * 一开始 gap=4
 * 那么索引 [0,4] [1,5] [2,6] [3,7] 各个分成一组
 * 排序完
 * [ 9, 3, 7, 2, 5, 8, 1, 4]
 *   0  1  2  3  4  5  6  7
 *
 * 此时 gap= gap / 2 = 2
 * 那么索引 [0,2,4,6] [1,3,5,7]各个分成一组
 * 排序完
 * [ 1, 2, 5, 3, 7, 4, 9, 8]
 *   0  1  2  3  4  5  6  7
 *
 * 此时 gap= gap / 2 = 1
 * 那么索引 [0  1  2  3  4  5  6  7]各个分成一组
 * 排序完
 * [ 1, 2, 3, 4, 5, 7, 8, 9]
 *   0  1  2  3  4  5  6  7
 */
public class ShellSort {
    public static void sort(int[] a) {
        for (int gap = a.length >> 1; gap >= 1; gap = gap / 2) {
            for (int low = gap; low < a.length; low++) {
                int t = a[low]; // t=5
                int i = low - gap;
                // 自右向左找插入位置，如果比待插入元素大，则不断右移，空出插入位置
                while (i >= 0 && t < a[i]) {
                    a[i + gap] = a[i];
                    i =i -gap;
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
