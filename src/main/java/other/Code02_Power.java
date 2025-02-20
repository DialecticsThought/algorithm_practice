package other;

public class Code02_Power {

    /**
     * 判断一个32位正数是不是2的幂、4的幂
     * <p>
     * 2^0 =0.....001
     * 2^1 = 0.....10
     * 2^2 = 0.....100
     * 2^3 = 0....1000
     * 2^n特点只有一个1其他位都是0
     * 如果 n 是 2的幂  那么 n-1的二进制数会变成
     * 2^0-1 =0....000
     * 2^1-1 =0......001
     * 2^2-1 =0......011
     * 2^3-1 =0.....0111
     * 那么 => n&(n-1) = 0
     * <p>
     * 4的幂的判断
     * 1）
     * 首先判断 是不是2的幂
     * 2)
     * 4^0 =0.....001  1在第0位
     * 4^1 = 0.....100  1在第2位
     * 4^2 = 0.....10000 1在第4位
     * 4^3 = 0.....100000000 1在第8位
     * => n & 0101....0101 !=0
     */
    public static boolean is2Power(int n) {
        return (n & (n - 1)) == 0;
    }

    public static boolean is4Power(int n) {
        //....1010101 <=>0X55555555
        return (n & (n - 1)) == 0 && (n & 0X55555555) != 0;

    }
}
/**
 * ^异或运算符 也就是无进位相加
 * 与运算 如果要加的话 将产生了进位信息
 * 与运算再向左移动一位 就是进位信息
 * 原始的数 相加 应该等于  13^7 +（(13&7)<<1）
 * 13: 01101
 * 7: 00111
 * ^: 01010
 * <p>
 * 13:  01101
 * 7:   00111
 * &:   00101
 * <p>
 * 13:  01101
 * 7:   00111
 * &<<1: 01010  得到进位信息
 * 继续对(13&7)<<1 和 13^7 这两结果做操作
 * 13+7 的结果 就是无进位相加的结果加上进位信息
 * 13^7:     01010
 * (13&7)<<1 01010
 * ^         00000
 * <p>
 * 13^7:     01010
 * (13&7)<<1 01010
 * &<<1      10100
 * <p>
 * 也就是说(13&7)<<1(01010)和13^7(01010)做相加 => 等于 00000 和 10100 做相加
 * 00000
 * 10100
 * ^     10100
 * <p>
 * 00000
 * 10100
 * &<<1  00000   这里说明 没有进位信息了
 */
