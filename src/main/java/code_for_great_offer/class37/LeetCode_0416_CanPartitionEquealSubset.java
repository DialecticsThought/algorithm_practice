package code_for_great_offer.class37;

import java.util.HashMap;

/**
 * @Description
 * @Author veritas
 * @Data 2023/2/24 18:55
 */
public class LeetCode_0416_CanPartitionEquealSubset {
    /*
     *TODO
     * 要使得给定数组能够被拆分为两个元素和相等的子集，则数组所有元素和必然为偶数。
     * 因此第一步可以快速处理掉数组元素总和为奇数的情况。
     * 子集的元素和为half = sum / 2，如果能从给定数组从，选取子集A，且A的元素和 = half，则答案为 true；否则为 false。
     * 那么对于数组中任意元素，它有两种选择：
     * 进入子集 A；
     * 不进入子集 A；
     * 显而易见的，这种思路是典型的 DFS。但这里还有一个可优化的点——
     * 如果给定数组有序递增的，那么当遍历到元素nums[i]，且如果把nums[i]分到子集 A 时元素和超过 half，则无需继续遍历。
     * 所以，在 DFS 之前，可以对给定数组进行排序，注意到数组长度 <= 200，因此排序的压力不大。
     * 为了使 DFS 更快，通常可以在 DFS 基础上，使用记忆法。即申请额外的存储空间，记录 DFS 已遍历的结果，下次重入时直接返回相应结果，避免重复计算。
     *
     * 作者：eequalmcc
     * 链接：https://leetcode.cn/problems/partition-equal-subset-sum/solution/416-bu-bu-fen-xi-ru-he-cong-dfs-tui-dao-isk33/
     * */
    public boolean canPartition(int[] nums) {
        int sum = 0;
        //得到累加和
        for (int num : nums) {
            sum += num;
        }
        //判断累加和是否是偶数
        if (sum % 2 == 1) {
            return false;
        }
        //TODO 目标值的设定
        int target = sum / 2;

        HashMap<Info, Boolean> cache = new HashMap<>();
        return dfs(nums, 0, target, cache);
    }

    private boolean dfs(int[] nums, int index, int rest, HashMap<Info, Boolean> cache) {
        //base case
        if (index == nums.length) {
            if (rest != 0) {
                return false;
            } else {
                return true;
            }
        }
        //TODO 从缓存中找
        if (cache.containsKey(new Info(index, rest))) {
            return cache.get(new Info(index, rest));
        }
        // TODO 选择1 选择当前的数
        boolean case1 = this.dfs(nums, index + 1, rest - nums[index], cache);
        // TODO 选择2 选择当前的数
        boolean case2 = this.dfs(nums, index + 1, rest, cache);
        //TODO 得到当前节点的结果
        boolean ans = case1 | case2;
        //TODO 放入缓存
        cache.put(new Info(index, rest), ans);

        return ans;
    }

    public class Info {
        Integer index;
        Integer rest;

        public Info() {
        }

        public Info(Integer index, Integer rest) {
            this.index = index;
            this.rest = rest;
        }
    }
}
