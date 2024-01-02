package heima_data_structure.java.src.main.java.com.itheima.datastructure.heap;

import java.util.Arrays;

public class E01HeapSort {
    public static void sort(int[] array) {
        // 1. 建堆
        int size = array.length;
        heapify(array, size);

        while (size > 1) {
            // 2. 将堆顶元素交换至堆底
            swap(array, 0, size - 1);
            // 堆大小减一
            size--;
            // 重新调整堆
            down(array, 0, size);
        }
    }

    // 建堆
    private static void heapify(int[] array, int size) {
        for (int i = size / 2 - 1; i >= 0; i--) {
            down(array, i, size);
        }
    }

    // 下潜
    private static void down(int[] array, int parent, int size) {
        int left = parent * 2 + 1;
        int right = left + 1;
        int max = parent;
        if (left < size && array[left] > array[max]) {
            max = left;
        }
        if (right < size && array[right] > array[max]) {
            max = right;
        }
        if (max != parent) { // 找到了更大的孩子
            swap(array, max, parent);
            down(array, max, size);
        }
    }

    // 交换
    private static void swap(int[] array, int i, int j) {
        int t = array[i];
        array[i] = array[j];
        array[j] = t;
    }

    public static void main(String[] args) {
        int[] array = {2, 3, 1, 7, 6, 4, 5};
        sort(array);
        System.out.println(Arrays.toString(array));
    }
}
