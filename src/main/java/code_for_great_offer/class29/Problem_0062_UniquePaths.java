package code_for_great_offer.class29;

import java.util.HashMap;
import java.util.Map;

/*
* 62. 不同路径
一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
问总共有多少条不同的路径？
示例 2：
输入：m = 3, n = 2
输出：3
解释：
从左上角开始，总共有 3 条路径可以到达右下角。
1. 向右 -> 向下 -> 向下
2. 向下 -> 向下 -> 向右
3. 向下 -> 向右 -> 向下
示例 3：
输入：m = 7, n = 3
输出：28
示例 4：
输入：m = 3, n = 3
输出：6
* */
public class Problem_0062_UniquePaths {

    // m 行
    // n 列
    // 下：m-1
    // 右：n-1
    public static int uniquePaths(int m, int n) {
        int right = n - 1;
        int all = m + n - 2;
        long o1 = 1;
        long o2 = 1;
        // o1乘进去的个数 一定等于 o2乘进去的个数
        for (int i = right + 1, j = 1; i <= all; i++, j++) {
            o1 *= i;
            o2 *= j;
            long gcd = gcd(o1, o2);
            o1 /= gcd;
            o2 /= gcd;
        }
        return (int) o1;
    }

    // 调用的时候，请保证初次调用时，m和n都不为0
    public static long gcd(long m, long n) {
        return n == 0 ? m : gcd(n, m % n);
    }


    public int uniquePaths2(int m, int n) {
        return dfs(m, n, 0, 0, new HashMap<String, Integer>());
    }

    private int dfs(int m, int n, int i, int j, HashMap<String, Integer> cache) {
        //如果(i,j)在缓存中则直接返回
        String key = i+"_"+j;
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        //到达边界时，直接返回 1
        if (i == m - 1 || j == n - 1) {
            return 1;
        }
        // case1
        int case1 = dfs(m, n, i + 1, j, cache);
        // case2
        int case2 = dfs(m, n, i, j + 1, cache);
        // ans
        int ans = case1 + case2;

        cache.put(key,ans);
        return cache.get(key);
    }

}
