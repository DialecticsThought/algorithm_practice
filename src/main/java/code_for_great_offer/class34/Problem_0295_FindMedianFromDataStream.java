package code_for_great_offer.class34;

import java.util.PriorityQueue;
/*
* 中位数是有序整数列表中的中间值。如果列表的大小是偶数，则没有中间值，中位数是两个中间值的平均值。
* 例如 arr = [2,3,4]的中位数是 3。
* 例如arr = [2,3] 的中位数是 (2 + 3) / 2 = 2.5 。
* 实现 MedianFinder 类:
* MedianFinder() 初始化 MedianFinder对象。
* void addNum(int num) 将数据流中的整数 num 添加到数据结构中。
* double findMedian() 返回到目前为止所有元素的中位数。与实际答案相差10-5以内的答案将被接受。
* 示例 1：
* 输入
* ["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
* [[], [1], [2], [], [3], []]
* 输出
* [null, null, null, 1.5, null, 2.0]
* 解释
* MedianFinder medianFinder = new MedianFinder();
* medianFinder.addNum(1);    // arr = [1]
* medianFinder.addNum(2);    // arr = [1, 2]
* medianFinder.findMedian(); // 返回 1.5 ((1 + 2) / 2)
* medianFinder.addNum(3);    // arr[1, 2, 3]
* medianFinder.findMedian(); // return 2.0

来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/find-median-from-data-stream
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
* */
public class Problem_0295_FindMedianFromDataStream {
	/*
	*TODO
	* 第1个数字 5
	* 直接进入大根堆
	* 第2个数字 3
	* 判断当前数字是否<= 大根堆的堆顶 那么 就如大根堆 否则 入小根堆
	* 入堆之后，检查大小并排序
	* 如果 大根堆和小根堆的长度差值>=2 那么就弹出元素多的堆的堆顶放入到另一个堆中
	* 也就是说 此时大根堆 有一个元素3 小根堆 有一个元素5
	* 第3个数字 7
	* 判断当前数字是否<= 大根堆的堆顶
	* 入小根堆之后，检查大小并排序 此时小根堆 5 7
	* 第3个数字 6
	* 判断当前数字是否<= 大根堆的堆顶
	* 入小根堆之后，检查大小并排序 此时小根堆 5 6 7
	* 如果 大根堆和小根堆的长度差值>=2 那么就弹出元素多的堆的堆顶放入到另一个堆中
	* 也就是说 此时大根堆 有一个元素 5 3 小根堆 有一个元素 6 7
	* 也就是说 所有的数据小的一半 在大根堆 大的一半在小根堆
	* 最后 哪一个堆的元素多 哪一个堆的堆顶就是中位数
	 * */
	class MedianFinder {
		//大根堆
		private PriorityQueue<Integer> maxh;
		//小根堆
		private PriorityQueue<Integer> minh;

		public MedianFinder() {
			maxh = new PriorityQueue<>((a, b) -> b - a);
			minh = new PriorityQueue<>((a, b) -> a - b);
		}
		//TODO
		public void addNum(int num) {
			if (maxh.isEmpty() || maxh.peek() >= num) {
				maxh.add(num);
			} else {
				minh.add(num);
			}
			balance();
		}
		//TODO 找到中位数
		public double findMedian() {
			if (maxh.size() == minh.size()) {
				return (double) (maxh.peek() + minh.peek()) / 2;
			} else {
				return maxh.size() > minh.size() ? maxh.peek() : minh.peek();
			}
		}

		private void balance() {
			if (Math.abs(maxh.size() - minh.size()) == 2) {
				if (maxh.size() > minh.size()) {
					minh.add(maxh.poll());
				} else {
					maxh.add(minh.poll());
				}
			}
		}

	}

}
