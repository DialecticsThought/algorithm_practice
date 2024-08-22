package code_for_great_offer.class48;

import java.util.Arrays;


/**
 *TODO 来自学员问题
 * 比如{ 5, 3, 1, 4 }
 * 全部数字对是：(5,3)、(5,1)、(5,4)、(3,1)、(3,4)、(1,4)
 * 数字对的差值绝对值： 2、4、1、2、1、3
 * 差值绝对值排序后：1、1、2、2、3、4
 * 给定一个数组arr，和一个正数k
 * 返回arr中所有数字对差值的绝对值，第k小的是多少
 * arr = { 5, 3, 1, 4 }, k = 4
 * 返回2
 * eg: a b c d e 这5个数 生成对应的数据对
 * 对于a 有4个数据对 (a,b) (a,c) (a,d) (a,e)
 * 对于b 就有3个数据对
 * 对于c 就有2个数据对
 * 对于d 就有1个数据对
 * 也就是等差数列
 * eg:
 *TODO 有一个原始数组arr[1,3,6,9.....100] 问题：求第350小的数值对差值
 * arr.max-arr.min=100-1=99
 * 说明 所有数的差值一定在区间[0~99]
 * 如果问 数对差值 <=5的数量是a个
 * 如果问 数对差值 <=50的数量是b个
 * 那么 a<=b
 * eg:
 * [0,.....100] 取重点 (0+100)/2 =50
 * 问 数对差值 <=50 这个中点 的有几个
 * 如果有760个
 * 因为现在找数对差值第350小的数对  760 > 350
 * 那么数对差值第350小的数对  在 0~49范围上
 * 因为现在找数对差值第170小的数对  170 < 350
 * 那么数对差值第350小的数对  在 51~100范围上
 * 部分2：
 * 假设
 * <36 有 170个
 * <37 有 170个
 * <38 有 170个
 * <39 有 186个
 * ....
 * 这个假设中 为什么会有相同的个数？
 * 因为2分得到的数有可能不存在，那么这个数就是差值
 * 一下子变成186 说明这个差值38真实存在
 * 也就是说
 * 对于差值∈[0,99]
 * 找到最后一个<=x的差值的个数 并且其个数 <350
 * 假设 < 41 这个差值 的个数 是346
 * 假设 < 42 这个差值 的个数 是354
 * 那么上面如果是找第350小的差值的话
 * 就要选 < 41 的个数 346
 * 答案就是41+1
 * 问题： 现在就是解决找到 < x 这个差值的数
 * eg:
 * arr [1,3,6,9,13,17,19,25] 求差值的个数有几个
 * 利用双指针不会退
 * [1,3,6,9,13,17,19,25]
 *  ↑ ↑
 *  L R
 *  .....
 * [1,3,6,9,13,17,19,25]
 *  ↑     ↑
 *  L     R
 *  R来到9位置的时候 [R]-[L] > 5 有2个数对 R-L-1=2 (1,6) (1,3)
 *  接下来 L右移一次 继续上面的操作
 * [1,3,6,9,13,17,19,25]
 *    ↑   ↑
 *    L   R
 */
public class Code01_MinKthPairMinusABS {

    // 暴力解，生成所有数字对差值绝对值，排序，拿出第k个，k从1开始
    public static int kthABS1(int[] arr, int k) {
        int n = arr.length;
        int m = ((n - 1) * n) >> 1;
        if (m == 0 || k < 1 || k > m) {
            return -1;
        }
        int[] abs = new int[m];
        int size = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                abs[size++] = Math.abs(arr[i] - arr[j]);
            }
        }
        Arrays.sort(abs);
        return abs[k - 1];
    }

    // 二分 + 不回退
    public static int kthABS2(int[] arr, int k) {
        int n = arr.length;
        if (n < 2 || k < 1 || k > ((n * (n - 1)) >> 1)) {
            return -1;
        }
        Arrays.sort(arr);
        // 区间上[0 ~ 大 - 小] 二分
        // l  ~  r
        int left = 0;
        int right = arr[n - 1] - arr[0];
        int mid = 0;
        // 核心的变量  取-1的话 是因为
        int rightest = -1;
        while (left <= right) {
            // 这个mid是向下取整的
            mid = (left + right) / 2;
            //TODO 数字对差值的绝对值<=mid的数字对的个数，是不是 < k个的！
            if (valid(arr, mid, k)) {
                // 找到 最右的元素
                rightest = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return rightest + 1;
    }

    /**
     * TODO
     * 假设arr中的所有数字对，差值绝对值<=limit的个数为x
     * 如果 x < k，达标，返回true
     * 如果 x >= k，不达标，返回false
     *
     * @param arr
     * @param limit
     * @param k
     * @return
     */
    public static boolean valid(int[] arr, int limit, int k) {
        int x = 0;
        for (int l = 0, r = 1; l < arr.length; r = Math.max(r, ++l)) {
            while (r < arr.length && arr[r] - arr[l] <= limit) {
                r++;
            }
            x += r - l - 1;
        }
        return x < k;
    }

    // 为了测试
    public static int[] randomArray(int n, int v) {
        int[] ans = new int[n];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * v);
        }
        return ans;
    }

    // 为了测试
    public static void main(String[] args) {
        int size = 100;
        int value = 100;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int n = (int) (Math.random() * size);
            int k = (int) (Math.random() * (n * (n - 1) / 2)) + 1;
            int[] arr = randomArray(n, value);
            int ans1 = kthABS1(arr, k);
            int ans2 = kthABS2(arr, k);
            if (ans1 != ans2) {
                System.out.print("arr : ");
                for (int num : arr) {
                    System.out.print(num + " ");
                }
                System.out.println();
                System.out.println("k : " + k);
                System.out.println("ans1 : " + ans1);
                System.out.println("ans2 : " + ans2);
                System.out.println("出错了！");
                break;
            }
        }
        System.out.println("测试结束");

        long start;
        long end;
        int n = 500000;
        int v = 50000000;
        int[] arr = randomArray(n, v);
        int k = (int) (Math.random() * (n * (n - 1) / 2)) + 1;
        start = System.currentTimeMillis();
        kthABS2(arr, k);
        end = System.currentTimeMillis();
        System.out.println("大数据量运行时间（毫秒）：" + (end - start));
    }

}
