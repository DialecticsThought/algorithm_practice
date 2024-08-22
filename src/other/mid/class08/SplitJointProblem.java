package other.mid.class08;

import java.util.HashSet;

public class SplitJointProblem {
    /*
     *
     *假设所有字符都是小写字母.
     * 某个字符串是str. arr是去重的单词表，每个单词都不是空字符串且可以使用任意次.
     * 使用arr中的单词有多少种拼接str的方式．返回方法数.
     * */
    public static int ways1(String str, String[] arr) {
        if (str == null || str.length() == 0 || arr == null || arr.length == 0) {
            return 0;
        }
        HashSet<String> map = new HashSet<>();
        for (String s : arr) {
            map.add(s);
        }
        return f(str, map, 0);
    }

    //暴力递归
    // str[index. ...]有多少种方式可以被set中的单词分解。
    public static int f(String str, HashSet<String> set, int index) {
        if (index == str.length()) {
            return 1;//1表示你之前走过的路径有效 是可以返回结果的
        }
        int ways = 0;//如果index不是终止位置 说明 上面有字符
        //str[index...end] 只要这一段字符串时set中的单词 就可以作为此时的第一个部分
        for (int end = index; end < str.length(); end++) {
            //subString是左闭右开的 想要表达index~end这一段
            if (set.contains(str.substring(index, end + 1))) {//有效的第一部分
                ways += f(str, set, end + 1);//从位置n开始的后续n~end让后面的递归决定
            }
        }
        return ways;
    }

    //dp表方式
    public static int ways2(String str, String[] arr) {
        if (str == null || str.length() == 0 || arr == null || arr.length == 0) {
            return 0;
        }
        HashSet<String> map = new HashSet<>();
        for (String s : arr) {
            map.add(s);
        }
        int N = str.length();
        int[] dp = new int[N + 1];
        dp[N] = 1;
        //0(N^3)
        for (int i = N - 1; i >= 0; i--) {
            for (int end = i; end < N; end++) {
                if (map.contains(str.substring(i, end + 1))) {
                    dp[i] += dp[end + 1];
                }
            }
        }
        return dp[0];
    }

    //前缀树的实现
    public static class Node {
        public boolean end;
        public Node[] nexts;

        public Node() {
            end = false;
            nexts = new Node[26];
        }

    }

    public static int ways3(String str, String[] arr) {
        if (str == null || str.length() == 0 || arr == null || arr.length == 0) {
            return 0;
        }
        Node root = new Node();
        for (String s : arr) {
            char[] chs = s.toCharArray();
            Node node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                if (node.nexts[index] == null) {
                    node.nexts[index] = new Node();
                }
                node = node.nexts[index];
            }
            node.end = true;
        }
        return g(str.toCharArray(), root, 0);

    }
    //前缀树的暴力递归
    public static int g(char[] str, Node root, int index) {
        if (index == str.length) {
            return 1;
        }
        int ways = 0;
        Node cur = root;
        for (int end = index; end < str.length; end++) {
            int path = str[end] - 'a';
            if (cur.nexts[path] == null) {
                break;
            }
            cur = cur.nexts[path];
            if (cur.end) {
                ways += g(str, root, end + 1);
            }
        }
        return ways;
    }
    //前缀树的dp
    public static int ways4(String s, String[] arr) {
        if (s == null || s.length() == 0 || arr == null || arr.length == 0) {
            return 0;
        }
        Node root = new Node();
        for (String str : arr) {
            char[] chs = str.toCharArray();
            Node node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                if (node.nexts[index] == null) {
                    node.nexts[index] = new Node();
                }
                node = node.nexts[index];
            }
            node.end = true;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[] dp = new int[N + 1];
        dp[N] = 1;
        for (int i = N - 1; i >= 0; i--) {
            Node cur = root;
            for (int end = i; end < N; end++) {
                int path = str[end] - 'a';
                if (cur.nexts[path] == null) {
                    break;
                }
                cur = cur.nexts[path];
                if (cur.end) {
                    dp[i] += dp[end + 1];
                }
            }
        }
        return dp[0];
    }
}
