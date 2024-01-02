package algorithmbasic2020_master.class059;

import java.util.Arrays;
import java.util.Comparator;

// 来自小红书
// [0,4,7] ： 0表示这里石头没有颜色，如果变红代价是4，如果变蓝代价是7
// [1,X,X] ： 1表示这里石头已经是红，而且不能改颜色，所以后两个数X无意义
// [2,X,X] ： 2表示这里石头已经是蓝，而且不能改颜色，所以后两个数X无意义
// 颜色只可能是0、1、2，代价一定>=0
// 给你一批这样的小数组，要求最后必须所有石头都有颜色，且红色和蓝色一样多，返回最小代价
// 如果怎么都无法做到所有石头都有颜色、且红色和蓝色一样多，返回-1
public class Code01_MagicStone {
    /**
     * 先统计所有石头的数量 然后统计出 已经是红色石头 和蓝色石头的数量
     * 判断已经是红色石头是不是超过了总数的一半
     * 判断已经是蓝色石头是不是超过了总数的一半
     * 还要判断石头的总数是不是奇数 因为要求是红色石头和蓝色石头数量相同 必然总数是偶数
     * eg： [0,3,7] [0,6,2] [0,2,9]  [0,6,4]  [0,1,3] 5个无色的石头 规定要分配 3个红 2个蓝
     * 解决方法 因为都是无色 索引一开始都变成红 看看一开始都变成红的总代价
     * 3+6+2+6+1=18  再从中 取出两个变蓝 条件： 红色和蓝色代价之差最大的 两个石头
     * 红色和蓝色代价之差越大 说明 变成红色代价比蓝色更大
     * 所以分别是 -4， 4， -7， 2， -2  就决定[0,6,2]和[0,6,4]变成蓝色
     */
    public static int minCost(int[][] stones) {
        int n = stones.length;
        if ((n & 1) != 0) {
            return -1;
        }
        Arrays.sort(stones, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                if (a[0] == 0 && b[0] == 0) {
                    //return (b[1] - b[2] - a[1] + a[2]);
                    return ((b[1] - a[1]) - (b[2] - a[2]));
                } else {
                    return (a[0] - b[0]);
                }
                //return a[0] == 0 && b[0] == 0 ? (b[1] - b[2] - a[1] + a[2]) : (a[0] - b[0]);
            }
        });
        int zero = 0;
        int red = 0;
        int blue = 0;
        int cost = 0;
        for (int i = 0; i < n; i++) {
            if (stones[i][0] == 0) {
                zero++;
                cost += stones[i][1];
            } else if (stones[i][0] == 1) {
                red++;
            } else {
                blue++;
            }
        }
        if (red > (n >> 1) || blue > (n >> 1)) {
            return -1;
        }
        blue = zero - ((n >> 1) - red);
        for (int i = 0; i < blue; i++) {
            cost += stones[i][2] - stones[i][1];
        }
        return cost;
    }

    public static void main(String[] args) {
        int[][] stones = {{1, 5, 3}, {2, 7, 9}, {0, 6, 4}, {0, 7, 9}, {0, 2, 1}, {0, 5, 9}};
        System.out.println(minCost(stones));
    }

}
