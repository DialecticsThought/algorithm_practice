package code_for_great_offer.class30;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Problem_0103_BinaryTreeZigzagLevelOrderTraversal {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }
    /*
    *TODO
    *       1
    *   2       3
    * 4  5    6    7
    * 先把1放入双端队列中  size=0 isHead=true
    * 1.生成一个数组 然后 size=1
    *    因为isHead=true 弹出双端队列的队首 cur=1
    *    并且把2和3一次放入双端队列的队尾
    *    把当前节点1放入数组
    *    数组放入ans
    *    isHead=false
    * 2.生成一个数组 然后 size=2
    *   因为isHead=false
    *   弹出双端队列的队尾 cur=3
    *   并且把6和7一次放入双端队列的队首  把当前节点3放入数组
    *   弹出双端队列的队尾 cur=2
    *   并且把4和5一次放入双端队列的队首 把当前节点2放入数组
    *   数组放入ans
    *   isHead=true
    * 3。生成一个数组 然后 size=4
    *   因为isHead=true
    *   弹出双端队列的队首 cur=4
    *   因为没有子节点 所以不放入 把当前节点4放入数组
    *   弹出双端队列的队首 cur=5
    *   因为没有子节点 所以不放入 把当前节点5放入数组
    *   弹出双端队列的队首 cur=6
    *   因为没有子节点 所以不放入 把当前节点4放入数组
    *   弹出双端队列的队首 cur=7
    *   因为没有子节点 所以不放入 把当前节点5放入数组
     * */
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        /*
         *TODO 每个元素 是一个数组 这个数组记录了该层节点 （以锯齿形 按序遍历）
         * */
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        LinkedList<TreeNode> deque = new LinkedList<>();
        //TODO 先把根节点 放入双端队列
        deque.add(root);
        int size = 0;
        boolean isHead = true;
        while (!deque.isEmpty()) {
            size = deque.size();
            //TODO 生成存储 当前层的元素的数组
            List<Integer> curLevel = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode cur;
                //TODO 判断当前节点是不是头
                if (isHead) {
                    //TODO 从队首弹出
                    cur = deque.pollFirst();
                } else {
                    //TODO 从队尾弹出
                    cur = deque.pollLast();
                }
                curLevel.add(cur.val);
                //TODO 如果是头 把当前节点的左右子节点放入队尾
                if (isHead) {
                    if (cur.left != null) {
                        deque.addLast(cur.left);
                    }
                    if (cur.right != null) {
                        deque.addLast(cur.right);
                    }
                } else {
                    //TODO 如果不是头 把当前节点的左右子节点放入队尾
                    if (cur.right != null) {
                        deque.addFirst(cur.right);
                    }
                    if (cur.left != null) {
                        deque.addFirst(cur.left);
                    }
                }
            }
            ans.add(curLevel);
            isHead = !isHead;
        }
        return ans;
    }

}
