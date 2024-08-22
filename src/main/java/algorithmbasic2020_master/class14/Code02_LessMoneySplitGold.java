package algorithmbasic2020_master.class14;

import java.util.PriorityQueue;

public class Code02_LessMoneySplitGold {
    /*
    *TODO
    * 一块金条切成两半，是需要花费和长度数值一样的铜板的。
    * 比如长度为20的金条，不管怎么切，都要花费20个铜板。一群人想整分整块金条，怎么分最省铜板?
    * 例如,给定数组[10,20,30]，代表一共三个人，整块金条长度为60，金条要分成10，20，30三个部分。
    * 如果先把长度60的金条分成10和50，花费60;再把长度50的金条分成20和30，花费50;一共花费110铜板。
    * 但如果先把长度60的金条分成30和30，花费60;再把长度30金条分成10和20，花费30;一共花费90铜板。
    * 输入一个数组，返回分割的最小代价。
    * */
    // 纯暴力！
    public static int lessMoney1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        //TODO 初始情况 之前的代价为0
        return process(arr, 0);
    }

    /*
     TODO 等待合并的数都在arr里，pre之前的合并行为产生了多少总代价
      arr中只剩一个数字的时候，停止合并，返回最小的总代价
    */
    public static int process(int[] arr, int pre) {
        if (arr.length == 1) {//TODO 当数组只有一个元素 就直接返回 之前的代表
            return pre;
        }
        int ans = Integer.MAX_VALUE;
        /*
         * TODO 枚举遍历出任意2个数去求后续 每一次都这样
         *  arr [0,1,2,3,4,5,6] 7个元素
         *  0和 剩下的1-6 一一合并
         *
         * */
        for (int i = 0; i < arr.length; i++) {//TODO 合并i位置和j位置的数
            for (int j = i + 1; j < arr.length; j++) {
                //TODO i位置和j位置的数合并之后 生成新数组 之后再让后续遍历使用  合并代价是 之前的代价 + 现在的代价
                ans = Math.min(ans, process(copyAndMergeTwo(arr, i, j), pre + arr[i] + arr[j]));
            }
        }
        return ans;
    }

    public static int[] copyAndMergeTwo(int[] arr, int i, int j) {
        int[] ans = new int[arr.length - 1];
        int ansi = 0;
        for (int arri = 0; arri < arr.length; arri++) {
            if (arri != i && arri != j) {
                ans[ansi++] = arr[arri];
            }
        }
        ans[ansi] = arr[i] + arr[j];
        return ans;
    }

    public static int lessMoney2(int[] arr) {
        /*
         *搞出一个小根堆
         * */
        PriorityQueue<Integer> pQ = new PriorityQueue<>();
        for (int i = 0; i < arr.length; i++) {
            pQ.add(arr[i]);
        }
        int sum = 0;
        int cur = 0;
        while (pQ.size() > 1) {//不断地操作 直到 小根堆里面只有一个数
            /*
             * 每次从堆里面 打出最小和次最小
             * 合成一个数 这个数是非叶子结点
             * */
            cur = pQ.poll() + pQ.poll();
            sum += cur;//因为每一个非叶子结点就是代价 代价加入到sum中
            //把合成出来的数 放进小根堆里面
            pQ.add(cur);
        }
        return sum;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 6;
        int maxValue = 1000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            if (lessMoney1(arr) != lessMoney2(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
