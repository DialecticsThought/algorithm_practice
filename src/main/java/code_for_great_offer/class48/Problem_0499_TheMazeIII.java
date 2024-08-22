package code_for_great_offer.class48;

/**
 * 题目：
 * 由空地和墙组成的迷宫中有一个球。球可以向上（u）下（d）左（l）右（r）四个方向滚动，
 * 但在遇到墙壁前不会停止滚动。当球停下时，可以选择下一个方向。迷宫中还有一个洞，当球运动经过洞时，就会掉进洞里。
 * 给定球的起始位置，目的地和迷宫，找出让球以最短距离掉进洞里的路径。
 * 距离的定义是球从起始位置（不包括）到目的地（包括）经过的空地个数。
 * 通过’u’, ‘d’, ‘l’ 和 'r’输出球的移动方向。 由于可能有多条最短路径， 请输出字典序最小的路径。如果球无法进入洞，输出"impossible"。
 * 迷宫由一个0和1的二维数组表示。 1表示墙壁，0表示空地。你可以假定迷宫的边缘都是墙壁。起始位置和目的地的坐标通过行号和列号给出。
 * <p>
 * 输入1:迷宫由以下二维数组表示
 * 0 0 0 0 0
 * 1 1 0 0 1
 * 0 1 0 0 1
 * 0 1 0 0 0
 * 输入2:球的初始位置(rowBall, colBall) = (4，3)
 * 输入3:洞的位置(rowHole, colHole) = (0，1)
 * 输出: "lul"
 * 解析:有两条让球进洞的最短路径。
 * 第一条路径是 左 ->上 ->左，记为"lul".第二条路径是上-→左，记为 ‘ul'.
 * 两条路径都具有最短距离6，但'1'<'u '，故第一条路径字典序更小。因此输出"lul"。
 * <p>
 * 示例2:
 * 输入1:迷宫由以下二维数组表示
 * 0 0 0 0 0
 * 1 1 0 0 1
 * 0 0 0 0 0
 * 0 1 0 0 1
 * 0 1 0 0 0
 * 输入2:球的初始位置( rowBall， colBall)= (4,3)
 * 输入3:洞的位置(rowHole, colHole) = (3,0 )
 * 输出:"impossible"
 *TODO
 * 用bfs去操作
 * eg:
 *   0  1  2  3
 * 0       X
 * 1       ↑
 * 2    X  ☆ →
 * 3    X  ↓
 * 上面例子中 ☆表示起始点  X表示障碍
 * 对于☆而言，有3个方向，那么会生成3个node放入queue
 * [(下,1,2),(上,3,2),(左,2,3)]
 * (下,1,2) 是针对坐标(1,2)而言，如果☆到这个坐标是从下方进入的(1,2)
 * (上,3,2) 是针对坐标(3,2)而言，如果☆到这个坐标是从上方进入的(3,2)
 * <p>
 * bfs防止 同一个节点多次进入queue，这个节点包含坐标+方向
 * 一个点+一个方向 必须不重复
 * eg:
 * | ? F ? ? ? ?
 * | ? ↑ ? ? ? ?
 * | △ □ ← ← ← ☆
 * | ? ↑ ? ? ? ↓
 * | X ← ← ← ← △
 * ☆表示初始  △表示下一个就是撞墙  X表示障碍  ? 表示不关心
 * □ 表示这2个路线有相同的经过位置
 * eg:
 * |           ↑
 * |           b
 * | ← ← ← ← a ☆ c → →
 * |           d
 * |           ↓
 * |           ↓
 * ☆是初始位置，他在这个例子中有4个初始选项
 * 一旦做了某一个初始选项 如果没有障碍/撞墙 那么就是需要一直走下去 也就是沿着上一个选择的方向
 * eg:
 * 假设当前来到(x,y) 并且 当前从左侧进入 也就是右方向
 * 并且假设(x,y,7) 也就是已经走了距离7了
 * 此时不能向右前进了
 * 那么下一个bfs会有3种情况
 * (x-1,y,下) 8
 * (x+1,y,上) 8
 * (x,y-1,右) 8
 */
public class Problem_0499_TheMazeIII {


    /**
     * 节点：来到了哪？(r,c)这个位置
     * 从哪个方向来的！d -> 0 1 2 3 4
     * 4是初始状态 没有方向，0 1 2 3分别代表上下左右
     * 之前做了什么决定让你来到这个位置。
     */
    public static class Node {
        public int r;
        public int c;
        public int d;
        public String p;

        public Node(int row, int col, int dir, String path) {
            r = row;
            c = col;
            d = dir;
            p = path;
        }

    }

    public static String findShortestWay(int[][] maze, int[] ball, int[] hole) {
        int n = maze.length;
        int m = maze[0].length;
        /**
         * 遍历q1里面用来生成q2的所需元素，最后放入q2
         * 轮到遍历q2用来生成q1的所需元素，最后放入q1
         * 2个轮回利用
         */
        Node[] q1 = new Node[n * m], q2 = new Node[n * m];
        int s1 = 0, s2 = 0;
        // 每个格子有4个方向
        boolean[][][] visited = new boolean[maze.length][maze[0].length][4];
        /**
         *  在球所在的节点Node(ball[0], ball[1], 4, "")，生成后填入q1
         */
        s1 = spread(maze, n, m, new Node(ball[0], ball[1], 4, ""), visited, q1, s1);
        while (s1 != 0) {
            //q1的每一个节点调用spread方法 ，生成的节点放入q2
            // 下面的for循环就是把当前的这一层的所有bfs做完，外面的while就是把所有层的bfs做完
            for (int i = 0; i < s1; i++) {
                Node cur = q1[i];
                if (hole[0] == cur.r && hole[1] == cur.c) {
                    return cur.p;
                }
                s2 = spread(maze, n, m, cur, visited, q2, s2);
            }
            //q1和q2空间交换
            Node[] tmp = q1;
            q1 = q2;
            q2 = tmp;
            //元素交换，队列对应的size也要交换，只不过有一个 队列是不用的， size = 0
            s1 = s2;
            s2 = 0;
        }
        return "impossible";
    }

    public static int[][] to = {{1, 0}, {0, -1}, {0, 1}, {-1, 0}, {0, 0}};

    public static String[] re = {"d", "l", "r", "u"};

    /**
     * maze迷宫，走的格子
     * n 行数	m 列数
     * 当前来到的节点，cur -> (r,c) 方向 路径（决定）
     * v [行][列][方向] 一个格子，其实在宽度有限遍历时，是4个点！
     * q 下一层的队列	 因为bfs会使用到queue 这个就是  所有的节点存到一个队列里面
     * s 下一层队列填到了哪，size
     * 当前点cur，该分裂分裂，该继续走继续走，所产生的一下层的点，进入q，s++
     * 返回值：q增长到了哪？返回size -> s
     *
     * @param maze
     * @param n
     * @param m
     * @param cur
     * @param v
     * @param q
     * @param s
     * @return
     */
    public static int spread(int[][] maze, int n, int m,
                             Node cur, boolean[][][] v, Node[] q, int s) {
        int d = cur.d;
        int r = cur.r + to[d][0];
        int c = cur.c + to[d][1];
        // 分裂去！
        // d == 4  初始状态  maze[r][c] != 0 表示障碍  r < 0 || r == n || c < 0 || c == m表示越界和撞墙
        if (d == 4 || r < 0 || r == n || c < 0 || c == m || maze[r][c] != 0) {
            //分裂成4个bfs
            for (int i = 0; i < 4; i++) {
                if (i != d) {
                    r = cur.r + to[i][0];
                    c = cur.c + to[i][1];
                    if (r >= 0 && r < n && c >= 0 && c < m && maze[r][c] == 0 && !v[r][c][i]) {
                        v[r][c][i] = true;
                        Node next = new Node(r, c, i, cur.p + re[i]);
                        q[s++] = next;
                    }
                }
            }
        } else { // 不分裂！继续走！
            if (!v[r][c][d]) {
                v[r][c][d] = true;
                q[s++] = new Node(r, c, d, cur.p);
            }
        }
        return s;
    }

}
