package heima_data_structure.java.src.main.java.com.itheima.datastructure.binarytree;


import java.util.HashMap;

/**
 * 根据前序中序遍历结果构造二叉树
 */
public class Leetcode_105_Improved_E09 {
    //  用 hashmap 改善查找性能，其中 key 是 inOrder 值， value 是 inOrder 索引
    HashMap<Integer, Integer> map = new HashMap<>();

    public TreeNode buildTree(int[] preOrder, int[] inOrder) {
        for (int i = 0; i < inOrder.length; i++) {
            map.put(inOrder[i], i);
        }
        return helper(preOrder, 0, 0, inOrder.length - 1);
    }

    // preBegin 决定了每次在 preOrder 中找到根元素
    // inBegin 和 inEnd 可以用来获取区间内元素个数，结束递归等
    private TreeNode helper(int[] preOrder, int preBegin, int inBegin, int inEnd) {
        if (inBegin > inEnd) {
            return null;
        }
        int rootValue = preOrder[preBegin];
        TreeNode root = new TreeNode(rootValue);
        int i = map.get(rootValue);
        int leftSize = i - inBegin;
        System.out.println("元素：" + rootValue + " left[" + (preBegin + 1) + "] inOrder 索引范围[" + inBegin + "~" + (i - 1) + "]");
        System.out.println("元素：" + rootValue + " right[" + (preBegin + 1 + leftSize) + "] inOrder 索引范围[" + (i + 1) + "~" + inEnd + "]");
        root.left = helper(preOrder, preBegin + 1, inBegin, i - 1);
        root.right = helper(preOrder, preBegin + 1 + leftSize, i + 1, inEnd);
        return root;
    }

    public static void main(String[] args) {
        int[] preOrder = {1, 2, 4, 3, 6, 7};
        int[] inOrder = {4, 2, 1, 6, 3, 7};
        /*
        例如：
        根据根节点[1] 到中序遍历数组中一分为2，首次递归
            [1] 2  4  3  6  7  前
            0   1  2  3  4  5  前索引

            4  2  [1] 6  3  7  中
            0  1  2   3  4  5  中索引

            确定 preOrder 中 left 和 right 的递归起始索引，当然也要确定 inOrder 对应的两个索引位置
                left    right
            1   [2]  4  [3]  6  7  前
            0   1    2  3    4  5  前索引
            left  inOrder 索引范围： 0~1
            right inOrder 索引范围： 3~5

         */
        TreeNode root = new Leetcode_105_Improved_E09().buildTree(preOrder, inOrder);
        System.out.println(root);
    }

}
