package code_for_great_offer.class33;

/*
* 请设计并实现一个能够展开二维向量的迭代器。该迭代器需要支持 next和hasNext两种操作。,
* 示例:
* Vector2D iterator = new Vector2D([[1,2],[3],[4]]);
* iterator.next();//返回1
* iterator.next();//返回2
* iterator.next();//返回 3
* iterator.hasNext();//返回true
* iterator.hasNext();//返回true
* iterator.next();//返回4
* iterator.hasNext();//返回false
* */
public class Problem_0251_Flatten2DVector {

	public static class Vector2D {
		private int[][] matrix;
		//TODO 迭代器停在的位置 就用row col 表示
		private int row;
		private int col;
		//TODO 光标来到的位置的数 也就是 (row，col)的数字是否被使用过
		private boolean curUse;

		public Vector2D(int[][] v) {
			matrix = v;
			/*
			*TODO
			* row=0 col=-1表示光标没有进入数组
			* */
			row = 0;
			col = -1;
			//TODO 默认光标来到0行-1列 已经使用过了
			curUse = true;
			//TODO 光标来到下一个位置
			hasNext();
		}
		/*
		*TODO
		* next()方法 是 返回 (row，col)的数字
		*  curUse表示(row，col)的数字是否被使用过
		* */
		public int next() {
			int ans = matrix[row][col];
			curUse = true;
			hasNext();
			return ans;
		}
		/*
		*TODO
		* 1.如果使用了next()方法 把(row，col)数返回 那么就curUse = true 表示使用过当前位置的数
		* 并且next()会执行hashNext()方法 该方法会判断(row，col)下一个位置的（没有被使用过的）数是否存在 并返回
		* 2.没有调用next() 直接调用hashNext()，那么(row，col)数还没有被用过 那么curUse = false
		* 只要当前数没有用过 那么调用hashNext 返回true 并且 curUse = false
		* 当前数没有用过 那么一定有下一个
		* */
		public boolean hasNext() {
			//TODO 光标来到最后一行的下一行
			if (row == matrix.length) {
				return false;
			}
			//TODO 说明这个数没有用过 就用这个数 并且 说明还有下一个数
			//TODO 很重要！！！！用来平衡 hashNext()和next()方法
			if (!curUse) {
				return true;
			}
			/*
			*TODO
			* 光标 从左到右 走 直到 这一行 遍历结束
			* 光标才到下一行
			* */
			//TODO 当前位置(row，col)的数用过了 去下一个位置
			if (col < matrix[row].length - 1) {
				col++;
			} else {
				//来到第一列
				col = 0;
				/*
				*TODO
				* [3,4,1,2]
				* [0,0,0,0]
				* [0,0,0,0]
				* [3,2,1,0]
				* 从第一行遍历结束
				* 做do while语句 因为第二行 第三行满足matrix[row].length == 0
				* 所以row++
				* */
				do {
					row++;
				} while (row < matrix.length && matrix[row].length == 0);
			}
			/*
			*TODO
			* 来到新的位置(row，col)
			* 只要不是最后一行的下一行
			* 那么来到的位置的数 没有被使用过 curUse =false
			* 返回true 表示有下一个
			* */
			if (row != matrix.length) {
				curUse = false;
				return true;
			} else {
				return false;
			}
		}

	}

}
