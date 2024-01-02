package heima_data_structure.java.src.main.java.com.itheima.algorithm.sort;

import java.util.Arrays;

/**
 * <h3>计数排序</h3>
 */
public class CountingSort {

    /*
        要点
        1. 让原始数组的最小值映射到 count[0] 最大值映射到 count 最右侧
        2. 原始数组元素 - 最小值 = count 索引
        3. count 索引 + 最小值 = 原始数组元素
     */
    public static void main(String[] args) {
        int[] a = {5, 1, 1, 3, 0, -1};
        System.out.println(Arrays.toString(a));
        sort(a);
        System.out.println(Arrays.toString(a));
    }

    // 2N + K         n*log(n)
    /*
        {5, 1, 1, 3, 0, -1}  原始数组 a

        [1   1   2   0   1   0   1 ] count
         0   1   2   3   4   5   6
         -1  0   1       3       5
     */
    public static void sort(int[] a) {
        int max = a[0];
        int min = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
            }
            if (a[i] < min) {
                min = a[i];
            }
        }
        int[] count = new int[max - min + 1];

        for (int v : a) { // v 原始数组元素 - 最小值 = count 索引
            count[v - min]++;
        }
        System.out.println(Arrays.toString(count));

        int k = 0;
        for (int i = 0; i < count.length; i++) {
            // i + min 代表原始数组元素 count[i] 元素出现次数
            while (count[i] > 0) {
                a[k++] = i + min;
                count[i]--;
            }
        }
    }
}
