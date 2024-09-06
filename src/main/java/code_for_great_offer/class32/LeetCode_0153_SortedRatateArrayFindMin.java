package code_for_great_offer.class32;

/**
 * 已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。
 * 例如，原数组 nums = [0,1,2,4,5,6,7] 在变化后可能得到：
 * 若旋转 4 次，则可以得到 [4,5,6,7,0,1,2]
 * 若旋转 7 次，则可以得到 [0,1,2,4,5,6,7]
 * 注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2], ..., a[n-2]] 。
 * 给你一个元素值 互不相同 的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转。请你找出并返回数组中的 最小元素 。
 * 你必须设计一个时间复杂度为O(log n) 的算法解决此问题。
 * 链接：https://leetcode.cn/problems/find-minimum-in-rotated-sorted-array
 */
public class LeetCode_0153_SortedRatateArrayFindMin {
    /**
     * 有序数组 arr 可能经过一次旋转处理，
     * eg：有序数组[1,2,3,4,5,6,7]，可以旋转处理成[4,5,6,7,1,2,3]等
     * 题目例子里的数组，旋转后断点在 1 所处的位置，也就是位置 4。
     * 只要找到断点，就找到了最小值
     * <pre>
     * 我们假设目前想在 arr[low~high]范围上找到这个范围的最小值
     *
     * 1.如果 arr[low] < arr[high]，说明 arr[low..high]上没有旋转，断点就是 arr[low]，返回 arr[low]
     * 2.如果 arr[low] > arr[high]
     *      令 mid=(low+high)/2，mid 即 arr[low..high]中间的位置
     *      2.1 如果 arr[low]>arr[mid]，说明断点一定在 arr[low..mid]上，
     *              则令 high=mid，然后回到步骤
     *      2.2 如果 arr[mid]>arr[high]，说明断点一定在 arr[mid..high]上，
     *              令 low=mid，然后回到1。
     * 3．如果步骤 1 和步骤 2 的逻辑都没有命中，说明什么？
     *          步骤 1 没有命中说明 arr[low]>=arr[high]
     *      步骤 2 的 1）没有命中说明 arr[low]<=arr[mid]，
     *      步骤 2 的 2）没有命中说明 arr[mid]<=arr[high]
     *      => arr[low]==arr[mid]==arr[high]
     * </pre>
     *
     * @param arr
     * @return
     */
    public static int getMin(int[] arr) {
        int low = 0;
        int high = arr.length - 1;
        int mid = 0;
        while (low < high) {
            // base case
            if (low == high - 1) {
                break;
            }
            // 针对 case 1.
            if (arr[low] < arr[high]) {
                return arr[low];
            }
            mid = (low + high) / 2;
            // 针对 case 2.1
            if (arr[low] > arr[mid]) {
                high = mid;
                continue;
            }
            // 针对 case 2.2
            if (arr[mid] > arr[high]) {
                low = mid;
                continue;
            }
            // 针对 case 3
            /**
             * 在面对 arr[low] == arr[mid] 的情况下，
             * 通过逐步增加 low 的值，尝试找到一个与 mid 不相等的元素，进而判断最小值的区间。
             * 如果 arr[low] 小于 arr[mid]，直接返回 arr[low] 作为可能的最小值。
             * 如果找不到这种情况，就缩小搜索范围，继续判断
             */
            while (low < mid) {
                // 如果 arr[low] 和 arr[mid] 相等，low 指针向右移动一位，即 low++
                // 这一步的目的是跳过相等的元素，尝试找到一个与 mid 不相等的元素。
                if (arr[low] == arr[mid]) {
                    low++;
                } else if (arr[low] < arr[mid]) {
                    // 如果 arr[low] 小于 arr[mid]，
                    // 意味着 arr[low] 可能是这个区间中的最小值，因此直接返回 arr[low]。
                    return arr[low];
                } else {
                    // 如果 arr[low] 大于 arr[mid]，说明最小值在 low 和 mid 之间的区间，
                    // 因此将 high 设置为 mid，缩小搜索范围。
                    // break 语句用于跳出当前的 while 循环，继续外层的 while 循环。
                    high = mid;
                    break;
                }
            }
        }
        return Math.min(arr[low], arr[high]);
    }

    public static void main(String[] args) {
        int[] test = {4, 5, 5, 5, 1, 2, 3};
        System.out.println(getMin(test));

    }

}
