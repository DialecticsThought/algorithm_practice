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
     *
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
     *
     * @param a
     * @param left
     * @param right
     * @return
     */
    private static int partition(int[] a, int left, int right) {
        // 假设返回值是 p，那么执行完后一定满足：
        // a[p] == pv
        // a[left ... p-1] < pv
        // a[p+1 ... right] >= pv
        // [ 比 pv 小的区域 ][ pv ][ 大于等于 pv 的区域 ]
        // 基准点元素值
        int pv = a[right];
        // 从左往右 找 > pv的元素
        int i = left;
        // 从左往右 找 < pv的元素
        int j = left;
        while (j < right) {
            // 如果当前 j 指向的元素比基准值小，那它应该去左边“小于区”
            // 但是现在它在 j 这个位置，不一定在正确的位置。
            // j 找到比基准点小的了, 没找到大的
            if (a[j] < pv) {
                // 而 i 恰好指向 下一个属于“小于区”的空位
                // 如果 i != j，说明这个“小元素”现在还 不在它该在的位置
                //  要交换，把它放过去
                // 如果 i == j，说明这个“小元素”本来就在它该在的位置
                //  不需要交换  避免 swap(a, j, j); 也就是“自己和自己交换”
                if (i != j) {
                    swap(a, i, j);
                }
                i++;
            }
            // 如果 a[j] >= pv，那它本来就该留在右边那一侧
            // 所以不需要交换
            j++;
        }
        swap(a, i, right);
        return i;
    }

    public int func(int[] nums, int left, int right) {
        int pv = nums[right];
        int i = left;
        int j = left;
        while (j < right) {
            if (nums[j] < pv) {
                if (i != j) {
                    swap(nums,i,j);
                }
                i++;
            }
            j++;
        }
        swap(nums, j, right);
        return j;
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
