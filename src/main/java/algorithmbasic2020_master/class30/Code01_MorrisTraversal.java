package algorithmbasic2020_master.class30;

public class Code01_MorrisTraversal {
    /**
     * Morris遍历细节
     * 假设来到当前节点cur，开始时cur来到头节点位置
     * 1)如果cur没有左孩子，cur向右移动(cur = cur.right)
     * 2)如果cur有左孩子，找到左子树上最右的节点mostRight:
     * 2.1)a.如果mostRight的右指针指向空，让其指向cur,然后cur向左移动(cur = cur.left)
     * 2.2)b.如果mostRight的右指针指向cur，让其指向null ,然后cur向右移动(cur = cur.right)
     * 3) cur为空时遍历停止
     * TODO
     * 本质是利用左子树的最右节点的有指针的状态来标记 是否第一次到达该节点 还是第二次
     * 任何节点只要有左子树会遍历2次，并且第二次来到该节点的时候 该节点的左子树已经遍历过了
     * <pre>
     * 初始
     *          a  <--cur
     *       ↙     ↘
     *      b        c
     *   ↙   ↘      ↙   ↘
     *  d    e      f    g
     * 判断当前cur有无左子节点 => 有 那么找到cur的左子树的最右节点 mostright = e
     * 查找mostright有无右子节点  无：让mostright 的right指向cur  并让cur左移 cur = b
     *  cur     a
     *    ↘  ↙  ↑  ↘
     *      b   ↑    c
     *   ↙   ↘  ↑   ↙   ↘
     *  d     e →   f    g
     * 判断当前cur有无左子节点 => 有 那么找到cur的左子树的最右节点 mostright = d
     * 查找mostright 有无右子节点  无 让mostright 的right指向cur  并让cur左移 cur = d
     *                 a
     *           ↙     ↑   ↘
     *           b     ↑    c
     *        ↙  ↑  ↘  ↑    ↙   ↘
     * cur → d → ↑   e →    f    g
     * 判断当前cur有无左子节点 => 无 cur向右移动 cur = b
     *                 a
     *           ↙     ↑   ↘
     *    cur →  b     ↑    c
     *        ↙  ↑  ↘  ↑    ↙   ↘
     *       d → ↑   e →    f    g
     * 判断当前cur有无左子节点 => 有 那么找到cur的左子树的最右节点 mostright = d
     * 查找mostright 有无右子节点 => 有  mostright.right = cur   mostright.right = null 并且 cur == cur.right
     *                 a
     *           ↙     ↑   ↘
     *           b     ↑    c
     *        ↙    ↘   ↑    ↙   ↘
     *       d       e →    f    g
     *         cur ↗
     * 判断当前cur有无左子节点 => 无 cur=cur.right = a 回到a点
     *                 a <--cur
     *           ↙     ↑   ↘
     *           b     ↑    c
     *        ↙    ↘   ↑    ↙   ↘
     *       d      e →    f    g
     * 判断当前cur有无左子节点 => 有 那么找到cur的左子树的最右节点 mostright = e
     * 查找mostright 有无右子节点 => 有  mostright.right = cur   mostright.right = null 并且 cur == cur.right = c
     *          a
     *       ↙     ↘
     *      b        c <--cur
     *   ↙   ↘      ↙   ↘
     *  d    e      f    g
     * 判断当前cur有无左子节点 => 有 那么找到cur的左子树的最右节点 mostright = f
     * 查找mostright有无右子节点  无：让mostright 的right指向cur  mostright.right = cur 并让cur左移 cur = cur.left  cur =f
     *          a
     *       ↙     ↘
     *      b         c
     *   ↙   ↘      ↙ ↑  ↘
     *  d    e     f → →   g
     *              ↑
     *             cur
     * 判断当前cur有无左子节点 => 无 cur=cur.right = c 回到c点
     *          a
     *       ↙     ↘
     *      b         c <--cur
     *   ↙   ↘      ↙ ↑  ↘
     *  d    e     f → →   g
     * 判断当前cur有无左子树 => 有
     * 判断左子树的最右节点的right是否为null => 不为空 ,且指向cur 那么mostright.right = null && cur=cur.right = g
     *          a
     *       ↙     ↘
     *      b         c
     *   ↙   ↘      ↙   ↘
     *  d    e     f    g    <--cur
     * 判断当前cur有无左子树 => 无
     * TODO
     * 先序：对于能回到自己2次的节点 第1次到达该节点就处理，对于只能回到自己第1次的节点 就直接处理
     * 中序：对于能回到自己2次的节点 第2次到达该节点就处理，对于只能回到自己第1次的节点 就直接处理
     * TODO
     * 后序：处理是时机，只能放在能回到自己2次的节点，并且是第2次回到节点的时候
     *      但不是打印自己 而是逆序打印左子树的右边界
     *          a
     *       ↙     ↘
     *      b         c
     *   ↙   ↘      ↙   ↘
     *  d    e     f    g
     * morris顺序：
     * a b d b e a c f c g
     *       △   △     △
     * 对于b而言 左子树的右边界是d
     * 对于a而言 左子树的右边界是b,e;在逆序是e，b
     * 对于c而言 左子树的右边界是f
     *          a
     *       ↙     ↘
     *      b         k
     *       ↘      ↙   ↘
     *        c     s     t
     *          ↘        ↙
     *           d      x
     *         ↙
     *        e
     *         ↘
     *          f
     * morris顺序：
     * a b c d e f d a k s k t x t
     *             △ △     △     △
     * 对于d而言 逆序打印左子树的右边界是 => f,e
     * 对于a而言 逆序打印左子树的右边界是 => d,c,b
     * 对于k而言 逆序打印左子树的右边界是 => s
     * 对于t而言 逆序打印左子树的右边界是 => t
     * 最后逆序整棵树的右边界 t k a
     * </pre>
     */
    public static class Node {
        public int value;
        Node left;
        Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static void process(Node root) {
        if (root == null) {
            return;
        }
        /*
         * 打印语句房子那里 就是哪一个遍历
         *
         * */
        // 1
        process(root.left);
        // 2
        process(root.right);
        // 3
    }

    public static void morris(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {//cur为空才停止
            mostRight = cur.left;//mostrRight是cur的左孩子
            if (mostRight != null) {//cur没有最孩子的话
                //果cur有左孩子，找到左子树上最右的节点mostRight
                //TODO 第1种是 左子树上最右的节点mostRight的right是null 第2种是 左子树上最右的节点mostRight的right是自己
                //TODO 这2种情况就是 当前节点找到最右的节点mostRight的途径
                while (mostRight.right != null && mostRight.right != cur) {//左子树最后节点有两个条件
                    mostRight = mostRight.right;
                }
                //此时mostRight变成了cur左子树的最右节点
                //a.如果mostRight的右指针指向空，让其指向cur,然后cur向左移动(cur = cur.left)
                if (mostRight.right == null) {//说明此时第一次来到cur
                    mostRight.right = cur;//
                    cur = cur.left;//cur向左移动
                    continue;//表示继续下一轮的遍历
                } else {//b.如果mostRight的右指针指向cur，让其指向null ,然后cur向右移动(cur = cur.right)
                    mostRight.right = null;
                }
            }
            cur = cur.right;//如果cur没有左孩子，cur向右移动(cur = cur.right)
        }
    }

    /*
     * morris变成先序就是
     * 某个节点只能一次到达的话直接打印
     * 某个节点能到二次的话 第一次就打印
     * */
    public static void morrisPre(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {//cur为空才停止
            mostRight = cur.left;
            if (mostRight != null) {
                //果cur有左孩子，找到左子树上最右的节点mostRight
                while (mostRight.right != null && mostRight.right != cur) {//左子树最后节点有两个条件
                    mostRight = mostRight.right;
                }
                //mostRight变成了cur左子树的最右节点
                //a.如果mostRight的右指针指向空，让其指向cur,然后cur向左移动(cur = cur.left)
                if (mostRight.right == null) {//说明此时第一次来到cur
                    System.out.print(cur.value + " ");//打印
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;//表示继续下一轮的遍历
                } else {//第二次来到这个节点的话 直接跳出执行cur = cur.right;
                    //b.如果mostRight的右指针指向cur，让其指向null ,然后cur向右移动(cur = cur.right)
                    mostRight.right = null;
                }
            } else {
                //某个节点只能一次到达的话直接打印
                System.out.print(cur.value + " ");
            }
            cur = cur.right;//如果cur没有左孩子，cur向右移动(cur = cur.right)
        }
        System.out.println();
    }

    /*
     * morris变成中序就是
     * 某个节点只能一次到达的话直接打印
     * 某个节点能到二次的话 第二次就打印
     * */
    public static void morrisIn(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                //果cur有左孩子，找到左子树上最右的节点mostRight
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                //mostRight变成了cur左子树的最右节点
                //a.如果mostRight的右指针指向空，让其指向cur,然后cur向左移动(cur = cur.left)
                if (mostRight.right == null) {//说明此时第一次来到cur
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;//表示继续下一轮的遍历
                } else {//对于能够两次到达的节点 执行完mostRight.right = null这一行代码后 直接跳出 执行打印输出的代码
                    //b.如果mostRight的右指针指向cur，让其指向null ,然后cur向右移动(cur = cur.right)
                    mostRight.right = null;
                }
            }
            /*
             * 下面代码是能打印到达一次的节点和到达两次的节点所执行的
             * */
            System.out.print(cur.value + " ");
            cur = cur.right;
        }
        System.out.println();
    }

    /*
     *
     * */
    public static void morrisPos(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    /*
                     * 只有 第二次来到该节点的时候 逆序打印该节点的左子树的右边界
                     * */
                    mostRight.right = null;
                    //TODO 把当前节点的左孩子传入
                    printEdge(cur.left);
                }
            }
            cur = cur.right;
        }
        //while循环之后，单独打印整棵树的右边界
        printEdge(head);
        System.out.println();
    }

    public static void printEdge(Node head) {
        Node tail = reverseEdge(head);//TODO 先链表反转
        Node cur = tail;
        //TODO 打印这个翻转后的链表
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.right;
        }
        //TODO 打印为 再链表反转
        reverseEdge(tail);
    }

    public static Node reverseEdge(Node from) {
        Node pre = null;
        Node next = null;
        while (from != null) {
            next = from.right;
            from.right = pre;
            pre = from;
            from = next;
        }
        return pre;
    }

    // for test -- print tree
    public static void printTree(Node head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    public static void printInOrder(Node head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", len);
        String val = to + head.value + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", len);
    }

    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }

    /*
     * 判断是不是搜索二叉树  也就是实现中序遍历
     * */
    public static boolean isBST(Node head) {
        if (head == null) {
            return true;
        }
        Node cur = head;
        Node mostRight = null;
        Integer pre = null;//之前的值
        boolean ans = true;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }
            //中序遍历的打印变成了判断
            //System.out.print(cur.value + " ");
            /*
             * 当前节点的value < 上一个节点的val 就返回false
             * 否则 上一个节点的值设置成当前节点的值
             * */
            if (pre != null && pre >= cur.value) {
                ans = false;
            }
            pre = cur.value;
            cur = cur.right;
        }
        return ans;
    }

    public static void main(String[] args) {
        Node head = new Node(4);
        head.left = new Node(2);
        head.right = new Node(6);
        head.left.left = new Node(1);
        head.left.right = new Node(3);
        head.right.left = new Node(5);
        head.right.right = new Node(7);
        printTree(head);
        morrisIn(head);
        morrisPre(head);
        morrisPos(head);
        printTree(head);
    }

}
