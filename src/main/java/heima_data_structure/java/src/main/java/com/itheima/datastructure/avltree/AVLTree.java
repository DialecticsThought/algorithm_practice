package heima_data_structure.java.src.main.java.com.itheima.datastructure.avltree;

/**
 * AVL 树
 * 二叉搜索树在插入和删除时，节点可能失衡
 * 如果在插入和删除时通过旋转, 始终让二叉搜索树保持平衡, 称为自平衡的二叉搜索树
 * AVL 是自平衡二叉搜索树的实现之一
 * 如果一个节点的左右孩子，高度差超过1，则此节点失衡，才需要旋转
 * <p>
 * 定义:
 * 向下的Pink，换爹的Green，向上的Yellow
 * <pre>
 * eg:
 * 旋转前:
 *            4(P)
 *           ↙
 *         3(Y)
 *       ↙
 *      2
 *    ↙
 *   1
 * 旋转后:
 *         3(Y)
 *       ↙      ↘
 *      2       4(P)
 *    ↙
 *   1
 * 对于上面这个树 ，满足 每一个节点的左右孩子  高度差不超过1
 * eg:
 * 旋转前:
 *            8(P)
 *           ↙
 *         6(Y)
 *       ↙
 *      3
 * 旋转后:
 *         6(Y)
 *       ↙      ↘
 *      3       8(P)
 * </pre>
 */
public class AVLTree {

    public static class AVLNode {
        public int key;

        public Object value;

        public AVLNode left;

        public AVLNode right;

        int height = 1; // 高度 防止节点是null的时候的高度处理

        public AVLNode(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        public AVLNode(int key) {
            this.key = key;
        }

        public AVLNode(int key, Object value, AVLNode left, AVLNode right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 求节点的高度  方便null的节点的高度处理
     *
     * @param node
     * @return
     */
    private int height(AVLNode node) {
        return node == null ? 0 : node.height;
    }

    /**
     * 用递归的方式得到高度
     *
     * @param node
     * @return
     */
    private int getHeight(AVLNode node) {
        if (node == null) {
            return 0;
        }
        // 得到左孩子的高度
        int leftHeight = getHeight(node.left);
        //  得到右孩子的高度
        int rightHeight = getHeight(node.right);
        // 左右孩子中的最大值 + 当前节点的高度
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * 更新节点高度 (新增、删除、旋转)
     *
     * @param node
     */
    private void updateHeight(AVLNode node) {
        node.height = Integer.max(height(node.left), height(node.right)) + 1;
    }

    /**
     * 平衡因子 (balance factor) = 左子树高度-右子树高度  分别可能是:1 -1 0
     * bf = 0，1，-1时，表示左右平衡
     * <pre>
     * bf > 1时，表示左边太高
     * eg:
     *          6
     *        ↙    ↘
     *       2     7
     *     ↙  ↘
     *    1    4
     *        ↙  ↘
     *        3   5
     * bf < -1时，表示右边太高
     * eg:
     *          2
     *      ↙    ↘
     *      1     6
     *          ↙  ↘
     *         4    7
     *       ↙  ↘
     *       3   5
     * </pre>
     *
     * @param node
     * @return
     */
    private int bf(AVLNode node) {
        return height(node.left) - height(node.right);
    }


    /**
     * 参数：要旋转的节点, 返回值：新的根节点 LL
     *
     * LL
     * - 失衡节点（即叶 5 红色）的 bf > 1, 即左边更高
     * - 失衡节点的左孩子（即叶 3 黄色）的 bf >= 0, 即左孩子这也是左边更高或者等高
     *
     * <pre>
     * eg:
     * 被处理的节点是5
     *
     *           5(P)
     *         ↙    ↘
     *       3(Y)     6
     *      ↙  ↘
     *    2    4(G)
     *   ↙
     *  1
     *  树的节点 除了5为根节点的树不平衡之外 其他都平衡
     *  3是向上的 5是向下的 4是换爹的
     *  失衡 节点（图中5粉红色）的 bf > 1，即左边更高
     *  失衡 节点左孩子(节点3)是bf >=0也就是做孩子这边 也是左边更高 或者等高
     *  eg:
     *         3(Y)
     *       ↙    ↘
     *      2     5(P)
     *    ↙       ↙  ↘
     *   1       4(G)  6
     * </pre>
     *
     * <pre>
     * eg:
     * 旋转前：
     * eg
     *       8(P)
     *     ↙   ↘
     *    6(Y)  9
     *  ↙  ↘
     * 5   7(G)
     * 8是粉红色节点 向下的    6是黄色节点 向上的      7是绿色节点也就是黄色节点的右孩子 换爹的
     * 绿色节点需要换爹
     * 旋转的时候绿色节点作为粉红色节点的左孩子
     * 旋转后：
     * eg:
     *
     *      6
     *    ↙   ↘
     *   5     8(P)
     *        ↙   ↘
     *       7(G)  9
     * </pre>
     *
     * @param red
     * @return
     */
    private AVLNode rightRotate(AVLNode red) {
        // 右旋的话 说明是左侧高 那么yellow不可能空指针
        AVLNode yellow = red.left;
        // 找到绿色节点 这里绿色节点是黄色节点的右孩子
        AVLNode green = yellow.right;
        yellow.right = red;   // 上位  这行代码必须在上面代码执行之后
        red.left = green;     // 换爹
        // 绿色节点和没有提到的节点高度是不变的
        // 红色节点和黄色节点 旋转之后 更新高度
        updateHeight(red);
        updateHeight(yellow);
        return yellow;
    }

    /**
     * 参数：要旋转的节点, 返回值：新的根节点 RR
     * RR
     * - 失衡节点（即叶 3）的 bf < -1, 即右边更高
     * - 失衡节点的右孩子（即叶 6 黄色）的 bf <= 0, 即右孩子这边也是右边更高或者等高
     * <pre>
     *  eg:
     *       2(P)
     *    ↙    ↘
     *   1      4(Y)
     *         ↙     ↘
     *        3(G)    5
     *                  ↘
     *                    6
     *  树的节点 除了5为根节点的树不平衡之外 其他都平衡
     *  失衡 节点（图中5粉红色）的 bf > 1，即右边更高
     *  失衡 节点右孩子(节点4)市bf >=0也就是右孩子这边 也是右边更高 或者等高
     *  eg:
     *  旋转前：
     *       2(P)
     *    ↙    ↘
     *   1      4(Y)
     *         ↙    ↘
     *        3(G)   5
     *                  ↘
     *                    6
     *  2是粉红色节点 向下的
     *  4是黄色节点 向上的
     *  3是绿色节点也就是黄色节点的左孩子 绿色节点需要换爹
     *  旋转的时候绿色节点作为粉红色节点的右孩子
     *  旋转后：
     *       4(Y)
     *     ↙    ↘
     *    2(P)     5
     *   ↙   ↘       ↘
     *  1   3(G)       6
     * </pre>
     *
     * @param red
     * @return
     */
    private AVLNode leftRotate(AVLNode red) {
        // 右旋的话 说明是右侧高 那么yellow不可能空指针
        AVLNode yellow = red.right;
        // 找到绿色节点 这里绿色节点是黄色节点的左孩子
        AVLNode green = yellow.left;
        yellow.left = red; // 上位  这行代码必须在上面代码执行之后
        red.right = green; // 换爹
        // 绿色节点和没有提到的节点高度是不变的
        // 红色节点和黄色节点 旋转之后 更新高度
        updateHeight(red);
        updateHeight(yellow);
        return yellow;
    }

    /**
     * 先右旋右子树, 再左旋根节点 RL
     * LR
     * - 失衡节点（即叶 6）的 bf > 1, 即左边更高
     * - 失衡节点的左孩子（即叶 7 红色）的 bf <= 0, 即左孩子这边也是右边更高
     * <pre>
     * eg:
     *       6
     *     ↙    ↘
     *    2(P)    7
     *  ↙   ↘
     * 1    4(Y)
     *     ↙    ↘
     *    3(G)   5
     * 树的节点 除了5为根节点的树不平衡之外 其他都平衡
     * 失衡 节点(图中5粉红色)的 bf > 1，即右边更高
     * 失衡 节点右孩子(节点2)是 bf >=0也就是右孩子这边 左边更高
     *
     * 如果这棵树仅仅只是右旋 ，即:
     *       6(P)
     *     ↙    ↘
     *    2(Y)   7
     *  ↙   ↘
     * 1    4(G)
     *     ↙   ↘
     *    3     5
     * 右旋之后 ，平衡的问题没有解决
     *       2(Y)
     *     ↙    ↘
     *    1     6(P)
     *          ↙    ↘
     *        4(G)    7
     *       ↙   ↘
     *      3     5
     * 正确的做法
     * eg:
     *  旋转前：
     *        6
     *      ↙    ↘
     *    2(P)    7
     *   ↙   ↘
     *  1    4(Y)
     *     ↙     ↘
     *    3(G)    5
     *  先以当前失衡节点的左子节点左旋：
     *  2是粉红色节点  向下的
     *  4是黄色节点   向上的
     *  3是绿色节点 也就是黄色节点的左孩子        绿色节点需要换爹
     *
     *  旋转的时候绿色节点作为粉红色节点的左孩子 因为左旋
     *  旋转后：
     *          6(P)
     *       ↙    ↘
     *     4(Y)     7
     *    ↙   ↘
     *   2     5(G)
     *  ↙  ↘
     *  1  3
     *  再以当前失衡节点右旋
     *  此时
     *  6是粉红色节点  向下的
     *  4是黄色节点   向上的
     *  5是绿色节点 也就是黄色节点的右孩子：
     *  绿色节点需要换爹
     *  旋转的时候绿色节点作为粉红色节点的右孩子  因为右旋
     *         4(Y)
     *       ↙    ↘
     *     2       6(P)
     *    ↙ ↘      ↙  ↘
     *   1  3     5(G)  7
     *  最后返回黄色节点4
     * </pre>
     *
     * @param node 失衡的节点
     * @return
     */
    private AVLNode leftRightRotate(AVLNode node) {
        // 对node的左孩子执行左旋 执行后侧方法返回的node 就是新的左孩子
        node.left = leftRotate(node.left);
        // 对node执行右旋  返回黄色节点
        return rightRotate(node);
    }

    /**
     * 先左旋左子树, 再右旋根节点 LR
     * RL
     * - 失衡节点（即叶 2）的 bf < -1, 即右边更高
     * - 失衡节点的右孩子（即叶 7 红色）的 bf >= 0, 即右孩子这边也是左边更高
     *
     * <pre>
     * eg:
     *          2
     *       ↙    ↘
     *      1      6(P)
     *             ↙     ↘
     *           4(Y)     7
     *         ↙    ↘
     *        3    5(G)
     *  树的节点 除了2为根节点的树不平衡之外 其他都平衡
     *  失衡 节点（图中2粉红色）的 bf > 1，即左边更高
     *  失衡 节点左孩子(节点2)市bf >=0也就是做孩子这边 右边更高
     * eg:
     * 旋转前：
     *          2
     *       ↙    ↘
     *      1      6(P)
     *             ↙     ↘
     *           4(Y)     7
     *         ↙    ↘
     *        3    5(G)
     * 先以当前节点的右子节点右旋：
     * 6是粉红色节点  向下的
     * 4是黄色节点  向上的
     * 5是绿色节点 也就是黄色节点的右孩子
     * 绿色节点需要换爹
     * 旋转的时候绿色节点作为粉红色节点的右孩子 因为右旋
     * 旋转后：
     *      2(P)
     *   ↙    ↘
     *  1      4(Y)
     *       ↙    ↘
     *      3(G)    6
     *            ↙    ↘
     *           5      7
     * 再以当前失衡节点右旋
     * 此时
     * 2是粉红色节点  向下的
     * 4是黄色节点  向上的
     * 3是绿色节点 也就是黄色节点的左孩子：
     * 绿色节点需要换爹
     * 旋转的时候绿色节点作为粉红色节点的左孩子  因为左旋
     *          4
     *        ↙    ↘
     *      2       6
     *    ↙  ↘    ↙    ↘
     *   1   3    5    7
     *  此时4也就是黄色 要被返回
     * </pre>
     *
     * @param node 失衡的节点
     * @return
     */
    private AVLNode rightLeftRotate(AVLNode node) {
        // 对node的右孩子执行右旋 执行后侧方法返回的node 就是新的右孩子
        node.right = rightRotate(node.right);
        // 在进行右旋 返回黄色节点
        return leftRotate(node);
    }

    /**
     * 检查节点是否失衡, 重新平衡代码
     *
     * @param node
     * @return
     */
    private AVLNode balance(AVLNode node) {
        if (node == null) {
            return null;
        }
        //得到节点的平衡因子
        int bf = bf(node);
        //bf > 1说明当前节点是失衡节点  失衡节点的左子树的 左子树比右子树更高
        if (bf > 1 && bf(node.left) >= 0) { // LL
            return rightRotate(node);
            //bf > 1说明当前节点是失衡节点  失衡节点的左子树的 右子树比左子树更高
        } else if (bf > 1 && bf(node.left) < 0) { // LR
            return leftRightRotate(node);
            //bf > 1说明当前节点是失衡节点   失衡节点的右子树的 左子树比右子树更高
        } else if (bf < -1 && bf(node.right) > 0) { // RL
            return rightLeftRotate(node);
            //bf > 1说明当前节点是失衡节点  失衡节点的右子树的 右子树比左子树更高
        } else if (bf < -1 && bf(node.right) <= 0) { // RR
            return leftRotate(node);
        }
        return node;
    }

    public AVLNode root;


    public void put(int key, Object value) {
        root = doPut(root, key, value);
    }

    /**
     * 新增方法   找到空位 新增
     *
     * @param node  起点节点 从该节点查找
     * @param key
     * @param value
     * @return
     */
    private AVLNode doPut(AVLNode node, int key, Object value) {
        // 1. 找到空位, 创建新节点  第一次调用put的时候 root为空 那么 node为空 就会执行这个
        if (node == null) {
            return new AVLNode(key, value);
        }
        // 2. key 已存在, 更新
        if (key == node.key) {
            node.value = value;
            return node;
        }
        // 3. 继续查找
        if (key < node.key) {
            node.left = doPut(node.left, key, value); // 向左
        } else {
            node.right = doPut(node.right, key, value); // 向右
        }
        //  新增节点后 更新高度
        updateHeight(node);
        //  再平衡
        return balance(node);
    }

    public void remove(int key) {
        root = doRemove(root, key);
    }

    /**
     * @param node 从起点node开始遍历
     * @param key  被删除的节点的key
     * @return
     */
    private AVLNode doRemove(AVLNode node, int key) {
        // 1. node == null
        if (node == null) {
            return null;
        }
        // 2. 没找到 key
        if (key < node.key) {
            // 向左孩子
            node.left = doRemove(node.left, key);
        } else if (node.key < key) {
            // 向右孩子
            node.right = doRemove(node.right, key);
        } else {
            // 3. 找到 key  1) 没有孩子 2) 只有一个孩子 3) 有两个孩子
            if (node.left == null && node.right == null) {
                return null;
            } else if (node.left == null) {
                node = node.right;
            } else if (node.right == null) {
                node = node.left;
            } else {
                /**
                 * 待删除的节点的右孩子还是找，不断对做右孩子的左分支遍历
                 * 直到某一个节点没有左子树 此时这个节点就是要找的后继结点
                 */
                AVLNode s = node.right;
                while (s.left != null) {
                    s = s.left;
                }
                /**
                 * 此时s 就是后继节点 ，后继节点 先从 需要被删除的节点为根的子树中删去
                 * 但不是说不用这个后继节点了
                 * 然后 后继结点 替代 被删除的节点 也就是 被删除的节点的右子树 接到 后继结点 上
                 * 这个右子树 原本是有 后继结点，但是因为后继结点要体会被删除的节点，所以先被删除
                 */
                s.right = doRemove(node.right, s.key);
                // 后继结点的左子树 就是被删除节点的左子树
                s.left = node.left;
                node = s;
            }
        }
        // 4. 更新高度
        updateHeight(node);
        // 5. balance
        return balance(node);
    }
}
