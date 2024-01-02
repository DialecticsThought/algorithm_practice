package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * <h3>单调栈 从栈底到栈顶递减</h3>
 */
public class MonotonicStack {

    private final LinkedList<Integer> stack = new LinkedList<>();

    public void push(int value) {
        while (!stack.isEmpty() && stack.peek() < value) {
            stack.pop();
        }
        stack.push(value);
    }

    @Override
    public String toString() {
        ArrayList<Integer> reverse = new ArrayList<>(stack);
        Collections.reverse(reverse);
        return reverse.toString();
    }

    public static void main(String[] args) {
        MonotonicStack stack = new MonotonicStack();
        stack.push(3);
        stack.push(2);
        stack.push(1);
        stack.push(2);
        System.out.println(stack);
    }
}
