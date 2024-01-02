package code_for_great_offer.class33;

import java.util.ArrayList;

/*
*TODO
*  给你一个整数 n ，返回 和为 n 的完全平方数的最少数量 。
* 完全平方数 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。
* 例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。
* 示例1：
* 输入：n = 12
* 输出：3
* 解释：12 = 4 + 4 + 4
* 示例 2：
* 输入：n = 13
* 输出：2
* 解释：13 = 4 + 9
来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/perfect-squares
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
* */
public class Problem_0279_PerfectSquares {

    /*
    *TODO 推荐做法
    * eg: n=26
    * 那么把 所有平方 <26的数 放进去
    * 那么[1,4,9,16,25]
    * */
    public static int numSquares(int n) {
        ArrayList<Integer> list = new ArrayList<>();
        //TODO 因为题目规定n必须由完全平方数构成，所以我们先找到所有的完全平方数 并且完全平方数满足 <=n
        for (int i = 1; Math.pow(i, 2) <= n; i++) {
            list.add((int) Math.pow(i, 2));
        }
        // 进递归
        return process(list, n);
    }

    // 递归含义：返回可以构成rest的最小完全平方数的个数
    private static int process(ArrayList<Integer> list, int rest) {
        if (rest < 0) {
            // base case，如果rest在减的过程中，变成了负数。说明这个尝试是不行的。
            return -1;
        }
        if (rest == 0) {
            // base case，如果正好减到了0，说明尝试成功。
            // 例如rest=9的时候，用循环尝试。当尝试到list中的9时，发现减去这个9等于0了。
            return 0;
        }
        //TODO min用来表示 n可以被多少个完全平方数构成 并且是最小
        int min = Integer.MAX_VALUE;
        //TODO 遍历集合里面的完全平方数
        for (Integer num : list) {
            //TODO 剩余数-当前比那里到的完全拼防暑
            rest = rest - num;
            int result = process(list, rest);
            if (result != -1 && result < min) {
                // 如果尝试成功了 && 并且这个尝试小于最小值
                // result < min的含义：为什么不是小于等于呢。因为如果说第一次尝试返回了1，那么就会更新min，min=2.
                // 如果说下一个尝试返回2，我们还设置了result<=min，那么min就会再次更新一次，min=3。这是不对的
                min = result + 1;
            }
        }
        return min;
    }


    // 暴力解
    /*
     *TODO
     * n是否能被1个平方数搞出来
     * n是否能被2个平方数搞出来
     * n是否能被3个平方数搞出来
     * .....
     * */
    public static int numSquares1(int n) {
        int res = n, num = 2;
        while (num * num <= n) {
            int a = n / (num * num), b = n % (num * num);
            res = Math.min(res, a + numSquares1(b));
            num++;
        }
        return res;
    }

    // 1 : 1, 4, 9, 16, 25, 36, ...
    // 4 : 7, 15, 23, 28, 31, 39, 47, 55, 60, 63, 71, ...
    // 规律解
    // 规律一：个数不超过4
    // 规律二：出现1个的时候，显而易见
    // 规律三：任何数 % 8 == 7，一定是4个
    // 规律四：任何数消去4的因子之后，剩下rest，rest % 8 == 7，一定是4个
    public static int numSquares2(int n) {
        int rest = n;
        while (rest % 4 == 0) {
            rest /= 4;
        }
        if (rest % 8 == 7) {
            return 4;
        }
        int f = (int) Math.sqrt(n);
        if (f * f == n) {
            return 1;
        }
        for (int first = 1; first * first <= n; first++) {
            int second = (int) Math.sqrt(n - first * first);
            if (first * first + second * second == n) {
                return 2;
            }
        }
        return 3;
    }

    // 数学解
    // 1）四平方和定理
    // 2）任何数消掉4的因子，结论不变
    public static int numSquares3(int n) {
        while (n % 4 == 0) {
            n /= 4;
        }
        if (n % 8 == 7) {
            return 4;
        }
        for (int a = 0; a * a <= n; ++a) {
            // a * a +  b * b = n
            int b = (int) Math.sqrt(n - a * a);
            if (a * a + b * b == n) {
                return (a > 0 && b > 0) ? 2 : 1;
            }
        }
        return 3;
    }

    public static void main(String[] args) {
        for (int i = 1; i < 1000; i++) {
            System.out.println(i + " , " + numSquares1(i));
        }
    }

}
