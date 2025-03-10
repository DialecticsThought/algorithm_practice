package code_for_great_offer.class02;

import java.util.Arrays;

public class Code04_Drive {

    /**
     * 课上的现场版本
     * income -> N * 2 的矩阵 N是偶数！
     * 0 [9, 13]
     * 1 [45,60]
     * 表示0号司机去A的话，9块， 去B 的话 45
     * 1号司机去A的话，45块， 去B 的话 60
     *
     * @param income
     * @return
     */
    public static int maxMoney1(int[][] income) {
        if (income == null || income.length < 2 || (income.length & 1) != 0) {
            return 0;
        }
        int N = income.length; //TODO 司机数量一定是偶数，所以才能平分，A N /2 B N/2
        int M = N >> 1; //TODO  M = N / 2 要去A区域的人
        return process1(income, 0, M);
    }

    /**
     * [index]~[arr.len]所有的司机，往A和B区域分配！
     * A区域还有rest个名额!
     * 返回把[index]~[arr.len]司机，分配完，并且最终A和B区域同样多的情况下，[index]~[arr.len]这些司机，整体收入最大是多少！
     *
     * @param income
     * @param index
     * @param rest
     * @return
     */
    public static int process1(int[][] income, int index, int rest) {
        if (index == income.length) {
            return 0;
        }
        //TODO 还剩下司机！
        if (income.length - index == rest) {//TODO 如果剩下司机的数量等于A区域的名额  也就是B区域满了
            //TODO 当前司机一定去A服务 后面的司机继续递归
            return income[index][0] + process1(income, index + 1, rest - 1);
        }
        if (rest == 0) {//TODO 如果A区域的名额满了之后 剩下的司机一定去B区域
            return income[index][1] + process1(income, index + 1, rest);
        }
        //TODO  剩下的情况：
        //TODO  当前司机，可以去A，或者去B
        int p1 = income[index][0] + process1(income, index + 1, rest - 1);
        int p2 = income[index][1] + process1(income, index + 1, rest);
        return Math.max(p1, p2);
    }

    // 严格位置依赖的动态规划版本
    public static int maxMoney2(int[][] income) {
        int N = income.length;
        int M = N >> 1;
        int[][] dp = new int[N + 1][M + 1];
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= M; rest++) {
                if (N - index == rest) {
                    dp[index][rest] = income[index][0] + dp[index + 1][rest - 1];
                } else if (rest == 0) {
                    dp[index][rest] = income[index][1] + dp[index + 1][rest];
                } else {
                    int p1 = income[index][0] + dp[index + 1][rest - 1];
                    int p2 = income[index][1] + dp[index + 1][rest];
                    dp[index][rest] = Math.max(p1, p2);
                }
            }
        }
        return dp[0][M];
    }

    // 这题有贪心策略 :
    // 假设一共有10个司机，思路是先让所有司机去A，得到一个总收益
    // 然后看看哪5个司机改换门庭(去B)，可以获得最大的额外收益
    // 这道题有贪心策略，打了我的脸
    // 但是我课上提到的技巧请大家重视
    // 根据数据量猜解法可以省去大量的多余分析，节省时间
    // 这里感谢卢圣文同学
    public static int maxMoney3(int[][] income) {
        int N = income.length;
        int[] arr = new int[N];
        int sum = 0;
        for (int i = 0; i < N; i++) {
            arr[i] = income[i][1] - income[i][0];
            sum += income[i][0];
        }
        Arrays.sort(arr);
        int M = N >> 1;
        for (int i = N - 1; i >= M; i--) {
            sum += arr[i];
        }
        return sum;
    }

    // 返回随机len*2大小的正数矩阵
    // 值在0~value-1之间
    public static int[][] randomMatrix(int len, int value) {
        int[][] ans = new int[len << 1][2];
        for (int i = 0; i < ans.length; i++) {
            ans[i][0] = (int) (Math.random() * value);
            ans[i][1] = (int) (Math.random() * value);
        }
        return ans;
    }

    public static void main(String[] args) {
        int N = 10;
        int value = 100;
        int testTime = 500;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N) + 1;
            int[][] matrix = randomMatrix(len, value);
            int ans1 = maxMoney1(matrix);
            int ans2 = maxMoney2(matrix);
            int ans3 = maxMoney3(matrix);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }

}
