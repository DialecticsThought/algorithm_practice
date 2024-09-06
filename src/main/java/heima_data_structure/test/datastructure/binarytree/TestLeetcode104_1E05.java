package heima_data_structure.test.datastructure.binarytree;

import heima_data_structure.java.src.main.java.com.itheima.datastructure.binarytree.Leetcode_104_1_E05;
import heima_data_structure.java.src.main.java.com.itheima.datastructure.binarytree.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLeetcode104_1E05 {

    private final Leetcode_104_1_E05 e05 = new Leetcode_104_1_E05();

    @Test
    public void test1() {
        TreeNode root = new TreeNode(new TreeNode(2), 1, new TreeNode(3));
        assertEquals(2, e05.maxDepth(root));
    }

    @Test
    public void test2() {
        TreeNode root = new TreeNode(new TreeNode(2), 1, new TreeNode(null, 3, new TreeNode(4)));
        assertEquals(3, e05.maxDepth(root));
    }

    @Test
    public void test3() {
        TreeNode root = new TreeNode(1);
        assertEquals(1, e05.maxDepth(root));
    }
}
