package code_for_great_offer.class26;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// 本题测试链接 : https://leetcode.cn/problems/expression-add-operators/
/*
 * 282. 给表达式添加运算符
 * */
public class leetCode_282_ExpressionAddOperators {

    public static List<String> addOperators(String num, int target) {
        List<String> ret = new LinkedList<>();
        if (num.length() == 0) {
            return ret;
        }
        // 沿途的数字拷贝和+ - * 的决定，放在path里
        char[] path = new char[num.length() * 2 - 1];
        // num -> char[]
        char[] digits = num.toCharArray();
        long n = 0;
        for (int i = 0; i < digits.length; i++) { // 尝试0~i前缀作为第一部分
            n = n * 10 + digits[i] - '0';
            path[i] = digits[i];
            dfs(ret, path, i + 1, 0, n, digits, i + 1, target); // 后续过程
            if (n == 0) {
                break;
            }
        }
        return ret;
    }


    /*
     *TODO
     * char[] digits 固定参数，字符类型数组，等同于num
     *  char[] path 之前做的决定，已经从左往右依次填写的字符在其中，可能含有'0'~'9' 与 * - +
     *  int len path[0..len-1]已经填写好，len是终止 len就是达到aim的时候 此时 辅助arr 也就是path的长度
     *  int pos 字符类型数组num, 使用到了哪
     *  left -> 前面固定的部分 cur -> 前一块
     *  默认 left + cur ...
     *  char[] num, int index, int aim 表示在原始字符串中 来到了哪个位置 目标是aim
     *  如果 来到原始字符串的最后位置 达到了 aim 就把这个决定path[]放入 res中
     * */
    public static void dfs(List<String> res, char[] path, int len,
                           long left, //TODO 之前做的决定不会改变已经算出来的结果  哪些是后面添加符号不会改变的 叫做left
                           long cur,//TODO 之前做的决定可能被之后的字符改变的 哪些是后面添加符号会改变的 叫cur
                           char[] num, int index, int aim) {
        //TODO base case
        if (index == num.length) {
            if (left + cur == aim) {//TODO 计算结果 =  已经算出来的部分 + 待定的部分
                res.add(new String(path, 0, len));
            }
            return;
        }
        long n = 0;
        int j = len + 1;
        /*
         *TODO
         * 这个遍历 本身就能解决 某位置的字符之前要不要填符号
         * 试每一个可能的前缀，作为第一个数字！
         * num[index]~num[i] 作为第一个数字！
         * */
        for (int i = index; i < num.length; i++) { // pos ~ i
            // n * 10 + num[i]的ASCII码 -0的ASCII码
            // 试每一个可能的前缀，作为第一个数字！
            // num[index...i] 作为第一个数字！
            n = n * 10 + num[i] - '0';
            path[j++] = num[i];
            /*
             *TODO
             * eg: 12  3  4
             * 3已经做决定了是 “+”
             * 接下来是 4做什么决定
             * 之前做的决定不会改变已经算出来的结果  哪些是后面添加符号不会改变的 叫做left
             * 之前做的决定可能被之后的字符改变的 哪些是后面添加符号会改变的 叫cur
             * 4前面是 “+”  left =12+3 cur = 4   3是上一轮的cur
             * 4前面是 “-”  left = 12-3 cur = 4  3是上一轮的cur
             * 4前面是 “*”  left = 12 cur = 3 * 4  cur是上一轮和这一轮合并
             * */
            path[len] = '+';
            dfs(res, path, j, left + cur, n, num, i + 1, aim);
            path[len] = '-';
            dfs(res, path, j, left + cur, -n, num, i + 1, aim);
            path[len] = '*';
            dfs(res, path, j, left, cur * n, num, i + 1, aim);
            /*
             * TODO 因为 num[index] == '0'
             *  那么 num[index+1]做决定一定要在 num[index+1]前添加符号 否则 会出现前缀0
             * */
            if (num[index] == '0') {
                break;
            }
        }
    }
    /*
     * String[0~index-1]的决定已经做完了 都在path里面
     * 轮到index位置的字符 决定其后面的符号(+ , - * , / , 不做)
     * 最终高出target的决定 存到ans中去
     * */
    public static List<String> getAllResult(String num, int target) {
        List<String> ans = new ArrayList<>();
        char[] str = num.toCharArray();
        process(str, 0, target, "", ans);
        return ans;
    }

    public static void process(char[] str, int index,
                               int target, String path, List<String> ans) {
        if (index == str.length) {
            //如果到达了最后一个位置
            //路径最后一个字符是运算符号  把它删掉
            //路径最后一个字符不是是运算符号 就保持不变
            char last = path.charAt(path.length() - 1);
            path = (last == '+' || last == '-' || last == '*')
                    ? path.substring(0, path.length() - 1) : path;
            if (check(path, target)) {
                ans.add(path);
            }
            return;
        }
        //4个决策
        String p0 = String.valueOf(str[index]);
        String p1 = p0 + "+";
        String p2 = p0 + "-";
        String p3 = p0 + "*";
        /*
         * 四个决定 + - * 什么多读不做
         * */
        process(str, index + 1, target, path + p0, ans);
        process(str, index + 1, target, path + p1, ans);
        process(str, index + 1, target, path + p2, ans);
        process(str, index + 1, target, path + p3, ans);
    }

    // path是正常的公式字符串，检查是否计算的过程是否和target一样
    public static boolean check(String path, int target) {
        //黑盒
        return true;
    }

    public static int ways(String num, int target) {
        char[] str = num.toCharArray();
        int first = str[0] - '0';
        return f(str, 1, 0, first, target);
    }

    public static int f(char[] str, int index, int left, int cur, int target) {
        if (index == str.length) {
            return (left + cur) == target ? 1 : 0;
        }
        int ways = 0;
        //第一个决定+
        int num = str[index] - '0';
        //第二个决定-
        ways += f(str, index + 1, left + cur, num, target);
        //第三个决定*
        ways += f(str, index + 1, left + cur, -num, target);
        //第四个决定不加符号
        ways += f(str, index + 1, left, cur * num, target);
        if (cur != 0) {
            if (cur > 0) {
                ways += f(str, index + 1, left, cur * 10 + num, target);
            } else {
                ways += f(str, index + 1, left, cur * 10 - num, target);
            }
        }
        return ways;
    }

    public static List<String> addoperators(String num, int target) {
        List<String> ret = new LinkedList<>();
        if (num.length() == 0) {
            return ret;
        }
        /*
         * 沿途的数字拷贝和+ - *的决定，放在path里
         * 最长的字符串有多长
         * eg: "105" 最短是 105 3个字符 最多 是1+0+5 5个字符
         * */
        char[] path = new char[num.length() * 2 - 1];
        // num -> char[]
        char[] digits = num.toCharArray();
        long n = 0;
        /*
         * 递归时从左往右尝试的模型
         *
         * */
        for (int i = 0; i < digits.length; i++) {
            //尝试0~i前缀作为第一部分
            n = n * 10 + digits[i] - '0';
            path[i] = digits[i];
            //后续过程
            dfs(ret, path, i + 1, 0, n, digits, i + 1, target);
            if (n == 0) {
                break;
            }
        }
        return ret;
    }

}
