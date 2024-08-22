package code_for_great_offer.class42;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/*
*TODO
* 给定一个不为空的二叉搜索树和一个目标值 target，请在该二叉搜索树中找到最接近目标值 target 的 k 个值。
* 注意：
* 给定的目标值 target 是一个浮点数
* 你可以默认 k 值永远是有效的，即 k ≤ 总结点数
* 题目保证该二叉搜索树中只会存在一种 k 个值集合最接近目标值
* 示例：
* 输入: root = [4,2,5,1,3]，目标值 = 3.714286，且 k = 2
* 输出: [4,3]
*    4
*  2   5
* 1 3
*TODO
* 利用搜索二叉树
* 前驱节点： 中序遍历中 某一个节点的前面一个节点
* 后继节点： 中序遍历中 某一个节点的后面一个节点
* 如何快速找到后继节点：
* 如果某一个节点有右子树 那么右子树的最左节点就是后继节点
* 如果某个节点没有右子树 并且 它是它的父亲的右孩子的话 继续找 ，直到找到它的祖宗是他祖宗的父亲是某刻子树的左子节点
* eg:  5的左孩子是1 1的右孩子是2 2的右孩子是3  3的后继节点是5
*    5
* 1
* 	2
* 	  3
* eg: aim=8 k=5
* 先找到最接近且<=8的节点 假设是x 找到最接近且>=8的节点 假设是y
* 如果x和y是同一个节点 也就是x=y=8
* 那么找x的前驱节点 这个节点就是接近且<=8的节点
* 如果x和y不是同一个节点
* 查看x与8的差距大还是y与8的差距大
* 如果y与8的差距小 那么先收集y 然后找到y的后继节点z
* 查看x与8的差距大还是z与8的差距大
* 如果x与8的差距小 那么先收集x 然后找到x的前驱节点a
* 查看a与8的差距大还是z与8的差距大
 * */
public class Problem_0272_ClosestBinarySearchTreeValueII {

	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int val) {
			this.val = val;
		}
	}

	// 这个解法来自讨论区的回答，最优解实现的很易懂且漂亮
	public static List<Integer> closestKValues(TreeNode root, double target, int k) {
		List<Integer> ret = new LinkedList<>();
		//TODO >=8，最近的节点，而且需要快速找后继的这么一种结构
		Stack<TreeNode> moreTops = new Stack<>();
		//TODO <=8，最近的节点，而且需要快速找前驱的这么一种结构
		Stack<TreeNode> lessTops = new Stack<>();
		getMoreTops(root, target, moreTops);
		getLessTops(root, target, lessTops);
		//TODO 如果>=8的最近节点和<=8的最近节点是同一节点 那么跳一下前驱再找  不同节点的话直接找
		if (!moreTops.isEmpty() && !lessTops.isEmpty() && moreTops.peek().val == lessTops.peek().val) {
			getPredecessor(lessTops);
		}
		while (k-- > 0) {
			if (moreTops.isEmpty()) {//TODO >=8的最近节点已经没了 但是没有收集够 那么从前驱节点找
				ret.add(getPredecessor(lessTops));
			} else if (lessTops.isEmpty()) {//TODO 反之亦然
				ret.add(getSuccessor(moreTops));
			} else {
				//TODO 比较差值
				double diffs = Math.abs((double) moreTops.peek().val - target);
				double diffp = Math.abs((double) lessTops.peek().val - target);
				if (diffs < diffp) {
					ret.add(getSuccessor(moreTops));
				} else {
					ret.add(getPredecessor(lessTops));
				}
			}
		}
		return ret;
	}
	/*
	*TODO
	* 在root为头的树上
	* 找到>=target，且最接近target的节点
	* 并且找的过程中，只要某个节点x往左走了，就把x放入moreTops里
	* eg： 一个搜索二叉树 target=8
	*     10
	*   6    12
	* 4  9
	*   8
	* 从10开始 10>8 加入到栈中 左移
	* 来到6 6<8 向右移动
	* 来到9 9>8 进栈 向左移动
	* 来到8 8=8 进栈 退出
	* 这个栈能快速找到后继 也就是某节点走了右分支
	* 如果某一个节点有右子树 那么右子树的最左节点就是后继节点
	* 如果某个节点没有右子树 并且 它是它的父亲的右孩子的话 继续找 ，直到找到它的祖宗是他祖宗的父亲是某刻子树的左子节点
	* */
	public static void getMoreTops(TreeNode root, double target, Stack<TreeNode> moreTops) {
		while (root != null) {
			if (root.val == target) {
				moreTops.push(root);
				break;
			} else if (root.val > target) {
				moreTops.push(root);
				root = root.left;
			} else {
				root = root.right;
			}
		}
	}

	/*
	*TODO
	* 在root为头的树上
	* 找到<=target，且最接近target的节点
	* 并且找的过程中，只要某个节点x往右走了，就把x放入lessTops里
	* */
	public static void getLessTops(TreeNode root, double target, Stack<TreeNode> lessTops) {
		while (root != null) {
			if (root.val == target) {
				lessTops.push(root);
				break;
			} else if (root.val < target) {
				lessTops.push(root);
				root = root.right;
			} else {
				root = root.left;
			}
		}
	}
	/*
	*TODO
	* 返回moreTops的头部的值
	* 并且调整moreTops : 为了以后能很快的找到返回节点的后继节点
	* */
	public static int getSuccessor(Stack<TreeNode> moreTops) {
		TreeNode cur = moreTops.pop();
		int ret = cur.val;
		/*
		*TODO
		* 为了以后能很快的找到返回节点的后继节点
		* 需要把返回的节点的右子树的左边界都放入栈
		* 本质就是 所有节点的左子节点都找到
		* eg:一颗子树 target=8
		*   12
		* 6
		* 	10
		* 7
		* 	8
		* 	   9
		* 	8.5
		* 栈里从顶到底依次是 8 10 12
		* 8=target 所以弹出
		* 把8的右子树的左分支都放入栈 也就是8.5和9进栈
		* 如果不进栈 那么就错过了8.5 和9 下一次弹出的是10
		* */
		cur = cur.right;
		while (cur != null) {
			moreTops.push(cur);
			cur = cur.left;
		}
		return ret;
	}

	/*
	* 返回lessTops的头部的值
	* 并且调整lessTops : 为了以后能很快的找到返回节点的前驱节点
	* */
	public static int getPredecessor(Stack<TreeNode> lessTops) {
		TreeNode cur = lessTops.pop();
		int ret = cur.val;
		cur = cur.left;
		/*
		*TODO
		* 为了以后能很快的找到返回节点的前驱节点
		* 需要把返回的节点的左子树的右边界都放入栈
		* 本质就是 所有节点的右子节点都找到
		*/
		while (cur != null) {
			lessTops.push(cur);
			cur = cur.right;
		}
		return ret;
	}

}
