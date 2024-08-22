package algorithmbasic2020_master.class18;

/**
 * TODO [3,100,7]
 * 主函数： f1(0,2) => a g1(0,2) => b
 * A
 *                      f(0,2)
 *              3+后续 ↙   ↘ 7+后续
 *  香山返回7       g1(1,2)  g1(0,1)  向上返回3 因为 B先手让最烂的牌留给A，此时B是先手
 *              ↙   ↘       ↙   ↘
 *          f(2,2) f(2,2) f(1,1) f(0,0)
 *          ↓       ↓       ↓       ↓
 *          返回7   返回100  返回100 返回3
 * B
 *        g1(0,2)
 *      3+后续 ↙   ↘ 7+后续
 *    f1(1,2)           f1(0,1)
 *    ↙   ↘             ↙   ↘
 * g1(2,2) g1(2,2)     g1(1,1) g1(0,0)
 *    ↓     ↓           ↓       ↓
 * 什么都没有 因为最后一张牌  最后一张牌 后手什么都不拿
 * TODO 方面f和g函数的dp因为 L一定<R 所有dp表的下半部分不用
 * eg:
 * arr = [7,4,16,15,1]
 * f的dp
 *   0  1  2  3  4
 * 0 7
 * 1 x  4
 * 2 x  x  16 □ ←?
 *               ↓
 * 3 x  x  x 15  □
 * 4 x  x  x  x  1
 * 对角线是0的原因 f函数 是if(L == R) {return arr[i]};
 * f(arr,L ,R) 依赖于arr[L]+g(arr,L+1,R) arr[R]+g(arr,L,R-1)
 * g的dp
 *   0  1  2  3  4
 * 0 0
 * 1 x  0
 * 2 x  x  0  □ ←?
 * 3 x  x  x  0  □
 * 4 x  x  x  x  0
 * 对角线是0的原因 g函数 是if(L == R) {return 0};
 * g(arr,L ,R) 依赖于 f(arr,L+1,R) f(arr,L,R-1)
 * TODO
 * 这2个dp表都是从对角线开始向右方向 ↗ 相互地推导
 * f的dp表的倒数第1条对角线推出g的dp表第2条对角线(对角线是从左下向右上排序)
 * f的dp表的第2条对角线推出g的dp表第3条对角线
 * TODO
 * 先手
 * 先手执行 int f(arr,L,R) f的意思：作为先手，在L~R之间获得最优解
 * max(先手拿了左侧牌+执行后手函数,先手拿了右侧牌+执行后手函数)，max是因为要得到最优解
 * 后手
 * min([对方拿走L的话，那么执行f(arr,L+1,R)],[对方拿走R的话，那么执行f(arr,L,R-1)])
 * 执行f函数是因为后手的下一轮就是先手，会在下一轮得到最优解
 * 取min是因为对方在次轮作为先手会拿到最优解
 */
public class Code02_CardsInLine {

    // 根据规则，返回获胜者的分数
    public static int win1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int first = f1(arr, 0, arr.length - 1);//0~arr.length - 1的范围上先手得到的最优分
        int second = g1(arr, 0, arr.length - 1);//0~arr.length - 1的范围上后手得到的最优分
        //谁优谁获胜
        return Math.max(first, second);
    }

    /**
     * TODO 一轮里面只有一个人拿牌
     * 先手的人就是拿牌的人也就是执行f1
     * 后手的人不拿牌执行g2
     * arr[L..R]，先手获得的最好分数返回
     * 这个是先手的函数
     * L和R的范围都是0~arr.length - 1
     * 那么dp表示正方形
     */
    public static int f1(int[] arr, int L, int R) {
        if (L == R) {//说明值剩下最后一张牌的情况下 拿走
            return arr[L];
        }
        /**
         *TODO 第一种情况 先手（自己） 拿走了左侧的牌之后，
         * 自己在下一轮变成后手 在L+1到R的范围里拿牌
         * */
        int p1 = arr[L] + g1(arr, L + 1, R);//TODO 拿走最左侧的牌 L+1~R的范围上先手得到的最优分
        /**
         *TODO 第一种情况 先手（自己） 拿走了右侧的牌之后，
         * 自己在下一轮变成后手 在L到R-1的范围里拿牌
         * */
        int p2 = arr[R] + g1(arr, L, R - 1);//TODO 拿走最由侧的牌 L~R-1的范围上先手得到的最优分
        /**
         * TODO  先手是会比较到底拿走哪一侧牌得到最大的数 所以用max得到最大值
         * */
        return Math.max(p1, p2);
    }

    // arr[L..R]，后手获得的最好分数返回
    public static int g1(int[] arr, int L, int R) {
        if (L == R) {//TODO 说明值剩下最后一张牌的情况下 因为是后手 不拿 这牌是给先手（对手）的
            return 0;
        }
        /**
         *TODO
         * 对手拿走了L位置的牌的情况下
         * 自己（后手）是下一轮的先手 会在（L+1，R）的范围拿牌
         * */
        int p1 = f1(arr, L + 1, R);
        /**
         *TODO
         * 对手拿走了R位置的牌的情况下
         * 自己（后手）是下一轮的先手 会在（L，R-1）的范围拿牌
         * */
        int p2 = f1(arr, L, R - 1); // 对手拿走了R位置的数
        /**
         *TODO
         * 后手用min是因为当前是先手在拿牌 先手一定会拿到最大的牌 所以先手用max函数
         * 因为是零和博弈
         * 先手(对手) 一定会对后手(自己)最不利的方向决定 一定会让后手挑最小的牌
         * */
        return Math.min(p1, p2);
    }

    public static int win2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] fmap = new int[N][N];
        int[][] gmap = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                fmap[i][j] = -1;
                gmap[i][j] = -1;
            }
        }
        int first = f2(arr, 0, arr.length - 1, fmap, gmap);
        int second = g2(arr, 0, arr.length - 1, fmap, gmap);
        return Math.max(first, second);
    }

    // arr[L..R]，先手获得的最好分数返回
    public static int f2(int[] arr, int L, int R, int[][] fmap, int[][] gmap) {
        if (fmap[L][R] != -1) {
            return fmap[L][R];
        }
        int ans = 0;
        if (L == R) {
            ans = arr[L];
        } else {
            int p1 = arr[L] + g2(arr, L + 1, R, fmap, gmap);
            int p2 = arr[R] + g2(arr, L, R - 1, fmap, gmap);
            ans = Math.max(p1, p2);
        }
        fmap[L][R] = ans;
        return ans;
    }

    // // arr[L..R]，后手获得的最好分数返回
    public static int g2(int[] arr, int L, int R, int[][] fmap, int[][] gmap) {
        if (gmap[L][R] != -1) {
            return gmap[L][R];
        }
        int ans = 0;
        if (L != R) {
            int p1 = f2(arr, L + 1, R, fmap, gmap); // 对手拿走了L位置的数
            int p2 = f2(arr, L, R - 1, fmap, gmap); // 对手拿走了R位置的数
            ans = Math.min(p1, p2);
        }
        gmap[L][R] = ans;
        return ans;
    }

    public static int win3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        /*
         * 有两张表 一张是先手的 另一张是后手的
         * */
        int[][] fmap = new int[N][N];
        int[][] gmap = new int[N][N];
        for (int i = 0; i < N; i++) {
            /*
             * 根据 f1方法中的
             * 	if (L == R) {//说明值剩下最后一张牌的情况下 拿走
             * 	return arr[L];
             * 	}
             * 得到 张两张表 的对角线(L == R)就是 arr[i]
             * */
            fmap[i][i] = arr[i];
        }
        /*
         * 因为两张表的对角线都是已知的 （也就是上面的代码）
         * 对角线的起始点是（0,col)
         * eg 起始对角线的起始点是（0,0） 接下来是 （0,1） （0,2） 最后（0,3）
         * */
        for (int startCol = 1; startCol < N; startCol++) {
            int row = 0;
            int col = startCol;
            /*
             * 防止 对角线不超过两张表
             * N*N的表格 对角线一定是 小于 N
             * col 就是原先递归的L  row就是原先递归的R
             * */
            while (col < N && row < N) {
                /*
                 * 对手拿走了L位置的牌的情况下
                 * 自己（后手）是下一轮的先手 会在（L+1，R）的范围拿牌
                 * int p1 = f1(arr, L + 1, R);
                 * 第一种情况 先手（自己） 拿走了有侧的牌之后，
                 * 自己在下一轮变成后手 在L到R-1的范围里拿牌
                 * int p2 = f1(arr, L, R - 1);
                 * Math.max(p1, p2)先手是会比较到底拿走哪一侧牌得到最大的数 所以用max得到最大值
                 * */
                fmap[row][col] = Math.max(arr[row] + gmap[row + 1][col], arr[col] + gmap[row][col - 1]);
                /*
                 * 对手拿走了L位置的牌的情况下
                 * 自己（后手）是下一轮的先手 会在（L+1，R）的范围拿牌
                 * int p1 = f1(arr, L + 1, R);
                 * 对手拿走了R位置的牌的情况下
                 * 自己（后手）是下一轮的先手 会在（L，R-1）的范围拿牌
                 *  int p2 = f1(arr, L, R - 1); // 对手拿走了R位置的数
                 * 后手用min是因为当前是先手在拿牌 先手一定会拿到最大的牌 所以先手用max函数
                 * 因为是零和博弈
                 * return Math.min(p1, p2);
                 * */
                gmap[row][col] = Math.min(fmap[row + 1][col], fmap[row][col - 1]);
                row++;
                col++;
            }
        }
        /*根据暴力递归的主函数
         * 得到
         * int first = f1(arr, 0, arr.length - 1);
         * int second = g1(arr, 0, arr.length - 1);
         * return Math.max(first, second);
         * */
        return Math.max(fmap[0][N - 1], gmap[0][N - 1]);
    }

    public static void main(String[] args) {
        int[] arr = {5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7};
        System.out.println(win1(arr));
        System.out.println(win2(arr));
        System.out.println(win3(arr));

    }

}
