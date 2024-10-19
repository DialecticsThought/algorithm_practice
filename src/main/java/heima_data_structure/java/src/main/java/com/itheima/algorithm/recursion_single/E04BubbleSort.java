package heima_data_structure.java.src.main.java.com.itheima.algorithm.recursion_single;

import java.util.Arrays;

/**
 * 递归冒泡排序
 * <ul>
 *     <li>将数组划分成两部分 [0 .. j] [j+1 .. a.length-1]</li>
 *     <li>左边 [0 .. j] 是未排序部分</li>
 *     <li>右边 [j+1 .. a.length-1] 是已排序部分</li>
 *     <li>未排序区间内, 相邻的两个元素比较, 如果前一个大于后一个, 则交换位置</li>
 * </ul>
 */
public class E04BubbleSort {

    public static void sort(int[] a) {
        bubble(a, a.length - 1);
    }

    /**
     * 递归函数
     * 1.在范围 [0 ~ j] 内冒泡最大元素到最右侧位置 j
     * 2.对[0 ~ j-1]执行相同的操作
     * TODO 算法相同，但是算法执行的范围不同，那么范围就是形参
     *
     * @param a 数组
     * @param j 未排序区域右边界
     */
    private static void bubble(int[] a, int j) {
        // base case
        if (j == 0) {
            return;
        }
        // 初始化 下一个右边界的值
        int x = 0;
        // 在范围 [0 ~ j] 内冒泡最大元素到最右侧位置 j
        for (int i = 0; i < j; i++) {
            // 此时遍历到新的i

            // 如果 新的i对应的值 > 新的i+1对应的值
            if (a[i] > a[i + 1]) {
                // 交换
                int t = a[i];
                a[i] = a[i + 1];
                a[i + 1] = t;
                // 更新 下一个右边界的值
                x = i;
            }
            // 如果 新的i对应的值 <= 新的i+1对应的值
            // 那么 x 不更新 还是上一轮最后一次交换更新到的值
        }
        // 对[0 ~ j-1]执行相同的操作
        bubble(a, x);
    }

    public static void main(String[] args) {
        int[] a = {6, 5, 4, 3, 2, 1};
        System.out.println(Arrays.toString(a));
        bubble(a, a.length - 1);
        System.out.println(Arrays.toString(a));
    }

}
