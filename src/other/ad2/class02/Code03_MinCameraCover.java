package other.ad2.class02;

public class Code03_MinCameraCover {

	public static class Node {
		public int value;
		public Node left;
		public Node right;
	}

	public static int minCameraCover1(Node root) {
		RetrunType1 data = process1(root);
		return (int) Math.min(data.uncovered + 1, Math.min(data.coveredNoCamera, data.coveredHasCamera));
	}
	/*
	 * 潜台词：x是头结点，一定要保证x下方的点都被覆盖
	 * */
	public static class RetrunType1 {
		/*
		* x没有相机 也没有被覆盖 x为头的树至少需要几个相机
		* x被覆盖 但是x没相机 x为头的树至少需要几个相机
		* x有相机且被覆盖，x为头的树至少需要几个相机
		* */
		public long uncovered;
		public long coveredNoCamera;
		public long coveredHasCamera;

		public RetrunType1(long un, long no, long has) {
			uncovered = un;
			coveredNoCamera = no;
			coveredHasCamera = has;
		}
	}
	/*
	* x为头的整棵树 把三种情况都要返回
	* */
	public static RetrunType1 process1(Node root) {
		if (root == null) {//空节点不能放相机 且天然被覆盖 需要0个相机
			return new RetrunType1(Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
		}
		/*
		* 左孩子 3个答案：
		* 	 	左孩子没有被覆盖 左孩子以下的点都被覆盖
		* 		左孩子被覆盖但没相机，左孩子以下的点都被覆盖
		* 		左孩子被覆盖且有相机，左孩子以下的点都被覆盖
		* 右孩子 3个答案：
		* 	 	右孩子没有被覆盖 右孩子以下的点都被覆盖
		* 		右孩子被覆盖但没相机，右孩子以下的点都被覆盖
		* 		右孩子被覆盖且有相机，右孩子以下的点都被覆盖
		*
		*  加工出父节点x的答案
		*  以x开头的整棵树 可以在父节点放相机的话 需要哪些可能性
		*     x没有相机 也没有被覆盖 但是x下方都被覆盖的解 =  左孩子被覆盖但没相机，左孩子以下的点都被覆盖 + 右孩子被覆盖但没相机，右孩子以下的点都被覆盖
		* 	   		1.左右孩子都不能有相机 否则x会被覆盖  不能使用  左孩子被覆盖且有相机，左孩子以下的点都被覆盖 或 右孩子被覆盖且有相机，右孩子以下的点都被覆盖
		* 			2.左右孩子一定要被覆盖 因为潜台词x以下的所有节点都被覆盖
		*			3.但是不能选择  左孩子没有被覆盖 左孩子以下的点都被覆盖  或 右孩子没有被覆盖 右孩子以下的点都被覆盖
		*				因为不符合潜台词
		*
		*
		* 	如何得到 x被覆盖 但是x没相机 但是x下方都被覆盖的解 =  Math.min（左孩子被覆盖有相机的解+右孩子被覆盖有相机的解,
		* 															左孩子被覆盖但没相机的解+右孩子被覆盖有相机的解,
		* 															左孩子被覆盖有相机的解+右孩子被覆盖但没相机的解)
		*
		*   +1的原因是不管左右子树什么样的解 x一定方向机
		*   如何得到 x有相机且被覆盖 x下方都被覆盖的解 = 1+ 左子树三种方案求最小+右子树三种方案求最小
		*
		 * */
		RetrunType1 left = process1(root.left);
		RetrunType1 right = process1(root.right);
		long uncovered = left.coveredNoCamera + right.coveredNoCamera;
		long coveredNoCamera = Math.min(left.coveredHasCamera + right.coveredHasCamera,
				Math.min(left.coveredHasCamera + right.coveredNoCamera, left.coveredNoCamera + right.coveredHasCamera));
		long coveredHasCamera = Math.min(left.uncovered, Math.min(left.coveredNoCamera, left.coveredHasCamera))
				+ Math.min(right.uncovered, Math.min(right.coveredNoCamera, right.coveredHasCamera)) + 1;
		return new RetrunType1(uncovered, coveredNoCamera, coveredHasCamera);
	}

	public static int minCameraCover2(Node root) {
		ReturnType2 data = process2(root);
		return data.cameras + (data.status == Status.UNCOVERED ? 1 : 0);
	}
	/*
	* 以x为头 x下层的所有节点都被覆盖 x自己的状况分3种
	* x如何知道父节点只用到某一种可能性
	* */
	public enum Status {
		UNCOVERED, COVERED_NO_CAMERA, COVERED_HAS_CAMERA
	}
	/*
	* 以x为头的树，潜台词：一定要保证x下方法 所有节点都被覆盖
	* 潜台词：可以确保 上一层用到的信息的时候 只会使用到下一层返回的三种可能性中的一种可能性
	* 返回：是哪一种可能性，相机数量多少  贪心算法
	* */
	public static class ReturnType2 {
		public Status status;
		public int cameras;

		public ReturnType2(Status status, int cameras) {
			this.status = status;
			this.cameras = cameras;
		}
	}

	public static ReturnType2 process2(Node root) {
		if (root == null) {//空的话 一定是覆盖但没相机
			return new ReturnType2(Status.COVERED_NO_CAMERA, 0);
		}
		ReturnType2 left = process2(root.left);
		ReturnType2 right = process2(root.right);
		int cameras = left.cameras + right.cameras;
		/*
		* 如果左孩子/右孩子有一个没覆盖 那么x一定要放相机
		* x返回给上一次父节点一个可能性 就是 x放相机了+x的左右子节点的相机数量
		* */
		if (left.status == Status.UNCOVERED || right.status == Status.UNCOVERED) {
			return new ReturnType2(Status.COVERED_HAS_CAMERA, cameras + 1);
		}
		/*
		* 左右两个节点不存在没被覆盖的情况
		* 左右两个节点有一个是有相机的 那么x返回给上一次父节点一个可能性 就是 x的左右子节点的相机数量  x不用放相机
		* 如果x现在这个情况放相机 只影响一个点 就是父节点  如果x的父节点放相机影响3个点 父节点的另一个孩子 父节点本身 父节点的父节点 x被覆盖了不用算
		* 那么肯定选择让父节点放相机 更优先
		* */
		if (left.status == Status.COVERED_HAS_CAMERA || right.status == Status.COVERED_HAS_CAMERA) {
			return new ReturnType2(Status.COVERED_NO_CAMERA, cameras);
		}
		/*
		* 左右孩子 都被覆盖 但是都没相机
		* 如果x现在这个情况放相机 只影响2个点  就是父节点和x本身
		* 如果x的父节点放相机影响4个点 父节点的另一个孩子 父节点本身 父节点的父节点 x本身
		* 肯定让父节点放相机
		 * */
		return new ReturnType2(Status.UNCOVERED, cameras);
	}

}
