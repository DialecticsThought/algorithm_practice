package algorithmbasic2020_master.class12;

import java.util.ArrayList;

public class Code05_MaxSubBSTSize {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static int getBSTSize(Node head) {
		if (head == null) {
			return 0;
		}
		ArrayList<Node> arr = new ArrayList<>();
		in(head, arr);
		for (int i = 1; i < arr.size(); i++) {
			if (arr.get(i).value <= arr.get(i - 1).value) {
				return 0;
			}
		}
		return arr.size();
	}

	public static void in(Node head, ArrayList<Node> arr) {
		if (head == null) {
			return;
		}
		in(head.left, arr);
		arr.add(head);
		in(head.right, arr);
	}

	public static int maxSubBSTSize1(Node head) {
		if (head == null) {
			return 0;
		}
		int h = getBSTSize(head);
		if (h != 0) {
			return h;
		}
		return Math.max(maxSubBSTSize1(head.left), maxSubBSTSize1(head.right));
	}

/*	public static int maxSubBSTSize2(Node head) {
		if (head == null) {
			return 0;
		}
		return process(head).maxSubBSTSize;
	}*/


	// 任何子树
	public static class Info {
		public boolean isAllBST;//TODO 是否整体都是二叉搜索树
		public int maxSubBSTSize;//TODO 最大满足二叉搜索树条件的子树大小
		public int min;//TODO 左子树的最大值
		public int max;//TODO 右子树的最小值

		public Info(boolean is, int size, int mi, int ma) {
			isAllBST = is;
			maxSubBSTSize = size;
			min = mi;
			max = ma;
		}
	}

	/*
	*TODO
	* 判断 是否和根节点有关
	* 有关的话
	* 左子树和右子树都是二叉搜索树
	* 左子树的最大节点 < x
	* 右子树的最小节点 > x
	* 无关的话
	* */
	public static Info process(Node X) {
		if(X == null) {
			return null;
		}
		/*
		* TODO 通过递归的方式获取左右子树的信息（有四个信息）
		* */
		Info leftInfo = process(X.left);
		Info rightInfo = process(X.right);
		/*
		*TODO
		* 先赋值 让x参与整个树 的最大和最小
		* 当前的min和max是之前收集到的
		* */
		int min = X.value;
		int max = X.value;
		/*
		*TODO
		* 只有子节点为空 才会返回控信息
		* 子节点不为空 执行下面操作
		*TODO
		* 加工当前节点的信息
		* 首先是加工得到min和max
		* */
		if(leftInfo != null) {
			min = Math.min(min, leftInfo.min);//TODO 之前收集到的右子树的最小值和当前收集到的信息做比较
			max = Math.max(max, leftInfo.max);//TODO 之前收集到的左子树的最大值和当前收集到的信息做比较
		}
		if(rightInfo != null) {//TODO 同上面的操作
			min = Math.min(min, rightInfo.min);
			max = Math.max(max, rightInfo.max);
		}
		//TODO 设立初始值
		int maxSubBSTSize = 0;
		/*
		*TODO  加工maxSubBSTSize
		 * */
		if(leftInfo != null) {
			//TODO 左子树不为空的话 左子树的maxSubBSTSize先成为当前节点的maxSubBSTSize
			maxSubBSTSize = leftInfo.maxSubBSTSize;
		}
		if(rightInfo !=null) {
			//TODO 右子树不为空的话 左子树的maxSubBSTSize和右子树的maxSubBSTSize比较得到最大值
			maxSubBSTSize = Math.max(maxSubBSTSize, rightInfo.maxSubBSTSize);
		}
		boolean isAllBST = false;
		/*
		*TODO
		* 有关的话 也就是说 最后找到的二叉搜索树是以x为头结点的
		* 1.左子树和右子树都是二叉搜索树 那么以当前节点为头结点的树 可能就是当前最大的二叉搜索树
	    * 2.左子树的最大节点 < x
		* 3.右子树的最小节点 > x
		* 无关的话
		* */
		if(		//TODO  判断空是因为process方法一开始是用到了null后面都要使用到
				//TODO  左树整体需要是搜索二叉树
			   //TODO 左子节点为空 左子树一定是搜索二叉树  要满足条件1
			   //TODO 不为空的话 确保左子树要是搜索二叉树 也就用到了左子树信息的isAllBST 后续递归会去确定的
				(leftInfo == null ? true : leftInfo.isAllBST)
				&&//右子节点为空 右子树一定是搜索二叉树 要满足条件1
				//TODO  不为空的话 确保右子树要是搜索二叉树 也就用到了右子树信息的isAllBST 后续递归会去确定的
				(rightInfo == null ? true : rightInfo.isAllBST)
				&& //判断条件2
				//TODO  左子节点为空 左子树一定是搜索二叉树 不为空 确保x的左子树的最大节点<x
				(leftInfo == null ? true : leftInfo.max < X.value)
				&&//判断条件3
				//TODO  右子节点为空 左子树一定是搜索二叉树 不为空 确保x的右子树的最大节点<x
				(rightInfo == null ? true : rightInfo.min > X.value)
				) {
			/*
			*TODO
			* 如果左树整体是搜索二叉树 右树整体是搜索二叉树 而且与x根节点有关 说明 那么
			* maxSubBSTSize 就是 左子树的大小+右子树的大小+根节点
			* 当然排除空的情况
			* */
			maxSubBSTSize = (leftInfo == null ? 0 : leftInfo.maxSubBSTSize) + (rightInfo == null ? 0 : rightInfo.maxSubBSTSize) + 1;
			isAllBST = true;

		}
		return new Info(isAllBST, maxSubBSTSize, min, max);//TODO 最后的一步 递归到最后 加工自身的信息 并返回上一级
	}

/*	public static int maxSubBSTSize2(Node head) {
		if(head == null) {
			return 0;
		}
		return process(head).maxBSTSubtreeSize;
	}*/

	public static class Info2 {
		public int maxBSTSubtreeSize;//最大搜索二叉子树的大小size
		public int allSize;//该子树是否是搜索二叉树
		public int max;//左子树的最大值
		public int min;//右子树的最小值

		public Info2(int m, int a, int ma, int mi) {
			maxBSTSubtreeSize = m;
			allSize = a;
			max = ma;
			min = mi;
		}
	}

	public static Info2 process2(Node x) {
		if (x == null) {
			return null;
		}
		Info2 leftInfo = process2(x.left);
		Info2 rightInfo = process2(x.right);
		int max = x.value;
		int min = x.value;
		int allSize = 1;
		if (leftInfo != null) {
			max = Math.max(leftInfo.max, max);
			min = Math.min(leftInfo.min, min);
			allSize += leftInfo.allSize;
		}
		if (rightInfo != null) {
			max = Math.max(rightInfo.max, max);
			min = Math.min(rightInfo.min, min);
			allSize += rightInfo.allSize;
		}
		int p1 = -1;
		if (leftInfo != null) {
			p1 = leftInfo.maxBSTSubtreeSize;
		}
		int p2 = -1;
		if (rightInfo != null) {
			p2 = rightInfo.maxBSTSubtreeSize;
		}
		int p3 = -1;
		//TODO 判断 左子树是否是BST 如果是空 就是 如果不是 那么 需要满足 左子树的节点数 = 左子树的最大BST的节点
		boolean leftBST = leftInfo == null ? true : (leftInfo.maxBSTSubtreeSize == leftInfo.allSize);
		//TODO 判断 右子树是否是BST 如果是空 就是 如果不是 那么 需要满足 右子树的节点数 = 右子树的最大BST的节点
		boolean rightBST = rightInfo == null ? true : (rightInfo.maxBSTSubtreeSize == rightInfo.allSize);
		//TODO 判断是否与当前节点x有关
		if (leftBST && rightBST) {
			//TODO 判断 左子树的最大值 是否小于当前节点
			boolean leftMaxLessX = leftInfo == null ? true : (leftInfo.max < x.value);
			//TODO 判断 右子树的最大值 是否大于当前节点
			boolean rightMinMoreX = rightInfo == null ? true : (x.value < rightInfo.min);
			if (leftMaxLessX && rightMinMoreX) {
				//TODO 设置当前节点为根节点的树的大小 如果左子树不是空 直接拿出左子树的节点数
				int leftSize = leftInfo == null ? 0 : leftInfo.allSize;
				//TODO 设置当前节点为根节点的树的大小 如果右子树不是空 直接拿出右子树的节点数
				int rightSize = rightInfo == null ? 0 : rightInfo.allSize;
				p3 = leftSize + rightSize + 1;
			}
		}
		return new Info2(Math.max(p1, Math.max(p2, p3)), allSize, max, min);
	}

	// for test
	public static Node generateRandomBST(int maxLevel, int maxValue) {
		return generate(1, maxLevel, maxValue);
	}

	// for test
	public static Node generate(int level, int maxLevel, int maxValue) {
		if (level > maxLevel || Math.random() < 0.5) {
			return null;
		}
		Node head = new Node((int) (Math.random() * maxValue));
		head.left = generate(level + 1, maxLevel, maxValue);
		head.right = generate(level + 1, maxLevel, maxValue);
		return head;
	}

	public static void main(String[] args) {
		int maxLevel = 4;
		int maxValue = 100;
		int testTimes = 1000000;
/*		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
			if (maxSubBSTSize1(head) != maxSubBSTSize2(head)) {
				System.out.println("Oops!");
			}
		}*/
		System.out.println("finish!");
	}

}
