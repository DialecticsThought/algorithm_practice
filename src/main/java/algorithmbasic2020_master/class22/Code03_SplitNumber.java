package algorithmbasic2020_master.class22;

/**
 *TODO
 * 把数拆开来有几种方法 但是后面的数必须>=前面的数
 * 5  = 1 + 1 + 1 + 1 + 1 √
 * = 1 + 1 + 1 + 2 √
 * = 1 + 2 + 2 √
 * = 1 + 3 + 1  ×
 * = 1 + 1 + 3  √
 * eg:
 * 我要拆解5（添加一个前置条件） 但是我把它认作为6被拆成1和5 目的是携程函数f(1,5) 求6剩下的方法
 * => 为了保证第一个被拆的数是1
 * |                        f(1,5)
 * |             ↙           ↙            ↙      ↘       ↘
 * |        f(1,4)          f(2,3)      f(3,2) f(4,1)  f(5,0)
 * |    ↙     ↓   ↘        ↓    ↘
 * |    f(1,3) f(2,2) f(3,1) f(1,2) f(2,1) ........................
 * |    ↙  ↘
 * |f(1,2) f(2,1) ........................
 * |........................
 * | eg:
 * |f(2,7)  我已经拆出了一个数字2 还剩下7这个数没有被拆
 * |                7被拆分
 * |↙       ↙      ↙     ↙      ↘       ↘      ↘
 * |f(1,6) f(2,5) f(3,4) f(4,3) f(5,2) f(6,1) f(7,0)
 * |
 * |f(1,5) f(2,4) f(3,3) f(4,2) f(5,1) f(6,0)
 * 假设 我要插接5 添加一个前置条件
 * eg:我要拆4这个数
 * |            4被拆分
 * |        f(1,3)           f(2,2)       f(3,1)      f(4,0)
 * |    ↙       ↙    ↘          ↙    ↘
 * | f(1,2)   f(2,1) f(3,0) f(1,1) f(2,0)
 * | ↙    ↘
 * |f(1,1)f(2,0)
 * eg：
 * 因为每个节点的分支都是枚举行为
 * for(int first =pre;first<=rest;first++){
 *     way+=process(first,rest-first);
 * }
 * rest-first 做减法的原因
 * eg:f(2,7)  pre=2,pre=7
 * 7可以被拆分成
 * 1 不可以 因为1 < pre = 2
 * 2 可以==pre，rest=5
 * 3, rest=4
 * 4, 不可以 ,4>rest=3
 *
 */
public class Code03_SplitNumber {

    // n为正数
    public static int ways(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return process(1, n);
    }

    /**
     * TODO
     *  上一个拆出来的数是pre
     *  还剩rest需要去拆
     *  返回拆解的方法数
     */
    public static int process(int pre, int rest) {
        if (rest == 0) {//TODO 到达了该分支的终结
            return 1;
        }
        if (pre > rest) {//TODO 前一个数 > 剩下的数
            return 0;
        }
        int ways = 0;
        for (int first = pre; first <= rest; first++) {
            ways += process(first, rest - first);
        }
        return ways;
    }

    //TODO 这个dp表 是正方型
    public static int dpSelf(int pre, int rest) {
        int[][] dp = new int[pre + 1][rest + 1];
        //TODO pre不可能等于0 第0行不用写
        for (int i = 1; i <= pre + 1; i++) {
            dp[i][0] = 1;//TODO  if (rest == 0) return 1;
            dp[pre][pre] = 1;
        }
        //TODO 前一个数 > 剩下的数
/*        if (pre > rest) {
            return 0;
        }*/
        for (int i = pre - 1; i >= 1; i--) {
            for (int j = 1; j <= rest; j++) {
                int ways = 0;
                for (int first = pre; first <= rest; first++) {
                    ways += dp[first][rest - first];
                }
                dp[pre][rest] = ways;
            }
        }

        return dp[1][rest];
    }


    public static int dp1(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int[][] dp = new int[n + 1][n + 1];
        for (int pre = 1; pre <= n; pre++) {
            dp[pre][0] = 1;
            dp[pre][pre] = 1;
        }
        for (int pre = n - 1; pre >= 1; pre--) {
            for (int rest = pre + 1; rest <= n; rest++) {
                int ways = 0;
                for (int first = pre; first <= rest; first++) {
                    ways += dp[first][rest - first];
                }
                dp[pre][rest] = ways;
            }
        }
        return dp[1][n];
    }

    public static int dp2(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int[][] dp = new int[n + 1][n + 1];
        for (int pre = 1; pre <= n; pre++) {
            dp[pre][0] = 1;
            dp[pre][pre] = 1;
        }
        for (int pre = n - 1; pre >= 1; pre--) {
            for (int rest = pre + 1; rest <= n; rest++) {
                dp[pre][rest] = dp[pre + 1][rest];
                dp[pre][rest] += dp[pre][rest - pre];
            }
        }
        return dp[1][n];
    }

    public static void main(String[] args) {
        int test = 39;
        System.out.println(ways(test));
        System.out.println(dp1(test));
        System.out.println(dp2(test));
        System.out.println(dpSelf(test, test));
        new Thread(() -> {
            System.out.println("先执行外面的start()方法 再执行lamda表达式的代码逻辑");
        }).start();
    }

}
