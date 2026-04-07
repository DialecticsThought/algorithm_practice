package heima_data_structure.java.src.main.java.com.itheima.algorithm.sort;

import java.util.Arrays;

/**
 * <h3>堆排序</h3>
 */
public class HeapSort {
    public static void sort(int[] a) {
        heapify(a, a.length);
        // 从最后一个元素开始
        for (int right = a.length - 1; right > 0; right--) {
            // 堆的头部节点和当前的最后一个节点(会变)做交换
            swap(a, 0, right);
            // 建堆
            down(a, 0, right);
        }
    }

    //
    /**
     * 建堆 O(n)
     * 从最后一个非叶子节点开始，一路往前到根节点。
     * 对每个节点，都把它当作当前子树的根，让它在自己的子树里往下沉。
     * 然后 新的当前节点 变成 当前节点的父节点
     * 全部处理完后，整个数组就变成堆了。
     * @param array
     * @param size
     */
    private static void heapify(int[] array, int size) {
        for (int i = size / 2 - 1; i >= 0; i--) {
            down(array, i, size);
        }
    }


    /**
     * 下潜,自上而下
     * leetcode 上数组排序题目用堆排序求解，非递归实现比递归实现大约快 6ms
     * @param array
     * @param parent
     * @param size
     */
    private static void down(int[] array, int parent, int size) {
        while (true) {
            // 当前节点parent的左子节点
            int left = parent * 2 + 1;
            // 当前节点parent的右子节点
            int right = left + 1;
            // 设置 当前节点 当前节点的右子节点 当前节点的左子节点 这三个节点的最大值默认是当前节点
            int max = parent;
            if (left < size && array[left] > array[max]) {
                max = left;
            }
            if (right < size && array[right] > array[max]) {
                max = right;
            }
            // 没找到更大的孩子
            if (max == parent) {
                break;
            }
            swap(array, max, parent);
            // 下一轮循环中 当前节点parent就是max
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
