package code_for_great_offer.class45;

import java.util.HashMap;
import java.util.HashSet;

public class Problem_0403_FrogJump {

	public static boolean canCross(int[] stones) {
		HashSet<Integer> set = new HashSet<>();
		for (int num : stones) {
			set.add(num);
		}
		HashMap<Integer, HashMap<Integer, Boolean>> dp = new HashMap<>();
		return jump(1, 1, stones[stones.length - 1], set, dp);
	}

	/**
	 *
	 * @param cur 一共跳了多少步数
	 * @param pre 上一步跳了多少
	 * @param end
	 * @param set
	 * @param dp
	 * @return
	 */
	public static boolean jump(int cur, int pre, int end, HashSet<Integer> set,
			HashMap<Integer, HashMap<Integer, Boolean>> dp) {
		if (cur == end) {
			return true;
		}
		if (!set.contains(cur)) {
			return false;
		}
		if (dp.containsKey(cur) && dp.get(cur).containsKey(pre)) {
			return dp.get(cur).get(pre);
		}
		/**
		 * 这一步的跳的步数 取决于上一步跳的步数
		 *上一步数 = pre
		 *下一步 = pre-1 / pre / pre+1
		 */
		boolean ans = (pre > 1 && jump(cur + pre - 1, pre - 1, end, set, dp))
				|| jump(cur + pre, pre, end, set, dp)
				|| jump(cur + pre + 1, pre + 1, end, set, dp);
		if (!dp.containsKey(cur)) {
			dp.put(cur, new HashMap<>());
		}
		if (!dp.get(cur).containsKey(pre)) {
			dp.get(cur).put(pre, ans);
		}
		return ans;
	}

}
