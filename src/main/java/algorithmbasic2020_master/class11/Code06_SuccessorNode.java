package algorithmbasic2020_master.class11;

public class Code06_SuccessorNode {

	public static class Node {
		public int value;
		public Node left;
		public Node right;
		public Node parent;

		public Node(int data) {
			this.value = data;
		}
	}
	/*
	 * 找到 后继节点 通过中序遍历
	 * */
	public static Node getSuccessorNode(Node node) {
		if (node == null) {//TODO 如果这个节点为空 返回该节点
			return node;
		}
		/*
		 *TODO
		 * 如果该节点有右子节点
		 * 那么后继节点就是 该节点的右子节点的左子树里面的最左边的节点
		 * 如果该节点没右子节点
		 * 找到该节点的父节点
		 * 只要父节点和子节点的关系 不是 父节点和左子节点 那么就一直遍历
		 * 当前父节点变成下一轮遍历的当前节点 当前父节点的父节点变成下一轮便利的父节点
		 * */
		if (node.right != null) {
			return getLeftMost(node.right);
		} else { // 无右子树
			Node parent = node.parent;
			//TODO parent != null防止空指针异常 因为每次遍历 node会指向上一轮parent指向的的节点位置
			while (parent != null && parent.right == node) { //TODO 当前节点是其父亲节点右孩子
				node = parent;//TODO node 上移
				parent = node.parent;//TODO parent 上移
			}
			return parent;
		}
	}

	public static Node getLeftMost(Node node) {
		if (node == null) {
			return node;
		}
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}

	public static void main(String[] args) {
		Node head = new Node(6);
		head.parent = null;
		head.left = new Node(3);
		head.left.parent = head;
		head.left.left = new Node(1);
		head.left.left.parent = head.left;
		head.left.left.right = new Node(2);
		head.left.left.right.parent = head.left.left;
		head.left.right = new Node(4);
		head.left.right.parent = head.left;
		head.left.right.right = new Node(5);
		head.left.right.right.parent = head.left.right;
		head.right = new Node(9);
		head.right.parent = head;
		head.right.left = new Node(8);
		head.right.left.parent = head.right;
		head.right.left.left = new Node(7);
		head.right.left.left.parent = head.right.left;
		head.right.right = new Node(10);
		head.right.right.parent = head.right;

		Node test = head.left.left;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.left.left.right;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.left;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.left.right;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.left.right.right;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.right.left.left;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.right.left;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.right;
		System.out.println(test.value + " next: " + getSuccessorNode(test).value);
		test = head.right.right; // 10's next is null
		System.out.println(test.value + " next: " + getSuccessorNode(test));
	}

}
