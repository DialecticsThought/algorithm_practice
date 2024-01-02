package other.进阶班二.第二章;

/**
 * @author zbl
 * @version 1.0
 * @content:给定一个整数矩阵matrix，每个位置你可以向左、右、下、上移动，找到其中最长的递增路径。
例如：
matrix = [
[9,9,4],
[6,6,8],
[2,1,1]
]
返回4
最长路径是[1, 2, 6, 9].
matrix = [
[3,4,5],
[3,2,6],
[2,2,1]
]
返回4
最长路径是[1, 2, 6, 9].
 * @date 2020/2/15 18:37
 */
public class Problem05_Longest_Increasing_Path_in_a_Matrix {

    //递归的方式
    public static int longest(int[][] matrix){
        int answer=Integer.MIN_VALUE;
        //有可能从任何一个位置出发 那就每一个都试一遍
        for(int i=0;i<matrix.length;i++) {
            for(int j=0;j<matrix[0].length;j++)
                //任何一个row行 col列的位置都出发一遍
                answer=Math.max(answer,process(matrix,i,j));
        }
        return answer;
    }
    //i,j表示从matrix的i，j位置出发，返回从i,j位置出发的最长路径长度
    private static int process(int[][] matrix, int row, int col) {
        /*
        * 因为每个位置都可以走 左右上下四个方向
        * 那就尝试四个方向
        * */
        int answer=1;//原地不动 什么方向都没走的长度
        //判断是否可以往左走 可以的话 就尝试
        if(col>0 && matrix[row][col-1]>matrix[row][col]){
            //在能向左走的情况下 后续长度的最长量是多少 就调用递归
            answer=Math.max(answer,process(matrix,row,col-1)+1);
        }
        //判断是否可以往右走 可以的话 就尝试
        if(col<matrix[0].length-1 && matrix[row][col]<matrix[row][col+1]){
            //在能向右走的情况下 后续长度的最长量是多少 就调用递归
            answer=Math.max(answer,process(matrix,row,col+1)+1);
        }

        //判断是否可以往上走 可以的话 就尝试
        if(row>0 && matrix[row-1][col]>matrix[row][col]){
            //在能向右走的情况下 后续长度的最长量是多少 就调用递归
            answer=Math.max(answer,process(matrix,row-1,col)+1);
        }

        //判断是否可以往下走 可以的话 就尝试
        if(row<matrix.length-1 && matrix[row+1][col]>matrix[row][col]){
            //在能向下走的情况下 后续长度的最长量是多少 就调用递归
            answer=Math.max(answer,process(matrix,row+1,col)+1);
        }
        return answer;
    }

    //i,j表示从matrix的i，j位置出发，返回从i,j位置出发的最长路径长度
    private static int process2(int[][] matrix, int row, int col) {
        /*
         * 因为每个位置都可以走 左右上下四个方向
         * 那就尝试四个方向
         *
         * 这个暴力递归的终止条件就是四个if条件你都不中的时候
         * */
        int answer=1;//原地不动 什么方向都没走的长度
        int nextLeft=0;
        //判断是否可以往左走 可以的话 就尝试
        if(col>0 && matrix[row][col-1]>matrix[row][col]){
            //在能向左走的情况下 后续长度的最长量是多少 就调用递归
            nextLeft=process2(matrix,row,col-1);
        }
        int nextRight=0;
        //判断是否可以往右走 可以的话 就尝试
        if(col<matrix[0].length-1 && matrix[row][col]<matrix[row][col+1]){
            //在能向右走的情况下 后续长度的最长量是多少 就调用递归
            nextRight=process2(matrix,row,col+1);
        }
        int nextUp=0;
        //判断是否可以往上走 可以的话 就尝试
        if(row>0 && matrix[row-1][col]>matrix[row][col]){
            //在能向右走的情况下 后续长度的最长量是多少 就调用递归
            nextUp=process2(matrix,row-1,col);
        }
        int nextDown=0;
        //判断是否可以往下走 可以的话 就尝试
        if(row<matrix.length-1 && matrix[row+1][col]>matrix[row][col]){
            //在能向下走的情况下 后续长度的最长量是多少 就调用递归
            nextDown=process2(matrix,row+1,col);
        }
        answer = Math.max(Math.max(nextLeft,nextRight),Math.max(nextDown,nextUp))+answer;
        return answer;
    }
    /*
    * 改动态规划
    * 但是位置依赖 这件事不是很明显 所以只能用缓存版本的dp表
    * eg: 某一个点e 上下左右 点 分别是 a b c d , a b c d都大于e, e才能有四个方向走
    *  假设 e向上走 走到a 那么a就要判断a的上下左右四个点。。。。。。。
     * */
    public static int longestDP(int[][] matrix){
        int max=Integer.MIN_VALUE;
        int[][]dp=new int[matrix.length][matrix.length];
        for(int i=0;i<matrix.length;i++)
        {
            for(int j=0;j<matrix[0].length;j++)
                max=Math.max(max,processDP(matrix,dp,i,j));
        }
        return max;
    }

    //i,j表示从matrix的i，j位置出发，返回从i,j位置出发的最长路径长度
    private static int processDP(int[][] matrix, int[][] dp,int i, int j) {

        if(dp[i][j]==0) { //表示从来没有得到过该位置的值，需要进行计算，如果！=0说明前面已经计算过了，直接赋值即可，相当于加了个缓存。
            dp[i][j] = 1;//原地不动的长度
            //判断是否可以往左走
            if (j > 0 && matrix[i][j - 1] > matrix[i][j]) {
                dp[i][j] = Math.max(dp[i][j], processDP(matrix, dp, i, j - 1) + 1);
            }
            //判断是否可以往右走
            if (j < matrix[0].length - 1 && matrix[i][j] < matrix[i][j + 1]) {
                dp[i][j] = Math.max(dp[i][j], processDP(matrix, dp, i, j +1) + 1);
            }

            //判断是否可以往上走
            if (i > 0 && matrix[i - 1][j] > matrix[i][j]) {
                dp[i][j] = Math.max(dp[i][j], processDP(matrix, dp, i-1, j ) + 1);
            }

            //判断是否可以往下走
            if (i < matrix.length - 1 && matrix[i + 1][j] > matrix[i][j]) {
                dp[i][j] = Math.max(dp[i][j], processDP(matrix, dp, i+1, j ) + 1);
            }
        }
        return dp[i][j];
    }

    //i,j表示从matrix的i，j位置出发，返回从i,j位置出发的最长路径长度
    private static int processDP2(int[][] matrix, int row, int col,int[][] dp) {
        //表示从来没有得到过该位置的值，需要进行计算，如果！=0说明前面已经计算过了，直接赋值即可，相当于加了个缓存。
        if(dp[row][col]!=0){
            return dp[row][col];
        }
        /*
         * 因为每个位置都可以走 左右上下四个方向
         * 那就尝试四个方向
         *
         * 这个暴力递归的终止条件就是四个if条件你都不中的时候
         * */
        int answer=1;//原地不动 什么方向都没走的长度
        int nextLeft=0;
        //判断是否可以往左走 可以的话 就尝试
        if(col>0 && matrix[row][col-1]>matrix[row][col]){
            //在能向左走的情况下 后续长度的最长量是多少 就调用递归
            nextLeft=processDP2(matrix,row,col-1,dp);
        }
        int nextRight=0;
        //判断是否可以往右走 可以的话 就尝试
        if(col<matrix[0].length-1 && matrix[row][col]<matrix[row][col+1]){
            //在能向右走的情况下 后续长度的最长量是多少 就调用递归
            nextRight=processDP2(matrix,row,col+1,dp);
        }
        int nextUp=0;
        //判断是否可以往上走 可以的话 就尝试
        if(row>0 && matrix[row-1][col]>matrix[row][col]){
            //在能向右走的情况下 后续长度的最长量是多少 就调用递归
            nextUp=processDP2(matrix,row-1,col,dp);
        }
        int nextDown=0;
        //判断是否可以往下走 可以的话 就尝试
        if(row<matrix.length-1 && matrix[row+1][col]>matrix[row][col]){
            //在能向下走的情况下 后续长度的最长量是多少 就调用递归
            nextDown=processDP2(matrix,row+1,col,dp);
        }
        answer = Math.max(Math.max(nextLeft,nextRight),Math.max(nextDown,nextUp))+answer;
        dp[row][col] = answer;
        return answer;
    }
    public static void main(String[] args) {
        int[][]arr=new int[][]{
                {3,4,5},{3,2,6},{2,2,1}
        };
        System.out.println(longestDP(arr));
        System.out.println(longestDP(arr));
    }

}
