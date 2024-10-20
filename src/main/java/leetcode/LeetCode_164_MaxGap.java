package leetcode;

/**
 * code_for_great_offer.class07
 * 测试链接 : https://leetcode.com/problems/maximum-gap/
 */
public class LeetCode_164_MaxGap {
    //三次遍历 O(n)
    public static int maximumGap(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        int len = nums.length;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        //遍历数组 得到arr的 min 和 max
        for (int i = 0; i < len; i++) {
            min = Math.min(min, nums[i]);
            max = Math.max(max, nums[i]);
        }
        if (min == max) {
            return 0;
        }
        //TODO 每个通知需要三个数据 有没有数 最小值 最大值
        // hasNum[i]i号桶是否进来过数字
        boolean[] hasNum = new boolean[len + 1];
        // maxs[i] i号桶收集的所有数字的最大值
        int[] maxs = new int[len + 1];
        // mins[i] i号桶收集的所有数字的最小值
        int[] mins = new int[len + 1];

        int bid = 0;
        for (int i = 0; i < len; i++) {//该数字 进入几号桶 bid
            bid = bucket(nums[i], len, min, max);
            //TODO 每次有数字 进入桶 那么 就要更新 那个桶的 max 和 min
            mins[bid] = hasNum[bid] ? Math.min(mins[bid], nums[i]) : nums[i];
            maxs[bid] = hasNum[bid] ? Math.max(maxs[bid], nums[i]) : nums[i];
            hasNum[bid] = true;
        }
        int res = 0;
        int lastMax = maxs[0];//上一个非空桶的最大值
        int i = 1;
        for (; i <= len; i++) {
            if (hasNum[i]) {//当前桶 不空
                res = Math.max(res, mins[i] - lastMax);//算一下 当前桶和上一个非空桶的差
                //更新
                lastMax = maxs[i];
            }
        }
        return res;
    }
    /*
    * 如果当前的数是num，整个范围是min~max，分成了len + 1份
    * 返回num该进第几号桶
    * */
    public static int bucket(long num, long len, long min, long max) {
        return (int) ((num - min) * len / (max - min));
    }

}
