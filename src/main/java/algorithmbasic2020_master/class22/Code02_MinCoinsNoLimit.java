package algorithmbasic2020_master.class22;

public class Code02_MinCoinsNoLimit {

    public static int minCoins(int[] arr, int aim) {
        return process(arr, 0, aim);
    }

    /**
     * TODO
     * arr[index...]面值，每种面值张数自由选择，
     * 搞出rest正好这么多钱，返回最小张数
     * 拿Integer.MAX_VALUE标记怎么都搞定不了
     */
    public static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {//TODO 如果已经到最后 已经没有任何面值的钱了 还有n元要搞定 说明这条路径的选择是错的 返回Integer.MAX_VALUE
            return rest == 0 ? 0 : Integer.MAX_VALUE;//TODO 如果已经到最后 已经没有任何面值的钱了 还有0元要搞定 返回0张
        } else {
            int ans = Integer.MAX_VALUE;
            for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
                //TODO 表示 选了这么多张 arr[index]面值的前后 返回 从index + 1开始选 搞定剩下的钱的最小张数
                int next = process(arr, index + 1, rest - zhang * arr[index]);
                if (next != Integer.MAX_VALUE) {//TODO 说明有效
                    //TODO
                    ans = Math.min(ans, zhang + next);
                }
            }
            return ans;
        }
    }

    public static int dp1(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ans = Integer.MAX_VALUE;
                for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
                    int next = dp[index + 1][rest - zhang * arr[index]];
                    if (next != Integer.MAX_VALUE) {
                        ans = Math.min(ans, zhang + next);
                    }
                }
                dp[index][rest] = ans;
            }
        }
        return dp[0][aim];
    }

    public static int process2(int[] arr, int index, int rest) {
        if (index == arr.length) {//TODO 如果已经到最后 已经没有任何面值的钱了 还有n元要搞定 说明这条路径的选择是错的 返回Integer.MAX_VALUE
            return rest == 0 ? 0 : -1;//TODO 如果已经到最后 已经没有任何面值的钱了 还有0元要搞定 返回0张
        } else {
            int ans = -1;
            for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
                //TODO 表示 选了这么多张 arr[index]面值的前后 返回 从index + 1开始选 搞定剩下的钱的最小张数
                int next = process2(arr, index + 1, rest - zhang * arr[index]);
                if (next != -1) {//TODO 说明有效
                    if (next == -1) {
                        ans = next + zhang;
                    } else {
                        //TODO
                        ans = Math.min(ans, zhang + next);
                    }
                }
            }
            return ans;
        }
    }

    public static int dp2(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (rest - arr[index] >= 0
                        && dp[index][rest - arr[index]] != Integer.MAX_VALUE) {
                    dp[index][rest] = Math.min(dp[index][rest], dp[index][rest - arr[index]] + 1);
                }
            }
        }
        return dp[0][aim];
    }

    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        boolean[] has = new boolean[maxValue + 1];
        for (int i = 0; i < N; i++) {
            do {
                arr[i] = (int) (Math.random() * maxValue) + 1;
            } while (has[arr[i]]);
            has[arr[i]] = true;
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
        int testTime = 300000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxLen);
            int[] arr = randomArray(N, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = minCoins(arr, aim);
            int ans2 = dp1(arr, aim);
            int ans3 = dp2(arr, aim);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        int N = (int) (Math.random() * maxLen);
        int[] arr = randomArray(N, maxValue);
        int aim = (int) (Math.random() * maxValue);
        int ans1 = minCoins(arr, aim);
        int ans2 = dp1(arr, aim);
        int ans3 = dp2(arr, aim);
        System.out.println(ans1);
        System.out.println(ans2);
        System.out.println("功能测试结束");
    }

}
