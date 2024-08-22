package code_for_great_offer.class20;

/**
 *TODO
 * 给定一个字符串str，当然可以生成很多子序列
 * 返回有多少个子序列是回文子序列，空序列不算回文比如, str = “aba”
 * 回文子序列:{a}、{a}、{a,a}、{b}、{a,b,a}返回5
 *TODO
 * 回文串一般是范围尝试模型
 * 也就是需要讨论开头 或者 结尾
 *TODO
 * dp[i][j] 表示从str[i]~str[j]中所有的子序列能搞出几个回文出来 不算空串
 * 所以dp[o][N-1]是最终答案
 * 可能性
 * 1.子序列 一定不选择str[i]  也一定不选择str[j]这2个char  str[i+1]~str[j-1]范围随意选择  产生的子串数量==a
 * 2.子序列 一定选择str[i]  也一定不选择str[j]这2个char  str[i+1]~str[j-1]范围随意选择 产生的子串数量==b
 * 3.子序列 一定不选择str[i]  也一定选择str[j]这2个char  str[i+1]~str[j-1]范围随意选择 产生的子串数量==c
 * 4.如果str[i]==str[j]，子序列 一定选择str[i]  也一定不选择str[j]这2个char  str[i+1]~str[j-1]范围随意选择 产生的子串数量==d
 * 最终答案： a+b+c+d
 * <p>
 *TODO
 * 根据上面的操作 dp[i+1][j] = a+b
 * dp[i+1][j]表示 一定不选择str[i]  可以(不)选择str[j] str[i+1]~str[j-1]范围随意选择
 * eg:
 * str = ? a   a  有{a}   {a}   {aa}      3种回文子序列
 * 		 L L+1 K  只要L+1 只要K  L+1和R都要
 * 		 × √   √
 * dp[i][j-1] = a+c 表示一定不选择str[j] str[i]可选可不选 str[i+1]~str[j-1]可选可不选
 * 当str[i]!=str[j]时，
 * 		dp[i][j] = dp[i+1][j] + dp[i][j-1]-dp[i+1][j-1] = a+b +a+c-a = a+b+c
 * eg:
 * str =
 * a c a c
 * 0 1 2 3
 * 所有子序列
 * {a} {c} {a} {c}  {aa} {cc} {aca} {cac}  {ac} {ac}
 *  0   1   2   3    01   12   012   123    23   12
 *  √   √   √   √    √     √    √     √     ×     ×
 *  str[0]!=str[3]
 *  不选str[0]和str[3] 那么{c} {a}
 *  一定选str[0]和一定不选str[3] 那么{a} {aa} {aca}
 *  一定不选str[0]和一定选str[3] 那么{a} {c} {cc}  {cac}
 *  只考虑0,1,2 范围上随意选择 dp[i][j-1] 有哪些
 *      =>  {a} {c} {a} {aa} {aca}
 *           0   1   2   02   012
 *  只考虑1,2,3 范围上随意选择 dp[i][j-1] 有哪些
 *      =>  {c} {a} {c} {cc} {caa}
 *           1   2   3   13   123
 * 总结
 *  str[L]=str[R]
 * 1.子序列 一定不选择str[i]  也一定不选择str[j]这2个char   产生的子串数量==a
 * 2.子序列 一定选择str[i]  也一定不选择str[j]这2个char   产生的子串数量==b
 * 3.子序列 一定不选择str[i]  也一定选择str[j]这2个char   产生的子串数量==c
 * 4.如果str[i]==str[j]，子序列 一定选择str[i]  也一定不选择str[j]这2个char   产生的子串数量==d
 * 对于d有2种情况 但是本质是[L+1]~[R-1]的回文外面包一层
 *      1. 也就是[L][L+1]~[R-1][R]  [L+1]~[R-1]构成的回文 = dp[L+1][R-1]
 *      2. 也就是[L][L+1]~[R-1][R] 但是[L+1]~[R-1] = 空串
 *      => d = dp[L+1][R-1] + 1
 */
public class Code04_PalindromeWays {

    public static int ways1(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] s = str.toCharArray();
        char[] path = new char[s.length];
        return process(str.toCharArray(), 0, path, 0);
    }

    public static int process(char[] s, int si, char[] path, int pi) {
        if (si == s.length) {
            return isP(path, pi) ? 1 : 0;
        }
        int ans = process(s, si + 1, path, pi);
        path[pi] = s[si];
        ans += process(s, si + 1, path, pi + 1);
        return ans;
    }

    public static boolean isP(char[] path, int pi) {
        if (pi == 0) {
            return false;
        }
        int L = 0;
        int R = pi - 1;
        while (L < R) {
            if (path[L++] != path[R--]) {
                return false;
            }
        }
        return true;
    }

    /**
     * dp[i][j]的含义是：字符串str[i]~str[j]区间内可以得到的最多回文子序列个数，所以，dp[0][n-1]的值就是我们需要求的最终答案。
     * 根据dp数组的含义，我们可以得到，二维矩阵dp中的对角线上的值都是1, 对角线上面的位置也可以很容易得出，见如下注释
     * <p>
     * 接下来考虑普遍位置dp[i][j]，可以有如下几种情况：
     * 情况一，i位置的字符弃而不用，那么dp[i][j] = dp[i+1][j]；
     * 情况二，j位置的字符弃而不用，那么dp[i][j] = dp[i][j-1]；
     * 基于情况一和情况二，
     * dp[i][j] = dp[i+1][j] + dp[i][j-1]
     * 这个时候，其实是算重了一部分，算重的部分是dp[i+1][j-1]，所以 dp[i][j] = dp[i+1][j] + dp[i][j-1] - dp[i+1][j-1]
     * 如果str[i] == str[j]，则存在情况三，
     * 情况三中，str[i]和str[j]可都保留，与区间[i+1...j-1]形成回文串 str[i]也可以单独和str[j]形成一个回文串
     * dp[i][j] = dp[i + 1][j] + dp[i][j - 1] - dp[i + 1][j - 1] +  dp[i + 1][j - 1]  + 1
     * dp[i][j]分成两部分
     * 第一部分：dp[i + 1][j] + dp[i][j - 1] - dp[i + 1][j - 1] 表示i位置和j位置弃而不用的情况下，得到的答案数
     * 第二部分：dp[i + 1][j - 1]  + 1 表示在情况三下，同时使用str[i]和str[j]位置得到的答案数
     */
    public static int ways2(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] s = str.toCharArray();
        int n = s.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }
        for (int i = 0; i < n - 1; i++) {
            dp[i][i + 1] = s[i] == s[i + 1] ? 3 : 2;
        }
        for (int L = n - 3; L >= 0; L--) {
            for (int R = L + 2; R < n; R++) {
                dp[L][R] = dp[L + 1][R] + dp[L][R - 1] - dp[L + 1][R - 1];
                if (s[L] == s[R]) {
                    dp[L][R] += dp[L + 1][R - 1] + 1;
                }
            }
        }
        return dp[0][n - 1];
    }

    public static String randomString(int len, int types) {
        char[] str = new char[len];
        for (int i = 0; i < str.length; i++) {
            str[i] = (char) ('a' + (int) (Math.random() * types));
        }
        return String.valueOf(str);
    }

    public static void main(String[] args) {
        int N = 10;
        int types = 5;
        int testTimes = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            int len = (int) (Math.random() * N);
            String str = randomString(len, types);
            int ans1 = ways1(str);
            int ans2 = ways2(str);
            if (ans1 != ans2) {
                System.out.println(str);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }

}
