package algorithmbasic2020_master.class04;

public class Code01_MergeSort {

    // 递归方法实现
    public static void mergeSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    // 请把arr[L..R]排有序
    // l...r N
    // T(N) = 2 * T(N / 2) + O(N)
    // O(N * logN)
    public static void process(intZ[] arr, int L, int R) {
        if (L == R) { // base case 意思就是 该问题小到什么规模 就不用划分了
            return;
        }
        // mid = (L + R)/2 = L + ((R - L) >> 1);
        int mid = L + ((R - L) >> 1);
        process(arr, L, mid);
        process(arr, mid + 1, R);
        merge(arr, L, mid, R);
    }

    public static void merge(int[] arr, int L, int M, int R) {
        //准备一个辅助数组 大小是 L~R的范围
        int[] help = new int[R - L + 1];
        int i = 0;
        int p1 = L;//指向L的指针 也就是左部分的指针
        int p2 = M + 1;//指向M+1的指针 也就是右部分的指针
        /**
         * p1和p2都不越界的时候
         * p1 到达M+1就越界
         * p2到达R+1就越界
         * */
        while (p1 <= M && p2 <= R) {
            /**
             * 如果 p1指向的元素 <= p2指向的元素
             * p1指向的元素 复制到 辅助数组的i位置,i++
             * 否则  p2指向的元素 复制到 辅助数组的i位置,i++
             * */
            if (arr[p1] <= arr[p2]) {
                help[i] = arr[p1];
                i++;
                p1++;
            } else {
                help[i] = arr[p2];
                i++;
                p2++;
            }

        }
        // 要么p1越界了，要么p2越界了 因为 上面循环中 要么 p1++ 要么p2++
        while (p1 <= M) {
            //p1越界了
            help[i] = arr[p1];
            i++; // 自增i
            p1++; // 自增p1
        }
        while (p2 <= R) {
            //p2越界了
            help[i] = arr[p2];
            i++; // 自增i
            p2++; // 自增p2
        }
        /*
         * 赋值完help数组之后 重新放回arr数组
         * */
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
    }

    // 非递归方法实现
    public static void mergeSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        // 步长
        int mergeSize = 1;
        /*
         * arr [ 2,1,0,3,4,2,6,3,7,5,4] mergeSize = 2
         * 2,1 => 1,2  0,3 =>0,3  4,2 =>2,4 6,3=>3,6 7,5=>5,7  4=>4
         * arr [ 1,2,0,3,2,4,3,6,5,7,4] mergeSize = 4
         * 1,2,0,3 => 0,1,2,3  2,4,3,6 => 2,3,4,6   5,7,4 => 4,5,7
         * arr [0,1,2,3,2,3,4,6,4,5,7] mergeSize = 8
         * 0,1,2,3,2,3,4,6,4,5,7
         * */
        while (mergeSize < N) { // log N
            // 当前左组的，第一个位置
            int L = 0;
            while (L < N) {//不能越界
                if (mergeSize >= N - L) {
                    break;
                }
                /*
                 * 归并排序 就是左组 和 右组 合并
                 * 左组 L...M
                 * 左组 的最右侧就是下标L + mergeSize - 1
                 * 右组 M+1...R
                 * R =  N - 1 表示凑不齐的情况
                 * R = M+mergeSize 表示 足够的情况
                 * */
                int M = L + mergeSize - 1;
                int R = Math.min(M + mergeSize, N - 1);
                merge(arr, L, M, R);//合并
                //下一次的左组的第一个位置就是R+1
                L = R + 1;
            }
            /*
             * 如果真的 mergeSize > N / 2
             * 那么执行mergeSize <<= 1
             * 导致 mergeSize > N 防止溢出
             * 还有 就是 int范围最大是21亿多
             * 假设 N 接近了这个最大值
             * mergeSize 有一轮 是小于N 但是*2 之后 超过了N 且超过了int范围
             * 会溢出
             * */
            if (mergeSize > N / 2) {
                break;
            }
            /*
             * mergeSize *= 2 <==> mergeSize <<= 1
             * 左移一位
             * */
            mergeSize <<= 1;
        }
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
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

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            mergeSort1(arr1);
            mergeSort2(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("出错了！");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }

}
