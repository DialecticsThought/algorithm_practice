package heima_data_structure.java.src.main.java.com.itheima.algorithm.divideandconquer;

/**
 * <h3>快速幂 - 分治</h3>
 */
public class LeetCode_50_QuickPow {
    /**
     * <pre>
     * 设幂就是n  2是x
     * 如果幂是偶数的话
     *                            2^16                 65536  乘四次    ↑
     *                    /                  \                         |
     *                  2^8                  2^8        256*256  乘三次 |  2^16 = 2^8 * 2^8
     *               /      \              /      \                    |
     *              2^4     2^4           2^4     2^4    16*16   乘二次 |  2^8 = 2^4 * 2^4
     *             /  \     /  \         /  \     /  \                 |
     *           2^2 2^2  2^2 2^2       2^2 2^2  2^2 2^2   4*4   乘一次 |  2^4 = 2^2 * 2^2
     *           /\  /\   /\  /\       /\  /\    /\  /\                |
     *          2 2 2 2  2 2 2 2      2 2 2 2   2 2 2  2               |
     * 对于每一个节点，而言，只要看某一侧子树即可，因为左子树和右子树都是相同
     * 只要乘4次 所以叫做快速幂 如果不用该方法 需要乘16次
     *
     * 如果幂是奇数的话
     *                   2^10
     *               /         \
     *             2^5         2^5    2*2^4 这里幂是奇数 拆成 2个部分
     *            /  \        /  \
     *         2 2^2 2^2    2 2^2 2^2
     *          / \  / \     / \  / \
     *         2  2  2  2   2  2  2  2
     * </pre>
     */
    static double myPow(double x, int n) {
        long p = n; // -2
        // 如果 幂n是负数的话 相当于先当做正数求出 再倒数
        if (p < 0) {
            p = -p; // -2147483648 int类型的最小值  2147483647 int类型的最大值
        }
        double r = myPowPositive(x, p);
        return n < 0 ? 1 / r : r;
    }

    public double myPow1(double x, long n) {
        double mul = 1;
        for (int i = 0; i < n; i++) {
            mul = mul * x;
        }
        return mul;
    }

    public double myPowPositive2(double x, long n) {
        // base case 任何数的0次方 = 1
        if (n == 0) {
            return 1.0;
        }
        // base case 任何数的1次方 = 任何数本身
        if (n == 1) {
            return x;
        }
        if (n % 2 == 0) {// 幂n 是 偶数
            return myPowPositive2(x, n / 2) * myPowPositive2(x, n / 2);
        } else {// 幂n 是 奇数
            return x * myPowPositive2(x, n / 2) * myPowPositive2(x, n / 2);
        }
    }

    public double myPowPositive3(double x, long n) {
        // base case 任何数的0次方 = 1
        if (n == 0) {
            return 1.0;
        }
        // base case 任何数的1次方 = 任何数本身
        if (n == 1) {
            return x;
        }
        // 因为左子树和右子树是相同的 所以只需计算一次  省掉一次子节点调用
        double result = myPowPositive2(x, n / 2);
        if (n % 2 == 0) {// 幂n 是 偶数
            return result * result;
        } else {// 幂n 是 奇数
            return x * result * result;
        }
    }


    static double myPowPositive(double x, long n) {
        if (n == 0) {
            return 1.0;
        }
        // base case
        if (n == 1) {
            return x;
        }
        double y = myPowPositive(x, n / 2);
        /**
         * <pre>
         * 对于奇数
         *             1   001
         *             3   011
         *             5   101
         *             7   111
         *           &     001
         *           ----------
         *                 001
         * 对于偶数
         *             2   010
         *             4   100
         *             6   110
         *             8  1000
         *          &     0001
         *           ----------
         *                 000
         *  </pre>
         */
        if ((n & 1) == 0) { // 幂n 是 偶数
            return y * y;
        } else { // 幂n 是 奇数
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
