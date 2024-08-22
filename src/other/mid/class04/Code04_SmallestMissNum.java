package other.mid.class04;

/**
 * 题目六
 * 数组中未出现的最小正整数
 * 【题目】
 * 给定一个无序整型数组arr，找到数组中未出现的最小正整数。
 * 【举例】
 * arr=[-1,2,3,4]。返回1。
 * arr=[1,2,3,4]。返回5。
 */
public class Code04_SmallestMissNum {

	public static int missNum(int[] arr) {
		int l = 0;//0~L-1范围上 做到了任何一个i 上面放的是i+1
		int r = arr.length;
		/*
		* R假设待定区是最好的情况下 可以收集到1~R的所有数字
		* R表示无效区 垃圾区
		* 初始的时候 没有垃圾区 后面向右滑的过程中 垃圾区变大
		* R的右侧 也代表 我默认我没看过的数是最好的期望的情况下1我可以手机圈1~R的所有数组
		*
		* */
		while (l < r) {
			//arr[l]当前数字 之前的区域都是有效区域
			if (arr[l] == l + 1) {//已经做了
				l++;
			} else if (arr[l] <= l || arr[l] > r || arr[arr[l] - 1] == arr[l]) {
				//arr[arr[l] - 1] == arr[l]:表示arr中存在1~arr.length的两个两个相同的数
				arr[l] = arr[--r];
			} else {
				swap(arr, l, arr[l] - 1);//表示1~arr.lenght之间的数大的跑到了前面
			}
		}
		return l + 1;
	}

	public static void swap(int[] arr, int index1, int index2) {
		int tmp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = tmp;
	}

	public static void main(String[] args) {
		int[] arr = { -1, 0, 2, 1, 3, 5 };
		System.out.println(missNum(arr));

	}
}
