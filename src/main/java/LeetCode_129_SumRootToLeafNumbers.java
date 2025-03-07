/**
 * @Description
 * @Author veritas
 * @Data 2025/3/7 21:52
 */
public class LeetCode_129_SumRootToLeafNumbers {

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

    public int sumNumbers(TreeNode root) {
        return dfs(root, 0);
    }

    /**
     * <pre>
     * 求根节点到叶子节点路径的数值之和 这个问题中：
     *  大问题是 整棵树的路径和
     *  小问题是 左子树的路径和 + 右子树的路径和
     *  直到 叶子节点 作为基本情况，路径值已经完整，我们可以直接返回
     *  最终每一层都 汇总自己的左右子树路径和，返回给父节点
     *          1
     *        /   \
     *       2     3
     *      / \   / \
     *     4   5 6   7
     *  Step 1: 访问 root = 1
     *      prevSum = 0
     *      curSum = 0 * 10 + 1 = 1
     *      root 不是叶子节点，递归：
     *      return dfs(2, 1) + dfs(3, 1);
     *      现在变成两个子问题：
     *          求 2 为根的左子树路径和
     *          求 3 为根的右子树路径和
     *
     *  Step 2: 访问 root = 2（左子树）
     *      prevSum = 1
     *      curSum = 1 * 10 + 2 = 12
     *      不是叶子节点，递归：
     *      return dfs(4, 12) + dfs(5, 12);
     *      现在又变成了两个更小的子问题
     *          求 4 为叶子节点的路径值
     *          求 5 为叶子节点的路径值
     *
     *  Step 3: 访问 root = 4（左子树）
     *      prevSum = 12
     *      curSum = 12 * 10 + 4 = 124
     *      是叶子节点，直接返回 124
     *      return 124;
     *
     *  Step 4: 访问 root = 5（右子树）
     *      prevSum = 12
     *      curSum = 12 * 10 + 5 = 125
     *      是叶子节点，直接返回 125
     *      return 125;
     *
     *  Step 5: 回溯到 2 处理完左、右子树
     *      dfs(4, 12) = 124
     *      dfs(5, 12) = 125
     *      计算并返回
     *      return 124 + 125 = 249;
     *
     *  Step 6: 访问 root = 3（右子树）
     *      prevSum = 1
     *      curSum = 1 * 10 + 3 = 13
     *      不是叶子节点，递归：
     *      return dfs(6, 13) + dfs(7, 13);
     *
     *  Step 7: 访问 root = 6（左子树）
     *      prevSum = 13
     *      curSum = 13 * 10 + 6 = 136
     *      是叶子节点，直接返回 136
     *      return 136;
     *
     *  Step 8: 访问 root = 7（右子树）
     *      prevSum = 13
     *      curSum = 13 * 10 + 7 = 137
     *      是叶子节点，直接返回 137
     *      return 137;
     *  Step 9: 回溯到 3 处理完左、右子树
     *      dfs(6, 13) = 136
     *      dfs(7, 13) = 137
     *      计算并返回
     *      return 136 + 137 = 273;
     *
     *  Step 10: 回溯到 1 处理完左、右子树
     *      dfs(2, 1) = 249
     *      dfs(3, 1) = 273
     *      计算最终结果
     *      return 249 + 273 = 522;
     *  📌 最终输出
     *  sumNumbers(root);  // 输出 522
     *
     *  dfs(1, 0)
     *  ├── dfs(2, 1)
     *  │   ├── dfs(4, 12) → 124
     *  │   ├── dfs(5, 12) → 125
     *  │   └── return 124 + 125 = 249
     *  ├── dfs(3, 1)
     *  │   ├── dfs(6, 13) → 136
     *  │   ├── dfs(7, 13) → 137
     *  │   └── return 136 + 137 = 273
     *  └── return 249 + 273 = 522
     *  📌 递和归
     *      🔼 递（拆解问题）
     *      根节点 1 计算 sum = 1
     *      拆成 dfs(2, 1) + dfs(3, 1)
     *      再拆成 dfs(4, 12) + dfs(5, 12)
     *      直到遇到叶子节点，返回路径值
     *      🔽 归（合并结果）
     *      叶子节点 4 返回 124
     *      叶子节点 5 返回 125
     *      节点 2 返回 124 + 125 = 249
     *      叶子节点 6 返回 136
     *      叶子节点 7 返回 137
     *      节点 3 返回 136 + 137 = 273
     *      根节点 1 返回 249 + 273 = 522
     * </pre>
     * @param root
     * @param preSum
     * @return
     */
    public int dfs(TreeNode root,Integer preSum) {
        if(root == null) {// 空节点，直接返回 0（不影响求和）
            return 0;
        }
        /**
         * <pre>
         *          1
         *        /   \
         *       2     3
         *      / \   / \
         *     4   5 6   7
         *
         *  对于节点2而言, 节点1把1传递给它的左孩子2，左孩子2接收之后计算 1 -> 2 的路径的值是多少 然后传递给左孩子4和右孩子5
         *      对于节点4而言, 节点2把2传递给它的左孩子4，左孩子4接收之后计算 1 -> 2 -> 4 的路径的值是多少
         *          因为已经是叶子节点了， 把 路径值返回给上层节点2
         *      对于节点5而言, 节点2把2传递给它的左孩子5，左孩子4接收之后计算 1 -> 2 -> 5 的路径的值是多少
         *          因为已经是叶子节点了， 把 路径值返回给上层节点2
         *  对于节点2而言，接收到左右孩子路径和，向上返回
         * </pre>
         */
        int curSum = 10 * preSum + root.val;// 当前路径值
        /**
         * <pre>
         * eg1:
         *       1
         *      / \
         *     2   3
         *    / \   \
         *   4   5   7
         *   例如，当前递归到 root = 3
         *   dfs(root.left, sum) 访问 2 后递归 4 和 5，不会访问 null。
         *   dfs(root.right, sum) 访问 3，但 3 只有右子节点 7，左子节点是 null，
         *   所以：return dfs(root.left, sum) + dfs(root.right, sum);
         *   其中 dfs(root.left, sum) 返回 0（不会影响求和）
         * eg2:
         *       1
         *      / \
         *     2   3
         *    /
         *   4
         *  dfs(1, 0) → sum = 1
         *      dfs(2, 1) → sum = 12
         *          dfs(4, 12) → sum = 124 (叶子节点，直接返回 124)
         *          dfs(null, 12) → 返回 0
         *      dfs(3, 1) → sum = 13 (叶子节点，直接返回 13)
         *  return 124 + 0 + 13 = 137
         *  if (root == null) 是为了 防止访问 null，避免递归错误。
         *  if (root.left == null && root.right == null) 确保叶子节点返回完整路径数值，否则递归会丢失它的贡献值。
         *  如果省略第二个 if，叶子节点的路径值会被 0 覆盖，导致错误的求和
         * </pre>
         */
        if(root.left == null && root.right == null) {  // 叶子节点，直接返回路径值
            return curSum;
        }else {
            // 递归调用左子树和右子树，并将路径值相加返回
            return dfs(root.left, curSum) + dfs(root.right, curSum);
        }

    }
}
