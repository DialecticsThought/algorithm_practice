package code_for_great_offer.class22;

import java.util.Arrays;

//本题测试链接 : https:leetcode.com/problems/maximum-sum-of-3-non-overlapping-subarrays/
public class LeetCode_689_MaximumSumof3NonOverlappingSubarrays {
    //如果远问题不考虑k
    public static int[] maxSumArray1(int[] arr) {
        int N = arr.length;
        int[] help = new int[N];
        //help[i] 子数组必须以i位置结尾的情况下，累加和最大是多少？
        help[0] = arr[0];
        for (int i = 1; i < N; i++) {
            int p1 = arr[i];
            int p2 = arr[i] + help[i - 1];
            help[i] = Math.max(p1, p2);
        }
        //dp[i] 在0~i范围上，随意选一个子数组，累加和最大是多少？
        int[] dp = new int[N];
        dp[0] = help[0];
        for (int i = 1; i < N; i++) {
            int p1 = help[i];
            int p2 = dp[i - 1];
            dp[i] = Math.max(p1, p2);
        }
        return dp;
    }

    public static int maxSumLenK(int[] arr, int k) {
        int N = arr.length;
        // 子数组必须以i位置的数结尾，长度一定要是K，累加和最大是多少？ 就是help[i]
        //help[0] ~help[k-2] 可以省略 因为长度不到k个
        int sum = 0;
        for (int i = 0; i < k - 1; i++) {
            sum += arr[i];
        }
        //0...k-2 k-1 sum
        int[] help = new int[N];
        for (int i = k - 1; i < N; i++) {
            //因为 已经有0~k-2的累加和sum了
            //1~k-1范围就是 sum+arr[i]
            /*
             * 0~k-2范围的累加和sum 但是长度没有到k 所以不算
             * 1~k-1范围就是 sum+arr[i] 开始算了
             * 接下来是2~k
             * */
            sum += arr[i];
            help[i] = sum;
            //i == k-1
            sum -= arr[i - k + 1];
        }
        //help[i] - > dp[i]  0~i范围上  随意选一个长度为K的子arr
        return 0;
    }

    public static int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int N = nums.length;
        int[] range = new int[N];
        int[] left = new int[N];
        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        /*
        *TODO
        * range[i] 表示以i为结尾 i-K+1 ~ i这k个数组成的子arr的累加和是多少
        * range[i] 记录的是 累加和
        * */
        range[0] = sum;
        /*
        *TODO
        * left记录的是 求 0 ~ i之间选定一个范围为k的子arr 哪一个累加和最大 （不用以i结尾 但是子数组连续）
        * left记录的是 选定的位置 j ，表示 j~j+k-1范围为k的子arr的累加和最大
        * */
        left[k - 1] = 0;
        int max = sum;//TODO max用来记录K~N范围 哪个长度为k的子arr的累加和最大
        //TODO i从K开始 是因为 0~K-1的长度小于k大小
        for (int i = k; i < N; i++) {
            //TODO sum记录的是 i-k+1~i范围这k个数组成的子arr的累加和
            sum = sum - nums[i - k] + nums[i];
            range[i - k + 1] = sum;
            left[i] = left[i - 1];
            if (sum > max) {
                max = sum;
                left[i] = i - k + 1;
            }
        }
        sum = 0;
        for (int i = N - 1; i >= N - k; i--) {
            sum += nums[i];
        }
        max = sum;

        System.out.println(" range[] = " + Arrays.toString(range));
        System.out.println(" left[] = " + Arrays.toString(left));
        int[] right = new int[N];
        /*
        *TODO
        * i~arr.len-1之间选定一个范围为k的子arr 哪一个累加和最大 （不用以i结尾 但是子数组连续）
        * right[i]记录的是 选定的位置 j ，表示 j~j+k-1范围为k的子arr的累加和最大
        * */
        right[N - k] = N - k;
        //TODO i从N - k - 1开始 是因为 N-k~N的长度小于k大小
        for (int i = N - k - 1; i >= 0; i--) {
            sum = sum - nums[i + k] + nums[i];//TODO 此时的sum 是i~i+k-1的累加和
            right[i] = right[i + 1];
            if (sum >= max) {
                max = sum;
                right[i] = i;
            }
        }
        System.out.println(" right[] = " + Arrays.toString(right));
        int a = 0;
        int b = 0;
        int c = 0;
        max = 0;
        //TODO 枚举中间的子arr
        for (int i = k; i < N - 2 * k + 1; i++) {  //中间一块的起始点 (0...k-1)选不了 i == N-1
            int part1 = range[left[i - 1]];
            int part2 = range[i];
            int part3 = range[right[i + k]];
            if (part1 + part2 + part3 > max) {
                max = part1 + part2 + part3;
                //记录划分点
                a = left[i - 1];
                b = i;
                c = right[i + k];
            }
        }
        return new int[]{a, b, c};
    }

    public static void main(String[] args) {
        int[] arr = {3, -5, 3, 2, -7, 2, 3};
        System.out.println(" arr = " + Arrays.toString(arr));
        int[] ints = maxSumOfThreeSubarrays(arr, 2);
        System.out.println(" ans = " + Arrays.toString(ints));
    }
}
