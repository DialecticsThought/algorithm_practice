package code_for_great_offer.class35;

import java.util.HashMap;
/*
* 给你四个整数数组 nums1、nums2、nums3 和 nums4 ，数组长度都是 n ，请你计算有多少个元组 (i, j, k, l) 能满足：
0 <= i, j, k, l < n
nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0
示例 1：
输入：nums1 = [1,2], nums2 = [-2,-1], nums3 = [-1,2], nums4 = [0,2]
输出：2
解释：
两个元组如下：
1. (0, 0, 0, 1) -> nums1[0] + nums2[0] + nums3[0] + nums4[1] = 1 + (-2) + (-1) + 2 = 0
2. (1, 1, 0, 0) -> nums1[1] + nums2[1] + nums3[0] + nums4[0] = 2 + (-1) + (-1) + 0 = 0
示例 2：
输入：nums1 = [0], nums2 = [0], nums3 = [0], nums4 = [0]
输出：1
来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/4sum-ii
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
* */
public class Problem_0454_4SumII {
	/*
	*TODO
	* 对于 A数组 和B数组
	* 先两两组合形成n平方  把组合相加的结果存到map1
	* eg:[1,3,2] [0,1,-1]
	* key是相加的结果 value是 下标的位置
	* 1:[0,0]
	* 2:[0,1] [2,0]
	* 0:[0,2]
	* 3:[1,0]
	* ....
	* 对于 C数组 和D数组
	* 先两两组合形成n平方  把组合相加的结果存到map2
	* 接下来 对于map1
	* 遍历key 没找到一个key 就去找map2有没有key是map1的key的负数
	* */
	public static int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
		HashMap<Integer, Integer> map = new HashMap<>();
		int sum = 0;
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < B.length; j++) {
				sum = A[i] + B[j];
				if (!map.containsKey(sum)) {
					map.put(sum, 1);
				} else {
					map.put(sum, map.get(sum) + 1);
				}
			}
		}
		int ans = 0;
		for (int i = 0; i < C.length; i++) {
			for (int j = 0; j < D.length; j++) {
				sum = C[i] + D[j];
				if (map.containsKey(-sum)) {
					ans += map.get(-sum);
				}
			}
		}
		return ans;
	}

}
