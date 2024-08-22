package algorithmbasic2020_master.class14;

import java.util.Arrays;
import java.util.Comparator;
/*
* 一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲。
* 给你每一个项目开始的时间和结束的时间
* 你来安排宣讲的日程，要求会议室进行的宣讲的场次最多。返回最多的宣讲场次。
* */
public class Code03_BestArrange {

	public static class Program {
		public int start;
		public int end;
		/*
		* 会议的开始时间 和 结束时间
		* */
		public Program(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}

	// 暴力！所有情况都尝试！
	public static int bestArrange1(Program[] programs) {
		if (programs == null || programs.length == 0) {
			return 0;
		}
		/*
		* 因为最开始的时候 安排了0个会议 done = 0
		* 时间点是 0
		* */
		return process(programs, 0, 0);
	}

	// 还剩下的会议都放在programs里
	// done之前已经安排了多少会议的数量
	// timeLine目前来到的时间点是什么
	// 问题： 目前来到timeLine的时间点，已经安排了done多的会议，剩下的会议（这些会议放在programs）可以自由安排
	// 返回能安排的最多会议数量
	public static int process(Program[] programs, int done, int timeLine) {
		/*
		 * 当剩余会议 为 0 的时候 说明 没有回忆可以安排了 直接返回done
		 * */
		if (programs.length == 0) {
			return done;
		}
		// 还剩下会议可以选择  准确来说到现在为止能选择的会议的（最大）数量
		int max = done;
		// 当前安排的会议是什么会，每一个都枚举
		for (int i = 0; i < programs.length; i++) {
			/*
			* 当枚举到的的会议的开始时间 晚于 现在的事件 说明可以安排
			* */
			if (programs[i].start >= timeLine) {
				//copyButExcept把当前会议删除 并放入新的数组里面
				Program[] next = copyButExcept(programs, i);
				/*
				* 既然 安排了这个会议 那么 想开下一个会 就把timeLine 变成 这个会议的结束时间
				* 也就是 想安排下一个会议就取决于当前会议的结束时间
				* done+1 的+1 就代表i号会议 programs[i].end表示来到了i号会议结束的时间点
				* 通过和max比较得出到现在为止能选择的会议的（最大）数量  那个数量大 选哪个
				* */
				max = Math.max(max, process(next, done + 1, programs[i].end));
			}
		}
		return max;
	}

	public static Program[] copyButExcept(Program[] programs, int i) {
		Program[] ans = new Program[programs.length - 1];
		int index = 0;
		for (int k = 0; k < programs.length; k++) {
			if (k != i) {
				ans[index++] = programs[k];
			}
		}
		return ans;
	}

	// 会议的开始时间和结束时间，都是数值，不会 < 0
	public static int bestArrange2(Program[] programs) {
		/*
		* 进过比较 能得到一个数组  数组里面的会议 哪个会议的结束时间早 哪个会议会排在前面
		* */
		Arrays.sort(programs, new ProgramComparator());
		int timeLine = 0;//表示当前时间 并且是初始状态
		int result = 0;//表示已经安排了多了个会议
		// 依次遍历每一个会议，结束时间早的会议先遍历
		for (int i = 0; i < programs.length; i++) {
			if (timeLine <= programs[i].start) {//如果遍历到的会议晚于当前时间点 就可以放入安排中
				result++;//已安排的会议数量+1
				timeLine = programs[i].end;//时间点 来到当前遍历到的会议的结束时间点 下次循环安排下一个会议
			}
		}
		return result;
	}
	/*
	* 定一个比较器
	* 两个会议的结束时间 作比较
	*/
	public static class ProgramComparator implements Comparator<Program> {

		@Override
		public int compare(Program o1, Program o2) {
			return o1.end - o2.end;
		}

	}

	// for test
	public static Program[] generatePrograms(int programSize, int timeMax) {
		Program[] ans = new Program[(int) (Math.random() * (programSize + 1))];
		for (int i = 0; i < ans.length; i++) {
			int r1 = (int) (Math.random() * (timeMax + 1));
			int r2 = (int) (Math.random() * (timeMax + 1));
			if (r1 == r2) {
				ans[i] = new Program(r1, r1 + 1);
			} else {
				ans[i] = new Program(Math.min(r1, r2), Math.max(r1, r2));
			}
		}
		return ans;
	}

	public static void main(String[] args) {
		int programSize = 12;
		int timeMax = 20;
		int timeTimes = 1000000;
		for (int i = 0; i < timeTimes; i++) {
			Program[] programs = generatePrograms(programSize, timeMax);
			if (bestArrange1(programs) != bestArrange2(programs)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
