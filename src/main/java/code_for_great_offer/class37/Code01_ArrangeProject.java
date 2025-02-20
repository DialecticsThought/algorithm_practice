package code_for_great_offer.class37;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
*TODO
* 来自网易
* 刚入职网易互娱，新人mini项目便如火如荼的开展起来。为了更好的项目协作与管理，
* 小易决定将学到的甘特图知识用于mini项目时间预估。小易先把项目中每一项工作以任务的形式列举出来，
* 每项任务有一个预计花费时间与前置任务表，必须完成了该任务的前置任务才能着手去做该任务。
* 作为经验PM，小易把任务划分得井井有条，保证没有前置任务或者前置任务全数完成的任务，都可以同时进行。
* 小易给出了这样一个任务表，请作为程序的你计算需要至少多长时间才能完成所有任务。
* 输入第一行为一个正整数T，表示数据组数。
* 对于接下来每组数据，第一行为一个正整数N，表示一共有N项任务。
* 接下来N行，每行先有两个整数Di和Ki，表示完成第i个任务的预计花费时间为Di天，该任务有Ki个前置任务。
* 之后为Ki个整数Mj，表示第Mj个任务是第i个任务的前置任务。
* 数据范围：对于所有数据，满足1<=T<=3, 1<=N, Mj<=100000, 0<=Di<=1000, 0<=sum(Ki)<=N*2。
*TODO
* a:[]  b:[a]  c:[b,a] d:[b,a]
* a=7 c=2 b=3 d=5
* 图：
* ↑---------↓
* a -> b -> c
* ↓	   ↓---------d
* ↓--------------↑
* 先做掉a任务 否则其他任务不能做
* 再做掉b任务 否则任务c和d不能做
* 任务c和d可以一起做
*TODO
* ↓--a(3) ----> c(5) ------↓
* ↓		  ↑		↓          ↓
* ↓	 ↑----↑	    e(3) ----> k <---f(9)
* ↓	 ↑			↑          ↑
* ↓  b(7) ----> d(9) ------↑
* ↓-------------↑
* 1.先得到拓扑排序 ab d ce f k
* 任务： a b c d e f k
* 时间： 0 0 0 0 0 0 0
* a完成：
*  任务： a b c d e f k
*  时间： 0 0 3 3 0 0 0
* b完成： 7 > 3 更新
*  任务： a b c d e f k
*  时间： 0 0 7 7 0 0 0
* f完成： 9 > 0 更新
*  任务： a b c d e f k
*  时间： 0 0 7 7 0 0 9
* c完成： 12 > 0 更新
*  任务： a b c d e  f k
*  时间： 0 0 7 7 12 0 9
* d完成： 16 > 12 更新
*  任务： a b c d e  f k
*  时间： 0 0 7 7 16 0 9
* */
public class Code01_ArrangeProject {

	public static int dayCount(ArrayList<Integer>[] nums, int[] days, int[] headCount) {
		Queue<Integer> head = countHead(headCount);
		int maxDay = 0;
		int[] countDay = new int[days.length];
		while (!head.isEmpty()) {
			int cur = head.poll();
			countDay[cur] += days[cur];
			for (int j = 0; j < nums[cur].size(); j++) {
				headCount[nums[cur].get(j)]--;
				if (headCount[nums[cur].get(j)] == 0) {
					head.offer(nums[cur].get(j));
				}
				countDay[nums[cur].get(j)] = Math.max(countDay[nums[cur].get(j)], countDay[cur]);
			}
		}
		for (int i = 0; i < countDay.length; i++) {
			maxDay = Math.max(maxDay, countDay[i]);
		}
		return maxDay;
	}

	private static Queue<Integer> countHead(int[] headCount) {
		Queue<Integer> queue = new LinkedList<>();
		for (int i = 0; i < headCount.length; i++) {
			if (headCount[i] == 0)
				queue.offer(i); // 没有前驱任务
		}
		return queue;
	}

}
