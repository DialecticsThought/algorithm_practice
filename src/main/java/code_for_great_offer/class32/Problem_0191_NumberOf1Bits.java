package code_for_great_offer.class32;

public class Problem_0191_NumberOf1Bits {

	// n的二进制形式，有几个1？
	public static int hammingWeight1(int n) {
		int bits = 0;
		int rightOne = 0;
		while(n != 0) {
			bits++;
			/*
			*TODO 最末尾的1拿出来
			* n 和 n取反 做与运算
			* */
			rightOne = n & (-n);
			//TODO n 与当前的 最低位的1 做异或 运算 让 n 把最低位的1变成0
			n ^= rightOne;
		}
		return bits;
	}

	public static int hammingWeight2(int n) {
		n = (n & 0x55555555) + ((n >>> 1) & 0x55555555);
		n = (n & 0x33333333) + ((n >>> 2) & 0x33333333);
		n = (n & 0x0f0f0f0f) + ((n >>> 4) & 0x0f0f0f0f);
		n = (n & 0x00ff00ff) + ((n >>> 8) & 0x00ff00ff);
		n = (n & 0x0000ffff) + ((n >>> 16) & 0x0000ffff);
		return n;
	}

}
