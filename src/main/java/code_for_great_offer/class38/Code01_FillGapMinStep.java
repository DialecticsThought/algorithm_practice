package code_for_great_offer.class38;

/*
 *TODO
 * 来自字节
 * 给定两个数a和b
 * 第1轮，把1选择给a或者b
 * 第2轮，把2选择给a或者b
 * ...
 * 第i轮，把i选择给a或者b
 * 想让a和b的值一样大，请问至少需要多少轮？
 */
public class Code01_FillGapMinStep {

    /*
     * 暴力方法
     * 不要让a、b过大！
     */
    public static int minStep0(int a, int b) {
        if (a == b) {
            return 0;
        }
        int limit = 15;
        return process(a, b, 1, limit);
    }

    /*
     *TODO
     * 来到 第i轮  并且i<limit
     * 选择1：a+i
     * 选择2：b+i
     * */
    public static int process(int a, int b, int i, int n) {
        if (i > n) {
            return Integer.MAX_VALUE;
        }
        if (a + i == b || a == b + i) {
            return i;
        }
        int case1 = process(a + i, b, i + 1, n);
        int case2 = process(a, b + i, i + 1, n);
        return Math.min(case1, case2);
    }
    /*
    *TODO
    * 一开始 有2个数 一个叫 大 一个叫 小
    * 先让这两个数作差 大 - 小 = s
    * 假设 来到第i轮 它们相同了
    * 假设这些轮中 小 加了很多数 总共 a  大 加了很多数 总共 b
    * 那么 a - b = s 也成立
    * 那么 a + b = 等差数列求和 i * (i+1) / 2
    * 因为每一轮加的是i
    * 把 i * (i+1) / 2 称为sum
    * 那么
    * （a+b）+ (a-b)= (sum + s)/2 = a
    * （a+b）- (a-b)= (sum - s)/2 = b
    * 因为 a > b 那么 a就是 >0的整数 b是>=0的整数
    * 所以 sum -s 是 >=0的偶数 因为 整数/2 =整数 只能是偶数
    * 所以问题是：求 最小的i（最少轮） 得到的最小的sum 保证 sum -s 是 >=0的偶数
    * 也可以说 是 i * (i+1) / 2 >= s ==> i * (i+1) >= 2 * s
    * */
    public static int minStep1(int a, int b) {
        if (a == b) {
            return 0;
        }
        int s = Math.abs(a - b);
        int num = 1;
        int sum = 0;
        for (; !(sum >= s && (sum - s) % 2 == 0); num++) {
            sum += num;
        }
        return num - 1;
    }

    public static int minStep2(int a, int b) {
        if (a == b) {
            return 0;
        }
        int s = Math.abs(a - b);
        /*
        *TODO
        * 找到sum >= s, 最小的i
        * 保证 sum -s 是 >=0的偶数
        * 如果 s是偶数  sum 也是偶数, s是奇数  sum 也是奇数
        * 先别管 sum -s 是否是 >=0的偶数 先求出 sum >=s
        * 比如 s=120
        * 那 i =2 sum+=i, i= 4 sum+=i, i=8 sum+=i....
        * 不断地乘2 再累加进sum 查看 是否满足 sum >= s
        * 直到 sum >= s
        * 假设 i=64 时 初次 实现 sum >= s
        * 也就是说 i=32 时 sum < s 因为每一次 i = i*2
        * 上面只是粗略地找到了i 下面 是精细地找 i 这个数
        * 那么找到了 范围 即 32~64
        * 在这个范围做二分，找到一个数i' i' ∈ [32,64],且 第一个实现 sum >=s 因为 i * (i+1) / 2
        * 这个数字i'是要找的最小的i 实现 sum >= s
        * 这里有个结论 找到i之后 只需再尝试 <=3 次 就能出答案
        * 因为
        * i=1 时 sum=1是奇数
        * i=2 时 sum=3是奇数
        * i=3 时 sum=6是偶数
        * ...
        * 发现 一个奇数 最多经历 3轮 就可找到下一个奇数 ，同理，偶数也是如此
         * */
        int begin = best(s << 1);
        for (; (begin * (begin + 1) / 2 - s) % 2 != 0; ) {
            begin++;
        }
        return begin;
    }

    public static int best(int s2) {
        int L = 0;
        int R = 1;
        for (; R * (R + 1) < s2; ) {
            L = R;
            R *= 2;
        }
        int ans = 0;
        while (L <= R) {
            int M = (L + R) / 2;
            if (M * (M + 1) >= s2) {
                ans = M;
                R = M - 1;
            } else {
                L = M + 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("功能测试开始");
        for (int a = 1; a < 100; a++) {
            for (int b = 1; b < 100; b++) {
                int ans1 = minStep0(a, b);
                int ans2 = minStep1(a, b);
                int ans3 = minStep2(a, b);
                if (ans1 != ans2 || ans1 != ans3) {
                    System.out.println("出错了！");
                    System.out.println(a + " , " + b);
                    break;
                }
            }
        }
        System.out.println("功能测试结束");

        int a = 19019;
        int b = 8439284;
        int ans2 = minStep1(a, b);
        int ans3 = minStep2(a, b);
        System.out.println(ans2);
        System.out.println(ans3);

    }

}
