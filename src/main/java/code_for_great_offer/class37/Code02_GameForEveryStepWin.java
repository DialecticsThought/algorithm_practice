package code_for_great_offer.class37;

/*
*TODO
* 来自字节
* 扑克牌中的红桃J和梅花Q找不到了，为了利用剩下的牌做游戏，小明设计了新的游戏规则
* 1) A,2,3,4....10,J,Q,K分别对应1到13这些数字，大小王对应0
* 2) 游戏人数为2人，轮流从牌堆里摸牌，每次摸到的牌只有“保留”和“使用”两个选项，且当前轮必须做出选择
* 3) 如果选择“保留”当前牌，那么当前牌的分数加到总分里，并且可以一直持续到游戏结束
* 4) 如果选择“使用”当前牌，那么当前牌的分数*3，加到总分上去，但是只有当前轮，下一轮，下下轮生效，之后轮效果消失。（持续3轮）
* 5) 每一轮总分大的人获胜
* 假设小明知道每一轮对手做出选择之后的总分(1)，返回小明在每一轮都赢的情况下(2)，最终的最大分是多少
* 如果小明怎么都无法保证每一轮都赢，返回-1
* */
public class Code02_GameForEveryStepWin {

//	public static max(int[] cards, int[] sroces) {
//		return f(cards, sroces, 0, 0, 0, 0);
//	}
	/*
	*TODO
	* 当前来到index位置，牌是cands[index]值
	* 对手第i轮的得分，sroces[i]
	* index -> 26种
	* hold -> (1+2+3+..13) -> 91 -> 91 * 4 - (11 + 12) -> 341
	* cur -> 26
	* next -> 13
	* 26 * 341 * 26 * 13 -> ? * (10 ^ 5)
	* int[] cards 所有牌
	* int[] sroces 记录对手每一轮的分数 对手第i轮的得分，sroces[i]
	* int index  记录当前来到的位置
	* int hold i位置之前 所有做出保留选择的牌的总分
	* int cur  来到当前第index轮，之前的牌（0~index-1）只算上使用的效果  加成是多少
	* int next 之前的牌，对index的下一轮 加成是多少
	* 假设 [5 3 ....]
	* 来到 index=2的位置 且 第一轮和第二轮都爆发的话 此时cur=24
	* 那么 对index的下一轮  index=3的话 能影响到index=3的 也就是 index=1的3   index=0的5 结束了
	* 返回值：如果i...最后，不能全赢，返回-1
	* 如果i...最后，能全赢，返回最后一轮的最大值
	* [5,3]
	* 一开始调用f( 0, 0, 0, 0);
	* 假设 0位置也就是第0轮 爆发 调用f(1,0,15,15) 第1个15是指 第1轮的效果 第2个15是指 第2轮的效果
	* 假设 1位置也就是第1轮 保留 调用f(2,3,15,0) 第1个15是指 第2轮的效果 第2个0是指 第3轮的效果没了
	* 假设 1位置也就是第1轮 爆发 调用f(2,0,15+9,9) 第1个24是指 第2轮的效果 第2个9是指 第3轮的效果
	* */
	public static int f(int[] cards, int[] sroces, int index, int hold, int cur, int next) {
		/*
		*TODO 一共52张牌
		* 2个人抽 那么 每个人都是26张
		* 最后一张牌就是index=25的牌
		* */
		if (index == 25) { //TODO 来到 最后一张  倒数第2张牌  倒数第3张牌 都会爆发
			int all = hold + cur + cards[index] * 3;
			//TODO 如果最后一轮 小明得到的总分 还是小于 对手 就结束
			if (all <= sroces[index]) {
				return -1;
			}
			return all;
		}
		//TODO 不仅最后一张

		//TODO case1 保留
		int all1 = hold + cur + cards[index];
		int p1 = -1;
		if (all1 > sroces[index]) {
			/*
			* TODO
			*  下一轮的效果 就是此时的next  因为 当前的next就是上一轮的形参的next
			*  表示上一轮的下一轮的下一轮，上一轮的下一轮指的是当前这一轮
			*  下一轮的在下一轮的影响是0
			* */
			p1 = f(cards, sroces, index + 1, hold + cards[index], next, 0);
		}
		//TODO case2  爆发
		int all2 = hold + cur + cards[index] * 3;
		int p2 = -1;
		if (all2 > sroces[index]) {
			//当前这一轮爆发 这一轮的hold是下一轮的hold ，下一轮的效果：next + cards[index] * 3  在下一轮的效果：cards[index] * 3
			p2 = f(cards, sroces, index + 1, hold, next + cards[index] * 3, cards[index] * 3);
		}
		return Math.max(p1, p2);
	}

	// 26 * 341 * 78 * 39 = 2 * (10 ^ 7)
	public static int process(int[] cards, int[] scores, int index, int hold, int cur, int next) {
		if (index == 25) {
			int all = hold + cur + cards[index] * 3;
			if (all > scores[index]) {
				return all;
			} else {
				return -1;
			}
		} else {
			int d1 = hold + cur + cards[index];
			int p1 = -1;
			if (d1 > scores[index]) {
				p1 = process(cards, scores, index + 1, hold + cards[index], next, 0);
			}
			int d2 = hold + cur + cards[index] * 3;
			int p2 = -1;
			if (d2 > scores[index]) {
				p2 = process(cards, scores, index + 1, hold, next + cards[index] * 3, cards[index] * 3);
			}
			return Math.max(p1, p2);
		}
	}



	// cur -> 牌点数    ->  * 3 之后是效果
	// next -> 牌点数   ->  * 3之后是效果
	public static int p(int[] cands, int[] sroces, int index, int hold, int cur, int next) {
		if (index == 25) { // 最后一张
			int all = hold + cur * 3 + cands[index] * 3;
			if (all <= sroces[index]) {
				return -1;
			}
			return all;
		}
		// 不仅最后一张
		// 保留
		int all1 = hold + cur * 3 + cands[index];
		int p1 = -1;
		if (all1 > sroces[index]) {
			p1 = f(cands, sroces, index + 1, hold + cands[index], next, 0);
		}
		// 爆发
		int all2 = hold + cur * 3 + cands[index] * 3;
		int p2 = -1;
		if (all2 > sroces[index]) {
			p2 = f(cands, sroces, index + 1, hold, next + cands[index], cands[index]);
		}
		return Math.max(p1, p2);
	}

	// 改出动态规划，记忆化搜索！



}
