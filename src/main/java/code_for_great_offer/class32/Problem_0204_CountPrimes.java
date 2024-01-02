package code_for_great_offer.class32;

import java.util.Arrays;

public class Problem_0204_CountPrimes {
    /*
    *TODO
    * 给定整数 n ，返回 所有小于非负整数n的质数的数量 。
    * 示例 1：
    * 输入：n = 10
    * 输出：4
    * 解释：小于 10 的质数一共有 4 个, 它们是 2, 3, 5, 7 。
    * 示例 2：
    * 输入：n = 0
    * 输出：0
    * 示例 3：
    * 输入：n = 1
    * 输出：0
     * 链接：https://leetcode.cn/problems/count-primes
    * */
    /*
     * 枚举没有考虑到数与数的关联性，因此难以再继续优化时间复杂度。接下来我们介绍一个常见的算法，该算法由希腊数学家厄拉多塞（Eratosthenes）提出，称为厄拉多塞筛法，简称埃氏筛。
     * 我们考虑这样一个事实：如果 x 是质数，那么大于x 的倍数 2x,3x,… 一定不是质数，因此我们可以从这里入手。
     * 我们设 isPrime[i] 表示数 i 是不是质数，如果是质数则为 1，否则为 0。从小到大遍历每个数，如果这个数为质数，
     * 则将其所有的倍数都标记为合数（除了该质数本身），即
     * 0，这样在运行结束的时候我们即能知道质数的个数。
     * 这种方法的正确性是比较显然的：这种方法显然不会将质数标记成合数；另一方面，当从小到大遍历到数 x 时，倘若它是合数，
     * 则它一定是某个小于 x 的质数 y 的整数倍，故根据此方法的步骤，我们在遍历到 y 时，就一定会在此时将 x 标记为 isPrime[x]=0。
     * 因此，这种方法也不会将合数标记为质数。当然这里还可以继续优化，对于一个质数 x，
     * 如果按上文说的我们从 2x 开始标记其实是冗余的，应该直接从 x 开始标记，
     * 因为 2x,3x,… 这些数一定在 x 之前就被其他数的倍数标记过了，例如 2 的所有倍数，3 的所有倍数等。
     * */
    class Solution2 {
        public int countPrimes(int n) {
            int[] isPrime = new int[n];
            Arrays.fill(isPrime, 1);
            int ans = 0;
            for (int i = 2; i < n; ++i) {
                if (isPrime[i] == 1) {
                    ans += 1;
                    if ((long) i * i < n) {
                        for (int j = i * i; j < n; j += i) {
                            isPrime[j] = 0;
                        }
                    }
                }
            }
            return ans;
        }
    }

    /*
    * 很直观的思路是我们枚举每个数判断其是不是质数。考虑质数的定义：在大于 1 的自然数中，除了 1 和它本身以外不再有其他因数的自然数。
    * 因此对于每个数 x，我们可以从小到大枚举 [2,x−1] 中的每个数 y，判断 y 是否为 x 的因数。
    * 但这样判断一个数是否为质数的时间复杂度最差情况下会到 O(n)，无法通过所有测试数据。
    * 考虑到如果 y 是 x 的因数，那么 y/x也必然是 x 的因数，因此我们只要校验 y 或者  y/x即可。
    * 而如果我们每次选择校验两者中的较小数，则不难发现较小数一定落在[2, Math.sqrt(x)]的区间中，
    * 因此我们只需要枚举 [2, Math.sqrt(x)]中的所有数即可，这样单次检查的时间复杂度从 O(n) 降低至了 O(Math.sqrt(x))。
    * */
    class Solution1 {
        public int countPrimes(int n) {
            int ans = 0;
            for (int i = 2; i < n; ++i) {
                ans += isPrime(i) ? 1 : 0;
            }
            return ans;
        }

        public boolean isPrime(int x) {
            for (int i = 2; i * i <= x; ++i) {
                if (x % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }

    public static int countPrimes(int n) {
        if (n < 3) {
            return 0;
        }
        /*
         *TODO 建立一个n长度的数组
         * 尽可能地把有些重复的数字省略掉
         * 如果 j已经不是素数了，f[j] = true;
         * 把 2 及其 2的倍数 都变成 true
         * 也就是 2 2*2 2*3 2*4 .......
         * 把 3 及其 3的倍数 都变成 true
         * 也就是 3  3*3 3*5  3*7 3*9 3*11 .......
         * 4 已经被2试过了 不用再试了
         * 把 5 及其 5的倍数 都变成 true
         * 也就是 5  5*5  5*7 3*9 3*11 .......
         * ....
         * 来到 13 把 13  及其 13 的倍数 都变成 true
         * 也就是 13*13 13*15 13*17 ....
         * 13之前的数不用再乘上13了
         * */
        boolean[] f = new boolean[n];
        //TODO 所有偶数都不要，还剩几个数
        // 以后在发现 不是质数 那么 count--
        int count = n / 2;
        //TODO 跳过了1、2  直接从3、5、7、.....
        for (int i = 3; i * i < n; i += 2) {//从奇数出发 每一次+2 那么遍历的所有数都是奇数
            if (f[i]) {//TODO 如果i不再是素数 那么直接判断i+2
                continue;
            }
            /*
             *TODO
             * eg:
             * 3 -> 3 * 3 = 9
             * 3 * 5 = 15   15 = 9 + 2*3
             * 3 * 7 = 21   31 = 15 + 2*3
             * ...
             * eg:
             * 7 -> 7 * 7 = 49  49不是素数
             * 7*8不用试
             * 尝试 7 * 9 = 63  63 = 49 + 2*7
             * eg：
             * 来到 i =13 直接尝试 13 * 13 再13 * 15 ....
             * */
            for (int j = i * i; j < n; j += 2 * i) {
                if (!f[j]) {
                    --count;
                    f[j] = true;
                }
            }
        }
        return count;
    }

}
