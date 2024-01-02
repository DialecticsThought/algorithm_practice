package algorithmbasic2020_master.class19;

public class Code01_Knapsack {

    /**
     * TODO 所有的货，重量和价值，都在w和v数组里
     * 为了方便，其中没有负数
     * bag背包容量，不能超过这个载重
     * 返回：不超重的情况下，能够得到的最大价值
     */
    public static int maxValue(int[] w, int[] v, int bag) {
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        // 尝试函数！
        return process(w, v, 0, bag);
    }

    /**
     * TODO 总价值指的是 从index开始往后产生的最大价值 之前的不用管
     * 0---index-1上做了货物的选择 使得你已经达到了重量是alreadyW
     * 所有货物的价值和重量都在数组里面 这两个数组 不变
     * 之前做的选择 使得已经达到的负重是多少
     * 包的总载重 不变
     * 返回-1 认为没有方案
     * 不返回-1 认为返回的值是真实的价值
     */
    public static int process2(int[] weight, int[] value, int index, int alreadyW, int bag) {
        if (alreadyW > bag) {//TODO 表示  认为没有方案
            return -1;
        }
        if (index == weight.length) {//TODO 重量没超 如果index来到了最终位置 也就是没货
            //TODO 你要的价值是index之后所能产生的最大价值 不用管index之前的价值
            return 0;
        }
        /**
         *TODO
         * 第1种选择 没有要当前index的货 的情况下 后续所产生的最大价值 alreadyW没有动直接传入递归
         * 第2种选择 要当前index的货 的情况下  后续所产生的最大价值 alreadyW+当前index对应的货的重量
         * */
        int p1 = process2(weight, value, index + 1, alreadyW, bag);
        int next = process2(weight, value, index + 1, alreadyW + weight[index], bag);
        int p2 = -1;
        if (next != -1) {//TODO 如果p2后续是有效的 如果p2后续是无效 p2=0 意思：没有p2这个可能性
            //TODO 第2种选择 的总价值 = 当前货的价值+后续的最大的价值
            p2 = value[index] + next;
        }
        /*
         * 查看 两种选择中 产生价值最大的
         * */
        return Math.max(p1, p2);
    }

    /**
     * TODO 总价值指的是 从index开始往后产生的最大价值 之前的不用管
     * index之后的货物的自由选择 但是不能超过rest剩余的空间  也就是说rest不能小于0
     * 所有货物的价值和重量都在数组里面 这两个数组 不变
     * 之前做的选择 使得已经达到的负重是多少
     * 返回-1 认为没有方案
     * 不返回-1 认为返回的值是真实的价值
     */
    public static int process(int[] w, int[] v, int index, int rest) {
        if (rest < 0) {//TODO  某一步 剩余的空间小于0 认为你从开始选到某一步（也就是index）的方案是无效的
            return -1;
        }
        if (index == w.length) {//TODO  如果index来到了最终位置 也就是没货了
            return 0;//TODO  从index开始没货了 就是没有价值
        }
        //TODO  第1种选择 没有要当前index的货 的情况下 后续所产生的最大价值
        //TODO  p1不用判断-1 因为 执行这段代码的时候 rest > 0 只有可能p1后续违规
        int p1 = process(w, v, index + 1, rest);
        int p2 = 0;
        //TODO  第2种选择 要当前index的货 的情况下  后续所产生的最大价值  rest - w[index] 体现了
        int next = process(w, v, index + 1, rest - w[index]);
        if (next != -1) {//TODO 如果p2后续是有效的 如果p2后续是无效 p2=0 意思：没有p2这个可能性
            p2 = v[index] + next;//当前 index的货+后续
        }
        return Math.max(p1, p2);
    }

    /**
     * 首先要确定这个暴力递归是不是有重复解
     */
    public static int dp(int[] w, int[] v, int bag) {
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        int N = w.length;
        //TODO N + 1 的 "+ 1"指的是 0--N 0是多一个的
        int[][] dp = new int[N + 1][bag + 1];//准备一个缓存
        /*
         *TODO dp[N][...] = 0 因为创建数组的时候默认初始化
         * 实现了
         * if (index == w.length) {//如果index来到了最终位置 也就是没货了
         *	return 0;}//从index开始没货了 就是没有价值
         * */
        for (int index = N - 1; index >= 0; index--) {//TODO 从矩阵表中的下面的行到上面的行 开始
            for (int rest = 0; rest <= bag; rest++) {//TODO 从 矩阵表中的左侧不断到右侧 开始
                //每次都是从下往上 同一行中 从左往右
                /**
                 * 递归函数怎么调用 dp写么写
                 * 试图搞定dp[index][rest]
                 *TODO int p1 = process(w, v, index + 1, rest); 第1种选择 没有要当前index的货 的情况下 后续所产生的最大价值
                 * 也就是说 dp表中的index行rest列（dp[index][rest]） 取决于 dp[index + 1][rest]
                 * rest 你没有选当前index的货 bag的容量就不减少
                 * index + 1表示 查看第index+1的选择
                 */
                int p1 = dp[index + 1][rest];
                int p2 = 0;//和递归相同
                /**
                 *TODO int next = rest - w[index] < 0 ? -1 : dp[index + 1][rest - w[index]];
                 * 首先 判断 塞入index下标对应的物品后 是否>0 也就是有效
                 * */
                int next;
                if (rest - w[index] < 0) {
                    next = -1;
                } else {
                    /**
                     *TODO 第2种选择 要当前index的货 的情况下  后续所产生的最大价值
                     * int next = process(w, v, index + 1, rest - w[index]);
                     * rest - w[index]体现了 你选了当前index的货 bag的容量就要减少
                     * index + 1表示 查看第index+1的选择
                     * */
                    next = dp[index + 1][rest - w[index]];
                }
                if (next != -1) {//如果p2后续是有效的 如果p2后续是无效 p2=0 意思：没有p2这个可能性
                    p2 = v[index] + next;//当前 index的货+后续
                }
                /*
                 * dp表的index行rest列 就是取 p1和p2最好的选择
                 * */
                dp[index][rest] = Math.max(p1, p2);
            }
        }
        return dp[0][bag];
    }

    public static void main(String[] args) {
        int[] weights = {3, 2, 4, 7, 3, 1, 7};
        int[] values = {5, 6, 3, 19, 12, 4, 2};
        int bag = 15;
        System.out.println(maxValue(weights, values, bag));
        System.out.println(dp(weights, values, bag));
    }

}
