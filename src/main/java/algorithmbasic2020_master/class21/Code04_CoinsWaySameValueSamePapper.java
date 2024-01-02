package algorithmbasic2020_master.class21;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * TODO 从左到右的尝试模型
 * arr是货币数组，其中的值都是正数。再给定一个正数aim。每个值都认为是一张货币，
 * 认为值相同的货币没有任何不同，返回组成aim的方法数
 * 例如︰arr.= [1,2,1,1,2,1,2]， aim = 4方法∶1+1+1+1、1+1+2、2+2一共就3种方法，所以返回3
 * eg:
 * 表示面值的arr[1,2]
 * 表示该面值数量的数量[3,2]
 * f(index,rest)表示当前来到index位置选择用index的面值多少张，当前还剩下rest元
 * | 			f(0,4)
 * | 	 0 ↙	1↙   	2↘ 		3 ↘		<--表示张数
 * | 	f(1,4)	f(1,3)  f(1,2)	 f(1,1)
 * | 0 ↙ 1↙ 2↘   0↙ 1↘    0↙ 1↘   0↘
 * | 4   2   0   4   1    2   0    1
 * | ×   ×   √   ×   ×    ×   √    ×
 * dp表 纵是index 横是rest
 * 如果arr[i]=3 有2张 现在求rest=14的dp[i][14]
 * eg:有个状态
 *   .... 5 6 7 8 9 10 11 12 13 14(dp的列)
 * ....
 * i                    v       ?
 * i+1    d     c       b       a
 * 上面的dp表中间就那个  当前index=i，依赖于i+1行的数组
 * 1.?依赖于 a(张数=0) 和 b(张数=1) 和 c(张数=2),不能依赖其他的了 因为张数最大=2
 * 2.v依赖于b和c和a
 * 3.v和?的关系  ? = v+a-c
 * 上面的行为是优化枚举的操作，查看临近位置把枚举行为换掉
 * 抽象化
 * 有y张x元  x元是arr[i]  假设 rest >= x *(y+1)
 * dp表如下
 *  .... r-3x  r-2x  r-x  r(dp的列)
 * ....
 * i                 v    ?
 * i+1    d     c    b    a
 * ? = dp[i][r]
 * v = dp[i][r-x]
 * ? 依赖于dp[i+1][r]和dp[i+1][r-x]和dp[i+1][r-2x]和dp[i+1][r-3x]...和dp[i+1][r-(y+1)-x]
 */
public class Code04_CoinsWaySameValueSamePapper {

    public static class Info {
        public int[] coins;
        public int[] zhangs;

        public Info(int[] c, int[] z) {
            coins = c;
            zhangs = z;
        }
    }

    public static Info getInfo(int[] arr) {
        HashMap<Integer, Integer> counts = new HashMap<>();
        for (int value : arr) {
            if (!counts.containsKey(value)) {
                counts.put(value, 1);
            } else {
                counts.put(value, counts.get(value) + 1);
            }
        }
        int N = counts.size();
        int[] coins = new int[N];
        int[] zhangs = new int[N];
        int index = 0;
        for (Entry<Integer, Integer> entry : counts.entrySet()) {
            coins[index] = entry.getKey();
            zhangs[index++] = entry.getValue();
        }
        return new Info(coins, zhangs);
    }

    public static int coinsWay(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        Info info = getInfo(arr);
        return process(info.coins, info.zhangs, 0, aim);
    }

    // coins 面值数组，正数且去重
    // zhangs 每种面值对应的张数
    public static int process(int[] coins, int[] zhangs, int index, int rest) {
        if (index == coins.length) {
            return rest == 0 ? 1 : 0;
        }
        int ways = 0;
        for (int zhang = 0; zhang * coins[index] <= rest && zhang <= zhangs[index]; zhang++) {
            //更新剩余的钱
            rest = rest - (zhang * coins[index]);
            ways += process(coins, zhangs, index + 1, rest);
        }
        return ways;
    }

    public static int dp1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] zhangs = info.zhangs;
        int N = coins.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ways = 0;
                for (int zhang = 0; zhang * coins[index] <= rest && zhang <= zhangs[index]; zhang++) {
                    ways += dp[index + 1][rest - (zhang * coins[index])];
                }
                dp[index][rest] = ways;
            }
        }
        return dp[0][aim];
    }

    public static int dp2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] zhangs = info.zhangs;
        int N = coins.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (rest - coins[index] >= 0) {
                    dp[index][rest] += dp[index][rest - coins[index]];
                }
                if (rest - coins[index] * (zhangs[index] + 1) >= 0) {
                    dp[index][rest] -= dp[index + 1][rest - coins[index] * (zhangs[index] + 1)];
                }
            }
        }
        return dp[0][aim];
    }

    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    // 为了测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 为了测试
    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 20;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinsWay(arr, aim);
            int ans2 = dp1(arr, aim);
            int ans3 = dp2(arr, aim);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("测试结束");
    }

}
