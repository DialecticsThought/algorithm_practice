package code_for_great_offer.class10;

// 本题测试链接 : https://leetcode-cn.com/problems/boolean-evaluation-lcci/
public class Code05_BooleanEvaluation {

	public static int countEval0(String express, int desired) {
		if (express == null || express.equals("")) {
			return 0;
		}
		char[] exp = express.toCharArray();
		int N = exp.length;
		Info[][] dp = new Info[N][N];
		Info allInfo = func(exp, 0, exp.length - 1, dp);
		return desired == 1 ? allInfo.t : allInfo.f;
	}

	public static class Info {
		public int t;//为true的方法数
		public int f;//为false  的方法数

		public Info(int tr, int fa) {
			t = tr;
			f = fa;
		}
	}

	/*
	*  限制:
	*  L...R上，一定有奇数个字符
	*  L位置的字符和R位置的字符，非0即1，不能是逻辑符号！
	*TODO： 该函数的作用：返回str[L...R]这一段，为true的方法数，和false的方法数
	* 递归+缓存 = 记忆化搜索
	*/
	public static Info func(char[] str, int L, int R, Info[][] dp) {
		if (dp[L][R] != null) {
			return dp[L][R];
		}
		int t = 0;
		int f = 0;
		if (L == R) {
			/*
			*TODO 只有一个字符的情况下
			* 想要true 那么该字符一定要是1
			* 想要false 那么该字符一定要是0
			* */
			t = str[L] == '1' ? 1 : 0;
			f = str[L] == '0' ? 1 : 0;
		} else { //TODO 不只有一个字符 那么 L..R >=3  因为起码有一个逻辑符号
			/*
			* split枚举的东西 是 每一个种逻辑符号
			* 都去试试最后结合
			* 第一个逻辑运算符一定在 L + 1位置
			* 每次+2的原因是 逻辑运算符 之间隔着一个数字
			* */
			for (int split = L + 1; split < R; split += 2) {
				//TODO 左半部分 得到info
				Info leftInfo = func(str, L, split - 1, dp);
				//TODO 右半部分 得到info
				Info rightInfo = func(str, split + 1, R, dp);
				int a = leftInfo.t;
				int b = leftInfo.f;
				int c = rightInfo.t;
				int d = rightInfo.f;
				//TODO 开始 用 左半部分的结果 split 右半部分的结果 = 最终结果
				switch (str[split]) {
				case '&':
					t += a * c;
					f += b * c + b * d + a * d;
					break;
				case '|':
					t += a * c + a * d + b * c;
					f += b * d;
					break;
				case '^':
					t += a * d + b * c;
					f += a * c + b * d;
					break;
				}
			}

		}
		//在返回答案之前 放入缓存
		dp[L][R] = new Info(t, f);
		return dp[L][R];
	}

	public static int countEval1(String express, int desired) {
		if (express == null || express.equals("")) {
			return 0;
		}
		char[] exp = express.toCharArray();
		return f(exp, desired, 0, exp.length - 1);
	}

	public static int f(char[] str, int desired, int L, int R) {
		if (L == R) {
			if (str[L] == '1') {
				return desired;
			} else {
				return desired ^ 1;
			}
		}
		int res = 0;
		if (desired == 1) {
			for (int i = L + 1; i < R; i += 2) {
				switch (str[i]) {
				case '&':
					res += f(str, 1, L, i - 1) * f(str, 1, i + 1, R);
					break;
				case '|':
					res += f(str, 1, L, i - 1) * f(str, 0, i + 1, R);
					res += f(str, 0, L, i - 1) * f(str, 1, i + 1, R);
					res += f(str, 1, L, i - 1) * f(str, 1, i + 1, R);
					break;
				case '^':
					res += f(str, 1, L, i - 1) * f(str, 0, i + 1, R);
					res += f(str, 0, L, i - 1) * f(str, 1, i + 1, R);
					break;
				}
			}
		} else {
			for (int i = L + 1; i < R; i += 2) {
				switch (str[i]) {
				case '&':
					res += f(str, 0, L, i - 1) * f(str, 1, i + 1, R);
					res += f(str, 1, L, i - 1) * f(str, 0, i + 1, R);
					res += f(str, 0, L, i - 1) * f(str, 0, i + 1, R);
					break;
				case '|':
					res += f(str, 0, L, i - 1) * f(str, 0, i + 1, R);
					break;
				case '^':
					res += f(str, 1, L, i - 1) * f(str, 1, i + 1, R);
					res += f(str, 0, L, i - 1) * f(str, 0, i + 1, R);
					break;
				}
			}
		}
		return res;
	}

	public static int countEval2(String express, int desired) {
		if (express == null || express.equals("")) {
			return 0;
		}
		char[] exp = express.toCharArray();
		int N = exp.length;
		int[][][] dp = new int[2][N][N];
		dp[0][0][0] = exp[0] == '0' ? 1 : 0;
		dp[1][0][0] = dp[0][0][0] ^ 1;
		for (int i = 2; i < exp.length; i += 2) {
			dp[0][i][i] = exp[i] == '1' ? 0 : 1;
			dp[1][i][i] = exp[i] == '0' ? 0 : 1;
			for (int j = i - 2; j >= 0; j -= 2) {
				for (int k = j; k < i; k += 2) {
					if (exp[k + 1] == '&') {
						dp[1][j][i] += dp[1][j][k] * dp[1][k + 2][i];
						dp[0][j][i] += (dp[0][j][k] + dp[1][j][k]) * dp[0][k + 2][i] + dp[0][j][k] * dp[1][k + 2][i];
					} else if (exp[k + 1] == '|') {
						dp[1][j][i] += (dp[0][j][k] + dp[1][j][k]) * dp[1][k + 2][i] + dp[1][j][k] * dp[0][k + 2][i];
						dp[0][j][i] += dp[0][j][k] * dp[0][k + 2][i];
					} else {
						dp[1][j][i] += dp[0][j][k] * dp[1][k + 2][i] + dp[1][j][k] * dp[0][k + 2][i];
						dp[0][j][i] += dp[0][j][k] * dp[0][k + 2][i] + dp[1][j][k] * dp[1][k + 2][i];
					}
				}
			}
		}
		return dp[desired][0][N - 1];
	}

}
