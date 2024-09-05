package code_for_great_offer.class46;

import java.util.HashMap;
import java.util.HashSet;

public class LeetCode_0391_PerfectRectangle {
	/*
	*TODO
	* 第一点
	* 把所有矩阵的 最左 最上 最下 最右的边界记录下来
	* 这4个边界所形成的矩形面积算出来
	* 再算出每个矩形的面积然后求和
	* 看看相不相同
	* 第二点
	* 每个矩形得到出现 都会出现4个点
	* 但是如果所有矩形能严丝合缝地拼在一起形成一个大矩形
	* 那么大矩形的4个点都只出现一次
	* 大矩形内部的所有点会出现偶数次
	* 因为一个矩形和另一个矩形拼在一起 那么矩形1的2个点会和矩形2的2个点重合
	* */
	public static boolean isRectangleCover(int[][] matrix) {
		if (matrix.length == 0 || matrix[0].length == 0) {
			return false;
		}
		int l = Integer.MAX_VALUE;
		int r = Integer.MIN_VALUE;
		int d = Integer.MAX_VALUE;
		int u = Integer.MIN_VALUE;
		HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<>();
		int area = 0;
		/*
		*TODO
		* 遍历所有矩阵
		* 每一个矩形，在map中 加4个点
		* 如果某个点出现过，那么该点的出现次数++
		* */
		for (int[] rect : matrix) {
			add(map, rect[0], rect[1]);
			add(map, rect[0], rect[3]);
			add(map, rect[2], rect[1]);
			add(map, rect[2], rect[3]);
			//TODO 每一个矩形的面积求出来加入到are里面
			area += (rect[2] - rect[0]) * (rect[3] - rect[1]);
			//TODO 每一次遍历新的矩阵，去更新大矩阵的边界
			l = Math.min(rect[0], l);
			d = Math.min(rect[1], d);
			r = Math.max(rect[2], r);
			u = Math.max(rect[3], u);
		}
		return checkPoints(map, l, d, r, u) && area == (r - l) * (u - d);
	}
	/*
	*TODO
	* <1,<5,1>>意思是<1,5>这个点出现了1次
	* */
	public static void add(HashMap<Integer, HashMap<Integer, Integer>> map, int row, int col) {
		if (!map.containsKey(row)) {
			map.put(row, new HashMap<>());
		}
		//TODO 已经存在了 加次数
		map.get(row).put(col, map.get(row).getOrDefault(col, 0) + 1);
	}

	public static boolean checkPoints(HashMap<Integer, HashMap<Integer, Integer>> map, int l, int d, int r, int u) {
		//TODO 如果所有矩形能严丝合缝地拼在一起形成一个大矩形 那么大矩形的4个点都只出现一次
		if (map.get(l).getOrDefault(d, 0) != 1
				|| map.get(l).getOrDefault(u, 0) != 1
				|| map.get(r).getOrDefault(d, 0) != 1
				|| map.get(r).getOrDefault(u, 0) != 1) {
			return false;
		}
		/*
		*TODO
		* 除了上面的所有点之外的点的出现次数必须是偶数次
		* */
		map.get(l).remove(d);
		map.get(l).remove(u);
		map.get(r).remove(d);
		map.get(r).remove(u);
		for (int key : map.keySet()) {
			for (int value : map.get(key).values()) {
				if ((value & 1) != 0) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean isRectangleCover2(int[][] matrix) {
		if (matrix.length == 0 || matrix[0].length == 0) {
			return false;
		}
		int xL = Integer.MAX_VALUE;
		int yD = Integer.MIN_VALUE;
		int xR = Integer.MAX_VALUE;
		int yU = Integer.MIN_VALUE;
		HashSet<String> set = new HashSet<>();
		int area = 0;//每一个矩形累加的面积和
		for (int[] rect : matrix) {
			/*
			 *TODO
			 * 某一个点 eg:(1,3) 用 1_3表示
			 * 一旦有新的小矩形出现 更新 xL xR yD yU
			 * rect[0]左下角点x坐标
			 * rect[1]左下角点y坐标
			 * rect[2]右上角点x坐标
			 * rect[3]右上角点y坐标
			 * */
			xL = Math.min(rect[0], xL);
			xR = Math.min(rect[1], xR);
			yD = Math.max(rect[2], yD);
			yU = Math.max(rect[3], yU);
			area += (rect[2] - rect[0]) * (rect[3] - rect[1]);//所有小矩形的面积累加和
			//每个长方形 一定会有四个点
			String s1=rect[0] + "_" + rect[1];//左下角点坐标
			String s2 = rect[0] + "_" + rect[3]; // 左上角点坐标
			String s3 = rect[2] + "_" + rect[3]; //右上角点坐标
			String s4 = rect[2] + "_" + rect[1]; //右下角点坐标
			//之前加过 返回false
			//之前每加过 返回true
			//标准2：如果出现过奇数次 set里面有这个点 偶数次就没有
			if ( !set.add(s1)) set.remove(s1);
			if ( !set.add(s2)) set.remove(s2);
			if ( !set.add(s3)) set.remove(s3);
			if ( !set.add(s4)) set.remove(s4);
		}
		/*
		 * 一共只有四个点 xL xR yD yU 如果不是返回false
		 * */
		if ( !set.contains(xL + "_" + yD) || !set.contains(xL + "_" + yU)
				|| !set.contains(xR + "_" + yD) || !set.contains(xR + "_" + yU)
				|| set.size() != 4) {
			return false;
		}
		//(最右 - 最左) * (最上 - 最下)
		return area == (xR - xL) * (yU - yD);
	}

	public static boolean test(int[][] matrix){
		if (matrix.length == 0 || matrix[0].length == 0) {
			return false;
		}
		int xL = Integer.MAX_VALUE;
		int yD = Integer.MIN_VALUE;
		int xR = Integer.MAX_VALUE;
		int yU = Integer.MIN_VALUE;
		HashSet<String> set = new HashSet<>();
		int area = 0;//每一个矩形累加的面积和
		for (int[] rect : matrix) {
			/*
			 * 某一个点 eg:(1,3) 用 1_3表示
			 * 一旦有新的小矩形出现 更新 xL xR yD yU
			 * */
			// rect[0]左下角点x坐标
			// rect[1]左下角点y坐标
			// rect[2]右上角点x坐标
			// rect[3]右上角点y坐标
			xL = Math.min(rect[0], xL);
			xR = Math.min(rect[1], xR);
			yD = Math.max(rect[2], yD);
			yU = Math.max(rect[3], yU);
			area += (rect[2] - rect[0]) * (rect[3] - rect[1]);//所有小矩形的面积累加和
			//每个长方形 一定会有四个点
			String s1=rect[0] + "_" + rect[1];//左下角点坐标
			String s2 = rect[0] + "_" + rect[3]; // 左上角点坐标
			String s3 = rect[2] + "_" + rect[3]; //右上角点坐标
			String s4 = rect[2] + "_" + rect[1]; //右下角点坐标
			//之前加过 返回false
			//之前每加过 返回true
			//标准2：如果出现过奇数次 set里面有这个点 偶数次就没有
			if ( !set.add(s1)) set.remove(s1);
			if ( !set.add(s2)) set.remove(s2);
			if ( !set.add(s3)) set.remove(s3);
			if ( !set.add(s4)) set.remove(s4);
		}
		/*
		 * 一共只有四个点 xL xR yD yU 如果不是返回false
		 * */
		if ( !set.contains(xL + "_" + yD) || !set.contains(xL + "_" + yU)
				|| !set.contains(xR + "_" + yD) || !set.contains(xR + "_" + yU)
				|| set.size() != 4) {
			return false;
		}
		//(最右 - 最左) * (最上 - 最下)
		return area == (xR - xL) * (yU - yD);
	}
}
