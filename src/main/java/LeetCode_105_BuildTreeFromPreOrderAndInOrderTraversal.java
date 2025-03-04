import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author veritas
 * @Data 2025/3/4 17:42
 */
public class LeetCode_105_BuildTreeFromPreOrderAndInOrderTraversal {

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
     * 二叉树前序遍历的顺序为：
     *      先遍历根节点
     *      随后递归地遍历左子树
     *      最后递归地遍历右子树
     *
     * 二叉树中序遍历的顺序为：
     *      先递归地遍历左子树
     *      随后遍历根节点
     *      最后递归地遍历右子树
     * </pre>
     * 对于任意一颗树而言，前序遍历的形式总是
     * [ 根节点, [左子树的前序遍历结果], [右子树的前序遍历结果] ]
     * 即根节点总是前序遍历中的第一个节点。而中序遍历的形式总是
     * [ [左子树的中序遍历结果], 根节点, [右子树的中序遍历结果] ]
     * <p>
     * TODO 我们还需要知道 当前子树在preOrder和InOrder中的范围
     * 只要我们在中序遍历中定位到根节点，那么我们就可以分别知道左子树和右子树中的节点数目。☆☆☆☆☆☆☆☆☆☆
     * 由于同一颗子树的前序遍历和中序遍历的长度显然是相同的，
     * 因此我们就可以对应到前序遍历的结果中，对上述形式中的所有左右括号进行定位
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        /**
         * 在递归构造二叉树时，每次需要根据当前根节点的值在中序数组中找到该节点的位置，
         * 从而划分出左子树和右子树。如果每次用循环查找，则需要 O(n) 时间
         */
        Map<Integer, Integer> map = new HashMap<>();
        /**
         * TODO 中序数组的目的就是用来构建hashmap
         */
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        /**
         * 根节点在preOrder是i 通过hashmap找到InOrder的位置是j
         *而且你要知道 这个节点i的树的长度
         */
        doBuildTree(preorder, map, 0, preorder.length - 1, 0, inorder.length - 1);
    }

    /**
     * @param preorder
     * @param map
     * @param preStart：当前子树在前序数组中的起始索引，也就是当前子树根节点的位置。
     * @param preEnd：当前子树在前序数组中的结束索引，表示该子树所有节点在前序数组中的范围。
     * @param inStart：当前子树在中序数组中的起始索引。
     * @param inEnd：当前子树在中序数组中的结束索引
     * @return TODO 向上(也就是父节点)返回当前一层构造的根节点
     */
    public TreeNode doBuildTree(int[] preorder, Map<Integer, Integer> map,
                                int preStart, int preEnd, int inStart, int inEnd) {
        // base case 前序遍历的开始索引 > 结束索引
        if (preStart > preEnd) {
            return null;
        }
        // 构造当前子树的根节点
        TreeNode root = new TreeNode(preorder[preStart]);
        // 从 hashmap中找到该节点在中序遍历的位置
        Integer i = map.get(preorder[preStart]);
        // TODO 利用 当前子树在前序遍历 和中序遍历的区间范围
        //  得到当前根节点的左右子树的分别的前序遍历 和中序遍历的区间范围
        //    对于任意一颗树而言，前序遍历的形式总是
        //     [ 根节点, [左子树的前序遍历结果], [右子树的前序遍历结果] ]
        //    即根节点总是前序遍历中的第一个节点。而中序遍历的形式总是
        //     [ [左子树的中序遍历结果], 根节点, [右子树的中序遍历结果] ]

        // 得到 当前根节点的左孩子在前序遍历中的索引 也是当前根节点所在子树 在前序遍历中的 范围 的起始点 的后面一个
        int leftChildPreStart = preStart + 1;

        // 通过当前节点(根节点)在中序遍历中的索引 + 当前节点所在子树 在中序遍历中的范围起始点 得到 当前节点的左子树的长度
        int leftChildLength = i - inStart;

        // 通过 左子树的长度 直到 左子树的在前序遍历中的范围终止点
        // TODO 只需直到 左子树的区间长度 不用求 右子树的区间长度 ，通过其他方式直到右子树在前序遍历/中序遍历的开始/结束索引
        int leftChildPreEnd = leftChildPreStart + leftChildLength;

        //  得到 当前根节点的左孩子所在的子树 在 中序遍历的 范围的起始点 就是 当前根节所在的子树 在 中序遍历的 范围的起始点
        int leftChildInStart = inStart;

        //  得到 当前根节点的左孩子所在的子树 在 中序遍历的 范围的终止点 就是 当前根节 在 中序遍历的 索引的前一个节点
        int leftChildInEnd = i -1;

        // 知道了左孩子所在的子树的长度 +  [ 根节点, [左子树的前序遍历结果], [右子树的前序遍历结果] ]
        // 得到 当前根节点的右孩子在前序遍历中的索引
        int rightChildPreStart = preStart + leftChildLength + 1;

        // 得到 当前根节点的右孩子所在的子树 在 前序遍历中 范围的终止点 就是 当前根节所在的子树 在 前序遍历的 范围的终止点
        int rightChildPreEnd = preEnd;

        //  得到 当前根节点的右孩子所在的子树 在 中序遍历的 范围的起始点 就是 当前根节 在 中序遍历的 索引的后一个节点
        int rightChildInStart = i + 1;

        // 得到 当前根节点的右孩子所在的子树 在 前序遍历中 范围的终止点 就是 当前根节所在的子树 在 中序遍历的 范围的终止点
        int rightChildInEnd = inEnd;

        return null;
    }
}
