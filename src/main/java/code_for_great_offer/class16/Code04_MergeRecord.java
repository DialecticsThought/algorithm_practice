package code_for_great_offer.class16;

public class Code04_MergeRecord {

    /**
     * 腾讯原题
     *TODO
     * 给定整数power，给定一个数组arr，给定一个数组reverse。含义如下：
     * arr的长度一定是2的power次方，reverse中的每个值一定都在0~power范围。
     * 例如power = 2, arr = {3, 1, 4, 2}，reverse = {0, 1, 0, 2}
     * 任何一个在前的数字可以和任何一个在后的数组，构成一对数。可能是升序关系、相等关系或者降序关系。
     * 比如arr开始时有如下的降序对：(3,1)、(3,2)、(4,2)，一共3个。
     * 接下来根据reverse对arr进行调整：
     * reverse[0] = 0, 表示在arr中，划分每1(2的0次方)个数一组，然后每个小组内部逆序，那么arr变成
     * [3,1,4,2]，此时有3个逆序对。[3,1] [3,2] [4,2]
     * reverse[1] = 1, 表示在arr中，划分每2(2的1次方)个数一组，然后每个小组内部逆序，那么arr变成
     * [1,3,2,4]，此时有1个逆序对	[3,2]
     * reverse[2] = 0, 表示在arr中，划分每1(2的0次方)个数一组，然后每个小组内部逆序，那么arr变成
     * [1,3,2,4]，此时有1个逆序对。 [3,2]
     * reverse[3] = 2, 表示在arr中，划分每4(2的2次方)个数一组，然后每个小组内部逆序，那么arr变成
     * [4,2,3,1]，此时有5个逆序对。[4,2] [4,3] [4,1] [3,1] [2,1]
     * 所以返回[3,1,1,5]，表示每次调整之后的逆序对数量。
     * 输入数据状况：
     * power的范围[0,20]
     * arr长度范围[1,10的7次方]
     * reverse长度范围[1,10的6次方]
     *TODO
     * 问题1：求逆序对的方法：
     * 方法1：
     * eg:
     * [6,4,2,5,1,3,0,7] 6+4+2+3+1+1+0
     * 6:[6,4] [6,2] [6,5] [6,1] [6,3] [6,0]
     * 4:[4,2] [4,1] [4,3] [4,0]
     * 2:[2,1] [2,0]
     * 3:[5,1] [5,3] [5,0]
     * ....
     * 方法2：
     * 1.以2^1 个元素为一组有几个逆序对:(6,4),(2,5),(1,3),(0,7) 也就是(6,4)
     * 2.以2^2 个元素为一组有几个逆序对 但是不能重新回到条件1
     *  本质是 先4个元素一组 [6,4,2,5] [1,3,0,7],再[6,4 | 2,5]  [1,3 | 0,7]划分成左和右部分
     *  左部分的每一个元素遍历到时，再去遍历右部分的元素： (6,2) (6,5) (4,2) (4,5) (3,0) (3,7) (1,0) (1,7)
     *  也就是(6,2) (6,5) (4,2) (3,0) (1,0)
     * 3.以2^3 个元素为一组有几个逆序对 但是不能重新回到条件1,2
     *  本质是 先8个元素一组 [6,4,2,5,1,3,0,7],再[6,4,2,5 | 1,3,0,7]划分成左和右部分
     *  左部分的每一个元素遍历到时，再去遍历右部分的元素：(2,1) (2,3) (2,0) (2,7) (5,1) (5,3) (5,0) (5,7)
     *          (6,1) (6,3) (6,0) (6,7) (4,1) (4,3) (4,0) (4,7)
     *  也就是(6,1) (6,3) (6,0) (4,1) (4,3) (4,0)
     * eg2:
     * 有个8元素的arr
     * 假设 2^1为1组有逆序对a个  正序对b个
     * 假设 2^2为1组有逆序对c个  正序对d个
     * 假设 2^3为1组有逆序对e个  正序对f个
     * 也就是
     *  [a,b]
     *  [c,d]
     *  [e,f]
     * 现在假设 reverse[i] = 4 也就是要求以2^2为1组 数组元素逆序再求逆序对
     *  以2^2为1组不会影响原先2^3为1组求逆序对的结果 但会影响 原先2^1为1组求逆序对的结果和原先2^2为1组求逆序对的结果
     * 也就是变成
     *  [b,a]
     *  [d,c]
     *  [e,f]
     *  以2^2为1组不会影响原先2^3为1组求逆序对的结果的原因
     *      求逆序对的时候每4个元素为一组，并且每4个元素再分成左右2各部分，
     *      左部分的每一个元素遍历到时，再去遍历右部分的元素，
     *      这个操作不会影响到每8个元素为一组，因为4个元素为一组的逆序，不会影响到8个元素一组的相对次序
     *      4个元素为一组的逆序之后，还是在8个元素为一组的某一个左部分，或者右部分，不会从左部分变成右部分
     *  FINAL: 要求以2^2为1组 数组元素逆序再求逆序对  = b+d+e
     * eg2:
     * 有个16元素的arr
     * 假设 2^1为1组有逆序对a个  正序对b个
     * 假设 2^2为1组有逆序对c个  正序对d个
     * 假设 2^3为1组有逆序对e个  正序对f个
     * 假设 2^4为1组有逆序对g个  正序对h个
     * 也就是
     *  [a,b]
     *  [c,d]
     *  [e,f]
     *  [g,h]
     * 现在假设 reverse[i] = 2 也就是要求以2^1为1组 数组元素逆序再求逆序对
     * 也就是变成
     *  [b,a]
     *  [c,d]
     *  [e,f]
     *  [g,h]
     *  次轮答案: b+c+e+g
     * 现在假设 reverse[i] = 4 也就是要求以2^3为1组 数组元素逆序再求逆序对
     * 也就是变成
     *  [a,b]
     *  [d,c]
     *  [e,f]
     *  [g,h]
     *  次轮答案: a+d+e+g
     * 现在假设 reverse[i] = 8 也就是要求以2^3为1组 数组元素逆序再求逆序对
     * 也就是变成
     *  [b,a]
     *  [c,d]
     *  [f,e]
     *  [g,h]
     *  次轮答案: c+b+f+g
     * 现在假设 reverse[i] = 16 也就是要求以2^3为1组 数组元素逆序再求逆序对
     * 也就是变成
     *  [a,b]
     *  [d,c]
     *  [e,f]
     *  [h,g]
     *  次轮答案: a+d+e+h
     *  FINAL : a+d+e+h  +  c+b+f+g + a+d+e+g +  b+c+e+g
     * eg4:
     * [6,4,2,5,3,1,7,0]
     * 以2个数为1组，有逆序对(6,4) (7,0) (3,1) 3个 正序对(2,5) 1个 [6 | 4] [2 | 5] [3 | 1] [7 | 0]
     * 以4个数为1组，有逆序对(6,2) (6,5) (3,0) (4,2) (1,0) 5个 正序对(4,5) (3,7) (1,7)  3个 [6,4 | 2,5] [3,1 | 7,0]
     * 以8个数为1组，有逆序对(6,3) (6,1)  (6,0) (4,3) (4,1) (4,0) (5,3) (5,1) (5,0) 5个 正序对(6,7) (4,7) (2,3) (2,7)  (5,7) 3个
     * 总结
     * 2:[3,1]
     * 4:[5,3]
     * 8:[11,5]
     * reverse=2 => newArr = [5,2,4,6,0,7,1,3]
     * 对于以reverse=2逆序之后的newArr 以8个数为1组 之后 逆序对是11个，正序对5个
     * 但以4个数为1组，有逆序对 3 正序对5个
     * 但以2个数为1组，有逆序对 1 正序对3个
     * FINAL: 11+3+1
     */

    public static int[] reversePair1(int[] originArr, int[] reverseArr, int power) {
        int[] ans = new int[reverseArr.length];
        for (int i = 0; i < reverseArr.length; i++) {
            reverseArray(originArr, 1 << (reverseArr[i]));
            ans[i] = countReversePair(originArr);
        }
        return ans;
    }

    public static void reverseArray(int[] originArr, int teamSize) {
        if (teamSize < 2) {
            return;
        }
        for (int i = 0; i < originArr.length; i += teamSize) {
            reversePart(originArr, i, i + teamSize - 1);
        }
    }

    public static void reversePart(int[] arr, int L, int R) {
        while (L < R) {
            int tmp = arr[L];
            arr[L++] = arr[R];
            arr[R--] = tmp;
        }
    }

    public static int countReversePair(int[] originArr) {
        int ans = 0;
        for (int i = 0; i < originArr.length; i++) {
            for (int j = i + 1; j < originArr.length; j++) {
                if (originArr[i] > originArr[j]) {
                    ans++;
                }
            }
        }
        return ans;
    }

    /**
     * 重点 ☆☆☆☆
     * @param originArr
     * @param reverseArr
     * @param power
     * @return
     */
    public static int[] reversePair2(int[] originArr, int[] reverseArr, int power) {
        int[] reverse = copyArray(originArr);

        reversePart(reverse, 0, reverse.length - 1);
        /**
         *降序信息
         * 划分每1(2的0次方)个数一组 一共有几个降序对
         * 划分每1(2的1次方)个数一组 一共有几个降序对
         * 划分每1(2的2次方)个数一组 一共有几个降序对
         * ......
         * */
        int[] recordDown = new int[power + 1];//降序信息
        /**
         *升序的信息
         * 划分每1(2的0次方)个数一组 一共有几个升序对
         * 划分每1(2的1次方)个数一组 一共有几个升序对
         * 划分每1(2的2次方)个数一组 一共有几个升序对
         * ......
         * */
        int[] recordUp = new int[power + 1];
        /**
         * 先求原始arr的逆序对
         * 再求原始arr的正序对(利用逆序对求逆序对)
         */
        //利用递归函数填写好降序信息
        process(originArr, 0, originArr.length - 1, power, recordDown);
        //利用递归函数和逆序数组填写好升序信息
        process(reverse, 0, reverse.length - 1, power, recordUp);
        int[] ans = new int[reverseArr.length];
        for (int i = 0; i < reverseArr.length; i++) {
            int curPower = reverseArr[i];
            //每个组的逆序对和正序对 该翻转的翻转
            for (int p = 1; p <= curPower; p++) {
                int tmp = recordDown[p];
                recordDown[p] = recordUp[p];
                recordUp[p] = tmp;
            }
            for (int p = 1; p <= power; p++) {
                ans[i] += recordDown[p];
            }
        }
        return ans;
    }

    // originArr[L...R]完成排序！
    // L...M左  M...R右  merge
    // L...R  2的power次方

    public static void process(int[] originArr, int L, int R, int power, int[] record) {
        if (L == R) {
            return;
        }
        int mid = L + ((R - L) >> 1);
        process(originArr, L, mid, power - 1, record);
        process(originArr, mid + 1, R, power - 1, record);
        //一定要累加 因为 8 二分成 2个4，一个4 二分成 2个2 ，2个2计算好 累加在一起就是4的答案 其他同理
        record[power] += merge(originArr, L, mid, R);
    }

    public static int merge(int[] arr, int L, int m, int r) {
        int[] help = new int[r - L + 1];
        int i = 0;
        int p1 = L;
        int p2 = m + 1;
        int ans = 0;
        while (p1 <= m && p2 <= r) {
            ans += arr[p1] > arr[p2] ? (m - p1 + 1) : 0;
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= m) {
            help[i++] = arr[p1++];
        }
        while (p2 <= r) {
            help[i++] = arr[p2++];
        }
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
        return ans;
    }

    // for test
    public static int[] generateRandomOriginArray(int power, int value) {
        int[] ans = new int[1 << power];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value);
        }
        return ans;
    }

    // for test
    public static int[] generateRandomReverseArray(int len, int power) {
        int[] ans = new int[len];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * (power + 1));
        }
        return ans;
    }

    // for test
    public static void printArray(int[] arr) {
        System.out.println("arr size : " + arr.length);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int powerMax = 8;
        int msizeMax = 10;
        int value = 30;
        int testTime = 50000;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int power = (int) (Math.random() * powerMax) + 1;
            int msize = (int) (Math.random() * msizeMax) + 1;
            int[] originArr = generateRandomOriginArray(power, value);
            int[] originArr1 = copyArray(originArr);
            int[] originArr2 = copyArray(originArr);
            int[] reverseArr = generateRandomReverseArray(msize, power);
            int[] reverseArr1 = copyArray(reverseArr);
            int[] reverseArr2 = copyArray(reverseArr);
            int[] ans1 = reversePair1(originArr1, reverseArr1, power);
            int[] ans2 = reversePair2(originArr2, reverseArr2, power);
            if (!isEqual(ans1, ans2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");

        powerMax = 20;
        msizeMax = 1000000;
        value = 1000;
        int[] originArr = generateRandomOriginArray(powerMax, value);
        int[] reverseArr = generateRandomReverseArray(msizeMax, powerMax);
        // int[] ans1 = reversePair1(originArr1, reverseArr1, powerMax);
        long start = System.currentTimeMillis();
        reversePair2(originArr, reverseArr, powerMax);
        long end = System.currentTimeMillis();
        System.out.println("run time : " + (end - start) + " ms");
    }

}
