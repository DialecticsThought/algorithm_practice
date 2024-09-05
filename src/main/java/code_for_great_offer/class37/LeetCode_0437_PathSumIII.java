package code_for_great_offer.class37;

import java.util.HashMap;
/*
* 给定一个二叉树的根节点 root，和一个整数 targetSum ，
* 求该二叉树里节点值之和等于 targetSum 的 路径 的数目。
* 路径 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）
链接：https://leetcode.cn/problems/path-sum-iii
* 输入：root = [10,5,-3,3,2,null,11,3,-2,null,1], targetSum = 8
* 输出：3
* 解释：和等于 8 的路径有 3 条，如图所示。
* 示例 2：
* 输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
* 输出：3
* 来源：力扣（LeetCode）
* 链接：https://leetcode.cn/problems/path-sum-iii
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
*TODO 有个二叉树
* 1层     10
* 2层   5    -3
* 3层  3  2    11
* 4层 3 -2         这2个节点是第3层3的孩子
* 基础： 要求arr中子数组的和为num的子数组
* 1.先把子数组的每个位置的前缀和求出来 preSum[i]
* 2. n=arr.len-1  找到前缀和是 preSum[n] - num的位置
* 回到这道题  有张表  并且题目要求的累加和=8
* 对于第0层 那么 前缀和=0 把<0,0>记录到表1中  key= preSum  value=层数  还有张表2记录每个前缀和出现的次数
* 来到第1层 对于这个第1层的节点的前缀和是10   从表里找到第1层之前的层数 哪些满足 10-8=2的前缀和
* 	没有找到 说明 没有以10这个节点作为路径中的最初节点（路径中 数的底层是初始节点 从下往上） 不存在sum=8的路径
*   最后把<10,1>记录到表中
* 来到第2层 对于这个第2层的节点5而言 前缀和是15   从表里找到第2层之前的层数 哪些满足 15-8=5的前缀和
* 	没有找到 说明 没有以5这个节点作为路径中的最初节点 不存在sum=8的路径
* 	最后把<15,2>记录到表中
* 来到第3层 对于这个第3层的节点3而言 前缀和是18   从表里找到第3层之前的层数 哪些满足 18-8=10的前缀和
* 	找到 说明 有以3这个节点作为路径中的最初节点 存在sum=8的路径
* 	最后把<15,3>记录到表中
* 来到第4层 对于这个第4层的节点3而言 前缀和是21   从表里找到第4层之前的层数 哪些满足 21-8=13的前缀和
* 	没有找到 说明 没有以3这个节点作为路径中的最初节点 不存在sum=8的路径
* 	最后把<21,4>记录到表中
* 来到第5层 即第4层 的3节点的左孩子 为空 向上返回
* 回到第4层 的3节点
* 来到第5层 即第4层 的3节点的右孩子 为空 向上返回
* 回到第4层 的3节点 此时该节点的左右子节点都遍历过了，在该节点向上返回之前，把表对应的记录<21,4>删除 ☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆
* 回到第3层 的3节点
* 来到第4层 即第3层 的3节点的右孩子 为-2 这个第4层的节点-2而言 前缀和是16 从表里找到第4层之前的层数 哪些满足 16-8=8的前缀和
* 	没有找到 说明 没有以-2这个节点作为路径中的最初节点 不存在sum=8的路径
* 	最后把<16,4>记录到表中
* 来到第5层 即第4层 的-2节点的左孩子 为空 向上返回
* 回到第4层 的-2节点
* 来到第5层 即第4层 的-2节点的右孩子 为空 向上返回
* 回到第4层 的-2节点 此时该节点的左右子节点都遍历过了，在该节点向上返回之前，把表对应的记录<16,4>删除
* */
public class LeetCode_0437_PathSumIII {

	public class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	public static int pathSum(TreeNode root, int sum) {
		HashMap<Long, Integer> preSumMap = new HashMap<>();
		preSumMap.put(0L, 1);
		return process(root, sum, 0, preSumMap);
	}

	// 返回方法数
	// preAll 之前走过的节点 的累加和是多少
	public static int process(TreeNode x, int sum, long preAll, HashMap<Long, Integer> preSumMap) {
		if (x == null) {
			return 0;
		}
		//TODO 从头结点到该节点的路径的和
		long all = preAll + x.val;
		int ans = 0;
		if (preSumMap.containsKey(all - sum)) {
			ans = preSumMap.get(all - sum);
		}
		if (!preSumMap.containsKey(all)) {
			preSumMap.put(all, 1);
		} else {
			preSumMap.put(all, preSumMap.get(all) + 1);
		}
		ans += process(x.left, sum, all, preSumMap);
		ans += process(x.right, sum, all, preSumMap);
		//TODO 当前节点向上返回的时候，当前从头结点到该节点的路径的和的出现次数-1
		if (preSumMap.get(all) == 1) {
			preSumMap.remove(all);
		} else {
			preSumMap.put(all, preSumMap.get(all) - 1);
		}
		return ans;
	}

}
