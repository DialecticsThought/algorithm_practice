package code_for_great_offer.class51;

import java.util.Arrays;
import java.util.PriorityQueue;

public class LeetCode_0630_CourseScheduleIII {
	/*
	*TODO
	* 这里有 n 门不同的在线课程，按从 1 到 n编号。给你一个数组 courses ，
	* 其中 courses[i] = [durationi, lastDayi] 表示第 i 门课将会 持续 上 durationi 天课，并且必须在不晚于 lastDayi 的时候完成。
	* 你的学期从第 1 天开始。且不能同时修读两门及两门以上的课程。
	* 返回你最多可以修读的课程数目。
	* 示例 1：
	* 输入：courses = [[100, 200], [200, 1300], [1000, 1250], [2000, 3200]]
	* 输出：3
	* 解释：
	* 这里一共有 4 门课程，但是你最多可以修 3 门：
	* 首先，修第 1 门课，耗费 100 天，在第 100 天完成，在第 101 天开始下门课。
	* 第二，修第 3 门课，耗费 1000 天，在第 1100 天完成，在第 1101 天开始下门课程。
	* 第三，修第 2 门课，耗时 200 天，在第 1300 天完成。
	* 第 4 门课现在不能修，因为将会在第 3300 天完成它，这已经超出了关闭日期。
	* 示例 2：
	* 输入：courses = [[1,2]]
	* 输出：1
	* 示例 3
	* 输入：courses = [[3,2],[4,3]]
	* 输出：0
	* 链接：https://leetcode.cn/problems/course-schedule-iii
	* */
	public static int scheduleCourse(int[][] courses) {
		/*
		*TODO
		* courses[i]  = {花费，截止}
		* 根据截止时间排序  截止时间小的 先被考虑到
		* 截止时间相同的话 无所谓
		* */
		Arrays.sort(courses, (a, b) -> a[1] - b[1]);
		/*
		*TODO
		* 根据花费时间建立的大根堆 代表这哪些课也已被修
		* 用大根堆的原因： 要淘汰课程先淘汰花费时间多的课程 因为没课程的收益都一样 ，就是修完一门课
		* */
		PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> b - a);
		//TODO 时间点
		int time = 0;
		for (int[] c : courses) {
			/*
			*TODO
			* 当前时间 + 当前遍历到的课的花费时间 <= 当前遍历到的课的截止时间
			* */
			if (time + c[0] <= c[1]) {
				//TODO 把当前课程纳入考虑的范围
				heap.add(c[0]);
				//TODO 当前时间 变成修完该课程c[0]的时间
				time += c[0];
			} else {
				/*
				*TODO
				* 当前时间 + 课程的花费时间 > 截止时间,
				* 只有淘汰掉某课，当前的课才能进来！ 也就是不修堆顶的课 修当前的课
				* 要淘汰 也要淘汰 堆顶的课
				* 判断是否淘汰的条件
				* 就是堆顶的课的花费时间 是否 > 当前遍历到的课的花费时间
				* */
				if (!heap.isEmpty() && heap.peek() > c[0]) {
					//time -= heap.poll();
					//heap.add(c[0]);
					//time += c[0];
					/*
					 *TODO
					 * 如果 当前堆顶的课 淘汰了
					 * time回到 塞入堆顶的课之前的time 再+c[0]
					 * */
					heap.add(c[0]);
					time += c[0] - heap.poll();
				}
			}
		}
		return heap.size();
	}

}
