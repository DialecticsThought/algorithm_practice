package code_for_great_offer.class44;

/*
* 给定两个字符串 low 和 high 表示两个整数 low 和 high ，其中 low <= high ，返回 范围 [low, high] 内的 「中心对称数」总数 。
* 中心对称数 是一个数字在旋转了 180 度之后看起来依旧相同的数字（或者上下颠倒地看）。
* 示例 1:
* 输入: low = “50”, high = “100”
* 输出: 3
* 示例 2:
* 输入: low = “0”, high = “0”
* 输出: 1
* 提示:
* 1 <= low.length, high.length <= 15
* low 和 high 只包含数字
* low <= high
* low and high 不包含任何前导零，除了零本身。
*
* 16-> 91 不是中心对称数
* 181-> 181 是中心对称数
* 10->01 不是中心对称数
* eg: 0~100有几个中心对称数 0 1 8 11 88 ....
* 数位dp
* */
public class Problem_0248_StrobogrammaticNumberIII {
	/*
	*TODO
	* eg: 264~783 low high位数相同
	* 先求出783~999有几个 再求出264~999有几个 作差，再判断783是不是 不是+0 是+1
	* eg: 264~7864531  low high位数不相同
	* 263~999有几个
	* 1000~9999有几个
	* 10000~99999有几个
	* 100000~999999有几个
	* 1000000~7864531有几个
	* */
	public static int strobogrammaticInRange(String l, String h) {
		char[] low = l.toCharArray();
		char[] high = h.toCharArray();
		if (!equalMore(low, high)) {
			return 0;
		}
		int lowLen = low.length;
		int highLen = high.length;
		if (lowLen == highLen) {
			int up1 = up(low, 0, false, 1);
			int up2 = up(high, 0, false, 1);
			return up1 - up2 + (valid(high) ? 1 : 0);
		}
		int ans = 0;
		/*
		*TODO
		* eg:
		* 一个数是269 另一个数是7864531
		* lowLen = 3 hightLen = 7  一个是3位数 一个是7位数
		* 中间 有4 5 6
		* */
		for (int i = lowLen + 1; i < highLen; i++) {
			//TODO all函数是告诉有几位数
			ans += all(i);
		}
		//TODO 求出264~999
		ans += up(low, 0, false, 1);
		//TODO 求出1000001~7864531
		ans += down(high, 0, false, 1);
		return ans;
	}

	public static boolean equalMore(char[] low, char[] cur) {
		if (low.length != cur.length) {
			return low.length < cur.length;
		}
		for (int i = 0; i < low.length; i++) {
			if (low[i] != cur[i]) {
				return low[i] < cur[i];
			}
		}
		return true;
	}

	public static boolean valid(char[] str) {
		int L = 0;
		int R = str.length - 1;
		while (L <= R) {
			boolean t = L != R;
			if (convert(str[L++], t) != str[R--]) {
				return false;
			}
		}
		return true;
	}

	// left想得到cha字符，right配合应该做什么决定，
	// 如果left怎么也得不到cha字符，返回-1；如果能得到，返回right配合应做什么决定
	// 比如，left!=right，即不是同一个位置
	// left想得到0，那么就right就需要是0
	// left想得到1，那么就right就需要是1
	// left想得到6，那么就right就需要是9
	// left想得到8，那么就right就需要是8
	// left想得到9，那么就right就需要是6
	// 除此了这些之外，left不能得到别的了。
	// 比如，left==right，即是同一个位置
	// left想得到0，那么就right就需要是0
	// left想得到1，那么就right就需要是1
	// left想得到8，那么就right就需要是8
	// 除此了这些之外，left不能得到别的了，比如：
	// left想得到6，那么就right就需要是9，而left和right是一个位置啊，怎么可能即6又9，返回-1
	// left想得到9，那么就right就需要是6，而left和right是一个位置啊，怎么可能即9又6，返回-1
	public static int convert(char cha, boolean diff) {
		switch (cha) {
		case '0':
			return '0';
		case '1':
			return '1';
		case '6':
			return diff ? '9' : -1;
		case '8':
			return '8';
		case '9':
			return diff ? '6' : -1;
		default:
			return -1;
		}
	}


	/*
	*TODO
	* eg:
	* low = 2323423 下标是0~6范围
	* 注意！！！：0位置的元素一定是和6位置的元素一起考虑的 left和right是分别的下标
	* 1位置的元素一定是和5位置的元素一起考虑的 left和right是分别的下标
	* 2位置的元素一定是和4位置的元素一起考虑的 left和right是分别的下标
	* left和right此时同事指向4位置
	* 但是right其实没有必要定义 因为可以算出来
	* low  被分为了 [左边已经做完决定了 left ..... right 右边已经做完决定了]
	* 左边已经做完决定的部分，如果大于low的原始，leftMore = true;
	* 左边已经做完决定的部分，如果不大于low的原始，那一定是相等，不可能小于，leftMore = false;
	* eg:
	* low 原始=12345678 要求 12345678~99999999之间有几个数
	* 决定原始low的最高位和最低位分别是1和1，查看是否超过了99999999，没有，中间剩余的位数继续做决定，
	* 	只要中间的数继续做决定之后使得low大于原始的low就是有效，但是当前left 也就是做的决定是1和原始low的最高位相等
	* 决定原始low的最高位和最低位分别是6和9，查看是否超过了99999999，没有，但是当前的假设 比原始的low要大
	* 也就是 62345679 > 12345678 那么就是leftMore的意义： 左边做过的决定是否使得当前low大于原始low
	* 只能大于或者等于 不能小于
	* 因为 eg: low = 6327453 要求 6327453~9999999 那么不能决定原始low的最高位和最低位都是1
	* 因为一旦做出了这个决定 中间的位数怎么变都不会在6327453~9999999范围
	*TODO
	* 右边已经做完决定的部分，如果小于low的原始，rightLessEqualMore = 0;
	* 右边已经做完决定的部分，如果等于low的原始，rightLessEqualMore = 1;
	* 右边已经做完决定的部分，如果大于low的原始，rightLessEqualMore = 2;
	* rightLessEqualMore < = >
	*                    0 1 2
	* 返回 ：没做决定的部分，随意变，几个有效的情况？返回！
	*TODO
	* eg: low=3458726
	* 当前决定最高位和最低位分别是6和9，左边：6比原始3大，右边：9比原始6大
	* 当做完这个决定往下调用递归的时候 rs=2 表示 右边：9比原始6大
	* 当前决定最高位和最低位分别是9和6，左边：9比原始3大，右边：6和原始6相同
	* 当做完这个决定往下调用递归的时候 rs=1 表示 右边：和原始6相同
	* eg: low=12345
	* 当前决定最高位和最低位分别是1和1 左边：和原始1相同，右边：1比原始5小
	* 这个决定可以做 因为中间的位数待定 最终决定后可以比原始low大
	* */
	public static int up(char[] low, int left, boolean leftMore, int rightLessEqualMore) {
		int N = low.length;
		int right = N - 1 - left;
		if (left > right) {
			/*
			*TODO
			* base case
			* 都做完决定了！
			* 如果左边做完决定的部分大于原始 或者 如果左边做完决定的部分等于原始&左边做完决定的部分不小于原始
			* 有效！
			* 否则，无效！
			* */
			return leftMore || (!leftMore && rightLessEqualMore != 0) ? 1 : 0;
		}
		//TODO 如果上面没有return，说明决定没做完，就继续做
		if (leftMore) { //TODO 如果左边做完决定的部分大于原始 那么中间怎么变都有效
			//TODO (left << 1) 因为 中间是-左部分-右部分
			return num(N - (left << 1));
		} else { //TODO 如果左边做完决定的部分等于原始
			int ways = 0;
			//TODO 当前left做的决定，大于原始的left
			/*
			*TODO
			* eg: low当前的left对应的元素是1 作出决定 左和右同时做决定
			* left能不能变成2  不能 因为2不能旋转
			* left能不能变成3 不能 因为3不能旋转
			* left能不能变成4 不能 因为4不能旋转
			* ....
			* left能不能变成8 能 right变成8
			  left能不能变成9 能 right变成6
			* 做完决定之后 已经造成left对应的元素 比原始low对应的元素大了
			* */
			for (char cha = (char) (low[left] + 1); cha <= '9'; cha++) {
				//convert(cha, left != right) != -1表示当前能变
				if (convert(cha, left != right) != -1) {
					//TODO 跑后续的分支 后续的分支rs不重要了 因为 左侧做完决定的部分大于原始 后面的递归直接公式计算出来
					ways += up(low, left + 1, true, rightLessEqualMore);
				}
			}
			//TODO 当前left做的决定，等于原始的left
			//TODO 不是所有的东西都能尝试等于 必须 先把原始left的元素 判断能不能转
			int convert = convert(low[left], left != right);
			if (convert != -1) {//TODO 能转 尝试等于的情况
				/*
				*TODO
				* 假设low的最高位和最低位是1和9
				* 因为是等于的情况
				* 当前做决定新的left和right都是1
				* 那么left和原始相同 right比原始小，然后往下做递归
				* 假设low的最高位和最低位是6和4
				* 因为是等于的情况
				* 当前做决定新的left和right 就是6 和9
				* 那么left和原始相同 right比原始大，然后往下做递归
				* */
				if (convert < low[right]) {
					ways += up(low, left + 1, false, 0);
				} else if (convert == low[right]) {
					ways += up(low, left + 1, false, rightLessEqualMore);
				} else {
					ways += up(low, left + 1, false, 2);
				}
			}
			return ways;
		}
	}

	// ll < =
	// rs < = >
	public static int down(char[] high, int left, boolean ll, int rs) {
		int N = high.length;
		int right = N - 1 - left;
		if (left > right) {
			return ll || (!ll && rs != 2) ? 1 : 0;
		}
		if (ll) {
			return num(N - (left << 1));
		} else {
			int ways = 0;
			for (char cha = (N != 1 && left == 0) ? '1' : '0'; cha < high[left]; cha++) {
				if (convert(cha, left != right) != -1) {
					ways += down(high, left + 1, true, rs);
				}
			}
			int convert = convert(high[left], left != right);
			if (convert != -1) {
				if (convert < high[right]) {
					ways += down(high, left + 1, false, 0);
				} else if (convert == high[right]) {
					ways += down(high, left + 1, false, rs);
				} else {
					ways += down(high, left + 1, false, 2);
				}
			}
			return ways;
		}
	}

	public static int num(int bits) {
		if (bits == 1) {
			return 3;
		}
		if (bits == 2) {
			return 5;
		}
		int p2 = 3;
		int p1 = 5;
		int ans = 0;
		for (int i = 3; i <= bits; i++) {
			ans = 5 * p2;
			p2 = p1;
			p1 = ans;
		}
		return ans;
	}

	/*
	*TODO
	* 如果是最开始 :
	* Y X X X Y
	* -> 1 X X X 1
	* -> 8 X X X 8
	* -> 9 X X X 6
	* -> 6 X X X 9
	* 如果不是最开始 :
	* Y X X X Y
	* -> 0 X X X 0
	* -> 1 X X X 1
	* -> 8 X X X 8
	* -> 9 X X X 6
	* -> 6 X X X 9
	* 所有的len位数，有几个有效的？
	* */
	public static int all(int len) {
		int ans = (len & 1) == 0 ? 1 : 3;
		for (int i = (len & 1) == 0 ? 2 : 3; i < len; i += 2) {
			ans *= 5;
		}
		return ans << 2;
	}

	/*
	*TODO
	* eg:
	* 一个五位数 问几个有效
	* X X X X X
	* 最高位和最低位 只能同时是1和8 或者 最高位是6 最低位是9 或者 最到位是9 最低位是6
	* 也就是说
	* 一共i位数
	* i=1 那么 也就是 0 1 8 这3个数
	* i!=1 假设是4位数 开头和结尾4种组合  中间那两位自己去组合 并且中间2位可以是00
	* 所以i是分阶段的
	* 如果是最开始的8位数 那么开头和结尾的4中组合 中间6位自己去组合
	* 如果不是最开始的8位数 eg:10位 那么开头和结尾的4种组合 中间8位自己去组合 这个8位是非最开始的8位 不是上一行的8位的操作方式
	* 这个8位是非最开始的8位 有5种 分别是 00 11 69 88 96
	 * */
	public static int all(int len, boolean init) {
		/*
		*TODO
		* 如果当前是0位数
		* 判断是否是最开始的0位
		* 是 直接返回0
		* 不是 直接返回1
		* eg: 2位数 可以是11 88 69 96
		* 拨掉2位之后只剩下0位 但不是最开始的0位
		* 如果当前是1位数
		* 判断是否是最开始的1位
		* 是  那么就有 0 1 8 这3种情况  return 3
		* 不是 那么就是 0 1 8 同样3种  return 3
		* 如果当前是i位
		* 判断是否是最开始的i位
		* 是 那么剥掉最高位和最低位 留下非开始的i-2位 4*counts(i-2,false)
		* 不是 那么剥掉最高位和最低位 留下非开始的i-2位 5*counts(i-2,false)
		* eg: 有一个6位数
		* 对于这个数 最高位和最低位有四种情况 11 88 69 96 对于中间的4位是非开始的4位 也就是4*counts(4,false)
		* 对于中间的非开始的4位counts(4,false) 如何求解： 也就是 这4位的最高位和最低位 可以是 11 88 00 69 96  5*counts(2,false)
		* 对于中间的非开始的2位counts(2,false) 如何求解： 也就是 这2位的最高位和最低位 可以是 11 88 00 69 96  5*counts(0,false)
		* 最后只剩下0位 那么就是一种可能性
		* */
		if (len == 0) { // init == true，不可能调用all(0)
			return 1;
		}
		if (len == 1) {
			return 3;
		}
		if (init) {
			return all(len - 2, false) << 2;
		} else {
			return all(len - 2, false) * 5;
		}
	}

}
