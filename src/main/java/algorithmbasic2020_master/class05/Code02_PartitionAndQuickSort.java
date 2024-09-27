package algorithmbasic2020_master.class05;

import java.util.HashMap;

public class Code02_PartitionAndQuickSort {

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // arr[L..R]上，以arr[R]位置的数做划分值
    // <= X > X
    // <= X X
    public static int partition(int[] arr, int L, int R) {
        if (L > R) {
            return -1;
        }
        if (L == R) {
            return L;
        }
        int less = L - 1;//TODO 左边界
        int index = L;//TODO 指针
        //TODO 右边界直接传入
        while (index < R) {
            if (arr[index] <= arr[R]) {
                swap(arr, index, ++less);
				/*swap(arr, index, lessEqual+1);
				lessEqual++;*/
            }
            index++;
        }
		/*swap(arr, ++lessEqual, R);
		return lessEqual;*/
        swap(arr, less + 1, R);
        return less + 1;
    }

    /**
     * arr[L...R] 玩荷兰国旗问题的划分，以arr[R]做划分值
     * <arr[R] ==arr[R] > arr[R]
     * <pre>
     *  TODO
     *   初始状态:
     *     arr = {3, 5, 2, 1, 4, 3}
     *     L = 0
     *     R = 5
     *     less = -1 （小于区的右边界）
     *     more = 6 （大于区的左边界）
     *     index = 0 （当前指针）
     *   目标:
     *     我们要把数组分成三部分：
     *     < arr[R]：小于 3 的部分。
     *     = arr[R]：等于 3 的部分。
     *     > arr[R]：大于 3 的部分。
     *   第一次循环 (index = 0):
     * 		arr[index] = 3，等于基准值 arr[R] = 3。
     * 		进入 if (arr[index] == arr[R]) 分支。
     * 		代码：index = index + 1;
     * 		结果：index 自增为 1，其他变量保持不变。
     * 	 当前状态：
     * 		arr = {3, 5, 2, 1, 4, 3}
     * 		less = -1
     * 		more = 5
     * 		index = 1
     *   第二次循环 (index = 1):
     * 		arr[index] = 5，大于基准值 arr[R] = 3。
     * 		进入 else 分支。
     * 		代码：more = more - 1; swap(arr, index, more);
     * 		交换 arr[1] 和 arr[4]，并将 more 左移。
     * 		结果：arr = {3, 4, 2, 1, 5, 3}，more 减少为 4，index 不变。
     *   当前状态：
     * 		arr = {3, 4, 2, 1, 5, 3}
     * 		less = -1
     * 		more = 4
     * 		index = 1
     *   第三次循环 (index = 1):
     * 		arr[index] = 4，大于基准值 arr[R] = 3。
     * 		再次进入 else 分支。
     * 		代码：more = more - 1; swap(arr, index, more);
     * 		交换 arr[1] 和 arr[3]，并将 more 左移。
     * 		结果：arr = {3, 1, 2, 4, 5, 3}，more 减少为 3，index 不变。
     *   当前状态：
     * 		arr = {3, 1, 2, 4, 5, 3}
     * 		less = -1
     * 		more = 3
     * 		index = 1
     *   第四次循环 (index = 1):
     * 		arr[index] = 1，小于基准值 arr[R] = 3。
     * 		进入 else if (arr[index] < arr[R]) 分支。
     * 		代码：swap(arr, less + 1, index); less = less + 1; index = index + 1;
     * 		交换 arr[0] 和 arr[1]，然后 less 右移，index 右移。
     * 		结果：arr = {1, 3, 2, 4, 5, 3}，less 增加为 0，index 增加为 2。
     *   当前状态：
     * 		arr = {1, 3, 2, 4, 5, 3}
     * 		less = 0
     * 		more = 3
     * 		index = 2
     *   第五次循环 (index = 2):
     * 		arr[index] = 2，小于基准值 arr[R] = 3。
     * 		进入 else if (arr[index] < arr[R]) 分支。
     * 		代码：swap(arr, less + 1, index); less = less + 1; index = index + 1;
     * 		交换 arr[1] 和 arr[2]，然后 less 右移，index 右移。
     * 		结果：arr = {1, 2, 3, 4, 5, 3}，less 增加为 1，index 增加为 3。
     * 		当前状态：
     * 		arr = {1, 2, 3, 4, 5, 3}
     * 		less = 1
     * 		more = 3
     * 		index = 3
     *    第六次循环 (index = 3):
     * 		index 等于 more，结束 while 循环。
     * 		最后一步：
     * 		交换 arr[more] 和 arr[R]，即 arr[3] 和 arr[5] 交换。
     * 		代码：swap(arr, more, R);
     * 		结果：arr = {1, 2, 3, 3, 5, 4}
     * </pre>
     *
     * @param arr
     * @param L
     * @param R
     * @return
     */
    public static int[] netherlandsFlag(int[] arr, int L, int R) {
        if (L > R) { // L...R L>R
            return new int[]{-1, -1};//TODO 表示该范围不是有效范围
        }
        if (L == R) {
            return new int[]{L, R};
        }
        // TODO 整个区域是[L ~ R-1] 左边区域是L-1 右边区域是R
        int less = L - 1; // < 区 右边界
        int more = R; // > 区 左边界
        int index = L; // 指针

        while (index < more) { // 当前位置，不能和 >区的左边界撞上
            if (arr[index] == arr[R]) {// 当前元素等于基准值，无需交换，直接移动index
                index = index + 1;
            } else if (arr[index] < arr[R]) {// 当前元素小于基准值，交换并移动less和index
                // 将 arr[index] 放入 < 区 的下一个空位，也就是 arr[less + 1]
                swap(arr, less + 1, index);
                //  扩大 < 区 的边界 因为上一步 已经将 arr[index] 放入 < 区 的下一个位置
                less = less + 1;
                index = index + 1;
            } else { // 当前元素大于基准值，交换并移动more
                // 遇到这种情况 扩大 > 区 的边界
                more = more - 1;
                //  将新加入边界的元素 arr[more]和arr[index]交换 因为这个扩大的边界元素是arr[index]而不是arr[more]
                swap(arr, index, more);
                /**
                 * 不执行 index = index + 1的原因：
                 * 原有arr[more]和arr[index]交换后，新的arr[index] 位置的元素可能还没有被处理
                 */
            }
        }

        // 目标arr[R] 和 >arr[R]区域的第一个元素交换
        swap(arr, more, R); // <[R] = [R] >[R]


        return new int[]{less + 1, more};


    }

    public static void quickSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process1(arr, 0, arr.length - 1);
    }

    public static void process1(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        // L..R partition arr[R] [ <=arr[R] arr[R] >arr[R] ]
        int M = partition(arr, L, R);
        process1(arr, L, M - 1);
        process1(arr, M + 1, R);
    }

    public static int func(int[] arr, int L, int R) {
        int less = L - 1;
        int index = L;
        while (index < R) {
            if (arr[index] <= arr[R]) {
                swap(arr, index, less + 1);
                less++;
            }
            index++;
        }
        swap(arr, less + 1, R);
        return less + 1;
    }


    public static void quickSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process2(arr, 0, arr.length - 1);
    }

    // arr[L...R] 排有序，快排2.0方式
    public static void process2(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        // [ equalArea[0]  ,  equalArea[0]]
        int[] equalArea = netherlandsFlag(arr, L, R);
        process2(arr, L, equalArea[0] - 1);
        process2(arr, equalArea[1] + 1, R);
    }


    public static void quickSort3(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process3(arr, 0, arr.length - 1);
    }

    public static void process3(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        swap(arr, L + (int) (Math.random() * (R - L + 1)), R);
        int[] equalArea = netherlandsFlag(arr, L, R);
        process3(arr, L, equalArea[0] - 1);
        process3(arr, equalArea[1] + 1, R);
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
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr1);
            quickSort1(arr1);
            quickSort2(arr2);
            quickSort3(arr3);
            if (!isEqual(arr1, arr2) || !isEqual(arr2, arr3)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Oops!");

    }

}
