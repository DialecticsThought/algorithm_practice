package heima_data_structure.java.src.main.java.com.itheima.algorithm.sort;

import java.util.Arrays;

/**
 * 希尔排序 插入排序就是gap = 1 的shell排序
 * [ 9, 3, 7, 2, 5, 8, 1, 4]
 *   0  1  2  3  4  5  6  7
 * 一开始 gap=4
 * 那么索引 [0,4] [1,5] [2,6] [3,7] 各个分成一组
 * 排序完
 * [ 5, 3, 1, 2, 9, 8, 7, 4]
 *   0  1  2  3  4  5  6  7
 *
 * 此时 gap= gap / 2 = 2
 * 那么索引 [0,2,4,6] [1,3,5,7]各个分成一组
 * 排序完
 * [ 1, 2, 5, 3, 7, 4, 9, 8]
 *   0  1  2  3  4  5  6  7
 *
 * 此时 gap= gap / 2 = 1
 * 那么索引 [0  1  2  3  4  5  6  7]各个分成一组
 * 排序完
 * [ 1, 2, 3, 4, 5, 7, 8, 9]
 *   0  1  2  3  4  5  6  7
 */
public class ShellSort {
    public static void sort(int[] a) {
        /**
         * 初始gap =  a.length / 2
         * 每一次遍历之后 gap / 2
         * 循环截止条件: gap = 1
         */
        for (int gap = a.length / 2; gap >= 1; gap = gap / 2) {
            /**
             * gap 是未排序区域的左边界
             * eg：
             * [ 5, 3, 1, 2, 9, 8, 7, 4]
             *   0  1  2  3  4  5  6  7
             *
             * 此时 gap= gap / 2 = 2 , low = gap =2
             * 对于 1 而言 这个单独的数字就是已排序的区间的右边界
             * 对于 5 而言 就是未排序区域的左边界
             第一步：low = 2
             tmp = a[2] = 1
             i = low - gap = 2 - 2 = 0，也就是我们要开始从索引 0 开始比较。
             比较 tmp (1) 和 a[0] (5)，发现 1 < 5，所以将 5 移动到索引 2：
             [ 5, 3, 5, 2, 9, 8, 7, 4 ]
             更新 i = -2，跳出 while 循环。
             将 1 插入到索引 0：
             [ 1, 3, 5, 2, 9, 8, 7, 4 ]
             第二步：low = 3
             tmp = a[3] = 2
             i = low - gap = 3 - 2 = 1
             比较 tmp (2) 和 a[1] (3)，发现 2 < 3，将 3 移动到索引 3：
             [ 1, 3, 5, 3, 9, 8, 7, 4 ]
             更新 i = -1，跳出 while 循环。
             将 2 插入到索引 1：
             [ 1, 2, 5, 3, 9, 8, 7, 4 ]
             第三步：low = 4
             tmp = a[4] = 9
             i = low - gap = 4 - 2 = 2
             比较 tmp (9) 和 a[2] (5)，9 > 5，不需要移动。
             数组保持不变：
             [ 1, 2, 5, 3, 9, 8, 7, 4 ]
             第四步：low = 5
             tmp = a[5] = 8
             i = low - gap = 5 - 2 = 3
             比较 tmp (8) 和 a[3] (3)，8 > 3，不需要移动。
             数组保持不变：
             [ 1, 2, 5, 3, 9, 8, 7, 4 ]
             第五步：low = 6
             tmp = a[6] = 7
             i = low - gap = 6 - 2 = 4
             比较 tmp (7) 和 a[4] (9)，7 < 9，将 9 移动到索引 6：
             [ 1, 2, 5, 3, 9, 8, 9, 4 ]
             更新 i = 2，继续比较 7 和 a[2] (5)，7 > 5，跳出 while 循环。
             将 7 插入到索引 4：
             [ 1, 2, 5, 3, 7, 8, 9, 4 ]
             第六步：low = 7
             tmp = a[7] = 4
             i = low - gap = 7 - 2 = 5
             比较 tmp (4) 和 a[5] (8)，4 < 8，将 8 移动到索引 7：
             [ 1, 2, 5, 3, 7, 8, 9, 8 ]
             更新 i = 3，继续比较 4 和 a[3] (3)，4 > 3，跳出 while 循环。
             将 4 插入到索引 4：
             [ 1, 2, 5, 3, 4, 7, 9, 8 ]
             */
            for (int low = gap; low < a.length; low++) {
                // 未排序区域的左边界 起到缓存的作用
                int tmp = a[low];
                // 一开始 是已排序的区间的右边界的索引
                // 这是一个辅助变量，用来指向该元素在已排序部分的“前一个位置”。 因为比较的数字相差gap
                // 然后我们会比较该元素与“前一个位置”的元素，直到找到正确的插入位置
                // 而且也表示 元素 tmp 最终应该插入的位置。
                int i = low - gap;
                // 自右向左找插入位置，如果比待插入元素(也就是tmp)大，则不断右移，空出插入位置
                // i是插入排序中用来找到元素最终位置的变量。
                // 在 while 循环中，i 会逐步往左移动，直到找到一个合适的插入位置
                while (i >= 0 && tmp < a[i]) {
                    // 这里交换是因为上面变量tmp缓存了
                    a[i + gap] = a[i];
                    // 交换完 当前 更新 下一个需要比对的位置
                    i =i - gap;
                }
                // 找到插入位置
                // low - gap是元素 tmp 最开始的位置
                // i + gap：是最后插入的位置。
                // 由于在 while 循环中，i 会逐步向左移动，直到找到正确位置，最终 i + gap 位置就是插入的地方
                /**
                 * 如果 i == low - gap：
                 * 这意味着元素 tmp 没有发生位置变化，
                 * 也就是说它已经在正确的位置上（即比前面的元素大），我们不需要插入它到新的位置。
                 * 所以，不需要执行插入操作。
                 *
                 * 如果 i != low - gap：
                 * 这意味着在 while 循环中，i 移动了，元素 tmp 的位置发生了变化。
                 * 也就是说，tmp 的正确位置在其他位置，而不是原始位置，所以我们需要将 tmp 插入到新的位置，
                 * 即 i + gap
                 */
                if (i != low - gap) {
                    a[i + gap] = tmp;
                }
            }
        }
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode(int val,ListNode next) {
            this.val = val;
            this.next = next;
        }

        public ListNode reverse (ListNode head){
            // p是反转链表的头指针
            ListNode p = null;
            while(head != null){
                // 当前节点是head
                // 得到当前节点的下一个节点
                ListNode currentNext = head.next;
                // 让当前节点的下一个节点指向p
                head.next = p;
                // p指向 反转链表的头
                p = head;
                // 回到初始的链表 此时当前节点已经和初始链表分开了
                // 下一轮当前节点就是保存的currentNext;
                head = currentNext;
            }
            return p;
        }
    }


    public static void main(String[] args) {
        int[] a = {9, 3, 7, 2, 5, 8, 1, 4};
        System.out.println(Arrays.toString(a));
        sort(a);
        System.out.println(Arrays.toString(a));
    }
}
