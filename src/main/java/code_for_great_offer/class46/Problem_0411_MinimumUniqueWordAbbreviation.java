package code_for_great_offer.class46;

public class Problem_0411_MinimumUniqueWordAbbreviation {

	// 区分出来之后，缩写的长度，最短是多少？
	public static int min = Integer.MAX_VALUE;

	//TODO 取得缩写的长度最短的时候，决定是什么(fix)  这是答案
	public static int best = 0;

	//TODO 利用位运算加速  主方法
	public static String minAbbreviation1(String target, String[] dictionary) {
		min = Integer.MAX_VALUE;//TODO
		best = 0;
		char[] t = target.toCharArray();
		int len = t.length;
		int siz = 0;
		for (String word : dictionary) {
			if (word.length() == len) {//TODO 字典中的单词的长度不和target一样 就移除
				siz++;
			}
		}
		//TODO 上面的单词变成整数之后放入新数组里面
		int[] words = new int[siz];
		int index = 0;
		for (String word : dictionary) {
			if (word.length() == len) {
				char[] w = word.toCharArray();
				int status = 0;
				for (int j = 0; j < len; j++) {
					//TODO 每一个位置的元素 和对应的str的位置的元素是否相同
					if (t[j] != w[j]) {
						//TODO 相同，这个位置是1 不相同，这个位置是0
						status |= 1 << j;
					}
				}
				words[index++] = status;
			}
		}
		dfs1(words, len, 0, 0);
		/*
		*TODO
		* 下面就是把best还原成字符串
		* eg: 得到 10000  str是apple
		* 那么还原是a4
		* */
		StringBuilder builder = new StringBuilder();
		int count = 0;
		for (int i = 0; i < len; i++) {
			if ((best & (1 << i)) != 0) {
				if (count > 0) {
					builder.append(count);
				}
				builder.append(t[i]);
				count = 0;
			} else {
				count++;
			}
		}
		if (count > 0) {
			builder.append(count);
		}
		return builder.toString();
	}
	/*
	 *TODO
	 * eg: 当前为止做了个决定 1000000000100
	 * 判断连续的0的个数是否进位 最后生成 ?10?2
	 * */
	public static int abbrLen(int fix, int len) {
		int ans = 0;
		int cnt = 0;
		for (int i = 0; i < len; i++) {
			if ((fix & (1 << i)) != 0) {
				ans++;
				if (cnt != 0) {
					ans += 1;
				}
				cnt = 0;
			} else {
				cnt++;
			}
		}
		if (cnt != 0) {
			ans += 1;
		}
		return ans;
	}

	/*
	 *TODO
	 * 原始的字典，被改了
	 * target : abc 字典中的词 : bbb -> 101 也就是一个整数
	 * 对str做一个决定 根本不用str本身的char，用状态 表示 每一位保留还是不保留的决定
	 * */
	public static boolean canFix(int[] words, int fix) {
		for (int word : words) {
			//TODO 当前决定和数组里面的数与运算之后又存在0的情况
			if ((fix & word) == 0) {
				return false;
			}
		}
		return true;
	}
	/*
	 *TODO
	 * eg: liu 可以写成l2 可以写成3
	 * eg: azc arr=[abc ,ccc]
	 * azc不能写成a2 因为回合abc 混淆 abc不能写成2c 因为会和ccc混淆
	 * azc 也不能只写成3 因为3能匹配出3个str
	 * 那么只能写成1z1，但是和原始str长度相同
	 * 同理：
	 * abzcd arr=[abccc,ccccd]
	 * 那么只能写成2z2
	 * 2代表某2个字符串
	 *TODO
	 * 先得到目标str的长度
	 * 在arr中和该长度不同的str全部移除
	 *TODO
	 * 对于单词apple
	 * 决定哪些字母保留
	 * eg:只保留a 那么就是a4
	 * 只保留第2个p 那额就是2p2
	 * 每来到一个位置 就两个选择： 保留 or 不保留
	 *
	 *TODO
	 * eg: str = apple  arr[apabc,bpple]
	 * 现在做处理 str的每一个位置的char和arr的str的每个位置比对
	 * apabc => 00111 0表示该位置和str的对应char相同
	 * bpple => 10000
	 * ....
	 * 这样的话 就会把arr每个单词变成了一个数
	 * 比如说做了一个决定 只保留第一个p 也就是 01000  这个0表示省略该位字符 1表示保留该位字符
	 * 可以理解为 01000 => 1?4
	 * 01000和arr里面的数字做与运算 如果是0 那么说明该决定不成立
	 * */
	/*
	*TODO
	* 所有字典中的单词现在都变成了int，放在words里
	* 0....len-1 位去决定保留还是不保留！当前来到index位
	* fix是之前做出的决定!
	* */
	public static void dfs1(int[] words, int len, int fix, int index) {
		if (!canFix(words, fix)) {//TODO 之前的做的决定 不能区分当前str和words里面所有的单词
			if (index < len) {
				//TODO 不保留
				dfs1(words, len, fix, index + 1);
				//TODO 保留  1左移index位 再或运算
				dfs1(words, len, fix | (1 << index), index + 1);
			}
		} else {//TODO 之前的做的决定 能区分当前str和words里面所有的单词了
			//TODO 决定是fix，一共的长度是len，求出缩写是多长？
			//TODO eg: 1000000111 => 16111 => 5长度
			int ans = abbrLen(fix, len);
			//TODO 当前长度比之前的还要小
			if (ans < min) {//TODO 说明 更新min
				min = ans;
				//TODO 这个best递归到最后就是最终答案
				best = fix;
			}
		}
	}


	/*
	* 进一步设计剪枝，注意diff的用法
	*TODO
	* target=apple
	* 但是字典里的单词 的第2个位置都是'p'
	* 那么这个'p'没有必要保留
	* 也就是说这个位置没有必要尝试
	* 要求：所有单词的这个位置都是同一个字符
	* */
	public static String minAbbreviation2(String target, String[] dictionary) {
		min = Integer.MAX_VALUE;
		best = 0;
		char[] t = target.toCharArray();
		int len = t.length;
		int siz = 0;
		for (String word : dictionary) {
			if (word.length() == len) {
				siz++;
			}
		}
		int[] words = new int[siz];
		int index = 0;
		// 用来剪枝
		int diff = 0;
		for (String word : dictionary) {
			if (word.length() == len) {
				char[] w = word.toCharArray();
				int status = 0;
				for (int j = 0; j < len; j++) {
					if (t[j] != w[j]) {
						status |= 1 << j;
					}
				}
				words[index++] = status;
				diff |= status;
			}
		}
		dfs2(words, len, diff, 0, 0);
		StringBuilder builder = new StringBuilder();
		int count = 0;
		for (int i = 0; i < len; i++) {
			if ((best & (1 << i)) != 0) {
				if (count > 0) {
					builder.append(count);
				}
				builder.append(t[i]);
				count = 0;
			} else {
				count++;
			}
		}
		if (count > 0) {
			builder.append(count);
		}
		return builder.toString();
	}

	public static void dfs2(int[] words, int len, int diff, int fix, int index) {
		if (!canFix(words, fix)) {
			if (index < len) {
				dfs2(words, len, diff, fix, index + 1);
				if ((diff & (1 << index)) != 0) {
					dfs2(words, len, diff, fix | (1 << index), index + 1);
				}
			}
		} else {
			int ans = abbrLen(fix, len);
			if (ans < min) {
				min = ans;
				best = fix;
			}
		}
	}

}
