package algorithmbasic2020_master.class02;
/*
* 题目二
* 一个数组中有一种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这种数
* */
public class Code02_EvenTimesOddTimes {
	/*
	* arr[2,1,3,2,2,4,1,3,1,2,4,1,4]
	* =>arr里面 1有4个 2有4个 3有2个 4有3个
	* eor = 0 然后 和数组里面的所有元素异或
	* 得到 eor ^ 1 ^ 1 ^ 1 ^ 1 ^ 2 ^ 2 ^ 2 ^ 2 ^ 3 ^ 3 ^ 4 ^ 4 ^ 4
	* => 1 ^ 1 ^ 1 ^ 1 = 0   2 ^ 2 ^ 2 ^ 2 = 0  3 ^ 3 = 0
	* => eor ^ 0 ^ 0 ^ 4 ^ 4 ^ 4 = 4
	* */
	// arr中，只有一种数，出现奇数次
	public static void printOddTimesNum1(int[] arr) {
		int eor = 0;
		for (int i = 0; i < arr.length; i++) {
			eor ^= arr[i];
		}
		System.out.println(eor);
	}
	/*
	*怎么把一个int类型的数,提取出最右侧的1来
	* —个数组中有两种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这两种数
	* */
	public static void printOddTimesNum2(int[] arr) {
		/*
		* 一开始 用eor 把数组中的元素都异或一边
		* 得到 eor = a ^ h
		* */
		int eor = 0;
		for (int i = 0; i < arr.length; i++) {
			eor ^= arr[i];
		}
		/*
		* a 和 b是两种数
		* eor != 0
		* eor最右侧的1，提取出来
		* eg:  eor = 00110010110111000
		* mostRightOne = 00000000000001000
		* */
		int rightOne = eor & (-eor); // 提取出最右的1
		int onlyOne = 0; //准备一个 eor'
		for (int i = 0 ; i < arr.length;i++) {
			/*
			* eg
			* arr[1] =  111100011110000
			* rightOne=  000000000010000
			* (arr[i] & rightOne) != 0 说明 arr[i] 最右侧是1不是0
			* */
			if ((arr[i] & rightOne) != 0) {
				onlyOne ^= arr[i];//得到eor'
			}
		}
		System.out.println(onlyOne + " " + (eor ^ onlyOne));
	}

	/*
	*
	* 输出二进制位为1的个数
	* */
	public static int bit1counts(int N) {
		int count = 0;
		//   011011010000
		//   000000010000    count+1
		//   011011000000
		while(N != 0) {
			//提取最右侧的一
			int rightOne = N & ((~N) + 1);
			//提取一次就计数器加1
			count++;
			//提出来之后 删掉最右侧的1
			N ^= rightOne;
			// N -= rightOne
		}
		return count;
	}


	public static void main(String[] args) {
		int a = 5;
		int b = 7;

		a = a ^ b;
		b = a ^ b;
		a = a ^ b;

		System.out.println(a);
		System.out.println(b);

		int[] arr1 = { 3, 3, 2, 3, 1, 1, 1, 3, 1, 1, 1 };
		printOddTimesNum1(arr1);

		int[] arr2 = { 4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2 };
		printOddTimesNum2(arr2);

	}

}
