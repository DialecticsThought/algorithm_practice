package code_for_great_offer.class30;

import java.util.ArrayList;
import java.util.List;

public class LeetCode_0118_PascalTriangle {

	/*
	* 给定一个非负整数 numRows，生成「杨辉三角」的前 numRows 行。
	* 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
	* eg:
	* numRows = 5
	* [1]
	* [1,1]
	* [1,2,1]
	* [1,3,3,1]
	* [1,4,6,4,1]
	* 每个元素就是上面上面的元素和左上角的元素
	* 如果某个元素 上面没有元素 就是 1
	* */
	public static List<List<Integer>> generate(int numRows) {
		List<List<Integer>> ans = new ArrayList<>();
		for (int i = 0; i < numRows; i++) {
			//每一层初始化一个list
			ans.add(new ArrayList<>());
			//每一层的第一个元素是1
			ans.get(i).add(1);
		}
		for (int i = 1; i < numRows; i++) {
			for (int j = 1; j < i; j++) {
				// 每个元素就是上面上面的元素和左上角的元素
				ans.get(i).add(ans.get(i - 1).get(j - 1) + ans.get(i - 1).get(j));
			}
			//i位置就是1
			ans.get(i).add(1);
		}
		return ans;
	}

}
