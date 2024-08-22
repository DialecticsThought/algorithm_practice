package algorithmbasic2020_master.class11;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Code05_TreeMaxWidth {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}
	/*
	 * 计算出一棵树的最大宽度
	 * */
	public static int maxWidthUseMap(Node head) {
		if (head == null) {
			return 0;
		}
		//创建一个队列
		Queue<Node> queue = new LinkedList<>();
		queue.add(head);//先把根节点放入队列中
		// key 在 哪一层，value
		HashMap<Node, Integer> levelMap = new HashMap<>();
		levelMap.put(head, 1);//根节点 在第一层
		int curLevel = 1; // 当前你正在统计哪一层的宽度
		int curLevelNodes = 0; // 当前层curLevel层，宽度目前是多少
		int max = 0;//到目前为止 更新到的宽度最大值
		while (!queue.isEmpty()) {//循环终止条件是 队里为空
			Node cur = queue.poll();//弹出队列中第一个节点
			int curNodeLevel = levelMap.get(cur);//根据弹出的节点找到对应的层数
			//打印该节点
			System.out.println(cur);
			//判断该节点是否有左右子节点 有的话 放入队列中
			if (cur.left != null) {
				//curNodeLevel + 1是因为记录子节点的层数
				levelMap.put(cur.left, curNodeLevel + 1);
				queue.add(cur.left);
			}
			//判断该节点是否有右子节点 有的话 放入队列中
			if (cur.right != null) {
				//curNodeLevel + 1是因为记录子节点的层数
				levelMap.put(cur.right, curNodeLevel + 1);
				queue.add(cur.right);
			}
			//如果当前节点所在的层 和当前要统计的层 一样 说明这一层没有结束
			if (curNodeLevel == curLevel) {
				//当前层的节点数+1
				curLevelNodes++;
			} else {//如果当前节点所在的层 和当前要统计的层 不一样
				// 说明了当前节点是最下面的一层的第一个 新的一层开始了 还要做到上一层为止的统计
				//上一层层数的节点总量和max比较 得到到目前为止 更新到的宽度最大值
				max = Math.max(max, curLevelNodes);
				curLevel++;//当前层数+1 要和最新节点的层数相匹配
				curLevelNodes = 1; // 因为上一层节点统计完毕 这个变量是为了当前层的节点数量做准备 所以设置当前层的节点数量为1
			}
		}
		max = Math.max(max, curLevelNodes);
		return max;
	}

	/*
	 * 计算出一棵树的最大宽度
	 * */
	public static int maxWidthWithoutMap(Node head) {
		if (head == null) {
			return 0;
		}
		//创建一个队列
		Queue<Node> queue = new LinkedList<>();
		//先把根节点放入队列中
		queue.add(head);
		Node curEnd = head;//当前层 最后一个节点是谁 也就是最右边的节点是谁
		Node nextEnd = null;//如果由下一层的话 下一层的最右节点是谁
		int curLevelNodes = 0;//当前curLevel层 宽度目前是多少 节点弹出来的时候 做统计
		int max = 0;//到目前为止 更新到的宽度最大值
		while (!queue.isEmpty()) {//循环终止条件是 队里为空
			//弹出队列中第一个节点
			Node cur = queue.poll();
			//打印该节点
			System.out.println(cur);
			//判断该节点是否有左右子节点 有的话 放入队列中
			if (cur.left != null) {
				queue.add(cur.left);
				/*
				 * 当前层 在随时更新下一层的最右节点
				 * 只要当前层出来一个节点 下一层的最右节点会更新
				 * 此时出来的是当前节点的左子节点 就把这个节点当做目前的下一层的最右节点
				 * */
				nextEnd = cur.left;
			}
			//判断该节点是否有右子节点 有的话 放入队列中
			if (cur.right != null) {
				queue.add(cur.right);
				/*
				 * 当前层 在随时更新下一层的最右节点
				 * 只要当前层出来一个节点 下一层的最右节点会更新
				 * 此时出来的是当前节点的右子节点 就把这个节点当做目前的下一层的最右节点
				 * */
				nextEnd = cur.right;
			}
			curLevelNodes++;
			/*
			 * 判断当前节点是不是当前层的最右节点
			 * 是的话 就结算
			 * 不同于使用Map的方法 要到最新层的第一个 才结算 之前所有层数的max
			 * */
			if (cur == curEnd) {
				max = Math.max(max, curLevelNodes);
				/*
				 * 不是1 的原因 是目前的节点是当前层的最后一个
				 * 而不是像使用Map的方法中 当前的节点是下一层的第一个
				 * */
				curLevelNodes = 0;
				/*
				 * 进入下一层之前 下一层的最右就是下一轮循环当前层的最右
				 * eg: 当前层是2 下一层是3 下一轮循环 当前层是3 下一层是4
				 * */
				curEnd = nextEnd;
			}
		}
		max = Math.max(max, curLevelNodes);
		return max;
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
		int maxLevel = 10;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
			if (maxWidthUseMap(head) != maxWidthWithoutMap(head)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");

	}

}
