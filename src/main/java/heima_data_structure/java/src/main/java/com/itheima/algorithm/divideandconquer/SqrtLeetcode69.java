package heima_data_structure.java.src.main.java.com.itheima.algorithm.divideandconquer;

/**
 * <h3>平方根整数部分</h3>
 */
public class SqrtLeetcode69 {

    /*
        99 = 9.?

        1*1 = 1  10 次
        2*2 = 4
        ...
        9*9 = 81
        10*10 = 100


        x=99

        i j  m
        1 99 50  6 次
        1 49 25
        1 24 12
        1 11 6
        7 11 9
        10 11 10
     */

    static int mySqrt(int x) {
        int i = 1, j = x;
        int r = 0;
        while (i <= j) {
            int m = (i + j) >>> 1;
//            int mm = m * m;
            if (m <= x / m) {
                i = m + 1;
                r = m;
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
