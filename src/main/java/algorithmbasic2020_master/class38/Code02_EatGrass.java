package algorithmbasic2020_master.class38;




/*
* 给定一个正整数N,表示有N份青草统一堆放在仓库里有一只牛和一只羊，牛先吃，羊后吃，
* 它俩轮流吃草不管是牛还是羊，每一轮能吃的草量必须是:
* 1，4，16，64…(4的某次方)
* 谁最先把草吃完，谁获胜
* 假设牛和羊都绝顶聪明，都想赢，都会做出理性的决定根据唯一的参数N,返回谁会赢
* */
public class Code02_EatGrass {

	// 如果n份草，最终先手赢，返回"先手"
	// 如果n份草，最终后手赢，返回"后手"
	public static String whoWin(int n) {
		if (n < 5) {
			return n == 0 || n == 2 ? "后手" : "先手";
		}
		// 进到这个过程里来，当前的先手，先选
		int want = 1;
		while (want <= n) {
			if (whoWin(n - want).equals("后手")) {
				return "先手";
			}
			if (want <= (n / 4)) {
				want *= 4;
			} else {
				break;
			}
		}
		return "后手";
	}

	public static String winner1(int n) {//谁调用该函数 谁就是先手
		// 0  1 2  3  4
		// 后 先 后 先 先
		if (n < 5) {//base case 也就是 < 5的时候 一定发生的情况
			return (n == 0 || n == 2) ? "后手" : "先手";
		}
		// n >= 5的时候
		int base = 1;//先手决定吃的草数量
		//当前是先手 在 选择 吃多少
		while (base <= n) {
			/*
			* 当前一共有n份草 先手吃掉的是 base份，n-base 是留给后手的
			* */
			if (winner1(n - base).equals("后手")) {
				/*
				  先手吃掉 base份
				  子函数就吃掉n-base份 并且如果胜利者是后手（也就是子函数执行完返回后手）
				  就返回先手

				  因为主过程（最外层调用winner1）是先手的话
				  在子函数里面 先手变成了后手   代表的是同一个人
				  意思就是 如果先手在后面调用winner1(n - base)能赢的话 现在已经赢了
				  如果没有赢的话  base *= 4; 再判断
				  不断地 base *= 4; 发现还是没有赢的话 就是后手赢
				*/
				return "先手";
			}
			if (base > n / 4) { // 防止base*4之后溢出
				/*
				* 因为 正整数有最大范围 防止 不断base *= 4 之后 base 超出了最大范围 就变成了负数
				* */
				break;
			}
			base *= 4;
		}
		return "后手";
	}

	public static String winner2(int n) {
		/*
		* 因为不管怎么样 都是 后 先 后 先 先 的顺序  然后再一次  后 先 后 先 先
		*  0  1 2  3  4 5  6  7 8  9
		*  后 先 后 先 先 后 先 后 先 先 ......
		* 找到规律
		* */
		if (n % 5 == 0 || n % 5 == 2) {
			return "后手";
		} else {
			return "先手";
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i <= 50; i++) {
			System.out.println(i + " : " + whoWin(i));
		}
	}

	public void test01(){
		for (int i = 0; i <= 10; i++) {
			System.out.println(i + " : " + winner1(i));
		}
	}
}
