package heima_data_structure.java.src.main.java.com.itheima.algorithm.divideandconquer;

/**
 * <h3>求数组中第 K 大的元素 - 快速选择</h3>
 * <ul>
 *     <li>时间复杂度 O(n)</li>
 * </ul>
 */
public class FindKthLargestLeetcode215 {


    /*
        由大到小
        a.length = 5 -
        5   4   3   2   1
        由小到大
        0   1   2   3   4
        1   2   4   5   6

     */

    public int findKthLargest(int[] a, int k) {
        return QuickSelect.quick(
                a, 0, a.length - 1, a.length - k
        );
    }

    public static void main(String[] args) {
        FindKthLargestLeetcode215 code = new FindKthLargestLeetcode215();
        // 应为5
        System.out.println(code.findKthLargest(new int[]{2, 1, 4, 5, 6}, 2));
    }
}
