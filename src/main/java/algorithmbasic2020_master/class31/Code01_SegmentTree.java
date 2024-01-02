package algorithmbasic2020_master.class31;

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
