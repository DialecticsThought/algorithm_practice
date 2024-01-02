package code_for_great_offer.class17;

/**
 *TODO
 * 给定一个每一行有序、每一列也有序，整体可能无序的二维数组再给定一个数num,
 * 返回二维数组中有没有num这个数
 * 一个arr[m][n]
 * 套路： 从arr的右上角开始找
 * 每一次向左 or 向下走
 * 向下： 因为 当前来到 arr[x][y],arr[x][y] < 当前要找的数，并且 [0][y]~[x-1][y]都是 < arr[x][y]
 * 向左： 因为 当前来到 arr[x][y],arr[x][y] > 当前要找的数，并且 [x][y+1]~[x][n]都是 > arr[x][y]
 * eg： 有一个矩阵 知道 arr[0][m-1] = 90  arr[0][m] = 120 现在要找100这个数
 *  ..... 90 120
 *  ...........
 *  ...........
 *  最右上角是120 ，并且每一列都有序，说明 矩阵的最右列的数字都是比100大
 *  所以向左走到 arr[0][m-1]=90，因为每一行都有序，说明 矩阵的第0行的数字都是比100小 所以向下走到arr[1][m-1]
 */
public class Code01_FindNumInSortedMatrix {

    public static boolean isContains(int[][] matrix, int K) {
        int row = 0;
        int col = matrix[0].length - 1;
        while (row < matrix.length && col > -1) {
            if (matrix[row][col] == K) {
                return true;
            } else if (matrix[row][col] > K) {
                col--;
            } else {
                row++;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{{0, 1, 2, 3, 4, 5, 6}, // 0
                {10, 12, 13, 15, 16, 17, 18}, // 1
                {23, 24, 25, 26, 27, 28, 29}, // 2
                {44, 45, 46, 47, 48, 49, 50}, // 3
                {65, 66, 67, 68, 69, 70, 71}, // 4
                {96, 97, 98, 99, 100, 111, 122}, // 5
                {166, 176, 186, 187, 190, 195, 200}, // 6
                {233, 243, 321, 341, 356, 370, 380} // 7
        };
        int K = 233;
        System.out.println(isContains(matrix, K));
    }

}
