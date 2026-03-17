/**
 * code_for_great_offer.class12
 * 本题测试链接 : https://leetcode.com/problems/median-of-two-sorted-arrays/
 * <p>
 * https://www.bilibili.com/video/BV162PVzPECg
 */
public class LeetCode_004_FindKthMinNumber {

    /**
     * 中位数的本质 就是把总序列切成左右两半 左半元素数量等于右半元素或者多一个
     * 不需要真的归并排序，分别在两个有序数组上切一刀，组成左右两个部分
     * 切割成立条件， left1 <= right2 并且 left2 <= right1 就能找到中位数
     * <pre>
     *   arr1 = 1 3 5 14
     *   arr2 = 2 4 8 9 11
     *   对arr1 在arr[1] arr[2]之间砍一刀
     *   然后 这里保证 left1和left2的元素总数量  = right1和right2的元素中数量
     *   对arr2 在arr[2] arr[3]之间砍一刀
     *   得到
     *   left1 = 1 3  right1 = 5 14
     *   left2 = 2 4 8 right2 = 9 11
     *   发现 left1最后一个元素 3 < right2第一个元素 9 √
     *   发现 left2最后一个元素 8 > right1第一个元素 5 ❌️
     *   说明arr1砍的一刀位置靠左了 需要向右 这里用到了二分搜索 在right1之间二分 得到新的砍一刀的位置
     *   也就是从原先的arr1 在arr[1] arr[2]之间，利用二分搜索 得到 现在在arr1[2] arr[3] 砍一刀
     *   这里保证 left1和left2的元素总数量  = right1和right2的元素中数量
     *   对arr2 在arr[1] arr[2]之间砍一刀
     *   得到
     *   left1 = 1 3 5  right1 = 14
     *   left2 = 2 4  right2 = 8 9 11
     *   发现 left1最后一个元素 5 < right2第一个元素 8 √
     *   发现 left2最后一个元素 4 > right1第一个元素 14  √
     *   这里 再对 left1和left2元素中取最大 得到 中位数 5
     * </pre>
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {

        if (nums1.length > nums2.length) {
            return findMedianSortedArrays2(nums1, nums2);
        }

        int num1Len = nums1.length;
        int num2Len = nums2.length;

        int left = 0;
        int right = num1Len;

        while (left <= right) {
            // 对num1 做分割 但是 做二分
            int i = left + (right - left) / 2;
            // 对num2 做分割 保证 num1的left 和 num2的 left 总和 = num1的right 和 num2的 right 总和
            int j = (num1Len + num2Len + 1) / 2 - i;
            // 如果 i == 0，说明 nums1 这一刀切在最左边
            // 那么 nums1 的左边根本没有元素
            // 这时没法取 nums1[i - 1]
            // 所以人为把它看成 -∞
            int left1 = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            // 如果 i == num1Len，说明 nums1 这一刀切在最右边
            // nums1 的右边没有元素
            // 那就把右边第一个值当成 +∞
            int right1 = (num1Len == i) ? Integer.MAX_VALUE : nums1[i];
            // 如果 j == 0，说明 nums2 这一刀切在最左边
            // 那么 nums2 的左边根本没有元素
            // 这时没法取 nums2[j - 1]
            // 所以人为把它看成 -∞
            int left2 = (j == 0) ? Integer.MIN_VALUE : nums1[j - 1];
            // 如果 j == num2Len，说明 nums2 这一刀切在最右边
            // nums2 的右边没有元素
            // 那就把右边第一个值当成 +∞
            int right2 = (num2Len == j) ? Integer.MAX_VALUE : nums2[j];

            if (left1 < right2 && left2 < right1) {
                //判断num1 num2 的总长度是不是奇数
                if (((num1Len + num2Len) & 1) == 1) {
                    return Math.max(left1, left2);
                }
                // 说明是偶数
                return (Math.max(left1, left2) + Math.max(right1, right2)) / 2.0;
            }
            //走到这里 说明 判断失败 需要继续对num1 num2做划分
            // nums1 左边拿多了，切点往左 也就是对num1 的 0~i做二分
            else if (left1 > right1) {
                right = i - 1;
            }
            // nums1 左边拿少了，切点往右 也就是对num1 的 i+1~num1.len做二分
            else {
                left = i + 1;
            }
        }
        // 按题意不会走到这里
        throw new IllegalArgumentException("输入数组不合法");
    }


    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int size = nums1.length + nums2.length;
        boolean even = (size & 1) == 0;
        if (nums1.length != 0 && nums2.length != 0) {
            if (even) {
                return (double) (findKthNum(nums1, nums2, size / 2) + findKthNum(nums1, nums2, size / 2 + 1)) / 2D;
            } else {
                return findKthNum(nums1, nums2, size / 2 + 1);
            }
        } else if (nums1.length != 0) {
            if (even) {
                return (double) (nums1[(size - 1) / 2] + nums1[size / 2]) / 2;
            } else {
                return nums1[size / 2];
            }
        } else if (nums2.length != 0) {
            if (even) {
                return (double) (nums2[(size - 1) / 2] + nums2[size / 2]) / 2;
            } else {
                return nums2[size / 2];
            }
        } else {
            return 0;
        }
    }

    // 进阶问题 : 在两个都有序的数组中，找整体第K小的数
    // 可以做到O(log(Min(M,N)))
    public static int findKthNum(int[] arr1, int[] arr2, int kth) {
        //哪个数组长 哪个赋值给longs
        int[] longs = arr1.length >= arr2.length ? arr1 : arr2;
        //哪个数组长 哪个赋值给longs
        int[] shorts = arr1.length < arr2.length ? arr1 : arr2;
        int l = longs.length;
        int s = shorts.length;
        if (kth <= s) { //case 1)
            return getUpMedian(shorts, 0, kth - 1, longs, 0, kth - 1);
        }
        if (kth > l) { //case 3)
            if (shorts[kth - l - 1] >= longs[l - 1]) {
                return shorts[kth - l - 1];
            }
            if (longs[kth - s - 1] >= shorts[s - 1]) {
                return longs[kth - s - 1];
            }
            return getUpMedian(shorts, kth - l, s - 1, longs, kth - s, l - 1);
        }
        //case 2)  s < k <= l
        if (longs[kth - s - 1] >= shorts[s - 1]) {
            return longs[kth - s - 1];
        }
        return getUpMedian(shorts, 0, s - 1, longs, kth - s, kth - 1);
    }


    // A[s1...e1]
    // B[s2...e2]
    // 一定等长！
    // 返回整体的，上中位数！8（4） 10（5） 12（6）
    // s1表示arr1的开始位置 e1表示arr1的结束位置 s2表示arr2的开始位置 e2表示arr2的结束位置
    public static int getUpMedian(int[] A, int s1, int e1, int[] B, int s2, int e2) {
        int mid1 = 0;
        int mid2 = 0;
        while (s1 < e1) {
            // mid1 = s1 + (e1 - s1) >> 1
            mid1 = (s1 + e1) / 2;
            mid2 = (s2 + e2) / 2;
            if (A[mid1] == B[mid2]) {
                return A[mid1];
            }
            // 两个中点一定不等！
            //e1 - s1 + 1 开头-结尾+1 表示总个数
            if (((e1 - s1 + 1) & 1) == 1) { // 奇数长度
                if (A[mid1] > B[mid2]) {
                    if (B[mid2] >= A[mid1 - 1]) {
                        return B[mid2];
                    }
                    e1 = mid1 - 1;
                    s2 = mid2 + 1;
                } else { // A[mid1] < B[mid2]
                    if (A[mid1] >= B[mid2 - 1]) {
                        return A[mid1];
                    }
                    e2 = mid2 - 1;
                    s1 = mid1 + 1;
                }
            } else { // 偶数长度
                if (A[mid1] > B[mid2]) {
                    e1 = mid1;
                    s2 = mid2 + 1;
                } else {
                    e2 = mid2;
                    s1 = mid1 + 1;
                }
            }
        }
        return Math.min(A[s1], B[s2]);
    }

}
