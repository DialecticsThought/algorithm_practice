import java.util.Comparator;
import java.util.PriorityQueue;

public class Code02_Heap {
    /**
     * <pre>
     * 小顶堆： 任意节点的值 ≤ 其子节点的值
     *                       1
     *                  ↙        ↘
     *              3                2
     *           ↙    ↘            ↙    ↘
     *          6      4          2      6
     *        ↙  ↘    ↙  ↘      ↙
     *       8   7   9    6    5
     * </pre>
     * <pre>
     * 大顶堆： 任意节点的值 >= 其子节点的值
     *                       9
     *                  ↙        ↘
     *              8                6
     *           ↙    ↘            ↙    ↘
     *          6      7          5      2
     *        ↙  ↘    ↙  ↘      ↙
     *       1   4   3    6     2
     * </pre>
     * 堆作为完全二叉树的一个特例，具有以下特性。
     * 1.最底层节点靠左填充，其他层的节点都被填满。
     * 2.我们将二叉树的根节点称为“堆顶”，将底层最靠右的节点称为“堆底”。
     * 3.对于大顶堆（小顶堆），堆顶元素（即根节点）的值分别是最大（最小）的。
     * <p>
     * 许多编程语言提供的是「优先队列 priority queue」，这是一种抽象数据结构，定义为具有优先级排序的队列。
     * 实际上，堆通常用作实现优先队列，大顶堆相当于元素按从大到小顺序出队的优先队列。从使用角度来看，
     * 我们可以将“优先队列”和“堆”看作等价的数据结构。
     *
     * <pre>
     * 由于堆正是一种完全二叉树，我们将采用数组来存储堆
     * 节点指针通过索引映射公式来实现
     * 给定索引 𝑖 ，其左子节点索引为 2𝑖 + 1 ，右子节点索引为 2𝑖 + 2 ，父节点索引为 (𝑖 − 1)/2（向下取整）
     * 当索引越界时，表示空节点或节点不存在
     * 大顶堆： 任意节点的值 >= 其子节点的值
     *                     9[0]
     *                  ↙        ↘
     *             8[1]           6[2]
     *           ↙    ↘            ↙    ↘
     *         6[3]   7[4]       5[5)   2[6]
     *        ↙  ↘    ↙   ↘      ↙
     *       1    4   3   6     2
     *      [7]  [8] [9] [10]   [11]
     * arr  = [ 9  8  6  6  7  5  2  1  4  3  6  2 ]
     * index  = 0  1  2  3  4  5  6  7  8  9  10 11
     * </pre>
     * <p>
     * 元素入堆
     * 给定元素 val ，我们首先将其添加到堆底。
     * 堆化 heapify: 由于 val 可能大于堆中其他元素，堆的成立条件可能已被破坏。因此，需要修复从插入节点到根节点的路径上的各个节点
     * 考虑从入堆节点开始，从底至顶执行堆化。
     * 我们比较插入节点与其父节点的值，如果插入节点更大，则将它们交换。
     * 然后继续执行此操作，从底至顶修复堆中的各个节点，
     * 直至越过根节点或遇到无须交换的节点时结束
     * <pre>
     * 大顶堆： 任意节点的值 >= 其子节点的值
     *                       9
     *                  ↙        ↘
     *              8                6
     *           ↙    ↘            ↙    ↘
     *          6      7          5      2
     *        ↙  ↘    ↙  ↘      ↙
     *       1   4   3    6     2
     * arr  = [ 9  8  6  6  7  5  2  1  4  3  6  2 ]
     * index  = 0  1  2  3  4  5  6  7  8  9  10 11
     * 此时7入堆底
     *                       9
     *                  ↙        ↘
     *              8                6
     *           ↙    ↘            ↙    ↘
     *          6      7          5      2
     *        ↙  ↘    ↙  ↘      ↙    ↘
     *       1   4   3    6    2     7 《=
     * arr  = [ 9  8  6  6  7  5  2  1  4  3  6  2  7 ]
     * index  = 0  1  2  3  4  5  6  7  8  9  10 11 12
     * 从底至顶进行堆化
     * 发现 5 <= 7
     *                       9
     *                  ↙        ↘
     *              8                6
     *           ↙    ↘            ↙    ↘
     *          6      7          5 《=   2
     *        ↙  ↘    ↙  ↘      ↙    ↘
     *       1   4   3    6    2     7 《=
     * arr  = [ 9  8  6  6  7  5  2  1  4  3  6  2  7 ]
     * index  = 0  1  2  3  4  5  6  7  8  9  10 11 12
     *                         ↑                    ↑
     * 交换 5 和 7
     *                       9
     *                  ↙        ↘
     *              8                6
     *           ↙    ↘            ↙    ↘
     *          6      7          7 《=   2
     *        ↙  ↘    ↙  ↘      ↙    ↘
     *       1   4   3    6    2     5 《=
     * arr  = [ 9  8  6  6  7  7  2  1  4  3  6  2  5 ]
     * index  = 0  1  2  3  4  5  6  7  8  9  10 11 12
     *                         ↑                    ↑
     * 发现 6 <= 7
     *                       9
     *                  ↙        ↘
     *              8                6 《=
     *           ↙    ↘            ↙    ↘
     *          6      7          7 《=   2
     *        ↙  ↘    ↙  ↘      ↙    ↘
     *       1   4   3    6    2     5
     * arr  = [ 9  8  6  6  7  7  2  1  4  3  6  2  5 ]
     * index  = 0  1  2  3  4  5  6  7  8  9  10 11 12
     *                ↑        ↑
     * 交换 6 和 7
     *                       9
     *                  ↙        ↘
     *              8                7 《=
     *           ↙    ↘            ↙    ↘
     *          6      7          6 《=   2
     *        ↙  ↘    ↙  ↘      ↙    ↘
     *       1   4   3    6    2     5
     * arr  = [ 9  8  7  6  7  6  2  1  4  3  6  2  5 ]
     * index  = 0  1  2  3  4  5  6  7  8  9  10 11 12
     *                ↑        ↑
     * 发现 9 >= 7
     *                       9  《=
     *                  ↙        ↘
     *              8                7 《=
     *           ↙    ↘            ↙    ↘
     *          6      7          6      2
     *        ↙  ↘    ↙  ↘      ↙    ↘
     *       1   4   3    6    2     5
     * arr  = [ 9  8  7  6  7  6  2  1  4  3  6  2  5 ]
     * index  = 0  1  2  3  4  5  6  7  8  9  10 11 12
     *          ↑     ↑
     * 不交换
     * </pre>
     * 堆顶元素出堆
     * 1. 交换堆顶元素与堆底元素（即交换根节点与最右叶节点）。
     * 2. 交换完成后，将堆底从列表中删除（注意，由于已经交换，实际上删除的是原来的堆顶元素）。
     * 3. 从根节点开始，从顶至底执行堆化
     * “从顶至底堆化”的操作方向与“从底至顶堆化”相反，我们将根节点的值与其两个子节点的值进行比较，
     * 将最大的子节点与根节点交换。然后循环执行此操作，直到越过叶节点或遇到无须交换的节点时结束
     * <pre>
     *                       9
     *                  ↙        ↘
     *              8                7
     *           ↙    ↘            ↙    ↘
     *          6      7          6      2
     *        ↙  ↘    ↙  ↘      ↙    ↘
     *       1   4   3    6    2     5
     * arr  = [ 9  8  7  6  7  6  2  1  4  3  6  2  5 ]
     * index  = 0  1  2  3  4  5  6  7  8  9  10 11 12
     *          ↑                                   ↑
     * 1.交换堆顶9和最后一个元素5
     *                       5
     *                  ↙        ↘
     *              8                7
     *           ↙    ↘            ↙    ↘
     *          6      7          6      2
     *        ↙  ↘    ↙  ↘      ↙    ↘
     *       1   4   3    6    2     9
     * arr  = [ 5  8  7  6  7  6  2  1  4  3  6  2  9 ]
     * index  = 0  1  2  3  4  5  6  7  8  9  10 11 12
     *          ↑                                   ↑
     * 2.弹出最后一个元素9
     *                       5
     *                  ↙        ↘
     *              8                7
     *           ↙    ↘            ↙    ↘
     *          6      7          6      2
     *        ↙  ↘    ↙  ↘      ↙
     *       1   4   3    6    2
     * arr  = [ 5  8  7  6  7  6  2  1  4  3  6  2 ]
     * index  = 0  1  2  3  4  5  6  7  8  9  10 11
     * 3.从顶至底开始堆化
     * 3.1 节点 5,8,7 中 8最大
     *                       5  《=
     *                  ↙        ↘
     *              8 《=            7  《=
     *           ↙    ↘            ↙    ↘
     *          6      7          6      2
     *        ↙  ↘    ↙  ↘      ↙
     *       1   4   3    6    2
     * arr  = [ 5  8  7  6  7  6  2  1  4  3  6  2 ]
     * index  = 0  1  2  3  4  5  6  7  8  9  10 11
     * 3.2 交换节点5和8
     *                       8  《=
     *                  ↙        ↘
     *              5 《=            7
     *           ↙    ↘            ↙    ↘
     *          6      7          6      2
     *        ↙  ↘    ↙  ↘      ↙
     *       1   4   3    6    2
     * arr  = [ 8  5  7  6  7  6  2  1  4  3  6  2 ]
     * index  = 0  1  2  3  4  5  6  7  8  9  10 11
     *          ↑  ↑
     * 3.3 节点 5,6,7 中 7最大
     *                       8
     *                  ↙        ↘
     *              5 《=            7
     *           ↙    ↘            ↙    ↘
     *          6《=   7《=        6      2
     *        ↙  ↘    ↙  ↘      ↙
     *       1   4   3    6    2
     * arr  = [ 8  5  7  6  7  6  2  1  4  3  6  2 ]
     * index  = 0  1  2  3  4  5  6  7  8  9  10 11
     * 3.4 交换节点5和7
     *                       8
     *                  ↙        ↘
     *              7 《=            7
     *           ↙    ↘            ↙    ↘
     *          6     5《=        6      2
     *        ↙  ↘    ↙  ↘      ↙
     *       1   4   3    6    2
     * arr  = [ 8  7  7  6  5  6  2  1  4  3  6  2 ]
     * index  = 0  1  2  3  4  5  6  7  8  9  10 11
     *             ↑        ↑
     * 3.5 节点 5,4,6 中 6最大
     *                       8
     *                  ↙        ↘
     *              7               7
     *           ↙    ↘            ↙    ↘
     *          6     5《=        6      2
     *        ↙  ↘    ↙  ↘       ↙
     *       1   4   3《= 6 《=  2
     * arr  = [ 8  7  7  6  5  6  2  1  4  3  6  2 ]
     * index  = 0  1  2  3  4  5  6  7  8  9  10 11
     * 3.6 交换节点5和6
     *                       8
     *                  ↙        ↘
     *              7               7
     *           ↙    ↘            ↙    ↘
     *          6     6《=        6      2
     *        ↙  ↘    ↙  ↘       ↙
     *       1   4   3   5《=   2
     * arr  = [ 8  7  7  6  6  6  2  1  4  3  5  2 ]
     * index  = 0  1  2  3  4  5  6  7  8  9  10 11
     *                      ↑                 ↑
     * 建堆操作
     * 1.自上而下构建
     * 我们首先创建一个空堆，然后遍历列表，依次对每个元素执行“入堆操作”，即先将元素添加至堆的尾部，再
     * 对该元素执行“从底至顶”堆化。
     * 每当一个元素入堆，堆的长度就加一，因此堆是“自上而下”地构建的。
     * 2.自下而上构建
     * 2.1. 将列表所有元素原封不动添加到堆中。
     * 2.2. 倒序遍历堆（即层序遍历的倒序），依次对每个非叶节点执行“从顶至底堆化
     * 在倒序遍历中，堆是“自下而上”地构建的，需要重点理解以下两点。
     * ‧ 由于叶节点没有子节点，因此无需对它们执行堆化。最后一个节点的父节点是最后一个非叶节点。
     * ‧ 在倒序遍历中，我们能够保证当前节点之下的子树已经完成堆化（已经是合法的堆），而这是堆化当前节点的前置条件
     * </pre>
     */
    public static class MyMaxHeap {
        private int[] heap;
        private final int limit;
        //即表示堆的大小 也表示 新来的节点一开始放在哪里
        private int heapSize;

        public MyMaxHeap(int limit) {
            heap = new int[limit];
            this.limit = limit;
            heapSize = 0;
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public boolean isFull() {
            return heapSize == limit;
        }

        /**
         * 插入时，新元素先放到最后，所以问题从下面开始，往上修。
         *
         * @param value
         */
        public void push(int value) {
            if (heapSize == limit) {
                throw new RuntimeException("heap is full");
            }
            //一开始新来的节点放在heapSize位置
            heap[heapSize] = value;
            // value  heapSize
            heapInsert(heap, heapSize);
            heapSize++;
        }

        /**
         * 弹出堆顶时，最后一个元素先顶到最上面，所以问题从上面开始，往下修
         * <p>
         * 用户此时，让你返回最大值，并且在大根堆中，把最大值删掉
         * 剩下的数，依然保持大根堆组织
         * eg: [9,8,7,6,3] 返回9这个头节点
         * 让最后一个节点替换掉头节点 得到 [3,8,7,6,3] => [3,8,7,6]
         * 然后调整 整个树变成大根堆的模样
         */
        public int pop() {
            int ans = heap[0];
            swap(heap, 0, heapSize);
            heapSize--;
            heapify(heap, 0, heapSize);
            return ans;
        }

        /**
         * 新加进来的数，现在停在了index位置，请依次往上移动，
         * 移动到0位置，或者干不掉自己的父亲了，停！
         */
        private void heapInsert(int[] arr, int index) {
            /*
             *
             * [index]    [index-1]/2
             * index == 0
             * 判断当前节点和父节点的大小
             * 当前节点大于父节点 的话 当前节点与父节点的位置交换
             * 再判断当前节点与新的父节点的大小 如果当前节点大于父节点 的话 执行上面相同的操作
             * 直到当前节点达到了最顶端
             * */
            while (arr[index] > arr[(index - 1) / 2]) {
                swap(arr, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        /**
         * 从index位置，往下看，不断的下沉
         * 停：较大的孩子都不再比index位置的数大；已经没孩子了
         * eg：[3,8,7,6] 头节点3的左右子节点分别是 8 7
         * 头节点和 左右子节点中最大的节点比较 如果 头节点 < 左右子节点中最大的节点 则 交换
         * 得到 [8,3,7,6] 3这个节点 和左右子节点的最大值比较 交换
         * [8,6,7,3]  heapSize发现3这个节点没有右子节点 越界了
         */
        private void heapify(int[] arr, int index, int heapSize) {
            int left = index * 2 + 1;// 得到当前节点的左子节点下标
            while (left < heapSize) { // 如果有左孩子，有没有右孩子，可能有可能没有！
                //int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
                /**
                 *  把较大孩子的下标，给largest
                 * 有右子节点 且 右子节点 > 左子节点 当前节点和右子节点交换
                 * 有左子节点 且 左子节点 > 右子节点 当前节点和左子节点交换
                 * 有无 左右子节点 查看 heapSize和right/left关系
                 * */
                int largest;
                int right = left + 1;//当前节点的右子节点下标
                // right < heapSize  防止计算出的right下标溢出
                if (right < heapSize && arr[right] > arr[left]) {
                    largest = right;
                } else {
                    largest = left;
                }
                /**
                 * 选出了 左右子节点中的最大子节点的下标
                 * 接下来 该下标的元素 和 父节点(当前节点)元素比较
                 * 哪个大 把哪一个元素的下标赋值给largest
                 * */
                largest = arr[largest] > arr[index] ? largest : index;
                if (largest == index) {// 该下标的元素 和 父节点(当前节点)元素比较 父节点大 就结束
                    break;
                }
                // index和较大孩子，要互换
                swap(arr, largest, index);
                index = largest;
                left = index * 2 + 1;
            }
        }

        private void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }

    }

    public static class RightMaxHeap {
        private int[] arr;
        private final int limit;
        private int size;

        public RightMaxHeap(int limit) {
            arr = new int[limit];
            this.limit = limit;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == limit;
        }

        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException("heap is full");
            }
            arr[size++] = value;
        }

        public int pop() {
            int maxIndex = 0;
            for (int i = 1; i < size; i++) {
                if (arr[i] > arr[maxIndex]) {
                    maxIndex = i;
                }
            }
            int ans = arr[maxIndex];
            arr[maxIndex] = arr[--size];
            return ans;
        }

    }


    public static class MyComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }

    }

    public static void main(String[] args) {
        //  默认小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>(new MyComparator());
        heap.add(5);
        heap.add(5);
        heap.add(5);
        heap.add(3);
        //  5 , 3
        System.out.println(heap.peek());
        heap.add(7);
        heap.add(0);
        heap.add(7);
        heap.add(0);
        heap.add(7);
        heap.add(0);
        System.out.println(heap.peek());
        while (!heap.isEmpty()) {
            System.out.println(heap.poll());
        }


        int value = 1000;
        int limit = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            int curLimit = (int) (Math.random() * limit) + 1;
            MyMaxHeap my = new MyMaxHeap(curLimit);
            RightMaxHeap test = new RightMaxHeap(curLimit);
            int curOpTimes = (int) (Math.random() * limit);
            for (int j = 0; j < curOpTimes; j++) {
                if (my.isEmpty() != test.isEmpty()) {
                    System.out.println("Oops!");
                }
                if (my.isFull() != test.isFull()) {
                    System.out.println("Oops!");
                }
                if (my.isEmpty()) {
                    int curValue = (int) (Math.random() * value);
                    my.push(curValue);
                    test.push(curValue);
                } else if (my.isFull()) {
                    if (my.pop() != test.pop()) {
                        System.out.println("Oops!");
                    }
                } else {
                    if (Math.random() < 0.5) {
                        int curValue = (int) (Math.random() * value);
                        my.push(curValue);
                        test.push(curValue);
                    } else {
                        if (my.pop() != test.pop()) {
                            System.out.println("Oops!");
                        }
                    }
                }
            }
        }
        System.out.println("finish!");

    }

}
