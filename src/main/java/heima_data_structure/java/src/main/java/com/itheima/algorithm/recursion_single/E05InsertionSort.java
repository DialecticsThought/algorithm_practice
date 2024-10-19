package heima_data_structure.java.src.main.java.com.itheima.algorithm.recursion_single;

/**
 * 递归插入排序
 * <ul>
 *     <li>将数组分为两部分 [0 .. low-1]  [low .. a.length-1]</li>
 *     <li>左边 [0 .. low-1] 是已排序部分</li>
 *     <li>右边 [low .. a.length-1] 是未排序部分</li>
 *     <li>每次从未排序区域取出 low 位置的元素, 插入到已排序区域</li>
 * </ul>
 */
public class E05InsertionSort {
    public static void sort(int[] a) {
        insertion2(a, 1);
    }

    /**
     * 递归函数
     * 将数组分成 0 ~ low - 1 和 low ~ arr.length 两个区域
     * 将 low 位置的元素插入至 0 ~ low - 1 的已排序区域
     * <pre>
     * eg:
     * 2 4 5 10 7 1
     *     ↓
     * [2 4 5 10] [7 1]
     *             ↑
     *            low
     * 现在将arr[low]插入到已经排序的区域中
     * 1.用一个tmp去接收arr[low]
     * 2.从low-1开始向前遍历，设 i = low - 1
     *  arr[i]和tmp比较
     *    if arr[i] > tmp
     *          把 arr[i] 放入到tmp对应的位置
     *    if arr[i] < tmp
     *          把 tmp 放入到i+1的位置
     * </pre>
     *
     * @param a   数组
     * @param low 未排序区域的左边界
     */
    private static void insertion(int[] a, int low) {
        // base case
        if (low == a.length) {
            return;
        }
        // 开始 向 [0 ~ low - 1] 插入 arr[low] 元素

        // 报错 当前轮 的 需要插入的数据
        int tmp = a[low];
        // 已排序区域的右边界的指针
        int i = low - 1;
        while (i >= 0 && tmp < a[i]) { // 没有找到插入位置
            // 空出插入位置
            a[i + 1] = a[i];
            i--;
        }
        // while循环出来的时候 说明找到插入位置

        // 插入
        if (i + 1 != low) {
            a[i + 1] = tmp;
        }

        // 结束 向 [0 ~ low - 1] 插入 arr[low] 元素

        // 执行 向 [0 ~ low] 插入 arr[low + 1] 元素
        insertion(a, low + 1);
    }

    // 另一种插入排序的实现,缺点: 赋值次数较多
    private static void insertion2(int[] a, int low) {
        if (low == a.length) {
            return;
        }

        int i = low - 1;
        while (i >= 0 && a[i] > a[i + 1]) {
            int t = a[i];
            a[i] = a[i + 1];
            a[i + 1] = t;

            i--;
        }

        insertion(a, low + 1);
    }
}
