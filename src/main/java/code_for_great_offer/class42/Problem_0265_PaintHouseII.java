package code_for_great_offer.class42;

import java.util.HashMap;

public class Problem_0265_PaintHouseII {

    /*
     *TODO
     * https://leetcode.cn/problems/paint-house-ii/
     * 假如有一排房子，共 n 个，每个房子可以被粉刷成 k 种颜色中的一种，你需要粉刷所有的房子并且使其相邻的两个房子颜色不能相同。
     * 当然，因为市场上不同颜色油漆的价格不同，所以房子粉刷成不同颜色的花费成本也是不同的。
     * 每个房子粉刷成不同颜色的花费是以一个 n x k 的矩阵来表示的。
     * 例如，costs[0][0] 表示第 0 号房子粉刷成 0 号颜色的成本花费；
     * costs[1][2] 表示第 1 号房子粉刷成 2 号颜色的成本花费，以此类推。
     * 请你计算出粉刷完所有房子最少的花费成本。
     * costs[i][k] i号房子用k颜色刷的花费
     * 要让0...N-1的房子相邻不同色
     * 返回最小花费
     * */
    public static void main(String[] args) {
        int[][] costs = new int[][]{{5, 3, 2}, {6, 10, 12}, {3, 7, 4}, {1, 10, 2}};
        int i = minCostII(costs);
        int j = process(costs);
        System.out.println(i);
        System.out.println(j);
    }

    public static int process(int[][] costs) {
        int n = costs.length;//行
        int m = costs[0].length;//列
        Integer[][] cache = new Integer[n+1][m];
        int ans=Integer.MAX_VALUE;
        for (int i = 0; i < m; i++){
            ans=Math.min(ans,dfs(costs, n, m, 0, i, cache));
        }
        return ans;
    }

    /*
     *TODO
     * dp[i][颜色]的意思是
     * 0涂到i房子 必须相邻不同色
     * 最后一个 房子i 是 颜色 的情况下 最低代价是多少
     * 最后返回 dp表的最后一行的最小值
     * eg 二维数组有3列 分别代表 红 黄 蓝
     * {5, 3, 2}
     * {6, 10, 12}
     * {3, 7, 4}
     * {1, 10, 2}
     * 对于dp表
     * 第0行： 5 3 2 直接是原数组的第0行
     * 第1行： 8 12 15
     * 8是因为 房子1想要涂成红色 那么就不能让房子0是红色 并且还要最小代价 3和2取最小 与房子1的涂成红色的代价相加
     * 第2行： 15 15 12
     * 第3行： 13 22 17
     * 最终答案是13
     *TODO
     * 暴力递归
     * n是指二维数组的长度
     * m是指二维数组的宽度
     * i是来到的位置
     * k表示上一个节点（选择）选了哪一个颜色
     * 当前来到i位置 一共有m-1个选择（分支） 因为相邻的房子不能相同的个颜色
     * 来到位置i 求出i~cost.len范围中最小的涂颜色代价
     * */
    public static int dfs(int[][] costs, int n, int m, int i, int k, Integer[][] cache) {
        if (cache[i][k] != null) {
            return cache[i][k];
        }
        if (i == n) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (int j = 0; j < m; j++) {
            if (j != k) {
                min = Math.min(min, dfs(costs, n, m, i + 1, j, cache) + costs[i][j]);
            }
        }
        cache[i][k] = min;
        return min;
    }

    public static int minCostII(int[][] costs) {
        int N = costs.length;
        if (N == 0) {
            return 0;
        }
        int K = costs[0].length;
        // 之前取得的最小代价、取得最小代价时的颜色
        int preMin1 = 0;
        int preEnd1 = -1;
        // 之前取得的次小代价、取得次小代价时的颜色
        int preMin2 = 0;
        int preEnd2 = -1;
        for (int i = 0; i < N; i++) { // i房子
            int curMin1 = Integer.MAX_VALUE;
            int curEnd1 = -1;
            int curMin2 = Integer.MAX_VALUE;
            int curEnd2 = -1;
            for (int j = 0; j < K; j++) { // j颜色！
                //TODO 当前的颜色j 与最优的颜色不同
                if (j != preEnd1) {
                    //最优+此时j颜色的代价 变得更好了
                    if (preMin1 + costs[i][j] < curMin1) {
                        //TODO 最优的 赋值给次优的
                        curMin2 = curMin1;
                        curEnd2 = curEnd1;
                        //TODO 最优的更新
                        curMin1 = preMin1 + costs[i][j];
                        curEnd1 = j;
                        //最优+此时j颜色的代价 只干过了次优
                    } else if (preMin1 + costs[i][j] < curMin2) {
                        //TODO 只更新次优
                        curMin2 = preMin1 + costs[i][j];
                        curEnd2 = j;
                    }
                    //TODO 当前的颜色j 与次优的颜色不同
                } else if (j != preEnd2) {
                    //TODO 次优+ 此时的代价 判断是否小于 最优
                    if (preMin2 + costs[i][j] < curMin1) {
                        //TODO 最优赋值给次优
                        curMin2 = curMin1;
                        curEnd2 = curEnd1;
                        //TODO 最优赋值
                        curMin1 = preMin2 + costs[i][j];
                        curEnd1 = j;
                    } else if (preMin2 + costs[i][j] < curMin2) {
                        curMin2 = preMin2 + costs[i][j];
                        curEnd2 = j;
                    }
                }
            }
            preMin1 = curMin1;
            preEnd1 = curEnd1;
            preMin2 = curMin2;
            preEnd2 = curEnd2;
        }
        return preMin1;
    }
}
