package heima_data_structure.test.other;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <h3>单边循环快排（lomuto 洛穆托分区方案）</h3>
 * <ol>
 * <li>选择最左元素作为基准点元素</li>
 * <li>j 指针负责从右向左找比基准点小或等的元素，i 指针负责从左向右找比基准点大的元素，一旦找到二者交换，直至 i，j 相交</li>
 * <li>最后基准点与 i（此时 i 与 j 相等）交换，i 即为分区位置</li>
 * </ol>
 */
public class QuickSortHoare2 {
    static ThreadLocalRandom r = ThreadLocalRandom.current();

    public static void sort(int[] a) {
        quick(a, 0, a.length - 1);
    }

    private static void quick(int[] a, int left, int right) {
        if (left >= right) {
            return;
        }
        int p = partition(a, left, right);
        quick(a, left, p - 1);
        quick(a, p + 1, right);
    }

    private static int partition(int[] a, int left, int right) {
//        System.out.println(r.nextInt(right + 1));
//        swap(a, r.nextInt(right - left) + left, left);
        int i = left;
        int j = right;
        int m = left;
        int pv = a[left];
        while (i < j) {
            while (i < j && a[j] > pv) {
                j--;
            }
            if (i < j) {
                a[i] = a[j];
                i++;
            }
            while (i < j && pv >= a[i]) {
                i++;
            }
            if (i < j) {
                a[j] = a[i];
                j--;
            }
        }
        a[i] = pv;
        return i;
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        int[] a = {9, 3, 7, 2, 8, 5, 1, 4};
        System.out.println(Arrays.toString(a));
        sort(a);
        System.out.println(Arrays.toString(a));
    }
}
