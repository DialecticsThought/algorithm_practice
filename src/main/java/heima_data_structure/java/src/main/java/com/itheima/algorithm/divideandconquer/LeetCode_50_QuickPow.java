package heima_data_structure.java.src.main.java.com.itheima.algorithm.divideandconquer;

/**
 * <h3>快速幂 - 分治</h3>
 */
public class LeetCode_50_QuickPow {
    /*

                           2^16                 65536  乘四次
                   /                  \
                 2^8                  2^8        256*256  乘三次
              /      \              /      \
             2^4     2^4           2^4     2^4    16*16  乘二次
            /  \     /  \         /  \     /  \
          2^2 2^2  2^2 2^2       2^2 2^2  2^2 2^2   4*4  乘一次
          /\  /\   /\  /\       /\  /\    /\  /\
         2 2 2 2  2 2 2 2      2 2 2 2   2 2 2  2


                  2^10
              /         \
            2^5         2^5    2*2^4
           /  \        /  \
        2 2^2 2^2    2 2^2 2^2
         / \  / \     / \  / \
        2  2  2  2   2  2  2  2

     */

    static double myPow(double x, int n) {
        long p = n; // -2
        if (p < 0) {
            p = -p; // -2147483648  2147483647
        }
        double r = myPowPositive(x, p);
        return n < 0 ? 1 / r : r;
    }

    static double myPowPositive(double x, long n) {
        if (n == 0) {
            return 1.0;
        }
        if (n == 1) {
            return x;
        }
        double y = myPowPositive(x, n / 2);

        /*
            1   001
            3   011
            5   101
            7   111
                001 &
                -----
                001

            2   010
            4   100
            6   110
            8  1000
                001 &
                -----
                000
         */

        if ((n & 1) == 0) { // 偶数
            return y * y;
        } else { // 奇数
            return x * y * y;
        }
    }

    public static void main(String[] args) {
        System.out.println(myPow(2, 16));  // 65536
        System.out.println(myPow(2, 10));  // 1024
        System.out.println(myPow(2, 0)); // 1.0
        System.out.println(myPow(2, -2)); // 0.25    2^-2 = 1/2^2
        System.out.println(myPow(2, -2147483648)); // 1.0   int
//        System.out.println(myPow(2.1, 3)); // 9.261
    }
}
