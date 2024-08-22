package code_for_great_offer.class39;


/*
*TODO
* 来自腾讯
* 给定一个长度为n的数组arr，求有多少个子数组满足 :
* 子数组两端的值，是这个子数组的最小值和次小值，最小值和次小值谁在最左和最右无所谓
* n<=100000（10^5） n*logn  O(N)
* arr = [5 4 2 3 6 2]
* [5 4] √
* [5 4 2] ×
* [2 3 6] ×
* 技巧：单调栈  eg: 可见山峰对
* 问题：
* 0位置的数如果作为子arr的右侧 也作为最小值 ，向左侧延伸 有多少个子arr
* 1位置的数如果作为子arr的右侧 也作为最小值 ，向左侧延伸 有多少个子arr
* ...
* 把所有的位置的答案求出来
* N=arr.len-1
* N-1位置的数如果作为子arr的最左侧 也作为最小值 ，向右侧延伸 有多少个子arr
* N-2位置的数如果作为子arr的最左侧 也作为最小值 ，向右侧延伸 有多少个子arr
* ...
* 把所有的位置的答案求出来
* arr = [3 2 1 2 3 2 1 1 0]
* 栈 从栈底到栈顶 是 小 -> 大
* 1.来到0位置 3进栈
* 2.来到1位置 2 < 栈顶的3 => 3弹出，3弹出 说明 3作为子arr的左侧 2作为arr的右侧 满足条件
* 	1位置的2生成答案：1  然后2 进栈
* 3.来到0位置 1 < 栈顶的2 => 2弹出，2弹出 说明 2作为子arr的左侧 1作为arr的右侧 满足条件
* 	2位置的1生成答案：1  然后1 进栈
* 4.来到3位置 2进栈
* 5.来到4位置 3进栈
* 6.来到5位置 2 < 栈顶的3 => 3弹出，3弹出 说明 3作为子arr的左侧 2作为arr的右侧 满足条件
* 	5位置的2生成答案：1  然后2 进栈 此时 外面的2与栈顶的2合并 变成一个元素<2,2> 进入栈
* 7.来到6位置 1 < 栈顶的2 => 2弹出，2弹出 说明 2作为子arr的左侧 1作为arr的右侧 满足条件
* 	6位置的1生成答案：2  即[2 3 2 1] [2 1]
* 	对于弹出的2个2而言 它的答案是 2C2=1个(概率论的) 即[2,3,2]这个数组
* 		eg:如果是7个3的话 那么 1生成答案：7 对于3而言 它的答案是 7C2个(概率论的)
*   然后1 进栈 此时 外面的1与栈顶的1合并 变成一个元素<1,2>
* ...
* arr = [2 3 2 3 2 1 4 3 1 2 2 3]
* 1.来到0位置 2进栈
* 2.来到1位置 3进栈
* 3.来到2位置 2< 栈顶的3 => 3弹出，3弹出 说明 3作为子arr的左侧 2作为arr的右侧 满足条件
*   1置的3生成答案：1 然后2 进栈 此时 外面的2与栈顶的2合并 变成一个元素<2,2> 进入栈
* 4.来到3位置 3进栈
* 5.来到4位置 2 < 栈顶的3 => 3弹出，3弹出 说明 3作为子arr的左侧 2作为arr的右侧 满足条件
* 	3置的3生成答案：1 然后2 进栈 此时 外面的2与栈顶的2合并 变成一个元素<2,3> 进入栈
* 6.来到5位置 1 < 栈顶的2 => 2弹出，2弹出 说明 2作为子arr的左侧 1作为arr的右侧 满足条件
* 	6位置的1生成答案：3  即[2 3 2 3 2 1] [2 3 2 1] [2 1]
* 	对于弹出的3个2而言 它的答案是 3C2个(概率论的)
* 7.来到6位置 4进栈
* 8.来到7位置 3 < 栈顶的4 => 4弹出，4弹出 说明 4作为子arr的左侧 3作为arr的右侧 满足条件
* 	6位置的4生成答案：1  然后3 进栈
* 9.来到8位置 1 < 栈顶的3 => 3弹出，3弹出 说明 3作为子arr的左侧 1作为arr的右侧 满足条件
* 	7位置的3生成答案：1  然后3 进栈 此时 外面的1与栈顶的1合并 变成一个元素<1,2> 进入栈
* 10.来到9位置 2进栈
* 11.来到10位置 2进栈
* 12.来到11位置 3进栈
* 13.最后单独结算 弹出3 生成对应 1C2=0 ，弹出2 生成对应2C2=1 ，弹出1 生成2C2=1
* 从右往左遍历
* 因为从左往右遍历 已经计算出 每个位置弹出的时候的答案了 ，只要计算当前数的答案
* */
public class Code02_ValidSequence {


	public static int nums(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int n = arr.length;
		//TODO 出现的值
		int[] values = new int[n];
		//TODO 出现的值对应的次数
		int[] times = new int[n];
		//TODO 栈的下标
		int size = 0;
		int ans = 0;
		for (int i = 0; i < arr.length; i++) {
			//TODO values[size - 1]栈顶元素
			while (size != 0 && values[size - 1] > arr[i]) {
				size--;
				ans += times[size] + cn2(times[size]);
			}
			if (size != 0 && values[size - 1] == arr[i]) {
				times[size - 1]++;
			} else {
				values[size] = arr[i];
				times[size++] = 1;
			}
		}
		//TODO 单独结算栈里的元素
		while (size != 0) {
			ans += cn2(times[--size]);
		}
		for (int i = arr.length - 1; i >= 0; i--) {
			while (size != 0 && values[size - 1] > arr[i]) {
				ans += times[--size];
			}
			if (size != 0 && values[size - 1] == arr[i]) {
				times[size - 1]++;
			} else {
				values[size] = arr[i];
				times[size++] = 1;
			}
		}
		return ans;
	}

	public static int cn2(int n) {
		return (n * (n - 1)) >> 1;
	}

	// 为了测试
	// 暴力方法
	public static int test(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int ans = 0;
		for (int s = 0; s < arr.length; s++) {
			for (int e = s + 1; e < arr.length; e++) {
				int max = Math.max(arr[s], arr[e]);
				boolean valid = true;
				for (int i = s + 1; i < e; i++) {
					if (arr[i] < max) {
						valid = false;
						break;
					}
				}
				ans += valid ? 1 : 0;
			}
		}
		return ans;
	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = (int) (Math.random() * v);
		}
		return arr;
	}

	// 为了测试
	public static void printArray(int[] arr) {
		for (int num : arr) {
			System.out.print(num + " ");
		}
		System.out.println();
	}

	// 为了测试
	public static void main(String[] args) {
		int n = 30;
		int v = 30;
		int testTime = 100000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int m = (int) (Math.random() * n);
			int[] arr = randomArray(m, v);
			int ans1 = nums(arr);
			int ans2 = test(arr);
			if (ans1 != ans2) {
				System.out.println("出错了!");
				printArray(arr);
				System.out.println(ans1);
				System.out.println(ans2);
			}
		}
		System.out.println("测试结束");
	}

}
