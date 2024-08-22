package other.ad2.class01;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class BricksFallingWhenHit1 {

    public static class Dot {
    }

    public static class UnionFind {
        private int[][] grid; //主函数处理后的原始矩阵  也就是变成2的矩阵
        private int N;//行数
        private int M; //列数
        private int cellingAll; //有多少个1能连接到天花板
        private Dot[][] dots; //位置到点的对应关系
        //某一个点不是集合的代表点 一定不会在set中  某一个点是集合的代表点且接到天花板上 不会在set中
        private HashSet<Dot> cellingSet;//集合能够连到天花板，它的代表点才在里面
        /*
         * 利用dot去做并查集
         * */
        private HashMap<Dot, Dot> fatherMap;//任何一个dot都有记录，value就是父节点
        private HashMap<Dot, Integer> sizeMap;//只有一个dot是代表点，才有记录，value表示这个集合的大小

        public UnionFind(int[][] matrix) {
            initSpace(matrix);
            initconnect();
        }

        private void initSpace(int[][] matrix) {
            grid = matrix;
            N = grid.length;
            M = grid[0].length;
            cellingAll = 0;
            dots = new Dot[N][M]; // dot null
            cellingSet = new HashSet<>();
            fatherMap = new HashMap<>();
            sizeMap = new HashMap<>();
            //初始化
            for (int row = 0; row < N; row++) {
                for (int col = 0; col < M; col++) {
                    if (grid[row][col] == 1) {//该点是我关心的，不是1就不关心
                        //每一个是1的点都会生成dot 用dot的内存地址来代表值为1的点
                        Dot cur = new Dot();
                        //把dot放入矩阵中
                        dots[row][col] = cur;
                        //一开始 自己是自己的父节点  每一个节点（置为1的）都是单独一个集合
                        fatherMap.put(cur, cur);
                        sizeMap.put(cur, 1);
                        if (row == 0) { // dot是天花板上的点
                            //自己所在的集合是天花板集合
                            cellingSet.add(cur);
                            cellingAll++;//接到天花板的数量++
                        }
                    }
                }
            }
        }

        /*
            每一个位置会找上下左右 是1的都连在一起
        */
        private void initconnect() {
            for (int row = 0; row < N; row++) {
                for (int col = 0; col < M; col++) {
                    union(row, col, row - 1, col);//点(row,col)和下方连
                    union(row, col, row + 1, col);//点(row,col)和上方连
                    union(row, col, row, col - 1);//点(row,col)和左方连
                    union(row, col, row, col + 1);
                }
            }
        }

        //row，col的dot，所在的集合，代表dot是谁返回
        private Dot find(int row, int col) {
            Dot cur = dots[row][col];
            Stack<Dot> stack = new Stack<>();
            while (cur != fatherMap.get(cur)) {
                stack.add(cur);
                cur = fatherMap.get(cur);
            }
            while (!stack.isEmpty()) {
                fatherMap.put(stack.pop(), cur);//修改每一个栈中对应的父节点
            }
            return cur;//返回代表点
        }
    /*
        点(r1,c1)  和点(r2,c2)相邻 才能union
        并且两个点是1
     */

        private void union(int r1, int c1, int r2, int c2) {
            if (valid(r1, c1) && valid(r2, c2)) {
                /*
                 * 执行相连
                 * 2个集合的父节点相连
                 */
                Dot father1 = find(r1, c1);
                Dot father2 = find(r2, c2);
                if (father1 != father2) {
                    /*
                     * 集合1多少个1  集合2多少个1
                     * 集合1和2分别是不是天花板的集合
                     * 是就是true 不是就是false
                     * */
                    int size1 = sizeMap.get(father1);
                    int size2 = sizeMap.get(father2);
                    boolean status1 = cellingSet.contains(father1);
                    boolean status2 = cellingSet.contains(father2);
                    if (size1 <= size2) {//father1挂在father2底下
                        fatherMap.put(father1, father2);
                        sizeMap.put(father2, size1 + size2);
                        if (status1 ^ status2) {//如果两个集合是否能接到天花板的状态不一样  也就是一个是天花板的集合另一个不是 的情况
                            cellingSet.add(father2);//英两两个集合合并了那么就一定是天花板大集合
                            cellingAll += status1 ? size2 : size1;//接到天花板是1的数量怎么变 哪一个不是天花板的集合 就增加相应的1
                        }
                    } else {
                        fatherMap.put(father2, father1);
                        sizeMap.put(father1, size1 + size2);
                        if (status1 ^ status2) {
                            cellingSet.add(father1);
                            cellingAll += status1 ? size2 : size1;
                        }
                    }
                }
            }
        }


        private boolean valid(int row, int col) {
            return row >= 0 && row < N && col >= 0 && col < M && grid[row][col] == 1;
        }

        public int cellingNum() {
            return cellingAll;
        }


        public int finger(int row, int col) {
            //戳了一个点（这个点原先有砖块 但是炮弹的出现变成了2） 重新还原变成1
            grid[row][col] = 1;
            //新建一个dot 对应这个点
            Dot cur = new Dot();
            dots[row][col] = cur
            /*
             * 如果大的炮弹的位置就在天花板上
             * cellingSet增加 爬蛋还原的点
             * */;
            if (row == 0) {
                cellingSet.add(cur);
                cellingAll++;
            }
            fatherMap.put(cur, cur);//当前这个点被当做一个集合
            sizeMap.put(cur, 1);
            /*
             * 评估合并前后的天花板的数量
             * 确定掉落的砖块数
             * */
            int pre = cellingAll;
            union(row, col, row - 1, col);
            union(row, col, row + 1, col);
            union(row, col, row, col - 1);
            union(row, col, row, col + 1);
            int now = cellingAll;
            if (row == 0) {
                return now - pre;
            } else {
                return now == pre ? 0 : now - pre - 1;
            }
        }
    }


    public static int[] hitBricks(int[][] grid, int[][] hits) {
        for (int i = 0; i < hits.length; i++) {
            //既有炮弹击中 原始值是1的地方  如果击中的地方是0 什么都不做
            if (grid[hits[i][0]][hits[i][1]] == 1) {
                //炮弹所击中的位置原本有砖块 变成2
                grid[hits[i][0]][hits[i][1]] = 2;
            }
        }
        //搞并查集
        UnionFind unionFind = new UnionFind(grid);
        int[] ans = new int[hits.length];
        for (int i = hits.length - 1; i >= 0; i--) {
            /*
             * 逆序考察所有炮弹
             * 必须是2的位置（也就是炮弹打到了）
             * */
            if (grid[hits[i][0]][hits[i][1]] == 2) {
                ans[i] = unionFind.finger(hits[i][0], hits[i][1]);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        //int[][] grid = {{1, 0, 1}, {1, 1, 1}};
        //int[][] hits = {{0, 0}, {0, 2}, {1, 1}};
        int[][] grid = {
                {1,0,1,1,0},
                {1,1,0,1,0},
                {1,0,0,1,1},
                {1,1,0,1,0}
        };
        int[][] hits = {
                {1,0},
                {2,3},
                {0,3}
        };
        int[] ans = hitBricks(grid, hits);
        for (int i = 0; i < ans.length; i++) {
            System.out.println(ans[i]);
        }
    }
}
