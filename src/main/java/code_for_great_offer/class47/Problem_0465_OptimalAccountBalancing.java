package code_for_great_offer.class47;

import java.util.Arrays;
import java.util.HashMap;

/**
 * TODO  本质求的是最多划分一个大集合变成多个集合 让集合==0， 让集合的个数最多
 * 一群朋友在度假期间会相互借钱。比如说，小爱同学支付了小新同学的午餐共计 10 美元。如果小明同学支付了小爱同学的出租车钱共计 5 美元。
 * 我们可以用一个三元组 (x, y, z) 表示一次交易，表示 x 借给 y 共计 z 美元。
 * 用 0, 1, 2 表示小爱同学、小新同学和小明同学（0, 1, 2 为人的标号），上述交易可以表示为 [[0, 1, 10], [2, 0, 5]]。
 * 给定一群人之间的交易信息列表，计算能够还清所有债务的最小次数。
 * 注意：
 * 一次交易会以三元组 (x, y, z) 表示，并有 x ≠ y 且 z > 0。
 * 人的标号可能不是按顺序的，例如标号可能为 0, 1, 2 也可能为 0, 2, 6。
 * 提示：
 * 1 <= transactions.length <= 8   状态压缩
 * transactions[i].length == 3
 * 0 <= from_i, to_i < 12
 * from_i != to_i
 * 1 <= amount[i]<= 100
 * <p>
 * 需要证明：
 * 一个集合中，假设整体的累加和为K，
 * 不管该集合用了什么样的0集合划分方案，当一个新的数到来时：
 * 1) 如果该数是-K，那么任何0集合的划分方案中，因为新数字的加入，0集合的数量都会+1
 * 2) 如果该数不是-K，那么任何0集合的划分方案中，0集合的数量都会不变
 * eg:
 * [0,1,10] 0当前-10元  欠了10元
 * [2,0,5]  2当前-5元  欠了5元  0当前-5元  欠了5元
 * 平账
 * [1,0,5]  0当前0元  欠了0元
 * [1,2,5]  2当前0元  欠了0元
 * eg:
 * [0,1,10] 0当前-10元  1当前10元 2当前0元
 * [1,0,1]  0当前-9元   1当前9元  2当前0元
 * [1,2,5]  1当前4元   2当前5元   0当前-9元
 * [2,0,5]  2当前0元   0当前-4元  1当前4元
 * 如果想要让 0 ， 1,2者3个人都是0  以最快的速度
 * 那么就是[1,0,4]
 * <p>
 * 状态压缩  用位运算
 * 0 1  2   3  这4个人发生了若干个交易
 * 5 -4 -1 0
 * 意思是
 * 0号要给出去5元
 * 1号要收回4元
 * 2号要收回1元
 * 3号 已经没有用了  只要关注0,1,2的要给出去和收回的钱即可
 * 写成一个数组[5,-4,-1] 对应的人无所谓  只要通过手段让数组的累加和变成 0
 */
public class Problem_0465_OptimalAccountBalancing {

    // 用位信息替代集合结构的暴力尝试
    public static int minTransfers1(int[][] transactions) {
        /**
         * 不管转账有几笔，最终每个人收到的钱，如果是0，不进入debt数组
         * 只有最终收到的钱，不等于0的人，进入debt数组
         * 收到的钱，4，说明该给出去！
         * 收到的钱，-4，说明该要回来！
         * debt数组的累加和，必为0！
         */
        int[] debt = debts(transactions);
        int N = debt.length;
        return N - process1(debt, (1 << N) - 1, 0, N);
    }

    /**
     * set -> int -> 不使用值 -> 只使用状态！
     * eg:001110
     * 0号人，不在集合里；1、2、3号人在集合里，4、5号人不在集合里！
     * sum -> set这个集合累加和是多少？sum被set决定的！
     * debt数组，收到的钱的数组(固定)
     * N, debt的长度(固定)
     * <p>
     * <p>
     * 返回值含义 : set这个集合中，最多能划分出多少个小集合累加和是0，返回累加和是0的小集合最多的数量
     * eg: [7,-7,5,-5,3,1]  这是一个大集合
     * {[7,-7] [5,-5] [3] [1]}  返回2 也就是[7,-7] [5,-5]
     * eg: [3,-3,3-1,-2,2,1]
     * {[-2,2],[-1,1],[-3,3],[3]} 返回3  选这个
     * {[3,-3],[3,-1,-2],[1],[2]} 返回2
     * eg:
     * [1,2,-3]  这是一个大集合 这个集合的意思是 要给出和收回的钱的组成的数组
     * {[1,2,-3]} 划分后返回1   那么总共的交易最少就是2次 也就是 持有1的人把1给持有-3的人 持有2的人把2给持有-3的人
     * 也就是数组大集合的size3-返回的1=2
     * eg:
     * [1,2,-3,3,3,1,2]  这是一个大集合
     * {[1,-1],[-2,2],[3,-3],3} 划分后返回3
     * 也就是数组大集合的size7-返回的3=4
     *
     * @param debt
     * @param set
     * @param sum
     * @param N
     * @return 只有一个可变参数 因为 sum由set决定
     */
    public static int process1(int[] debt, int set, int sum, int N) {
        /**
         * (set & (set - 1)) == 0 set中只有一个人的时候！ 也就是set里面只有1个1
         * set: 0 0 1 0 0  表示：集合中只有一个人
         * set-1: 0 0 0 1 1
         * (set & (set - 1)) == 0
         * debt中，没有0的，所以每个人一定都需要转账！
         */
        if ((set & (set - 1)) == 0) {
            return 0;
        }
        int value = 0;
        int max = 0;
        /**
         * 有一个8个位的数
         * eg:1111  4个人
         * 1 1 1 1
         * 3 2 1 0  分别对应的几号人
         * 假设3号人最后的交易者考虑 考虑另外3个
         * 假设2号人最后的交易者考虑 考虑另外3个
         * 假设1号人最后的交易者考虑 考虑另外3个
         * 假设0号人最后的交易者考虑 考虑另外3个
         *
         *
         * 尝试，每一个人都最后考虑
         * 0,如果最后考虑
         * 1,如果最后考虑
         * 2,如果最后考虑
         * ....
         * n-1，最后考虑
         * eg:
         * 0 1 1 0 0 1
         * 5 4 3 2 1 0
         * 1号人（不能考虑！因为不在集合里！）
         */
        for (int i = 0; i < N; i++) {
            value = debt[i];
            if ((set & (1 << i)) != 0) { // i号人真的在集合里，才能考虑！
                /**
                 * 011001
                 * 3号人在
                 * 3号人考虑之前，考虑其他人 也就是010001（考虑0号人和4号人剩下的情况）
                 * set ^ (1 << i) 就是把第i位的1 删去 ，sum-value， value就是i号人的金额
                 * process ( set ^ (1 << i) , sum - value )
                 * 如果几号人用字母表示的话
                 * eg： a b c d e f
                 * 把a排除掉 考虑 其他的出现 为0的子集合 假设2个
                 * 把b排除掉 考虑 其他的出现 为0的子集合 假设1个
                 * 把c排除掉 考虑 其他的出现 为0的子集合 假设3个
                 * 。。。。
                 * 得到上面最大的max
                 * 如果 a b c d e f  对应的金额（给出去的/收进来的）的综合=0
                 * 设a=7 把a排除掉 考虑 其他的出现 为0的子集合 假设2个， 其他不构成集合的元素 一定是-7
                 * 设b=3 把b排除掉 考虑 其他的出现 为0的子集合 假设1个， 其他不构成集合的元素 一定是-3
                 * 设c=4 把c排除掉 考虑 其他的出现 为0的子集合 假设3个， 其他不构成集合的元素 一定是-4
                 *
                 * 并且 不排除任何一个 ，全部考虑进来 为0的子集合 一定是上面的max+1
                 * 这个1是排除不构成集合的元素
                 * 如果 a b c d e f  对应的金额（给出去的/收进来的）的综合！=0
                 * 还是
                 * 设a=7 把a排除掉 考虑 其他的出现 为0的子集合 假设2个， 其他不构成集合的元素 一定是-7
                 * 设b=3 把b排除掉 考虑 其他的出现 为0的子集合 假设1个， 其他不构成集合的元素 一定是-3
                 * 设c=4 把c排除掉 考虑 其他的出现 为0的子集合 假设3个， 其他不构成集合的元素 一定是-4
                 * ...
                 * 得到上面最大的max
                 * 不排除任何一个 ，全部考虑进来 为0的子集合 一定是上面的max  因为整体累加和！=0多不出一个元素
                 */
                max = Math.max(max, process1(debt, set ^ (1 << i), sum - value, N));
            }
        }
        return sum == 0 ? max + 1 : max;
    }

    // 上面的尝试过程 + 记忆化搜索
    // 最优解
    public static int minTransfers2(int[][] transactions) {
        int[] debt = debts(transactions);
        int N = debt.length;
        int sum = 0;
        for (int num : debt) {
            sum += num;
        }
        int[] dp = new int[1 << N];
        Arrays.fill(dp, -1);
        return N - process2(debt, (1 << N) - 1, sum, N, dp);
    }

    /**
     * 只有一个可变参数 因为 sum由set决定
     *
     * @param debt
     * @param set
     * @param sum
     * @param N
     * @param dp
     * @return
     */
    public static int process2(int[] debt, int set, int sum, int N, int[] dp) {
        if (dp[set] != -1) {
            return dp[set];
        }
        int ans = 0;
        if ((set & (set - 1)) != 0) {
            int value = 0;
            int max = 0;
            for (int i = 0; i < N; i++) {
                value = debt[i];
                if ((set & (1 << i)) != 0) {
                    max = Math.max(max, process2(debt, set ^ (1 << i), sum - value, N, dp));
                }
            }
            ans = sum == 0 ? max + 1 : max;
        }
        dp[set] = ans;
        return ans;
    }

    public static int[] debts(int[][] transactions) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int[] tran : transactions) {
            map.put(tran[0], map.getOrDefault(tran[0], 0) + tran[2]);
            map.put(tran[1], map.getOrDefault(tran[1], 0) - tran[2]);
        }
        int N = 0;
        for (int value : map.values()) {
            if (value != 0) {
                N++;
            }
        }
        int[] debt = new int[N];
        int index = 0;
        for (int value : map.values()) {
            if (value != 0) {
                debt[index++] = value;
            }
        }
        return debt;
    }

    // 为了测试
    public static int[][] randomTrans(int s, int n, int m) {
        int[][] trans = new int[s][3];
        for (int i = 0; i < s; i++) {
            trans[i][0] = (int) (Math.random() * n);
            trans[i][1] = (int) (Math.random() * n);
            trans[i][2] = (int) (Math.random() * m) + 1;
        }
        return trans;
    }

    // 为了测试
    public static void main(String[] args) {
        int s = 8;
        int n = 8;
        int m = 10;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[][] trans = randomTrans(s, n, m);
            int ans1 = minTransfers1(trans);
            int ans2 = minTransfers2(trans);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("测试结束");
    }

}
