package other.ad2.class01;

public class Code02_LeastOperatorsToExpressNumber {

	public int leastOpsExpressTarget(int x, int target) {
		int pos = 0;
		int neg = 0;
		int k = 0;
		while (target > 0) {
			int cur = target % x;
			if (k > 0) {
				int pos2 = Math.min(cur * k + pos, (cur + 1) * k + neg);
				int neg2 = Math.min((x - cur) * k + pos, (x - cur - 1) * k + neg);
				pos = pos2;
				neg = neg2;
			} else {
				pos = cur * 2;
				neg = (x - cur) * 2;
			}
			target /= x;
			k++;
		}
		return Math.min(pos, k + neg) - 1;
	}

}
