package heima_data_structure.java.src.main.java.com.itheima.algorithm.greedy;

import java.util.PriorityQueue;

/**
 * <h3>连接棒材的最低费用</h3>
 * <p>为了装修新房，你需要加工一些长度为正整数的棒材。如果要将长度分别为 X 和 Y 的两根棒材连接在一起，你需要支付 X + Y 的费用。 返回讲所有棒材连成一根所需要的最低费用。</p>
 * <p>与Huffman树非常类似</p>
 */
public class Code_1167 {
    /*
        举例 棒材为 [1,8,3,5]

        如果以如下顺序连接(非最优)
        - 1+8=9
        - 9+3=12
        - 12+5=17
        总费用为 9+12+17=38

        如果以如下顺序连接(最优)
        - 1+3=4
        - 4+5=9
        - 8+9=17
        总费用为 4+9+17=30
     */
    int connectSticks(int[] sticks) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int stick : sticks) {
            queue.offer(stick);
        }

        int sum = 0;
        while (queue.size() >= 2) {
            Integer x = queue.poll();
            Integer y = queue.poll();
            int c = x + y;
            sum += c;
            queue.offer(c);
        }
        return sum;
    }

    public static void main(String[] args) {
        Code_1167 leetcode = new Code_1167();
        System.out.println(leetcode.connectSticks(new int[]{1, 8, 3, 5})); // 30
        System.out.println(leetcode.connectSticks(new int[]{2, 4, 3})); // 14
    }
}
