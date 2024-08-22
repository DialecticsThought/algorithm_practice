package code_for_great_offer.class36;

import java.util.TreeSet;

/*
*TODO
* 来自腾讯
* 给定一个字符串str，和一个正数k
* 返回长度为k的所有子序列中，字典序最大的子序列
* b c d c a b c..... k=5 单调栈
* 先把0位置的b放入栈
* 来到1位置的c
* 规则：只要栈顶的元素字典序 < 当前遍历的字符 就弹出 直到 栈顶的元素字典序 >= 当前遍历的字符
* b弹出 c进入
* 来到2位置的d c弹出 d进入
* 来到3位置的c c进入
* 来到4位置的a a进入
* 来到5位置的b a弹出 b进入
* 来到6位置的c c进入
* ...
* 有两种情况：
* 1.能遍历到结尾 且 栈的元素数量 >=k 那么前k个元素就是答案
* 2.栈里有元素a个 当前遍历到的元素机器后面所有额的元素长度是b 如果a-1+b <  k
* eg： z a y f c d     k=5
* 当来到 2位置的y时 a=2 b=4
* 满足 a -1 + b < k 那么 z a y f c d就是答案
* eg2: z b k d c f c f
* 来到5位置的f的时候
* 栈里的元素有 c d k z k=5
* 此时 c<f 弹出 d<f 弹出 还剩下栈的2个元素 + 剩下的元素3 = 长度5
* 那么答案 也生成了 z k f c f
* 因为 栈的元素 从栈顶到栈底 是 小 到 大排列
* */
public class Code09_MaxKLenSequence {

	public static String maxString(String s, int k) {
		if (k <= 0 || s.length() < k) {
			return "";
		}
		char[] str = s.toCharArray();
		int n = str.length;
		char[] stack = new char[n];
		int size = 0;
		for (int i = 0; i < n; i++) {
			//栈底有东西 栈顶元素 比当前元素小 弹出一个后a-1+b > k
			while (size > 0 && stack[size - 1] < str[i] && size + n - i > k) {
				size--;
			}
			if (size + n - i == k) {
				return String.valueOf(stack, 0, size) + s.substring(i);
			}
			stack[size] = str[i];
			size++;
		}
		//拿 栈里的前k个
		return String.valueOf(stack, 0, k);
	}

	// 为了测试
	public static String test(String str, int k) {
		if (k <= 0 || str.length() < k) {
			return "";
		}
		TreeSet<String> ans = new TreeSet<>();
		process(0, 0, str.toCharArray(), new char[k], ans);
		return ans.last();
	}

	// 为了测试
	public static void process(int si, int pi, char[] str, char[] path, TreeSet<String> ans) {
		if (si == str.length) {
			if (pi == path.length) {
				ans.add(String.valueOf(path));
			}
		} else {
			process(si + 1, pi, str, path, ans);
			if (pi < path.length) {
				path[pi] = str[si];
				process(si + 1, pi + 1, str, path, ans);
			}
		}
	}

	// 为了测试
	public static String randomString(int len, int range) {
		char[] str = new char[len];
		for (int i = 0; i < len; i++) {
			str[i] = (char) ((int) (Math.random() * range) + 'a');
		}
		return String.valueOf(str);
	}

	public static void main(String[] args) {
		int n = 12;
		int r = 5;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * (n + 1));
			String str = randomString(len, r);
			int k = (int) (Math.random() * (str.length() + 1));
			String ans1 = maxString(str, k);
			String ans2 = test(str, k);
			if (!ans1.equals(ans2)) {
				System.out.println("出错了！");
				System.out.println(str);
				System.out.println(k);
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("测试结束");
	}

}
