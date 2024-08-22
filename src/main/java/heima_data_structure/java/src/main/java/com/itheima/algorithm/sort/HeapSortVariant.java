package heima_data_structure.java.src.main.java.com.itheima.algorithm.sort;

import java.util.Arrays;

/**
 * <h3>堆排序变体</h3>
 * <p>参考了这篇论文 <a href="https://www.sciencedirect.com/science/article/pii/030439759390364Y">...</a></p>
 *
 */
public class HeapSortVariant {
    public static void sort(int[] a) {
        heapify(a, a.length);
        for (int right = a.length - 1; right > 0; right--) {
            int t = a[0];
            a[0] = a[right];
            a[right] = t;
            down(a, 0, right);
        }
    }

    private static void heapify(int[] array, int size) {
        for (int i = size / 2 - 1; i >= 0; i--) {
            down(array, i, size);
        }
    }

    private static int leaf(int[] array, int start, int size) {
        int j = start;
        while (true) {
            int left = 2 * j + 1;
            int right = left + 1;
            if(left < size && right < size) {
                if (array[right] > array[left]) {
                    j = right;
                } else {
                    j = left;
                }
            } else if (left < size) {
                j = left;
            } else {
                break;
            }
        }
        return j;
    }

    private static int parent(int j) {
        return (j - 1) >>> 1;
    }

    private static void down(int[] array, int start, int size) {
        int leaf = leaf(array, start, size);
        while (array[start] > array[leaf]) {
            leaf = parent(leaf);
        }
        int x = array[leaf];
        array[leaf] = array[start];
        while (leaf > start) {
            leaf = parent(leaf);
            int t = array[leaf];
            array[leaf] = x;
            x = t;
        }
    }

    public static void main(String[] args) {
        int[] a = {2, 3, 1, 7, 6, 4, 5};
        System.out.println(Arrays.toString(a));
        sort(a);
        System.out.println(Arrays.toString(a));
    }
}
