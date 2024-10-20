package code_for_great_offer.class02;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

public class Code01_ChooseWork {

	public static class Job {
		public int money;
		public int hard;

		public Job(int m, int h) {
			money = m;
			hard = h;
		}
	}

	public static class JobComparator implements Comparator<Job> {
		@Override
		public int compare(Job o1, Job o2) {
			return o1.hard != o2.hard ? (o1.hard - o2.hard) : (o2.money - o1.money);
		}
	}

	public static int[] getMoneys(Job[] job, int[] ability) {
		Arrays.sort(job, new JobComparator());
		//TODO key : 难度   value：报酬
		TreeMap<Integer, Integer> map = new TreeMap<>();
		//TODO 先放入第一个工作
		map.put(job[0].hard, job[0].money);
		// pre : 上一份进入map的工作
		Job pre = job[0];
		//TODO 过滤工作
		for (int i = 1; i < job.length; i++) {
			//TODO 如果相同的难度的工作 只留最高工资的  并且 难度越大 工资也越大 否则都删除
			if (job[i].hard != pre.hard && job[i].money > pre.money) {
				//TODO pre 换成最新的
				pre = job[i];
				map.put(pre.hard, pre.money);
			}
		}
		int[] ans = new int[ability.length];
		for (int i = 0; i < ability.length; i++) {
			//TODO 找到 ability[i] 当前人的能力 <= ability[i]  且离它最近的
			Integer key = map.floorKey(ability[i]);
			ans[i] = key != null ? map.get(key) : 0;
		}
		return ans;
	}

}
