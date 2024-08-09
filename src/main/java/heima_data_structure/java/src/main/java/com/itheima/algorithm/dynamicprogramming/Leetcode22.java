package heima_data_structure.java.src.main.java.com.itheima.algorithm.dynamicprogramming;



import java.util.ArrayList;
import java.util.List;

/**
 * <h3>括号生成</h3>
 */
public class Leetcode22 {

    public List<String> generateParenthesis(int n) {
        ArrayList<String>[] dp = new ArrayList[n + 1];
        dp[0] = new ArrayList<>(List.of("")); // ""
        dp[1] = new ArrayList<>(List.of("()")); // ()
//        dp[2] = ()(), (())
//        dp[3] = ()()(), ()(()), (())(), (()()), ((()))
        for (int j = 2; j < n + 1; j++) {
            dp[j] = new ArrayList<>();
            for (int i = 0; i < j; i++) { // 第j个卡特兰数的拆分
                // i 对应的集合是内层要嵌套的括号, j - 1 - i 对应的集合是平级要拼接的括号
                System.out.printf("(%d,%d)\t", i, j - 1 - i);
//                dp[j] += dp[i] * dp[j - 1 - i];
                for (String k1 : dp[i]) { // ()(), (())
                    for (String k2 : dp[j - 1 - i]) { // ""
                        // ()()()
                        // ()(())
                        // (())()
                        // (()())
                        // ((()))
                        dp[j].add("(" + k1 + ")" + k2);
                    }
                }
            }
            System.out.println();
        }
//        System.out.println(dp[n]);
        return dp[n];
    }

    public static void main(String[] args) {
        Leetcode22 code = new Leetcode22();
        System.out.println(code.generateParenthesis(3));
    }
}

