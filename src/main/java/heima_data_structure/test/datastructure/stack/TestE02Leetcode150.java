package heima_data_structure.test.datastructure.stack;

import heima_data_structure.java.src.main.java.com.itheima.datastructure.stack.E02Leetcode150;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestE02Leetcode150 {

    private final E02Leetcode150 e02 = new E02Leetcode150();

    @Test
    public void test1() {
        String[] tokens = {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
        assertEquals(22, e02.evalRPN(tokens));
    }

    @Test
    public void test2() {
        String[] tokens = {"2", "1", "+", "3", "*"};
        assertEquals(9, e02.evalRPN(tokens));
    }

    @Test
    public void test3() {
        String[] tokens = {"2", "1", "-"};
        assertEquals(1, e02.evalRPN(tokens));
    }

}