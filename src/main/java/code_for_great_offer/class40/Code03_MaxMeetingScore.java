package code_for_great_offer.class40;

import java.util.Arrays;
import java.util.PriorityQueue;

/*
* 给定int[][] meetings，比如
*  {
*    {66, 70}   0号会议截止时间66，获得收益70
*    {25, 90}   1号会议截止时间25，获得收益90
*    {50, 30}   2号会议截止时间50，获得收益30
*  }
*  一开始的时间是0，任何会议都持续10的时间，但是一个会议一定要在该会议截止时间之前开始
*  只有一个会议室，任何会议不能共用会议室，一旦一个会议被正确安排，将获得这个会议的收益
*  请返回最大的收益
* [6,20] [9,50]
* 如果 0时间选择了会议0 那么 会议1就不能开了
*TODO
* [6,20] [9,50] [13,42] [16,39] [25,40] [25,39] [25,69]
* 1.所有的会议先按照截止时间排序
* 2.准备一个小根堆 根据收益排序的小根堆
*  初始time=0
*  来到index=0的会议 查看截止时间是否 <time
*  是 放入堆 然后time=10
*  来到index=1的会议 查看截止时间是否 <time
*  否 然后查看该会议的收益 是否比堆首大
*  是 替换掉堆首
*  来到index=2的会议 查看截止时间是否 <time
*  是 放入堆 然后time=20
*  来到index=3的会议 查看截止时间是否 <time
*  否 然后查看该会议的收益 是否比堆首大
*  否 什么都不做
*  来到index=4的会议 查看截止时间是否 <time
*  是 放入堆 然后time=30
*  来到index=5的会议 查看截止时间是否 <time
*  否 然后查看该会议的收益 是否比堆首大
*  否 什么都不做
*  来到index=6的会议 查看截止时间是否 <time
*  否 然后查看该会议的收益 是否比堆首大
*  是 替换掉堆首
*/
public class Code03_MaxMeetingScore {

	public static int maxScore1(int[][] meetings) {
		Arrays.sort(meetings, (a, b) -> a[0] - b[0]);
		int[][] path = new int[meetings.length][];
		int size = 0;
		return process1(meetings, 0, path, size);
	}

	public static int process1(int[][] meetings, int index, int[][] path, int size) {
		if (index == meetings.length) {
			int time = 0;
			int ans = 0;
			for (int i = 0; i < size; i++) {
				if (time + 10 <= path[i][0]) {
					ans += path[i][1];
					time += 10;
				} else {
					return 0;
				}
			}
			return ans;
		}
		//TODO case1 不选择当前会议
		int p1 = process1(meetings, index + 1, path, size);

		//TODO case1 不选择当前会议
		path[size] = meetings[index];
		int p2 = process1(meetings, index + 1, path, size + 1);
		// path[size] = null;
		return Math.max(p1, p2);
	}

	public static int maxScore2(int[][] meetings) {
		Arrays.sort(meetings, (a, b) -> a[0] - b[0]);
		PriorityQueue<Integer> heap = new PriorityQueue<>();
		int time = 0;
		// 已经把所有会议，按照截止时间，从小到大，排序了！
		// 截止时间一样的，谁排前谁排后，无所谓
		for (int i = 0; i < meetings.length; i++) {
			//会议结束的时间是否超过了截止时间
			if (time + 10 <= meetings[i][0]) {
				heap.add(meetings[i][1]);
				time += 10;
			} else {
				if (!heap.isEmpty() && heap.peek() < meetings[i][1]) {
					heap.poll();
					heap.add(meetings[i][1]);
				}
			}
		}
		int ans = 0;
		while (!heap.isEmpty()) {
			ans += heap.poll();
		}
		return ans;
	}

	public static int[][] randomMeetings(int n, int t, int s) {
		int[][] ans = new int[n][2];
		for (int i = 0; i < n; i++) {
			ans[i][0] = (int) (Math.random() * t) + 1;
			ans[i][1] = (int) (Math.random() * s) + 1;
		}
		return ans;
	}

	public static int[][] copyMeetings(int[][] meetings) {
		int n = meetings.length;
		int[][] ans = new int[n][2];
		for (int i = 0; i < n; i++) {
			ans[i][0] = meetings[i][0];
			ans[i][1] = meetings[i][1];
		}
		return ans;
	}

	public static void main(String[] args) {
		int n = 12;
		int t = 100;
		int s = 500;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int size = (int) (Math.random() * n) + 1;
			int[][] meetings1 = randomMeetings(size, t, s);
			int[][] meetings2 = copyMeetings(meetings1);
			int ans1 = maxScore1(meetings1);
			int ans2 = maxScore2(meetings2);
			if (ans1 != ans2) {
				System.out.println("出错了!");
				System.out.println(ans1);
				System.out.println(ans2);
			}
		}
		System.out.println("测试结束");
	}

}
