package heima_data_structure.test.algorithm.sort;

import heima_data_structure.java.src.main.java.com.itheima.algorithm.sort.Code_1122_E01;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TestCode1122E01 {

    private Code_1122_E01 leetcode = new Code_1122_E01();

    @Test
    public void test1() {
        int[] arr1 = {28, 6, 22, 8, 44, 17};
        int[] arr2 = {22, 28, 8, 6};
        int[] result = {22, 28, 8, 6, 17, 44};
        assertArrayEquals(result, leetcode.relativeSortArray(arr1, arr2));
    }

    @Test
    public void test2() {
        int[] arr1 = {2, 3, 1, 3, 2, 4, 6, 7, 9, 2, 19};
        int[] arr2 = {2, 1, 4, 3, 9, 6};
        int[] result = {2, 2, 2, 1, 4, 3, 3, 9, 6, 7, 19};
        assertArrayEquals(result, leetcode.relativeSortArray(arr1, arr2));
    }
}
