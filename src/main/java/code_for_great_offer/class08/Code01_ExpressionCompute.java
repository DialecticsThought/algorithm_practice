package code_for_great_offer.class08;

import java.util.LinkedList;

// 本题测试链接 : https://leetcode.com/problems/basic-calculator-iii/
public class Code01_ExpressionCompute {

	public static int calculate(String str) {
		return f(str.toCharArray(), 0)[0];
	}

	// 请从str[i...]往下算，遇到字符串终止位置或者右括号，就停止
	// 返回两个值，长度为2的数组
	// 0) 负责的这一段的结果是多少
	// 1) 负责的这一段计算到了哪个位置
	public static int[] f(char[] str, int i) {
		LinkedList<String> que = new LinkedList<String>();
		int cur = 0;
		int[] bra = null;
		// 从i出发，开始撸串
		while (i < str.length && str[i] != ')') {
			if (str[i] >= '0' && str[i] <= '9') {
				//收集数字的阶段 eg： 1位置 是1 cur=1 2位置是2 cur=1*10+2=12
				cur = cur * 10 + str[i++] - '0';
			} else if (str[i] != '(') { // 遇到的是运算符号
				addNum(que, cur);//把收集到的值cur 放入容器
				que.addLast(String.valueOf(str[i++]));//把符号 放入容器
				cur = 0;//cur清空
			} else { // 遇到左括号了
				bra = f(str, i + 1);//递归子过程
				cur = bra[0];//第一个返回值 就是当前函数的cur
				i = bra[1] + 1;//子过程算到哪 如果算到i位置 当前函数从i+1位置继续
			}
		}
		addNum(que, cur);//最后一个数 被收集时执行的方法
		//计算出整个值
		return new int[] { getNum(que), i };
	}
	/*
	*TODO
	* 把收集到的值 放入容器
	* 这个方法 要判断如果栈顶 是乘 或 除 那么 收集到的数进栈之前 先要 把栈顶和次栈顶 弹出
	*  次栈顶 栈顶 收集到的数num = 得到新的数  再让新的数进栈
	* 如果是 加  或 减
	 * */
	public static void addNum(LinkedList<String> que, int num) {
		if (!que.isEmpty()) {
			int cur = 0;
			String top = que.pollLast();
			if (top.equals("+") || top.equals("-")) {
				que.addLast(top);
			} else {
				cur = Integer.valueOf(que.pollLast());
				num = top.equals("*") ? (cur * num) : (cur / num);
			}
		}
		que.addLast(String.valueOf(num));
	}

	public static int getNum(LinkedList<String> que) {
		int res = 0;
		boolean add = true;
		String cur = null;
		int num = 0;
		while (!que.isEmpty()) {
			cur = que.pollFirst();
			if (cur.equals("+")) {
				add = true;
			} else if (cur.equals("-")) {
				add = false;
			} else {
				num = Integer.valueOf(cur);
				res += add ? num : (-num);
			}
		}
		return res;
	}

}
