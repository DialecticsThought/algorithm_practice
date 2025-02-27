package heima_data_structure.java.src.main.java.com.itheima.algorithm.sort;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <h3>双边循环快排</h3>
 * <ol>
 * <li>选择最左元素作为基准点元素</li>
 * <li>j 指针负责从右向左找比基准点小或等的元素，i 指针负责从左向右找比基准点大的元素，一旦找到二者交换，直至 i，j 相交</li>
 * <li>最后基准点与 i（此时 i 与 j 相等）交换，i 即为分区位置</li>
 * </ol>
 *
 * <pre>
 *   4  3  7  2  9  8  1  5
 *   0  1  2  3  4  5  6  7
 *   ↑                    ↑
 * left                  right
 *   i                    j
 *  此时的基准点是初始left指向的位置  也就是4
 *  4 < arr[j]
 *  j--  j是负责 找比基准点小或等的元素
 *
 *   4  3  7  2  9  8  1  5
 *   0  1  2  3  4  5  6  7
 *   ↑                 ↑  ↑
 * left                j  right
 *   i
 *   4 > arr[j]
 *   j 不动  j是负责 找比基准点小或等的元素
 *   4 = arr[i]
 *   i++     i是负责 找比基准点大的元素
 *
 *   4  3  7  2  9  8  1  5
 *   0  1  2  3  4  5  6  7
 *   ↑  ↑              ↑  ↑
 * left i              j  right
 *   4 < arr[i]
 *   i++     i是负责 找比基准点大的元素
 *
 *   4  3  7  2  9  8  1  5
 *   0  1  2  3  4  5  6  7
 *   ↑     ↑           ↑  ↑
 * left    i           j  right
 *   4 > arr[i]
 *   swap(a, i, j);
 *
 *   4  3  1  2  9  8  7  5
 *   0  1  2  3  4  5  6  7
 *   ↑     ↑           ↑  ↑
 * left    i           j  right
 *  4 < arr[j]
 *  j--  j是负责 找比基准点小或等的元素
 *
 *   4  3  1  2  9  8  7  5
 *   0  1  2  3  4  5  6  7
 *   ↑     ↑        ↑     ↑
 * left    i        j     right
 *  4 < arr[j]
 *  j--  j是负责 找比基准点小或等的元素
 *
 *   4  3  1  2  9  8  7  5
 *   0  1  2  3  4  5  6  7
 *   ↑     ↑     ↑        ↑
 * left    i     j        right
 *  4 < arr[j]
 *  j--  j是负责 找比基准点小或等的元素
 *
 *   4  3  1  2  9  8  7  5
 *   0  1  2  3  4  5  6  7
 *   ↑     ↑  ↑           ↑
 * left    i  j           right
 *   4 > arr[j]
 *   j 不动  j是负责 找比基准点小或等的元素
 *   4 > arr[i]
 *   i++     i是负责 找比基准点大的元素
 *
 *   4  3  1  2  9  8  7  5
 *   0  1  2  3  4  5  6  7
 *   ↑        ↑           ↑
 * left       j           right
 *            i
 *   此时 i == j 停止循环
 *  交换 基准点和当前的i或者j所在位置的元素
 *
 *   2  3  1  4  9  8  7  5
 *   0  1  2  3  4  5  6  7
 *   ↑        ↑           ↑
 * left      i,j         right
 * </pre>
 */
public class QuickSortHoare {

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

    /*
        注意事项
        1. 为啥要加内层循环的 i<j 条件
        2. 为啥要先处理 j，再处理 i
        3. 随机元素作为基准点
        4. 如果有大量重复元素
     */
    private static int partition(int[] a, int left, int right) {
        int idx = ThreadLocalRandom.current().nextInt(right - left + 1) + left;
        // [0~9] right-left+1=3 [0,2]+4=[4,6]
        swap(a, idx, left);
        int pv = a[left];
        int i = left;
        int j = right;
        while (i < j) {// i == j 说明当前部分的元素都遍历过了 ☆☆☆☆☆☆☆☆☆☆☆☆
            // 要让 基准 左侧全是 小于 基准的
            // 要让 基准 右侧全是 大于 基准的
            // 因为 基准最终要换到 中间 也就是 i == j的时候
            // 原先 基准点 不在数组中间的某一个位置上
            // 那么 j从右向左遍历就会 < 基准点的数 目标是要找到 并换到 i位置上
            // 那么 i从左向右遍历就会 > 基准点的数 目标是要找到 并换到 j位置上

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
        // 到这里 i==j 了 基准点的位置就是 i 或者 j
        swap(a, left, i);
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
