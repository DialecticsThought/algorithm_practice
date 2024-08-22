package algorithmbasic2020_master.class17;

import java.util.Stack;
/**
*TODO 给你一个栈，请你逆序这个栈,不能申请额外的数据结构只能使用递归函数
* */
public class Code05_ReverseStackUsingRecursive {
	/*
	*TODO
	* 有一个栈 从底到上 3 2 1
	* 执行reverse 栈不为空 执行i = f(stack); 得到 栈底元素i= 3 和一个栈 从底到上 2 1
	* 继续执行 再一次对栈调用reverse 标记为r2
	* 执行r2 栈不为空 执行i = f(stack); 得到 栈底元素 i= 2 和一个栈 1
	* 继续执行 再一次对栈调用reverse 标记为r3
	* 执行r3 栈不为空 执行i = f(stack); 得到 栈底元素 i= 1 和一个栈 空
	* 继续执行 再一次对栈调用reverse 标记为r4
	* 执行r4 栈为空 返回上一级
	* 执行r3 stack.push(i); 此时 i=1 r3结束 返回上一级
	* 执行r2 stack.push(i); 此时 i=2 r2结束 返回上一级
	* 执行r1 stack.push(i); 此时 i=3 r1结束 整个函数结束
	* r1 	| i=3 r2
	* 1.↓		  6.↑
	* r2 	| i=3 r2
	* 2.↓		  5.↑
	* r3 	| i=3 r2
	* 3.↓ 		  4.↑
	* r4 	| 返回	→
	 * */
	public static void reverse(Stack<Integer> stack) {
		if (stack.isEmpty()) {
			return;
		}
		int i = f(stack);
		reverse(stack);
		stack.push(i);
	}

	// 栈底元素移除掉
	// 上面的元素盖下来
	// 返回移除掉的栈底元素
	/**
	*TODO 有一个栈 从底到上 3 2 1
	* 执行f 弹出栈顶1存到result里面 因为栈不为空 再一次对栈调用f 标记为f2
	* 执行f2 弹出栈顶2存到result里面 因为栈不为空 再一次对栈调用f 标记为f3
	* 执行f3 弹出栈顶3存到result里面 栈里面是空的 直接返回上一级 也就是f2
	* 执行f2 得到last =3 执行 stack.push(result); 把2压入栈 此时2为栈底 再返回last到上一级
	* 执行f1 得到last =3 执行 stack.push(result); 把1压入栈 此时栈 从底到上 3 2
	* 执行结束
	* f1 | result = 1, last = f2
	* ↓						↑
	* f2 | result = 2, last = f3
	* ↓						↑
	* f3 | result = 3
	* */
	public static int f(Stack<Integer> stack) {
		int result = stack.pop();
		if (stack.isEmpty()) {
			return result;
		} else {
			int last = f(stack);
			stack.push(result);
			return last;
		}
	}

	public static void main(String[] args) {
		Stack<Integer> test = new Stack<Integer>();
		test.push(1);
		test.push(2);
		test.push(3);
		test.push(4);
		test.push(5);
		reverse(test);
		while (!test.isEmpty()) {
			System.out.println(test.pop());
		}

	}

}
