package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * code_for_great_offer.class11
 * 本题测试链接 : https://leetcode.com/problems/palindrome-partitioning-ii/
 */
public class LeetCode_132_PalindromePartitioningII {


/*    public static int f(char[] str, int i) {
        if (i == str.length) {
            return 0;
        }
        // i ..
        // i..i+1
        // i. ..i+2
        int next = Integer.MAX_VALUE;
        for (int end = i; end < str.length; end++) {
            if () {//str[i..end]是回文的话
                //后续至少切几个部分
                //f(str,end+1) end+1的原因是  str[i..end]是1部分
                next = Math.max(next, f(str, end + 1));
            }
        }
        return next + 1;
    }*/


    // 测试链接只测了本题的第一问，直接提交可以通过
    public static int minCut(String s) {
        if (s == null || s.length() < 2) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        boolean[][] checkMap = createCheckMap(str, N);
        int[] dp = new int[N + 1];
        dp[N] = 0;
        /*
         *TODO
         * dp[i]表示str[i]~str[str.length-1]的子str要至少切成几份才能都是回文
         * 所以要从最后往前求
         * */
        for (int i = N - 1; i >= 0; i--) {
            if (checkMap[i][N - 1]) {//TODO i~N-1这个部分是回文的话
                dp[i] = 1;
            } else {
                int next = Integer.MAX_VALUE;
                for (int j = i; j < N; j++) {
                    if (checkMap[i][j]) {//如果i~j部分是惠文德话
                        next = Math.min(next, dp[j + 1]);
                    }
                }
                dp[i] = 1 + next;
            }
        }
        return dp[0] - 1;
    }

    /*
     *TODO 这个二维表表示str[L]~str[R]是否是回文
     * 二维表示一个正方形 长度str.len
     * 因为L>R不成立，所以这个正方形的左下部分是不填写的
     * 先填写 对角线 一定是True
     * 再填写 倒数第二个对角线 也就是L=R-1  如果str[R-1]=str[R] 那么就是True 否则反之
     * 这张二维表一定是从下往上 从左往右填写
     * */
    public static boolean[][] createCheckMap(char[] str, int N) {
        boolean[][] ans = new boolean[N][N];
        //TODO 先把除了最后一行的对角线和倒数第二个的对角线填好
        for (int i = 0; i < N - 1; i++) {
            ans[i][i] = true;
            ans[i][i + 1] = str[i] == str[i + 1];
        }
        ans[N - 1][N - 1] = true;//TODO 最后一行的最后一个格子单独填写
        for (int i = N - 3; i >= 0; i--) {
            for (int j = i + 2; j < N; j++) {
                ans[i][j] = str[i] == str[j] && ans[i + 1][j - 1];
            }
        }
        return ans;
    }

    // 本题第二问，返回其中一种结果
    public static List<String> minCutOneWay(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() < 2) {
            ans.add(s);
        } else {
            char[] str = s.toCharArray();
            int N = str.length;
            boolean[][] checkMap = createCheckMap(str, N);
            int[] dp = new int[N + 1];
            dp[N] = 0;
            for (int i = N - 1; i >= 0; i--) {
                if (checkMap[i][N - 1]) {
                    dp[i] = 1;
                } else {
                    int next = Integer.MAX_VALUE;
                    for (int j = i; j < N; j++) {
                        if (checkMap[i][j]) {
                            next = Math.min(next, dp[j + 1]);
                        }
                    }
                    dp[i] = 1 + next;
                }
            }
            // dp[i]  (0....5) 回文！  dp[0] == dp[6] + 1
            //  (0....5)   6
            for (int i = 0, j = 1; j <= N; j++) {
                if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
                    ans.add(s.substring(i, j));
                    i = j;
                }
            }
        }
        return ans;
    }

    // 本题第三问，返回所有结果
    public static List<List<String>> minCutAllWays(String s) {
        List<List<String>> ans = new ArrayList<>();
        if (s == null || s.length() < 2) {
            List<String> cur = new ArrayList<>();
            cur.add(s);
            ans.add(cur);
        } else {
            char[] str = s.toCharArray();
            int N = str.length;
            boolean[][] checkMap = createCheckMap(str, N);
            int[] dp = new int[N + 1];
            dp[N] = 0;
            for (int i = N - 1; i >= 0; i--) {
                if (checkMap[i][N - 1]) {
                    dp[i] = 1;
                } else {
                    int next = Integer.MAX_VALUE;
                    for (int j = i; j < N; j++) {
                        if (checkMap[i][j]) {
                            next = Math.min(next, dp[j + 1]);
                        }
                    }
                    dp[i] = 1 + next;
                }
            }
            process(s, 0, 1, checkMap, dp, new ArrayList<>(), ans);
        }
        return ans;
    }

    // s[0....i-1]  怎么分割的 就存到path里去了（也就是之前做的决定）
    // s[i..j-1]考察的分出来的第一份
    // List<List<String>> ans存放所有可能的结果
    public static void process(String s, int i, int j, boolean[][] checkMap, int[] dp,
                               List<String> path,
                               List<List<String>> ans) {
        if (j == s.length()) { //现在考察 s[i...N-1]
            //TODO 这个情况没有后续了 因为 j到底了
            if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
                path.add(s.substring(i, j));//最后的一份 放入path
                ans.add(copyStringList(path));//把当前的分支 拷贝到ans
                path.remove(path.size() - 1);//还原现场
            }
        } else {//现在考察 s[i...j-1] 还没有到底
            if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
                path.add(s.substring(i, j));//把当前的部分放入path
                //原来是i 现在是j 原来是j 现在是j+1
                //选择1： 走一个分支路径
                process(s, j, j + 1, checkMap, dp, path, ans);
                path.remove(path.size() - 1);//还原现场
            }
            //选择2： 走其他路径
            process(s, i, j + 1, checkMap, dp, path, ans);
        }
    }

    public static List<String> copyStringList(List<String> list) {
        List<String> ans = new ArrayList<>();
        for (String str : list) {
            ans.add(str);
        }
        return ans;
    }

    public static void main(String[] args) {
        String s = null;
        List<String> ans2 = null;
        List<List<String>> ans3 = null;

        System.out.println("本题第二问，返回其中一种结果测试开始");
        s = "abacbc";
        ans2 = minCutOneWay(s);
        for (String str : ans2) {
            System.out.print(str + " ");
        }
        System.out.println();

        s = "aabccbac";
        ans2 = minCutOneWay(s);
        for (String str : ans2) {
            System.out.print(str + " ");
        }
        System.out.println();

        s = "aabaa";
        ans2 = minCutOneWay(s);
        for (String str : ans2) {
            System.out.print(str + " ");
        }
        System.out.println();
        System.out.println("本题第二问，返回其中一种结果测试结束");
        System.out.println();
        System.out.println("本题第三问，返回所有可能结果测试开始");
        s = "cbbbcbc";
        ans3 = minCutAllWays(s);
        for (List<String> way : ans3) {
            for (String str : way) {
                System.out.print(str + " ");
            }
            System.out.println();
        }
        System.out.println();

        s = "aaaaaa";
        ans3 = minCutAllWays(s);
        for (List<String> way : ans3) {
            for (String str : way) {
                System.out.print(str + " ");
            }
            System.out.println();
        }
        System.out.println();

        s = "fcfffcffcc";
        ans3 = minCutAllWays(s);
        for (List<String> way : ans3) {
            for (String str : way) {
                System.out.print(str + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("本题第三问，返回所有可能结果测试结束");
    }

}
