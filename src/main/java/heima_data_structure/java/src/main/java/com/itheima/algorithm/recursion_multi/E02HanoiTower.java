package heima_data_structure.java.src.main.java.com.itheima.algorithm.recursion_multi;

//import org.springframework.util.StopWatch;

import java.util.LinkedList;

/**
 * 递归汉诺塔
 */
public class E02HanoiTower {
    static LinkedList<Integer> a = new LinkedList<>();
    static LinkedList<Integer> b = new LinkedList<>();
    static LinkedList<Integer> c = new LinkedList<>();

    static void init(int n) {
        for (int i = n; i >= 1; i--) {
            a.addLast(i);
        }
    }

    /**
     * <h3>移动圆盘</h3>
     * <pre>
     *  n个盘子 从 左 中 右  的左 移动到右
     *  分成3步
     *  让n-1个盘子 从左边 移动到中间
     *  第n个盘子 从左边移动到右边
     *  让n-1个盘子 从中间移动到 右边
     *
     *  让n-1个盘子 从左边 移动到中间 可以再分
     *  让n-2个盘子 从左边 移动到中间
     *  第n-1个盘子 从左边移动到右边
     *  让n-2个盘子 从中间移动到 右边
     *  ....
     * </pre>
     * a,b,c这3个LinkedList 作为3个柱子
     * @param n 圆盘个数
     * @param a 来源 ,初始所有盘子放的地方
     * @param b 中间 ,他是一个中间的柱子,目的是为了把n-1个盘子暂时放在这个柱子上
     * @param c 目标 ,最终要把所有盘子要放入的地方
     */
    static void move(int n, LinkedList<Integer> a,
                     LinkedList<Integer> b,
                     LinkedList<Integer> c) {
        // base case 没有盘子
        if (n == 0) {
            return;
        }
        // 下一轮中
        move(n - 1, a, c, b);   // 把 n-1 个盘子从 a 移至 b , c是中间
        c.addLast(a.removeLast()); // 把 第 n 个的盘子由 a 移至 c
        move(n - 1, b, a, c);   // 把 n-1 个盘子从 b 移至 c , a是中间
    }

    // T(n) = 2T(n-1) + c,  O(2^64)


    public static void main(String[] args) {
/*        StopWatch sw = new StopWatch();
        int n = 1;
        init(n);
        print();
        sw.start("n=1");
        move(n, a, b, c);
        sw.stop();
        print();
        System.out.println(sw.prettyPrint());*/
    }

    private static void print() {
        System.out.println("----------------");
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }
}
