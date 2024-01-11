package code_for_great_offer.class06;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 题目
 * 数组所有数都疑惑起来的结果，叫做异或和
 * 给定一个数组arr，可以任务切分 若干个不相交的子数组其中一定存在一种最优的方案，
 * 使得切除异或和为0的子数组最多返回这个最多数量
 */
public class Code04_MostXorZero {

	// 暴力方法
	public static int comparator(int[] arr) {
		// 检查输入数组是否为空或长度为0
		if (arr == null || arr.length == 0) {
			return 0;
		}
		// 计算数组长度
		int N = arr.length;
		// 初始化异或前缀和数组
		int[] eor = new int[N];
		// 第一个元素的异或前缀和是它本身
		eor[0] = arr[0];
		for (int i = 1; i < N; i++) {
			// 计算每个位置的异或前缀和
			eor[i] = eor[i - 1] ^ arr[i];
		}
		// 开始递归处理，寻找最优切分
		return process(eor, 1, new ArrayList<>());
	}

	/**
	 * index去决定：前一坨部分，结不结束！
	 * 如果结束！就把index放入到parts里去
	 * 如果不结束，就不放
	 * arr = [1, 2, 3, 0, 2]
	 * eor = [1, 3, 0, 0, 2]
	 *           [1, 3, 0, 0, 2]
	 *              /         \
	 *          切分[1]        不切分
	 *  	  /       \       /      \
	 *     切分[1,2]   不切分 切分[2]   不切分
	 *     /     \       /      \    /     \
	 *   切分[1,2,3] 不切分   切分 不切分  切分 不切分
	 * |   			  		 						f(1,[])
	 * | 			 				/         								\
	 * | 						f(2,[]) 										f(2,[1])
	 * |	 			 /         				\				 					/         		\
	 * |    		 f(3,[])  						f(3,[2])	   	 	    f(3,[1])	 				f(3,[1,2])
	 * |			/      \						/      \				  /      \				/      \
	 * |  	f(4,[])    f(4,[3]) 			f(4,[2]) 	f(4,[2,3])   	f(4,[1]) f(4,[1,3]) 	f(4,[1,2]) f(4,[1,2,3])
	 * |		/      \	/    \				/    \				/  \
	 * |f(5,[])f(5,[4])f(5,[3])f(5,[3,4]) f(5,[2]) f(5,[2,4]) f(5,[2,3])f(5,[2,3,4])
	 * @param eor
	 * @param index
	 * @param parts
	 * @return
	 */
	public static int process(int[] eor, int index, ArrayList<Integer> parts) {
		int ans = 0;
		if (index == eor.length) {
			// 将最后一个位置加入切分点
			parts.add(eor.length);
			// 算一下这种分割下，有多少种异或和为0的部分
			// 计算当前切分方式的异或和为0的子数组数量
			ans = eorZeroParts(eor, parts);
			/**
			*TODO 深度优先还原现场
			* 假设 已经在 2,3之间 5,6之间  7，8之间 做了划分 => {3,6,8}
			* 现在 arr还差9和10位置没做选择
			* 情况1 9,10位置之间做划分 =》 {3,6,8,10}
			* 情况2之前 还原现场 {3,6,8,10} =》 {3,6,8}
			* 情况2
			* */
			parts.remove(parts.size() - 1);
		} else {
			//TODO 决定1 当前遍历到的位置index 不做划分 让下一轮 决定 index + 1和index之间做划分
			int p1 = process(eor, index + 1, parts);
			//TODO 决定2 当前遍历到的位置index 做划分  让下一轮 决定 index + 1和index之间做划分
			parts.add(index);
			int p2 = process(eor, index + 1, parts);
			//TODO 决定2的结果删去 深度优先还原
			parts.remove(parts.size() - 1);
			ans = Math.max(p1, p2);
		}
		return ans;
	}
	/**
	 * TODO
	 * parts表示哪些位置要做划分 eor异或前缀数组
	 * 判断切分后各个子数组的异或和是否为0
	 * @param eor
	 * @param parts
	 * @return
	 */
	public static int eorZeroParts(int[] eor, ArrayList<Integer> parts) {
		// 上一个子数组的结束位置
		int L = 0;
		// 满足条件的子数组计数
		int ans = 0;
		// 计算每个子数组的异或和
		for (Integer end : parts) {
			// 如果异或和为0，计数加1
			if ((eor[end - 1] ^ (L == 0 ? 0 : eor[L - 1])) == 0) {
				ans++;
			}
			// 更新上一个子数组的结束位置
			L = end;
		}
		return ans;
	}

	// 时间复杂度O(N)的方法
	public static int mostXor(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		int[] dp = new int[N];//TODO 该数组的最后一个元素就是答案

		// key 某一个前缀异或和
		// value 这个前缀异或和上次出现的位置(最晚！)
		HashMap<Integer, Integer> map = new HashMap<>();
		map.put(0, -1);//TODO 一个数也没有 就有异或和0了 出现的位置为-1
		//代表 0~i整体的异或和
		int xor = 0;
		for (int i = 0; i < N; i++) {
			xor ^= arr[i];
			if (map.containsKey(xor)) { // 可能性2  之前出现过这个异或和
				/*
				*TODO 最近出现的位置为pre  说明 pre+1~i 范围是最后一个arr 实现异或和为0的部分
				* 那么 dp[pre]+1
				* 如果pre是-1 那说明 只有一个arr是异或和为0的部分 那就是 0~i整体
				* */
				int pre = map.get(xor);
				dp[i] = pre == -1 ? 1 : (dp[pre] + 1);
			}
			if (i > 0) {
				dp[i] = Math.max(dp[i - 1], dp[i]);
			}
			map.put(xor, i);
		}
		return dp[N - 1];
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random());
		}
		return arr;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {
		int testTime = 150000;
		int maxSize = 12;
		int maxValue = 5;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(maxSize, maxValue);
			int res = mostXor(arr);
			int comp = comparator(arr);
			if (res != comp) {
				succeed = false;
				printArray(arr);
				System.out.println(res);
				System.out.println(comp);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");
	}

}
