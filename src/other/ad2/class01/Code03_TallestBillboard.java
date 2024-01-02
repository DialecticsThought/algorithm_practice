package other.ad2.class01;

import java.util.HashMap;

public class Code03_TallestBillboard {

	public int tallestBillboard(int[] rods) {
		HashMap<Integer, Integer> dp = new HashMap<>(), cur;
		dp.put(0, 0);
		for (int x : rods) {
			if (x != 0) {
				cur = new HashMap<>(dp);
				for (int d : cur.keySet()) {
					int diffMore = cur.get(d);
					dp.put(d + x, Math.max(diffMore, dp.getOrDefault(x + d, 0)));
					int diffXD = dp.getOrDefault(Math.abs(x - d), 0);
					if (d >= x) {
						dp.put(d - x, Math.max(diffMore + x, diffXD));
					} else {
						dp.put(x - d, Math.max(diffMore + d, diffXD));
					}
				}
			}
		}
		return dp.get(0);
	}

}
