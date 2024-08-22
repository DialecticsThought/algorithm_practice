package code_for_great_offer.class28;

/*
* 20. 有效的括号
给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
有效字符串需满足：
左括号必须用相同类型的右括号闭合。
左括号必须以正确的顺序闭合。
每个右括号都有一个对应的相同类型的左括号。
示例 1：
输入：s = "()"
输出：true
示例 2：
输入：s = "()[]{}"
输出：true
示例 3：
输入：s = "(]"
输出：false
* */
public class Problem_0020_ValidParentheses {
	/*
	* 准备一个栈
	* 遇到 "{" "(" "[" 就压入栈
	* 否则就弹出
	* 弹出的和当前遇到的是配对的
	* 如果不是配对的就是 有问题的
	* 如果整个char遍历完之后 栈 必须是空
	*TODO 优化的点
	* 当 "(" 准备进栈的时候 实际是直接让")"进栈
	* 这样的在后边遍历的时候 遇到")" 这届判断是否与栈顶相同
	* */
	public static boolean isValid(String s) {
		if (s == null || s.length() == 0) {
			return true;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		//用数组 来代替stack结构
		char[] stack = new char[N];
		int size = 0;
		for (int i = 0; i < N; i++) {
			char cha = str[i];
			if (cha == '(' || cha == '[' || cha == '{') {
				stack[size++] = cha == '(' ? ')' : (cha == '[' ? ']' : '}');
			} else {
				if (size == 0) {
					return false;
				}
				char last = stack[--size];
				if (cha != last) {
					return false;
				}
			}
		}
		return size == 0;
	}

}
