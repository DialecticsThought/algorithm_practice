package code_for_great_offer.class33;

/*
*TODO
* 假设你是一个专业的狗仔，参加了一个 n 人派对，其中每个人被从 0 到 n - 1 标号。
* 在这个派对人群当中可能存在一位 “名人”。
* 所谓 “名人” 的定义是：其他所有 n - 1 个人都认识他/她，而他/她并不认识其他任何人。☆☆☆☆☆☆☆☆☆
* 现在你想要确认这个 “名人” 是谁，或者确定这里没有 “名人”。
* 而你唯一能做的就是问诸如 “A 你好呀，请问你认不认识 B呀？” 的问题，以确定 A 是否认识 B。
* 你需要在（渐近意义上）尽可能少的问题内来确定这位 “名人” 是谁（或者确定这里没有 “名人”）。
* 在本题中，你可以使用辅助函数 bool knows(a, b) 获取到 A 是否认识 B。
* 请你来实现一个函数 int findCelebrity(n)。
* 派对最多只会有一个 “名人” 参加。
* 若 “名人” 存在，请返回他/她的编号；若 “名人” 不存在，请返回 -1。
 * */
public class LeetCode_0277_FindTheCelebrity {
	/*
	* 输入: graph = [
	*   [1,1,0],
	*   [0,1,0],
	*   [1,1,1]
	* ]
	* 输出: 1
	* 解析: 有编号分别为 0、1 和 2 的三个人。
	* graph[i][j] = 1 代表编号为 i 的人认识编号为 j 的人，
	* 而 graph[i][j] = 0 则代表编号为 i 的人不认识编号为 j 的人。
	* “名人” 是编号 1 的人，因为 0 和 2 均认识他/她，但 1 不认识任何人。
	*
	* */
	// 提交时不要提交这个函数，因为默认系统会给你这个函数
	// knows方法，自己不认识自己
	//TODO 表示x是否认识j
	public static boolean knows(int x, int i) {
		return true;
	}

	// 只提交下面的方法 0 ~ n-1
	public int findCelebrity(int n) {
		//TODO 这个变量： 谁可能成为明星，谁就是cand
		int cand = 0;
		//
		/*
		*TODO
		* 如果 候选人认识i 那么i就是候选人
		* 因为明星候选人不能认识任何人
		* 这个方法 决定了 cand 是否不认识cand+1~arr.len-1的所有（cand的右侧）
		* */
		for (int i = 0; i < n; ++i) {
			if (knows(cand, i)) {
				cand = i;
			}
		}
		/*
		*TODO
		* cand是什么？唯一可能是明星的人！
		* 下一步就是验证，它到底是不是明星
		* 1) cand是不是不认识所有的人 cand...（右侧cand都不认识）
		* 所以，只用验证 0~cand-1的左侧即可
		* */
		for (int i = 0; i < cand; ++i) {
			if (knows(cand, i)) {
				return -1;
			}
		}
		// 2) 是不是所有的人都认识cand
		for (int i = 0; i < n; ++i) {
			if (!knows(i, cand)) {
				return -1;
			}
		}
		return cand;
	}

}
