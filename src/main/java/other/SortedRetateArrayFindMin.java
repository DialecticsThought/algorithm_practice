package other;

public class SortedRetateArrayFindMin {

    public static int getMin(int[] arr) {
        int L = 0;
        int R = arr.length - 1;
        int mid = 0;
        //潜台词:L..R有全局min
        while (L < R) {
            if (L == R - 1) {//这个范围只有两个输了
                break;
            }
            // arr[L] < arr[R] L..R范围上，没有旋转 断点一定是L位置上的数
            if (arr[L] < arr[R]) {
                return arr[L];
            }
            // [L] >=[R]
            mid = (L + R) / 2;
            // arr[L] > arr[mid]
            if (arr[L] > arr[mid]) {
                //说明全剧最小在L~M上面
                //二分
                R = mid;
                continue;
            }
            // [L] >=[R] && [Mid] >=[L]
            if (arr[mid] > arr[R]) {
                //说明断点一定在右侧
                //Mid~R上面
                L = mid;
                continue;
            }
            // [L]b= [R]&&[mid] >= [L] &8 [mid] <=[R]
            // ->[L] ==[M] == [R]
            while (L < mid) {
                if (arr[L] == arr[mid]) {
                    L++;
                } else if (arr[L] < arr[mid]) {
                    return arr[L];
                } else {
                    // arr[ low] > arr[mid]
                    R = mid;
                    break;
                }
            }
        }
        return Math.min(arr[L], arr[R]);
    }

    /*
     * 有一个结论 中间三个点不都相同总是可以二分
     * 接下来就是如何把可能性分成二分的样子
     * */
    public static boolean test2(int[] arr,int num){
        int L = 0;
        int R = arr.length - 1;
        int M = 0;
        //在L...R范围上，找num
        //潜台词: L..R之外的范围上，一定没有num; L...R范围上可能有nu
        // L.. . . ..M... ...R
        while (L<=R) {
            M = (L + R) / 2;
            if (arr[M] == num) {
                return true;
            }
            //arr[M] ！=num
            //三个位置的数相同 但是都不同于num 跳过
            if (arr[L] == arr[M] && arr[M] == arr[R]) {
                //L向右移动 直到 arr[L] ！= arr[M]
                while (L != M && arr[L] == arr[M]) {
                    L++;
                }
                //L==M或者L在运动的过程中 找到了和arr[M]不一样的值
                if (L == M) {
                    //在右边范围二分法
                    //因为上面的代码判断了L~M之间的数都相同 并且都不同于num
                    L = M + 1;
                    continue;
                }
            }
            //arr[L] arr[M] arr[R] 不都相同 arr[M]!=num
            /*
             *1   3
             *L   M    R     num=5
             * arr[L]!=arr[M]且 arr[L] < arr[M] 并且这段是递增的 L~R之间一定没有5
             * 在M+1 ~ R做二分
             * */
            if (arr[L] != arr[M]) {
                if (arr[M] > arr[L]) {//L ..M之间有没断点（原始数组中最小的值扭过去的点（新位置）叫断点）
                    if (num >= arr[L] && num < arr[M]) {
                        //M和L这两个位置包住了num
                        R = M - 1;
                    } else {
                        //M和L这两个位置没有包住了num
                        L = M + 1;
                    }
                } else {//arr[M] < arr[L] M~R没有断点
                    if (num > arr[M] && num <= arr[R]) {
                        L = M + 1;
                    } else {
                        R = M - 1;
                    }
                }
            } else {//arr[M] !=arr[R] 且arr[L] == arr[M]
                if (arr[M] < arr[R]) {
                    if (num > arr[M] && num <= arr[R]) {
                        L = M + 1;
                    } else {
                        R = M - 1;
                    }
                } else {
                    if (num >= arr[L] && num < arr[M]) {
                        L = M + 1;
                    } else {
                        R = M - 1;
                    }
                }
            }
        }
        return false;
    }
}
