package heima_data_structure.test.other;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <h3>双边循环快排</h3>
 * <ol>
 * <li>选择最左元素作为基准点元素</li>
 * <li>j 指针负责从右向左找比基准点小或等的元素，i 指针负责从左向右找比基准点大的元素，一旦找到二者交换，直至 i，j 相交</li>
 * <li>最后基准点与 i（此时 i 与 j 相等）交换，i 即为分区位置</li>
 * </ol>
 */
public class QuickSortHoare3 {

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
//        int idx = ThreadLocalRandom.current().nextInt(right - left + 1) + left;
//        // [0~9] right-left+1=3 [0,2]+4=[4,6]
//        swap(a, idx, left);
        int pv = a[left];
        int i = left+1;
        int j = right;
        while (true) {
            // i 不断向右找 >= 的
            while (i <= right && a[i] < pv) {
                i++;
            }
            // j 不断向左找 <= 的
            while (j > left && a[j] > pv) {
                j--;
            }
            if (i >= j) {
                break;
            }
            // 不光是大的和小的交换，相等的也会交换，=和=交换，让两边更均匀
            swap(a, i, j);
            i++;
            j--;
        }
        swap(a, left, j);
        return j;
    }

    private static void swap(int[] a, int i, int j) {
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
