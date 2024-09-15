package heima_data_structure.java.src.main.java.com.itheima.datastructure.btree;

import java.util.ArrayList;
import java.util.List;

/**
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
