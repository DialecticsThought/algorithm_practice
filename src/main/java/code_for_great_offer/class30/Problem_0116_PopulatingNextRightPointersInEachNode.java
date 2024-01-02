package code_for_great_offer.class30;
/*
* 给定一个完美二叉树，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下：
* struct Node {
*   int val;
*   Node *left;
*   Node *right;
*   Node *next;
* }
* 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，
* 则将 next 指针设置为 NULL。
* 初始状态下，所有next 指针都被设置为 NULL。
 * 链接：https://leetcode.cn/problems/populating-next-right-pointers-in-each-node
* */
public class Problem_0116_PopulatingNextRightPointersInEachNode {

	// 不要提交这个类
	public static class Node {
		public int val;
		public Node left;
		public Node right;
		public Node next;
	}
	/*
	*TODO
	* eg: 有颗树
	*     1
	*   2   3
	*  4     5
	* 6     7 8
	* 第一层
	* 1.pre = null size =1 队列中有①元素， 弹出 ① ，cur = ①，
	* 	依次放入 ② 和 ③ ，因为pre原先是null ①不能连到pre上， pre = ①
	* 第二层
	* 2.pre = null size =2 队列中有② 和 ③元素， 弹出 ② ，cur = ②
	* 	放入④， 因为pre原先是null ②不能连到pre上， pre = ②
	*   弹出 ③ ，cur = ③，放入⑤ ，pre 连接 ③ ，即 ② -> ③
	* 第三层
	* 3.pre = null size =2 队列中有④ 和 ⑤元素，弹出 ④ ，cur = ④
	*   放入⑥ ， 因为pre原先是null ④不能连到pre上， pre = ④
	*   弹出 ⑤ ，cur = ⑤ ，依次放入⑦ 和 ⑧ ，pre 连接 ④，即 ④ -> ⑤
	* 第四层
	* 4.
	 * */
	public static Node connect(Node root) {
		if (root == null) {
			return root;
		}
		MyQueue queue = new MyQueue();
		queue.offer(root);
		while (!queue.isEmpty()) {
			//TODO 第一个弹出的节点 初始时null
			Node pre = null;
			int size = queue.size;
			for (int i = 0; i < size; i++) {
				//TODO 得到当前节点
				Node cur = queue.poll();
				//TODO 把当前节点的左右孩子放入队列中
				if (cur.left != null) {
					queue.offer(cur.left);
				}
				if (cur.right != null) {
					queue.offer(cur.right);
				}
				//TODO 当前节点挂在上一个节点
				if (pre != null) {
					pre.next = cur;
				}
				//TODO 当前节点就是下一个循环的pre
				pre = cur;
			}
		}
		return root;
	}
	// 双端队列 只需要头指针 尾指针 和大小
	public static class MyQueue {
		public Node head;
		public Node tail;
		public int size;

		public MyQueue() {
			head = null;
			tail = null;
			size = 0;
		}

		public boolean isEmpty() {
			return size == 0;
		}

		//加节点
		public void offer(Node cur) {
			size++;
			if (head == null) {//TODO 说明一个节点还没进来
				head = cur;
				tail = cur;
			} else {//TODO 队列不仅只有一个节点
				tail.next = cur;//TODO 当前节点挂在旧的尾巴上面
				tail = cur;//TODO 当前节点变成新的尾巴
			}
		}
		//从头部弹出
		public Node poll() {
			size--;
			//老的头结点
			Node ans = head;
			//新的头结点
			head = head.next;
			//断开
			ans.next = null;
			return ans;
		}

	}

}
