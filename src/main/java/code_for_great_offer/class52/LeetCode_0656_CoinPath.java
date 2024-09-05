package code_for_great_offer.class52;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/*
 *TODO
 * 给定一个数组A(下标从1开始)包含N个整数:A1，A2....，AN和一个整数B。
 * 你可以从数组中的任何一个位置（下标为i）跳到下标i+1， i+2. .....i+B的任意一个可以跳到的位置上。
 * 如果你在下标为i的位置上，你需要支付A个金币。如果A是-1，意味着下标为i的位置是不可以跳到的。
 * 现在，你希望花费最少的金币从数组A的1位置跳到N位置，你需要输出花费最少的路径，依次输出所有经过的下标(从1到N)。
 * 如果有多种花费最少的方案，输出字典顺序最小的路径。
 * 如果无法到达N位置，请返回一个空数组。
 * 样例1:
 * 输入:[1,2,4,-1,2]，2输出:[1,3,5]
 * 每个点往右跳<=2个格子 -1表示没有路
 * 样例2∶
 * 输入:[1,2,4,-1,2]，1输出:[]
 *
 * [0 0 0 0 0]  k=3
 * 每个点往右跳<=3个格子
 * 0位置 可以直接跳到3 再跳到4 路径 [0 3 4]
 * 0位置 可以直接跳到1 再跳到2  再跳到3 再跳到4 路径 [0 1 2 3 4]
 * 因为第二个路径 的字典序最小
 * 所以问题是 路径代价最小的情况下，哪个字典序最小
 * */
public class LeetCode_0656_CoinPath {
    /*
     * arr 0 -> n-1
     * arr[i] = -1 死了！ 所以不能跳到i位置
     * 只考虑最小路径代价
     * */
    public static int minCost(int[] arr, int jump) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length;
        if (arr[0] == -1 || arr[n - 1] == -1) {
            return -1;
        }
        //TODO dp[i] : 从0位置开始出发，到达i位置的最小代价
        int[] dp = new int[n];
        dp[0] = arr[0];
        for (int i = 1; i < n; i++) {
            dp[i] = Integer.MAX_VALUE;
            //TODO 当前位置 = -1 那么dp[i]=-1
            if (arr[i] != -1) {
                /*
                 *TODO
                 * jump = 3 那么分别可以从 i-3,i-2,i-1 到i
                 * 但是i-4以及之前的点不能到i
                 * 但是 如果jump=7 但是 当前位置i=4
                 * 4-7=-3 最多是从0开始 所以 Math.max(0, i - jump)
                 * */
                for (int pre = Math.max(0, i - jump); pre < i; pre++) {
                    if (dp[pre] != -1) {//TODO 之前的点可达 那么 才能从pre位置 跳到 i位置
                        dp[i] = Math.min(dp[i], dp[pre] + arr[i]);
                    }
                }
            }
            //TODO 如果if (arr[i] != -1) 分支没有中 那么就是不可达
            dp[i] = dp[i] == Integer.MAX_VALUE ? -1 : dp[i];
        }
        return dp[n - 1];
    }

    //TODO 函数的含义 当前来到 i位置，想要从当前位置出发 跳到最后位置需要的最小代价
    public static int minCost1(int[] arr, int jump, int i) {
        //TODO base case 来到最后一个位置
        if (i >= arr.length) {
            return 0;
        }
        if (arr[i] == -1) {//TODO 说明 到这个位置的选择是无效的
            return Integer.MAX_VALUE;
        }
        int ans = Integer.MAX_VALUE;
        //TODO 当前来到i位置 i位置可以跳到 i+1，i+2,...,i+k
        for (int j = 1; j <= jump; j++) {
            int case1 = minCost1(arr, jump, i + j);
            //TODO 说明 可以到达 i+j的位置
            if (case1 != Integer.MAX_VALUE) {
                ans = Math.min(ans, case1) + arr[i];
            }
        }
        return ans;
    }


    public static void main(String[] args) {
        int[] arr = {1, 2, 4, -1, 2};
        System.out.println(minCost(arr, 2) == minCost1(arr, 2, 0));
    }

    /*
     *TODO
     * 贪心算法
     * 有个结论1
     * 从1出发到i位置 假设 k=4
     * 也就是 a b c d i  ，a b c d位置都能到i
     * 如果从1到a 跳了k个点，最后a直接到 i 并且是最短路径 一共k+1个点
     * 如果从1到b 也跳了k个点，最后b直接到 i 并且也是最短路径 一共k+1个点
     * 这2个路径 都是相同花费的点 k+1 ,那么最优路径 一定是 1 到 a 再到 i  字典序
     * 证明
     * 假设 i 到 a 再到i是最优路径 (代价最小 + 字典序最小)
     * 1到a的最优路径是 1 到 甲 到 a  即 1 -> 甲 -> a -> i 这里是下标
     * 假设 a位置后面有个b位置
     * 1到b的最优路径是 1 到 乙 到 a  即 1 -> 乙 -> b -> i 这里是下标
     * 代价方面：
     * [1] + [甲] + [a] + [i] = [1] + [乙] + [b] + [i]
     * => [甲] + [a] =  [乙] + [b]
     * 路径字符串：
     * 1 -> 甲 -> a   str1= 1 [甲]  a
     * 1 -> 乙 -> b   str2= 1 [乙]  b
     * str1 和 str2必能分出大小 因为 a<b 因为 a在b的前面
     * 反证该结论：
     * 1[甲]a > 1乙b 成立的话 => 也就是  甲  >  乙
     * 又因为
     * 已知  [甲] + [a] =  [乙] + [b]
     * if [a] = [b] =>  [甲] = [乙]  那么 1甲a = 1乙b
     * if [a] > [b] =>  [甲] < [乙]  那是 1甲b 不是  1甲a
     * if [a] < [b] =>  [甲] > [乙]   那是 1乙a 不是  1甲a
     * eg:
     * 代价相同的情况下 有几条路径
     * 1 10 12 => "11012"
     * 1 7 12 => "1712"
     * "11012" < "1712"
     * 有个结论2
     * 如果从1到a 跳了k个点，最后a直接到 i 并且是最短路径 一共k+1个点
     * 如果从1到b 也跳了>k个点，最后b直接到 i 并且也是最短路径 一共>k+1个点
     * 这2个路径 代价相同 那么 最优路径 一定是  是第二条
     * 证明：
     *  有个路径1 ：1到甲到i 3个点
     *  有个路径2 ：1到α到β到i 4个点
     *  甲在在 α的前面的位置
     *  同样是最小代价10
     * str1=1甲i
     * str2=1αβi
     * 反证
     * 已知 i>β
     * 假设证明 1甲i < 1αβi
     * 也就是证明甲的字典序 <α
     * 如果 甲的字典序 <α
     * [1] + [甲] + [i] = [1] + [α] + [β] + [i]
     * [甲] =[α] + [β]
     * 当 [β] = 0的时候  => [甲] =[α]
     * 又因为甲的字典序 <α + 甲位置能直接到i 那么最优： 1 ->甲 -> β -> i 而不是 1->α->β->i
     * 当 [β] > 0的时候 => [甲] > [α] 甲的代价 > α的代价
     * 但是 甲 在 α的前面的位置 那么甲能直接到i α也能直接到i 也就是 i -> α -> i  但是 不成立
     * */
    public static List<Integer> cheapestJump(int[] arr, int jump) {
        int n = arr.length;
        int[] best = new int[n];
        int[] last = new int[n];
        int[] size = new int[n];
        Arrays.fill(best, Integer.MAX_VALUE);
        Arrays.fill(last, -1);
        best[0] = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] != -1) {
                for (int j = Math.max(0, i - jump); j < i; j++) {
                    if (arr[j] != -1) {
                        int cur = best[j] + arr[i];
                        // 1) 代价低换方案！
                        // 2) 代价一样，但是点更多，换方案！
                        // (cur == best[i] && size[i] - 1 < size[j])是最难的地方 要证明
                        if (cur < best[i] || (cur == best[i] && size[i] - 1 < size[j])) {
                            //当前的代价 比之前的代价小 那么就是选当前最小的 也就是换方案
                            best[i] = cur;
                            last[i] = j;
                            size[i] = size[j] + 1;
                        }
                    }
                }
            }
        }
        List<Integer> path = new LinkedList<>();
        for (int cur = n - 1; cur >= 0; cur = last[cur]) {
            path.add(0, cur + 1);
        }
        return path.get(0) != 1 ? new LinkedList<>() : path;
    }
}
