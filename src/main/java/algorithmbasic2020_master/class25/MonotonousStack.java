package algorithmbasic2020_master.class25;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 *TODO
 * 单调栈中的元素要求从栈底到栈顶单调递增
 * 遍历数组，如果元素入栈后符合单调要求则顺利入栈
 * 不符合要求则弹出栈顶元素，元素出栈时得出结果
 * 右侧结果:待入栈元素
 * 左侧结果:出栈后的栈顶元素
 * 出栈元素右侧的最小值 就是目前待入栈的元素 ，左侧的最小值 就是他出栈之后的栈顶元素
 * eg:
 * arr=[3,6,4,7,5,8,0]
 * 0 -》
 * 有一个空栈
 * 1.遍历到0位置
 * 栈： 0 -> 3
 * 2.遍历到1位置
 * 栈： 0 -> 3 , 1 -> 6
 * 3.遍历到2位置  因为 4 < 6  需要让 1 -> 6弹出
 * 1 -> 6 的左侧最近小于元素 0 -> 3,右侧最近小于元素 2 -> 4
 * 栈： 0 -> 3 , 2 -> 4
 * 4.遍历到3位置
 * 栈： 0 -> 3 , 2 -> 4  ,3 -> 7
 * 5.遍历到4位置  因为 5 < 7  需要让 3 -> 7弹出
 * 3 -> 7 的左侧最近小于元素 2 -> 4,右侧最近小于元素 4 -> 5
 * 栈： 0 -> 3 , 2 -> 4 ,  4 -> 5
 * 6.遍历到5位置
 * 栈： 0 -> 3 , 2 -> 4  , 4 -> 5 , 5 -> 8
 * 7.遍历到6位置  因为 0 < 8  需要让 5 -> 8弹出
 * 5 -> 8 的左侧最近小于元素 4 -> 5,右侧最近小于元素 6 -> 0
 * 因为 0 < 5 需要让 4 -> 5弹出
 * 4 -> 5 的左侧最近小于元素 2 -> 4,右侧最近小于元素 6 -> 0
 * 因为 0 < 4 需要让 2 -> 4 弹出
 * 2 -> 4 的左侧最近小于元素 0 -> 3,右侧最近小于元素 6 -> 0
 * 因为 0 < 3 需要让 2 -> 4 弹出
 * 0 -> 3 的左侧最近小于元素 -1 -> -1,右侧最近小于元素 6 -> 0
 * 栈： 6 -> 0
 */
public class MonotonousStack {
    public static void main(String[] args) {
        int[] arr = {3, 4, 1, 5, 6, 2, 7};
        int[][] resultByArray = rightWay(arr);
        int[][] resultByStack = getNearLessNoRepeat(arr);
        //遍历二维数组
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < resultByArray[i].length; j++) {
                System.out.print(resultByArray[i][j] + "  ");
            }
            System.out.println("  ");
        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < resultByStack[i].length; j++) {
                System.out.print(resultByStack[i][j] + "  ");
            }
            System.out.println("  ");
        }

    }

    public static int[][] rightWay(int[] arr) {
        //定义结果集
        int[][] res = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            //定义无法找到符合条件时间left和right下标
            int leftLessIndex = -1;
            int rightLessIndex = -1;
            //开始求复合条件的left
            int cur = i - 1;
            while (cur >= 0) {
                if (arr[cur] < arr[i]) {
                    leftLessIndex = cur;
                    break;
                }
                cur--;
            }
            //开始求复合条件的right
            cur = i + 1;
            while (cur < arr.length) {
                if (arr[cur] < arr[i]) {
                    rightLessIndex = cur;
                    break;
                }
                cur++;
            }
            res[i][0] = leftLessIndex;
            res[i][1] = rightLessIndex;
        }
        return res;
    }

    public static int[][] getNearLessNoRepeat(int[] arr) {
        //定义一个栈
        Stack<Integer> stack = new Stack<>();
        //定义结果集
        int[][] res = new int[arr.length][2];
        //开始遍历
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                int popIndex = stack.pop();
                //判断stack
                int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
                res[popIndex][0] = leftLessIndex;
                res[popIndex][1] = i;
            }
            stack.push(i);
        }
        //当执行完循环，此时i为数组最后一位
        while (!stack.isEmpty()) {
            int popIndex = stack.pop();
            int leftIndex = stack.isEmpty() ? -1 : stack.peek();
            res[popIndex][0] = leftIndex;
            res[popIndex][1] = -1;
        }
        return res;
    }

    /**
     * 单调栈
     * 典型例题：给一个数组，返回一个相同长度的数组，值为当前位置对应的下一个最大元素（值或索引）
     * 没有更大的则赋值-1
     * <p>
     * 扩展：如果是环状数组则将原始数组翻倍进行处理。
     */
    public static int[] monotonousStack(int[] nums) {
        int[] res = new int[nums.length];
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = nums.length - 1; i >= 0; i--) {
            //永远保持栈顶是最小的，因此入栈前将比这个数小的都移出去
            while (!q.isEmpty() && q.peek() <= nums[i]) {
                q.pop();
            }
            res[i] = q.isEmpty() ? -1 : q.peek();
            //如果是返回更大的位置到当前位置的距离则用下面这句
//            res[i] = q.isEmpty()?-1:(q.peek()-i);
            q.push(nums[i]);
        }
        return res;
    }

    /**
     * 单调栈
     * 典型例题：一横排高楼，返回一个新数组，在当前高楼能左右看到的楼数。（不算本身这座楼）
     * （当前面的楼的高度大于等于后面的楼时，后面的楼将被挡住）
     * 输入[5,3,8,3,2,5]，输出[2,2,4,3,3,3]
     */
    public static int[] findBuilding(int[] heights) {
        int[] res = new int[heights.length];
        Deque<Integer> q1 = new ArrayDeque<>();
        //从右向左看
        for (int i = heights.length - 1; i > 0; i--) {
            while (!q1.isEmpty() && q1.peek() <= heights[i]) {//栈顶最小
                q1.pop();
            }
            q1.push(heights[i]);
            res[i - 1] += q1.size();
        }
        Deque<Integer> q2 = new ArrayDeque<>();
        //从左向右看
        for (int i = 0; i < heights.length - 1; i++) {
            while (!q2.isEmpty() && q2.peek() <= heights[i]) {
                q2.pop();
            }
            q2.push(heights[i]);
            res[i + 1] += q2.size();
        }
        return res;
    }
}
