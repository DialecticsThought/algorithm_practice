package code_for_great_offer.class29;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author veritas
 * @Data 2023/3/19 15:07
 */
public class Problem_0051_NQueens {
    public static void main(String[] args) {
        int n=4;
        int[] record = new int[n];
        ArrayList<Integer> path = new ArrayList<>();
        List<List<Integer>> result =new ArrayList<>();
        dfs(n,0,record,result);
        System.out.println(result);
    }
    /*
     * 一共n行 n列
     * 当前来到第i行
     * record[i]记录第行 皇后放在哪一列
     * List<List<Integer>> result 记录结果
     * */
    public static void dfs(int n, int i, int[] record, List<List<Integer>> result) {
        if (i == n) { //TODO 来到了终止行
            List<Integer> path = new ArrayList<Integer>();
            for (int num : record) {
                path.add(num);
            }
            result.add(path);

            return;
        }
        //TODO  第i行的皇后，放哪一列呢？j列，=> 枚举尝试
        for (int j = 0; j < n; j++) {//TODO 当前行在i行 尝试i行所有的列 也就是j
            /*
             *TODO
             * 表示传入要摆的皇后的当前坐标i行j列 和 record 判断要摆的皇后的当前位置有不有效
             * 不冲突 有效  直接尝试下一行
             * 冲突 尝试下一列
             * 不需要还原现场是因为 如果当前皇后所处的坐标i行j列 不符合要求 直接改动就可以了
             * */
            if (isValid(record, i, j)) {
                record[i] = j;
                //TODO 第i行已经选完了，开始选第i+1行的皇后
                dfs(n, i + 1, record, result);
                record[i] = 0;
            }
        }
        return;
    }

    /*
     *TODO
     *  没有必要加上行的判断
     * 加入两个坐标(a,b) (c,d)
     * 共列的话就是 b == d
     * 共斜线的话就是 |a-c| = |b-d|
     * eg (a,b) =（2,3） (c,d) =（4,5） 共线
     * 那么 |a-c| = |2-4|   |b-d| = |3-5|
     * 只要查看 record[0...i-1] i之后的不需要看 因为还没有记录
     * */
    public static boolean isValid(int[] record, int i, int j) {
        //TODO 0..i-1 遍历0-i-1个皇后
        for (int k = 0; k < i; k++) {//TODO 0-i-1之间的某一行皇后 某一行记名为k
            /*
             *TODO  k行皇后的列就是record[k] 和 j列做对比
             * */
            if (j == record[k] || Math.abs(record[k] - j) == Math.abs(i - k)) {
                return false;
            }
        }
        return true;
    }
}
