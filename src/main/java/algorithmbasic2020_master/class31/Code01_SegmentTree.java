package algorithmbasic2020_master.class31;

/**
 * TODO  构造线段树
 * ARR = [1,3,5,7,9,11] 每一个节点求出start，end，mid
 * <pre>
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
 * </pre>
 * <pre>
 *                                     [1-10]
 *                             /                 \
 *                   [1-5]                           [6-10]
 *                /          \                   /            \
 *          [1-3]           [4-5]              [6-8]          [9-10]
 *         /      \        /     \         /       \         /       \
 *   [1-2]        [3]     [4]   [5]      [6-7]    [8]        [9]    [10]
 *   /   \                               /   \
 * [1]  [2]                            [6]  [7]
 * </pre>
 * <pre> </pre>
 * 任务1：对 [1, 5] 区间加上 5。
 * 根节点 [1, 10]：
 *  当你在根节点上对 [1, 5] 区间执行加 5 操作时，根节点不会直接将这个加法操作应用到其所有子节点上。相反，它会将这个操作的影响记录在它的懒标记上，等待需要访问子节点时再处理。
 *  但是，由于 [1, 5] 是根节点的左子树的子区间，累加操作只会影响到左子树的那部分。
 *  懒标记：根节点的 lazy 值不会立即改变，除非你显式下放懒标记。
 * 左子树 [1, 5]：
 *  左子树覆盖的区间正好是 [1, 5]，因此它会直接记录这个操作，即加 5。
 *  懒标记：左子树节点的 lazy 值应该记录 5。
 * 右子树 [6, 10]：
 *  右子树的区间 [6, 10] 与操作区间 [1, 5] 没有重叠，因此不会受到影响。
 *  懒标记：右子树的 lazy 值保持不变，应该是 0。
 * <p>
 * 任务2：对 [1, 3] 区间加上 3。
 * 下放懒标记
 *  当要对 [1, 3] 区间加 3 时，由于左子树 [1, 5] 上已有一个未处理的累加 5 的懒标记，所以首先需要下放这个懒标记。
 *  下放懒标记时：
 *      左子树 [1, 5] 的累加 5 被传递到其子节点：左子树 [1, 3] 和右子树 [4, 5]。
 *      左子树 [1, 3] 接收到累加 5 的懒标记。
 *      右子树 [4, 5] 接收到累加 5 的懒标记。
 * 应用新的累加操作：
 *  累加 3 的操作只影响到 [1, 3] 区间。
 *  左子树 [1, 3] 上累加 5 的懒标记会与新的累加 3 操作相加，结果为累加 8。
 * 结果分析：
 *  根节点 [1, 10]：懒标记 0。根节点的懒标记已下放，它的懒标记已清空。
 *  左子树 [1, 5]：懒标记 0。由于懒标记 5 已被下放到它的子节点，左子树 [1, 5] 的懒标记清空。
 *  左子树 [1, 3]：懒标记 8。累加 5 的懒标记已经下放到此节点，然后再加上累加 3 的新操作，总共累加 8。
 *  右子树 [4, 5]：懒标记 5。累加 5 的懒标记已下放到 [4, 5]，但没有受到任务2的影响，所以保持为 5。
 *  右子树 [6, 10]：懒标记 0。右子树与操作区间无关，因此懒标记保持为 0。
 * <p>
 * 任务3：对 [6, 10] 区间加上 2。
 * 执行累加操作：
 *  当对 [6, 10] 区间加 2 时，这个操作只影响右子树 [6, 10]，所以懒标记 2 被直接记录在右子树 [6, 10] 节点上。
 * 结果分析：
 *  根节点 [1, 10]：懒标记 0。根节点没有累加标记，因为它的子树已分别处理过之前的操作。
 *  左子树 [1, 5]：懒标记 0。左子树之前的操作已经处理过，它的懒标记为 0。
 *  左子树 [1, 3]：懒标记 8。继承了任务2中的累加 8 标记，保持不变。
 *  右子树 [4, 5]：懒标记 5。继承了任务2中的累加 5 标记，保持不变。
 *  右子树 [6, 10]：懒标记 2。新加的累加操作被记录为 2，因为这个操作只影响 [6, 10] 的区间。
 *
 * 任务4：对1,5区间执行覆盖操作（设为10）
 *  理想中的执行过程 ☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆
 *      步骤1：从根节点开始递归下放懒标记
 *          根节点 [1, 10]：懒标记为 0，无需下放。这是正确的。
 *          左子树 [1, 5]：当前有懒标记 5，这个标记需要被下放到其子节点 [1, 3] 和 [4, 5]。
 *          子节点 [1, 3]：原本累加标记为 8（从之前的操作得来），现在累加 5 下放后，应该是 8 + 5 = 13。
 *          子节点 [4, 5]：懒标记为 5，这个标记需要下放到其子节点 [4, 4] 和 [5, 5]。
 *          子节点 [1, 2]：继续下放懒标记，累加 13。
 *          子节点 [1, 1]：累加 13。
 *          子节点 [2, 2]：累加 13。
 *          子节点 [3, 3]：累加 13。
 *          子节点 [4, 4] 和 [5, 5]：每个节点的累加标记为 5。
 *      步骤2：执行覆盖操作
 *          覆盖操作：覆盖操作会将 [1, 5] 区间内的所有子节点的值直接设为 10，并清除所有懒标记。这是一个直接的覆盖操作，所以所有累加的标记将被清除，节点的值设为 10。
 *      具体执行覆盖操作后的结果：
 *          节点 [1, 1]：懒标记清空，值设为 10。
 *          节点 [2, 2]：懒标记清空，值设为 10。
 *          节点 [3, 3]：懒标记清空，值设为 10。
 *          节点 [4, 4]：懒标记清空，值设为 10。
 *          节点 [5, 5]：懒标记清空，值设为 10。
 *  正确的执行过程 ☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆
 *      在任务4中，当 [1, 5] 区间执行覆盖操作时：
 *      1.update[rt] 为 true：如果当前节点 rt（如 [1, 5]）已经设置了更新标记（覆盖操作），pushDown 会进入 if (update[rt]) 分支，将更新标记传递给左右子节点。
 *      2.执行覆盖操作：update[rt] 的子节点会收到这个更新标记，清除累加标记，并直接更新它们的值为覆盖值。
 *      3.lazy[rt] 被清除：覆盖操作清除累加标记，因为覆盖值取代了累加结果。
 *
 * 任务6：递归访问节点[2,2] 假设没有做任务4，从任务3开始
 * 1. 从根节点 [1, 10] 开始递归访问：
 *  当前区间：[1, 10]
 *  操作：目标区间 [2, 2] 位于 [1, 10]，我们需要继续访问左子节点 [1, 5]。
 *  懒标记：根节点的累加标记为 0，无需处理懒标记或下放。
 * 2. 访问左子节点 [1, 5]：
 *  当前区间：[1, 5]
 *  操作：目标区间 [2, 2] 位于 [1, 5]，我们需要继续访问左子节点 [1, 3]。
 *  懒标记：节点 [1, 5] 有累加标记 5（来自任务1），需要将这个标记下放到其子节点 [1, 3] 和 [4, 5]。
 *  左子节点 [1, 3]：累加标记 5 传递后，加上之前的累加 3，变为 8。
 *  右子节点 [4, 5]：接收累加标记 5。
 * 3. 访问左子节点 [1, 3]：
 *  当前区间：[1, 3]
 *  操作：目标区间 [2, 2] 位于 [1, 3]，我们继续访问左子节点 [1, 2]。
 *  懒标记：节点 [1, 3] 有累加标记 8（任务1和任务2的累加结果），需要将累加标记下放到其子节点 [1, 2] 和 [3, 3]。
 *  左子节点 [1, 2]：接收累加标记 8。
 *  右子节点 [3, 3]：接收累加标记 8。
 * 4. 访问左子节点 [1, 2]：
 *  当前区间：[1, 2]
 *  操作：目标区间 [2, 2] 位于 [1, 2]，我们继续访问右子节点 [2, 2]。
 *  懒标记：节点 [1, 2] 有累加标记 8，需要将累加标记下放到其子节点 [1, 1] 和 [2, 2]。
 *  左子节点 [1, 1]：接收累加标记 8。
 *  右子节点 [2, 2]：接收累加标记 8。
 */
public class Code01_SegmentTree {

    public static class SegmentTree {
        private int MAXN;
        private int[] arr;// arr[]为原序列的信息从0开始，但在arr里是从1开始的
        private int[] sum;// sum[]模拟线段树维护区间和
        private int[] lazy;//懒信息 用来揽住任务
        // 当我们对一个区间 [L, R] 执行更新操作时，我们希望将这个区间内的所有值都设置为 val。
        // 这个 val 就会被保存到 change 数组的相应位置。
        private int[] change;// change[]为更新的值
        //update[rt] 为 true，则表示 change[rt] 中存储的值需要被应用到该节点的区间
        private boolean[] update;// update[]为更新慵懒标记
        private int n;         // 原始数组的大小

        public SegmentTree(int[] origin) {
            n = origin.length;  // 设置原始数组的大小
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
         * @param rt 是当前节点在线段树中的索引，用于访问线段树中存储的数组
         * @param l  当前节点所代表的区间的左边界（左端点）。
         * @param r  当前节点所代表的区间的右边界（右端点）。
         *           这两个参数共同确定了当前节点覆盖的区间 [l, r]。
         *           示例：假设当前节点覆盖的区间是 [1, 5]，那么 l = 1，r = 5。*
         * @param ln 当前节点的左子节点所覆盖的区间长度。
         * @param rn 当前节点的右子节点所覆盖的区间长度。
         *           这两个参数分别表示左子区间和右子区间的大小，用于在懒标记下放时正确计算累加值对子节点的影响。
         *           示例：如果当前节点覆盖的区间是 [1, 5]，那么它的左子节点覆盖 [1, 3]，右子节点覆盖 [4, 5]。
         *           在这种情况下，ln = 3（即 [1, 3] 的长度），rn = 2（即 [4, 5] 的长度）
         */
        private void pushDown(int rt, int l, int r, int ln, int rn) {
            int mid = (l + r) / 2;
            System.out.println("正在将节点的: " + rt + " (范围: [" + l + ", " + r + "])" + " 懒标记（即未被处理的更新或累加操作）传递给其子节点: ");
            printNodeState(rt, l, r);

            //TODO 针对更新操作
            if (update[rt]) {//TODO 对于 change[rt]有更新的任务
                System.out.println("更新标记已经被成功传递并应用到节点: " + rt + " (范围: [" + l + ", " + r + "]) 的子节点上，" +
                        "左子节点范围: [" + l + ", " + mid + "], 右子节点范围: [" + (mid + 1) + ", " + r + "]");
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
                //sum[rt << 1] = change[rt] * l;
                //sum[rt << 1 | 1] = change[rt] * r;
                sum[rt * 2] = change[rt] * ln;
                sum[rt * 2 + 1] = change[rt] * rn;
                update[rt] = false;

                System.out.println("已经被成功传递并应用到节点: " + rt + " (范围: [" + l + ", " + r + "])" + "的子节点上");
                printNodeState(rt * 2, l, mid);  // 左子节点
                printNodeState(rt * 2 + 1, mid + 1, r);  // 右子节点
            }
            if (lazy[rt] != 0) {//TODO 针对添加操作
                System.out.println("节点：" + rt + " (范围: [" + l + ", " + r + "]) 懒标记的累加操作已被传递并应用到子节点上，" +
                        "左子节点范围: [" + l + ", " + mid + "], 右子节点范围: [" + (mid + 1) + ", " + r + "]");
                lazy[rt << 1] += lazy[rt];//TODO 左孩子的之前懒信息+当前节点的的懒信息
                sum[rt * 2] += lazy[rt] * ln;  //左侧孩子的累加和
                lazy[rt << 1 | 1] += lazy[rt]; //TODO 右孩子的之前懒信息+当前节点的的懒信息
                sum[rt * 2 + 1] += lazy[rt] * rn; //右侧孩子的累加和
                lazy[rt] = 0; //当前节点的的懒信息清空

                System.out.println("节点：" + rt + " (范围: [" + l + ", " + r + "])" + "懒标记的累加操作已被传递并应用到子节点上");
                printNodeState(rt * 2, l, mid);  // 左子节点
                printNodeState(rt * 2 + 1, mid + 1, r);  // 右子节点
            }

            System.out.println("节点：" + rt + " (范围: [" + l + ", " + r + "])" + "的所有懒标记（包括累加和更新）都已经被下放并处理完毕");
            printNodeState(rt, l, r);
        }

        /**
         * TODO
         * 在初始化阶段，先把sum数组，填好
         * 在arr[l~r]范围上，去build建立数组，1~N，这个数组的第一个位置（树的根节点） 就是代表[l~r]范围
         * rt : 这个范围在sum中的下标
         */
        public void build(int l, int r, int rt) {
            if (l == r) {//TODO 说明是叶子结点 不用向子节点派发任务
                sum[rt] = arr[l];//TODO 叶子节点的值等于原始数组的值
                return;
            }
            int mid = (l + r) >> 1;
            //左边的树
            build(l, mid, rt << 1);
            //右边的树
            build(mid + 1, r, rt << 1 | 1);
            // 向上更新当前节点的区间和
            pushUp(rt);
        }

        // 区间更新方法，将[L, R]范围内的所有元素更新为val
        public void update(int L, int R, int val) {
            System.out.println("\nUpdating range [" + L + ", " + R + "] to " + val);
            update(L, R, val, 1, n, 1);             // 从根节点开始执行update操作
            System.out.println("范围更新过的树:");
            printAllNodes();                        // 打印整个树的状态
        }

        /**
         * 把 arr元素中的 L~R 所有的值变成C
         * change[rt]表示l~r范围
         *
         * @param L  当你希望更新数组中的某个区间时，L 和 R 指定了这个区间的开始和结束位置
         * @param R  当你希望更新数组中的某个区间时，L 和 R 指定了这个区间的开始和结束位置
         * @param C  C 表示要将目标区间 [L, R] 内的值更新为的值。如果是覆盖操作，C 就是新值；如果是累加操作，C 就是要加上的值
         * @param l  在递归过程中，l 和 r 指定了当前节点所覆盖的区间范围。
         * @param r  在递归过程中，l 和 r 指定了当前节点所覆盖的区间范围。
         * @param rt 在递归过程中，rt 用于标识当前正在处理的节点，便于操作线段树数组
         */
        public void update(int L, int R, int C, int l, int r, int rt) {
            //TODO 如果 change[rt]表示l~r范围
            // 任务如果把此时的范围全包了！
            if (L <= l && r <= R) {  // 如果当前区间完全在[L, R]范围内
                update[rt] = true;//TODO 标志位 设置成true 表示change[rt] 对应的范围的所有数都更新成C
                change[rt] = C; // 设置更新值
                sum[rt] = C * (r - l + 1);//TODO 这里不是累加 而是设置成一个num = 个数 * C
                lazy[rt] = 0;//TODO 之前揽住的所有累加的任务 全部清空
                printNodeState(rt, l, r);
                return;
            }
            // 当前任务躲不掉，无法懒更新，要往下发
            int mid = (l + r) >> 1;  // 计算中点
            //TODO 以前的任务下发给子节点
            pushDown(rt, l, r, mid - l + 1, r - mid); // 向下传递懒惰标记
            if (L <= mid) {   // 如果左子区间与[L, R]有重叠
                update(L, R, C, l, mid, rt << 1);  // 递归处理左子区间
            }
            if (R > mid) {  // 如果右子区间与[L, R]有重叠
                update(L, R, C, mid + 1, r, rt << 1 | 1);  // 递归处理右子区间
            }
            pushUp(rt);  // 向上更新当前节点的区间和
        }

        // 区间增加方法，在[l, r]范围内的所有元素加上val
        public void add(int L, int R, int val) {
            System.out.println("\nAdding " + val + " to range [" + L + ", " + R + "]");
            add(L, R, val, 1, n, 1);                // 从根节点开始执行add操作
            System.out.println("范围添加某一个值之后的数:");
            printAllNodes();                        // 打印整个树的状态
        }


        /**
         * @param L  当你希望数组中的某个区间都加上C时，L 和 R 指定了这个区间的开始和结束位置
         * @param R  当你希望数组中的某个区间都加上C时，L 和 R 指定了这个区间的开始和结束位置
         * @param C  C 表示要将目标区间 [L, R] 内的值更新为的值。如果是覆盖操作，C 就是新值；如果是累加操作，C 就是要加上的值
         * @param l  在递归过程中，l 和 r 指定了当前节点所覆盖的区间范围。
         * @param r  在递归过程中，l 和 r 指定了当前节点所覆盖的区间范围。
         * @param rt 在递归过程中，rt 用于标识当前正在处理的节点，便于操作线段树数组
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
            if (L <= l && r <= R) {// 如果当前区间完全在[L, R]范围内
                sum[rt] += C * (r - l + 1); //TODO 更新当前节点的区间和
                lazy[rt] += C; //TODO 更新 懒更新的信息  之前揽住的累加的任务 和当前拦住的累加的任务
                return;
            }
            //TODO 来到这里说明 任务没有把你全包！
            int mid = (l + r) / 2;//mid = (l+r) >>1
            pushDown(rt, l, r, mid - l + 1, r - mid);//TODO 之前的揽住的任务 先下发 用来承接新任务
            //TODO 到这里老的任务 下发完毕 开始新的任务
            // L~R范围
            if (L <= mid) {//TODO  如果左子区间与[L, R]有重叠 判断是否要把新的任务发给左孩子
                add(L, R, C, l, mid, (rt * 2)); // 递归处理左子区间
            }
            if (R > mid) {//TODO 如果右子区间与[L, R]有重叠 判断是否要把新的任务发给右孩子
                add(L, R, C, mid + 1, r, (rt * 2 + 1)); // 递归处理右子区间
            }
            pushUp(rt);//TODO 向上更新当前节点的区间和
        }

        /**
         * @param L  希望查询数组中的某个区间时，L 指定了这个区间的开始
         * @param R  希望查询数组中的某个区间时，R 指定了这个区间的结束
         * @param l  在递归过程中，l 和 r 指定了当前节点所覆盖的区间范围
         * @param r  在递归过程中，l 和 r 指定了当前节点所覆盖的区间范围
         * @param rt 在递归过程中，rt 用于标识当前正在处理的节点，便于查询线段树数组中当前节点的区间和（或其他值）
         * @return
         */
        public long query(int L, int R, int l, int r, int rt) {
            if (L <= l && r <= R) {                 // 如果当前区间完全在[L, R]范围内
                return sum[rt];                     // 返回当前节点的区间和
            }
            int mid = (l + r) >> 1;                  // 计算中点
            pushDown(rt, l, r, mid - l + 1, r - mid);     // 向下传递懒惰标记
            long ans = 0;
            if (L <= mid) {                         // 如果左子区间与[L, R]有重叠
                ans += query(L, R, l, mid, rt << 1); // 递归查询左子区间
            }
            if (R > mid) {                          // 如果右子区间与[L, R]有重叠
                ans += query(L, R, mid + 1, r, rt << 1 | 1); // 递归查询右子区间
            }
            return ans;                             // 返回查询结果
        }

        // 打印所有节点及其详细信息的方法
        public void printAllNodes() {
            printAllNodes(1, n, 1);                 // 从根节点开始打印
        }

        // 递归打印每个节点的信息
        private void printAllNodes(int l, int r, int rt) {
            if (l == r) {                           // 如果是叶子节点
                printNodeState(rt, l, r);
                return;
            }
            int mid = (l + r) / 2;                  // 计算中点
            printNodeState(rt, l, r);                     // 打印当前节点状态
            printAllNodes(l, mid, rt * 2);          // 打印左子树的信息
            printAllNodes(mid + 1, r, rt * 2 + 1);  // 打印右子树的信息
        }

        // 递归打印所有受懒标记影响的节点，包括叶子节点
        private void printAffectedNodes(int rt, int l, int r) {
            printNodeState(rt, l, r);  // 打印当前节点状态

            if (l == r) return;  // 如果是叶子节点，返回

            int mid = (l + r) / 2;  // 计算中点
            printAffectedNodes(rt * 2, l, mid);  // 打印左子树所有受影响的节点
            printAffectedNodes(rt * 2 + 1, mid + 1, r);  // 打印右子树所有受影响的节点
        }

        // 打印某个节点的状态
        private void printNodeState(int rt, int l, int r) {
            System.out.println("Node: " + rt + " (Range: [" + l + ", " + r + "])" +
                    ", sum: " + sum[rt] +
                    ", lazy: " + lazy[rt] +
                    ", change: " + change[rt] +
                    ", update: " + update[rt]);
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
/*        int[] origin = {2, 1, 1, 2, 3, 4, 5};
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
        System.out.println("测试结果 : " + (test() ? "通过" : "未通过"));*/

        int[] origin = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        SegmentTree segmentTree = new SegmentTree(origin);

        System.out.println("Initial tree structure:");
        segmentTree.printAllNodes();  // 打印初始状态的线段树

        // 任务1：对1,5区间加上5
        System.out.println("Task 1: Add 5 to range [1, 5]");
        segmentTree.add(1, 5, 5); // 对1到5范围内所有元素加5
        // 下放懒标记的过程和修改后的状态将自动打印
        System.out.println("================================================\n");

        // 任务2：对1,3区间加上3
        System.out.println("Task 2: Add 3 to range [1, 3]");
        segmentTree.add(1, 3, 3); // 对1到3范围内所有元素加3
        // 下放懒标记的过程和修改后的状态将自动打印
        System.out.println("================================================\n");

        // 任务3：对6,10区间加上2
        System.out.println("Task 3: Add 2 to range [6, 10]");
        segmentTree.add(6, 10, 2); // 对6到10范围内所有元素加2
        // 下放懒标记的过程和修改后的状态将自动打印
        System.out.println("================================================\n");

        // 任务4：对6,10区间加上2
/*        System.out.println("Task 4: Add 4 to range [2, 7]");
        segmentTree.add(2, 7, 4); // 对6到10范围内所有元素加2
        System.out.println("================================================\n");*/

        // 任务5：对1,5区间执行覆盖操作（设为10）
        //System.out.println("Task 5: Update range [1, 5] to 10");
        //segmentTree.update(1, 5, 10); // 将1到5范围内的所有元素更新为10
        // 下放懒标记的过程和修改后的状态将自动打印
        //System.out.println("================================================\n");

        // 任务6：递归访问并更新子节点2,2（加2）
        System.out.println("Task 6: Query node [2, 2]");
        // 递归查询节点[2, 2]的值
        long result = segmentTree.query(2, 2, 1, origin.length, 1);
        System.out.println("Value at range [2, 2]: " + result);
        segmentTree.printAllNodes();  // 打印初始状态的线段树
        // 下放懒标记的过程和修改后的状态将自动打印
        System.out.println("================================================\n");
    }

}
