package heima_data_structure.test.datastructure.binarytree;

import heima_data_structure.java.src.main.java.com.itheima.datastructure.binarytree.E06Leetcode111_1;
import heima_data_structure.java.src.main.java.com.itheima.datastructure.binarytree.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestE06Leetcode111_1 {
    @Test
    public void test1() {
        TreeNode root = new TreeNode(new TreeNode(2), 1, new TreeNode(3));
        assertEquals(2, new E06Leetcode111_1().minDepth(root));
    }

    @Test
    public void test2() {
        TreeNode root = new TreeNode(new TreeNode(2), 1, new TreeNode(null, 3, new TreeNode(4)));
        assertEquals(2, new E06Leetcode111_1().minDepth(root));
    }

    @Test
    public void test3() {
        TreeNode root = new TreeNode(1);
        assertEquals(1, new E06Leetcode111_1().minDepth(root));
    }

    @Test
    public void test4() {
        TreeNode root = new TreeNode(new TreeNode(2), 1, null);
        assertEquals(2, new E06Leetcode111_1().minDepth(root));
    }
}
