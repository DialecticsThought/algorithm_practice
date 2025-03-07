import org.checkerframework.checker.units.qual.min;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author veritas
 * @Data 2025/3/6 18:04
 */
public class LeetCode_120_TriangleMin {

    public int minimumTotal(List<List<Integer>> triangle) {
        int[][] cache = new int[triangle.size()][triangle.size()];
        for (int i = 0; i < triangle.size(); i++) {
            for (int j = 0; j < triangle.size(); j++) {
                cache[i][j] = -1;
            }
        }
        return minimumTotal(triangle, 0, 0, cache);
    }

    /**
     * <pre>
     * 输入：triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
     * 输出：11
     * 解释：如下面简图所示：
     *    2
     *   3 4
     *  6 5 7
     * 4 1 8 3
     * 自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。
     * [2]
     * [3,4]
     * [6,5,7]
     * [4,1,8,7]
     * </pre>
     * <p>
     * 从给定的三角形（triangle）中，从位置(i,j) 开始，到达最后一行时所能取得的路径和的最小值
     *
     * @param triangle
     * @param i
     * @param j
     * @return
     */
    public int minimumTotal(List<List<Integer>> triangle, int i, int j, int[][] cache) {
        // base case
        // 如果来到了最后一行 返回当前元素的值
        if (i == triangle.size() - 1) {
            return triangle.get(i).get(j);
        }
        if (cache[i][j] != -1) {
            return cache[i][j];
        }
        /**
         * TODO 对于某一行i的索引j的元素 只能访问下一行i+1的索引 i,i+1的元素
         */
        int min = Integer.MAX_VALUE;

        // 从位置(i+1,j) 开始，到达最后一行时所能取得的路径和的最小值
        int case1 = minimumTotal(triangle, i + 1, j, cache);

        // 从位置(i+1,j+1) 开始，到达最后一行时所能取得的路径和的最小值
        int case2 = minimumTotal(triangle, i + 1, j + 1, cache);

        min = Math.min(case1, case2);
        int ans = min + triangle.get(i).get(j);

        cache[i][j] = ans;

        return ans;
    }
}
