import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


/**
 * @Description
 * @Author veritas
 * @Data 2025/3/3 15:30
 */
public class LeetCode_54_MergeIntervals {


    public int[][] merge(int[][] intervals) {
        // base case
        if (intervals.length == 0) {
            return new int[0][2];
        }
        // 对所有的二维数组进行排列 按照每一个区间的 开始排列
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        // 创建一个 存储 合并后的区间的数组 也就也是最终结果
        /**
         * 多个合并区间会出现的情况是当输入的区间中存在不相交的部分，也就是它们之间有空隙时。例如：
         *
         * 如果输入区间为 [[1,3], [5,7]]，排序后依然是 [[1,3], [5,7]]。
         * 第一个区间 [1,3] 被加入 merged。
         * 接下来处理 [5,7] 时，会判断上一个合并区间的右边界 3 是否小于当前区间的左边界 5。
         * 由于 3 < 5 成立，说明两个区间不重叠，所以会将 [5,7]作为新的合并区间加入。
         * 这样最终返回的就是两个独立的区间，而不是合并成一个大的区间
         */
        List<int[]> merged = new ArrayList<int[]>();

        // 开始遍历每一个区间
        for (int i = 0; i < intervals.length; i++) {
            // 得到当前被遍历到的区间
            int currentLeft = intervals[i][0];
            int currentRight = intervals[i][1];
            // base case
            if (merged.size() == 0) {// 一开始 merged里面一个区间都没有
                merged.add(new int[]{currentLeft, currentRight});
            }
            // 如果当前的merged的最后一个区间的右端 < 当前区间的左端 说明 两个区间无交集
            if (merged.get(merged.size() - 1)[1] < currentRight) {
                merged.add(new int[]{currentLeft, currentRight});
            }
            // 代码执行到这 说明  当前的merged的最后一个区间的右端 > 当前区间的左端 说明 两个区间有交集
            // 修改merged的最后一个区间的右端
            int[] lastIntervals = merged.get(merged.size() - 1);
            lastIntervals[1] = Math.max(lastIntervals[1], currentRight);
        }
        /**
         * 创建一个 int[][] 数组，其中第一维的大小为 merged.size()
         * 将列表中每个 int[] 元素复制到这个新数组中
         * 每个复制过来的 int[] 数组已经是固定大小（长度为 2），所以无需在这里额外指定
         */
        return merged.toArray(new int[merged.size()][]);
    }
}
