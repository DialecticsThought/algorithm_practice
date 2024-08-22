package code_for_great_offer.class30;

import java.util.HashMap;

/*
* 91. 解码方法
一条包含字母 A-Z 的消息通过以下映射进行了 编码 ：
'A' -> "1"
'B' -> "2"
...
'Z' -> "26"
要 解码 已编码的消息，所有数字必须基于上述映射的方法，反向映射回字母（可能有多种方法）。例如，"11106" 可以映射为：
"AAJF" ，将消息分组为 (1 1 10 6)
"KJF" ，将消息分组为 (11 10 6)
注意，消息不能分组为  (1 11 06) ，因为 "06" 不能映射为 "F" ，这是由于 "6" 和 "06" 在映射中并不等价。
给你一个只含数字的 非空 字符串 s ，请计算并返回 解码 方法的 总数 。
题目数据保证答案肯定是一个 32 位 的整数。
示例 1：
输入：s = "12"
输出：2
解释：它可以解码为 "AB"（1 2）或者 "L"（12）。
示例 2：
输入：s = "226"
输出：3
解释：它可以解码为 "BZ" (2 26), "VF" (22 6), 或者 "BBF" (2 2 6) 。
示例 3：
输入：s = "06"
输出：0
解释："06" 无法映射到 "F" ，因为存在前导零（"6" 和 "06" 并不等价）。
* */
public class Problem_0639_DecodeWaysII {

    public static int numDecodings0(String str) {
        //return f(str.toCharArray(), 0);
        HashMap<Integer, Long> cache = new HashMap<Integer, Long>();
        return (int) f2(str.toCharArray(), 0, cache);
    }

    //最原始的方法
    public static int f(char[] str, int i) {
        //base case 来到 最后一个位置 直接返回1 表示这条路径正确
        if (i == str.length) {
            return 1;
        }
        //base case 当前来到的位置是'0'字符 说明这条路径 是错的
        if (str[i] == '0') {
            return 0;
        }
        int p1 = 0;
        int p2 = 0;
        // str[index]有字符且不是'0'
        if (str[i] != '*') {
            /*
             *TODO
             * str[index] = 1~9
             * case1 i位置的char 单独转 考虑i+1的位置
             * */
            p1 = f(str, i + 1);
            // base case 考虑 i+1位置是否存在
            if (i + 1 == str.length) {
                return p1;
            }
            p2 = 0;
            //TODO 下面的是case2 i位置+i+1位置 2个char 一起转换
            // eg : 17 i位置是1 i+1位置是7
            if (str[i + 1] != '*') {
                int num = (str[i] - '0') * 10 + str[i + 1] - '0';

                if (num < 27) {
                    p2 = f(str, i + 2);
                }
                return p1 + p2;
            } else { // str[i+1] == '*'
                /*
                 * i位置+i+1位置 2个char 一起转换
                 * 例如: 1* 2* 可以一起
                 * 但是 例如 3* 9* 不能用一起转换
                 * */
                if (str[i] < '3') {
                    /*
                     * i位置是'1' 那么 i和i+1位置合在一起就是 '1*' 有 9种
                     * i位置是'2' 那么 i和i+1位置合在一起就是 '1*' 有 6种
                     * */
                    //p2 = f(str, i + 2) * (str[i] == '1' ? 9 : 6);
                    if (str[i] == '1') {
                        p2 = f(str, i + 2) * 9;
                    } else {
                        p2 = f(str, i + 2) * 6;
                    }
                }
                return p1 + p2;
            }
        } else { // str[i] == '*' 代表了1~9
            // i 单转 9种
            p1 = 9 * f(str, i + 1);
            if (i + 1 == str.length) {
                return p1;
            }
            if (str[i + 1] != '*') {
                /*
                 * * 代表 0 => 10 20  * 代表 1 => 11 21
                 * * 代表 2 => 12 22  * 代表 3 => 13 23
                 * * 代表 4 => 14 24  * 代表 5 => 15 25
                 * * 代表 6 => 16 26  * 代表 7 =>  17
                 * * 代表 8 => 18     * 代表 9 =>  19
                 * */
                //int p2 = (str[i + 1] < '7' ? 2 : 1) * f(str, i + 2);
                if (str[i + 1] < '7') {
                    p2 = 2 * f(str, i + 2);
                } else {
                    p2 = 1 * f(str, i + 2);
                }
                return p1 + p2;
            } else { // str[i+1] == *
                // **
                // 11~19 9
                // 21 ~26 6
                // 15
                p2 = 15 * f(str, i + 2);
                return p1 + p2;
            }
        }
    }

    public static long mod = 1000000007;

    //最原始的方法+缓存
    public static long f2(char[] str, int index, HashMap<Integer, Long> cache) {
        if (cache.containsKey(index)) {
            return cache.get(index);
        }

        //base case 来到 最后一个位置 直接返回1 表示这条路径正确
        if (index == str.length) {
            return 1;
        }
        //base case 当前来到的位置是'0'字符 说明这条路径 是错的
        if (str[index] == '0') {
            return 0;
        }
        long p1 = 0;
        long p2 = 0;
        long ans = 0;
        // str[index]有字符且不是'0'
        if (str[index] != '*') {
            /*
             *TODO
             * str[index] = 1~9
             * case1 i位置的char 单独转 考虑i+1的位置
             * */
            p1 = f2(str, index + 1, cache);
            // base case 考虑 i+1位置是否存在
            if (index + 1 == str.length) {
                ans = p1;
                ans = ans % mod;
                cache.put(index, ans);
                return ans;
            }
            p2 = 0;
            //TODO 下面的是case2 i位置+i+1位置 2个char 一起转换
            // eg : 17 i位置是1 i+1位置是7
            if (str[index + 1] != '*') {
                int num = (str[index] - '0') * 10 + str[index + 1] - '0';

                if (num < 27) {
                    p2 = f2(str, index + 2, cache);
                }
                ans = p1 + p2;
                ans = ans % mod;
                cache.put(index, ans);
                return ans;
            } else { // str[i+1] == '*'
                /*
                 * i位置+i+1位置 2个char 一起转换
                 * 例如: 1* 2* 可以一起
                 * 但是 例如 3* 9* 不能用一起转换
                 * */
                if (str[index] < '3') {
                    /*
                     * i位置是'1' 那么 i和i+1位置合在一起就是 '1*' 有 9种
                     * i位置是'2' 那么 i和i+1位置合在一起就是 '1*' 有 6种
                     * */
                    //p2 = f(str, i + 2) * (str[i] == '1' ? 9 : 6);
                    if (str[index] == '1') {
                        p2 = f2(str, index + 2, cache) * 9;
                    } else {
                        p2 = f2(str, index + 2, cache) * 6;
                    }
                }
                ans = p1 + p2;
                ans = ans % mod;
                cache.put(index, ans);
                return ans;
            }
        } else { // str[i] == '*' 代表了1~9
            // i 单转 9种
            p1 = 9 * f2(str, index + 1, cache);
            if (index + 1 == str.length) {
                return p1;
            }
            if (str[index + 1] != '*') {
                /*
                 * * 代表 0 => 10 20  * 代表 1 => 11 21
                 * * 代表 2 => 12 22  * 代表 3 => 13 23
                 * * 代表 4 => 14 24  * 代表 5 => 15 25
                 * * 代表 6 => 16 26  * 代表 7 =>  17
                 * * 代表 8 => 18     * 代表 9 =>  19
                 * */
                //int p2 = (str[i + 1] < '7' ? 2 : 1) * f(str, i + 2);
                if (str[index + 1] < '7') {
                    p2 = 2 * f2(str, index + 2, cache);
                } else {
                    p2 = 1 * f2(str, index + 2, cache);
                }
                ans = p1 + p2;
                ans = ans % mod;
                cache.put(index, ans);
                return ans;
            } else { // str[i+1] == *
                // **
                // 11~19 9
                // 21 ~26 6
                // 15
                p2 = 15 * f2(str, index + 2, cache);
                ans = p1 + p2;
                ans = ans % mod;
                cache.put(index, ans);
                return ans;
            }
        }
    }


    public static int numDecodings1(String str) {
        long[] dp = new long[str.length()];
        return (int) ways1(str.toCharArray(), 0, dp);
    }

    public static long ways1(char[] s, int i, long[] dp) {
        if (i == s.length) {
            return 1;
        }
        if (s[i] == '0') {
            return 0;
        }
        if (dp[i] != 0) {
            return dp[i];
        }
        long ans = ways1(s, i + 1, dp) * (s[i] == '*' ? 9 : 1);
        if (s[i] == '1' || s[i] == '2' || s[i] == '*') {
            if (i + 1 < s.length) {
                if (s[i + 1] == '*') {
                    ans += ways1(s, i + 2, dp) * (s[i] == '*' ? 15 : (s[i] == '1' ? 9 : 6));
                } else {
                    if (s[i] == '*') {
                        ans += ways1(s, i + 2, dp) * (s[i + 1] < '7' ? 2 : 1);
                    } else {
                        ans += ((s[i] - '0') * 10 + s[i + 1] - '0') < 27 ? ways1(s, i + 2, dp) : 0;
                    }
                }
            }
        }
        ans %= mod;
        dp[i] = ans;
        return ans;
    }

    public static int numDecodings2(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        long[] dp = new long[n + 1];
        dp[n] = 1;
        for (int i = n - 1; i >= 0; i--) {
            if (s[i] != '0') {
                dp[i] = dp[i + 1] * (s[i] == '*' ? 9 : 1);
                if (s[i] == '1' || s[i] == '2' || s[i] == '*') {
                    if (i + 1 < n) {
                        if (s[i + 1] == '*') {
                            dp[i] += dp[i + 2] * (s[i] == '*' ? 15 : (s[i] == '1' ? 9 : 6));
                        } else {
                            if (s[i] == '*') {
                                dp[i] += dp[i + 2] * (s[i + 1] < '7' ? 2 : 1);
                            } else {
                                dp[i] += ((s[i] - '0') * 10 + s[i + 1] - '0') < 27 ? dp[i + 2] : 0;
                            }
                        }
                    }
                }
                dp[i] %= mod;
            }
        }
        return (int) dp[0];
    }

    public static int numDecodings3(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        long a = 1;
        long b = 1;
        long c = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (s[i] != '0') {
                c = b * (s[i] == '*' ? 9 : 1);
                if (s[i] == '1' || s[i] == '2' || s[i] == '*') {
                    if (i + 1 < n) {
                        if (s[i + 1] == '*') {
                            c += a * (s[i] == '*' ? 15 : (s[i] == '1' ? 9 : 6));
                        } else {
                            if (s[i] == '*') {
                                c += a * (s[i + 1] < '7' ? 2 : 1);
                            } else {
                                c += a * (((s[i] - '0') * 10 + s[i + 1] - '0') < 27 ? 1 : 0);
                            }
                        }
                    }
                }
            }
            c %= mod;
            a = b;
            b = c;
            c = 0;
        }
        return (int) b;
    }

}
