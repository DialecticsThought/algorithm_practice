package code_for_great_offer.class18;

/**
 * 本题测试链接 : https://leetcode.cn/problems/shortest-bridge/
 * 1  1  0  0
 * 0  0  1  1
 * 把这个arr变成2个单独的岛，分别利用bfs的思想从岛向外广播
 * 每一次循环就是把直接打到的为0的点变成1
 * 岛1               岛2
 * 1  1  0  0       0  0  0  0
 * 0  0  0  0       0  0  1  1
 * 第1次bfs
 * 岛1               岛2
 * 1  1  2  0       0  0  2  2
 * 2  2  0  0       0  2  1  1
 * 1表示岛屿到自身的距离为1,2表示该点到岛屿的距离为2
 * 第2次bfs
 * 岛1               岛2
 * 1  1  2  3       0  3  2  2
 * 2  2  3  0       3  2  1  1
 * 1表示岛屿到自身的距离为1,2表示该点到岛屿的距离为2,3表示该点到岛屿的距离为3
 * 第3次bfs
 * 岛1               岛2
 * 1  1  2  3       4  3  2  2
 * 2  2  3  4       3  2  1  1
 * 1表示岛屿到自身的距离为1,2表示该点到岛屿的距离为2,3表示该点到岛屿的距离为3,4表示该点到岛屿的距离为4
 * 把上面的矩阵相加 除了值为1的部分 因为是岛屿
 * 1  1  4  5
 * 5  4  1  1
 * 回到原始的岛屿arr
 * 1  1  0  0
 * 0  0  1  1
 * 对于arr[1][1]而言 距离是岛1到arr[1][1]的点的距离+岛2到arr[1][1]的点的距离-3
 * -3是一个经验，因为算重复了
 * eg:
 * 0  1  2  3
 * 0 1  0  0  0
 * 1 1  1  1  0
 * 2 0  0  0  0
 * curs = [0,4,5,6]
 * 0 1 2 3
 * record=[1,0,0,0,1,1,1,0,0,0,0,0]
 * 0 1 2 3 4 5 6 7 8 9 10 11
 * 接下来求 nexts[]
 * 本质就是把二维arr中与1直接有关的0变成V（下面定义的变量）
 * 但是我们使用curs[]和record[]去做而已
 * 检查curs[]=0本质对应arr[0][0]的上下左右(这里没有左，没有上)
 * 发现他的下是1他的右是0，把它的右变成V
 * 那么 nexts[0] = 1 表示arr[0][1]被修改了，record[1]=2  索引1就是nexts[0]=1
 * 检查curs[1]=4本质对应arr[1][0]的上下左右(没有左，上遍历过)
 * 发现他的下是0，他的右是1，把他的下变成V
 * 那么nexts[1]=8 表示arr[2[0]被修改了 records[8]=2
 * 检查curs[5]=5本质对应arr1[][1]的上下左右(左侧是1，不管，上册被修改过 根据record)
 * 发现他的下是0 他的右是1，把他的下变成V
 * 那么nexts[2]=9，表示arr[2][1]被修改了 records[9]=2
 * 检查curs[3]=6 本质对应arr[1][0]的上下左右(上没有被修改)
 * 发现他的上是0，他的左是1，把他的上变成V
 * 那么nexts[3]=2，表示arr[2][0]被修改了 records[2]=2
 * 发现他的下是，把他的下变成V
 * 那么nexts[4]=10，表示arr[2][2]被修改了 records[10]=2
 * 发现他的右是0，把他的右变成V
 * 那么nexts[5]=7，表示arr[2][3]被修改了 records[7]=2
 * 此时 record[]=[1,2,2,0,1,1,1,2,2,2,2,0] 此时就差3和11位置没有被修改
 */
public class leetCode_934_ShortestBridge {

    public static int shortestBridge(int[][] m) {
        int N = m.length;
        int M = m[0].length;
        // 一共多少个点
        int all = N * M;
        int island = 0;//岛的数量初始为0
        // 队列
        int[] curs = new int[all];
        // bfs用 ，一个节点的相邻节点 会放入nexts
        int[] nexts = new int[all];
        //一个岛一片1 一共2个岛
        int[][] records = new int[2][all];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (m[i][j] == 1) { // 每次一遇到1的时候 一定是遇到了新的岛
                    /**
                     * 当前位置发现了1！
                     * 把这一片的1，都变成2，同时，抓上来了，这一片1组成的初始队列
                     * curs, 把这一片的1到自己的距离，都设置成1了，records
                     * 也就是说把一片岛每一个点的位置放入curs
                     */
                    int queueSize = infect(m, i, j, N, M, curs, 0, records[island]);
                    //当前 还属于第一层
                    int V = 1;
                    //接下来广播 也就是bfs
                    while (queueSize != 0) {//只要队列有东西，就不断广播
                        // 也就是说当前层推出下一层，然后再交换
                        V++;
                        // 宽度优先curs里面的点，上下左右4个方向，找那些只针对records[点]==0的点， 放入nexts
                        queueSize = bfs(N, M, all, V, curs, queueSize, nexts, records[island]);
                        /**
                         * 不断交替 节省空间
                         * 假设树的bfs，一层层的遍历
                         * 然后遍历甲层 -> 乙层 -> 丙层
                         * 一开始A 引用甲队列
                         * 然后B 因为乙队列
                         * 接下来 C 引用丙队列
                         * ....
                         */
                        int[] tmp = curs;
                        curs = nexts;
                        nexts = tmp;
                    }
                    island++;
                }
            }
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < all; i++) {
            min = Math.min(min, records[0][i] + records[1][i]);
        }
        return min - 3;
    }

    /**
     * TODO
     *  当前来到m[i][j] , 总行数是N，总列数是M
     *   m[i][j]感染出去(找到这一片岛所有的1),把每一个1的坐标，放入到int[] curs队列！
     *   这个curs队列就是收集所有的1，
     *   1 (a,b) -> curs[index++] = (a * M + b)
     *   1 (c,d) -> curs[index++] = (c * M + d)
     *   二维已经变成一维了， eg：有一个1 位置是(a,b) -> a * M + b，这是一维坐标，或者说是一维数组的下标
     *   设置距离record[a * M +b ] = 1 原本二维的 record[a][b]=1
     *   这个方法返回的是队列的长度，也就是收集到多少个1
     * TODO
     * 这个递归的index是公用一个的 因为求的是
     * TODO
     * 除此之外2维arr简化为1维arr
     * eg:一个arr[3][7]
     * 第0行的数组对于1维arr的0~7个格子
     * 第1行的数组对于1维arr的8~15个格子
     * 公式: 二维(i,j) -> i * 列数 + j
     * 下面是原始的2维arr 记作arr
     * 1 1 0
     * 1 0 0
     * .....
     * 有一个curs[] 记录 [0,1,3.....]  也就是把1的2维坐标转成1维的坐标 放入curs
     * curs[0]=0 表示arr[0][0]=1,curs[1]=1表示cur[0][1]=1 最后curs.len就说明整个arr[][] 有多少个1
     * 有一个record[] 记录 [1,1,0,1....]
     * record下标代表2维arr的位置 对应的值是1表示2维arr的位置上是1的部分
     * TODO
     * 1个节点curs上是curs[a] 该节点对应的二维节点的左侧节点a-1 右侧节点是a+1,上侧节点a-M 下侧节点就是a+M
     */
    public static int infect(int[][] m, int i, int j, int N, int M, int[] curs, int index, int[] record) {
        /**
         * 如果当前越界 or 当前位置不是1，直接返回长度
         */
        if (i < 0 || i == N || j < 0 || j == M || m[i][j] != 1) {
            return index;
        }
        // m[i][j] 不越界，且m[i][j] == 1
        // 防止其他的递归也把这个数算进去  一旦算入就变成2 防止重复计算
        m[i][j] = 2;
        // 这是二维数组中的位置变成一维数组的位置
        int p = i * M + j;
        // 记录下来
        record[p] = 1;
        // 把当前的位置一维化后，放入队列
        curs[index] = p;
        // 收集到不同的1
        // 每个矩阵的每个格子都有上下左右4个方向
        index++;
        // 向下
        index = infect(m, i - 1, j, N, M, curs, index, record);
        // 向上
        index = infect(m, i + 1, j, N, M, curs, index, record);
        // 向左
        index = infect(m, i, j - 1, N, M, curs, index, record);
        // 向右
        index = infect(m, i, j + 1, N, M, curs, index, record);
        // 最后的返回值 就是队列的长度
        return index;
    }

    /**
     * TODO 这个函数就是通过bfs 把广播的功能实现出来
     * 二维原始矩阵中，N总行数，M总列数
     * all 总 all = N * M
     * V 要生成的是第几层 curs 的是 V-1 nexts 的是V 也就是要求的
     * record里面拿距离
     *
     * @param N
     * @param M
     * @param all
     * @param V
     * @param curs
     * @param size
     * @param nexts
     * @param record
     * @return
     */
    public static int bfs(int N, int M, int all, int V,
                          int[] curs, int size, int[] nexts, int[] record) {
        // 我要生成的下一层队列成长到哪了？
        int nexti = 0;
        for (int i = 0; i < size; i++) {
            // curs[i] -> 一个位置
            /**
             * curs[i]代表的是原先的二维数组位置的一维形式
             * 一维形式i % M = 0 表示 当前节点 的格子在二维中是最右侧或者最左侧
             * 一维形式i % M = M-1 表示 当前节点 的格子在二维中是最右侧或者最左侧
             * 一维形式i / M =0 说明二维中是最上面的一行的节点
             * 一维形式i / M =N-1 说明二维中是最下面的一行的节点
             * 上面的检查做完之后 就知道
             * 对于 curs[i] 想求 它二维形式的左侧个格子 就是得到curs[i] - 1;
             * 对于 curs[i] 想求 它二维形式的右侧个格子就是得到curs[i] + 1;
             * 同理 curs[i] 想求 它二维形式的上下个格子
             * */
            int up = curs[i] < M ? -1 : curs[i] - M;
            int down = curs[i] + M >= all ? -1 : curs[i] + M;

            int left = curs[i] % M == 0 ? -1 : curs[i] - 1;
            int right = curs[i] % M == M - 1 ? -1 : curs[i] + 1;
            // record[up] == 0表示从来没有到过这个节点  或者说没有统计过这个距离
            if (up != -1 && record[up] == 0) {//表示上方有位置 并没有来过
                record[up] = V;
                //把统计到的位置 记录下来
                nexts[nexti++] = up;
            }
            if (down != -1 && record[down] == 0) {//表示下方有位置 并没有来过
                record[down] = V;
                nexts[nexti++] = down;
            }
            if (left != -1 && record[left] == 0) {//表示左方有位置 并没有来过
                record[left] = V;
                nexts[nexti++] = left;
            }
            if (right != -1 && record[right] == 0) {//表示右方有位置 并没有来过
                record[right] = V;
                nexts[nexti++] = right;
            }
        }
        return nexti;
    }

}
