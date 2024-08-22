package other;

public class Code2_CoinsMin {

    //保证arr中都是正数
    public static int minCoin1(int[] arr, int aim) {
        return process01(arr, 0, aim);
    }

    // arr硬币都在其中，固定参数
    // aim:最终要达成的目标,固定参数
    //如果自由选择arr[index... . .]这些硬币，
    // 最后组成aim的方法数返回
    public static int process01(int[] arr, int index, int rest) {
        if (rest < 0) {//当rest< 0说明到目前为止 这个选择路径不可行
            return -1;
        }
        if (rest == 0) {//当rest=0说明已经达到aim了 就是用0个硬币
            return 0;
        }
        //如果已经选到最后一个硬币了 还是rest!=0 说明这个选择路径不可行
        if (index == arr.length && rest > 0) {
            return -1;
        }
        /*
         * rest>0 并且 还有硬币的情况下
         * 有2个分支
         * 一个分支是选择当前的硬币
         * 另一个分支是不选择当前的硬币
         * 相当于一个暴力递归数中的某一个节点的2个分支
         * 这两个分支取出最好的 也就是min
         * */
        int p1 = process01(arr, index + 1, rest);
        int p2 = 1 + process01(arr, index + 1, rest - arr[index]);
        if (p1 == -1 && p2 == -1) {//如果两个分支得到的rest<0 那么都是错误
            return -1;
        } else {
            if (p1 == -1) {
                return p2 + 1;
            }
            if (p2 == -1) {
                return p1;
            }
            //如果两个分支都!=-1
            return Math.min(p1, p2);
        }
    }

    /*
     * 动态规划 之一 记忆化搜索
     * 暴力递归有重复计算 加缓存
     * 暴力递归的分析过程抽象出来就是dp的转移方程
     * 所有dp都来自于暴力递归
     * 为什么dp表的 行：index   列：rest 因为这两个是可变参数 只要这两个固定了 结果就出来了
     * */
    public static int minCoin2(int[] arr, int aim) {
        /*
         *
         * */
        int[][] dp = new int[arr.length + 1][aim + 1];
        for (int i = 0; i <= arr.length; i++) {
            for (int j = 0; j <= aim; j++) {
                //因为-1是迎来表示 说明到目前为止 这个选择路径不可行
                dp[i][j] = -2;
            }
        }
        return process02(arr, 0, aim, dp);
    }

    public static int process02(int[] arr, int index, int rest, int[][] dp) {
        if (rest < 0) {//当rest< 0说明到目前为止 这个选择路径不可行
            return -1;
        }
        if (dp[index][rest] != -2) {
            return dp[index][rest];//说明之前已经记录过了 不用做任何事
        }
        if (rest == 0) {//当rest=0说明已经达到aim了 就是用0个硬币
            dp[index][rest] = 0;//return 0;
        } else if (index == arr.length && rest > 0) {//如果已经选到最后一个硬币了 还是rest!=0 说明这个选择路径不可行
            dp[index][rest] = -1;//return -1;
        } else {
            /*
             * rest>0 并且 还有硬币的情况下
             * 有2个分支
             * 一个分支是选择当前的硬币
             * 另一个分支是不选择当前的硬币
             * 相当于一个暴力递归数中的某一个节点的2个分支
             * 这两个分支取出最好的 也就是min
             * */
            int p1 = process02(arr, index + 1, rest, dp);
            int p2 = 1 + process02(arr, index + 1, rest - arr[index], dp);
            if (p1 == -1 && p2 == -1) {//如果两个分支得到的rest<0 那么都是错误
                dp[index][rest] = -1;//return -1;
            } else {
                if (p1 == -1) {
                    dp[index][rest] = p2 + 1;//return p2+1;
                }
                if (p2 == -1) {
                    dp[index][rest] = p1;//return p1;
                }
                //如果两个分支都!=-1
                dp[index][rest] = Math.min(p1, p2+1);//return Math.min(p1 ,p2);
            }
        }
        return dp[index][rest];
    }

    public static int minCoin3(int[] arr, int aim) {
        int N = arr.length;
        int[][] dp = new int[arr.length + 1][aim + 1];
        for (int row = 0; row <= N; row++) {
            dp[row][0] = 0;
        }
        for (int col = 0; col <= aim; col++) {
            dp[N][col] = -1;
        }

        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 1; rest <= aim; rest++) {
                /*if (rest < 0) {//当rest< 0说明到目前为止 这个选择路径不可行
                    return -1;
                }
                if (dp[index][rest] != -2) {
                    return dp[index][rest];//说明之前已经记录过了 不用做任何事
                }
                if (rest == 0) {//当rest=0说明已经达到aim了 就是用0个硬币
                    dp[index][rest] = 0;
                } else if (index == arr.length && rest > 0) {//如果已经选到最后一个硬币了 还是rest!=0 说明这个选择路径不可行
                    dp[index][rest] = -1;
                }*/
                /*
                 * rest>0 并且 还有硬币的情况下
                 * 有2个分支
                 * 一个分支是选择当前的硬币
                 * 另一个分支是不选择当前的硬币
                 * 相当于一个暴力递归数中的某一个节点的2个分支
                 * 这两个分支取出最好的 也就是min
                 * 不同于上面的做法 分支的结果直接从dp表里面拿值
                 *
                 * 查看依赖 发现这个dp表示从下往上 从左往右建立的
                 * index行 rest列的数据 依赖于 index+1行rest列 和index+1行rest - arr[index]列的数据
                 *   int p1 = process01(arr, index + 1, rest);
                 *   int p2 = 1 + process01(arr, index + 1, rest - arr[index]);
                 * */
                int p1 = dp[index+1][rest];//int p1 = process02(arr, index + 1, rest, dp);
                int p2=-1;//声明p2的时候给个初始值
                if(rest-arr[index]>=0){
                    p2= dp[index+1][rest-arr[index]];//int p2 = 1 + process02(arr, index + 1, rest - arr[index], dp);
                }
                if (p1 == -1 && p2 == -1) {//如果两个分支得到的rest<0 那么都是错误
                    dp[index][rest] = -1;//return -1;
                } else {
                    if (p1 == -1) {
                        dp[index][rest] = p2 + 1;//return p2+1;
                    }
                    if (p2 == -1) {
                        dp[index][rest] = p1;//return p1;
                    }
                    //如果两个分支都!=-1
                    dp[index][rest] = Math.min(p1, p2+1);//return Math.min(p1 ,p2)
                }
            }
        }
        //因为暴力递归调用主函数 return process01(arr, 0, aim);
        return dp[0][aim];
    }
}