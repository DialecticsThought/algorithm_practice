package other.mid.class002;

/**
 * 假设函数 Math.random()等概率随机返回一个在[0,1)范围上的数， 那么我们知
 * 道， 在[0,x)区 间上的数出现的概率为 x(0<x≤1)。 给定一个大于 0 的整数 k，
 * 并且可以使用 Math.random()函数， 请实现一个函数依然返回在[0,1)范围上的
 * 数， 但是在[0,x)区间上的数出现的概率为x^k(0<x≤ 1)。
 */
public class ProbabilityXPowerK {

	public static double randXPower2() {
		return Math.max(Math.random(), Math.random());
	}

	public static double randXPowerK(int k) {
		if (k < 1) {
			return 0;
		}
		double res = -1;
		for (int i = 0; i != k; i++) {
			res = Math.max(res, Math.random());
		}
		return res;
	}

	public static void main(String[] args) {
		double range = 0.5;
		int times = 5000000;
		int count = 0;
		for (int i = 0; i != times; i++) {
			if (randXPowerK(2) < range) {
				count++;
			}
		}
		double p = (double) count / (double) times;
		System.out.println("range [0," + range + "), probability: " + p);
	}
}
