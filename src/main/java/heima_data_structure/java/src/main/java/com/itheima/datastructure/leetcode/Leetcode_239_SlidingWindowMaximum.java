package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h3>滑动窗口最大值 - 单调队列</h3>
 */
public class Leetcode_239_SlidingWindowMaximum {

    /**
     * 每一次窗口右移
     * 向单调递减队列加入新的元素
     * 队头元素即为滑动窗口最大值
     * <pre>
     * 初始
     * arr = [1 3 -1 -3 -4 5 3 6 7]
     * 单调队列：空
     * 一开始窗口
     * [1 3 -1 -3 -4 5 3 6 7]
     *  ↑    ↑
     * 依次把 1 3 -1 放入单调队列
     * 1.单调队列对头元素的索引没有超出滑动窗口范围，不做处理
     * 放入1:
     * 单调队列：1
     * 2.单调队列对头元素的索引没有超出滑动窗口范围，不做处理
     * 放入3:
     * 单调队列：3   因为3>1,1弹出
     * 3.单调队列对头元素的索引没有超出滑动窗口范围，不做处理
     * 放入-1:
     * 单调队列：3 -> -1
     * 窗口右移
     * [1 3 -1 -3 -4 5 3 6 7]
     *    ↑    ↑
     * 4.单调队列对头元素的索引没有超出滑动窗口范围，不做处理
     * 放入-3:
     * 单调队列：3 -> -1 -> -3
     * 窗口右移
     * [1 3 -1 -3 -4 5 3 6 7]
     *       ↑     ↑
     * 5.单调队列对头元素的索引超出滑动窗口范围，做处理
     * 变成 -1 -> -3
     * 放入-4:
     * 单调队列： -1 -> -3 -> -4
     * 窗口右移
     * [1 3 -1 -3 -4 5 3 6 7]
     *          ↑    ↑
     * 6.单调队列对头元素的索引没有超出滑动窗口范围，不做处理
     * 放入5:
     * 单调队列：5  5 > -4 5 > -3  5 > -1 依次弹出
     * ...
     * </pre>
     *
     * @param nums
     * @param k
     * @return
     */
    static int[] maxSlidingWindow(int[] nums, int k) {
        // 单调队列
        MonotonicQueue queue = new MonotonicQueue();
        List<Integer> list = new ArrayList<>();
        // 遍历
        for (int i = 0; i < nums.length; i++) {
            // 检查队列头部元素的索引，超过滑动窗口范围要移除
            // 得到单调队列的队首
            // 如果 滑动窗口的右边界索引 - 滑动窗口大小 = index
            // num[index]就是上一个元素 或者说滑动窗口外的左侧最近的元素
            // num[index] = 单调队列的队首 那么需要移除
            // 不能用 元素个数 与窗口大小的比较 做判读 因为 可能存在 元素个数< 窗口大小但是 队首已经超出范围的情况
            Integer peek = queue.peek();
            int index = i - k;
            if (i >= k && peek == nums[index]) {
                // 弹出
                queue.poll();
            }
            int num = nums[i];
            // 加入
            queue.offer(num);
            if (i >= (k - 1)) {
                ;
                list.add(queue.peek());
            }
        }
        return list.stream().mapToInt(Integer::intValue)
                .toArray();
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(
                maxSlidingWindow(new int[]{1, 3, -1, -3, -4, 5, 3, 6, 7}, 3))
        ); //[3, 3, 5, 5, 6, 7]
//        System.out.println(Arrays.toString(maxSlidingWindow(new int[]{7, 2, 4}, 2))); // [7, 4]
//        System.out.println(Arrays.toString(maxSlidingWindow(new int[]{1, 3, 1, 2, 0, 5}, 3))); // [3, 3, 2, 5]
//        System.out.println(Arrays.toString(maxSlidingWindow(new int[]{-7, -8, 7, 5, 7, 1, 6, 0}, 4))); // [7, 7, 7, 7, 7]
    }
}
