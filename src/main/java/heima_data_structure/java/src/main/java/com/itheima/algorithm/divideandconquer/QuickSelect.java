package heima_data_structure.java.src.main.java.com.itheima.algorithm.divideandconquer;

import java.util.concurrent.ThreadLocalRandom;

/**
 * <h3>快速选择算法 - 分而治之</h3>
 */
public class QuickSelect {

    /*
        求排在第 i 名的元素，i 从 0 开始，由小到大排
        6   5   1   2   4
     */

    static int quick(int[] array, int left, int right, int i) {
        /*
            6   5   1   2   [4]

                    2
            1   2   4   6   5

            1   2   4   6   [5]
                        3
            1   2   4   5   6
         */
        int p = partition(array, left, right); // 基准点元素索引值
        if (p == i) {
            return array[p];
        }
        if(i < p) { // 到左边找
            return quick(array, left, p - 1, i);
        } else { // 到右边找
            return quick(array, p + 1, right, i);
        }
    }

    public static void main(String[] args) {
        int[] array = {6, 5, 1, 2, 4};
        System.out.println(quick(array, 0, array.length - 1, 0)); // 1
        System.out.println(quick(array, 0, array.length - 1, 1)); // 2
        System.out.println(quick(array, 0, array.length - 1, 2)); // 4
        System.out.println(quick(array, 0, array.length - 1, 3)); // 5
        System.out.println(quick(array, 0, array.length - 1, 4)); // 6
    }

    static int partition(int[] a, int left, int right) {
        int idx = ThreadLocalRandom.current().nextInt(right - left + 1) + left;
        // [0~9] right-left+1=3 [0,2]+4=[4,6]
        swap(a, idx, left);
        int pv = a[left];
        int i = left;
        int j = right;
        while (i < j) {
            // 1. j 从右向左找小(等)的
            while (i < j && a[j] > pv) {
                j--;
            }
            // 2. i 从左向右找大的
            while (i < j && a[i] <= pv) {
                i++;
            }
            // 3. 交换位置
            swap(a, i, j);
        }
        swap(a, left, i);
        return i;
    }

    static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
