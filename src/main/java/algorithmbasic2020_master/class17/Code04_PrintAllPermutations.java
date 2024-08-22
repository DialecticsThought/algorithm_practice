package algorithmbasic2020_master.class17;

import java.util.ArrayList;
import java.util.List;

/**
 * 打印一个字符串的全部排列
 */
public class Code04_PrintAllPermutations {

    public static List<String> permutation1(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] str = s.toCharArray();
        ArrayList<Character> rest = new ArrayList<Character>();
        //TODO 把所有字符放入
        for (char cha : str) {
            rest.add(cha);
        }
        String path = "";
        f(rest, path, ans);
        return ans;
    }

    /**
     * TODO
     * [a,b,c]
     * | 				root(--)
     * |            ↙↗       ↑↓         ↘↖
     * |          a--       b--       c--
     * |      ↙↗   ↘↖    ↙↗   ↘↖     ↙↗  ↘↖
     * |    ab-    ac-   ba-   bc-   ca-   cb-
     * |    ↑↓      ↑↓   ↑↓    ↑↓     ↑↓    ↑↓
     * |   abc     acb   bac    bca    cab  cba
     * 这是一个三层的树，从根节点开始，到达叶子节点时，会得到所有可能的字符串排列。
     * 例如，从根节点选择 ‘a’，
     * 接下来在第二层，就可以选择’a’剩下未被选的字符’b’或’c’，
     * 同理，接下来的层级继续这个逻辑，直到遍历所有的节点，就能得到全部的字符串排列。
     * 递归和回溯的相关思想是：我们首先选择一个字符，然后递归地去选择剩余的字符，当剩余字符选完时，
     * 我们回溯，移到上一级，然后再选择另一个字符进行递归。
     * 整个过程大致如下：
     * 从根节点开始，我们可以选择字符 a，b 或 c。
     * 假设我们选择了 a，然后在下一层，我们可以选择剩余的字符 b 或 c。
     * 假设我们再次选择了 b，那么在下一层，只剩下字符 c 可以选择。
     * 然后我们完成了一个递归路径，生成字符串 “abc”。
     * 接下来进行回溯，在这种情况下，我们回到 “ab-”，然后选择在这层未被选中的其他字符，例如 c，但是在这里没有可选的字符，所以我们再回溯到 “a-”。
     * 然后在 “a-” 这一层，我们选择剩下未选择的字符，这次选择 c，然后在下一层只剩下字符 b 可以选择。
     * 这样我们得到了另一条路径 “acb”。
     * 我们继续这个过程，回溯然后反复选择，直到所有可能的路径都被处理。
     * 希望这会更有帮助。非常抱歉我目前不能直观的呈现这个过程，如果你需要进一步的解释或有其他的问题，欢迎你随时提问
     *TODO
     * 如果不要重复的排列，但是要全部的排列
     * 需要加上一个标志位 判断某位置之前是否尝试过
     * 用一个标志位 isVisited
     *
     * @param rest
     * @param path
     * @param ans
     */
    public static void f(ArrayList<Character> rest, String path, List<String> ans) {
        if (rest.isEmpty()) {
            ans.add(path);
        } else {
            int N = rest.size();
            //TODO 集合的每一个元素 相当于排列的第一个字符
            for (int i = 0; i < N; i++) {
                char cur = rest.get(i);//TODO 得到当前的字符
                rest.remove(i);//TODO 使用过后 删除
                //TODO 当前字符作为这一轮的选择 加入到path
                f(rest, path + cur, ans);
                //TODO
                rest.add(i, cur);
            }
        }
    }

    public static List<String> permutation2(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] str = s.toCharArray();
        g1(str, 0, ans);
        return ans;
    }

    /*
     *todo
     * str[0-->index-1]0-index-1位置都已经是决定好了 哪个一个位置上有哪一个字符
     * str[index...] index之后的所有字符都有机会来到i位置
     * i来到终止位置，str所形成的当前的样子就是一种结果 存到ans
     * */
    public static void g1(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
        } else {
            //TODO 如果i没有停止 i之后的所哟祖父都可以来到i位置
            for (int i = index; i < str.length; i++) {//TODO i表示 index之后的所有字符都有机会
                //表示index上的字符和i上的字符交换
                swap(str, index, i);
                //index上的字符和i交换之后 通过 查看 index+1位置之后的情况
                g1(str, index + 1, ans);
                //交换完index上的字符和i上的字符 并且递归遍历完所有index+1位置之后的情况后
                //还要恢复回来 也就是递归回退 每一次回退去新的分支查找
                swap(str, index, i);
            }
        }
    }

    public static List<String> permutation3(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] str = s.toCharArray();
        g2(str, 0, ans);
        return ans;
    }

    public static void g2(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
        } else {
            /*
             * 0代表a这个字符在当前的位置是否使用过
             * 2代表b这个字符在当前的位置是否使用过
             * */
            boolean[] visited = new boolean[256];
            /*
             * 如果i没有停止 i之后的所哟祖父都可以来到i位置
             * i表示 index之后的所有字符都有机会
             * */
            for (int i = index; i < str.length; i++) {
                /*
                 * 字符转成数字
                 * eg a-a=0 判断a这个字符在当前的位置是否使用过
                 * b-a=1 判断b这个字符在当前的位置是否使用过
                 * */
                if (!visited[str[i] - 'a']) {
                    visited[str[i]] = true;//之前没有出现过 现在出现了 改成true
                    swap(str, index, i);
                    //表示index上的字符和i上的字符交换
                    //index上的字符和i交换之后 通过 查看 index+1位置之后的情况
                    g2(str, index + 1, ans);
                    //交换完index上的字符和i上的字符 并且递归遍历完所有index+1位置之后的情况后
                    //还要恢复回来 也就是递归回退 每一次回退去新的分支查找
                    swap(str, index, i);
                }
            }
        }
    }

    /*
     * 交换两个字符
     * */
    public static void swap(char[] chs, int i, int j) {
        char tmp = chs[i];
        chs[i] = chs[j];
        chs[j] = tmp;
    }

    public static void main(String[] args) {
        String s = "acc";
        List<String> ans1 = permutation1(s);
        for (String str : ans1) {
            System.out.println(str);
        }
        System.out.println("=======");
        List<String> ans2 = permutation2(s);
        for (String str : ans2) {
            System.out.println(str);
        }
        System.out.println("=======");
        List<String> ans3 = permutation3(s);
        for (String str : ans3) {
            System.out.println(str);
        }

    }

}
