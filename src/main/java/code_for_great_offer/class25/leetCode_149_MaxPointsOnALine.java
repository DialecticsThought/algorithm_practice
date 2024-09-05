package code_for_great_offer.class25;

import java.util.HashMap;
import java.util.Map;

// 本题测试链接: https://leetcode.cn/problems/max-points-on-a-line/
public class leetCode_149_MaxPointsOnALine {

    // [
    //    [1,3]
    //    [4,9]
    //    [5,7]
    //   ]
    public static int maxPoints(int[][] points) {
        if (points == null) {
            return 0;
        }
        if (points.length <= 2) {
            return points.length;
        }
        /*
         *TODO
         * <分子，<分母，该斜率出现的次数>>
         * <3,<7,10>>  -> 斜率为3/7的线 有10个
         * <5,<5,15>> -> 斜率为3/5的线 有15个
         * */
        Map<Integer, Map<Integer, Integer>> map = new HashMap<Integer, Map<Integer, Integer>>();
        int result = 0;
        /*
         *TODO
         * 2个for循环 最外层遍历第一个点 里面for循环遍历第二个点
         * 并且每个第二个点 都从 第一个点位置开始往后遍历 不用遍历第一个点之前的点了
         * */
        for (int i = 0; i < points.length; i++) {
            //当你来到新的一个出发点的时候，之前的记录就失效了
            map.clear();
            int samePosition = 1;//共位置的点 初始就是1 出发点自己
            int sameX = 0; //共水平线
            int sameY = 0; //共竖线
            int line = 0;
            for (int j = i + 1; j < points.length; j++) { // i号点，和j号点，的斜率关系
                // 斜率 k = （y2-y1）/ (x2-x1)
                int x = points[j][0] - points[i][0];
                int y = points[j][1] - points[i][1];
                if (x == 0 && y == 0) {//说明2个点是一个点
                    samePosition++;
                } else if (x == 0) {//说明共水平线
                    sameX++;
                } else if (y == 0) {//说明 共竖线
                    sameY++;
                } else { // 普通斜率
                    /*
                     *TODO
                     * 找到分子 分母的最大公约数
                     * eg： 4/6 => 2/3 最大公约数 = 2
                     * */
                    int gcd = gcd(x, y);
                    x /= gcd;
                    y /= gcd;
                    // x / y
                    if (!map.containsKey(x)) {
                        map.put(x, new HashMap<Integer, Integer>());
                    }
                    if (!map.get(x).containsKey(y)) {
                        map.get(x).put(y, 0);
                    }
                    map.get(x).put(y, map.get(x).get(y) + 1);
                    //  共水平线 共竖线 和 所有斜率 中 哪个出现次数最多 就是line
                    line = Math.max(line, map.get(x).get(y));
                }
            }
            result = Math.max(result, Math.max(Math.max(sameX, sameY), line) + samePosition);
        }
        return result;
    }

    //TODO 保证初始调用的时候，a和b不等于0 gcd 找到最高公约数 O(1)
    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static class Node {
        public int x;
        public int y;
    }

    public static int maxPoints(Node[] nodes) {
        /*
         * 讨论node0 和node1 之间的斜率 node0 和node2的之间的斜率......
         * i和 i+1 i和i+2 i和i+3
         * */
        for (int i = 0; i < nodes.length; i++) {

            for (int j = i + 1; j < nodes.length; j++) {
                int sameXY = 1;//i 共点
                int difXYSameX = 0;//共竖线
                int difXYSameY = 0;//共横线
                //每一种斜率 都有记录 和i点之间的斜率为key 有value个
                HashMap<Double, Integer> map = new HashMap<>();
                //共点
                if (nodes[j].x == nodes[i].x && nodes[j].y == nodes[i].y) {
                    sameXY++;
                } else if (nodes[j].x == nodes[i].x) {
                    difXYSameX++;
                } else if (nodes[j].y == nodes[i].y) {
                    difXYSameX++;
                } else {
                    //因为double精度问题 要改良
                    double p = (double) (nodes[j].y - nodes[i].y) / (double) (nodes[j].x - nodes[i].x);
                    /*
                     * 两个数找到最大公约数 gcd算法 eg： 6/10  =》 3/5
                     * 然后key 写成String "3_5"
                     * */
                    if (map.containsKey(p)) {
                        map.put(p, map.get(p) + 1);
                    } else {
                        map.put(p, 1);
                    }
                }
            }
        }
        return 1;
    }
}
