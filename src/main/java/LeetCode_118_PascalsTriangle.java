import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author veritas
 * @Data 2025/3/6 17:21
 */
public class LeetCode_118_PascalsTriangle {
    /**
     * 给定一个非负整数 numRows，生成「杨辉三角」的前 numRows 行。
     * 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
     * eg:
     * numRows = 5
     * [1]
     * [1,1]
     * [1,2,1]
     * [1,3,3,1]
     * [1,4,6,4,1]
     * 每个元素就是上面上面的元素和左上角的元素
     * 如果某个元素 上面没有元素 就是 1
     *
     * @param numRows
     * @return
     */
    public List<List<Integer>> generate(int numRows) {

        List<List<Integer>> res = new ArrayList<List<Integer>>();
        // 先初始化
        for (int i = 0; i < numRows; i++) {
            List<Integer> list = new ArrayList<>();
            list.add(1);
            res.add(list);
        }
        // 外层遍历每一行
        for (int i = 1; i < numRows; i++) {
            // 获取上一层
            List<Integer> lastLevel = res.get(i - 1);
            // 获取当前层 计算的就是当前层
            List<Integer> currentLevel = res.get(i);
            // 第 i 行 有 i 个 元素
            // 内层遍历 每一行的每一个元素
            for (int j = 1; j < i; j++) {
                // 当前层的最后一个元素 不是
                // 当前层的第j个元素 是 上一行的list的索引 j-1 和 j的元素相加
                currentLevel.add(lastLevel.get(j - 1) + lastLevel.get(j));
            }
            currentLevel.add(1);
        }
        return res;
    }

}
