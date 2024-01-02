package code_for_great_offer.class16;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 这道题是一个小小的补充，课上没有讲
 * 但是如果你听过体系学习班动态规划专题和本节课的话
 * 这道题就是一道水题
 * <p>
 *TODO
 * 给定一个正整数组arr
 * 返回arr的子集不能累加出的最小正数
 * 1.正常怎么做
 * 2.如果arr中肯定有1这个值，怎么做？
 * 问题1：暴力 和下面代码相同
 * 问题2：先对arr排序，因为arr是正数arr且肯定存在1这个数 那么arr[0] = 1
 * 此时定义 range =1 表示从1~range范围都能累加出来，
 * arr[0] = 1 表示1~1范围的数都能累加出来
 * 抽象： 来到arr[i] = k arr[0]~arr[i-1]搞定了1~a范围上的数
 * 如果
 * k <= a+1 那么arr[0]~arr[i]能搞定 1~a+k范围
 * k > a+1 那么a+1是搞不定的最小正整数
 * eg:
 * 如果 arr[1] == 1 那么range ==2 表示 1~2范围的数能累加起来
 * 如果 arr[2] == 2 那么range ==4 表示 1~4范围的数能累加起来
 * 如果 来到i位置,arr[i] == 17 再假设arr[0]~arr[i-1]  能实现range = 100
 * 那么arr[i]=17 能让range= 117 为什么
 * 对于 101 原先 range = 100 那么肯定能累加出84 再加上arr[i]
 * 对于102 原先 range = 100 那么肯定能累加出 85 再加上arr[i]
 * .....
 * 但是如果arr[i] = 102 原先 arr[0] - arr[i-1]的range =100
 * 那么 arr[0]-arr[i] 不能解决累加和 = 101
 * 因为 0~i-1 只能搞定 0~100的数 arr[i]=102而且arr[]是排序过的
 */
public class Code01_IsSum {

    /**
     *TODO
     * arr中的值可能为正，可能为负，可能为0
     * 问题1：自由选择arr中的数字得到一个arr的子集，能不能累加得到sum
     * 问题2：如果arr中的数值很大，但是arr的长度不大，怎么做
     * 暴力递归方法
     * arr的子集相当于子序列
     * 每一个arr[i]都有2种选择： 要/不要
     * 从左到右的尝试模型 [0~i]范围上能否累加出k
     * eg: arr[1,4,3,7] k = 8
     *            0
     *         ↙     ↘
     *        1        0
     *      ↙  ↘      ↙   ↘
     *     5     1    4     0
     *    ↙ ↘   ↙ ↘  ↙ ↘   ↙ ↘
     *   8  5  4  1 7  4  3   0
     *  ↙ ↘ .......
     * 15 8 ..........
     * dp[i][j] 表示 [0]~[j] 范围上随意选择能否得到j 填的是true/false
     * eg:
     * dp[5][2] 表示 [0]~[5] 范围能否高出 12 这个数
     * 用到arr[5]这个数 dp[4][12-arr[5]]表示 0~4 范围上搞定12-arr[5]
     * 不用到arr[5]这个数 dp[4][12]表示 0~4 范围上搞定12
     * 问题 dp表的行 是arr》length列怎么定义
     * arr中把所有正数累加一起得到a 负数累加一起得到b
     * 那么dp的列就是范围[b,a]
     * 但是dp是从0开始 所以 b~a范围平移得到 0~a-b+1
     * 对于问题2，如果值越大，那么dp表的列就会变的很多
     * 假设 arr.lentgh = 40
     * 那么勇分治法
     * 把 30分成左侧和右侧，利用暴力法把左侧的所有累加和求出来，同理右侧的所有累加和求出
     * 假设 k = 17
     * 如果光是做部分能得到k 就返回true 有可能右部分就可以得到k，也返回true
     * 如果单独的左侧或右侧不能解决的话
     *  对于左侧的某个累加和N，去找右侧的某个累加和M实现 N+M = K
     *
     * @param arr
     * @param sum
     * @return
     */
    public static boolean isSum1(int[] arr, int sum) {
        if (sum == 0) {
            return true;
        }
        if (arr == null || arr.length == 0) {
            return false;
        }
        return process1(arr, arr.length - 1, sum);
    }

    // 可以自由使用arr[0...i]上的数字，能不能累加得到sum
    public static boolean process1(int[] arr, int i, int sum) {
        if (sum == 0) {
            return true;
        }
        if (i == -1) {
            return false;
        }
        return process1(arr, i - 1, sum) || process1(arr, i - 1, sum - arr[i]);
    }

    /**
     * arr中的值可能为正，可能为负，可能为0
     * 自由选择arr中的数字，能不能累加得到sum
     * 从暴力递归方法来，加了记忆化缓存，就是动态规划了
     * 记忆化搜索方法
     *
     * @param arr
     * @param sum
     * @return
     */
    public static boolean isSum2(int[] arr, int sum) {
        if (sum == 0) {
            return true;
        }
        if (arr == null || arr.length == 0) {
            return false;
        }
        return process2(arr, arr.length - 1, sum, new HashMap<>());
    }

    public static boolean process2(int[] arr, int i, int sum, HashMap<Integer, HashMap<Integer, Boolean>> dp) {
        if (dp.containsKey(i) && dp.get(i).containsKey(sum)) {
            return dp.get(i).get(sum);
        }
        boolean ans = false;
        if (sum == 0) {
            ans = true;
        } else if (i != -1) {
            ans = process2(arr, i - 1, sum, dp) || process2(arr, i - 1, sum - arr[i], dp);
        }
        if (!dp.containsKey(i)) {
            dp.put(i, new HashMap<>());
        }
        dp.get(i).put(sum, ans);
        return ans;
    }

    /**
     * arr中的值可能为正，可能为负，可能为0
     * 自由选择arr中的数字，能不能累加得到sum
     * 经典动态规划
     *
     * @param arr
     * @param sum
     * @return
     */
    public static boolean isSum3(int[] arr, int sum) {
        if (sum == 0) {
            return true;
        }
        // sum != 0
        if (arr == null || arr.length == 0) {
            return false;
        }
        // arr有数，sum不为0
        int min = 0;
        int max = 0;
        for (int num : arr) {
            min += num < 0 ? num : 0;
            max += num > 0 ? num : 0;
        }
        // min~max
        if (sum < min || sum > max) {
            return false;
        }

        // min <= sum <= max
        int N = arr.length;
        // dp[i][j]
        // 假设 min=-7
        //  0   1   2   3  4    5   6    7 (实际的对应)
        // -7  -6  -5  -4  -3   -2  -1   0（想象中）
        //
        // dp[0][-min] -> dp[0][7] -> dp[0][0]
        boolean[][] dp = new boolean[N][max - min + 1];
        // dp[0][0] = true
        dp[0][-min] = true;
        // dp[0][arr[0]] = true
        dp[0][arr[0] - min] = true;
        for (int i = 1; i < N; i++) {
            for (int j = min; j <= max; j++) {
                // dp[i][j] = dp[i-1][j]
                dp[i][j - min] = dp[i - 1][j - min];
                // dp[i][j] |= dp[i-1][j - arr[i]]
                int next = j - min - arr[i];
                dp[i][j - min] |= (next >= 0 && next <= max - min && dp[i - 1][next]);
            }
        }
        // dp[N-1][sum]
        return dp[N - 1][sum - min];
    }

    /**
     * arr中的值可能为正，可能为负，可能为0
     * 自由选择arr中的数字，能不能累加得到sum
     * 分治的方法
     * 如果arr中的数值特别大，动态规划方法依然会很慢
     * 此时如果arr的数字个数不算多(40以内)，哪怕其中的数值很大，分治的方法也将是最优解
     *
     * @param arr
     * @param sum
     * @return
     */
    public static boolean isSum4(int[] arr, int sum) {
        if (sum == 0) {
            return true;
        }
        if (arr == null || arr.length == 0) {
            return false;
        }
        if (arr.length == 1) {
            return arr[0] == sum;
        }
        int N = arr.length;
        int mid = N >> 1;
        HashSet<Integer> leftSum = new HashSet<>();//存放左部分的累加和
        HashSet<Integer> rightSum = new HashSet<>();
        // 0...mid-1
        process4(arr, 0, mid, 0, leftSum);
        // mid..N-1
        process4(arr, mid, N, 0, rightSum);
        // 单独查看，只使用左部分，能不能搞出sum
        // 单独查看，只使用右部分，能不能搞出sum
        // 左+右，联合能不能搞出sum
        // 左部分搞出所有累加和的时候，包含左部分一个数也没有，这种情况的，leftsum表里，0
        // 17  17
        for (int l : leftSum) {
            if (rightSum.contains(sum - l)) {
                return true;
            }
        }
        return false;
    }

    // arr[0...i-1]决定已经做完了！形成的累加和是pre
    // arr[i...end - 1] end(终止位置)  所有数字随意选择，
    // arr[0...end-1]所有可能的累加和存到ans里去
    public static void process4(int[] arr, int i, int end, int pre, HashSet<Integer> ans) {
        if (i == end) {//来到终止位
            ans.add(pre);
        } else {
            /*
             * 来到arr[i]有2种选择
             * 2. 选择arr[i] 累加到pre 去arr[i+1]做选择
             * 3. 不选择arr[i] pre不变 去arr[i+1]做选择
             * */
            process4(arr, i + 1, end, pre, ans);
            process4(arr, i + 1, end, pre + arr[i], ans);
        }
    }

    // 为了测试
    // 生成长度为len的随机数组
    // 值在[-max, max]上随机
    public static int[] randomArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * ((max << 1) + 1)) - max;
        }
        return arr;
    }

    // 对数器验证所有方法
    public static void main(String[] args) {
        int N = 20;
        int M = 100;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int size = (int) (Math.random() * (N + 1));
            int[] arr = randomArray(size, M);
            int sum = (int) (Math.random() * ((M << 1) + 1)) - M;
            boolean ans1 = isSum1(arr, sum);
            boolean ans2 = isSum2(arr, sum);
            boolean ans3 = isSum3(arr, sum);
            boolean ans4 = isSum4(arr, sum);
            if (ans1 ^ ans2 || ans3 ^ ans4 || ans1 ^ ans3) {
                System.out.println("出错了！");
                System.out.print("arr : ");
                for (int num : arr) {
                    System.out.print(num + " ");
                }
                System.out.println();
                System.out.println("sum : " + sum);
                System.out.println("方法一答案 : " + ans1);
                System.out.println("方法二答案 : " + ans2);
                System.out.println("方法三答案 : " + ans3);
                System.out.println("方法四答案 : " + ans4);
                break;
            }
        }
        System.out.println("测试结束");
    }

}
