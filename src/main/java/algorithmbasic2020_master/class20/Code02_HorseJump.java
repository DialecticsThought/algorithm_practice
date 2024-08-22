package algorithmbasic2020_master.class20;

/**
 * 请同学们自行搜索或者想象一个象棋的棋盘，
 * 然后把整个棋盘放入第一象限，棋盘的最左下角是(0.0)位置那么整个棋盘就是横坐标上9条线、纵坐标上10条线的区域给你三个参数x, y, k
 * 返回“马”从(O,0)位置出发，必须走k步最后落在(x.y)上的方法数有多少种?
 */
public class Code02_HorseJump {

    /**
     * TODO 当前来到的位置是（x,y）
     * 还剩下rest步需要跳
     * 跳完rest步，正好跳到a，b的方法数是多少？
     * 10 * 9
     */
    public static int jump(int a, int b, int k) {
        //主函数：从(0,0)点出发 经过k步到达(a,b)
        return process(0, 0, k, a, b);
    }

    /**
     * 如果用dp的话是一个三维表
     * 因为x和y和rest都是变量
     * 所以可以简写成f(x,y,rest)
     * 三维表的某一个点[或者说是函数](x,y,rest) 依赖于其他8个点[或者说是函数]
     * (x+2,y+1,rest-1) (x+1,y+2,rest-1)
     * (x-1,y+2,rest-1) (x-2,y+1,rest-1)
     * (x-2,y-1,rest-1) (x-1,y-2,rest-1)
     * (x-2,y-1,rest-1) (x-1,y-2,rest-1)
     * (x+1,y-2,rest-1) (x+2,y-1,rest-1)
     * 也就是说3维表的某一个点依赖于该点的下一层的8个点 不依赖于其他层的点
     * 说明3维表的每一层都是从下往上写
     * eg:
     * (0,0) 走10步 (6,6)的方法数
     * 如果现在在A点，已经跳了9步了，也就是step当前=9，再跳一部到达(6,6)的重点 那个时候step=10
     * 只要关心如何通过9步到达A点就可以了
     * 9 ↑
     * 8 ↑                  ☆       ☆
     * 7 ↑              ☆              ☆
     * 6 ↑                      F
     * 5 ↑              ☆              ☆
     * 4 ↑                  ☆       ☆
     * 3 ↑
     * 2 ↑
     * 1 ↑
     *   → → → → → → → → → → → → → → → → →
     *      1   2   3   4   5   6   7   8
     * TODO
     * 当前在位置(x,y) 目标是(a,b) 剩下rest步要跳
     *
     * @param x
     * @param y
     * @param rest
     * @param a
     * @param b
     * @return
     */
    public static int process(int x, int y, int rest, int a, int b) {
        if (x < 0 || x > 9 || y < 0 || y > 8) {//TODO 判断越界的情况
            return 0;//TODO 返回0表示无法到达
        }
        if (rest == 0) {//TODO rest剩余步数为0 不能懂了
            //TODO 查看是否到达了终点 到达了终点  方法数+1 => rest = 0且 到达目的地
            // 没有到达了终点  方法数不加 就是返回0
            return (x == a && y == b) ? 1 : 0;
        }
        /**
         * TODO 以下是不越界 但是可以跳跃的情况：
         * 因为象棋中 马的跳法比较特殊
         * 所以 针对(a,b)
         * 我们可以理解为 有8个点
         * 设(m, n)为A点 从原点到达A点已经花了rest-1步 那么只要再走一步就可以到(a,b)
         * 那么我们就要考虑了 如果走rest-1步到达A点 然后不断地递归思考下去
         * */
        int ways = process(x + 2, y + 1, rest - 1, a, b);
        ways += process(x + 1, y + 2, rest - 1, a, b);
        ways += process(x - 1, y + 2, rest - 1, a, b);
        ways += process(x - 2, y + 1, rest - 1, a, b);
        ways += process(x - 2, y - 1, rest - 1, a, b);
        ways += process(x - 1, y - 2, rest - 1, a, b);
        ways += process(x + 1, y - 2, rest - 1, a, b);
        ways += process(x + 2, y - 1, rest - 1, a, b);
        return ways;
    }

    public static int dp(int a, int b, int k) {
        int[][][] dp = new int[10][9][k + 1];
        //TODO 因为 if (rest == 0) return (x == a && y == b) ? 1 : 0;
        dp[a][b][0] = 1;
        for (int rest = 1; rest <= k; rest++) {//TODO rest表示3维表的每一次遍历
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 9; y++) {
                    int ways = pick(dp, x + 2, y + 1, rest - 1);
                    ways += pick(dp, x + 1, y + 2, rest - 1);
                    ways += pick(dp, x - 1, y + 2, rest - 1);
                    ways += pick(dp, x - 2, y + 1, rest - 1);
                    ways += pick(dp, x - 2, y - 1, rest - 1);
                    ways += pick(dp, x - 1, y - 2, rest - 1);
                    ways += pick(dp, x + 1, y - 2, rest - 1);
                    ways += pick(dp, x + 2, y - 1, rest - 1);
                    dp[x][y][rest] = ways;
                }
            }
        }
        return dp[0][0][k];
    }

    //TODO 没有越界的情况下 从dp表拿值
    public static int pick(int[][][] dp, int x, int y, int rest) {
        if (x < 0 || x > 9 || y < 0 || y > 8) {//TODO 越界
            return 0;
        }
        return dp[x][y][rest];
    }

    public static int ways(int a, int b, int step) {
        return f(0, 0, step, a, b);
    }

    public static int f(int i, int j, int step, int a, int b) {
        if (i < 0 || i > 9 || j < 0 || j > 8) {
            return 0;
        }
        if (step == 0) {
            return (i == a && j == b) ? 1 : 0;
        }
        return f(i - 2, j + 1, step - 1, a, b) + f(i - 1, j + 2, step - 1, a, b) + f(i + 1, j + 2, step - 1, a, b)
                + f(i + 2, j + 1, step - 1, a, b) + f(i + 2, j - 1, step - 1, a, b) + f(i + 1, j - 2, step - 1, a, b)
                + f(i - 1, j - 2, step - 1, a, b) + f(i - 2, j - 1, step - 1, a, b);

    }

    public static int waysdp(int a, int b, int s) {
        int[][][] dp = new int[10][9][s + 1];
        dp[a][b][0] = 1;
        for (int step = 1; step <= s; step++) { // 按层来
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    dp[i][j][step] = getValue(dp, i - 2, j + 1, step - 1) + getValue(dp, i - 1, j + 2, step - 1)
                            + getValue(dp, i + 1, j + 2, step - 1) + getValue(dp, i + 2, j + 1, step - 1)
                            + getValue(dp, i + 2, j - 1, step - 1) + getValue(dp, i + 1, j - 2, step - 1)
                            + getValue(dp, i - 1, j - 2, step - 1) + getValue(dp, i - 2, j - 1, step - 1);
                }
            }
        }
        return dp[0][0][s];
    }

    // 在dp表中，得到dp[i][j][step]的值，但如果(i，j)位置越界的话，返回0；
    public static int getValue(int[][][] dp, int i, int j, int step) {
        if (i < 0 || i > 9 || j < 0 || j > 8) {
            return 0;
        }
        return dp[i][j][step];
    }

    public static void main(String[] args) {
        int x = 7;
        int y = 7;
        int step = 10;
        System.out.println(ways(x, y, step));
        System.out.println(dp(x, y, step));

        System.out.println(jump(x, y, step));
    }
}
