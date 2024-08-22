package algorithmbasic2020_master.class11;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Code02_SerializeAndReconstructTree {
    /*
     * 二叉树可以通过先序、后序或者按层遍历的方式序列化和反序列化，
     * 以下代码全部实现了。
     * 但是，二叉树无法通过中序遍历的方式实现序列化和反序列化
     * 因为不同的两棵树，可能得到同样的中序序列，即便补了空位置也可能一样。
     * 比如如下两棵树
     *         __2
     *        /
     *       1
     *       和
     *       1__
     *          \
     *           2
     * 补足空位置的中序遍历结果都是{ null, 1, null, 2, null}
     *
     * */
	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}


	/*
	 * 先序遍历的序列化
	 * */
	public static Queue<String> preSerial(Node head) {
		Queue<String> ans = new LinkedList<>();
		pres(head, ans);
		return ans;
	}
	//TODO 先序遍历的序列化
	public static void pres(Node head, Queue<String> ans) {
		//TODO head为空的时候 队列加入null 一开始head指向的节点就是根节点
		if (head == null) {
			ans.add(null);
		} else {//TODO head指向的节点不为空 则把序列化后的数据放入队列
			ans.add(String.valueOf(head.value));
			pres(head.left, ans);//TODO 让head 指向当前节点的左子节点 并递归执行
			pres(head.right, ans);//TODO 让head 指向当前节点的右子节点 并递归执行
		}
	}

	/*
	 * 中序遍历的序列化
	 * */
	public static Queue<String> inSerial(Node head) {
		Queue<String> ans = new LinkedList<>();
		ins(head, ans);
		return ans;
	}

	public static void ins(Node head, Queue<String> ans) {
		if (head == null) {//head为空的时候 队列加入null 一开始head指向的节点就是根节点
			ans.add(null);
		} else {
			ins(head.left, ans);//让head 指向当前节点的左子节点 并递归执行
			ans.add(String.valueOf(head.value));//head指向的节点不为空 则把序列化后的数据放入队列
			ins(head.right, ans);//让head 指向当前节点的右子节点 并递归执行
		}
	}

	public static Queue<String> posSerial(Node head) {
		Queue<String> ans = new LinkedList<>();
		poss(head, ans);
		return ans;
	}

	public static void poss(Node head, Queue<String> ans) {
		if (head == null) {
			ans.add(null);
		} else {
			poss(head.left, ans);
			poss(head.right, ans);
			ans.add(String.valueOf(head.value));
		}
	}
	/*
	* 先序遍历的反序列化
	* */
	public static Node buildByPreQueue(Queue<String> prelist) {
		if (prelist == null || prelist.size() == 0) {
			return null;
		}
		return preb(prelist);
	}
	//TODO 先序的反序列化
	public static Node preb(Queue<String> prelist) {
		String value = prelist.poll();//从队列弹出队首
		/*
		 * TODO
		 *  如果这队首是null 说明这个节点空节点
		 *  如果该空节点是某一个节点的左子节点 说明 该节点没有左子树 转而穿件该节点的柚右子节点
		 *  如果该空节点是某一个节点的右子节点 说明 该节点没有右子树 又因为是先序遍历（中 左 右） 说明 该节点的左右子树都创建完毕
		 * */
		if (value == null) {
			return null;
		}
		/*
		* 先序序列化 是 头 左 右 那么反序列化也要按照这种顺序
		* */
		Node head = new Node(Integer.valueOf(value));//执行完该行代码 此时已经消费了一个
		//TODO 开始依次建立左子树和右子树
		head.left = preb(prelist);//TODO 因为先序遍历 反序列化 处理完中 后 处理左子节点
		head.right = preb(prelist);//TODO 因为先序遍历 反序列化 处理完左子节点 后 处理右子节点
		return head;
	}
	/*
	 * 先序遍历的反序列化 把队列里的数据 按照先序遍历 变成一个树
	 * */
	public static Node buildByPosQueue(Queue<String> poslist) {
		if (poslist == null || poslist.size() == 0) {
			return null;
		}
		// 左右中  ->  stack(中右左)
		Stack<String> stack = new Stack<>();
		while (!poslist.isEmpty()) {
			stack.push(poslist.poll());
		}
		return posb(stack);
	}

	public static Node posb(Stack<String> posstack) {
		String value = posstack.pop();
		if (value == null) {
			return null;
		}
		Node head = new Node(Integer.valueOf(value));
		head.right = posb(posstack);
		head.left = posb(posstack);
		return head;
	}

	/*
	 * TODO  按层遍历并序列化
	 * */
	public static Queue<String> levelSerial(Node head) {
		Queue<String> ans = new LinkedList<>();
		if (head == null) {
			ans.add(null);
		} else {
			//TODO 序列化的时机放在加入队列的时候
			ans.add(String.valueOf(head.value));
			Queue<Node> queue = new LinkedList<Node>();
			queue.add(head);
			while (!queue.isEmpty()) {
				head = queue.poll(); //TODO head 每个父被弹出 找到对应的子并序列化
				if (head.left != null) {
					/*
					 * TODO
					 *  左子节点不为空 即序列化也加入队列queue
					 *  左子节点为空 只序列化但不加入队列queue
					 * */
					ans.add(String.valueOf(head.left.value));
					queue.add(head.left);
				} else {
					ans.add(null);
				}
				if (head.right != null) {
					/*
					 * TODO
					 *  右子节点不为空 即序列化也加入队列
					 *  右子节点为空 只序列化但不加入队列
					 * */
					ans.add(String.valueOf(head.right.value));
					queue.add(head.right);
				} else {
					ans.add(null);
				}
			}
		}
		return ans;
	}
	/*
	 * TODO
	 *  按层遍历反序列化
	 *  不管是不是空节点都是要建立节点的
	 *  但是只有不是空节点的节点才会加入到队列
	 * */
	public static Node buildByLevelQueue(Queue<String> levelList) {
		if (levelList == null || levelList.size() == 0) {
			return null;
		}
		//TODO 一开始 从序列化的队列中 弹出队首 并生成相应节点 该节点是根节点
		Node head = generateNode(levelList.poll());
		Queue<Node> queue = new LinkedList<Node>();//创建一个新的队列
		if (head != null) {// TODO 如果头结点不为空
			queue.add(head);//TODO 把根节点放入该队列
		}
		Node node = null;//TODO 先定一个指针node
		while (!queue.isEmpty()) {//TODO 如果队列不为空的话
			node = queue.poll();//TODO 弹出队首 让node指向该节点
			node.left = generateNode(levelList.poll());//TODO 调用方法生辰该节点的左子节点
			node.right = generateNode(levelList.poll());//TODO 调用方法生辰该节点的右子节点
			/*
			 *TODO
			 *  不同于序列化
			 * 序列化的队列是不管有没有值都要放入的 没有值 放入null 有值 放入值
			 * 但是队列是 只有值的放入该队列
			 * */
			if (node.left != null) {//如果节点不为空 加入到队列
				queue.add(node.left);
			}
			if (node.right != null) {//如果节点不为空 加入到队列
				queue.add(node.right);
			}
		}
		return head;
	}
	/*
	 *TODO
	 * 因为 按层遍历序列化的过程中 有值的把值放入队列 没有值的放入null
	 * 所以要分 null 和 值的情况
	 * 创建一个节点
	 * 如果val=null 返回null
	 * 如果val有值 就创建一个node
	 * */
	public static Node generateNode(String val) {
		if (val == null) {
			return null;
		}
		return new Node(Integer.valueOf(val));
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

	// for test
	public static boolean isSameValueStructure(Node head1, Node head2) {
		if (head1 == null && head2 != null) {
			return false;
		}
		if (head1 != null && head2 == null) {
			return false;
		}
		if (head1 == null && head2 == null) {
			return true;
		}
		if (head1.value != head2.value) {
			return false;
		}
		return isSameValueStructure(head1.left, head2.left) && isSameValueStructure(head1.right, head2.right);
	}

	// for test
	public static void printTree(Node head) {
		System.out.println("Binary Tree:");
		printInOrder(head, 0, "H", 17);
		System.out.println();
	}

	public static void printInOrder(Node head, int height, String to, int len) {
		if (head == null) {
			return;
		}
		printInOrder(head.right, height + 1, "v", len);
		String val = to + head.value + to;
		int lenM = val.length();
		int lenL = (len - lenM) / 2;
		int lenR = len - lenM - lenL;
		val = getSpace(lenL) + val + getSpace(lenR);
		System.out.println(getSpace(height * len) + val);
		printInOrder(head.left, height + 1, "^", len);
	}

	public static String getSpace(int num) {
		String space = " ";
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < num; i++) {
			buf.append(space);
		}
		return buf.toString();
	}

	public static void main(String[] args) {
		int maxLevel = 5;
		int maxValue = 100;
		int testTimes = 1000000;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
			Queue<String> pre = preSerial(head);
			Queue<String> pos = posSerial(head);
			Queue<String> level = levelSerial(head);
			Node preBuild = buildByPreQueue(pre);
			Node posBuild = buildByPosQueue(pos);
			Node levelBuild = buildByLevelQueue(level);
			if (!isSameValueStructure(preBuild, posBuild) || !isSameValueStructure(posBuild, levelBuild)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish!");

	}
}
