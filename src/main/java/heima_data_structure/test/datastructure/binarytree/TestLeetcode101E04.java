package heima_data_structure.test.datastructure.binarytree;

import heima_data_structure.java.src.main.java.com.itheima.datastructure.binarytree.Leetcode_101_E04;
import heima_data_structure.java.src.main.java.com.itheima.datastructure.binarytree.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestLeetcode101E04 {

    @Test
    public void test1() {
        TreeNode root = new TreeNode(
                new TreeNode(new TreeNode(3), 2, new TreeNode(4)),
                1,
                new TreeNode(new TreeNode(4), 2, new TreeNode(3))
        );
        assertTrue(new Leetcode_101_E04().isSymmetric(root));
    }

    @Test
    public void test2() {
        TreeNode root = new TreeNode(
                new TreeNode(null, 2, new TreeNode(3)),
                1,
                new TreeNode(null, 2, new TreeNode(3))
        );
        assertFalse(new Leetcode_101_E04().isSymmetric(root));
    }
}
