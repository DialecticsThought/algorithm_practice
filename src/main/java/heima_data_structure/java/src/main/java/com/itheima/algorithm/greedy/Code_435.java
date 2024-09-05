package heima_data_structure.java.src.main.java.com.itheima.algorithm.greedy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <h3>无重叠区间</h3>
 * <p>本质上就是活动选择问题</p>
 */
public class Code_435 {
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));
        int i, j;
        i = 0;
        int count = 1;
        for (j = 1; j < intervals.length; j++) {
            if (intervals[j][0] >= intervals[i][1]) {
                i = j;
                count++;
            }
        }
        return intervals.length - count;
    }

    public static void main(String[] args) {
        int[][] intervals = {{1, 2}, {2, 3}, {3, 4}, {1, 3}};
        int count = new Code_435().eraseOverlapIntervals(intervals);
        System.out.println(count);
    }
}
