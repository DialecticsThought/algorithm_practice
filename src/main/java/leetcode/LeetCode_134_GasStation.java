package leetcode;

import java.util.LinkedList;

/**
 * 测试链接：https://leetcode.com/problems/gas-station
 * <pre>
 * 1.问题转换：
 * 创建一个差值数组，其中每个元素为 gas[i] - cost[i]。这个数组表示在每个加油站净增（或净亏）的汽油量。
 * 现在问题转化为：找到一个起点，使得从这个起点开始累积净汽油量，在完成一圈后总和不小于0。
 * 2.滑动窗口设置：
 * 		使用滑动窗口来检查每个可能的起点。
 * 		维护两个变量：current_sum表示当前窗口的汽油和，total_sum表示整个数组的总和。
 *      totalSum 的作用：
 *          整体判定是否有解：totalSum 计算的是整个数组的净汽油增量，即 gas[i] - cost[i] 的总和。
 *          这个总和表示，如果你从某个起点出发，无论路径怎么走，能否完成整个环路。
 *          如果 totalSum 为负数，则不可能完成环路，因为总的汽油量不足以覆盖总的消耗量，整个问题就无解，返回 -1。
 *          如果 totalSum 为非负数，那么理论上存在一个起点可以完成整个环路。这时候，我们需要用 currentSum 来找出这个起点。
 *      currentSum 的作用：
 *          局部判定是否要移动窗口：currentSum 是一个滑动窗口的和，计算从某个起点 startIndex 到当前加油站的净汽油增量。
 *          如果在某个位置 currentSum 变为负数，说明从当前 startIndex 到这个位置的路径无法完成，
 *          即从 startIndex 出发到这一路线中的某一个站点时，汽油不足。
 *          当 currentSum 变为负数时，我们需要调整起点，因为从当前的 startIndex 出发无法走到这里。
 *          此时，窗口的起点 startIndex 就需要向后移动，移到下一个位置 i + 1
 * 3.滑动窗口的滑动过程：
 * 		使用循环遍历差值数组。开始时窗口大小为1，逐步扩大窗口。
 * 		记录当前窗口的起始索引。
 * 		如果在某一时刻，current_sum变为负数，说明从当前窗口的起始索引出发无法完成环路，
 * 		因此将起始索引移到下一个加油站，并重置current_sum。 （相当于窗口移动了）
 * 4.最终检查：
 * 		遍历完成后，如果total_sum为负数，返回-1，表示无法完成环路。
 * 		如果total_sum为非负数，那么最后的起始索引就是答案。
 * 	</pre>
 * <pre>
 * eg1:
 * int[] gas = {2, 3, 4, 5, 1};
 * int[] cost = {3, 4, 3, 2, 5};
 * diff = gas[i] - cost[i]  => diff = {-1, -1, 1, 3, -4}
 * 遍历差值数组：
 * 1.起始窗口：0
 *  i = 0:
 *  diff[0] = -1
 *  currentSum = currentSum + diff[0] = 0 + (-1) = -1
 *  totalSum = totalSum + diff[0] = 0 + (-1) = -1
 *  currentSum 小于 0，重置 currentSum = 0，startIndex = 1
 *  窗口移动：起点从 0 移动到 1
 * 2.i = 1:
 *   diff[1] = -1
 *   currentSum = currentSum + diff[1] = 0 + (-1) = -1
 *   totalSum = totalSum + diff[1] = -1 + (-1) = -2
 *   currentSum 小于 0，重置 currentSum = 0，startIndex = 2
 *   窗口移动：起点从 1 移动到 2
 * 3.i = 3:
 *   diff[3] = 3
 *   currentSum = currentSum + diff[3] = 1 + 3 = 4
 *   totalSum = totalSum + diff[3] = -1 + 3 = 2
 *   currentSum 没有小于 0，不重置 startIndex
 * 4.i = 4:
 *  diff[4] = -4
 *  currentSum = currentSum + diff[4] = 4 + (-4) = 0
 *  totalSum = totalSum + diff[4] = 2 + (-4) = -2
 *  currentSum 没有小于 0，不重置 startIndex
 *  eg2:
 *  int[] gas = {2, 3, 4, 5, 1};
 *  int[] cost = {3, 4, 3, 2, 5};
 *  计算 totalSum:
 *  totalSum = (2 - 3) + (3 - 4) + (4 - 3) + (5 - 2) + (1 - 5) = -1 + (-1) + 1 + 3 + (-4) = -2
 *  totalSum 是负数，意味着不可能绕环一圈完成。
 *  计算 currentSum:
 *  当遍历到 i = 3 时，currentSum 变得足够大（在 startIndex = 3），这时局部来看你可以完成环路。
 *  但是由于 totalSum 负数的限制，最终还是无法完成整体旅程，最终答案仍然是 -1。
 * </pre>
 */
public class LeetCode_134_GasStation {

    // 这个方法的时间复杂度O(N)，额外空间复杂度O(N)
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        boolean[] good = goodArray(gas, cost);
        for (int i = 0; i < gas.length; i++) {
            if (good[i]) {
                return i;
            }
        }
        return -1;
    }

    public static boolean[] goodArray(int[] g, int[] c) {
        int N = g.length;
        int M = N << 1;
        int[] arr = new int[M];
        for (int i = 0; i < N; i++) {
            arr[i] = g[i] - c[i];
            arr[i + N] = g[i] - c[i];
        }
        for (int i = 1; i < M; i++) {
            arr[i] += arr[i - 1];
        }
        LinkedList<Integer> w = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            while (!w.isEmpty() && arr[w.peekLast()] >= arr[i]) {
                w.pollLast();
            }
            w.addLast(i);
        }
        boolean[] ans = new boolean[N];
        for (int offset = 0, i = 0, j = N; j < M; offset = arr[i++], j++) {
            if (arr[w.peekFirst()] - offset >= 0) {
                ans[i] = true;
            }
            if (w.peekFirst() == i) {
                w.pollFirst();
            }
            while (!w.isEmpty() && arr[w.peekLast()] >= arr[j]) {
                w.pollLast();
            }
            w.addLast(j);
        }
        return ans;
    }

}
