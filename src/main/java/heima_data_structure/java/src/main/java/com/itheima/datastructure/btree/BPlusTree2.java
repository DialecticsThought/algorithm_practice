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
 *  eg:
 *  依次 插入 8 5 1 7 3 12 9 6
 *  插入8
 *   [8]
 *  插入5
 *   [ 5 | 8 ]
 *  插入1
 *  变成了[ 1 | 5 | 8 ]
 *  出现溢出，需要分裂原始节点[ 5 | 8 ]
 *  利用到 该性质: 在分裂时，第二个节点是第一个节点的一半（向上取整）
 *  分裂变成
 *  [ 5 |   ] [ 8 |   ]
 *  然后1加入到节点中
 *  [ 1 | 5 ] [ 8 |   ]
 *  又因为 每个键值出现在内部节点 和 在其左侧指针的子树叶子节点层的最右侧节点相同
 *  最终
 *        [ 5 |   ]
 *       ↙     ↘
 *  [ 1 | 5 ] -> [ 8 |   ]
 *  插入7
 *        [ 5 |   ]
 *       ↙     ↘
 *  [ 1 | 5 ] -> [ 7 | 8 ]
 *  插入3
 *           [ 5 |   ]
 *         ↙      ↘
 *  [ 1 | 3 | 5 ] -> [ 7 | 8 ]
 *  叶子结点出现溢出，需要分裂原始节点[ 1 | 5 ]
 *  分裂变成
 *  [ 1 |   ] [ 5 |   ]
 *  然后3加入到节点中
 *  [ 1 | 3 ] [ 5 |   ]
 *  所以叶子结点变成
 *  [ 1 | 3 ] -> [ 5 |   ] -> [ 7 | 8 ]
 *  又因为 每个键值出现在内部节点 和 在其左侧指针的子树叶子节点层的最右侧节点相同
 *  最后是
 *              [ 3 | 5 ]
 *           ↙     ↙       ↘
 *  [ 1 | 3 ] -> [ 5 |   ] -> [ 7 | 8 ]
 *  对于 [ 1 | 5 ] 有三个指针
 *  对于3 这个键 有左右2个指针，3这个键的左侧指针指向的节点的最右侧 也是3
 *  对于5 这个键 有左右2个指针，5这个键的左侧指针指向的节点的最右侧 也是5
 *  插入12
 *              [ 3 | 5 ]
 *           ↙     ↙       ↘
 *  [ 1 | 3 ] -> [ 5 |   ] -> [ 7 | 8 | 12 ]
 *  叶子结点出现溢出，需要分裂原始节点[ 7 | 8 ]
 *  分裂变成
 *  [ 7 |   ] [ 8 |   ]
 *  然后12加入到节点中
 *  [ 7 | 8 ] [ 12 |   ]
 *  所以叶子结点变成
 *  [ 1 | 3 ] -> [ 5 |   ] -> [ 7 | 8 ] -> [ 12 |   ]
 *  又因为 每个键值出现在内部节点 和 在其左侧指针的子树叶子节点层的最右侧节点相同
 *               [ 3 |   ]          [ 8 |   ]
 *            ↙      ↘            ↙      ↘
 *  [ 1 | 3 ] -> [ 5 |   ] -> [ 7 | 8 ] -> [ 12 |   ]
 *  最终是
 *                         [ 5 |   ]
 *                       ↙      ↘
 *               [ 3 |   ]          [ 8 |   ]
 *            ↙      ↘            ↙      ↘
 *  [ 1 | 3 ] -> [ 5 |   ] -> [ 7 | 8 ] -> [ 12 |   ]
 *  插入9
 *                         [ 5 |   ]
 *                       ↙      ↘
 *               [ 3 |   ]          [ 8 |   ]
 *            ↙      ↘            ↙      ↘
 *  [ 1 | 3 ] -> [ 5 |   ] -> [ 7 | 8 ] -> [ 9 | 12 ]
 *  插入6
 *                         [ 5 |   ]
 *                       ↙      ↘
 *               [ 3 |   ]          [ 8 |   ]
 *            ↙      ↘            ↙      ↘
 *  [ 1 | 3 ] -> [ 5 |   ] -> [ 6 | 7 | 8 ] -> [ 9 | 12 ]
 *  叶子结点出现溢出，需要分裂原始节点[ 7 | 8 ]
 *  分裂变成
 *  [   | 7 ] [ 8 |  ]
 *  然后12加入到节点中
 *  [ 6 | 7 ] [ 8 |   ]
 *  所以叶子结点变成
 *  [ 1 | 3 ] -> [ 5 |   ] -> [ 6 | 7 ] —> [ 8 |   ] -> [ 9 | 12 ]
 *  又因为 每个键值出现在内部节点 和 在其左侧指针的子树叶子节点层的最右侧节点相同
 *               [ 3 |   ]                  [ 8 |   ]
 *            ↙      ↘                ↙         ↓       ↘
 *  [ 1 | 3 ] -> [ 5 |   ] -> [ 6 | 7 ] —> [ 8 |   ] -> [ 9 | 12 ]
 *  最终是
 *                             [ 5 |   ]
 *                       ↙          ↘
 *               [ 3 |   ]                  [ 7 | 8 ]
 *            ↙      ↘                ↙         ↓       ↘
 *  [ 1 | 3 ] -> [ 5 |   ] -> [ 6 | 7 ] —> [ 8 |   ] -> [ 9 | 12 ]
 * </pre>
 *
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
