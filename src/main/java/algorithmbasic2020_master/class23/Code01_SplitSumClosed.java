package algorithmbasic2020_master.class23;

/**
 * TODO
 * 给定一个正数数组arr,
 * 请把arr中所有的数分成两个集合，尽量让两个集合的累加和接近返回︰
 * 最接近的情况下，较小集合的累加和
 * eg:[3,4,5,1] => [3,4] [5,1]  返回6
 * eg:[100,2,3,5] =>  [2,3,5] [100] 返回10
 * 1.先统计arr中的累加和 arr[0]+arr[1]+.....arr[100]
 * 暴力递归
 * 从arr[i]开始选择，返回累加和尽可能接近rest，但不能 > rest 最近的累加和
 * 每个节点有2个节点
 * 	1.选择arr[i] 左侧
 * 	2.不选择arr[i]，从arr[i+1]开始选 右侧
 * 	[10,2,3,5] sum = 20  rest = 10
 * 	下面的节点的左分支是选择，有分支是被选择
 * 			  f(0,10)
 * 			  ↙ 	↘
 * 		f(1,0)  	f(1,10)  当前选择的10<=rest
 * 					 ↙  	↘
 * 				f(2,8)	     f(2,10)   当前选择的2<=rest
 * 				↙ 	↘		    ↙ 	↘
 * 		 f(3,5)     f(3,8)   f(3,7)     f(3,10) 当前选择的3<=rest
 * 		↙ 	↘	    ↙ 	↘	     ↙   ↘	     ↙ 	↘
 * 	f(4,0) f(4,5) f(4,3) f(4,8)	f(4,2) f(4,7)	.....   当前选择的5<=rest
 * 	↑
 * 	只有这个是成功的
 * eg:[5,6,7]  sum=18=5+6+7 rest = 9
 *      f(0,9)
 *      ↙ 	 ↘
 *    f(1,4) f(1,9)
 *            ↙  ↘
 *         f(2,3) f(2,9)
 *                  ↙  ↘
 *             f(3,2) f(3,9)
 *               ↑
 *             2最接近rest
 * 如果在原题目的基础 规定一定要挑picks的个数
 * 某个实现这些书的累加和 离sum/2最近
 * eg:[3,1,2] sum = 6  sum/2= 3 picks = 1
 *      f(0,1,3)
 *        ↙  ↘
 *  f(1,0,0)   f(1,1,3)
 *               ↙  ↘
 *        f(2,0,2)   f(2,1,3)
 *                    ↙  ↘
 * 因为process(i,rest) 依赖 process(i+1,rest-arr[i])
 * => dp表 从下 -> 上
 * 并且 dp[i][rest] 依赖于dp[i+1][rest-arr[i]]
 */
public class Code01_SplitSumClosed {

    public static int right(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        //TODO sum / 2 = sum >>2
        return process(arr, 0, sum / 2);
    }

    //TODO  arr[i...]可以自由选择，请返回累加和尽量接近rest，但不能超过rest的情况下，最接近的累加和是多少？
    public static int process(int[] arr, int i, int rest) {
        if (i == arr.length) {
            return 0;
        } else { //TODO 还有数，现在来到arr[i]这个数
            //TODO  可能性1，不使用arr[i]
            int p1 = process(arr, i + 1, rest);
            //TODO  可能性2，要使用arr[i]
            int p2 = 0;
            if (arr[i] <= rest) {
                p2 = arr[i] + process(arr, i + 1, rest - arr[i]);
            }
            return Math.max(p1, p2);//TODO 哪一个选择更接近当前的rest
        }
    }

    public static int dp(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        sum /= 2;
        int N = arr.length;
        //TODO 因为变化范围是 0~arr.length => N+1的大小 下标 0-N
        int[][] dp = new int[N + 1][sum + 1];
        for (int i = N - 1; i >= 0; i--) {
            for (int rest = 0; rest <= sum; rest++) {
                // 可能性1，不使用arr[i]
                int p1 = dp[i + 1][rest];
                // 可能性2，要使用arr[i]
                int p2 = 0;
                if (arr[i] <= rest) {
                    p2 = arr[i] + dp[i + 1][rest - arr[i]];
                }
                dp[i][rest] = Math.max(p1, p2);
            }
        }
        return dp[0][sum];
    }

    public static int[] randomArray(int len, int value) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * value);
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 50;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = right(arr);
            int ans2 = dp(arr);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }

}
