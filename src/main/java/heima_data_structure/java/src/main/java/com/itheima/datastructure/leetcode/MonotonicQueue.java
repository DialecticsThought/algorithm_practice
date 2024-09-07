package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.util.LinkedList;

/**
 * <h3>单调递减队列</h3>
 */
public class MonotonicQueue {
    /**
     * 初始队列 从队头到队尾 依次变小
     * 初始情况
     * 7 6 5 2 1
     * 此时要加入3
     * 比较 队尾 和 3
     * 发现 队尾 < 3
     * 弹出队尾 1
     * 比较 队尾 和 3
     * 发现 队尾 < 3
     * 弹出队尾 2
     * 比较 队尾 和 3
     * 发现 队尾 > 3
     * 加入 3
     */
    private LinkedList<Integer> deque = new LinkedList<>();

    /**
     * 获取队列头部元素 但是不移除
     * @return
     */
    public Integer peek() {
        return deque.peekFirst();
    }

    /**
     * 移除队列头部元素
     */
    public void poll() {
        deque.pollFirst();
    }

    /**
     * 向队列尾部添加
     * @param t
     */
    public void offer(Integer t) {
        while (!deque.isEmpty() && deque.peekLast() < t) {
            // 弹出队列尾部
            deque.pollLast();
        }
        // 向队列尾部添加
        deque.offerLast(t);
    }

    @Override
    public String toString() {
        return deque.toString();
    }

    public static void main(String[] args) {
        MonotonicQueue q = new MonotonicQueue();
        for (int i : new int[]{1, 3, -1, -3, 5, 3, 6, 7}) {
            q.offer(i);
            System.out.println(q);
        }

    }
}
