package code_for_great_offer.class50;

import java.util.Arrays;

public class LeetCode_0587_ErectTheFence {
	/*
	*TODO 凸包
	* Y轴 (这里表示ace一条直线)
	* |   b   d
	* |			   f
	* |		       e     h
	* |       c              i
	* | a                g
	* |----------------------------> X轴
	* 从前往后遍历
	* 一开始a 是倒一
	* 	此时栈从顶到底  a
	* 遍历到b 只有一个向量a => a是倒二 b是倒一
	* 	此时栈从顶到底  b a
	* 遍历到c，查看倒二(a) -> c的向量 是否在倒二(a)->倒一(b)向量的右侧 =>成立 b出去 c成为倒一，a还是倒二
	* 	此时栈从顶到底  c a
	* 遍历到d，查看倒二(a) -> d的向量 是否在倒二(a)->倒一(c)向量的右侧 =>不成立 a成为倒三 d成为倒一，c还是倒二
	* 	此时栈从顶到底 d c a
	* 遍历到e，查看倒二(c) -> e的向量 是否在倒二(c)->倒一(d)向量的右侧 =>成立 d出去 c成为倒一，a还是倒二
	* 		查看倒二(a) -> e的向量 是否在倒二(a)->倒一(c)向量的右侧 =>成立 d出去 e成为倒一，a还是倒二
	* 	此时栈从顶到底 e a
	* 遍历到f，查看倒二(a) -> f的向量 是否在倒二(a)->倒一(e)向量的右侧 =>不成立 a成为倒三e成为倒一，e是倒二
	* 	此时栈从顶到底 f e a
	* 遍历到g，查看倒二(e) -> g的向量 是否在倒二(e)->倒一(f)向量的右侧 =>成立 e出去 g成为倒一，a是倒二
	* 	此时栈从顶到底 g a
	* ......
	* 等遍历到 最后的元素之后 一半的线已经连出来了
	* 再从后往前遍历 另一半的线连出来
	*
	*TODO
	* 用栈来实现
	* 栈顶是倒一 次栈顶是倒二
	* eg: 从顶到底 依次是 e d c b a  倒一是e 倒二是d
	* 当前遍历到x 查看 dx向量 是否 在de 向量的右侧
	* 	1.不成立 直接进
	* 	2.成立 栈顶弹出
	* 		此时递归 倒一是d 倒二是c 继续判断  查看 cx向量 是否 在cd 向量的右侧
	* 			1.成立 ......
	* 			2.不成立 ......
	* 	 递归的终止条件：就是当前的 倒二->x的向量 是否在倒二->倒一向量的右侧
	* 特殊情况：如果 d e x 这3个点一条直线 也算不成立
	* */
	public static int[][] outerTrees(int[][] points) {
		int n = points.length;
		int s = 0;
		int[][] stack = new int[n << 1][];
		//TODO x小的排前面，x一样的，y小的排前面
		Arrays.sort(points, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
		//TODO 从前往后遍历 一半的线连出来
		for (int i = 0; i < n; i++) {
			//TODO  stack[s - 2] 倒数第1  stack[s - 1] 倒数第1 points[i]当前点
			while (s > 1 && cross(stack[s - 2], stack[s - 1], points[i]) > 0) {
				s--;
			}
			stack[s++] = points[i];
		}
		//TODO 从后往前遍历 另一半的线连出来
		for (int i = n - 2; i >= 0; i--) {
			while (s > 1 && cross(stack[s - 2], stack[s - 1], points[i]) > 0) {
				s--;
			}
			stack[s++] = points[i];
		}
		// 去重返回
		Arrays.sort(stack, 0, s, (a, b) -> b[0] == a[0] ? b[1] - a[1] : b[0] - a[0]);
		n = 1;
		for (int i = 1; i < s; i++) {
			// 如 果i点，x和y，与i-1点，x和y都一样
			// i点与i-1点，在同一个位置，此时，i点不保留
			if (stack[i][0] != stack[i - 1][0] || stack[i][1] != stack[i - 1][1]) {
				stack[n++] = stack[i];
			}
		}
		return Arrays.copyOf(stack, n);
	}


	/*
	*TODO
	* 叉乘的使用：
	* 如果有3个点 a b c 组成一个三角形
	* 这个三角形 的边 逆时针 组成3个向量
	* 如果有个点d 在3角形的内部
	* 那么 d 一定是 在这3个向量的左侧
	* 如何判断 "左侧" ? ===> 用叉乘
	* 这个函数 传入 a b d , b c d , c a d 做3次调用
	*TODO
	* 如果是凸多边形 也能要是用这个方式 凹多边形 不能使用这个方式
	* */
	public static int cross(int[] a, int[] b, int[] c) {
		/*
		*TODO
		* 这是代数意义 a × b = (y1*z2-z1y2,z1x2—x1z2,z1y2-y1x2)
		* 几何意义 |a|*|b|*sin（向量a,向量b）
		* 运算结果是一个向量c，这个c 满足 c ⊥ a && c ⊥ b
		 * */
		return (b[1] - a[1]) * (c[0] - b[0]) - (b[0] - a[0]) * (c[1] - b[1]);
	}

	public static void main(String[] args) {
		int[] a = { 4, 4 };
		int[] b = { 1, 1 };
		int[] c = { 1, 5 };
		System.out.println(cross(a, b, c));
	}

}
