package algorithmbasic2020_master.class24;

import java.util.LinkedList;

/**
 * 滑动窗口技巧与相关题目
 * 前置知识∶无
 * 滑动窗口:维持左、右边界都不回退的一段范围，来求解很多子数组（串）的相关问题
 * 滑动窗口的关键:找到范围和答案指标之间的单调性关系(类似贪心)
 * 滑动过程:滑动窗口可以用简单变量或者结构来维护信息
 * 求解大流程:求子数组在每个位置开头或结尾情况下的答案(开头还是结尾在于个人习惯)
 * 注意:
 * 滑动窗口维持最大值或者最小值的更新结构，在【必备】课程【单调队列】视频里讲述
 */
public class Code01_SlidingWindowMaxArray {
    /**
     * 有一个整型数组arr和一个大小为w的窗口从数组的最左边滑到最右边，窗口每次向右边滑一个位置。
     * 例如,数组为[4,3,5,4,3,3,6,7]，窗口大小为3时:
     * [4 3 5]4 3 3 67
     * 4[3 5 4]3 3 6 7
     * 4 3[5 4 3]3 6 7
     * 4 3 5[4 3 3]6 7
     * 4 3 5 4[3 3 6]7
     * 4 3 5 4 3[3 6 7]
     * 窗口中最大值为5窗口中最大值为5 窗口中最大值为5 窗口中最大值为4窗口中最大值为6窗口中最大值为7
     * 如果数组长度为n，窗口大小为w，则一共产生n-w+1个窗口的最大值。请实现一个函数。输入:整型数组arr，窗口头小为w。
     * 输出:一个长度为n-w+1的数组res，res[i]表示每一种窗口状态下的以本题为例，结果应该返回{5,5,5,4,6,7}。
     */
    // 暴力的对数器方法
    public static int[] right(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }
        int N = arr.length;
        int[] res = new int[N - w + 1];
        int index = 0;
        int L = 0;
        int R = w - 1;
        while (R < N) {
            int max = arr[L];
            for (int i = L + 1; i <= R; i++) {
                max = Math.max(max, arr[i]);

            }
            res[index++] = max;
            L++;
            R++;
        }
        return res;
    }

    public static int[] getMaxWindow(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }
        // qmax 窗口最大值的更新结构
        // 放下标
        LinkedList<Integer> qmax = new LinkedList<Integer>();
        int[] res = new int[arr.length - w + 1];
        int index = 0;
        for (int R = 0; R < arr.length; R++) {//移动窗口的R
            //i-->arr[i]
            while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[R]) {
                qmax.pollLast();
            }
            qmax.addLast(R);
            if (qmax.peekFirst() == R - w) {//i-w过期的下标
                qmax.pollFirst();
            }
            if (R >= w - 1) {//窗口形成了
                res[index++] = arr[qmax.peekFirst()];
            }
        }
        return res;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
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
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int w = (int) (Math.random() * (arr.length + 1));
            int[] ans1 = getMaxWindow(arr, w);
            int[] ans2 = right(arr, w);
            if (!isEqual(ans1, ans2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }

}
