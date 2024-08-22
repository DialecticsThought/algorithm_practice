package heima_data_structure.java.src.main.java.com.itheima.algorithm.divideandconquer;

/**
 * <h3>数组中的中位数 - 快速选择</h3>
 */
public class FindMedian {
    /*
        奇数个
            1   4   5               ==> 4       3/2 = 1
            1   3   4   5   6       ==> 4       5/2 = 2
        偶数个
            0   1   2   3
            1   3   4   5           ==> 3.5     4/2
                                                4/2-1
     */
    public static double findMedian(int[] nums) {
        if (nums.length % 2 == 1) { // 奇数
            return QuickSelect.quick(nums, 0, nums.length - 1, nums.length / 2);
        } else { // 偶数
            int x = QuickSelect.quick(nums, 0, nums.length - 1, nums.length / 2);
            int y = QuickSelect.quick(nums, 0, nums.length - 1, nums.length / 2 - 1);
            return (x + y) / 2.0;
        }
    }

    public static void main(String[] args) {
        System.out.println("奇数");
        System.out.println(findMedian(new int[]{4, 5, 1}));
        System.out.println(findMedian(new int[]{4, 5, 1, 6, 3}));
        System.out.println("偶数");
        System.out.println(findMedian(new int[]{3, 1, 5, 4}));
        System.out.println(findMedian(new int[]{3, 1, 5, 4, 7, 8}));
    }


}
