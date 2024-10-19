package heima_data_structure.java.src.main.java.com.itheima.algorithm.recursion_single;

/**
 * 递归二分查找
 */
public class E03BinarySearch {

    public static int search(int[] a, int target) {
        return f(a, target, 0, a.length - 1);
    }

    /**
     * <h3>递归(子问题)函数：查找 [i .. j] 范围内的目标</h3>
     *
     * @param a      数组
     * @param target 待查找值
     * @param i      起始索引
     * @param j      结束索引
     * @return <p>找到返回索引</p><p>找不到返回 -1</p>
     */
    private static int f(int[] a, int target, int i, int j) {
        if (i > j) {
            return -1;
        }

        int m = (i + j) >>> 1;
        if (target < a[m]) {
            return f(a, target, i, m - 1);
        } else if (a[m] < target) {
            return f(a, target, m + 1, j);
        } else {
            return m;
        }
    }

    public static int binarySearch(int[] arr, int target, int i, int j) {
        // base case
        if (i == j) {
            if (arr[i] == target) {
                return i;
            } else {
                return -1;
            }
        }
        // 得到中间索引
        int midIndex = (i + j) / 2;

        if (arr[midIndex] < target) {
            return binarySearch(arr, target, midIndex + 1, j);
        } else if (arr[midIndex] > target) {
            return binarySearch(arr, target, i, midIndex - 1);
        } else {
            return midIndex;
        }
    }
}
