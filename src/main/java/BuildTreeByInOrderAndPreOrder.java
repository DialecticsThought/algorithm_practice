import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author veritas
 * @Data 2025/2/20 18:22
 */
public class BuildTreeByInOrderAndPreOrder {
    /**
     * 一棵树
     * 前序遍历
     * <p>
     * 中序遍历
     * <p>
     * 假设
     * 当前节点为根的所在子树 在中序遍历中的区间范围是[l,r]
     * 当前节点在中序遍历中的索引是m
     * 当前节点在中序遍历中的索引是i
     * 可以得到当前节点的左子树在中序遍历的区间范围是[l,m-1],左子树的节点个数: m-1 - l + 1 = m -l
     * 可以得到当前节点的右子树在中序遍历的区间范围是[m+1,r],右子树的节点个数: r - (m-1) + 1 - r -m
     * 那么
     * 当前节点在preorder中的索引     当前节点为根的所在子树在preorder中的区间
     * 根节点              i                           [l,r]
     * 左子树             i+1                          [l, m− 1]
     * 右子树             i+1+(m-l)                    [m+1,r]
     */
    TreeNode dfs(int[] preorder, int[] inorder, Map<Integer, Integer> map, int i, int l, int r) {
        // TODO 构建二叉树的递归过程，各个节点是在向下“递”的过程中建立的，
        //      而各条边（即引用）是在向上“归”的过程中建立的

        // base case
        if (r < l) {//右边界 < 左边界
            return null;
        }
        // 初始化当前节点
        TreeNode currentNode = new TreeNode(preorder[i]);
        // 得到当前节点在中序遍历中的位置
        Integer m = map.get(preorder[i]);
        // 对当前节点的左子节点做相同操作
        currentNode.left = dfs(preorder, inorder, map, i + 1, l, m - 1);
        // 对当前节点的左子节点做相同操作
        currentNode.left = dfs(preorder, inorder, map, i + 1 + (m - l), m + 1, r);
        // 返回当前节点
        return currentNode;
    }

    TreeNode buildTree(int[] preorder, int[] inorder) {
        // 初始化哈希表，存储 inorder 元素到索引的映射
        Map<Integer, Integer> hmap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            hmap.put(inorder[i], i);
        }
        TreeNode root = dfs(preorder, inorder, hmap, 0, 0, inorder.length - 1);
        return root;
    }

    class TreeNode {
        Integer id;

        TreeNode left;

        TreeNode right;

        public TreeNode(Integer id) {
            this.id = id;
        }
    }


    public int binarySearch(int[] nums, int target, int l, int r) {
        if (r < l) {
            return -1;
        }
        int mid = (l + r) / 2;
        // 如果找到了
        if (nums[mid] == target) {
            return mid;
        }
        if (nums[mid] > target) {
            return binarySearch(nums, target, mid + 1, r);
        }
        if (nums[mid] < target) {
            return binarySearch(nums, target, l, mid - 1);
        }
        return -1;
    }
}
