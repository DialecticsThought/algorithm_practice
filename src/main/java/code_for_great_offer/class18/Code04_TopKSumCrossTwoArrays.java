package code_for_great_offer.class18;


/**
 * 牛客的测试链接：
 * https://www.nowcoder.com/practice/7201cacf73e7495aa5f88b223bbbf6d1
 * 不要提交包信息，把import底下的类名改成Main，提交下面的代码可以直接通过
 * 因为测试平台会卡空间，所以把set换成了动态加和减的结构
 * 给定两个有序数组arr1和arr2， 再给定一个整数k， 返回来自arr1和arr2的两个数相加和最
 * 大的前k个， 两个数必须分别来自两个数组。
 * 【 举例】
 * arr1=[1,2,3,4,5]， arr2=[3,5,7,9,11]， k=4。 返回数组[16,15,14,14]
 * 【 要求】
 * 时间复杂度达到 O(klogk)
 * eg:
 * [1,3,9,13] [2,8,13,15]
 * top1:13+15 top2:13+13 .....
 * 利用大根堆
 * arr1[3,5,7,9] arr2[4,6,9,13]
 * arr1做行对应 arr2做列对应
 * 		4  6  9  13
 * 	    0  1  2  3
 * 3  0
 * 5  1
 * 7  2			↑
 * 9  3		  ← 22  <- 表示arr1[3]+arr2[3]
 * 此时大根堆
 * <3,3,22> 表示arr1[3]+arr2[3] = 22
 * 弹出堆首<3,3,22>  收集答案 把arr[2][3]和arr[3][2]放入大根堆
 * 此时大根堆
 * <2,3,20>
 * <3,2,18>
 * 弹出堆首 <2,3,20>  收集答案 把arr[2][2]和arr[1][3]放入大根堆
 * 此时大根堆
 * <3,2,18>
 * <1,3,18>
 * <2,2,16>
 * 弹出堆首 <3,2,18>   收集答案 把arr[3][1]放入大根堆    但是不能重复放入arr[2][2]
 * 一定是避免重复
 * ........
 */
import java.util.*;

public class Code04_TopKSumCrossTwoArrays {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int K = sc.nextInt();
		int[] arr1 = new int[N];
		int[] arr2 = new int[N];
		for (int i = 0; i < N; i++) {
			arr1[i] = sc.nextInt();
		}
		for (int i = 0; i < N; i++) {
			arr2[i] = sc.nextInt();
		}
		int[] topK = topKSum1(arr1, arr2, K);
		for (int i = 0; i < K; i++) {
			System.out.print(topK[i] + " ");
		}
		System.out.println();
		sc.close();
	}

	// 放入大根堆中的结构
	public static class Node {
		public int index1;// arr1中的位置
		public int index2;// arr2中的位置
		public int sum;// arr1[index1] + arr2[index2]的值
		public int value;

		public Node(int i1, int i2, int s) {
			index1 = i1;
			index2 = i2;
			sum = s;
		}
	}

	// 生成大根堆的比较器
	public static class MaxHeapComp implements Comparator<Node> {
		@Override
		public int compare(Node o1, Node o2) {
			return o2.sum - o1.sum;
		}
	}
	/*
	 * 在arr1和arr2中每次只拿一个数 请打印累加和的前k个
	 * */
	public static int[] topKSum1(int[] arr1, int[] arr2, int topK) {
		if (arr1 == null || arr2 == null || topK < 1) {
			return null;
		}
		/*
		 * eg: arr1长度10 arr2长度为5 一共只有50个组合
		 * 那最多只能拿前50个累加组合
		 * */
		int N = arr1.length;
		int M = arr2.length;
		// 一共20个数 但是要前500个，不可能
		topK = Math.min(topK, N * M);
		int[] res = new int[topK];//放答案的结构
		int resIndex = 0;//给答案数组使用
		PriorityQueue<Node> maxHeap = new PriorityQueue<>(new MaxHeapComp());
		//防止重复 而申请的结构
		HashSet<Long> set = new HashSet<>();
		// 一开始最右下角的数字 加入堆
		int i1 = N - 1;
		int i2 = M - 1;
		//生成一个新的节点 放入大根堆
		maxHeap.add(new Node(i1, i2, arr1[i1] + arr2[i2]));
		//利用二维数组的(i1, i2)和列数M这个3个参数算出一个一维坐标 做标记 表示该位置是否加入过堆
		set.add(x(i1, i2, M));
		while (resIndex != topK) {
			//每一次弹出一个节点
			Node curNode = maxHeap.poll();
			//top1 top2 。。。topk 依次放入res
			res[resIndex++] = curNode.sum;
			i1 = curNode.index1;
			i2 = curNode.index2;
			//TODO 每个点都会把左和上放入堆 弹出堆的点 不会再一次被其他店放入堆中 所以直接删去
			set.remove(x(i1, i2, M));
			/*
			 * 一个矩阵 从最右下角 作为起点 慢慢向上 向左 递推
			 * 让当前topN的左边和者上边的节点进入大根堆
			 * i1 - 1 >= 0判断是否越界  set.contains(x(i1 - 1, i2, M))判断之前是不是进入过
			 * */
			if (i1 - 1 >= 0 && !set.contains(x(i1 - 1, i2, M))) {
				set.add(x(i1 - 1, i2, M));
				maxHeap.add(new Node(i1 - 1, i2, arr1[i1 - 1] + arr2[i2]));
			}
			if (i2 - 1 >= 0 && !set.contains(x(i1, i2 - 1, M))) {
				set.add(x(i1, i2 - 1, M));
				maxHeap.add(new Node(i1, i2 - 1, arr1[i1] + arr2[i2 - 1]));
			}
		}
		return res;
	}

	public static long x(int i1, int i2, int M) {
		return (long) i1 * (long) M + (long) i2;
	}


	public static int[] topKSum(int[] arr1, int[] arr2, int topK) {
		if (arr1 == null || arr2 == null || topK < 1) {
			return null;
		}
		//防止出现k的数值大于所有的组合的情况
		topK = Math.min(topK, arr1.length * arr2.length);
		int[] res = new int[topK];
		int resIndex = 0;
		PriorityQueue<Node> maxHeap = new PriorityQueue<>(new MaxHeapComp());
		HashSet<String> positionSet = new HashSet<String>();
		int i1 = arr1.length - 1;
		int i2 = arr2.length - 1;
		maxHeap.add(new Node(i1, i2, arr1[i1] + arr2[i2]));
		positionSet.add(String.valueOf(i1 + "_" + i2));
		while (resIndex != topK) {
			Node curNode = maxHeap.poll();
			res[resIndex++] = curNode.value;
			i1 = curNode.index1;
			i2 = curNode.index2;
			if (!positionSet.contains(String.valueOf((i1 - 1) + "_" + i2))) {
				positionSet.add(String.valueOf((i1 - 1) + "_" + i2));
				maxHeap.add(new Node(i1 - 1, i2, arr1[i1 - 1] + arr2[i2]));
			}
			if (!positionSet.contains(String.valueOf(i1 + "_" + (i2 - 1)))) {
				positionSet.add(String.valueOf(i1 + "_" + (i2 - 1)));
				maxHeap.add(new Node(i1, i2 - 1, arr1[i1] + arr2[i2 - 1]));
			}
		}
		return res;
	}

	// For test, this method is inefficient but absolutely right
	public static int[] topKSumTest(int[] arr1, int[] arr2, int topK) {
		int[] all = new int[arr1.length * arr2.length];
		int index = 0;
		for (int i = 0; i != arr1.length; i++) {
			for (int j = 0; j != arr2.length; j++) {
				all[index++] = arr1[i] + arr2[j];
			}
		}
		Arrays.sort(all);
		int[] res = new int[Math.min(topK, all.length)];
		index = all.length - 1;
		for (int i = 0; i != res.length; i++) {
			res[i] = all[index--];
		}
		return res;
	}

	public static int[] generateRandomSortArray(int len) {
		int[] res = new int[len];
		for (int i = 0; i != res.length; i++) {
			res[i] = (int) (Math.random() * 50000) + 1;
		}
		Arrays.sort(res);
		return res;
	}

	public static void printArray(int[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static boolean isEqual(int[] arr1, int[] arr2) {
		if (arr1 == null || arr2 == null || arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i != arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

}
