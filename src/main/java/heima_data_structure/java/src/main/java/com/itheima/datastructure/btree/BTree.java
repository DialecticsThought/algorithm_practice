package heima_data_structure.java.src.main.java.com.itheima.datastructure.btree;

import java.util.Arrays;

/**
 * TODO
 * B-树
 * 度degree指树中节点孩子数
 * 阶order 指所有节点孩子数最大值
 * B-树的特性:
 * 1,每个节点最多有m个孩子，其中m称为B-树的阶;
 * 2．除根节点和叶子节点外,其他每个节点至少有ceil(m/2)个孩子;
 * 3。若根节点不是叶子节点，则至少有两个孩子;
 * 4。所有叶子节点都在同一层;
 * 5，每个非叶子节点由n个关键字和n+1个指针组成，其中ceil(m/2)-1<=n<=m-1;6，关键字按非降序排列，即节点中的第i个关键字大于等于第i-1个关键字;
 * 7。指针P.[i]指向关键字值位于第i个关键字和第i+1个关键字之间的子树。
 * 孩子数ceil(m/2) ~ m
 * 关键字数ceil(m/2)-1 ~ m
 * |          [4]
 * |   [2]            [6,8](关键字就是6和8)
 * | [1]  [3]      [5]  [7]  [9,10]
 */
@SuppressWarnings("all")

public class BTree {

    public static class Node {
        public int[] keys; //TODO  该节点关键字
        public Node[] children; //TODO  该节点孩子  该数组索引与keys索引对应
        public int keyNumber; //TODO  该节点有效关键字数目
        public boolean leaf = true; //TODO  该节点是否是叶子节点
        public int t; //TODO  该节点最小度数 (最小孩子数)

        public Node(int t) { // t>=2
            this.t = t;
            //TODO 若根节点不是叶子节点，则至少有两个孩子;  最小度数*2 这是规定
            this.children = new Node[2 * t];
            //TODO 关键字 比孩子数-1
            this.keys = new int[2 * t - 1];
        }

        public Node(int[] keys) {
            this.keys = keys;
        }

        @Override
        public String toString() {
            return Arrays.toString(Arrays.copyOfRange(keys, 0, keyNumber));
        }

        /**
         * TODO 多路查找  下面有2层树
         *                             0  1  2  3  4
         *                            [5 10 15 20 25]
         *  [1,2,3,4] [5,7,8,9]  [11,12,13,14]  [16,17,18,19]  [21,22,23,24]  [26,27,28,29,30]
         *   0            1           2               3             4             5
         *
         * @param key
         * @return 比如我要找16 这个key 那么执行该方法 从头节点开始 5 -> 10 -> 15
         * 到20的时候 16 < 20的时候 退出循环  此时 i = 3
         * 根据3，找3这个子节点 ，继续 在子节点执行该方法
         * 比如我要找22 这个key 那么执行该方法 从头节点开始 5 -> 10 -> 15 -> 20
         * 到25的时候 22 < 25的时候 退出循环  此时 i = 4
         * 根据4，找4这个子节点 ，继续 在子节点执行该方法
         * 比如我要找30 这个key 那么执行该方法 从头节点开始 5 -> 10 -> 15 -> 20 -> 25
         * 到25的时候 30 > 25的时候 退出循环  此时 i = 5
         * 根据4，找4这个子节点 ，继续 在子节点执行该方法
         */
        public Node get(int key) {
            /**
             * TODO 要插入的key 对这个节点的每一个已存在的key一个个比较
             */
            int i = 0;
            while (i < keyNumber) {//TODO 关键字 < 被查找的关键字
                if (keys[i] == key) {//TODO  当前关键字 == 被查找的关键字
                    return this;
                }
                if (keys[i] > key) {//TODO  当前关键字 > 被查找的关键字的时候  退出
                    break;
                }
                i++;
            }
            //TODO  当前节点是叶子情况 执行到此时 keys[i]>key 或 i==keyNumber
            if (leaf) {
                return null;
            }
            // 当前节点是非叶子情况
            return children[i].get(key);
        }

        /**
         * 1.向 keys 指定索引处插入 key
         *
         * @param key
         * @param index
         */
        void insertKey(int key, int index) {
            System.arraycopy(keys, index, keys, index + 1, keyNumber - index);
            keys[index] = key;
            keyNumber++;
        }

        /**
         * 2.向 children 指定索引处插入 child
         *
         * @param child
         * @param index
         */
        void insertChild(Node child, int index) {
            System.arraycopy(children, index, children, index + 1, keyNumber - index);
            children[index] = child;
        }

        //TODO 移除指定 index 处的 key
        int removeKey(int index) {
            int t = keys[index];
            // 向左移动一位
            System.arraycopy(keys, index + 1, keys, index, --keyNumber - index);
            return t;
        }

        //TODO 移除最左边的 key
        int removeLeftmostKey() {
            return removeKey(0);
        }

        //TODO 移除最右边的 key
        int removeRightmostKey() {
            return removeKey(keyNumber - 1);
        }

        //TODO 移除指定 index 处的 child
        Node removeChild(int index) {
            Node t = children[index];
            System.arraycopy(children, index + 1, children, index, keyNumber - index);
            children[keyNumber] = null; // help GC
            return t;
        }

        //TODO 移除最左边的 child
        Node removeLeftmostChild() {
            return removeChild(0);
        }

        //TODO 移除最右边的 child
        Node removeRightmostChild() {
            return removeChild(keyNumber);
        }

        //TODO index 孩子处左边的兄弟 先找到index处的孩子 但是要找这个孩子的左兄弟
        Node childLeftSibling(int index) {
            return index > 0 ? children[index - 1] : null;
        }

        //TODO index 孩子处右边的兄弟 先找到index处的孩子 但是要找这个孩子的右兄弟
        Node childRightSibling(int index) {
            return index == keyNumber ? null : children[index + 1];
        }

        //TODO 复制当前节点的所有 key 和 child 到 target节点
        void moveToTarget(Node target) {
            int start = target.keyNumber;
            if (!leaf) {
                for (int i = 0; i <= keyNumber; i++) {
                    target.children[start + i] = children[i];
                }
            }
            for (int i = 0; i < keyNumber; i++) {
                target.keys[target.keyNumber++] = keys[i];
            }
        }
    }

    //TODO btree的根节点
    public Node root;
    //TODO 树中节点最小度数  所有节点都是统一的值
    int t;
    //TODO 最小key数目
    final int MIN_KEY_NUMBER;
    //TODO 最大key数目  超过这个数目 就需要分裂节点
    final int MAX_KEY_NUMBER;

    public BTree() {
        //TODO 最少需要2个孩子
        this(2);
    }

    public BTree(int t) {
        this.t = t;
        root = new Node(t);
        //TODO 固定操作
        MAX_KEY_NUMBER = 2 * t - 1;
        MIN_KEY_NUMBER = t - 1;
    }

    /**
     * 1. 是否存在
     *
     * @param key
     * @return
     */
    public boolean contains(int key) {
        return root.get(key) != null;
    }

    /**
     * TODO 2. 新增
     * 1.首先查找本节点中的插入位置i，如果没有空位 （key 被找到)，应该走更新的逻辑，目前什么没做
     * 2.接下来分两种情况
     *      如果节点是叶子节点，可以直接插入了
     *      如果节点是非叶子节点，需要继续在children[i]处继续递归插入
     * 3.无论哪种情况，插入完成后都可能超过节点keys 数目限制，此时应当执行节点分裂
     *
     * @param key
     */
    public void put(int key) {
        doPut(root, key, null, 0);
    }

    /**
     * TODO 递归插入 执行的是这个方法
     *
     * @param node
     * @param key
     * @param parent
     * @param index
     */
    private void doPut(Node node, int key, Node parent, int index) {
        int i = 0;
        /**
         * TODO 要插入的key 对这个节点的每一个已存在的key一个个比较
         */
        while (i < node.keyNumber) {
            if (node.keys[i] == key) {//TODO 已存在的key == 被插入的key
                return; // 更新
            }
            if (node.keys[i] > key) {// 已存在的key > 被插入的key
                break; //TODO  退出循环的时候 找到了插入位置，即为此时的 i
            }
            //TODO 继续向后找
            i++;
        }
        if (node.leaf) {//TODO 如果是叶子结点 直接插入
            node.insertKey(key, i);
        } else {//TODO 不是当前节点叶子结点的话 需要找 该节点的孩子
            //TODO 根据当前的被插入的节点索引 去第i个子节点做递归插入
            doPut(node.children[i], key, node, i);
        }
        //TODO 插入完成后都可能超过节点keys 数目限制，此时应当执行节点分裂
        // 分裂的过程中 有些节点会扩充 这时候 还会出现需要再次分裂的情况
        if (node.keyNumber == MAX_KEY_NUMBER) {
            split(node, parent, index);
        }
    }

    /**
     * TODO 分裂方法
     * 创建right节点 也就是新节点(分裂后大于当前left 节点的)，[把t以后的key和child都拷贝过去]重点☆☆☆☆☆
     * 如果当前被分裂的节点不是叶子结点 那么其孩子节点也要分裂
     * t-1处的 key插入到 parent 的 index 处,index指left 作为孩子时的索引
     * right 节点作为 parent的孩子插入到 index +1处  也就是被分裂节点的右边一个
     * <pre>
     * TODO 叶子结点的情况
     *  eg: t=2
     *              [2]
     *           ↙          ↘
     *       [1]            [3,4,5]
     *  第1步
     *              [2]
     *           ↙          ↘
     *       [1]            [3,4]            [5]（单独出来一个节点）
     *  第2步
     *              [2,4]
     *           ↙          ↘
     *       [1]            [3]             [5]（单独出来一个节点）
     *  第3步
     *                [2,4]
     *           ↙       ↓         ↘
     *       [1]       [3]        [5]
     *  [2,4]这个节点 的孩子0是1，孩子1是3，孩子2是5
     *  eg: t=3
     *              [3]
     *           ↙          ↘
     *       [1,2]          [4,5,6,7,8]
     *  第1步
     *             [3]
     *        ↙          ↘
     *      [1,2]       [4,5,6]         [7,8]（单独出一个节点,因为key=3 从索引3开始的所有都要抽离）
     *  第2步
     *            [3,6](6是原始分裂的节点的下标是2的位置 ,因为原始被分裂的节点下标是1，所以上移到父节点的下标为1的位置， 下标为0是原来就有的3)
     *        ↙        ↘
     *      [1,2]      [4,5]            [7,8]（单独出一个节点）
     *  第3步
     *              [3,6]
     *          ↙      ↓     ↘
     *      [1,2]    [4,5]    [7,8]
     *  [3,6]这个节点 的孩子0是1,2 ,孩子1是4,5，孩子2是7,8
     * </pre>
     * TODO
     *   如果被分裂的节点是叶子结点 那么 新创建的节点也是叶子结点
     *   如果被分裂的节点是非叶子结点 那么 新创建的节点也是非叶子结点
     * TODO
     *  创建right 节点(分裂后大于当前left 节点的)，把t以后的key和child都拷贝过去
     *  t-1处的key插入到 parent 的 index处，index 指 left 作为孩子时的索刳
     *  right节点作为parent的孩子插入到 index+1处
     *  <pre>
     *  非叶子节点 t=2
     *                 [4]
     *         ↙                 ↘
     *      [2]                  [6,8,10](需要被分裂)
     *    ↙     ↘               ↙  ↓  ↓   ↘
     *  [1]      [3]          [5] [7] [9] [11]
     *  <pre>
     *  第1步
     *                  [4]
     *          ↙                 ↘
     *      [2]                    [6,8]             10(被单独出来成为一个节点)
     *    ↙     ↘                ↙  ↓  ↓
     *  [1]      [3]            [5] [7] [9] [11]
     *  第2步
     *                           [4]
     *         ↙                   ↓             ↘
     *      [2]                   [6,8]           [10](被单独出来成为一个节点)
     *    ↙     ↘                ↙  ↓            ↙  ↓
     *  [1]      [3]            [5] [7]         [9] [11](成为10这个节点的孩子)
     *  第3步
     *                          [4,8](t-1处的 key插入到 parent 的 index 处)
     *          ↙                   ↓             ↘
     *      [2]                   [6]             [10](被单独出来成为一个节点)
     *    ↙     ↘               ↙     ↘         ↙    ↘
     *  [1]     [3]            [5]   [7]       [9] [11](成为10这个节点的孩子)
     *  第3步
     *                   [4,8]
     *       ↙              ↓            ↘
     *      [2]            [10]          [6]
     *    ↙     ↘        ↙     ↘        ↙    ↘
     *  [1]     [3]    [9]   [11]     [5]   [7]
     *  </pre>
     * TODO 分裂根节点
     *  如果parent == null表示要分裂的是根节点，此时需要创建新根，原来的根节点作为新根的О孩子
     *  否则
     *      创建 right节点（分裂后大于当前left节点的)，把t以后的 key和child都拷贝过去
     *      t-1处的 key插入到 parent 的 index 处，index指left作为孩子时的索引
     *      right 节点作为parent的孩子插入到 index+1处
     *   <pre>
     *   t=3  当前只有一个
     *       [1,2,3,4,5]
     *  第1步  创建出新的根节点 作为left  新的节点 作为right
     *       [null]
     *         ↓
     *      [1,2,3,4,5]
     *         ↓
     *       [null]
     *  第2步  有3个节点
     *       [3]
     *        ↓
     *      [1,2]
     *        ↓
     *      [4,5]
     *  第3步
     *        [3]
     *     ↙        ↘
     *   [1,2]     [4,5]
     *  </pre>
     *
     * @param left   要分裂的节点
     * @param parent 分裂节点的父节点
     * @param index  分裂节点是第几个孩子
     */
    public void split(Node left, Node parent, int index) {
        //TODO 分裂的是根节点
        if (parent == null) {
            //TODO 所有节点的t都是一个
            Node newRoot = new Node(t);
            newRoot.leaf = false;//新的根节点 创建出来 肯定不是叶子节点
            newRoot.insertChild(left, 0); // TODO keyNumber 的维护（新节点没有孩子，应该不会有问题）
            this.root = newRoot;
            parent = newRoot;
        }
        //TODO 1. 创建 right 节点，把 left 中 t 之后的 key 和 child 移动过去
        Node right = new Node(t);
        right.leaf = left.leaf;
        // left.keys 旧数组 从t开始拷贝 到right.keys这个新数组  从新数组的0位置开始 拷贝旧数组的长度t-1
        System.arraycopy(left.keys, t, right.keys, 0, t - 1);
        //TODO 分裂节点是非叶子的情况
        if (!left.leaf) {
            // left.keys 旧数组 从t开始拷贝 到right.keys这个新数组  从新数组的0位置开始 拷贝旧数组的长度t(因为拷贝孩子比父节点多一个)
            System.arraycopy(left.children, t, right.children, 0, t);
            for (int i = t; i <= left.keyNumber; i++) {
                left.children[i] = null;
            }
        }
        right.keyNumber = t - 1;
        left.keyNumber = t - 1;
        //TODO 2. 中间的 key （t-1 处）插入到父节点
        int mid = left.keys[t - 1];//TODO 得到中间的key 需要插入到父节点
        parent.insertKey(mid, index);
        //TODO 3. right 节点作为父节点的孩子
        parent.insertChild(right, index + 1);
    }

    // 3. 删除
    public void remove(int key) {
        doRemove(null, root, 0, key);
    }

    /**
     * TODO 删除某个节点的key 不是删除节点
     * case 1:当前节点是叶子节点，没找到被删除的key 就直接返回
     * case 2:当前节点是叶子节点，找到了被删除的key 就直接删除
     * case 3:当前节点是非叶子节点，没找到，去孩子
     * case 4:当前节点是非叶子节点，找到了，去找后继key 替换被删除的key 然后把后继key删去
     * case 5:删除后key 数目<下限（不平衡)  上限达到了需要分裂  下限达到了 也要作相应操作
     * case 6:根节点
     * TODO 有一个b树根节点
     * [1,2,3,5]
     * 这个节点有5个子节点 ，因为该节点有4个key关键字
     * 现在要删除4这个key，那么 从根节点的key[]的0号索引开始遍历 1->2->3 直到5 ,  4 < 5
     * 查看 5的索引 是3 那么去 [1,2,3,5]的第三个子节点继续找
     *
     * @param parent
     * @param node
     * @param index
     * @param key
     */
    private void doRemove(Node parent, Node node, int index, int key) {
        int i = 0;
        while (i < node.keyNumber) {
            if (node.keys[i] >= key) {
                break;//TODO 退出循环的时候 当前节点的第i个子节点 就是需要遍历的节点 / 待删除的key的索引
            }
            i++;
        }
        /**
         *TODO
         * i 找到：代表待删除 key 的索引
         * i 没找到： 代表到第i个孩子继续查找
         */
        if (node.leaf) {
            if (!found(node, key, i)) { //TODO case1 当前节点是叶子节点  没找到
                return; //TODO 直接返回
            } else { //TODO case2 当前节点是叶子节点  找到了
                node.removeKey(i);//TODO 直接删去
            }
        } else {
            if (!found(node, key, i)) { //TODO case3 当前节点是非叶子节点 没找到
                //TODO 到 当前节点的第i个子节点继续查找  => 递归
                doRemove(node, node.children[i], i, key);
            } else { //TODO case4  当前节点是非叶子节点 找到了
                /**
                 *TODO  这是一个3层的树
                 *         [9]
                 *       ↙    ↘
                 *  [8]       [12,15] (是[9]的右侧孩子)
                 *            ↙    ↘
                 *        [10,11]  [13,14]
                 * 要删除[9]这个节点的话  需要找到后继节点10
                 * 把9用10替换掉
                 * 把原始10的key删去
                 *      [10]
                 *     ↙    ↘
                 *  [8]     [12,15](是[10]的右侧孩子)
                 *          ↙    ↘
                 *      [11]   [13,14]
                 *  从[9]这个节点的key=9的索引(0)+1的的孩子  以1号孩子 为根节点 一直找最左侧的分支
                 *  直到到达了最后一层的最左侧节点 把该节点的索引0的key拿出来
                 *  这里是[10,11] 只有一层 直接就是[12,15]的最左侧的子节点
                 */
                //TODO 1. 找到后继 key
                Node s = node.children[i + 1];
                while (!s.leaf) {
                    s = s.children[0];
                }
                int skey = s.keys[0];
                //TODO 2. 替换待删除 key
                node.keys[i] = skey;
                //TODO 3. 删除后继 key
                doRemove(node, node.children[i + 1], i + 1, skey);
            }
        }
        if (node.keyNumber < MIN_KEY_NUMBER) {
            //TODO 调整平衡 case 5 case 6
            balance(parent, node, index);
        }
    }

    /**
     * TODO
     * case 5-1
     * <pre>
     *             [4]
     *       ↙          ↘
     *    [1,2,3]       [5,6]
     * 把5删去 此时少于最小key的数量  去左侧兄弟接一个key过来
     * 需要右旋
     * 先把4旋转下来
     *           [4]
     *      ↙          ↘
     *    [1,2,3]       [4,6]
     * 再把4的左侧兄弟3旋转上去
     *           [3]
     *      ↙          ↘
     *    [1,2]       [4,6]
     * </pre>
     * case 5-2
     * <pre>
     *            [3]
     *       ↙          ↘
     *    [1,2]       [4,5,6]
     * 把1删去 此时少于最小key的数量 去右侧兄弟接一个key过来
     * 需要左旋
     * 先把3旋转下来
     *            [3]
     *       ↙          ↘
     *    [2,3]       [4,5,6]
     * 再把3的右侧兄弟4旋转上去
     *            [4]
     *       ↙          ↘
     *    [2,3]       [5,6]
     * </pre>
     * case 5-3
     * <pre>
     *  eg:有一个t=3的树
     *          [3]
     *       ↙          ↘
     *    [1,2]       [4,5]
     * 把4删去 此时少于最小key的数量   此时不能去左和右侧兄弟接一个key过来
     *          [3]
     *      ↙          ↘
     *    [1,2]       [4]
     * 解决方法： 合并 减少节点数目   这里统一是向左合并
     * 此时  先2层树  2个节点
     *          [3]
     *    ↙          ↘
     *   [1,2]         [5]被单独出来
     * 此时把 [3] [1,2]合并
     * 就剩下 []的根节点 和 [1,2,3] 和  单独出来的[5]
     * 最后整个合并 剩下[]的根节点 和 [1,2,3,5]
     *     []
     *     ↓
     *  [1,2,3,5]
     * 最后把[1,2,3,5] 替换掉原始的根
     * </pre>
     * <pre>
     * eg:有一个t=3的树
     *          [3]
     *       ↙        ↘
     *    [1,2]       [4,5]
     * 把2删去 此时少于最小key的数量   此时不能去左和右侧兄弟接一个key过来
     * 此时  先2层树  2个节点
     *    [3]
     *     ↓
     *    [1]         [4,5]被单独出来
     * 此时把 [3] [1]合并
     * 就剩下 []的根节点 和 [1,3] 和  单独出来的[4,5]
     * 最后整个合并 剩下[]的根节点 和 [1,3,4,5]
     * 最后把[1,3,4,5] 替换掉原始的根
     * </pre>
     * @param parent
     * @param x
     * @param i
     */
    private void balance(Node parent, Node x, int i) {
        /**
         *TODO  case 6 根节点
         * <pre>
         * eg: t = 3
         *       [4]
         *     ↙      ↘
         *   [1,2]    [5,7]
         * 删去2
         *       [4]
         *     ↙      ↘
         *   [1]    [5,7]
         * 合并
         *       [4]
         *      ↙
         *   [1]                 [5,7]被单独出来
         *       []
         *      ↙
         *   [1,4]               [5,7]被单独出来
         *       []
         *       ↓
         *   [1,4,5,7]
         *  此时根节点的key数=0 没有用了
         *  用唯一的孩子 变成新的根节点
         * </pre>
         */
        if (x == root) {
            if (root.keyNumber == 0 && root.children[0] != null) {
                root = root.children[0];
            }
            return;
        }
        // 找到该节点左侧兄弟
        Node left = parent.childLeftSibling(i);
        // 找到该节点的右侧兄弟
        Node right = parent.childRightSibling(i);
        /**
         *TODO case 5-1 左边富裕，右旋
         *                   [4,8,11]
         *      ↙             ↙      ↘          ↘
         *  [1,2]        [5,6,7]     [9,10]      [12,13]
         *        ↙      ↓    ↓     ↘
         *      [4.5] [5.5] [6.5] [7.5]   这4个表示[5,6,7]的节点
         * 把9删去 开始右旋
         *                   [4,8,11]
         *      ↙             ↙      ↘          ↘
         *  [1,2]        [5,6,7]     [8,10]      [12,13]
         *         ↙     ↓    ↓     ↘
         *       [4.5] [5.5] [6.5] [7.5]    这4个表示[5,6,7]的节点
         * 父节点的8旋转下来 保证升序   8是10最接近的前驱 并且 8在[4,8,11]的索引是被调整的节点[10]的10的索引+1
         *                   [4,8,11]
         *       ↙             ↙      ↘          ↘
         *  [1,2]        [5,6,7]     [8,10]      [12,13]
         *       ↙     ↓    ↓     ↘
         *     [4.5] [5.5] [6.5] [7.5]   这4个表示[5,6,7]的节点
         * 把 左侧兄弟的7旋转上去  必须是被删除的key所在节点的左兄弟节点的最大节点
         *                   [4,7,11]
         *      ↙             ↙      ↘          ↘
         *  [1,2]        [5,6,7]     [8,10]      [12,13]
         *        ↙     ↓    ↓     ↘
         *     [4.5] [5.5] [6.5] [7.5]   这4个表示[5,6,7]的节点
         * 此时[5,6]的子节点 需要把最右侧节点 也就是最大的节点 移动到 被调整的节点[8,10]的最左侧节点
         *                   [4,7,11]
         *      ↙             ↙      ↘          ↘
         *  [1,2]        [5,6,7]        [8,10]   [12,13]
         *             ↙   ↓  ↘         ↙
         *        [4.5] [5.5] [6.5]    [7.5]
         */
        if (left != null && left.keyNumber > MIN_KEY_NUMBER) {// left.keyNumber > MIN_KEY_NUMBER 才能够借用
            //TODO a) 父节点中前驱key旋转下来
            x.insertKey(parent.keys[i - 1], 0);
            if (!left.leaf) {
                //TODO  b) left中最大的孩子换爹
                x.insertChild(left.removeRightmostChild(), 0);
            }
            //TODO  c) left中最大的key旋转上去
            parent.keys[i - 1] = left.removeRightmostKey();
            return;
        }
        /**
         *TODO case 5-2 右边富裕，左旋
         *                   [4,7,11]
         *    ↙             ↙       ↘          ↘
         *  [1,2]        [5,6]     [8,9,10]      [12,13]
         *                     ↙     ↓  ↓    ↘
         *                  [7.5] [8.5] [9.5] [10.5]  这4个表示[8,9,10] 的节点
         * 把5删去 开始左旋
         *                   [4,7,11]
         *     ↙             ↙       ↘          ↘
         *  [1,2]        [6]      [8,9,10]       [12,13]
         *                    ↙     ↓  ↓    ↘
         *                  [7.5] [8.5] [9.5] [10.5]  这4个表示[8,9,10] 的节点
         * 父节点的7旋转下来 保证升序   7是6最接近的后继 并且 7在[4,7,11]的索引是被调整的节点[6]的6的索引+1
         *                   [4,7,11]
         *     ↙             ↙       ↘          ↘
         *  [1,2]        [6,7]       [8,9,10]    [12,13]
         *                       ↙     ↓  ↓    ↘
         *                    [7.5] [8.5] [9.5] [10.5]  这4个表示[8,9,10] 的节点
         * 把 右侧兄弟的8旋转上去  必须是被删除的key所在节点的右兄弟节点的最小节点
         *                   [4,8,11]
         *      ↙             ↙       ↘          ↘
         *  [1,2]        [6,7]        [9,10]     [12,13]
         *                        ↙     ↓  ↓    ↘
         *                     [7.5] [8.5] [9.5] [10.5]  这4个表示[8,9,10] 的节点
         * 此时[5,6]的子节点 需要把最右侧节点 也就是最大的节点 移动到 被调整的节点[8,10]的最左侧节点
         *                   [4,8,11]
         *     ↙             ↙       ↘          ↘
         *  [1,2]        [6,7]       [9,10]     [12,13]
         *                    ↘      ↙ ↓   ↘
         *                  [7.5] [8.5][9.5][10.5]  这3个表示[8,9,10] 的节点
         */
        if (right != null && right.keyNumber > MIN_KEY_NUMBER) {
            //TODO  a) 父节点中后继key旋转下来
            x.insertKey(parent.keys[i], x.keyNumber);
            //TODO  b) right中最小的孩子换爹
            if (!right.leaf) {
                x.insertChild(right.removeLeftmostChild(), x.keyNumber); // TODO 学员指出多加了1
            }
            //TODO  c) right中最小的key旋转上去
            parent.keys[i] = right.removeLeftmostKey();
            return;
        }
        /**
         *TODO
         * case 5-3 两边都不够借，向左合并
         * eg1:
         *           [4,8]
         *     ↙     ↓       ↘
         *   [1,2]  [5,7]  [9,10]
         *  删除2的话 只剩下1 但是1 没有左兄弟  只能把右节点的key和父节点的某一个key 合并到被调整的节点
         * eg2:
         *            [4,8]
         *      ↙      ↓       ↘
         *   [1,2]    [5,7]     [9,10]
         *  删除5这个key
         *            [4,8]
         *      ↙      ↓       ↘
         *   [1,2]    [7]     [9,10]
         * 先让 被调整节点[7] 单独隔离开来
         *           [4,8]
         *       ↙          ↘
         *   [1,2]        [9,10]             [7]被单独出来
         * 找到被调整节点的左兄弟也就是[1,2]
         * 找到被调整节点的父节点的某一个key 先合并到 被调整节点的左兄弟的最右侧
         * 被调整节点是父节点的第i个孩子(i从0开始)  i-1就是父节点需要合并到其他节点的key的索引
         * i=1 i-1=0 也就是4
         *           [8]
         *      ↙          ↘
         *   [1,2,4]        [9,10]             [7]被单独出来
         * 最后就把被调整节点的所有key合并
         *           [8]
         *     ↙          ↘
         *   [1,2,4,7]     [9,10]
         * eg1:
         *       [4,8]
         *    ↙    ↓     ↘
         *   [1]  [5,7]  [9,10]
         * 删除1 但是没有左兄弟了
         * 只能是找右兄弟了  把他删除掉
         *       [4,8]
         *     ↙       ↘
         *   [1]      [9,10]             [5,7] 被单独出来
         * 找到被调整节点的左兄弟也就是[4,8]
         * 找到被调整节点的父节点的某一个key 先合并到 被调整节点自己的最右侧
         * 被调整节点是父节点的第i个孩子(i从0开始)  i就是父节点需要合并到其他节点的key的索引
         *           [8]
         *     ↙            ↘
         *   [1,4]        [9,10]            [5,7] 被单独出来
         * 最后就把被调整节点的所有key合并
         *           [8]
         *    ↙            ↘
         *  [1,4,5,7]   [9,10]
         */
        if (left != null) {
            /**
             * 向左兄弟合并
             */
            parent.removeChild(i);
            left.insertKey(parent.removeKey(i - 1), left.keyNumber);
            x.moveToTarget(left);
        } else {
            /**
             * 向自己合并
             */
            parent.removeChild(i + 1);
            x.insertKey(parent.removeKey(i), x.keyNumber);
            right.moveToTarget(x);
        }
    }

    private boolean found(Node node, int key, int i) {
        return i < node.keyNumber && node.keys[i] == key;
    }

    public void travel() {
        doTravel(root);
    }

    public void doTravel(Node node) {
        if (node == null) {
            return;
        }
        int i = 0;
        for (; i < node.keyNumber; i++) {
            doTravel(node.children[i]);
            System.out.println(node.keys[i]);
        }
        doTravel(node.children[i]);
    }
}
