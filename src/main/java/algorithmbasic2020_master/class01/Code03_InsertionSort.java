package algorithmbasic2020_master.class01;

import java.util.Arrays;

public class Code03_InsertionSort {
    /*
     * 5 4 3 4 3 2 划分成
     * [5], [4,3,4,3,2]
     * [4,5] [3,4,3,2]
     * [3,4,5] [4,3,2]
     * [3,4,4,5] [3,2]
     * [3,3,4,4,5] [2]
     * */
    public static void insertionSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // 不只1个数
        for (int i = 1; i < arr.length; i++) { // 0 ~ i 做到有序
            for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
    }

    public static void insertionSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // 不只1个数
        for (int i = 1; i < arr.length; i++) { // 0 ~ i 做到有序
            for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
    }

    // i和j是一个位置的话，会出错
    public static void swap(int[] arr, int i, int j) {
        /*
         * a和b内存地址一定是不同的 才能交换 要不然 得到的结果会有问题
         * int a = 甲 ,  int b = 乙
         * a = a ^ b  ==> a = 甲 ^ 乙 , b = 乙
         * b = a ^ b  ==> b = 甲 ^ 乙 ^ 乙 = 甲 ^ (乙 ^ 乙) = 甲 ^ 0 = 甲, a = 甲 ^ 乙
         * a = a ^ b  ==> a = 甲 ^ 乙 ^ 甲 = 甲 ^ 甲 ^ 乙 = 0 ^ 乙  = 乙 , b = 甲
         * */
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    // for test 系统提供的
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        // Math.random() -> [0,1) 所有的小数，等概率返回一个
        // Math.random() * N -> [0,N) 所有小数，等概率返回一个
        // (int)(Math.random() * N) -> [0,N-1] 所有的整数，等概率返回一个
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())]; // 长度随机的数组
        for (int i = 0; i < arr.length; i++) {
            /*
             * 表示生成一个可能证整数可能负数的随机
             * */
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
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

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static int partition(int[] arr, int L, int R) {
        if (L > R) {
            return -1;
        }
        if (L == R) {
            return L;
        }
        int less = L - 1;
        int index = L;
        while (index < R) {
            if (arr[index] <= arr[R]) {
                swap(arr, index, less);
                less++;
            }
            index++;
        }
        swap(arr, less, R);
        return less + 1;
    }

    public void p(int[] arr, int L, int R) {
        int M = partition(arr, L, R);
        partition(arr, L, M - 1);
        partition(arr, M + 1, M);

    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100; // 随机数组的长度0～100
        int maxValue = 100;// 值：-100～100
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            insertionSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                // 打印arr1
                // 打印arr2
                succeed = false;
                for (int j = 0; j < arr.length; j++) {
                    System.out.print(arr[j] + " ");
                }
                System.out.println();
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        insertionSort(arr);
        printArray(arr);
    }

}
