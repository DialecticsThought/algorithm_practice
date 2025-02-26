package heima_data_structure.java.src.main.java.com.itheima.algorithm.sort;

import java.util.Arrays;

/**
 * <h3>单边循环快排（lomuto 洛穆托分区方案）</h3>
 * <p>核心思想：每轮找到一个基准点元素，把比它小的放到它左边，比它大的放到它右边，这称为分区</p>
 * <ol>
 * <li>选择最右元素作为基准点元素</li>
 * <li>j 找比基准点小的，i 找比基准点大的，一旦找到，二者进行交换</li>
 * <ul>
 * <li>交换时机：j 找到小的，且与 i 不相等</li>
 * <li>i 找到 >= 基准点元素后，不应自增</li>
 * </ul>
 * <li>最后基准点与 i 交换，i 即为基准点最终索引</li>
 * </ol>
 */
public class QuickSortLomuto {
    /**
     * <pre>
     * [5  3  7  2  9  8  1  4]
     *  0  1  2  3  4  5  6  7
     *  ↑                    ↑
     * left                right
     * 以 4为 基准点 也就是以 right指针指向的元素作为基准点
     * 把 < 4排在 左边 , > 4 排在 右边
     * 得到
     * [3  2  1  4  9  8  7  5]
     *  0  1  2  3  4  5  6  7
     *  ↑                    ↑
     * left                right
     * 现在对  < 4 , > 4 这2个区域 分别 再做上述操作
     *  [3  2  1  4  9  8  7  5]
     *   0  1  2  3  4  5  6  7
     *   ↑     ↑     ↑        ↑
     * left right   left    right
     * </pre>
     * @param a
     */
    public static void sort(int[] a) {
        quick(a, 0, a.length - 1);
    }

    private static void quick(int[] a, int left, int right) {
        if (left >= right) {
            return;
        }
        int p = partition(a, left, right); // p代表基准点元素索引
        quick(a, left, p - 1);
        quick(a, p + 1, right);
    }

    /**
     * <pre>
     *   4  3  7  2  9  8  1  5
     *   0  1  2  3  4  5  6  7
     *   ↑                    ↑
     * left                  right
     *   i
     *   j
     *   i = j
     *   i++ j++
     *   4  3  7  2  9  8  1  5
     *   0  1  2  3  4  5  6  7
     *   ↑  ↑                 ↑
     * left i                right
     *      j
     *   a[j] < pv but i = j
     *   i++, j++
     *
     *   4  3  7  2  9  8  1  5
     *   0  1  2  3  4  5  6  7
     *   ↑     ↑              ↑
     * left    i             right
     *         j
     *   a[j] > pv
     *   j++
     *
     *   4  3  7  2  9  8  1  5
     *   0  1  2  3  4  5  6  7
     *   ↑     ↑  ↑           ↑
     * left    i  j          right
     *   a[j] < pv
     *   swap(a, i, j)
     *   i++ , j++
     *
     *   4  3  2  7  9  8  1  5
     *   0  1  2  3  4  5  6  7
     *   ↑        ↑  ↑        ↑
     * left       i  j      right
     *   a[j] > pv
     *   j++
     *
     *   4  3  2  7  9  8  1  5
     *   0  1  2  3  4  5  6  7
     *   ↑        ↑     ↑     ↑
     * left       i     j   right
     *   a[j] > pv
     *   j++
     *
     *   4  3  2  7  9  8  1  5
     *   0  1  2  3  4  5  6  7
     *   ↑        ↑        ↑  ↑
     * left       i        j right
     *   a[j] < pv
     *   swap(a, i, j)
     *   i++ , j++
     *
     *   4  3  2  1  9  8  7  5
     *   0  1  2  3  4  5  6  7
     *   ↑           ↑        ↑
     * left          i       right
     *                        j
     *   swap(a, i, right);
     *
     *   4  3  2  1  5  8  7  9
     *   0  1  2  3  4  5  6  7
     *   ↑           ↑        ↑
     * left          i       right
     *                        j
     * 结束
     * </pre>
     * @param a
     * @param left
     * @param right
     * @return
     */
    private static int partition(int[] a, int left, int right) {
        int pv = a[right]; // 基准点元素值
        int i = left;    // 从左往右 找 > pv的元素
        int j = left;     // 从左往右 找 < pv的元素
        while (j < right) {
            if (a[j] < pv) { // j 找到比基准点小的了, 没找到大的
                if (i != j) {
                    swap(a, i, j);
                }
                i++;
            }
            j++;
        }
        swap(a, i, right);
        return i;
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        int[] a = {5, 3, 7, 2, 9, 8, 1, 4};
        System.out.println(Arrays.toString(a));
        sort(a);
        System.out.println(Arrays.toString(a));
    }

}
