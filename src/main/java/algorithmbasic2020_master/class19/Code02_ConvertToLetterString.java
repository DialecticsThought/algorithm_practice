package algorithmbasic2020_master.class19;

/**
 * TODO 规定1和A对应、2和B对应、3和C对应...26和Z对应那么一个数字字符串
 * 比如"111”就可以转化为:"AAA"、“KA"和"AK"
 * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果
 * 从左到右的尝试模型 根据要划分的多大
 * str = "11111"
 *          11111
 *      1_A  ↙   ↘  11_K
 *       1111          111
 *   1_A ↙ ↘ 11_K   1_A ↙ ↘ 11_K
 *    111     11        11    1
 * 1_A ↙ ↘ 11_K     1_A ↙ ↘ 11_K
 * 11    1             1   ""
 * str = 10
 *         10
 *  1_A  ↙   ↘  10_J
 *      0    返回J
 * i位置上的字符 1~9 有2种选择
 * i位置转化 i+1递归
 * i和i+1位置 转化 i+2递归
 * i位置不能是字符0 ，因为不能转化
 * TODO
 * 因为暴力递归的函数f(char[] str,int i)
 * dp表是一个一维数组
 * base case if(i == str.length) return i;
 * 说明dp表是一个一维arr，最后一个元素是1
 * 查看递归函数知道dp[i]依赖于dp[i+1]和dp[i+2]的值 ==> dp表从后往前填写
 * eg: n个字符的str dp的length=n+1
 * n=5 dp如下
 * 0 1 2 3 4 5 6
 * ? ? ? ? ? ? 1
 */
public class Code02_ConvertToLetterString {

    // str只含有数字字符0~9
    // 返回多少种转化方案
    public static int number(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        return process(str.toCharArray(), 0);
    }

    //TODO str[0..i-1]转化无需过问 因为已经转化过了
    //TODO str[i.....]去转化，返回有多少种转化方法
    public static int process(char[] str, int i) {
        if (i == str.length) { //i来到终止位置
            return 1;//没有字符的时候 能转化成空字符
        }
        // i没到最后，说明有字符
        if (str[i] == '0') { //TODO 之前的决定有问题
            /**
             *TODO
             * “10”
             * 但也可以转成A 但是出现了0
             * 就停止转化 直接返回0之前已经转化的 表示这个分支不能结束 返回上一级 看看 10还能转化成什么
             * 发现另一个分支 可以一下子转成J 并且返回1 表示结束
             * */
            return 0;
        }
        //TODO str[i] != '0' 从A开始 但是 A =1
        // 可能性一，i单转
		/*int ways = process(str, i + 1);
		if (i + 1 < str.length && (str[i] - '0') * 10 + str[i + 1] - '0' < 27) {
			ways += process(str, i + 2);
		}
		return ways;*/
        if (str[i] == '1') {
            int res = process(str, i + 1); //TODO i自己作为单独的部分，后续有多少种方法
            if (i + 1 < str.length) {//TODO i和i+1位置一起转化 但是排除i已经是最后一个位置
                res += process(str, i + 2);//TODO (i和i+1)作为单独的部分，后续有多少种方法
            }
            return res;
        }
        if (str[i] == '2') {
            int res = process(str, i + 1); //TODO i自己作为单独的部分，后续有多少种方法
            //TODO (i和i+1)作为单独的部分并且没有超过26，后续有多少种方法
            if (i + 1 < str.length && (str[i + 1] >= '0' && str[i + 1] <= '6')) {
                res += process(str, i + 2); //TODO (i和i+1)作为单独的部分，后续有多少种方法
            }
            return res;
        }
        /*
         *TODO
         * 因为 转发只有两种选择
         * i位置转化 i+1递归
         * i和i+1位置一起转化 i+2递归
         * 但是只有26个字母 所以i不能超过2 i∈[3,9]就会出现问题
         * str[i]== '3’~ '9" 直接转化
         * */
        return process(str, i + 1);
    }

    /**
     * 从右往左的动态规划
     * 就是上面方法的动态规划版本
     * dp[i]表示：str[i...]有多少种转化方式
     *
     * @param s
     * @return
     */
    public static int dp1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[] dp = new int[N + 1];
        /**
         *  if (i == str.length) { //i来到终止位置
         *    return 1;//没有字符的时候 能转化成空字符
         *  }
         *  说明表的第n列全是1
         * */
        dp[N] = 1;
        for (int i = N - 1; i >= 0; i--) {
            if (str[i] == '0') {
                /**
                 *  i没到最后，说明有字符
                 *  if (str[i] == '0') { // 之前的决定有问题
                 * “10” 但也可以转成A 但是出现了0
                 * 就停止转化 直接返回0之前已经转化的 表示这个分支不能结束 返回上一级 看看 10还能转化成什么
                 * 发现另一个分支 可以一下子转成J 并且返回1 表示结束
                 *     return 0;
                 *    }
                 *
                 * */
                dp[i] = 0;
            }
            /**
             * dp表中的第i列依赖于i+1和i+2
             * return的位置就是设置dp[i]的地方
             * */
            if (str[i] == '1') {
                /**
                 *  if (str[i] == '1') {
                 * int res = process(str, i + 1); // i自己作为单独的部分，后续有多少种方法
                 * if (i + 1 < str.length) {//i和i+1位置一起转化 但是排除i已经是最后一个位置
                 *     res += process(str, i + 2);// (i和i+1)作为单独的部分，后续有多少种方法
                 * }
                 *  return res;
                 * }
                 * */
                dp[i] = dp[i + 1];
                if (i + 1 < str.length) {
                    dp[i] += dp[i + 2];
                }
            }
            if (str[i] == '2') {
                /**
                 *  if (str[i] == '2') {
                 * int res = process(str, i + 1); // i自己作为单独的部分，后续有多少种方法
                 * (i和i+1)作为单独的部分并且没有超过26，后续有多少种方法
                 * if (i + 1 < str.length && (str[i + 1] >= '0' && str[i + 1] <= '6')) {
                 *    res += process(str, i + 2); //(i和i+1)作为单独的部分，后续有多少种方法
                 * }
                 * return res;
                 *  }
                 *  */
                dp[i] = dp[i + 1];
                if (i + 1 < str.length && (str[i + 1] >= '0' && str[i + 1] <= '6')) {
                    dp[i] += dp[i + 2];
                }
            }
        }
        // 因为递归的主函数process(str.toCharArray(), 0);
        return dp[0];
    }

    /**
     * 从左往右的动态规划
     * dp[i]表示：str[0...i]有多少种转化方式
     * 这也说明对于这一维动态表  最后一个元素 一定是已知的
     *
     * @param s
     * @return
     */
    public static int dp2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        if (str[0] == '0') {
            return 0;
        }
        int[] dp = new int[N];
        /*
         *  if (i == str.length) { //i来到终止位置
         *    return 1;//没有字符的时候 能转化成空字符
         *  }
         *  说明表的第n列全是1
         * */
        dp[0] = 1;
        for (int i = 1; i < N; i++) {
            if (str[i] == '0') {
                // 如果此时str[i]=='0'，那么他是一定要拉前一个字符(i-1的字符)一起拼的，
                // 那么就要求前一个字符，不能也是‘0’，否则拼不了。
                // 前一个字符不是‘0’就够了嘛？不够，还得要求拼完了要么是10，要么是20，如果更大的话，拼不了。
                // 这就够了嘛？还不够，你们拼完了，还得要求str[0...i-2]真的可以被分解！
                // 如果str[0...i-2]都不存在分解方案，那i和i-1拼成了也不行，因为之前的搞定不了。
                /*
                 * if (str[i] == '1') {
                 * int res = process(str, i + 1); // i自己作为单独的部分，后续有多少种方法
                 * if (i + 1 < str.length) {//i和i+1位置一起转化 但是排除i已经是最后一个位置
                 *     res += process(str, i + 2);// (i和i+1)作为单独的部分，后续有多少种方法
                 * }
                 *  return res;
                 * }
                 *         if (str[i] == '2') {
                 *int res = process(str, i + 1); // i自己作为单独的部分，后续有多少种方法
                 * (i和i+1)作为单独的部分并且没有超过26，后续有多少种方法
                 *if (i + 1 < str.length && (str[i + 1] >= '0' && str[i + 1] <= '6')) {
                 *    res += process(str, i + 2); //(i和i+1)作为单独的部分，后续有多少种方法
                 *}
                 *return res;
                 *}
                 * dp表中的第i列依赖于i+1和i+2
                 *
                 * return的位置就是设置dp[i]的时刻
                 * */
                if (str[i - 1] == '0' || str[i - 1] > '2' || (i - 2 >= 0 && dp[i - 2] == 0)) {
                    return 0;
                } else {
                    dp[i] = i - 2 >= 0 ? dp[i - 2] : 1;
                }
            } else {
                dp[i] = dp[i - 1];
                if (str[i - 1] != '0' && (str[i - 1] - '0') * 10 + str[i] - '0' <= 26) {
                    dp[i] += i - 2 >= 0 ? dp[i - 2] : 1;
                }
            }
        }
        return dp[N - 1];
    }

    public static int dp3(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        if (str[0] == '0') {
            return 0;
        }
        int[] dp = new int[N + 1];
        /*
         *  if (i == str.length) { //i来到终止位置
         *    return 1;//没有字符的时候 能转化成空字符
         *  }
         *  说明表的第n列全是1
         * */
        dp[N] = 1;
        for (int i = N - 1; i >= 0; i--) {
            /*
             * if (str[i] == '1') {
             * int res = process(str, i + 1); // i自己作为单独的部分，后续有多少种方法
             * if (i + 1 < str.length) {//i和i+1位置一起转化 但是排除i已经是最后一个位置
             *     res += process(str, i + 2);// (i和i+1)作为单独的部分，后续有多少种方法
             * }
             *  return res;
             * }
             *         if (str[i] == '2') {
             *int res = process(str, i + 1); // i自己作为单独的部分，后续有多少种方法
             * (i和i+1)作为单独的部分并且没有超过26，后续有多少种方法
             *if (i + 1 < str.length && (str[i + 1] >= '0' && str[i + 1] <= '6')) {
             *    res += process(str, i + 2); //(i和i+1)作为单独的部分，后续有多少种方法
             *}
             *return res;
             *}
             * dp表中的第i列依赖于i+1和i+2
             *
             * return的位置就是设置dp[i]的时刻
             * */
            if (str[i] == '0') {
                // 如果此时str[i]=='0'，那么他是一定要拉前一个字符(i-1的字符)一起拼的，
                // 那么就要求前一个字符，不能也是‘0’，否则拼不了。
                // 前一个字符不是‘0’就够了嘛？不够，还得要求拼完了要么是10，要么是20，如果更大的话，拼不了。
                // 这就够了嘛？还不够，你们拼完了，还得要求str[0...i-2]真的可以被分解！
                // 如果str[0...i-2]都不存在分解方案，那i和i-1拼成了也不行，因为之前的搞定不了。
                if (str[i - 1] == '0' || str[i - 1] > '2' || (i - 2 >= 0 && dp[i - 2] == 0)) {
                    return 0;
                } else {
                    dp[i] = i - 2 >= 0 ? dp[i - 2] : 1;
                }
            } else {
                dp[i] = dp[i - 1];
                if (str[i - 1] != '0' && (str[i - 1] - '0') * 10 + str[i] - '0' <= 26) {
                    dp[i] += i - 2 >= 0 ? dp[i - 2] : 1;
                }
            }
        }
        return dp[N - 1];
    }

    // 为了测试
    public static String randomString(int len) {
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ((int) (Math.random() * 10) + '0');
        }
        return String.valueOf(str);
    }

    // 为了测试
    public static void main(String[] args) {
        int N = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            String s = randomString(len);
            int ans0 = number(s);
            int ans1 = dp1(s);
            int ans2 = dp2(s);
            if (ans0 != ans1 || ans0 != ans2) {
                System.out.println(s);
                System.out.println(ans0);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }

}
