import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author veritas
 * @Data 2025/3/3 15:50
 */
public class LeetCode_57_InsertIntervals {
    /**
     * 在给定的区间集合 X 互不重叠的前提下，当我们需要插入一个新的区间 S=[left,right] 时，我们只需要：
     * <p>
     * 找出所有与区间 S 重叠的区间集合 X′
     * 将 X′中的所有区间连带上区间 S 合并成一个大区间；
     * 最终的答案即为不与 X′重叠的区间以及合并后的大区间
     *
     * @param intervals
     * @param newInterval
     * @return
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int left = newInterval[0];
        int right = newInterval[1];
        // 使用 placed 确保了新区间只插入一次，并且出现在正确的位置：
        // 当遇到第一个不重叠且位于新区间右侧的区间之前。如果没有找到这样的区间，则在最后插入
        boolean placed = false;
        // 存放答案
        List<int[]> ans = new ArrayList<int[]>();
        for (int[] interval : intervals) {
            // 得到 当前被遍历到的区间的左端 和 右端
            int currentLeft = interval[0];
            int currentRight = interval[1];

            // 如果 当前 被遍历到的区间 与 newInterval 没有交集

            // 当前 被遍历到的区间 的右端 < newInterval 的左端 说明 当前被遍历到的区间 在 newInterval的左侧
            // 说明 没有交集 interval可以直接放入 答案集合
            if(currentRight < left) {
                ans.add(interval);
            }
            // 当前 被遍历到的区间 的左端 > newInterval 的右端 说明 当前被遍历到的区间 在 newInterval的右侧
            // 说明 没有交集 interval可以直接放入 答案集合
            // 而且 newInterval 也 可以直接放入 答案集合 ☆☆☆☆☆☆☆☆☆☆☆
            // 因为 说明 之前遍历到的所有interval 都与  newInterval 没有交集
            else if(currentLeft > right){
                if(!placed){
                    placed = true;
                    // 先放合并后的新区间，而不是原始的 newInterval ☆☆☆☆☆☆☆☆☆☆☆☆☆☆
                    ans.add(new int[]{left, right});
                }
                // 再放Interval
                ans.add(interval);
            }else {
                //上面的情况没有遇到 说明是 被遍历到的区间 与  newInterval 有交集了
                // 更新 这个被合并的区间
                left =  Math.min(currentLeft,left);
                right =  Math.max(currentRight,right);
            }
        }
        // for循环结束之后 查看 是否newInterval 放入到ans集合
        if(!placed){
            ans.add(new int[]{left,right});
        }
        return ans.toArray(new int[ans.size()][]);
    }
}
