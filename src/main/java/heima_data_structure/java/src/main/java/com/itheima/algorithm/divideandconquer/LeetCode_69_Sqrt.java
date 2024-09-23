package heima_data_structure.java.src.main.java.com.itheima.algorithm.divideandconquer;

/**
 * <h3>平方根整数部分</h3>
 */
public class LeetCode_69_Sqrt {

    /**
     * 现在要求99的平方根
     * 9*9 = 81
     * 10*10 = 100
     * 那么99的平方根在 9 ~ 10 之间，这里只要平方根的整数部分
     * 也就是说要逐步尝试
     * 找到一个 某一个数x,执行 x^2 < 99 ,但是(x+1)^2 > 99
     * 这里的x就是要找的答案
     * 逐步尝试的过程:
     * 1*1 = 1
     * 2*2 = 4
     * ...
     * 9*9 = 81
     * 10*10 = 100
     * 得到解：9
     * 这里尝试 10 次
     * 优化方法:分治思想
     * 在 1 ~ 99 之间取一个中间数: 50
     * 求 50 ^ 2 > 99
     * 现在 1 ~ (50 - 1) 之间取一个中间数: 25
     * 求 25 ^ 2 > 99
     * 现在 1 ~ (24 - 1) 之间取一个中间数: 12
     * 求 12 ^ 2 > 99
     * 现在 1 ~ (12 - 1) 之间取一个中间数: 6
     * 求 6 ^ 2 < 99
     * 现在 (6 + 1) ~ 11  之间取一个中间数: 9
     * 求 9 ^ 2 < 99
     * 现在 (9 + 1) ~ 11  之间取一个中间数: 10
     * 求 10 ^ 2 > 99
     * 现在 (10 + 1) ~ 11  之间取一个中间数: 10
     * 求 10 ^ 2 > 99
     * 连续出现两个相同的数
     * 这里只要6次
     */

    static int mySqrt(int x) {
        int i = 1, j = x;
        int r = 0;// 用来记录方法执行期间找到的数
        while (i <= j) {
            // 无符号左移>>> 相比与直接/2 可以防止溢出
            int m = (i + j) >>> 1;
            if (m <= x / m) {//不用 m * m <= x是因为防止溢出
                i = m + 1;
                r = m;//更新 记录
            } else {
                j = m - 1;
            }
        }
        return r;
    }

    public static void main(String[] args) {
        System.out.println(mySqrt(99)); // 9
        System.out.println(mySqrt(1)); // 1
        System.out.println(mySqrt(2)); // 1
        System.out.println(mySqrt(4)); // 2
        System.out.println(mySqrt(5)); // 2
        System.out.println(mySqrt(8)); // 2
        System.out.println(mySqrt(9)); // 3
        System.out.println(mySqrt(2147395599));
    }
}
