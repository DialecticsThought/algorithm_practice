package leetcode;

import java.util.HashMap;

/**
 * code_for_great_offer.class20
 * 本题为leetcode原题
 * 测试链接：https://leetcode.cn/problems/largest-component-size-by-common-factor/
 * 方法1会超时，但是方法2直接通过/
 *
 * 给一个数 求出该书的所有质数因子
 * 判断这个数是不是质数
 * 给一个数x
 * 判断 x%2!=0 是否成立 如果不成立，不再判断，成立继续下面
 * 判断 x%3!=0 是否成立 如果不成立，不再判断，成立继续下面
 * 判断 x%4!=0 是否成立 如果不成立，不再判断，成立继续下面
 * ....
 * 判断 x%(Math.sqrt(num)向下取整)!=0 是否成立 如果不成立，不再判断，成立继续下面
 * eg：
 * 有一个arr[0]=20  ，Math.sqrt(20)向下取整 = 4
 * 20%2=0  20/2=10 得到2和10这2个因子
 * 20%3!=0
 * 20%4=0 20/5=5 得到4和5这2个因子
 * 此时建立一个因子表
 * <2:0> <4:0> <5:0> <10:0>
 * 有一个arr[1]=15  ，Math.sqrt(15)向下取整 = 3
 * 15%2!=0
 * 15%3=0  15/3=5 得到5和3这2个因子
 * 此时因子表
 * <2:0> <4:0> <5:[0,1]> <10:0>
 *
 * num[4,6,15,35]
 * 初始：parents[0,1,2,3] sizes[1,1,1,1]
 * parents[0]表示num[0]的父亲是nums[0],其他也是同理
 * size[0]表示num[0]的所属集合大小是1
 * 1.求num[0]的因子并放入表
 * <2:0> <4:0>
 * 2.求num[0]的因子并放入表
 * <2,[0,1]> <4,0> <6,1> <3,1>
 * <2,[0,1]>说明2是num[0]和[1]的共同因子
 * 利用unionfind把num[0]和[1]的集合合并
 * 4 ↔ 6 , 15 , 35
 * parents[0,0,2,3]
 * 2.求num[0]的因子并放入表
 * <2,[0,1]> <4,0> <6,1> <3,[1,2]> <5,2> <15,2>
 * 利用unionfind把num[0]和[2]的集合合并
 * 	 4   35
 * 	↙ ↘
 * 6   15
 * parents[0,0,0,3]
 * .....
 */
public class LeetCode_952_LargestComponentSizebyCommonFactor {

	public static int largestComponentSize1(int[] arr) {
		int N = arr.length;
		UnionFind set = new UnionFind(N);
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				/*
				 * 求最大公约数 !=1 表示有路
				 * */
				if (gcd(arr[i], arr[j]) != 1) {
					//把两个数的所在集合合并
					set.union(i, j);
				}
			}
		}
		return set.maxSize();
	}

	public static int largestComponentSize2(int[] arr) {
		int N = arr.length;
		//TODO arr中，N个位置，在并查集初始时，每个位置自己是一个单独的集合
		// 构建并查集，并查集中存储的数据是nums的下标
		UnionFind unionFind = new UnionFind(N);
		//TODO 因子表   key 某个因子   value 哪个位置拥有这个因子
		HashMap<Integer, Integer> fathorsMap = new HashMap<>();
		//TODO  找到num的所有因子，如果这个因子不曾被任何数拥有，就加入因子表，
		// 如果别的数也有这个因子，就说明这两个数是连通的，可以加入到同一个集合中，执行合并操作
		for (int i = 0; i < N; i++) {
			int num = arr[i];
			// 求出根号N， -> limit
			int limit = (int) Math.sqrt(num);//1~根号num
			for (int j = 1; j <= limit; j++) {//j是现在试的因子
				if (num % j == 0) {//TODO 说明j是num的因子
					//TODO  因为题目说公因子大于1才算连通，所以不将1因子加入到因子表
					if (j != 1) {
						if (!fathorsMap.containsKey(j)) {//当前数含有j因子的第一个数
							fathorsMap.put(j, i);
						} else {
							//TODO  合并 j这个因子对应的数的下标 和当前的数的下标
							unionFind.union(fathorsMap.get(j), i);
						}
					}
					//TODO other是num另一个因子
					int other = num / j;//other*j == num
					if (other != 1) {//num含有other的因子
						if (!fathorsMap.containsKey(other)) {
							fathorsMap.put(other, i);
						} else {
							unionFind.union(fathorsMap.get(other), i);
						}
					}
				}
			}
		}
		return unionFind.maxSize();
	}

	// O(1)
	// m,n 要是正数，不能有任何一个等于0
	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static class UnionFind {
		private int[] parents;//TODO 每个下标代表一个数 parents[i]表示arr[i]的父节点是谁
		private int[] sizes;//TODO sizes[i]代表arr[i]所属的集合大小
		private int[] help;

		public UnionFind(int N) {
			parents = new int[N];
			sizes = new int[N];
			help = new int[N];
			//TODO 每个arr[i]都是一个单独的集合
			for (int i = 0; i < N; i++) {
				parents[i] = i;
				sizes[i] = 1;
			}
		}

		//TODO  找到所有独立集合中大小最大的返回集合大小
		public int maxSize() {
			int ans = 0;
			//TODO  有效的代表节点对应的集合大小一定不是0，其他的位置就都是0
			for (int size : sizes) {
				ans = Math.max(ans, size);
			}
			return ans;
		}
		//TODO 找到arr[i]的父节点
		private int find(int i) {
			int hi = 0;
			while (i != parents[i]) {//只要当前i的父亲不是他自己
				help[hi++] = i;
				i = parents[i];
			}
			for (hi--; hi >= 0; hi--) {
				parents[help[hi]] = i;
			}
			return i;
		}

		public void union(int i, int j) {
			int f1 = find(i);//TODO 找到i对应的父节点
			int f2 = find(j);//TODO 找到j对应的父节点
			if (f1 != f2) {
				int big = sizes[f1] >= sizes[f2] ? f1 : f1;
				int small = big == f1 ? f2 : f1;
				parents[small] = big;
				sizes[big] = sizes[f1] + sizes[f2];//TODO 把小的结合合并入大的集合
			}
		}
	}

}
