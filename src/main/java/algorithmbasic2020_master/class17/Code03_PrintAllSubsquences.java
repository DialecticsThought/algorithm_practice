package algorithmbasic2020_master.class17;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 从左到右的尝试模型
 * 打印一个字符串的全部子序列
 */
public class Code03_PrintAllSubsquences {

    // s -> "abc" ->
    public static List<String> subs(String s) {
        char[] str = s.toCharArray();
        String path = "";
        List<String> ans = new ArrayList<>();
        process1(str, 0, ans, path);
        return ans;
    }

    /*
      TODO
       str 固定参数
       来到了str[index]字符，index是位置
       str[0..index-1]已经走过了！之前的决定，都在path上
       之前的决定已经不能改变了，就是path
       str[index....]还能决定，之前已经确定，而后面还能自由选择的话，
       把所有生成的子序列 也就是index来到str的终止位置 把沿途路径所形成的的所有答案，放入到ans里去
    */
    public static void process1(char[] str, int index, List<String> ans, String path) {
        if (index == str.length) {//说明index来到str的终止位置
            ans.add(path);//把这个条路径加入到ans
            return;
        }
        /**
         *TODO
         * 有一个str = abc  index就是 0,1,2
         * 每一个位置上的字符有两种选择： 要 和 不要
         * 选择 某一个分支（要和不要） 就调到下一个位置的字符 继续做选择（要 和 不要）
         * abc 就有 2*2*2 = 8 种路径
         * [a,b,c]
         * 				    0下标
         *              要 ↙↗   ↘↖ 不要
         *              1下标     1下标
         *          ↙↗   ↘↖      ↙↗   ↘↖
         *        2下标   2下标   2下标   2下标
         *       ↑↓  ↑↓  ↑↓  ↑↓  ↑↓ ↑↓   ↑↓
         *      abc  ab  ac  a   bc  b   c
         * */
        // 没有要index位置的字符
        String no = path;
        process1(str, index + 1, ans, no);
        // 要了index位置的字符
        String yes = path + String.valueOf(str[index]);
        process1(str, index + 1, ans, yes);
    }

    public static List<String> subsNoRepeat(String s) {
        char[] str = s.toCharArray();
        String path = "";
        HashSet<String> set = new HashSet<>();
        process2(str, 0, set, path);
        List<String> ans = new ArrayList<>();
        for (String cur : set) {
            ans.add(cur);
        }
        return ans;
    }

    /*
     * 打印一个字符串的全部子序列，要求不要出现重复字面值的子序列
     * HashSet可以去重
     * */
    public static void process2(char[] str, int index, HashSet<String> set, String path) {
        if (index == str.length) {
            set.add(path);
            return;
        }
        String no = path;
        process2(str, index + 1, set, no);
        String yes = path + String.valueOf(str[index]);
        process2(str, index + 1, set, yes);
    }

    public static void main(String[] args) {
        String test = "acccc";
        List<String> ans1 = subs(test);
        List<String> ans2 = subsNoRepeat(test);

        for (String str : ans1) {
            System.out.println(str);
        }
        System.out.println("=================");
        for (String str : ans2) {
            System.out.println(str);
        }
        System.out.println("=================");

    }

}
