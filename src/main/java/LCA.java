/**
 * @Description
 * @Author veritas
 * @Data 2024/9/19 16:04
 */
public class LCA {

    class Node {
        private Integer value;

        private Node left;

        private Node right;
    }

    /**
     * <pre>
     *        3
     *       / \
     *      5   1
     *     / \   \
     *    6   2   8
     *       / \
     *      7   4
     * 第一步：从根节点 3 开始。递归遍历左子树查找 5 和 1。
     *      左子树的根是 5，继续递归遍历其左子树和右子树。
     *          左子树是 6，没有找到 5 或 1，返回 null。
     *          右子树的根是 2，递归遍历其左右子树。
     *              左子树是 7，返回 null。
     *              右子树是 4，返回 null。
     *          返回 null，表示在 5 的右子树中没有找到 5 或 1。
     *     但是，当前节点 5 就是我们要找的节点之一，所以返回节点 5。
     * 第二步：递归遍历右子树查找 5 和 1。
     *      右子树的根是 1，找到了节点 1，所以返回 1。
     * 第三步：现在，左子树返回了 5，右子树返回了 1，说明 p 和 q 分别位于当前节点 3 的左右子树中，因此 3 就是它们的最近公共祖先。
     * </pre>
     * <pre>
     *           3
     *          / \
     *         5   1
     *        / \
     *       6   2
     *          / \
     *         7   4
     *            /
     *           8
     * 1.从根节点 3 开始递归查找：
     *      递归查找左子树 5，递归查找右子树 1。
     *      右子树 1 返回 null，因为 p 和 q 都不在右子树中。
     * 2.在左子树 5 中递归查找：
     *      递归查找左子树 6，返回 null，因为 6 既不是 p 也不是 q。
     *      递归查找右子树 2。
     * 3.在右子树 2 中继续递归查找：
     *      递归查找左子树 7，找到 p，于是返回 7。
     *      递归查找右子树 4。
     * 4.进入右子树 4 中继续递归查找：
     *      递归查找左子树 8，找到 q，于是返回 8。
     *      右子树为空，返回 null。
     * 5.回溯到节点 2：
     *      left 返回了 7，right 返回了 8。因为 p 和 q 分别在左右子树中，因此节点 2 是它们的最近公共祖先，返回 2。
     * 6.回溯到节点 5：
     *      左子树返回 null，右子树返回 2，因此返回右子树的结果，即 2。
     * 7.回溯到根节点 3：
     *      左子树返回 2，右子树返回 null，因此最终返回 2 作为 7 和 8 的最近公共祖先
     * lowestCommonAncestor(3, 7, 8)
     * ├── lowestCommonAncestor(5, 7, 8)
     * │   ├── lowestCommonAncestor(6, 7, 8)
     * │   │   ├── lowestCommonAncestor(null, 7, 8) -> null
     * │   │   └── lowestCommonAncestor(null, 7, 8) -> null
     * │   │   └── return null
     * │   └── lowestCommonAncestor(2, 7, 8)
     * │       ├── lowestCommonAncestor(7, 7, 8) -> 7
     * │       └── lowestCommonAncestor(4, 7, 8)
     * │           ├── lowestCommonAncestor(8, 7, 8) -> 8
     * │           └── lowestCommonAncestor(null, 7, 8) -> null
     * │           └── return 8
     * │       └── since left=7 and right=8, return 2
     * │   └── return 2
     * ├── lowestCommonAncestor(1, 7, 8)
     * │   ├── lowestCommonAncestor(null, 7, 8) -> null
     * │   └── lowestCommonAncestor(null, 7, 8) -> null
     * │   └── return null
     * └── since left=2 and right=null, return 2
     * </pre>
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public Node lca(Node root, Node p, Node q) {
        // base case 如果遍历到的当前节点为空 就返回空
        if (root == null) {
            return null;
        }
        // base case 如果遍历到的当前节点 是p或者q 那么返回当前节点
        if (root == p || root == q) {
            return root;
        }
        /**
         * <pre>
         *  这两个递归调用 left 和 right 并不一定是直接找到 p 或 q，而是返回值可能为以下三种情况：
         *      1.返回 null：这意味着在该子树中没有找到 p 或 q。
         *      2.返回 p 或 q 本身：如果 p 或 q 是子树的某个节点，那么递归会返回该节点。
         *      3.返回最近公共祖先（LCA）：当左右子树分别找到了 p 和 q 时，返回的就是当前节点作为 LCA。
         * </pre>
         */
        Node left = lca(root.left, p, q);

        Node right = lca(root.right, p, q);
        // 左右子树都返回非空
        // 这个情况 当前节点（也就是 root）就是 LCA
        if (left != null && right != null) {
            return root;
        }
        // 只有一侧返回非空（即 left 或 right 有一个为 null，另一个为非 null）
        // 在这种情况下
        // 可能返回 p 或者 q 在 当前子树中 但还不能确定 LCA，所以我们将找到的 p 或 q 节点继续返回
        // 也有可能是 之前的递归调用已经找到了 p 和 q 的 LCA，我们直接向上传递这个 LCA
        // 如果之前已经找到了 p 和 q 的 LCA 说明，递归的子方法执行过 if (left != null && right != null){ return root;}这段逻辑
        return left != null ? left : right;
    }
}
