package algorithmbasic2020_master.class21;

import java.util.HashMap;

public class Code6_CoinsWay {
    /**
     * arr中都是整数且没有重复的值 返回组成aim的方法数
     */
    public static int ways(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        return process(arr, 0, aim);
    }

    /**
     * 可以自由使用arr[index]~arr[lenght-1]的所有数字
     * 每个数字可以使用任意次数
     * 问题：有多少种方法 把所有数字加到rest
     */
    public static int process(int[] arr, int index, int rest) {
        //可以不写这段判断 因为for循环 里面 调用递归 rest-zhang * arr[index]保证了 rest > 0
        if (rest < 0) {
            return 0;
        }
        //rest >= 0
        if (index == arr.length) {//表示现在没有数字可以选择了
            if (rest == 0) {//表示把数字加到了和rest一样的数
                //返回1 表示 有一种方法叫做 数字的序列
                return 1;
            } else {
                // 表示利用数组里的数字 加不到和rest一样
                // 返回 0 表示搞不定
                return 0;
            }
        }
        /**
         * 表示有几种方法 因为有方法的话 就是
         * if(rest == 0){return 1;}
         * 导致ways = ways +1
         * */
        int ways = 0; //表示有几种方法 因为有方法的话 就是
        // 当前数字 arr[index] 先选择某一个硬币 在给定的硬币条件下 设定 硬币的张数 eg： 硬币 20元 张数2  总价值40
        for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
            /**
             * eg: 有一个数组 [10,2......]  rest = 1000 分类讨论
             * 如果 使用了arr[0]也就是10 0次的话 就是process(arr, 1, 1000) 再去讨论 使用arr[1]的情况
             * 如果 使用了arr[0]也就是10 1次的话 就是process(arr, 1, 990) 再讨论 使用arr[1]的情况
             * 如果 使用了arr[0]也就是10 2次的话 就是process(arr, 1, 980) 再讨论 使用arr[1]的情况
             * ....
             * 如果 使用了arr[0]也就是10 100次的话 就是process(arr, 1, 0) 不用讨论了 利用10 这个数字 已经达到了rest
             * 对于arr[1]的分析方法和上面相同
             * 针对 如果 使用了arr[0]也就是10 0次的话 就是process(arr, 1, 1000) 再去讨论 使用arr[1]的情况
             * 就有 如果 使用了arr[1]也就是2 0次的话 就是process(arr, 1, 1000) 再去讨论 使用arr[2]的情况
             * 如果 使用了arr[1]也就是2 1次的话 就是process(arr, 1, 998) 再去讨论 使用arr[2]的情况
             * */
            ways += process(arr, index + 1, rest - zhang * arr[index]);
        }
        return ways;
    }

    public static int ways2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        /*
         * 创建一个缓存
         * eg: [10,100,50......]
         * 不管
         * 0张10 1张100 0张50
         * 0张10 0张100 2张50
         * 都是f(3,900) 返回值相同
         * 不管什么之前的决定 只要导致了相同的index和rest f(index,rest)一定返回相同的值
         * 函数中的index和rest一旦确定了 就确定了返回值  arr固定的
         * 如果 index = 3 rest =900 那么map的key就是 "3_900"
         * */
        HashMap<String, Integer> map = new HashMap<>();
        //行是index 列是rest
        int[][] dp = new int[arr.length + 1][aim + 1];
        //一开始所有的过程 都没有计算
        //初始化成-1
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                dp[i][j] = -1;
            }
        }
        return process2(arr, 0, aim, dp);
    }

    public static int process2(int[] arr, int index, int rest, int[][] dp) {
        if (dp[index][rest] != -1) {
            //不等于-1说明已经算过了不同重复计算
            return dp[index][rest];
        }
        //可以不写这段判断 因为for循环 里面 调用递归 rest-zhang * arr[index]保证了 rest > 0
        if (rest < 0) {
            return 0;
        }
        //rest >= 0
        if (index == arr.length) {//表示现在没有数字可以选择了
            if (rest == 0) {//表示可以把数字加到了和rest一样的数
                //返回1
                dp[index][rest] = 1;
                return dp[index][rest];//不同于递归 这里是返回缓存
            } else {
                // 表示利用数组里的数字 加不到和rest一样
                // 返回 0 表示搞不定
                dp[index][rest] = 0;
                return dp[index][rest];
            }
        }
        /**
         * 表示有几种方法 因为有方法的话 就是
         * if(rest == 0){return 1;}
         * 导致ways = ways +1
         * */
        int ways = 0; //表示有几种方法 因为有方法的话 就是
        // 当前数字 arr[index]
        for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
            /**
             * eg: 有一个数组 [10,2......]  rest = 1000 分类讨论
             * 如果 使用了arr[0]也就是10 0次的话 就是process(arr, 1, 1000) 再去讨论 使用arr[1]的情况
             * 如果 使用了arr[0]也就是10 1次的话 就是process(arr, 1, 990) 再讨论 使用arr[1]的情况
             * 如果 使用了arr[0]也就是10 2次的话 就是process(arr, 1, 980) 再讨论 使用arr[1]的情况
             * ....
             * 如果 使用了arr[0]也就是10 100次的话 就是process(arr, 1, 0) 不用讨论了 利用10 这个数字 已经达到了rest
             * 对于arr[1]的分析方法和上面相同
             * 针对 如果 使用了arr[0]也就是10 0次的话 就是process(arr, 1, 1000) 再去讨论 使用arr[1]的情况
             * 就有 如果 使用了arr[1]也就是2 0次的话 就是process(arr, 1, 1000) 再去讨论 使用arr[2]的情况
             * 如果 使用了arr[1]也就是2 1次的话 就是process(arr, 1, 998) 再去讨论 使用arr[2]的情况
             * */
            ways += process2(arr, index + 1, rest - zhang * arr[index], dp);
        }
        return ways;
    }

    public static int ways3(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int N = arr.length;
        /**
         * 创建一个缓存
         * eg: [10,100,50......]
         * 不管
         * 0张10 1张100 0张50
         * 0张10 0张100 2张50
         * 都是f(3,900) 返回值相同
         * 不管什么之前的决定 只要导致了相同的index和rest f(index,rest)一定返回相同的值
         * 函数中的index和rest一旦确定了 就确定了返回值  arr固定的
         * */
        //行是index 列是rest
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        /**
         * 这张表的某一行某一列的值 是依赖于下一行的值 因为 ways += process2(arr,index+1,rest-zhang * arr[index],dp);
         * index+1
         * 所以整张表的填写就是 从下向上求解
         * 具体到每一行 求解顺序从左向右
         * */
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                /*
                 * 表示有几种方法 因为有方法的话 就是
                 * if(rest == 0){return 1;}
                 * 导致ways = ways +1
                 *
                int ways= 0 ; //表示有几种方法 因为有方法的话 就是
                for(int zhang = 0; zhang * arr[index] <= rest ; zhang++){
                    ways += process2(arr,index+1,rest-zhang * arr[index],dp);
                }
                return ways;
                */
                int ways = 0; //表示有几种方法 因为有方法的话 就是
                // 当前数字 arr[index]
                for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
                    ways += dp[index + 1][rest - (zhang * arr[index])];
                }
                dp[index][rest] = ways;
            }
        }
        return dp[0][aim];
    }

    public static int ways4(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int N = arr.length;
        /**
         * 创建一个缓存
         * eg: [10,100,50......]
         * 不管
         * 0张10 1张100 0张50
         * 0张10 0张100 2张50
         * 都是f(3,900) 返回值相同
         * 不管什么之前的决定 只要导致了相同的index和rest f(index,rest)一定返回相同的值
         * 函数中的index和rest一旦确定了 就确定了返回值  arr固定的
         * */
        //行是index 列是rest
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        /**
         * 这张表的某一行某一列的值 是依赖于下一行的值 因为 ways += process2(arr,index+1,rest-zhang * arr[index],dp);
         * index+1
         * 所以整张表的填写就是 从下向上求解
         * 具体到每一行 求解顺序从左向右
         * */
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                /**
                 * 判定条件意思是 不越界的话
                 * 整张表格 列是rest（0~aim） 行是（0~N）
                 * 求解的目标是 第0行第aim列的数据 因为 主函数process(arr, 0, aim);
                 * 整个表格求解的顺序是从下到上  每行是从左到右
                 * 从下到上原因是从函数中ways += process(arr, index + 1, rest - zhang * arr[index]);
                 * 依赖于index+1行的数据
                 * eg： rest=100  arr[i]=3
                 * 那么第i行就是代表3面值的一行
                 * 因为每一行的数据取决于下一行的数据
                 * arr[i][100]=arr[i+1][100]+ arr[i+1][100-3*1]+arr[i+1][100-3*2]...
                 * arr[i][100-3*1]=arr[i+1][100-3*1]+arr[i+1][100-3*2]+arr[i+1][100-3*3]..
                 * 说明arr[i][100]=arr[i][100-3*1]+arr[i+1][100]
                 * */
                if (rest - arr[index] >= 0) {
                    dp[index][rest] += dp[index][rest - arr[index]];
                }
            }
        }
        return dp[0][aim];
    }

    public static void main(String[] args) {
        int[] arr = {5, 10, 50, 100};
        int sum = 1000;
        System.out.println(ways(arr, sum));
        System.out.println(ways2(arr, sum));
        System.out.println(ways3(arr, sum));
        System.out.println(ways4(arr, sum));
    }
}
