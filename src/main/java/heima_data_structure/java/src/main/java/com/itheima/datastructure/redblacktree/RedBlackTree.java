package heima_data_structure.java.src.main.java.com.itheima.datastructure.redblacktree;
import static heima_data_structure.java.src.main.java.com.itheima.datastructure.redblacktree.RedBlackTree.Color.BLACK;
import static heima_data_structure.java.src.main.java.com.itheima.datastructure.redblacktree.RedBlackTree.Color.RED;

/**
 *TODO
 * <h3>红黑树</h3>
 * 红黑树也是一种自平衡的二叉搜索树，较之AVL，插入和删除时旋转次数更少
 * 红黑树 平衡特性
 * 1.所有节点都有两种颜色:红与黑
 * 2.所有null视为黑色
 * 3.红色节点不能相邻
 * 4.根节点是黑色
 * 5.从根到任意一个叶子节点，路径中的黑色节点数一样（黑色完美平衡)
 * 下面这个树是平衡的
 *         6(B)
 *      ↙       ↘
 *   2(B)       8(B)
 *   ↙
 * 1(R)
 * 下面这个树是不平衡的
 *         6(B)
 *     ↙       ↘
 *   2(R)      8(B)
 *   ↙
 * 1(B)
 * 因为叶子节点是黑色节点并且该节点没有兄弟的时候 需要考虑空指针
 *                  6(B)
 *             ↙          ↘
 *        2(R)              8(B)
 *        ↙
 *     1(B)
 *  画上null节点
 *                  6(B)
 *             ↙          ↘
 *        2(R)              8(B)
 *        ↙  ↘            ↙   ↘
 *     1(B)   N(B)     N(B)    N(B)
 *   ↙  ↘
 * N(B) N(B)
 * 此时这棵树是不平衡的
 * 该树需要转变成
 *                  6(B)
 *             ↙          ↘
 *        2(B)              8(B)
 *        ↙  ↘            ↙   ↘
 *     1(R)   N(B)     N(B)    N(B)
 *    ↙  ↘
 * N(B) N(B)
 * 小结论
 * 如果叶子结点是红色，他没有兄弟节点是平衡的，如果叶子结点是黑色但是没有兄弟节点，那么一定不平衡
 */
public class RedBlackTree {

    public enum Color {
        RED, BLACK;
    }

    public Node root;

    public static class Node {
        public int key;
        public Object value;
        // 左子节点
        public Node left;
        // 右子节点
        public Node right;
        // 父节点
        public Node parent;
        // 初始颜色
        public Color color = RED;

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        public Node(int key) {
            this.key = key;
        }

        public Node(int key, Color color) {
            this.key = key;
            this.color = color;
        }

        public Node(int key, Color color, Node left, Node right) {
            this.key = key;
            this.color = color;
            this.left = left;
            this.right = right;
            if (left != null) {
                left.parent = this;
            }
            if (right != null) {
                right.parent = this;
            }
        }

        //TODO  是否是左孩子
        public boolean isLeftChild() {
            return parent != null && parent.left == this;
        }

        //TODO  得到当前节点的叔叔
        public Node uncle() {
            //TODO  父亲是null 爷爷也是null 不可能有叔叔
            if (parent == null || parent.parent == null) {
                return null;
            }
            //TODO 判断父亲是不是爷爷的左孩子
            if (parent.isLeftChild()) {
                //TODO  是的话 叔叔是爷爷的右孩子
                return parent.parent.right;
            } else {
                //TODO  是的话 叔叔是爷爷的左孩子
                return parent.parent.left;
            }
        }

        //TODO 兄弟
        public Node sibling() {
            if (parent == null) {
                return null;
            }
            //TODO 判断当前节点是不是父亲的左孩子
            if (this.isLeftChild()) {
                //TODO 返回当前节点父亲的右孩子
                return parent.right;
            } else {
                //TODO 返回当前节点父亲的左孩子
                return parent.left;
            }
        }
    }

    //TODO 判断红
    boolean isRed(Node node) {
        return node != null && node.color == RED;
    }

    //TODO  判断黑
    boolean isBlack(Node node) {
        return node == null || node.color == BLACK;
    }

    /**
     *TODO  P = Pink Y = Yellow  G = Green
     * 右旋
     * 1. parent 的处理
     * 2. 旋转后新根的父子关系
     * eg1:
     * 旋转前
     *                      8(P)
     *                ↙              ↘
     *           5(Y)                   10(B)
     *         ↙     ↘                  ↙     ↘
     *      3(R)       6(G)        9(R)      11(R)
     *      ↙   ↘       ↘
     *   2(B)  4(B)      7(R)
     *   ↙
     * 1(R)
     * 黄色是未来子树的新根的节点  绿色是要换爹的节点  粉色是传入的节点
     * 旋转后
     *                      5(Y)
     *               ↙              ↘
     *           3(R)                  8(P)
     *        ↙      ↘                ↙      ↘
     *      3(R)     4(B)        6(G)           10(B)
     *      ↙                       ↘         ↙      ↘
     *   2(B)                       7(R)    9(R)    11(R)
     *   ↙
     * 1(R)
     * eg2: 上面例子有点特殊 因为 pink的父节点是null
     * 旋转前
     *              8(B)
     *            ↙      ↘
     *        5(P)        10(B)
     *         ↙
     *      3(Y)
     *      ↙
     *    2(R)
     * 旋转后
     *              8(B)
     *           ↙      ↘
     *        3(Y)       10(B)
     *       ↙   ↘
     *    2(R)  5(P)
     */
    private void rightRotate(Node pink) {
        // 用来建立新的父子关系
        Node parent = pink.parent;
        Node yellow = pink.left;
        Node green = yellow.right;
        //TODO 绿色节点可能是null
        if (green != null) {
            //TODO 粉色节点变成了绿色节点的新的爹 单向绑定 ，还差一个方向
            green.parent = pink;
        }
        //TODO 黄色节点的右子节点是粉色节点  单项绑定了，还差一个方向
        yellow.right = pink;
        //TODO 粉色节点的爹变成了黄色的新的爹 此时黄色节点和新的爹 单项绑定了，还差一个方向
        yellow.parent = parent;
        //TODO 粉色节点变成了绿色节点的新的爹 单向绑定
        pink.left = green;
        //TODO 黄色变成了粉色节点的新的爹
        pink.parent = yellow;
        //TODO 说明 pink是根节点
        if (parent == null) {
            root = yellow;
            //TODO 判断pink原来是不是其父节点的左子节点
        } else if (parent.left == pink) {
            // 黄色作为其父节点的新的左子节点
            parent.left = yellow;
        } else {//TODO 判断pink原来是不是其父节点的右子节点
            parent.right = yellow;
        }
    }

    // 左旋
    private void leftRotate(Node pink) {
        Node parent = pink.parent;
        Node yellow = pink.right;
        Node green = yellow.left;
        if (green != null) {
            green.parent = pink;
        }
        yellow.left = pink;
        yellow.parent = parent;
        pink.right = green;
        pink.parent = yellow;
        if (parent == null) {
            root = yellow;
        } else if (parent.left == pink) {
            parent.left = yellow;
        } else {
            parent.right = yellow;
        }
    }

    /**
     * 新增或更新
     * <br>
     * 正常增、遇到红红不平衡进行调整
     * 如果遇到相同的就更新 没有的话 就新增  4种case
     * @param key   键
     * @param value 值
     */
    public void put(int key, Object value) {
        Node p = root;
        Node parent = null;
        while (p != null) {
            parent = p;
            if (key < p.key) {
                p = p.left;//TODO 向左子分支移动
            } else if (p.key < key) {
                p = p.right;//TODO  向右子分支移动
            } else {
                p.value = value; //TODO 更新
                return;
            }
        }
        //TODO 创建出需要插入的对象
        Node inserted = new Node(key, value);
        if (parent == null) {//TODO 说明插入之前 树是空的
            root = inserted;
        } else if (key < parent.key) {//TODO 判断当前插入的节点是被插入节点的左孩子
            //TODO 绑定 父 <-> 子 双向关系
            parent.left = inserted;
            inserted.parent = parent;
        } else {//TODO 判断当前插入的节点是被插入节点的右孩子
            //TODO 绑定 父 <-> 子 双向关系
            parent.right = inserted;
            inserted.parent = parent;
        }
        //TODO 新增节点因为默认是R，连续两个节点是R，所以需要再平衡
        fixRedRed(inserted);
    }

    /**
     * 修复2个红色节点22相邻
     *TODO 规则
     * 1.所有节点都有两种颜色:红与黑
     * 2.所有null视为黑色
     * 3.红色节点不能相邻
     * 4.根节点是黑色
     * 5.从根到任意一个叶子节点，路径中的黑色节点数一样（黑色完美平衡)
     * case 1 插入节点是根节点，变黑即可
     * case 2 插入节点父亲是黑色，无需调整
     * case 3:叔叔为红色 插入节点的父亲为红色，触发红红相邻
     * 修复流程：
     * 父亲变为黑色，为了保证黑色平衡，连带的叔叔也变为黑色
     * 祖父如果是果色不变，会造成这颗子树黑色过多，因此祖父节点变为红色
     * 祖父如果变成红色，可能会接着触发红红相邻，因此对将祖父进行递归调整
     * case 4 当红红相邻，叔叔为黑时
     * 修复流程：
     * 1.父亲为左孩子，插入节点也是左孩子，此时即LL不平衡 => 父亲节点右旋
     * 2.父亲为左孩子，插入节点是右孩子，此时即LR不平衡  => 父亲节点左旋 爷爷右旋
     * 3.父亲为右孩子，插入节点也是右孩子，此时即 RR不平衡 => 父亲节点左旋
     * 4.父亲为右孩子，插入节点是左孩子，此时即 RL不平衡 => 父亲节点右旋 爷爷左旋
     * @param x
     */
    void fixRedRed(Node x) {
        /**
         *TODO case 1 插入节点是根节点，变黑即可
         */
        if (x == root) {
            x.color = BLACK;
            return;
        }
        /**
         * TODO case 2 插入节点父亲是黑色，无需调整
         */
        if (isBlack(x.parent)) {
            return;
        }
        /**
         *TODO
         * case 3
         * 当红红相邻，叔叔为红时
         * 需要将父亲、叔叔变黑、祖父变红，然后对祖父做递归处理
         * eg:
         *                          9(B)
         *                  ↙                 ↘
         *          5(R)                            13(R)
         *         ↙     ↘                      ↙       ↘
         *     3(B)         7(B)           11(B)          15(B)
         *     ↙   ↘        ↙   ↘           ↙   ↘          ↙   ↘
         *  2(R)  4(R)    6(R)  8(R)    10(R)  12(R)  14(R)   16(R)
         * 插入1(R)
         *                          9(B)
         *                  ↙                 ↘
         *          5(R)                            13(R)
         *         ↙     ↘                      ↙       ↘
         *     3(B)         7(B)           11(B)          15(B)
         *     ↙   ↘        ↙   ↘           ↙   ↘          ↙   ↘
         *  2(R)  4(R)    6(R)  8(R)    10(R)  12(R)  14(R)   16(R)
         *  ↙
         * 1(R)
         * 此时违反了规则3 先变色 2(R) -> 2(B)  父亲变色
         *                          9(B)
         *                  ↙                 ↘
         *          5(R)                            13(R)
         *         ↙     ↘                      ↙       ↘
         *     3(B)         7(B)           11(B)          15(B)
         *     ↙   ↘        ↙   ↘           ↙   ↘          ↙   ↘
         *  2(B)  4(R)    6(R)  8(R)    10(R)  12(R)  14(R)   16(R)
         *  ↙
         * 1(R)
         * 此时违反了规则5 变色 2(R) -> 4(B) 叔叔变色
         *                          9(B)
         *                 ↙                 ↘
         *          5(R)                            13(R)
         *        ↙     ↘                        ↙       ↘
         *     3(B)         7(B)           11(B)          15(B)
         *    ↙   ↘        ↙   ↘           ↙   ↘          ↙   ↘
         *  2(B)  4(B)    6(R)  8(R)    10(R)  12(R)  14(R)   16(R)
         *  ↙
         * 1(R)
         * 此时还是违反了规则5 变色 3(B) -> 3(R) 爷爷变色
         *                          9(B)
         *          5(R)                            13(R)
         *        ↙     ↘                        ↙       ↘
         *     3(R)         7(B)           11(B)          15(B)
         *    ↙   ↘        ↙   ↘           ↙   ↘          ↙   ↘
         *  2(B)  4(B)    6(R)  8(R)    10(R)  12(R)  14(R)   16(R)
         *  ↙
         * 1(R)
         * 此时违反了规则3 先变色 5(R) -> 5(B)  3的父亲5变色   递归 至根节点☆☆☆☆☆☆☆☆
         *                          9(B)
         *          5(B)                            13(R)
         *        ↙     ↘                        ↙       ↘
         *     3(R)         7(B)           11(B)          15(B)
         *    ↙   ↘        ↙   ↘           ↙   ↘          ↙   ↘
         *  2(B)  4(B)    6(R)  8(R)    10(R)  12(R)  14(R)   16(R)
         *  ↙
         * 1(R)
         * 此时违反了规则5 变色 13(R) -> 13(B) 叔叔变色  递归 至根节点☆☆☆☆☆☆☆☆
         *                          9(B)
         *          5(B)                            13(B)
         *        ↙     ↘                        ↙       ↘
         *     3(R)         7(B)           11(B)          15(B)
         *     ↙   ↘        ↙   ↘           ↙   ↘          ↙   ↘
         *  2(B)  4(B)    6(R)  8(R)    10(R)  12(R)  14(R)   16(R)
         *  ↙
         * 1(R)
         * 此时还是违反了规则5 变色 9(B) -> 9(R) 爷爷变色  递归 至根节点☆☆☆☆☆☆☆☆
         *                          9(R)
         *          5(B)                            13(B)
         *         ↙     ↘                        ↙       ↘
         *     3(R)         7(B)           11(B)          15(B)
         *    ↙   ↘        ↙   ↘           ↙   ↘          ↙   ↘
         *  2(B)  4(B)    6(R)  8(R)    10(R)  12(R)  14(R)   16(R)
         *  ↙
         * 1(R)
         * 此时还是违反了规则4 9(R) -> 9(B)
         *                          9(B)
         *          5(B)                            13(B)
         *        ↙     ↘                        ↙       ↘
         *     3(R)         7(B)           11(B)          15(B)
         *     ↙   ↘        ↙   ↘           ↙   ↘          ↙   ↘
         *  2(B)  4(B)    6(R)  8(R)    10(R)  12(R)  14(R)   16(R)
         *  ↙
         * 1(R)
         */
        Node parent = x.parent;
        Node uncle = x.uncle();
        Node grandparent = parent.parent;
        //TODO 如果叔叔是红色，就变色
        if (isRed(uncle)) {
            parent.color = BLACK;
            uncle.color = BLACK;
            grandparent.color = RED;
            //TODO 递归 此时祖父判断
            fixRedRed(grandparent);
            return;
        }

        /**
         *TODO
         * case 4
         * 当红红相邻，叔叔为黑时
         * eg: LL不平衡
         *              8(B)
         *           ↙        ↘
         *        5(B)       10(B)
         *        ↙
         *    3(R)
         * 插入2 造成了
         *              8(B)
         *            ↙        ↘
         *        5(B)       10(B)
         *        ↙
         *    3(R)
         *    ↙
         * 2(R)
         * 下面是错误的解决方法：
         * 父亲3变成黑色
         *              8(B)
         *           ↙        ↘
         *        5(B)       10(B)
         *        ↙
         *    3(B)
         *   ↙
         * 2(R)
         * 爷爷3变成红色
         *              8(B)
         *            ↙        ↘
         *        5(R)       10(B)
         *         ↙
         *    3(B)
         *     ↙
         * 2(R)
         * 此时对于2的叔叔节点而言，也就是空节点，这里没有画出来
         * 叔叔到根的路径就会少黑色节点，必须要其他解决方法
         *              8(B)
         *           ↙        ↘
         *        5(R,P)       10(B)
         *        ↙
         *    3(B,Y)
         *   ↙
         * 2(R)
         * 此时3作为黄色节点(从旋转的角度)，他还是黑色节点(从红黑树的角度)
         * 此时5作为粉色节点(从旋转的角度)，他还是红色节点(从红黑树的角度)
         * 以粉色节点作为轴 旋转
         * 旋转之后
         *              8(B)
         *          ↙        ↘
         *        3(B,Y)       10(B)
         *     ↙        ↘
         *   2(R)     5(R,P)
         * 此时平衡了
         * eg2：
         * LR不平衡的例子：
         *           8(B)
         *        ↙        ↘
         *      5(B)      10(B)
         *     ↙
         *  3(R)
         *  插入节点4
         *           8(B)
         *         ↙      ↘
         *      5(B)      10(B)
         *     ↙
         *  3(R)
         *      ↘
         *       4(R)
         *  先以 3为粉红色节点 为轴  4为黄色节点 开始左旋
         *  也就是
         *           8(B)
         *          ↙      ↘
         *      5(B)      10(B)
         *      ↙
         *  3(R,P)
         *      ↘
         *       4(R,Y)
         *  得到
         *           8(B)
         *         ↙      ↘
         *      5(B)      10(B)
         *       ↙
         *    4(R,Y)
         *    ↙
         *  3(R,P)
         *  再让 4节点变成黑色
         *             8(B)
         *           ↙      ↘
         *        5(B)      10(B)
         *        ↙
         *     4(B,Y)
         *     ↙
         *  3(R,P)
         *  再让 祖父5变成红色
         *            8(B)
         *         ↙      ↘
         *       5(R)      10(B)
         *       ↙
         *     4(B,Y)
         *     ↙
         *  3(R,P)
         *  再让 祖父5为轴 开始右旋
         *             8(B)
         *           ↙      ↘
         *       5(R,P)      10(B)
         *       ↙
         *     4(B,Y)
         *     ↙
         *  3(R)
         *  旋转后：
         *            8(B)
         *            ↙      ↘
         *       4(B,Y)      10(B)
         *      ↙      ↘
         *   3(R)  5(R,P)
         */
        if (parent.isLeftChild() && x.isLeftChild()) { //TODO LL 父亲是左孩子并且插入节点也是左孩子
            parent.color = BLACK;
            grandparent.color = RED;
            //TODO 右旋
            rightRotate(grandparent);
        } else if (parent.isLeftChild()) { //TODO LR 父亲是左孩子并且插入节点是右孩子
            //TODO 左旋
            leftRotate(parent);
            //TODO 修改当前颜色
            x.color = BLACK;
            //TODO 修改祖父颜色
            grandparent.color = RED;
            //TODO 右旋
            rightRotate(grandparent);
        } else if (!x.isLeftChild()) { //TODO RR 父亲是右孩子并且插入节点也是右孩子
            parent.color = BLACK;
            grandparent.color = RED;
            leftRotate(grandparent);
        } else { //TODO RL 父亲是右孩子并且插入节点是左孩子
            //TODO 右旋
            rightRotate(parent);
            //TODO 修改当前颜色
            x.color = BLACK;
            //TODO 修改祖父颜色
            grandparent.color = RED;
            //TODO 左旋
            leftRotate(grandparent);
        }
    }

    /**
     *TODO 删除
     * 正常删、会用到李代桃僵技巧、遇到黑黑不平衡进行调整  6种case
     * @param key 键
     */
    public void remove(int key) {
        Node deleted = find(key);
        if (deleted == null) {
            return;
        }
        doRemove(deleted);
    }

    public boolean contains(int key) {
        return find(key) != null;
    }

    // 查找删除节点
    private Node find(int key) {
        Node p = root;
        //TODO 二叉搜索树的查找方式
        while (p != null) {
            if (key < p.key) {
                p = p.left;
            } else if (p.key < key) {
                p = p.right;
            } else {
                return p;
            }
        }
        return null;
    }

    /**
     *TODO 查找剩余节点  也就是后继结点 用来替换被删除的节点
     * @param deleted
     * @return
     */
    private Node findReplaced(Node deleted) {
        //TODO 被删除的节点 是叶子节点
        if (deleted.left == null && deleted.right == null) {
            return null;
        }
        //TODO 说明 只有右孩子
        if (deleted.left == null) {
            return deleted.right;
        }
        //TODO 说明 只有左孩子
        if (deleted.right == null) {
            return deleted.left;
        }
        /**
         *TODO
         * 下面的操作是找到被删除节点的后继节点
         * 来到右孩子
         */
        Node s = deleted.right;
        while (s.left != null) {//TODO 走到右孩子为根的树的最左侧
            s = s.left;
        }
        return s;
    }

    /**
     *TODO
     * 处理双黑 (case3、case4、case5)
     * 删除节点和剩下节点都是黑色，触发双黑，双黑意思是，少了一个黑，那个黑就是被删节点
     * 因为这个情况是无法通过变色来补齐黑色节点数量，另一个节点就是黑色
     * case 3:删除节点或剩余节点的兄弟为红，此时两个侄子定为黑
     * case 4:删除节点或剩余节点的兄弟、和兄弟孩子都为黑
     * case 5:删除节点的兄弟为黑，至少一个红侄子
     * 规律：被删除的节点的兄弟是红色节点的话，对于红色节点 要么没有孩子要么有两个孩子☆☆☆☆☆☆☆☆☆
     * eg:
     *          6(B)
     *       ↙           ↘
     *  4(B)              8(R)
     *                  ↙      ↘
     *              7(B)       9(B)
     *              ↙
     *            6.5(R)
     * 上面的树 满足case3
     * 现在删除的是4，其侄子是7
     *          6(B,P)
     *     ↙           ↘
     *  4(B)              8(R,Y)
     *                  ↙      ↘
     *              7(B,G)       9(B)
     *              ↙
     *            6.5(R)
     * 1.以4的父亲6 为轴左旋
     *          8(R,Y)
     *         ↙      ↘
     *     6(B,P)       9(B)
     *    ↙      ↘
     *  4(B)   7(B,G)
     *          ↙
     *       6.5(R)
     * 现在 4和以前的侄子7变成了兄弟
     * 2.现在让这棵树平衡
     * 被删除的节点原来的父亲和原来的兄弟要互换颜色 ☆☆☆☆☆☆☆☆☆
     *          8(B,Y)
     *     6(R,P)       9(B)
     *  4(B)   7(B,G)
     *       6.5(R)
     * @param x
     */
    private void fixDoubleBlack(Node x) {
        if (x == root) {
            return;
        }
        //TODO 得到被调整节点的旧的父亲
        Node parent = x.parent;
        //TODO 得到被调整节点的旧的兄弟
        Node sibling = x.sibling();
        //TODO case 3 被调整的节点的旧的兄弟节点是红色
        if (isRed(sibling)) {
            //TODO 判断被调整节点是其父亲的左孩子还是右孩子
            if (x.isLeftChild()) {
                //TODO 左旋
                leftRotate(parent);
            } else {
                //TODO 右旋
                rightRotate(parent);
            }
            /**
             *TODO 得到被调整节点的父亲和兄弟 互换颜色
             *  父亲的颜色一定是红色 因为上面判断被调整的节点是红色 会导致红红相邻
             */
            parent.color = RED;
            sibling.color = BLACK;
            /**
             * 目的 让被调整节点的新的兄弟 也就是原来的侄子要符合黑色  传入的参数还是之前传入的
             * 只不过此时被调整节点新的兄弟已经是黑色的了
             */
            fixDoubleBlack(x);
            return;
        }
        /**
         * TODO case 4 兄弟是黑色, 两个侄子也是黑色
         * 1.将兄弟变红，目的是将删除节点和兄弟那边的黑色高度同时减少1
         * 2.如果父亲是红，则需将父亲变为黑，避免红红，此时路径黑节点数目不变
         * 3.如果父亲是黑，说明这条路径则少了一个黑，再次让父节点触发双黑
         * eg1:
         *       4(B)
         *  3(B)       7(B)
         *          N(B)    N(B)
         * 现在删除3
         * 现在让7变成红色即可
         * eg2:
         *                          8(B)
         *         4(B)                          12(B)
         *   2(R)       6(R)             10(R)          14(R)
         * 1(B) 3(B)   5(B)  7(B)       9(B)  11(B)   13(B)  15(B)
         * 现在删除1
         * 先让3变成红色，那么以2为根的子节点 就平衡了  但是 导致了从根节点出发到1和3的黑色节点数比到其他叶子结点少1
         * 让父亲2节点 变成黑色即可
         * eg3:
         *                          8(B)
         *         4(B)                          12(B)
         *   2(B)       6(B)             10(B)          14(B)
         * 1(B) 3(B)   5(B)  7(B)      9(B)  11(B)   13(B)  15(B)
         * 现在删除1
         * 让被删除节点的兄弟3变成红色  这样的话根节点到2节点的孩子节点之间的路径的黑色数量相同
         *                          8(B)
         *         4(B)                          12(B)
         *   2(B)       6(B)             10(B)          14(B)
         *      3(R)   5(B)  7(B)      9(B)  11(B)   13(B)  15(B)
         * 但是这个黑色节点数量比根节点到其他叶子节点路径的黑色节点数量少1
         * 递归处理 让2节点的兄弟 变成红色
         *                          8(B)
         *         4(B)                          12(B)
         *   2(B)       6(R)             10(B)          14(B)
         *      3(R)   5(B)  7(B)      9(B)  11(B)   13(B)  15(B)
         * 此时 以4为根节点的子树平衡了 但是以8为根节点的子树不平衡
         * 递归处理 让4节点的兄弟 变成红色
         *                          8(B)
         *         4(B)                          12(R)
         *   2(B)       6(R)             10(B)          14(B)
         *      3(R)   5(B)  7(B)      9(B)  11(B)   13(B)  15(B)
         */
        if (sibling != null) {
            // 被调整节点的左侄子和右侄子是否都是黑
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                sibling.color = RED;
                if (isRed(parent)) {//父亲是红色的话
                    // 父亲颜色变黑
                    parent.color = BLACK;
                } else {
                    // 递归调用父节点
                    fixDoubleBlack(parent);
                }
            }
            else {
                /**
                 *case 5:被调整节点的兄弟为黑，至少一个红侄子
                 * 如果兄弟是左孩子，左侄子是红，LL不平衡·
                 * 如果兄弟是左孩子，右侄子是红，LR不平衡·
                 * 如果兄弟是右孩子，右侄子是红，RR不平衡·
                 * 如果兄弟是右孩子，左侄子是红，RL不平衡
                 *  LL
                 * eg1:
                 *              5(B)
                 *        3(R)         7(R)
                 *   2(B)   4(B)     6(B)  8(B)
                 *  1(R)
                 * 现在被删除的节点是4
                 *              5(B)
                 *        3(R)         7(R)
                 *   2(B)            6(B)  8(B)
                 *  1(R)
                 * 这个情况是LL不平衡
                 * 尝试把被删节点的兄弟变成红色，会导致 兄弟和其孩子都是红色
                 * 所以用旋转
                 * 以被删节点4的父亲为轴(粉色)右旋，兄弟节点是黄色
                 *              5(B)
                 *        3(R,P)         7(R)
                 *   2(B,Y)   4(B)     6(B)  8(B)
                 *  1(R)
                 * 旋转后
                 *              5(B)
                 *        2(B,Y)         7(R)
                 *   1(R)   3(R,P)    6(B)  8(B)
                 *              4(B)
                 *  再变色，因为4被删，所以其父亲3要变成黑色，保证根节点到叶子节点的路径上黑色节点相同
                 *  并且2也要变成红色，因为3要变成黑色，保证根节点到叶子节点的路径上黑色节点相同
                 *  这导致根节点到1节点的黑色节点数目变少，所以也要1变黑色
                 * eg2 此时3是黑色:
                 *              5(B)
                 *        3(B)         7(R)
                 *   2(B)   4(B)     6(B)  8(B)
                 *  1(R)
                 *  以被删节点4的父亲为轴(粉色)右旋，兄弟节点是黄色
                 * 旋转后
                 *              5(B)
                 *        2(B,Y)         7(R)
                 *   1(R)   3(B,P)    6(B)  8(B)
                 *              4(B)
                 *  免去了2和3的变色，但是1还是变色
                 */
                if (sibling.isLeftChild() && isRed(sibling.left)) {
                    rightRotate(parent);
                    sibling.left.color = BLACK;
                    sibling.color = parent.color;
                }
                /**
                 * LR  被删除节点的兄弟是其父节点的左孩子 而且 其兄弟的右孩子是红色
                 * 原始：
                 *              5(B)
                 *        3(R)              7(R)
                 *   1(B)       4(B)     6(B)  8(B)
                 *      2(R)
                 *  现在被删除的节点是4
                 *              5(B)
                 *        3(R)         7(R)
                 *   1(B,P)     4(B)     6(B)  8(B)
                 *      2(R,Y)
                 *  以1这个粉色节点为轴 左旋 2是黄色 也就是
                 *  旋转后
                 *              5(B)
                 *        3(R)         7(R)
                 *   2(R,Y)   4(B)     6(B)  8(B)
                 * 1(B,P)
                 * 此时这棵树就成了LL
                 *              5(B)
                 *        3(R)         7(R)
                 *   2(R,Y)   4(B)   6(B)  8(B)
                 * 1(B,P)
                 * 以1节点的原始父节点3，这个3也是4的父节点 这个粉色节点为轴 右旋 2是黄色 下面这棵树
                 *              5(B)
                 *        3(R,P)         7(R)
                 *   2(R,Y)   4(B)   6(B)  8(B)
                 * 1(B)
                 * 旋转后
                 *              5(B)
                 *     2(R,Y)              7(R)
                 *  1(B)  3(R,P)        6(B)  8(B)
                 *            4(B)
                 *   因为4节点是被删除节点 4是黑色
                 *   所以其父亲3变黑色
                 *   还没有旋转之前 1是4的兄弟 ，2是1的右孩子
                 *   在第一次旋转之后，2就是3的孩子
                 *   在第二次旋转之后，2变成了3的父亲，2这个颜色要设置成第一次旋转后，其父节点的颜色 也就是红色
                 *   这里 2和3的原始颜色正好相同
                 * eg2
                 *              6(B)
                 *        2(R)                 8(R)
                 *   1(B)    4(R)           7(B)  9(R)
                 *         3(B) 5(B)
                 *  现在删除的是7
                 *                  6(B)
                 *        2(R,P)               8(R)
                 *   1(B)     4(R,Y)         7(B)  9(R)
                 *         3(B,G)  5(B)
                 *  先围绕2(粉色)左旋  4是黄色 3是绿色
                 *  旋转后
                 *                  6(B)
                 *        4(R,Y)               8(R)
                 *   2(B,P)     5(B)         7(B)  9(R)
                 * 1(B) 3(B,G)
                 * 接下来围绕6(粉色)右旋  4是黄色 5是绿色
                 *                  6(B,P)
                 *        4(R,Y)             8(R)
                 *   2(B)     5(B,G)      7(B)  9(R)
                 * 1(B) 3(B)
                 * 旋转后
                 *                4(R,Y)
                 *        2(B)              6(R,P)
                 *   1(B)     3(B)     5(B,G)     8(R)
                 *                              7(B)  9(R)
                 *  7被删除 其父节点变成黑色
                 *  根节点4肯定要变色
                 */
                else if (sibling.isLeftChild() && isRed(sibling.right)) {
                    sibling.right.color = parent.color;
                    leftRotate(sibling);
                    // 围绕
                    rightRotate(parent);
                }
                // RL
                else if (!sibling.isLeftChild() && isRed(sibling.left)) {
                    sibling.left.color = parent.color;
                    rightRotate(sibling);
                    leftRotate(parent);
                }
                // RR
                else {
                    leftRotate(parent);
                    sibling.right.color = BLACK;
                    sibling.color = parent.color;
                }
                parent.color = BLACK;
            }
        } else {
            //TODO 实际也不会出现，触发双黑后，兄弟节点不会为 null
            fixDoubleBlack(parent);
        }
    }

    /**
     * 删除的本质是让后继节点的值付给被删除节点，后继节点置空
     * @param deleted
     */
    private void doRemove(Node deleted) {
        //TODO 这个是被删除接节点的后继结点
        Node replaced = findReplaced(deleted);
        Node parent = deleted.parent;
        //TODO case1 没有孩子
        if (replaced == null) {
            //TODO case 1.1 需要删除的是根节点 没有孩子
            if (deleted == root) {
                root = null;
            } else {//TODO case 1.2 删除的不是根节点 并且 没有孩子
                /**
                 * 这个没有孩子的情况
                 * 是先调整 调整的是被删节点 再删除被删节点
                 *
                 *TODO
                 * eg1:
                 *               8              (B)
                 *            ↙      ↘
                 *        4             12      (B)
                 *      ↙  ↘            ↙  ↘
                 *    2     6        10     14      (R)
                 *   ↙ ↘   ↙ ↘      ↙ ↘    ↙ ↘
                 *  1  3  5  7     9  11  13  15    (B)
                 * 这里我要删除1节点 或者是3节点 因为是叶子节点  先直接置空
                 * 此时被删除的节点是黑色节点 需要考虑平衡 ☆☆☆☆☆☆☆☆☆☆
                 */
                if (isBlack(deleted)) {//TODO 被删除节点是不是黑色
                    //TODO 复杂调整
                    fixDoubleBlack(deleted);
                } else {
                    //TODO 红色叶子, 无需任何处理
                }
                if (deleted.isLeftChild()) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
                deleted.parent = null;
            }
            return;
        }
        //TODO case2 有一个孩子
        if (deleted.left == null || deleted.right == null) {
            /**
             *TODO
             * case 2.1:删的是根节点，但是根节点有一个孩子
             * 删完了，直接将root = null
             * 用剩余节点替换了根节点的key，value，根节点孩子 = null，颜色保持黑色不变
             * 根节点只有一个孩子的话 那么孩子一定是红色
             * 直接让根节点和唯一孩子节点的数据互换，删去孩子即可
             * 根节点有2个孩子的话 转成 1个孩子/0个孩子
             */
            if (deleted == root) {//TODO case 2.1 需要删除的是根节点
                root.key = replaced.key;
                root.value = replaced.value;
                //TODO 不关心这个情况根节点的孩子是左还是右 全部置空
                root.left = root.right = null;
            } else {//TODO case 2.2 删除的不是根节点 但是有一个孩子
                /**
                 *TODO
                 * 这个有一个孩子的情况
                 * 实现删去被删节点，再调整
                 *
                 *TODO
                 *               8(B)
                 *            ↙      ↘
                 *        4(B)         12(B)
                 *      ↙  ↘             ↙  ↘
                 *    2(B)  6(R)      10(R)   14(R)
                 *   ↙     ↙    ↘      ↙   ↘       ↙ ↘
                 *  1(R) 5(B)  7(B)  9(B) 11(B)  13(B)  15(B)
                 * 这里我要删除2节点 黑色节点 需要考虑平衡
                 * 因为被删节点是其父亲的左孩子 那么父亲的新的左孩子 连接的是被删节点的唯一的孩子
                 * 如果被删除的是红色节点 不需要考虑平衡 因为不影响黑色节点个数 ☆☆☆☆☆☆☆☆☆☆☆
                 */
                if (deleted.isLeftChild()) {
                    // 单项绑定完
                    parent.left = replaced;
                } else {
                    // 单项绑定完
                    parent.right = replaced;
                }
                // 另一个方向的绑定
                replaced.parent = parent;
                deleted.left = deleted.right = deleted.parent = null;
                //TODO 删除的节点是黑色， 剩下的节点（也就是后继节点）也是黑色
                if (isBlack(deleted) && isBlack(replaced)) {
                    //TODO 复杂处理  实际不会有这种情况 因为只有一个孩子时 被删除节点是黑色 那么剩余节点只能是红色不会触发双黑
                    fixDoubleBlack(replaced);
                } else {
                    /**
                     *TODO
                     * case 2:删的是黑，剩下的是红，剩下这个红节点变黑，变黑的原因是让该路径的黑色节点个数不变
                     * eg:
                     *                      8(B)
                     *             ↙                        ↘
                     *         4(B)                          12(B)
                     *       ↙      ↘                       ↙      ↘
                     *   2(B)       6(R)             10(R)          14(R)
                     *     ↘         ↙    ↘        ↙      ↘       ↙      ↘
                     *    3(R)     5(B)  7(B)     9(B)  11(B)   13(B)  15(B)
                     * 删黑色会失衡，删红色不会失衡，但删黑色有一种简单情况
                     * 把2节点删去  3接替2 但是变黑
                     *                      8(B)
                     *             ↙                        ↘
                     *         4(B)                          12(B)
                     *       ↙      ↘                       ↙      ↘
                     *   3(B)       6(R)             10(R)          14(R)
                     *              ↙    ↘        ↙      ↘       ↙      ↘
                     *            5(B)  7(B)     9(B)  11(B)   13(B)  15(B)
                     */
                    replaced.color = BLACK;
                }
            }
            return;
        }
        /**
         *TODO
         *  case 3.0 有两个孩子 最终会转换成 有一个孩子 或 没有孩子的情况
         *  为什么能实现：
         *  不同于之前的删除方式 也就是找到后继结点 然后后继结点的左右孩子被赋值上 被删除节点的左右孩子  再删除被删除节点
         *  这里直接把后继结点的所有值 覆盖掉被删除节点  然后再把后继结点删去
         */
        //TODO 把被删除的节点的key暂存下来
        int t = deleted.key;
        //TODO 把被删除的节点的key用后继结点的key替换掉
        deleted.key = replaced.key;
        //TODO 被删除的节点的key赋值到后继结点上
        replaced.key = t;
        //TODO 把被删除的节点的value暂存下来
        Object v = deleted.value;
        //TODO 把被删除的节点的value用后继结点的value替换掉
        deleted.value = replaced.value;
        //TODO 被删除的节点的value赋值到后继结点上
        replaced.value = v;
        //TODO 此时删除后继结点即可  因为value和key互换了
        doRemove(replaced);
    }
}