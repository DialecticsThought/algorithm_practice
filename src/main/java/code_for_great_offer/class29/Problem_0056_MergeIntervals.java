package code_for_great_offer.class29;

import java.util.Arrays;
/*
* 56. 合并区间

以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
示例 1：
输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
输出：[[1,6],[8,10],[15,18]]
解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
示例 2：
输入：intervals = [[1,4],[4,5]]
输出：[[1,5]]
解释：区间 [1,4] 和 [4,5] 可被视为重叠区间。

提示：
1 <= intervals.length <= 104
intervals[i].length == 2
0 <= starti <= endi <= 104
* */
public class Problem_0056_MergeIntervals {

	public static int[][] merge(int[][] intervals) {
		if (intervals.length == 0) {
			return new int[0][0];
		}
		//按照 开始位置 排序  结束位置无所谓
		Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
		int start = intervals[0][0];
		int end = intervals[0][1];
		int size = 0;
		/*
		* eg： [1,5] [1,3] [1,2]
		* 以start=1 为1组 找到 end 最大的数
		* 生成区间[1,5]
		* 查兰下一组的开头 是否 < end
		* eg: [2,5] [2,7] [2,4]
		* 继续合并 生成区间[1,7]
		* 查兰下一组的开头 是否 < end
		* eg: [3,5] [3,7] [3,9]
		* 继续合并 生成区间[1,9]
		* 查兰下一组的开头 是否 < end
		* eg: [10,15] [10,17] [10,11]
		* 结束 9 < 10
		* */
		for (int i = 1; i < intervals.length; i++) {
			if (intervals[i][0] > end) {
				//复用intervals
				intervals[size][0] = start;
				intervals[size][1] = end;
				start = intervals[i][0];
				end = intervals[i][1];
				size++;
			} else {
				end = Math.max(end, intervals[i][1]);
			}
		}
		intervals[size][0] = start;
		intervals[size++][1] = end;
		return Arrays.copyOf(intervals, size);
	}

}
