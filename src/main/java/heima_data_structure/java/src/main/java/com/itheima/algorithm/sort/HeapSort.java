package heima_data_structure.java.src.main.java.com.itheima.algorithm.sort;

import java.util.Arrays;

/**
 * <h3>堆排序</h3>
 */
public class HeapSort {
    public static void sort(int[] a) {
        heapify(a, a.length);
        for (int right = a.length - 1; right > 0; right--) {
            swap(a, 0, right);
            down(a, 0, right);
        }
    }

    // 建堆 O(n)
    private static void heapify(int[] array, int size) {
        for (int i = size / 2 - 1; i >= 0; i--) {
            down(array, i, size);
        }
    }

    // 下潜
    // leetcode 上数组排序题目用堆排序求解，非递归实现比递归实现大约快 6ms
    private static void down(int[] array, int parent, int size) {
        while (true) {
            int left = parent * 2 + 1;
            int right = left + 1;
            int max = parent;
            if (left < size && array[left] > array[max]) {
                max = left;
            }
            if (right < size && array[right] > array[max]) {
                max = right;
            }
            if (max == parent) { // 没找到更大的孩子
                break;
            }
            swap(array, max, parent);
            parent = max;
        }
    }

    // 交换
    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        int[] a = {2, 3, 1, 7, 6, 4, 5};
        System.out.println(Arrays.toString(a));
        sort(a);
        System.out.println(Arrays.toString(a));
    }
}
