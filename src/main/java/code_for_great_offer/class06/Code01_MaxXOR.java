package code_for_great_offer.class06;

public class Code01_MaxXOR {

    // O(N^2)
    public static int maxXorSubarray1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 准备一个前缀异或和数组eor
        // eor[i] = arr[0...i]的异或结果
        int[] eor = new int[arr.length];
        eor[0] = arr[0];
        // 生成eor数组，eor[i]代表arr[0..i]的异或和
        for (int i = 1; i < arr.length; i++) {
            eor[i] = eor[i - 1] ^ arr[i];
        }
        int max = Integer.MIN_VALUE;
        for (int j = 0; j < arr.length; j++) {
            for (int i = 0; i <= j; i++) { // 依次尝试arr[0..j]、arr[1..j]..arr[i..j]..arr[j..j]
                max = Math.max(max, i == 0 ? eor[j] : eor[j] ^ eor[i - 1]);
            }
        }
        return max;
    }

    /**
     * 前缀树的Node结构
     * nexts[0] -> 0方向的路
     * nexts[1] -> 1方向的路
     * nexts[0] == null 0方向上没路！
     * nexts[0] != null 0方向有路，可以跳下一个节点
     * nexts[1] == null 1方向上没路！
     * nexts[1] != null 1方向有路，可以跳下一个节点
     */
    public static class Node {
        //TODO 一个路径0 一个路径1
        public Node[] nexts = new Node[2];
    }

    // 基于本题，定制前缀树的实现
    public static class NumTrie {
        // 头节点
        public Node head = new Node();

        /**
         * TODO
         * 把节点加入到树中
         * 前缀树的数字是挂在路径上 而不是节点上
         */
        public void add(int newNum) {
            Node cur = head;
            //TODO 0~31表示整型
            for (int move = 31; move >= 0; move--) {
                /*
                 *TODO 提取中这个数的第move位的状态
                 * eg:  newNum = 1010 move = 3
                 * (newNum >> move) & 1 = 1
                 * */
                int path = ((newNum >> move) & 1);
                //TODO 当前节点 没有该路径 新建一个节点  有路径的话 就复用该路径
                cur.nexts[path] = cur.nexts[path] == null ? new Node() : cur.nexts[path];
                cur = cur.nexts[path];
            }
        }

        /**
         * 该结构之前收集了一票数字，并且建好了前缀树
         * num和 谁 ^ 最大的结果（把结果返回）
         *
         * @param num
         * @return
         */
        public int maxXor(int num) {
            Node cur = head;
            int ans = 0;
            //TODO 从31位最高位 依次取状态
            for (int move = 31; move >= 0; move--) {
                //TODO 取出num中第move位的状态，path只有两种值0就1，整数
                int path = (num >> move) & 1;
                /**
                 * TODO
                 * 最高位的符号位 期待遇到的数是相同的
                 *  期待遇到的东西 比如一个正数 31位为0  那么期望与到另一个数也是正数 31位为0
                 *  除了最高位的符号位 其他位都期望遇到不一样的数
                 * */
                int best = move == 31 ? path : (path ^ 1);
                /**
                 * TODO  如果存在期待的路径并且就是实际遇到的路径
				 * cur.nexts[best] != null  表示在期待的方向上面有路
				 * 那么期待的和实际遇到的是相同的，也就是best
                 * 就走这个路径 没有的话 只能走相反的路径 也就是(best ^ 1)
                 * 实际遇到的就是best
				 * num的第move位是path
				 * 现在要设置答案第move位 就是(path ^ best)再向左移动move位 再或运算到 答案的第move位
                 */
                best = cur.nexts[best] != null ? best : (best ^ 1);
                /**
                 *TODO
                 * (path ^ best) 当前位位异或完的结果
                 * eg：
                 * num 此时来到第17位,值是1  path就是1
                 * 实际遇到的路径best是0
                 * (path ^ best) = 1 说明num和一个数异或后在17位置上是1，所以让1左移17位或运算到 答案上
                 * */
                ans |= (path ^ best) << move;
                cur = cur.nexts[best];
            }
            return ans;
        }
    }

    // O(N)
    public static int maxXorSubarray2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        // 这个数表示0~i整体异或和
        int xor = 0;
        NumTrie numTrie = new NumTrie();
        //表示一个数都没有的时候 异或和是0
        numTrie.add(0);
        /**
         * 每一个位置 求出一个前缀和，选出一个最好答案，把当前位置的前缀和加入进前缀树
         */
        for (int i = 0; i < arr.length; i++) {
            // i位置的数 异或到xor中
            // xor 表示 0 ~ i范围的数的异或和
            xor ^= arr[i];
            max = Math.max(max, numTrie.maxXor(xor));
            //把当前 0 ~ i范围的数的异或和 放入前缀树里面
            numTrie.add(xor);
        }
        return max;
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
        int maxSize = 30;
        int maxValue = 50;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int comp = maxXorSubarray1(arr);
            int res = maxXorSubarray2(arr);
            if (res != comp) {
                succeed = false;
                printArray(arr);
                System.out.println(res);
                System.out.println(comp);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
