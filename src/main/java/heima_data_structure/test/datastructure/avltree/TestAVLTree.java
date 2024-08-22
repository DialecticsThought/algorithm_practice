package heima_data_structure.test.datastructure.avltree;

import heima_data_structure.java.src.main.java.com.itheima.datastructure.avltree.AVLTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAVLTree {
    @Test
    public void testRemoveWithoutBalance() {
        AVLTree tree = new AVLTree();
        tree.put(1, "A");
        tree.put(2, "B");
        tree.put(3, "C");
        tree.put(4, "D");
        tree.put(5, "E");
        tree.put(6, "F");
        tree.put(7, "G");
        tree.put(8, "H");
        tree.put(9, "I");
        tree.put(10, "J");
        tree.remove(5);
        assertEquals("F", tree.root.right.left.value);
    }
}
