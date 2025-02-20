package code_for_great_offer.class32;

/*
* 给定一个整数 n ，返回 n! 结果中尾随零的数量。
* 提示n! = n * (n - 1) * (n - 2) * ... * 3 * 2 * 1
* 示例 1：
* 输入：n = 3
* 输出：0
* 解释：3! = 6 ，不含尾随 0
* 示例 2：
* 输入：n = 5
* 输出：1
* 解释：5! = 120 ，有一个尾随 0
* 示例 3：
* 输入：n = 0
* 输出：0
链接：https://leetcode.cn/problems/factorial-trailing-zeroes
* */
public class LeetCode_0172_FactorialTrailingZeroes {
    /*
     *TODO
     * 问题的本质 就是考虑 10的个数 10 = 2 * 5
     * 对于 n! = 1 * 2 * 3 * 4 * 5 * 6 * 7 ....
     * 每一个项 拆出因子
     * eg:
     * 4 = 2 * 2, 3 = 3 * 1
     * 5 = 1 * 5 ,6 = 3 * 2
     * ......
     * 会发现 2作为因子的个数 > 5作为因子的个数
     *TODO
     * 说明 问题转成了 5作为因子的个数
     * 对于整数： 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 .....
     * 每5个整数作为一组 eg:
     * 1~5 有一个数有一个因子5
     * 6~10 有一个数有一个因子5
     * .......
     * 说明至少 有 n / 5 个 因子 5  (每5个整数作为一组)
     * 为什么是至少 ？
     * 因为 比如 25 = 5 * 5 有2个因子5
     * 1~25 26~50 ....
     * 说明： 每25个数一组 就会有一个数是由2个因子 5的  n / 25
     * 比如 125 = 5 * 5 * 5 有3个因子5
     * 说明： 每125个数一组 就会有一个数是由3个因子 5的  n / 125
     * ........
     * n/5+n/25+n/125+......n/5^k  且n/5^k=0
     * */
    public static int trailingZeroes(int n) {
        int ans = 0;
        while (n != 0) {
            n /= 5;
            ans += n;
        }
        return ans;
    }

}
