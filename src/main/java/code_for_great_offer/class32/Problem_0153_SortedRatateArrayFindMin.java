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
public class Problem_0153_SortedRatateArrayFindMin {
    /**
     * 我们假设目前想在 arr[low..high]范围上找到这个范围的最小值
     * <pre>
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
            if (low == high - 1) {
                break;
            }
            if (arr[low] < arr[high]) {
                return arr[low];
            }
            mid = (low + high) / 2;
            if (arr[low] > arr[mid]) {
                high = mid;
                continue;
            }
            if (arr[mid] > arr[high]) {
                low = mid;
                continue;
            }
            while (low < mid) {
                if (arr[low] == arr[mid]) {
                    low++;
                } else if (arr[low] < arr[mid]) {
                    return arr[low];
                } else {
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
