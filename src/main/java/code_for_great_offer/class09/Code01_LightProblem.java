package code_for_great_offer.class09;

/*
 * 给定一个数组arr，长度为N，arr中的值不是0就是1
 * arr[i]表示第i栈灯的状态，0代表灭灯，1代表亮灯
 * 每一栈灯都有开关，但是按下i号灯的开关，会同时改变i-1、i、i+2栈灯的状态
 * 问题一：
 * 如果N栈灯排成一条直线,请问最少按下多少次开关,能让灯都亮起来
 * 排成一条直线说明：
 * i为中间位置时，i号灯的开关能影响i-1、i和i+1
 * 0号灯的开关只能影响0和1位置的灯
 * N-1号灯的开关只能影响N-2和N-1位置的灯
 *
 * 问题二：
 * 如果N栈灯排成一个圈,请问最少按下多少次开关,能让灯都亮起来
 * 排成一个圈说明：
 * i为中间位置时，i号灯的开关能影响i-1、i和i+1
 * 0号灯的开关能影响N-1、0和1位置的灯
 * N-1号灯的开关能影响N-2、N-1和0位置的灯
 *
 * */
public class Code01_LightProblem {

	// 无环改灯问题的暴力版本
	public static int noLoopRight(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		if (arr.length == 1) {
			return arr[0] == 1 ? 0 : 1;
		}
		if (arr.length == 2) {
			return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
		}
		return f1(arr, 0);
	}

	public static int f1(int[] arr, int i) {
		if (i == arr.length) {
			return valid(arr) ? 0 : Integer.MAX_VALUE;
		}
		int p1 = f1(arr, i + 1);
		change1(arr, i);
		int p2 = f1(arr, i + 1);
		change1(arr, i);
		p2 = (p2 == Integer.MAX_VALUE) ? p2 : (p2 + 1);
		return Math.min(p1, p2);
	}

	public static void change1(int[] arr, int i) {
		if (i == 0) {
			arr[0] ^= 1;
			arr[1] ^= 1;
		} else if (i == arr.length - 1) {
			arr[i - 1] ^= 1;
			arr[i] ^= 1;
		} else {
			arr[i - 1] ^= 1;
			arr[i] ^= 1;
			arr[i + 1] ^= 1;
		}
	}

	public static boolean valid(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == 0) {
				return false;
			}
		}
		return true;
	}

	// 无环改灯问题的递归版本
	public static int noLoopMinStep1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		if (arr.length == 1) {
			return arr[0] ^ 1;
		}
		if (arr.length == 2) {
			return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
		}
		//TODO 2中选择
		// 不变0位置的状态
		int p1 = process1(arr, 2, arr[0], arr[1]);
		// 改变0位置的状态
		int p2 = process1(arr, 2, arr[0] ^ 1, arr[1] ^ 1);
		if (p2 != Integer.MAX_VALUE) {
			p2++;
		}
		return Math.min(p1, p2);
	}

	// 当前在哪个位置上，做选择，nextIndex - 1 = cur ，当前！
	// cur - 1位置的状态 preStatus
	// cur位置的状态  curStatus
	// 该函数能保证的前提  0....cur-2  全亮的！  该函数返回让所有的灯都亮 最少要摁几个按钮
	public static int process1(int[] arr, int nextIndex, int preStatus, int curStatus) {
		if (nextIndex == arr.length) { // 当前来到最后一个开关的位置
			//TODO 这行代码 如果最后两位置都是0 那么 返回1 反之
			return preStatus != curStatus ? (Integer.MAX_VALUE) : (curStatus ^ 1);
		}
		// 没到最后一个按钮呢！
		// i < arr.length
		if (preStatus == 0) { // 前一个状态为0 那么一定要改变
			curStatus ^= 1;//当前位置 也被改变
			int cur = arr[nextIndex] ^ 1;//下一个位置的状态也被变化 cur是因为nextIndex变成了下一个递归的当前
			int next = process1(arr, nextIndex + 1, curStatus, cur);
			//TODO 判断递归返回的解 是否为无效解 不是的话 摁开关的数量+1
			return next == Integer.MAX_VALUE ? next : (next + 1);
		} else { // 一定不能改变  eg：来到17位置 16位置是1 所以17位置不摁开关
			return process1(arr, nextIndex + 1, curStatus, arr[nextIndex]);
		}
	}

	// 无环改灯问题的迭代版本
	public static int noLoopMinStep2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		if (arr.length == 1) {
			return arr[0] == 1 ? 0 : 1;
		}
		if (arr.length == 2) {
			return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
		}
		int p1 = traceNoLoop(arr, arr[0], arr[1]);
		int p2 = traceNoLoop(arr, arr[0] ^ 1, arr[1] ^ 1);
		p2 = (p2 == Integer.MAX_VALUE) ? p2 : (p2 + 1);
		return Math.min(p1, p2);
	}

	public static int traceNoLoop(int[] arr, int preStatus, int curStatus) {
		int i = 2;
		int op = 0;
		while (i != arr.length) {
			if (preStatus == 0) {
				op++;
				preStatus = curStatus ^ 1;
				curStatus = arr[i++] ^ 1;
			} else {
				preStatus = curStatus;
				curStatus = arr[i++];
			}
		}
		return (preStatus != curStatus) ? Integer.MAX_VALUE : (op + (curStatus ^ 1));
	}

	// 有环改灯问题的暴力版本
	public static int loopRight(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		if (arr.length == 1) {
			return arr[0] == 1 ? 0 : 1;
		}
		if (arr.length == 2) {
			return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
		}
		return f2(arr, 0);
	}

	public static int f2(int[] arr, int i) {
		if (i == arr.length) {
			return valid(arr) ? 0 : Integer.MAX_VALUE;
		}
		int p1 = f2(arr, i + 1);
		change2(arr, i);
		int p2 = f2(arr, i + 1);
		change2(arr, i);
		p2 = (p2 == Integer.MAX_VALUE) ? p2 : (p2 + 1);
		return Math.min(p1, p2);
	}

	public static void change2(int[] arr, int i) {
		arr[lastIndex(i, arr.length)] ^= 1;
		arr[i] ^= 1;
		arr[nextIndex(i, arr.length)] ^= 1;
	}

	public static int lastIndex(int i, int N) {
		return i == 0 ? (N - 1) : (i - 1);
	}

	public static int nextIndex(int i, int N) {
		return i == N - 1 ? 0 : (i + 1);
	}

	// 有环改灯问题的递归版本
	public static int loopMinStep1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		if (arr.length == 1) {
			return arr[0] == 1 ? 0 : 1;
		}
		if (arr.length == 2) {
			return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
		}
		if (arr.length == 3) {
			return (arr[0] != arr[1] || arr[0] != arr[2]) ? Integer.MAX_VALUE : (arr[0] ^ 1);
		}
		// 0不变，1不变
		int p1 = process2(arr, 3, arr[1], arr[2], arr[arr.length - 1], arr[0]);
		// 0改变，1不变
		int p2 = process2(arr, 3, arr[1] ^ 1, arr[2], arr[arr.length - 1] ^ 1, arr[0] ^ 1);
		// 0不变，1改变
		int p3 = process2(arr, 3, arr[1] ^ 1, arr[2] ^ 1, arr[arr.length - 1], arr[0] ^ 1);
		// 0改变，1改变
		int p4 = process2(arr, 3, arr[1], arr[2] ^ 1, arr[arr.length - 1] ^ 1, arr[0]);
		p2 = p2 != Integer.MAX_VALUE ? (p2 + 1) : p2;
		p3 = p3 != Integer.MAX_VALUE ? (p3 + 1) : p3;
		p4 = p4 != Integer.MAX_VALUE ? (p4 + 2) : p4;
		return Math.min(Math.min(p1, p2), Math.min(p3, p4));
	}


	/*
	*TODO  下一个位置是，nextIndex
	* 当前位置是，nextIndex - 1 -> curIndex
	* 上一个位置是, nextIndex - 2 -> preIndex   preStatus表示nextIndex - 2的状态
	* 当前位置是，nextIndex - 1, curStatus表示nextIndex - 1的状态
	* endStatus, N-1位置的状态
	* firstStatus, 0位置的状态
	* 函数：返回，让所有灯都亮，至少按下几次按钮
	* 当前来到的位置(也就是nextIndex - 1)，一定不能是位置1！至少从位置2开始 ==> nextIndex >= 3
	* 原因：这里i是nextIndex不是curIndex  来到i位置 0....i位置之间 0位置可以是0 因为 最后一个位置可以影响到0位置
	*  但是0~i-1位置的所有灯必须是1 因为i和i+1之后的开关无法改变0~i-1位置的灯
	* => 每来到一个位置i，必须保证该位置的前一个位置灯是亮的  除了0位置和最后一个位置 因为是一个环
	*TODO 这里i是nextIndex不是curIndex
	* => 一般的位置i 如果i-1的灯是0 那么 i必须按开关  但是 1位置不用 因为 0位置的灯是关着的 那么n-1位置还可以补救
	*  所以函数从2位置开始
	*  0 ，1位置特殊  因为每个位置都有两种选择 那么 0,1组合在一起就是4种选择
	* 0,1： √ ×, × √  ,√ √ , × ×  => 在这4中分支的基础上 再做递归函数
	*/
	public static int process2(int[] arr,
			int nextIndex, int preStatus, int curStatus,
			int endStatus, int firstStatus) {
		/*
		*TODO
		* base case 来到最后一按钮！ 也就是nextIndex==n-1  潜台词 0~n-2的灯都是亮的
		* 只有n-1 n-2 和 0 这3个位置是相同的状态才能实现 所有的灯都亮
		* */
		if (nextIndex == arr.length) {
			//endStatus ^ 1 就是取反
			return (endStatus != firstStatus || endStatus != preStatus) ? Integer.MAX_VALUE : (endStatus ^ 1);
		}
		// 当前位置，nextIndex - 1
		// 当前的状态，叫curStatus
		// 如果不按下按钮，下一步的preStatus, curStatus
		// 如果按下按钮，下一步的preStatus, curStatus ^ 1
		// 如果不按下按钮，下一步的curStatus, arr[nextIndex]
		// 如果按下按钮，下一步的curStatus, arr[nextIndex] ^ 1
		int noNextPreStatus = 0;
		int yesNextPreStatus = 0;
		int noNextCurStatus =0;
		int yesNextCurStatus = 0;
		int noEndStatus = 0;
		int yesEndStatus = 0;
		if(nextIndex < arr.length - 1) {// 当前没来到N-2位置
			//不管按不下按钮，0位置的状态是不变的 firstStatus 最后一个位置也不会变
			 noNextPreStatus = curStatus;//不按开关 下一步的PreStatus
			 yesNextPreStatus = curStatus ^ 1;//按开关 下一步的PreStatus 也即是取反（异或）
			 noNextCurStatus = arr[nextIndex];//不按开关 下一步的CurStatus
			 yesNextCurStatus = arr[nextIndex] ^ 1;//按开关 下一步的CurStatus

		} else if(nextIndex == arr.length - 1) {// 当前来到的就是N-2位置
			/*
			* 最后一个位置的状态可能会变   可以被n-1位置或0位置改变
			* 假设endStatus被0位置改变  并且函数从2位置开始 每个递归函数都是获得被改变后的endStatus 那么就不能用数组原始的arr[n-1]
			* 但是来到n-2位置 因为n-2位置能改变n-1位置 也就是endStatus  所以不能用之前递归函数传入的endStatus
			* 用自己n-2位置是否改变 来决定 传endStatus给递归子函数
			 * */
			/*
			* 来到n-2位置的特殊性： 因为n-2位置会改变最后一个位置的灯  所以单独讨论
			* 如果不按下按钮，下一步（n-1）的preStatus, curStatus
			* 如果按下按钮，下一步（n-1）的preStatus, curStatus ^ 1
			* 如果不按下按钮，下一步（n-1）的curStatus,不能拿数组中的数 也就是 arr[nextIndex]（arr[n-1]）  只能拿endStatus 因为可能被修改过
			* 如果按下按钮，下一步（n-1）的curStatus, 不能拿数组中的数 也就是 arr[nextIndex]（arr[n-1]）  只能拿endStatus 因为可能被修改过
			* */
			noNextPreStatus = curStatus;
			yesNextPreStatus = curStatus ^ 1;
			noNextCurStatus = endStatus;
			yesNextCurStatus = endStatus ^ 1;
			noEndStatus = endStatus;
			yesEndStatus = endStatus ^ 1;
		}
		//TODO 这2个分支只走一个
		if(preStatus == 0) {//来到当前回位置 发现前一个位置是不亮的 说明当前位置必须按开关
			int next = process2(arr, nextIndex + 1, yesNextPreStatus, yesNextCurStatus,
					nextIndex == arr.length - 1 ? yesEndStatus : endStatus, firstStatus);
			return next == Integer.MAX_VALUE ? next : (next + 1);
		}else {//来到当前回位置 发现前一个位置是亮的 说明当前位置必须不按开关
			return process2(arr, nextIndex + 1, noNextPreStatus, noNextCurStatus,
					nextIndex == arr.length - 1 ? noEndStatus : endStatus, firstStatus);

		}
//		int curStay = (nextIndex == arr.length - 1) ? endStatus : arr[nextIndex];
//		int curChange = (nextIndex == arr.length - 1) ? (endStatus ^ 1) : (arr[nextIndex] ^ 1);
//		int endChange = (nextIndex == arr.length - 1) ? curChange : endStatus;
//		if (preStatus == 0) {
//			int next = process2(arr, nextIndex + 1, curStatus ^ 1, curChange, endChange, firstStatus);
//			return next == Integer.MAX_VALUE ? next : (next + 1);
//		} else {
//			return process2(arr, nextIndex + 1, curStatus, curStay, endStatus, firstStatus);
//		}
	}

	// 有环改灯问题的迭代版本
	public static int loopMinStep2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		if (arr.length == 1) {
			return arr[0] == 1 ? 0 : 1;
		}
		if (arr.length == 2) {
			return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
		}
		if (arr.length == 3) {
			return (arr[0] != arr[1] || arr[0] != arr[2]) ? Integer.MAX_VALUE : (arr[0] ^ 1);
		}
		// 0不变，1不变
		int p1 = traceLoop(arr, arr[1], arr[2], arr[arr.length - 1], arr[0]);
		// 0改变，1不变
		int p2 = traceLoop(arr, arr[1] ^ 1, arr[2], arr[arr.length - 1] ^ 1, arr[0] ^ 1);
		// 0不变，1改变
		int p3 = traceLoop(arr, arr[1] ^ 1, arr[2] ^ 1, arr[arr.length - 1], arr[0] ^ 1);
		// 0改变，1改变
		int p4 = traceLoop(arr, arr[1], arr[2] ^ 1, arr[arr.length - 1] ^ 1, arr[0]);
		p2 = p2 != Integer.MAX_VALUE ? (p2 + 1) : p2;
		p3 = p3 != Integer.MAX_VALUE ? (p3 + 1) : p3;
		p4 = p4 != Integer.MAX_VALUE ? (p4 + 2) : p4;
		return Math.min(Math.min(p1, p2), Math.min(p3, p4));
	}

	public static int traceLoop(int[] arr, int preStatus, int curStatus, int endStatus, int firstStatus) {
		int i = 3;
		int op = 0;
		while (i < arr.length - 1) {
			if (preStatus == 0) {
				op++;
				preStatus = curStatus ^ 1;
				curStatus = (arr[i++] ^ 1);
			} else {
				preStatus = curStatus;
				curStatus = arr[i++];
			}
		}
		if (preStatus == 0) {
			op++;
			preStatus = curStatus ^ 1;
			endStatus ^= 1;
			curStatus = endStatus;
		} else {
			preStatus = curStatus;
			curStatus = endStatus;
		}
		return (endStatus != firstStatus || endStatus != preStatus) ? Integer.MAX_VALUE : (op + (endStatus ^ 1));
	}

	// 生成长度为len的随机数组，值只有0和1两种值
	public static int[] randomArray(int len) {
		int[] arr = new int[len];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * 2);
		}
		return arr;
	}

	public static void main(String[] args) {
		System.out.println("如果没有任何Oops打印，说明所有方法都正确");
		System.out.println("test begin");
		int testTime = 20000;
		int lenMax = 12;
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * lenMax);
			int[] arr = randomArray(len);
			int ans1 = noLoopRight(arr);
			int ans2 = noLoopMinStep1(arr);
			int ans3 = noLoopMinStep2(arr);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("1 Oops!");
			}
		}
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * lenMax);
			int[] arr = randomArray(len);
			int ans1 = loopRight(arr);
			int ans2 = loopMinStep1(arr);
			int ans3 = loopMinStep2(arr);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("2 Oops!");
			}
		}
		System.out.println("test end");

		int len = 100000000;
		System.out.println("性能测试");
		System.out.println("数组大小：" + len);
		int[] arr = randomArray(len);
		long start = 0;
		long end = 0;
		start = System.currentTimeMillis();
		noLoopMinStep2(arr);
		end = System.currentTimeMillis();
		System.out.println("noLoopMinStep2 run time: " + (end - start) + "(ms)");

		start = System.currentTimeMillis();
		loopMinStep2(arr);
		end = System.currentTimeMillis();
		System.out.println("loopMinStep2 run time: " + (end - start) + "(ms)");
	}

}
