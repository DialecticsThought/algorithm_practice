

/**
 * @Description
 * @Author veritas
 * @Data 2025/3/5 12:29
 */
public class LeetCode_226_InvertBinaryTree {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }


    /**
     * <pre>
     *        4
     *       / \
     *      2   7
     *     / \ / \
     *    1  3 6  9
     * 初始状态
     * 根节点：4
     * 左子树（挂在节点2上）：
     * 节点2的左子节点：1
     * 节点2的右子节点：3
     * 右子树（挂在节点7上）：
     * 节点7的左子节点：6
     * 节点7的右子节点：9
     *
     * 第一步：处理根节点（4）
     * 检查节点4是否为 null
     * 不是空节点，继续。
     * 交换节点4的左右子节点
     * 原来左子节点为节点2，右子节点为节点7。
     * 交换后，节点4的左子节点变为节点7，右子节点变为节点2。
     * 递归调用
     * 接下来对交换后的左子树（节点7）和右子树（节点2）分别调用 invertTree。
     * TODO 交换的不是节点的val 而是节点的引用 所以相当于子树换了位置
     * 此时树的结构为：
     *        4
     *       / \
     *      7   2
     *     / \ / \
     *    6  9 1  3
     *
     * 第二步：处理左子树（当前为节点7）
     * 检查节点7是否为 null
     *
     * 不是空节点，继续。
     * 交换节点7的左右子节点
     *
     * 节点7的左子节点原为节点6，右子节点原为节点9。
     * 交换后，左子节点变为节点9，右子节点变为节点6。
     * 递归调用
     *
     * 对节点9和节点6分别调用 invertTree。
     * 节点9和节点6都是叶子节点（它们的左右子节点均为 null），递归调用会直接返回。
     * 此时节点7子树的结构变为
     *    7
     *   / \
     *  9   6
     *
     * 第三步：处理右子树（当前为节点2）
     * 检查节点2是否为 null
     * 不是空节点，继续。
     * 交换节点2的左右子节点
     * 节点2的左子节点原为节点1，右子节点原为节点3。
     * 交换后，左子节点变为节点3，右子节点变为节点1。
     * 递归调用
     * 对节点3和节点1分别调用 invertTree。
     * 节点3和节点1均为叶子节点，递归调用直接返回。
     * 此时节点2子树的结构变为：
     *    2
     *   / \
     *  3   1
     * 最终状态
     * 经过以上所有步骤，整棵树的镜像反转结果为
     *        4
     *       / \
     *      7   2
     *     / \ / \
     *    9  6 3  1
     * </pre>
     *
     * @param treeNode
     * @return
     */
    public TreeNode invertTree(TreeNode treeNode) {
        // base case
        if (treeNode == null) {
            return null;
        }
        // 类似于交换 需要一个临时变量
        // 交换的是引用 不是节点的val
        TreeNode tmp = treeNode.left;
        treeNode.left = treeNode.right;
        treeNode.right = tmp;
        // 对交换过后的左右孩子递归执行代码
        invertTree(treeNode.left);
        invertTree(treeNode.right);
        // 返回当前的节点
        return treeNode;
    }



}
