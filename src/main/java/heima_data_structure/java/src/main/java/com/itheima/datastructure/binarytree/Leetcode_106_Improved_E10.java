package heima_data_structure.java.src.main.java.com.itheima.datastructure.binarytree;

import java.util.HashMap;

/**
 * 根据中序与后序遍历结果构造二叉树
 */
public class Leetcode_106_Improved_E10 {
    HashMap<Integer, Integer> map = new HashMap<>();

    public TreeNode buildTree(int[] inOrder, int[] postOrder) {
        for (int i = 0; i < inOrder.length; i++) {
            map.put(inOrder[i], i);
        }
        return helper(postOrder, postOrder.length - 1, 0, inOrder.length - 1);
    }

    /*
        inOrder = {4,2,1,6,3,7}
        postOrder = {4,2,6,7,3,1}
     */
    private TreeNode helper(int[] postOrder, int postEnd, int inBegin, int inEnd) {
        if (inBegin > inEnd) {
            return null;
        }
        int rootValue = postOrder[postEnd];
        TreeNode root = new TreeNode(rootValue);
        Integer i = map.get(rootValue);
//        int leftSize = i - inBegin;
        int rightSize = inEnd - i;
        System.out.println("元素：" + rootValue + " left[" + (postEnd - 1 - rightSize) + "] inOrder 索引范围[" + inBegin + "~" + (i - 1) + "]");
        System.out.println("元素：" + rootValue + " right[" + (postEnd - 1) + "] inOrder 索引范围[" + (i + 1) + "~" + inEnd + "]");
        root.left = helper(postOrder, postEnd - 1 - rightSize, inBegin, i - 1);
        root.right = helper(postOrder, postEnd - 1, i + 1, inEnd);
        return root;
    }

    public static void main(String[] args) {
        int[] postOrder = {4, 2, 6, 7, 3, 1};
        int[] inOrder = {4, 2, 1, 6, 3, 7};
        TreeNode root = new Leetcode_106_Improved_E10().buildTree(inOrder, postOrder);
        System.out.println(root);
    }
}
