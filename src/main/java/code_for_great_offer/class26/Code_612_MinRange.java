package code_for_great_offer.class26;



import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class Code_612_MinRange {

	// 本题为求最小包含区间
	// 测试链接 :
	// https://leetcode.cn/problems/smallest-range-covering-elements-from-k-lists/
	public static class Node {
		public int val;
		public int arr;
		public int idx;

		public Node(int value, int arrIndex, int index) {
			val = value;
			arr = arrIndex;//数组的编号
			idx = index;//数组内部的元素下标
		}
	}

	public static class NodeComparator implements Comparator<Node> {

		@Override
		public int compare(Node a, Node b) {
			//val相同的对象不能相互覆盖 2个数组相同位置的元素 也可能是被覆盖 所以 (a.arr - b.arr)
			// 有序表的特性 相同值的元素 会覆盖 多个数会被当成一个数处理
			// 为了能保留 相同 值的不同node 直接比较内存地址
			return a.val != b.val ? (a.val - b.val) : (a.arr - b.arr);
		}

	}
	//返回长度为2的数组作为答案
	//请保证每一个martix[i]所代表的数组有序
	public static int[] minRange(int[][] matrix){
		//建立一个有序表
		TreeSet<Node> set =new TreeSet<>(new NodeComparator());
		//把所有节点放到有序表中
		for (int arrNo = 0; arrNo <matrix.length ; arrNo++) {
			set.add(new Node(matrix[arrNo][0],arrNo,0));
		}
		//这个表示有没有设置过答案
		boolean isSet = false;
		int begin = 0;
		int end = 0;
		while (true){
			//得到有序表中最大和最小
			Node minNode =set.first();
			Node maxNode =set.last();
			if(!isSet){//之前没有设置过答案
				begin =minNode.val;
				end=maxNode.val;
				isSet=true;
			}else {//设置过答案 判断哪一个最优
				if(end-begin>maxNode.val-minNode.val){
					begin =minNode.val;
					end=maxNode.val;
				}
			}
			//弹出有序表中最小的
			set.pollFirst();
			int[] arr = matrix[minNode.arr];
			if(arr.length-1 == minNode.idx){//如果弹出的节点已经是该行数组的最后一个
				break;
			}else {//把弹出的节点的后续节点放入有序表
				set.add(new Node(arr[minNode.idx+1], minNode.arr, minNode.idx+1));
			}
		}
		return new int[]{begin,end};
	}

	public static int[] smallestRange(List<List<Integer>> nums) {
		int N = nums.size();
		TreeSet<Node> set = new TreeSet<>(new NodeComparator());
		for (int i = 0; i < N; i++) {
			set.add(new Node(nums.get(i).get(0), i, 0));
		}
		int r = Integer.MAX_VALUE;
		int a = 0;
		int b = 0;
		while (set.size() == N) {
			Node max = set.last();
			Node min = set.pollFirst();
			if (max.val - min.val < r) {
				r = max.val - min.val;
				a = min.val;
				b = max.val;
			}
			if (min.idx < nums.get(min.arr).size() - 1) {
				set.add(new Node(nums.get(min.arr).get(min.idx + 1), min.arr, min.idx + 1));
			}
		}
		return new int[] { a, b };
	}

	// 以下为课堂题目，在main函数里有对数器
	public static int minRange1(int[][] m) {
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < m[0].length; i++) {
			for (int j = 0; j < m[1].length; j++) {
				for (int k = 0; k < m[2].length; k++) {
					min = Math.min(min,
							Math.abs(m[0][i] - m[1][j]) + Math.abs(m[1][j] - m[2][k]) + Math.abs(m[2][k] - m[0][i]));
				}
			}
		}
		return min;
	}

	public static int minRange2(int[][] matrix) {
		int N = matrix.length;
		TreeSet<Node> set = new TreeSet<>(new NodeComparator());
		for (int i = 0; i < N; i++) {
			set.add(new Node(matrix[i][0], i, 0));
		}
		int min = Integer.MAX_VALUE;
		while (set.size() == N) {//当某一个arr的所有元素都耗尽了 那么 就说明要结束了
			min = Math.min(min, set.last().val - set.first().val);
			Node cur = set.pollFirst();//弹出队首
			if (cur.idx < matrix[cur.arr].length - 1) {//当前弹出的元素 不是所属arr的最后一个元素
				set.add(new Node(matrix[cur.arr][cur.idx + 1], cur.arr, cur.idx + 1));
			}
		}
		return min << 1;
	}

	public static int[][] generateRandomMatrix(int n, int v) {
		int[][] m = new int[3][];
		int s = 0;
		for (int i = 0; i < 3; i++) {
			s = (int) (Math.random() * n) + 1;
			m[i] = new int[s];
			for (int j = 0; j < s; j++) {
				m[i][j] = (int) (Math.random() * v);
			}
			Arrays.sort(m[i]);
		}
		return m;
	}

	public static void main(String[] args) {
		int n = 20;
		int v = 200;
		int t = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < t; i++) {
			int[][] m = generateRandomMatrix(n, v);
			int ans1 = minRange1(m);
			int ans2 = minRange2(m);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}
