package heima_data_structure.java.src.main.java.com.itheima.algorithm.recursion_single;

/**
 * 递归反向打印字符串
 */
public class E02ReversePrintString {
    /**
     * TODO 这里利用到了递归中的 "递" 执行打印任务
     *
     * @param string
     * @param i
     */
    public static void reversePrintString(String string, int i) {
        // base case
        if (i < 0) {
            return;
        }
        // 打印当前遍历到的i的索引的字符
        char[] charArray = string.toCharArray();
        System.out.println(charArray[i]);
        //执行 打印当前遍历到的i-1的索引的字符的任务
        reversePrintString(string, i - 1);
    }

    /**
     * TODO 这里利用到了递归中的 "归" 执行打印任务
     *
     * @param n
     * @param str
     */
    public static void f(int n, String str) {
        if (n == str.length()) {
            return;
        }
        f(n + 1, str);
        System.out.println(str.charAt(n));
    }

    public static void main(String[] args) {
        String str = "abcd";

        f(0, str);

        reversePrintString(str, str.length() - 1);
    }
}
