package algorithmbasic2020_master.class10;

import java.util.Stack;

public class Code03_UnRecursiveTraversalBT {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int v) {
			value = v;
		}
	}
	/*
	 * 用非递归的方法 也就是用栈来实现前序遍历 中 左 右
	 * 传入根节点
	 * */
	public static void pre(Node head) {
		System.out.print("pre-order: ");
		if (head != null) {
			//创建一个栈来当做容器
			Stack<Node> stack = new Stack<Node>();
			//第一步 就是传入根节点
			stack.add(head);
			while (!stack.isEmpty()) {//只要栈不为空
				head = stack.pop();//弹出栈顶
				System.out.print(head.value + " ");//打印栈顶
				/*
				 * 因为栈是先进后出 所以前序遍历的中 左 右
				 * 实现过程中是先右再左  根节点还是最先放入 是循环外的操作
				 * */
				//有右子节点 就压入右子节点
				if (head.right != null) {
					stack.push(head.right);
				}
				//有左子节点 就压入做子节点
				if (head.left != null) {
					stack.push(head.left);
				}
			}
		}
		System.out.println();
	}
	/*
	 * 用非递归的方法 也就是用栈来实现前序遍历 左 中  右
	 * 传入根节点
	 * */
	public static void in(Node cur) {
		//判断head是不是空 不为空 还能继续向左边遍历
		System.out.print("in-order: ");
		if (cur != null) {
			//创建一个栈
			Stack<Node> stack = new Stack<Node>();
			//只要栈不为空或者head不为空 就一直循环
			while (!stack.isEmpty() || cur != null) {
				if (cur != null) {//head不为空 还能继续向左边遍历
					stack.push(cur);//压入head
					cur = cur.left;//让head的左子节点称为新的head 向左继续遍历
				} else {//head为空 来到右子树上 重复上面if的操作
					cur = stack.pop();//弹出栈顶
					System.out.print(cur.value + " ");//打印栈顶
					cur = cur.right;//让head的有子节点称为新的head 向左继续遍历
				}
			}
		}
		System.out.println();
	}
	/*
	 * 用非递归的方法 也就是用栈来实现后序遍历 左 右 中
	 * 传入根节点
	 * 利用前序遍历是 中 左 右 栈压入次序是中 右 左 的特性
	 * 在弹出栈顶并打印操作执行完后 压入另一个栈  左后在遍历栈里的元素 实现后序遍历 左 右 中
	 * */
	public static void pos1(Node head) {
		System.out.print("pos-order: ");
		if (head != null) {
			//创建一个栈来当做容器
			Stack<Node> s1 = new Stack<Node>();
			Stack<Node> s2 = new Stack<Node>();
			//第一步 就是传入根节点
			s1.push(head);
			while (!s1.isEmpty()) {//只要栈1不为空
				//弹出栈顶
				head = s1.pop(); // 头 右 左
				s2.push(head);//打印栈顶
				/*
				 * 因为栈是先进后出 所以前序遍历的中 左 右
				 * 实现过程中是先右再左  根节点还是最先放入 是循环外的操作
				 * */
				//有右子节点 就压入右子节点
				if (head.left != null) {
					s1.push(head.left);
				}
				//有左子节点 就压入做子节点
				if (head.right != null) {
					s1.push(head.right);
				}
			}
			// 左 右 头
			while (!s2.isEmpty()) {//只要栈2不为空
				System.out.print(s2.pop().value + " ");
			}
		}
		System.out.println();
	}

	public static void pos2(Node h) {
		System.out.print("pos-order: ");
		if (h != null) {
			Stack<Node> stack = new Stack<Node>();
			stack.push(h);
			Node c = null;
			while (!stack.isEmpty()) {
				c = stack.peek();
				/*
				 * head != c.left && head != c.right说明这里的head指向的节点（也就是上一轮打印并弹出的c）
				 * 不是c的左子节点 并且不是c的右子节点
				 * 说明这个c是刚创建的 c指向的节点的左右子节点都没处理
				 * head != c.right表示如果连右子节点都处理完了的话 那么左子节点一定处理掉了
				 * 所以 这个if分支处理c的左子节点 对应后序遍历的左
				 * */
				if (c.left != null && h != c.left && h != c.right) {//这个分支是左树没处理完的情况下处理左树
					/*
					 * 处理完当前c指向节点的左子节点后 判断
					 * head != c.right说明head指向的节点(也就是上一轮打印并弹出的c)不是c的右子节点
					 * 说明这个c是处理完左子节点 但没有处理右子节点
					 * 所以 这个if分支处理c的右子节点 对应后序遍历的右
					 * */
					stack.push(c.left);
				} else if (c.right != null && h != c.right) {//这个分支是右树没处理完的情况下处理右树
					/*
					 * 处理完当前c指向节点的左子节点后 判断
					 * head != c.right说明head指向的节点(也就是上一轮打印并弹出的c)不是c的右子节点
					 * 说明这个c是处理完左子节点 但没有处理右子节点
					 * 所以 这个if分支处理c的右子节点 对应后序遍历的右
					 * */
					stack.push(c.right);
				} else {//左右树都处理完了 开始处理自己了
					/*
					 * 执行这里说明c的左右子节点都处理完了 解决c本身 并返回
					 * 所以 这个分支处理c 对应后序遍历的中
					 * */
					System.out.print(stack.pop().value + " ");
					/*
					 * 这里说明 head指向的节点 就是这一轮处理完的c 而这一轮处理完的c被弹出了
					 * 因为执行完head = c 这一轮循环结束 下一轮循环时候 会执行 c = stack.peek();
					 * */
					h = c;
				}
			}
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Node head = new Node(1);
		head.left = new Node(2);
		head.right = new Node(3);
		head.left.left = new Node(4);
		head.left.right = new Node(5);
		head.right.left = new Node(6);
		head.right.right = new Node(7);

		pre(head);
		System.out.println("========");
		in(head);
		System.out.println("========");
		pos1(head);
		System.out.println("========");
		pos2(head);
		System.out.println("========");
	}

}
