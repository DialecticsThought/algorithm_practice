package code_for_great_offer.class33;

import java.util.HashMap;

/**
 * @Description
 * @Author veritas
 * @Data 2023/3/21 21:43
 */
public class LeetCode_0198_HouseRobber {
    public static void main(String[] args) {
    }

    public static int rob(int[] nums) {
        HashMap<Integer, Integer> cache = new HashMap<>();
        return dfs(nums, 0, cache);
    }

    /*
     *TODO
     * 当前来到i位置
     * 有2种选择
     * 1.偷 ，要去i+2位置做选择
     * 2.不偷，要去i+1位置做选择
     * 得到2种选择的最大值（最优解）
     * */
    public static int dfs(int[] nums, int i, HashMap<Integer, Integer> cache) {
        //判断是否有缓存
        if (cache.get(i) != null) {
            return cache.get(i);
        }
        //base case
        if (i >= nums.length) {
            return 0;
        }
        int case1 = nums[i] + dfs(nums, i + 2, cache);
        int case2 = dfs(nums, i + 1, cache);

        int ans = Math.max(case1, case2);

        cache.put(i, ans);
        return ans;
    }
}
