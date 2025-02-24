import java.util.LinkedList;
import java.util.Queue;

/**
 * @Description
 * @Author veritas
 * @Data 2025/2/24 16:43
 */
public class Josephus {
    /**
     * 初始化：
     * <p>
     * 初始时，所有人的顺序是：1, 2, 3, 4, 5。
     * <p>
     * 第一次报数：
     * 从 1 开始报数，数到 3 的人被淘汰。
     * 淘汰的是 3（因为报数到 3）。
     * 剩下的人是：1, 2, 4, 5。 => 队列 4 -> 5 -> 1 -> 2
     * <p>
     * 第二次报数：
     * 从 4（上一次淘汰后的下一个人）开始报数，数到 3 的人被淘汰。
     * 从 4 开始报数：4 → 5 → 1 → 2 → 4。
     * 结果是，淘汰的是 1（因为报数到 3 时是 1）。
     * 剩下的人是：2, 4, 5。  => 队列 2 -> 4 -> 5
     * <p>
     * 第三次报数：
     * 从 2（上一次淘汰后的下一个人）开始报数，数到 3 的人被淘汰。
     * 从 2 开始报数：2 → 4 → 5 → 2。
     * 结果是，淘汰的是 5（因为报数到 3 时是 5）。
     * 剩下的人是：2, 4。 => 队列 2 -> 4
     * <p>
     * 第四次报数：
     * 从 2（上一次淘汰后的下一个人）开始报数，数到 3 的人被淘汰。
     * 从 2 开始报数：2 → 4 → 2。
     * 结果是，淘汰的是 4（因为报数到 3 时是 4）。
     * 剩下的人是：2。
     * <p>
     * 结束：
     * 最后剩下的人是 2。
     *
     * @param n 一共几个人
     * @param m 第几个人被淘汰
     * @return
     */
    public Integer josephus(int n, int m) {
        // 创建一个队列并将所有人按顺序加入
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            queue.add(i);
        }
        // 每次报数到 m 的人被淘汰
        while (queue.size() > 1) {
            // 循环遍历 m 次 第1 ~ m-1次遍历到的元素 重新放回去
            for (int i = 1; i < m; i++) {
                // poll() 移除队首元素并返回
                Integer poll = queue.poll();
                queue.offer(poll);
            }
            // 淘汰第 m 个元素
            queue.poll();
        }
        // 执行这行代码只有一个元素了
        return queue.poll();
    }
}
