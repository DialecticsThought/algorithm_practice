package code_for_great_offer.class30;

import java.util.ArrayList;
import java.util.List;

public class LeetCode_0119_PascalTriangleII {
	/*
	* 给定一个非负索引 rowIndex，返回「杨辉三角」的第 rowIndex 行。
	* 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
	* 输入: rowIndex = 3
	* 输出: [1,3,3,1]
	* 输入: rowIndex = 0
	* 输出: [1]
	* eg:
	* [1]
	* [1,1]
	* [1,2,1]
	* [1,3,3,1]
	* [1,4,6,4,1]
	* 第一行 1个空间
	* 第二行 2个空间
	* 第n行 n个空间
	*TODO
	* 如果求 第n行 先来n个空间
	* 然后先求第1行 自己更新成 第2行  自己更新成 第3行 ... 自己更新成 第n行
	*TODO
	* 对于每一行 从右往左填写
	* 假设求第5行 说明已经求出了第4行
	* [1,3,3,1]
	* 第5行的最后一个元素一定是1 那么就是
	* [_,_,_,_,1]
	* 对于倒数第2个元素 就是 左上角蒜素+上方的元素
	* [_,_,_,6,1]
	* 对于倒数第3个元素 就是 左上角蒜素+上方的元素
	* [_,_,_,6,1]
	* */
	public List<Integer> getRow(int rowIndex) {
		List<Integer> ans = new ArrayList<>();
		for (int i = 0; i <= rowIndex; i++) {
			for (int j = i - 1; j > 0; j--) {
				ans.set(j, ans.get(j - 1) + ans.get(j));
			}
			ans.add(1);
		}
		return ans;
	}

}
