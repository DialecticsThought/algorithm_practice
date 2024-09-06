package code_for_great_offer.class30;





import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

// follow up : 如果要求返回整个路径怎么做？
public class LeetCode_0124_BinaryTreeMaximumPathSum {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		public TreeNode(int v) {
			val = v;
		}

	}

	public static int maxPathSum(TreeNode root) {
		if (root == null) {
			return 0;
		}
		return process(root).maxPathSum;
	}

	// 任何一棵树，必须汇报上来的信息
	public static class Info {
		//TODO 1.以x节点为头的子树的最大路径和 不含x  或者整棵树的最大路径和
		public int maxPathSum;
		//TODO 2.以x节点为头的子树的最大路径和（路径中一定有x）
		public int maxPathSumFromHead;

		public Info(int path, int head) {
			maxPathSum = path;
			maxPathSumFromHead = head;
		}
	}
	/*
	*TODO
	* 1.路径不包含x
	* 	1.1 x的左子树的最大路径和
	*   1.2 x的右子树的最大路径和
	* 	1.3再求max
	* 2.路径包含x
	*   2.1.只有x一个节点
	* 	2.2.x + x的左孩子为头的子树的最大路径和（路径中包含x的左孩子）
	* 	2.3.x + x的右孩子为头的子树的最大路径和（路径中包含x的右孩子）
	* 	2.4.x + x的右孩子为头的子树的最大路径和 + x的左孩子为头的子树的最大路径和（路径中包含x的右左孩子）
	* */
	public static Info process(TreeNode x) {
		if (x == null) {
			return null;
		}
		Info leftInfo = process(x.left);
		Info rightInfo = process(x.right);
		// x 1)只有x 2）x往左扎 3）x往右扎
		int maxPathSumFromHead = x.val;//保留当前节点自己  和x有关的时候 情况二
		if (leftInfo != null) {
			maxPathSumFromHead = Math.max(maxPathSumFromHead, x.val + leftInfo.maxPathSumFromHead);
		}
		if (rightInfo != null) {
			maxPathSumFromHead = Math.max(maxPathSumFromHead, x.val + rightInfo.maxPathSumFromHead);
		}
		/*
		* x整棵树最大路径和
		* 1) 只有x
		* 2) 左子树整体的最大路径和
		* 3) 右子树整体的最大路径和
		* */
		int maxPathSum = x.val;
		if (leftInfo != null) {//有左子树
			maxPathSum = Math.max(maxPathSum, leftInfo.maxPathSum);//左子树的整棵树的最大路径和和当前变量比较取最大
		}
		if (rightInfo != null) {//有右子树
			maxPathSum = Math.max(maxPathSum, rightInfo.maxPathSum);//右子树的整棵树的最大路径和和当前变量比较取最大
		}
		// 4) x只往左扎 5）x只往右扎
		maxPathSum = Math.max(maxPathSumFromHead, maxPathSum);
		// 6）一起扎
		if (leftInfo != null && rightInfo != null && leftInfo.maxPathSumFromHead > 0
				&& rightInfo.maxPathSumFromHead > 0) {
			maxPathSum = Math.max(maxPathSum, leftInfo.maxPathSumFromHead + rightInfo.maxPathSumFromHead + x.val);
		}
		return new Info(maxPathSum, maxPathSumFromHead);
	}

	public static Info process1(TreeNode x){
		if (x == null) {
			return null;
		}
		//向左和右子树分别要信息
		Info leftData = process1(x.left);
		Info rightData = process1(x.right);
		//与x无关的时候
		int maxPathSumP1 = Integer.MIN_VALUE;
		if(leftData!=null){//有左子树
			//左子树的整棵树的最大路径和和当前变量比较取最大
			maxPathSumP1 = Math.max(maxPathSumP1,leftData.maxPathSum);
		}
		if(rightData!=null){//有右子树
			//右子树的整棵树的最大路径和和当前变量比较取最大
			maxPathSumP1 = Math.max(maxPathSumP1,rightData.maxPathSum);
		}
		// 和x有关的时候 情况二
		int maxPathSumP2=x.val;//保留当前节点自己
		// x决定往左走获得的最好情况
		int maxPathSumP3 = Integer.MIN_VALUE;
		if(leftData !=null){//左子节点不为空
			//整个树的最大路径和就是 当前头节点的值+左子树从左子树的头结点出发的情况下的最大路径和
			maxPathSumP3 = x.val+ leftData.maxPathSumFromHead;
		}else {
			maxPathSumP3  = Integer.MIN_VALUE;
		}
		// x决定往右走获得的最好情况
		int maxPathSumP4 = Integer.MIN_VALUE;
		if(leftData !=null){//右子节点不为空
			//整个树的最大路径和就是 当前头节点的值+右子树从右子树的头结点出发的情况下的最大路径和
			maxPathSumP4 = x.val+ rightData.maxPathSumFromHead;
		}else {
			maxPathSumP4 = Integer.MIN_VALUE;
		}
		//求出4中情况的最大值
		int maxPathSum = Math.max(Math.max(maxPathSumP1,maxPathSumP2),Math.max(maxPathSumP3,maxPathSumP4));

		int headMaxPathSum=Math.max(maxPathSumP2,Math.max(maxPathSumP3,maxPathSumP4));

		return new Info(maxPathSum,headMaxPathSum);
	}

	/*
	 *TODO
	 * 如果要返回路径的做法
	 * 先得到最大路径和
	 * 然后得到最大路径和的开始节点和结束节点
	 * 找到开始节点和结束节点的最低公共祖先
	 * 开始节点和结束节点和最低公共祖先编程一条路径
	 * */
	public static List<TreeNode> getMaxSumPath(TreeNode head) {
		List<TreeNode> ans = new ArrayList<>();
		if (head != null) {
			Data data = f(head);
			HashMap<TreeNode, TreeNode> fmap = new HashMap<>();
			fmap.put(head, head);
			fatherMap(head, fmap);
			fillPath(fmap, data.from, data.to, ans);
		}
		return ans;
	}

	public static class Data {
		public int maxAllSum;//当前节点为头的子树的最大路径和
		public TreeNode from;//最大路径的开始节点
		public TreeNode to;//最大路径的结束节点
		public int maxHeadSum;//当前节点为头的子树的最大路径和（一定包含当前节点x）
		public TreeNode end;//当前扎到了哪

		public Data(int a, TreeNode b, TreeNode c, int d, TreeNode e) {
			maxAllSum = a;
			from = b;
			to = c;
			maxHeadSum = d;
			end = e;
		}
	}

	public static Data f(TreeNode x) {
		if (x == null) {
			return null;
		}
		Data l = f(x.left);//向左子树要信息
		Data r = f(x.right);//向右子树要信息
		//从头结点出发的最大路径和 就只是x的话
		int maxHeadSum = x.val;
		//当前扎到了x
		TreeNode end = x;
		/*
		* l != null 说明左子树不为空
		* l.maxHeadSum > 0 左子树为头的子树的最大路径和（一定包含左子节点x） > 0
		* r == null 说明右子树为空
		* l.maxHeadSum > r.maxHeadSum左子树为头的子树的最大路径和（一定包含左子节点x）> 右子树为头的子树的最大路径和（一定包含右子节点x）
		* => x向左扎 => 扎到了 左子树的最深处
		* 反之  x向右扎 => 扎到了 右子树的最深处
		* */
		if (l != null && l.maxHeadSum > 0 && (r == null || l.maxHeadSum > r.maxHeadSum)) {
			maxHeadSum += l.maxHeadSum;
			end = l.end;
		}
		if (r != null && r.maxHeadSum > 0 && (l == null || r.maxHeadSum > l.maxHeadSum)) {
			maxHeadSum += r.maxHeadSum;
			end = r.end;
		}
		// x 向左 也向右 扎
		int maxAllSum = Integer.MIN_VALUE;
		TreeNode from = null;
		TreeNode to = null;
		if (l != null) {
			maxAllSum = l.maxAllSum;
			from = l.from;
			to = l.to;
		}
		if (r != null && r.maxAllSum > maxAllSum) {
			maxAllSum = r.maxAllSum;
			from = r.from;
			to = r.to;
		}
		/*
		* TODO 重点
		* */
		int p3 = x.val + (l != null && l.maxHeadSum > 0 ? l.maxHeadSum : 0)
				+ (r != null && r.maxHeadSum > 0 ? r.maxHeadSum : 0);
		if (p3 > maxAllSum) {
			maxAllSum = p3;
			from = (l != null && l.maxHeadSum > 0) ? l.end : x;
			to = (r != null && r.maxHeadSum > 0) ? r.end : x;
		}
		return new Data(maxAllSum, from, to, maxHeadSum, end);
	}

	public static void fatherMap(TreeNode h, HashMap<TreeNode, TreeNode> map) {
		if (h.left == null && h.right == null) {
			return;
		}
		if (h.left != null) {
			map.put(h.left, h);
			fatherMap(h.left, map);
		}
		if (h.right != null) {
			map.put(h.right, h);
			fatherMap(h.right, map);
		}
	}

	public static void fillPath(HashMap<TreeNode, TreeNode> fmap, TreeNode a, TreeNode b, List<TreeNode> ans) {
		if (a == b) {
			ans.add(a);
		} else {
			HashSet<TreeNode> ap = new HashSet<>();
			TreeNode cur = a;
			while (cur != fmap.get(cur)) {
				ap.add(cur);
				cur = fmap.get(cur);
			}
			ap.add(cur);
			cur = b;
			TreeNode lca = null;
			while (lca == null) {
				if (ap.contains(cur)) {
					lca = cur;
				} else {
					cur = fmap.get(cur);
				}
			}
			while (a != lca) {
				ans.add(a);
				a = fmap.get(a);
			}
			ans.add(lca);
			ArrayList<TreeNode> right = new ArrayList<>();
			while (b != lca) {
				right.add(b);
				b = fmap.get(b);
			}
			for (int i = right.size() - 1; i >= 0; i--) {
				ans.add(right.get(i));
			}
		}
	}

	public static void main(String[] args) {
		TreeNode head = new TreeNode(4);
		head.left = new TreeNode(-7);
		head.right = new TreeNode(-5);
		head.left.left = new TreeNode(9);
		head.left.right = new TreeNode(9);
		head.right.left = new TreeNode(4);
		head.right.right = new TreeNode(3);

		List<TreeNode> maxPath = getMaxSumPath(head);

		for (TreeNode n : maxPath) {
			System.out.println(n.val);
		}
	}

}
