package algorithmbasic2020_master.class38;
/*
* 题目三
定义一种数:可以表示成若干（数量>1)连续正数和的数比如:
5 = 2+3，5就是这样的数
12=3+4+5，12就是这样的数
1不是这样的数，因为要求数量大于1个、连续正数和2 = 1+ 1，2也不是，因为等号右边不是连续正数
给定一个参数N，返回是不是可以表示成若干连续正数和的数
*
* */
public class Code03_MSumToN {
	/*
	* 有一个数 100
	* 1+2+3+4+...+? 是否等于 100 那就成功 如果 > 100
	* 尝试 2+3+4+5+...+? 是否等于 100 那就成功 如果 > 100
	* 尝试 3+4+5+6+...+? 是否等于 100 那就成功 如果 > 100
	* */
	public static boolean isMSum1(int num) {
		for (int start = 1; start <= num; start++) {//开头的数为 start
			int sum = start;
			/*
			* j <= num 是因为 如果 等于num
			* eg num =100  1+2+3+。。。+99+100 的总和一定大于100
			* 所以作为终止循环的条件
			* */
			for (int j = start + 1; j <= num; j++) {
				if (sum + j > num) {
					break;
				}
				if (sum + j == num) {
					return true;
				}
				sum += j;
			}
		}
		return false;
	}

	public static boolean isMSum2(int num) {
		/*
		* 因为有一个规律
		* num =1 和 2 的时候一定是false
		* 4 8 16 32 64 的时候 也是false
		* 那么就是 2的n次幂
		* */
		if(num < 3){
			return false;
		}
		/*
		 * 那么就是 2的n次方的写法 如果(num & (num - 1)) ==0 就是2的n次方
		 * 1 4 8 16 32  64.。。。的2进制上只有一个1
		 * 8的2进制： 1000
		 * 16的2进制： 10000
		 * 如果 num-1 就是 2的n次方 -1 那么
		 * eg num =16 num-1 =15
		 *  num的2进制：10000
		 *  num-1的2进制：01111
		 * 那么结果进行与运算
		 *   10000
		 * & 01111
		 *   00000
		 * */
		return (num & (num - 1)) != 0;
	}

	public static void main(String[] args) {
		for (int num = 1; num < 200; num++) {
			System.out.println(num + " : " + isMSum1(num));
		}
		System.out.println("test begin");
		for (int num = 1; num < 5000; num++) {
			if (isMSum1(num) != isMSum2(num)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test end");

	}
}
