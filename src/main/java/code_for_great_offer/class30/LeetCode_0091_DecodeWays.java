package code_for_great_offer.class30;

import java.util.HashMap;

public class LeetCode_0091_DecodeWays {

    public static int numDecodings1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        HashMap<Integer, Integer> cache = new HashMap<Integer, Integer>();
        //return process(str, 0);
        return process2(str, 0, cache);
    }

    /*
     *TODO
     * 潜台词：str[0...index-1]已经转化完了，不用操心了
     * str[index....] 能转出多少有效的，返回方法数
     *TODO
     * 来到str[index] 只有2种决定
     * index单独的转换
     * index和index+1一起转换
     * */
    public static int process(char[] str, int index) {
        //base case 说明 能转换一个str
        if (index == str.length) {
            return 1;
        }
        //base case 当前位置是字符'0' 转不了 那么之前的决定路径 是错的
        if (str[index] == '0') {
            return 0;
        }
        //TODO 情况1: 当前index 直接转成一个char 去index+1位置做决定
        int case1 = process(str, index + 1);
        if (index + 1 == str.length) {
            return case1;
        }
        int case2 = 0;
        /*
         *TODO
         * 情况2：
         * index和index+1一起成char的情况就是
         * index = '1' 的话 index+1 可以是'0'~'9'
         * index = '2' 的话 index+1 可以是'0'~'6'
         * eg: '2' '3' -> '23'
         * */
        int num = (str[index] - '0') * 10 + str[index + 1] - '0';
        if (num < 27) {
            case2 = process(str, index + 2);
        }
        int ways = case1 + case2;
        return ways;
    }


    /*
     *TODO
     * 潜台词：str[0...index-1]已经转化完了，不用操心了
     * str[index....] 能转出多少有效的，返回方法数
     *TODO
     * 来到str[index] 只有2种决定
     * index单独的转换
     * index和index+1一起转换
     * */
    public static int process2(char[] str, int index, HashMap<Integer, Integer> cache) {
        if (cache.containsKey(index)) {
            return cache.get(index);
        }
        //base case 说明 能转换一个str
        if (index == str.length) {
            return 1;
        }
        //base case 当前位置是字符'0' 转不了 那么之前的决定路径 是错的
        if (str[index] == '0') {
            return 0;
        }
        //TODO 情况1: 当前index 直接转成一个char 去index+1位置做决定
        int case1 = process2(str, index + 1, cache);
        if (index + 1 == str.length) {
            return case1;
        }
        int case2 = 0;
        /*
         *TODO
         * 情况2：
         * index和index+1一起成char的情况就是
         * index = '1' 的话 index+1 可以是'0'~'9'
         * index = '2' 的话 index+1 可以是'0'~'6'
         * eg: '2' '3' -> '23'
         * */
        int num = (str[index] - '0') * 10 + str[index + 1] - '0';
        if (num < 27) {
            case2 = process2(str, index + 2, cache);
        }
        int ways = case1 + case2;
        cache.put(index, ways);
        return cache.get(index);
    }


    public static int numDecodings2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        // dp[i] -> process(str, index)返回值 index 0 ~ N
        int[] dp = new int[N + 1];
        dp[N] = 1;

        // dp依次填好 dp[i] dp[i+1] dp[i+2]
        for (int i = N - 1; i >= 0; i--) {
            if (str[i] != '0') {
                dp[i] = dp[i + 1];
                if (i + 1 == str.length) {
                    continue;
                }
                int num = (str[i] - '0') * 10 + str[i + 1] - '0';
                if (num <= 26) {
                    dp[i] += dp[i + 2];
                }
            }
        }
        return dp[0];
    }

    public static int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[] dp = new int[N + 1];
        dp[N] = 1;
        for (int i = N - 1; i >= 0; i--) {
            if (str[i] == '0') {
                dp[i] = 0;
            } else if (str[i] == '1') {
                dp[i] = dp[i + 1];
                if (i + 1 < N) {
                    dp[i] += dp[i + 2];
                }
            } else if (str[i] == '2') {
                dp[i] = dp[i + 1];
                if (i + 1 < str.length && (str[i + 1] >= '0' && str[i + 1] <= '6')) {
                    dp[i] += dp[i + 2];
                }
            } else {
                dp[i] = dp[i + 1];
            }
        }
        return dp[0];
    }

}
