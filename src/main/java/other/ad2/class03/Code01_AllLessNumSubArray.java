package other.ad2.class03;

import java.util.Arrays;
import java.util.LinkedList;

public class Code01_AllLessNumSubArray {


    public static int check(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length, res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i ; j < n; j++) {
                if (isValid(arr, num, i, j)) {
                    res++;
                }
            }
        }
        return res;
    }

    public static boolean isValid(int[] arr, int num, int l, int r) {
        int max = arr[l], min = arr[l];
        for (int i = l; i <= r; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return (max - min ) < num;
    }

    public static int getNum(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        /*
        * 窗口内最大值和最小值的更新结构
        * */
        LinkedList<Integer> qmin = new LinkedList<Integer>();
        LinkedList<Integer> qmax = new LinkedList<Integer>();
        int i = 0;
        int j = 0;
        int res = 0;
        while (i < arr.length) {
            while (j < arr.length) {
                while (!qmin.isEmpty() && arr[qmin.peekLast()] >= arr[j]) {
                    qmin.pollLast();
                }
                qmin.addLast(j);
                while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[j]) {
                    qmax.pollLast();
                }
                qmax.addLast(j);
                if (arr[qmax.getFirst()] - arr[qmin.getFirst()] > num) {
                    break;
                }
                j++;
            }
            if (qmin.peekFirst() == i) {
                qmin.pollFirst();
            }
            if (qmax.peekFirst() == i) {
                qmax.pollFirst();
            }
            res += j - i;
            i++;
        }
        return res;
    }

    // for test
    public static int[] getRandomArray(int len) {
        if (len < 0) {
            return null;
        }
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * 10);
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
//		int[] arr = getRandomArray(30);
//		int num = 5;
//		printArray(arr);
//		System.out.println(getNum(arr, num));
        for (int i = 0; i < 50000; i++) {
            int[] test = getRandomArray(10);
            int num = (int) (Math.random() * 10);
            if (check(test, num) != getNum(test, num)) {
                System.out.println(check(test, num));
                System.out.println(getNum(test, num));
                System.out.println(Arrays.toString(test));
                break;
            }
        }
    }

}
