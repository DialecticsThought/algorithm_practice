import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author veritas
 * @Data 2025/3/3 8:50
 */
public class LeetCode_54_SpiralMatrix {

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> order = new ArrayList<Integer>();
        int rows = matrix.length;
        int cols = matrix[0].length;
        int total = rows * cols;
        // 重新初始化
        int row = 0, col = 0;
        // 定义一个方向数组
        // 右 下 左 上
        // {0, 1} 行保持不变，列加1——相当于向右走
        // {1, 0}：行加1，列保持不变——相当于向下走。
        // {0, -1}：行保持不变，列减1——相当于向左走。
        // {-1, 0}：行减1，列保持不变——相当于向上走。
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        // 初始方向是右边
        int directionIndex = 0;
        // 访问标记
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        // 当路径的长度达到矩阵中的元素数量时即为完整路径，将该路径返回
        for (int i = 0; i < total; i++) {
            // 添加遍历到的数组单元格
            order.add(matrix[row][col]);
            visited[row][col] = true;
            // 这两个变量 提前判断下一个位置是否越界或已经被访问
            int nextRow = row + directions[directionIndex][0];
            int nextCol = col + directions[directionIndex][1];
            // 如果下一个要遍历的单元格 超出遍历的范围
            if (nextRow < 0 || nextRow >= rows
                    || nextCol < 0 || nextCol >= cols
                    || visited[nextRow][nextCol]) {// 或者下一个遍历的单元格已经之前遍历过了
                // ( directionIndex + 1 ) % 4 保证在 0,1,2,3 之间循环
                // 当前是 右 那么接下来是 下
                // 当前是 下 那么接下来是 左
                // ...
                directionIndex = (directionIndex + 1) % 4;
            }
            // 判断完之后 真正的下一个单元格的行和列
            row = row + directions[directionIndex][0];
            col = col + directions[directionIndex][1];
        }
        return order;
    }
}
