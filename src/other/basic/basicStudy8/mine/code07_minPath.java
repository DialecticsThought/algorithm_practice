package other.basic.basicStudy8.mine;

/**
 * @Author zzz
 * @Date 2021/12/2 14:35
 * @Version 1.0
 */
public class code07_minPath {
    public static int minPath(int[][] metrics, int i, int j){
        if (i == 0 && j == 0){
            return metrics[0][0];
        }
        if (i == 0){
            return minPath(metrics, i, j-1)+metrics[i][j];
        }
        if (j == 0){
            return minPath(metrics,i-1, j)+metrics[i][j];
        }
        return Math.min(minPath(metrics, i, j-1), minPath(metrics, i-1, j))+metrics[i][j];
    }

    public static int minpathDP(int[][] metrics){
        int n1 = metrics.length,n2 = metrics[0].length;
        if (n1 == 0 || n2 == 0){
            return 0;
        }
        int[][] compare = new int[n1][n2];
        compare[0][0] = metrics[0][0];
        for (int i = 1; i < n1; i++) {
            compare[i][0] = compare[i-1][0]+metrics[i][0];
        }
        for (int i = 1; i < n2; i++) {
            compare[0][i] += compare[0][i-1]+metrics[0][i];
        }
        for (int i = 1; i < n1; i++) {
            for (int j = 1; j < n2; j++) {
                compare[i][j] = Math.min(compare[i-1][j],compare[i][j-1])+metrics[i][j];
            }
        }
        return compare[n1-1][n2-1];
    }

    public static void main(String[] args) {
        int[][] test1 = {
                {3,5,2,6},
                {1,4,7,8},
                {2,1,1,1},
                {5,2,2,1}
        };
        System.out.println(minPath(test1, 3, 3));
        System.out.println(minpathDP(test1));
    }
}
