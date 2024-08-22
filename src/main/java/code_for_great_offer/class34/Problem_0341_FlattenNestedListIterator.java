package code_for_great_offer.class34;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;
/*
*TODO
* 给你一个嵌套的整数列表 nestedList 。每个元素要么是一个整数，要么是一个列表；该列表的元素也可能是整数或者是其他列表。请你实现一个迭代器将其扁平化，使之能够遍历这个列表中的所有整数。
* 实现扁平迭代器类 NestedIterator ：
* NestedIterator(List<NestedInteger> nestedList) 用嵌套列表 nestedList 初始化迭代器。
* int next() 返回嵌套列表的下一个整数。
* boolean hasNext() 如果仍然存在待迭代的整数，返回 true ；否则，返回 false 。
* 你的代码将会用下述伪代码检测：
* initialize iterator with nestedList
* res = []
* while iterator.hasNext()
*     append iterator.next() to the end of res
* return res
* 如果 res 与预期的扁平化列表匹配，那么你的代码将会被判为正确。
* 示例 1：
* 输入：nestedList = [[1,1],2,[1,1]]
* 输出：[1,1,2,1,1]
* 解释：通过重复调用next 直到hasNext 返回 false，next返回的元素的顺序应该是: [1,1,2,1,1]。
* 示例 2：
* 输入：nestedList = [1,[4,[6]]]
* 输出：[1,4,6]
* 解释：通过重复调用next直到hasNext 返回 false，next返回的元素的顺序应该是: [1,4,6]。
链接：https://leetcode.cn/problems/flatten-nested-list-iterator
* */
public class Problem_0341_FlattenNestedListIterator {
	/*
	*TODO
	* 有一个数组 [[1,[2,3]],[4,5],6,[7,[8,9],10]]
	* 第1个next() 返回1
	* 第2个next() 返回2
	* 第3个next() 返回3
	* ....
	* 如何实现：
	* 有一个栈，用来记录 某一个数的位置
	* 对于上面的大型数组 找到 1这个元素
	* 那么栈存的是 0 0（从栈顶开始往栈底 的顺序）
	* 第1个0意思是[1,[2,3]]是最外层数组里面的第0个元素
	* 第2个0意思是[1]是[1,[2,3]]数组里面的第0个元素
	* 然后调用一个函数 弹出栈顶 找到 [1,[2,3]]
	* 再调用一个函数 弹出栈顶 找到 1
	* 那么如何把2找到呢 因为 1的后面调用next()是2
	* 只要再最后一次弹出栈顶的时候 弹出的值+1 => 0+1=1 就是[1,[2,3]] 的[2,3]索引
	* 根据索引找到 [2,3]，发现这个元素是一个arr，那么就返回0
	* 所以此时栈 就是 0,1,0（从栈顶开始往栈底 的顺序）
	* */
	// 不要提交这个接口类
	public interface NestedInteger {

		// @return true if this NestedInteger holds a single integer, rather than a
		// nested list.
		public boolean isInteger();

		// @return the single integer that this NestedInteger holds, if it holds a
		// single integer
		// Return null if this NestedInteger holds a nested list
		public Integer getInteger();

		// @return the nested list that this NestedInteger holds, if it holds a nested
		// list
		// Return null if this NestedInteger holds a single integer
		public List<NestedInteger> getList();
	}

	public class NestedIterator implements Iterator<Integer> {

		private List<NestedInteger> list;
		private Stack<Integer> stack;
		private boolean used;

		public NestedIterator(List<NestedInteger> nestedList) {
			list = nestedList;
			stack = new Stack<>();
			stack.push(-1);
			used = true;
			hasNext();
		}

		@Override
		public Integer next() {
			Integer ans = null;
			if (!used) {
				ans = get(list, stack);
				used = true;
				//TODO 这里调用的原因是 让栈跳到下一个位置 并且 used设置为false
				hasNext();
			}
			return ans;
		}
		/*
		*TODO
		* 用来准备这个数在哪 但不取这个数
		* */
		@Override
		public boolean hasNext() {
			//栈为空 说明最大的那个数组没有元素可以遍历的了
			if (stack.isEmpty()) {
				return false;
			}
			//TODO 如果仅仅是调用该方法判断是否有下一个数但不是真的要去取 直接返回true
			if (!used) {
				return true;
			}
			//准备好下一个要取的数 并且把将要被取的数 标志为没有使用过
			if (findNext(list, stack)) {
				used = false;
			}
			return !used;
		}

		private Integer get(List<NestedInteger> nestedList, Stack<Integer> stack) {
			int index = stack.pop();
			Integer ans = null;
			if (!stack.isEmpty()) {
				ans = get(nestedList.get(index).getList(), stack);
			} else {
				ans = nestedList.get(index).getInteger();
			}
			stack.push(index);
			return ans;
		}

		private boolean findNext(List<NestedInteger> nestedList, Stack<Integer> stack) {
			int index = stack.pop();
			if (!stack.isEmpty() && findNext(nestedList.get(index).getList(), stack)) {
				stack.push(index);
				return true;
			}
			for (int i = index + 1; i < nestedList.size(); i++) {
				if (pickFirst(nestedList.get(i), i, stack)) {
					return true;
				}
			}
			return false;
		}
		/*
		*TODO
		* 假设栈存的是 a b c d（从栈顶开始往栈底 的顺序）
		* 函数f1：找到 a位置的元素 因为栈里还有元素 调用f2
		* 函数f2：找到 上一轮a位置元素里的b位置 因为栈里还有元素 调用f3
		* 函数f3：找到 上一轮a位置元素里的b位置元素里的c位置 因为栈里还有元素 调用f4
		* 函数f4：找到 上一轮a位置元素里的b位置元素里的c位置元素里的d位置  的 元素x
		* 得到元素x 判断 元素x的索引+1的索引 所对应的位置是否有元素
		* 没有：
		* 	判断a位置元素里的b位置元素里的c位置的下一个位置是否有元素
		* 有：
		* 	把 d+1 放入栈 , c 放入栈 , b 放入栈 , a 放入栈
		*
		* */
		private boolean pickFirst(NestedInteger nested, int position, Stack<Integer> stack) {
			if (nested.isInteger()) {
				stack.add(position);
				return true;
			} else {
				List<NestedInteger> actualList = nested.getList();
				for (int i = 0; i < actualList.size(); i++) {
					if (pickFirst(actualList.get(i), i, stack)) {
						stack.add(position);
						return true;
					}
				}
			}
			return false;
		}

	}

}
