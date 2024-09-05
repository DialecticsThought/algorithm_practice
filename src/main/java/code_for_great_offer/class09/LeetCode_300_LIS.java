package code_for_great_offer.class09;

import java.util.Arrays;

// 本题测试链接 : https://leetcode.com/problems/longest-increasing-subsequence
public class LeetCode_300_LIS {

    public static int lengthOfLIS(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[] ends = new int[arr.length];
        ends[0] = arr[0];
        int right = 0;
        int l = 0;
        int r = 0;
        int m = 0;
        int max = 1;
        for (int i = 1; i < arr.length; i++) {
            l = 0;
            r = right;
            while (l <= r) {
                m = (l + r) / 2;
                if (arr[i] > ends[m]) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }
            right = Math.max(right, l);
            ends[l] = arr[i];
            max = Math.max(max, l + 1);
        }
        return max;
    }

    /*
     * 该函数返回从当前元素（cur）向前（包括当前元素）的LIS长度
     * 该问题考虑的是： （之前0~i-1位置不考虑）对于当前位置i 的LIS长度是什么
     *TODO
     * eg: num=[1,6,7,2,4,5,3]
     * 从前往后的尝试模型
     * 来到 i位置 有2中选择
     * 1.选
     *  1.1 判断当前i位置的元素 与上一次选的元素的大小
     *      当前i位置的元素 > 上一次选的元素 可以选
     * 2.不选
     * */
    public class Solution1 {

        public int lengthOfLIS(int[] arr) {
            //TODO 一开始 当前数是arr[0] 当前数的前一个数就规定是Integer.MIN_VALUE
            return lengthofLIS(arr, Integer.MIN_VALUE, 0);
        }

        //元素的位置（cur）  prev是 从当前arr[cur]的前一个元素arr[cur-1]
        public int lengthofLIS(int[] arr, int prev, int cur) {
            if (cur == arr.length) {//base case 当前元素的位置来到最后一个 返回0
                return 0;
            }
            int case1 = 0;
            int case2 = 0;
            if (arr[cur] > prev) {//TODO ① 当前元素比LIS中的前一个元素大
                //可以将当前元素纳入LIS。然后求出包含当前元素后的LIS长度。
                case1 = 1 + lengthofLIS(arr, arr[cur], cur + 1);
            }
            /*
             *TODO ② 当前元素小于LIS中的前一个元素
             * 不能将当前元素放入LIS中。因此，我们返回不包含当前元素的LIS长度。
             * */
            case2 = lengthofLIS(arr, prev, cur + 1);
            int ans = Math.max(case1, case2);
            return ans;
        }
    }

    /*
     *TODO
     * 在上一个方法中，许多参数相同的递归过程被多次调用。通过将递归调用结果保存在一个二维记忆数组cache中，可以消除冗余的调用。
     * cache[i][j]表示num[i]作为前一个元素放入/不放入LIS，并且num[j]作为当前元素放入/不放入LIS时的最长LIS
     *TODO
     * 由暴力递归改写。递归函数可变参数 preIndex、cur，无后效性，故cache[i][j]中缓存由特定递归过程(preIndex, cur)计算出的值。
     * 考虑到元素的取值范围无界，在上一个方法中使用的pre参数（表示LIS中的上一个元素的值）需要替换，这里使用preIndex表示该元素在arr数组中的位置。
     * */
    public class Solution2 {
        public int lengthOfLIS(int[] arr) {

            // preIndex的取值范围[-1, arr.length - 1],-1表示无前缀。故长度为arr.length + 1，为了将下标为-1的值放入数组，元素整体向后移一位。
            int cache[][] = new int[arr.length + 1][arr.length];

            for (int[] l : cache) {
                Arrays.fill(l, -1);
            }
            return lengthofLIS(arr, -1, 0, cache);
        }

        //TODO 传入 原数组（不变） cur 当前遍历到的数的位置  pre 当前遍历到的数的位置 的前一个位置  cache缓存
        public int lengthofLIS(int[] arr, int pre, int cur, int[][] cache) {
            if (cur == arr.length) {// base case
                return 0;
            }
            //TODO ① serach first 先从缓存中找有没有答案
            if (cache[pre + 1][cur] >= 0) {
                return cache[pre + 1][cur];
            }
            //TODO ② 没有缓存 就计算 compute and cache
            int taken = 0;
            if (pre < 0 || arr[cur] > arr[pre]) {
                taken = 1 + lengthofLIS(arr, cur, cur + 1, cache);
            }
            int nottaken = lengthofLIS(arr, pre, cur + 1, cache);
            //TODO 吧计算出的答案 缓存住
            cache[pre + 1][cur] = Math.max(taken, nottaken);

            return cache[pre + 1][cur];
        }
    }
    /*
    * 创建数组dp来存储相关数据。dp[i]表示以索引i元素作为结尾的子序列的最大长度。为了求解dp[i]，
    * 尝试将当前元素（nums[i]）追加到每一个既有的递增子序列中，使得添加完当前元素后的新序列仍然是递增序列，
    * 若无法添加至仍以既有子序列，则以i元素结尾的LIS长度为1
    * dp[i]的表达式为: dp[i] = max(dp[j]) + 1, 0 <= j < i and nums[i] > nums[j]
    * TODO 即，对于dp[i],求nums[i]结尾的最长递增子序列，在nums[0..i-1]中选出比nums[i]小且长度最长（dp[j] max）的
    * */
    public class Solution3 {
        public int lengthOfLIS(int[] nums) {
            if (nums.length == 0) {
                return 0;
            }
            int[] dp = new int[nums.length];
            dp[0] = 1;
            int maxans = 1;
            for (int i = 1; i < dp.length; i++) {
                int maxval = 0;
                for (int j = 0; j < i; j++) {
                    if (nums[i] > nums[j]) {
                        maxval = Math.max(maxval, dp[j]);
                    }
                }
                dp[i] = maxval + 1;
                maxans = Math.max(maxans, dp[i]);
            }
            return maxans;
        }
    }
}
