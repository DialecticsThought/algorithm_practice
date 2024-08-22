package code_for_great_offer.class30;

public class Problem_0079_WordSearch {

    public static boolean exist(char[][] board, String word) {
        char[] w = word.toCharArray();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (f(board, w, i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     *TODO
     * 有2个位置指针 一个执行 board数组 一个指向word数组
     * 当前到达了b[i][j]，
     * 从b[i][j]出发，能不能搞定word[k]~word[word.len-1]
     * 返回  true false
     * */
    public static boolean f(char[][] board, char[] w, int i, int j, int k) {
        //base case 如果来到 word的最后位置 直接返回
        if (k == w.length) {
            return true;
        }
        /*
         *TODO  base case
         * word[k]开始到word[word.len-1]还有字符
         * 如果(i,j)越界，返回false
         * */
        if (i < 0 || i == board.length || j < 0 || j == board[0].length) {
            return false;
        }
        /*
         *TODO  base case
         * 因为该函数从b[i][j]出发，能不能搞定word[k]~word[word.len-1]
         * 但是 board[i][j] != w[k] 说明当前位置就不能搞定
         * */
        if (board[i][j] != w[k]) {
            return false;
        }
        //TODO 上面代码执行完 说明 board[i][j] == w[k]

        /*
         *TODO
         * 因为dfs
         * 当前节点board[i][j]有上下左右 四个选择（路径）
         * 如果 当前节点 选择某一个路径来到子节点
         * 为了防止 子节点 的下一个节点 还是当前节点（走回头路）
         * board[i][j] = 0
         * */
        char tmp = board[i][j];
        board[i][j] = 0;
        boolean case1 = f(board, w, i - 1, j, k + 1);
        boolean case2 = f(board, w, i + 1, j, k + 1);
        boolean case3 = f(board, w, i, j - 1, k + 1);
        boolean case4 = f(board, w, i, j + 1, k + 1);
        boolean ans = case1 || case2 || case3 || case4;
        /*
         *TODO
         * 当前节点 返回到其父节点 需要把状态还原到父节点的状态
         * */
        board[i][j] = tmp;
        //
        return ans;
    }

}
