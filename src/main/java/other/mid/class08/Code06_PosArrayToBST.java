package other.mid.class08;
/**
 * @author zbl
 * @version 1.0
 * @content:题目三
KMP算法扩展题目二
根据后序数组重建搜索二叉树
【题目】
给定一个整型数组arr，已知其中没有重复值，判断arr是否可能是节
点值类型为整型的搜索二叉树后序遍历的结果。
进阶：如果整型数组arr中没有重复值，且已知是一棵搜索二叉树的后
序遍历结果，通过数组arr重构二叉树。
 * @date 2020/2/21 16:35
 */
public class Code06_PosArrayToBST {

	public static boolean isPostArray(int[] arr) {
		if (arr == null || arr.length == 0) {
			return false;
		}
		return isPost(arr, 0, arr.length - 1);
	}

	public static boolean isPost(int[] arr, int start, int end) {
		if (start == end) {
			return true;
		}
		int less = -1;
		int more = end;
		for (int i = start; i < end; i++) {
			if (arr[end] > arr[i]) {
				less = i;
			} else {
				more = more == end ? i : more;
			}
		}
		if (less == -1 || more == end) {
			return isPost(arr, start, end - 1);
		}
		if (less != more - 1) {
			return false;
		}
		return isPost(arr, start, less) && isPost(arr, more, end - 1);
	}

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int value) {
			this.value = value;
		}
	}

	public static Node posArrayToBST(int[] posArr) {
		if (posArr == null) {
			return null;
		}
		return posToBST(posArr, 0, posArr.length - 1);
	}

	public static Node posToBST(int[] posArr, int start, int end) {
		if (start > end) {
			return null;
		}
		Node head = new Node(posArr[end]);
		int less = -1;
		int more = end;
		for (int i = start; i < end; i++) {
			if (posArr[end] > posArr[i]) {
				less = i;
			} else {
				more = more == end ? i : more;
			}
		}
		head.left = posToBST(posArr, start, less);
		head.right = posToBST(posArr, more, end - 1);
		return head;
	}

	// for test -- print tree
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
		int[] arr = { 2, 1, 3, 6, 5, 7, 4 };
		System.out.println(isPost(arr, 0, arr.length - 1));
		printTree(posArrayToBST(arr));

	}

}
