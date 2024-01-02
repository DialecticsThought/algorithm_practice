package code_for_great_offer.class41;


import java.util.Arrays;
import java.util.HashMap;

/*
 *TODO
 * 来自小红书
 * 一个无序数组长度为n，所有数字都不一样，并且值都在[0...n-1]范围上
 * 返回让这个无序数组变成有序数组的最小交换次数
 * 原问题 对于arr[i]位置的指标 =Math.abs(arr[i-1]-arr[i])
 * 让总指标 就是0~n-1的指标加起来最大=> 贪心算法：arr从小到大/从大到小排序
 * 但是问题跟进一步问：需要交换几次 才能让总指标最大
 * eg:arr[17 34 5] 可以让arr离散化 也就是arr变成[1 2 0] 因为 5最小 34最大 17中间
 * [1 2 0] 变有序所交换的次数 == [17 34 5] 变有序所交换的次数
 * 也就是说 因为题目的数组的元素不会重复 那么
 * arr 就会变成一个有0~n-1 这个n个数组成的数组 每个元素只出现了一次
 * 那么问题 就是arr=[] 这里面的数字是0~n-1 ，这个arr的变有序的最小交换次数
 * 求一次 从小到大的交换次数 再求一次 从大到小的交换次数
 *TODO
 * 下标循环怼
 * eg:[4 2 3 1 0] 要变成[0 1 2 3 4]怎么实现 下标与对应的元素值是相同的
 * 0位置的4应该在4位置上 所以0位置的元素与4位置的元素交换 那么0位置现在是0 4位置在是4
 * 1位置的2应该在2位置上 所以1位置的元素与2位置的元素交换 2位置的元素现在是2
 * 但是2位置的元素原先是3 应该去3位置 3位置的1 最后去1位置
 * 这个案例就是有2个环
 * 4 -> 0   2 -> 3 -> 1
 * ↑ -- ↓   ↑ ------- ↓
 * 这2个环没有交集 一个环有k个元素 环的交换次数 是 k-1次
 * 抽象： 交涉一个数组 有4个环 这4个环的元素 分别是 a b c d
 * 那么 总交换次数就是 a+b+c+d-4 因为a+b+c+d=arr.len-1
 * 那么就是arr.len-1-4 是最优解
 * ========>arr.len-1 - α (α是环的个数)
 * */
public class Code01_MinSwapTimes {

    // 纯暴力，arr长度大一点都会超时
    // 但是绝对正确
    public static int minSwap1(int[] arr) {
        return process1(arr, 0);
    }

    // 让arr变有序，最少的交换次数是多少！返回
    // times, 之前已经做了多少次交换
    public static int process1(int[] arr, int times) {
        /*
         *TODO 一个长度为N的数组 一定能在N-1次交换内变得有序
         * 所以增加一个限制 如果time>= arr.len-1 结束了
         * */
        boolean sorted = true;
        //TODO 遍历整个数组 有序的话 就返回times
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) {
                sorted = false;
                break;
            }
        }
        //TODO base case1
        if (sorted) {
            return times;
        }
        /*
         *TODO base case2
         * 数组现在是无序的状态！
         * */
        if (times >= arr.length - 1) {
            return Integer.MAX_VALUE;
        }
        int ans = Integer.MAX_VALUE;
        /*
         *TODO 2个for循环枚举 所有的交换
         * eg [x x x x] 一共4个元素 元素是啥不关心
         * 先让0位置和1位置交换 得到新的数组 然后用递归跑个后续
         * 先让0位置和2位置交换 得到新的数组 然后用递归跑个后续
         * 先让0位置和3位置交换 得到新的数组 然后用递归跑个后续
         * 先让1位置和2位置交换 得到新的数组 然后用递归跑个后续
         * 先让1位置和3位置交换 得到新的数组 然后用递归跑个后续
         * 先让2位置和3位置交换 得到新的数组 然后用递归跑个后续
         * */
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                swap(arr, i, j);
                ans = Math.min(ans, process1(arr, times + 1));
                //TODO dfs深层遍历的 还原现场
                swap(arr, i, j);
            }
        }
        return ans;
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
    /*
    *TODO
    * 离散化
    * */
    public static void change(int[] arr) {
        int[] copy = Arrays.copyOf(arr, arr.length);
        Arrays.sort(copy);
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < copy.length; i++) {
            hashMap.put(copy[i], i);
        }

        for (int i = 0; i < arr.length; i++) {
            arr[i] = hashMap.get(arr[i]);
        }
    }


    // 已知arr中，只有0~n-1这些值，并且都出现1次
    public static int minSwap2(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            //TODO 如果i位置上不是i
            while (i != arr[i]) {
                //TODO i位置与arr[i]交换
                swap(arr, i, arr[i]);
                ans++;
            }
        }
        return ans;
    }

    // 为了测试
    public static int[] randomArray(int len) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < len; i++) {
            swap(arr, i, (int) (Math.random() * len));
        }
        return arr;
    }

    public static void main(String[] args) {
        int n = 6;
        int testTime = 2000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * n) + 1;
            int[] arr = randomArray(len);
            int ans1 = minSwap1(arr);
            int ans2 = minSwap2(arr);
            if (ans1 != ans2) {
                System.out.println("出错了!");
            }
        }
        System.out.println("测试结束");
    }

}
