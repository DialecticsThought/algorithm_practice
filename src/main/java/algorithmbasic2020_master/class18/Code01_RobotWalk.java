package algorithmbasic2020_master.class18;

/**
 *
 * 假设有排成一行的N个位置，记为1~N，N一定大于或等于2开始时机器人在其中的M位置上(M一定是1~N中的一个)如果机器人来到1位置，那么下一步只能往右来到2位置;
 * 如果机器人来到N位置,那么下一步只能往左来到N-1位置;
 * 如果机器人来到中间位置，那么下一步可以往左走或者往右走;
 * 规定机器人必须走K步，最终能来到P位置(P也是1~N中的一个)的方法有多少和给定四个参数N、M、K、P，返回方法数。
 * 方法1：
 * 3 -> 2 -> 3 -> 2
 * 方法2：
 * 3 -> 2 -> 1 -> 2
 * 方法3：
 * 3 -> 4 -> 3 -> 2
 * [1 2 3 4 5 6] M = 3 P = 4 k = 3
 * 起始函数 f(6,3,3,4)  6是固定的  3是开始  3是可移动3步  4是最终目标
 * 中间2各参数是固定的 所以可以抽象为f(3,3)
 * 递归函数有2个固定的分支分别是向左走 向右走
 * <pre>
 * 		         f(3,3)
 * 	              L ↙    ↘ R
 * 	        f(2,2)	       	 f(4,2)
 *      L ↙   ↘ R 	        L ↙   ↘ R
 *  f(1,1) f(3,2)        f(3,1)   	  f(5,1)
 *   ↓ R	L ↙ ↘ R 	 L ↙ ↘ R		L ↙ ↘ R
 * f(2,0) f(2,1) f(4,1) f(2,0) f(4,0)	f(4,0) f(6,0)
 * </pre>
 *  <pre>
 * eg:
 * [1 2 3 4 5 6] M = 3 star = 3  aim = 5
 *           f(3,4)
 *         L ↙     ↘ R
 *       f(2,3)	     f(4,3)
 *      L ↙  ↘ R      L ↙  ↘ R
 *   f(1,2)	 f(3,2)  f(3,2)	 f(5,2)
 *   ............
 * </pre>
 */
public class Code01_RobotWalk {
    /**
     *
     * 假设有排成一行的N个位置，记为1~N，N一定大于或等于2开始时机器人在其中的M位置上(M一定是1~N中的一个)
     * 如果机器人来到1位置，那么下一步只能往右来到2位置;
     * 如果机器人来到N位置,那么下一步只能往左来到N-1位置;
     * 如果机器人来到中间位置，那么下一步可以往左走或者往右走;
     * 规定机器人必须走K步，最终能来到P位置(P也是1~N中的一个)的方法有多少和给定四个参数N、M、K、P，返回方法数。
     * 暴力递归就是一棵树 树的高度就是K☆☆☆☆☆☆☆☆☆☆
     */
    public static int ways1(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
            return -1;
        }
        //总共N个位置，从M点出发，还剩K步，返回最终能达到P的方法数
        return process1(start, K, aim, N);
    }

    /**
     *
     * 机器人当前来到的位置是cur，cur是可变参数
     * 机器人还有rest步需要去走， rest是可变参数
     * 最终的目标是aim，
     * 有哪些位置？1~N  N是固定参数
     * 返回：机器人从cur出发，走过rest步之后，最终停在aim的方法数，是多少？☆☆☆☆☆☆☆☆☆☆
     * int cur, int rest 这2个可变参数固定的话 那么返回值就会固定
     */
    public static int process1(int cur, int rest, int aim, int N) {
        // 如果没有剩余步数了，当前的cur位置就是最后的位置
        // 如果最后的位置停在P上，那么之前做的移动是有效的
        // 如果最后的位置没在P上，那么之前做的移动是无效的
        if (rest == 0) { // 如果已经不需要走了，走完了！
            //return cur == aim ? 1 : 0;
            if (cur == aim) {//走到底了 如果是最终目的地的话
                return 1;//返回1 表示找了一种方法 这个方法就是你之前的递归路径
            } else {
                return 0;//返回0 表示这个方法就是你之前的递归路径是错的
            }
        }
        /**
         * 如果还有rest步要走，而当前的cur位置在1位置上，那么根据规则当前这步只能从1走向2
         * 也就是递归函数的cur 传入2
         * 剩下的步数-1 也就是递归函数的rest 传入rest-1 还有rest-1步要走
         * aim最终目的地 和 N总数不变的
         * */
        if (cur == 1) { // 1 -> 2
            return process1(2, rest - 1, aim, N);
        }
        /**
         * 如果还有rest步要走，而当前的cur位置在N位置上，那么根据规则当前这步只能从N走向N-1
         * 也就是递归函数的cur 传入N-1
         * 剩下的步数-1 也就是递归函数的rest 传入rest-1 还有rest-1步要走
         * 相加是因为 左走 和右走 是不同的方法
         * */
        if (cur == N) { // N-1 <- N
            return process1(N - 1, rest - 1, aim, N);
        }
        /**
         * 如果还有rest步要走，而当前的cur位置在中间位置上，那么当前这步可以走向左,也可以走向右
         * 走向左之后，后续的过程就是，来到cur-1位置上，还剩rest-1步要走
         * 走向右之后，后续的过程就是，来到cur+1位置上，还剩rest-1步要走
         * 走向左、走向右是截然不同的方法，所以总方法数要都算上. 走向左的方法数+走向右的方法数
         * 相当于每棵树的某个节点（除了叶子结点）都有两个分支☆☆☆☆☆☆☆☆☆☆
         * */
        return process1(cur - 1, rest - 1, aim, N) + process1(cur + 1, rest - 1, aim, N);
    }

    // 把所有的cur和rest组合 返回的结果 加入到缓存中 因为有重复计算 会导致多次出现相同的cur和rest
    public static int ways2(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
            return -1;
        }
        /**
         * 为什么dp表的行：cur   列：rest 因为这两个是可变参数 只要这两个固定了 结果就出来了
         * 有N距离 移动的位置不会超过N  最多走K步 步数不会超过K
         * dp是一个缓存结构
         * 暴力递归就是一棵树 树的高度就是K
         * */
        int[][] dp = new int[N + 1][K + 1];
        for (int i = 0; i <= N; i++) {// 对整张表初始化 -1表示该组合还没有算过
            for (int j = 0; j <= K; j++) {
                dp[i][j] = -1;
            }
        }
        /**
         * dp就是缓存表
         * dp[cur][rest] == -1 -> process1(cur, rest)之前没算过！
         * dp[cur][rest] != -1 -> process1(cur, rest)之前算过！返回值，dp[cur][rest]
         * N+1 * K+1
         */
        return process2(start, K, aim, N, dp);
    }

    /**
     * 动态规划 之一 记忆化搜索
     * 暴力递归有重复计算 加缓存
     * 暴力递归的分析过程抽象出来就是dp的转移方程
     * 所有dp都来自于暴力递归
     * 为什么dp表的行：cur   列：rest 因为这两个是可变参数 只要这两个固定了 结果就出来了
     */
    // cur 范: 1 ~ N  那么cur的最大是N
    // rest 范：0 ~ K  那么rest的最大是K
    public static int process2(int cur, int rest, int aim, int N, int[][] dp) {
        if (dp[cur][rest] != -1) {// 计算过cur和rest的组合 这是第2次遇到 不算
            return dp[cur][rest];
        }
        // 之前没算过！
        int ans = 0;
        if (rest == 0) {
            //ans = cur == aim ? 1 : 0;
            if (cur == aim) {// 走到底了 如果是最终目的地的话
                /**
                 *  1 表示找了一种方法 这个方法就是你之前的递归路径
                 * 把当前 cur 和 rest的组合答案记录下来 表示已经算过了
                 * 相当于return 1;
                 * */
                dp[cur][rest] = 1;
                return dp[cur][rest];//返回
            } else {
                /**
                 *  0 表示这个方法就是你之前的递归路径是错的
                 * 把当前 cur 和 rest的组合记录下来 表示已经算过了
                 * */
                dp[cur][rest] = 0;
                return dp[cur][rest];//返回
            }
        } else if (cur == 1) {
            /**
             *  如果还有rest步要走，而当前的cur位置在1位置上，那么根据规则当前这步只能从1走向2
             * 也就是递归函数的cur 传入2
             * 剩下的步数-1 也就是递归函数的rest 传入rest-1 还有rest-1步要走
             * aim最终目的地 和 N总数不变的 和 dp记录
             * */
            //ans = process2(2, rest - 1, aim, N, dp);
            dp[cur][rest] = process2(2, rest - 1, aim, N, dp);
        } else if (cur == N) {
            /**
             *  如果还有rest步要走，而当前的cur位置在N位置上，那么根据规则当前这步只能从N走向N-1
             * 也就是递归函数的cur 传入N-1
             * 剩下的步数-1 也就是递归函数的rest 传入rest-1 还有rest-1步要走
             * aim最终目的地 和 N总数不变的 和 dp记录
             * */
            //ans = process2(N - 1, rest - 1, aim, N, dp);
            dp[cur][rest] = process2(N - 1, rest - 1, aim, N, dp);
        } else {
            //ans = process2(cur - 1, rest - 1, aim, N, dp) + process2(cur + 1, rest - 1, aim, N, dp);
            dp[cur][rest] = process2(cur - 1, rest - 1, aim, N, dp) + process2(cur + 1, rest - 1, aim, N, dp);
        }
		/*dp[cur][rest] = ans;
		return ans;*/
        return dp[cur][rest];
    }


    // cur 范: 1 ~ N  那么cur的最大是N
    // rest 范：0 ~ K  那么rest的最大是K
    public static int process3(int cur, int rest, int aim, int N, int[][] dp) {
        if (dp[cur][rest] != -1) {// 计算过cur和rest的组合 这是第2次遇到 不算
            return dp[cur][rest];
        }
        // 之前没算过！
        int ans = 0;
        if (rest == 0) {
            //ans = cur == aim ? 1 : 0;
            if (cur == aim) {// 走到底了 如果是最终目的地的话
                /**
                 *  1 表示找了一种方法 这个方法就是你之前的递归路径
                 * 把当前 cur 和 rest的组合答案记录下来 表示已经算过了
                 * 相当于return 1;
                 * */
                dp[cur][rest] = 1;
                return dp[cur][rest];//返回
            } else {
                /**
                 *  0 表示这个方法就是你之前的递归路径是错的
                 * 把当前 cur 和 rest的组合记录下来 表示已经算过了
                 * */
                dp[cur][rest] = 0;
                return dp[cur][rest];//返回
            }
        } else if (cur == 1) {
            /**
             *  如果还有rest步要走，而当前的cur位置在1位置上，那么根据规则当前这步只能从1走向2
             * 也就是递归函数的cur 传入2
             * 剩下的步数-1 也就是递归函数的rest 传入rest-1 还有rest-1步要走
             * aim最终目的地 和 N总数不变的 和 dp记录
             * */
            ans = process2(2, rest - 1, aim, N, dp);
        } else if (cur == N) {
            /**
             *  如果还有rest步要走，而当前的cur位置在N位置上，那么根据规则当前这步只能从N走向N-1
             * 也就是递归函数的cur 传入N-1
             * 剩下的步数-1 也就是递归函数的rest 传入rest-1 还有rest-1步要走
             * aim最终目的地 和 N总数不变的 和 dp记录
             * */
            ans = process2(N - 1, rest - 1, aim, N, dp);
        } else {
            ans = process2(cur - 1, rest - 1, aim, N, dp) + process2(cur + 1, rest - 1, aim, N, dp);
        }
        // 最后 在记入到缓存
        dp[cur][rest] = ans;
        return ans;
    }

    /**
     *
     * eg: M = 2 K = 5 P =3  N = 1 ~ 7
     * 对于dp表的第一列 用判断 if(rest == 0) {return cur == aim ? 1 : 0}
     *   0 1 2 3 4 5
     * 0 x x x x x x  因为 current != 0
     * 1       ? ? ?
     *        ↙ ↙ ↙  箭头表示依赖关系   if (cur == 1)  f(2, rest - 1);
     * 2     ? ? ?
     * 3 1
     *       ↖
     * 4       ? 因为递归有2个分支 所以对应到dp表 f(cur - 1, rest - 1) f(cur + 1, rest - 1)
     *       ↙
     * 5
     * 6     ? ? ?
     *        ↖ ↖ ↖
     * 7      ? ? ?  因为递归中 f(N - 1, rest - 1);
     *
     * @param N
     * @param start
     * @param aim
     * @param K
     * @return
     */
    public static int ways3(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
            return -1;
        }
        int[][] dp = new int[N + 1][K + 1];
        dp[aim][0] = 1;
        for (int rest = 1; rest <= K; rest++) {
            /**
             * else if (cur == 1) {
             * dp[cur][rest] = process2(2, rest - 1, aim, N, dp);
             * }
             * */
            dp[1][rest] = dp[2][rest - 1];
            for (int cur = 2; cur < N; cur++) {
                // dp[cur][rest] = process2(cur - 1, rest - 1, aim, N, dp) + process2(cur + 1, rest - 1, aim, N, dp);
                dp[cur][rest] = dp[cur - 1][rest - 1] + dp[cur + 1][rest - 1];
            }
            /**
             * else if (cur == N) {
             * 如果还有rest步要走，而当前的cur位置在N位置上，那么根据规则当前这步只能从N走向N-1
             * 也就是递归函数的cur 传入N-1
             * 剩下的步数-1 也就是递归函数的rest 传入rest-1 还有rest-1步要走
             * aim最终目的地 和 N总数不变的 和 dp记录
             *
             * dp[cur][rest] = process2(N - 1, rest - 1, aim, N, dp);
             * }
             * */
            dp[N][rest] = dp[N - 1][rest - 1];
        }
        return dp[start][K];
    }

    public static void main(String[] args) {
        System.out.println(ways1(5, 2, 4, 6));
        System.out.println(ways2(5, 2, 4, 6));
        System.out.println(ways3(5, 2, 4, 6));
    }

}
