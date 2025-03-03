/**
 * @Description
 * @Author veritas
 * @Data 2025/3/2 16:24
 */
public class LeetCode_101_SymmetryTree {
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

    public boolean isSymmetric(TreeNode root) {
        return isSymmetric(root.left, root.right);
    }

    /**
     * 如果同时满足下面的条件，两个树互为镜像：
     * 它们的两个根结点具有相同的值
     * 每个树的右子树都与另一个树的左子树镜像对称
     * <p>
     * 通过「同步移动」两个指针的方法来遍历这棵树，p 指针和 q 指针一开始都指向这棵树的根，随后 p 右移时，q 左移，p 左移时，q 右移。
     * 每次检查当前 p 和 q 节点的值是否相等，如果相等再判断左右子树是否对称。
     * <p>
     * TODO 所谓的镜像树 或者叫做 对称树 就是
     * 当前 遍历的两颗树 的根节点相同 p.val == q.val
     * 当前 遍历到的两棵树 的两个根节点 其中一个根节点p的左子树 等于另一个根节点q的右子树
     * 当前 遍历到的两棵树 的两个根节点 其中一个根节点p的右子树 等于另一个根节点q的左子树
     * @param p
     * @param q
     * @return
     */
    public boolean isSymmetric(TreeNode p, TreeNode q) {
        // Base case
        if (p == null && q == null) {
            return true;
        }

        if (p != null && q == null) {
            return false;
        }

        if (p == null && q != null) {
            return false;
        }

        if (p.val != q.val) {
            return false;
        }

        return isSymmetric(p.left, q.right) && isSymmetric(p.right, q.left);
    }
}
