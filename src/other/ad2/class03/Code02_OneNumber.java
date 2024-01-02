package other.ad2.class03;

public class Code02_OneNumber {

	public static int solution1(int num) {
		if (num < 1) {
			return 0;
		}
		int count = 0;
		for (int i = 1; i != num + 1; i++) {
			count += get1Nums(i);
		}
		return count;
	}

	public static int get1Nums(int num) {
		int res = 0;
		while (num != 0) {
			if (num % 10 == 1) {
				res++;
			}
			num /= 10;
		}
		return res;
	}
	//1~num中有多少个1
	public static int solution2(int num) {
		if (num < 1) {
			return 0;
		}
		//求N的位数len eg：1999,4位  12， 2位
		int len = getLenOfNum(num);
		if (len == 1) {
			return 1;
		}
		/*
		* 4198427 -> tmp1 ->1000000
		* 1000000  和原来的数 位数相同  但是最高位为1
		* 567890
		* 100000 ~ 199999
		* */
		int tmp1 = powerBaseOf10(len - 1);
		int first = num / tmp1;//拿到最高位的数
		/*
		* 432198 最高位上的4 总有100000
		* 132198 最高位的1 有32199个
		* */
		int firstOneNum = first == 1 ? ((num % tmp1) + 1) : tmp1;
		/*
		* 432198 万位为1的数量 10^4个
		* 剩下的位数 * 最高位的数字（多少个小组范围） * 10^（k-2)
		* */
		int otherOneNum = first * (len - 1) * (tmp1 / 10);
		return firstOneNum + otherOneNum + solution2(num % tmp1);
	}

	public static int getLenOfNum(int num) {
		int len = 0;
		while (num != 0) {
			len++;
			num /= 10;
		}
		return len;
	}

	public static int powerBaseOf10(int base) {
		return (int) Math.pow(10, base);
	}

	public static void main(String[] args) {
		int num = 50000000;
		long start1 = System.currentTimeMillis();
		System.out.println(solution1(num));
		long end1 = System.currentTimeMillis();
		System.out.println("cost time: " + (end1 - start1) + " ms");

		long start2 = System.currentTimeMillis();
		System.out.println(solution2(num));
		long end2 = System.currentTimeMillis();
		System.out.println("cost time: " + (end2 - start2) + " ms");

	}
}
