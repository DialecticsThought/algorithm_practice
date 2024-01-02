package algorithmbasic2020_master.class21;

/**
 * arr是货币数组，其中的值都是正数。再给定一个正数aim。每个值都认为是一张货币，
 * 即便是值相同的货币也认为每一张都是不同的，返回组成aim的方法数
 * 例如︰arr = {1,1,1}. aim = 2
 * 第0个和第1个能组成2，第1个和第2个能组成2，第0个和第2个能组成2一共就3种方法，所以返回3
 * TODO
 * arr=[5,1,1,2,2,5]  aim=20
 * 从左往右的尝试模型
 * 每个节点有两个分支：选 / 不选
 * 				f(0,20) => 从下标=0开始选择 rest=20
 * 			选↙			↘ 不选
 * 		f(1,15) 		f(1,20)
 * 		↙		↘		  ↙ 	↘
 * 	f(2,14)   f(1,15)	f(2,19) f(2,20)
 * 	 ↙	  ↘
 * f(3,13) f(3,14) 	.............
 * .............
 * dp表：
 * 对于递归f(index,rest)函数 依赖于f(index+1,rest) 和f(index+1,rest-arr[index])
 * 那么dp[index][rest]依赖于dp[index+1][rest]和dp[index+1][rest-arr[index]]
 * 所以实时从下往上填写dp表(每行从左->右)
 * if (index == arr.length) {return rest == 0 ? 1 : 0;} => dp[arr.length][0]=1
 */
public class Code02_CoinsWayEveryPaperDifferent {

    public static int coinWays(int[] arr, int aim) {
        return process(arr, 0, aim);
    }

    // arr[index....] 组成正好rest这么多的钱，有几种方法
    public static int process(int[] arr, int index, int rest) {
        if (rest < 0) {
            return 0;
        }
        if (index == arr.length) { //TODO 没钱了！
            return rest == 0 ? 1 : 0;//TODO rest=0说明这条路径是正确的的
        } else {
            //TODO 2个决策 不要arr[index]的钱的决策 + 要arr[index]的钱的决策
            return process(arr, index + 1, rest) + process(arr, index + 1, rest - arr[index]);
        }
    }

    public static int dp(int[] arr, int aim) {
        if (aim == 0) {
            return 1;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;//TODO 因为 if (index == arr.length)  return rest == 0 ? 1 : 0;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest] + (rest - arr[index] >= 0 ? dp[index + 1][rest - arr[index]] : 0);
            }
        }
        return dp[0][aim];
    }

    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    // 为了测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 为了测试
    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinWays(arr, aim);
            int ans2 = dp(arr, aim);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("测试结束");
    }

}
