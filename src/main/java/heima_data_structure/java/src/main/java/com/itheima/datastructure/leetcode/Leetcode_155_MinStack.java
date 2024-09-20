package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.util.LinkedList;

@SuppressWarnings("all")
public class Leetcode_155_MinStack {
    /**
     * <pre>
     * 有两个栈 一个栈存放数据 还有一个栈表示 每一次放入元素的时候 整个栈的最小值
     * eg:  图中 自上而下 依次是栈底到栈顶
     * 一开始放入2：
     *  栈       最小栈
     *  2         2  表示当前栈里面最小的是2
     * 放入3
     *  栈       最小栈
     *  3         2  表示当前栈里面最小的是2   因为最小栈的上一轮栈顶 与放入的元素作比较 小的入最小栈的栈顶
     *  2         2
     * 放入1
     *  栈       最小栈
     *  1         1  表示当前栈里面最小的是1   因为最小栈的上一轮栈顶 与放入的元素作比较 小的入最小栈的栈顶
     *  3         2
     *  2         2
     * 放入5
     *  栈       最小栈
     *  5         1 表示当前栈里面最小的是1   因为最小栈的上一轮栈顶 与放入的元素作比较 小的入最小栈的栈顶
     *  1         1
     *  3         2
     *  2         2
     *
     *  如果是弹栈的话： 最小栈和普通栈一起弹出栈顶即可
     *  </pre>
     */
    static class MinStack {
        LinkedList<Integer> stack = new LinkedList<>();
        LinkedList<Integer> min = new LinkedList<>();

        public MinStack() {
            min.push(Integer.MAX_VALUE);
        }

        public void push(int val) {
            // 普通的栈
            stack.push(val);
            // 最小栈 ，最小栈的栈顶和要放入的元素作比对 ，哪一个小，哪一个放进去
            // 如果最小栈的栈顶和要放入的元素比较之后 最小栈的栈顶更小，重复的地放入
            min.push(Math.min(val, min.peek()));
        }

        public void pop() {
            if (stack.isEmpty()) {
                return;
            }
            stack.pop();
            min.pop();
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return min.peek();
        }
    }

    static class MinStack2 {
        record Data(int val, int min) {

        }
        //TODO 这里把此轮的带放入数据和此轮的最小值 作为一个整体  放入一个栈
        LinkedList<Data> stack = new LinkedList<>();
        public void push(int val) {
            if (stack.isEmpty()) {
                stack.push(new Data(val, val));
            } else {
                stack.push(new Data(val, Math.min(stack.peek().min, val)));
            }
        }

        public void pop() {
            stack.pop();
        }

        public int top() {
            return stack.peek().val;
        }

        public int getMin() {
            return stack.peek().min;
        }
    }

    public static void main(String[] args) {
        MinStack stack2 = new MinStack();
        stack2.push(-2);
        stack2.push(0);
        stack2.push(-3);
        System.out.println(stack2.getMin()); // -3
        stack2.pop();
        System.out.println(stack2.top()); // 0
        System.out.println(stack2.getMin()); // -2
    }
}
