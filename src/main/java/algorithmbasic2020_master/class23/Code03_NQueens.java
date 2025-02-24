package algorithmbasic2020_master.class23;

import java.util.ArrayList;
import java.util.List;

public class Code03_NQueens {
    /*
     * N皇后问题是指在NN的棋盘上要摆N个皇后，
     * 要求任何两个皇后不同行、不同列,也不在同一条斜线上给定一个整数n,返回n皇后的摆法有多少种。
     * n=1，返回1
     * n=2或3，2皇后和3皇后问题无论怎么摆都不行,返回0
     * n=8，返回92
     * */
    public static int num1(int n) {
        if (n < 1) {
            return 0;
        }
        int[] record = new int[n];
        return process1(0, record, n);
    }

    /**
     * TODO
     * 当前来到i行，一共是0~N-1行
     * 在i行上放皇后，所有列都尝试
     * 必须要保证跟之前所有的皇后不打架
     * int[] record record[x] = y 之前的第x行的皇后，放在了y列上 存放了[0-i-1]的皇后的准确位置
     * n代表一共有n行 0--n-1
     * 返回：不关心i之前行发生了什么，i开始....i后面 后续有多少合法的方法数
     * eg： 现在在摆第n行的皇后 那么是在0--n-1行的皇后确定位置的情况下 移动第n行的皇后查看合适的位置
     */
    public static int process1(int i, int[] record, int n) {
        if (i == n) { //TODO 来到了终止行
            return 1;//TODO 只有一种情况： 就是之前的所有情况
        }
        //TODO  哈没有到终止位置 还有皇后要摆
        int res = 0;
        //TODO  第i行的皇后，放哪一列呢？j列，=> 枚举尝试
        for (int j = 0; j < n; j++) {//TODO 当前行在i行 尝试i行所有的列 也就是j
            /*
             *TODO
             * 表示传入要摆的皇后的当前坐标i行j列 和 record 判断要摆的皇后的当前位置有不有效
             * 不冲突 有效  直接尝试下一行
             * 冲突 尝试下一列
             * 不需要还原现场是因为 如果当前皇后所处的坐标i行j列 不符合要求 直接改动就可以了
             * */
            if (isValid(record, i, j)) {
                record[i] = j;
                //TODO 第i行已经选完了，开始选第i+1行的皇后
                res += process1(i + 1, record, n);
            }
        }
        return res;
    }


    /*
     *TODO
     *  没有必要加上行的判断
     * 加入两个坐标(a,b) (c,d)
     * 共列的话就是 b == d
     * 共斜线的话就是 |a-c| = |b-d|
     * eg (a,b) =（2,3） (c,d) =（4,5） 共线
     * 那么 |a-c| = |2-4|   |b-d| = |3-5|
     * 只要查看 record[0...i-1] i之后的不需要看 因为还没有记录
     * */
    public static boolean isValid(int[] record, int i, int j) {
        //TODO 0..i-1 遍历0-i-1个皇后
        for (int k = 0; k < i; k++) {//TODO 0-i-1之间的某一行皇后 某一行记名为k
            /*
             *TODO  k行皇后的列就是record[k] 和 j列做对比
             * */
            if (j == record[k] || Math.abs(record[k] - j) == Math.abs(i - k)) {
                return false;
            }
        }
        return true;
    }

    // 请不要超过32皇后问题
    public static int num2(int n) {
        if (n < 1 || n > 32) {
            return 0;
        }
        int limit;
        // 如果你是13皇后问题，limit 最右13个1，其他都是0
        //int limit = n == 32 ? -1 : (1 << n) - 1;
        if (n == 32) {
            limit = -1;
        } else {
            /*
             * n = 8   <<表示向左移位
             * 1 << 8 => 1000 0000
             * 1000 0000 -1 = 0 1111 1111
             * */
            limit = (1 << n) - 1;
        }
        return process2(limit, 0, 0, 0);
    }

    /*
     * 8皇后问题 就准备一个数 这个数的二进制 是 ...000..000 1111 1111 有8个1
     * 8皇后问题 就准备一个数 这个数的二进制 是 ...000..000 1111 1111 有8个1
     * 放一个皇后需要考虑 列 左对角线 右对角线
     * 之前皇后的列影响：colLim 1的位置不能放皇后 0的位置可以
     * 之前皇后的左下对角线影响：leftDiaLim  1的位置不能放皇后 0的位置可以
     * 之前皇后的右下对角线影响：rightDiaLim  1的位置不能放皇后 0的位置可以
     *
     * limit 划定了问题的规模
     * */
    public static int process2(int limit, int colLim, int leftDiaLim, int rightDiaLim) {
        /*
         * 8皇后问题
         * 第2列摆放了皇后 0000 0000 =》 0100 0000
         * 如果都是1 也就是 1111 1111 那么和limit相同 说明每一列都摆满了皇后
         * 直接返回-1
         * */
        if (colLim == limit) {
            return 1;
        }
        // pos中所有是1的位置，是你可以去尝试皇后的位置
        /*
         * “|”表示或运算
         * (colLim | leftDiaLim | rightDiaLim)就是总限制
         * eg：  列限制：...000 0010 0000
         * 	   左限制：...000 0100 0000
         *      右限制：...000 0000 1000
         * 总线制：...000 0011 1000 再取反“~” 变成 ...111 1100 0111
         * 1100 0111 中的 1 就是可以摆放皇后的位置  最左侧的一坨1 是干扰项
         * 如何删掉 干扰项 就是和 limit 与运算 只有都是1 才能得到1  得到了最终的结果pos
         * eg： 已经得到了最终的结果 ..000 0110 0010 每次提取最右侧的1来
         * a & (~a + 1)
         * a = 0101 1000 要取出最右侧的1 也就是 0000 1000
         * 0101 1000取反（~） => 1010 0111
         *       1010 0111
         *       +       1
         * 结果： 1010 1000
         * 与:   0101 1000
         * 结果： 0000 1000
         * */
        int pos = limit & (~(colLim | leftDiaLim | rightDiaLim));
        int mostRightOne = 0;
        int res = 0;
        while (pos != 0) {
            //提取出pos中的最右侧的1 盛夏未至都是0
            mostRightOne = pos & (~pos + 1);
            //当前pos 减掉最右侧的1 也就是提取出最右侧的1 下一轮循环再提取最右侧的1
            pos = pos - mostRightOne;
            /*
             * 最右侧的1 是放皇后的位置
             * 下一轮 列限制  就是 这一轮递归中 列限制 或 此时摆放的皇后的位置
             * 下一轮 左限制  就是 这一轮递归中 左限制 或 此时摆放的皇后的位置 并左移一位
             * 下一轮 右限制  就是 这一轮递归中 右限制 或 此时摆放的皇后的位置 并右移一位
             * */
            res += process2(limit,
                    colLim | mostRightOne,
                    (leftDiaLim | mostRightOne) << 1,
                    (rightDiaLim | mostRightOne) >>> 1);
        }
        return res;
    }

    public static void main(String[] args) {
        int n = 15;

        long start = System.currentTimeMillis();
        System.out.println(num2(n));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println(num1(n));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

    }
}
