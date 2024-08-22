package heima_data_structure.test.other;

import java.util.Arrays;

/**
 * <h3>双边循环快排</h3>
 * <ol>
 * <li>选择最左元素作为基准点元素</li>
 * <li>j 指针负责从右向左找比基准点小或等的元素，i 指针负责从左向右找比基准点大的元素，一旦找到二者交换，直至 i，j 相交</li>
 * <li>最后基准点与 i（此时 i 与 j 相等）交换，i 即为分区位置</li>
 * </ol>
 */
public class QuickSort {

    public static void insertion(int[] a, int left, int right) {
        for (int low = left + 1; low <= right; low++) {
            int t = a[low];
            int i = low - 1;
            // 自右向左找插入位置，如果比待插入元素大，则不断右移，空出插入位置
            while (i >= left && t < a[i]) {
                a[i + 1] = a[i];
                i--;
            }
            // 找到插入位置
            if (i != low - 1) {
                a[i + 1] = t;
            }
        }
    }

    public static void sort(int[] a) {
        quick(a, 0, a.length - 1);
    }

    private static void quick(int[] a, int left, int right) {
        if (right - left <= 32) {
            // 插入排序
            insertion(a, left, right);
            return;
        }
        int pv = a[right];
        int i = left;
        int j = right - 1;
        int e = left;
        while (e <= j) {
            if (a[e] < pv) {
                swap(a, e, i);
                e++;
                i++;
            } else if (a[e] == pv) {
                e++;
            } else {
                swap(a, e, j);
                j--;
            }
        }
        swap(a, right, e);
        quick(a, left, i - 1);
        quick(a, e + 1, right);
    }

    private static void swap(int[] a, int i, int j) {
        if (i == j) {
            return;
        }
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        int[] a = {3, 5, 5, 5, 2};
        System.out.println(Arrays.toString(a));
        sort(a);
        System.out.println(Arrays.toString(a));
    }
}
