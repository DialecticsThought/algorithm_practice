package code_for_great_offer.class35;
/*
* 来自网易
* 给定一个正数数组arr，表示每个小朋友的得分
* 任何两个相邻的小朋友，如果得分一样，怎么分糖果无所谓，但如果得分不一样，分数大的一定要比分数少的多拿一些糖果
* 假设所有的小朋友坐成一个环形，返回在不破坏上一条规则的情况下，需要的最少糖果数
*/
public class Code05_CircleCandy {
	/*
	*TODO 不带有环形
	* 数组：[3,2,1,4,5,3]
	* 下标： 0 1 2 3 4 5
	* 找到分数最低的2位置 给他1颗糖
	* 1位置的分数>2位置的分数 给1位置的2颗糖
	* 0位置的分数>1位置的分数 给0位置的3颗糖
	* 3位置的分数>2位置的分数 给3位置的2颗糖
	* 4位置的分数>3位置的分数 给4位置的3颗糖
	* 5位置的分数<4位置的分数 给5位置的1颗糖
	*TODO
	* 类似于波峰波谷，波谷一定是1
	* 对于2个波谷之间的波峰而言 拿到的糖数是是2个来自波谷的向上趋势的最大值
	*TODO 如果不带有环形
	* 有一个arr=[3 1 2 4 2 1 6 2 7 2 1]
	* 创建一个left[] left[i]的值 判断 arr[i-1]和arr[i]哪个大
	* arr[i]大 left[i]=left[i-1]+1
	* arr[i-1]大 left[i]=1
	* left=[1 1 2 3 1 1 2 1 2 1 1]
	* 创建一个right[] right[i]的值 判断 arr[i+1]和arr[i]哪个大
	* arr[i]大 right[i]=right[i+1]+1
	* arr[i+1]大 right[i]=1
	* 最后 nums[i]=Math.max(left[i],right[i])
	*TODO 带有环形
	* eg: arr = [4 3 2 1 3 4 5]
	* 找到最小的数 是arr[3]=1
	* 把数组变成
	* arr = [1 3 4 5 4 3 2 1] 这个数组就可以使用不带环形的方法 但是最后一个元素不能算（重复）
	* 下标    3 4 5 6 0 1 2 3
	* 也就是 arr = [1 3 4 5 4 3 2]
	* */
	public static int minCandy(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		if (arr.length == 1) {
			return 1;
		}
		int n = arr.length;
		int minIndex = 0;
		for (int i = 0; i < n; i++) {
			if (arr[i] <= arr[lastIndex(i, n)] && arr[i] <= arr[nextIndex(i, n)]) {
				minIndex = i;
				break;
			}
		}
		int[] nums = new int[n + 1];
		for (int i = 0; i <= n; i++, minIndex = nextIndex(minIndex, n)) {
			nums[i] = arr[minIndex];
		}
		int[] left = new int[n + 1];
		left[0] = 1;
		for (int i = 1; i <= n; i++) {
			left[i] = nums[i] > nums[i - 1] ? (left[i - 1] + 1) : 1;
		}
		int[] right = new int[n + 1];
		right[n] = 1;
		for (int i = n - 1; i >= 0; i--) {
			right[i] = nums[i] > nums[i + 1] ? (right[i + 1] + 1) : 1;
		}
		int ans = 0;
		for (int i = 0; i < n; i++) {
			ans += Math.max(left[i], right[i]);
		}
		return ans;
	}

	public static int nextIndex(int i, int n) {
		return i == n - 1 ? 0 : (i + 1);
	}

	public static int lastIndex(int i, int n) {
		return i == 0 ? (n - 1) : (i - 1);
	}

	public static void main(String[] args) {
		int[] arr = { 3, 4, 2, 3, 2 };
		System.out.println(minCandy(arr));
	}

}
