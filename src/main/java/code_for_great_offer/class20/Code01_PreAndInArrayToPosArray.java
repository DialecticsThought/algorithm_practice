package code_for_great_offer.class20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 如果只给定一个二叉树前序遍历数组pre和中序遍历数组in,能否不重建树，而直接生成这个二叉树的后序数组并返回已知二叉树中没有重复值
 * |		a
 * |	b	  c
 * |  d  e		f
 * 上面这课树
 * 先序{a,b,d,e,c,f}
 * 中序{d,b,e,a,c,f}
 * 在中序遍历中找到整棵树的根节点a
 * a的前面有3个元素，a的后面右2个元素
 * 找到先序遍历中的a，往后数3个，说明是a的左子树的元素，再往后2个是a的右子树的元素
 * 那么后续遍历数组就是 先填写a的左子树 在填写a的右子树 再填写a
 * 也就是 {b,d,e}+{c,f}+{a}
 * |        a
 * |    b       c
 * | d   e   f    g
 * pre = [a,b,d,e,c,f,g]
 * in = [d,b,e,a,f,c,g]
 * 初始：L1 =L2 = L3 = 0 R1=R2=R3=6
 * 1.
 * 找到头结点a载中序arr的下标是3 == index
 * index - L2 = 3  说明a的左子树的大小是3
 * 1.1解决左子树
 * 那么中序遍历中a的左子树范围是L1+1~L1+1+(index-L2)  ==>
 * 说明 新的左递归函数中 L1=L1+1 新的R2=L1+1+(index-L2)
 * 那么对应的中序遍历是0~0+(index-0)-1 ==>
 * 说明 新的左递归函数中L3=L3 新的R3=L3-1+(index-L2) 因为算上L3位置，往后数index-L2个
 * 1.2解决右子树
 * 那么先序遍历中a的下标是0,a的左子树在先序遍历的范围  ==>
 * 说明 新的右递归函数中L1=L1+(index-L2)+1 新的R1=R1
 * 那么对应的中序遍历是index+1~R2   ==>
 * 说明 新的右递归函数中L2=index+1 R2=R2 L3=L3-1+(index-L2)(因为左子树的最后位置+1就是右子树的开始位置)  R3=R3-1[疑问R3填好了]
 */
public class Code01_PreAndInArrayToPosArray {

    public static int[] zuo(int[] pre, int[] in) {
        if (pre == null || in == null || pre.length != in.length) {
            return null;
        }
        int N = pre.length;
        /**
         * 中序遍历的map
         */
        HashMap<Integer, Integer> inMap = new HashMap<>();
        for (int i = 0; i < N; i++) {
            inMap.put(in[i], i);
        }
        int[] pos = new int[N];
        func(pre, 0, N - 1, in, 0, N - 1, pos, 0, N - 1, inMap);
        return pos;
    }

    /**
     * 用pre先序数组的L1~L2 和in中序数组的L2~R2，填写pos后续数组的L3~R3
     *
     * @param pre
     * @param L1
     * @param R1
     * @param in
     * @param L2
     * @param R2
     * @param pos
     * @param L3
     * @param R3
     * @param inMap
     */
    public static void func(int[] pre, int L1, int R1, int[] in, int L2, int R2, int[] pos, int L3, int R3,
                            HashMap<Integer, Integer> inMap) {
        /**
         * base case
         *
         * 一般情况下
         * 先序   x _____ _____
         * 中序  ______ x ____
         * 但是会有类似
         * 先序   x _____ _____
         * 中序   x ____
         * 也就是x左侧没有东西
         * 或者是
         * 先序   x _____ _____
         * 中序   ______ x
         * 也就是x右侧没有东西
         * 下面的代码能确保不会有上面的问题发生
         */
        if (L1 > R1) {
            return;
        }
        if (L1 == R1) {//base case 就一个数了
            pos[L3] = pre[L1];
        } else {
            /**
             * 先序的第一个数pre[L1]就是后序的最后一个数pos[R3]
             * 因为 先序 中 左 右  后序是 左 右 中  说明 pre[L1]是头节点
             * 接下来在中序遍历中 找到头节点 位置是index
             * */
            pos[R3] = pre[L1];
            int index = L2;
            for (int i = L2; i <= R2; i++) {
                if (in[i] == pre[L1]) {
                    index = i;
                    break;
                }
            }
            //int index = inMap.get(pre[L1]);
            /**
             * 1.左子树再pos的值
             * 先序遍历中 L1对应头节点 左子树的范围就是 L1+1~L1+index-2
             * 中序遍历中 头节点对应index  那么头节点的左子树范围 就是 L2~index-1 左子树的总结点数 就是index-L2个
             * 后序遍历中 要填写的范围是 L3 ~ L3 + index - L2 - 1
             * 2.右子树再pos的值
             * 中序遍历中 头节点对应index  那么头节点的右子树范围 就是 index + 1 ~ R2
             * 先序遍历中 L1对应头节点 的右子树范围 就是 L1 + index - L2 + 1 ~ R1
             * 后序遍历中 要填写的范围是 L3 + index - L2 ~ R3 - 1  因为 pos[R3]是头结点
             * */
            func(pre, L1 + 1, L1 + index - L2, in, L2, index - 1, pos, L3, L3 + index - L2 - 1, inMap);
            func(pre, L1 + index - L2 + 1, R1, in, index + 1, R2, pos, L3 + index - L2, R3 - 1, inMap);
        }
    }

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static int[] preInToPos1(int[] pre, int[] in) {
        if (pre == null || in == null || pre.length != in.length) {
            return null;
        }
        int N = pre.length;
        int[] pos = new int[N];
        process1(pre, 0, N - 1, in, 0, N - 1, pos, 0, N - 1);
        return pos;
    }

    // L1...R1 L2...R2 L3...R3
    public static void process1(int[] pre, int L1, int R1, int[] in, int L2, int R2, int[] pos, int L3, int R3) {
        if (L1 > R1) {
            return;
        }
        if (L1 == R1) {
            pos[L3] = pre[L1];
            return;
        }
        pos[R3] = pre[L1];
        int mid = L2;
        for (; mid <= R2; mid++) {
            if (in[mid] == pre[L1]) {
                break;
            }
        }
        int leftSize = mid - L2;
        process1(pre, L1 + 1, L1 + leftSize, in, L2, mid - 1, pos, L3, L3 + leftSize - 1);
        process1(pre, L1 + leftSize + 1, R1, in, mid + 1, R2, pos, L3 + leftSize, R3 - 1);
    }

    public static int[] preInToPos2(int[] pre, int[] in) {
        if (pre == null || in == null || pre.length != in.length) {
            return null;
        }
        int N = pre.length;
        HashMap<Integer, Integer> inMap = new HashMap<>();
        for (int i = 0; i < N; i++) {
            inMap.put(in[i], i);
        }
        int[] pos = new int[N];
        process2(pre, 0, N - 1, in, 0, N - 1, pos, 0, N - 1, inMap);
        return pos;
    }

    public static void process2(int[] pre, int L1, int R1, int[] in, int L2, int R2, int[] pos, int L3, int R3,
                                HashMap<Integer, Integer> inMap) {
        if (L1 > R1) {
            return;
        }
        if (L1 == R1) {
            pos[L3] = pre[L1];
            return;
        }
        pos[R3] = pre[L1];
        int mid = inMap.get(pre[L1]);
        int leftSize = mid - L2;
        process2(pre, L1 + 1, L1 + leftSize, in, L2, mid - 1, pos, L3, L3 + leftSize - 1, inMap);
        process2(pre, L1 + leftSize + 1, R1, in, mid + 1, R2, pos, L3 + leftSize, R3 - 1, inMap);
    }

    // for test
    public static int[] getPreArray(Node head) {
        ArrayList<Integer> arr = new ArrayList<>();
        fillPreArray(head, arr);
        int[] ans = new int[arr.size()];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = arr.get(i);
        }
        return ans;
    }

    // for test
    public static void fillPreArray(Node head, ArrayList<Integer> arr) {
        if (head == null) {
            return;
        }
        arr.add(head.value);
        fillPreArray(head.left, arr);
        fillPreArray(head.right, arr);
    }

    // for test
    public static int[] getInArray(Node head) {
        ArrayList<Integer> arr = new ArrayList<>();
        fillInArray(head, arr);
        int[] ans = new int[arr.size()];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = arr.get(i);
        }
        return ans;
    }

    // for test
    public static void fillInArray(Node head, ArrayList<Integer> arr) {
        if (head == null) {
            return;
        }
        fillInArray(head.left, arr);
        arr.add(head.value);
        fillInArray(head.right, arr);
    }

    // for test
    public static int[] getPosArray(Node head) {
        ArrayList<Integer> arr = new ArrayList<>();
        fillPostArray(head, arr);
        int[] ans = new int[arr.size()];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = arr.get(i);
        }
        return ans;
    }

    // for test
    public static void fillPostArray(Node head, ArrayList<Integer> arr) {
        if (head == null) {
            return;
        }
        fillPostArray(head.left, arr);
        fillPostArray(head.right, arr);
        arr.add(head.value);
    }

    // for test
    public static Node generateRandomTree(int value, int maxLevel) {
        HashSet<Integer> hasValue = new HashSet<Integer>();
        return createTree(value, 1, maxLevel, hasValue);
    }

    // for test
    public static Node createTree(int value, int level, int maxLevel, HashSet<Integer> hasValue) {
        if (level > maxLevel) {
            return null;
        }
        int cur = 0;
        do {
            cur = (int) (Math.random() * value) + 1;
        } while (hasValue.contains(cur));
        hasValue.add(cur);
        Node head = new Node(cur);
        head.left = createTree(value, level + 1, maxLevel, hasValue);
        head.right = createTree(value, level + 1, maxLevel, hasValue);
        return head;
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

    public static void main(String[] args) {
        System.out.println("test begin");
        int maxLevel = 5;
        int value = 1000;
        int testTime = 100000;
        for (int i = 0; i < testTime; i++) {
            Node head = generateRandomTree(value, maxLevel);
            int[] pre = getPreArray(head);
            int[] in = getInArray(head);
            int[] pos = getPosArray(head);
            int[] ans1 = preInToPos1(pre, in);
            int[] ans2 = preInToPos2(pre, in);
            int[] classAns = zuo(pre, in);
            if (!isEqual(pos, ans1) || !isEqual(ans1, ans2) || !isEqual(pos, classAns)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");

    }
}
