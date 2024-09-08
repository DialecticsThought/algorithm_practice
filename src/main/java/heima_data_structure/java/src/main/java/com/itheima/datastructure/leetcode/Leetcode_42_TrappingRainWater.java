package heima_data_structure.java.src.main.java.com.itheima.datastructure.leetcode;

import java.util.LinkedList;

/**
 * <h3>接雨水 - 单调栈</h3>
 *
 * <pre>
 * 1. 维护一个单调栈 从大到小
 * 2. 当加入一个新元素时，如果发现需要弹出元素
 *    表示遇到了一个凹陷的位置
 *    此时应该计算雨水容量
 * eg：
 * 1.一开始加入1个方块
 *  ██
 * 单调栈 :1
 * 2.接下来什么都不加
 *  ██ __
 * 单调栈 :1 -> 0
 * 2.加入2个方块
 *        ██
 *  ██ __ ██
 * 这2个柱子分别称为left right
 * 其索引位置分别是 i和j
 * 高度是 math.min(left,right) 宽度是 j - i - 1
 * eg:
 *   Y轴
 *   ↑
 * 3 |                      ██
 * 2 |          ██          ██ ██    ██
 * 1 | __ ██ __ ██ ██ __ ██ ██ ██ ██ ██ ██
 *   |----------------------------------> X轴
 *
 * 1.索引 0 (heights[0] = 0):
 * 将 (0, 0) 入栈。
 * 栈状态：[0]
 * 索引 1 (heights[1] = 1):
 * 当前高度为1，大于栈顶高度0。
 * 出栈 (0, 0)，栈变为空，无左边界，不计积水。
 * 将 (1, 1) 入栈。
 * 栈状态：[1]
 * 2.索引 2 (heights[2] = 0):
 * 当前高度为0，低于栈顶高度1。
 * 将 (0, 2) 入栈。
 * 栈状态：[1, 0]
 * 3.索引 3 (heights[3] = 2):
 * 当前高度为2，大于栈顶高度0。
 * 出栈 (0, 2)。
 * 查看新栈顶 (1, 1)，计算积水：
 * 宽度 = 3 - 1 - 1 = 1
 * 高度 = min(1, 2) - 0 = 1
 * 积水量 = 1 * 1 = 1
 * 累加积水到 sum，sum = 1。
 * 当前高度依然大于新栈顶高度1，继续出栈 (1, 1)，栈为空 ，无左边界，不计积水。
 * 将 (2, 3) 入栈。
 * 栈状态：[2]
 * 5.索引 4 (heights[4] = 1):
 * 当前高度为1，低于栈顶高度2。
 * 将 (1, 4) 入栈。
 * 栈状态：[2, 1]
 * 6.索引 5 (heights[5] = 0):
 * 当前高度为0，低于栈顶高度1。
 * 将 (0, 5) 入栈。
 * 栈状态：[2, 1, 0]
 * 7.索引 6 (heights[6] = 1):
 * 当前高度为1，高于栈顶高度0。
 * 出栈 (0, 5)。
 * 查看新栈顶 Data(1, 4)，高度为1，等于当前高度，没有形成凹槽，因此不计积水。
 * 将 (1, 6) 入栈。
 * 栈状态：[2, 1, 1]
 * 8.索引 7 (heights[7] = 3):
 * 当前高度为3，大于栈顶高度1。
 * 出栈 (1, 6) 查看 新栈顶 (1, 4)，由于高度相同，不形成凹槽，不计积水 (原先的栈顶和次栈顶相同)
 * 出栈 (1, 4)，查看 新栈顶 (2, 3)，计算积水：
 * 宽度 = 7 - 3 - 1 = 3
 * 高度 = min(2, 3) - 1 = 1
 * 积水量 = 3 * 1 = 3
 * 累加积水到 sum，sum = 4。
 * 当前高度依然大于新栈顶高度2，继续出栈 (2, 3)，栈为空 ，无左边界，不计积水。
 * 将 (3, 7) 入栈。
 * 栈状态：[3]
 * 9.索引 8 (heights[8] = 2):
 * 当前高度为2，低于栈顶高度3。
 * 将 (2, 8) 入栈。
 * 栈状态：[3, 2]
 * 10.索引 9 (heights[9] = 1):
 * 当前高度为1，低于栈顶高度2。
 * 将 (1, 9) 入栈。
 * 栈状态：[3, 2, 1]
 * 11.索引 10 (heights[10] = 2):
 * 当前高度为2，大于栈顶高度1。
 * 出栈 (1, 9)。
 * 查看新栈顶 (2, 8)，计算积水：
 * 宽度 = 10 - 8 - 1 = 1。
 * 高度 = min(2, 2) - 1 = 1。
 * 积水量 = 1 * 1 = 1。
 * 累加积水到 sum。
 * 将 (2, 10) 入栈。
 * 栈状态：[3, 2, 2]
 * 11.索引 11 (heights[11] = 1):
 * 当前高度为1，低于栈顶高度2。
 * 将 (1, 11) 入栈。
 * 栈状态：[3, 2, 2, 1]
 * </pre>
 */
public class Leetcode_42_TrappingRainWater {
    public static void main(String[] args) {
        System.out.println(trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1})); // 6
        System.out.println(trap(new int[]{4, 2, 0, 3, 2, 5})); // 9
    }

    static int trap(int[] heights) {
        LinkedList<Data> stack = new LinkedList<>();
        int sum = 0;
        // 遍历元素
        for (int i = 0; i < heights.length; i++) {
            // 把当前高度和对应的索引 封装成data
            Data right = new Data(heights[i], i);
            // 如果栈顶不为空 并且 栈顶的高度 <= 当前的高度 => 那么不断弹出栈顶 然后 计算次栈顶和当前元素right
            while (!stack.isEmpty()
                    && stack.peek().height < right.height) {
                // 弹栈
                Data pop = stack.pop();
                // 查看 原先次栈顶 或者说 新的栈顶
                Data left = stack.peek();
                if (left != null) { // 计算水的容量
                    int width = right.i - left.i - 1;
                    /**
                     * <pre>
                     *         ██
                     *   ██    ██
                     *   ██ ██ ██
                     *  math.min(2 , 3) - 1 = 1
                     * </pre>
                     */
                    int height = Math.min(left.height, right.height) - pop.height;
                    sum += width * height;
                }
            }

            stack.push(right);
        }
        return sum;
    }

    static class Data {
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
