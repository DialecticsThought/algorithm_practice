package heima_data_structure.java.src.main.java.com.itheima.algorithm.sort;

import java.util.Arrays;

/**
 * <h3>归并排序 TopDown</h3>
 */
public class MergeSortTopDown {

    /**
        a1 原始数组
        i~iEnd 第一个有序范围
        j~jEnd 第二个有序范围
        a2 临时数组
     */
    public static void merge(int[] a1, int i, int iEnd, int j, int jEnd, int[] a2) {
        // 这是 临时数组的索引
        int k = i;
        while (i <= iEnd && j <= jEnd) {
            // a[i] a[j] 哪一个小 哪一个填充到 临时数组的索引j
            if (a1[i] < a1[j]) {// 第1个有序范围的索引指向的元素 < 第2个有序范围的索引指向的元素
                //临时数组当前索引的空间被更新
                a2[k] = a1[i];
                // a[i] 被使用了 更新 第1个有序范围的 索引
                i++;
            } else {// 第1个有序范围的索引指向的元素 > 第2个有序范围的索引指向的元素
                a2[k] = a1[j];
                // a[j] 被使用了 更新 第2个有序范围的 索引
                j++;
            }
            // 当前临时数组的索引的元素已经填充好了，来到下一个索引
            k++;
        }
        // 说明 第1个有序范围的所有数字被填充到 临时数组里面 但是 第2个有序范围的数组还留有数字
        if (i > iEnd) {
            System.arraycopy(a1, j, a2, k, jEnd - j + 1);
        }
        // 说明 第2个有序范围的所有数字被填充到 临时数组里面 但是 第1个有序范围的数组还留有数字
        if (j > jEnd) {
            System.arraycopy(a1, i, a2, k, iEnd - i + 1);
        }
    }

    public static void sort(int[] a1) {
        int[] a2 = new int[a1.length];
        split(a1, 0, a1.length - 1, a2);
    }

    private static void split(int[] a1, int left, int right, int[] a2) {
        // base case 2. 分治的 "治"
        if (left == right) {
            return;
        }
        // 找到中间点
        // 1. 分
        int m = (left + right) >>> 1;
        split(a1, left, m, a2);                 // left = 0 m = 0  9
        split(a1, m + 1, right, a2);       // m+1 = 1 right = 1  3
        // 3. 合并
        // 执行 这个方法 说明  split(a1, left, m, a2) 和 split(a1, m + 1, right, a2) 都返回到了这一层
        // 说明 [left ~ m] [m+1 ~ right] 这两个区间有序了 现在要让[left ~ m] [m+1 ~ right]合并成[left ~ right]
        // 这也是分治的"治" 因为 对于left ~ right 之间 的元素处理好了 向上返回 对于上一层 而言 这一层就是"治"
        merge(a1, left, m, m + 1, right, a2);
        System.arraycopy(a2, left, a1, left, right - left + 1);
        System.out.println(left + " " + right);
    }

    public static void main(String[] args) {
        int[] a = {9, 3, 7, 2, 8, 5, 1, 4};
        System.out.println(Arrays.toString(a));
        sort(a);
        System.out.println(Arrays.toString(a));
    }
}
