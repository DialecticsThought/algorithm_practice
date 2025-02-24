/**
 * @Description 最大公约数
 * @Author veritas
 * @Data 2025/2/23 18:36
 */
public class GCD {
    /**
     * 找到 a 和 b 的最大公约数
     * gcd(a,b) = gcd(b,a mod b)
     * 设 q = quotient 商 r = rest 余数  即 a mod b = r
     * 设 d 是最大公约数 并且 a > b
     * a =  b * q + r  => r = a - b * q
     * 因为 d 是最大公约数
     * 那么 a / d , b / d 都有整数
     * 那么 r 能被 d 整除
     *
     * @param a
     * @param b
     * @return
     */
    public int gcd(int a, int b) {
        while (b != 0) {
            int rest  = a % b;
            a = b;
            b = rest;
        }
        return a;
    }
}
