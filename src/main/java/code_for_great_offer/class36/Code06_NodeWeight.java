package code_for_great_offer.class36;

import java.util.HashMap;
/*
*TODO
* 来自美团
* 有一棵树，给定头节点h，和结构数组m，下标0弃而不用
* 比如h = 1, m = [ [] , [2,3], [4], [5,6], [], [], []]
* 表示1的孩子是2、3; 2的孩子是4; 3的孩子是5、6; 4、5和6是叶节点，都不再有孩子
* 每一个节点都有颜色，记录在c数组里，比如c[i] = 4, 表示节点i的颜色为4
* 一开始只有叶节点是有权值的，记录在w数组里，
* 比如，如果一开始就有w[i] = 3, 表示节点i是叶节点、且权值是3
* 现在规定非叶节点i的权值计算方式：
* 根据i的所有直接孩子来计算，假设i的所有直接孩子，颜色只有a,b,k
* w[i] = Max {
*              (颜色为a的所有孩子个数 + 颜色为a的孩子权值之和),
*              (颜色为b的所有孩子个数 + 颜色为b的孩子权值之和),
*              (颜色为k的所有孩子个数 + 颜色k的孩子权值之和)
*            }
* 请计算所有孩子的权值并返回
*TODO
* 有一个节点x x本身是什么颜色不重要
* 他有这些孩子
* 节点10 颜色是2
* 节点7 颜色是1
* 节点3 颜色是1
* 节点4 颜色是3
* 节点6 颜色是2
* 节点9 颜色是1
* 那么 颜色是1个数 + 颜色是1的孩子的权值 = 3 + 19 √
* 那么 颜色是2个数 + 颜色是2的孩子的权值 = 2 + 16
* 那么 颜色是5个数 + 颜色是5的孩子的权值 = 1 + 4
*TODO 求法
* 对于节点x
* 1.分别对其孩子调用函数 求出 孩子的权值
* 2.然后算出自己的权值
* 3.返回
*/
public class Code06_NodeWeight {
	/*
	*TODO
	* 当前来到h节点，
	* h的直接孩子，在哪呢？m[h] = {a,b,c,d,e}
	* 每个节点的颜色在哪？比如i号节点，c[i]就是i号节点的颜色
	* 每个节点的权值在哪？比如i号节点，w[i]就是i号节点的权值
	* void : 把w数组填满就是这个函数的目标
	*/
	public static void w(int h, int[][] m, int[] w, int[] c) {
		if (m[h].length == 0) { // 叶节点 题目高收益我们了
			return;
		}
		/*
		* 有若干个直接孩子 key是颜色 value是对应的孩子的数量
		*/
		HashMap<Integer, Integer> colors = new HashMap<Integer, Integer>();
		/*
		* 有若干个直接孩子 key是颜色 value是对应颜色的孩子的权值的和
		 */
		HashMap<Integer, Integer> weight = new HashMap<Integer, Integer>();
		for (int child : m[h]) {
			//TODO 当前节点的孩子 调用该方法 更新 2张表
			w(child, m, w, c);
			//TODO 更新当前节点的2张表
			colors.put(c[child], colors.getOrDefault(c[child], 0) + 1);
			weight.put(c[child], weight.getOrDefault(c[child], 0) + w[child]);
		}
		for (int color : colors.keySet()) {
			w[h] = Math.max(w[h], colors.get(color) + weight.get(color));
		}
	}

}
