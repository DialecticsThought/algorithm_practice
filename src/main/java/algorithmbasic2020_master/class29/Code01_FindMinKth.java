package algorithmbasic2020_master.class29;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 问题：无序的arr中找到第k小的数
 * 1.在arr中 随机挑选一个数p => bfprt算法是根据规则选出一个数字p ，bfprt 估计 <p的至少有几个数 相当于求>=p的个数
 * 2.类似于荷兰国旗问题
 * 3.查看 =p的区域是否命中k
 *      3.1命中直接返回
 *      3.2不命中，左/右递归
 * 举例:假设求第100小的个数 ,=p的区间在50~150,那么就是命中
 * <pre>
 * 1.分组：首先将输入数组分成若干组，每组包含5个元素（如果最后一组少于5个元素，可以保持原样）。
 * 2.组内排序：对每组中的5个元素进行排序，并找出每组的中位数。
 * 3.中位数的中位数：将所有组的中位数提取出来，构成一个新的数组。然后，递归地在这个数组中找到中位数，即所谓的“中位数的中位数”（MOM）。
 * 4.划分：使用“中位数的中位数”作为枢轴（pivot）将原数组划分成两部分：一部分小于这个枢轴，另一部分大于这个枢轴。
 * 5.选择：根据第k小元素的下标位置，决定在划分后的哪一部分继续递归寻找，直到找到第k小的元素为止。
 * </pre>
 *TODO
 * 1.一个数组 5 个数分成一组 最后一组可以不是5个 O(1)
 * eg: 0~4 5~9 10~14 15~19
 * 2.每个组做组内排序 每个组排序是 O(1) 整体O(n)
 * 3.每个组的中位数取出(第3个数) 组成新数组 tmpArr  如果最后一组的数量手术的话 把上中位数取出
 * 4. 求 tmpArr 的中位数 => 递归调用 bfprt
 * 5.拿出选出的p  做  <p  =p  >p  类似荷兰国旗
 * 6.如果没命中 走递归 右递归
 * <p>
 * eg: 假设下面的5个数组
 * [a b c d e]
 * [f g h i j]
 * [k l m n o]
 * [r q p s  ]
 * 用字母代表数字 假设排序之后
 * [e d b a c]
 * [j i h f g]
 * [m n o l k]
 * [r q p s]
 * 针对上面的每个数组取中间的数 得到一个tmpArr [b h o q]
 * 求tmpArr的中位数 也就是h ，相当于执行了bfprt(tmpArr,2)
 * eg:
 * [x x a x x]
 * [x x b x x]
 * [x x c x x]
 * [x x d x x]
 * [x x e x x]
 * 那么tmpArr=[a,b,c,d,e]
 * 再求tmpArr的中位数，设为p
 * 那么说明 tmpArr中>=p 的个数至少N/10个
 * 因为 N/10 = tmpArr的length/2
 * 想法：想证明 < p 的有多少 那么先证明 >= p 的是多少，想证明 > p 的有多少 那么先证明 <= p的是多少
 * 如果假设c是中位数，那么 c < d 并且 c < e 并且d在自己所属的组内有2个是大于d的
 * 同理e也是在原先小组，有2个数大于e，再加上c所属的数有2个数大于3
 * c是中位数，那么tmpArr中整体 N / 5 个数中，有 N / 10 个数是 >= c 的
 * 那么整个 arr 中至少有 3/10 个数>=c
 * 估计至少 N/10*3 的个数是 >= p 的 => 证出至少 N / 10 * 7 的个数是 <p 的相当于走左侧递归
 * 用同样的方法估计至少 N / 10 * 3 的个数是 <= p 的 => 证出至少N/10*7的个数是 >p 的相当于走左侧递归
 * <p>
 * 精髓：可以保证每一次递归可以至少淘汰 N / 10 * 3 的个数
 */
public class Code01_FindMinKth {

    public static class MaxHeapComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }

    }

    // 利用大根堆，时间复杂度O(N*logK)  迭代版本
    public static int minKth1(int[] arr, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new MaxHeapComparator());
        for (int i = 0; i < k; i++) {
            maxHeap.add(arr[i]);
        }
        for (int i = k; i < arr.length; i++) {
            if (arr[i] < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.add(arr[i]);
            }
        }
        return maxHeap.peek();
    }

    // 改写快排，时间复杂度O(N)  找到第k小的数
    // k >= 1
    public static int minKth2(int[] array, int k) {
        int[] arr = copyArray(array);
        //k-1 是因为第几小是从1开始的
        return process2(arr, 0, arr.length - 1, k - 1);
    }

    public static int[] copyArray(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i != ans.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    // arr 第k小的数
    // process2(arr, 0, N-1, k-1)
    // arr[L..R]  范围上，如果排序的话(不是真的去排序)，找位于index的数是什么
    // index [L..R]
    public static int process2(int[] arr, int L, int R, int index) {
        if (L == R) { //这个情况 L = =R ==INDEX
            return arr[L];
        }
        //TODO 1. 不止一个数  L +  [0, R -L],  在L~R范围上随机选择一个数
        int pivot = arr[L + (int) (Math.random() * (R - L + 1))];
        /*
         *TODO
         * 2.执行类似于荷兰国旗问题的操作 <pivot放左侧 =pivot放中间 >pivot放右侧
         * 返回是一个=pivot区间的左边界和右边界 用来查看 是否命中index
         * */
        int[] range = partition(arr, L, R, pivot);
        //TODO index在 =pivot的区间 的话 就是命中
        if (index >= range[0] && index <= range[1]) {
            return arr[index];
            /*
             * TODO 左右两侧只需要进入一侧 不像快拍
             * */
        } else if (index < range[0]) {//小于左边界 那么就在左侧递归
            return process2(arr, L, range[0] - 1, index);
        } else {//大于右边界 那么就在右侧递归
            return process2(arr, range[1] + 1, R, index);
        }
    }

    public static int[] partition(int[] arr, int L, int R, int pivot) {
        int less = L - 1;
        int more = R + 1;
        int cur = L;
        while (cur < more) {
            if (arr[cur] < pivot) {
                swap(arr, ++less, cur++);
            } else if (arr[cur] > pivot) {
                swap(arr, cur, --more);
            } else {
                cur++;
            }
        }
        return new int[]{less + 1, more - 1};
    }

    public static void swap(int[] arr, int i1, int i2) {
        int tmp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = tmp;
    }

    // 利用rt算法，时间复杂度O(N)
    public static int minKth3(int[] array, int k) {
        int[] arr = copyArray(array);
        return bfprt(arr, 0, arr.length - 1, k - 1);
    }

    // arr[L..R]  如果排序的话，位于index位置的数，是什么，返回
    public static int bfprt(int[] arr, int L, int R, int index) {
        if (L == R) {
            return arr[L];
        }
		/*
		  TODO L...R  每五个数一组
		   每一个小组内部排好序
		   小组的中位数组成新数组
		   这个新数组的中位数返回
		*/
        int pivot = medianOfMedians(arr, L, R);
        int[] range = partition(arr, L, R, pivot);
        if (index >= range[0] && index <= range[1]) {
            return arr[index];
        } else if (index < range[0]) {
            return bfprt(arr, L, range[0] - 1, index);
        } else {
            return bfprt(arr, range[1] + 1, R, index);
        }
    }

    // arr[L...R]  五个数一组
    // 每个小组内部排序
    // 每个小组中位数领出来，组成marr
    // marr中的中位数，返回
    public static int medianOfMedians(int[] arr, int L, int R) {
        int size = R - L + 1;
        int offset = size % 5 == 0 ? 0 : 1;
        int[] mArr = new int[size / 5 + offset];
        for (int team = 0; team < mArr.length; team++) {
            int teamFirst = L + team * 5;
            // L ... L + 4
            // L +5 ... L +9
            // L +10....L+14
            mArr[team] = getMedian(arr, teamFirst, Math.min(R, teamFirst + 4));
        }
        // marr中，找到中位数
        // marr(0, marr.len - 1,  mArr.length / 2 )
        return bfprt(mArr, 0, mArr.length - 1, mArr.length / 2);
    }

    //TODO 得到中位数
    public static int getMedian(int[] arr, int L, int R) {
        insertionSort(arr, L, R);
        return arr[(L + R) / 2];
    }

    //TODO 插入排序
    public static void insertionSort(int[] arr, int L, int R) {
        for (int i = L + 1; i <= R; i++) {
            for (int j = i - 1; j >= L && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 1000000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int k = (int) (Math.random() * arr.length) + 1;
            int ans1 = minKth1(arr, k);
            int ans2 = minKth2(arr, k);
            int ans3 = minKth3(arr, k);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }

}
