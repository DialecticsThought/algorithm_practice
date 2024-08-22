package algorithmbasic2020_master.class02;

public class Code01_Swap {

	public static void main(String[] args) {

		/*
		 * a和b内存地址一定是不同的 才能交换 要不然 得到的结果会有问题
		 * int a = 甲 ,  int b = 乙
		 * a = a ^ b  ==> a = 甲 ^ 乙 , b = 乙
		 * b = a ^ b  ==> b = 甲 ^ 乙 ^ 乙 = 甲 ^ (乙 ^ 乙) = 甲 ^ 0 = 甲, a = 甲 ^ 乙
		 * a = a ^ b  ==> a = 甲 ^ 乙 ^ 甲 = 甲 ^ 甲 ^ 乙 = 0 ^ 乙  = 乙 , b = 甲
		 * */
		int a = 16;
		int b = 603;

		System.out.println(a);
		System.out.println(b);


		a = a ^ b;
		b = a ^ b;
		a = a ^ b;


		System.out.println(a);
		System.out.println(b);




		int[] arr = {3,1,100};

		int i = 0;
		int j = 0;

		arr[i] = arr[i] ^ arr[j];
		arr[j] = arr[i] ^ arr[j];
		arr[i] = arr[i] ^ arr[j];

		System.out.println(arr[i] + " , " + arr[j]);


		System.out.println(arr[0]);
		System.out.println(arr[2]);

		swap(arr, 0, 0);

		System.out.println(arr[0]);
		System.out.println(arr[2]);


	}


	public static void swap (int[] arr, int i, int j) {
		// arr[0] = arr[0] ^ arr[0];
		arr[i]  = arr[i] ^ arr[j];
		arr[j]  = arr[i] ^ arr[j];
		arr[i]  = arr[i] ^ arr[j];
	}



}
