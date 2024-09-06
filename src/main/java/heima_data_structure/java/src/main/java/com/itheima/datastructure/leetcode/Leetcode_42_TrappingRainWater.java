package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.util.LinkedList;

/**
 * <h3>接雨水 - 单调栈</h3>
 */
public class Leetcode_42_TrappingRainWater {
    public static void main(String[] args) {
        System.out.println(trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1})); // 6
        System.out.println(trap(new int[]{4, 2, 0, 3, 2, 5})); // 9
    }

    static int trap(int[] heights) {
        LinkedList<Data> stack = new LinkedList<>();
        int sum = 0;
        for (int i = 0; i < heights.length; i++) {
            Data right = new Data(heights[i], i);

            while (!stack.isEmpty()
                    && stack.peek().height < right.height) {
                Data pop = stack.pop();
                Data left = stack.peek();
                if (left != null) { // 计算水的容量
                    int width = right.i - left.i - 1;
                    int height = Math.min(left.height, right.height) - pop.height;
                    sum += width * height;
                }
            }

            stack.push(right);
//            System.out.println(stack);
        }
        return sum;
    }

    static class Data{
        int height;
        int i;  // 索引

        public Data(int height, int i) {
            this.height = height;
            this.i = i;
        }

        @Override
        public String toString() {
            return String.valueOf(height);
        }
    }
}
