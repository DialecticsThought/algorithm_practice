package heima_data_structure.java.src.main.java.com.itheima.datastructure.btree;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 对于B+树而言，节点分成 leaf节点，internal内部节点，root节点
 *
 * 每个 leaf node 都应该至少存在p/2(向上取整)的元素
 *
 * 每个键值必须存在于叶子节点层，因为所有的数据指针都位于叶子节点层
 *
 * 每个键值出现在内部节点 和 在其左侧指针的子树叶子节点层的最右侧节点相同
 * eg:
 *       [ 5 |   ]
 *      ↙     ↘
 * [ 1 | 5 ]  [ 8 |   ]
 * 5这个键值，其子树的叶子结点层 的最右侧是5
 * 为什么 [ 5 |   ] 节点中，没有8 也就是 [ 5 | 8 ] ?
 * 因为 对于每一个键，pointer指针2侧都要有 ，对于5而言，2侧都有指针，
 * 但是如果是[ 5 | 8 ] 的话  ，对于8而言，只有一侧拥有指针，另一侧没有节点，所以没有指针
 *
 * 根节点只有树的节点指针
 *
 * 叶子节点只有数据指针
 *
 * 在分裂时，第二个节点是第一个节点的一半（向上取整）
 *
 * eg：
 * 设 p = 3  and  pLeaf = 2
 * 叶子节点最多能放2个键值，但现在要插入第3个键值，这个节点就需要分裂
 * 分裂过程
 * 假设有一个满的节点（比如现在要插入的叶子节点已经有了 pLeaf=2，但需要插入第3个键值），我们称这个节点为“第一个节点”
 * 在分裂时，B+树将该节点中的键值和指针分成两部分，形成两个节点。这里的“第二个节点”就是通过分裂产生的新节点
 * “一半”的意思是，分裂时，原节点中的键值和指针被尽量平均分配给两个节点。
 *      例如，如果原节点中有3个键值，那么分裂后，通常第一个节点保留1个键值，第二个节点保留2个键值
 *
 *  设 p = 3  and  pLeaf = 2
 *  p=3: 表示内部节点（非叶子节点）最多可以有3个指针（即最多可以有3个子节点）。
 *      这意味着该节点可以包含最多2个键值（分隔3个子节点的键值）。
 *  pLeaf=2: 表示叶子节点最多可以包含2个键值。叶子节点存储实际的数据值或数据指针，而在B+树中，所有数据都保存在叶子节点中
 *  简而言之：
 *      内部节点的最大分支度为3，即每个内部节点最多有3个子节点。
 *      叶子节点最多可以存储2个键值
 * </pre>
 * <pre>
 *  标准
 *  每一个key的左侧指针指向的节点的所有key < 当前key
 *  每一个key的右侧指针指向的节点的所有key >= 当前key
 *
 *  B+分裂规则:
 *  1. 叶子节点的分裂规则
 *  B+树的叶子节点存储的是实际数据，且这些数据是按顺序排列的。
 *  当插入一个新的键值对时，如果叶子节点已经满了（节点的度数已达到最大），就需要进行分裂。
 *  规则：
 *  确定中间值：如果叶子节点已满，则找到其中的中间值（通常是中间位置的值），把叶子节点分为左右两部分。
 *  中间值上升到父节点：将中间值上升到父节点，使得父节点作为这个叶子节点分裂的“路标”，用来区分左右两个新叶子节点的范围。
 *  叶子节点指针的调整：分裂后的两个叶子节点会通过链表连接，保持所有叶子节点之间的顺序（这是B+树的特点，所有叶子节点通过链表相连）。
 *  例子：
 *  假设度数为3的B+树叶子节点存储 [3, 5, 8]，插入9后节点变满：
 *  找到中间值8，分裂成 [3, 5] 和 [9] 两个叶子节点。
 *  中间值8上升到父节点，父节点指示范围。
 *  分裂后的叶子节点通过链表连接，保持顺序。
 *
 *  2. 内部节点的分裂规则
 *  内部节点（非叶子节点）存储的是索引值（也就是各子节点的“分界值”）。
 *  内部节点的分裂规则类似于叶子节点，但内部节点存储的值仅用于索引，而不包含实际的数据。
 *  因此，分裂的操作更多是调整树的结构。
 *  规则：
 *  找到中间值：如果内部节点已满，则找到中间的索引值，将该节点分裂为两个节点。
 *  中间值上升：将中间的索引值上升到父节点。
 *  不保留中间值：与叶子节点不同，内部节点的分裂不会保留中间值。该中间值一旦上升到父节点，便充当新的“路标”，不再在原子节点中存在。
 *  例子：
 *  假设内部节点存储的索引值是 [5, 10, 15]，插入12后需要分裂：
 *  找到中间值10，分裂成两个内部节点：左节点 [5] 和右节点 [12, 15]。
 *  中间值10上升到父节点。
 *
 *  3. 根节点的分裂规则
 *  当根节点也变满时，B+树的高度会增加，因为根节点需要分裂，并且会生成新的根节点。
 *  根节点的分裂规则与内部节点类似，但它的分裂会导致树的层数增加。
 *  规则：
 *  找到中间值：同样找到中间值，分裂根节点。
 *  中间值成为新根节点：不同于内部节点的分裂，根节点分裂后，中间值会上升成为新的根节点，导致树的层数增加。
 *  左右子树形成：根节点分裂后会形成两个子节点，这两个子节点的范围分别由新根节点的中间值分界。
 *  例子：
 *  假设根节点是 [5, 10, 15]，插入12后需要分裂：
 *  找到中间值10，分裂成两个子节点：左节点 [5] 和右节点 [12, 15]。
 *  中间值10上升成为新的根节点，树的层数增加。
 *
 *  1. 奇数节点数量的分裂
 *  如果节点中的键值数量是奇数，那么当这个节点被分裂时，通常会选择中间位置的键值进行提升。
 *  分裂后，中间键值不会留在原来的两个子节点中，而是提升到父节点，剩余的键值平均分配到两个子节点中
 *  假设一个叶子节点有 3 个键值 [1, 5, 8]，现在需要分裂：
 *  中间键值是 5
 *  2. 偶数节点数量的分裂
 *  如果节点中的键值数量是偶数，那么中间没有一个明确的“中间键值”，
 *  在这种情况下，分裂时可以选择靠近中间的一个键值进行提升。通常的策略是：
 *  提升靠中间偏右的键值（即分裂后的右半部分的第一个键值）。
 *  分裂后，提升的键值不会留在原来的两个子节点中，而是提升到父节点
 *  一个叶子节点有 4 个键值 [1, 3, 5, 8]，现在需要分裂：
 *  在这个例子中，5 作为提升的中间键值
 *
 *  B+插入规则:
 *  1. 叶子节点的插入操作
 *  叶子节点存储实际的数据值（键值对），当我们进行插入操作时，首先会查找到正确的叶子节点。
 *  如果叶子节点没有满，插入操作就相对简单；如果叶子节点已满，就需要分裂。
 *  步骤：
 *  查找插入位置：根据B+树的查找特性，找到应该插入的叶子节点。
 *  如果树的层数大于1，首先从根节点出发，逐层根据索引值找到相应的叶子节点。
 *  插入元素：将新元素插入到叶子节点的正确位置，保证叶子节点内部数据的有序性。
 *  处理叶子节点满的情况：
 *  如果插入后叶子节点没有满，则插入操作完成。
 *  如果叶子节点满了（即包含的元素个数等于最大度数），就需要进行分裂：
 *  找到叶子节点的中间值。
 *  将叶子节点分裂为两个节点，左节点包含比中间值小的元素，右节点包含比中间值大的元素。
 *  将中间值上升到父节点，作为新分界点。
 *  保持叶子节点的链表指针更新，使叶子节点保持按顺序相连。
 *  例子：
 *  假设度数为3的B+树中，叶子节点存储 [3, 5]，插入值4：
 *  插入值4后，节点变为 [3, 4, 5]，没有满，操作完成。
 *  如果插入值6：
 *  插入值6后，节点变为 [3, 4, 5, 6]，已满。
 *  进行分裂：中间值为4，节点分裂为 [3, 4] 和 [6]。
 *  将中间值4上升到父节点。
 *  2. 内部节点的插入操作
 *  内部节点（非叶子节点）只存储索引值，用来指引搜索的方向。
 *  当内部节点满时，也需要进行分裂。内
 *  部节点的插入操作与叶子节点的操作类似，但它只存储索引值，不包含具体的数据，因此处理稍有不同。
 *  步骤：
 *  查找插入位置：从根节点开始，根据索引值找到应该插入的叶子节点，逐层遍历内部节点。
 *  插入中间值到父节点：如果叶子节点分裂了，中间值需要上升到父节点，此时父节点相当于接收一个新的索引值。
 *  处理内部节点满的情况：
 *  如果父节点没有满，则将中间值插入父节点即可。
 *  如果父节点已满（存储的索引值达到了最大度数），则需要对父节点进行分裂：
 *  找到中间值，分裂父节点为左右两个子节点。
 *  中间值继续上升到上层节点，形成新的分界点。
 *  例子：
 *  假设内部节点 [10, 20]，并且我们在子节点中插入了一个新元素，导致子节点分裂，将中间值15上升到内部节点。
 *  内部节点变为 [10, 15, 20]。
 *  如果内部节点没有满，操作完成。
 *  如果满了，需要分裂内部节点为 [10] 和 [20]，中间值15上升到父节点。
 *  3. 根节点的插入操作
 *  当根节点执行插入操作时，如果根节点没有满，操作较为简单；
 *  如果根节点满了，插入操作会导致树的高度增加，并生成新的根节点。
 *  这是唯一一种导致树高度增加的插入情况。
 *  步骤：
 *  插入元素：根节点作为特殊的内部节点，插入索引值或接收子节点分裂上升的中间值。
 *  处理根节点满的情况：
 *  如果根节点没有满，插入索引值后操作完成。
 *  如果根节点已满（即包含的索引值达到了最大度数），则根节点需要分裂：
 *  找到根节点的中间值，将其上升到新根节点，分裂根节点为两个子节点。
 *  形成新的根节点，并使树的层数增加。
 *  新根节点只有一个值，它有两个子节点，分别是分裂后的左右节点。
 *  例子：
 *  假设根节点存储 [5, 10, 15]，插入一个元素导致根节点满了：
 *  根节点分裂为两个子节点 [5] 和 [15]，中间值10上升成为新的根节点。
 *  新的根节点 [10] 有两个子节点 [5] 和 [15]，树的层数增加。
 *  4. 加入操作的总结
 *  叶子节点的插入：
 *  查找到合适的叶子节点，将新元素插入保持顺序。
 *  叶子节点满时进行分裂，中间值上升到父节点。
 *  内部节点的插入：
 *  内部节点负责存储索引值，如果下层节点分裂，中间值上升到父节点。
 *  内部节点满时进行分裂，中间值上升到父节点，不在原节点保留。
 *  根节点的插入：
 *  根节点插入时，根节点满了会导致根节点分裂，树的高度增加。
 *  新根节点上升，中间值成为新的根，树的层数增加。
 *  TODO
 *  5. 插入操作整体流程:
 *   插入操作的整体流程可以归纳为以下几点：
 *   查找插入位置：从根节点开始，依次找到合适的子节点或叶子节点。
 *   插入新元素：如果目标节点没有满，直接插入；否则进行分裂。
 *   处理节点分裂：分裂后的中间值上升到父节点，父节点接收中间值。
 *   递归处理父节点的分裂：如果父节点也满了，继续分裂，直到根节点为止。
 *   根节点分裂时树的高度增加：如果根节点满了，分裂根节点，增加树的高度
 *
 *
 *  eg：
 *  依次 插入 8 5 1 7 3 12 9 6
 *  1.插入8
 *   [8]
 *  2.插入5
 *   [ 5 | 8 ]
 *  3.插入1
 *  变成了[ 1 | 5 | 8 ]
 *  出现溢出，需要分裂节点[ 1 | 5 | 8 ]
 *  利用到 中间key向上
 *  该中间key的左侧指针指向的节点的所有key < 中间key
 *  该中间key的右侧指针指向的节点的所有key >= 当前key
 *  分裂变成
 *  [ 1 |   ] [ 5 | 8 ]
 *  最终
 *        [ 5 |   ]
 *       ↙     ↘
 *  [ 1 |   ] -> [ 5 | 8 ]
 *  4.插入7
 *  变成了
 *        [ 5 |   ]
 *       ↙     ↘
 *  [ 1 |   ] -> [ 5 | 7 | 8 ]
 *  出现溢出，需要分裂节点[ 5 | 7 | 8 ]
 *  利用到 中间key向上
 *  该中间key的左侧指针指向的节点的所有key < 中间key
 *  该中间key的右侧指针指向的节点的所有key >= 当前key
 *  分裂变成
 *  [ 5 |   ] [ 7 | 8 ]
 *  最终
 *              [ 5 | 7 ]
 *        ↙         ↓       ↘
 *  [ 1 |   ] -> [ 5 |   ] -> [ 7 | 8 ]
 *  5.插入3
 *              [ 5 | 7 ]
 *        ↙         ↓       ↘
 *  [ 1 | 3 ] -> [ 5 |   ] -> [ 7 | 8 ]
 *  6.插入12
 *              [ 5 | 7 ]
 *        ↙         ↓       ↘
 *  [ 1 | 3 ] -> [ 5 |   ] -> [ 7 | 8 | 12 ]
 *  出现溢出，需要分裂节点[ 7 | 8 | 12 ]
 *  利用到 中间key向上
 *  该中间key的左侧指针指向的节点的所有key < 中间key
 *  该中间key的右侧指针指向的节点的所有key >= 当前key
 *  分裂变成
 *  [ 7 |  ] [8 | 12 ]
 *  变成了
 *              [ 5 | 7 | 8 ]
 *        ↙         ↓       ↘
 *  [ 1 | 3 ] -> [ 5 |   ] -> [ 7 |  ] -> [8 | 12 ]
 *  出现溢出，需要分裂节点[ 5 | 7 | 8 ]
 *  利用到 中间key向上
 *  该中间key的左侧指针指向的节点的所有key < 中间key
 *  该中间key的右侧指针指向的节点的所有key >= 当前key
 *  分裂变成
 *  [ 5 |   ] [ 7 | 8 ]
 *  最终
 *                       [ 7 |   ]
 *                    ↙           ↘
 *              [ 5 |   ]         [ 8 |  ]
 *           ↙      ↓           ↙      ↘
 *  [ 1 | 3 ] -> [ 5 |   ] -> [ 7 |  ] -> [8 | 12 ]
 *  7.插入9
 *                       [ 7 |   ]
 *                    ↙           ↘
 *              [ 5 |   ]         [ 8 |  ]
 *           ↙      ↓           ↙      ↘
 *  [ 1 | 3 ] -> [ 5 |   ] -> [ 7 |  ] -> [8 | 9 | 12 ]
 *  出现溢出，需要分裂节点[8 | 9 | 12 ]
 *  利用到 中间key向上
 *  该中间key的左侧指针指向的节点的所有key < 中间key
 *  该中间key的右侧指针指向的节点的所有key >= 当前key
 *  分裂变成
 *  [ 8 |  ] [9 | 12 ]
 *  最终
 *                       [ 7 |   ]
 *                    ↙           ↘
 *              [ 5 |   ]               [ 8 | 9 ]
 *           ↙      ↓             ↙         ↓         ↘
 *  [ 1 | 3 ] -> [ 5 |   ] -> [ 7 |  ] ->  [ 8 |  ] -> [9 | 12 ]
 *  8.插入6
 *                       [ 7 |   ]
 *                    ↙           ↘
 *              [ 5 |   ]               [ 8 | 9 ]
 *           ↙      ↓             ↙         ↓         ↘
 *  [ 1 | 3 ] -> [ 5 | 6 ] -> [ 7 |  ] ->  [ 8 |  ] -> [9 | 12 ]
 * </pre>
 * @Description
 * @Author veritas
 * @Data 2024/9/14 15:31
 */
public class BPlusTree2 {
    private int m;             // B+树的阶数（每个节点的最大子节点数）
    private BPlusTreeNode root; // 根节点

    // B+树的节点类
    class BPlusTreeNode {
        boolean isLeaf;               // 是否为叶子节点
        List<Integer> keys;           // 关键字列表
        List<String> values;          // 值列表，仅叶子节点使用
        List<BPlusTreeNode> children; // 子节点列表（仅内部节点使用）
        BPlusTreeNode next;           // 叶子节点的指针，用于链接叶子节点

        // 构造函数
        public BPlusTreeNode(boolean isLeaf) {
            this.isLeaf = isLeaf;            // 设置节点类型
            this.keys = new ArrayList<>();   // 初始化关键字列表
            if (isLeaf) {
                this.values = new ArrayList<>(); // 初始化值列表
            } else {
                this.children = new ArrayList<>(); // 初始化子节点列表
            }
        }
    }

    // 构造函数
    public BPlusTree2(int m) {
        if (m < 3) {
            throw new IllegalArgumentException("B+树的阶数必须大于等于3"); // 阶数必须至少为3
        }
        this.m = m;                       // 设置阶数
        this.root = new BPlusTreeNode(true); // 初始化根节点为叶子节点
    }

    // 搜索操作
    public String search(int key) {
        BPlusTreeNode node = root;       // 从根节点开始
        while (!node.isLeaf) {           // 如果不是叶子节点
            int i = 0;
            while (i < node.keys.size() && key >= node.keys.get(i)) {
                i++;                     // 找到子节点索引
            }
            node = node.children.get(i); // 进入子节点
        }
        int i = 0;
        while (i < node.keys.size()) {
            if (key == node.keys.get(i)) {
                return node.values.get(i); // 找到关键字，返回对应的值
            }
            i++;
        }
        return null; // 未找到关键字
    }

    // 插入操作
    public void insert(int key, String value) {
        BPlusTreeNode node = root;       // 从根节点开始
        if (node.keys.size() == m - 1) {
            // 根节点已满，需要分裂
            BPlusTreeNode newRoot = new BPlusTreeNode(false); // 创建新根节点
            newRoot.children.add(root);  // 新根节点的第一个子节点为旧根节点
            splitChild(newRoot, 0, root); // 分裂旧根节点
            root = newRoot;              // 更新根节点
        }
        insertNonFull(root, key, value); // 在非满节点中插入
    }

    // 在非满节点中插入关键字
    private void insertNonFull(BPlusTreeNode node, int key, String value) {
        if (node.isLeaf) {
            // 如果是叶子节点，插入或更新值
            int i = 0;
            while (i < node.keys.size() && key > node.keys.get(i)) {
                i++; // 找到插入位置
            }
            if (i < node.keys.size() && key == node.keys.get(i)) {
                // 关键字已存在，更新值
                node.values.set(i, value);
            } else {
                // 插入新的关键字和值
                node.keys.add(i, key);
                node.values.add(i, value);
            }
        } else {
            // 如果是内部节点
            int i = 0;
            while (i < node.keys.size() && key >= node.keys.get(i)) {
                i++; // 找到子节点索引
            }
            BPlusTreeNode child = node.children.get(i); // 获取子节点
            if (child.keys.size() == m - 1) {
                // 子节点已满，需要分裂
                splitChild(node, i, child);
                if (key >= node.keys.get(i)) {
                    i++; // 确定正确的子节点
                }
            }
            insertNonFull(node.children.get(i), key, value); // 递归插入
        }
    }

    // 分裂子节点
    private void splitChild(BPlusTreeNode parent, int index, BPlusTreeNode node) {
        int mid = m / 2;                 // 中间索引
        BPlusTreeNode newNode = new BPlusTreeNode(node.isLeaf); // 创建新节点
        // 将关键字和子节点分裂到新节点
        newNode.keys.addAll(node.keys.subList(mid, node.keys.size())); // 复制右半部分关键字
        node.keys.subList(mid, node.keys.size()).clear();              // 移除原节点的右半部分关键字

        if (node.isLeaf) {
            // 如果是叶子节点
            newNode.values = new ArrayList<>(node.values.subList(mid, node.values.size())); // 复制右半部分值
            node.values.subList(mid, node.values.size()).clear();                           // 移除原节点的右半部分值

            // 更新叶子节点的链接
            newNode.next = node.next; // 新节点的下一个节点为原节点的下一个节点
            node.next = newNode;      // 原节点的下一个节点为新节点

            // 将新节点的第一个关键字插入父节点
            parent.keys.add(index, newNode.keys.get(0));
            parent.children.add(index + 1, newNode);
        } else {
            // 如果是内部节点
            newNode.children = new ArrayList<>(node.children.subList(mid, node.children.size())); // 复制右半部分子节点
            node.children.subList(mid, node.children.size()).clear();                             // 移除原节点的右半部分子节点

            int upKey = node.keys.remove(mid - 1); // 提升的关键字

            // 将提升的关键字插入父节点
            parent.keys.add(index, upKey);
            parent.children.add(index + 1, newNode);
        }
    }

    // 删除操作
    public void delete(int key) {
        delete(root, key); // 从根节点开始删除
        // 如果根节点为空且不是叶子节点，降低树的高度
        if (!root.isLeaf && root.keys.size() == 0) {
            root = root.children.get(0); // 更新根节点
        }
    }

    private boolean delete(BPlusTreeNode node, int key) {
        int idx = 0;
        while (idx < node.keys.size() && key > node.keys.get(idx)) {
            idx++; // 找到关键字的位置
        }

        if (node.isLeaf) {
            // 在叶子节点中删除
            if (idx < node.keys.size() && key == node.keys.get(idx)) {
                node.keys.remove(idx);   // 移除关键字
                node.values.remove(idx); // 移除对应的值
                return true;             // 删除成功
            }
            return false; // 未找到关键字
        } else {
            // 在内部节点中删除
            BPlusTreeNode child = node.children.get(idx); // 获取子节点
            boolean deleted = delete(child, key);         // 递归删除
            if (child.keys.size() < (m - 1) / 2) {
                // 子节点关键字数不足，需要调整
                rebalance(node, idx);
            }
            return deleted;
        }
    }

    // 重新平衡节点
    private void rebalance(BPlusTreeNode parent, int idx) {
        BPlusTreeNode node = parent.children.get(idx);                          // 当前节点
        BPlusTreeNode leftSibling = idx > 0 ? parent.children.get(idx - 1) : null; // 左兄弟节点
        BPlusTreeNode rightSibling = idx < parent.children.size() - 1 ? parent.children.get(idx + 1) : null; // 右兄弟节点

        if (leftSibling != null && leftSibling.keys.size() > (m - 1) / 2) {
            // 从左兄弟借一个关键字
            if (node.isLeaf) {
                node.keys.add(0, leftSibling.keys.remove(leftSibling.keys.size() - 1)); // 移动关键字
                node.values.add(0, leftSibling.values.remove(leftSibling.values.size() - 1)); // 移动值
                parent.keys.set(idx - 1, node.keys.get(0)); // 更新父节点的关键字
            } else {
                node.keys.add(0, parent.keys.get(idx - 1)); // 将父节点的关键字下移
                parent.keys.set(idx - 1, leftSibling.keys.remove(leftSibling.keys.size() - 1)); // 左兄弟的最大关键字上移
                node.children.add(0, leftSibling.children.remove(leftSibling.children.size() - 1)); // 移动子节点
            }
        } else if (rightSibling != null && rightSibling.keys.size() > (m - 1) / 2) {
            // 从右兄弟借一个关键字
            if (node.isLeaf) {
                node.keys.add(rightSibling.keys.remove(0)); // 移动关键字
                node.values.add(rightSibling.values.remove(0)); // 移动值
                parent.keys.set(idx, rightSibling.keys.get(0)); // 更新父节点的关键字
            } else {
                node.keys.add(parent.keys.get(idx)); // 将父节点的关键字下移
                parent.keys.set(idx, rightSibling.keys.remove(0)); // 右兄弟的最小关键字上移
                node.children.add(rightSibling.children.remove(0)); // 移动子节点
            }
        } else {
            // 需要合并节点
            if (leftSibling != null) {
                if (node.isLeaf) {
                    leftSibling.keys.addAll(node.keys);   // 合并关键字
                    leftSibling.values.addAll(node.values); // 合并值
                    leftSibling.next = node.next;         // 更新链表指针
                } else {
                    leftSibling.keys.add(parent.keys.remove(idx - 1)); // 父节点的关键字下移
                    leftSibling.keys.addAll(node.keys);                // 合并关键字
                    leftSibling.children.addAll(node.children);        // 合并子节点
                }
                parent.children.remove(idx); // 移除被合并的节点
            } else if (rightSibling != null) {
                if (node.isLeaf) {
                    node.keys.addAll(rightSibling.keys);   // 合并关键字
                    node.values.addAll(rightSibling.values); // 合并值
                    node.next = rightSibling.next;         // 更新链表指针
                } else {
                    node.keys.add(parent.keys.remove(idx)); // 父节点的关键字下移
                    node.keys.addAll(rightSibling.keys);    // 合并关键字
                    node.children.addAll(rightSibling.children); // 合并子节点
                }
                parent.children.remove(idx + 1); // 移除被合并的节点
            }
        }
    }

    // 中序遍历打印树（用于调试）
    public void printTree() {
        printTree(root, 0); // 从根节点开始打印，层级为0
    }

    private void printTree(BPlusTreeNode node, int level) {
        if (node != null) {
            if (node.isLeaf) {
                // 打印叶子节点
                System.out.print("Level " + level + " [叶节点]: ");
                for (int key : node.keys) {
                    System.out.print(key + " ");
                }
                System.out.println();
            } else {
                // 打印内部节点
                System.out.print("Level " + level + " [内部节点]: ");
                for (int key : node.keys) {
                    System.out.print(key + " ");
                }
                System.out.println();
                for (BPlusTreeNode child : node.children) {
                    printTree(child, level + 1); // 递归打印子节点，层级加1
                }
            }
        }
    }
}
