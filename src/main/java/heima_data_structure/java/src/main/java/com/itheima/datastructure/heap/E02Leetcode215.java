package heima_data_structure.java.src.main.java.com.itheima.datastructure.heap;

/**
 * <h3>求数组中第 K 大的元素 - 小顶堆</h3>
 * <p>
 * 思路
 * <ol>
 *     <li>向小顶堆放入前k个元素</li>
 *     <li>剩余元素</li>
 *     <ul>
 *         <li>若 <= 堆顶元素, 则略过</li>
 *         <li>若 > 堆顶元素, 则替换堆顶元素</li>
 *     </ul>
 *     <li>这样小顶堆始终保留的是到目前为止, <b>前 K 大</b>的元素</li>
 *     <li>循环结束, 堆顶元素即为<b>第 K 大</b>元素</li>
 *     <li>时间复杂度 O(n*log(n))</li>
 * </ol>
 */
public class E02Leetcode215 {

    public int findKthLargest(int[] numbers, int k) {
        MinHeap heap = new MinHeap(k);
        for (int i = 0; i < k; i++) {
            heap.offer(numbers[i]);
        }
        for (int i = k; i < numbers.length; i++) {
            if(numbers[i] > heap.peek()) {
                heap.replace(numbers[i]);
            }
        }
        return heap.peek();
    }

    public static void main(String[] args) {
        E02Leetcode215 code = new E02Leetcode215();
        // 应为5
        System.out.println(code.findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));
        // 应为4
        System.out.println(code.findKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4));
    }
}
