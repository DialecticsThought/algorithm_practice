package algorithmbasic2020_master.class31;

/**
 * TODO  构造线段树
 * ARR = [1,3,5,7,9,11] 每一个节点求出start，end，mid
 *        [0-5] =>  这个节点start=0，end=5  mid = (0+5)/2=2
 *      ↙       ↘
 *    [0-2]    [3-5]  =>  这个节点start=3，end=5  mid = (3+5)/2=4
 *    ↙   ↘     ↙   ↘
 *  [0-1] [2]  [3-4] [5]
 *  ↙ ↘         ↙  ↘
 * [0] [1]     [3] [4]
 * 本质就是
 *          36
 *      ↙       ↘
 *      9       27
 *    ↙  ↘      ↙   ↘
 *   4    5    16    11
 *  ↙ ↘        ↙  ↘
 * 1  3        7   9
 * 如果我填满整棵树，并且写成一个树状数组
 * 那么树就变成
 *          36
 *      ↙       ↘
 *      9       27
 *    ↙  ↘      ↙   ↘
 *   4    5    16    11
 *  ↙ ↘  ↙ ↘   ↙  ↘  ↙ ↘
 * 1  3  N  N  7  9  N  N
 * TREE :
 * [36,9,27,4,5,16,11,1,3,n,n,7,9,n,n]
 *  0  1 2  3 4  5 6  7 8 9 9 10 11 12 13
 * 这个树状数组TREE满足 某一个节点[i]的左子节点=2*i+1 右子节点=2*i+2
 * 我们规定：
 * start，end，mid，idx指定的是ARR的索引，node指定的是TREE索引
 * 并且tree[node]=arr[start]~arr[end]的值
 *TODO
 * 从根节点进入,递归找到叶子节点[x,x]，把该节点的值增加k。然后从下往上更新其祖先节点上的统计值。
 * eg:
 *        [0-5]
 *      ↙       ↘
 *    [0-2]    [3-5]
 *    ↙   ↘     ↙   ↘
 *  [0-1] [2]  [3-4] [5]
 *  ↙ ↘         ↙  ↘
 * [0] [1]     [3] [4]
 * 现在想要给予上面构建的树，进行更新 ，更新arr[idx]=val
 * 这里举例idx=4，val=6
 * 那么怎么知道要修改哪一个节点，哪一个分支
 * 1.
 * 对于这个树的[0-5]节点 start=0,mid=(0+5)/2=2,end=5
 * 判断 idx是否是 idx>=start && idx<=mid
 *  是，则说明需要修改当前节点的左子节点
 *  不是，则说明需要修改当前节点的右子节点
 * 这里是 不是，需要更新[0-5]节点的右子节点[3-4]
 * 2.
 * 对于这个树的[0-5]节点 start=3,mid=(3+5)/2=4,end=5
 * 判断 idx是否是 idx>=start && idx<=mid
 *  是，则说明需要修改当前节点的左子节点
 *  不是，则说明需要修改当前节点的右子节点
 * 这里是 是，需要更新[3-4]节点的左子节点[4]
 * 3.
 * 来到base case 因为 start = end，跟新完对应的tree和arr的值，向上返回
 * 函数
 * void update_tree(int arr[], int tree[], int node,int start,int end,int idx, int val){
 * // base case
 * if (start ==end) {// 开始 ==  结束 说明来到需要修改的节点
 *  arr[idx] = val;
 *  tree[node] = val;
 * }else {// 中间，左，右
 *  int mid = (start + end) / 2;
 *  int left_node=2 * node + 1;
 *  int right_node = 2 * node + 2;
 *  if (idx >= start && idx <= mid){ // 判断idx是当前节点的左子节点的范围 还是右子节点的范围
 *  update_tree(arr,tree, left_node,start, mid, idx, val);
 *  }else {
 *  update_tree(arr, tree, right_node, mid+1, end, idx, val);
 *  }
 *  tree[node] = tree[left_node] + tree[right_node];// 上面的if分支更新完子节点之后，更新当前节点
 *  }
 * }
 * TODO query操作
 *        [0-5]                     36
 *      ↙       ↘               ↙       ↘
 *    [0-2]    [3-5]            9       27
 *    ↙   ↘     ↙   ↘         ↙  ↘      ↙   ↘
 *  [0-1] [2]  [3-4] [5]     4    5    16    11
 *  ↙ ↘         ↙  ↘         ↙ ↘       ↙  ↘
 *  [0] [1]     [3] [4]      1  3       7   9
 *  假设现在需要查找 start=0,end=5 之间的范围L~R的值
 *  [   [        ]       ]
 *  0   L       R        5
 *  相当于劈了一半，分成左部分和右部分，分别查出来 并求和
 *  eg:
 *  [    [         ]     [      ]       ]
 *  0   start      end   L     R        5
 *  [    [         ]     [      ]       ]
 *  0    L         R    start   end     5
 *  这2个情况就是要查询arr的L~R的范围的值 和tree[node]所代表的arr[start]~arr[end]没有关系
 *  也就是R < start || L > end
 *  eg:需要查询2~5的累加和
 *  从arr的角度： 查询的是2-5范围
 *  从根节点进入，递归执行以下过程:
 *  1.若查询区间[x,y]完全覆盖当前节点区间，则立即回溯，并返回该节点的sum值。
 *  2.若左子节点与[x,y]有重叠，则递归访问左子树。
 *  3.若右子节点与[x,y]有重叠，则递归访问右子树。
 *  [1,3,5,7,9,11]
 *   0,1,2,3,4,5
 *  拆成
 *  [1,3,5 | 7,9,11]
 *   0,1,2   3,4,5
 *  拆成
 *  [1,3 | 5]  [7,9 | 11]
 *  拆成
 *  [5] [7,9] [11]
 *  拆成
 *  [5] [7] [9] [11]
 *  写成递归树
 *        [0-5]
 *      ↙↗       ↘↖
 *    [0-2]    [3-5]
 *    ↙↗   ↘↖    ↙↗  ↘↖
 *  [0-1] [2]  [3-4] [5]
 *              ↙↗ ↘↖
 *             [3] [4]
 *  [0-1]不在query的范围内，那么不会走到其子节点,并且向上返回0
 *  并且[3-5]直接返回，不用再遍历子节点 因为 start~end 完全在L~R里面
 *  函数：
 *  int query(int p,int x,int y){//区间查询
 *  if(x<=tr[p].1&&tr[p].r<=y){//覆盖则返回
 *      return tr[p].sum;
 *  }
 *  int m=tr[p].l+tr[p].r>>1;//不覆盖则裂开
 *  int sum=0;
 *  if(x<=m) sum+=query(lc,x,y);
 *  if(y>m) sum+=query(rc,x,y);
 *  return sum;
 *  }
 * TODO
 *  arr = [5,2,0,1,9,7,2,1,2,3]
 *                       [1,10]
 *                ↙                 ↘
 *          [1,5]                       [6,10]
 *         ↙       ↘                ↙           ↘
 *     [1,3]        [4,5]        [6,8]           [9,10]
 *     ↙    ↘       ↙    ↘       ↙    ↘         ↙    ↘
 *  [1,2]  [3,3]  [4,4] [5,5]  [6,7]  [8,8]  [9,9] [10,10]
 *  ↙    ↘                      ↙    ↘
 *  [1,1][2,2]                 [6,6][7,7]
 *  tree = [32,17,15,7,10,10,15,7,0,1,9,9,1,2,3,5,2,7,2]
 *  如果我要让arr[7] 位置上加5 ，递归操作如下
 *                       [1,10]
 *                                  ↘↖
 *          [1,5]                       [6,10]
 *                                  ↙↗
 *     [1,3]        [4,5]        [6,8]           [9,10]
 *                               ↙↗
 *  [1,2]  [3,3]  [4,4] [5,5]  [6,7]  [8,8]  [9,9] [10,10]
 *                                   ↘↖
 *  [1,1][2,2]                 [6,6][7,7]
 *  进tree[1] ↔ 进tree[3] ↔ 进tree[6] ↔ 进tree[12] ↔ 进tree[25]
 *  查询区间4~9
 *                       [1,10]
 *             ↙↗                    ↘↖
 *          [1,5]                       [6,10]
 *                  ↘↖               ↙↗         ↘↖
 *     [1,3]        [4,5]        [6,8]           [9,10]
 *                                              ↙↗
 *  [1,2]  [3,3]  [4,4] [5,5]  [6,7]  [8,8]  [9,9] [10,10]
 *
 *  [1,1][2,2]                 [6,6][7,7]
 * 入1 => 入2 => 入5  re10 => 回2 re10  => 回1 s=10 => 入3 =>入6 re10 => 回3 s=10
 * 入7 =>  入14 re2 =>  回7 s=2 re2 =>  回3 s=12 re12 => 回1 s=22 re22
 * 5.区间修改
 * 例如，对区间[4,9]内的每个数加上5。_如果修改区间[x,y]所覆盖的每个叶子节点，时间将是O(n)的。
 * 我们做懒惰修改,当[x,y]完全覆盖节点区间[a,b]时，先修改该区间的sum值，再打上一个“懒标记”，然后立即返回。
 * 等下次需要时，再下传“懒标记”。这样，可以把每次修改和查询的时间都控制到O(logn)
 *                       [1,10]
 *             ↙↗                    ↘↖
 *          [1,5]                       [6,10]
 *                  ↘↖               ↙↗         ↘↖
 *     [1,3]        [4,5]        [6,8]           [9,10]
 *                                              ↙↗
 *  [1,2]  [3,3]  [4,4] [5,5]  [6,7]  [8,8]  [9,9] [10,10]
 *
 *  [1,1][2,2]                 [6,6][7,7]
 *  [4,5]和 [6,8]和[9,9] 这3个节点承接住了，
 *  这样的话，不会传递到[4,4] [5,5] [6,7]  [8,8] [9,9]这些节点
 *  [6,7]不会传递到 [6,6][7,7]
 *
 */
public class Code01_SegmentTree {

    public static class SegmentTree {

        /**
         * TODO
         * arr[]为原序列的信息从0开始，但在arr里是从1开始的
         * sum[]模拟线段树维护区间和
         * lazy[]为累加和懒惰标记 用来揽住任务
         * change[]为更新的值
         * update[]为更新慵懒标记
         */
        private int MAXN;
        private int[] arr;
        private int[] sum;
        private int[] lazy;//懒信息
        private int[] change;
        private boolean[] update;

        public SegmentTree(int[] origin) {
            MAXN = origin.length + 1;
            //TODO arr[0] 不用 从1开始使用
            arr = new int[MAXN];
            for (int i = 1; i < MAXN; i++) {
                arr[i] = origin[i - 1];
            }
            //TODO MAXN << 2向左移动2位 就是 *4

            //TODO  用来支持脑补概念中，某一个范围的累加和信息
            sum = new int[MAXN << 2];
            //TODO  用来支持脑补概念中，某一个范围沒有往下傳遞的纍加任務
            lazy = new int[MAXN << 2];
            //TODO  用来支持脑补概念中，某一个范围有没有更新操作的任务
            change = new int[MAXN << 2];
            //TODO  用来支持脑补概念中，某一个范围更新任务，更新成了什么
            update = new boolean[MAXN << 2];
        }

        private void pushUp(int rt) {//TODO 更新当前节点的累加和
            //TODO 把左节点2*i和右孩子2*i+1累加起来
            //sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
            sum[rt] = sum[rt * 2] + sum[rt * 2 + 1];
        }

        /**
         * TODO
         * 之前的，所有懒增加，和懒更新，从父范围，发给左右两个子范围
         * 分发策略是什么
         * ln表示左子树元素结点个数，rn表示右子树结点个数
         * rt是父节点的下标
         */
        private void pushDown(int rt, int ln, int rn) {
            //TODO 针对更新操作
            if (update[rt]) {//TODO 对于 change[rt]有更新的任务
                //TODO change[rt]的左孩子 也有更新的任务
                update[rt << 1] = true;
                //TODO change[rt]的右孩子 也有更新的任务
                update[rt << 1 | 1] = true;
                //TODO change[rt]的左孩子 所表示的那段范围的数 更新为change[rt] 表示任务下发
                change[rt << 1] = change[rt];
                //TODO change[rt]的右孩子 所表示的那段范围的数 更新为change[rt] 表示任务下发
                change[rt << 1 | 1] = change[rt];
                //TODO 左和右孩子之前的所有拦下的累加任务都失效
                lazy[rt << 1] = 0;
                lazy[rt << 1 | 1] = 0;
                //TODO 因为修改了一定范围的数 那么左和右孩子之前的累加和也要被修改
                sum[rt << 1] = change[rt] * ln;
                sum[rt << 1 | 1] = change[rt] * rn;
                update[rt] = false;
            }
            //TODO 针对添加操作
            if (lazy[rt] != 0) {
                //TODO 左孩子的之前懒信息+当前节点的的懒信息
                lazy[rt << 1] += lazy[rt];
                //左侧孩子的累加和
                sum[rt << 1] += lazy[rt] * ln;
                //TODO 右孩子的之前懒信息+当前节点的的懒信息
                lazy[rt << 1 | 1] += lazy[rt];
                //右侧孩子的累加和
                sum[rt << 1 | 1] += lazy[rt] * rn;
                //当前节点的的懒信息清空
                lazy[rt] = 0;
            }
        }

        /**
         * TODO
         * 在初始化阶段，先把sum数组，填好
         * 在arr[l~r]范围上，去build建立数组，1~N，这个数组的第一个位置（树的根节点） 就是代表[l~r]范围
         * rt : 这个范围在sum中的下标
         */
        public void build(int l, int r, int rt) {
            if (l == r) {//TODO 说明是叶子结点 不用向子节点派发任务
                sum[rt] = arr[l];//TODO 老数组中的值 直接塞入
                return;
            }
            int mid = (l + r) >> 1;
            //左边的树
            build(l, mid, rt << 1);
            //右边的树
            build(mid + 1, r, rt << 1 | 1);
            pushUp(rt);
        }

        /**
         * TODO
         * 把 arr元素中的 L~R 所有的值变成C
         * change[rt]表示l~r范围
         */
        public void update(int L, int R, int C, int l, int r, int rt) {
            //TODO 如果 change[rt]表示l~r范围
            // 任务如果把此时的范围全包了！
            if (L <= l && r <= R) {
                update[rt] = true;//TODO 标志位 设置成true 表示change[rt] 对应的范围的所有数都更新成C
                change[rt] = C;
                sum[rt] = C * (r - l + 1);//TODO 这里不是累加 而是设置成一个num = 个数 * C
                lazy[rt] = 0;//TODO 之前揽住的所有累加的任务 全部清空
                return;
            }
            // 当前任务躲不掉，无法懒更新，要往下发
            int mid = (l + r) >> 1;
            //TODO 以前的任务下发给子节点
            pushDown(rt, mid - l + 1, r - mid);
            if (L <= mid) {
                update(L, R, C, l, mid, rt << 1);
            }
            if (R > mid) {
                update(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            pushUp(rt);
        }

        /**
         * TODO
         * arr的L~R范围的每个数 + C  任务！
         * 当前来的格子rt，也就是lazy[rt]表示的范围l~r
         */
        public void add(int L, int R, int C, int l, int r, int rt) {
            /**
             *TODO
             * eg: 3~874 范围上加5  也就是 L=3 R=874 C=5
             * 当前来到的格子是表示范围1~1000 假设格子是sum[1]
             * 会下发任务给左右子节点 1~500,2 表示sum[2]表示1~500范围  501~1000,3 表示sum[3]表示501~1000范围
             * 当来到sum[i]节点表达的是251~500范围 那么就不会下发任务 因为任务全部包揽了
             * */
            //TODO 这个情况， 任务如果把此时的范围全包了！
            if (L <= l && r <= R) {
                //TODO 调整累加和
                sum[rt] += C * (r - l + 1);
                //TODO 更新 懒更新的信息  之前揽住的累加的任务 和当前拦住的累加的任务
                lazy[rt] += C;
                return;
            }
            //TODO 来到这里说明 任务没有把你全包！
            int mid = (l + r) / 2;//mid = (l+r) >>1
            pushDown(rt, mid - l + 1, r - mid);//TODO 之前的揽住的任务 先下发 用来承接新任务
            //TODO 到这里老的任务 下发完毕 开始新的任务
            // L~R范围
            if (L <= mid) {//TODO 判断是否要把新的任务发给左孩子
                add(L, R, C, l, mid, (rt * 2));
            }
            if (R > mid) {//TODO 判断是否要把新的任务发给右孩子
                add(L, R, C, mid + 1, r, (rt * 2 + 1));
            }
            pushUp(rt);//TODO 更新当前节点的累加和
        }

        // 1~6 累加和是多少？ 1~8 rt
        public long query(int L, int R, int l, int r, int rt) {
            // 任务如果把此时的范围全包了！
            if (L <= l && r <= R) {
                return sum[rt];
            }
            int mid = (l + r) >> 1;
            // 以前的任务下发给子节点
            pushDown(rt, mid - l + 1, r - mid);
            long ans = 0;
            if (L <= mid) {//TODO 判断是否要把新的任务发给左孩子
                ans += query(L, R, l, mid, rt << 1);
            }
            if (R > mid) {//TODO 判断是否要把新的任务发给右孩子
                ans += query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return ans;
        }

    }

    public static class Right {
        public int[] arr;

        public Right(int[] origin) {
            arr = new int[origin.length + 1];
            for (int i = 0; i < origin.length; i++) {
                arr[i + 1] = origin[i];
            }
        }

        public void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] = C;
            }
        }

        public void add(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] += C;
            }
        }

        public long query(int L, int R) {
            long ans = 0;
            for (int i = L; i <= R; i++) {
                ans += arr[i];
            }
            return ans;
        }

    }

    public static int[] genarateRandomArray(int len, int max) {
        int size = (int) (Math.random() * len) + 1;
        int[] origin = new int[size];
        for (int i = 0; i < size; i++) {
            origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return origin;
    }

    public static boolean test() {
        int len = 100;
        int max = 1000;
        int testTimes = 5000;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = genarateRandomArray(len, max);
            SegmentTree seg = new SegmentTree(origin);
            int S = 1;
            int N = origin.length;
            int root = 1;
            seg.build(S, N, root);
            Right rig = new Right(origin);
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int C = (int) (Math.random() * max) - (int) (Math.random() * max);
                if (Math.random() < 0.5) {
                    seg.add(L, R, C, S, N, root);
                    rig.add(L, R, C);
                } else {
                    seg.update(L, R, C, S, N, root);
                    rig.update(L, R, C);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                long ans1 = seg.query(L, R, S, N, root);
                long ans2 = rig.query(L, R);
                if (ans1 != ans2) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] origin = {2, 1, 1, 2, 3, 4, 5};
        SegmentTree seg = new SegmentTree(origin);
        int S = 1; // 整个区间的开始位置，规定从1开始，不从0开始 -> 固定
        int N = origin.length; // 整个区间的结束位置，规定能到N，不是N-1 -> 固定
        int root = 1; // 整棵树的头节点位置，规定是1，不是0 -> 固定
        int L = 2; // 操作区间的开始位置 -> 可变
        int R = 5; // 操作区间的结束位置 -> 可变
        int C = 4; // 要加的数字或者要更新的数字 -> 可变
        // 区间生成，必须在[S,N]整个范围上build
        seg.build(S, N, root);
        // 区间修改，可以改变L、R和C的值，其他值不可改变
        seg.add(L, R, C, S, N, root);
        // 区间更新，可以改变L、R和C的值，其他值不可改变
        seg.update(L, R, C, S, N, root);
        // 区间查询，可以改变L和R的值，其他值不可改变
        long sum = seg.query(L, R, S, N, root);
        System.out.println(sum);

        System.out.println("对数器测试开始...");
        System.out.println("测试结果 : " + (test() ? "通过" : "未通过"));

    }

}
