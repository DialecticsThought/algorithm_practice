package code_for_great_offer.class45;

import java.util.HashMap;
import java.util.TreeSet;

public class LeetCode_2035_PartitionArrayIntoTwoArraysToMinimizeSumDifference {
	/**
	 * 假设有个arr 30个元素 30个元素累加和=100
	 * 分成左半边和右半边
	 * 如果左半边挑选0个数 那么累加和 = 0 那么右半边挑选15个数  累加和尽可能<=50
	 * 如果左半边挑选1个数 假设累加和 = 80 那么右半边挑选15个数  累加和尽可能<=-30  因为(80+-30) = 100/2
	 *
	 * 说人话 就是遍历一遍左半部分  右半部分配合他
	 * @param arr
	 * @return
	 */
	public static int minimumDifference(int[] arr) {
		int size = arr.length;
		int half = size >> 1;// 一半长度
		// 左半部分的表
		HashMap<Integer, TreeSet<Integer>> lmap = new HashMap<>();
		process(arr, 0, half, 0, 0, lmap);
		HashMap<Integer, TreeSet<Integer>> rmap = new HashMap<>();
		// 右半部分的表
		process(arr, half, size, 0, 0, rmap);
		int sum = 0;
		// 0~arr.length 累加和
		for (int num : arr) {
			sum += num;
		}
		int ans = Integer.MAX_VALUE;
		for (int leftNum : lmap.keySet()) {
			for (int leftSum : lmap.get(leftNum)) {
				Integer rightSum = rmap.get(half - leftNum).floor((sum >> 1) - leftSum);
				if (rightSum != null) {
					int pickSum = leftSum + rightSum;
					int restSum = sum - pickSum;
					// 得到最小差值
					ans = Math.min(ans, restSum - pickSum);
				}
			}
		}
		return ans;
	}

	/**
	 * arr -> 8个数   0 1 2 3      4 5 6 7
	 * process(arr, 0, 4)  [0,4)  4是取不到的
	 * process(arr, 4, 8)  [4,8)  8是取不到的
	 * arr[index....end-1]这个范围上，去做选择
	 * pick挑了几个数！
	 * 对于一半的部分，遍历，每次来到一个位置都会有两种选择 1：选 2：不选
	 * sum挑的这些数 ，累加和是多少！
	 * map记录结果 挑选了哪些数 ，累加和是多少
	 * eg: 有一半部分 [3,4,2,5] 假设挑选了 3 2 5 一次选择，又挑选了 4 2 5
	 * 这两个选择都是3个数字，就会有一个有序表专门存放相同挑选数量的各个累加和
	 * HashMap<Integer, TreeSet<Integer>> map
	 * key -> 挑了几个数，比如挑了3个数，但是形成累加和可能多个！
	 * value -> 有序表，都记下来！
	 * 整个过程，纯暴力！2^15 -> 3万多，纯暴力跑完，依然很快！
	 *
	 * 先把arr强行变成2半  30个数 一半15
	 * 左半边的累加和的所有情况
	 * @param arr
	 * @param index
	 * @param end
	 * @param pick
	 * @param sum
	 * @param map
	 */
	public static void process(int[] arr, int index, int end, int pick, int sum,
			HashMap<Integer, TreeSet<Integer>> map) {
		if (index == end) {
			if (!map.containsKey(pick)) {
				map.put(pick, new TreeSet<>());
			}
			map.get(pick).add(sum);
		} else {
			process(arr, index + 1, end, pick, sum, map);
			process(arr, index + 1, end, pick + 1, sum + arr[index], map);
		}
	}

}
