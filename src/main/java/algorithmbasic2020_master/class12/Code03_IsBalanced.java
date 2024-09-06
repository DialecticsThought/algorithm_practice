package algorithmbasic2020_master.class12;

public class Code03_IsBalanced {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static boolean isBalanced1(Node head) {
        boolean[] ans = new boolean[1];
        ans[0] = true;
        process1(head, ans);
        return ans[0];
    }

    public static int process1(Node head, boolean[] ans) {
        if (!ans[0] || head == null) {
            return -1;
        }
        int leftHeight = process1(head.left, ans);
        int rightHeight = process1(head.right, ans);
        if (Math.abs(leftHeight - rightHeight) > 1) {
            ans[0] = false;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public static boolean isBalanced2(Node head) {
        return process(head).isBalanced;
    }

    /**
     * 信息返回的结构体
     * 左右子树 所需要的信息
     * 1.左右子树是否平衡 boolean类型
     * 2.左右子树的高度 int类型
     * */
    public static class Info {
        public boolean isBalanced;
        public int height;

        public Info(boolean i, int h) {
            isBalanced = i;
            height = h;
        }
    }

    /**
     * 假设以x为头的树
     * 因为以递归形式  可以的得到以x为头的左子树的信息 和右子树的信息
     * 还要规定好 x的信息
     * 也就是说 对头结点的子树有要求 那么对头结点自身 也要有要求（要求是返回信息的结构体） 因为这个是递归函数
     * 平衡二叉树的话 不仅是头结点 没一个节点的子树都要满足平很二叉树的要求  也就用到递归
     * 如果 一个头节点 左右子树的高度差<= 1 但是左右子树不满足要求 也不是平衡二叉树
     */
    public static Info process(Node x) {
        if (x == null) {//TODO 头结点为空 是平衡数  高度为0
            return new Info(true, 0);
        }
        /**
         *   返回左右子树的信息结构体 是否为平衡子树 子树的高度
         * */
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);
        // 整棵树的高度 左右子树的最大高度（根据平衡二叉树的定义） + 一个头节点代表的高度
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        // 假设当前节点的树是平衡树
        boolean isBalanced = true;
        /**
         * 只要左子树不是平衡数
         * 只要右子树不是平衡树
         * 只要 左右子树的高度差 > 1
         * 就不是平衡数
         * 那么假设不成立
         * */
        if (!leftInfo.isBalanced) {
            isBalanced = false;
        }
        if (!rightInfo.isBalanced) {
            isBalanced = false;
        }
        if (Math.abs(leftInfo.height - rightInfo.height) > 1) {
            isBalanced = false;
        }
        return new Info(isBalanced, height);
    }


    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isBalanced1(head) != isBalanced2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
